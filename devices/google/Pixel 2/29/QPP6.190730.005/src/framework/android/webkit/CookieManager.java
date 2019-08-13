/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.net.WebAddress;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewFactory;

public abstract class CookieManager {
    public static boolean allowFileSchemeCookies() {
        return CookieManager.getInstance().allowFileSchemeCookiesImpl();
    }

    public static CookieManager getInstance() {
        return WebViewFactory.getProvider().getCookieManager();
    }

    public static void setAcceptFileSchemeCookies(boolean bl) {
        CookieManager.getInstance().setAcceptFileSchemeCookiesImpl(bl);
    }

    public abstract boolean acceptCookie();

    public abstract boolean acceptThirdPartyCookies(WebView var1);

    @SystemApi
    protected abstract boolean allowFileSchemeCookiesImpl();

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("doesn't implement Cloneable");
    }

    public abstract void flush();

    @SystemApi
    public String getCookie(WebAddress object) {
        synchronized (this) {
            object = this.getCookie(((WebAddress)object).toString());
            return object;
        }
    }

    public abstract String getCookie(String var1);

    @SystemApi
    public abstract String getCookie(String var1, boolean var2);

    public abstract boolean hasCookies();

    @SystemApi
    public abstract boolean hasCookies(boolean var1);

    @Deprecated
    public abstract void removeAllCookie();

    public abstract void removeAllCookies(ValueCallback<Boolean> var1);

    @Deprecated
    public abstract void removeExpiredCookie();

    @Deprecated
    public abstract void removeSessionCookie();

    public abstract void removeSessionCookies(ValueCallback<Boolean> var1);

    public abstract void setAcceptCookie(boolean var1);

    @SystemApi
    protected abstract void setAcceptFileSchemeCookiesImpl(boolean var1);

    public abstract void setAcceptThirdPartyCookies(WebView var1, boolean var2);

    public abstract void setCookie(String var1, String var2);

    public abstract void setCookie(String var1, String var2, ValueCallback<Boolean> var3);
}

