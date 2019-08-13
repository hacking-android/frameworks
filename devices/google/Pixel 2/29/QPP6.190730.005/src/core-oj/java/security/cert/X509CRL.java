/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CRL;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.X509CRLImpl;

public abstract class X509CRL
extends CRL
implements X509Extension {
    private transient X500Principal issuerPrincipal;

    protected X509CRL() {
        super("X.509");
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof X509CRL)) {
            return false;
        }
        try {
            boolean bl = Arrays.equals(X509CRLImpl.getEncodedInternal(this), X509CRLImpl.getEncodedInternal((X509CRL)object));
            return bl;
        }
        catch (CRLException cRLException) {
            return false;
        }
    }

    public abstract byte[] getEncoded() throws CRLException;

    public abstract Principal getIssuerDN();

    public X500Principal getIssuerX500Principal() {
        if (this.issuerPrincipal == null) {
            this.issuerPrincipal = X509CRLImpl.getIssuerX500Principal(this);
        }
        return this.issuerPrincipal;
    }

    public abstract Date getNextUpdate();

    public abstract X509CRLEntry getRevokedCertificate(BigInteger var1);

    public X509CRLEntry getRevokedCertificate(X509Certificate x509Certificate) {
        if (!x509Certificate.getIssuerX500Principal().equals(this.getIssuerX500Principal())) {
            return null;
        }
        return this.getRevokedCertificate(x509Certificate.getSerialNumber());
    }

    public abstract Set<? extends X509CRLEntry> getRevokedCertificates();

    public abstract String getSigAlgName();

    public abstract String getSigAlgOID();

    public abstract byte[] getSigAlgParams();

    public abstract byte[] getSignature();

    public abstract byte[] getTBSCertList() throws CRLException;

    public abstract Date getThisUpdate();

    public abstract int getVersion();

    public int hashCode() {
        int n;
        byte[] arrby;
        int n2 = 0;
        int n3 = 0;
        try {
            arrby = X509CRLImpl.getEncodedInternal(this);
            n = 1;
        }
        catch (CRLException cRLException) {
            return n2;
        }
        do {
            n2 = n3;
            if (n >= arrby.length) break;
            n2 = arrby[n];
            n3 += n2 * n;
            ++n;
        } while (true);
        return n3;
    }

    public abstract void verify(PublicKey var1) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;

    public abstract void verify(PublicKey var1, String var2) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;

    public void verify(PublicKey publicKey, Provider provider) throws CRLException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        throw new UnsupportedOperationException("X509CRL instance doesn't not support X509CRL#verify(PublicKey, Provider)");
    }
}

