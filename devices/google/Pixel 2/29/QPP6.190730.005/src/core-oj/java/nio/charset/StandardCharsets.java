/*
 * Decompiled with CFR 0.145.
 */
package java.nio.charset;

import java.nio.charset.Charset;

public final class StandardCharsets {
    public static final Charset ISO_8859_1;
    public static final Charset US_ASCII;
    public static final Charset UTF_16;
    public static final Charset UTF_16BE;
    public static final Charset UTF_16LE;
    public static final Charset UTF_8;

    static {
        US_ASCII = Charset.forName("US-ASCII");
        ISO_8859_1 = Charset.forName("ISO-8859-1");
        UTF_8 = Charset.forName("UTF-8");
        UTF_16BE = Charset.forName("UTF-16BE");
        UTF_16LE = Charset.forName("UTF-16LE");
        UTF_16 = Charset.forName("UTF-16");
    }

    private StandardCharsets() {
        throw new AssertionError((Object)"No java.nio.charset.StandardCharsets instances for you!");
    }
}

