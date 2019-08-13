/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.PeriodicAdvertisingReport;

public abstract class PeriodicAdvertisingCallback {
    public static final int SYNC_NO_RESOURCES = 2;
    public static final int SYNC_NO_RESPONSE = 1;
    public static final int SYNC_SUCCESS = 0;

    public void onPeriodicAdvertisingReport(PeriodicAdvertisingReport periodicAdvertisingReport) {
    }

    public void onSyncEstablished(int n, BluetoothDevice bluetoothDevice, int n2, int n3, int n4, int n5) {
    }

    public void onSyncLost(int n) {
    }
}

