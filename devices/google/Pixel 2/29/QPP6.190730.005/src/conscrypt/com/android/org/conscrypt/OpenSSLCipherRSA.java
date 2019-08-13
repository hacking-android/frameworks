/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.EvpMdRef;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateCrtKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateKey;
import com.android.org.conscrypt.OpenSSLRSAPublicKey;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;

abstract class OpenSSLCipherRSA
extends CipherSpi {
    private byte[] buffer;
    private int bufferOffset;
    boolean encrypting;
    private boolean inputTooLarge;
    OpenSSLKey key;
    int padding = 1;
    boolean usingPrivateKey;

    OpenSSLCipherRSA(int n) {
        this.padding = n;
    }

    void doCryptoInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException, InvalidKeyException {
    }

    abstract int doCryptoOperation(byte[] var1, byte[] var2) throws BadPaddingException, IllegalBlockSizeException;

    @Override
    protected int engineDoFinal(byte[] object, int n, int n2, byte[] arrby, int n3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        object = this.engineDoFinal((byte[])object, n, n2);
        n = ((byte[])object).length + n3;
        if (n <= arrby.length) {
            System.arraycopy(object, 0, arrby, n3, ((byte[])object).length);
            return ((Object)object).length;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("output buffer is too small ");
        ((StringBuilder)object).append(arrby.length);
        ((StringBuilder)object).append(" < ");
        ((StringBuilder)object).append(n);
        throw new ShortBufferException(((StringBuilder)object).toString());
    }

    @Override
    protected byte[] engineDoFinal(byte[] object, int n, int n2) throws IllegalBlockSizeException, BadPaddingException {
        if (object != null) {
            this.engineUpdate((byte[])object, n, n2);
        }
        if (!this.inputTooLarge) {
            n = this.bufferOffset;
            byte[] arrby = this.buffer;
            if (n != arrby.length) {
                if (this.padding == 3) {
                    object = new byte[arrby.length];
                    System.arraycopy(arrby, 0, object, arrby.length - n, n);
                } else {
                    object = Arrays.copyOf(arrby, n);
                }
            } else {
                object = this.buffer;
            }
            arrby = new byte[this.buffer.length];
            n = this.doCryptoOperation((byte[])object, arrby);
            object = arrby;
            if (!this.encrypting) {
                object = arrby;
                if (n != arrby.length) {
                    object = Arrays.copyOf(arrby, n);
                }
            }
            this.bufferOffset = 0;
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("input must be under ");
        ((StringBuilder)object).append(this.buffer.length);
        ((StringBuilder)object).append(" bytes");
        throw new IllegalBlockSizeException(((StringBuilder)object).toString());
    }

    @Override
    protected int engineGetBlockSize() {
        if (this.encrypting) {
            return this.paddedBlockSizeBytes();
        }
        return this.keySizeBytes();
    }

    @Override
    protected byte[] engineGetIV() {
        return null;
    }

    @Override
    protected int engineGetKeySize(Key key) throws InvalidKeyException {
        if (key instanceof OpenSSLRSAPrivateKey) {
            return ((OpenSSLRSAPrivateKey)key).getModulus().bitLength();
        }
        if (key instanceof RSAPrivateCrtKey) {
            return ((RSAPrivateCrtKey)key).getModulus().bitLength();
        }
        if (key instanceof RSAPrivateKey) {
            return ((RSAPrivateKey)key).getModulus().bitLength();
        }
        if (key instanceof OpenSSLRSAPublicKey) {
            return ((OpenSSLRSAPublicKey)key).getModulus().bitLength();
        }
        if (key instanceof RSAPublicKey) {
            return ((RSAPublicKey)key).getModulus().bitLength();
        }
        if (key == null) {
            throw new InvalidKeyException("RSA private or public key is null");
        }
        throw new InvalidKeyException("Need RSA private or public key");
    }

    @Override
    protected int engineGetOutputSize(int n) {
        if (this.encrypting) {
            return this.keySizeBytes();
        }
        return this.paddedBlockSizeBytes();
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        return null;
    }

    @Override
    protected void engineInit(int n, Key serializable, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameters == null) {
            this.engineInitInternal(n, (Key)serializable, null);
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("unknown param type: ");
        ((StringBuilder)serializable).append(algorithmParameters.getClass().getName());
        throw new InvalidAlgorithmParameterException(((StringBuilder)serializable).toString());
    }

    @Override
    protected void engineInit(int n, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            this.engineInitInternal(n, key, null);
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException("Algorithm parameters rejected when none supplied", invalidAlgorithmParameterException);
        }
    }

    @Override
    protected void engineInit(int n, Key serializable, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec == null) {
            this.engineInitInternal(n, (Key)serializable, algorithmParameterSpec);
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("unknown param type: ");
        ((StringBuilder)serializable).append(algorithmParameterSpec.getClass().getName());
        throw new InvalidAlgorithmParameterException(((StringBuilder)serializable).toString());
    }

    void engineInitInternal(int n, Key serializable, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        block12 : {
            block8 : {
                block11 : {
                    block10 : {
                        block9 : {
                            block7 : {
                                if (n != 1 && n != 3) {
                                    if (n != 2 && n != 4) {
                                        serializable = new StringBuilder();
                                        ((StringBuilder)serializable).append("Unsupported opmode ");
                                        ((StringBuilder)serializable).append(n);
                                        throw new InvalidParameterException(((StringBuilder)serializable).toString());
                                    }
                                    this.encrypting = false;
                                } else {
                                    this.encrypting = true;
                                }
                                if (!(serializable instanceof OpenSSLRSAPrivateKey)) break block7;
                                serializable = (OpenSSLRSAPrivateKey)serializable;
                                this.usingPrivateKey = true;
                                this.key = ((OpenSSLRSAPrivateKey)serializable).getOpenSSLKey();
                                break block8;
                            }
                            if (!(serializable instanceof RSAPrivateCrtKey)) break block9;
                            serializable = (RSAPrivateCrtKey)serializable;
                            this.usingPrivateKey = true;
                            this.key = OpenSSLRSAPrivateCrtKey.getInstance((RSAPrivateCrtKey)serializable);
                            break block8;
                        }
                        if (!(serializable instanceof RSAPrivateKey)) break block10;
                        serializable = (RSAPrivateKey)serializable;
                        this.usingPrivateKey = true;
                        this.key = OpenSSLRSAPrivateKey.getInstance((RSAPrivateKey)serializable);
                        break block8;
                    }
                    if (!(serializable instanceof OpenSSLRSAPublicKey)) break block11;
                    serializable = (OpenSSLRSAPublicKey)serializable;
                    this.usingPrivateKey = false;
                    this.key = ((OpenSSLRSAPublicKey)serializable).getOpenSSLKey();
                    break block8;
                }
                if (!(serializable instanceof RSAPublicKey)) break block12;
                serializable = (RSAPublicKey)serializable;
                this.usingPrivateKey = false;
                this.key = OpenSSLRSAPublicKey.getInstance((RSAPublicKey)serializable);
            }
            this.buffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
            this.bufferOffset = 0;
            this.inputTooLarge = false;
            this.doCryptoInit(algorithmParameterSpec);
            return;
        }
        if (serializable == null) {
            throw new InvalidKeyException("RSA private or public key is null");
        }
        throw new InvalidKeyException("Need RSA private or public key");
    }

    @Override
    protected void engineSetMode(String string) throws NoSuchAlgorithmException {
        CharSequence charSequence = string.toUpperCase(Locale.ROOT);
        if (!"NONE".equals(charSequence) && !"ECB".equals(charSequence)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("mode not supported: ");
            ((StringBuilder)charSequence).append(string);
            throw new NoSuchAlgorithmException(((StringBuilder)charSequence).toString());
        }
    }

    @Override
    protected void engineSetPadding(String string) throws NoSuchPaddingException {
        CharSequence charSequence = string.toUpperCase(Locale.ROOT);
        if ("PKCS1PADDING".equals(charSequence)) {
            this.padding = 1;
            return;
        }
        if ("NOPADDING".equals(charSequence)) {
            this.padding = 3;
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("padding not supported: ");
        ((StringBuilder)charSequence).append(string);
        throw new NoSuchPaddingException(((StringBuilder)charSequence).toString());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Key engineUnwrap(byte[] object, String object2, int n) throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            Object object3;
            void var3_8;
            byte[] arrby = this.engineDoFinal((byte[])object, 0, ((byte[])object).length);
            if (var3_8 == true) {
                KeyFactory keyFactory = KeyFactory.getInstance((String)object3);
                object3 = new X509EncodedKeySpec(arrby);
                return keyFactory.generatePublic((KeySpec)object3);
            }
            if (var3_8 == 2) {
                KeyFactory keyFactory = KeyFactory.getInstance((String)object3);
                object3 = new PKCS8EncodedKeySpec(arrby);
                return keyFactory.generatePrivate((KeySpec)object3);
            }
            if (var3_8 == 3) {
                return new SecretKeySpec(arrby, (String)object3);
            }
            object3 = new StringBuilder();
            ((StringBuilder)object3).append("wrappedKeyType == ");
            ((StringBuilder)object3).append((int)var3_8);
            UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException(((StringBuilder)object3).toString());
            throw unsupportedOperationException;
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            throw new InvalidKeyException(invalidKeySpecException);
        }
        catch (BadPaddingException badPaddingException) {
            throw new InvalidKeyException(badPaddingException);
        }
        catch (IllegalBlockSizeException illegalBlockSizeException) {
            throw new InvalidKeyException(illegalBlockSizeException);
        }
    }

    @Override
    protected int engineUpdate(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException {
        this.engineUpdate(arrby, n, n2);
        return 0;
    }

    @Override
    protected byte[] engineUpdate(byte[] arrby, int n, int n2) {
        int n3 = this.bufferOffset;
        byte[] arrby2 = this.buffer;
        if (n3 + n2 > arrby2.length) {
            this.inputTooLarge = true;
            return EmptyArray.BYTE;
        }
        System.arraycopy(arrby, n, arrby2, n3, n2);
        this.bufferOffset += n2;
        return EmptyArray.BYTE;
    }

    @Override
    protected byte[] engineWrap(Key arrby) throws IllegalBlockSizeException, InvalidKeyException {
        try {
            arrby = arrby.getEncoded();
            arrby = this.engineDoFinal(arrby, 0, arrby.length);
            return arrby;
        }
        catch (BadPaddingException badPaddingException) {
            IllegalBlockSizeException illegalBlockSizeException = new IllegalBlockSizeException();
            illegalBlockSizeException.initCause(badPaddingException);
            throw illegalBlockSizeException;
        }
    }

    boolean isInitialized() {
        boolean bl = this.key != null;
        return bl;
    }

    int keySizeBytes() {
        if (this.isInitialized()) {
            return NativeCrypto.RSA_size(this.key.getNativeRef());
        }
        throw new IllegalStateException("cipher is not initialized");
    }

    int paddedBlockSizeBytes() {
        int n;
        int n2 = n = this.keySizeBytes();
        if (this.padding == 1) {
            n2 = n - 1 - 10;
        }
        return n2;
    }

    public static abstract class DirectRSA
    extends OpenSSLCipherRSA {
        public DirectRSA(int n) {
            super(n);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        int doCryptoOperation(byte[] arrby, byte[] object) throws BadPaddingException, IllegalBlockSizeException {
            if (this.encrypting) {
                if (!this.usingPrivateKey) return NativeCrypto.RSA_public_encrypt(arrby.length, arrby, (byte[])object, this.key.getNativeRef(), this.padding);
                return NativeCrypto.RSA_private_encrypt(arrby.length, arrby, (byte[])object, this.key.getNativeRef(), this.padding);
            }
            try {
                if (!this.usingPrivateKey) return NativeCrypto.RSA_public_decrypt(arrby.length, arrby, (byte[])object, this.key.getNativeRef(), this.padding);
                return NativeCrypto.RSA_private_decrypt(arrby.length, arrby, (byte[])object, this.key.getNativeRef(), this.padding);
            }
            catch (SignatureException signatureException) {
                object = new IllegalBlockSizeException();
                ((Throwable)object).initCause(signatureException);
                throw object;
            }
        }
    }

    static class OAEP
    extends OpenSSLCipherRSA {
        private byte[] label;
        private long mgf1Md;
        private long oaepMd;
        private int oaepMdSizeBytes;
        private NativeRef.EVP_PKEY_CTX pkeyCtx;

        public OAEP(long l, int n) {
            super(4);
            this.mgf1Md = l;
            this.oaepMd = l;
            this.oaepMdSizeBytes = n;
        }

        private void readOAEPParameters(OAEPParameterSpec object) throws InvalidAlgorithmParameterException {
            String string = ((OAEPParameterSpec)object).getMGFAlgorithm().toUpperCase(Locale.US);
            AlgorithmParameterSpec algorithmParameterSpec = ((OAEPParameterSpec)object).getMGFParameters();
            if (("MGF1".equals(string) || "1.2.840.113549.1.1.8".equals(string)) && algorithmParameterSpec instanceof MGF1ParameterSpec) {
                algorithmParameterSpec = (MGF1ParameterSpec)algorithmParameterSpec;
                string = ((OAEPParameterSpec)object).getDigestAlgorithm().toUpperCase(Locale.US);
                try {
                    this.oaepMd = EvpMdRef.getEVP_MDByJcaDigestAlgorithmStandardName(string);
                    this.oaepMdSizeBytes = EvpMdRef.getDigestSizeBytesByJcaDigestAlgorithmStandardName(string);
                    this.mgf1Md = EvpMdRef.getEVP_MDByJcaDigestAlgorithmStandardName(((MGF1ParameterSpec)algorithmParameterSpec).getDigestAlgorithm());
                    object = ((OAEPParameterSpec)object).getPSource();
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    throw new InvalidAlgorithmParameterException(noSuchAlgorithmException);
                }
                if ("PSpecified".equals(((PSource)object).getAlgorithm()) && object instanceof PSource.PSpecified) {
                    this.label = ((PSource.PSpecified)object).getValue();
                    return;
                }
                throw new InvalidAlgorithmParameterException("Only PSpecified accepted for PSource");
            }
            throw new InvalidAlgorithmParameterException("Only MGF1 supported as mask generation function");
        }

        @Override
        void doCryptoInit(AlgorithmParameterSpec arrby) throws InvalidAlgorithmParameterException, InvalidKeyException {
            long l = this.encrypting ? NativeCrypto.EVP_PKEY_encrypt_init(this.key.getNativeRef()) : NativeCrypto.EVP_PKEY_decrypt_init(this.key.getNativeRef());
            this.pkeyCtx = new NativeRef.EVP_PKEY_CTX(l);
            if (arrby instanceof OAEPParameterSpec) {
                this.readOAEPParameters((OAEPParameterSpec)arrby);
            }
            NativeCrypto.EVP_PKEY_CTX_set_rsa_padding(this.pkeyCtx.address, 4);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_oaep_md(this.pkeyCtx.address, this.oaepMd);
            NativeCrypto.EVP_PKEY_CTX_set_rsa_mgf1_md(this.pkeyCtx.address, this.mgf1Md);
            arrby = this.label;
            if (arrby != null && arrby.length > 0) {
                NativeCrypto.EVP_PKEY_CTX_set_rsa_oaep_label(this.pkeyCtx.address, this.label);
            }
        }

        @Override
        int doCryptoOperation(byte[] arrby, byte[] arrby2) throws BadPaddingException, IllegalBlockSizeException {
            if (this.encrypting) {
                return NativeCrypto.EVP_PKEY_encrypt(this.pkeyCtx, arrby2, 0, arrby, 0, arrby.length);
            }
            return NativeCrypto.EVP_PKEY_decrypt(this.pkeyCtx, arrby2, 0, arrby, 0, arrby.length);
        }

        @Override
        protected AlgorithmParameters engineGetParameters() {
            if (!this.isInitialized()) {
                return null;
            }
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("OAEP");
                PSource.PSpecified pSpecified = this.label == null ? PSource.PSpecified.DEFAULT : new PSource.PSpecified(this.label);
                String string = EvpMdRef.getJcaDigestAlgorithmStandardNameFromEVP_MD(this.oaepMd);
                MGF1ParameterSpec mGF1ParameterSpec = new MGF1ParameterSpec(EvpMdRef.getJcaDigestAlgorithmStandardNameFromEVP_MD(this.mgf1Md));
                OAEPParameterSpec oAEPParameterSpec = new OAEPParameterSpec(string, "MGF1", mGF1ParameterSpec, pSpecified);
                algorithmParameters.init(oAEPParameterSpec);
                return algorithmParameters;
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                throw new RuntimeException("No providers of AlgorithmParameters.OAEP available");
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw (Error)((Throwable)((Object)new AssertionError((Object)"OAEP not supported"))).initCause(noSuchAlgorithmException);
            }
        }

        @Override
        protected void engineInit(int n, Key key, AlgorithmParameters algorithmParameters, SecureRandom object) throws InvalidKeyException, InvalidAlgorithmParameterException {
            object = null;
            if (algorithmParameters != null) {
                try {
                    object = algorithmParameters.getParameterSpec(OAEPParameterSpec.class);
                }
                catch (InvalidParameterSpecException invalidParameterSpecException) {
                    throw new InvalidAlgorithmParameterException("Only OAEP parameters are supported", invalidParameterSpecException);
                }
            }
            this.engineInitInternal(n, key, (AlgorithmParameterSpec)object);
        }

        @Override
        protected void engineInit(int n, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
            if (algorithmParameterSpec != null && !(algorithmParameterSpec instanceof OAEPParameterSpec)) {
                throw new InvalidAlgorithmParameterException("Only OAEPParameterSpec accepted in OAEP mode");
            }
            this.engineInitInternal(n, key, algorithmParameterSpec);
        }

        @Override
        void engineInitInternal(int n, Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
            block7 : {
                block6 : {
                    block5 : {
                        if (n == 1 || n == 3) break block5;
                        if (!(n != 2 && n != 4 || key instanceof PrivateKey)) {
                            throw new InvalidKeyException("Only private keys may be used to decrypt");
                        }
                        break block6;
                    }
                    if (!(key instanceof PublicKey)) break block7;
                }
                super.engineInitInternal(n, key, algorithmParameterSpec);
                return;
            }
            throw new InvalidKeyException("Only public keys may be used to encrypt");
        }

        @Override
        protected void engineSetPadding(String string) throws NoSuchPaddingException {
            if (string.toUpperCase(Locale.US).equals("OAEPPADDING")) {
                this.padding = 4;
                return;
            }
            throw new NoSuchPaddingException("Only OAEP padding is supported");
        }

        @Override
        int paddedBlockSizeBytes() {
            return this.keySizeBytes() - (this.oaepMdSizeBytes * 2 + 2);
        }

        public static final class SHA1
        extends OAEP {
            public SHA1() {
                super(EvpMdRef.SHA1.EVP_MD, EvpMdRef.SHA1.SIZE_BYTES);
            }
        }

        public static final class SHA224
        extends OAEP {
            public SHA224() {
                super(EvpMdRef.SHA224.EVP_MD, EvpMdRef.SHA224.SIZE_BYTES);
            }
        }

        public static final class SHA256
        extends OAEP {
            public SHA256() {
                super(EvpMdRef.SHA256.EVP_MD, EvpMdRef.SHA256.SIZE_BYTES);
            }
        }

        public static final class SHA384
        extends OAEP {
            public SHA384() {
                super(EvpMdRef.SHA384.EVP_MD, EvpMdRef.SHA384.SIZE_BYTES);
            }
        }

        public static final class SHA512
        extends OAEP {
            public SHA512() {
                super(EvpMdRef.SHA512.EVP_MD, EvpMdRef.SHA512.SIZE_BYTES);
            }
        }

    }

    public static final class PKCS1
    extends DirectRSA {
        public PKCS1() {
            super(1);
        }
    }

    public static final class Raw
    extends DirectRSA {
        public Raw() {
            super(3);
        }
    }

}

