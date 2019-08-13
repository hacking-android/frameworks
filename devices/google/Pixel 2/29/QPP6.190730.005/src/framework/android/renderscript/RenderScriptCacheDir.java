/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import java.io.File;

public class RenderScriptCacheDir {
    @UnsupportedAppUsage
    static File mCacheDir;

    @UnsupportedAppUsage
    public static void setupDiskCache(File file) {
        mCacheDir = file;
    }
}

