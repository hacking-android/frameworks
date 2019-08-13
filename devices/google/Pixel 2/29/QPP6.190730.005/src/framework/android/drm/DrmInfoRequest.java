/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class DrmInfoRequest {
    public static final String ACCOUNT_ID = "account_id";
    public static final String SUBSCRIPTION_ID = "subscription_id";
    public static final int TYPE_REGISTRATION_INFO = 1;
    public static final int TYPE_RIGHTS_ACQUISITION_INFO = 3;
    public static final int TYPE_RIGHTS_ACQUISITION_PROGRESS_INFO = 4;
    public static final int TYPE_UNREGISTRATION_INFO = 2;
    private final int mInfoType;
    private final String mMimeType;
    private final HashMap<String, Object> mRequestInformation = new HashMap();

    public DrmInfoRequest(int n, String string2) {
        this.mInfoType = n;
        this.mMimeType = string2;
        if (this.isValid()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("infoType: ");
        stringBuilder.append(n);
        stringBuilder.append(",mimeType: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static boolean isValidType(int n) {
        boolean bl = false;
        if (n == 1 || n == 2 || n == 3 || n == 4) {
            bl = true;
        }
        return bl;
    }

    public Object get(String string2) {
        return this.mRequestInformation.get(string2);
    }

    public int getInfoType() {
        return this.mInfoType;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    boolean isValid() {
        String string2 = this.mMimeType;
        boolean bl = string2 != null && !string2.equals("") && this.mRequestInformation != null && DrmInfoRequest.isValidType(this.mInfoType);
        return bl;
    }

    public Iterator<Object> iterator() {
        return this.mRequestInformation.values().iterator();
    }

    public Iterator<String> keyIterator() {
        return this.mRequestInformation.keySet().iterator();
    }

    public void put(String string2, Object object) {
        this.mRequestInformation.put(string2, object);
    }
}

