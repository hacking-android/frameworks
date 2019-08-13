/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.net.Uri;
import com.android.internal.app.ChooserActivity;

public final class _$$Lambda$ChooserActivity$ContentPreviewCoordinator$4EA4_6wC7DBv77gLolqI2_lsDQI
implements Runnable {
    private final /* synthetic */ ChooserActivity.ContentPreviewCoordinator f$0;
    private final /* synthetic */ Uri f$1;
    private final /* synthetic */ int f$2;
    private final /* synthetic */ int f$3;

    public /* synthetic */ _$$Lambda$ChooserActivity$ContentPreviewCoordinator$4EA4_6wC7DBv77gLolqI2_lsDQI(ChooserActivity.ContentPreviewCoordinator contentPreviewCoordinator, Uri uri, int n, int n2) {
        this.f$0 = contentPreviewCoordinator;
        this.f$1 = uri;
        this.f$2 = n;
        this.f$3 = n2;
    }

    @Override
    public final void run() {
        this.f$0.lambda$loadUriIntoView$0$ChooserActivity$ContentPreviewCoordinator(this.f$1, this.f$2, this.f$3);
    }
}

