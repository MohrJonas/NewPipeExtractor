package org.schabi.newpipe.extractor.services.twitch;

import org.schabi.newpipe.extractor.StreamingService;

import java.util.Set;

public class TwitchServiceInfo extends StreamingService.ServiceInfo {

    public TwitchServiceInfo() {
        super("Twitch", Set.of(MediaCapability.LIVE));
    }
}
