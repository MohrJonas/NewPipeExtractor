package org.schabi.newpipe.extractor.services.twitch.data.api.responses.channel;

import javax.annotation.Nonnull;

public final class TwitchChannelResponseInner {

    @Nonnull
    private final String channelName;

    @Nonnull
    private final String channelBannerUrl;

    @Nonnull
    private final String streamerAvatarUrl;

    @Nonnull
    private final String streamerDescription;

    private final int followerCount;

    private final boolean isPartner;

    private final boolean isLive;

    public TwitchChannelResponseInner(@Nonnull String channelName, @Nonnull String channelBannerUrl, @Nonnull String streamerAvatarUrl, @Nonnull String streamerDescription, int followerCount, boolean isPartner, boolean isLive) {
        this.channelName = channelName;
        this.channelBannerUrl = channelBannerUrl;
        this.streamerAvatarUrl = streamerAvatarUrl;
        this.streamerDescription = streamerDescription;
        this.followerCount = followerCount;
        this.isPartner = isPartner;
        this.isLive = isLive;
    }

    @Nonnull
    public String getChannelBannerUrl() {
        return channelBannerUrl;
    }

    @Nonnull
    public String getStreamerAvatarUrl() {
        return streamerAvatarUrl;
    }

    @Nonnull
    public String getStreamerDescription() {
        return streamerDescription;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public boolean isPartner() {
        return isPartner;
    }

    @Nonnull
    public String getChannelName() {
        return channelName;
    }

    public boolean isLive() {
        return isLive;
    }
}
