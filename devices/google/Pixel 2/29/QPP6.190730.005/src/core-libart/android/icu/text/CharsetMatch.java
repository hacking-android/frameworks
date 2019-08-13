/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.CharsetDetector;
import android.icu.text.CharsetRecognizer;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class CharsetMatch
implements Comparable<CharsetMatch> {
    private String fCharsetName;
    private int fConfidence;
    private InputStream fInputStream = null;
    private String fLang;
    private byte[] fRawInput = null;
    private int fRawLength;

    CharsetMatch(CharsetDetector charsetDetector, CharsetRecognizer charsetRecognizer, int n) {
        this.fConfidence = n;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = charsetRecognizer.getName();
        this.fLang = charsetRecognizer.getLanguage();
    }

    CharsetMatch(CharsetDetector charsetDetector, CharsetRecognizer charsetRecognizer, int n, String string, String string2) {
        this.fConfidence = n;
        if (charsetDetector.fInputStream == null) {
            this.fRawInput = charsetDetector.fRawInput;
            this.fRawLength = charsetDetector.fRawLength;
        }
        this.fInputStream = charsetDetector.fInputStream;
        this.fCharsetName = string;
        this.fLang = string2;
    }

    @Override
    public int compareTo(CharsetMatch charsetMatch) {
        int n = 0;
        int n2 = this.fConfidence;
        int n3 = charsetMatch.fConfidence;
        if (n2 > n3) {
            n = 1;
        } else if (n2 < n3) {
            n = -1;
        }
        return n;
    }

    public int getConfidence() {
        return this.fConfidence;
    }

    public String getLanguage() {
        return this.fLang;
    }

    public String getName() {
        return this.fCharsetName;
    }

    public Reader getReader() {
        InputStream inputStream;
        Closeable closeable = inputStream = this.fInputStream;
        if (inputStream == null) {
            closeable = new ByteArrayInputStream(this.fRawInput, 0, this.fRawLength);
        }
        try {
            closeable.reset();
            closeable = new InputStreamReader((InputStream)closeable, this.getName());
            return closeable;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public String getString() throws IOException {
        return this.getString(-1);
    }

    public String getString(int n) throws IOException {
        if (this.fInputStream != null) {
            int n2;
            StringBuilder stringBuilder = new StringBuilder();
            char[] arrc = new char[1024];
            Reader reader = this.getReader();
            if (n < 0) {
                n = Integer.MAX_VALUE;
            }
            while ((n2 = reader.read(arrc, 0, Math.min(n, 1024))) >= 0) {
                stringBuilder.append(arrc, 0, n2);
                n -= n2;
            }
            reader.close();
            return stringBuilder.toString();
        }
        String string = this.getName();
        String string2 = "_rtl";
        if (string.indexOf("_rtl") < 0) {
            string2 = "_ltr";
        }
        n = string.indexOf(string2);
        string2 = string;
        if (n > 0) {
            string2 = string.substring(0, n);
        }
        return new String(this.fRawInput, string2);
    }
}

