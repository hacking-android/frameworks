/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm;

import android.content.ComponentName;

public final class ActivityPresentationInfo {
    public final ComponentName componentName;
    public final int displayId;
    public final int taskId;

    public ActivityPresentationInfo(int n, int n2, ComponentName componentName) {
        this.taskId = n;
        this.displayId = n2;
        this.componentName = componentName;
    }
}

