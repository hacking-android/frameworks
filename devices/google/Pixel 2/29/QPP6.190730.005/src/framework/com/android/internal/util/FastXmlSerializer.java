/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlSerializer
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;
import org.xmlpull.v1.XmlSerializer;

public class FastXmlSerializer
implements XmlSerializer {
    private static final int DEFAULT_BUFFER_LEN = 32768;
    private static final String[] ESCAPE_TABLE = new String[]{"&#0;", "&#1;", "&#2;", "&#3;", "&#4;", "&#5;", "&#6;", "&#7;", "&#8;", "&#9;", "&#10;", "&#11;", "&#12;", "&#13;", "&#14;", "&#15;", "&#16;", "&#17;", "&#18;", "&#19;", "&#20;", "&#21;", "&#22;", "&#23;", "&#24;", "&#25;", "&#26;", "&#27;", "&#28;", "&#29;", "&#30;", "&#31;", null, null, "&quot;", null, null, null, "&amp;", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, "&lt;", null, "&gt;", null};
    private static String sSpace = "                                                              ";
    private final int mBufferLen;
    private ByteBuffer mBytes;
    private CharsetEncoder mCharset;
    private boolean mInTag;
    private boolean mIndent = false;
    private boolean mLineStart = true;
    private int mNesting = 0;
    private OutputStream mOutputStream;
    private int mPos;
    private final char[] mText;
    private Writer mWriter;

    @UnsupportedAppUsage
    public FastXmlSerializer() {
        this(32768);
    }

    public FastXmlSerializer(int n) {
        if (n <= 0) {
            n = 32768;
        }
        n = this.mBufferLen = n;
        this.mText = new char[n];
        this.mBytes = ByteBuffer.allocate(n);
    }

    private void append(char c) throws IOException {
        int n;
        int n2 = n = this.mPos;
        if (n >= this.mBufferLen - 1) {
            this.flush();
            n2 = this.mPos;
        }
        this.mText[n2] = c;
        this.mPos = n2 + 1;
    }

    private void append(String string2) throws IOException {
        this.append(string2, 0, string2.length());
    }

    private void append(String string2, int n, int n2) throws IOException {
        int n3;
        int n4 = this.mBufferLen;
        if (n2 > n4) {
            int n5 = n + n2;
            while (n < n5) {
                n2 = this.mBufferLen;
                int n6 = n + n2;
                if (n6 >= n5) {
                    n2 = n5 - n;
                }
                this.append(string2, n, n2);
                n = n6;
            }
            return;
        }
        int n7 = n3 = this.mPos;
        if (n3 + n2 > n4) {
            this.flush();
            n7 = this.mPos;
        }
        string2.getChars(n, n + n2, this.mText, n7);
        this.mPos = n7 + n2;
    }

    private void append(char[] arrc, int n, int n2) throws IOException {
        int n3;
        int n4 = this.mBufferLen;
        if (n2 > n4) {
            int n5 = n + n2;
            while (n < n5) {
                n2 = this.mBufferLen;
                int n6 = n + n2;
                if (n6 >= n5) {
                    n2 = n5 - n;
                }
                this.append(arrc, n, n2);
                n = n6;
            }
            return;
        }
        int n7 = n3 = this.mPos;
        if (n3 + n2 > n4) {
            this.flush();
            n7 = this.mPos;
        }
        System.arraycopy(arrc, n, this.mText, n7, n2);
        this.mPos = n7 + n2;
    }

    private void appendIndent(int n) throws IOException {
        int n2;
        n = n2 = n * 4;
        if (n2 > sSpace.length()) {
            n = sSpace.length();
        }
        this.append(sSpace, 0, n);
    }

    private void escapeAndAppendString(String string2) throws IOException {
        int n;
        int n2 = string2.length();
        char c = (char)ESCAPE_TABLE.length;
        String[] arrstring = ESCAPE_TABLE;
        int n3 = 0;
        for (n = 0; n < n2; ++n) {
            String string3;
            char c2 = string2.charAt(n);
            if (c2 >= c || (string3 = arrstring[c2]) == null) continue;
            if (n3 < n) {
                this.append(string2, n3, n - n3);
            }
            n3 = n + 1;
            this.append(string3);
        }
        if (n3 < n) {
            this.append(string2, n3, n - n3);
        }
    }

    private void escapeAndAppendString(char[] arrc, int n, int n2) throws IOException {
        int n3;
        char c = (char)ESCAPE_TABLE.length;
        String[] arrstring = ESCAPE_TABLE;
        int n4 = n;
        for (n3 = n; n3 < n + n2; ++n3) {
            String string2;
            char c2 = arrc[n3];
            if (c2 >= c || (string2 = arrstring[c2]) == null) continue;
            if (n4 < n3) {
                this.append(arrc, n4, n3 - n4);
            }
            n4 = n3 + 1;
            this.append(string2);
        }
        if (n4 < n3) {
            this.append(arrc, n4, n3 - n4);
        }
    }

    private void flushBytes() throws IOException {
        int n = this.mBytes.position();
        if (n > 0) {
            this.mBytes.flip();
            this.mOutputStream.write(this.mBytes.array(), 0, n);
            this.mBytes.clear();
        }
    }

    public XmlSerializer attribute(String string2, String string3, String string4) throws IOException, IllegalArgumentException, IllegalStateException {
        this.append(' ');
        if (string2 != null) {
            this.append(string2);
            this.append(':');
        }
        this.append(string3);
        this.append("=\"");
        this.escapeAndAppendString(string4);
        this.append('\"');
        this.mLineStart = false;
        return this;
    }

    public void cdsect(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void comment(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void docdecl(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void endDocument() throws IOException, IllegalArgumentException, IllegalStateException {
        this.flush();
    }

    public XmlSerializer endTag(String string2, String string3) throws IOException, IllegalArgumentException, IllegalStateException {
        --this.mNesting;
        if (this.mInTag) {
            this.append(" />\n");
        } else {
            if (this.mIndent && this.mLineStart) {
                this.appendIndent(this.mNesting);
            }
            this.append("</");
            if (string2 != null) {
                this.append(string2);
                this.append(':');
            }
            this.append(string3);
            this.append(">\n");
        }
        this.mLineStart = true;
        this.mInTag = false;
        return this;
    }

    public void entityRef(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void flush() throws IOException {
        int n = this.mPos;
        if (n > 0) {
            block4 : {
                if (this.mOutputStream != null) {
                    CharBuffer charBuffer = CharBuffer.wrap(this.mText, 0, n);
                    CoderResult coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
                    while (!coderResult.isError()) {
                        if (coderResult.isOverflow()) {
                            this.flushBytes();
                            coderResult = this.mCharset.encode(charBuffer, this.mBytes, true);
                            continue;
                        }
                        this.flushBytes();
                        this.mOutputStream.flush();
                        break block4;
                    }
                    throw new IOException(coderResult.toString());
                }
                this.mWriter.write(this.mText, 0, n);
                this.mWriter.flush();
            }
            this.mPos = 0;
        }
    }

    public int getDepth() {
        throw new UnsupportedOperationException();
    }

    public boolean getFeature(String string2) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getNamespace() {
        throw new UnsupportedOperationException();
    }

    public String getPrefix(String string2, boolean bl) throws IllegalArgumentException {
        throw new UnsupportedOperationException();
    }

    public Object getProperty(String string2) {
        throw new UnsupportedOperationException();
    }

    public void ignorableWhitespace(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void processingInstruction(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void setFeature(String string2, boolean bl) throws IllegalArgumentException, IllegalStateException {
        if (string2.equals("http://xmlpull.org/v1/doc/features.html#indent-output")) {
            this.mIndent = true;
            return;
        }
        throw new UnsupportedOperationException();
    }

    public void setOutput(OutputStream outputStream, String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        if (outputStream != null) {
            try {
                this.mCharset = Charset.forName(string2).newEncoder().onMalformedInput(CodingErrorAction.REPLACE).onUnmappableCharacter(CodingErrorAction.REPLACE);
            }
            catch (UnsupportedCharsetException unsupportedCharsetException) {
                throw (UnsupportedEncodingException)new UnsupportedEncodingException(string2).initCause(unsupportedCharsetException);
            }
            catch (IllegalCharsetNameException illegalCharsetNameException) {
                throw (UnsupportedEncodingException)new UnsupportedEncodingException(string2).initCause(illegalCharsetNameException);
            }
            this.mOutputStream = outputStream;
            return;
        }
        throw new IllegalArgumentException();
    }

    public void setOutput(Writer writer) throws IOException, IllegalArgumentException, IllegalStateException {
        this.mWriter = writer;
    }

    public void setPrefix(String string2, String string3) throws IOException, IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void setProperty(String string2, Object object) throws IllegalArgumentException, IllegalStateException {
        throw new UnsupportedOperationException();
    }

    public void startDocument(String string2, Boolean bl) throws IOException, IllegalArgumentException, IllegalStateException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<?xml version='1.0' encoding='utf-8' standalone='");
        string2 = bl != false ? "yes" : "no";
        stringBuilder.append(string2);
        stringBuilder.append("' ?>\n");
        this.append(stringBuilder.toString());
        this.mLineStart = true;
    }

    public XmlSerializer startTag(String string2, String string3) throws IOException, IllegalArgumentException, IllegalStateException {
        if (this.mInTag) {
            this.append(">\n");
        }
        if (this.mIndent) {
            this.appendIndent(this.mNesting);
        }
        ++this.mNesting;
        this.append('<');
        if (string2 != null) {
            this.append(string2);
            this.append(':');
        }
        this.append(string3);
        this.mInTag = true;
        this.mLineStart = false;
        return this;
    }

    public XmlSerializer text(String string2) throws IOException, IllegalArgumentException, IllegalStateException {
        boolean bl = this.mInTag;
        boolean bl2 = false;
        if (bl) {
            this.append(">");
            this.mInTag = false;
        }
        this.escapeAndAppendString(string2);
        if (this.mIndent) {
            bl = bl2;
            if (string2.length() > 0) {
                bl = bl2;
                if (string2.charAt(string2.length() - 1) == '\n') {
                    bl = true;
                }
            }
            this.mLineStart = bl;
        }
        return this;
    }

    public XmlSerializer text(char[] arrc, int n, int n2) throws IOException, IllegalArgumentException, IllegalStateException {
        boolean bl = this.mInTag;
        boolean bl2 = false;
        if (bl) {
            this.append(">");
            this.mInTag = false;
        }
        this.escapeAndAppendString(arrc, n, n2);
        if (this.mIndent) {
            if (arrc[n + n2 - 1] == '\n') {
                bl2 = true;
            }
            this.mLineStart = bl2;
        }
        return this;
    }
}

