/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import com.android.internal.app.AccessibilityButtonChooserActivity;
import com.android.internal.widget.ResolverDrawerLayout;

public final class _$$Lambda$EK3sgUmlvAVQupMeTV9feOrWuPE
implements ResolverDrawerLayout.OnDismissedListener {
    private final /* synthetic */ AccessibilityButtonChooserActivity f$0;

    public /* synthetic */ _$$Lambda$EK3sgUmlvAVQupMeTV9feOrWuPE(AccessibilityButtonChooserActivity accessibilityButtonChooserActivity) {
        this.f$0 = accessibilityButtonChooserActivity;
    }

    @Override
    public final void onDismissed() {
        this.f$0.finish();
    }
}

