package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandlerFactory;

public abstract class TwitchBaseLinkHandlerFactory extends LinkHandlerFactory {

    protected static final String TwitchHost = "twitch.tv";
    protected static final String LIVE_PREFIX = "live";
    protected static final String VOD_PREFIX = "vod";
    protected static final String CLIP_PREFIX = "clip";

    protected static String buildId(String prefix, String... values) {
        return prefix + ":" + String.join(":", values);
    }

    protected static String buildUrl(String... pathElements) {
        return "https://" + TwitchHost + String.join("/", pathElements);
    }

    @Override
    public boolean onAcceptUrl(final String urlString) throws ParsingException {
//        try {
//            return Utils
//                .stringToURL(Utils.removeMAndWWWFromUrl(urlString))
//                .getHost()
//                .equals(TwitchHost);
//        } catch (MalformedURLException e) {
//            return false;
//        }
        return true;
    }
}
