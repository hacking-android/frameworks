/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.ArrayUtils;
import com.android.org.conscrypt.EmptyArray;
import com.android.org.conscrypt.GCMParameters;
import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.Platform;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;
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
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Locale;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class OpenSSLCipher
extends CipherSpi {
    private int blockSize;
    byte[] encodedKey;
    private boolean encrypting;
    byte[] iv;
    Mode mode = Mode.ECB;
    private Padding padding = Padding.PKCS5PADDING;

    OpenSSLCipher() {
    }

    OpenSSLCipher(Mode mode, Padding padding) {
        this.mode = mode;
        this.padding = padding;
        this.blockSize = this.getCipherBlockSize();
    }

    private byte[] checkAndSetEncodedKey(int n, Key object) throws InvalidKeyException {
        if (n != 1 && n != 3) {
            if (n != 2 && n != 4) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported opmode ");
                ((StringBuilder)object).append(n);
                throw new InvalidParameterException(((StringBuilder)object).toString());
            }
            this.encrypting = false;
        } else {
            this.encrypting = true;
        }
        if (object instanceof SecretKey) {
            if ((object = object.getEncoded()) != null) {
                this.checkSupportedKeySize(((byte[])object).length);
                this.encodedKey = object;
                return object;
            }
            throw new InvalidKeyException("key.getEncoded() == null");
        }
        throw new InvalidKeyException("Only SecretKey is supported");
    }

    abstract void checkSupportedKeySize(int var1) throws InvalidKeyException;

    abstract void checkSupportedMode(Mode var1) throws NoSuchAlgorithmException;

    abstract void checkSupportedPadding(Padding var1) throws NoSuchPaddingException;

    abstract int doFinalInternal(byte[] var1, int var2, int var3) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException;

    @Override
    protected int engineDoFinal(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        if (arrby2 != null) {
            int n4 = this.getOutputSizeForFinal(n2);
            if (n2 > 0) {
                n2 = this.updateInternal(arrby, n, n2, arrby2, n3, n4);
                n3 += n2;
                n = n4 - n2;
            } else {
                n2 = 0;
                n = n4;
            }
            return this.doFinalInternal(arrby2, n3, n) + n2;
        }
        throw new NullPointerException("output == null");
    }

    @Override
    protected byte[] engineDoFinal(byte[] arrby, int n, int n2) throws IllegalBlockSizeException, BadPaddingException {
        int n3 = this.getOutputSizeForFinal(n2);
        byte[] arrby2 = new byte[n3];
        if (n2 > 0) {
            try {
                n = this.updateInternal(arrby, n, n2, arrby2, 0, n3);
            }
            catch (ShortBufferException shortBufferException) {
                throw new RuntimeException("our calculated buffer was too small", shortBufferException);
            }
        } else {
            n = 0;
        }
        try {
            n2 = this.doFinalInternal(arrby2, n, n3 - n);
        }
        catch (ShortBufferException shortBufferException) {
            throw new RuntimeException("our calculated buffer was too small", shortBufferException);
        }
        if ((n += n2) == arrby2.length) {
            return arrby2;
        }
        if (n == 0) {
            return EmptyArray.BYTE;
        }
        return Arrays.copyOfRange(arrby2, 0, n);
    }

    @Override
    protected int engineGetBlockSize() {
        return this.blockSize;
    }

    @Override
    protected byte[] engineGetIV() {
        return this.iv;
    }

    @Override
    protected int engineGetKeySize(Key arrby) throws InvalidKeyException {
        if (arrby instanceof SecretKey) {
            if ((arrby = arrby.getEncoded()) != null) {
                this.checkSupportedKeySize(arrby.length);
                return arrby.length * 8;
            }
            throw new InvalidKeyException("key.getEncoded() == null");
        }
        throw new InvalidKeyException("Only SecretKey is supported");
    }

    @Override
    protected int engineGetOutputSize(int n) {
        return Math.max(this.getOutputSizeForUpdate(n), this.getOutputSizeForFinal(n));
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        Object object = this.iv;
        if (object != null && ((byte[])object).length > 0) {
            try {
                object = AlgorithmParameters.getInstance(this.getBaseCipherName());
                IvParameterSpec ivParameterSpec = new IvParameterSpec(this.iv);
                ((AlgorithmParameters)object).init(ivParameterSpec);
                return object;
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                return null;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                return null;
            }
        }
        return null;
    }

    @Override
    protected void engineInit(int n, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.engineInit(n, key, this.getParameterSpec(algorithmParameters), secureRandom);
    }

    @Override
    protected void engineInit(int n, Key key, SecureRandom secureRandom) throws InvalidKeyException {
        this.checkAndSetEncodedKey(n, key);
        try {
            this.engineInitInternal(this.encodedKey, null, secureRandom);
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new RuntimeException(invalidAlgorithmParameterException);
        }
    }

    @Override
    protected void engineInit(int n, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.checkAndSetEncodedKey(n, key);
        this.engineInitInternal(this.encodedKey, algorithmParameterSpec, secureRandom);
    }

    abstract void engineInitInternal(byte[] var1, AlgorithmParameterSpec var2, SecureRandom var3) throws InvalidKeyException, InvalidAlgorithmParameterException;

    @Override
    protected void engineSetMode(String object) throws NoSuchAlgorithmException {
        Mode mode;
        try {
            mode = Mode.valueOf(((String)object).toUpperCase(Locale.US));
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No such mode: ");
            stringBuilder.append((String)object);
            object = new NoSuchAlgorithmException(stringBuilder.toString());
            ((Throwable)object).initCause(illegalArgumentException);
            throw object;
        }
        this.checkSupportedMode(mode);
        this.mode = mode;
    }

    @Override
    protected void engineSetPadding(String object) throws NoSuchPaddingException {
        Object object2 = ((String)object).toUpperCase(Locale.US);
        try {
            object2 = Padding.getNormalized(object2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No such padding: ");
            stringBuilder.append((String)object);
            object = new NoSuchPaddingException(stringBuilder.toString());
            ((Throwable)object).initCause(illegalArgumentException);
            throw object;
        }
        this.checkSupportedPadding((Padding)((Object)object2));
        this.padding = object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected Key engineUnwrap(byte[] object, String object2, int n) throws InvalidKeyException, NoSuchAlgorithmException {
        try {
            object = this.engineDoFinal((byte[])object, 0, ((Object)object).length);
            if (n == 1) {
                object2 = KeyFactory.getInstance((String)object2);
                X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[])object);
                return ((KeyFactory)object2).generatePublic(x509EncodedKeySpec);
            }
            if (n == 2) {
                object2 = KeyFactory.getInstance((String)object2);
                PKCS8EncodedKeySpec pKCS8EncodedKeySpec = new PKCS8EncodedKeySpec((byte[])object);
                return ((KeyFactory)object2).generatePrivate(pKCS8EncodedKeySpec);
            }
            if (n == 3) {
                return new SecretKeySpec((byte[])object, (String)object2);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("wrappedKeyType == ");
            ((StringBuilder)object).append(n);
            object2 = new UnsupportedOperationException(((StringBuilder)object).toString());
            throw object2;
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
        return this.updateInternal(arrby, n, n2, arrby2, n3, this.getOutputSizeForUpdate(n2));
    }

    @Override
    protected byte[] engineUpdate(byte[] arrby, int n, int n2) {
        int n3 = this.getOutputSizeForUpdate(n2);
        byte[] arrby2 = n3 > 0 ? new byte[n3] : EmptyArray.BYTE;
        try {
            n = this.updateInternal(arrby, n, n2, arrby2, 0, n3);
        }
        catch (ShortBufferException shortBufferException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("calculated buffer size was wrong: ");
            stringBuilder.append(n3);
            throw new RuntimeException(stringBuilder.toString());
        }
        if (arrby2.length == n) {
            return arrby2;
        }
        if (n == 0) {
            return EmptyArray.BYTE;
        }
        return Arrays.copyOfRange(arrby2, 0, n);
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

    abstract String getBaseCipherName();

    abstract int getCipherBlockSize();

    abstract int getOutputSizeForFinal(int var1);

    abstract int getOutputSizeForUpdate(int var1);

    Padding getPadding() {
        return this.padding;
    }

    protected AlgorithmParameterSpec getParameterSpec(AlgorithmParameters object) throws InvalidAlgorithmParameterException {
        if (object != null) {
            try {
                object = ((AlgorithmParameters)object).getParameterSpec(IvParameterSpec.class);
                return object;
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                throw new InvalidAlgorithmParameterException("Params must be convertible to IvParameterSpec", invalidParameterSpecException);
            }
        }
        return null;
    }

    boolean isEncrypting() {
        return this.encrypting;
    }

    boolean supportsVariableSizeIv() {
        return false;
    }

    boolean supportsVariableSizeKey() {
        return false;
    }

    abstract int updateInternal(byte[] var1, int var2, int var3, byte[] var4, int var5, int var6) throws ShortBufferException;

    public static abstract class EVP_AEAD
    extends OpenSSLCipher {
        private static final int DEFAULT_TAG_SIZE_BITS = 128;
        private static int lastGlobalMessageSize = 32;
        private byte[] aad;
        byte[] buf;
        int bufCount;
        long evpAead;
        private boolean mustInitialize;
        private byte[] previousIv;
        private byte[] previousKey;
        int tagLengthInBytes;

        public EVP_AEAD(Mode mode) {
            super(mode, Padding.NOPADDING);
        }

        private boolean arraysAreEqual(byte[] arrby, byte[] arrby2) {
            int n = arrby.length;
            int n2 = arrby2.length;
            boolean bl = false;
            if (n != n2) {
                return false;
            }
            n = 0;
            for (n2 = 0; n2 < arrby.length; ++n2) {
                n |= arrby[n2] ^ arrby2[n2];
            }
            if (n == 0) {
                bl = true;
            }
            return bl;
        }

        private void checkInitialization() {
            if (!this.mustInitialize) {
                return;
            }
            throw new IllegalStateException("Cannot re-use same key and IV for multiple encryptions");
        }

        private void expand(int n) {
            int n2 = this.bufCount;
            byte[] arrby = this.buf;
            if (n2 + n <= arrby.length) {
                return;
            }
            byte[] arrby2 = new byte[(n2 + n) * 2];
            System.arraycopy(arrby, 0, arrby2, 0, n2);
            this.buf = arrby2;
        }

        private void reset() {
            this.aad = null;
            int n = lastGlobalMessageSize;
            byte[] arrby = this.buf;
            if (arrby == null) {
                this.buf = new byte[n];
            } else {
                int n2 = this.bufCount;
                if (n2 > 0 && n2 != n) {
                    lastGlobalMessageSize = n2;
                    if (arrby.length != n2) {
                        this.buf = new byte[n2];
                    }
                }
            }
            this.bufCount = 0;
        }

        private void throwAEADBadTagExceptionIfAvailable(String object, Throwable throwable) throws BadPaddingException {
            Object object2;
            Constructor<?> constructor;
            Object object3;
            try {
                constructor = Class.forName("javax.crypto.AEADBadTagException").getConstructor(String.class);
                object3 = null;
                object2 = null;
            }
            catch (Exception exception) {
                return;
            }
            object2 = object = (BadPaddingException)constructor.newInstance(object);
            object3 = object;
            try {
                ((Throwable)object).initCause(throwable);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw (BadPaddingException)new BadPaddingException().initCause(invocationTargetException.getTargetException());
            }
            catch (InstantiationException instantiationException) {
                object = object2;
            }
            catch (IllegalAccessException illegalAccessException) {
                object = object3;
            }
            if (object == null) {
                return;
            }
            throw object;
        }

        @Override
        void checkSupportedPadding(Padding padding) throws NoSuchPaddingException {
            if (padding == Padding.NOPADDING) {
                return;
            }
            throw new NoSuchPaddingException("Must be NoPadding for AEAD ciphers");
        }

        @Override
        int doFinalInternal(byte[] arrby, int n, int n2) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
            this.checkInitialization();
            try {
                n = this.isEncrypting() ? NativeCrypto.EVP_AEAD_CTX_seal(this.evpAead, this.encodedKey, this.tagLengthInBytes, arrby, n, this.iv, this.buf, 0, this.bufCount, this.aad) : NativeCrypto.EVP_AEAD_CTX_open(this.evpAead, this.encodedKey, this.tagLengthInBytes, arrby, n, this.iv, this.buf, 0, this.bufCount, this.aad);
            }
            catch (BadPaddingException badPaddingException) {
                this.throwAEADBadTagExceptionIfAvailable(badPaddingException.getMessage(), badPaddingException.getCause());
                throw badPaddingException;
            }
            if (this.isEncrypting()) {
                this.mustInitialize = true;
            }
            this.reset();
            return n;
        }

        @Override
        protected int engineDoFinal(byte[] arrby, int n, int n2, byte[] arrby2, int n3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
            if (arrby2 != null && this.getOutputSizeForFinal(n2) > arrby2.length - n3) {
                throw new ShortBufferException("Insufficient output space");
            }
            return super.engineDoFinal(arrby, n, n2, arrby2, n3);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void engineInitInternal(byte[] object, AlgorithmParameterSpec arrby, SecureRandom arrby2) throws InvalidKeyException, InvalidAlgorithmParameterException {
            int n;
            if (arrby == null) {
                arrby = null;
                n = 128;
            } else {
                GCMParameters gCMParameters = Platform.fromGCMParameterSpec((AlgorithmParameterSpec)arrby);
                if (gCMParameters != null) {
                    arrby = gCMParameters.getIV();
                    n = gCMParameters.getTLen();
                } else if (arrby instanceof IvParameterSpec) {
                    arrby = ((IvParameterSpec)arrby).getIV();
                    n = 128;
                } else {
                    n = 128;
                    arrby = null;
                }
            }
            if (n % 8 != 0) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Tag length must be a multiple of 8; was ");
                ((StringBuilder)object).append(this.tagLengthInBytes);
                throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
            }
            this.tagLengthInBytes = n / 8;
            boolean bl = this.isEncrypting();
            this.evpAead = this.getEVP_AEAD(((Object)object).length);
            n = NativeCrypto.EVP_AEAD_nonce_length(this.evpAead);
            if (arrby == null && n != 0) {
                if (!bl) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("IV must be specified in ");
                    ((StringBuilder)object).append((Object)this.mode);
                    ((StringBuilder)object).append(" mode");
                    throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                }
                arrby = new byte[n];
                if (arrby2 != null) {
                    arrby2.nextBytes(arrby);
                    arrby2 = arrby;
                } else {
                    NativeCrypto.RAND_bytes(arrby);
                    arrby2 = arrby;
                }
            } else {
                if (n == 0 && arrby != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("IV not used in ");
                    ((StringBuilder)object).append((Object)this.mode);
                    ((StringBuilder)object).append(" mode");
                    throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                }
                arrby2 = arrby;
                if (arrby != null) {
                    if (arrby.length != n) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Expected IV length of ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" but was ");
                        ((StringBuilder)object).append(arrby.length);
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                    }
                    arrby2 = arrby;
                }
            }
            if (this.isEncrypting() && arrby2 != null) {
                arrby = this.previousKey;
                if (arrby != null && this.previousIv != null && this.arraysAreEqual(arrby, (byte[])object) && this.arraysAreEqual(this.previousIv, arrby2)) {
                    this.mustInitialize = true;
                    throw new InvalidAlgorithmParameterException("When using AEAD key and IV must not be re-used");
                }
                this.previousKey = object;
                this.previousIv = arrby2;
            }
            this.mustInitialize = false;
            this.iv = arrby2;
            this.reset();
        }

        @Override
        protected void engineUpdateAAD(ByteBuffer byteBuffer) {
            this.checkInitialization();
            byte[] arrby = this.aad;
            if (arrby == null) {
                this.aad = new byte[byteBuffer.remaining()];
                byteBuffer.get(this.aad);
            } else {
                byte[] arrby2 = new byte[arrby.length + byteBuffer.remaining()];
                arrby = this.aad;
                System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
                byteBuffer.get(arrby2, this.aad.length, byteBuffer.remaining());
                this.aad = arrby2;
            }
        }

        @Override
        protected void engineUpdateAAD(byte[] arrby, int n, int n2) {
            this.checkInitialization();
            byte[] arrby2 = this.aad;
            if (arrby2 == null) {
                this.aad = Arrays.copyOfRange(arrby, n, n + n2);
            } else {
                byte[] arrby3 = new byte[arrby2.length + n2];
                System.arraycopy(arrby2, 0, arrby3, 0, arrby2.length);
                System.arraycopy(arrby, n, arrby3, this.aad.length, n2);
                this.aad = arrby3;
            }
        }

        abstract long getEVP_AEAD(int var1) throws InvalidKeyException;

        @Override
        int getOutputSizeForFinal(int n) {
            int n2 = this.bufCount;
            int n3 = this.isEncrypting() ? NativeCrypto.EVP_AEAD_max_overhead(this.evpAead) : 0;
            return n2 + n + n3;
        }

        @Override
        int getOutputSizeForUpdate(int n) {
            return 0;
        }

        @Override
        int updateInternal(byte[] arrby, int n, int n2, byte[] arrby2, int n3, int n4) throws ShortBufferException {
            this.checkInitialization();
            if (this.buf != null) {
                ArrayUtils.checkOffsetAndCount(arrby.length, n, n2);
                if (n2 > 0) {
                    this.expand(n2);
                    System.arraycopy(arrby, n, this.buf, this.bufCount, n2);
                    this.bufCount += n2;
                }
                return 0;
            }
            throw new IllegalStateException("Cipher not initialized");
        }

        public static abstract class AES
        extends EVP_AEAD {
            private static final int AES_BLOCK_SIZE = 16;

            AES(Mode mode) {
                super(mode);
            }

            @Override
            void checkSupportedKeySize(int n) throws InvalidKeyException {
                if (n != 16 && n != 32) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported key size: ");
                    stringBuilder.append(n);
                    stringBuilder.append(" bytes (must be 16 or 32)");
                    throw new InvalidKeyException(stringBuilder.toString());
                }
            }

            @Override
            String getBaseCipherName() {
                return "AES";
            }

            @Override
            int getCipherBlockSize() {
                return 16;
            }

            public static class GCM
            extends AES {
                public GCM() {
                    super(Mode.GCM);
                }

                @Override
                void checkSupportedMode(Mode mode) throws NoSuchAlgorithmException {
                    if (mode == Mode.GCM) {
                        return;
                    }
                    throw new NoSuchAlgorithmException("Mode must be GCM");
                }

                @Override
                protected AlgorithmParameters engineGetParameters() {
                    if (this.iv == null) {
                        return null;
                    }
                    AlgorithmParameterSpec algorithmParameterSpec = Platform.toGCMParameterSpec(this.tagLengthInBytes * 8, this.iv);
                    if (algorithmParameterSpec == null) {
                        return super.engineGetParameters();
                    }
                    try {
                        AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("GCM");
                        algorithmParameters.init(algorithmParameterSpec);
                        return algorithmParameters;
                    }
                    catch (InvalidParameterSpecException invalidParameterSpecException) {
                        return null;
                    }
                    catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                        throw (Error)((Throwable)((Object)new AssertionError((Object)"GCM not supported"))).initCause(noSuchAlgorithmException);
                    }
                }

                @Override
                long getEVP_AEAD(int n) throws InvalidKeyException {
                    if (n == 16) {
                        return NativeCrypto.EVP_aead_aes_128_gcm();
                    }
                    if (n == 32) {
                        return NativeCrypto.EVP_aead_aes_256_gcm();
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected key length: ");
                    stringBuilder.append(n);
                    throw new RuntimeException(stringBuilder.toString());
                }

                @Override
                int getOutputSizeForFinal(int n) {
                    if (this.isEncrypting()) {
                        return this.bufCount + n + this.tagLengthInBytes;
                    }
                    return Math.max(0, this.bufCount + n - this.tagLengthInBytes);
                }

                @Override
                protected AlgorithmParameterSpec getParameterSpec(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
                    if (algorithmParameters != null) {
                        AlgorithmParameterSpec algorithmParameterSpec = Platform.fromGCMParameters(algorithmParameters);
                        if (algorithmParameterSpec != null) {
                            return algorithmParameterSpec;
                        }
                        return super.getParameterSpec(algorithmParameters);
                    }
                    return null;
                }

                public static class AES_128
                extends GCM {
                    @Override
                    void checkSupportedKeySize(int n) throws InvalidKeyException {
                        if (n == 16) {
                            return;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported key size: ");
                        stringBuilder.append(n);
                        stringBuilder.append(" bytes (must be 16)");
                        throw new InvalidKeyException(stringBuilder.toString());
                    }
                }

                public static class AES_256
                extends GCM {
                    @Override
                    void checkSupportedKeySize(int n) throws InvalidKeyException {
                        if (n == 32) {
                            return;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unsupported key size: ");
                        stringBuilder.append(n);
                        stringBuilder.append(" bytes (must be 32)");
                        throw new InvalidKeyException(stringBuilder.toString());
                    }
                }

            }

        }

        public static class ChaCha20
        extends EVP_AEAD {
            public ChaCha20() {
                super(Mode.POLY1305);
            }

            @Override
            void checkSupportedKeySize(int n) throws InvalidKeyException {
                if (n == 32) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported key size: ");
                stringBuilder.append(n);
                stringBuilder.append(" bytes (must be 32)");
                throw new InvalidKeyException(stringBuilder.toString());
            }

            @Override
            void checkSupportedMode(Mode mode) throws NoSuchAlgorithmException {
                if (mode == Mode.POLY1305) {
                    return;
                }
                throw new NoSuchAlgorithmException("Mode must be Poly1305");
            }

            @Override
            String getBaseCipherName() {
                return "ChaCha20";
            }

            @Override
            int getCipherBlockSize() {
                return 0;
            }

            @Override
            long getEVP_AEAD(int n) throws InvalidKeyException {
                if (n == 32) {
                    return NativeCrypto.EVP_aead_chacha20_poly1305();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected key length: ");
                stringBuilder.append(n);
                throw new RuntimeException(stringBuilder.toString());
            }

            @Override
            int getOutputSizeForFinal(int n) {
                if (this.isEncrypting()) {
                    return this.bufCount + n + 16;
                }
                return Math.max(0, this.bufCount + n - 16);
            }
        }

    }

    public static abstract class EVP_CIPHER
    extends OpenSSLCipher {
        boolean calledUpdate;
        private final NativeRef.EVP_CIPHER_CTX cipherCtx = new NativeRef.EVP_CIPHER_CTX(NativeCrypto.EVP_CIPHER_CTX_new());
        private int modeBlockSize;

        public EVP_CIPHER(Mode mode, Padding padding) {
            super(mode, padding);
        }

        private void reset() {
            NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, this.encodedKey, this.iv, this.isEncrypting());
            this.calledUpdate = false;
        }

        @Override
        int doFinalInternal(byte[] object, int n, int n2) throws IllegalBlockSizeException, BadPaddingException, ShortBufferException {
            int n3;
            block7 : {
                block6 : {
                    block5 : {
                        if (!this.isEncrypting() && !this.calledUpdate) {
                            return 0;
                        }
                        n3 = ((byte[])object).length - n;
                        if (n3 < n2) break block5;
                        n2 = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, (byte[])object, n);
                        break block6;
                    }
                    byte[] arrby = new byte[n2];
                    n2 = NativeCrypto.EVP_CipherFinal_ex(this.cipherCtx, arrby, 0);
                    if (n2 > n3) break block7;
                    if (n2 > 0) {
                        System.arraycopy(arrby, 0, object, n, n2);
                    }
                }
                this.reset();
                return n + n2 - n;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("buffer is too short: ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" > ");
            ((StringBuilder)object).append(n3);
            throw new ShortBufferException(((StringBuilder)object).toString());
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        void engineInitInternal(byte[] object, AlgorithmParameterSpec object2, SecureRandom object3) throws InvalidKeyException, InvalidAlgorithmParameterException {
            object2 = object2 instanceof IvParameterSpec ? ((IvParameterSpec)object2).getIV() : null;
            long l = NativeCrypto.EVP_get_cipherbyname(this.getCipherName(((Object)object).length, this.mode));
            if (l == 0L) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Cannot find name for key length = ");
                ((StringBuilder)object2).append(((Object)object).length * 8);
                ((StringBuilder)object2).append(" and mode = ");
                ((StringBuilder)object2).append((Object)this.mode);
                throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
            }
            boolean bl = this.isEncrypting();
            int n = NativeCrypto.EVP_CIPHER_iv_length(l);
            if (object2 == null && n != 0) {
                if (!bl) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("IV must be specified in ");
                    ((StringBuilder)object).append((Object)this.mode);
                    ((StringBuilder)object).append(" mode");
                    throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                }
                object2 = new byte[n];
                if (object3 != null) {
                    ((SecureRandom)object3).nextBytes((byte[])object2);
                    object3 = object2;
                } else {
                    NativeCrypto.RAND_bytes((byte[])object2);
                    object3 = object2;
                }
            } else {
                if (n == 0 && object2 != null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("IV not used in ");
                    ((StringBuilder)object).append((Object)this.mode);
                    ((StringBuilder)object).append(" mode");
                    throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                }
                object3 = object2;
                if (object2 != null) {
                    if (((Object)object2).length != n) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("expected IV length of ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" but was ");
                        ((StringBuilder)object).append(((Object)object2).length);
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                    }
                    object3 = object2;
                }
            }
            this.iv = object3;
            if (this.supportsVariableSizeKey()) {
                NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, l, null, null, bl);
                NativeCrypto.EVP_CIPHER_CTX_set_key_length(this.cipherCtx, ((Object)object).length);
                NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, 0L, (byte[])object, (byte[])object3, this.isEncrypting());
            } else {
                NativeCrypto.EVP_CipherInit_ex(this.cipherCtx, l, (byte[])object, (byte[])object3, bl);
            }
            object = this.cipherCtx;
            bl = this.getPadding() == Padding.PKCS5PADDING;
            NativeCrypto.EVP_CIPHER_CTX_set_padding((NativeRef.EVP_CIPHER_CTX)object, bl);
            this.modeBlockSize = NativeCrypto.EVP_CIPHER_CTX_block_size(this.cipherCtx);
            this.calledUpdate = false;
        }

        abstract String getCipherName(int var1, Mode var2);

        @Override
        int getOutputSizeForFinal(int n) {
            if (this.modeBlockSize == 1) {
                return n;
            }
            int n2 = NativeCrypto.get_EVP_CIPHER_CTX_buf_len(this.cipherCtx);
            if (this.getPadding() == Padding.NOPADDING) {
                return n2 + n;
            }
            boolean bl = NativeCrypto.get_EVP_CIPHER_CTX_final_used(this.cipherCtx);
            int n3 = 0;
            int n4 = bl ? this.modeBlockSize : 0;
            n = (n4 = n + n2 + n4) % this.modeBlockSize == 0 && !this.isEncrypting() ? n3 : this.modeBlockSize;
            n = n4 + n;
            return n - n % this.modeBlockSize;
        }

        @Override
        int getOutputSizeForUpdate(int n) {
            return this.getOutputSizeForFinal(n);
        }

        @Override
        int updateInternal(byte[] object, int n, int n2, byte[] arrby, int n3, int n4) throws ShortBufferException {
            int n5 = arrby.length - n3;
            if (n5 >= n4) {
                n = NativeCrypto.EVP_CipherUpdate(this.cipherCtx, arrby, n3, (byte[])object, n, n2);
                this.calledUpdate = true;
                return n3 + n - n3;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("output buffer too small during update: ");
            ((StringBuilder)object).append(n5);
            ((StringBuilder)object).append(" < ");
            ((StringBuilder)object).append(n4);
            throw new ShortBufferException(((StringBuilder)object).toString());
        }

        public static class AES
        extends AES_BASE {
            AES(Mode mode, Padding padding) {
                super(mode, padding);
            }

            @Override
            void checkSupportedKeySize(int n) throws InvalidKeyException {
                if (n != 16 && n != 24 && n != 32) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported key size: ");
                    stringBuilder.append(n);
                    stringBuilder.append(" bytes");
                    throw new InvalidKeyException(stringBuilder.toString());
                }
            }

            public static class CBC
            extends AES {
                public CBC(Padding padding) {
                    super(Mode.CBC, padding);
                }

                public static class NoPadding
                extends CBC {
                    public NoPadding() {
                        super(Padding.NOPADDING);
                    }
                }

                public static class PKCS5Padding
                extends CBC {
                    public PKCS5Padding() {
                        super(Padding.PKCS5PADDING);
                    }
                }

            }

            public static class CTR
            extends AES {
                public CTR() {
                    super(Mode.CTR, Padding.NOPADDING);
                }
            }

            public static class ECB
            extends AES {
                public ECB(Padding padding) {
                    super(Mode.ECB, padding);
                }

                public static class NoPadding
                extends ECB {
                    public NoPadding() {
                        super(Padding.NOPADDING);
                    }
                }

                public static class PKCS5Padding
                extends ECB {
                    public PKCS5Padding() {
                        super(Padding.PKCS5PADDING);
                    }
                }

            }

        }

        public static class AES_128
        extends AES_BASE {
            AES_128(Mode mode, Padding padding) {
                super(mode, padding);
            }

            @Override
            void checkSupportedKeySize(int n) throws InvalidKeyException {
                if (n == 16) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported key size: ");
                stringBuilder.append(n);
                stringBuilder.append(" bytes");
                throw new InvalidKeyException(stringBuilder.toString());
            }

            public static class CBC
            extends AES_128 {
                public CBC(Padding padding) {
                    super(Mode.CBC, padding);
                }

                public static class NoPadding
                extends CBC {
                    public NoPadding() {
                        super(Padding.NOPADDING);
                    }
                }

                public static class PKCS5Padding
                extends CBC {
                    public PKCS5Padding() {
                        super(Padding.PKCS5PADDING);
                    }
                }

            }

            public static class CTR
            extends AES_128 {
                public CTR() {
                    super(Mode.CTR, Padding.NOPADDING);
                }
            }

            public static class ECB
            extends AES_128 {
                public ECB(Padding padding) {
                    super(Mode.ECB, padding);
                }

                public static class NoPadding
                extends ECB {
                    public NoPadding() {
                        super(Padding.NOPADDING);
                    }
                }

                public static class PKCS5Padding
                extends ECB {
                    public PKCS5Padding() {
                        super(Padding.PKCS5PADDING);
                    }
                }

            }

        }

        public static class AES_256
        extends AES_BASE {
            AES_256(Mode mode, Padding padding) {
                super(mode, padding);
            }

            @Override
            void checkSupportedKeySize(int n) throws InvalidKeyException {
                if (n == 32) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported key size: ");
                stringBuilder.append(n);
                stringBuilder.append(" bytes");
                throw new InvalidKeyException(stringBuilder.toString());
            }

            public static class CBC
            extends AES_256 {
                public CBC(Padding padding) {
                    super(Mode.CBC, padding);
                }

                public static class NoPadding
                extends CBC {
                    public NoPadding() {
                        super(Padding.NOPADDING);
                    }
                }

                public static class PKCS5Padding
                extends CBC {
                    public PKCS5Padding() {
                        super(Padding.PKCS5PADDING);
                    }
                }

            }

            public static class CTR
            extends AES_256 {
                public CTR() {
                    super(Mode.CTR, Padding.NOPADDING);
                }
            }

            public static class ECB
            extends AES_256 {
                public ECB(Padding padding) {
                    super(Mode.ECB, padding);
                }

                public static class NoPadding
                extends ECB {
                    public NoPadding() {
                        super(Padding.NOPADDING);
                    }
                }

                public static class PKCS5Padding
                extends ECB {
                    public PKCS5Padding() {
                        super(Padding.PKCS5PADDING);
                    }
                }

            }

        }

        static abstract class AES_BASE
        extends EVP_CIPHER {
            private static final int AES_BLOCK_SIZE = 16;

            AES_BASE(Mode mode, Padding padding) {
                super(mode, padding);
            }

            @Override
            void checkSupportedMode(Mode mode) throws NoSuchAlgorithmException {
                int n = 1.$SwitchMap$com$android$org$conscrypt$OpenSSLCipher$Mode[mode.ordinal()];
                if (n != 1 && n != 2 && n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported mode ");
                    stringBuilder.append(mode.toString());
                    throw new NoSuchAlgorithmException(stringBuilder.toString());
                }
            }

            @Override
            void checkSupportedPadding(Padding padding) throws NoSuchPaddingException {
                int n = 1.$SwitchMap$com$android$org$conscrypt$OpenSSLCipher$Padding[padding.ordinal()];
                if (n != 1 && n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported padding ");
                    stringBuilder.append(padding.toString());
                    throw new NoSuchPaddingException(stringBuilder.toString());
                }
            }

            @Override
            String getBaseCipherName() {
                return "AES";
            }

            @Override
            int getCipherBlockSize() {
                return 16;
            }

            @Override
            String getCipherName(int n, Mode mode) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("aes-");
                stringBuilder.append(n * 8);
                stringBuilder.append("-");
                stringBuilder.append(mode.toString().toLowerCase(Locale.US));
                return stringBuilder.toString();
            }
        }

        public static class ARC4
        extends EVP_CIPHER {
            public ARC4() {
                super(Mode.ECB, Padding.NOPADDING);
            }

            @Override
            void checkSupportedKeySize(int n) throws InvalidKeyException {
            }

            @Override
            void checkSupportedMode(Mode mode) throws NoSuchAlgorithmException {
                if (mode != Mode.NONE && mode != Mode.ECB) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported mode ");
                    stringBuilder.append(mode.toString());
                    throw new NoSuchAlgorithmException(stringBuilder.toString());
                }
            }

            @Override
            void checkSupportedPadding(Padding padding) throws NoSuchPaddingException {
                if (padding == Padding.NOPADDING) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported padding ");
                stringBuilder.append(padding.toString());
                throw new NoSuchPaddingException(stringBuilder.toString());
            }

            @Override
            String getBaseCipherName() {
                return "ARCFOUR";
            }

            @Override
            int getCipherBlockSize() {
                return 0;
            }

            @Override
            String getCipherName(int n, Mode mode) {
                return "rc4";
            }

            @Override
            boolean supportsVariableSizeKey() {
                return true;
            }
        }

        public static class DESEDE
        extends EVP_CIPHER {
            private static final int DES_BLOCK_SIZE = 8;

            public DESEDE(Mode mode, Padding padding) {
                super(mode, padding);
            }

            @Override
            void checkSupportedKeySize(int n) throws InvalidKeyException {
                if (n != 16 && n != 24) {
                    throw new InvalidKeyException("key size must be 128 or 192 bits");
                }
            }

            @Override
            void checkSupportedMode(Mode mode) throws NoSuchAlgorithmException {
                if (mode == Mode.CBC) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported mode ");
                stringBuilder.append(mode.toString());
                throw new NoSuchAlgorithmException(stringBuilder.toString());
            }

            @Override
            void checkSupportedPadding(Padding padding) throws NoSuchPaddingException {
                int n = 1.$SwitchMap$com$android$org$conscrypt$OpenSSLCipher$Padding[padding.ordinal()];
                if (n != 1 && n != 2) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unsupported padding ");
                    stringBuilder.append(padding.toString());
                    throw new NoSuchPaddingException(stringBuilder.toString());
                }
            }

            @Override
            String getBaseCipherName() {
                return "DESede";
            }

            @Override
            int getCipherBlockSize() {
                return 8;
            }

            @Override
            String getCipherName(int n, Mode mode) {
                String string = n == 16 ? "des-ede" : "des-ede3";
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append("-");
                stringBuilder.append(mode.toString().toLowerCase(Locale.US));
                return stringBuilder.toString();
            }

            public static class CBC
            extends DESEDE {
                public CBC(Padding padding) {
                    super(Mode.CBC, padding);
                }

                public static class NoPadding
                extends CBC {
                    public NoPadding() {
                        super(Padding.NOPADDING);
                    }
                }

                public static class PKCS5Padding
                extends CBC {
                    public PKCS5Padding() {
                        super(Padding.PKCS5PADDING);
                    }
                }

            }

        }

    }

    static enum Mode {
        NONE,
        CBC,
        CTR,
        ECB,
        GCM,
        POLY1305;
        
    }

    static enum Padding {
        NOPADDING,
        PKCS5PADDING,
        PKCS7PADDING;
        

        public static Padding getNormalized(String object) {
            if ((object = Padding.valueOf(object)) == PKCS7PADDING) {
                return PKCS5PADDING;
            }
            return object;
        }
    }

}

