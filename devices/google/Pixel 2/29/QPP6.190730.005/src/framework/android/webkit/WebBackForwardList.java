/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.webkit.WebHistoryItem;
import java.io.Serializable;

public abstract class WebBackForwardList
implements Cloneable,
Serializable {
    protected abstract WebBackForwardList clone();

    public abstract int getCurrentIndex();

    public abstract WebHistoryItem getCurrentItem();

    public abstract WebHistoryItem getItemAtIndex(int var1);

    public abstract int getSize();
}

