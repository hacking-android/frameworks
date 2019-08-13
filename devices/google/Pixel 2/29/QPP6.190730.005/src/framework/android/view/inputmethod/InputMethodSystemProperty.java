/*
 * Decompiled with CFR 0.145.
 */
package android.view.inputmethod;

import android.content.ComponentName;
import android.os.Build;
import android.os.SystemProperties;

public class InputMethodSystemProperty {
    public static final boolean MULTI_CLIENT_IME_ENABLED;
    public static final boolean PER_PROFILE_IME_ENABLED;
    private static final String PROP_DEBUG_MULTI_CLIENT_IME = "persist.debug.multi_client_ime";
    private static final String PROP_DEBUG_PER_PROFILE_IME = "persist.debug.per_profile_ime";
    private static final String PROP_PROD_MULTI_CLIENT_IME = "ro.sys.multi_client_ime";
    public static final ComponentName sMultiClientImeComponentName;

    static {
        sMultiClientImeComponentName = InputMethodSystemProperty.getMultiClientImeComponentName();
        boolean bl = sMultiClientImeComponentName != null;
        MULTI_CLIENT_IME_ENABLED = bl;
        PER_PROFILE_IME_ENABLED = MULTI_CLIENT_IME_ENABLED ? true : (Build.IS_DEBUGGABLE ? SystemProperties.getBoolean(PROP_DEBUG_PER_PROFILE_IME, true) : true);
    }

    private static ComponentName getMultiClientImeComponentName() {
        ComponentName componentName;
        if (Build.IS_DEBUGGABLE && (componentName = ComponentName.unflattenFromString(SystemProperties.get(PROP_DEBUG_MULTI_CLIENT_IME, ""))) != null) {
            return componentName;
        }
        return ComponentName.unflattenFromString(SystemProperties.get(PROP_PROD_MULTI_CLIENT_IME, ""));
    }
}

