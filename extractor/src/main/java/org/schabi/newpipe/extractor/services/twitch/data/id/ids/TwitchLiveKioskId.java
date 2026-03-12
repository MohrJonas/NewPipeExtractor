package org.schabi.newpipe.extractor.services.twitch.data.id.ids;

import org.schabi.newpipe.extractor.services.twitch.Assertions;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;

import javax.annotation.Nonnull;

public final class TwitchLiveKioskId extends TwitchId {

    @Nonnull
    private final String kioskType;

    public TwitchLiveKioskId(@Nonnull String kioskType) {
        super(TwitchIdType.KIOSK);
        this.kioskType = kioskType;
    }

    @Nonnull
    public String getKioskType() {
        return kioskType;
    }

    @Override
    public String toString() {
        return String.join(partSeparator, TwitchIdType.asString(getIdType()), kioskType);
    }

    public static @Nonnull TwitchLiveKioskId fromString(@Nonnull final String kioskIdString) {
        final var parts = kioskIdString.split(partSeparator);
        Assertions.assertThat(() -> parts.length == 2);

        final var idType = TwitchIdType.fromString(parts[0]);
        Assertions.assertThat(() -> idType == TwitchIdType.KIOSK);

        return new TwitchLiveKioskId(kioskIdString);
    }
}
