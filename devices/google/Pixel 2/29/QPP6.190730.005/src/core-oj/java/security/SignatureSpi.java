/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import sun.security.jca.JCAUtil;

public abstract class SignatureSpi {
    protected SecureRandom appRandom = null;

    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }

    @Deprecated
    protected abstract Object engineGetParameter(String var1) throws InvalidParameterException;

    protected AlgorithmParameters engineGetParameters() {
        throw new UnsupportedOperationException();
    }

    protected abstract void engineInitSign(PrivateKey var1) throws InvalidKeyException;

    protected void engineInitSign(PrivateKey privateKey, SecureRandom secureRandom) throws InvalidKeyException {
        this.appRandom = secureRandom;
        this.engineInitSign(privateKey);
    }

    protected abstract void engineInitVerify(PublicKey var1) throws InvalidKeyException;

    @Deprecated
    protected abstract void engineSetParameter(String var1, Object var2) throws InvalidParameterException;

    protected void engineSetParameter(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        throw new UnsupportedOperationException();
    }

    protected int engineSign(byte[] arrby, int n, int n2) throws SignatureException {
        byte[] arrby2 = this.engineSign();
        if (n2 >= arrby2.length) {
            if (arrby.length - n >= arrby2.length) {
                System.arraycopy(arrby2, 0, arrby, n, arrby2.length);
                return arrby2.length;
            }
            throw new SignatureException("insufficient space in the output buffer to store the signature");
        }
        throw new SignatureException("partial signatures not returned");
    }

    protected abstract byte[] engineSign() throws SignatureException;

    protected abstract void engineUpdate(byte var1) throws SignatureException;

    protected void engineUpdate(ByteBuffer byteBuffer) {
        block6 : {
            byte[] arrby;
            int n;
            int n2;
            if (!byteBuffer.hasRemaining()) {
                return;
            }
            try {
                if (byteBuffer.hasArray()) {
                    byte[] arrby2 = byteBuffer.array();
                    int n3 = byteBuffer.arrayOffset();
                    int n4 = byteBuffer.position();
                    int n5 = byteBuffer.limit();
                    this.engineUpdate(arrby2, n3 + n4, n5 - n4);
                    byteBuffer.position(n5);
                    break block6;
                }
                arrby = new byte[JCAUtil.getTempArraySize(n2)];
            }
            catch (SignatureException signatureException) {
                throw new ProviderException("update() failed", signatureException);
            }
            for (n2 = byteBuffer.remaining(); n2 > 0; n2 -= n) {
                n = Math.min(n2, arrby.length);
                byteBuffer.get(arrby, 0, n);
                this.engineUpdate(arrby, 0, n);
                continue;
            }
        }
        return;
    }

    protected abstract void engineUpdate(byte[] var1, int var2, int var3) throws SignatureException;

    protected abstract boolean engineVerify(byte[] var1) throws SignatureException;

    protected boolean engineVerify(byte[] arrby, int n, int n2) throws SignatureException {
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        return this.engineVerify(arrby2);
    }
}

