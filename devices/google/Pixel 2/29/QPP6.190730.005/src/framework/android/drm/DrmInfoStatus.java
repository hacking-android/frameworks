/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import android.drm.DrmInfoRequest;
import android.drm.ProcessedData;

public class DrmInfoStatus {
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_OK = 1;
    public final ProcessedData data;
    public final int infoType;
    public final String mimeType;
    public final int statusCode;

    public DrmInfoStatus(int n, int n2, ProcessedData object, String string2) {
        if (DrmInfoRequest.isValidType(n2)) {
            if (this.isValidStatusCode(n)) {
                if (string2 != null && string2 != "") {
                    this.statusCode = n;
                    this.infoType = n2;
                    this.data = object;
                    this.mimeType = string2;
                    return;
                }
                throw new IllegalArgumentException("mimeType is null or an empty string");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unsupported status code: ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("infoType: ");
        ((StringBuilder)object).append(n2);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private boolean isValidStatusCode(int n) {
        boolean bl;
        boolean bl2 = bl = true;
        if (n != 1) {
            bl2 = n == 2 ? bl : false;
        }
        return bl2;
    }
}

