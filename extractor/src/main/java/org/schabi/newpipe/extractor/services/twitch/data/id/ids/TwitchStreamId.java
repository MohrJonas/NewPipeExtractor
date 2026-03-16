package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;

public final class TwitchStreamId extends TwitchId {

    @Nonnull
    private final String streamId;

    public TwitchStreamId(@Nonnull final String streamId) {
        super(TwitchIdType.STREAM);
        this.streamId = streamId;
    }

    public static @Nonnull TwitchStreamId fromString(@Nonnull final String twitchIdString) {
        final var parts = twitchIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 2);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.STREAM);

        return new TwitchStreamId(parts[1]);
    }

    @Nonnull
    public String getStreamId() {
        return streamId;
    }

    @Override
    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), streamId);
    }
}
