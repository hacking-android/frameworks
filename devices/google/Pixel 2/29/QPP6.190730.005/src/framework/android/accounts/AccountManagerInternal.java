/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.os.RemoteCallback;

public abstract class AccountManagerInternal {
    public abstract void addOnAppPermissionChangeListener(OnAppPermissionChangeListener var1);

    public abstract byte[] backupAccountAccessPermissions(int var1);

    public abstract boolean hasAccountAccess(Account var1, int var2);

    public abstract void requestAccountAccess(Account var1, String var2, int var3, RemoteCallback var4);

    public abstract void restoreAccountAccessPermissions(byte[] var1, int var2);

    public static interface OnAppPermissionChangeListener {
        public void onAppPermissionChanged(Account var1, int var2);
    }

}

