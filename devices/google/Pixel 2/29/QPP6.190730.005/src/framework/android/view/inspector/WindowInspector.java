/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import android.view.View;
import android.view.WindowManagerGlobal;
import java.util.ArrayList;
import java.util.List;

public final class WindowInspector {
    private WindowInspector() {
    }

    public static List<View> getGlobalWindowViews() {
        return WindowManagerGlobal.getInstance().getWindowViews();
    }
}

