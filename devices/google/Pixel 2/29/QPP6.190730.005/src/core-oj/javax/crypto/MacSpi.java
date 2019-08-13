/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.CipherSpi;

public abstract class MacSpi {
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }

    protected abstract byte[] engineDoFinal();

    protected abstract int engineGetMacLength();

    protected abstract void engineInit(Key var1, AlgorithmParameterSpec var2) throws InvalidKeyException, InvalidAlgorithmParameterException;

    protected abstract void engineReset();

    protected abstract void engineUpdate(byte var1);

    protected void engineUpdate(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return;
        }
        if (byteBuffer.hasArray()) {
            byte[] arrby = byteBuffer.array();
            int n = byteBuffer.arrayOffset();
            int n2 = byteBuffer.position();
            int n3 = byteBuffer.limit();
            this.engineUpdate(arrby, n + n2, n3 - n2);
            byteBuffer.position(n3);
        } else {
            int n;
            int n4;
            byte[] arrby = new byte[CipherSpi.getTempArraySize(n4)];
            for (n4 = byteBuffer.remaining(); n4 > 0; n4 -= n) {
                n = Math.min(n4, arrby.length);
                byteBuffer.get(arrby, 0, n);
                this.engineUpdate(arrby, 0, n);
            }
        }
    }

    protected abstract void engineUpdate(byte[] var1, int var2, int var3);
}

