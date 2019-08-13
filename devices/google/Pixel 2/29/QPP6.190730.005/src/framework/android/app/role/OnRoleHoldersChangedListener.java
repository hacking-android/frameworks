/*
 * Decompiled with CFR 0.145.
 */
package android.app.role;

import android.annotation.SystemApi;
import android.os.UserHandle;

@SystemApi
public interface OnRoleHoldersChangedListener {
    public void onRoleHoldersChanged(String var1, UserHandle var2);
}

