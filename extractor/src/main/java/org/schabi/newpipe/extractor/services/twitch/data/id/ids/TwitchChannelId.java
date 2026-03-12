package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;

public final class TwitchChannelId extends TwitchId {

    @Nonnull
    private final String channelName;

    public TwitchChannelId(@Nonnull String channelName) {
        super(TwitchIdType.CHANNEL);
        this.channelName = channelName;
    }

    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), channelName);
    }

    @Nonnull
    public String getChannelName() {
        return channelName;
    }

    public static @Nonnull TwitchChannelId fromString(@Nonnull final String twitchIdString) {
        final var parts = twitchIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 2);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.CHANNEL);

        return new TwitchChannelId(parts[1]);
    }
}
