/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.OpenSSLX509CRL;
import com.android.org.conscrypt.OpenSSLX509CertificateFactory;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

final class OpenSSLX509CRLEntry
extends X509CRLEntry {
    private final long mContext;
    private final Date revocationDate;

    OpenSSLX509CRLEntry(long l) throws OpenSSLX509CertificateFactory.ParsingException {
        this.mContext = l;
        this.revocationDate = OpenSSLX509CRL.toDate(NativeCrypto.get_X509_REVOKED_revocationDate(this.mContext));
    }

    @Override
    public Set<String> getCriticalExtensionOIDs() {
        String[] arrstring = NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1);
        if (arrstring.length == 0 && NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 0).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(arrstring));
    }

    @Override
    public byte[] getEncoded() throws CRLException {
        return NativeCrypto.i2d_X509_REVOKED(this.mContext);
    }

    @Override
    public byte[] getExtensionValue(String string) {
        return NativeCrypto.X509_REVOKED_get_ext_oid(this.mContext, string);
    }

    @Override
    public Set<String> getNonCriticalExtensionOIDs() {
        String[] arrstring = NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 0);
        if (arrstring.length == 0 && NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1).length == 0) {
            return null;
        }
        return new HashSet<String>(Arrays.asList(arrstring));
    }

    @Override
    public Date getRevocationDate() {
        return (Date)this.revocationDate.clone();
    }

    @Override
    public BigInteger getSerialNumber() {
        return new BigInteger(NativeCrypto.X509_REVOKED_get_serialNumber(this.mContext));
    }

    @Override
    public boolean hasExtensions() {
        boolean bl;
        block0 : {
            int n = NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 0).length;
            bl = true;
            if (n != 0 || NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1).length != 0) break block0;
            bl = false;
        }
        return bl;
    }

    @Override
    public boolean hasUnsupportedCriticalExtension() {
        for (String string : NativeCrypto.get_X509_REVOKED_ext_oids(this.mContext, 1)) {
            if (NativeCrypto.X509_supported_extension(NativeCrypto.X509_REVOKED_get_ext(this.mContext, string)) == 1) continue;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        Object object = new ByteArrayOutputStream();
        long l = NativeCrypto.create_BIO_OutputStream((OutputStream)object);
        try {
            NativeCrypto.X509_REVOKED_print(l, this.mContext);
            object = ((ByteArrayOutputStream)object).toString();
            return object;
        }
        finally {
            NativeCrypto.BIO_free_all(l);
        }
    }
}

