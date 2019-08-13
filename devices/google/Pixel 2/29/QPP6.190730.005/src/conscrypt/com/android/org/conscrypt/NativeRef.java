/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;

abstract class NativeRef {
    final long address;

    NativeRef(long l) {
        if (l != 0L) {
            this.address = l;
            return;
        }
        throw new NullPointerException("address == 0");
    }

    abstract void doFree(long var1);

    public boolean equals(Object object) {
        boolean bl = object instanceof NativeRef;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (((NativeRef)object).address == this.address) {
            bl2 = true;
        }
        return bl2;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.address != 0L) {
                this.doFree(this.address);
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public int hashCode() {
        long l = this.address;
        return (int)(l ^ l >>> 32);
    }

    static final class EC_GROUP
    extends NativeRef {
        EC_GROUP(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.EC_GROUP_clear_free(l);
        }
    }

    static final class EC_POINT
    extends NativeRef {
        EC_POINT(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.EC_POINT_clear_free(l);
        }
    }

    static final class EVP_CIPHER_CTX
    extends NativeRef {
        EVP_CIPHER_CTX(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.EVP_CIPHER_CTX_free(l);
        }
    }

    static final class EVP_MD_CTX
    extends NativeRef {
        EVP_MD_CTX(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.EVP_MD_CTX_destroy(l);
        }
    }

    static final class EVP_PKEY
    extends NativeRef {
        EVP_PKEY(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.EVP_PKEY_free(l);
        }
    }

    static final class EVP_PKEY_CTX
    extends NativeRef {
        EVP_PKEY_CTX(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.EVP_PKEY_CTX_free(l);
        }
    }

    static final class HMAC_CTX
    extends NativeRef {
        HMAC_CTX(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.HMAC_CTX_free(l);
        }
    }

    static final class SSL_SESSION
    extends NativeRef {
        SSL_SESSION(long l) {
            super(l);
        }

        @Override
        void doFree(long l) {
            NativeCrypto.SSL_SESSION_free(l);
        }
    }

}

