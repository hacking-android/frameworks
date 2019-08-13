/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Norm2AllModes;
import android.icu.impl.Normalizer2Impl;

@Deprecated
public final class ComposedCharIter {
    @Deprecated
    public static final char DONE = '\uffff';
    private int curChar = 0;
    private String decompBuf;
    private final Normalizer2Impl n2impl;
    private int nextChar = -1;

    @Deprecated
    public ComposedCharIter() {
        this(false, 0);
    }

    @Deprecated
    public ComposedCharIter(boolean bl, int n) {
        this.n2impl = bl ? Norm2AllModes.getNFKCInstance().impl : Norm2AllModes.getNFCInstance().impl;
    }

    private void findNextChar() {
        int n;
        block2 : {
            this.decompBuf = null;
            for (n = this.curChar + 1; n < 65535; ++n) {
                this.decompBuf = this.n2impl.getDecomposition(n);
                if (this.decompBuf == null) {
                    continue;
                }
                break block2;
            }
            n = -1;
        }
        this.nextChar = n;
    }

    @Deprecated
    public String decomposition() {
        String string = this.decompBuf;
        if (string != null) {
            return string;
        }
        return "";
    }

    @Deprecated
    public boolean hasNext() {
        if (this.nextChar == -1) {
            this.findNextChar();
        }
        boolean bl = this.nextChar != -1;
        return bl;
    }

    @Deprecated
    public char next() {
        if (this.nextChar == -1) {
            this.findNextChar();
        }
        this.curChar = this.nextChar;
        this.nextChar = -1;
        return (char)this.curChar;
    }
}

