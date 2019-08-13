/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.IMediaHTTPConnection;
import android.net.NetworkUtils;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class MediaHTTPConnection
extends IMediaHTTPConnection.Stub {
    private static final int CONNECT_TIMEOUT_MS = 30000;
    private static final int HTTP_TEMP_REDIRECT = 307;
    private static final int MAX_REDIRECTS = 20;
    private static final String TAG = "MediaHTTPConnection";
    private static final boolean VERBOSE = false;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private boolean mAllowCrossDomainRedirect = true;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private boolean mAllowCrossProtocolRedirect = true;
    @UnsupportedAppUsage
    private volatile HttpURLConnection mConnection = null;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private long mCurrentOffset = -1L;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private Map<String, String> mHeaders = null;
    @GuardedBy(value={"this"})
    private InputStream mInputStream = null;
    private long mNativeContext;
    private final AtomicInteger mNumDisconnectingThreads = new AtomicInteger(0);
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private long mTotalSize = -1L;
    @UnsupportedAppUsage
    @GuardedBy(value={"this"})
    private URL mURL = null;

    static {
        System.loadLibrary("media_jni");
        MediaHTTPConnection.native_init();
    }

    @UnsupportedAppUsage
    public MediaHTTPConnection() {
        if (CookieHandler.getDefault() == null) {
            Log.w(TAG, "MediaHTTPConnection: Unexpected. No CookieHandler found.");
        }
        this.native_setup();
    }

    private Map<String, String> convertHeaderStringToMap(String string2) {
        synchronized (this) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for (String string3 : string2.split("\r\n")) {
                int n = string3.indexOf(":");
                if (n < 0) continue;
                string2 = string3.substring(0, n);
                string3 = string3.substring(n + 1);
                if (this.filterOutInternalHeaders(string2, string3)) continue;
                hashMap.put(string2, string3);
            }
            return hashMap;
        }
    }

    private boolean filterOutInternalHeaders(String string2, String string3) {
        synchronized (this) {
            if ("android-allow-cross-domain-redirect".equalsIgnoreCase(string2)) {
                this.mAllowCrossProtocolRedirect = this.mAllowCrossDomainRedirect = MediaHTTPConnection.parseBoolean(string3);
                return true;
            }
            return false;
        }
    }

    private static final boolean isLocalHost(URL object) {
        block6 : {
            if (object == null) {
                return false;
            }
            if ((object = ((URL)object).getHost()) == null) {
                return false;
            }
            if (!((String)object).equalsIgnoreCase("localhost")) break block6;
            return true;
        }
        try {
            boolean bl = NetworkUtils.numericToInetAddress((String)object).isLoopbackAddress();
            if (bl) {
                return true;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return false;
    }

    private final native void native_finalize();

    private final native IBinder native_getIMemory();

    private static final native void native_init();

    private final native int native_readAt(long var1, int var3);

    private final native void native_setup();

    private static boolean parseBoolean(String string2) {
        boolean bl = true;
        boolean bl2 = true;
        try {
            long l = Long.parseLong(string2);
            if (l == 0L) {
                bl2 = false;
            }
            return bl2;
        }
        catch (NumberFormatException numberFormatException) {
            bl2 = !"true".equalsIgnoreCase(string2) && !"yes".equalsIgnoreCase(string2) ? false : bl;
            return bl2;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private int readAt(long var1_1, byte[] var3_2, int var4_9) {
        block14 : {
            // MONITORENTER : this
            var5_10 = new StrictMode.ThreadPolicy.Builder();
            StrictMode.setThreadPolicy(var5_10.permitAll().build());
            try {
                var6_13 = this.mCurrentOffset;
                if (var1_1 == var6_13) ** GOTO lbl10
                try {
                    this.seekTo(var1_1);
lbl10: // 2 sources:
                    var9_15 = var8_14 = this.mInputStream.read((byte[])var3_2, 0, var4_9);
                    if (var8_14 == -1) {
                        var9_15 = 0;
                    }
                    this.mCurrentOffset += (long)var9_15;
                    // MONITOREXIT : this
                    return var9_15;
                }
                catch (IOException var3_3) {
                    return -1;
                }
                catch (UnknownServiceException var3_4) {
                    break block14;
                }
            }
            catch (Exception var3_5) {
                // MONITOREXIT : this
                return -1;
            }
            catch (IOException var3_6) {
                // empty catch block
            }
            // MONITOREXIT : this
            return -1;
            catch (UnknownServiceException var3_7) {
                // empty catch block
            }
        }
        var5_10 = new StringBuilder();
        var5_10.append("readAt ");
        var5_10.append(var1_1);
        var5_10.append(" / ");
        var5_10.append(var4_9);
        var5_10.append(" => ");
        var5_10.append(var3_8);
        Log.w("MediaHTTPConnection", var5_10.toString());
        // MONITOREXIT : this
        return -1010;
        catch (NoRouteToHostException var5_11) {
            var3_2 = new StringBuilder();
            var3_2.append("readAt ");
            var3_2.append(var1_1);
            var3_2.append(" / ");
            var3_2.append(var4_9);
            var3_2.append(" => ");
            var3_2.append(var5_11);
            Log.w("MediaHTTPConnection", var3_2.toString());
            // MONITOREXIT : this
            return -1010;
        }
        catch (ProtocolException var5_12) {
            var3_2 = new StringBuilder();
            var3_2.append("readAt ");
            var3_2.append(var1_1);
            var3_2.append(" / ");
            var3_2.append(var4_9);
            var3_2.append(" => ");
            var3_2.append(var5_12);
            Log.w("MediaHTTPConnection", var3_2.toString());
            // MONITOREXIT : this
            return -1010;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void seekTo(long l) throws IOException {
        synchronized (this) {
            this.teardownConnection();
            try {
                Object object3 = this.mURL;
                boolean bl = MediaHTTPConnection.isLocalHost((URL)object3);
                int n = 0;
                while (this.mNumDisconnectingThreads.get() <= 0) {
                    this.mConnection = bl ? (HttpURLConnection)((URL)object3).openConnection(Proxy.NO_PROXY) : (HttpURLConnection)((URL)object3).openConnection();
                    if (this.mNumDisconnectingThreads.get() <= 0) {
                        int n2;
                        Object object2;
                        this.mConnection.setConnectTimeout(30000);
                        this.mConnection.setInstanceFollowRedirects(this.mAllowCrossDomainRedirect);
                        if (this.mHeaders != null) {
                            for (Object object3 : this.mHeaders.entrySet()) {
                                this.mConnection.setRequestProperty((String)object3.getKey(), (String)object3.getValue());
                            }
                        }
                        if (l > 0L) {
                            object2 = this.mConnection;
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("bytes=");
                            ((StringBuilder)object3).append(l);
                            ((StringBuilder)object3).append("-");
                            ((URLConnection)object2).setRequestProperty("Range", ((StringBuilder)object3).toString());
                        }
                        if ((n2 = this.mConnection.getResponseCode()) != 300 && n2 != 301 && n2 != 302 && n2 != 303 && n2 != 307) {
                            if (this.mAllowCrossDomainRedirect) {
                                this.mURL = this.mConnection.getURL();
                            }
                            if (n2 == 206) {
                                object3 = this.mConnection.getHeaderField("Content-Range");
                                this.mTotalSize = -1L;
                                if (object3 != null && (n = ((String)object3).lastIndexOf(47)) >= 0) {
                                    object3 = ((String)object3).substring(n + 1);
                                    try {
                                        this.mTotalSize = Long.parseLong((String)object3);
                                    }
                                    catch (NumberFormatException numberFormatException) {}
                                }
                            } else {
                                if (n2 != 200) {
                                    object3 = new IOException();
                                    throw object3;
                                }
                                this.mTotalSize = this.mConnection.getContentLength();
                            }
                            if (l > 0L && n2 != 206) {
                                object3 = new ProtocolException();
                                throw object3;
                            }
                            this.mInputStream = object3 = new BufferedInputStream(this.mConnection.getInputStream());
                            this.mCurrentOffset = l;
                            return;
                        }
                        if (++n <= 20) {
                            object3 = this.mConnection.getRequestMethod();
                            if (n2 == 307 && !((String)object3).equals("GET") && !((String)object3).equals("HEAD")) {
                                object3 = new NoRouteToHostException("Invalid redirect");
                                throw object3;
                            }
                            object2 = this.mConnection.getHeaderField("Location");
                            if (object2 == null) {
                                object3 = new NoRouteToHostException("Invalid redirect");
                                throw object3;
                            }
                            object3 = new URL(this.mURL, (String)object2);
                            if (!((URL)object3).getProtocol().equals("https") && !((URL)object3).getProtocol().equals("http")) {
                                object3 = new NoRouteToHostException("Unsupported protocol redirect");
                                throw object3;
                            }
                            boolean bl2 = this.mURL.getProtocol().equals(((URL)object3).getProtocol());
                            if (!this.mAllowCrossProtocolRedirect && !bl2) {
                                object3 = new NoRouteToHostException("Cross-protocol redirects are disallowed");
                                throw object3;
                            }
                            bl2 = this.mURL.getHost().equals(((URL)object3).getHost());
                            if (!this.mAllowCrossDomainRedirect && !bl2) {
                                object3 = new NoRouteToHostException("Cross-domain redirects are disallowed");
                                throw object3;
                            }
                            if (n2 == 307) continue;
                            this.mURL = object3;
                            continue;
                        }
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Too many redirects: ");
                        ((StringBuilder)object3).append(n);
                        object2 = new NoRouteToHostException(((StringBuilder)object3).toString());
                        throw object2;
                    }
                    object3 = new IOException("concurrently disconnecting");
                    throw object3;
                }
                object3 = new IOException("concurrently disconnecting");
                throw object3;
            }
            catch (IOException iOException) {
                this.mTotalSize = -1L;
                this.teardownConnection();
                this.mCurrentOffset = -1L;
                throw iOException;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void teardownConnection() {
        synchronized (this) {
            if (this.mConnection != null) {
                InputStream inputStream = this.mInputStream;
                if (inputStream != null) {
                    try {
                        this.mInputStream.close();
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    this.mInputStream = null;
                }
                this.mConnection.disconnect();
                this.mConnection = null;
                this.mCurrentOffset = -1L;
            }
            return;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    @Override
    public IBinder connect(String object, String string2) {
        // MONITORENTER : this
        this.disconnect();
        try {
            URL uRL;
            void var2_4;
            this.mAllowCrossDomainRedirect = true;
            this.mURL = uRL = new URL((String)object);
            this.mHeaders = this.convertHeaderStringToMap((String)var2_4);
            object = this.native_getIMemory();
        }
        catch (MalformedURLException malformedURLException) {
            return null;
        }
        return object;
        catch (MalformedURLException malformedURLException) {
            // empty catch block
        }
        // MONITOREXIT : this
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    @Override
    public void disconnect() {
        this.mNumDisconnectingThreads.incrementAndGet();
        try {
            HttpURLConnection httpURLConnection = this.mConnection;
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
            synchronized (this) {
                this.teardownConnection();
                this.mHeaders = null;
                this.mURL = null;
            }
        }
        catch (Throwable throwable) {
            this.mNumDisconnectingThreads.decrementAndGet();
            throw throwable;
        }
        this.mNumDisconnectingThreads.decrementAndGet();
    }

    protected void finalize() {
        this.native_finalize();
    }

    @UnsupportedAppUsage
    @Override
    public String getMIMEType() {
        synchronized (this) {
            Object object = this.mConnection;
            if (object == null) {
                this.seekTo(0L);
            }
            object = this.mConnection.getContentType();
            return object;
        }
    }

    @Override
    public long getSize() {
        synchronized (this) {
            HttpURLConnection httpURLConnection = this.mConnection;
            if (httpURLConnection == null) {
                this.seekTo(0L);
            }
            long l = this.mTotalSize;
            return l;
        }
    }

    @UnsupportedAppUsage
    @Override
    public String getUri() {
        synchronized (this) {
            String string2 = this.mURL.toString();
            return string2;
        }
    }

    @UnsupportedAppUsage
    @Override
    public int readAt(long l, int n) {
        synchronized (this) {
            n = this.native_readAt(l, n);
            return n;
        }
    }
}

