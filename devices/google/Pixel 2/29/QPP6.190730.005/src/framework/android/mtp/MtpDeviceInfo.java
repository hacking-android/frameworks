/*
 * Decompiled with CFR 0.145.
 */
package android.mtp;

public class MtpDeviceInfo {
    private int[] mEventsSupported;
    private String mManufacturer;
    private String mModel;
    private int[] mOperationsSupported;
    private String mSerialNumber;
    private String mVersion;

    private MtpDeviceInfo() {
    }

    private static boolean isSupported(int[] arrn, int n) {
        int n2 = arrn.length;
        for (int i = 0; i < n2; ++i) {
            if (arrn[i] != n) continue;
            return true;
        }
        return false;
    }

    public final int[] getEventsSupported() {
        return this.mEventsSupported;
    }

    public final String getManufacturer() {
        return this.mManufacturer;
    }

    public final String getModel() {
        return this.mModel;
    }

    public final int[] getOperationsSupported() {
        return this.mOperationsSupported;
    }

    public final String getSerialNumber() {
        return this.mSerialNumber;
    }

    public final String getVersion() {
        return this.mVersion;
    }

    public boolean isEventSupported(int n) {
        return MtpDeviceInfo.isSupported(this.mEventsSupported, n);
    }

    public boolean isOperationSupported(int n) {
        return MtpDeviceInfo.isSupported(this.mOperationsSupported, n);
    }
}

