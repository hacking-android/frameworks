/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.os.Handler;
import android.os.IUpdateEngine;
import android.os.IUpdateEngineCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UpdateEngineCallback;

@SystemApi
public class UpdateEngine {
    private static final String TAG = "UpdateEngine";
    private static final String UPDATE_ENGINE_SERVICE = "android.os.UpdateEngineService";
    private IUpdateEngine mUpdateEngine = IUpdateEngine.Stub.asInterface(ServiceManager.getService("android.os.UpdateEngineService"));
    private IUpdateEngineCallback mUpdateEngineCallback = null;
    private final Object mUpdateEngineCallbackLock = new Object();

    public void applyPayload(String string2, long l, long l2, String[] arrstring) {
        try {
            this.mUpdateEngine.applyPayload(string2, l, l2, arrstring);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean bind(UpdateEngineCallback updateEngineCallback) {
        return this.bind(updateEngineCallback, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean bind(final UpdateEngineCallback updateEngineCallback, final Handler handler) {
        Object object = this.mUpdateEngineCallbackLock;
        synchronized (object) {
            IUpdateEngineCallback.Stub stub = new IUpdateEngineCallback.Stub(){

                @Override
                public void onPayloadApplicationComplete(final int n) {
                    Handler handler2 = handler;
                    if (handler2 != null) {
                        handler2.post(new Runnable(){

                            @Override
                            public void run() {
                                updateEngineCallback.onPayloadApplicationComplete(n);
                            }
                        });
                    } else {
                        updateEngineCallback.onPayloadApplicationComplete(n);
                    }
                }

                @Override
                public void onStatusUpdate(final int n, final float f) {
                    Handler handler2 = handler;
                    if (handler2 != null) {
                        handler2.post(new Runnable(){

                            @Override
                            public void run() {
                                updateEngineCallback.onStatusUpdate(n, f);
                            }
                        });
                    } else {
                        updateEngineCallback.onStatusUpdate(n, f);
                    }
                }

            };
            this.mUpdateEngineCallback = stub;
            try {
                return this.mUpdateEngine.bind(this.mUpdateEngineCallback);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void cancel() {
        try {
            this.mUpdateEngine.cancel();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resetStatus() {
        try {
            this.mUpdateEngine.resetStatus();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resume() {
        try {
            this.mUpdateEngine.resume();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void suspend() {
        try {
            this.mUpdateEngine.suspend();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean unbind() {
        Object object = this.mUpdateEngineCallbackLock;
        synchronized (object) {
            if (this.mUpdateEngineCallback == null) {
                return true;
            }
            try {
                boolean bl = this.mUpdateEngine.unbind(this.mUpdateEngineCallback);
                this.mUpdateEngineCallback = null;
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public boolean verifyPayloadMetadata(String string2) {
        try {
            boolean bl = this.mUpdateEngine.verifyPayloadApplicable(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static final class ErrorCodeConstants {
        public static final int DOWNLOAD_PAYLOAD_VERIFICATION_ERROR = 12;
        public static final int DOWNLOAD_TRANSFER_ERROR = 9;
        public static final int ERROR = 1;
        public static final int FILESYSTEM_COPIER_ERROR = 4;
        public static final int INSTALL_DEVICE_OPEN_ERROR = 7;
        public static final int KERNEL_DEVICE_OPEN_ERROR = 8;
        public static final int PAYLOAD_HASH_MISMATCH_ERROR = 10;
        public static final int PAYLOAD_MISMATCHED_TYPE_ERROR = 6;
        public static final int PAYLOAD_SIZE_MISMATCH_ERROR = 11;
        public static final int PAYLOAD_TIMESTAMP_ERROR = 51;
        public static final int POST_INSTALL_RUNNER_ERROR = 5;
        public static final int SUCCESS = 0;
        public static final int UPDATED_BUT_NOT_ACTIVE = 52;
    }

    public static final class UpdateStatusConstants {
        public static final int ATTEMPTING_ROLLBACK = 8;
        public static final int CHECKING_FOR_UPDATE = 1;
        public static final int DISABLED = 9;
        public static final int DOWNLOADING = 3;
        public static final int FINALIZING = 5;
        public static final int IDLE = 0;
        public static final int REPORTING_ERROR_EVENT = 7;
        public static final int UPDATED_NEED_REBOOT = 6;
        public static final int UPDATE_AVAILABLE = 2;
        public static final int VERIFYING = 4;
    }

}

