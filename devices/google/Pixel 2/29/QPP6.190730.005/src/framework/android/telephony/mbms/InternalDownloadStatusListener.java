/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.DownloadStatusListener;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.IDownloadStatusListener;
import java.util.concurrent.Executor;

public class InternalDownloadStatusListener
extends IDownloadStatusListener.Stub {
    private final DownloadStatusListener mAppListener;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadStatusListener(DownloadStatusListener downloadStatusListener, Executor executor) {
        this.mAppListener = downloadStatusListener;
        this.mExecutor = executor;
    }

    @Override
    public void onStatusUpdated(final DownloadRequest downloadRequest, final FileInfo fileInfo, final int n) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long l = Binder.clearCallingIdentity();
        try {
            Executor executor = this.mExecutor;
            Runnable runnable = new Runnable(){

                @Override
                public void run() {
                    InternalDownloadStatusListener.this.mAppListener.onStatusUpdated(downloadRequest, fileInfo, n);
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

