package cn.stark.utils;

import java.util.UUID;

/**
 * Created by nimitz on 2016/11/19.
 */
public class StringUtils {

    private StringUtils() {
        super();
    }

    /**
     *
     * @param src
     * @return
     */
    public static String formatNull(String src) {
        return (src == null || "null".equals(src)) ? "" : src;
    }

    private static final String EMPTY_REGEX = "[\\s\\u00a0\\u2007\\u202f\\u0009-\\u000d\\u001c-\\u001f]+";

    /**
     *
     * @param input
     * @return
     */
    public static boolean isEmpty(String input) {
        return input == null || input.equals("") || input.matches(EMPTY_REGEX);
    }

    /**
     *
     * @return
     */
    public static String getUuid() {
        return UUID.randomUUID().toString();
    }
}
