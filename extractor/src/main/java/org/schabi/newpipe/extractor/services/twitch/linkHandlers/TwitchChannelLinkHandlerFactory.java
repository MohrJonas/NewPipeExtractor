package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchChannelId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchStreamId;

import java.util.List;

public final class TwitchChannelLinkHandlerFactory extends ListLinkHandlerFactory {

    @Override
    public String getId(String url) throws ParsingException, UnsupportedOperationException {
        try {
            return TwitchChannelId.fromString(url).getChannelName();
        }
        catch (AssertionError ignored) {}
        return TwitchStreamId.fromString(url).getStreamId();
    }

    @Override
    public String getUrl(String id, List<String> contentFilter, String sortFilter) throws ParsingException, UnsupportedOperationException {
        return new TwitchChannelId(id).toString();
    }

    @Override
    public boolean onAcceptUrl(String urlString) throws ParsingException {
        try {
            TwitchChannelId.fromString(urlString);
            return true;
        }
        catch (AssertionError ignored) {}
        try {
            TwitchStreamId.fromString(urlString);
            return true;
        }
        catch (AssertionError ignored) {}
        return false;
    }
}
