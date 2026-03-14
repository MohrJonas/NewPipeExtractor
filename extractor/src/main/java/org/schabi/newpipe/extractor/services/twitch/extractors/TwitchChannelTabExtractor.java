package org.schabi.newpipe.extractor.services.twitch.extractors;

import org.schabi.newpipe.extractor.InfoItem;
import org.schabi.newpipe.extractor.Page;
import org.schabi.newpipe.extractor.StreamingService;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.downloader.Downloader;
import org.schabi.newpipe.extractor.exceptions.ExtractionException;
import org.schabi.newpipe.extractor.linkhandler.ListLinkHandler;

import java.io.IOException;

import javax.annotation.Nonnull;

public final class TwitchChannelTabExtractor extends ChannelTabExtractor {

    public TwitchChannelTabExtractor(@Nonnull StreamingService service, @Nonnull ListLinkHandler linkHandler) {
        super(service, linkHandler);
    }

    @Nonnull
    @Override
    public InfoItemsPage<InfoItem> getInitialPage() throws IOException, ExtractionException {
        return null;
    }

    @Override
    public InfoItemsPage<InfoItem> getPage(Page page) throws IOException, ExtractionException {
        return null;
    }

    @Override
    public void onFetchPage(@Nonnull Downloader downloader) throws IOException, ExtractionException {

    }
}
