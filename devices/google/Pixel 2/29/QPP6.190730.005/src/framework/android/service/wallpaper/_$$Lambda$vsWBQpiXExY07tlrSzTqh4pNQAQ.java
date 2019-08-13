/*
 * Decompiled with CFR 0.145.
 */
package android.service.wallpaper;

import android.service.wallpaper.WallpaperService;

public final class _$$Lambda$vsWBQpiXExY07tlrSzTqh4pNQAQ
implements Runnable {
    private final /* synthetic */ WallpaperService.Engine f$0;

    public /* synthetic */ _$$Lambda$vsWBQpiXExY07tlrSzTqh4pNQAQ(WallpaperService.Engine engine) {
        this.f$0 = engine;
    }

    @Override
    public final void run() {
        this.f$0.notifyColorsChanged();
    }
}

