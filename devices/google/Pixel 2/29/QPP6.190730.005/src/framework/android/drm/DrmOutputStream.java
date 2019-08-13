/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  libcore.io.IoBridge
 *  libcore.io.Streams
 *  libcore.util.ArrayUtils
 */
package android.drm;

import android.drm.DrmConvertedStatus;
import android.drm.DrmManagerClient;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownServiceException;
import libcore.io.IoBridge;
import libcore.io.Streams;
import libcore.util.ArrayUtils;

public class DrmOutputStream
extends OutputStream {
    private static final String TAG = "DrmOutputStream";
    private final DrmManagerClient mClient;
    private final FileDescriptor mFd;
    private final ParcelFileDescriptor mPfd;
    private int mSessionId = -1;

    public DrmOutputStream(DrmManagerClient object, ParcelFileDescriptor parcelFileDescriptor, String string2) throws IOException {
        this.mClient = object;
        this.mPfd = parcelFileDescriptor;
        this.mFd = parcelFileDescriptor.getFileDescriptor();
        this.mSessionId = this.mClient.openConvertSession(string2);
        if (this.mSessionId != -1) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to open DRM session for ");
        ((StringBuilder)object).append(string2);
        throw new UnknownServiceException(((StringBuilder)object).toString());
    }

    @Override
    public void close() throws IOException {
        if (this.mSessionId == -1) {
            Log.w(TAG, "Closing stream without finishing");
        }
        this.mPfd.close();
    }

    public void finish() throws IOException {
        DrmConvertedStatus drmConvertedStatus = this.mClient.closeConvertSession(this.mSessionId);
        if (drmConvertedStatus.statusCode == 1) {
            try {
                Os.lseek((FileDescriptor)this.mFd, (long)drmConvertedStatus.offset, (int)OsConstants.SEEK_SET);
            }
            catch (ErrnoException errnoException) {
                errnoException.rethrowAsIOException();
            }
            IoBridge.write((FileDescriptor)this.mFd, (byte[])drmConvertedStatus.convertedData, (int)0, (int)drmConvertedStatus.convertedData.length);
            this.mSessionId = -1;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected DRM status: ");
        stringBuilder.append(drmConvertedStatus.statusCode);
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void write(int n) throws IOException {
        Streams.writeSingleByte((OutputStream)this, (int)n);
    }

    @Override
    public void write(byte[] object, int n, int n2) throws IOException {
        Object object2;
        ArrayUtils.throwsIfOutOfBounds((int)((byte[])object).length, (int)n, (int)n2);
        if (n2 != ((byte[])object).length) {
            object2 = new byte[n2];
            System.arraycopy(object, n, object2, 0, n2);
            object = object2;
        }
        object = this.mClient.convertData(this.mSessionId, (byte[])object);
        if (object.statusCode == 1) {
            IoBridge.write((FileDescriptor)this.mFd, (byte[])object.convertedData, (int)0, (int)object.convertedData.length);
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Unexpected DRM status: ");
        ((StringBuilder)object2).append(object.statusCode);
        throw new IOException(((StringBuilder)object2).toString());
    }
}

