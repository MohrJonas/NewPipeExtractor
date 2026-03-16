package org.schabi.newpipe.extractor.services.twitch.graphql;

import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonWriter;

public final class TwitchRequest {

    private final String query;

    public TwitchRequest(final String queryString) {
        query = queryString;
    }

    public String AsJsonString() {
        var object = new JsonObject();
        object.put("query", query);
        return JsonWriter.string(object);
    }

}
