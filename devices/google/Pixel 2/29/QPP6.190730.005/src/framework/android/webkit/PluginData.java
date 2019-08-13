/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import java.io.InputStream;
import java.util.Map;

@Deprecated
public final class PluginData {
    private long mContentLength;
    private Map<String, String[]> mHeaders;
    private int mStatusCode;
    private InputStream mStream;

    @Deprecated
    @UnsupportedAppUsage
    public PluginData(InputStream inputStream, long l, Map<String, String[]> map, int n) {
        this.mStream = inputStream;
        this.mContentLength = l;
        this.mHeaders = map;
        this.mStatusCode = n;
    }

    @Deprecated
    @UnsupportedAppUsage
    public long getContentLength() {
        return this.mContentLength;
    }

    @Deprecated
    @UnsupportedAppUsage
    public Map<String, String[]> getHeaders() {
        return this.mHeaders;
    }

    @Deprecated
    @UnsupportedAppUsage
    public InputStream getInputStream() {
        return this.mStream;
    }

    @Deprecated
    @UnsupportedAppUsage
    public int getStatusCode() {
        return this.mStatusCode;
    }
}

