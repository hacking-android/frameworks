/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

public class DrmConvertedStatus {
    public static final int STATUS_ERROR = 3;
    public static final int STATUS_INPUTDATA_ERROR = 2;
    public static final int STATUS_OK = 1;
    public final byte[] convertedData;
    public final int offset;
    public final int statusCode;

    public DrmConvertedStatus(int n, byte[] object, int n2) {
        if (this.isValidStatusCode(n)) {
            this.statusCode = n;
            this.convertedData = object;
            this.offset = n2;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unsupported status code: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private boolean isValidStatusCode(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = bl;
            if (n != 2) {
                bl2 = n == 3 ? bl : false;
            }
        }
        return bl2;
    }
}

