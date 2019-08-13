/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.le.IPeriodicAdvertisingCallback;
import android.bluetooth.le.PeriodicAdvertisingCallback;
import android.bluetooth.le.PeriodicAdvertisingReport;
import android.bluetooth.le.ScanResult;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import java.util.IdentityHashMap;
import java.util.Map;

public final class PeriodicAdvertisingManager {
    private static final int SKIP_MAX = 499;
    private static final int SKIP_MIN = 0;
    private static final int SYNC_STARTING = -1;
    private static final String TAG = "PeriodicAdvertisingManager";
    private static final int TIMEOUT_MAX = 16384;
    private static final int TIMEOUT_MIN = 10;
    private BluetoothAdapter mBluetoothAdapter;
    private final IBluetoothManager mBluetoothManager;
    Map<PeriodicAdvertisingCallback, IPeriodicAdvertisingCallback> mCallbackWrappers;

    public PeriodicAdvertisingManager(IBluetoothManager iBluetoothManager) {
        this.mBluetoothManager = iBluetoothManager;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mCallbackWrappers = new IdentityHashMap<PeriodicAdvertisingCallback, IPeriodicAdvertisingCallback>();
    }

    private IPeriodicAdvertisingCallback wrap(final PeriodicAdvertisingCallback periodicAdvertisingCallback, final Handler handler) {
        return new IPeriodicAdvertisingCallback.Stub(){

            @Override
            public void onPeriodicAdvertisingReport(final PeriodicAdvertisingReport periodicAdvertisingReport) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        periodicAdvertisingCallback.onPeriodicAdvertisingReport(periodicAdvertisingReport);
                    }
                });
            }

            @Override
            public void onSyncEstablished(final int n, final BluetoothDevice bluetoothDevice, final int n2, final int n3, final int n4, final int n5) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        periodicAdvertisingCallback.onSyncEstablished(n, bluetoothDevice, n2, n3, n4, n5);
                        if (n5 != 0) {
                            PeriodicAdvertisingManager.this.mCallbackWrappers.remove(periodicAdvertisingCallback);
                        }
                    }
                });
            }

            @Override
            public void onSyncLost(final int n) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        periodicAdvertisingCallback.onSyncLost(n);
                        PeriodicAdvertisingManager.this.mCallbackWrappers.remove(periodicAdvertisingCallback);
                    }
                });
            }

        };
    }

    public void registerSync(ScanResult scanResult, int n, int n2, PeriodicAdvertisingCallback periodicAdvertisingCallback) {
        this.registerSync(scanResult, n, n2, periodicAdvertisingCallback, null);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void registerSync(ScanResult scanResult, int n, int n2, PeriodicAdvertisingCallback periodicAdvertisingCallback, Handler object) {
        IBluetoothGatt iBluetoothGatt;
        if (periodicAdvertisingCallback == null) throw new IllegalArgumentException("callback can't be null");
        if (scanResult == null) throw new IllegalArgumentException("scanResult can't be null");
        if (scanResult.getAdvertisingSid() == 255) throw new IllegalArgumentException("scanResult must contain a valid sid");
        if (n < 0) throw new IllegalArgumentException("timeout must be between 10 and 16384");
        if (n > 499) throw new IllegalArgumentException("timeout must be between 10 and 16384");
        if (n2 < 10) throw new IllegalArgumentException("timeout must be between 10 and 16384");
        if (n2 > 16384) throw new IllegalArgumentException("timeout must be between 10 and 16384");
        try {
            iBluetoothGatt = this.mBluetoothManager.getBluetoothGatt();
            Object handler = object;
            if (object == null) {
                handler = new Handler(Looper.getMainLooper());
            }
            object = this.wrap(periodicAdvertisingCallback, (Handler)handler);
            this.mCallbackWrappers.put(periodicAdvertisingCallback, (IPeriodicAdvertisingCallback)object);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to get Bluetooth gatt - ", remoteException);
            periodicAdvertisingCallback.onSyncEstablished(0, scanResult.getDevice(), scanResult.getAdvertisingSid(), n, n2, 2);
            return;
        }
        iBluetoothGatt.registerSync(scanResult, n, n2, (IPeriodicAdvertisingCallback)object);
        return;
        catch (RemoteException remoteException) {
            Log.e(TAG, "Failed to register sync - ", remoteException);
            return;
        }
    }

    public void unregisterSync(PeriodicAdvertisingCallback object) {
        if (object != null) {
            block5 : {
                IBluetoothGatt iBluetoothGatt;
                try {
                    iBluetoothGatt = this.mBluetoothManager.getBluetoothGatt();
                    object = this.mCallbackWrappers.remove(object);
                    if (object == null) break block5;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Failed to get Bluetooth gatt - ", remoteException);
                    return;
                }
                try {
                    iBluetoothGatt.unregisterSync((IPeriodicAdvertisingCallback)object);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Failed to cancel sync creation - ", remoteException);
                    return;
                }
            }
            throw new IllegalArgumentException("callback was not properly registered");
        }
        throw new IllegalArgumentException("callback can't be null");
    }

}

