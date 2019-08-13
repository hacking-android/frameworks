/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import sun.security.x509.X509CertImpl;

public abstract class Certificate
implements Serializable {
    private static final long serialVersionUID = -3585440601605666277L;
    private int hash = -1;
    private final String type;

    protected Certificate(String string) {
        this.type = string;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Certificate)) {
            return false;
        }
        try {
            boolean bl = Arrays.equals(X509CertImpl.getEncodedInternal(this), X509CertImpl.getEncodedInternal((Certificate)object));
            return bl;
        }
        catch (CertificateException certificateException) {
            return false;
        }
    }

    public abstract byte[] getEncoded() throws CertificateEncodingException;

    public abstract PublicKey getPublicKey();

    public final String getType() {
        return this.type;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hash;
        if (n == -1) {
            try {
                n2 = Arrays.hashCode(X509CertImpl.getEncodedInternal(this));
            }
            catch (CertificateException certificateException) {
                n2 = 0;
            }
            this.hash = n2;
        }
        return n2;
    }

    public abstract String toString();

    public abstract void verify(PublicKey var1) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;

    public abstract void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;

    public void verify(PublicKey publicKey, Provider provider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        throw new UnsupportedOperationException();
    }

    protected Object writeReplace() throws ObjectStreamException {
        try {
            CertificateRep certificateRep = new CertificateRep(this.type, this.getEncoded());
            return certificateRep;
        }
        catch (CertificateException certificateException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("java.security.cert.Certificate: ");
            stringBuilder.append(this.type);
            stringBuilder.append(": ");
            stringBuilder.append(certificateException.getMessage());
            throw new NotSerializableException(stringBuilder.toString());
        }
    }

    protected static class CertificateRep
    implements Serializable {
        private static final long serialVersionUID = -8563758940495660020L;
        private byte[] data;
        private String type;

        protected CertificateRep(String string, byte[] arrby) {
            this.type = string;
            this.data = arrby;
        }

        protected Object readResolve() throws ObjectStreamException {
            try {
                Object object = CertificateFactory.getInstance(this.type);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.data);
                object = ((CertificateFactory)object).generateCertificate(byteArrayInputStream);
                return object;
            }
            catch (CertificateException certificateException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("java.security.cert.Certificate: ");
                stringBuilder.append(this.type);
                stringBuilder.append(": ");
                stringBuilder.append(certificateException.getMessage());
                throw new NotSerializableException(stringBuilder.toString());
            }
        }
    }

}

