/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 */
package java.io;

import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import java.io.SyncFailedException;
import sun.misc.JavaIOFileDescriptorAccess;
import sun.misc.SharedSecrets;

public final class FileDescriptor {
    public static final long NO_OWNER = 0L;
    public static final FileDescriptor err;
    public static final FileDescriptor in;
    public static final FileDescriptor out;
    private int descriptor;
    private long ownerId = 0L;

    static {
        in = FileDescriptor.dupFd(0);
        out = FileDescriptor.dupFd(1);
        err = FileDescriptor.dupFd(2);
        SharedSecrets.setJavaIOFileDescriptorAccess(new JavaIOFileDescriptorAccess(){

            @Override
            public int get(FileDescriptor fileDescriptor) {
                return fileDescriptor.descriptor;
            }

            @Override
            public long getHandle(FileDescriptor fileDescriptor) {
                throw new UnsupportedOperationException();
            }

            @Override
            public void set(FileDescriptor fileDescriptor, int n) {
                fileDescriptor.descriptor = n;
            }

            @Override
            public void setHandle(FileDescriptor fileDescriptor, long l) {
                throw new UnsupportedOperationException();
            }
        });
    }

    public FileDescriptor() {
        this.descriptor = -1;
    }

    private FileDescriptor(int n) {
        this.descriptor = n;
    }

    private static FileDescriptor dupFd(int n) {
        try {
            FileDescriptor fileDescriptor = new FileDescriptor(n);
            fileDescriptor = new FileDescriptor(Os.fcntlInt((FileDescriptor)fileDescriptor, (int)OsConstants.F_DUPFD_CLOEXEC, (int)0));
            return fileDescriptor;
        }
        catch (ErrnoException errnoException) {
            throw new RuntimeException(errnoException);
        }
    }

    private static native boolean isSocket(int var0);

    public final int getInt$() {
        return this.descriptor;
    }

    public long getOwnerId$() {
        return this.ownerId;
    }

    public boolean isSocket$() {
        return FileDescriptor.isSocket(this.descriptor);
    }

    public FileDescriptor release$() {
        FileDescriptor fileDescriptor = new FileDescriptor();
        fileDescriptor.descriptor = this.descriptor;
        fileDescriptor.ownerId = this.ownerId;
        this.descriptor = -1;
        this.ownerId = 0L;
        return fileDescriptor;
    }

    public final void setInt$(int n) {
        this.descriptor = n;
    }

    public void setOwnerId$(long l) {
        this.ownerId = l;
    }

    public native void sync() throws SyncFailedException;

    public boolean valid() {
        boolean bl = this.descriptor != -1;
        return bl;
    }

}

