/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.view.accessibility.AccessibilityEvent;

public interface AccessibilityEventSource {
    public void sendAccessibilityEvent(int var1);

    public void sendAccessibilityEventUnchecked(AccessibilityEvent var1);
}

