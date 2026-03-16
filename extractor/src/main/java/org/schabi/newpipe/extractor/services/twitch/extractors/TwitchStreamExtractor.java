package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.TwitchVideoStream;
import org.schabi.newpipe.extractor.services.twitch.data.api.responses.stream.TwitchStreamResponseInner;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchStreamId;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class TwitchStreamExtractor extends StreamExtractor {

    private TwitchStreamResponseInner response;
    private TwitchVideoStream[] streams;

    public TwitchStreamExtractor(StreamingService service, LinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public List<Image> getThumbnails() throws ParsingException {
        return Collections.emptyList();
    }

    @Nonnull
    @Override
    public String getUploaderUrl() throws ParsingException {
        return TwitchStreamId.fromString(getUrl()).toString();
    }

    @Nonnull
    @Override
    public String getUploaderName() throws ParsingException {
        return response.getStreamerName();
    }

    @Override
    public List<AudioStream> getAudioStreams() throws IOException, ExtractionException {
        return Collections.emptyList();
    }

    @Override
    public List<VideoStream> getVideoStreams() throws IOException, ExtractionException {
        return Arrays.stream(streams).map(str ->
                        new VideoStream.Builder()
                                .setId(VideoStream.ID_UNKNOWN)
                                .setContent(str.getStreamUrl(), true)
                                .setDeliveryMethod(DeliveryMethod.HLS)
                                .setResolution(str.getResolution())
                                .setIsVideoOnly(false)
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoStream> getVideoOnlyStreams() throws IOException, ExtractionException {
        return null;
    }

    @Override
    public StreamType getStreamType() throws ParsingException {
        return StreamType.LIVE_STREAM;
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            final var streamId = TwitchStreamId.fromString(getUrl());
            final var streamInfo = TwitchApi.getStreamInformation(downloader, streamId.getStreamId());
            response = streamInfo.getData();
            final var playbackToken = TwitchApi.getPlaybackToken(downloader, streamId.getStreamId());
            streams = TwitchApi.getM3U8PlaybackUrl(downloader, streamId.getStreamId(), playbackToken.getData().getSignature(), playbackToken.getData().getValue());
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return response.getStreamTitle();
    }
}
