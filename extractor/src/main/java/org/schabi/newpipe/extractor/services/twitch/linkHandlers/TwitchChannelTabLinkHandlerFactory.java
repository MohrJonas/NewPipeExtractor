package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandlerFactory;

import java.util.List;

public final class TwitchChannelTabLinkHandlerFactory extends ListLinkHandlerFactory {
    @Override
    public String getUrl(String id, List<String> contentFilter, String sortFilter) throws ParsingException, UnsupportedOperationException {
        return "";
    }

    @Override
    public String getId(String url) throws ParsingException, UnsupportedOperationException {
        return "";
    }

    @Override
    public boolean onAcceptUrl(String url) throws ParsingException {
        return true;
    }
}
