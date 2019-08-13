/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.IOException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.NameConstraintsExtension;

public class TrustAnchor {
    private final String caName;
    private final X500Principal caPrincipal;
    private NameConstraintsExtension nc;
    private byte[] ncBytes;
    private final PublicKey pubKey;
    private final X509Certificate trustedCert;

    public TrustAnchor(String string, PublicKey publicKey, byte[] arrby) {
        if (publicKey != null) {
            if (string != null) {
                if (string.length() != 0) {
                    this.caPrincipal = new X500Principal(string);
                    this.pubKey = publicKey;
                    this.caName = string;
                    this.trustedCert = null;
                    this.setNameConstraints(arrby);
                    return;
                }
                throw new IllegalArgumentException("the caName parameter must be a non-empty String");
            }
            throw new NullPointerException("the caName parameter must be non-null");
        }
        throw new NullPointerException("the pubKey parameter must be non-null");
    }

    public TrustAnchor(X509Certificate x509Certificate, byte[] arrby) {
        if (x509Certificate != null) {
            this.trustedCert = x509Certificate;
            this.pubKey = null;
            this.caName = null;
            this.caPrincipal = null;
            this.setNameConstraints(arrby);
            return;
        }
        throw new NullPointerException("the trustedCert parameter must be non-null");
    }

    public TrustAnchor(X500Principal x500Principal, PublicKey publicKey, byte[] arrby) {
        if (x500Principal != null && publicKey != null) {
            this.trustedCert = null;
            this.caPrincipal = x500Principal;
            this.caName = x500Principal.getName();
            this.pubKey = publicKey;
            this.setNameConstraints(arrby);
            return;
        }
        throw new NullPointerException();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void setNameConstraints(byte[] object) {
        if (object == null) {
            this.ncBytes = null;
            this.nc = null;
            return;
        }
        this.ncBytes = (byte[])object.clone();
        try {
            NameConstraintsExtension nameConstraintsExtension;
            this.nc = nameConstraintsExtension = new NameConstraintsExtension(Boolean.FALSE, object);
            return;
        }
        catch (IOException iOException) {
            object = new IllegalArgumentException(iOException.getMessage());
            ((Throwable)object).initCause(iOException);
            throw object;
        }
    }

    public final X500Principal getCA() {
        return this.caPrincipal;
    }

    public final String getCAName() {
        return this.caName;
    }

    public final PublicKey getCAPublicKey() {
        return this.pubKey;
    }

    public final byte[] getNameConstraints() {
        Object object = this.ncBytes;
        object = object == null ? null : (byte[])object.clone();
        return object;
    }

    public final X509Certificate getTrustedCert() {
        return this.trustedCert;
    }

    public String toString() {
        StringBuilder stringBuilder;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[\n");
        if (this.pubKey != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("  Trusted CA Public Key: ");
            stringBuilder.append(this.pubKey.toString());
            stringBuilder.append("\n");
            stringBuffer.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("  Trusted CA Issuer Name: ");
            stringBuilder.append(String.valueOf(this.caName));
            stringBuilder.append("\n");
            stringBuffer.append(stringBuilder.toString());
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append("  Trusted CA cert: ");
            stringBuilder.append(this.trustedCert.toString());
            stringBuilder.append("\n");
            stringBuffer.append(stringBuilder.toString());
        }
        if (this.nc != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("  Name Constraints: ");
            stringBuilder.append(this.nc.toString());
            stringBuilder.append("\n");
            stringBuffer.append(stringBuilder.toString());
        }
        return stringBuffer.toString();
    }
}

