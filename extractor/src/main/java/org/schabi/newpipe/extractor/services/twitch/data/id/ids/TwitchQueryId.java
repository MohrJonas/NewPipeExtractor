package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;

public final class TwitchQueryId extends TwitchId {

    @Nonnull
    private final String queryString;

    public TwitchQueryId(@Nonnull final String queryString) {
        super(TwitchIdType.QUERY);
        this.queryString = queryString;
    }

    public static @Nonnull TwitchQueryId fromString(@Nonnull final String twitchIdString) {
        final var parts = twitchIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 2);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.QUERY);

        return new TwitchQueryId(parts[1]);
    }

    @Nonnull
    public String getQueryString() {
        return queryString;
    }

    @Override
    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), queryString);
    }
}
