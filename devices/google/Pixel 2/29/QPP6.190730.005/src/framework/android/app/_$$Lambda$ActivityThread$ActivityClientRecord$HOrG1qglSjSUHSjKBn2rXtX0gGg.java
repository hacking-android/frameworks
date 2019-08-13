/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityThread;
import android.content.res.Configuration;
import android.view.ViewRootImpl;

public final class _$$Lambda$ActivityThread$ActivityClientRecord$HOrG1qglSjSUHSjKBn2rXtX0gGg
implements ViewRootImpl.ActivityConfigCallback {
    private final /* synthetic */ ActivityThread.ActivityClientRecord f$0;

    public /* synthetic */ _$$Lambda$ActivityThread$ActivityClientRecord$HOrG1qglSjSUHSjKBn2rXtX0gGg(ActivityThread.ActivityClientRecord activityClientRecord) {
        this.f$0 = activityClientRecord;
    }

    @Override
    public final void onConfigurationChanged(Configuration configuration, int n) {
        this.f$0.lambda$init$0$ActivityThread$ActivityClientRecord(configuration, n);
    }
}

