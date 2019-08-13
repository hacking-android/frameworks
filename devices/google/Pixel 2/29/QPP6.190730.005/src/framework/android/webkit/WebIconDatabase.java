/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.webkit.WebViewFactory;

@Deprecated
public abstract class WebIconDatabase {
    public static WebIconDatabase getInstance() {
        return WebViewFactory.getProvider().getWebIconDatabase();
    }

    @SystemApi
    public abstract void bulkRequestIconForPageUrl(ContentResolver var1, String var2, IconListener var3);

    public abstract void close();

    public abstract void open(String var1);

    public abstract void releaseIconForPageUrl(String var1);

    public abstract void removeAllIcons();

    public abstract void requestIconForPageUrl(String var1, IconListener var2);

    public abstract void retainIconForPageUrl(String var1);

    @Deprecated
    public static interface IconListener {
        public void onReceivedIcon(String var1, Bitmap var2);
    }

}

