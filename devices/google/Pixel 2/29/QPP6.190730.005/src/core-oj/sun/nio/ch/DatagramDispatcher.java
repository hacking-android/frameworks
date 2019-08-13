/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.BlockGuard
 */
package sun.nio.ch;

import dalvik.system.BlockGuard;
import java.io.FileDescriptor;
import java.io.IOException;
import sun.nio.ch.FileDispatcherImpl;
import sun.nio.ch.NativeDispatcher;

class DatagramDispatcher
extends NativeDispatcher {
    DatagramDispatcher() {
    }

    static native int read0(FileDescriptor var0, long var1, int var3) throws IOException;

    static native long readv0(FileDescriptor var0, long var1, int var3) throws IOException;

    static native int write0(FileDescriptor var0, long var1, int var3) throws IOException;

    static native long writev0(FileDescriptor var0, long var1, int var3) throws IOException;

    @Override
    void close(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.close0(fileDescriptor);
    }

    @Override
    void preClose(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.preClose0(fileDescriptor);
    }

    @Override
    int read(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onNetwork();
        return DatagramDispatcher.read0(fileDescriptor, l, n);
    }

    @Override
    long readv(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onNetwork();
        return DatagramDispatcher.readv0(fileDescriptor, l, n);
    }

    @Override
    int write(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onNetwork();
        return DatagramDispatcher.write0(fileDescriptor, l, n);
    }

    @Override
    long writev(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onNetwork();
        return DatagramDispatcher.writev0(fileDescriptor, l, n);
    }
}

