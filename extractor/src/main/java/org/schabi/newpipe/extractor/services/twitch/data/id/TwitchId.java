package org.schabi.newpipe.extractor.services.twitch.data.id;

public abstract class TwitchId {

    protected static final String partSeparator = ":::";

    private final TwitchIdType idType;

    protected TwitchId(final TwitchIdType idType) {
        this.idType = idType;
    }

    public TwitchIdType getIdType() {
        return idType;
    }

    public static TwitchIdType getIdTypeFromString(final String s) {
        return TwitchIdType.fromString(s.split(partSeparator)[0]);
    }
}
