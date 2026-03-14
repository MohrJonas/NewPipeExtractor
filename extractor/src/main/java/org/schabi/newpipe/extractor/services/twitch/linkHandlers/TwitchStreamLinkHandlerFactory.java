package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchId;
import org.schabi.newpipe.extractor.services.twitch.data.id.TwitchIdType;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchClipId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchStreamId;
import org.schabi.newpipe.extractor.services.twitch.data.id.ids.TwitchVodId;

import java.util.Set;

public class TwitchStreamLinkHandlerFactory extends TwitchBaseLinkHandlerFactory {

    private static final Set<TwitchIdType> ACCEPTED_IDS = Set.of(
            TwitchIdType.CLIP,
            TwitchIdType.STREAM,
            TwitchIdType.VOD
    );

    private TwitchIdType idType;

    @Override
    public String getId(final String urlString) throws ParsingException, UnsupportedOperationException {
        switch (idType) {
            case CLIP:
                return TwitchClipId.fromString(urlString).getClipId();
            case STREAM:
                return TwitchStreamId.fromString(urlString).getStreamId();
            case VOD:
                return TwitchVodId.fromString(urlString).getVodId();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public String getUrl(final String id) throws ParsingException, UnsupportedOperationException {
        switch (idType) {
            case CLIP:
                return new TwitchClipId(id).toString();
            case STREAM:
                return new TwitchStreamId(id).toString();
            case VOD:
                return new TwitchVodId(id).toString();
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean onAcceptUrl(final String urlString) throws ParsingException {
        try {
            final var type = TwitchId.getIdTypeFromString(urlString);
            if (ACCEPTED_IDS.contains(type)) {
                idType = type;
                return true;
            }
            return false;
        } catch (Throwable e) {
            return false;
        }
    }
}
