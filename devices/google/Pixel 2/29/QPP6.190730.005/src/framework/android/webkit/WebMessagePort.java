/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.os.Handler;
import android.webkit.WebMessage;

public abstract class WebMessagePort {
    public abstract void close();

    public abstract void postMessage(WebMessage var1);

    public abstract void setWebMessageCallback(WebMessageCallback var1);

    public abstract void setWebMessageCallback(WebMessageCallback var1, Handler var2);

    public static abstract class WebMessageCallback {
        public void onMessage(WebMessagePort webMessagePort, WebMessage webMessage) {
        }
    }

}

