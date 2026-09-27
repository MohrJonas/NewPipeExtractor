package org.schabi.newpipe.extractor.services.twitch.data.api.responses.clip;

import javax.annotation.Nonnull;

public class TwitchVideoPlayerMediaSessionManagerResponseInner {
    @Nonnull
    final String clipTitle;

    @Nonnull
    public String getClipTitle() {
        return clipTitle;
    }

    @Nonnull
    public String getOwnerDisplayName() {
        return ownerDisplayName;
    }

    @Nonnull
    public String getOwnerLoginName() {
        return ownerLoginName;
    }

    @Nonnull
    public String getOwnerProfileImageUrl() {
        return ownerProfileImageUrl;
    }

    @Nonnull final String ownerDisplayName;

    @Nonnull final String ownerLoginName;

    @Nonnull final String ownerProfileImageUrl;

    public TwitchVideoPlayerMediaSessionManagerResponseInner(@Nonnull String clipTitle, @Nonnull String ownerDisplayName, @Nonnull String ownerLoginName, @Nonnull String ownerProfileImageUrl) {
        this.clipTitle = clipTitle;
        this.ownerDisplayName = ownerDisplayName;
        this.ownerLoginName = ownerLoginName;
        this.ownerProfileImageUrl = ownerProfileImageUrl;
    }
}
