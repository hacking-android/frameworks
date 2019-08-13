/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.kxml2.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Locale;
import org.xmlpull.v1.XmlSerializer;

public class KXmlSerializer
implements XmlSerializer {
    private static final int BUFFER_LEN = 8192;
    private int auto;
    private int depth;
    private String[] elementStack = new String[12];
    private String encoding;
    private boolean[] indent = new boolean[4];
    private int mPos;
    private final char[] mText = new char[8192];
    private int[] nspCounts = new int[4];
    private String[] nspStack = new String[8];
    private boolean pending;
    private boolean unicode;
    private Writer writer;

    private void append(char c) throws IOException {
        if (this.mPos >= 8192) {
            this.flushBuffer();
        }
        char[] arrc = this.mText;
        int n = this.mPos;
        this.mPos = n + 1;
        arrc[n] = c;
    }

    private void append(String string) throws IOException {
        this.append(string, 0, string.length());
    }

    private void append(String string, int n, int n2) throws IOException {
        while (n2 > 0) {
            int n3;
            if (this.mPos == 8192) {
                this.flushBuffer();
            }
            int n4 = n3 = 8192 - this.mPos;
            if (n3 > n2) {
                n4 = n2;
            }
            string.getChars(n, n + n4, this.mText, this.mPos);
            n += n4;
            n2 -= n4;
            this.mPos += n4;
        }
    }

    private final void check(boolean bl) throws IOException {
        Object[] arrobject;
        if (!this.pending) {
            return;
        }
        ++this.depth;
        this.pending = false;
        Object[] arrobject2 = this.indent;
        int n = arrobject2.length;
        int n2 = this.depth;
        if (n <= n2) {
            arrobject = new boolean[n2 + 4];
            System.arraycopy(arrobject2, 0, arrobject, 0, n2);
            this.indent = arrobject;
        }
        arrobject2 = this.indent;
        n2 = this.depth;
        arrobject2[n2] = arrobject2[n2 - 1];
        for (n2 = this.nspCounts[n2 - 1]; n2 < (arrobject = this.nspCounts)[n = this.depth]; ++n2) {
            this.append(" xmlns");
            if (!this.nspStack[n2 * 2].isEmpty()) {
                this.append(':');
                this.append(this.nspStack[n2 * 2]);
            } else if (this.getNamespace().isEmpty() && !this.nspStack[n2 * 2 + 1].isEmpty()) {
                throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
            }
            this.append("=\"");
            this.writeEscaped(this.nspStack[n2 * 2 + 1], 34);
            this.append('\"');
        }
        if (arrobject.length <= n + 1) {
            arrobject2 = new int[n + 8];
            System.arraycopy(arrobject, 0, arrobject2, 0, n + 1);
            this.nspCounts = arrobject2;
        }
        arrobject2 = this.nspCounts;
        n2 = this.depth;
        arrobject2[n2 + 1] = arrobject2[n2];
        if (bl) {
            this.append(" />");
        } else {
            this.append('>');
        }
    }

    private final void flushBuffer() throws IOException {
        int n = this.mPos;
        if (n > 0) {
            this.writer.write(this.mText, 0, n);
            this.writer.flush();
            this.mPos = 0;
        }
    }

    private final String getPrefix(String string, boolean bl, boolean bl2) throws IOException {
        CharSequence charSequence;
        int n;
        String string2;
        for (n = this.nspCounts[this.depth + 1] * 2 - 2; n >= 0; n -= 2) {
            if (!this.nspStack[n + 1].equals(string) || !bl && this.nspStack[n].isEmpty()) continue;
            string2 = this.nspStack[n];
            int n2 = n + 2;
            do {
                charSequence = string2;
                if (n2 >= this.nspCounts[this.depth + 1] * 2) break;
                if (this.nspStack[n2].equals(string2)) {
                    charSequence = null;
                    break;
                }
                ++n2;
            } while (true);
            if (charSequence == null) continue;
            return charSequence;
        }
        if (!bl2) {
            return null;
        }
        if (string.isEmpty()) {
            charSequence = "";
        } else {
            block2 : do {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("n");
                n = this.auto;
                this.auto = n + 1;
                ((StringBuilder)charSequence).append(n);
                string2 = ((StringBuilder)charSequence).toString();
                n = this.nspCounts[this.depth + 1] * 2 - 2;
                do {
                    charSequence = string2;
                    if (n < 0) continue block2;
                    if (string2.equals(this.nspStack[n])) {
                        charSequence = null;
                        continue block2;
                    }
                    n -= 2;
                } while (true);
            } while (charSequence == null);
        }
        bl = this.pending;
        this.pending = false;
        this.setPrefix((String)charSequence, string);
        this.pending = bl;
        return charSequence;
    }

    private static void reportInvalidCharacter(char c) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal character (U+");
        stringBuilder.append(Integer.toHexString(c));
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private final void writeEscaped(String string, int n) throws IOException {
        for (int i = 0; i < string.length(); ++i) {
            CharSequence charSequence;
            char c = string.charAt(i);
            if (c != '\t' && c != '\n' && c != '\r') {
                if (c != '&') {
                    if (c != '<') {
                        if (c != '>') {
                            if (c == n) {
                                charSequence = c == '\"' ? "&quot;" : "&apos;";
                                this.append((String)charSequence);
                                continue;
                            }
                            boolean bl = c >= ' ' && c <= '\ud7ff' || c >= '\ue000' && c <= '\ufffd';
                            if (bl) {
                                if (!this.unicode && c >= '') {
                                    charSequence = new StringBuilder();
                                    ((StringBuilder)charSequence).append("&#");
                                    ((StringBuilder)charSequence).append((int)c);
                                    ((StringBuilder)charSequence).append(";");
                                    this.append(((StringBuilder)charSequence).toString());
                                    continue;
                                }
                                this.append(c);
                                continue;
                            }
                            if (Character.isHighSurrogate(c) && i < string.length() - 1) {
                                this.writeSurrogate(c, string.charAt(i + 1));
                                ++i;
                                continue;
                            }
                            KXmlSerializer.reportInvalidCharacter(c);
                            continue;
                        }
                        this.append("&gt;");
                        continue;
                    }
                    this.append("&lt;");
                    continue;
                }
                this.append("&amp;");
                continue;
            }
            if (n == -1) {
                this.append(c);
                continue;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("&#");
            ((StringBuilder)charSequence).append((int)c);
            ((StringBuilder)charSequence).append(';');
            this.append(((StringBuilder)charSequence).toString());
        }
    }

    private void writeSurrogate(char c, char c2) throws IOException {
        if (Character.isLowSurrogate(c2)) {
            int n = Character.toCodePoint(c, c2);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("&#");
            stringBuilder.append(n);
            stringBuilder.append(";");
            this.append(stringBuilder.toString());
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad surrogate pair (U+");
        stringBuilder.append(Integer.toHexString(c));
        stringBuilder.append(" U+");
        stringBuilder.append(Integer.toHexString(c2));
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public XmlSerializer attribute(String string, String string2, String string3) throws IOException {
        if (this.pending) {
            String string4 = string;
            if (string == null) {
                string4 = "";
            }
            string = string4.isEmpty() ? "" : this.getPrefix(string4, false, true);
            this.append(' ');
            if (!string.isEmpty()) {
                this.append(string);
                this.append(':');
            }
            this.append(string2);
            this.append('=');
            char c = '\"';
            char c2 = string3.indexOf(34) == -1 ? c : (c = '\'');
            this.append(c2);
            this.writeEscaped(string3, c2);
            this.append(c2);
            return this;
        }
        throw new IllegalStateException("illegal position for attribute");
    }

    @Override
    public void cdsect(String string) throws IOException {
        this.check(false);
        string = string.replace("]]>", "]]]]><![CDATA[>");
        this.append("<![CDATA[");
        for (int i = 0; i < string.length(); ++i) {
            char c = string.charAt(i);
            boolean bl = c >= ' ' && c <= '\ud7ff' || c == '\t' || c == '\n' || c == '\r' || c >= '\ue000' && c <= '\ufffd';
            if (bl) {
                this.append(c);
                continue;
            }
            if (Character.isHighSurrogate(c) && i < string.length() - 1) {
                this.append("]]>");
                this.writeSurrogate(c, string.charAt(++i));
                this.append("<![CDATA[");
                continue;
            }
            KXmlSerializer.reportInvalidCharacter(c);
        }
        this.append("]]>");
    }

    @Override
    public void comment(String string) throws IOException {
        this.check(false);
        this.append("<!--");
        this.append(string);
        this.append("-->");
    }

    @Override
    public void docdecl(String string) throws IOException {
        this.append("<!DOCTYPE");
        this.append(string);
        this.append('>');
    }

    @Override
    public void endDocument() throws IOException {
        int n;
        while ((n = this.depth) > 0) {
            String[] arrstring = this.elementStack;
            this.endTag(arrstring[n * 3 - 3], arrstring[n * 3 - 1]);
        }
        this.flush();
    }

    @Override
    public XmlSerializer endTag(String object, String string) throws IOException {
        if (!this.pending) {
            --this.depth;
        }
        if ((object != null || this.elementStack[this.depth * 3] == null) && (object == null || ((String)object).equals(this.elementStack[this.depth * 3])) && this.elementStack[this.depth * 3 + 2].equals(string)) {
            int n;
            if (this.pending) {
                this.check(true);
                --this.depth;
            } else {
                if (this.indent[this.depth + 1]) {
                    this.append("\r\n");
                    for (n = 0; n < this.depth; ++n) {
                        this.append("  ");
                    }
                }
                this.append("</");
                object = this.elementStack[this.depth * 3 + 1];
                if (!((String)object).isEmpty()) {
                    this.append((String)object);
                    this.append(':');
                }
                this.append(string);
                this.append('>');
            }
            object = this.nspCounts;
            n = this.depth;
            object[n + 1] = object[n];
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("</{");
        stringBuilder.append((String)object);
        stringBuilder.append("}");
        stringBuilder.append(string);
        stringBuilder.append("> does not match start");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void entityRef(String string) throws IOException {
        this.check(false);
        this.append('&');
        this.append(string);
        this.append(';');
    }

    @Override
    public void flush() throws IOException {
        this.check(false);
        this.flushBuffer();
    }

    @Override
    public int getDepth() {
        int n = this.pending ? this.depth + 1 : this.depth;
        return n;
    }

    @Override
    public boolean getFeature(String string) {
        boolean bl = "http://xmlpull.org/v1/doc/features.html#indent-output".equals(string) ? this.indent[this.depth] : false;
        return bl;
    }

    @Override
    public String getName() {
        String string = this.getDepth() == 0 ? null : this.elementStack[this.getDepth() * 3 - 1];
        return string;
    }

    @Override
    public String getNamespace() {
        String string = this.getDepth() == 0 ? null : this.elementStack[this.getDepth() * 3 - 3];
        return string;
    }

    @Override
    public String getPrefix(String string, boolean bl) {
        try {
            string = this.getPrefix(string, false, bl);
            return string;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException.toString());
        }
    }

    @Override
    public Object getProperty(String string) {
        throw new RuntimeException("Unsupported property");
    }

    @Override
    public void ignorableWhitespace(String string) throws IOException {
        this.text(string);
    }

    @Override
    public void processingInstruction(String string) throws IOException {
        this.check(false);
        this.append("<?");
        this.append(string);
        this.append("?>");
    }

    @Override
    public void setFeature(String string, boolean bl) {
        if ("http://xmlpull.org/v1/doc/features.html#indent-output".equals(string)) {
            this.indent[this.depth] = bl;
            return;
        }
        throw new RuntimeException("Unsupported Feature");
    }

    @Override
    public void setOutput(OutputStream closeable, String string) throws IOException {
        if (closeable != null) {
            closeable = string == null ? new OutputStreamWriter((OutputStream)closeable) : new OutputStreamWriter((OutputStream)closeable, string);
            this.setOutput((Writer)closeable);
            this.encoding = string;
            if (string != null && string.toLowerCase(Locale.US).startsWith("utf")) {
                this.unicode = true;
            }
            return;
        }
        throw new IllegalArgumentException("os == null");
    }

    @Override
    public void setOutput(Writer arrobject) {
        this.writer = arrobject;
        arrobject = this.nspCounts;
        arrobject[0] = 2;
        arrobject[1] = 2;
        arrobject = this.nspStack;
        arrobject[0] = (int)"";
        arrobject[1] = (int)"";
        arrobject[2] = (int)"xml";
        arrobject[3] = (int)"http://www.w3.org/XML/1998/namespace";
        this.pending = false;
        this.auto = 0;
        this.depth = 0;
        this.unicode = false;
    }

    @Override
    public void setPrefix(String string, String arrobject) throws IOException {
        this.check(false);
        String string2 = string;
        if (string == null) {
            string2 = "";
        }
        string = arrobject;
        if (arrobject == null) {
            string = "";
        }
        if (string2.equals(this.getPrefix(string, true, false))) {
            return;
        }
        arrobject = this.nspCounts;
        int n = this.depth + 1;
        int n2 = arrobject[n];
        arrobject[n] = n2 + 1;
        arrobject = this.nspStack;
        n = n2 << 1;
        if (arrobject.length < n + 1) {
            String[] arrstring = new String[arrobject.length + 16];
            System.arraycopy(arrobject, 0, arrstring, 0, n);
            this.nspStack = arrstring;
        }
        arrobject = this.nspStack;
        arrobject[n] = (int)string2;
        arrobject[n + 1] = (int)string;
    }

    @Override
    public void setProperty(String charSequence, Object object) {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unsupported Property:");
        ((StringBuilder)charSequence).append(object);
        throw new RuntimeException(((StringBuilder)charSequence).toString());
    }

    @Override
    public void startDocument(String string, Boolean bl) throws IOException {
        this.append("<?xml version='1.0' ");
        if (string != null) {
            this.encoding = string;
            if (string.toLowerCase(Locale.US).startsWith("utf")) {
                this.unicode = true;
            }
        }
        if (this.encoding != null) {
            this.append("encoding='");
            this.append(this.encoding);
            this.append("' ");
        }
        if (bl != null) {
            this.append("standalone='");
            string = bl != false ? "yes" : "no";
            this.append(string);
            this.append("' ");
        }
        this.append("?>");
    }

    @Override
    public XmlSerializer startTag(String string, String string2) throws IOException {
        String[] arrstring;
        int n;
        Object object;
        int n2;
        this.check(false);
        if (this.indent[this.depth]) {
            this.append("\r\n");
            for (n2 = 0; n2 < this.depth; ++n2) {
                this.append("  ");
            }
        }
        if (((String[])(object = this.elementStack)).length < (n = this.depth * 3) + 3) {
            arrstring = new String[((String[])object).length + 12];
            System.arraycopy(object, 0, arrstring, 0, n);
            this.elementStack = arrstring;
        }
        object = string == null ? "" : this.getPrefix(string, true, true);
        if (string != null && string.isEmpty()) {
            for (n2 = this.nspCounts[this.depth]; n2 < this.nspCounts[this.depth + 1]; ++n2) {
                if (!this.nspStack[n2 * 2].isEmpty() || this.nspStack[n2 * 2 + 1].isEmpty()) continue;
                throw new IllegalStateException("Cannot set default namespace for elements in no namespace");
            }
        }
        arrstring = this.elementStack;
        n2 = n + 1;
        arrstring[n] = string;
        arrstring[n2] = object;
        arrstring[n2 + 1] = string2;
        this.append('<');
        if (!((String)object).isEmpty()) {
            this.append((String)object);
            this.append(':');
        }
        this.append(string2);
        this.pending = true;
        return this;
    }

    @Override
    public XmlSerializer text(String string) throws IOException {
        this.check(false);
        this.indent[this.depth] = false;
        this.writeEscaped(string, -1);
        return this;
    }

    @Override
    public XmlSerializer text(char[] arrc, int n, int n2) throws IOException {
        this.text(new String(arrc, n, n2));
        return this;
    }
}

