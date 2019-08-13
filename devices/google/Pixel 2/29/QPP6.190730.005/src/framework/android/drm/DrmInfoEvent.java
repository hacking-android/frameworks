/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import android.drm.DrmEvent;
import java.util.HashMap;

public class DrmInfoEvent
extends DrmEvent {
    public static final int TYPE_ACCOUNT_ALREADY_REGISTERED = 5;
    public static final int TYPE_ALREADY_REGISTERED_BY_ANOTHER_ACCOUNT = 1;
    public static final int TYPE_REMOVE_RIGHTS = 2;
    public static final int TYPE_RIGHTS_INSTALLED = 3;
    public static final int TYPE_RIGHTS_REMOVED = 6;
    public static final int TYPE_WAIT_FOR_RIGHTS = 4;

    public DrmInfoEvent(int n, int n2, String string2) {
        super(n, n2, string2);
        this.checkTypeValidity(n2);
    }

    public DrmInfoEvent(int n, int n2, String string2, HashMap<String, Object> hashMap) {
        super(n, n2, string2, hashMap);
        this.checkTypeValidity(n2);
    }

    private void checkTypeValidity(int n) {
        if ((n < 1 || n > 6) && n != 1001 && n != 1002) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported type: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }
}

