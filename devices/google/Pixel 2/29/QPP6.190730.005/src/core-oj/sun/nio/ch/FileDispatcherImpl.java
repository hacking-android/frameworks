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
import java.nio.channels.SelectableChannel;
import sun.nio.ch.FileDispatcher;

class FileDispatcherImpl
extends FileDispatcher {
    FileDispatcherImpl() {
    }

    FileDispatcherImpl(boolean bl) {
    }

    static native void close0(FileDescriptor var0) throws IOException;

    static native void closeIntFD(int var0) throws IOException;

    static native int force0(FileDescriptor var0, boolean var1) throws IOException;

    static native int lock0(FileDescriptor var0, boolean var1, long var2, long var4, boolean var6) throws IOException;

    static native void preClose0(FileDescriptor var0) throws IOException;

    static native int pread0(FileDescriptor var0, long var1, int var3, long var4) throws IOException;

    static native int pwrite0(FileDescriptor var0, long var1, int var3, long var4) throws IOException;

    static native int read0(FileDescriptor var0, long var1, int var3) throws IOException;

    static native long readv0(FileDescriptor var0, long var1, int var3) throws IOException;

    static native void release0(FileDescriptor var0, long var1, long var3) throws IOException;

    static native long size0(FileDescriptor var0) throws IOException;

    static native int truncate0(FileDescriptor var0, long var1) throws IOException;

    static native int write0(FileDescriptor var0, long var1, int var3) throws IOException;

    static native long writev0(FileDescriptor var0, long var1, int var3) throws IOException;

    @Override
    boolean canTransferToDirectly(SelectableChannel selectableChannel) {
        return true;
    }

    @Override
    void close(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.close0(fileDescriptor);
    }

    @Override
    FileDescriptor duplicateForMapping(FileDescriptor fileDescriptor) {
        return new FileDescriptor();
    }

    @Override
    int force(FileDescriptor fileDescriptor, boolean bl) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return FileDispatcherImpl.force0(fileDescriptor, bl);
    }

    @Override
    int lock(FileDescriptor fileDescriptor, boolean bl, long l, long l2, boolean bl2) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return FileDispatcherImpl.lock0(fileDescriptor, bl, l, l2, bl2);
    }

    @Override
    void preClose(FileDescriptor fileDescriptor) throws IOException {
        FileDispatcherImpl.preClose0(fileDescriptor);
    }

    @Override
    int pread(FileDescriptor fileDescriptor, long l, int n, long l2) throws IOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return FileDispatcherImpl.pread0(fileDescriptor, l, n, l2);
    }

    @Override
    int pwrite(FileDescriptor fileDescriptor, long l, int n, long l2) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return FileDispatcherImpl.pwrite0(fileDescriptor, l, n, l2);
    }

    @Override
    int read(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return FileDispatcherImpl.read0(fileDescriptor, l, n);
    }

    @Override
    long readv(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return FileDispatcherImpl.readv0(fileDescriptor, l, n);
    }

    @Override
    void release(FileDescriptor fileDescriptor, long l, long l2) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        FileDispatcherImpl.release0(fileDescriptor, l, l2);
    }

    @Override
    long size(FileDescriptor fileDescriptor) throws IOException {
        BlockGuard.getThreadPolicy().onReadFromDisk();
        return FileDispatcherImpl.size0(fileDescriptor);
    }

    @Override
    boolean transferToDirectlyNeedsPositionLock() {
        return false;
    }

    @Override
    int truncate(FileDescriptor fileDescriptor, long l) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return FileDispatcherImpl.truncate0(fileDescriptor, l);
    }

    @Override
    int write(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return FileDispatcherImpl.write0(fileDescriptor, l, n);
    }

    @Override
    long writev(FileDescriptor fileDescriptor, long l, int n) throws IOException {
        BlockGuard.getThreadPolicy().onWriteToDisk();
        return FileDispatcherImpl.writev0(fileDescriptor, l, n);
    }
}

