/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.ActivityThread;
import android.content.res.Configuration;
import android.view.ViewRootImpl;

public final class _$$Lambda$ActivityThread$Wg40iAoNYFxps_KmrqtgptTB054
implements ViewRootImpl.ConfigChangedCallback {
    private final /* synthetic */ ActivityThread f$0;

    public /* synthetic */ _$$Lambda$ActivityThread$Wg40iAoNYFxps_KmrqtgptTB054(ActivityThread activityThread) {
        this.f$0 = activityThread;
    }

    @Override
    public final void onConfigurationChanged(Configuration configuration) {
        this.f$0.lambda$attach$1$ActivityThread(configuration);
    }
}

