/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.util.SparseIntArray;
import com.android.internal.util.function.QuadFunction;
import com.android.internal.util.function.TriFunction;

public abstract class AppOpsManagerInternal {
    public abstract int checkOperationUnchecked(int var1, int var2, String var3);

    public abstract void setAllPkgModesToDefault(int var1, int var2);

    public abstract void setDeviceAndProfileOwners(SparseIntArray var1);

    public abstract void setUidMode(int var1, int var2, int var3);

    public static interface CheckOpsDelegate {
        public int checkAudioOperation(int var1, int var2, int var3, String var4, QuadFunction<Integer, Integer, Integer, String, Integer> var5);

        public int checkOperation(int var1, int var2, String var3, boolean var4, QuadFunction<Integer, Integer, String, Boolean, Integer> var5);

        public int noteOperation(int var1, int var2, String var3, TriFunction<Integer, Integer, String, Integer> var4);
    }

}

