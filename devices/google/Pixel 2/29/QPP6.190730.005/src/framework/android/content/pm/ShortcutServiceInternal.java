/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ShortcutInfo;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import java.util.List;

public abstract class ShortcutServiceInternal {
    public abstract void addListener(ShortcutChangeListener var1);

    public abstract Intent[] createShortcutIntents(int var1, String var2, String var3, String var4, int var5, int var6, int var7);

    public abstract ParcelFileDescriptor getShortcutIconFd(int var1, String var2, String var3, String var4, int var5);

    public abstract int getShortcutIconResId(int var1, String var2, String var3, String var4, int var5);

    public abstract List<ShortcutInfo> getShortcuts(int var1, String var2, long var3, String var5, List<String> var6, ComponentName var7, int var8, int var9, int var10, int var11);

    public abstract boolean hasShortcutHostPermission(int var1, String var2, int var3, int var4);

    public abstract boolean isForegroundDefaultLauncher(String var1, int var2);

    public abstract boolean isPinnedByCaller(int var1, String var2, String var3, String var4, int var5);

    public abstract boolean isRequestPinItemSupported(int var1, int var2);

    public abstract void pinShortcuts(int var1, String var2, String var3, List<String> var4, int var5);

    public abstract boolean requestPinAppWidget(String var1, AppWidgetProviderInfo var2, Bundle var3, IntentSender var4, int var5);

    public abstract void setShortcutHostPackage(String var1, String var2, int var3);

    public static interface ShortcutChangeListener {
        public void onShortcutChanged(String var1, int var2);
    }

}

