/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import android.drm.DrmEvent;
import java.util.HashMap;

public class DrmErrorEvent
extends DrmEvent {
    public static final int TYPE_ACQUIRE_DRM_INFO_FAILED = 2008;
    public static final int TYPE_NOT_SUPPORTED = 2003;
    public static final int TYPE_NO_INTERNET_CONNECTION = 2005;
    public static final int TYPE_OUT_OF_MEMORY = 2004;
    public static final int TYPE_PROCESS_DRM_INFO_FAILED = 2006;
    public static final int TYPE_REMOVE_ALL_RIGHTS_FAILED = 2007;
    public static final int TYPE_RIGHTS_NOT_INSTALLED = 2001;
    public static final int TYPE_RIGHTS_RENEWAL_NOT_ALLOWED = 2002;

    public DrmErrorEvent(int n, int n2, String string2) {
        super(n, n2, string2);
        this.checkTypeValidity(n2);
    }

    public DrmErrorEvent(int n, int n2, String string2, HashMap<String, Object> hashMap) {
        super(n, n2, string2, hashMap);
        this.checkTypeValidity(n2);
    }

    private void checkTypeValidity(int n) {
        if (n >= 2001 && n <= 2008) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported type: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

