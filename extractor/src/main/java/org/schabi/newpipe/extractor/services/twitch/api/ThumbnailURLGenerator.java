package org.schabi.newpipe.extractor.services.twitch.api;

public final class ThumbnailURLGenerator {

    private static final String THUMBNAIL_URL_TEMPLATE = "https://static-cdn.jtvnw.net/previews-ttv/live_user_%s-440x248.jpg";

    public static String getThumbnailURLForStream(final String streamerName) {
        return String.format(THUMBNAIL_URL_TEMPLATE, streamerName.toLowerCase());
    }
}
