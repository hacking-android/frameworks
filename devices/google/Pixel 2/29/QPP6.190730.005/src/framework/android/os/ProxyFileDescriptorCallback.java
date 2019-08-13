/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.OsConstants
 */
package android.os;

import android.system.ErrnoException;
import android.system.OsConstants;

public abstract class ProxyFileDescriptorCallback {
    public void onFsync() throws ErrnoException {
        throw new ErrnoException("onFsync", OsConstants.EINVAL);
    }

    public long onGetSize() throws ErrnoException {
        throw new ErrnoException("onGetSize", OsConstants.EBADF);
    }

    public int onRead(long l, int n, byte[] arrby) throws ErrnoException {
        throw new ErrnoException("onRead", OsConstants.EBADF);
    }

    public abstract void onRelease();

    public int onWrite(long l, int n, byte[] arrby) throws ErrnoException {
        throw new ErrnoException("onWrite", OsConstants.EBADF);
    }
}

