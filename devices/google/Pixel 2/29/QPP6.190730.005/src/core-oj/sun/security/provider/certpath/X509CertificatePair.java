/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.io.Serializable;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import javax.security.auth.x500.X500Principal;
import sun.security.provider.X509Factory;
import sun.security.util.Cache;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.X509CertImpl;

public class X509CertificatePair {
    private static final byte TAG_FORWARD = 0;
    private static final byte TAG_REVERSE = 1;
    private static final Cache<Object, X509CertificatePair> cache = Cache.newSoftMemoryCache(750);
    private byte[] encoded;
    private X509Certificate forward;
    private X509Certificate reverse;

    public X509CertificatePair() {
    }

    public X509CertificatePair(X509Certificate x509Certificate, X509Certificate x509Certificate2) throws CertificateException {
        if (x509Certificate == null && x509Certificate2 == null) {
            throw new CertificateException("at least one of certificate pair must be non-null");
        }
        this.forward = x509Certificate;
        this.reverse = x509Certificate2;
        this.checkPair();
    }

    private X509CertificatePair(byte[] arrby) throws CertificateException {
        try {
            DerValue derValue = new DerValue(arrby);
            this.parse(derValue);
            this.encoded = arrby;
            this.checkPair();
            return;
        }
        catch (IOException iOException) {
            throw new CertificateException(iOException.toString());
        }
    }

    private void checkPair() throws CertificateException {
        Serializable serializable = this.forward;
        if (serializable != null && this.reverse != null) {
            X500Principal x500Principal = serializable.getSubjectX500Principal();
            X500Principal x500Principal2 = this.forward.getIssuerX500Principal();
            serializable = this.reverse.getSubjectX500Principal();
            Serializable serializable2 = this.reverse.getIssuerX500Principal();
            if (x500Principal2.equals(serializable) && ((X500Principal)serializable2).equals(x500Principal)) {
                try {
                    serializable = this.reverse.getPublicKey();
                    if (!(serializable instanceof DSAPublicKey) || ((DSAPublicKey)serializable).getParams() != null) {
                        this.forward.verify((PublicKey)serializable);
                    }
                    if (!((serializable = this.forward.getPublicKey()) instanceof DSAPublicKey) || ((DSAPublicKey)serializable).getParams() != null) {
                        this.reverse.verify((PublicKey)serializable);
                    }
                    return;
                }
                catch (GeneralSecurityException generalSecurityException) {
                    serializable2 = new StringBuilder();
                    ((StringBuilder)serializable2).append("invalid signature: ");
                    ((StringBuilder)serializable2).append(generalSecurityException.getMessage());
                    throw new CertificateException(((StringBuilder)serializable2).toString());
                }
            }
            throw new CertificateException("subject and issuer names in forward and reverse certificates do not match");
        }
    }

    public static void clearCache() {
        synchronized (X509CertificatePair.class) {
            cache.clear();
            return;
        }
    }

    private void emit(DerOutputStream derOutputStream) throws IOException, CertificateEncodingException {
        DerOutputStream derOutputStream2;
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.forward != null) {
            derOutputStream2 = new DerOutputStream();
            derOutputStream2.putDerValue(new DerValue(this.forward.getEncoded()));
            derOutputStream3.write(DerValue.createTag((byte)-128, true, (byte)0), derOutputStream2);
        }
        if (this.reverse != null) {
            derOutputStream2 = new DerOutputStream();
            derOutputStream2.putDerValue(new DerValue(this.reverse.getEncoded()));
            derOutputStream3.write(DerValue.createTag((byte)-128, true, (byte)1), derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream3);
    }

    public static X509CertificatePair generateCertificatePair(byte[] object) throws CertificateException {
        synchronized (X509CertificatePair.class) {
            Object object2;
            block5 : {
                object2 = new Cache.EqualByteArray((byte[])object);
                object2 = cache.get(object2);
                if (object2 == null) break block5;
                return object2;
            }
            object2 = new X509CertificatePair((byte[])object);
            object = new Cache.EqualByteArray(((X509CertificatePair)object2).encoded);
            cache.put(object, (X509CertificatePair)object2);
            return object2;
        }
    }

    private void parse(DerValue derValue) throws IOException, CertificateException {
        if (derValue.tag == 48) {
            while (derValue.data != null && derValue.data.available() != 0) {
                DerValue derValue2 = derValue.data.getDerValue();
                short s = (byte)(derValue2.tag & 31);
                if (s != 0) {
                    if (s == 1) {
                        if (!derValue2.isContextSpecific() || !derValue2.isConstructed()) continue;
                        if (this.reverse == null) {
                            this.reverse = X509Factory.intern(new X509CertImpl(derValue2.data.getDerValue().toByteArray()));
                            continue;
                        }
                        throw new IOException("Duplicate reverse certificate in X509CertificatePair");
                    }
                    throw new IOException("Invalid encoding of X509CertificatePair");
                }
                if (!derValue2.isContextSpecific() || !derValue2.isConstructed()) continue;
                if (this.forward == null) {
                    this.forward = X509Factory.intern(new X509CertImpl(derValue2.data.getDerValue().toByteArray()));
                    continue;
                }
                throw new IOException("Duplicate forward certificate in X509CertificatePair");
            }
            if (this.forward == null && this.reverse == null) {
                throw new CertificateException("at least one of certificate pair must be non-null");
            }
            return;
        }
        throw new IOException("Sequence tag missing for X509CertificatePair");
    }

    public byte[] getEncoded() throws CertificateEncodingException {
        try {
            if (this.encoded == null) {
                DerOutputStream derOutputStream = new DerOutputStream();
                this.emit(derOutputStream);
                this.encoded = derOutputStream.toByteArray();
            }
            return this.encoded;
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.toString());
        }
    }

    public X509Certificate getForward() {
        return this.forward;
    }

    public X509Certificate getReverse() {
        return this.reverse;
    }

    public void setForward(X509Certificate x509Certificate) throws CertificateException {
        this.checkPair();
        this.forward = x509Certificate;
    }

    public void setReverse(X509Certificate x509Certificate) throws CertificateException {
        this.checkPair();
        this.reverse = x509Certificate;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("X.509 Certificate Pair: [\n");
        if (this.forward != null) {
            stringBuilder.append("  Forward: ");
            stringBuilder.append(this.forward);
            stringBuilder.append("\n");
        }
        if (this.reverse != null) {
            stringBuilder.append("  Reverse: ");
            stringBuilder.append(this.reverse);
            stringBuilder.append("\n");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

