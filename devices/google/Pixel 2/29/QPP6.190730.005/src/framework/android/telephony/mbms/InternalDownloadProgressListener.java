/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.DownloadProgressListener;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.IDownloadProgressListener;
import java.util.concurrent.Executor;

public class InternalDownloadProgressListener
extends IDownloadProgressListener.Stub {
    private final DownloadProgressListener mAppListener;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadProgressListener(DownloadProgressListener downloadProgressListener, Executor executor) {
        this.mAppListener = downloadProgressListener;
        this.mExecutor = executor;
    }

    @Override
    public void onProgressUpdated(final DownloadRequest downloadRequest, final FileInfo fileInfo, final int n, final int n2, final int n3, final int n4) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalDownloadProgressListener.this.mAppListener.onProgressUpdated(downloadRequest, fileInfo, n, n2, n3, n4);
                }
            };
            executor.execute(runnable);
            return;
        }
        finally {
            Binder.restoreCallingIdentity(l);
        }
    }

    public void stop() {
        this.mIsStopped = true;
    }

}

