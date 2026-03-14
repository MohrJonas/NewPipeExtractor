package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.clip.TwitchClipResponseInner;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchChannelId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchClipId;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public final class TwitchChannelClipExtractor extends ChannelTabExtractor {

    private TwitchClipResponseInner[] response;

    public TwitchChannelClipExtractor(@Nonnull StreamingService service, @Nonnull ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        return new InfoItemsPage<>(Arrays.stream(response).map(res -> {
                    final var item = new StreamInfoItem(
                            getServiceId(),
                            new TwitchClipId(res.getClipId()).toString(),
                            res.getClipTitle(),
                            StreamType.NONE
                    );
                    item.setThumbnails(List.of(new Image(
                            res.getVodThumbnailUrl(),
                            Image.HEIGHT_UNKNOWN,
                            Image.WIDTH_UNKNOWN,
                            Image.ResolutionLevel.UNKNOWN
                    )));
                    item.setDuration(res.getClipLength());
                    item.setViewCount(res.getClipViewerCount());
                    item.setShortDescription("(" + res.getClipperName() + "), " + res.getGameName());
                    try {
                        item.setUploadDate(DateWrapper.fromInstant(res.getUploadDateTimeString()));
                    } catch (ParsingException ignored) {}
                    return item;
                }
        ).collect(Collectors.toList()), null, Collections.emptyList());
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        return getInitialPage();
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            final var channelId = TwitchChannelId.fromString(getUrl());
            response = TwitchApi.getTwitchClips(downloader, channelId.getChannelName()).getData();
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }
}
