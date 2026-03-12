package org.schabi.newpipe.extractor.services.twitch.data.id;

public enum TwitchIdType {
    VOD,
    CLIP,
    STREAM,
    QUERY,
    CHANNEL,
    GAME,
    KIOSK;

    public static String asString(final TwitchIdType idType) {
        switch (idType) {
            case VOD:
                return "vod";
            case CLIP:
                return "clip";
            case STREAM:
                return "stream";
            case QUERY:
                return "query";
            case KIOSK:
                return "kiosk";
            case CHANNEL:
                return "channel";
            case GAME:
                return "game";
            default:
                throw new IllegalArgumentException("Cannot convert TwitchIdType " + idType + " to string");
        }
    }

    public static TwitchIdType fromString(final String idType) {
        switch (idType) {
            case "vod":
                return TwitchIdType.VOD;
            case "clip":
                return TwitchIdType.CLIP;
            case "stream":
                return TwitchIdType.STREAM;
            case "query":
                return TwitchIdType.QUERY;
            case "kiosk":
                return TwitchIdType.KIOSK;
            case "channel":
                return TwitchIdType.CHANNEL;
            case "game":
                return TwitchIdType.GAME;
            default:
                throw new IllegalArgumentException("Cannot convert string " + idType + "to TwitchIdType");
        }
    }
}
