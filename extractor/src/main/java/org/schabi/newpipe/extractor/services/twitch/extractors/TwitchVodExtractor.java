package org.schabi.newpipe.extractor.services.twitch.extractors;

import com.grack.nanojson.JsonParserException;

import org.schabi.newpipe.extractor.Image;
import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.linkhandler.LinkHandler;
import org.schabi.newpipe.extractor.services.twitch.api.TwitchApi;
import org.schabi.newpipe.extractor.services.twitch.data.TwitchVideoStream;
import org.schabi.newpipe.extractor.stream.AudioStream;
import org.schabi.newpipe.extractor.stream.DeliveryMethod;
import org.schabi.newpipe.extractor.stream.StreamExtractor;
import org.schabi.newpipe.extractor.stream.StreamType;
import org.schabi.newpipe.extractor.stream.VideoStream;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

public class TwitchVodExtractor extends StreamExtractor {

    private TwitchVideoStream[] streams;

    public TwitchVodExtractor(StreamingService service, LinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {
        try {
            final var token = TwitchApi.getVodPlaybackToken(downloader, getId());
            final var playSessionId = UUID.randomUUID().toString().replace("-", "").substring(0, 32);
            streams = TwitchApi.getM3U8VodPlaybackUrl(downloader, getId(), token.getData().getSignature(), token.getData().getValue(), playSessionId);
        } catch (JsonParserException e) {
            throw new IOException(e);
        }
    }

    @Nonnull
    @Override
    public String getName() throws ParsingException {
        return "???";
    }

    @Nonnull
    @Override
    public List<Image> getThumbnails() throws ParsingException {
        return List.of();
    }

    @Nonnull
    @Override
    public String getUploaderUrl() throws ParsingException {
        return "???";
    }

    @Nonnull
    @Override
    public String getUploaderName() throws ParsingException {
        return "???";
    }

    @Override
    public List<AudioStream> getAudioStreams() throws IOException, ExtractionException {
        return Collections.emptyList();
    }

    @Override
    public List<VideoStream> getVideoStreams() throws IOException, ExtractionException {
        return Arrays.stream(streams).map(res ->
                        new VideoStream.Builder()
                                .setId(VideoStream.ID_UNKNOWN)
                                .setContent(res.getStreamUrl(), true)
                                .setDeliveryMethod(DeliveryMethod.HLS)
                                .setResolution(res.getResolution())
                                .setIsVideoOnly(false)
                                .setMediaFormat(MediaFormat.MPEG_4)
                                .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<VideoStream> getVideoOnlyStreams() throws IOException, ExtractionException {
        return Collections.emptyList();
    }

    @Override
    public StreamType getStreamType() throws ParsingException {
        return StreamType.POST_LIVE_STREAM;
    }
}
