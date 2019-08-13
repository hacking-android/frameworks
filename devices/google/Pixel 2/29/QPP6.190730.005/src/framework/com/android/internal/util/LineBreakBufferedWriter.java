/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Arrays;

public class LineBreakBufferedWriter
extends PrintWriter {
    private char[] buffer;
    private int bufferIndex;
    private final int bufferSize;
    private int lastNewline = -1;
    private final String lineSeparator;

    public LineBreakBufferedWriter(Writer writer, int n) {
        this(writer, n, 16);
    }

    public LineBreakBufferedWriter(Writer writer, int n, int n2) {
        super(writer);
        this.buffer = new char[Math.min(n2, n)];
        this.bufferIndex = 0;
        this.bufferSize = n;
        this.lineSeparator = System.getProperty("line.separator");
    }

    private void appendToBuffer(String string2, int n, int n2) {
        int n3 = this.bufferIndex;
        if (n3 + n2 > this.buffer.length) {
            this.ensureCapacity(n3 + n2);
        }
        string2.getChars(n, n + n2, this.buffer, this.bufferIndex);
        this.bufferIndex += n2;
    }

    private void appendToBuffer(char[] arrc, int n, int n2) {
        int n3 = this.bufferIndex;
        if (n3 + n2 > this.buffer.length) {
            this.ensureCapacity(n3 + n2);
        }
        System.arraycopy(arrc, n, this.buffer, this.bufferIndex, n2);
        this.bufferIndex += n2;
    }

    private void ensureCapacity(int n) {
        int n2;
        int n3 = n2 = this.buffer.length * 2 + 2;
        if (n2 < n) {
            n3 = n;
        }
        this.buffer = Arrays.copyOf(this.buffer, n3);
    }

    private void removeFromBuffer(int n) {
        int n2 = this.bufferIndex;
        if ((n = n2 - n) > 0) {
            char[] arrc = this.buffer;
            System.arraycopy(arrc, n2 - n, arrc, 0, n);
            this.bufferIndex = n;
        } else {
            this.bufferIndex = 0;
        }
    }

    private void writeBuffer(int n) {
        if (n > 0) {
            super.write(this.buffer, 0, n);
        }
    }

    @Override
    public void flush() {
        this.writeBuffer(this.bufferIndex);
        this.bufferIndex = 0;
        super.flush();
    }

    @Override
    public void println() {
        this.write(this.lineSeparator);
    }

    @Override
    public void write(int n) {
        int n2 = this.bufferIndex;
        char[] arrc = this.buffer;
        if (n2 < arrc.length) {
            arrc[n2] = (char)n;
            this.bufferIndex = n2 + 1;
            if ((char)n == '\n') {
                this.lastNewline = this.bufferIndex;
            }
        } else {
            this.write(new char[]{(char)n}, 0, 1);
        }
    }

    @Override
    public void write(String string2, int n, int n2) {
        int n3;
        int n4;
        int n5 = n;
        while ((n3 = this.bufferIndex) + n2 > (n4 = this.bufferSize)) {
            int n6 = -1;
            for (n = 0; n < n4 - n3; ++n) {
                int n7 = n6;
                if (string2.charAt(n5 + n) == '\n') {
                    if (this.bufferIndex + n >= this.bufferSize) break;
                    n7 = n;
                }
                n6 = n7;
            }
            if (n6 != -1) {
                this.appendToBuffer(string2, n5, n6);
                this.writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                this.lastNewline = -1;
                n = n5 + (n6 + 1);
                n2 -= n6 + 1;
            } else {
                n = this.lastNewline;
                if (n != -1) {
                    this.writeBuffer(n);
                    this.removeFromBuffer(this.lastNewline + 1);
                    this.lastNewline = -1;
                    n = n5;
                } else {
                    n6 = this.bufferSize - this.bufferIndex;
                    this.appendToBuffer(string2, n5, n6);
                    this.writeBuffer(this.bufferIndex);
                    this.bufferIndex = 0;
                    n = n5 + n6;
                    n2 -= n6;
                }
            }
            n5 = n;
        }
        if (n2 > 0) {
            this.appendToBuffer(string2, n5, n2);
            for (n = n2 - 1; n >= 0; --n) {
                if (string2.charAt(n5 + n) != '\n') continue;
                this.lastNewline = this.bufferIndex - n2 + n;
                break;
            }
        }
    }

    @Override
    public void write(char[] arrc, int n, int n2) {
        int n3;
        int n4;
        int n5 = n;
        while ((n3 = this.bufferIndex) + n2 > (n4 = this.bufferSize)) {
            int n6 = -1;
            for (n = 0; n < n4 - n3; ++n) {
                int n7 = n6;
                if (arrc[n5 + n] == '\n') {
                    if (this.bufferIndex + n >= this.bufferSize) break;
                    n7 = n;
                }
                n6 = n7;
            }
            if (n6 != -1) {
                this.appendToBuffer(arrc, n5, n6);
                this.writeBuffer(this.bufferIndex);
                this.bufferIndex = 0;
                this.lastNewline = -1;
                n = n5 + (n6 + 1);
                n2 -= n6 + 1;
            } else {
                n = this.lastNewline;
                if (n != -1) {
                    this.writeBuffer(n);
                    this.removeFromBuffer(this.lastNewline + 1);
                    this.lastNewline = -1;
                    n = n5;
                } else {
                    n6 = this.bufferSize - this.bufferIndex;
                    this.appendToBuffer(arrc, n5, n6);
                    this.writeBuffer(this.bufferIndex);
                    this.bufferIndex = 0;
                    n = n5 + n6;
                    n2 -= n6;
                }
            }
            n5 = n;
        }
        if (n2 > 0) {
            this.appendToBuffer(arrc, n5, n2);
            for (n = n2 - 1; n >= 0; --n) {
                if (arrc[n5 + n] != '\n') continue;
                this.lastNewline = this.bufferIndex - n2 + n;
                break;
            }
        }
    }
}

