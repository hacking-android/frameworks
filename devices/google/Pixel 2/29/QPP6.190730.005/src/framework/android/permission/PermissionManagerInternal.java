/*
 * Decompiled with CFR 0.145.
 */
package android.permission;

import android.os.UserHandle;

public abstract class PermissionManagerInternal {
    public abstract void addOnRuntimePermissionStateChangedListener(OnRuntimePermissionStateChangedListener var1);

    public abstract byte[] backupRuntimePermissions(UserHandle var1);

    public abstract void removeOnRuntimePermissionStateChangedListener(OnRuntimePermissionStateChangedListener var1);

    public abstract void restoreDelayedRuntimePermissions(String var1, UserHandle var2);

    public abstract void restoreRuntimePermissions(byte[] var1, UserHandle var2);

    public static interface OnRuntimePermissionStateChangedListener {
        public void onRuntimePermissionStateChanged(String var1, int var2);
    }

}

