/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.EvpMdRef;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;

public class OpenSSLMessageDigestJDK
extends MessageDigestSpi
implements Cloneable {
    private final NativeRef.EVP_MD_CTX ctx;
    private boolean digestInitializedInContext;
    private final long evp_md;
    private final byte[] singleByte = new byte[1];
    private final int size;

    private OpenSSLMessageDigestJDK(long l, int n) throws NoSuchAlgorithmException {
        this.evp_md = l;
        this.size = n;
        this.ctx = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
    }

    private OpenSSLMessageDigestJDK(long l, int n, NativeRef.EVP_MD_CTX eVP_MD_CTX, boolean bl) {
        this.evp_md = l;
        this.size = n;
        this.ctx = eVP_MD_CTX;
        this.digestInitializedInContext = bl;
    }

    private void ensureDigestInitializedInContext() {
        synchronized (this) {
            if (!this.digestInitializedInContext) {
                NativeCrypto.EVP_DigestInit_ex(this.ctx, this.evp_md);
                this.digestInitializedInContext = true;
            }
            return;
        }
    }

    @Override
    public Object clone() {
        NativeRef.EVP_MD_CTX eVP_MD_CTX = new NativeRef.EVP_MD_CTX(NativeCrypto.EVP_MD_CTX_create());
        if (this.digestInitializedInContext) {
            NativeCrypto.EVP_MD_CTX_copy_ex(eVP_MD_CTX, this.ctx);
        }
        return new OpenSSLMessageDigestJDK(this.evp_md, this.size, eVP_MD_CTX, this.digestInitializedInContext);
    }

    @Override
    protected byte[] engineDigest() {
        synchronized (this) {
            this.ensureDigestInitializedInContext();
            byte[] arrby = new byte[this.size];
            NativeCrypto.EVP_DigestFinal_ex(this.ctx, arrby, 0);
            this.digestInitializedInContext = false;
            return arrby;
        }
    }

    @Override
    protected int engineGetDigestLength() {
        return this.size;
    }

    @Override
    protected void engineReset() {
        synchronized (this) {
            NativeCrypto.EVP_MD_CTX_cleanup(this.ctx);
            this.digestInitializedInContext = false;
            return;
        }
    }

    @Override
    protected void engineUpdate(byte by) {
        synchronized (this) {
            this.singleByte[0] = by;
            this.engineUpdate(this.singleByte, 0, 1);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected void engineUpdate(ByteBuffer object) {
        synchronized (this) {
            boolean bl = ((Buffer)object).hasRemaining();
            if (!bl) {
                return;
            }
            if (!((ByteBuffer)object).isDirect()) {
                super.engineUpdate((ByteBuffer)object);
                return;
            }
            long l = NativeCrypto.getDirectBufferAddress((Buffer)object);
            if (l == 0L) {
                super.engineUpdate((ByteBuffer)object);
                return;
            }
            int n = ((Buffer)object).position();
            if (n < 0) {
                object = new RuntimeException("Negative position");
                throw object;
            }
            long l2 = n;
            int n2 = ((Buffer)object).remaining();
            if (n2 >= 0) {
                this.ensureDigestInitializedInContext();
                NativeCrypto.EVP_DigestUpdateDirect(this.ctx, l2 + l, n2);
                ((Buffer)object).position(n + n2);
                return;
            }
            object = new RuntimeException("Negative remaining amount");
            throw object;
        }
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) {
        synchronized (this) {
            this.ensureDigestInitializedInContext();
            NativeCrypto.EVP_DigestUpdate(this.ctx, arrby, n, n2);
            return;
        }
    }

    public static final class MD5
    extends OpenSSLMessageDigestJDK {
        public MD5() throws NoSuchAlgorithmException {
            super(EvpMdRef.MD5.EVP_MD, EvpMdRef.MD5.SIZE_BYTES);
        }
    }

    public static final class SHA1
    extends OpenSSLMessageDigestJDK {
        public SHA1() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA1.EVP_MD, EvpMdRef.SHA1.SIZE_BYTES);
        }
    }

    public static final class SHA224
    extends OpenSSLMessageDigestJDK {
        public SHA224() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA224.EVP_MD, EvpMdRef.SHA224.SIZE_BYTES);
        }
    }

    public static final class SHA256
    extends OpenSSLMessageDigestJDK {
        public SHA256() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA256.EVP_MD, EvpMdRef.SHA256.SIZE_BYTES);
        }
    }

    public static final class SHA384
    extends OpenSSLMessageDigestJDK {
        public SHA384() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA384.EVP_MD, EvpMdRef.SHA384.SIZE_BYTES);
        }
    }

    public static final class SHA512
    extends OpenSSLMessageDigestJDK {
        public SHA512() throws NoSuchAlgorithmException {
            super(EvpMdRef.SHA512.EVP_MD, EvpMdRef.SHA512.SIZE_BYTES);
        }
    }

}

