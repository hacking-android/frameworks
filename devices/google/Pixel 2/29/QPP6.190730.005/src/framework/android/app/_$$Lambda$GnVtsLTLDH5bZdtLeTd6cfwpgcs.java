/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.-$
 *  android.app.-$$Lambda
 *  android.app.-$$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs
 */
package android.app;

import android.app.-$;
import android.app.UiAutomation;
import android.view.accessibility.AccessibilityEvent;
import java.util.function.BiConsumer;

public final class _$$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs
implements BiConsumer {
    public static final /* synthetic */ -$.Lambda.GnVtsLTLDH5bZdtLeTd6cfwpgcs INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs();
    }

    private /* synthetic */ _$$Lambda$GnVtsLTLDH5bZdtLeTd6cfwpgcs() {
    }

    public final void accept(Object object, Object object2) {
        ((UiAutomation.OnAccessibilityEventListener)object).onAccessibilityEvent((AccessibilityEvent)object2);
    }
}

