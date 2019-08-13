/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.content.IntentFilter;
import com.android.internal.app.ChooserActivity;
import java.util.List;

public final class _$$Lambda$ChooserActivity$XR1YdxuJecnZUtHspyjpRELkDj8
implements Runnable {
    private final /* synthetic */ ChooserActivity f$0;
    private final /* synthetic */ IntentFilter f$1;
    private final /* synthetic */ List f$2;

    public /* synthetic */ _$$Lambda$ChooserActivity$XR1YdxuJecnZUtHspyjpRELkDj8(ChooserActivity chooserActivity, IntentFilter intentFilter, List list) {
        this.f$0 = chooserActivity;
        this.f$1 = intentFilter;
        this.f$2 = list;
    }

    @Override
    public final void run() {
        this.f$0.lambda$queryDirectShareTargets$1$ChooserActivity(this.f$1, this.f$2);
    }
}

