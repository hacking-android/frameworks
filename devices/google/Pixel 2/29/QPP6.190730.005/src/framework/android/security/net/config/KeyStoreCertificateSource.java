/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.TrustedCertificateIndex
 */
package android.security.net.config;

import android.security.net.config.CertificateSource;
import android.util.ArraySet;
import com.android.org.conscrypt.TrustedCertificateIndex;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;

class KeyStoreCertificateSource
implements CertificateSource {
    private Set<X509Certificate> mCertificates;
    private TrustedCertificateIndex mIndex;
    private final KeyStore mKeyStore;
    private final Object mLock = new Object();

    public KeyStoreCertificateSource(KeyStore keyStore) {
        this.mKeyStore = keyStore;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void ensureInitialized() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCertificates != null) {
                return;
            }
            try {
                TrustedCertificateIndex trustedCertificateIndex = new TrustedCertificateIndex();
                ArraySet<X509Certificate> arraySet = new ArraySet<X509Certificate>(this.mKeyStore.size());
                Enumeration<String> enumeration = this.mKeyStore.aliases();
                do {
                    if (!enumeration.hasMoreElements()) {
                        this.mIndex = trustedCertificateIndex;
                        this.mCertificates = arraySet;
                        return;
                    }
                    Object object2 = enumeration.nextElement();
                    if ((object2 = (X509Certificate)this.mKeyStore.getCertificate((String)object2)) == null) continue;
                    arraySet.add((X509Certificate)object2);
                    trustedCertificateIndex.index((X509Certificate)object2);
                } while (true);
            }
            catch (KeyStoreException keyStoreException) {
                RuntimeException runtimeException = new RuntimeException("Failed to load certificates from KeyStore", keyStoreException);
                throw runtimeException;
            }
        }
    }

    @Override
    public Set<X509Certificate> findAllByIssuerAndSignature(X509Certificate object) {
        this.ensureInitialized();
        Object object2 = this.mIndex.findAllByIssuerAndSignature((X509Certificate)object);
        if (object2.isEmpty()) {
            return Collections.emptySet();
        }
        object = new ArraySet(object2.size());
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object.add(((TrustAnchor)object2.next()).getTrustedCert());
        }
        return object;
    }

    @Override
    public X509Certificate findByIssuerAndSignature(X509Certificate object) {
        this.ensureInitialized();
        object = this.mIndex.findByIssuerAndSignature((X509Certificate)object);
        if (object == null) {
            return null;
        }
        return ((TrustAnchor)object).getTrustedCert();
    }

    @Override
    public X509Certificate findBySubjectAndPublicKey(X509Certificate object) {
        this.ensureInitialized();
        object = this.mIndex.findBySubjectAndPublicKey((X509Certificate)object);
        if (object == null) {
            return null;
        }
        return ((TrustAnchor)object).getTrustedCert();
    }

    @Override
    public Set<X509Certificate> getCertificates() {
        this.ensureInitialized();
        return this.mCertificates;
    }

    @Override
    public void handleTrustStorageUpdate() {
    }
}

