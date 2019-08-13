/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.StopWatchMap;

class GLFrameTimer {
    private static StopWatchMap mTimer = null;

    GLFrameTimer() {
    }

    public static StopWatchMap get() {
        if (mTimer == null) {
            mTimer = new StopWatchMap();
        }
        return mTimer;
    }
}

