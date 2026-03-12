package org.schabi.newpipe.extractor.services.twitch.data.api.responses.search;

public class TwitchSearchResponseEntry {
    private final boolean isLive;
    private final String name;
    private final String streamTitle;
    private final int streamViewers;
    private final String thumbnailUrl;
    private final String gameName;
    private final String avatarUrl;

    public TwitchSearchResponseEntry(boolean isLive, String name, String streamTitle, int streamViewers, String thumbnailUrl, String gameName, String avatarUrl) {
        this.isLive = isLive;
        this.name = name;
        this.streamTitle = streamTitle;
        this.streamViewers = streamViewers;
        this.thumbnailUrl = thumbnailUrl;
        this.gameName = gameName;
        this.avatarUrl = avatarUrl;
    }

    public int getStreamViewers() {
        return streamViewers;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public String getName() {
        return name;
    }

    public boolean isLive() {
        return isLive;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getGameName() {
        return gameName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
