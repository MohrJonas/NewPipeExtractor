package org.schabi.newpipe.extractor.services.twitch.data.api.responses.playbackToken;

import com.grack.nanojson.JsonObject;

import org.schabi.newpipe.extractor.services.twitch.data.api.TwitchExtensionsData;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.TwitchBaseResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class TwitchPlaybackTokenResponse extends TwitchBaseResponse<TwitchPlaybackTokenResponseInner> {

    public TwitchPlaybackTokenResponse(@Nullable String[] errors, @Nonnull TwitchExtensionsData extensions, @Nonnull JsonObject data) {
        super(errors, extensions, data);
    }

    @Override
    protected TwitchPlaybackTokenResponseInner ParseData(JsonObject data) {
        final var streamPlaybackAccessToken = data.getObject("streamPlaybackAccessToken");
        return new TwitchPlaybackTokenResponseInner(
            streamPlaybackAccessToken.getString("signature"),
            streamPlaybackAccessToken.getString("value")
        );
    }
}
