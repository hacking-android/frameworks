/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.WallpaperColors;
import android.app.WallpaperManager;
import android.util.Pair;

public final class _$$Lambda$WallpaperManager$Globals$1AcnQUORvPlCjJoNqdxfQT4o4Nw
implements Runnable {
    private final /* synthetic */ WallpaperManager.Globals f$0;
    private final /* synthetic */ Pair f$1;
    private final /* synthetic */ WallpaperColors f$2;
    private final /* synthetic */ int f$3;
    private final /* synthetic */ int f$4;

    public /* synthetic */ _$$Lambda$WallpaperManager$Globals$1AcnQUORvPlCjJoNqdxfQT4o4Nw(WallpaperManager.Globals globals, Pair pair, WallpaperColors wallpaperColors, int n, int n2) {
        this.f$0 = globals;
        this.f$1 = pair;
        this.f$2 = wallpaperColors;
        this.f$3 = n;
        this.f$4 = n2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$onWallpaperColorsChanged$1$WallpaperManager$Globals(this.f$1, this.f$2, this.f$3, this.f$4);
    }
}

