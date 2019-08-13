/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.accessibility;

import android.content.DialogInterface;
import com.android.internal.accessibility.AccessibilityShortcutController;

public final class _$$Lambda$AccessibilityShortcutController$cQtLiNhDc4H3BvMBZy00zj21oKg
implements DialogInterface.OnDismissListener {
    private final /* synthetic */ AccessibilityShortcutController.TtsPrompt f$0;

    public /* synthetic */ _$$Lambda$AccessibilityShortcutController$cQtLiNhDc4H3BvMBZy00zj21oKg(AccessibilityShortcutController.TtsPrompt ttsPrompt) {
        this.f$0 = ttsPrompt;
    }

    @Override
    public final void onDismiss(DialogInterface dialogInterface) {
        AccessibilityShortcutController.lambda$performTtsPrompt$2(this.f$0, dialogInterface);
    }
}

