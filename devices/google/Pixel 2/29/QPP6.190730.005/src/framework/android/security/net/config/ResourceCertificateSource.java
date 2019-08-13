/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.conscrypt.TrustedCertificateIndex
 *  libcore.io.IoUtils
 */
package android.security.net.config;

import android.content.Context;
import android.content.res.Resources;
import android.security.net.config.CertificateSource;
import android.util.ArraySet;
import com.android.org.conscrypt.TrustedCertificateIndex;
import java.io.InputStream;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import libcore.io.IoUtils;

public class ResourceCertificateSource
implements CertificateSource {
    private Set<X509Certificate> mCertificates;
    private Context mContext;
    private TrustedCertificateIndex mIndex;
    private final Object mLock = new Object();
    private final int mResourceId;

    public ResourceCertificateSource(int n, Context context) {
        this.mResourceId = n;
        this.mContext = context;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void ensureInitialized() {
        Throwable throwable2222;
        Object object = this.mLock;
        // MONITORENTER : object
        if (this.mCertificates != null) {
            // MONITOREXIT : object
            return;
        }
        Object object2 = new ArraySet();
        Object object3 = null;
        Iterator iterator = null;
        Object object4 = iterator;
        Object object5 = object3;
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        object4 = iterator;
        object5 = object3;
        iterator = this.mContext.getResources().openRawResource(this.mResourceId);
        object4 = iterator;
        object5 = iterator;
        object3 = certificateFactory.generateCertificates((InputStream)((Object)iterator));
        IoUtils.closeQuietly((AutoCloseable)((Object)iterator));
        object4 = new TrustedCertificateIndex();
        iterator = object3.iterator();
        do {
            if (!iterator.hasNext()) {
                this.mCertificates = object2;
                this.mIndex = object4;
                this.mContext = null;
                // MONITOREXIT : object
                return;
            }
            object5 = (Certificate)iterator.next();
            object2.add((X509Certificate)object5);
            object4.index((X509Certificate)object5);
        } while (true);
        {
            catch (Throwable throwable2222) {
            }
            catch (CertificateException certificateException) {}
            object4 = object5;
            {
                object4 = object5;
                object4 = object5;
                object3 = new StringBuilder();
                object4 = object5;
                ((StringBuilder)object3).append("Failed to load trust anchors from id ");
                object4 = object5;
                ((StringBuilder)object3).append(this.mResourceId);
                object4 = object5;
                object2 = new RuntimeException(((StringBuilder)object3).toString(), certificateException);
                object4 = object5;
                throw object2;
            }
        }
        IoUtils.closeQuietly(object4);
        throw throwable2222;
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

