package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabs;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.services.twitch.TwitchUtils;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.ImageSize;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.channel.TwitchChannelResponseInner;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchChannelId;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public class TwitchChannelExtractor extends ChannelExtractor {

    private final Map<String, StreamInfoItem> cache;
    private TwitchChannelResponseInner channelResponse;

    public TwitchChannelExtractor(final StreamingService service,
                                  final ListLinkHandler linkHandler,
                                  final Map<String, StreamInfoItem> cache) {
        super(service, linkHandler);
        this.cache = cache;
    }

    @Nonnull
    @Override
    public List<Image> getAvatars() throws ParsingException {
        final var imageSize =
                TwitchUtils.tryGetImageSizeFromUrl(channelResponse.getStreamerAvatarUrl());
        final var imageHeight = imageSize.map(ImageSize::getImageHeight)
                .orElse(Image.HEIGHT_UNKNOWN);
        final var imageWidth = imageSize.map(ImageSize::getImageWidth)
                .orElse(Image.WIDTH_UNKNOWN);
        final var resolutionLevel = Image.ResolutionLevel.fromHeight(imageHeight);
        return List.of(
                new Image(channelResponse.getStreamerAvatarUrl(), imageHeight, imageWidth, resolutionLevel)
        );
    }

    @Nonnull
    @Override
    public List<Image> getBanners() throws ParsingException {
        final var imageSize =
                TwitchUtils.tryGetImageSizeFromUrl(channelResponse.getChannelBannerUrl());
        final var imageHeight = imageSize.map(ImageSize::getImageHeight)
                .orElse(480);
        final var imageWidth = imageSize.map(ImageSize::getImageWidth)
                .orElse(1200);
        final var resolutionLevel = Image.ResolutionLevel.fromHeight(imageHeight);
        return List.of(
                new Image(channelResponse.getChannelBannerUrl(), imageHeight, imageWidth, resolutionLevel)
        );
    }

    @Override
    public String getFeedUrl() throws ParsingException {
        return null;
    }

    @Override
    public long getSubscriberCount() throws ParsingException {
        return channelResponse.getFollowerCount();
    }

    @Override
    public String getDescription() throws ParsingException {
        return channelResponse.getStreamerDescription();
    }

    @Override
    public String getParentChannelName() throws ParsingException {
        return "";
    }

    @Override
    public String getParentChannelUrl() throws ParsingException {
        return "";
    }

    @Nonnull
    @Override
    public List<Image> getParentChannelAvatars() throws ParsingException {
        return List.of();
    }

    @Override
    public boolean isVerified() throws ParsingException {
        return channelResponse.isPartner();
    }

    @Nonnull
    @Override
    public List<ListLinkHandler> getTabs() throws ParsingException {
        var tabs = new LinkedList<ListLinkHandler>();
        if (channelResponse.isLive())
            tabs.add(new ReadyChannelTabListLinkHandler(
                    getUrl(),
                    getId(),
                    ChannelTabs.LIVESTREAMS,
                    TwitchChannelStreamExtractor::new
            ));
        tabs.add(new ReadyChannelTabListLinkHandler(
                getUrl(),
                getId(),
                ChannelTabs.VIDEOS,
                (service, linkHandler) ->
                        new TwitchChannelVodExtractor(service, linkHandler, cache)
        ));
        // Clips are not really shorts, but probably still the best fit ¯\_(ツ)_/¯
        tabs.add(new ReadyChannelTabListLinkHandler(
                getUrl(),
                getId(),
                ChannelTabs.SHORTS,
                (service, linkHandler) ->
                    new TwitchChannelClipExtractor(service, linkHandler, cache)
        ));
        return tabs;
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            channelResponse = TwitchApi.getTwitchChannel(downloader, TwitchChannelId.fromString(getUrl()).getChannelName()).getData();
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return channelResponse.getChannelName();
    }
}
