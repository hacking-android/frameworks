/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.dalvik.ddmc;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.nio.ByteBuffer;

public class Chunk {
    public byte[] data;
    public int length;
    public int offset;
    public int type;

    public Chunk() {
    }

    @UnsupportedAppUsage
    public Chunk(int n, ByteBuffer byteBuffer) {
        this.type = n;
        this.data = byteBuffer.array();
        this.offset = byteBuffer.arrayOffset();
        this.length = byteBuffer.position();
    }

    public Chunk(int n, byte[] arrby, int n2, int n3) {
        this.type = n;
        this.data = arrby;
        this.offset = n2;
        this.length = n3;
    }
}

