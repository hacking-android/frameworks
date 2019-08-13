/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.data;

import android.icu.impl.PatternProps;
import android.icu.impl.Utility;
import android.icu.impl.data.ResourceReader;
import android.icu.text.UTF16;
import java.io.IOException;

public class TokenIterator {
    private StringBuffer buf;
    private boolean done;
    private int lastpos;
    private String line;
    private int pos;
    private ResourceReader reader;

    public TokenIterator(ResourceReader resourceReader) {
        this.reader = resourceReader;
        this.line = null;
        this.done = false;
        this.buf = new StringBuffer();
        this.lastpos = -1;
        this.pos = -1;
    }

    private int nextToken(int n) {
        char c;
        int n2;
        char c2;
        CharSequence charSequence;
        block10 : {
            block8 : {
                block9 : {
                    n2 = PatternProps.skipWhiteSpace(this.line, n);
                    if (n2 == this.line.length()) {
                        return -1;
                    }
                    charSequence = this.line;
                    n = n2 + 1;
                    c = ((String)charSequence).charAt(n2);
                    c2 = '\u0000';
                    if (c == '\"') break block8;
                    if (c == '#') break block9;
                    if (c == '\'') break block8;
                    this.buf.append(c);
                    break block10;
                }
                return -1;
            }
            c2 = c;
        }
        charSequence = null;
        while (n < this.line.length()) {
            c = this.line.charAt(n);
            if (c == '\\') {
                int n3;
                int[] arrn = charSequence;
                if (charSequence == null) {
                    arrn = new int[]{(CharSequence)(n + 1)};
                }
                if ((n3 = Utility.unescapeAt(this.line, arrn)) >= 0) {
                    UTF16.append(this.buf, n3);
                    n = arrn[0];
                    charSequence = arrn;
                    continue;
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid escape at ");
                ((StringBuilder)charSequence).append(this.reader.describePosition());
                ((StringBuilder)charSequence).append(':');
                ((StringBuilder)charSequence).append(n);
                throw new RuntimeException(((StringBuilder)charSequence).toString());
            }
            if (c2 != '\u0000' && c == c2 || c2 == '\u0000' && PatternProps.isWhiteSpace(c)) {
                return n + 1;
            }
            if (c2 == '\u0000' && c == '#') {
                return n;
            }
            this.buf.append(c);
            ++n;
        }
        if (c2 == '\u0000') {
            return n;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Unterminated quote at ");
        ((StringBuilder)charSequence).append(this.reader.describePosition());
        ((StringBuilder)charSequence).append(':');
        ((StringBuilder)charSequence).append(n2);
        throw new RuntimeException(((StringBuilder)charSequence).toString());
    }

    public String describePosition() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.reader.describePosition());
        stringBuilder.append(':');
        stringBuilder.append(this.lastpos + 1);
        return stringBuilder.toString();
    }

    public int getLineNumber() {
        return this.reader.getLineNumber();
    }

    public String next() throws IOException {
        if (this.done) {
            return null;
        }
        do {
            int n;
            if (this.line == null) {
                this.line = this.reader.readLineSkippingComments();
                if (this.line == null) {
                    this.done = true;
                    return null;
                }
                this.pos = 0;
            }
            this.buf.setLength(0);
            this.lastpos = n = this.pos;
            this.pos = this.nextToken(n);
            if (this.pos >= 0) break;
            this.line = null;
        } while (true);
        return this.buf.toString();
    }
}

