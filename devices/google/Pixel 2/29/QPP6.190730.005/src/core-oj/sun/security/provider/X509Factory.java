/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider;

import java.security.cert.CRLException;
import java.security.cert.CertificateException;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import sun.security.util.Cache;
import sun.security.x509.X509CRLImpl;
import sun.security.x509.X509CertImpl;

public class X509Factory {
    private static final int ENC_MAX_LENGTH = 4194304;
    private static final Cache<Object, X509CertImpl> certCache = Cache.newSoftMemoryCache(750);
    private static final Cache<Object, X509CRLImpl> crlCache = Cache.newSoftMemoryCache(750);

    private static <V> void addToCache(Cache<Object, V> cache, byte[] arrby, V v) {
        synchronized (X509Factory.class) {
            block5 : {
                int n = arrby.length;
                if (n <= 4194304) break block5;
                return;
            }
            Cache.EqualByteArray equalByteArray = new Cache.EqualByteArray(arrby);
            cache.put(equalByteArray, v);
            return;
        }
    }

    private static <K, V> V getFromCache(Cache<K, V> cache, byte[] arrby) {
        synchronized (X509Factory.class) {
            Cache.EqualByteArray equalByteArray = new Cache.EqualByteArray(arrby);
            cache = cache.get(equalByteArray);
            return (V)cache;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static X509CRLImpl intern(X509CRL x509CRL) throws CRLException {
        synchronized (X509Factory.class) {
            if (x509CRL == null) {
                return null;
            }
            boolean bl = x509CRL instanceof X509CRLImpl;
            byte[] arrby = bl ? ((X509CRLImpl)x509CRL).getEncodedInternal() : x509CRL.getEncoded();
            X509CRLImpl x509CRLImpl = X509Factory.getFromCache(crlCache, arrby);
            if (x509CRLImpl != null) {
                return x509CRLImpl;
            }
            if (bl) {
                x509CRL = (X509CRLImpl)x509CRL;
            } else {
                x509CRL = new X509CRLImpl(arrby);
                arrby = ((X509CRLImpl)x509CRL).getEncodedInternal();
            }
            X509Factory.addToCache(crlCache, arrby, x509CRL);
            return x509CRL;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static X509CertImpl intern(X509Certificate x509Certificate) throws CertificateException {
        synchronized (X509Factory.class) {
            if (x509Certificate == null) {
                return null;
            }
            boolean bl = x509Certificate instanceof X509CertImpl;
            byte[] arrby = bl ? ((X509CertImpl)x509Certificate).getEncodedInternal() : x509Certificate.getEncoded();
            X509CertImpl x509CertImpl = X509Factory.getFromCache(certCache, arrby);
            if (x509CertImpl != null) {
                return x509CertImpl;
            }
            if (bl) {
                x509Certificate = (X509CertImpl)x509Certificate;
            } else {
                x509Certificate = new X509CertImpl(arrby);
                arrby = ((X509CertImpl)x509Certificate).getEncodedInternal();
            }
            X509Factory.addToCache(certCache, arrby, x509Certificate);
            return x509Certificate;
        }
    }
}

