/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothUuid;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.AdvertisingSet;
import android.bluetooth.le.AdvertisingSetCallback;
import android.bluetooth.le.AdvertisingSetParameters;
import android.bluetooth.le.BluetoothLeUtils;
import android.bluetooth.le.IAdvertisingSetCallback;
import android.bluetooth.le.PeriodicAdvertisingParameters;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelUuid;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class BluetoothLeAdvertiser {
    private static final int FLAGS_FIELD_BYTES = 3;
    private static final int MANUFACTURER_SPECIFIC_DATA_LENGTH = 2;
    private static final int MAX_ADVERTISING_DATA_BYTES = 1650;
    private static final int MAX_LEGACY_ADVERTISING_DATA_BYTES = 31;
    private static final int OVERHEAD_BYTES_PER_FIELD = 2;
    private static final String TAG = "BluetoothLeAdvertiser";
    private final Map<Integer, AdvertisingSet> mAdvertisingSets = Collections.synchronizedMap(new HashMap());
    private BluetoothAdapter mBluetoothAdapter;
    private final IBluetoothManager mBluetoothManager;
    private final Map<AdvertisingSetCallback, IAdvertisingSetCallback> mCallbackWrappers = Collections.synchronizedMap(new HashMap());
    private final Handler mHandler;
    private final Map<AdvertiseCallback, AdvertisingSetCallback> mLegacyAdvertisers = new HashMap<AdvertiseCallback, AdvertisingSetCallback>();

    public BluetoothLeAdvertiser(IBluetoothManager iBluetoothManager) {
        this.mBluetoothManager = iBluetoothManager;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mHandler = new Handler(Looper.getMainLooper());
    }

    private int byteLength(byte[] arrby) {
        int n = arrby == null ? 0 : arrby.length;
        return n;
    }

    private void postStartFailure(final AdvertiseCallback advertiseCallback, final int n) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                advertiseCallback.onStartFailure(n);
            }
        });
    }

    private void postStartSetFailure(Handler handler, final AdvertisingSetCallback advertisingSetCallback, final int n) {
        handler.post(new Runnable(){

            @Override
            public void run() {
                advertisingSetCallback.onAdvertisingSetStarted(null, 0, n);
            }
        });
    }

    private void postStartSuccess(final AdvertiseCallback advertiseCallback, final AdvertiseSettings advertiseSettings) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                advertiseCallback.onStartSuccess(advertiseSettings);
            }
        });
    }

    private int totalBytes(AdvertiseData advertiseData, boolean bl) {
        int n = 0;
        if (advertiseData == null) {
            return 0;
        }
        if (bl) {
            n = 3;
        }
        int n2 = n;
        if (advertiseData.getServiceUuids() != null) {
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            for (ParcelUuid object2 : advertiseData.getServiceUuids()) {
                if (BluetoothUuid.is16BitUuid(object2)) {
                    ++n3;
                    continue;
                }
                if (BluetoothUuid.is32BitUuid(object2)) {
                    ++n4;
                    continue;
                }
                ++n5;
            }
            n2 = n;
            if (n3 != 0) {
                n2 = n + (n3 * 2 + 2);
            }
            n = n2;
            if (n4 != 0) {
                n = n2 + (n4 * 4 + 2);
            }
            n2 = n;
            if (n5 != 0) {
                n2 = n + (n5 * 16 + 2);
            }
        }
        Iterator<ParcelUuid> iterator = advertiseData.getServiceData().keySet().iterator();
        n = n2;
        while (iterator.hasNext()) {
            ParcelUuid parcelUuid = iterator.next();
            n += BluetoothUuid.uuidToBytes(parcelUuid).length + 2 + this.byteLength(advertiseData.getServiceData().get(parcelUuid));
        }
        for (n2 = 0; n2 < advertiseData.getManufacturerSpecificData().size(); ++n2) {
            n += this.byteLength(advertiseData.getManufacturerSpecificData().valueAt(n2)) + 4;
        }
        n2 = n;
        if (advertiseData.getIncludeTxPowerLevel()) {
            n2 = n + 3;
        }
        n = n2;
        if (advertiseData.getIncludeDeviceName()) {
            n = n2;
            if (this.mBluetoothAdapter.getName() != null) {
                n = n2 + (this.mBluetoothAdapter.getName().length() + 2);
            }
        }
        return n;
    }

    public void cleanup() {
        this.mLegacyAdvertisers.clear();
        this.mCallbackWrappers.clear();
        this.mAdvertisingSets.clear();
    }

    public void startAdvertising(AdvertiseSettings advertiseSettings, AdvertiseData advertiseData, AdvertiseCallback advertiseCallback) {
        this.startAdvertising(advertiseSettings, advertiseData, null, advertiseCallback);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void startAdvertising(AdvertiseSettings var1_1, AdvertiseData var2_5, AdvertiseData var3_6, AdvertiseCallback var4_7) {
        var5_8 = this.mLegacyAdvertisers;
        // MONITORENTER : var5_8
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        if (var4_7 == null) ** GOTO lbl66
        var6_9 = var1_1.isConnectable();
        var7_10 = this.totalBytes(var2_5, var6_9);
        var8_11 = 1;
        if (var7_10 > 31) ** GOTO lbl63
        {
            catch (Throwable var1_2) {
                throw var1_3;
            }
        }
        try {
            if (this.totalBytes(var3_6, false) <= 31) {
                if (this.mLegacyAdvertisers.containsKey(var4_7)) {
                    this.postStartFailure(var4_7, 3);
                    // MONITOREXIT : var5_8
                    return;
                }
                var9_12 = new AdvertisingSetParameters.Builder();
                var9_12.setLegacyMode(true);
                var9_12.setConnectable(var6_9);
                var9_12.setScannable(true);
                if (var1_1.getMode() == 0) {
                    var9_12.setInterval(1600);
                } else if (var1_1.getMode() == 1) {
                    var9_12.setInterval(400);
                } else if (var1_1.getMode() == 2) {
                    var9_12.setInterval(160);
                }
                if (var1_1.getTxPowerLevel() == 0) {
                    var9_12.setTxPowerLevel(-21);
                } else if (var1_1.getTxPowerLevel() == 1) {
                    var9_12.setTxPowerLevel(-15);
                } else if (var1_1.getTxPowerLevel() == 2) {
                    var9_12.setTxPowerLevel(-7);
                } else if (var1_1.getTxPowerLevel() == 3) {
                    var9_12.setTxPowerLevel(1);
                }
                var7_10 = var1_1.getTimeout();
                if (var7_10 > 0) {
                    if (var7_10 >= 10) {
                        var8_11 = var7_10 / 10;
                    }
                } else {
                    var8_11 = 0;
                }
                var1_1 = this.wrapOldCallback(var4_7, (AdvertiseSettings)var1_1);
                this.mLegacyAdvertisers.put(var4_7, (AdvertisingSetCallback)var1_1);
                this.startAdvertisingSet(var9_12.build(), var2_5, var3_6, null, null, var8_11, 0, (AdvertisingSetCallback)var1_1);
                // MONITOREXIT : var5_8
                return;
            }
lbl63: // 3 sources:
            this.postStartFailure(var4_7, 1);
            // MONITOREXIT : var5_8
            return;
lbl66: // 1 sources:
            var1_1 = new IllegalArgumentException("callback cannot be null");
            throw var1_1;
        }
        catch (Throwable var1_4) {
            throw var1_3;
        }
    }

    public void startAdvertisingSet(AdvertisingSetParameters advertisingSetParameters, AdvertiseData advertiseData, AdvertiseData advertiseData2, PeriodicAdvertisingParameters periodicAdvertisingParameters, AdvertiseData advertiseData3, int n, int n2, AdvertisingSetCallback advertisingSetCallback) {
        this.startAdvertisingSet(advertisingSetParameters, advertiseData, advertiseData2, periodicAdvertisingParameters, advertiseData3, n, n2, advertisingSetCallback, new Handler(Looper.getMainLooper()));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void startAdvertisingSet(AdvertisingSetParameters object, AdvertiseData advertiseData, AdvertiseData advertiseData2, PeriodicAdvertisingParameters periodicAdvertisingParameters, AdvertiseData advertiseData3, int n, int n2, AdvertisingSetCallback advertisingSetCallback, Handler handler) {
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        if (advertisingSetCallback == null) throw new IllegalArgumentException("callback cannot be null");
        boolean bl = ((AdvertisingSetParameters)object).isConnectable();
        if (((AdvertisingSetParameters)object).isLegacy()) {
            if (this.totalBytes(advertiseData, bl) > 31) throw new IllegalArgumentException("Legacy advertising data too big");
            if (this.totalBytes(advertiseData2, false) > 31) {
                throw new IllegalArgumentException("Legacy scan response data too big");
            }
        } else {
            boolean bl2 = this.mBluetoothAdapter.isLeCodedPhySupported();
            boolean bl3 = this.mBluetoothAdapter.isLe2MPhySupported();
            int n3 = ((AdvertisingSetParameters)object).getPrimaryPhy();
            int n4 = ((AdvertisingSetParameters)object).getSecondaryPhy();
            if (n3 == 3 && !bl2) {
                throw new IllegalArgumentException("Unsupported primary PHY selected");
            }
            if (n4 == 3 && !bl2 || n4 == 2 && !bl3) {
                throw new IllegalArgumentException("Unsupported secondary PHY selected");
            }
            n3 = this.mBluetoothAdapter.getLeMaximumAdvertisingDataLength();
            if (this.totalBytes(advertiseData, bl) > n3) throw new IllegalArgumentException("Advertising data too big");
            if (this.totalBytes(advertiseData2, false) > n3) throw new IllegalArgumentException("Scan response data too big");
            if (this.totalBytes(advertiseData3, false) > n3) throw new IllegalArgumentException("Periodic advertising data too big");
            bl2 = this.mBluetoothAdapter.isLePeriodicAdvertisingSupported();
            if (periodicAdvertisingParameters != null && !bl2) {
                throw new IllegalArgumentException("Controller does not support LE Periodic Advertising");
            }
        }
        if (n2 >= 0 && n2 <= 255) {
            if (n2 != 0 && !this.mBluetoothAdapter.isLePeriodicAdvertisingSupported()) {
                throw new IllegalArgumentException("Can't use maxExtendedAdvertisingEvents with controller that don't support LE Extended Advertising");
            }
            if (n >= 0 && n <= 65535) {
                IBluetoothGatt iBluetoothGatt;
                block13 : {
                    try {
                        iBluetoothGatt = this.mBluetoothManager.getBluetoothGatt();
                        if (iBluetoothGatt != null) break block13;
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "Failed to get Bluetooth GATT - ", remoteException);
                        this.postStartSetFailure(handler, advertisingSetCallback, 4);
                        return;
                    }
                    Log.e(TAG, "Bluetooth GATT is null");
                    this.postStartSetFailure(handler, advertisingSetCallback, 4);
                    return;
                }
                IAdvertisingSetCallback iAdvertisingSetCallback = this.wrap(advertisingSetCallback, handler);
                if (this.mCallbackWrappers.putIfAbsent(advertisingSetCallback, iAdvertisingSetCallback) != null) throw new IllegalArgumentException("callback instance already associated with advertising");
                try {
                    iBluetoothGatt.startAdvertisingSet((AdvertisingSetParameters)object, advertiseData, advertiseData2, periodicAdvertisingParameters, advertiseData3, n, n2, iAdvertisingSetCallback);
                    return;
                }
                catch (RemoteException remoteException) {
                    Log.e(TAG, "Failed to start advertising set - ", remoteException);
                    this.postStartSetFailure(handler, advertisingSetCallback, 4);
                    return;
                }
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("duration out of range: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("maxExtendedAdvertisingEvents out of range: ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void startAdvertisingSet(AdvertisingSetParameters advertisingSetParameters, AdvertiseData advertiseData, AdvertiseData advertiseData2, PeriodicAdvertisingParameters periodicAdvertisingParameters, AdvertiseData advertiseData3, AdvertisingSetCallback advertisingSetCallback) {
        this.startAdvertisingSet(advertisingSetParameters, advertiseData, advertiseData2, periodicAdvertisingParameters, advertiseData3, 0, 0, advertisingSetCallback, new Handler(Looper.getMainLooper()));
    }

    public void startAdvertisingSet(AdvertisingSetParameters advertisingSetParameters, AdvertiseData advertiseData, AdvertiseData advertiseData2, PeriodicAdvertisingParameters periodicAdvertisingParameters, AdvertiseData advertiseData3, AdvertisingSetCallback advertisingSetCallback, Handler handler) {
        this.startAdvertisingSet(advertisingSetParameters, advertiseData, advertiseData2, periodicAdvertisingParameters, advertiseData3, 0, 0, advertisingSetCallback, handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopAdvertising(AdvertiseCallback object) {
        Map<AdvertiseCallback, AdvertisingSetCallback> map = this.mLegacyAdvertisers;
        synchronized (map) {
            Throwable throwable2;
            if (object != null) {
                try {
                    AdvertisingSetCallback advertisingSetCallback = this.mLegacyAdvertisers.get(object);
                    if (advertisingSetCallback == null) {
                        return;
                    }
                    this.stopAdvertisingSet(advertisingSetCallback);
                    this.mLegacyAdvertisers.remove(object);
                    return;
                }
                catch (Throwable throwable2) {}
            } else {
                object = new IllegalArgumentException("callback cannot be null");
                throw object;
            }
            throw throwable2;
        }
    }

    public void stopAdvertisingSet(AdvertisingSetCallback object) {
        if (object != null) {
            if ((object = this.mCallbackWrappers.remove(object)) == null) {
                return;
            }
            try {
                this.mBluetoothManager.getBluetoothGatt().stopAdvertisingSet((IAdvertisingSetCallback)object);
            }
            catch (RemoteException remoteException) {
                Log.e(TAG, "Failed to stop advertising - ", remoteException);
            }
            return;
        }
        throw new IllegalArgumentException("callback cannot be null");
    }

    IAdvertisingSetCallback wrap(final AdvertisingSetCallback advertisingSetCallback, final Handler handler) {
        return new IAdvertisingSetCallback.Stub(){

            @Override
            public void onAdvertisingDataSet(final int n, final int n2) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onAdvertisingDataSet(advertisingSet, n2);
                    }
                });
            }

            @Override
            public void onAdvertisingEnabled(final int n, final boolean bl, final int n2) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onAdvertisingEnabled(advertisingSet, bl, n2);
                    }
                });
            }

            @Override
            public void onAdvertisingParametersUpdated(final int n, final int n2, final int n3) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onAdvertisingParametersUpdated(advertisingSet, n2, n3);
                    }
                });
            }

            @Override
            public void onAdvertisingSetStarted(final int n, final int n2, final int n3) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        if (n3 != 0) {
                            advertisingSetCallback.onAdvertisingSetStarted(null, 0, n3);
                            BluetoothLeAdvertiser.this.mCallbackWrappers.remove(advertisingSetCallback);
                            return;
                        }
                        AdvertisingSet advertisingSet = new AdvertisingSet(n, BluetoothLeAdvertiser.this.mBluetoothManager);
                        BluetoothLeAdvertiser.this.mAdvertisingSets.put(n, advertisingSet);
                        advertisingSetCallback.onAdvertisingSetStarted(advertisingSet, n2, n3);
                    }
                });
            }

            @Override
            public void onAdvertisingSetStopped(final int n) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onAdvertisingSetStopped(advertisingSet);
                        BluetoothLeAdvertiser.this.mAdvertisingSets.remove(n);
                        BluetoothLeAdvertiser.this.mCallbackWrappers.remove(advertisingSetCallback);
                    }
                });
            }

            @Override
            public void onOwnAddressRead(final int n, final int n2, final String string2) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onOwnAddressRead(advertisingSet, n2, string2);
                    }
                });
            }

            @Override
            public void onPeriodicAdvertisingDataSet(final int n, final int n2) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onPeriodicAdvertisingDataSet(advertisingSet, n2);
                    }
                });
            }

            @Override
            public void onPeriodicAdvertisingEnabled(final int n, final boolean bl, final int n2) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onPeriodicAdvertisingEnabled(advertisingSet, bl, n2);
                    }
                });
            }

            @Override
            public void onPeriodicAdvertisingParametersUpdated(final int n, final int n2) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onPeriodicAdvertisingParametersUpdated(advertisingSet, n2);
                    }
                });
            }

            @Override
            public void onScanResponseDataSet(final int n, final int n2) {
                handler.post(new Runnable(){

                    @Override
                    public void run() {
                        AdvertisingSet advertisingSet = (AdvertisingSet)BluetoothLeAdvertiser.this.mAdvertisingSets.get(n);
                        advertisingSetCallback.onScanResponseDataSet(advertisingSet, n2);
                    }
                });
            }

        };
    }

    AdvertisingSetCallback wrapOldCallback(final AdvertiseCallback advertiseCallback, final AdvertiseSettings advertiseSettings) {
        return new AdvertisingSetCallback(){

            @Override
            public void onAdvertisingEnabled(AdvertisingSet advertisingSet, boolean bl, int n) {
                if (bl) {
                    Log.e(BluetoothLeAdvertiser.TAG, "Legacy advertiser should be only disabled on timeout, but was enabled!");
                    return;
                }
                BluetoothLeAdvertiser.this.stopAdvertising(advertiseCallback);
            }

            @Override
            public void onAdvertisingSetStarted(AdvertisingSet advertisingSet, int n, int n2) {
                if (n2 != 0) {
                    BluetoothLeAdvertiser.this.postStartFailure(advertiseCallback, n2);
                    return;
                }
                BluetoothLeAdvertiser.this.postStartSuccess(advertiseCallback, advertiseSettings);
            }
        };
    }

}

