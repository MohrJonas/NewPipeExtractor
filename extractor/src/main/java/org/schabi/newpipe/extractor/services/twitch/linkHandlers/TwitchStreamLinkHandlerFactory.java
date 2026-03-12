package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;
import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchStreamId;
import org.schabi.newpipe.extractor.utils.Utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class TwitchStreamLinkHandlerFactory extends TwitchBaseLinkHandlerFactory {

    @Override
    public String getId(final String urlString) throws ParsingException, UnsupportedOperationException {
        return TwitchStreamId.fromString(urlString).getStreamId();
    }

    @Override
    public String getUrl(final String id) throws ParsingException, UnsupportedOperationException {
        return new TwitchStreamId(id).toString();
    }

    @Override
    public boolean onAcceptUrl(final String urlString) throws ParsingException {
        try {
            TwitchStreamId.fromString(urlString);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }
}
