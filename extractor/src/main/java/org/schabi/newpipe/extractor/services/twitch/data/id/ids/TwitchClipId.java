package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class TwitchClipId extends TwitchId {

    @Nonnull
    private final String clipId;

    @Nullable
    private final String streamerName;

    public TwitchClipId(@Nonnull final String clipId, @Nullable final String streamerName) {
        super(TwitchIdType.CLIP);
        this.clipId = clipId;
        this.streamerName = streamerName;
    }

    @Override
    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), streamerName, clipId);
    }

    @Nonnull
    public String getClipId() {
        return clipId;
    }

    @Nullable
    public String getStreamerName() {
        return streamerName;
    }

    public static @Nonnull TwitchClipId fromString(@Nonnull final String twitchIdString) {
        final var parts = twitchIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 3);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.CLIP);

        return new TwitchClipId(parts[1], parts[2]);
    }
}
