/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.accessibility;

import android.content.DialogInterface;
import com.android.internal.accessibility.AccessibilityShortcutController;

public final class _$$Lambda$AccessibilityShortcutController$T96D356_n5VObNOonEIYV8s83Fc
implements DialogInterface.OnCancelListener {
    private final /* synthetic */ AccessibilityShortcutController f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$AccessibilityShortcutController$T96D356_n5VObNOonEIYV8s83Fc(AccessibilityShortcutController accessibilityShortcutController, int n) {
        this.f$0 = accessibilityShortcutController;
        this.f$1 = n;
    }

    @Override
    public final void onCancel(DialogInterface dialogInterface) {
        this.f$0.lambda$createShortcutWarningDialog$1$AccessibilityShortcutController(this.f$1, dialogInterface);
    }
}

