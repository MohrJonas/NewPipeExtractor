package org.schabi.newpipe.extractor.services.twitch.data.api.responses.stream;

import javax.annotation.Nonnull;

public final class TwitchStreamResponseInner {

    @Nonnull
    private final String streamerName;

    @Nonnull
    private final String streamTitle;
    private final int viewerCount;

    @Nonnull
    private final String createdDateString;

    public TwitchStreamResponseInner(@Nonnull String streamerName, @Nonnull String streamTitle, int viewerCount, @Nonnull String createdDateString) {
        this.streamerName = streamerName;
        this.streamTitle = streamTitle;
        this.viewerCount = viewerCount;
        this.createdDateString = createdDateString;
    }

    @Nonnull
    public String getStreamTitle() {
        return streamTitle;
    }

    @Nonnull
    public String getStreamerName() {
        return streamerName;
    }

    public int getViewerCount() {
        return viewerCount;
    }

    @Nonnull
    public String getCreatedDateString() {
        return createdDateString;
    }
}
