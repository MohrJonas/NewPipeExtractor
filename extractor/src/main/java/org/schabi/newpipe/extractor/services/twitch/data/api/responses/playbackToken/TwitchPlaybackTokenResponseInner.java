package org.schabi.newpipe.extractor.services.twitch.data.api.responses.playbackToken;

import javax.annotation.Nonnull;

public final class TwitchPlaybackTokenResponseInner {

    @Nonnull
    private final String signature;

    @Nonnull
    private final String value;

    public TwitchPlaybackTokenResponseInner(@Nonnull String signature, @Nonnull String value) {
        this.signature = signature;
        this.value = value;
    }

    @Nonnull
    public String getSignature() {
        return signature;
    }

    @Nonnull
    public String getValue() {
        return value;
    }
}
