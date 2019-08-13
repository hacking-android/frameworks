/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.wifi.rtt.IRttCallback;
import android.net.wifi.rtt.IWifiRttManager;
import android.net.wifi.rtt.RangingRequest;
import android.net.wifi.rtt.RangingResult;
import android.net.wifi.rtt.RangingResultCallback;
import android.net.wifi.rtt._$$Lambda$WifiRttManager$1$3uT7vOEOvW11feiFUB6LWvcBJEk;
import android.net.wifi.rtt._$$Lambda$WifiRttManager$1$j3tVizFtxt_z0tTXfTNSFM4Loi8;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.WorkSource;
import java.util.List;
import java.util.concurrent.Executor;

public class WifiRttManager {
    public static final String ACTION_WIFI_RTT_STATE_CHANGED = "android.net.wifi.rtt.action.WIFI_RTT_STATE_CHANGED";
    private static final String TAG = "WifiRttManager";
    private static final boolean VDBG = false;
    private final Context mContext;
    private final IWifiRttManager mService;

    public WifiRttManager(Context context, IWifiRttManager iWifiRttManager) {
        this.mContext = context;
        this.mService = iWifiRttManager;
    }

    @SystemApi
    public void cancelRanging(WorkSource workSource) {
        try {
            this.mService.cancelRanging(workSource);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isAvailable() {
        try {
            boolean bl = this.mService.isAvailable();
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startRanging(RangingRequest rangingRequest, Executor executor, RangingResultCallback rangingResultCallback) {
        this.startRanging(null, rangingRequest, executor, rangingResultCallback);
    }

    @SystemApi
    public void startRanging(WorkSource workSource, RangingRequest rangingRequest, final Executor executor, final RangingResultCallback rangingResultCallback) {
        if (executor != null) {
            if (rangingResultCallback != null) {
                Binder binder = new Binder();
                try {
                    IWifiRttManager iWifiRttManager = this.mService;
                    String string2 = this.mContext.getOpPackageName();
                    IRttCallback.Stub stub = new IRttCallback.Stub(){

                        static /* synthetic */ void lambda$onRangingFailure$0(RangingResultCallback rangingResultCallback2, int n) {
                            rangingResultCallback2.onRangingFailure(n);
                        }

                        static /* synthetic */ void lambda$onRangingResults$1(RangingResultCallback rangingResultCallback2, List list) {
                            rangingResultCallback2.onRangingResults(list);
                        }

                        @Override
                        public void onRangingFailure(int n) throws RemoteException {
                            1.clearCallingIdentity();
                            executor.execute(new _$$Lambda$WifiRttManager$1$j3tVizFtxt_z0tTXfTNSFM4Loi8(rangingResultCallback, n));
                        }

                        @Override
                        public void onRangingResults(List<RangingResult> list) throws RemoteException {
                            1.clearCallingIdentity();
                            executor.execute(new _$$Lambda$WifiRttManager$1$3uT7vOEOvW11feiFUB6LWvcBJEk(rangingResultCallback, list));
                        }
                    };
                    iWifiRttManager.startRanging(binder, string2, workSource, rangingRequest, stub);
                    return;
                }
                catch (RemoteException remoteException) {
                    throw remoteException.rethrowFromSystemServer();
                }
            }
            throw new IllegalArgumentException("Null callback provided");
        }
        throw new IllegalArgumentException("Null executor provided");
    }

}

