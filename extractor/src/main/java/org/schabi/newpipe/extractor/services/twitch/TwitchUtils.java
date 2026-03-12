package org.schabi.newpipe.extractor.services.twitch;

public final class TwitchUtils {

    private TwitchUtils() {}

    public static boolean isSuccessfulResponseCode(int code) {
        // 2xx is success. Not all there exist but whatever
        return code >= 200 && code < 300;
    }
}
