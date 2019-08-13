/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.tls;

import com.android.okhttp.internal.tls.TrustRootIndex;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

public final class AndroidTrustRootIndex
implements TrustRootIndex {
    private final Method findByIssuerAndSignatureMethod;
    private final X509TrustManager trustManager;

    public AndroidTrustRootIndex(X509TrustManager x509TrustManager, Method method) {
        this.findByIssuerAndSignatureMethod = method;
        this.trustManager = x509TrustManager;
    }

    public static TrustRootIndex get(X509TrustManager object) {
        try {
            Method method = object.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", X509Certificate.class);
            method.setAccessible(true);
            object = new AndroidTrustRootIndex((X509TrustManager)object, method);
            return object;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    @Override
    public X509Certificate findByIssuerAndSignature(X509Certificate object) {
        block5 : {
            Object var2_4;
            block4 : {
                var2_4 = null;
                object = (TrustAnchor)this.findByIssuerAndSignatureMethod.invoke(this.trustManager, object);
                if (object == null) break block4;
                try {
                    object = ((TrustAnchor)object).getTrustedCert();
                    break block5;
                }
                catch (InvocationTargetException invocationTargetException) {
                    return null;
                }
                catch (IllegalAccessException illegalAccessException) {
                    throw new AssertionError();
                }
            }
            object = var2_4;
        }
        return object;
    }
}

