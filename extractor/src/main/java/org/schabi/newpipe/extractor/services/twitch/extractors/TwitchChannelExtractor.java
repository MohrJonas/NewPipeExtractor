package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabs;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.channel.TwitchChannelResponseInner;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchChannelId;
import org.schabi.newpipe.extractor.services.twitch.linkHandlers.TwitchChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

public class TwitchChannelExtractor extends ChannelExtractor {

    private TwitchChannelResponseInner channelResponse;

    public TwitchChannelExtractor(StreamingService service, ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public List<Image> getAvatars() throws ParsingException {
        return List.of(
                new Image(channelResponse.getStreamerAvatarUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.MEDIUM)
        );
    }

    @Nonnull
    @Override
    public List<Image> getBanners() throws ParsingException {
        return List.of(
                new Image(channelResponse.getChannelBannerUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.HIGH)
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
        return Collections.emptyList();
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
