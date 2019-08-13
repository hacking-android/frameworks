/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.net.Uri;
import java.util.Map;

public interface WebResourceRequest {
    public String getMethod();

    public Map<String, String> getRequestHeaders();

    public Uri getUrl();

    public boolean hasGesture();

    public boolean isForMainFrame();

    public boolean isRedirect();
}

