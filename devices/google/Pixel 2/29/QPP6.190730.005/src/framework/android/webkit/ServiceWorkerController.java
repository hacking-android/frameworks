/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.webkit.ServiceWorkerClient;
import android.webkit.ServiceWorkerWebSettings;
import android.webkit.WebViewFactory;

public abstract class ServiceWorkerController {
    public static ServiceWorkerController getInstance() {
        return WebViewFactory.getProvider().getServiceWorkerController();
    }

    public abstract ServiceWorkerWebSettings getServiceWorkerWebSettings();

    public abstract void setServiceWorkerClient(ServiceWorkerClient var1);
}

