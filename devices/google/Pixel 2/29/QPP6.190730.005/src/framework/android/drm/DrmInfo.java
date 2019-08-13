/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import android.drm.DrmInfoRequest;
import android.drm.DrmUtils;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DrmInfo {
    private final HashMap<String, Object> mAttributes = new HashMap();
    private byte[] mData;
    private final int mInfoType;
    private final String mMimeType;

    public DrmInfo(int n, String charSequence, String string2) {
        this.mInfoType = n;
        this.mMimeType = string2;
        try {
            this.mData = DrmUtils.readBytes((String)charSequence);
        }
        catch (IOException iOException) {
            this.mData = null;
        }
        if (this.isValid()) {
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("infoType: ");
        ((StringBuilder)charSequence).append(n);
        ((StringBuilder)charSequence).append(",mimeType: ");
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(",data: ");
        ((StringBuilder)charSequence).append(Arrays.toString(this.mData));
        ((StringBuilder)charSequence).toString();
        throw new IllegalArgumentException();
    }

    public DrmInfo(int n, byte[] arrby, String string2) {
        this.mInfoType = n;
        this.mMimeType = string2;
        this.mData = arrby;
        if (this.isValid()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("infoType: ");
        stringBuilder.append(n);
        stringBuilder.append(",mimeType: ");
        stringBuilder.append(string2);
        stringBuilder.append(",data: ");
        stringBuilder.append(Arrays.toString(arrby));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Object get(String string2) {
        return this.mAttributes.get(string2);
    }

    public byte[] getData() {
        return this.mData;
    }

    public int getInfoType() {
        return this.mInfoType;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    boolean isValid() {
        byte[] arrby = this.mMimeType;
        boolean bl = arrby != null && !arrby.equals("") && (arrby = this.mData) != null && arrby.length > 0 && DrmInfoRequest.isValidType(this.mInfoType);
        return bl;
    }

    public Iterator<Object> iterator() {
        return this.mAttributes.values().iterator();
    }

    public Iterator<String> keyIterator() {
        return this.mAttributes.keySet().iterator();
    }

    public void put(String string2, Object object) {
        this.mAttributes.put(string2, object);
    }
}

