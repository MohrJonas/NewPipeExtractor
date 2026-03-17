package org.schabi.newpipe.extractor.services.twitch.graphql;

public final class TwitchGQLTemplates {
    private static final String STREAM_PLAYBACK_ACCESS_TOKEN_TEMPLATE = "{\"query\": \"{\\n" +
            "            streamPlaybackAccessToken(\\n" +
            "                channelName: \\\"%s\\\",\\n" +
            "                params: {\\n" +
            "                    platform: \\\"web\\\",\\n" +
            "                    playerBackend: \\\"mediaplayer\\\",\\n" +
            "                    playerType: \\\"site\\\"\\n" +
            "                }\\n" +
            "            )\\n" +
            "            {\\n" +
            "                value\\n" +
            "                signature\\n" +
            "            }\\n" +
            "        }\\n" +
            "\"}";

    private static final String STREAMER_TEMPLATE = "{\"query\": \"{\\n" +
            "  user(login: \\\"%s\\\") {\\n" +
            "    displayName\\n" +
            "    stream {\\n" +
            "      title\\n" +
            "      viewersCount\\n" +
            "    }\\n" +
            "  }\\n" +
            "}\"}";

    private static final String SEARCH_TEMPLATE = "[\n" +
            "    {\n" +
            "        \"operationName\": \"SearchResultsPage_SearchResults\",\n" +
            "        \"variables\": {\n" +
            "            \"requestID\": \"%s\",\n" +
            "            \"query\": \"%s\",\n" +
            "            \"platform\": \"web\",\n" +
            "            \"options\": { \"targets\": null, \"shouldSkipDiscoveryControl\": false }\n" +
            "        },\n" +
            "        \"extensions\": {\n" +
            "            \"persistedQuery\": {\n" +
            "                \"version\": 1,\n" +
            "                \"sha256Hash\": \"a7c600111acc4d1b294eafa364600556227939e2ff88505faa73035b57a83b22\"\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "]";

    private static final String NOW_LIVE_TEMPLATE = "{\"query\":\"{\\n" +
            "    streams(first: 25) {\\n" +
            "        edges {\\n" +
            "        node {\\n" +
            "            id\\n" +
            "            title\\n" +
            "            viewersCount\\n" +
            "            broadcaster {\\n" +
            "                displayName\\n" +
            "            }\\n" +
            "            game {\\n" +
            "                name\\n" +
            "            }\\n" +
            "        }\\n" +
            "        }\\n" +
            "    }\\n" +
            "}\"}";

    private static final String CHANNEL_TEMPLATE = "[\n" +
            "    {\n" +
            "        \"operationName\": \"HomeOfflineCarousel\",\n" +
            "        \"variables\": {\n" +
            "            \"channelLogin\": \"%s\",\n" +
            "            \"includeTrailerUpsell\": false,\n" +
            "            \"trailerUpsellVideoID\": \"601752619\"\n" +
            "        },\n" +
            "        \"extensions\": {\n" +
            "            \"persistedQuery\": {\n" +
            "                \"version\": 1,\n" +
            "                \"sha256Hash\": \"0409584bcabf718836bf330c29d0ac9d9a58f9674f7684bcbfce1a3e8dcf93b2\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationName\": \"ChannelAvatar\",\n" +
            "        \"variables\": {\n" +
            "            \"channelLogin\": \"%s\"\n" +
            "        },\n" +
            "        \"extensions\": {\n" +
            "            \"persistedQuery\": {\n" +
            "                \"version\": 1,\n" +
            "                \"sha256Hash\": \"db0e7b54c5e75fcf7874cafca2dacde646344cbbd1a80a2488a7953176c87a68\"\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    {\n" +
            "        \"operationName\": \"ChannelShell\",\n" +
            "        \"variables\": {\n" +
            "            \"login\": \"%s\"\n" +
            "        },\n" +
            "        \"extensions\": {\n" +
            "            \"persistedQuery\": {\n" +
            "                \"version\": 1,\n" +
            "                \"sha256Hash\": \"fea4573a7bf2644f5b3f2cbbdcbee0d17312e48d2e55f080589d053aad353f11\"\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "]";

    private static final String VOD_TEMPLATE = "[\n" +
            "    {\n" +
            "        \"operationName\": \"FilterableVideoTower_Videos\",\n" +
            "        \"variables\": {\n" +
            "            \"includePreviewBlur\": false,\n" +
            "            \"limit\": 30,\n" +
            "            \"channelOwnerLogin\": \"%s\",\n" +
            "            \"broadcastType\": null,\n" +
            "            \"videoSort\": \"TIME\"\n" +
            "        },\n" +
            "        \"extensions\": {\n" +
            "            \"persistedQuery\": {\n" +
            "                \"version\": 1,\n" +
            "                \"sha256Hash\": \"67004f7881e65c297936f32c75246470629557a393788fb5a69d6d9a25a8fd5f\"\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "]";

    private static final String CLIP_TEMPLATE = "[\n" +
            "    {\n" +
            "        \"operationName\": \"ClipsCards__User\",\n" +
            "        \"variables\": {\n" +
            "            \"login\": \"%s\",\n" +
            "            \"limit\": 20,\n" +
            "            \"criteria\": {\n" +
            "                \"filter\": \"ALL_TIME\",\n" +
            "                \"shouldFilterByDiscoverySetting\": true\n" +
            "            },\n" +
            "            \"cursor\": null\n" +
            "        },\n" +
            "        \"extensions\": {\n" +
            "            \"persistedQuery\": {\n" +
            "                \"version\": 1,\n" +
            "                \"sha256Hash\": \"1cd671bfa12cec480499c087319f26d21925e9695d1f80225aae6a4354f23088\"\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "]";

    private static final String CLIP_PLAYBACK_ACCESS_TOKEN = "[\n" +
            "    {\n" +
            "        \"operationName\": \"VideoAccessToken_Clip\",\n" +
            "        \"variables\": {\n" +
            "            \"platform\": \"web\",\n" +
            "            \"slug\": \"%s\"\n" +
            "        },\n" +
            "        \"extensions\": {\n" +
            "            \"persistedQuery\": {\n" +
            "                \"version\": 1,\n" +
            "                \"sha256Hash\": \"4f35f1ac933d76b1da008c806cd5546a7534dfaff83e033a422a81f24e5991b3\"\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "]";

    private static final String VOD_PLAYBACK_ACCESS_TOKEN = "{\n" +
            "    \"operationName\": \"PlaybackAccessToken\",\n" +
            "    \"variables\": {\n" +
            "        \"isLive\": false,\n" +
            "        \"login\": \"\",\n" +
            "        \"isVod\": true,\n" +
            "        \"vodID\": \"%s\",\n" +
            "        \"playerType\": \"site\",\n" +
            "        \"platform\": \"web\"\n" +
            "    },\n" +
            "    \"extensions\": {\n" +
            "        \"persistedQuery\": {\n" +
            "            \"version\": 1,\n" +
            "            \"sha256Hash\": \"ed230aa1e33e07eebb8928504583da78a5173989fadfb1ac94be06a04f3cdbe9\"\n" +
            "        }\n" +
            "    }\n" +
            "}";

    public static String getPlaybackAccessTokenTemplate(final String channelName) {
        return String.format(STREAM_PLAYBACK_ACCESS_TOKEN_TEMPLATE, channelName);
    }

    public static String getStream(final String channelName) {
        return String.format(STREAMER_TEMPLATE, channelName);
    }

    public static String getSearchResult(final String requestId, final String query) {
        return String.format(SEARCH_TEMPLATE, requestId, query);
    }

    public static String getNowLive() {
        return NOW_LIVE_TEMPLATE;
    }

    public static String getChannel(final String channelName) {
        return String.format(CHANNEL_TEMPLATE, channelName, channelName, channelName);
    }

    public static String getVods(final String channelName) {
        return String.format(VOD_TEMPLATE, channelName);
    }

    public static String getClips(final String channelName) {
        return String.format(CLIP_TEMPLATE, channelName);
    }

    public static String getClipPlayback(final String clipSlug) {
        return String.format(CLIP_PLAYBACK_ACCESS_TOKEN, clipSlug);
    }

    public static String getVodPlaybackTokenTemplate(final String vodId) {
        return String.format(VOD_PLAYBACK_ACCESS_TOKEN, vodId);
    }
}
