package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.localization.DateWrapper;
import org.schabi.newpipe.extractor.services.twitch.api.ThumbnailURLGenerator;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.stream.TwitchStreamResponseInner;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchStreamId;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

public final class TwitchChannelStreamExtractor extends ChannelTabExtractor {

    private TwitchStreamResponseInner response;

    public TwitchChannelStreamExtractor(@Nonnull StreamingService service, @Nonnull ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        var item = new StreamInfoItem(
                getServiceId(),
                new TwitchStreamId(response.getStreamerName()).toString(),
                response.getStreamTitle(),
                StreamType.LIVE_STREAM
        );
        item.setUploaderName(response.getStreamerName());
        item.setViewCount(response.getViewerCount());
        item.setUploadDate(DateWrapper.fromInstant(response.getCreatedDateString()));
        item.setThumbnails(
                List.of(new Image(
                        ThumbnailURLGenerator.getThumbnailURLForStream(response.getStreamerName()),
                        Image.HEIGHT_UNKNOWN,
                        Image.WIDTH_UNKNOWN,
                        Image.ResolutionLevel.UNKNOWN)));
        return new InfoItemsPage<>(List.of(item), null, Collections.emptyList());
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        return getInitialPage();
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            response = TwitchApi.getStreamInformation(downloader, getId()).getData();
        }
        catch (JsonParserException e) {
            throw new IOException(e);
        }
    }
}
