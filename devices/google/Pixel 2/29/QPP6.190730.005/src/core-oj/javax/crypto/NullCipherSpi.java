/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.CipherSpi;

final class NullCipherSpi
extends CipherSpi {
    protected NullCipherSpi() {
    }

    @Override
    protected int engineDoFinal(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        return this.engineUpdate(arrby, n, n2, arrby2, n3);
    }

    @Override
    protected byte[] engineDoFinal(byte[] arrby, int n, int n2) {
        return this.engineUpdate(arrby, n, n2);
    }

    @Override
    protected int engineGetBlockSize() {
        return 1;
    }

    @Override
    protected byte[] engineGetIV() {
        return new byte[8];
    }

    @Override
    protected int engineGetKeySize(Key key) {
        return 0;
    }

    @Override
    protected int engineGetOutputSize(int n) {
        return n;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override
    protected void engineInit(int n, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) {
    }

    @Override
    protected void engineInit(int n, Key key, SecureRandom secureRandom) {
    }

    @Override
    protected void engineInit(int n, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) {
    }

    @Override
    public void engineSetMode(String string) {
    }

    @Override
    public void engineSetPadding(String string) {
    }

    @Override
    protected int engineUpdate(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        if (arrby == null) {
            return 0;
        }
        System.arraycopy(arrby, n, arrby2, n3, n2);
        return n2;
    }

    @Override
    protected byte[] engineUpdate(byte[] arrby, int n, int n2) {
        if (arrby == null) {
            return null;
        }
        byte[] arrby2 = new byte[n2];
        System.arraycopy(arrby, n, arrby2, 0, n2);
        return arrby2;
    }
}

