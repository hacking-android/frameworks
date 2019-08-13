/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.content.Context;
import android.webkit.WebViewFactory;

public abstract class WebViewDatabase {
    protected static final String LOGTAG = "webviewdatabase";

    public static WebViewDatabase getInstance(Context context) {
        return WebViewFactory.getProvider().getWebViewDatabase(context);
    }

    @Deprecated
    public abstract void clearFormData();

    public abstract void clearHttpAuthUsernamePassword();

    @Deprecated
    public abstract void clearUsernamePassword();

    public abstract String[] getHttpAuthUsernamePassword(String var1, String var2);

    @Deprecated
    public abstract boolean hasFormData();

    public abstract boolean hasHttpAuthUsernamePassword();

    @Deprecated
    public abstract boolean hasUsernamePassword();

    public abstract void setHttpAuthUsernamePassword(String var1, String var2, String var3, String var4);
}

