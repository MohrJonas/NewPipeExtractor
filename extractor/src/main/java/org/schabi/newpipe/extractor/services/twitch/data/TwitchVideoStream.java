package org.schabi.newpipe.extractor.services.twitch.data;

import javax.annotation.Nonnull;

public final class TwitchVideoStream {

    @Nonnull
    private final String resolution;

    @Nonnull
    private final String streamUrl;

    public TwitchVideoStream(@Nonnull String resolution, @Nonnull String streamUrl) {
        this.resolution = resolution;
        this.streamUrl = streamUrl;
    }

    @Nonnull
    public String getResolution() {
        return resolution;
    }

    @Nonnull
    public String getStreamUrl() {
        return streamUrl;
    }
}
