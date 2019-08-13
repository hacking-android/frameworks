/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.annotation.SystemApi;
import android.app.ActivityThread;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.IBluetoothGatt;
import android.bluetooth.IBluetoothManager;
import android.bluetooth.le.BluetoothLeUtils;
import android.bluetooth.le.IScannerCallback;
import android.bluetooth.le.ResultStorageDescriptor;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.TruncatedFilter;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.os.WorkSource;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public final class BluetoothLeScanner {
    private static final boolean DBG = true;
    public static final String EXTRA_CALLBACK_TYPE = "android.bluetooth.le.extra.CALLBACK_TYPE";
    public static final String EXTRA_ERROR_CODE = "android.bluetooth.le.extra.ERROR_CODE";
    public static final String EXTRA_LIST_SCAN_RESULT = "android.bluetooth.le.extra.LIST_SCAN_RESULT";
    private static final String TAG = "BluetoothLeScanner";
    private static final boolean VDBG = false;
    private BluetoothAdapter mBluetoothAdapter;
    private final IBluetoothManager mBluetoothManager;
    private final Handler mHandler;
    private final Map<ScanCallback, BleScanCallbackWrapper> mLeScanClients;

    public BluetoothLeScanner(IBluetoothManager iBluetoothManager) {
        this.mBluetoothManager = iBluetoothManager;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mLeScanClients = new HashMap<ScanCallback, BleScanCallbackWrapper>();
    }

    private boolean isHardwareResourcesAvailableForScan(ScanSettings scanSettings) {
        int n = scanSettings.getCallbackType();
        boolean bl = true;
        if ((n & 2) == 0 && (n & 4) == 0) {
            return true;
        }
        if (!this.mBluetoothAdapter.isOffloadedFilteringSupported() || !this.mBluetoothAdapter.isHardwareTrackingFiltersAvailable()) {
            bl = false;
        }
        return bl;
    }

    private boolean isSettingsAndFilterComboAllowed(ScanSettings object, List<ScanFilter> list) {
        if ((((ScanSettings)object).getCallbackType() & 6) != 0) {
            if (list == null) {
                return false;
            }
            object = list.iterator();
            while (object.hasNext()) {
                if (!((ScanFilter)object.next()).isAllFieldsEmpty()) continue;
                return false;
            }
        }
        return true;
    }

    private boolean isSettingsConfigAllowedForScan(ScanSettings scanSettings) {
        if (this.mBluetoothAdapter.isOffloadedFilteringSupported()) {
            return true;
        }
        return scanSettings.getCallbackType() == 1 && scanSettings.getReportDelayMillis() == 0L;
    }

    private void postCallbackError(final ScanCallback scanCallback, final int n) {
        this.mHandler.post(new Runnable(){

            @Override
            public void run() {
                scanCallback.onScanFailed(n);
            }
        });
    }

    private int postCallbackErrorOrReturn(ScanCallback scanCallback, int n) {
        if (scanCallback == null) {
            return n;
        }
        this.postCallbackError(scanCallback, n);
        return 0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private int startScan(List<ScanFilter> var1_1, ScanSettings var2_4, WorkSource var3_5, ScanCallback var4_6, PendingIntent var5_7, List<List<ResultStorageDescriptor>> var6_8) {
        block14 : {
            BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
            if (var4_6 == null) {
                if (var5_7 == null) throw new IllegalArgumentException("callback is null");
            }
            if (var2_4 == null) throw new IllegalArgumentException("settings is null");
            var7_9 = this.mLeScanClients;
            // MONITORENTER : var7_9
            if (var4_6 == null) ** GOTO lbl14
            if (this.mLeScanClients.containsKey(var4_6)) {
                var8_10 = this.postCallbackErrorOrReturn(var4_6, 1);
                // MONITOREXIT : var7_9
                return var8_10;
            }
lbl14: // 4 sources:
            var9_15 = this.mBluetoothManager.getBluetoothGatt();
            break block14;
            catch (RemoteException var9_16) {
                var9_15 = null;
            }
        }
        if (var9_15 == null) {
            var8_11 = this.postCallbackErrorOrReturn(var4_6, 3);
            // MONITOREXIT : var7_9
            return var8_11;
        }
        if (!this.isSettingsConfigAllowedForScan(var2_4)) {
            var8_12 = this.postCallbackErrorOrReturn(var4_6, 4);
            // MONITOREXIT : var7_9
            return var8_12;
        }
        if (!this.isHardwareResourcesAvailableForScan(var2_4)) {
            var8_13 = this.postCallbackErrorOrReturn(var4_6, 5);
            // MONITOREXIT : var7_9
            return var8_13;
        }
        if (!this.isSettingsAndFilterComboAllowed(var2_4, var1_1)) {
            var8_14 = this.postCallbackErrorOrReturn(var4_6, 4);
            // MONITOREXIT : var7_9
            return var8_14;
        }
        if (var4_6 != null) {
            var5_7 = new BleScanCallbackWrapper(var9_15, var1_1, var2_4, var3_5, var4_6, var6_8);
            var5_7.startRegistration();
            return 0;
        }
        try {
            var9_15.startScanForIntent((PendingIntent)var5_7, var2_4, var1_1, ActivityThread.currentOpPackageName());
            // MONITOREXIT : var7_9
            return 0;
        }
        catch (RemoteException var1_3) {
            // MONITOREXIT : var7_9
            return 3;
        }
    }

    public void cleanup() {
        this.mLeScanClients.clear();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void flushPendingScanResults(ScanCallback object) {
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        if (object == null) {
            throw new IllegalArgumentException("callback cannot be null!");
        }
        Map<ScanCallback, BleScanCallbackWrapper> map = this.mLeScanClients;
        synchronized (map) {
            object = this.mLeScanClients.get(object);
            if (object == null) {
                return;
            }
            ((BleScanCallbackWrapper)object).flushPendingBatchResults();
            return;
        }
    }

    public int startScan(List<ScanFilter> list, ScanSettings scanSettings, PendingIntent pendingIntent) {
        if (scanSettings == null) {
            scanSettings = new ScanSettings.Builder().build();
        }
        return this.startScan(list, scanSettings, null, null, pendingIntent, null);
    }

    public void startScan(ScanCallback scanCallback) {
        this.startScan(null, new ScanSettings.Builder().build(), scanCallback);
    }

    public void startScan(List<ScanFilter> list, ScanSettings scanSettings, ScanCallback scanCallback) {
        this.startScan(list, scanSettings, null, scanCallback, null, null);
    }

    @SystemApi
    public void startScanFromSource(WorkSource workSource, ScanCallback scanCallback) {
        this.startScanFromSource(null, new ScanSettings.Builder().build(), workSource, scanCallback);
    }

    @SystemApi
    public void startScanFromSource(List<ScanFilter> list, ScanSettings scanSettings, WorkSource workSource, ScanCallback scanCallback) {
        this.startScan(list, scanSettings, workSource, scanCallback, null, null);
    }

    @SystemApi
    public void startTruncatedScan(List<TruncatedFilter> object, ScanSettings scanSettings, ScanCallback scanCallback) {
        int n = object.size();
        ArrayList<ScanFilter> arrayList = new ArrayList<ScanFilter>(n);
        ArrayList<List<ResultStorageDescriptor>> arrayList2 = new ArrayList<List<ResultStorageDescriptor>>(n);
        object = object.iterator();
        while (object.hasNext()) {
            TruncatedFilter truncatedFilter = (TruncatedFilter)object.next();
            arrayList.add(truncatedFilter.getFilter());
            arrayList2.add(truncatedFilter.getStorageDescriptors());
        }
        this.startScan(arrayList, scanSettings, null, scanCallback, null, arrayList2);
    }

    public void stopScan(PendingIntent pendingIntent) {
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        try {
            this.mBluetoothManager.getBluetoothGatt().stopScanForIntent(pendingIntent, ActivityThread.currentOpPackageName());
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopScan(ScanCallback object) {
        BluetoothLeUtils.checkAdapterStateOn(this.mBluetoothAdapter);
        Map<ScanCallback, BleScanCallbackWrapper> map = this.mLeScanClients;
        synchronized (map) {
            object = this.mLeScanClients.remove(object);
            if (object == null) {
                Log.d(TAG, "could not find callback wrapper");
                return;
            }
            ((BleScanCallbackWrapper)object).stopLeScan();
            return;
        }
    }

    private class BleScanCallbackWrapper
    extends IScannerCallback.Stub {
        private static final int REGISTRATION_CALLBACK_TIMEOUT_MILLIS = 2000;
        private IBluetoothGatt mBluetoothGatt;
        private final List<ScanFilter> mFilters;
        private List<List<ResultStorageDescriptor>> mResultStorages;
        private final ScanCallback mScanCallback;
        private int mScannerId;
        private ScanSettings mSettings;
        private final WorkSource mWorkSource;

        public BleScanCallbackWrapper(IBluetoothGatt iBluetoothGatt, List<ScanFilter> list, ScanSettings scanSettings, WorkSource workSource, ScanCallback scanCallback, List<List<ResultStorageDescriptor>> list2) {
            this.mBluetoothGatt = iBluetoothGatt;
            this.mFilters = list;
            this.mSettings = scanSettings;
            this.mWorkSource = workSource;
            this.mScanCallback = scanCallback;
            this.mScannerId = 0;
            this.mResultStorages = list2;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void flushPendingBatchResults() {
            synchronized (this) {
                if (this.mScannerId <= 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error state, mLeHandle: ");
                    stringBuilder.append(this.mScannerId);
                    Log.e(BluetoothLeScanner.TAG, stringBuilder.toString());
                    return;
                }
                try {
                    this.mBluetoothGatt.flushPendingBatchResults(this.mScannerId);
                }
                catch (RemoteException remoteException) {
                    Log.e(BluetoothLeScanner.TAG, "Failed to get pending scan results", remoteException);
                }
                return;
            }
        }

        @Override
        public void onBatchScanResults(final List<ScanResult> list) {
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    BleScanCallbackWrapper.this.mScanCallback.onBatchScanResults(list);
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onFoundOrLost(final boolean bl, final ScanResult scanResult) {
            synchronized (this) {
                if (this.mScannerId <= 0) {
                    return;
                }
            }
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    if (bl) {
                        BleScanCallbackWrapper.this.mScanCallback.onScanResult(2, scanResult);
                    } else {
                        BleScanCallbackWrapper.this.mScanCallback.onScanResult(4, scanResult);
                    }
                }
            });
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onScanManagerErrorCallback(int n) {
            synchronized (this) {
                if (this.mScannerId <= 0) {
                    return;
                }
            }
            BluetoothLeScanner.this.postCallbackError(this.mScanCallback, n);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onScanResult(final ScanResult scanResult) {
            synchronized (this) {
                if (this.mScannerId <= 0) {
                    return;
                }
            }
            new Handler(Looper.getMainLooper()).post(new Runnable(){

                @Override
                public void run() {
                    BleScanCallbackWrapper.this.mScanCallback.onScanResult(1, scanResult);
                }
            });
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public void onScannerRegistered(int n, int n2) {
            block8 : {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onScannerRegistered() - status=");
                stringBuilder.append(n);
                stringBuilder.append(" scannerId=");
                stringBuilder.append(n2);
                stringBuilder.append(" mScannerId=");
                stringBuilder.append(this.mScannerId);
                Log.d(BluetoothLeScanner.TAG, stringBuilder.toString());
                // MONITORENTER : this
                if (n == 0) {
                    if (this.mScannerId == -1) {
                        this.mBluetoothGatt.unregisterScanner(n2);
                        break block8;
                    }
                    this.mScannerId = n2;
                    this.mBluetoothGatt.startScan(this.mScannerId, this.mSettings, this.mFilters, this.mResultStorages, ActivityThread.currentOpPackageName());
                    catch (RemoteException remoteException) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("fail to start le scan: ");
                        stringBuilder2.append(remoteException);
                        Log.e(BluetoothLeScanner.TAG, stringBuilder2.toString());
                        this.mScannerId = -1;
                    }
                } else {
                    this.mScannerId = n == 6 ? -2 : -1;
                }
            }
            this.notifyAll();
            // MONITOREXIT : this
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void startRegistration() {
            synchronized (this) {
                int n;
                if (this.mScannerId != -1 && (n = this.mScannerId) != -2) {
                    try {
                        this.mBluetoothGatt.registerScanner(this, this.mWorkSource);
                        this.wait(2000L);
                    }
                    catch (RemoteException | InterruptedException exception) {
                        Log.e(BluetoothLeScanner.TAG, "application registeration exception", exception);
                        BluetoothLeScanner.this.postCallbackError(this.mScanCallback, 3);
                    }
                    if (this.mScannerId > 0) {
                        BluetoothLeScanner.this.mLeScanClients.put(this.mScanCallback, this);
                    } else {
                        if (this.mScannerId == 0) {
                            this.mScannerId = -1;
                        }
                        if (this.mScannerId == -2) {
                            return;
                        }
                        BluetoothLeScanner.this.postCallbackError(this.mScanCallback, 2);
                    }
                    return;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void stopLeScan() {
            synchronized (this) {
                if (this.mScannerId <= 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Error state, mLeHandle: ");
                    stringBuilder.append(this.mScannerId);
                    Log.e(BluetoothLeScanner.TAG, stringBuilder.toString());
                    return;
                }
                try {
                    this.mBluetoothGatt.stopScan(this.mScannerId);
                    this.mBluetoothGatt.unregisterScanner(this.mScannerId);
                }
                catch (RemoteException remoteException) {
                    Log.e(BluetoothLeScanner.TAG, "Failed to stop scan and unregister", remoteException);
                }
                this.mScannerId = -1;
                return;
            }
        }

    }

}

