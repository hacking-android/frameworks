/*
 * Decompiled with CFR 0.145.
 */
package android.appwidget;

import android.util.ArraySet;

public abstract class AppWidgetManagerInternal {
    public abstract ArraySet<String> getHostedWidgetPackages(int var1);

    public abstract void unlockUser(int var1);
}

