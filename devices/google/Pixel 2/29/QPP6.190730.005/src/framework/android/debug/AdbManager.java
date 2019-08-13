/*
 * Decompiled with CFR 0.145.
 */
package android.debug;

import android.content.Context;
import android.debug.IAdbManager;

public class AdbManager {
    private static final String TAG = "AdbManager";
    private final Context mContext;
    private final IAdbManager mService;

    public AdbManager(Context context, IAdbManager iAdbManager) {
        this.mContext = context;
        this.mService = iAdbManager;
    }
}

