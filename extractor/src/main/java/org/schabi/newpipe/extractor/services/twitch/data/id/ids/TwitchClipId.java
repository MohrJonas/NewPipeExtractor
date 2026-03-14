package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class TwitchClipId extends TwitchId {

    @Nonnull
    private final String clipId;

    public TwitchClipId(@Nonnull final String clipId) {
        super(TwitchIdType.CLIP);
        this.clipId = clipId;
    }

    @Override
    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), clipId);
    }

    @Nonnull
    public String getClipId() {
        return clipId;
    }

    public static @Nonnull TwitchClipId fromString(@Nonnull final String twitchIdString) {
        final var parts = twitchIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 2);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.CLIP);

        return new TwitchClipId(parts[1]);
    }
}
