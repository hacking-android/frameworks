/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.media.IMediaHTTPConnection;
import android.media.IMediaHTTPService;
import android.media.MediaHTTPConnection;
import android.os.IBinder;
import android.util.Log;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.List;

public class MediaHTTPService
extends IMediaHTTPService.Stub {
    private static final String TAG = "MediaHTTPService";
    private Boolean mCookieStoreInitialized = new Boolean(false);
    private List<HttpCookie> mCookies;

    public MediaHTTPService(List<HttpCookie> list) {
        this.mCookies = list;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MediaHTTPService(");
        stringBuilder.append(this);
        stringBuilder.append("): Cookies: ");
        stringBuilder.append(list);
        Log.v(TAG, stringBuilder.toString());
    }

    @UnsupportedAppUsage
    static IBinder createHttpServiceBinderIfNecessary(String string2) {
        return MediaHTTPService.createHttpServiceBinderIfNecessary(string2, null);
    }

    static IBinder createHttpServiceBinderIfNecessary(String string2, List<HttpCookie> list) {
        if (!string2.startsWith("http://") && !string2.startsWith("https://")) {
            if (string2.startsWith("widevine://")) {
                Log.d(TAG, "Widevine classic is no longer supported");
            }
            return null;
        }
        return new MediaHTTPService(list).asBinder();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public IMediaHTTPConnection makeHTTPConnection() {
        Boolean bl = this.mCookieStoreInitialized;
        synchronized (bl) {
            if (!this.mCookieStoreInitialized.booleanValue()) {
                Object object;
                CookieHandler cookieHandler = CookieHandler.getDefault();
                if (cookieHandler == null) {
                    cookieHandler = new CookieManager();
                    CookieHandler.setDefault(cookieHandler);
                    object = new StringBuilder();
                    ((StringBuilder)object).append("makeHTTPConnection: CookieManager created: ");
                    ((StringBuilder)object).append(cookieHandler);
                    Log.v(TAG, ((StringBuilder)object).toString());
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("makeHTTPConnection: CookieHandler (");
                    ((StringBuilder)object).append(cookieHandler);
                    ((StringBuilder)object).append(") exists.");
                    Log.v(TAG, ((StringBuilder)object).toString());
                }
                if (this.mCookies != null) {
                    if (!(cookieHandler instanceof CookieManager)) {
                        Log.w(TAG, "makeHTTPConnection: The installed CookieHandler is not a CookieManager. Can\u2019t add the provided cookies to the cookie store.");
                    } else {
                        CookieStore cookieStore = ((CookieManager)cookieHandler).getCookieStore();
                        for (HttpCookie httpCookie : this.mCookies) {
                            try {
                                cookieStore.add(null, httpCookie);
                            }
                            catch (Exception exception) {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("makeHTTPConnection: CookieStore.add");
                                stringBuilder.append(exception);
                                Log.v(TAG, stringBuilder.toString());
                            }
                        }
                    }
                }
                this.mCookieStoreInitialized = true;
                object = new StringBuilder();
                ((StringBuilder)object).append("makeHTTPConnection(");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append("): cookieHandler: ");
                ((StringBuilder)object).append(cookieHandler);
                ((StringBuilder)object).append(" Cookies: ");
                ((StringBuilder)object).append(this.mCookies);
                Log.v(TAG, ((StringBuilder)object).toString());
            }
            return new MediaHTTPConnection();
        }
    }
}

