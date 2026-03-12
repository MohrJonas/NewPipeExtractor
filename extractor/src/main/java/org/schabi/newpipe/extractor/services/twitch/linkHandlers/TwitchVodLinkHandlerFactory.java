package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchVodId;
import org.schabi.newpipe.extractor.utils.Utils;

import java.net.MalformedURLException;
import java.util.List;

public final class TwitchVodLinkHandlerFactory extends TwitchBaseLinkHandlerFactory {

    // VOD are of the format twitch.tv/videos/<id> or m.twitch.tv/videos/<id>
    // -> Convert to live:<id>
    @Override
    public String getId(final String urlString) throws ParsingException, UnsupportedOperationException {
        try {
            final var url = Utils.stringToURL(Utils.removeMAndWWWFromUrl(urlString));
            final var pathParts = url.getHost().split("/");
            return new TwitchVodId(pathParts[1]).toString();
        }
        catch (MalformedURLException e) {
            throw new ParsingException("Unable to parse url " + urlString, e);
        }
    }


    @Override
    public String getUrl(final String id) throws ParsingException, UnsupportedOperationException {
        return buildUrl("videos", TwitchVodId.fromString(id).getVodId());
    }

    @Override
    public boolean onAcceptUrl(final String urlString) throws ParsingException {
        if(!super.onAcceptUrl(urlString))
            return false;
        try {
            final var url = Utils.stringToURL(Utils.removeMAndWWWFromUrl(urlString));
            final var pathParts = url.getPath().split("/");
            return pathParts.length == 2 && pathParts[0].equals("videos");
        }
        catch (MalformedURLException e) {
            return false;
        }
    }
}
