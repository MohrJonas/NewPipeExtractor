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
import org.schabi.newpipe.extractor.services.twitch.TwitchUrlParser;
import org.schabi.newpipe.extractor.services.twitch.TwitchUtils;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.Resolution;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.channel.TwitchChannelResponseInner;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

public class TwitchChannelExtractor extends ChannelExtractor {

    private TwitchChannelResponseInner channelResponse;

    public TwitchChannelExtractor(final StreamingService service,
                                  final ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public List<Image> getAvatars() throws ParsingException {
        final var resolution =
                TwitchUtils.tryGetResolutionFromUrl(channelResponse.streamerAvatarUrl());
        final var imageHeight = resolution.map(Resolution::height)
                .orElse(Image.HEIGHT_UNKNOWN);
        final var imageWidth = resolution.map(Resolution::width)
                .orElse(Image.WIDTH_UNKNOWN);
        final var resolutionLevel = Image.ResolutionLevel.fromHeight(imageHeight);
        return List.of(
                new Image(channelResponse.streamerAvatarUrl(), imageHeight, imageWidth, resolutionLevel)
        );
    }

    @Nonnull
    @Override
    public List<Image> getBanners() throws ParsingException {
        final var resolution =
                TwitchUtils.tryGetResolutionFromUrl(channelResponse.channelBannerUrl());
        final var imageHeight = resolution.map(Resolution::height)
                .orElse(480);
        final var imageWidth = resolution.map(Resolution::width)
                .orElse(1200);
        final var resolutionLevel = Image.ResolutionLevel.fromHeight(imageHeight);
        return List.of(
                new Image(channelResponse.channelBannerUrl(), imageHeight, imageWidth, resolutionLevel)
        );
    }

    @Override
    public String getFeedUrl() throws ParsingException {
        return null;
    }

    @Override
    public long getSubscriberCount() throws ParsingException {
        return channelResponse.followerCount();
    }

    @Override
    public String getDescription() throws ParsingException {
        return channelResponse.streamerDescription();
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
                TwitchChannelVodExtractor::new
        ));
        // Clips are not really shorts, but probably still the best fit ¯\_(ツ)_/¯
        tabs.add(new ReadyChannelTabListLinkHandler(
                getUrl(),
                getId(),
                ChannelTabs.SHORTS,
                TwitchChannelClipExtractor::new
        ));
        return tabs;
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            final var channelName = TwitchUrlParser.parseChannelNameFromChannelUrl(getUrl());
            channelResponse = TwitchApi.getTwitchChannel(downloader, channelName).getData();
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return channelResponse.channelName();
    }
}
