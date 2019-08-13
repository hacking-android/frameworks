/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.graphics.Bitmap;

public abstract class WebHistoryItem
implements Cloneable {
    protected abstract WebHistoryItem clone();

    public abstract Bitmap getFavicon();

    @SystemApi
    @Deprecated
    public abstract int getId();

    public abstract String getOriginalUrl();

    public abstract String getTitle();

    public abstract String getUrl();
}

