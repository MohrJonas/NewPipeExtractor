package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;

public final class TwitchGameId extends TwitchId {

    @Nonnull
    private final String gameName;

    public TwitchGameId(@Nonnull String gameName) {
        super(TwitchIdType.GAME);
        this.gameName = gameName;
    }

    public static @Nonnull TwitchGameId fromString(@Nonnull final String twitchIdString) {
        final var parts = twitchIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 2);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.GAME);

        return new TwitchGameId(parts[1]);
    }

    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), gameName);
    }

    @Nonnull
    public String getGameName() {
        return gameName;
    }
}
