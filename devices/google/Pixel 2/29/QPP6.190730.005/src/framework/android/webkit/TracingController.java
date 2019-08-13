/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.webkit.TracingConfig;
import android.webkit.WebViewFactory;
import java.io.OutputStream;
import java.util.concurrent.Executor;

public abstract class TracingController {
    public static TracingController getInstance() {
        return WebViewFactory.getProvider().getTracingController();
    }

    public abstract boolean isTracing();

    public abstract void start(TracingConfig var1);

    public abstract boolean stop(OutputStream var1, Executor var2);
}

