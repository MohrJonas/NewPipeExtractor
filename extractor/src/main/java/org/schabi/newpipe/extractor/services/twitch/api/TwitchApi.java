package org.schabi.newpipe.extractor.services.twitch.api;

import com.grack.nanojson.JsonArray;
import com.grack.nanojson.JsonObject;
import com.grack.nanojson.JsonParser;
import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ReCaptchaException;
import org.schabi.newpipe.extractor.services.twitch.StringUtils;
import org.schabi.newpipe.extractor.services.twitch.TwitchUtils;
import org.schabi.newpipe.extractor.services.twitch.data.TwitchVideoStream;
import org.schabi.newpipe.extractor.services.twitch.data.api.TwitchResponseParser;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.channel.TwitchChannelResponse;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.nowLive.TwitchNowLiveResponse;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.playbackToken.TwitchPlaybackTokenResponse;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.search.TwitchSearchResponse;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.stream.TwitchStreamResponse;
import org.schabi.newpipe.extractor.services.twitch.graphql.TwitchQGLTemplates;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

public final class TwitchApi {

    private static final String CLIENT_ID = "ue6666qo983tsx6so1t0vnawi233wa";
    private static final String TWITCH_QGL_URL = "https://gql.twitch.tv/gql";
    private static final String TWITCH_USHER_URL = "https://usher.ttvnw.net";

    private static final Map<String, List<String>> DEFAULT_HEADERS = Map.of("Client-ID", List.of(CLIENT_ID));

    public static TwitchSearchResponse getSearchResponse(final Downloader downloader, final String query) throws IOException, ReCaptchaException, JsonParserException {
        final var requestId = UUID.randomUUID().toString();
        final var rawRequest = TwitchQGLTemplates.getSearchResult(requestId, query);
        final var rawResponse = downloader.post(TWITCH_QGL_URL, DEFAULT_HEADERS, StringUtils.stringToBytes(TwitchQGLTemplates.getSearchResult(requestId, query)));
        if (!TwitchUtils.isSuccessfulResponseCode(rawResponse.responseCode()))
            throw new ResponseCodeIsNotSuccessException(rawResponse.responseCode());
        final var response = TwitchResponseParser.parseFromJson(JsonParser.array().from(rawResponse.responseBody()).getObject(0), TwitchSearchResponse.class);
        response.ensureSuccess();
        return response;
    }

    public static TwitchNowLiveResponse getNowLiveInformation(final Downloader downloader) throws IOException, ReCaptchaException, JsonParserException {
        final var rawResponse = downloader.post(TWITCH_QGL_URL, DEFAULT_HEADERS, StringUtils.stringToBytes(TwitchQGLTemplates.getNowLive()));
        if (!TwitchUtils.isSuccessfulResponseCode(rawResponse.responseCode()))
            throw new ResponseCodeIsNotSuccessException(rawResponse.responseCode());
        final var response = TwitchResponseParser.parseFromJson(JsonParser.object().from(rawResponse.responseBody()), TwitchNowLiveResponse.class);
        response.ensureSuccess();
        return response;
    }

    public static TwitchStreamResponse getStreamInformation(final Downloader downloader, final String channelName) throws IOException, ReCaptchaException, JsonParserException {
        final var rawResponse = downloader.post(TWITCH_QGL_URL, DEFAULT_HEADERS, StringUtils.stringToBytes(TwitchQGLTemplates.getStream(channelName)));
        if (!TwitchUtils.isSuccessfulResponseCode(rawResponse.responseCode()))
            throw new ResponseCodeIsNotSuccessException(rawResponse.responseCode());
        final var response = TwitchResponseParser.parseFromJson(JsonParser.object().from(rawResponse.responseBody()), TwitchStreamResponse.class);
        response.ensureSuccess();
        return response;
    }

    public static TwitchPlaybackTokenResponse getPlaybackToken(final Downloader downloader, final String channelName) throws IOException, ReCaptchaException, JsonParserException {
        final var rawResponse = downloader.post(TWITCH_QGL_URL, DEFAULT_HEADERS, StringUtils.stringToBytes(TwitchQGLTemplates.getPlaybackAccessTokenTemplate(channelName)));
        if (!TwitchUtils.isSuccessfulResponseCode(rawResponse.responseCode()))
            throw new ResponseCodeIsNotSuccessException(rawResponse.responseCode());
        final var response = TwitchResponseParser.parseFromJson(JsonParser.object().from(rawResponse.responseBody()), TwitchPlaybackTokenResponse.class);
        response.ensureSuccess();
        return response;
    }

    public static TwitchVideoStream[] getM3U8PlaybackUrl(final Downloader downloader, final String channelName, final String playbackTokenSignature, final String playbackTokenValue) throws IOException, ReCaptchaException {
        final var masterM3U8Url = TWITCH_USHER_URL + "/api/channel/hls/" + channelName + ".m3u8?allow_source=true&allow_audio_only=false&allow_spectre=true&p=1003155&platform=web&player=twitchweb&supported_codecs=av1,h265,h264&playlist_include_framerate=false&sig=" + playbackTokenSignature + "&token=" + URLEncoder.encode(playbackTokenValue, Charset.defaultCharset());
        final var response = downloader.get(masterM3U8Url);
        final var content = response.responseBody();
        final var lineQueue = new ArrayDeque<String>(List.of(content.split("\n")));
        final var commentBuffer = new ArrayList<String>();
        final var streams = new ArrayList<TwitchVideoStream>();
        while (!lineQueue.isEmpty()) {
            final var line = lineQueue.pop();
            if (line.startsWith("#")) commentBuffer.add(line);
            else {
                final var resolutionPattern = Pattern.compile("RESOLUTION=(\\d+x\\d+)");
                String resolution = null;
                for (final var bufferedLine : commentBuffer) {
                    final var matcher = resolutionPattern.matcher(bufferedLine);
                    if (matcher.find()) resolution = matcher.group(1);
                }
                if (resolution != null) streams.add(new TwitchVideoStream(resolution, line));
                commentBuffer.clear();
            }
        }
        return streams.toArray(TwitchVideoStream[]::new);
//        var usedStream = streamArray[0];
//        final var adM3U8 = downloader.get(usedStream.getStreamUrl()).responseBody();
//        final var pattern = Pattern.compile("X-TV-TWITCH-TRIGGER-URL=\"(.*)\"");
//        final var sessionIdPattern = Pattern.compile("X-TV-TWITCH-SESSIONID=\"(.*)\"");
//        final var matcher = pattern.matcher(adM3U8);
//        final var sessionMatcher = sessionIdPattern.matcher(adM3U8);
//        if (!matcher.find()) {
//            return new TwitchVideoStream[]{usedStream};
//        }
//        String sessionId = null;
//        if (sessionMatcher.find()) sessionId = sessionMatcher.group(1);
//        var tokenUrl = matcher.group(1);
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        var ret = downloader.get(tokenUrl, Map.of("X-TV-TWITCH-SESSIONID", List.of(sessionId)));
//        var neww = downloader.get(usedStream.getStreamUrl(), Map.of("X-TV-TWITCH-SESSIONID", List.of(sessionId))).responseBody();
//        return new TwitchVideoStream[]{usedStream};
//        var urls = Arrays.stream(lines)
//            .filter(line -> !line.startsWith("#"))
//                .collect(Collectors.toList());
//        return urls.get(urls.size() - 1);
    }

    public static TwitchChannelResponse getTwitchChannel(final Downloader downloader, final String channelName) throws IOException, ReCaptchaException, JsonParserException {
        final var rawResponse = downloader.post(TWITCH_QGL_URL, DEFAULT_HEADERS, StringUtils.stringToBytes(TwitchQGLTemplates.getChannel(channelName)));
        if (!TwitchUtils.isSuccessfulResponseCode(rawResponse.responseCode()))
            throw new ResponseCodeIsNotSuccessException(rawResponse.responseCode());
        final var response = TwitchResponseParser.parseFromJson(createJsonObjectFromArray(JsonParser.array().from(rawResponse.responseBody())), TwitchChannelResponse.class);
        response.ensureSuccess();
        return response;
    }

    private static JsonObject createJsonObjectFromArray(final JsonArray array) {
        var totalDuration = 0L;
        final var data = new JsonObject();
        final var errors = new LinkedList<>();
        for (var i = 0; i < array.size(); i++) {
            final var arrayElement = (JsonObject) array.get(i);
            data.put("synthetic-" + i, arrayElement.getObject("data"));
            totalDuration += arrayElement.getObject("extensions").getLong("durationMilliseconds");
            if(arrayElement.has("errors"))
                errors.addAll(arrayElement.getArray("errors"));
        }
        final var fullObject = new JsonObject();
        fullObject.put("data", data);
        final var fullExtensions = new JsonObject();
        fullExtensions.put("durationMilliseconds", totalDuration);
        fullObject.put("extensions", fullExtensions);
        if(!errors.isEmpty())
            fullObject.put("errors", errors);
        return fullObject;
    }
}
