package org.schabi.newpipe.extractor.services.twitch.kiosks;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.kiosk.KioskExtractor;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.services.twitch.api.ThumbnailURLGenerator;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.nowLive.TwitchNowLiveResponse;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchChannelId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchStreamId;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class TwitchLiveKiosk extends KioskExtractor<StreamInfoItem> {

    public static final String KIOSK_ID = "live";

    private TwitchNowLiveResponse response;

    public TwitchLiveKiosk(StreamingService streamingService, ListLinkHandler linkHandler) {
        super(streamingService, linkHandler, KIOSK_ID);
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            response = TwitchApi.getNowLiveInformation(downloader);
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return KIOSK_ID;
    }

    @Nonnull
    @Override
    public InfoItemsPage<StreamInfoItem> getInitialPage() throws IOException, ExtractionException {
        return new InfoItemsPage<>(
                response.getData()
                        .stream()
                        .map(liveEntry -> {
                            final var infoItem = new StreamInfoItem(
                                    getServiceId(),
                                    new TwitchStreamId(liveEntry.getStreamerName()).toString(),
                                    liveEntry.getStreamTitle(),
                                    StreamType.LIVE_STREAM
                            );
                            infoItem.setViewCount(liveEntry.getStreamViewers());
                            infoItem.setUploaderName(liveEntry.getStreamerName());
                            infoItem.setUploaderUrl(new TwitchChannelId(liveEntry.getStreamerName()).toString());
                            infoItem.setShortDescription(liveEntry.getGameName());
                            infoItem.setUploaderAvatars(List.of(new Image(liveEntry.getThumbnailUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.LOW)));
                            infoItem.setThumbnails(List.of(new Image(liveEntry.getThumbnailUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.MEDIUM)));
                            return infoItem;
                        })
                        .collect(Collectors.toList()),
                null,
                Collections.emptyList());
    }

    @Override
    public InfoItemsPage<StreamInfoItem> getPage(Page page) throws IOException, ExtractionException {
        return getInitialPage();
    }
}
