package org.schabi.newpipe.extractor.services.twitch;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class StringUtils {

    public static byte[] stringToBytes(final String string) {
        return string.getBytes(Charset.defaultCharset());
    }

    public static String[] splitStringRemovingEmpties(final String toSplit, final String separator) {
        return Arrays
            .stream(toSplit.split(separator))
            .filter(String::isEmpty)
            .toArray(String[]::new);
    }
}
