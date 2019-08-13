/*
 * Decompiled with CFR 0.145.
 */
package javax.crypto;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;

public abstract class CipherSpi {
    private int bufferCrypt(ByteBuffer object, ByteBuffer byteBuffer, boolean bl) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        if (object != null && byteBuffer != null) {
            int n = ((Buffer)object).position();
            int n2 = ((Buffer)object).limit();
            int n3 = n2 - n;
            if (bl && n3 == 0) {
                return 0;
            }
            int n4 = this.engineGetOutputSize(n3);
            if (byteBuffer.remaining() >= n4) {
                byte[] arrby;
                block22 : {
                    int n5;
                    boolean bl2 = ((ByteBuffer)object).hasArray();
                    boolean bl3 = byteBuffer.hasArray();
                    if (bl2 && bl3) {
                        byte[] arrby2 = ((ByteBuffer)object).array();
                        n = ((ByteBuffer)object).arrayOffset() + n;
                        byte[] arrby3 = byteBuffer.array();
                        n4 = byteBuffer.position();
                        int n6 = byteBuffer.arrayOffset() + n4;
                        n3 = bl ? this.engineUpdate(arrby2, n, n3, arrby3, n6) : this.engineDoFinal(arrby2, n, n3, arrby3, n6);
                        ((ByteBuffer)object).position(n2);
                        byteBuffer.position(n4 + n3);
                        return n3;
                    }
                    if (!bl2 && bl3) {
                        int n7;
                        int n8 = byteBuffer.position();
                        byte[] arrby4 = byteBuffer.array();
                        n4 = byteBuffer.arrayOffset();
                        byte[] arrby5 = new byte[CipherSpi.getTempArraySize(n3)];
                        n = n4 + n8;
                        n4 = 0;
                        do {
                            if ((n7 = Math.min(n3, arrby5.length)) > 0) {
                                ((ByteBuffer)object).get(arrby5, 0, n7);
                            }
                            int n9 = !bl && n3 == n7 ? this.engineDoFinal(arrby5, 0, n7, arrby4, n) : this.engineUpdate(arrby5, 0, n7, arrby4, n);
                            n4 += n9;
                            n += n9;
                        } while ((n3 -= n7) > 0);
                        byteBuffer.position(n8 + n4);
                        return n4;
                    }
                    if (bl2) {
                        arrby = ((ByteBuffer)object).array();
                        n5 = ((ByteBuffer)object).arrayOffset() + n;
                    } else {
                        arrby = new byte[CipherSpi.getTempArraySize(n3)];
                        n5 = 0;
                    }
                    byte[] arrby6 = new byte[CipherSpi.getTempArraySize(n4)];
                    n = arrby6.length;
                    n4 = 0;
                    int n10 = 0;
                    byte[] arrby7 = arrby;
                    arrby = arrby6;
                    do {
                        block21 : {
                            int n11;
                            int n12;
                            int n13;
                            block20 : {
                                int n14;
                                n12 = n == 0 ? arrby7.length : n;
                                n12 = Math.min(n3, n12);
                                if (!bl2 && n10 == 0 && n12 > 0) {
                                    ((ByteBuffer)object).get(arrby7, 0, n12);
                                    n5 = 0;
                                }
                                if (!bl && n3 == n12) {
                                    n13 = n12;
                                    n11 = n3;
                                    try {
                                        n10 = n14 = this.engineDoFinal(arrby7, n5, n13, arrby, 0);
                                        break block20;
                                    }
                                    catch (ShortBufferException shortBufferException) {
                                        n = n13;
                                        n3 = n11;
                                        break block21;
                                    }
                                }
                                n11 = n3;
                                n13 = n12;
                                try {
                                    n10 = n14 = this.engineUpdate(arrby7, n5, n13, arrby, 0);
                                }
                                catch (ShortBufferException shortBufferException) {
                                    n = n13;
                                    n3 = n11;
                                }
                            }
                            n11 = 0;
                            n13 = 0;
                            n5 += n12;
                            n3 -= n12;
                            if (n10 > 0) {
                                try {
                                    byteBuffer.put(arrby, 0, n10);
                                    n4 += n10;
                                }
                                catch (ShortBufferException shortBufferException) {
                                    n = n12;
                                    n10 = n13;
                                    break block21;
                                }
                            }
                            n10 = n11;
                            continue;
                        }
                        if (n10 != 0) break block22;
                        n = this.engineGetOutputSize(n);
                        arrby = new byte[n];
                        n10 = 1;
                    } while (n3 > 0);
                    if (bl2) {
                        ((ByteBuffer)object).position(n2);
                    }
                    return n4;
                }
                throw (ProviderException)new ProviderException("Could not determine buffer size").initCause((Throwable)arrby);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Need at least ");
            ((StringBuilder)object).append(n4);
            ((StringBuilder)object).append(" bytes of space in output buffer");
            throw new ShortBufferException(((StringBuilder)object).toString());
        }
        throw new NullPointerException("Input and output buffers must not be null");
    }

    static int getTempArraySize(int n) {
        return Math.min(4096, n);
    }

    protected int engineDoFinal(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        return this.bufferCrypt(byteBuffer, byteBuffer2, false);
    }

    protected abstract int engineDoFinal(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException;

    protected abstract byte[] engineDoFinal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException;

    protected abstract int engineGetBlockSize();

    protected abstract byte[] engineGetIV();

    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        throw new UnsupportedOperationException();
    }

    protected abstract int engineGetOutputSize(int var1);

    protected abstract AlgorithmParameters engineGetParameters();

    protected abstract void engineInit(int var1, Key var2, AlgorithmParameters var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException;

    protected abstract void engineInit(int var1, Key var2, SecureRandom var3) throws InvalidKeyException;

    protected abstract void engineInit(int var1, Key var2, AlgorithmParameterSpec var3, SecureRandom var4) throws InvalidKeyException, InvalidAlgorithmParameterException;

    protected abstract void engineSetMode(String var1) throws NoSuchAlgorithmException;

    protected abstract void engineSetPadding(String var1) throws NoSuchPaddingException;

    protected Key engineUnwrap(byte[] arrby, String string, int n) throws InvalidKeyException, NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    protected int engineUpdate(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws ShortBufferException {
        try {
            int n = this.bufferCrypt(byteBuffer, byteBuffer2, true);
            return n;
        }
        catch (BadPaddingException badPaddingException) {
            throw new ProviderException("Internal error in update()");
        }
        catch (IllegalBlockSizeException illegalBlockSizeException) {
            throw new ProviderException("Internal error in update()");
        }
    }

    protected abstract int engineUpdate(byte[] var1, int var2, int var3, byte[] var4, int var5) throws ShortBufferException;

    protected abstract byte[] engineUpdate(byte[] var1, int var2, int var3);

    protected void engineUpdateAAD(ByteBuffer byteBuffer) {
        throw new UnsupportedOperationException("The underlying Cipher implementation does not support this method");
    }

    protected void engineUpdateAAD(byte[] arrby, int n, int n2) {
        throw new UnsupportedOperationException("The underlying Cipher implementation does not support this method");
    }

    protected byte[] engineWrap(Key key) throws IllegalBlockSizeException, InvalidKeyException {
        throw new UnsupportedOperationException();
    }
}

