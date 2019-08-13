/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.Buffer;
import com.android.okhttp.okio.BufferedSource;
import com.android.okhttp.okio.InflaterSource;
import com.android.okhttp.okio.Okio;
import com.android.okhttp.okio.Segment;
import com.android.okhttp.okio.Source;
import com.android.okhttp.okio.Timeout;
import java.io.EOFException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Inflater;

public final class GzipSource
implements Source {
    private static final byte FCOMMENT = 4;
    private static final byte FEXTRA = 2;
    private static final byte FHCRC = 1;
    private static final byte FNAME = 3;
    private static final byte SECTION_BODY = 1;
    private static final byte SECTION_DONE = 3;
    private static final byte SECTION_HEADER = 0;
    private static final byte SECTION_TRAILER = 2;
    private final CRC32 crc = new CRC32();
    private final Inflater inflater;
    private final InflaterSource inflaterSource;
    private int section = 0;
    private final BufferedSource source;

    public GzipSource(Source source) {
        if (source != null) {
            this.inflater = new Inflater(true);
            this.source = Okio.buffer(source);
            this.inflaterSource = new InflaterSource(this.source, this.inflater);
            return;
        }
        throw new IllegalArgumentException("source == null");
    }

    private void checkEqual(String string, int n, int n2) throws IOException {
        if (n2 == n) {
            return;
        }
        throw new IOException(String.format("%s: actual 0x%08x != expected 0x%08x", string, n2, n));
    }

    private void consumeHeader() throws IOException {
        long l;
        this.source.require(10L);
        byte by = this.source.buffer().getByte(3L);
        boolean bl = (by >> 1 & 1) == 1;
        if (bl) {
            this.updateCrc(this.source.buffer(), 0L, 10L);
        }
        this.checkEqual("ID1ID2", 8075, this.source.readShort());
        this.source.skip(8L);
        if ((by >> 2 & 1) == 1) {
            this.source.require(2L);
            if (bl) {
                this.updateCrc(this.source.buffer(), 0L, 2L);
            }
            short s = this.source.buffer().readShortLe();
            this.source.require(s);
            if (bl) {
                this.updateCrc(this.source.buffer(), 0L, s);
            }
            this.source.skip(s);
        }
        if ((by >> 3 & 1) == 1) {
            l = this.source.indexOf((byte)0);
            if (l != -1L) {
                if (bl) {
                    this.updateCrc(this.source.buffer(), 0L, l + 1L);
                }
                this.source.skip(l + 1L);
            } else {
                throw new EOFException();
            }
        }
        if ((by >> 4 & 1) == 1) {
            l = this.source.indexOf((byte)0);
            if (l != -1L) {
                if (bl) {
                    this.updateCrc(this.source.buffer(), 0L, l + 1L);
                }
                this.source.skip(1L + l);
            } else {
                throw new EOFException();
            }
        }
        if (bl) {
            this.checkEqual("FHCRC", this.source.readShortLe(), (short)this.crc.getValue());
            this.crc.reset();
        }
    }

    private void consumeTrailer() throws IOException {
        this.checkEqual("CRC", this.source.readIntLe(), (int)this.crc.getValue());
        this.checkEqual("ISIZE", this.source.readIntLe(), this.inflater.getTotalOut());
    }

    private void updateCrc(Buffer object, long l, long l2) {
        int n;
        long l3;
        Object object2;
        object = ((Buffer)object).head;
        do {
            object2 = object;
            l3 = l;
            if (l < (long)(((Segment)object).limit - ((Segment)object).pos)) break;
            l -= (long)(((Segment)object).limit - ((Segment)object).pos);
            object = ((Segment)object).next;
        } while (true);
        for (long i = l2; i > 0L; i -= (long)n) {
            int n2 = (int)((long)((Segment)object2).pos + l3);
            n = (int)Math.min((long)(((Segment)object2).limit - n2), i);
            this.crc.update(((Segment)object2).data, n2, n);
            l3 = 0L;
            object2 = ((Segment)object2).next;
        }
    }

    @Override
    public void close() throws IOException {
        this.inflaterSource.close();
    }

    @Override
    public long read(Buffer object, long l) throws IOException {
        if (l >= 0L) {
            if (l == 0L) {
                return 0L;
            }
            if (this.section == 0) {
                this.consumeHeader();
                this.section = 1;
            }
            if (this.section == 1) {
                long l2 = ((Buffer)object).size;
                if ((l = this.inflaterSource.read((Buffer)object, l)) != -1L) {
                    this.updateCrc((Buffer)object, l2, l);
                    return l;
                }
                this.section = 2;
            }
            if (this.section == 2) {
                this.consumeTrailer();
                this.section = 3;
                if (!this.source.exhausted()) {
                    throw new IOException("gzip finished without exhausting source");
                }
            }
            return -1L;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("byteCount < 0: ");
        ((StringBuilder)object).append(l);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    @Override
    public Timeout timeout() {
        return this.source.timeout();
    }
}

