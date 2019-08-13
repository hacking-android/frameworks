/*
 * Decompiled with CFR 0.145.
 */
package android.util.apk;

import android.util.apk.WrappedX509Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.Arrays;

class VerbatimX509Certificate
extends WrappedX509Certificate {
    private final byte[] mEncodedVerbatim;
    private int mHash = -1;

    VerbatimX509Certificate(X509Certificate x509Certificate, byte[] arrby) {
        super(x509Certificate);
        this.mEncodedVerbatim = arrby;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof VerbatimX509Certificate)) {
            return false;
        }
        try {
            boolean bl = Arrays.equals(this.getEncoded(), ((VerbatimX509Certificate)object).getEncoded());
            return bl;
        }
        catch (CertificateEncodingException certificateEncodingException) {
            return false;
        }
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.mEncodedVerbatim;
    }

    @Override
    public int hashCode() {
        if (this.mHash == -1) {
            try {
                this.mHash = Arrays.hashCode(this.getEncoded());
            }
            catch (CertificateEncodingException certificateEncodingException) {
                this.mHash = 0;
            }
        }
        return this.mHash;
    }
}

