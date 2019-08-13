/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.TrustManagerImpl
 */
package android.net.http;

import android.security.net.config.UserCertificateSource;
import com.android.org.conscrypt.TrustManagerImpl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.X509TrustManager;

public class X509TrustManagerExtensions {
    private final Method mCheckServerTrusted;
    private final TrustManagerImpl mDelegate;
    private final Method mIsSameTrustConfiguration;
    private final X509TrustManager mTrustManager;

    public X509TrustManagerExtensions(X509TrustManager object) throws IllegalArgumentException {
        Object var2_4;
        if (object instanceof TrustManagerImpl) {
            this.mDelegate = (TrustManagerImpl)object;
            this.mTrustManager = null;
            this.mCheckServerTrusted = null;
            this.mIsSameTrustConfiguration = null;
            return;
        }
        this.mDelegate = null;
        this.mTrustManager = object;
        try {
            this.mCheckServerTrusted = object.getClass().getMethod("checkServerTrusted", X509Certificate[].class, String.class, String.class);
            var2_4 = null;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new IllegalArgumentException("Required method checkServerTrusted(X509Certificate[], String, String, String) missing");
        }
        try {
            object = object.getClass().getMethod("isSameTrustConfiguration", String.class, String.class);
        }
        catch (ReflectiveOperationException reflectiveOperationException) {
            object = var2_4;
        }
        this.mIsSameTrustConfiguration = object;
    }

    public List<X509Certificate> checkServerTrusted(X509Certificate[] object, String string2, String string3) throws CertificateException {
        TrustManagerImpl trustManagerImpl = this.mDelegate;
        if (trustManagerImpl != null) {
            return trustManagerImpl.checkServerTrusted(object, string2, string3);
        }
        try {
            object = (List)this.mCheckServerTrusted.invoke(this.mTrustManager, object, string2, string3);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            if (!(invocationTargetException.getCause() instanceof CertificateException)) {
                if (invocationTargetException.getCause() instanceof RuntimeException) {
                    throw (RuntimeException)invocationTargetException.getCause();
                }
                throw new CertificateException("checkServerTrusted failed", invocationTargetException.getCause());
            }
            throw (CertificateException)invocationTargetException.getCause();
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new CertificateException("Failed to call checkServerTrusted", illegalAccessException);
        }
    }

    public boolean isSameTrustConfiguration(String string2, String string3) {
        Method method = this.mIsSameTrustConfiguration;
        if (method == null) {
            return true;
        }
        try {
            boolean bl = (Boolean)method.invoke(this.mTrustManager, string2, string3);
            return bl;
        }
        catch (InvocationTargetException invocationTargetException) {
            if (invocationTargetException.getCause() instanceof RuntimeException) {
                throw (RuntimeException)invocationTargetException.getCause();
            }
            throw new RuntimeException("isSameTrustConfiguration failed", invocationTargetException.getCause());
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException("Failed to call isSameTrustConfiguration", illegalAccessException);
        }
    }

    public boolean isUserAddedCertificate(X509Certificate x509Certificate) {
        boolean bl = UserCertificateSource.getInstance().findBySubjectAndPublicKey(x509Certificate) != null;
        return bl;
    }
}

