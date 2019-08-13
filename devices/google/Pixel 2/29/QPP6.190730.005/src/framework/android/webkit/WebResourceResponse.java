/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.Map;

public class WebResourceResponse {
    private String mEncoding;
    @UnsupportedAppUsage
    private boolean mImmutable;
    private InputStream mInputStream;
    private String mMimeType;
    private String mReasonPhrase;
    private Map<String, String> mResponseHeaders;
    @UnsupportedAppUsage
    private int mStatusCode;

    public WebResourceResponse(String string2, String string3, int n, String string4, Map<String, String> map, InputStream inputStream) {
        this(string2, string3, inputStream);
        this.setStatusCodeAndReasonPhrase(n, string4);
        this.setResponseHeaders(map);
    }

    public WebResourceResponse(String string2, String string3, InputStream inputStream) {
        this.mMimeType = string2;
        this.mEncoding = string3;
        this.setData(inputStream);
    }

    @SystemApi
    public WebResourceResponse(boolean bl, String string2, String string3, int n, String string4, Map<String, String> map, InputStream inputStream) {
        this.mImmutable = bl;
        this.mMimeType = string2;
        this.mEncoding = string3;
        this.mStatusCode = n;
        this.mReasonPhrase = string4;
        this.mResponseHeaders = map;
        this.mInputStream = inputStream;
    }

    private void checkImmutable() {
        if (!this.mImmutable) {
            return;
        }
        throw new IllegalStateException("This WebResourceResponse instance is immutable");
    }

    public InputStream getData() {
        return this.mInputStream;
    }

    public String getEncoding() {
        return this.mEncoding;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public String getReasonPhrase() {
        return this.mReasonPhrase;
    }

    public Map<String, String> getResponseHeaders() {
        return this.mResponseHeaders;
    }

    public int getStatusCode() {
        return this.mStatusCode;
    }

    public void setData(InputStream inputStream) {
        this.checkImmutable();
        if (inputStream != null && StringBufferInputStream.class.isAssignableFrom(inputStream.getClass())) {
            throw new IllegalArgumentException("StringBufferInputStream is deprecated and must not be passed to a WebResourceResponse");
        }
        this.mInputStream = inputStream;
    }

    public void setEncoding(String string2) {
        this.checkImmutable();
        this.mEncoding = string2;
    }

    public void setMimeType(String string2) {
        this.checkImmutable();
        this.mMimeType = string2;
    }

    public void setResponseHeaders(Map<String, String> map) {
        this.checkImmutable();
        this.mResponseHeaders = map;
    }

    public void setStatusCodeAndReasonPhrase(int n, String string2) {
        this.checkImmutable();
        if (n >= 100) {
            if (n <= 599) {
                if (n > 299 && n < 400) {
                    throw new IllegalArgumentException("statusCode can't be in the [300, 399] range.");
                }
                if (string2 != null) {
                    if (!string2.trim().isEmpty()) {
                        for (int i = 0; i < string2.length(); ++i) {
                            if (string2.charAt(i) <= '') {
                                continue;
                            }
                            throw new IllegalArgumentException("reasonPhrase can't contain non-ASCII characters.");
                        }
                        this.mStatusCode = n;
                        this.mReasonPhrase = string2;
                        return;
                    }
                    throw new IllegalArgumentException("reasonPhrase can't be empty.");
                }
                throw new IllegalArgumentException("reasonPhrase can't be null.");
            }
            throw new IllegalArgumentException("statusCode can't be greater than 599.");
        }
        throw new IllegalArgumentException("statusCode can't be less than 100.");
    }
}

