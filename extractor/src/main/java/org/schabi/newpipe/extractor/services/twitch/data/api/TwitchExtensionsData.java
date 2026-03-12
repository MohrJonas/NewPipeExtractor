package org.schabi.newpipe.extractor.services.twitch.data.api;

public final class TwitchExtensionsData {
    private final long requestDuration;

    public TwitchExtensionsData(long requestDuration) {
        this.requestDuration = requestDuration;
    }

    public long getRequestDuration() {
        return requestDuration;
    }
}
