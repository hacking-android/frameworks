/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.BitSet;
import sun.nio.cs.ThreadLocalCoders;

public class ParseUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long H_ALPHA;
    private static final long H_ALPHANUM;
    private static final long H_DASH;
    private static final long H_DIGIT = 0L;
    private static final long H_ESCAPED = 0L;
    private static final long H_HEX;
    private static final long H_LOWALPHA;
    private static final long H_MARK;
    private static final long H_PATH;
    private static final long H_PCHAR;
    private static final long H_REG_NAME;
    private static final long H_RESERVED;
    private static final long H_SERVER;
    private static final long H_UNRESERVED;
    private static final long H_UPALPHA;
    private static final long H_URIC;
    private static final long H_USERINFO;
    private static final long L_ALPHA = 0L;
    private static final long L_ALPHANUM;
    private static final long L_DASH;
    private static final long L_DIGIT;
    private static final long L_ESCAPED = 1L;
    private static final long L_HEX;
    private static final long L_LOWALPHA = 0L;
    private static final long L_MARK;
    private static final long L_PATH;
    private static final long L_PCHAR;
    private static final long L_REG_NAME;
    private static final long L_RESERVED;
    private static final long L_SERVER;
    private static final long L_UNRESERVED;
    private static final long L_UPALPHA = 0L;
    private static final long L_URIC;
    private static final long L_USERINFO;
    static BitSet encodedInPath;
    private static final char[] hexDigits;

    static {
        encodedInPath = new BitSet(256);
        encodedInPath.set(61);
        encodedInPath.set(59);
        encodedInPath.set(63);
        encodedInPath.set(47);
        encodedInPath.set(35);
        encodedInPath.set(32);
        encodedInPath.set(60);
        encodedInPath.set(62);
        encodedInPath.set(37);
        encodedInPath.set(34);
        encodedInPath.set(123);
        encodedInPath.set(125);
        encodedInPath.set(124);
        encodedInPath.set(92);
        encodedInPath.set(94);
        encodedInPath.set(91);
        encodedInPath.set(93);
        encodedInPath.set(96);
        for (int i = 0; i < 32; ++i) {
            encodedInPath.set(i);
        }
        encodedInPath.set(127);
        hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        L_HEX = L_DIGIT = ParseUtil.lowMask('0', '9');
        H_HEX = ParseUtil.highMask('A', 'F') | ParseUtil.highMask('a', 'f');
        H_UPALPHA = ParseUtil.highMask('A', 'Z');
        H_LOWALPHA = ParseUtil.highMask('a', 'z');
        H_ALPHA = H_LOWALPHA | H_UPALPHA;
        L_ALPHANUM = L_DIGIT | 0L;
        H_ALPHANUM = H_ALPHA | 0L;
        L_MARK = ParseUtil.lowMask("-_.!~*'()");
        H_MARK = ParseUtil.highMask("-_.!~*'()");
        L_UNRESERVED = L_ALPHANUM | L_MARK;
        H_UNRESERVED = H_ALPHANUM | H_MARK;
        L_RESERVED = ParseUtil.lowMask(";/?:@&=+$,[]");
        H_RESERVED = ParseUtil.highMask(";/?:@&=+$,[]");
        L_DASH = ParseUtil.lowMask("-");
        H_DASH = ParseUtil.highMask("-");
        long l = L_RESERVED;
        long l2 = L_UNRESERVED;
        L_URIC = l | l2 | 1L;
        H_URIC = H_RESERVED | H_UNRESERVED | 0L;
        L_PCHAR = l2 | 1L | ParseUtil.lowMask(":@&=+$,");
        H_PCHAR = H_UNRESERVED | 0L | ParseUtil.highMask(":@&=+$,");
        L_PATH = L_PCHAR | ParseUtil.lowMask(";/");
        H_PATH = H_PCHAR | ParseUtil.highMask(";/");
        L_USERINFO = L_UNRESERVED | 1L | ParseUtil.lowMask(";:&=+$,");
        H_USERINFO = H_UNRESERVED | 0L | ParseUtil.highMask(";:&=+$,");
        L_REG_NAME = L_UNRESERVED | 1L | ParseUtil.lowMask("$,;:@&=+");
        H_REG_NAME = H_UNRESERVED | 0L | ParseUtil.highMask("$,;:@&=+");
        L_SERVER = L_USERINFO | L_ALPHANUM | L_DASH | ParseUtil.lowMask(".:@[]");
        H_SERVER = H_USERINFO | H_ALPHANUM | H_DASH | ParseUtil.highMask(".:@[]");
    }

    private static void appendAuthority(StringBuffer stringBuffer, String string, String string2, String string3, int n) {
        block7 : {
            block8 : {
                block6 : {
                    boolean bl = false;
                    if (string3 == null) break block6;
                    stringBuffer.append("//");
                    if (string2 != null) {
                        stringBuffer.append(ParseUtil.quote(string2, L_USERINFO, H_USERINFO));
                        stringBuffer.append('@');
                    }
                    if (string3.indexOf(58) >= 0 && !string3.startsWith("[") && !string3.endsWith("]")) {
                        bl = true;
                    }
                    if (bl) {
                        stringBuffer.append('[');
                    }
                    stringBuffer.append(string3);
                    if (bl) {
                        stringBuffer.append(']');
                    }
                    if (n == -1) break block7;
                    stringBuffer.append(':');
                    stringBuffer.append(n);
                    break block7;
                }
                if (string == null) break block7;
                stringBuffer.append("//");
                if (!string.startsWith("[")) break block8;
                n = string.indexOf("]");
                if (n == -1 || string.indexOf(":") == -1) break block7;
                if (n == string.length()) {
                    string2 = "";
                } else {
                    string3 = string.substring(0, n + 1);
                    string2 = string.substring(n + 1);
                    string = string3;
                }
                stringBuffer.append(string);
                stringBuffer.append(ParseUtil.quote(string2, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
                break block7;
            }
            stringBuffer.append(ParseUtil.quote(string, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
        }
    }

    private static void appendEncoded(StringBuffer stringBuffer, char c) {
        Object object = null;
        try {
            CharsetEncoder charsetEncoder = ThreadLocalCoders.encoderFor("UTF-8");
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("");
            ((StringBuilder)object2).append(c);
            object = object2 = charsetEncoder.encode(CharBuffer.wrap(((StringBuilder)object2).toString()));
        }
        catch (CharacterCodingException characterCodingException) {
            // empty catch block
        }
        while (((Buffer)object).hasRemaining()) {
            int n = ((ByteBuffer)object).get() & 255;
            if (n >= 128) {
                ParseUtil.appendEscape(stringBuffer, (byte)n);
                continue;
            }
            stringBuffer.append((char)n);
        }
    }

    private static void appendEscape(StringBuffer stringBuffer, byte by) {
        stringBuffer.append('%');
        stringBuffer.append(hexDigits[by >> 4 & 15]);
        stringBuffer.append(hexDigits[by >> 0 & 15]);
    }

    private static void appendFragment(StringBuffer stringBuffer, String string) {
        if (string != null) {
            stringBuffer.append('#');
            stringBuffer.append(ParseUtil.quote(string, L_URIC, H_URIC));
        }
    }

    private static void appendSchemeSpecificPart(StringBuffer stringBuffer, String string, String string2, String string3, String string4, int n, String string5, String string6) {
        if (string != null) {
            if (string.startsWith("//[")) {
                n = string.indexOf("]");
                if (n != -1 && string.indexOf(":") != -1) {
                    if (n == string.length()) {
                        string2 = "";
                    } else {
                        string3 = string.substring(0, n + 1);
                        string2 = string.substring(n + 1);
                        string = string3;
                    }
                    stringBuffer.append(string);
                    stringBuffer.append(ParseUtil.quote(string2, L_URIC, H_URIC));
                }
            } else {
                stringBuffer.append(ParseUtil.quote(string, L_URIC, H_URIC));
            }
        } else {
            ParseUtil.appendAuthority(stringBuffer, string2, string3, string4, n);
            if (string5 != null) {
                stringBuffer.append(ParseUtil.quote(string5, L_PATH, H_PATH));
            }
            if (string6 != null) {
                stringBuffer.append('?');
                stringBuffer.append(ParseUtil.quote(string6, L_URIC, H_URIC));
            }
        }
    }

    private static void checkPath(String string, String string2, String string3) throws URISyntaxException {
        if (string2 != null && string3 != null && string3.length() > 0 && string3.charAt(0) != '/') {
            throw new URISyntaxException(string, "Relative path in absolute URI");
        }
    }

    private static URI createURI(String string, String string2, String string3, String string4, String string5) throws URISyntaxException {
        string2 = ParseUtil.toString(string, null, string2, null, null, -1, string3, string4, string5);
        ParseUtil.checkPath(string2, string, string3);
        return new URI(string2);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String decode(String string) {
        int n = string.length();
        if (n == 0) return string;
        if (string.indexOf(37) < 0) {
            return string;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        CharBuffer charBuffer = CharBuffer.allocate(n);
        CharsetDecoder charsetDecoder = ThreadLocalCoders.decoderFor("UTF-8").onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT);
        char c = string.charAt(0);
        int n2 = 0;
        char c2 = c;
        while (n2 < n) {
            if (c2 != '%') {
                stringBuilder.append(c2);
                if (++n2 >= n) {
                    return stringBuilder.toString();
                }
                c2 = c = string.charAt(n2);
                continue;
            }
            byteBuffer.clear();
            int n3 = n2;
            char c3 = c2;
            do {
                block7 : {
                    byteBuffer.put(ParseUtil.unescape(string, n3));
                    n2 = n3 + 3;
                    if (n2 < n) break block7;
                    c = c3;
                    break;
                }
                c3 = c = string.charAt(n2);
                n3 = n2;
            } while (c == '%');
            byteBuffer.flip();
            charBuffer.clear();
            charsetDecoder.reset();
            if (charsetDecoder.decode(byteBuffer, charBuffer, true).isError()) throw new IllegalArgumentException("Error decoding percent encoded characters");
            if (charsetDecoder.flush(charBuffer).isError()) throw new IllegalArgumentException("Error decoding percent encoded characters");
            stringBuilder.append(charBuffer.flip().toString());
            c2 = c;
        }
        return stringBuilder.toString();
        catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException();
        }
    }

    public static String encodePath(String string) {
        return ParseUtil.encodePath(string, true);
    }

    public static String encodePath(String arrc, boolean bl) {
        char[] arrc2 = new char[arrc.length() * 2 + 16];
        int n = 0;
        char[] arrc3 = arrc.toCharArray();
        int n2 = arrc.length();
        arrc = arrc2;
        for (int i = 0; i < n2; ++i) {
            char c = arrc3[i];
            if (!bl && c == '/' || bl && c == File.separatorChar) {
                arrc[n] = (char)47;
                ++n;
            } else if (c <= '') {
                if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
                    arrc[n] = c;
                    ++n;
                } else if (encodedInPath.get(c)) {
                    n = ParseUtil.escape(arrc, c, n);
                } else {
                    arrc[n] = c;
                    ++n;
                }
            } else if (c > '\u07ff') {
                n = ParseUtil.escape(arrc, (char)(c >> 12 & 15 | 224), n);
                n = ParseUtil.escape(arrc, (char)(c >> 6 & 63 | 128), n);
                n = ParseUtil.escape(arrc, (char)(c >> 0 & 63 | 128), n);
            } else {
                n = ParseUtil.escape(arrc, (char)(c >> 6 & 31 | 192), n);
                n = ParseUtil.escape(arrc, (char)(c >> 0 & 63 | 128), n);
            }
            arrc2 = arrc;
            if (n + 9 > arrc.length) {
                int n3;
                int n4 = n3 = arrc.length * 2 + 16;
                if (n3 < 0) {
                    n4 = Integer.MAX_VALUE;
                }
                arrc2 = new char[n4];
                System.arraycopy((Object)arrc, 0, (Object)arrc2, 0, n);
            }
            arrc = arrc2;
        }
        return new String(arrc, 0, n);
    }

    private static int escape(char[] arrc, char c, int n) {
        int n2 = n + 1;
        arrc[n] = (char)37;
        n = n2 + 1;
        arrc[n2] = Character.forDigit(c >> 4 & 15, 16);
        arrc[n] = Character.forDigit(c & 15, 16);
        return n + 1;
    }

    public static URL fileToEncodedURL(File serializable) throws MalformedURLException {
        CharSequence charSequence = ParseUtil.encodePath(((File)serializable).getAbsolutePath());
        CharSequence charSequence2 = charSequence;
        if (!charSequence.startsWith("/")) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("/");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = charSequence2;
        if (!((String)charSequence2).endsWith("/")) {
            charSequence = charSequence2;
            if (((File)serializable).isDirectory()) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append((String)charSequence2);
                ((StringBuilder)serializable).append("/");
                charSequence = ((StringBuilder)serializable).toString();
            }
        }
        return new URL("file", "", (String)charSequence);
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

    private static boolean isEscaped(String string, int n) {
        boolean bl = false;
        if (string != null && string.length() > n + 2) {
            if (string.charAt(n) == '%' && ParseUtil.match(string.charAt(n + 1), L_HEX, H_HEX) && ParseUtil.match(string.charAt(n + 2), L_HEX, H_HEX)) {
                bl = true;
            }
            return bl;
        }
        return false;
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

    private static String quote(String string, long l, long l2) {
        string.length();
        StringBuffer stringBuffer = null;
        boolean bl = (1L & l) != 0L;
        for (int i = 0; i < string.length(); ++i) {
            StringBuffer stringBuffer2;
            char c = string.charAt(i);
            if (c < '') {
                if (!ParseUtil.match(c, l, l2) && !ParseUtil.isEscaped(string, i)) {
                    stringBuffer2 = stringBuffer;
                    if (stringBuffer == null) {
                        stringBuffer2 = new StringBuffer();
                        stringBuffer2.append(string.substring(0, i));
                    }
                    ParseUtil.appendEscape(stringBuffer2, (byte)c);
                } else {
                    stringBuffer2 = stringBuffer;
                    if (stringBuffer != null) {
                        stringBuffer.append(c);
                        stringBuffer2 = stringBuffer;
                    }
                }
            } else if (bl && (Character.isSpaceChar(c) || Character.isISOControl(c))) {
                stringBuffer2 = stringBuffer;
                if (stringBuffer == null) {
                    stringBuffer2 = new StringBuffer();
                    stringBuffer2.append(string.substring(0, i));
                }
                ParseUtil.appendEncoded(stringBuffer2, c);
            } else {
                stringBuffer2 = stringBuffer;
                if (stringBuffer != null) {
                    stringBuffer.append(c);
                    stringBuffer2 = stringBuffer;
                }
            }
            stringBuffer = stringBuffer2;
        }
        if (stringBuffer != null) {
            string = stringBuffer.toString();
        }
        return string;
    }

    private static String toString(String string, String string2, String string3, String string4, String string5, int n, String string6, String string7, String string8) {
        StringBuffer stringBuffer = new StringBuffer();
        if (string != null) {
            stringBuffer.append(string);
            stringBuffer.append(':');
        }
        ParseUtil.appendSchemeSpecificPart(stringBuffer, string2, string3, string4, string5, n, string6, string7);
        ParseUtil.appendFragment(stringBuffer, string8);
        return stringBuffer.toString();
    }

    public static URI toURI(URL object) {
        String string = ((URL)object).getProtocol();
        String string2 = ((URL)object).getAuthority();
        String string3 = ((URL)object).getPath();
        String string4 = ((URL)object).getQuery();
        String string5 = ((URL)object).getRef();
        object = string3;
        if (string3 != null) {
            object = string3;
            if (!string3.startsWith("/")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(string3);
                object = ((StringBuilder)object).toString();
            }
        }
        string3 = string2;
        if (string2 != null) {
            string3 = string2;
            if (string2.endsWith(":-1")) {
                string3 = string2.substring(0, string2.length() - 3);
            }
        }
        try {
            object = ParseUtil.createURI(string, string3, (String)object, string4, string5);
        }
        catch (URISyntaxException uRISyntaxException) {
            object = null;
        }
        return object;
    }

    private static byte unescape(String string, int n) {
        return (byte)Integer.parseInt(string.substring(n + 1, n + 3), 16);
    }

    public String canonizeString(String charSequence) {
        int n;
        CharSequence charSequence2;
        int n2;
        ((String)charSequence).length();
        do {
            n2 = ((String)charSequence).indexOf("/../");
            charSequence2 = charSequence;
            if (n2 < 0) break;
            n = ((String)charSequence).lastIndexOf(47, n2 - 1);
            if (n >= 0) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(((String)charSequence).substring(0, n));
                ((StringBuilder)charSequence2).append(((String)charSequence).substring(n2 + 3));
                charSequence = ((StringBuilder)charSequence2).toString();
                continue;
            }
            charSequence = ((String)charSequence).substring(n2 + 3);
        } while (true);
        do {
            n = ((String)charSequence2).indexOf("/./");
            charSequence = charSequence2;
            if (n < 0) break;
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(((String)charSequence2).substring(0, n));
            ((StringBuilder)charSequence).append(((String)charSequence2).substring(n + 2));
            charSequence2 = ((StringBuilder)charSequence).toString();
        } while (true);
        while (((String)charSequence).endsWith("/..")) {
            n = ((String)charSequence).indexOf("/..");
            n2 = ((String)charSequence).lastIndexOf(47, n - 1);
            if (n2 >= 0) {
                charSequence = ((String)charSequence).substring(0, n2 + 1);
                continue;
            }
            charSequence = ((String)charSequence).substring(0, n);
        }
        charSequence2 = charSequence;
        if (((String)charSequence).endsWith("/.")) {
            charSequence2 = ((String)charSequence).substring(0, ((String)charSequence).length() - 1);
        }
        return charSequence2;
    }
}

