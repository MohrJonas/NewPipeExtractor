package org.schabi.newpipe.extractor.services.twitch.data.api.responses.stream;

import javax.annotation.Nonnull;

public final class TwitchStreamResponseInner {

    private final String streamerName;
    private final String streamTitle;

    public TwitchStreamResponseInner(@Nonnull String streamerName, @Nonnull String streamTitle) {
        this.streamerName = streamerName;
        this.streamTitle = streamTitle;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public String getStreamerName() {
        return streamerName;
    }
}
