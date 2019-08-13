/*
 * Decompiled with CFR 0.145.
 */
package java.net;

import java.io.CharArrayWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import java.security.AccessController;
import java.util.BitSet;
import sun.security.action.GetPropertyAction;

public class URLEncoder {
    static final int caseDiff = 32;
    static String dfltEncName;
    static BitSet dontNeedEncoding;

    static {
        int n;
        dfltEncName = null;
        dontNeedEncoding = new BitSet(256);
        for (n = 97; n <= 122; ++n) {
            dontNeedEncoding.set(n);
        }
        for (n = 65; n <= 90; ++n) {
            dontNeedEncoding.set(n);
        }
        for (n = 48; n <= 57; ++n) {
            dontNeedEncoding.set(n);
        }
        dontNeedEncoding.set(32);
        dontNeedEncoding.set(45);
        dontNeedEncoding.set(95);
        dontNeedEncoding.set(46);
        dontNeedEncoding.set(42);
        dfltEncName = AccessController.doPrivileged(new GetPropertyAction("file.encoding"));
    }

    private URLEncoder() {
    }

    @Deprecated
    public static String encode(String string) {
        Object var1_2 = null;
        try {
            string = URLEncoder.encode(string, dfltEncName);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            string = var1_2;
        }
        return string;
    }

    public static String encode(String string, String arrby) throws UnsupportedEncodingException {
        char c = '\u0000';
        StringBuffer stringBuffer = new StringBuffer(string.length());
        CharArrayWriter charArrayWriter = new CharArrayWriter();
        if (arrby != null) {
            Charset charset;
            int n;
            try {
                charset = Charset.forName((String)arrby);
                n = 0;
            }
            catch (UnsupportedCharsetException unsupportedCharsetException) {
                throw new UnsupportedEncodingException((String)arrby);
            }
            catch (IllegalCharsetNameException illegalCharsetNameException) {
                throw new UnsupportedEncodingException((String)arrby);
            }
            while (n < string.length()) {
                char c2 = string.charAt(n);
                int n2 = n;
                char c3 = c2;
                if (dontNeedEncoding.get(c2)) {
                    n2 = c2;
                    if (c2 == ' ') {
                        n2 = 43;
                        c = '\u0001';
                    }
                    stringBuffer.append((char)n2);
                    ++n;
                    continue;
                }
                do {
                    charArrayWriter.write(c3);
                    n = n2;
                    if (c3 >= '\ud800') {
                        n = n2;
                        if (c3 <= '\udbff') {
                            n = n2;
                            if (n2 + 1 < string.length()) {
                                c = string.charAt(n2 + 1);
                                n = n2;
                                if (c >= '\udc00') {
                                    n = n2;
                                    if (c <= '\udfff') {
                                        charArrayWriter.write(c);
                                        n = n2 + 1;
                                    }
                                }
                            }
                        }
                    }
                    if (++n >= string.length()) break;
                    arrby = dontNeedEncoding;
                    c3 = c = string.charAt(n);
                    n2 = n;
                } while (!arrby.get(c));
                charArrayWriter.flush();
                arrby = new String(charArrayWriter.toCharArray()).getBytes(charset);
                for (n2 = 0; n2 < arrby.length; ++n2) {
                    char c4;
                    stringBuffer.append('%');
                    char c5 = c4 = Character.forDigit(arrby[n2] >> 4 & 15, 16);
                    if (Character.isLetter(c4)) {
                        c5 = c = (char)(c4 - 32);
                    }
                    stringBuffer.append(c5);
                    c4 = c5 = Character.forDigit(arrby[n2] & 15, 16);
                    if (Character.isLetter(c5)) {
                        c4 = c = (char)(c5 - 32);
                    }
                    stringBuffer.append(c4);
                }
                charArrayWriter.reset();
                c = '\u0001';
            }
            if (c != '\u0000') {
                string = stringBuffer.toString();
            }
            return string;
        }
        throw new NullPointerException("charsetName");
    }
}

