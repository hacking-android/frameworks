/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.webkit.ValueCallback;
import android.webkit.WebViewFactory;
import java.util.Set;

public class GeolocationPermissions {
    public static GeolocationPermissions getInstance() {
        return WebViewFactory.getProvider().getGeolocationPermissions();
    }

    public void allow(String string2) {
    }

    public void clear(String string2) {
    }

    public void clearAll() {
    }

    public void getAllowed(String string2, ValueCallback<Boolean> valueCallback) {
    }

    public void getOrigins(ValueCallback<Set<String>> valueCallback) {
    }

    public static interface Callback {
        public void invoke(String var1, boolean var2, boolean var3);
    }

}

