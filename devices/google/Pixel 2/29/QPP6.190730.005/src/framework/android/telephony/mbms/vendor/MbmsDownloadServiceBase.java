/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms.vendor;

import android.annotation.SystemApi;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.DownloadProgressListener;
import android.telephony.mbms.DownloadRequest;
import android.telephony.mbms.DownloadStatusListener;
import android.telephony.mbms.FileInfo;
import android.telephony.mbms.FileServiceInfo;
import android.telephony.mbms.IDownloadProgressListener;
import android.telephony.mbms.IDownloadStatusListener;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import android.telephony.mbms.MbmsDownloadSessionCallback;
import android.telephony.mbms.vendor.IMbmsDownloadService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SystemApi
public class MbmsDownloadServiceBase
extends IMbmsDownloadService.Stub {
    private final Map<IBinder, IBinder.DeathRecipient> mDownloadCallbackDeathRecipients = new HashMap<IBinder, IBinder.DeathRecipient>();
    private final Map<IBinder, DownloadProgressListener> mDownloadProgressListenerBinderMap = new HashMap<IBinder, DownloadProgressListener>();
    private final Map<IBinder, DownloadStatusListener> mDownloadStatusListenerBinderMap = new HashMap<IBinder, DownloadStatusListener>();

    public int addProgressListener(DownloadRequest downloadRequest, DownloadProgressListener downloadProgressListener) throws RemoteException {
        return 0;
    }

    @Override
    public final int addProgressListener(DownloadRequest object, IDownloadProgressListener iDownloadProgressListener) throws RemoteException {
        final int n = Binder.getCallingUid();
        if (object != null) {
            if (iDownloadProgressListener != null) {
                VendorDownloadProgressListener vendorDownloadProgressListener = new VendorDownloadProgressListener(iDownloadProgressListener, (DownloadRequest)object){
                    final /* synthetic */ DownloadRequest val$downloadRequest;
                    {
                        this.val$downloadRequest = downloadRequest;
                        super(iDownloadProgressListener);
                    }

                    @Override
                    protected void onRemoteException(RemoteException remoteException) {
                        MbmsDownloadServiceBase.this.onAppCallbackDied(n, this.val$downloadRequest.getSubscriptionId());
                    }
                };
                int n2 = this.addProgressListener((DownloadRequest)object, vendorDownloadProgressListener);
                if (n2 == 0) {
                    object = new IBinder.DeathRecipient((DownloadRequest)object, iDownloadProgressListener){
                        final /* synthetic */ DownloadRequest val$downloadRequest;
                        final /* synthetic */ IDownloadProgressListener val$listener;
                        {
                            this.val$downloadRequest = downloadRequest;
                            this.val$listener = iDownloadProgressListener;
                        }

                        @Override
                        public void binderDied() {
                            MbmsDownloadServiceBase.this.onAppCallbackDied(n, this.val$downloadRequest.getSubscriptionId());
                            MbmsDownloadServiceBase.this.mDownloadProgressListenerBinderMap.remove(this.val$listener.asBinder());
                            MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(this.val$listener.asBinder());
                        }
                    };
                    this.mDownloadCallbackDeathRecipients.put(iDownloadProgressListener.asBinder(), (IBinder.DeathRecipient)object);
                    iDownloadProgressListener.asBinder().linkToDeath((IBinder.DeathRecipient)object, 0);
                    this.mDownloadProgressListenerBinderMap.put(iDownloadProgressListener.asBinder(), vendorDownloadProgressListener);
                }
                return n2;
            }
            throw new NullPointerException("Callback must not be null");
        }
        throw new NullPointerException("Download request must not be null");
    }

    public int addStatusListener(DownloadRequest downloadRequest, DownloadStatusListener downloadStatusListener) throws RemoteException {
        return 0;
    }

    @Override
    public final int addStatusListener(DownloadRequest object, IDownloadStatusListener iDownloadStatusListener) throws RemoteException {
        final int n = Binder.getCallingUid();
        if (object != null) {
            if (iDownloadStatusListener != null) {
                VendorDownloadStatusListener vendorDownloadStatusListener = new VendorDownloadStatusListener(iDownloadStatusListener, (DownloadRequest)object){
                    final /* synthetic */ DownloadRequest val$downloadRequest;
                    {
                        this.val$downloadRequest = downloadRequest;
                        super(iDownloadStatusListener);
                    }

                    @Override
                    protected void onRemoteException(RemoteException remoteException) {
                        MbmsDownloadServiceBase.this.onAppCallbackDied(n, this.val$downloadRequest.getSubscriptionId());
                    }
                };
                int n2 = this.addStatusListener((DownloadRequest)object, vendorDownloadStatusListener);
                if (n2 == 0) {
                    object = new IBinder.DeathRecipient((DownloadRequest)object, iDownloadStatusListener){
                        final /* synthetic */ DownloadRequest val$downloadRequest;
                        final /* synthetic */ IDownloadStatusListener val$listener;
                        {
                            this.val$downloadRequest = downloadRequest;
                            this.val$listener = iDownloadStatusListener;
                        }

                        @Override
                        public void binderDied() {
                            MbmsDownloadServiceBase.this.onAppCallbackDied(n, this.val$downloadRequest.getSubscriptionId());
                            MbmsDownloadServiceBase.this.mDownloadStatusListenerBinderMap.remove(this.val$listener.asBinder());
                            MbmsDownloadServiceBase.this.mDownloadCallbackDeathRecipients.remove(this.val$listener.asBinder());
                        }
                    };
                    this.mDownloadCallbackDeathRecipients.put(iDownloadStatusListener.asBinder(), (IBinder.DeathRecipient)object);
                    iDownloadStatusListener.asBinder().linkToDeath((IBinder.DeathRecipient)object, 0);
                    this.mDownloadStatusListenerBinderMap.put(iDownloadStatusListener.asBinder(), vendorDownloadStatusListener);
                }
                return n2;
            }
            throw new NullPointerException("Callback must not be null");
        }
        throw new NullPointerException("Download request must not be null");
    }

    @SystemApi
    @Override
    public IBinder asBinder() {
        return super.asBinder();
    }

    @Override
    public int cancelDownload(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    @Override
    public void dispose(int n) throws RemoteException {
    }

    @Override
    public int download(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    @Override
    public final int initialize(final int n, final IMbmsDownloadSessionCallback iMbmsDownloadSessionCallback) throws RemoteException {
        if (iMbmsDownloadSessionCallback != null) {
            final int n2 = Binder.getCallingUid();
            int n3 = this.initialize(n, new MbmsDownloadSessionCallback(){

                /*
                 * Enabled aggressive block sorting
                 * Enabled unnecessary exception pruning
                 * Enabled aggressive exception aggregation
                 */
                @Override
                public void onError(int n3, String object) {
                    if (n3 != -1) {
                        try {
                            iMbmsDownloadSessionCallback.onError(n3, (String)object);
                            return;
                        }
                        catch (RemoteException remoteException) {}
                    } else {
                        object = new IllegalArgumentException("Middleware cannot send an unknown error.");
                        throw object;
                    }
                    MbmsDownloadServiceBase.this.onAppCallbackDied(n2, n);
                }

                @Override
                public void onFileServicesUpdated(List<FileServiceInfo> list) {
                    try {
                        iMbmsDownloadSessionCallback.onFileServicesUpdated(list);
                    }
                    catch (RemoteException remoteException) {
                        MbmsDownloadServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }

                @Override
                public void onMiddlewareReady() {
                    try {
                        iMbmsDownloadSessionCallback.onMiddlewareReady();
                    }
                    catch (RemoteException remoteException) {
                        MbmsDownloadServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }
            });
            if (n3 == 0) {
                iMbmsDownloadSessionCallback.asBinder().linkToDeath(new IBinder.DeathRecipient(){

                    @Override
                    public void binderDied() {
                        MbmsDownloadServiceBase.this.onAppCallbackDied(n2, n);
                    }
                }, 0);
            }
            return n3;
        }
        throw new NullPointerException("Callback must not be null");
    }

    public int initialize(int n, MbmsDownloadSessionCallback mbmsDownloadSessionCallback) throws RemoteException {
        return 0;
    }

    @Override
    public List<DownloadRequest> listPendingDownloads(int n) throws RemoteException {
        return null;
    }

    public void onAppCallbackDied(int n, int n2) {
    }

    @SystemApi
    @Override
    public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
        return super.onTransact(n, parcel, parcel2, n2);
    }

    public int removeProgressListener(DownloadRequest downloadRequest, DownloadProgressListener downloadProgressListener) throws RemoteException {
        return 0;
    }

    @Override
    public final int removeProgressListener(DownloadRequest downloadRequest, IDownloadProgressListener object) throws RemoteException {
        if (downloadRequest != null) {
            if (object != null) {
                IBinder.DeathRecipient deathRecipient = this.mDownloadCallbackDeathRecipients.remove(object.asBinder());
                if (deathRecipient != null) {
                    object.asBinder().unlinkToDeath(deathRecipient, 0);
                    object = this.mDownloadProgressListenerBinderMap.remove(object.asBinder());
                    if (object != null) {
                        return this.removeProgressListener(downloadRequest, (DownloadProgressListener)object);
                    }
                    throw new IllegalArgumentException("Unknown listener");
                }
                throw new IllegalArgumentException("Unknown listener");
            }
            throw new NullPointerException("Callback must not be null");
        }
        throw new NullPointerException("Download request must not be null");
    }

    public int removeStatusListener(DownloadRequest downloadRequest, DownloadStatusListener downloadStatusListener) throws RemoteException {
        return 0;
    }

    @Override
    public final int removeStatusListener(DownloadRequest downloadRequest, IDownloadStatusListener object) throws RemoteException {
        if (downloadRequest != null) {
            if (object != null) {
                IBinder.DeathRecipient deathRecipient = this.mDownloadCallbackDeathRecipients.remove(object.asBinder());
                if (deathRecipient != null) {
                    object.asBinder().unlinkToDeath(deathRecipient, 0);
                    object = this.mDownloadStatusListenerBinderMap.remove(object.asBinder());
                    if (object != null) {
                        return this.removeStatusListener(downloadRequest, (DownloadStatusListener)object);
                    }
                    throw new IllegalArgumentException("Unknown listener");
                }
                throw new IllegalArgumentException("Unknown listener");
            }
            throw new NullPointerException("Callback must not be null");
        }
        throw new NullPointerException("Download request must not be null");
    }

    @Override
    public int requestDownloadState(DownloadRequest downloadRequest, FileInfo fileInfo) throws RemoteException {
        return 0;
    }

    @Override
    public int requestUpdateFileServices(int n, List<String> list) throws RemoteException {
        return 0;
    }

    @Override
    public int resetDownloadKnowledge(DownloadRequest downloadRequest) throws RemoteException {
        return 0;
    }

    @Override
    public int setTempFileRootDirectory(int n, String string2) throws RemoteException {
        return 0;
    }

    private static abstract class VendorDownloadProgressListener
    extends DownloadProgressListener {
        private final IDownloadProgressListener mListener;

        public VendorDownloadProgressListener(IDownloadProgressListener iDownloadProgressListener) {
            this.mListener = iDownloadProgressListener;
        }

        @Override
        public void onProgressUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int n, int n2, int n3, int n4) {
            try {
                this.mListener.onProgressUpdated(downloadRequest, fileInfo, n, n2, n3, n4);
            }
            catch (RemoteException remoteException) {
                this.onRemoteException(remoteException);
            }
        }

        protected abstract void onRemoteException(RemoteException var1);
    }

    private static abstract class VendorDownloadStatusListener
    extends DownloadStatusListener {
        private final IDownloadStatusListener mListener;

        public VendorDownloadStatusListener(IDownloadStatusListener iDownloadStatusListener) {
            this.mListener = iDownloadStatusListener;
        }

        protected abstract void onRemoteException(RemoteException var1);

        @Override
        public void onStatusUpdated(DownloadRequest downloadRequest, FileInfo fileInfo, int n) {
            try {
                this.mListener.onStatusUpdated(downloadRequest, fileInfo, n);
            }
            catch (RemoteException remoteException) {
                this.onRemoteException(remoteException);
            }
        }
    }

}

