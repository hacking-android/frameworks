/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  android.system.StructStat
 *  libcore.io.IoUtils
 */
package android.os;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.os.ProxyFileDescriptorCallback;
import android.os.storage.StorageManager;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.system.StructStat;
import android.util.Slog;
import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InterruptedIOException;
import libcore.io.IoUtils;

public class RevocableFileDescriptor {
    private static final boolean DEBUG = true;
    private static final String TAG = "RevocableFileDescriptor";
    private final ProxyFileDescriptorCallback mCallback = new ProxyFileDescriptorCallback(){

        private void checkRevoked() throws ErrnoException {
            if (!RevocableFileDescriptor.this.mRevoked) {
                return;
            }
            throw new ErrnoException(RevocableFileDescriptor.TAG, OsConstants.EPERM);
        }

        @Override
        public void onFsync() throws ErrnoException {
            Slog.v(RevocableFileDescriptor.TAG, "onFsync()");
            this.checkRevoked();
            Os.fsync((FileDescriptor)RevocableFileDescriptor.this.mInner);
        }

        @Override
        public long onGetSize() throws ErrnoException {
            this.checkRevoked();
            return Os.fstat((FileDescriptor)RevocableFileDescriptor.access$100((RevocableFileDescriptor)RevocableFileDescriptor.this)).st_size;
        }

        @Override
        public int onRead(long l, int n, byte[] arrby) throws ErrnoException {
            int n2;
            this.checkRevoked();
            int n3 = 0;
            do {
                n2 = n3;
                if (n3 >= n) break;
                try {
                    n2 = Os.pread((FileDescriptor)RevocableFileDescriptor.this.mInner, (byte[])arrby, (int)n3, (int)(n - n3), (long)(l + (long)n3));
                    n2 = n3 + n2;
                }
                catch (InterruptedIOException interruptedIOException) {
                    n3 += interruptedIOException.bytesTransferred;
                    continue;
                }
                break;
            } while (true);
            return n2;
        }

        @Override
        public void onRelease() {
            Slog.v(RevocableFileDescriptor.TAG, "onRelease()");
            RevocableFileDescriptor.this.mRevoked = true;
            IoUtils.closeQuietly((FileDescriptor)RevocableFileDescriptor.this.mInner);
        }

        @Override
        public int onWrite(long l, int n, byte[] arrby) throws ErrnoException {
            int n2;
            this.checkRevoked();
            int n3 = 0;
            do {
                n2 = n3;
                if (n3 >= n) break;
                try {
                    n2 = Os.pwrite((FileDescriptor)RevocableFileDescriptor.this.mInner, (byte[])arrby, (int)n3, (int)(n - n3), (long)(l + (long)n3));
                    n2 = n3 + n2;
                }
                catch (InterruptedIOException interruptedIOException) {
                    n3 += interruptedIOException.bytesTransferred;
                    continue;
                }
                break;
            } while (true);
            return n2;
        }
    };
    private FileDescriptor mInner;
    private ParcelFileDescriptor mOuter;
    private volatile boolean mRevoked;

    public RevocableFileDescriptor() {
    }

    public RevocableFileDescriptor(Context context, File file) throws IOException {
        try {
            this.init(context, Os.open((String)file.getAbsolutePath(), (int)(OsConstants.O_CREAT | OsConstants.O_RDWR), (int)448));
            return;
        }
        catch (ErrnoException errnoException) {
            throw errnoException.rethrowAsIOException();
        }
    }

    public RevocableFileDescriptor(Context context, FileDescriptor fileDescriptor) throws IOException {
        this.init(context, fileDescriptor);
    }

    public ParcelFileDescriptor getRevocableFileDescriptor() {
        return this.mOuter;
    }

    public void init(Context context, FileDescriptor fileDescriptor) throws IOException {
        this.mInner = fileDescriptor;
        this.mOuter = context.getSystemService(StorageManager.class).openProxyFileDescriptor(805306368, this.mCallback);
    }

    public boolean isRevoked() {
        return this.mRevoked;
    }

    public void revoke() {
        this.mRevoked = true;
        IoUtils.closeQuietly((FileDescriptor)this.mInner);
    }

}

