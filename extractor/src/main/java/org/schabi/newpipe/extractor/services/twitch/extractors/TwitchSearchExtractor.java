package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.MetaInfo;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelInfoItem;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.playlist.PlaylistInfoItem;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.search.TwitchSearchResponse;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.search.types.TwitchSearchChannelResponseEntry;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.search.types.TwitchSearchGameResponseEntry;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.search.types.TwitchSearchStreamResponseEntry;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.search.types.TwitchSearchVodResponseEntry;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchChannelId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchGameId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchQueryId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchStreamId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchVodId;
import org.schabi.newpipe.extractor.stream.StreamInfoItem;
import org.schabi.newpipe.extractor.stream.StreamType;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class TwitchSearchExtractor extends SearchExtractor {

    private final Map<String, StreamInfoItem> cache;
    private TwitchSearchResponse response;

    public TwitchSearchExtractor(final StreamingService service,
                                 final SearchQueryHandler linkHandler,
                                 final Map<String, StreamInfoItem> cache) {
        super(service, linkHandler);
        this.cache = cache;
    }

    private static StreamInfoItem buildStreamInfoItem(final int serviceId, final TwitchSearchStreamResponseEntry entry) {
        final var item = new StreamInfoItem(
                serviceId,
                new TwitchStreamId(entry.getChannelName()).toString(),
                entry.getStreamTitle(),
                StreamType.LIVE_STREAM
        );
        item.setViewCount(entry.getStreamViewerCount());
        item.setShortDescription(entry.getStreamGameName());
        item.setUploaderAvatars(List.of(
                new Image(entry.getChannelAvatarUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.UNKNOWN)
        ));
        item.setUploaderName(entry.getChannelName());
        item.setThumbnails(List.of(
                new Image(entry.getStreamThumbnailUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.UNKNOWN)
        ));
        return item;
    }

    private static ChannelInfoItem buildChannelInfoItem(final int serviceId, final TwitchSearchChannelResponseEntry entry) {
        final var item = new ChannelInfoItem(
                serviceId,
                new TwitchChannelId(entry.getChannelName()).toString(),
                entry.getChannelName()
        );
        item.setThumbnails(List.of(
                new Image(entry.getChannelAvatarUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.UNKNOWN)
        ));
        item.setDescription(entry.getChannelDescription());
        item.setVerified(entry.isChannelPartnered());
        item.setSubscriberCount(entry.getChannelFollowerCount());
        return item;
    }

    private static PlaylistInfoItem buildGameInfoItem(final int serviceId, final TwitchSearchGameResponseEntry entry) {
        final var item = new PlaylistInfoItem(
                serviceId,
                new TwitchGameId(entry.getGameName()).toString(),
                entry.getGameName()
        );
        item.setThumbnails(List.of(
                new Image(entry.getGameBoxArtUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.UNKNOWN)
        ));
        return item;
    }

    private static StreamInfoItem buildVodInfoItem(final int serviceId, final TwitchSearchVodResponseEntry entry) {
        final var item = new StreamInfoItem(
                serviceId,
                // FIXME this should be vodId, not vod title
                new TwitchVodId(entry.getVodTitle()).toString(),
                entry.getVodTitle(),
                StreamType.POST_LIVE_STREAM
        );
        item.setViewCount(entry.getVodViewCount());
        item.setDuration(entry.getVodDurationInSeconds());
        item.setUploaderName(entry.getChannelName());
        item.setThumbnails(List.of(
                new Image(entry.getStreamThumbnailUrl(), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.UNKNOWN)
        ));
        return item;
    }

    @Nonnull
    @Override
    public String getSearchSuggestion() throws ParsingException {
        return "";
    }

    @Override
    public boolean isCorrectedSearch() throws ParsingException {
        return false;
    }

    @Nonnull
    @Override
    public List<MetaInfo> getMetaInfo() throws ParsingException {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        response.getData()
                .stream()
                .filter(searchEntry ->
                        searchEntry instanceof TwitchSearchStreamResponseEntry
                                || searchEntry instanceof TwitchSearchVodResponseEntry
                )
                .forEach(searchEntry -> {
                    final var key = searchEntry instanceof TwitchSearchStreamResponseEntry
                            ? new TwitchStreamId(((TwitchSearchStreamResponseEntry) searchEntry).getChannelName()).toString()
                            : new TwitchVodId(((TwitchSearchVodResponseEntry) searchEntry).getVodTitle()).toString();
                    final var value = searchEntry instanceof TwitchSearchStreamResponseEntry
                            ? buildStreamInfoItem(getServiceId(), (TwitchSearchStreamResponseEntry) searchEntry)
                            : buildVodInfoItem(getServiceId(), (TwitchSearchVodResponseEntry) searchEntry);
                    cache.put(key, value);
                });
        return new InfoItemsPage<>(
                response.getData()
                        .stream()
                        .map(searchEntry -> {
                            if (searchEntry instanceof TwitchSearchChannelResponseEntry)
                                return buildChannelInfoItem(getServiceId(), (TwitchSearchChannelResponseEntry) searchEntry);
                            else if (searchEntry instanceof TwitchSearchStreamResponseEntry)
                                return buildStreamInfoItem(getServiceId(), (TwitchSearchStreamResponseEntry) searchEntry);
                            else if (searchEntry instanceof TwitchSearchVodResponseEntry)
                                return buildVodInfoItem(getServiceId(), (TwitchSearchVodResponseEntry) searchEntry);
                            else if (searchEntry instanceof TwitchSearchGameResponseEntry)
                                return buildGameInfoItem(getServiceId(), (TwitchSearchGameResponseEntry) searchEntry);
                            else throw new RuntimeException();
                        })
                        .collect(Collectors.toList()),
                null,
                Collections.emptyList()
        );
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        return getInitialPage();
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            final var queryId = TwitchQueryId.fromString(getUrl());
            response = TwitchApi.getSearchResponse(downloader, queryId.getQueryString());
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }
}
