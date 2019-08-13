/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import java.util.HashMap;

public class DrmEvent {
    public static final String DRM_INFO_OBJECT = "drm_info_object";
    public static final String DRM_INFO_STATUS_OBJECT = "drm_info_status_object";
    public static final int TYPE_ALL_RIGHTS_REMOVED = 1001;
    public static final int TYPE_DRM_INFO_PROCESSED = 1002;
    private HashMap<String, Object> mAttributes = new HashMap();
    private String mMessage = "";
    private final int mType;
    private final int mUniqueId;

    protected DrmEvent(int n, int n2, String string2) {
        this.mUniqueId = n;
        this.mType = n2;
        if (string2 != null) {
            this.mMessage = string2;
        }
    }

    protected DrmEvent(int n, int n2, String string2, HashMap<String, Object> hashMap) {
        this.mUniqueId = n;
        this.mType = n2;
        if (string2 != null) {
            this.mMessage = string2;
        }
        if (hashMap != null) {
            this.mAttributes = hashMap;
        }
    }

    public Object getAttribute(String string2) {
        return this.mAttributes.get(string2);
    }

    public String getMessage() {
        return this.mMessage;
    }

    public int getType() {
        return this.mType;
    }

    public int getUniqueId() {
        return this.mUniqueId;
    }
}

