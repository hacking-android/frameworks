/*
 * Decompiled with CFR 0.145.
 */
package java.nio;

import java.io.FileDescriptor;
import java.nio.Bits;
import java.nio.ByteBuffer;
import sun.misc.Unsafe;

public abstract class MappedByteBuffer
extends ByteBuffer {
    private static byte unused;
    private final FileDescriptor fd;

    MappedByteBuffer(int n, int n2, int n3, int n4) {
        super(n, n2, n3, n4);
        this.fd = null;
    }

    MappedByteBuffer(int n, int n2, int n3, int n4, FileDescriptor fileDescriptor) {
        super(n, n2, n3, n4);
        this.fd = fileDescriptor;
    }

    MappedByteBuffer(int n, int n2, int n3, int n4, byte[] arrby, int n5) {
        super(n, n2, n3, n4, arrby, n5);
        this.fd = null;
    }

    private void checkMapped() {
        if (this.fd != null) {
            return;
        }
        throw new UnsupportedOperationException();
    }

    private native void force0(FileDescriptor var1, long var2, long var4);

    private native boolean isLoaded0(long var1, long var3, int var5);

    private native void load0(long var1, long var3);

    private long mappingAddress(long l) {
        return this.address - l;
    }

    private long mappingLength(long l) {
        return (long)this.capacity() + l;
    }

    private long mappingOffset() {
        int n = Bits.pageSize();
        long l = this.address % (long)n;
        if (l < 0L) {
            l = (long)n + l;
        }
        return l;
    }

    public final MappedByteBuffer force() {
        this.checkMapped();
        if (this.address != 0L && this.capacity() != 0) {
            long l = this.mappingOffset();
            this.force0(this.fd, this.mappingAddress(l), this.mappingLength(l));
        }
        return this;
    }

    public final boolean isLoaded() {
        this.checkMapped();
        if (this.address != 0L && this.capacity() != 0) {
            long l = this.mappingOffset();
            long l2 = this.mappingLength(l);
            return this.isLoaded0(this.mappingAddress(l), l2, Bits.pageCount(l2));
        }
        return true;
    }

    public final MappedByteBuffer load() {
        this.checkMapped();
        if (this.address != 0L && this.capacity() != 0) {
            long l = this.mappingOffset();
            long l2 = this.mappingLength(l);
            this.load0(this.mappingAddress(l), l2);
            Unsafe unsafe = Unsafe.getUnsafe();
            int n = Bits.pageSize();
            int n2 = Bits.pageCount(l2);
            l = this.mappingAddress(l);
            byte by = 0;
            for (int i = 0; i < n2; ++i) {
                by = (byte)(unsafe.getByte(l) ^ by);
                l += (long)n;
            }
            if (unused != 0) {
                unused = (byte)by;
            }
            return this;
        }
        return this;
    }
}

