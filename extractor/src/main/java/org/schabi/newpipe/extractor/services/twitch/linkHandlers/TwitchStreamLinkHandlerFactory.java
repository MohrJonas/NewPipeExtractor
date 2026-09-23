package org.schabi.newpipe.extractor.services.twitch.linkHandlers;

import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.twitch.TwitchUrlBuilder;
import org.schabi.newpipe.extractor.services.twitch.TwitchUrlParser;
import org.schabi.newpipe.extractor.services.twitch.data.TwitchStreamLinkType;

public class TwitchStreamLinkHandlerFactory extends TwitchBaseLinkHandlerFactory {

    private TwitchStreamLinkType type;

    @Override
    public String getId(final String urlString) throws ParsingException, UnsupportedOperationException {
        switch (type)
        {
            case CLIP:
                return TwitchUrlParser.parseClipIdFromClipUrl(urlString);
            case STREAM:
                return TwitchUrlParser.parseChannelNameFromStreamUrl(urlString);
            case VOD:
                return TwitchUrlParser.parseVodIdFromVodUrl(urlString);
        }
        throw new UnsupportedOperationException("Unsupported .getId call for stream type " + type);
    }

    @Override
    public String getUrl(final String id) throws ParsingException, UnsupportedOperationException {
        switch (type)
        {
            case CLIP:
                return TwitchUrlBuilder.buildClipUrlFromClipId(id);
            case STREAM:
                return TwitchUrlBuilder.buildStreamUrlFromChannelName(id);
            case VOD:
                return TwitchUrlBuilder.buildVodUrlFromVodId(id);
        }
        throw new UnsupportedOperationException("Unsupported .getId call for stream type " + type);
    }

    @Override
    public boolean onAcceptUrl(final String urlString) throws ParsingException {
        try
        {
            TwitchUrlParser.parseClipIdFromClipUrl(urlString);
            type = TwitchStreamLinkType.CLIP;
            return true;
        }
        catch (Exception ignored) {}
        try {
            TwitchUrlParser.parseVodIdFromVodUrl(urlString);
            type = TwitchStreamLinkType.VOD;
            return true;
        }
        catch (Exception ignored) {}
        try {
            TwitchUrlParser.parseChannelNameFromStreamUrl(urlString);
            type = TwitchStreamLinkType.STREAM;
            return true;
        }
        catch (Exception ignored) {}
        return false;
    }
}
