/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.CellInfo;
import android.telephony.NetworkScan;
import android.telephony.NetworkScanRequest;
import android.telephony.Rlog;
import android.telephony._$$Lambda$TelephonyScanManager$1$X9SMshZoHjJ6SzCbmgVMwQip2Q0;
import android.telephony._$$Lambda$TelephonyScanManager$1$jmXulbd8FzO5Qb8_Hi_Z6s_nJWI;
import android.telephony._$$Lambda$TelephonyScanManager$1$tGSpVQaVhc4GKIxjcECV_jCGYw4;
import android.util.SparseArray;
import com.android.internal.telephony.ITelephony;
import com.android.internal.util.Preconditions;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public final class TelephonyScanManager {
    public static final int CALLBACK_RESTRICTED_SCAN_RESULTS = 4;
    public static final int CALLBACK_SCAN_COMPLETE = 3;
    public static final int CALLBACK_SCAN_ERROR = 2;
    public static final int CALLBACK_SCAN_RESULTS = 1;
    public static final int INVALID_SCAN_ID = -1;
    public static final String SCAN_RESULT_KEY = "scanResult";
    private static final String TAG = "TelephonyScanManager";
    private final Looper mLooper;
    private final Messenger mMessenger;
    private SparseArray<NetworkScanInfo> mScanInfo = new SparseArray();

    public TelephonyScanManager() {
        HandlerThread handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        this.mLooper = handlerThread.getLooper();
        this.mMessenger = new Messenger(new Handler(this.mLooper){

            static /* synthetic */ void lambda$handleMessage$0(CellInfo[] arrcellInfo, NetworkScanCallback networkScanCallback) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onResults: ");
                stringBuilder.append(arrcellInfo.toString());
                Rlog.d(TelephonyScanManager.TAG, stringBuilder.toString());
                networkScanCallback.onResults(Arrays.asList(arrcellInfo));
            }

            static /* synthetic */ void lambda$handleMessage$1(int n, NetworkScanCallback networkScanCallback) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onError: ");
                stringBuilder.append(n);
                Rlog.d(TelephonyScanManager.TAG, stringBuilder.toString());
                networkScanCallback.onError(n);
            }

            static /* synthetic */ void lambda$handleMessage$2(NetworkScanCallback networkScanCallback) {
                Rlog.d(TelephonyScanManager.TAG, "onComplete");
                networkScanCallback.onComplete();
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void handleMessage(Message var1_1) {
                block17 : {
                    Preconditions.checkNotNull(var1_1, "message cannot be null");
                    var2_5 = TelephonyScanManager.access$000(TelephonyScanManager.this);
                    // MONITORENTER : var2_5
                    var3_6 = (NetworkScanInfo)TelephonyScanManager.access$000(TelephonyScanManager.this).get(var1_1.arg2);
                    // MONITOREXIT : var2_5
                    if (var3_6 == null) {
                        var2_5 = new StringBuilder();
                        var2_5.append("Failed to find NetworkScanInfo with id ");
                        var2_5.append(var1_1.arg2);
                        throw new RuntimeException(var2_5.toString());
                    }
                    var2_5 = NetworkScanInfo.access$100((NetworkScanInfo)var3_6);
                    var3_6 = NetworkScanInfo.access$200((NetworkScanInfo)var3_6);
                    if (var2_5 == null) {
                        var2_5 = new StringBuilder();
                        var2_5.append("Failed to find NetworkScanCallback with id ");
                        var2_5.append(var1_1.arg2);
                        throw new RuntimeException(var2_5.toString());
                    }
                    if (var3_6 == null) {
                        var2_5 = new StringBuilder();
                        var2_5.append("Failed to find Executor with id ");
                        var2_5.append(var1_1.arg2);
                        throw new RuntimeException(var2_5.toString());
                    }
                    var4_7 = var1_1.what;
                    if (var4_7 == 1) break block17;
                    if (var4_7 == 2) ** GOTO lbl53
                    if (var4_7 != 3) {
                        if (var4_7 != 4) {
                            var2_5 = new StringBuilder();
                            var2_5.append("Unhandled message ");
                            var2_5.append(Integer.toHexString(var1_1.what));
                            Rlog.e("TelephonyScanManager", var2_5.toString());
                            return;
                        }
                    } else {
                        try {
                            var5_8 = new _$$Lambda$TelephonyScanManager$1$tGSpVQaVhc4GKIxjcECV_jCGYw4((NetworkScanCallback)var2_5);
                            var3_6.execute(var5_8);
                            TelephonyScanManager.access$000(TelephonyScanManager.this).remove(var1_1.arg2);
                            return;
                        }
                        catch (Exception var1_2) {
                            Rlog.e("TelephonyScanManager", "Exception in networkscan callback onComplete", var1_2);
                            return;
                        }
lbl53: // 1 sources:
                        try {
                            var4_7 = var1_1.arg1;
                            var1_1 = new _$$Lambda$TelephonyScanManager$1$X9SMshZoHjJ6SzCbmgVMwQip2Q0(var4_7, (NetworkScanCallback)var2_5);
                            var3_6.execute((Runnable)var1_1);
                            return;
                        }
                        catch (Exception var1_3) {
                            Rlog.e("TelephonyScanManager", "Exception in networkscan callback onError", var1_3);
                            return;
                        }
                    }
                }
                try {
                    var5_9 /* !! */  = var1_1.getData().getParcelableArray("scanResult");
                    var1_1 = new CellInfo[var5_9 /* !! */ .length];
                    for (var4_7 = 0; var4_7 < var5_9 /* !! */ .length; ++var4_7) {
                        var1_1[var4_7] = (CellInfo)var5_9 /* !! */ [var4_7];
                    }
                    var5_9 /* !! */  = new _$$Lambda$TelephonyScanManager$1$jmXulbd8FzO5Qb8_Hi_Z6s_nJWI(var1_1, (NetworkScanCallback)var2_5);
                    var3_6.execute((Runnable)var5_9 /* !! */ );
                    return;
                }
                catch (Exception var1_4) {
                    Rlog.e("TelephonyScanManager", "Exception in networkscan callback onResults", var1_4);
                }
            }
        });
    }

    static /* synthetic */ SparseArray access$000(TelephonyScanManager telephonyScanManager) {
        return telephonyScanManager.mScanInfo;
    }

    private ITelephony getITelephony() {
        return ITelephony.Stub.asInterface(ServiceManager.getService("phone"));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void saveScanInfo(int n, NetworkScanRequest networkScanRequest, Executor executor, NetworkScanCallback networkScanCallback) {
        SparseArray<NetworkScanInfo> sparseArray = this.mScanInfo;
        synchronized (sparseArray) {
            SparseArray<NetworkScanInfo> sparseArray2 = this.mScanInfo;
            NetworkScanInfo networkScanInfo = new NetworkScanInfo(networkScanRequest, executor, networkScanCallback);
            sparseArray2.put(n, networkScanInfo);
            return;
        }
    }

    public NetworkScan requestNetworkScan(int n, NetworkScanRequest object, Executor executor, NetworkScanCallback networkScanCallback, String string2) {
        block6 : {
            int n2;
            block7 : {
                ITelephony iTelephony = this.getITelephony();
                if (iTelephony == null) break block6;
                Messenger messenger = this.mMessenger;
                Binder binder = new Binder();
                n2 = iTelephony.requestNetworkScan(n, (NetworkScanRequest)object, messenger, binder, string2);
                if (n2 != -1) break block7;
                Rlog.e(TAG, "Failed to initiate network scan");
                return null;
            }
            try {
                this.saveScanInfo(n2, (NetworkScanRequest)object, executor, networkScanCallback);
                object = new NetworkScan(n2, n);
                return object;
            }
            catch (NullPointerException nullPointerException) {
                Rlog.e(TAG, "requestNetworkScan NPE", nullPointerException);
            }
            catch (RemoteException remoteException) {
                Rlog.e(TAG, "requestNetworkScan RemoteException", remoteException);
            }
        }
        return null;
    }

    public static abstract class NetworkScanCallback {
        public void onComplete() {
        }

        public void onError(int n) {
        }

        public void onResults(List<CellInfo> list) {
        }
    }

    private static class NetworkScanInfo {
        private final NetworkScanCallback mCallback;
        private final Executor mExecutor;
        private final NetworkScanRequest mRequest;

        NetworkScanInfo(NetworkScanRequest networkScanRequest, Executor executor, NetworkScanCallback networkScanCallback) {
            this.mRequest = networkScanRequest;
            this.mExecutor = executor;
            this.mCallback = networkScanCallback;
        }

        static /* synthetic */ NetworkScanCallback access$100(NetworkScanInfo networkScanInfo) {
            return networkScanInfo.mCallback;
        }

        static /* synthetic */ Executor access$200(NetworkScanInfo networkScanInfo) {
            return networkScanInfo.mExecutor;
        }
    }

}

