/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.ClientSessionContext
 *  com.android.org.conscrypt.FileClientSessionCache
 *  com.android.org.conscrypt.SSLClientSessionCache
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.util.Log;
import com.android.org.conscrypt.ClientSessionContext;
import com.android.org.conscrypt.FileClientSessionCache;
import com.android.org.conscrypt.SSLClientSessionCache;
import java.io.File;
import java.io.IOException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSessionContext;

public final class SSLSessionCache {
    private static final String TAG = "SSLSessionCache";
    @UnsupportedAppUsage
    final SSLClientSessionCache mSessionCache;

    public SSLSessionCache(Context context) {
        File file = context.getDir("sslcache", 0);
        context = null;
        try {
            SSLClientSessionCache sSLClientSessionCache = FileClientSessionCache.usingDirectory((File)file);
            context = sSLClientSessionCache;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to create SSL session cache in ");
            stringBuilder.append(file);
            Log.w(TAG, stringBuilder.toString(), iOException);
        }
        this.mSessionCache = context;
    }

    public SSLSessionCache(File file) throws IOException {
        this.mSessionCache = FileClientSessionCache.usingDirectory((File)file);
    }

    public SSLSessionCache(Object object) {
        this.mSessionCache = (SSLClientSessionCache)object;
    }

    public static void install(SSLSessionCache object, SSLContext sSLContext) {
        SSLSessionContext sSLSessionContext = sSLContext.getClientSessionContext();
        if (sSLSessionContext instanceof ClientSessionContext) {
            sSLContext = (ClientSessionContext)sSLSessionContext;
            object = object == null ? null : ((SSLSessionCache)object).mSessionCache;
            sSLContext.setPersistentCache((SSLClientSessionCache)object);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Incompatible SSLContext: ");
        ((StringBuilder)object).append(sSLContext);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }
}

