/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.lowpan.-$
 *  android.net.lowpan.-$$Lambda
 *  android.net.lowpan.-$$Lambda$LowpanScanner
 *  android.net.lowpan.-$$Lambda$LowpanScanner$b0nnjTe02JXonssLsm5Kp4EaFqs
 */
package android.net.lowpan;

import android.net.lowpan.-$;
import android.net.lowpan.ILowpanEnergyScanCallback;
import android.net.lowpan.ILowpanInterface;
import android.net.lowpan.ILowpanNetScanCallback;
import android.net.lowpan.LowpanBeaconInfo;
import android.net.lowpan.LowpanEnergyScanResult;
import android.net.lowpan.LowpanException;
import android.net.lowpan.LowpanProperties;
import android.net.lowpan.LowpanProperty;
import android.net.lowpan._$$Lambda$LowpanScanner$1$47buDsybUOrvvSl0JOZR_FC9ISg;
import android.net.lowpan._$$Lambda$LowpanScanner$1$lUw1npYnRpaO9LS5odGyASQYaic;
import android.net.lowpan._$$Lambda$LowpanScanner$2$GBDCgjndr24KQueHMX2qGNtfLPg;
import android.net.lowpan._$$Lambda$LowpanScanner$2$n8MSb22N9MEsazioSumvyQhW3Z4;
import android.net.lowpan._$$Lambda$LowpanScanner$b0nnjTe02JXonssLsm5Kp4EaFqs;
import android.os.Handler;
import android.os.RemoteException;
import android.os.ServiceSpecificException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LowpanScanner {
    private static final String TAG = LowpanScanner.class.getSimpleName();
    private ILowpanInterface mBinder;
    private Callback mCallback = null;
    private ArrayList<Integer> mChannelMask = null;
    private Handler mHandler = null;
    private int mTxPower = Integer.MAX_VALUE;

    LowpanScanner(ILowpanInterface iLowpanInterface) {
        this.mBinder = iLowpanInterface;
    }

    private Map<String, Object> createScanOptionMap() {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        if (this.mChannelMask != null) {
            LowpanProperties.KEY_CHANNEL_MASK.putInMap(hashMap, this.mChannelMask.stream().mapToInt(_$$Lambda$LowpanScanner$b0nnjTe02JXonssLsm5Kp4EaFqs.INSTANCE).toArray());
        }
        if (this.mTxPower != Integer.MAX_VALUE) {
            LowpanProperties.KEY_MAX_TX_POWER.putInMap(hashMap, this.mTxPower);
        }
        return hashMap;
    }

    static /* synthetic */ int lambda$createScanOptionMap$0(Integer n) {
        return n;
    }

    public void addChannel(int n) {
        if (this.mChannelMask == null) {
            this.mChannelMask = new ArrayList();
        }
        this.mChannelMask.add(n);
    }

    public Collection<Integer> getChannelMask() {
        return (Collection)this.mChannelMask.clone();
    }

    public int getTxPower() {
        return this.mTxPower;
    }

    public void setCallback(Callback callback) {
        this.setCallback(callback, null);
    }

    public void setCallback(Callback callback, Handler handler) {
        synchronized (this) {
            this.mCallback = callback;
            this.mHandler = handler;
            return;
        }
    }

    public void setChannelMask(Collection<Integer> collection) {
        if (collection == null) {
            this.mChannelMask = null;
        } else {
            ArrayList<Integer> arrayList = this.mChannelMask;
            if (arrayList == null) {
                this.mChannelMask = new ArrayList();
            } else {
                arrayList.clear();
            }
            this.mChannelMask.addAll(collection);
        }
    }

    public void setTxPower(int n) {
        this.mTxPower = n;
    }

    public void startEnergyScan() throws LowpanException {
        Map<String, Object> map = this.createScanOptionMap();
        ILowpanEnergyScanCallback.Stub stub = new ILowpanEnergyScanCallback.Stub(){

            static /* synthetic */ void lambda$onEnergyScanFinished$1(Callback callback) {
                callback.onScanFinished();
            }

            static /* synthetic */ void lambda$onEnergyScanResult$0(Callback callback, int n, int n2) {
                if (callback != null) {
                    LowpanEnergyScanResult lowpanEnergyScanResult = new LowpanEnergyScanResult();
                    lowpanEnergyScanResult.setChannel(n);
                    lowpanEnergyScanResult.setMaxRssi(n2);
                    callback.onEnergyScanResult(lowpanEnergyScanResult);
                }
            }

            @Override
            public void onEnergyScanFinished() {
                Object object = LowpanScanner.this.mCallback;
                Handler handler = LowpanScanner.this.mHandler;
                if (object == null) {
                    return;
                }
                object = new _$$Lambda$LowpanScanner$2$n8MSb22N9MEsazioSumvyQhW3Z4((Callback)object);
                if (handler != null) {
                    handler.post((Runnable)object);
                } else {
                    object.run();
                }
            }

            @Override
            public void onEnergyScanResult(int n, int n2) {
                Object object = LowpanScanner.this.mCallback;
                Handler handler = LowpanScanner.this.mHandler;
                if (object == null) {
                    return;
                }
                object = new _$$Lambda$LowpanScanner$2$GBDCgjndr24KQueHMX2qGNtfLPg((Callback)object, n, n2);
                if (handler != null) {
                    handler.post((Runnable)object);
                } else {
                    object.run();
                }
            }
        };
        try {
            this.mBinder.startEnergyScan(map, stub);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void startNetScan() throws LowpanException {
        Map<String, Object> map = this.createScanOptionMap();
        ILowpanNetScanCallback.Stub stub = new ILowpanNetScanCallback.Stub(){

            static /* synthetic */ void lambda$onNetScanBeacon$0(Callback callback, LowpanBeaconInfo lowpanBeaconInfo) {
                callback.onNetScanBeacon(lowpanBeaconInfo);
            }

            static /* synthetic */ void lambda$onNetScanFinished$1(Callback callback) {
                callback.onScanFinished();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onNetScanBeacon(LowpanBeaconInfo object) {
                LowpanScanner lowpanScanner = LowpanScanner.this;
                // MONITORENTER : lowpanScanner
                Callback callback = LowpanScanner.this.mCallback;
                Handler handler = LowpanScanner.this.mHandler;
                // MONITOREXIT : lowpanScanner
                if (callback == null) {
                    return;
                }
                object = new _$$Lambda$LowpanScanner$1$47buDsybUOrvvSl0JOZR_FC9ISg(callback, (LowpanBeaconInfo)object);
                if (handler != null) {
                    handler.post((Runnable)object);
                    return;
                }
                object.run();
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onNetScanFinished() {
                Object object = LowpanScanner.this;
                // MONITORENTER : object
                Callback callback = LowpanScanner.this.mCallback;
                Handler handler = LowpanScanner.this.mHandler;
                // MONITOREXIT : object
                if (callback == null) {
                    return;
                }
                object = new _$$Lambda$LowpanScanner$1$lUw1npYnRpaO9LS5odGyASQYaic(callback);
                if (handler != null) {
                    handler.post((Runnable)object);
                    return;
                }
                object.run();
            }
        };
        try {
            this.mBinder.startNetScan(map, stub);
            return;
        }
        catch (ServiceSpecificException serviceSpecificException) {
            throw LowpanException.rethrowFromServiceSpecificException(serviceSpecificException);
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void stopEnergyScan() {
        try {
            this.mBinder.stopEnergyScan();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public void stopNetScan() {
        try {
            this.mBinder.stopNetScan();
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    public static abstract class Callback {
        public void onEnergyScanResult(LowpanEnergyScanResult lowpanEnergyScanResult) {
        }

        public void onNetScanBeacon(LowpanBeaconInfo lowpanBeaconInfo) {
        }

        public void onScanFinished() {
        }
    }

}

