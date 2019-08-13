/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.accessibility;

import android.content.DialogInterface;
import com.android.internal.accessibility.AccessibilityShortcutController;

public final class _$$Lambda$AccessibilityShortcutController$2NcDVJHkpsPbwr45v1_NfIM8row
implements DialogInterface.OnClickListener {
    private final /* synthetic */ AccessibilityShortcutController f$0;
    private final /* synthetic */ int f$1;

    public /* synthetic */ _$$Lambda$AccessibilityShortcutController$2NcDVJHkpsPbwr45v1_NfIM8row(AccessibilityShortcutController accessibilityShortcutController, int n) {
        this.f$0 = accessibilityShortcutController;
        this.f$1 = n;
    }

    @Override
    public final void onClick(DialogInterface dialogInterface, int n) {
        this.f$0.lambda$createShortcutWarningDialog$0$AccessibilityShortcutController(this.f$1, dialogInterface, n);
    }
}

