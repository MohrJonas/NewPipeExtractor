package org.schabi.newpipe.extractor.services.twitch.data.api.responses.stream;

import com.grack.nanojson.JsonObject;

import org.schabi.newpipe.extractor.services.twitch.data.api.TwitchExtensionsData;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.TwitchBaseResponse;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class TwitchStreamResponse extends TwitchBaseResponse<TwitchStreamResponseInner> {

    public TwitchStreamResponse(@Nullable String[] errors, @Nonnull TwitchExtensionsData extensions, @Nonnull JsonObject data) {
        super(errors, extensions, data);
    }

    @Override
    protected TwitchStreamResponseInner ParseData(final JsonObject data) {
        final var user = data.getObject("user");
        final var streamerName = user.getString("displayName");
        final var stream = user.getObject("stream");
        final var streamTitle = stream.getString("title");
        return new TwitchStreamResponseInner(streamerName, streamTitle);
    }
}
