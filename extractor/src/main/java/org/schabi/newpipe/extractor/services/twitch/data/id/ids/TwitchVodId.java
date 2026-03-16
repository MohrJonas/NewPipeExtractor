package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;

public final class TwitchVodId extends TwitchId {

    @Nonnull
    private final String vodId;

    public TwitchVodId(@Nonnull final String vodId) {
        super(TwitchIdType.VOD);
        this.vodId = vodId;
    }

    public static @Nonnull TwitchVodId fromString(@Nonnull final String twitchIdString) {
        final var parts = twitchIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 2);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.VOD);

        return new TwitchVodId(parts[1]);
    }

    @Nonnull
    public String getVodId() {
        return vodId;
    }

    @Override
    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), vodId);
    }
}