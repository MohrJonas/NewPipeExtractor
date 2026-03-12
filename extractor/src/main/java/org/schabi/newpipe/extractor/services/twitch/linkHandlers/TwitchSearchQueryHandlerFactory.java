package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.SearchQueryHandlerFactory;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchQueryId;

import java.util.List;

public final class TwitchSearchQueryHandlerFactory extends SearchQueryHandlerFactory  {
    @Override
    public String getUrl(String query,
                         List<String> contentFilter,
                         String sortFilter) throws ParsingException, UnsupportedOperationException {
        return new TwitchQueryId(query).toString();
    }
}
