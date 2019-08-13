/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

abstract class NativeDispatcher {
    NativeDispatcher() {
    }

    abstract void close(FileDescriptor var1) throws IOException;

    boolean needsPositionLock() {
        return false;
    }

    void preClose(FileDescriptor fileDescriptor) throws IOException {
    }

    int pread(FileDescriptor fileDescriptor, long l, int n, long l2) throws IOException {
        throw new IOException("Operation Unsupported");
    }

    int pwrite(FileDescriptor fileDescriptor, long l, int n, long l2) throws IOException {
        throw new IOException("Operation Unsupported");
    }

    abstract int read(FileDescriptor var1, long var2, int var4) throws IOException;

    abstract long readv(FileDescriptor var1, long var2, int var4) throws IOException;

    abstract int write(FileDescriptor var1, long var2, int var4) throws IOException;

    abstract long writev(FileDescriptor var1, long var2, int var4) throws IOException;
}

