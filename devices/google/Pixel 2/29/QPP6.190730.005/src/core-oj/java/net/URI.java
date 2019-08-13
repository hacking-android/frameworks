/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
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
import java.text.Normalizer;
import sun.nio.cs.ThreadLocalCoders;

public final class URI
implements Comparable<URI>,
Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long H_ALPHA;
    private static final long H_ALPHANUM;
    private static final long H_DASH;
    private static final long H_DIGIT = 0L;
    private static final long H_DOT;
    private static final long H_ESCAPED = 0L;
    private static final long H_HEX;
    private static final long H_LEFT_BRACKET;
    private static final long H_LOWALPHA;
    private static final long H_MARK;
    private static final long H_PATH;
    private static final long H_PCHAR;
    private static final long H_REG_NAME;
    private static final long H_RESERVED;
    private static final long H_SCHEME;
    private static final long H_SERVER;
    private static final long H_SERVER_PERCENT;
    private static final long H_UNDERSCORE;
    private static final long H_UNRESERVED;
    private static final long H_UPALPHA;
    private static final long H_URIC;
    private static final long H_URIC_NO_SLASH;
    private static final long H_USERINFO;
    private static final long L_ALPHA = 0L;
    private static final long L_ALPHANUM;
    private static final long L_DASH;
    private static final long L_DIGIT;
    private static final long L_DOT;
    private static final long L_ESCAPED = 1L;
    private static final long L_HEX;
    private static final long L_LEFT_BRACKET;
    private static final long L_LOWALPHA = 0L;
    private static final long L_MARK;
    private static final long L_PATH;
    private static final long L_PCHAR;
    private static final long L_REG_NAME;
    private static final long L_RESERVED;
    private static final long L_SCHEME;
    private static final long L_SERVER;
    private static final long L_SERVER_PERCENT;
    private static final long L_UNDERSCORE;
    private static final long L_UNRESERVED;
    private static final long L_UPALPHA = 0L;
    private static final long L_URIC;
    private static final long L_URIC_NO_SLASH;
    private static final long L_USERINFO;
    private static final char[] hexDigits;
    static final long serialVersionUID = -6052424284110960213L;
    private transient String authority;
    private volatile transient String decodedAuthority = null;
    private volatile transient String decodedFragment = null;
    private volatile transient String decodedPath = null;
    private volatile transient String decodedQuery = null;
    private volatile transient String decodedSchemeSpecificPart = null;
    private volatile transient String decodedUserInfo = null;
    private transient String fragment;
    private volatile transient int hash;
    private transient String host;
    private transient String path;
    private transient int port = -1;
    private transient String query;
    private transient String scheme;
    private volatile transient String schemeSpecificPart;
    private volatile String string;
    private transient String userInfo;

    static {
        L_DIGIT = URI.lowMask('0', '9');
        H_UPALPHA = URI.highMask('A', 'Z');
        H_LOWALPHA = URI.highMask('a', 'z');
        H_ALPHA = H_LOWALPHA | H_UPALPHA;
        long l = L_DIGIT;
        L_ALPHANUM = l | 0L;
        H_ALPHANUM = H_ALPHA | 0L;
        L_HEX = l;
        H_HEX = URI.highMask('A', 'F') | URI.highMask('a', 'f');
        L_MARK = URI.lowMask("-_.!~*'()");
        H_MARK = URI.highMask("-_.!~*'()");
        L_UNRESERVED = L_ALPHANUM | L_MARK;
        H_UNRESERVED = H_ALPHANUM | H_MARK;
        L_RESERVED = URI.lowMask(";/?:@&=+$,[]");
        H_RESERVED = URI.highMask(";/?:@&=+$,[]");
        long l2 = L_RESERVED;
        l = L_UNRESERVED;
        L_URIC = l2 | l | 1L;
        H_URIC = H_RESERVED | H_UNRESERVED | 0L;
        L_PCHAR = l | 1L | URI.lowMask(":@&=+$,");
        H_PCHAR = H_UNRESERVED | 0L | URI.highMask(":@&=+$,");
        L_PATH = L_PCHAR | URI.lowMask(";/");
        H_PATH = H_PCHAR | URI.highMask(";/");
        L_DASH = URI.lowMask("-");
        H_DASH = URI.highMask("-");
        L_UNDERSCORE = URI.lowMask("_");
        H_UNDERSCORE = URI.highMask("_");
        L_DOT = URI.lowMask(".");
        H_DOT = URI.highMask(".");
        L_USERINFO = L_UNRESERVED | 1L | URI.lowMask(";:&=+$,");
        H_USERINFO = H_UNRESERVED | 0L | URI.highMask(";:&=+$,");
        L_REG_NAME = L_UNRESERVED | 1L | URI.lowMask("$,;:@&=+");
        H_REG_NAME = H_UNRESERVED | 0L | URI.highMask("$,;:@&=+");
        L_SERVER = L_USERINFO | L_ALPHANUM | L_DASH | URI.lowMask(".:@[]");
        H_SERVER = H_USERINFO | H_ALPHANUM | H_DASH | URI.highMask(".:@[]");
        L_SERVER_PERCENT = L_SERVER | URI.lowMask("%");
        H_SERVER_PERCENT = H_SERVER | URI.highMask("%");
        L_LEFT_BRACKET = URI.lowMask("[");
        H_LEFT_BRACKET = URI.highMask("[");
        L_SCHEME = L_DIGIT | 0L | URI.lowMask("+-.");
        H_SCHEME = H_ALPHA | 0L | URI.highMask("+-.");
        L_URIC_NO_SLASH = L_UNRESERVED | 1L | URI.lowMask(";?:@&=+$,");
        H_URIC_NO_SLASH = H_UNRESERVED | 0L | URI.highMask(";?:@&=+$,");
        hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    }

    private URI() {
    }

    public URI(String string) throws URISyntaxException {
        new Parser(string).parse(false);
    }

    public URI(String string, String string2, String string3) throws URISyntaxException {
        new Parser(this.toString(string, string2, null, null, null, -1, null, null, string3)).parse(false);
    }

    public URI(String string, String string2, String string3, int n, String string4, String string5, String string6) throws URISyntaxException {
        string2 = this.toString(string, null, null, string2, string3, n, string4, string5, string6);
        URI.checkPath(string2, string, string4);
        new Parser(string2).parse(true);
    }

    public URI(String string, String string2, String string3, String string4) throws URISyntaxException {
        this(string, null, string2, -1, string3, null, string4);
    }

    public URI(String string, String string2, String string3, String string4, String string5) throws URISyntaxException {
        string2 = this.toString(string, null, string2, null, null, -1, string3, string4, string5);
        URI.checkPath(string2, string, string3);
        new Parser(string2).parse(false);
    }

    private void appendAuthority(StringBuffer stringBuffer, String string, String string2, String string3, int n) {
        block11 : {
            block10 : {
                boolean bl = false;
                if (string3 == null) break block10;
                stringBuffer.append("//");
                if (string2 != null) {
                    stringBuffer.append(URI.quote(string2, L_USERINFO, H_USERINFO));
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
                if (n == -1) break block11;
                stringBuffer.append(':');
                stringBuffer.append(n);
                break block11;
            }
            if (string == null) break block11;
            stringBuffer.append("//");
            if (string.startsWith("[")) {
                n = string.indexOf("]");
                String string4 = string;
                String string5 = "";
                string2 = string4;
                string3 = string5;
                if (n != -1) {
                    string2 = string4;
                    string3 = string5;
                    if (string.indexOf(":") != -1) {
                        if (n == string.length()) {
                            string2 = "";
                            string3 = string;
                        } else {
                            string3 = string.substring(0, n + 1);
                            string2 = string.substring(n + 1);
                        }
                    }
                }
                stringBuffer.append(string3);
                stringBuffer.append(URI.quote(string2, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
            } else {
                stringBuffer.append(URI.quote(string, L_REG_NAME | L_SERVER, H_REG_NAME | H_SERVER));
            }
        }
    }

    private static void appendEncoded(StringBuffer stringBuffer, char c) {
        Object object = null;
        try {
            Object object2 = ThreadLocalCoders.encoderFor("UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("");
            stringBuilder.append(c);
            object = object2 = ((CharsetEncoder)object2).encode(CharBuffer.wrap(stringBuilder.toString()));
        }
        catch (CharacterCodingException characterCodingException) {
            // empty catch block
        }
        while (((Buffer)object).hasRemaining()) {
            int n = ((ByteBuffer)object).get() & 255;
            if (n >= 128) {
                URI.appendEscape(stringBuffer, (byte)n);
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

    private void appendFragment(StringBuffer stringBuffer, String string) {
        if (string != null) {
            stringBuffer.append('#');
            stringBuffer.append(URI.quote(string, L_URIC, H_URIC));
        }
    }

    private void appendSchemeSpecificPart(StringBuffer stringBuffer, String string, String string2, String string3, String string4, int n, String string5, String string6) {
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
                    stringBuffer.append(URI.quote(string2, L_URIC, H_URIC));
                }
            } else {
                stringBuffer.append(URI.quote(string, L_URIC, H_URIC));
            }
        } else {
            this.appendAuthority(stringBuffer, string2, string3, string4, n);
            if (string5 != null) {
                stringBuffer.append(URI.quote(string5, L_PATH, H_PATH));
            }
            if (string6 != null) {
                stringBuffer.append('?');
                stringBuffer.append(URI.quote(string6, L_URIC, H_URIC));
            }
        }
    }

    private static void checkPath(String string, String string2, String string3) throws URISyntaxException {
        if (string2 != null && string3 != null && string3.length() > 0 && string3.charAt(0) != '/') {
            throw new URISyntaxException(string, "Relative path in absolute URI");
        }
    }

    private static int compare(String string, String string2) {
        if (string == string2) {
            return 0;
        }
        if (string != null) {
            if (string2 != null) {
                return string.compareTo(string2);
            }
            return 1;
        }
        return -1;
    }

    private static int compareIgnoringCase(String string, String string2) {
        if (string == string2) {
            return 0;
        }
        if (string != null) {
            if (string2 != null) {
                int n;
                int n2 = string.length();
                int n3 = n2 < (n = string2.length()) ? n2 : n;
                for (int i = 0; i < n3; ++i) {
                    int n4 = URI.toLower(string.charAt(i)) - URI.toLower(string2.charAt(i));
                    if (n4 == 0) continue;
                    return n4;
                }
                return n2 - n;
            }
            return 1;
        }
        return -1;
    }

    public static URI create(String object) {
        try {
            object = new URI((String)object);
            return object;
        }
        catch (URISyntaxException uRISyntaxException) {
            throw new IllegalArgumentException(uRISyntaxException.getMessage(), uRISyntaxException);
        }
    }

    private static byte decode(char c, char c2) {
        return (byte)((URI.decode(c) & 15) << 4 | (URI.decode(c2) & 15) << 0);
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
        return -1;
    }

    private static String decode(String string) {
        if (string == null) {
            return string;
        }
        int n = string.length();
        if (n == 0) {
            return string;
        }
        if (string.indexOf(37) < 0) {
            return string;
        }
        StringBuffer stringBuffer = new StringBuffer(n);
        ByteBuffer byteBuffer = ByteBuffer.allocate(n);
        CharBuffer charBuffer = CharBuffer.allocate(n);
        CharsetDecoder charsetDecoder = ThreadLocalCoders.decoderFor("UTF-8").onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
        int n2 = string.charAt(0);
        int n3 = 0;
        int n4 = 0;
        int n5 = n2;
        while (n4 < n) {
            int n6;
            if (n5 == 91) {
                n2 = 1;
            } else {
                n2 = n3;
                if (n3 != 0) {
                    n2 = n3;
                    if (n5 == 93) {
                        n2 = 0;
                    }
                }
            }
            if (n5 == 37 && n2 == 0) {
                block10 : {
                    byteBuffer.clear();
                    int n7 = n4;
                    n4 = n5;
                    do {
                        n6 = n7 + 1;
                        n5 = string.charAt(n6);
                        byteBuffer.put(URI.decode((char)n5, string.charAt(++n6)));
                        if (++n6 >= n) break block10;
                        n4 = n3 = (int)string.charAt(n6);
                        n7 = n6;
                    } while (n3 == 37);
                    n4 = n3;
                }
                byteBuffer.flip();
                charBuffer.clear();
                charsetDecoder.reset();
                charsetDecoder.decode(byteBuffer, charBuffer, true);
                charsetDecoder.flush(charBuffer);
                stringBuffer.append(charBuffer.flip().toString());
                n5 = n4;
                n3 = n2;
                n4 = n6;
                continue;
            }
            stringBuffer.append((char)n5);
            if (++n4 >= n) break;
            n5 = n6 = string.charAt(n4);
            n3 = n2;
        }
        return stringBuffer.toString();
    }

    private void defineSchemeSpecificPart() {
        if (this.schemeSpecificPart != null) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        this.appendSchemeSpecificPart(stringBuffer, null, this.getAuthority(), this.getUserInfo(), this.host, this.port, this.getPath(), this.getQuery());
        if (stringBuffer.length() == 0) {
            return;
        }
        this.schemeSpecificPart = stringBuffer.toString();
    }

    private void defineString() {
        if (this.string != null) {
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        String string = this.scheme;
        if (string != null) {
            stringBuffer.append(string);
            stringBuffer.append(':');
        }
        if (this.isOpaque()) {
            stringBuffer.append(this.schemeSpecificPart);
        } else {
            if (this.host != null) {
                boolean bl;
                stringBuffer.append("//");
                string = this.userInfo;
                if (string != null) {
                    stringBuffer.append(string);
                    stringBuffer.append('@');
                }
                if (bl = this.host.indexOf(58) >= 0 && !this.host.startsWith("[") && !this.host.endsWith("]")) {
                    stringBuffer.append('[');
                }
                stringBuffer.append(this.host);
                if (bl) {
                    stringBuffer.append(']');
                }
                if (this.port != -1) {
                    stringBuffer.append(':');
                    stringBuffer.append(this.port);
                }
            } else if (this.authority != null) {
                stringBuffer.append("//");
                stringBuffer.append(this.authority);
            }
            if ((string = this.path) != null) {
                stringBuffer.append(string);
            }
            if (this.query != null) {
                stringBuffer.append('?');
                stringBuffer.append(this.query);
            }
        }
        if (this.fragment != null) {
            stringBuffer.append('#');
            stringBuffer.append(this.fragment);
        }
        this.string = stringBuffer.toString();
    }

    private static String encode(String object) {
        int n = ((String)object).length();
        if (n == 0) {
            return object;
        }
        int n2 = 0;
        do {
            if (((String)object).charAt(n2) < '') continue;
            Object object2 = Normalizer.normalize((CharSequence)object, Normalizer.Form.NFC);
            object = null;
            try {
                object = object2 = ThreadLocalCoders.encoderFor("UTF-8").encode(CharBuffer.wrap((CharSequence)object2));
            }
            catch (CharacterCodingException characterCodingException) {
                // empty catch block
            }
            object2 = new StringBuffer();
            while (((Buffer)object).hasRemaining()) {
                n2 = ((ByteBuffer)object).get() & 255;
                if (n2 >= 128) {
                    URI.appendEscape((StringBuffer)object2, (byte)n2);
                    continue;
                }
                ((StringBuffer)object2).append((char)n2);
            }
            return ((StringBuffer)object2).toString();
        } while (++n2 < n);
        return object;
    }

    private static boolean equal(String string, String string2) {
        if (string == string2) {
            return true;
        }
        if (string != null && string2 != null) {
            if (string.length() != string2.length()) {
                return false;
            }
            if (string.indexOf(37) < 0) {
                return string.equals(string2);
            }
            int n = string.length();
            int n2 = 0;
            while (n2 < n) {
                char c = string.charAt(n2);
                char c2 = string2.charAt(n2);
                if (c != '%') {
                    if (c != c2) {
                        return false;
                    }
                    ++n2;
                    continue;
                }
                if (c2 != '%') {
                    return false;
                }
                if (URI.toLower(string.charAt(++n2)) != URI.toLower(string2.charAt(n2))) {
                    return false;
                }
                if (URI.toLower(string.charAt(++n2)) != URI.toLower(string2.charAt(n2))) {
                    return false;
                }
                ++n2;
            }
            return true;
        }
        return false;
    }

    private static boolean equalIgnoringCase(String string, String string2) {
        if (string == string2) {
            return true;
        }
        if (string != null && string2 != null) {
            int n = string.length();
            if (string2.length() != n) {
                return false;
            }
            for (int i = 0; i < n; ++i) {
                if (URI.toLower(string.charAt(i)) == URI.toLower(string2.charAt(i))) continue;
                return false;
            }
            return true;
        }
        return false;
    }

    private static int hash(int n, String string) {
        if (string == null) {
            return n;
        }
        n = string.indexOf(37) < 0 ? n * 127 + string.hashCode() : URI.normalizedHash(n, string);
        return n;
    }

    private static int hashIgnoringCase(int n, String string) {
        if (string == null) {
            return n;
        }
        int n2 = n;
        int n3 = string.length();
        for (n = 0; n < n3; ++n) {
            n2 = n2 * 31 + URI.toLower(string.charAt(n));
        }
        return n2;
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

    private static int join(char[] arrc, int[] arrn) {
        int n = arrn.length;
        int n2 = arrc.length - 1;
        int n3 = 0;
        if (arrc[0] == '\u0000') {
            arrc[0] = (char)47;
            n3 = 0 + 1;
        }
        for (int i = 0; i < n; ++i) {
            int n4;
            int n5 = arrn[i];
            if (n5 == -1) continue;
            if (n3 == n5) {
                for (n4 = n3; n4 <= n2 && arrc[n4] != '\u0000'; ++n4) {
                }
                n3 = n4;
                if (n4 > n2) continue;
                arrc[n4] = (char)47;
                n3 = n4 + 1;
                continue;
            }
            if (n3 < n5) {
                n4 = n3;
                while (n5 <= n2 && arrc[n5] != '\u0000') {
                    arrc[n4] = arrc[n5];
                    ++n4;
                    ++n5;
                }
                n3 = n4;
                if (n5 > n2) continue;
                arrc[n4] = (char)47;
                n3 = n4 + 1;
                continue;
            }
            throw new InternalError();
        }
        return n3;
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
        boolean bl = false;
        boolean bl2 = false;
        if (c == '\u0000') {
            return false;
        }
        if (c < '@') {
            if ((1L << c & l) != 0L) {
                bl2 = true;
            }
            return bl2;
        }
        if (c < '') {
            bl2 = bl;
            if ((1L << c - 64 & l2) != 0L) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    private static void maybeAddLeadingDot(char[] arrc, int[] arrn) {
        int n;
        if (arrc[0] == '\u0000') {
            return;
        }
        int n2 = arrn.length;
        for (n = 0; n < n2 && arrn[n] < 0; ++n) {
        }
        if (n < n2 && n != 0) {
            for (n = arrn[n]; n < arrc.length && arrc[n] != ':' && arrc[n] != '\u0000'; ++n) {
            }
            if (n < arrc.length && arrc[n] != '\u0000') {
                arrc[0] = (char)46;
                arrc[1] = (char)(false ? 1 : 0);
                arrn[0] = 0;
                return;
            }
            return;
        }
    }

    private static int needsNormalization(String string) {
        int n;
        block11 : {
            int n2;
            int n3 = 1;
            int n4 = 0;
            int n5 = string.length() - 1;
            for (n2 = 0; n2 <= n5 && string.charAt(n2) == '/'; ++n2) {
            }
            n = n4;
            int n6 = n2;
            if (n2 > 1) {
                n3 = 0;
                n6 = n2;
                n = n4;
            }
            block1 : while (n6 <= n5) {
                block9 : {
                    block10 : {
                        n2 = n3;
                        if (string.charAt(n6) != '.') break block9;
                        if (n6 == n5 || string.charAt(n6 + 1) == '/') break block10;
                        n2 = n3;
                        if (string.charAt(n6 + 1) != '.') break block9;
                        if (n6 + 1 == n5) break block10;
                        n2 = n3;
                        if (string.charAt(n6 + 2) != '/') break block9;
                    }
                    n2 = 0;
                }
                int n7 = n + 1;
                n4 = n6;
                do {
                    n3 = n2;
                    n = n7;
                    n6 = n4;
                    if (n4 > n5) continue block1;
                    n3 = n4 + 1;
                    if (string.charAt(n4) == '/') break;
                    n4 = n3;
                } while (true);
                n4 = n3;
                do {
                    n3 = n2;
                    n = n7;
                    n6 = ++n4;
                    if (n4 > n5) continue block1;
                    if (string.charAt(n4) != '/') {
                        n3 = n2;
                        n = n7;
                        n6 = n4;
                        continue block1;
                    }
                    n2 = 0;
                } while (true);
            }
            if (n3 == 0) break block11;
            n = -1;
        }
        return n;
    }

    private static String normalize(String string) {
        return URI.normalize(string, false);
    }

    private static String normalize(String string, boolean bl) {
        int n = URI.needsNormalization(string);
        if (n < 0) {
            return string;
        }
        Object object = string.toCharArray();
        int[] arrn = new int[n];
        URI.split((char[])object, arrn);
        URI.removeDots((char[])object, arrn, bl);
        URI.maybeAddLeadingDot((char[])object, arrn);
        object = new String((char[])object, 0, URI.join((char[])object, arrn));
        if (((String)object).equals(string)) {
            return string;
        }
        return object;
    }

    private static URI normalize(URI uRI) {
        Object object;
        if (!uRI.isOpaque() && (object = uRI.path) != null && ((String)object).length() != 0) {
            String string = URI.normalize(uRI.path);
            if (string == uRI.path) {
                return uRI;
            }
            object = new URI();
            ((URI)object).scheme = uRI.scheme;
            ((URI)object).fragment = uRI.fragment;
            ((URI)object).authority = uRI.authority;
            ((URI)object).userInfo = uRI.userInfo;
            ((URI)object).host = uRI.host;
            ((URI)object).port = uRI.port;
            ((URI)object).path = string;
            ((URI)object).query = uRI.query;
            return object;
        }
        return uRI;
    }

    private static int normalizedHash(int n, String string) {
        int n2 = 0;
        int n3 = 0;
        while (n3 < string.length()) {
            int n4 = string.charAt(n3);
            n2 = n2 * 31 + n4;
            if (n4 == 37) {
                for (n4 = n3 + 1; n4 < n3 + 3; ++n4) {
                    n2 = n2 * 31 + URI.toUpper(string.charAt(n4));
                }
                n4 = n3 + 2;
                n3 = n2;
                n2 = n4;
            } else {
                n4 = n2;
                n2 = n3;
                n3 = n4;
            }
            n4 = n2 + 1;
            n2 = n3;
            n3 = n4;
        }
        return n * 127 + n2;
    }

    private static String quote(String string, long l, long l2) {
        string.length();
        StringBuffer stringBuffer = null;
        boolean bl = (1L & l) != 0L;
        for (int i = 0; i < string.length(); ++i) {
            StringBuffer stringBuffer2;
            char c = string.charAt(i);
            if (c < '') {
                if (!URI.match(c, l, l2)) {
                    stringBuffer2 = stringBuffer;
                    if (stringBuffer == null) {
                        stringBuffer2 = new StringBuffer();
                        stringBuffer2.append(string.substring(0, i));
                    }
                    URI.appendEscape(stringBuffer2, (byte)c);
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
                URI.appendEncoded(stringBuffer2, c);
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

    private void readObject(ObjectInputStream object) throws ClassNotFoundException, IOException {
        this.port = -1;
        ((ObjectInputStream)object).defaultReadObject();
        try {
            object = new Parser(this.string);
            ((Parser)object).parse(false);
            return;
        }
        catch (URISyntaxException uRISyntaxException) {
            object = new InvalidObjectException("Invalid URI");
            ((Throwable)object).initCause(uRISyntaxException);
            throw object;
        }
    }

    private static URI relativize(URI object, URI uRI) {
        if (!uRI.isOpaque() && !((URI)object).isOpaque()) {
            if (URI.equalIgnoringCase(((URI)object).scheme, uRI.scheme) && URI.equal(((URI)object).authority, uRI.authority)) {
                String string = URI.normalize(((URI)object).path);
                String string2 = URI.normalize(uRI.path);
                Object object2 = string;
                if (!string.equals(string2)) {
                    object = string;
                    if (string.indexOf(47) != -1) {
                        object = string.substring(0, string.lastIndexOf(47) + 1);
                    }
                    object2 = object;
                    if (!string2.startsWith((String)object)) {
                        return uRI;
                    }
                }
                object = new URI();
                ((URI)object).path = string2.substring(((String)object2).length());
                ((URI)object).query = uRI.query;
                ((URI)object).fragment = uRI.fragment;
                return object;
            }
            return uRI;
        }
        return uRI;
    }

    private static void removeDots(char[] arrc, int[] arrn, boolean bl) {
        int n = arrn.length;
        int n2 = arrc.length - 1;
        int n3 = 0;
        while (n3 < n) {
            int n4;
            int n5;
            block13 : {
                n4 = 0;
                do {
                    if (arrc[n5 = arrn[n3]] == '.') {
                        if (n5 == n2) {
                            n4 = 1;
                            n5 = n3;
                            n3 = n4;
                            break block13;
                        }
                        if (arrc[n5 + 1] == '\u0000') {
                            n4 = 1;
                            n5 = n3;
                            n3 = n4;
                            break block13;
                        }
                        if (arrc[n5 + 1] == '.' && (n5 + 1 == n2 || arrc[n5 + 2] == '\u0000')) {
                            n4 = 2;
                            n5 = n3;
                            n3 = n4;
                            break block13;
                        }
                    }
                    n3 = n5 = n3 + 1;
                } while (n5 < n);
                n3 = n4;
            }
            if (n5 > n || n3 == 0) break;
            if (n3 == 1) {
                arrn[n5] = -1;
            } else {
                for (n3 = n5 - 1; n3 >= 0 && arrn[n3] == -1; --n3) {
                }
                if (n3 >= 0) {
                    n4 = arrn[n3];
                    if (arrc[n4] != '.' || arrc[n4 + 1] != '.' || arrc[n4 + 2] != '\u0000') {
                        arrn[n5] = -1;
                        arrn[n3] = -1;
                    }
                } else if (bl) {
                    arrn[n5] = -1;
                }
            }
            n3 = n5 + 1;
        }
    }

    private static URI resolve(URI object, URI object2) {
        if (!((URI)object2).isOpaque() && !((URI)object).isOpaque()) {
            String string;
            if (((URI)object2).scheme == null && ((URI)object2).authority == null && ((URI)object2).path.equals("") && (string = ((URI)object2).fragment) != null && ((URI)object2).query == null) {
                Object object3 = ((URI)object).fragment;
                if (object3 != null && string.equals(object3)) {
                    return object;
                }
                object3 = new URI();
                ((URI)object3).scheme = ((URI)object).scheme;
                ((URI)object3).authority = ((URI)object).authority;
                ((URI)object3).userInfo = ((URI)object).userInfo;
                ((URI)object3).host = ((URI)object).host;
                ((URI)object3).port = ((URI)object).port;
                ((URI)object3).path = ((URI)object).path;
                ((URI)object3).fragment = ((URI)object2).fragment;
                ((URI)object3).query = ((URI)object).query;
                return object3;
            }
            if (((URI)object2).scheme != null) {
                return object2;
            }
            URI uRI = new URI();
            uRI.scheme = ((URI)object).scheme;
            uRI.query = ((URI)object2).query;
            uRI.fragment = ((URI)object2).fragment;
            string = ((URI)object2).authority;
            if (string == null) {
                uRI.authority = ((URI)object).authority;
                uRI.host = ((URI)object).host;
                uRI.userInfo = ((URI)object).userInfo;
                uRI.port = ((URI)object).port;
                string = ((URI)object2).path;
                if (string != null && !string.isEmpty()) {
                    uRI.path = ((URI)object2).path.length() > 0 && ((URI)object2).path.charAt(0) == '/' ? URI.normalize(((URI)object2).path, true) : URI.resolvePath(((URI)object).path, ((URI)object2).path, ((URI)object).isAbsolute());
                } else {
                    uRI.path = ((URI)object).path;
                    object2 = ((URI)object2).query;
                    object = object2 != null ? object2 : ((URI)object).query;
                    uRI.query = object;
                }
            } else {
                uRI.authority = string;
                uRI.host = ((URI)object2).host;
                uRI.userInfo = ((URI)object2).userInfo;
                uRI.host = ((URI)object2).host;
                uRI.port = ((URI)object2).port;
                uRI.path = ((URI)object2).path;
            }
            return uRI;
        }
        return object2;
    }

    private static String resolvePath(String string, String string2, boolean bl) {
        int n = string.lastIndexOf(47);
        int n2 = string2.length();
        CharSequence charSequence = "";
        if (n2 == 0) {
            string2 = charSequence;
            if (n >= 0) {
                string2 = string.substring(0, n + 1);
            }
        } else {
            charSequence = new StringBuffer(string.length() + n2);
            if (n >= 0) {
                ((StringBuffer)charSequence).append(string.substring(0, n + 1));
            }
            ((StringBuffer)charSequence).append(string2);
            string2 = ((StringBuffer)charSequence).toString();
        }
        return URI.normalize(string2, true);
    }

    private static void split(char[] arrc, int[] arrn) {
        int n;
        int n2;
        int n3 = arrc.length - 1;
        int n4 = 0;
        int n5 = 0;
        do {
            n = ++n4;
            n2 = n5;
            if (n4 > n3) break;
            if (arrc[n4] != '/') {
                n = n4;
                n2 = n5;
                break;
            }
            arrc[n4] = (char)(false ? 1 : 0);
        } while (true);
        block1 : while (n <= n3) {
            n4 = n2 + 1;
            arrn[n2] = n++;
            while (n <= n3) {
                n2 = n + 1;
                if (arrc[n] != '/') {
                    n = n2;
                    continue;
                }
                arrc[n2 - 1] = (char)(false ? 1 : 0);
                for (n = n2; n <= n3 && arrc[n] == '/'; ++n) {
                    arrc[n] = (char)(false ? 1 : 0);
                }
                n2 = n4;
                continue block1;
            }
            n2 = n4;
        }
        if (n2 == arrn.length) {
            return;
        }
        throw new InternalError();
    }

    private static int toLower(char c) {
        if (c >= 'A' && c <= 'Z') {
            return c + 32;
        }
        return c;
    }

    private String toString(String string, String string2, String string3, String string4, String string5, int n, String string6, String string7, String string8) {
        StringBuffer stringBuffer = new StringBuffer();
        if (string != null) {
            stringBuffer.append(string);
            stringBuffer.append(':');
        }
        this.appendSchemeSpecificPart(stringBuffer, string2, string3, string4, string5, n, string6, string7);
        this.appendFragment(stringBuffer, string8);
        return stringBuffer.toString();
    }

    private static int toUpper(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 32;
        }
        return c;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        this.defineString();
        objectOutputStream.defaultWriteObject();
    }

    @Override
    public int compareTo(URI uRI) {
        int n = URI.compareIgnoringCase(this.scheme, uRI.scheme);
        if (n != 0) {
            return n;
        }
        if (this.isOpaque()) {
            if (uRI.isOpaque()) {
                n = URI.compare(this.schemeSpecificPart, uRI.schemeSpecificPart);
                if (n != 0) {
                    return n;
                }
                return URI.compare(this.fragment, uRI.fragment);
            }
            return 1;
        }
        if (uRI.isOpaque()) {
            return -1;
        }
        if (this.host != null && uRI.host != null) {
            n = URI.compare(this.userInfo, uRI.userInfo);
            if (n != 0) {
                return n;
            }
            n = URI.compareIgnoringCase(this.host, uRI.host);
            if (n != 0) {
                return n;
            }
            n = this.port - uRI.port;
            if (n != 0) {
                return n;
            }
        } else {
            n = URI.compare(this.authority, uRI.authority);
            if (n != 0) {
                return n;
            }
        }
        if ((n = URI.compare(this.path, uRI.path)) != 0) {
            return n;
        }
        n = URI.compare(this.query, uRI.query);
        if (n != 0) {
            return n;
        }
        return URI.compare(this.fragment, uRI.fragment);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof URI)) {
            return false;
        }
        object = (URI)object;
        if (this.isOpaque() != ((URI)object).isOpaque()) {
            return false;
        }
        if (!URI.equalIgnoringCase(this.scheme, ((URI)object).scheme)) {
            return false;
        }
        if (!URI.equal(this.fragment, ((URI)object).fragment)) {
            return false;
        }
        if (this.isOpaque()) {
            return URI.equal(this.schemeSpecificPart, ((URI)object).schemeSpecificPart);
        }
        if (!URI.equal(this.path, ((URI)object).path)) {
            return false;
        }
        if (!URI.equal(this.query, ((URI)object).query)) {
            return false;
        }
        String string = this.authority;
        String string2 = ((URI)object).authority;
        if (string == string2) {
            return true;
        }
        if (this.host != null) {
            if (!URI.equal(this.userInfo, ((URI)object).userInfo)) {
                return false;
            }
            if (!URI.equalIgnoringCase(this.host, ((URI)object).host)) {
                return false;
            }
            if (this.port != ((URI)object).port) {
                return false;
            }
        } else if (string != null ? !URI.equal(string, string2) : string != string2) {
            return false;
        }
        return true;
    }

    public String getAuthority() {
        if (this.decodedAuthority == null) {
            this.decodedAuthority = URI.decode(this.authority);
        }
        return this.decodedAuthority;
    }

    public String getFragment() {
        String string;
        if (this.decodedFragment == null && (string = this.fragment) != null) {
            this.decodedFragment = URI.decode(string);
        }
        return this.decodedFragment;
    }

    public String getHost() {
        return this.host;
    }

    public String getPath() {
        String string;
        if (this.decodedPath == null && (string = this.path) != null) {
            this.decodedPath = URI.decode(string);
        }
        return this.decodedPath;
    }

    public int getPort() {
        return this.port;
    }

    public String getQuery() {
        String string;
        if (this.decodedQuery == null && (string = this.query) != null) {
            this.decodedQuery = URI.decode(string);
        }
        return this.decodedQuery;
    }

    public String getRawAuthority() {
        return this.authority;
    }

    public String getRawFragment() {
        return this.fragment;
    }

    public String getRawPath() {
        return this.path;
    }

    public String getRawQuery() {
        return this.query;
    }

    public String getRawSchemeSpecificPart() {
        this.defineSchemeSpecificPart();
        return this.schemeSpecificPart;
    }

    public String getRawUserInfo() {
        return this.userInfo;
    }

    public String getScheme() {
        return this.scheme;
    }

    public String getSchemeSpecificPart() {
        if (this.decodedSchemeSpecificPart == null) {
            this.decodedSchemeSpecificPart = URI.decode(this.getRawSchemeSpecificPart());
        }
        return this.decodedSchemeSpecificPart;
    }

    public String getUserInfo() {
        String string;
        if (this.decodedUserInfo == null && (string = this.userInfo) != null) {
            this.decodedUserInfo = URI.decode(string);
        }
        return this.decodedUserInfo;
    }

    public int hashCode() {
        if (this.hash != 0) {
            return this.hash;
        }
        int n = URI.hash(URI.hashIgnoringCase(0, this.scheme), this.fragment);
        if (this.isOpaque()) {
            n = URI.hash(n, this.schemeSpecificPart);
        } else {
            n = URI.hash(URI.hash(n, this.path), this.query);
            n = this.host != null ? URI.hashIgnoringCase(URI.hash(n, this.userInfo), this.host) + this.port * 1949 : URI.hash(n, this.authority);
        }
        this.hash = n;
        return n;
    }

    public boolean isAbsolute() {
        boolean bl = this.scheme != null;
        return bl;
    }

    public boolean isOpaque() {
        boolean bl = this.path == null;
        return bl;
    }

    public URI normalize() {
        return URI.normalize(this);
    }

    public URI parseServerAuthority() throws URISyntaxException {
        if (this.host == null && this.authority != null) {
            this.defineString();
            new Parser(this.string).parse(true);
            return this;
        }
        return this;
    }

    public URI relativize(URI uRI) {
        return URI.relativize(this, uRI);
    }

    public URI resolve(String string) {
        return this.resolve(URI.create(string));
    }

    public URI resolve(URI uRI) {
        return URI.resolve(this, uRI);
    }

    public String toASCIIString() {
        this.defineString();
        return URI.encode(this.string);
    }

    public String toString() {
        this.defineString();
        return this.string;
    }

    public URL toURL() throws MalformedURLException {
        if (this.isAbsolute()) {
            return new URL(this.toString());
        }
        throw new IllegalArgumentException("URI is not absolute");
    }

    private class Parser {
        private String input;
        private int ipv6byteCount = 0;
        private boolean requireServerAuthority = false;

        Parser(String string) {
            this.input = string;
            URI.this.string = string;
        }

        private boolean at(int n, int n2, char c) {
            boolean bl = n < n2 && this.charAt(n) == c;
            return bl;
        }

        private boolean at(int n, int n2, String string) {
            int n3 = string.length();
            boolean bl = false;
            if (n3 > n2 - n) {
                return false;
            }
            n2 = 0;
            while (n2 < n3 && this.charAt(n) == string.charAt(n2)) {
                ++n2;
                ++n;
            }
            if (n2 == n3) {
                bl = true;
            }
            return bl;
        }

        private char charAt(int n) {
            return this.input.charAt(n);
        }

        private void checkChar(int n, long l, long l2, String string) throws URISyntaxException {
            this.checkChars(n, n + 1, l, l2, string);
        }

        private void checkChars(int n, int n2, long l, long l2, String string) throws URISyntaxException {
            if ((n = this.scan(n, n2, l, l2)) < n2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Illegal character in ");
                stringBuilder.append(string);
                this.fail(stringBuilder.toString(), n);
            }
        }

        private void fail(String string) throws URISyntaxException {
            throw new URISyntaxException(this.input, string);
        }

        private void fail(String string, int n) throws URISyntaxException {
            throw new URISyntaxException(this.input, string, n);
        }

        private void failExpecting(String string, int n) throws URISyntaxException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected ");
            stringBuilder.append(string);
            this.fail(stringBuilder.toString(), n);
        }

        private void failExpecting(String string, String string2, int n) throws URISyntaxException {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Expected ");
            stringBuilder.append(string);
            stringBuilder.append(" following ");
            stringBuilder.append(string2);
            this.fail(stringBuilder.toString(), n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private int parseAuthority(int n, int n2) throws URISyntaxException {
            int n3;
            int n4 = n;
            Object var4_4 = null;
            int n5 = this.scan(n, n2, "", "]");
            boolean bl = true;
            if (n5 > n) {
                n5 = this.scan(n, n2, L_SERVER_PERCENT, H_SERVER_PERCENT) == n2 ? 1 : 0;
                n3 = n5;
            } else {
                n5 = this.scan(n, n2, L_SERVER, H_SERVER) == n2 ? 1 : 0;
                n3 = n5;
            }
            if (this.scan(n, n2, L_REG_NAME, H_REG_NAME) != n2) {
                bl = false;
            }
            if (bl && n3 == 0) {
                URI.this.authority = this.substring(n, n2);
                return n2;
            }
            n5 = n4;
            Object var8_8 = var4_4;
            if (n3 != 0) {
                try {
                    n5 = this.parseServer(n, n2);
                    if (n5 < n2) {
                        this.failExpecting("end of authority", n5);
                    }
                    URI.this.authority = this.substring(n, n2);
                    var8_8 = var4_4;
                }
                catch (URISyntaxException uRISyntaxException) {
                    URI.this.userInfo = null;
                    URI.this.host = null;
                    URI.this.port = -1;
                    if (this.requireServerAuthority) throw uRISyntaxException;
                    n5 = n;
                }
            }
            if (n5 >= n2) return n2;
            if (bl) {
                URI.this.authority = this.substring(n, n2);
                return n2;
            }
            if (var8_8 != null) throw var8_8;
            this.fail("Illegal character in authority", n5);
            return n2;
        }

        private int parseHierarchical(int n, int n2) throws URISyntaxException {
            int n3;
            n = n3 = n;
            if (this.at(n3, n2, '/')) {
                n = n3;
                if (this.at(n3 + 1, n2, '/')) {
                    n = n3 + 2;
                    n3 = this.scan(n, n2, "", "/?#");
                    if (n3 > n) {
                        n = this.parseAuthority(n, n3);
                    } else if (n3 >= n2) {
                        this.failExpecting("authority", n);
                    }
                }
            }
            n3 = this.scan(n, n2, "", "?#");
            this.checkChars(n, n3, L_PATH, H_PATH, "path");
            URI.this.path = this.substring(n, n3);
            n3 = n = n3;
            if (this.at(n, n2, '?')) {
                n3 = this.scan(++n, n2, "", "#");
                this.checkChars(n, n3, L_URIC, H_URIC, "query");
                URI.this.query = this.substring(n, n3);
            }
            return n3;
        }

        private int parseHostname(int n, int n2) throws URISyntaxException {
            int n3;
            int n4 = n;
            int n5 = -1;
            while ((n3 = this.scan(n4, n2, L_ALPHANUM, H_ALPHANUM)) > n4) {
                int n6 = n4;
                n5 = n4;
                if (n3 > n4) {
                    n4 = n3;
                    long l = L_ALPHANUM;
                    long l2 = L_DASH;
                    long l3 = L_UNDERSCORE;
                    long l4 = H_ALPHANUM;
                    long l5 = H_DASH;
                    n3 = this.scan(n4, n2, l | l2 | l3, H_UNDERSCORE | (l4 | l5));
                    n5 = n4;
                    if (n3 > n4) {
                        if (this.charAt(n3 - 1) == '-') {
                            this.fail("Illegal character in hostname", n3 - 1);
                        }
                        n5 = n3;
                    }
                }
                if ((n3 = this.scan(n5, n2, '.')) <= n5) {
                    n4 = n5;
                    n5 = n6;
                    break;
                }
                n4 = n3;
                n5 = n6;
                if (n3 < n2) continue;
                n5 = n6;
                n4 = n3;
                break;
            }
            if (n4 < n2 && !this.at(n4, n2, ':')) {
                this.fail("Illegal character in hostname", n4);
            }
            if (n5 < 0) {
                this.failExpecting("hostname", n);
            }
            if (n5 > n && !URI.match(this.charAt(n5), 0L, H_ALPHA)) {
                this.fail("Illegal character in hostname", n5);
            }
            URI.this.host = this.substring(n, n4);
            return n4;
        }

        private int parseIPv4Address(int n, int n2) {
            int n3;
            block5 : {
                int n4;
                try {
                    n3 = n4 = this.scanIPv4Address(n, n2, false);
                    if (n4 <= n) break block5;
                    n3 = n4;
                    if (n4 >= n2) break block5;
                    n3 = n4;
                }
                catch (NumberFormatException numberFormatException) {
                    return -1;
                }
                catch (URISyntaxException uRISyntaxException) {
                    return -1;
                }
                if (this.charAt(n4) != ':') {
                    n3 = -1;
                }
            }
            if (n3 > n) {
                URI.this.host = this.substring(n, n3);
            }
            return n3;
        }

        private int parseIPv6Reference(int n, int n2) throws URISyntaxException {
            boolean bl;
            int n3 = n;
            boolean bl2 = false;
            int n4 = this.scanHexSeq(n3, n2);
            if (n4 > n3) {
                n3 = n4;
                if (this.at(n3, n2, "::")) {
                    bl = true;
                    n4 = this.scanHexPost(n3 + 2, n2);
                } else {
                    n4 = n3;
                    bl = bl2;
                    if (this.at(n3, n2, ':')) {
                        n4 = this.takeIPv4Address(n3 + 1, n2, "IPv4 address");
                        this.ipv6byteCount += 4;
                        bl = bl2;
                    }
                }
            } else {
                n4 = n3;
                bl = bl2;
                if (this.at(n3, n2, "::")) {
                    bl = true;
                    n4 = this.scanHexPost(n3 + 2, n2);
                }
            }
            if (n4 < n2) {
                this.fail("Malformed IPv6 address", n);
            }
            if (this.ipv6byteCount > 16) {
                this.fail("IPv6 address too long", n);
            }
            if (!bl && this.ipv6byteCount < 16) {
                this.fail("IPv6 address too short", n);
            }
            if (bl && this.ipv6byteCount == 16) {
                this.fail("Malformed IPv6 address", n);
            }
            return n4;
        }

        private int parseServer(int n, int n2) throws URISyntaxException {
            int n3 = n;
            int n4 = this.scan(n3, n2, "/?#", "@");
            n = n3;
            if (n4 >= n3) {
                n = n3;
                if (this.at(n4, n2, '@')) {
                    this.checkChars(n3, n4, L_USERINFO, H_USERINFO, "user info");
                    URI.this.userInfo = this.substring(n3, n4);
                    n = n4 + 1;
                }
            }
            if (this.at(n, n2, '[')) {
                if ((n4 = this.scan(++n, n2, "/?#", "]")) > n && this.at(n4, n2, ']')) {
                    n3 = this.scan(n, n4, "", "%");
                    if (n3 > n) {
                        this.parseIPv6Reference(n, n3);
                        if (n3 + 1 == n4) {
                            this.fail("scope id expected");
                        }
                        this.checkChars(n3 + 1, n4, L_ALPHANUM, H_ALPHANUM, "scope id");
                    } else {
                        this.parseIPv6Reference(n, n4);
                    }
                    URI.this.host = this.substring(n - 1, n4 + 1);
                    n = n4 + 1;
                } else {
                    this.failExpecting("closing bracket for IPv6 address", n4);
                }
            } else {
                n3 = this.parseIPv4Address(n, n2);
                n = n3 <= n ? this.parseHostname(n, n2) : n3;
            }
            n3 = n;
            if (this.at(n, n2, ':')) {
                if ((n3 = this.scan(++n, n2, "", "/")) > n) {
                    this.checkChars(n, n3, L_DIGIT, 0L, "port number");
                    try {
                        URI.this.port = Integer.parseInt(this.substring(n, n3));
                    }
                    catch (NumberFormatException numberFormatException) {
                        this.fail("Malformed port number", n);
                    }
                } else {
                    n3 = n;
                }
            }
            if (n3 < n2) {
                this.failExpecting("port number", n3);
            }
            return n3;
        }

        private int scan(int n, int n2, char c) {
            if (n < n2 && this.charAt(n) == c) {
                return n + 1;
            }
            return n;
        }

        private int scan(int n, int n2, long l, long l2) throws URISyntaxException {
            while (n < n2) {
                int n3;
                char c = this.charAt(n);
                if (URI.match(c, l, l2)) {
                    ++n;
                    continue;
                }
                if ((1L & l) == 0L || (n3 = this.scanEscape(n, n2, c)) <= n) break;
                n = n3;
            }
            return n;
        }

        private int scan(int n, int n2, String string, String string2) {
            while (n < n2) {
                char c = this.charAt(n);
                if (string.indexOf(c) >= 0) {
                    return -1;
                }
                if (string2.indexOf(c) >= 0) break;
                ++n;
            }
            return n;
        }

        private int scanByte(int n, int n2) throws URISyntaxException {
            if ((n2 = this.scan(n, n2, L_DIGIT, 0L)) <= n) {
                return n2;
            }
            if (Integer.parseInt(this.substring(n, n2)) > 255) {
                return n;
            }
            return n2;
        }

        private int scanEscape(int n, int n2, char c) throws URISyntaxException {
            if (c == '%') {
                if (n + 3 <= n2 && URI.match(this.charAt(n + 1), L_HEX, H_HEX) && URI.match(this.charAt(n + 2), L_HEX, H_HEX)) {
                    return n + 3;
                }
                this.fail("Malformed escape pair", n);
            } else if (c > '' && !Character.isSpaceChar(c) && !Character.isISOControl(c)) {
                return n + 1;
            }
            return n;
        }

        private int scanHexPost(int n, int n2) throws URISyntaxException {
            if (n == n2) {
                return n;
            }
            int n3 = this.scanHexSeq(n, n2);
            if (n3 > n) {
                n = n3;
                if (this.at(n3, n2, ':')) {
                    n = this.takeIPv4Address(n3 + 1, n2, "hex digits or IPv4 address");
                    this.ipv6byteCount += 4;
                }
            } else {
                n = this.takeIPv4Address(n, n2, "hex digits or IPv4 address");
                this.ipv6byteCount += 4;
            }
            return n;
        }

        private int scanHexSeq(int n, int n2) throws URISyntaxException {
            int n3 = this.scan(n, n2, L_HEX, H_HEX);
            if (n3 <= n) {
                return -1;
            }
            if (this.at(n3, n2, '.')) {
                return -1;
            }
            if (n3 > n + 4) {
                this.fail("IPv6 hexadecimal digit sequence too long", n);
            }
            this.ipv6byteCount += 2;
            n = n3;
            do {
                n3 = n;
                if (n >= n2) break;
                if (!this.at(n, n2, ':')) {
                    n3 = n;
                    break;
                }
                if (this.at(n + 1, n2, ':')) {
                    n3 = n;
                    break;
                }
                n3 = n + 1;
                n = this.scan(n3, n2, L_HEX, H_HEX);
                if (n <= n3) {
                    this.failExpecting("digits for an IPv6 address", n3);
                }
                if (this.at(n, n2, '.')) {
                    --n3;
                    break;
                }
                if (n > n3 + 4) {
                    this.fail("IPv6 hexadecimal digit sequence too long", n3);
                }
                this.ipv6byteCount += 2;
            } while (true);
            return n3;
        }

        private int scanIPv4Address(int n, int n2, boolean bl) throws URISyntaxException {
            block2 : {
                block9 : {
                    block4 : {
                        int n3;
                        block8 : {
                            int n4;
                            block7 : {
                                block6 : {
                                    block5 : {
                                        block3 : {
                                            n3 = this.scan(n, n2, L_DIGIT | L_DOT, 0L | H_DOT);
                                            if (n3 <= n || bl && n3 != n2) break block2;
                                            n2 = n4 = this.scanByte(n, n3);
                                            if (n4 > n) break block3;
                                            n = n2;
                                            break block4;
                                        }
                                        n4 = n = this.scan(n2, n3, '.');
                                        if (n > n2) break block5;
                                        n = n4;
                                        break block4;
                                    }
                                    n = n2 = this.scanByte(n4, n3);
                                    if (n2 <= n4) break block4;
                                    n2 = n4 = this.scan(n, n3, '.');
                                    if (n4 > n) break block6;
                                    n = n2;
                                    break block4;
                                }
                                n4 = n = this.scanByte(n2, n3);
                                if (n > n2) break block7;
                                n = n4;
                                break block4;
                            }
                            n = n2 = this.scan(n4, n3, '.');
                            if (n2 <= n4) break block4;
                            n2 = n4 = this.scanByte(n, n3);
                            if (n4 > n) break block8;
                            n = n2;
                            break block4;
                        }
                        if (n2 >= n3) break block9;
                        n = n2;
                    }
                    this.fail("Malformed IPv4 address", n);
                    return -1;
                }
                return n2;
            }
            return -1;
        }

        private String substring(int n, int n2) {
            return this.input.substring(n, n2);
        }

        private int takeIPv4Address(int n, int n2, String string) throws URISyntaxException {
            if ((n2 = this.scanIPv4Address(n, n2, true)) <= n) {
                this.failExpecting(string, n);
            }
            return n2;
        }

        void parse(boolean bl) throws URISyntaxException {
            int n;
            this.requireServerAuthority = bl;
            int n2 = this.input.length();
            int n3 = this.scan(0, n2, "/?#", ":");
            if (n3 >= 0 && this.at(n3, n2, ':')) {
                if (n3 == 0) {
                    this.failExpecting("scheme name", 0);
                }
                this.checkChar(0, 0L, H_ALPHA, "scheme name");
                this.checkChars(1, n3, L_SCHEME, H_SCHEME, "scheme name");
                URI.this.scheme = this.substring(0, n3);
                if (this.at(++n3, n2, '/')) {
                    int n4 = this.parseHierarchical(n3, n2);
                    n = n3;
                    n3 = n4;
                } else {
                    int n5 = this.scan(n3, n2, "", "#");
                    if (n5 <= n3) {
                        this.failExpecting("scheme-specific part", n3);
                    }
                    this.checkChars(n3, n5, L_URIC, H_URIC, "opaque part");
                    n = n3;
                    n3 = n5;
                }
            } else {
                n3 = this.parseHierarchical(0, n2);
                n = 0;
            }
            URI.this.schemeSpecificPart = this.substring(n, n3);
            n = n3;
            if (this.at(n3, n2, '#')) {
                this.checkChars(n3 + 1, n2, L_URIC, H_URIC, "fragment");
                URI.this.fragment = this.substring(n3 + 1, n2);
                n = n2;
            }
            if (n < n2) {
                this.fail("end of URI", n);
            }
        }
    }

}

