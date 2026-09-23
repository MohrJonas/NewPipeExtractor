package org.schabi.newpipe.extractor.services.twitch;

import java.net.MalformedURLException;
import java.net.URL;

import javax.annotation.Nonnull;

public final class TwitchUrlBuilder {
    public static @Nonnull String buildSearchUrlFromSearchQuery(@Nonnull final String query)
    {
        try {
            return new URL( TwitchService.BaseUrl + "/search?term=" + query).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static @Nonnull String buildChannelUrlFromChannelName(@Nonnull final String name)
    {
        try {
            return new URL(TwitchService.BaseUrl + "/" + name + "?_type=channel").toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static @Nonnull String buildStreamUrlFromChannelName(@Nonnull final String name)
    {
        try {
            return new URL(TwitchService.BaseUrl + "/" + name + "?_type=stream").toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static @Nonnull String buildCategoryUrlFromCategoryName(@Nonnull final String name)
    {
        try {
            return new URL(TwitchService.BaseUrl + "/directory/category/" + name).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static @Nonnull String buildClipUrlFromClipId(@Nonnull final String clipId) {
        try {
            return new URL(TwitchService.BaseUrl + "/clip/" + clipId).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static @Nonnull String buildVodUrlFromVodId(@Nonnull final String vodId) {
        try {
            return new URL(TwitchService.BaseUrl + "/videos/" + vodId).toString();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
