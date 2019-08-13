/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.CRLReason;
import java.security.cert.X509Extension;
import java.util.Date;
import javax.security.auth.x500.X500Principal;
import sun.security.x509.X509CRLEntryImpl;

public abstract class X509CRLEntry
implements X509Extension {
    public boolean equals(Object arrby) {
        byte[] arrby2;
        int n;
        if (this == arrby) {
            return true;
        }
        if (!(arrby instanceof X509CRLEntry)) {
            return false;
        }
        try {
            arrby2 = this.getEncoded();
            arrby = ((X509CRLEntry)arrby).getEncoded();
            if (arrby2.length != arrby.length) {
                return false;
            }
            n = 0;
        }
        catch (CRLException cRLException) {
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

    public X500Principal getCertificateIssuer() {
        return null;
    }

    public abstract byte[] getEncoded() throws CRLException;

    public abstract Date getRevocationDate();

    public CRLReason getRevocationReason() {
        if (!this.hasExtensions()) {
            return null;
        }
        return X509CRLEntryImpl.getRevocationReason(this);
    }

    public abstract BigInteger getSerialNumber();

    public abstract boolean hasExtensions();

    public int hashCode() {
        int n;
        byte[] arrby;
        int n2 = 0;
        int n3 = 0;
        try {
            arrby = this.getEncoded();
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

    public abstract String toString();
}

