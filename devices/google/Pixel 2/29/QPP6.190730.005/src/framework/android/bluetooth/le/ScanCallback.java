/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth.le;

import android.bluetooth.le.ScanResult;
import java.util.List;

public abstract class ScanCallback {
    static final int NO_ERROR = 0;
    public static final int SCAN_FAILED_ALREADY_STARTED = 1;
    public static final int SCAN_FAILED_APPLICATION_REGISTRATION_FAILED = 2;
    public static final int SCAN_FAILED_FEATURE_UNSUPPORTED = 4;
    public static final int SCAN_FAILED_INTERNAL_ERROR = 3;
    public static final int SCAN_FAILED_OUT_OF_HARDWARE_RESOURCES = 5;
    public static final int SCAN_FAILED_SCANNING_TOO_FREQUENTLY = 6;

    public void onBatchScanResults(List<ScanResult> list) {
    }

    public void onScanFailed(int n) {
    }

    public void onScanResult(int n, ScanResult scanResult) {
    }
}

