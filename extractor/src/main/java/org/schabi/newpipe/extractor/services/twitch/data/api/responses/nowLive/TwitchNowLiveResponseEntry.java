package org.schabi.newpipe.extractor.services.twitch.data.api.responses.nowLive;

public final class TwitchNowLiveResponseEntry {

    private final String streamerName;
    private final String streamTitle;
    private final int streamViewers;
    private final String thumbnailUrl;
    private final String gameName;

    public TwitchNowLiveResponseEntry(String streamerName, String streamTitle, int streamViewers, String thumbnailUrl, String gameName) {
        this.streamerName = streamerName;
        this.streamTitle = streamTitle;
        this.streamViewers = streamViewers;
        this.thumbnailUrl = thumbnailUrl;
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public int getStreamViewers() {
        return streamViewers;
    }

    public String getStreamTitle() {
        return streamTitle;
    }

    public String getStreamerName() {
        return streamerName;
    }
}
