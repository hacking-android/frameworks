/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 */
package android.os;

import android.annotation.SystemApi;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;

@SystemApi
public final class NativeHandle
implements Closeable {
    private FileDescriptor[] mFds;
    private int[] mInts;
    private boolean mOwn = false;

    public NativeHandle() {
        this(new FileDescriptor[0], new int[0], false);
    }

    public NativeHandle(FileDescriptor fileDescriptor, boolean bl) {
        this(new FileDescriptor[]{fileDescriptor}, new int[0], bl);
    }

    private NativeHandle(int[] arrn, int[] arrn2, boolean bl) {
        this(NativeHandle.createFileDescriptorArray(arrn), arrn2, bl);
    }

    public NativeHandle(FileDescriptor[] arrfileDescriptor, int[] arrn, boolean bl) {
        this.mFds = (FileDescriptor[])arrfileDescriptor.clone();
        this.mInts = (int[])arrn.clone();
        this.mOwn = bl;
    }

    private void checkOpen() {
        if (this.mFds != null) {
            return;
        }
        throw new IllegalStateException("NativeHandle is invalidated after close.");
    }

    private static FileDescriptor[] createFileDescriptorArray(int[] arrn) {
        FileDescriptor[] arrfileDescriptor = new FileDescriptor[arrn.length];
        for (int i = 0; i < arrn.length; ++i) {
            FileDescriptor fileDescriptor = new FileDescriptor();
            fileDescriptor.setInt$(arrn[i]);
            arrfileDescriptor[i] = fileDescriptor;
        }
        return arrfileDescriptor;
    }

    private int[] getFdsAsIntArray() {
        this.checkOpen();
        int n = this.mFds.length;
        int[] arrn = new int[n];
        for (int i = 0; i < n; ++i) {
            arrn[i] = this.mFds[i].getInt$();
        }
        return arrn;
    }

    @Override
    public void close() throws IOException {
        this.checkOpen();
        if (this.mOwn) {
            FileDescriptor[] arrfileDescriptor = this.mFds;
            int n = arrfileDescriptor.length;
            for (int i = 0; i < n; ++i) {
                try {
                    Os.close((FileDescriptor)arrfileDescriptor[i]);
                    continue;
                }
                catch (ErrnoException errnoException) {
                    errnoException.rethrowAsIOException();
                    break;
                }
            }
            this.mOwn = false;
        }
        this.mFds = null;
        this.mInts = null;
    }

    public NativeHandle dup() throws IOException {
        FileDescriptor[] arrfileDescriptor = new FileDescriptor[this.mFds.length];
        int n = 0;
        do {
            try {
                if (n >= this.mFds.length) break;
                FileDescriptor fileDescriptor = new FileDescriptor();
                fileDescriptor.setInt$(Os.fcntlInt((FileDescriptor)this.mFds[n], (int)OsConstants.F_DUPFD_CLOEXEC, (int)0));
                arrfileDescriptor[n] = fileDescriptor;
                ++n;
            }
            catch (ErrnoException errnoException) {
                errnoException.rethrowAsIOException();
                break;
            }
        } while (true);
        return new NativeHandle(arrfileDescriptor, this.mInts, true);
    }

    public FileDescriptor getFileDescriptor() {
        this.checkOpen();
        if (this.hasSingleFileDescriptor()) {
            return this.mFds[0];
        }
        throw new IllegalStateException("NativeHandle is not single file descriptor. Contents must be retreived through getFileDescriptors and getInts.");
    }

    public FileDescriptor[] getFileDescriptors() {
        this.checkOpen();
        return this.mFds;
    }

    public int[] getInts() {
        this.checkOpen();
        return this.mInts;
    }

    public boolean hasSingleFileDescriptor() {
        this.checkOpen();
        int n = this.mFds.length;
        boolean bl = true;
        if (n != 1 || this.mInts.length != 0) {
            bl = false;
        }
        return bl;
    }
}

