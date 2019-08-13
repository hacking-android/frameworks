/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import android.annotation.UnsupportedAppUsage;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;

public class IndentingPrintWriter
extends PrintWriter {
    private char[] mCurrentIndent;
    private int mCurrentLength;
    private boolean mEmptyLine = true;
    private StringBuilder mIndentBuilder = new StringBuilder();
    private char[] mSingleChar = new char[1];
    private final String mSingleIndent;
    private final int mWrapLength;

    @UnsupportedAppUsage
    public IndentingPrintWriter(Writer writer, String string2) {
        this(writer, string2, -1);
    }

    public IndentingPrintWriter(Writer writer, String string2, int n) {
        super(writer);
        this.mSingleIndent = string2;
        this.mWrapLength = n;
    }

    private void maybeWriteIndent() {
        if (this.mEmptyLine) {
            this.mEmptyLine = false;
            if (this.mIndentBuilder.length() != 0) {
                if (this.mCurrentIndent == null) {
                    this.mCurrentIndent = this.mIndentBuilder.toString().toCharArray();
                }
                char[] arrc = this.mCurrentIndent;
                super.write(arrc, 0, arrc.length);
            }
        }
    }

    @UnsupportedAppUsage
    public IndentingPrintWriter decreaseIndent() {
        this.mIndentBuilder.delete(0, this.mSingleIndent.length());
        this.mCurrentIndent = null;
        return this;
    }

    @UnsupportedAppUsage
    public IndentingPrintWriter increaseIndent() {
        this.mIndentBuilder.append(this.mSingleIndent);
        this.mCurrentIndent = null;
        return this;
    }

    public IndentingPrintWriter printHexPair(String string2, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("=0x");
        stringBuilder.append(Integer.toHexString(n));
        stringBuilder.append(" ");
        this.print(stringBuilder.toString());
        return this;
    }

    public IndentingPrintWriter printPair(String string2, Object object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("=");
        stringBuilder.append(String.valueOf(object));
        stringBuilder.append(" ");
        this.print(stringBuilder.toString());
        return this;
    }

    public IndentingPrintWriter printPair(String string2, Object[] arrobject) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("=");
        stringBuilder.append(Arrays.toString(arrobject));
        stringBuilder.append(" ");
        this.print(stringBuilder.toString());
        return this;
    }

    @Override
    public void println() {
        this.write(10);
    }

    public IndentingPrintWriter setIndent(int n) {
        this.mIndentBuilder.setLength(0);
        for (int i = 0; i < n; ++i) {
            this.increaseIndent();
        }
        return this;
    }

    public IndentingPrintWriter setIndent(String string2) {
        this.mIndentBuilder.setLength(0);
        this.mIndentBuilder.append(string2);
        this.mCurrentIndent = null;
        return this;
    }

    @Override
    public void write(int n) {
        char[] arrc = this.mSingleChar;
        arrc[0] = (char)n;
        this.write(arrc, 0, 1);
    }

    @Override
    public void write(String string2, int n, int n2) {
        char[] arrc = new char[n2];
        string2.getChars(n, n2 - n, arrc, 0);
        this.write(arrc, 0, n2);
    }

    @Override
    public void write(char[] arrc, int n, int n2) {
        int n3 = this.mIndentBuilder.length();
        int n4 = n;
        int n5 = n;
        while (n5 < n + n2) {
            int n6 = n5 + 1;
            int n7 = arrc[n5];
            ++this.mCurrentLength;
            n5 = n4;
            if (n7 == 10) {
                this.maybeWriteIndent();
                super.write(arrc, n4, n6 - n4);
                n5 = n6;
                this.mEmptyLine = true;
                this.mCurrentLength = 0;
            }
            n7 = this.mWrapLength;
            n4 = n5;
            if (n7 > 0) {
                n4 = n5;
                if (this.mCurrentLength >= n7 - n3) {
                    if (!this.mEmptyLine) {
                        super.write(10);
                        this.mEmptyLine = true;
                        this.mCurrentLength = n6 - n5;
                        n4 = n5;
                    } else {
                        this.maybeWriteIndent();
                        super.write(arrc, n5, n6 - n5);
                        super.write(10);
                        this.mEmptyLine = true;
                        n4 = n6;
                        this.mCurrentLength = 0;
                    }
                }
            }
            n5 = n6;
        }
        if (n4 != n5) {
            this.maybeWriteIndent();
            super.write(arrc, n4, n5 - n4);
        }
    }
}

