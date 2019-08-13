/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.UnsupportedAppUsage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

@Deprecated
public final class CacheManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    @Deprecated
    @UnsupportedAppUsage
    public static boolean cacheDisabled() {
        return false;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean endCacheTransaction() {
        return false;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static CacheResult getCacheFile(String string2, Map<String, String> map) {
        return null;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static File getCacheFileBaseDir() {
        return null;
    }

    @UnsupportedAppUsage
    static void saveCacheFile(String string2, long l, CacheResult cacheResult) {
        try {
            cacheResult.outStream.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public static void saveCacheFile(String string2, CacheResult cacheResult) {
        CacheManager.saveCacheFile(string2, 0L, cacheResult);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean startCacheTransaction() {
        return false;
    }

    @Deprecated
    public static class CacheResult {
        @UnsupportedAppUsage
        long contentLength;
        @UnsupportedAppUsage
        String contentdisposition;
        @UnsupportedAppUsage
        String crossDomain;
        @UnsupportedAppUsage
        String encoding;
        @UnsupportedAppUsage
        String etag;
        @UnsupportedAppUsage
        long expires;
        @UnsupportedAppUsage
        String expiresString;
        @UnsupportedAppUsage
        int httpStatusCode;
        @UnsupportedAppUsage
        InputStream inStream;
        @UnsupportedAppUsage
        String lastModified;
        @UnsupportedAppUsage
        String localPath;
        @UnsupportedAppUsage
        String location;
        @UnsupportedAppUsage
        String mimeType;
        @UnsupportedAppUsage
        File outFile;
        @UnsupportedAppUsage
        OutputStream outStream;

        @UnsupportedAppUsage
        public String getContentDisposition() {
            return this.contentdisposition;
        }

        @UnsupportedAppUsage
        public long getContentLength() {
            return this.contentLength;
        }

        @UnsupportedAppUsage
        public String getETag() {
            return this.etag;
        }

        @UnsupportedAppUsage
        public String getEncoding() {
            return this.encoding;
        }

        @UnsupportedAppUsage
        public long getExpires() {
            return this.expires;
        }

        @UnsupportedAppUsage
        public String getExpiresString() {
            return this.expiresString;
        }

        @UnsupportedAppUsage
        public int getHttpStatusCode() {
            return this.httpStatusCode;
        }

        @UnsupportedAppUsage
        public InputStream getInputStream() {
            return this.inStream;
        }

        @UnsupportedAppUsage
        public String getLastModified() {
            return this.lastModified;
        }

        @UnsupportedAppUsage
        public String getLocalPath() {
            return this.localPath;
        }

        @UnsupportedAppUsage
        public String getLocation() {
            return this.location;
        }

        @UnsupportedAppUsage
        public String getMimeType() {
            return this.mimeType;
        }

        @UnsupportedAppUsage
        public OutputStream getOutputStream() {
            return this.outStream;
        }

        public void setContentLength(long l) {
            this.contentLength = l;
        }

        @UnsupportedAppUsage
        public void setEncoding(String string2) {
            this.encoding = string2;
        }

        @UnsupportedAppUsage
        public void setInputStream(InputStream inputStream) {
            this.inStream = inputStream;
        }
    }

}

