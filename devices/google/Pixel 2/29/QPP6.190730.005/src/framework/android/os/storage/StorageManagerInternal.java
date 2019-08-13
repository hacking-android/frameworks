/*
 * Decompiled with CFR 0.145.
 */
package android.os.storage;

import android.os.IVold;

public abstract class StorageManagerInternal {
    public abstract void addExternalStoragePolicy(ExternalStorageMountPolicy var1);

    public abstract void addResetListener(ResetListener var1);

    public abstract int getExternalStorageMountMode(int var1, String var2);

    public abstract void onAppOpsChanged(int var1, int var2, String var3, int var4);

    public abstract void onExternalStoragePolicyChanged(int var1, String var2);

    public static interface ExternalStorageMountPolicy {
        public int getMountMode(int var1, String var2);

        public boolean hasExternalStorage(int var1, String var2);
    }

    public static interface ResetListener {
        public void onReset(IVold var1);
    }

}

