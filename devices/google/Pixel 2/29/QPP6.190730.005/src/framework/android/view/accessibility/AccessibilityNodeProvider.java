/*
 * Decompiled with CFR 0.145.
 */
package android.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.List;

public abstract class AccessibilityNodeProvider {
    public static final int HOST_VIEW_ID = -1;

    public void addExtraDataToAccessibilityNodeInfo(int n, AccessibilityNodeInfo accessibilityNodeInfo, String string2, Bundle bundle) {
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo(int n) {
        return null;
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String string2, int n) {
        return null;
    }

    public AccessibilityNodeInfo findFocus(int n) {
        return null;
    }

    public boolean performAction(int n, int n2, Bundle bundle) {
        return false;
    }
}

