/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixPath;

class UnixUriUtils {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long H_ALPHA;
    private static final long H_ALPHANUM;
    private static final long H_DIGIT = 0L;
    private static final long H_LOWALPHA;
    private static final long H_MARK;
    private static final long H_PATH;
    private static final long H_PCHAR;
    private static final long H_UNRESERVED;
    private static final long H_UPALPHA;
    private static final long L_ALPHA = 0L;
    private static final long L_ALPHANUM;
    private static final long L_DIGIT;
    private static final long L_LOWALPHA = 0L;
    private static final long L_MARK;
    private static final long L_PATH;
    private static final long L_PCHAR;
    private static final long L_UNRESERVED;
    private static final long L_UPALPHA = 0L;
    private static final char[] hexDigits;

    static {
        L_DIGIT = UnixUriUtils.lowMask('0', '9');
        H_UPALPHA = UnixUriUtils.highMask('A', 'Z');
        H_LOWALPHA = UnixUriUtils.highMask('a', 'z');
        H_ALPHA = H_LOWALPHA | H_UPALPHA;
        L_ALPHANUM = L_DIGIT | 0L;
        H_ALPHANUM = H_ALPHA | 0L;
        L_MARK = UnixUriUtils.lowMask("-_.!~*'()");
        H_MARK = UnixUriUtils.highMask("-_.!~*'()");
        L_UNRESERVED = L_ALPHANUM | L_MARK;
        H_UNRESERVED = H_ALPHANUM | H_MARK;
        L_PCHAR = L_UNRESERVED | UnixUriUtils.lowMask(":@&=+$,");
        H_PCHAR = H_UNRESERVED | UnixUriUtils.highMask(":@&=+$,");
        L_PATH = L_PCHAR | UnixUriUtils.lowMask(";/");
        H_PATH = H_PCHAR | UnixUriUtils.highMask(";/");
        hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    private UnixUriUtils() {
    }

    private static int decode(char c) {
        if (c >= '0' && c <= '9') {
            return c - 48;
        }
        if (c >= 'a' && c <= 'f') {
            return c - 97 + 10;
        }
        if (c >= 'A' && c <= 'F') {
            return c - 65 + 10;
        }
        throw new AssertionError();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static Path fromUri(UnixFileSystem unixFileSystem, URI object) {
        if (!((URI)object).isAbsolute()) throw new IllegalArgumentException("URI is not absolute");
        if (((URI)object).isOpaque()) throw new IllegalArgumentException("URI is not hierarchical");
        byte[] arrby = ((URI)object).getScheme();
        if (arrby == null || !arrby.equalsIgnoreCase("file")) throw new IllegalArgumentException("URI scheme is not \"file\"");
        if (((URI)object).getAuthority() != null) throw new IllegalArgumentException("URI has an authority component");
        if (((URI)object).getFragment() != null) throw new IllegalArgumentException("URI has a fragment component");
        if (((URI)object).getQuery() != null) throw new IllegalArgumentException("URI has a query component");
        if (!((URI)object).toString().startsWith("file:///")) {
            return new File((URI)object).toPath();
        }
        int n = ((String)(object = ((URI)object).getRawPath())).length();
        if (n == 0) throw new IllegalArgumentException("URI path component is empty");
        int n2 = n;
        if (((String)object).endsWith("/")) {
            n2 = n;
            if (n > 1) {
                n2 = n - 1;
            }
        }
        arrby = new byte[n2];
        int n3 = 0;
        n = 0;
        while (n < n2) {
            byte by;
            int n4 = n + 1;
            if ((n = (int)((String)object).charAt(n)) == 37) {
                n = n4 + 1;
                char c = ((String)object).charAt(n4);
                char c2 = ((String)object).charAt(n);
                by = (byte)(UnixUriUtils.decode(c) << 4 | UnixUriUtils.decode(c2));
                if (by == 0) throw new IllegalArgumentException("Nul character not allowed");
                ++n;
            } else {
                by = (byte)n;
                n = n4;
            }
            arrby[n3] = by;
            ++n3;
        }
        object = arrby;
        if (n3 == arrby.length) return new UnixPath(unixFileSystem, (byte[])object);
        object = Arrays.copyOf(arrby, n3);
        return new UnixPath(unixFileSystem, (byte[])object);
    }

    private static long highMask(char c, char c2) {
        long l = 0L;
        c = (char)Math.max(Math.min(c, 127), 64);
        c2 = (char)Math.max(Math.min(c2, 127), 64);
        for (c = (char)(c - 64); c <= c2 - 64; c = (char)(c + 1)) {
            l |= 1L << c;
        }
        return l;
    }

    private static long highMask(String string) {
        int n = string.length();
        long l = 0L;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            long l2 = l;
            if (c >= '@') {
                l2 = l;
                if (c < '') {
                    l2 = l | 1L << c - 64;
                }
            }
            l = l2;
        }
        return l;
    }

    private static long lowMask(char c, char c2) {
        long l = 0L;
        c2 = (char)Math.max(Math.min(c2, 63), 0);
        for (c = (char)Math.max((int)Math.min((int)c, (int)63), (int)0); c <= c2; c = (char)(c + 1)) {
            l |= 1L << c;
        }
        return l;
    }

    private static long lowMask(String string) {
        int n = string.length();
        long l = 0L;
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            long l2 = l;
            if (c < '@') {
                l2 = l | 1L << c;
            }
            l = l2;
        }
        return l;
    }

    private static boolean match(char c, long l, long l2) {
        boolean bl = true;
        boolean bl2 = true;
        if (c < '@') {
            if ((1L << c & l) == 0L) {
                bl2 = false;
            }
            return bl2;
        }
        if (c < '') {
            bl2 = (1L << c - 64 & l2) != 0L ? bl : false;
            return bl2;
        }
        return false;
    }

    static URI toUri(UnixPath comparable) {
        byte[] arrby = ((UnixPath)comparable.toAbsolutePath()).asByteArray();
        StringBuilder stringBuilder = new StringBuilder("file:///");
        for (int i = 1; i < arrby.length; ++i) {
            char c = (char)(arrby[i] & 255);
            if (UnixUriUtils.match(c, L_PATH, H_PATH)) {
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append('%');
            stringBuilder.append(hexDigits[c >> 4 & 15]);
            stringBuilder.append(hexDigits[c & 15]);
        }
        if (stringBuilder.charAt(stringBuilder.length() - 1) != '/') {
            try {
                if (UnixFileAttributes.get(comparable, true).isDirectory()) {
                    stringBuilder.append('/');
                }
            }
            catch (UnixException unixException) {
                // empty catch block
            }
        }
        try {
            comparable = new URI(stringBuilder.toString());
            return comparable;
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new AssertionError(uRISyntaxException);
        }
    }
}

