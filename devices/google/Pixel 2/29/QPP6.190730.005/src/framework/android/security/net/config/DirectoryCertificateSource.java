/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.security.net.config;

import android.security.net.config.CertificateSource;
import android.util.ArraySet;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import libcore.io.IoUtils;

abstract class DirectoryCertificateSource
implements CertificateSource {
    private static final char[] DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final String LOG_TAG = "DirectoryCertificateSrc";
    private final CertificateFactory mCertFactory;
    private Set<X509Certificate> mCertificates;
    private final File mDir;
    private final Object mLock = new Object();

    protected DirectoryCertificateSource(File file) {
        this.mDir = file;
        try {
            this.mCertFactory = CertificateFactory.getInstance("X.509");
            return;
        }
        catch (CertificateException certificateException) {
            throw new RuntimeException("Failed to obtain X.509 CertificateFactory", certificateException);
        }
    }

    private X509Certificate findCert(X500Principal x500Principal, CertSelector certSelector) {
        String string2 = this.getHash(x500Principal);
        for (int i = 0; i >= 0; ++i) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(".");
            ((StringBuilder)object).append(i);
            object = ((StringBuilder)object).toString();
            if (!new File(this.mDir, (String)object).exists()) break;
            if (this.isCertMarkedAsRemoved((String)object) || (object = this.readCertificate((String)object)) == null || !x500Principal.equals(((X509Certificate)object).getSubjectX500Principal()) || !certSelector.match((X509Certificate)object)) continue;
            return object;
        }
        return null;
    }

    private Set<X509Certificate> findCerts(X500Principal object, CertSelector certSelector) {
        String string2 = this.getHash((X500Principal)object);
        CharSequence charSequence = null;
        for (int i = 0; i >= 0; ++i) {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(".");
            ((StringBuilder)object2).append(i);
            object2 = ((StringBuilder)object2).toString();
            if (!new File(this.mDir, (String)object2).exists()) break;
            if (this.isCertMarkedAsRemoved((String)object2)) {
                object2 = charSequence;
            } else {
                X509Certificate x509Certificate = this.readCertificate((String)object2);
                if (x509Certificate == null) {
                    object2 = charSequence;
                } else if (!((X500Principal)object).equals(x509Certificate.getSubjectX500Principal())) {
                    object2 = charSequence;
                } else {
                    object2 = charSequence;
                    if (certSelector.match(x509Certificate)) {
                        object2 = charSequence;
                        if (charSequence == null) {
                            object2 = new ArraySet();
                        }
                        object2.add(x509Certificate);
                    }
                }
            }
            charSequence = object2;
        }
        object = charSequence != null ? charSequence : Collections.emptySet();
        return object;
    }

    private String getHash(X500Principal x500Principal) {
        return DirectoryCertificateSource.intToHexString(DirectoryCertificateSource.hashName(x500Principal), 8);
    }

    private static int hashName(X500Principal arrby) {
        int n;
        try {
            arrby = MessageDigest.getInstance("MD5").digest(arrby.getEncoded());
            n = 0 + 1;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new AssertionError(noSuchAlgorithmException);
        }
        byte by = arrby[0];
        int n2 = n + 1;
        byte by2 = arrby[n];
        n = arrby[n2];
        n2 = arrby[n2 + 1];
        return (by & 255) << 0 | (by2 & 255) << 8 | (n & 255) << 16 | (n2 & 255) << 24;
    }

    private static String intToHexString(int n, int n2) {
        int n3;
        char[] arrc = new char[8];
        int n4 = 8;
        do {
            arrc[--n4] = DIGITS[n & 15];
            n = n3 = n >>> 4;
        } while (n3 != 0 || 8 - n4 < n2);
        return new String(arrc, n4, 8 - n4);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private X509Certificate readCertificate(String string2) {
        Throwable throwable2222;
        BufferedInputStream bufferedInputStream;
        BufferedInputStream bufferedInputStream2 = null;
        BufferedInputStream bufferedInputStream3 = bufferedInputStream = null;
        BufferedInputStream bufferedInputStream4 = bufferedInputStream2;
        bufferedInputStream3 = bufferedInputStream;
        bufferedInputStream4 = bufferedInputStream2;
        bufferedInputStream3 = bufferedInputStream;
        bufferedInputStream4 = bufferedInputStream2;
        bufferedInputStream3 = bufferedInputStream;
        bufferedInputStream4 = bufferedInputStream2;
        File file = new File(this.mDir, string2);
        bufferedInputStream3 = bufferedInputStream;
        bufferedInputStream4 = bufferedInputStream2;
        FileInputStream fileInputStream = new FileInputStream(file);
        bufferedInputStream3 = bufferedInputStream;
        bufferedInputStream4 = bufferedInputStream2;
        Object object = new BufferedInputStream(fileInputStream);
        bufferedInputStream3 = bufferedInputStream = object;
        bufferedInputStream4 = bufferedInputStream;
        object = (X509Certificate)this.mCertFactory.generateCertificate(bufferedInputStream);
        IoUtils.closeQuietly((AutoCloseable)bufferedInputStream);
        return object;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException | CertificateException exception) {}
            bufferedInputStream3 = bufferedInputStream4;
            {
                bufferedInputStream3 = bufferedInputStream4;
                StringBuilder stringBuilder = new StringBuilder();
                bufferedInputStream3 = bufferedInputStream4;
                stringBuilder.append("Failed to read certificate from ");
                bufferedInputStream3 = bufferedInputStream4;
                stringBuilder.append(string2);
                bufferedInputStream3 = bufferedInputStream4;
                Log.e(LOG_TAG, stringBuilder.toString(), exception);
            }
            IoUtils.closeQuietly(bufferedInputStream4);
            return null;
        }
        IoUtils.closeQuietly(bufferedInputStream3);
        throw throwable2222;
    }

    @Override
    public Set<X509Certificate> findAllByIssuerAndSignature(final X509Certificate x509Certificate) {
        return this.findCerts(x509Certificate.getIssuerX500Principal(), new CertSelector(){

            @Override
            public boolean match(X509Certificate x509Certificate2) {
                try {
                    x509Certificate.verify(x509Certificate2.getPublicKey());
                    return true;
                }
                catch (Exception exception) {
                    return false;
                }
            }
        });
    }

    @Override
    public X509Certificate findByIssuerAndSignature(final X509Certificate x509Certificate) {
        return this.findCert(x509Certificate.getIssuerX500Principal(), new CertSelector(){

            @Override
            public boolean match(X509Certificate x509Certificate2) {
                try {
                    x509Certificate.verify(x509Certificate2.getPublicKey());
                    return true;
                }
                catch (Exception exception) {
                    return false;
                }
            }
        });
    }

    @Override
    public X509Certificate findBySubjectAndPublicKey(final X509Certificate x509Certificate) {
        return this.findCert(x509Certificate.getSubjectX500Principal(), new CertSelector(){

            @Override
            public boolean match(X509Certificate x509Certificate2) {
                return x509Certificate2.getPublicKey().equals(x509Certificate.getPublicKey());
            }
        });
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public Set<X509Certificate> getCertificates() {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mCertificates != null) {
                return this.mCertificates;
            }
            ArraySet<X509Certificate> arraySet = new ArraySet<X509Certificate>();
            if (this.mDir.isDirectory()) {
                for (Object object2 : this.mDir.list()) {
                    if (this.isCertMarkedAsRemoved((String)object2) || (object2 = this.readCertificate((String)object2)) == null) continue;
                    arraySet.add((X509Certificate)object2);
                }
            }
            this.mCertificates = arraySet;
            return this.mCertificates;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void handleTrustStorageUpdate() {
        Object object = this.mLock;
        synchronized (object) {
            this.mCertificates = null;
            return;
        }
    }

    protected abstract boolean isCertMarkedAsRemoved(String var1);

    private static interface CertSelector {
        public boolean match(X509Certificate var1);
    }

}

