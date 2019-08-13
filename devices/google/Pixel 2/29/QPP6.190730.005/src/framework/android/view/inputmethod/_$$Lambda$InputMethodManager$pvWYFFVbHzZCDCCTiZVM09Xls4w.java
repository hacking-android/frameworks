/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.inputmethod.-$
 *  android.view.inputmethod.-$$Lambda
 *  android.view.inputmethod.-$$Lambda$InputMethodManager
 *  android.view.inputmethod.-$$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w
 */
package android.view.inputmethod;

import android.view.inputmethod.-$;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import java.util.function.ToIntFunction;

public final class _$$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w
implements ToIntFunction {
    public static final /* synthetic */ -$.Lambda.InputMethodManager.pvWYFFVbHzZCDCCTiZVM09Xls4w INSTANCE;

    static /* synthetic */ {
        INSTANCE = new _$$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w();
    }

    private /* synthetic */ _$$Lambda$InputMethodManager$pvWYFFVbHzZCDCCTiZVM09Xls4w() {
    }

    public final int applyAsInt(Object object) {
        return InputMethodManager.lambda$getShortcutInputMethodsAndSubtypes$2((InputMethodInfo)object);
    }
}

