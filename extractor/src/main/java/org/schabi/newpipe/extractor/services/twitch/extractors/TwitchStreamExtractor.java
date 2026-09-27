package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.services.twitch.TwitchUrlBuilder;
import org.schabi.newpipe.extractor.services.twitch.TwitchUrlParser;
import org.schabi.newpipe.extractor.services.twitch.api.ThumbnailURLGenerator;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.Resolution;
import org.schabi.newpipe.extractor.services.twitch.data.TwitchVideoStream;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.channel.TwitchChannelResponseInner;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.stream.TwitchStreamResponseInner;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class TwitchStreamExtractor extends StreamExtractor {

    private TwitchStreamResponseInner streamResponse;
    private TwitchVideoStream[] streams;
    private TwitchChannelResponseInner channelResponse;

    public TwitchStreamExtractor(final StreamingService service,
                                 final LinkHandler linkHandler) {
        super(service, linkHandler);
    }

    private static int getResolutionPixelCount(Resolution resolution) {
        return resolution.width() * resolution.height();
    }

    @Nonnull
    private static TwitchVideoStream GetStreamWithHighestResolution(TwitchVideoStream[] streams) {
        return Arrays.stream(streams)
                .max(Comparator.comparingInt(twitchVideoStream ->
                        getResolutionPixelCount(twitchVideoStream.resolution()))).get();
    }

    @Nonnull
    @Override
    public List<Image> getThumbnails() throws ParsingException {
        return List.of(
                new Image(ThumbnailURLGenerator.getThumbnailURLForStream(streamResponse.streamerName()), Image.HEIGHT_UNKNOWN, Image.WIDTH_UNKNOWN, Image.ResolutionLevel.UNKNOWN)
        );
    }

    @Nonnull
    @Override
    public List<Image> getUploaderAvatars() throws ParsingException {
        return List.of(
            new Image(
                channelResponse.streamerAvatarUrl(),
                Image.HEIGHT_UNKNOWN,
                Image.WIDTH_UNKNOWN,
                Image.ResolutionLevel.UNKNOWN
            ));
    }

    @Nonnull
    @Override
    public String getHlsUrl() {
        return GetStreamWithHighestResolution(streams).streamUrl();
    }

    @Nonnull
    @Override
    public String getUploaderUrl() throws ParsingException {
        return TwitchUrlBuilder.buildChannelUrlFromChannelName(getUploaderName());
    }

    @Nonnull
    @Override
    public String getUploaderName() throws ParsingException {
        return streamResponse.streamerName();
    }

    @Override
    public List<AudioStream> getAudioStreams() throws IOException, ExtractionException {
        return Collections.emptyList();
    }

    @Override
    public List<VideoStream> getVideoStreams() throws IOException, ExtractionException {
        return Arrays.stream(streams).map(str ->
                        new VideoStream.Builder()
                                .setMediaFormat(MediaFormat.MPEG_4)
                                .setId(VideoStream.ID_UNKNOWN)
                                .setContent(str.streamUrl(), true)
                                .setDeliveryMethod(DeliveryMethod.HLS)
                                .setResolution(str.resolution().asResolutionString())
                                .setIsVideoOnly(false)
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoStream> getVideoOnlyStreams() throws IOException, ExtractionException {
        return List.of();
    }

    @Override
    public StreamType getStreamType() throws ParsingException {
        return StreamType.LIVE_STREAM;
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            final var streamId = TwitchUrlParser.parseChannelNameFromStreamUrl(getUrl());
            final var streamInfo = TwitchApi.getStreamInformation(downloader, streamId);
            streamResponse = streamInfo.getData();
            channelResponse = TwitchApi.getTwitchChannel(downloader, streamResponse.streamerLoginName()).getData();
            final var playbackToken = TwitchApi.getPlaybackToken(downloader, streamId);
            streams = TwitchApi.getM3U8PlaybackUrl(downloader, streamId, playbackToken.getData().signature(), playbackToken.getData().value());
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return streamResponse.streamTitle();
    }
}
