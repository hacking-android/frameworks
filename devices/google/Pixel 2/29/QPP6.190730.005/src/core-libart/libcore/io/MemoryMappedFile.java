/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import android.system.ErrnoException;
import android.system.OsConstants;
import android.system.StructStat;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.FileDescriptor;
import java.nio.ByteOrder;
import libcore.io.BufferIterator;
import libcore.io.Libcore;
import libcore.io.NioBufferIterator;
import libcore.io.Os;

public final class MemoryMappedFile
implements AutoCloseable {
    private final long address;
    private boolean closed;
    private final int size;

    public MemoryMappedFile(long l, long l2) {
        this.address = l;
        if (l2 >= 0L && l2 <= Integer.MAX_VALUE) {
            this.size = (int)l2;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported file size=");
        stringBuilder.append(l2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public static MemoryMappedFile mmapRO(String object) throws ErrnoException {
        object = Libcore.os.open((String)object, OsConstants.O_RDONLY, 0);
        try {
            long l = Libcore.os.fstat((FileDescriptor)object).st_size;
            MemoryMappedFile memoryMappedFile = new MemoryMappedFile(Libcore.os.mmap(0L, l, OsConstants.PROT_READ, OsConstants.MAP_SHARED, (FileDescriptor)object, 0L), l);
            return memoryMappedFile;
        }
        finally {
            Libcore.os.close((FileDescriptor)object);
        }
    }

    @UnsupportedAppUsage
    public BufferIterator bigEndianIterator() {
        long l = this.address;
        int n = this.size;
        boolean bl = ByteOrder.nativeOrder() != ByteOrder.BIG_ENDIAN;
        return new NioBufferIterator(this, l, n, bl);
    }

    void checkNotClosed() {
        if (!this.closed) {
            return;
        }
        throw new IllegalStateException("MemoryMappedFile is closed");
    }

    @Override
    public void close() throws ErrnoException {
        if (!this.closed) {
            this.closed = true;
            Libcore.os.munmap(this.address, this.size);
        }
    }

    public boolean isClosed() {
        return this.closed;
    }

    public BufferIterator littleEndianIterator() {
        long l = this.address;
        int n = this.size;
        boolean bl = ByteOrder.nativeOrder() != ByteOrder.LITTLE_ENDIAN;
        return new NioBufferIterator(this, l, n, bl);
    }

    public int size() {
        this.checkNotClosed();
        return this.size;
    }
}

