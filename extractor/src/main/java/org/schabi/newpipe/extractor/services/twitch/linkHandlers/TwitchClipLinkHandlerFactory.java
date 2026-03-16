package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchClipId;

public final class TwitchClipLinkHandlerFactory extends TwitchBaseLinkHandlerFactory {

    // Clips are of the format clips.twitch.tv/<id> or twitch.tv/<channel>/clip/<id>
    // -> Converted to clip:<channel>:<id>
    @Override
    public String getId(final String urlString) throws ParsingException, UnsupportedOperationException {
//        try {
//            final var url = Utils.stringToURL(Utils.removeMAndWWWFromUrl(urlString));
//            final var pathParts = url.getPath().split("/");
//            return pathParts.length == 1
//                ? new TwitchClipId(pathParts[0], null).toString()
//                : new TwitchClipId(pathParts[2], pathParts[0]).toString();
//        } catch (MalformedURLException e) {
//            throw new ParsingException("Unable to parse url " + urlString, e);
//        }
        return urlString;
    }

    @Override
    public String getUrl(final String id) throws ParsingException, UnsupportedOperationException {
        return new TwitchClipId(id).toString();
    }

    @Override
    public boolean onAcceptUrl(String url) throws ParsingException {
        try {
            TwitchClipId.fromString(url);
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }
}
