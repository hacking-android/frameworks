/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.DSAExt;
import com.android.org.bouncycastle.crypto.Digest;
import com.android.org.bouncycastle.crypto.signers.DSAEncoding;
import java.math.BigInteger;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.spec.AlgorithmParameterSpec;

public abstract class DSABase
extends SignatureSpi
implements PKCSObjectIdentifiers,
X509ObjectIdentifiers {
    protected Digest digest;
    protected DSAEncoding encoding;
    protected DSAExt signer;

    protected DSABase(Digest digest, DSAExt dSAExt, DSAEncoding dSAEncoding) {
        this.digest = digest;
        this.signer = dSAExt;
        this.encoding = dSAEncoding;
    }

    @Override
    protected Object engineGetParameter(String string) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override
    protected void engineSetParameter(String string, Object object) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override
    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) {
        throw new UnsupportedOperationException("engineSetParameter unsupported");
    }

    @Override
    protected byte[] engineSign() throws SignatureException {
        Object[] arrobject = new byte[this.digest.getDigestSize()];
        this.digest.doFinal((byte[])arrobject, 0);
        try {
            arrobject = this.signer.generateSignature((byte[])arrobject);
            arrobject = this.encoding.encode(this.signer.getOrder(), (BigInteger)arrobject[0], (BigInteger)arrobject[1]);
            return arrobject;
        }
        catch (Exception exception) {
            throw new SignatureException(exception.toString());
        }
    }

    @Override
    protected void engineUpdate(byte by) throws SignatureException {
        this.digest.update(by);
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) throws SignatureException {
        this.digest.update(arrby, n, n2);
    }

    @Override
    protected boolean engineVerify(byte[] arrobject) throws SignatureException {
        byte[] arrby = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(arrby, 0);
        try {
            arrobject = this.encoding.decode(this.signer.getOrder(), (byte[])arrobject);
        }
        catch (Exception exception) {
            throw new SignatureException("error decoding signature bytes.");
        }
        return this.signer.verifySignature(arrby, (BigInteger)arrobject[0], (BigInteger)arrobject[1]);
    }
}

