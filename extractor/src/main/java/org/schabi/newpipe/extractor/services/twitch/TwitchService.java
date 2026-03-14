package org.schabi.newpipe.extractor.services.twitch;

import static org.schabi.newpipe.extractor.StreamingService.ServiceInfo.MediaCapability.LIVE;

import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.ChannelExtractor;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.comments.CommentsExtractor;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.kiosk.KioskList;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.linkhandler.ReadyChannelTabListLinkHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandler;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.playlist.PlaylistExtractor;
import org.schabi.newpipe.extractor.search.SearchExtractor;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.extractors.TwitchChannelExtractor;
import org.schabi.newpipe.extractor.services.twitch.extractors.TwitchClipExtractor;
import org.schabi.newpipe.extractor.services.twitch.extractors.TwitchSearchExtractor;
import org.schabi.newpipe.extractor.services.twitch.extractors.TwitchStreamExtractor;
import org.schabi.newpipe.extractor.services.twitch.extractors.TwitchVodExtractor;
import org.schabi.newpipe.extractor.services.twitch.kiosks.TwitchLiveKiosk;
import org.schabi.newpipe.extractor.services.twitch.linkHandlers.TwitchChannelLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.twitch.linkHandlers.TwitchLiveKioskLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.twitch.linkHandlers.TwitchSearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.services.twitch.linkHandlers.TwitchStreamLinkHandlerFactory;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.subscription.SubscriptionExtractor;
import org.schabi.newpipe.extractor.suggestion.SuggestionExtractor;

import java.util.EnumSet;

public final class TwitchService extends StreamingService {

    public TwitchService(final int id) {
        super(id, "Twitch", EnumSet.of(LIVE));
    }

    @Override
    public String getBaseUrl() {
        return "https://twitch.tv";
    }

    @Override
    public SearchExtractor getSearchExtractor(SearchQueryHandler queryHandler) {
        return new TwitchSearchExtractor(this, queryHandler);
    }

    @Override
    public LinkHandlerFactory getStreamLHFactory() {
        return new TwitchStreamLinkHandlerFactory();
    }

    @Override
    public SearchQueryHandlerFactory getSearchQHFactory() {
        return new TwitchSearchQueryHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelLHFactory() {
        return new TwitchChannelLinkHandlerFactory();
    }

    @Override
    public ListLinkHandlerFactory getChannelTabLHFactory() {
        return null;
    }

    @Override
    public ListLinkHandlerFactory getPlaylistLHFactory() {
        return null;
    }

    @Override
    public ListLinkHandlerFactory getCommentsLHFactory() {
        return null;
    }

    @Override
    public SuggestionExtractor getSuggestionExtractor() {
        return null;
    }

    @Override
    public SubscriptionExtractor getSubscriptionExtractor() {
        return null;
    }

    @Override
    public KioskList getKioskList() throws ExtractionException {
        try {
            final var list = new KioskList(this);
            final var streamHandler = new TwitchLiveKioskLinkHandlerFactory();
            list.addKioskEntry((streamingService, url, kioskId) ->
                            new TwitchLiveKiosk(streamingService, streamHandler.fromUrl(url)),
                    streamHandler,
                    TwitchLiveKiosk.KIOSK_ID
            );
            list.setDefaultKiosk(TwitchLiveKiosk.KIOSK_ID);
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ChannelExtractor getChannelExtractor(ListLinkHandler linkHandler) throws ExtractionException {
        return new TwitchChannelExtractor(this, linkHandler);
    }

    @Override
    public ChannelTabExtractor getChannelTabExtractor(ListLinkHandler linkHandler) throws ExtractionException {
        if (linkHandler instanceof ReadyChannelTabListLinkHandler) {
            return ((ReadyChannelTabListLinkHandler) linkHandler).getChannelTabExtractor(this);
        }
        return null;
    }

    @Override
    public PlaylistExtractor getPlaylistExtractor(ListLinkHandler linkHandler) throws ExtractionException {
        return null;
    }

    @Override
    public StreamExtractor getStreamExtractor(LinkHandler linkHandler) throws ExtractionException {
        final var url = linkHandler.getUrl();
        switch (TwitchId.getIdTypeFromString(url)) {
            case CLIP:
                return new TwitchClipExtractor(this, linkHandler);
            case STREAM:
                return new TwitchStreamExtractor(this, linkHandler);
            case VOD:
                return new TwitchVodExtractor(this, linkHandler);
        }
        throw new ExtractionException("Cannot get StreamExtractor for url " + url);
    }

    @Override
    public CommentsExtractor getCommentsExtractor(ListLinkHandler linkHandler) throws ExtractionException {
        return null;
    }
}
