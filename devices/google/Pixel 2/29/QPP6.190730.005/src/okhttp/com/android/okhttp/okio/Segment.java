/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.okio;

import com.android.okhttp.okio.SegmentPool;

final class Segment {
    static final int SIZE = 8192;
    final byte[] data;
    int limit;
    Segment next;
    boolean owner;
    int pos;
    Segment prev;
    boolean shared;

    Segment() {
        this.data = new byte[8192];
        this.owner = true;
        this.shared = false;
    }

    Segment(Segment segment) {
        this(segment.data, segment.pos, segment.limit);
        segment.shared = true;
    }

    Segment(byte[] arrby, int n, int n2) {
        this.data = arrby;
        this.pos = n;
        this.limit = n2;
        this.owner = false;
        this.shared = true;
    }

    public void compact() {
        Segment segment = this.prev;
        if (segment != this) {
            if (!segment.owner) {
                return;
            }
            int n = this.limit - this.pos;
            int n2 = segment.limit;
            int n3 = segment.shared ? 0 : segment.pos;
            if (n > 8192 - n2 + n3) {
                return;
            }
            this.writeTo(this.prev, n);
            this.pop();
            SegmentPool.recycle(this);
            return;
        }
        throw new IllegalStateException();
    }

    public Segment pop() {
        Segment segment = this.next;
        if (segment == this) {
            segment = null;
        }
        Segment segment2 = this.prev;
        segment2.next = this.next;
        this.next.prev = segment2;
        this.next = null;
        this.prev = null;
        return segment;
    }

    public Segment push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        this.next.prev = segment;
        this.next = segment;
        return segment;
    }

    public Segment split(int n) {
        if (n > 0 && n <= this.limit - this.pos) {
            Segment segment = new Segment(this);
            segment.limit = segment.pos + n;
            this.pos += n;
            this.prev.push(segment);
            return segment;
        }
        throw new IllegalArgumentException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void writeTo(Segment segment, int n) {
        if (!segment.owner) throw new IllegalArgumentException();
        int n2 = segment.limit;
        if (n2 + n > 8192) {
            if (segment.shared) throw new IllegalArgumentException();
            int n3 = segment.pos;
            if (n2 + n - n3 > 8192) throw new IllegalArgumentException();
            byte[] arrby = segment.data;
            System.arraycopy((byte[])arrby, (int)n3, (byte[])arrby, (int)0, (int)(n2 - n3));
            segment.limit -= segment.pos;
            segment.pos = 0;
        }
        System.arraycopy((byte[])this.data, (int)this.pos, (byte[])segment.data, (int)segment.limit, (int)n);
        segment.limit += n;
        this.pos += n;
    }
}

