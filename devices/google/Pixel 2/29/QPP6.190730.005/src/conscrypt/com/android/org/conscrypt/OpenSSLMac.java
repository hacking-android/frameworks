/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.EvpMdRef;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.MacSpi;
import javax.crypto.SecretKey;

public abstract class OpenSSLMac
extends MacSpi {
    private NativeRef.HMAC_CTX ctx;
    private final long evp_md;
    private byte[] keyBytes;
    private final byte[] singleByte = new byte[1];
    private final int size;

    private OpenSSLMac(long l, int n) {
        this.evp_md = l;
        this.size = n;
    }

    private final void resetContext() {
        NativeRef.HMAC_CTX hMAC_CTX = new NativeRef.HMAC_CTX(NativeCrypto.HMAC_CTX_new());
        byte[] arrby = this.keyBytes;
        if (arrby != null) {
            NativeCrypto.HMAC_Init_ex(hMAC_CTX, arrby, this.evp_md);
        }
        this.ctx = hMAC_CTX;
    }

    @Override
    protected byte[] engineDoFinal() {
        byte[] arrby = NativeCrypto.HMAC_Final(this.ctx);
        this.resetContext();
        return arrby;
    }

    @Override
    protected int engineGetMacLength() {
        return this.size;
    }

    @Override
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (key instanceof SecretKey) {
            if (algorithmParameterSpec == null) {
                this.keyBytes = key.getEncoded();
                if (this.keyBytes != null) {
                    this.resetContext();
                    return;
                }
                throw new InvalidKeyException("key cannot be encoded");
            }
            throw new InvalidAlgorithmParameterException("unknown parameter type");
        }
        throw new InvalidKeyException("key must be a SecretKey");
    }

    @Override
    protected void engineReset() {
        this.resetContext();
    }

    @Override
    protected void engineUpdate(byte by) {
        byte[] arrby = this.singleByte;
        arrby[0] = by;
        this.engineUpdate(arrby, 0, 1);
    }

    @Override
    protected void engineUpdate(ByteBuffer byteBuffer) {
        if (!byteBuffer.hasRemaining()) {
            return;
        }
        if (!byteBuffer.isDirect()) {
            super.engineUpdate(byteBuffer);
            return;
        }
        long l = NativeCrypto.getDirectBufferAddress(byteBuffer);
        if (l == 0L) {
            super.engineUpdate(byteBuffer);
            return;
        }
        int n = byteBuffer.position();
        if (n >= 0) {
            long l2 = n;
            int n2 = byteBuffer.remaining();
            if (n2 >= 0) {
                NativeCrypto.HMAC_UpdateDirect(this.ctx, l2 + l, n2);
                byteBuffer.position(n + n2);
                return;
            }
            throw new RuntimeException("Negative remaining amount");
        }
        throw new RuntimeException("Negative position");
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) {
        NativeCrypto.HMAC_Update(this.ctx, arrby, n, n2);
    }

    public static final class HmacMD5
    extends OpenSSLMac {
        public HmacMD5() {
            super(EvpMdRef.MD5.EVP_MD, EvpMdRef.MD5.SIZE_BYTES);
        }
    }

    public static final class HmacSHA1
    extends OpenSSLMac {
        public HmacSHA1() {
            super(EvpMdRef.SHA1.EVP_MD, EvpMdRef.SHA1.SIZE_BYTES);
        }
    }

    public static final class HmacSHA224
    extends OpenSSLMac {
        public HmacSHA224() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA224.EVP_MD, EvpMdRef.SHA224.SIZE_BYTES);
        }
    }

    public static final class HmacSHA256
    extends OpenSSLMac {
        public HmacSHA256() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA256.EVP_MD, EvpMdRef.SHA256.SIZE_BYTES);
        }
    }

    public static final class HmacSHA384
    extends OpenSSLMac {
        public HmacSHA384() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA384.EVP_MD, EvpMdRef.SHA384.SIZE_BYTES);
        }
    }

    public static final class HmacSHA512
    extends OpenSSLMac {
        public HmacSHA512() {
            super(EvpMdRef.SHA512.EVP_MD, EvpMdRef.SHA512.SIZE_BYTES);
        }
    }

}

