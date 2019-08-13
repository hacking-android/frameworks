/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ProcFileReader
implements Closeable {
    private final byte[] mBuffer;
    private boolean mLineFinished;
    private final InputStream mStream;
    private int mTail;

    public ProcFileReader(InputStream inputStream) throws IOException {
        this(inputStream, 4096);
    }

    public ProcFileReader(InputStream inputStream, int n) throws IOException {
        this.mStream = inputStream;
        this.mBuffer = new byte[n];
        this.fillBuf();
    }

    private void consumeBuf(int n) throws IOException {
        byte[] arrby = this.mBuffer;
        System.arraycopy(arrby, n, arrby, 0, this.mTail - n);
        this.mTail -= n;
        if (this.mTail == 0) {
            this.fillBuf();
        }
    }

    private int fillBuf() throws IOException {
        byte[] arrby = this.mBuffer;
        int n = arrby.length;
        int n2 = this.mTail;
        if ((n -= n2) != 0) {
            if ((n2 = this.mStream.read(arrby, n2, n)) != -1) {
                this.mTail += n2;
            }
            return n2;
        }
        throw new IOException("attempting to fill already-full buffer");
    }

    private NumberFormatException invalidLong(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid long: ");
        stringBuilder.append(new String(this.mBuffer, 0, n, StandardCharsets.US_ASCII));
        return new NumberFormatException(stringBuilder.toString());
    }

    private int nextTokenIndex() throws IOException {
        if (this.mLineFinished) {
            return -1;
        }
        int n = 0;
        do {
            if (n < this.mTail) {
                byte by = this.mBuffer[n];
                if (by == 10) {
                    this.mLineFinished = true;
                    return n;
                }
                if (by == 32) {
                    return n;
                }
                ++n;
                continue;
            }
            if (this.fillBuf() <= 0) break;
        } while (true);
        throw new ProtocolException("End of stream while looking for token boundary");
    }

    private long parseAndConsumeLong(int n) throws IOException {
        long l;
        byte[] arrby = this.mBuffer;
        int n2 = 0;
        boolean bl = arrby[0] == 45;
        long l2 = l = 0L;
        if (bl) {
            n2 = 1;
            l2 = l;
        }
        while (n2 < n) {
            int n3 = this.mBuffer[n2] - 48;
            if (n3 >= 0 && n3 <= 9) {
                l = 10L * l2 - (long)n3;
                if (l <= l2) {
                    l2 = l;
                    ++n2;
                    continue;
                }
                throw this.invalidLong(n);
            }
            throw this.invalidLong(n);
        }
        this.consumeBuf(n + 1);
        if (!bl) {
            l2 = -l2;
        }
        return l2;
    }

    private String parseAndConsumeString(int n) throws IOException {
        String string2 = new String(this.mBuffer, 0, n, StandardCharsets.US_ASCII);
        this.consumeBuf(n + 1);
        return string2;
    }

    @Override
    public void close() throws IOException {
        this.mStream.close();
    }

    public void finishLine() throws IOException {
        if (this.mLineFinished) {
            this.mLineFinished = false;
            return;
        }
        int n = 0;
        do {
            if (n < this.mTail) {
                if (this.mBuffer[n] == 10) {
                    this.consumeBuf(n + 1);
                    return;
                }
                ++n;
                continue;
            }
            if (this.fillBuf() <= 0) break;
        } while (true);
        throw new ProtocolException("End of stream while looking for line boundary");
    }

    public boolean hasMoreData() {
        boolean bl = this.mTail > 0;
        return bl;
    }

    public int nextInt() throws IOException {
        long l = this.nextLong();
        if (l <= Integer.MAX_VALUE && l >= Integer.MIN_VALUE) {
            return (int)l;
        }
        throw new NumberFormatException("parsed value larger than integer");
    }

    public long nextLong() throws IOException {
        int n = this.nextTokenIndex();
        if (n != -1) {
            return this.parseAndConsumeLong(n);
        }
        throw new ProtocolException("Missing required long");
    }

    public long nextOptionalLong(long l) throws IOException {
        int n = this.nextTokenIndex();
        if (n == -1) {
            return l;
        }
        return this.parseAndConsumeLong(n);
    }

    public String nextString() throws IOException {
        int n = this.nextTokenIndex();
        if (n != -1) {
            return this.parseAndConsumeString(n);
        }
        throw new ProtocolException("Missing required string");
    }
}

