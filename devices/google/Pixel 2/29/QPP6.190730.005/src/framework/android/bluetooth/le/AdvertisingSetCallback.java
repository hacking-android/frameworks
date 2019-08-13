/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.le.AdvertisingSet;

public abstract class AdvertisingSetCallback {
    public static final int ADVERTISE_FAILED_ALREADY_STARTED = 3;
    public static final int ADVERTISE_FAILED_DATA_TOO_LARGE = 1;
    public static final int ADVERTISE_FAILED_FEATURE_UNSUPPORTED = 5;
    public static final int ADVERTISE_FAILED_INTERNAL_ERROR = 4;
    public static final int ADVERTISE_FAILED_TOO_MANY_ADVERTISERS = 2;
    public static final int ADVERTISE_SUCCESS = 0;

    public void onAdvertisingDataSet(AdvertisingSet advertisingSet, int n) {
    }

    public void onAdvertisingEnabled(AdvertisingSet advertisingSet, boolean bl, int n) {
    }

    public void onAdvertisingParametersUpdated(AdvertisingSet advertisingSet, int n, int n2) {
    }

    public void onAdvertisingSetStarted(AdvertisingSet advertisingSet, int n, int n2) {
    }

    public void onAdvertisingSetStopped(AdvertisingSet advertisingSet) {
    }

    public void onOwnAddressRead(AdvertisingSet advertisingSet, int n, String string2) {
    }

    public void onPeriodicAdvertisingDataSet(AdvertisingSet advertisingSet, int n) {
    }

    public void onPeriodicAdvertisingEnabled(AdvertisingSet advertisingSet, boolean bl, int n) {
    }

    public void onPeriodicAdvertisingParametersUpdated(AdvertisingSet advertisingSet, int n) {
    }

    public void onScanResponseDataSet(AdvertisingSet advertisingSet, int n) {
    }
}

