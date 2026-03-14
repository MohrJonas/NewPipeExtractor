package org.schabi.newpipe.extractor.services.twitch.data.api.responses.clip;

import javax.annotation.Nonnull;

public final class TwitchClipPlaybackResponseInner {

    @Nonnull
    private final String clipUrl;

    private final int clipHeight;

    private final int clipWidth;

    public TwitchClipPlaybackResponseInner(@Nonnull String clipUrl, int clipHeight, int clipWidth) {
        this.clipUrl = clipUrl;
        this.clipHeight = clipHeight;
        this.clipWidth = clipWidth;
    }

    @Nonnull
    public String getClipUrl() {
        return clipUrl;
    }

    public int getClipHeight() {
        return clipHeight;
    }

    public int getClipWidth() {
        return clipWidth;
    }
}
