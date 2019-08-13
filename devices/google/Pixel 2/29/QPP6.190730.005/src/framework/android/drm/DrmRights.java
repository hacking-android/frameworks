/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import android.drm.DrmUtils;
import android.drm.ProcessedData;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

public class DrmRights {
    private String mAccountId;
    private byte[] mData;
    private String mMimeType;
    private String mSubscriptionId;

    public DrmRights(ProcessedData object, String string2) {
        if (object != null) {
            this.mData = ((ProcessedData)object).getData();
            this.mAccountId = ((ProcessedData)object).getAccountId();
            this.mSubscriptionId = ((ProcessedData)object).getSubscriptionId();
            this.mMimeType = string2;
            if (this.isValid()) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("mimeType: ");
            ((StringBuilder)object).append(this.mMimeType);
            ((StringBuilder)object).append(",data: ");
            ((StringBuilder)object).append(Arrays.toString(this.mData));
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        throw new IllegalArgumentException("data is null");
    }

    public DrmRights(File file, String string2) {
        this.instantiate(file, string2);
    }

    public DrmRights(String string2, String string3) {
        this.instantiate(new File(string2), string3);
    }

    public DrmRights(String string2, String string3, String string4) {
        this(string2, string3);
        this.mAccountId = string4;
    }

    public DrmRights(String string2, String string3, String string4, String string5) {
        this(string2, string3);
        this.mAccountId = string4;
        this.mSubscriptionId = string5;
    }

    private void instantiate(File serializable, String string2) {
        try {
            this.mData = DrmUtils.readBytes((File)serializable);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.mMimeType = string2;
        if (this.isValid()) {
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("mimeType: ");
        ((StringBuilder)serializable).append(this.mMimeType);
        ((StringBuilder)serializable).append(",data: ");
        ((StringBuilder)serializable).append(Arrays.toString(this.mData));
        throw new IllegalArgumentException(((StringBuilder)serializable).toString());
    }

    public String getAccountId() {
        return this.mAccountId;
    }

    public byte[] getData() {
        return this.mData;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public String getSubscriptionId() {
        return this.mSubscriptionId;
    }

    boolean isValid() {
        byte[] arrby = this.mMimeType;
        boolean bl = arrby != null && !arrby.equals("") && (arrby = this.mData) != null && arrby.length > 0;
        return bl;
    }
}

