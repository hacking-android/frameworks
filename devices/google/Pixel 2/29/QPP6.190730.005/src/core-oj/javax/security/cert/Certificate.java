/*
 * Decompiled with CFR 0.145.
 */
package javax.security.cert;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.SignatureException;
import javax.security.cert.CertificateEncodingException;
import javax.security.cert.CertificateException;

public abstract class Certificate {
    public boolean equals(Object arrby) {
        byte[] arrby2;
        int n;
        if (this == arrby) {
            return true;
        }
        if (!(arrby instanceof Certificate)) {
            return false;
        }
        try {
            arrby2 = this.getEncoded();
            arrby = ((Certificate)arrby).getEncoded();
            if (arrby2.length != arrby.length) {
                return false;
            }
            n = 0;
        }
        catch (CertificateException certificateException) {
            return false;
        }
        do {
            if (n >= arrby2.length) break;
            byte by = arrby2[n];
            byte by2 = arrby[n];
            if (by != by2) {
                return false;
            }
            ++n;
        } while (true);
        return true;
    }

    public abstract byte[] getEncoded() throws CertificateEncodingException;

    public abstract PublicKey getPublicKey();

    public int hashCode() {
        int n;
        byte[] arrby;
        int n2 = 0;
        int n3 = 0;
        try {
            arrby = this.getEncoded();
            n = 1;
        }
        catch (CertificateException certificateException) {
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

    public abstract String toString();

    public abstract void verify(PublicKey var1) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;

    public abstract void verify(PublicKey var1, String var2) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException;
}

