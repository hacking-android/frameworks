/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.security.keystore;

import android.os.IBinder;
import android.os.Parcelable;
import android.security.KeyStore;
import android.security.KeyStoreException;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.OperationResult;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.KeyStoreConnectException;
import android.security.keystore.KeyStoreCryptoOperation;
import android.security.keystore.KeyStoreCryptoOperationChunkedStreamer;
import android.security.keystore.KeyStoreCryptoOperationStreamer;
import android.security.keystore.KeyStoreCryptoOperationUtils;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;
import libcore.util.EmptyArray;

abstract class AndroidKeyStoreCipherSpiBase
extends CipherSpi
implements KeyStoreCryptoOperation {
    private KeyStoreCryptoOperationStreamer mAdditionalAuthenticationDataStreamer;
    private boolean mAdditionalAuthenticationDataStreamerClosed;
    private Exception mCachedException;
    private boolean mEncrypting;
    private AndroidKeyStoreKey mKey;
    private final KeyStore mKeyStore = KeyStore.getInstance();
    private int mKeymasterPurposeOverride = -1;
    private KeyStoreCryptoOperationStreamer mMainDataStreamer;
    private long mOperationHandle;
    private IBinder mOperationToken;
    private SecureRandom mRng;

    AndroidKeyStoreCipherSpiBase() {
    }

    private void ensureKeystoreOperationInitialized() throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (this.mMainDataStreamer != null) {
            return;
        }
        if (this.mCachedException != null) {
            return;
        }
        if (this.mKey != null) {
            Parcelable parcelable = new KeymasterArguments();
            this.addAlgorithmSpecificParametersToBegin((KeymasterArguments)parcelable);
            Object object = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, this.getAdditionalEntropyAmountForBegin());
            int n = this.mKeymasterPurposeOverride != -1 ? this.mKeymasterPurposeOverride : (this.mEncrypting ? 0 : 1);
            parcelable = this.mKeyStore.begin(this.mKey.getAlias(), n, true, (KeymasterArguments)parcelable, (byte[])object, this.mKey.getUid());
            if (parcelable != null) {
                this.mOperationToken = ((OperationResult)parcelable).token;
                this.mOperationHandle = ((OperationResult)parcelable).operationHandle;
                object = KeyStoreCryptoOperationUtils.getExceptionForCipherInit(this.mKeyStore, this.mKey, ((OperationResult)parcelable).resultCode);
                if (object != null) {
                    if (!(object instanceof InvalidKeyException)) {
                        if (object instanceof InvalidAlgorithmParameterException) {
                            throw (InvalidAlgorithmParameterException)object;
                        }
                        throw new ProviderException("Unexpected exception type", (Throwable)object);
                    }
                    throw (InvalidKeyException)object;
                }
                if (this.mOperationToken != null) {
                    if (this.mOperationHandle != 0L) {
                        this.loadAlgorithmSpecificParametersFromBeginResult(((OperationResult)parcelable).outParams);
                        this.mMainDataStreamer = this.createMainDataStreamer(this.mKeyStore, ((OperationResult)parcelable).token);
                        this.mAdditionalAuthenticationDataStreamer = this.createAdditionalAuthenticationDataStreamer(this.mKeyStore, ((OperationResult)parcelable).token);
                        this.mAdditionalAuthenticationDataStreamerClosed = false;
                        return;
                    }
                    throw new ProviderException("Keystore returned invalid operation handle");
                }
                throw new ProviderException("Keystore returned null operation token");
            }
            throw new KeyStoreConnectException();
        }
        throw new IllegalStateException("Not initialized");
    }

    private void flushAAD() throws KeyStoreException {
        block5 : {
            byte[] arrby = this.mAdditionalAuthenticationDataStreamer;
            if (arrby != null && !this.mAdditionalAuthenticationDataStreamerClosed) {
                arrby = arrby.doFinal(EmptyArray.BYTE, 0, 0, null, null);
                if (arrby != null && arrby.length > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("AAD update unexpectedly returned data: ");
                    stringBuilder.append(arrby.length);
                    stringBuilder.append(" bytes");
                    throw new ProviderException(stringBuilder.toString());
                }
                break block5;
                finally {
                    this.mAdditionalAuthenticationDataStreamerClosed = true;
                }
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void init(int var1_1, Key var2_2, SecureRandom var3_3) throws InvalidKeyException {
        block4 : {
            block2 : {
                block3 : {
                    if (var1_1 == 1) break block2;
                    if (var1_1 == 2) break block3;
                    if (var1_1 != 3) {
                        if (var1_1 != 4) {
                            var2_2 = new StringBuilder();
                            var2_2.append("Unsupported opmode: ");
                            var2_2.append(var1_1);
                            throw new InvalidParameterException(var2_2.toString());
                        } else {
                            ** GOTO lbl12
                        }
                    }
                    break block2;
                }
                this.mEncrypting = false;
                break block4;
            }
            this.mEncrypting = true;
        }
        this.initKey(var1_1, (Key)var2_2);
        if (this.mKey == null) throw new ProviderException("initKey did not initialize the key");
        this.mRng = var3_3;
    }

    static String opmodeToString(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        return String.valueOf(n);
                    }
                    return "UNWRAP_MODE";
                }
                return "WRAP_MODE";
            }
            return "DECRYPT_MODE";
        }
        return "ENCRYPT_MODE";
    }

    protected abstract void addAlgorithmSpecificParametersToBegin(KeymasterArguments var1);

    protected KeyStoreCryptoOperationStreamer createAdditionalAuthenticationDataStreamer(KeyStore keyStore, IBinder iBinder) {
        return null;
    }

    protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStore keyStore, IBinder iBinder) {
        return new KeyStoreCryptoOperationChunkedStreamer(new KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStore, iBinder));
    }

    @Override
    protected final int engineDoFinal(ByteBuffer arrby, ByteBuffer byteBuffer) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        if (arrby != null) {
            if (byteBuffer != null) {
                int n = arrby.remaining();
                boolean bl = arrby.hasArray();
                int n2 = 0;
                if (bl) {
                    byte[] arrby2 = this.engineDoFinal(arrby.array(), arrby.arrayOffset() + arrby.position(), n);
                    arrby.position(arrby.position() + n);
                    arrby = arrby2;
                } else {
                    byte[] arrby3 = new byte[n];
                    arrby.get(arrby3);
                    arrby = this.engineDoFinal(arrby3, 0, n);
                }
                if (arrby != null) {
                    n2 = arrby.length;
                }
                if (n2 > 0) {
                    n = byteBuffer.remaining();
                    try {
                        byteBuffer.put(arrby);
                    }
                    catch (BufferOverflowException bufferOverflowException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Output buffer too small. Produced: ");
                        stringBuilder.append(n2);
                        stringBuilder.append(", available: ");
                        stringBuilder.append(n);
                        throw new ShortBufferException(stringBuilder.toString());
                    }
                }
                return n2;
            }
            throw new NullPointerException("output == null");
        }
        throw new NullPointerException("input == null");
    }

    @Override
    protected final int engineDoFinal(byte[] arrby, int n, int n2, byte[] object, int n3) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
        if ((arrby = this.engineDoFinal(arrby, n, n2)) == null) {
            return 0;
        }
        n = ((byte[])object).length - n3;
        if (arrby.length <= n) {
            System.arraycopy(arrby, 0, object, n3, arrby.length);
            return arrby.length;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Output buffer too short. Produced: ");
        ((StringBuilder)object).append(arrby.length);
        ((StringBuilder)object).append(", available: ");
        ((StringBuilder)object).append(n);
        throw new ShortBufferException(((StringBuilder)object).toString());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected final byte[] engineDoFinal(byte[] arrby, int n, int n2) throws IllegalBlockSizeException, BadPaddingException {
        if (this.mCachedException != null) throw (IllegalBlockSizeException)new IllegalBlockSizeException().initCause(this.mCachedException);
        this.ensureKeystoreOperationInitialized();
        try {
            this.flushAAD();
            byte[] arrby2 = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, this.getAdditionalEntropyAmountForFinish());
            arrby = this.mMainDataStreamer.doFinal(arrby, n, n2, null, arrby2);
            this.resetWhilePreservingInitState();
            return arrby;
        }
        catch (KeyStoreException keyStoreException) {
            n = keyStoreException.getErrorCode();
            if (n == -38) throw (BadPaddingException)new BadPaddingException().initCause(keyStoreException);
            if (n == -30) throw (AEADBadTagException)new AEADBadTagException().initCause(keyStoreException);
            if (n == -21) throw (IllegalBlockSizeException)new IllegalBlockSizeException().initCause(keyStoreException);
            throw (IllegalBlockSizeException)new IllegalBlockSizeException().initCause(keyStoreException);
        }
        catch (InvalidAlgorithmParameterException | InvalidKeyException generalSecurityException) {
            throw (IllegalBlockSizeException)new IllegalBlockSizeException().initCause(generalSecurityException);
        }
    }

    @Override
    protected final int engineGetKeySize(Key key) throws InvalidKeyException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected abstract AlgorithmParameters engineGetParameters();

    @Override
    protected final void engineInit(int n, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.resetAll();
        try {
            this.init(n, key, secureRandom);
            this.initAlgorithmSpecificParameters(algorithmParameters);
            this.ensureKeystoreOperationInitialized();
            return;
        }
        finally {
            if (!true) {
                this.resetAll();
            }
        }
    }

    @Override
    protected final void engineInit(int n, Key key, SecureRandom serializable) throws InvalidKeyException {
        this.resetAll();
        try {
            this.init(n, key, (SecureRandom)serializable);
            this.initAlgorithmSpecificParameters();
            try {
                this.ensureKeystoreOperationInitialized();
                return;
            }
            catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
                serializable = new InvalidKeyException(invalidAlgorithmParameterException);
                throw serializable;
            }
        }
        finally {
            if (!true) {
                this.resetAll();
            }
        }
    }

    @Override
    protected final void engineInit(int n, Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.resetAll();
        try {
            this.init(n, key, secureRandom);
            this.initAlgorithmSpecificParameters(algorithmParameterSpec);
            this.ensureKeystoreOperationInitialized();
            return;
        }
        finally {
            if (!true) {
                this.resetAll();
            }
        }
    }

    @Override
    protected final void engineSetMode(String string2) throws NoSuchAlgorithmException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected final void engineSetPadding(String string2) throws NoSuchPaddingException {
        throw new UnsupportedOperationException();
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected final Key engineUnwrap(byte[] object, String object2, int n) throws InvalidKeyException, NoSuchAlgorithmException {
        block7 : {
            block8 : {
                if (this.mKey == null) throw new IllegalStateException("Not initilized");
                if (this.isEncrypting()) throw new IllegalStateException("Cipher must be initialized in Cipher.WRAP_MODE to wrap keys");
                if (object == null) throw new NullPointerException("wrappedKey == null");
                object = this.engineDoFinal((byte[])object, 0, ((Object)object).length);
                if (n == 1) break block7;
                if (n == 2) break block8;
                if (n == 3) {
                    return new SecretKeySpec((byte[])object, (String)object2);
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported wrappedKeyType: ");
                ((StringBuilder)object).append(n);
                throw new InvalidParameterException(((StringBuilder)object).toString());
            }
            KeyFactory keyFactory = KeyFactory.getInstance((String)object2);
            try {
                object2 = new PKCS8EncodedKeySpec((byte[])object);
                return keyFactory.generatePrivate((KeySpec)object2);
            }
            catch (InvalidKeySpecException invalidKeySpecException) {
                throw new InvalidKeyException("Failed to create private key from its PKCS#8 encoded form", invalidKeySpecException);
            }
        }
        object2 = KeyFactory.getInstance((String)object2);
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec((byte[])object);
            return ((KeyFactory)object2).generatePublic(x509EncodedKeySpec);
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            throw new InvalidKeyException("Failed to create public key from its X.509 encoded form", invalidKeySpecException);
        }
        catch (BadPaddingException | IllegalBlockSizeException generalSecurityException) {
            throw new InvalidKeyException("Failed to unwrap key", generalSecurityException);
        }
    }

    @Override
    protected final int engineUpdate(ByteBuffer arrby, ByteBuffer byteBuffer) throws ShortBufferException {
        if (arrby != null) {
            if (byteBuffer != null) {
                int n = arrby.remaining();
                boolean bl = arrby.hasArray();
                int n2 = 0;
                if (bl) {
                    byte[] arrby2 = this.engineUpdate(arrby.array(), arrby.arrayOffset() + arrby.position(), n);
                    arrby.position(arrby.position() + n);
                    arrby = arrby2;
                } else {
                    byte[] arrby3 = new byte[n];
                    arrby.get(arrby3);
                    arrby = this.engineUpdate(arrby3, 0, n);
                }
                if (arrby != null) {
                    n2 = arrby.length;
                }
                if (n2 > 0) {
                    n = byteBuffer.remaining();
                    try {
                        byteBuffer.put(arrby);
                    }
                    catch (BufferOverflowException bufferOverflowException) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Output buffer too small. Produced: ");
                        stringBuilder.append(n2);
                        stringBuilder.append(", available: ");
                        stringBuilder.append(n);
                        throw new ShortBufferException(stringBuilder.toString());
                    }
                }
                return n2;
            }
            throw new NullPointerException("output == null");
        }
        throw new NullPointerException("input == null");
    }

    @Override
    protected final int engineUpdate(byte[] arrby, int n, int n2, byte[] object, int n3) throws ShortBufferException {
        if ((arrby = this.engineUpdate(arrby, n, n2)) == null) {
            return 0;
        }
        n = ((byte[])object).length - n3;
        if (arrby.length <= n) {
            System.arraycopy(arrby, 0, object, n3, arrby.length);
            return arrby.length;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Output buffer too short. Produced: ");
        ((StringBuilder)object).append(arrby.length);
        ((StringBuilder)object).append(", available: ");
        ((StringBuilder)object).append(n);
        throw new ShortBufferException(((StringBuilder)object).toString());
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected final byte[] engineUpdate(byte[] arrby, int n, int n2) {
        block5 : {
            if (this.mCachedException != null) {
                return null;
            }
            this.ensureKeystoreOperationInitialized();
            if (n2 != 0) break block5;
            return null;
        }
        try {
            this.flushAAD();
            arrby = this.mMainDataStreamer.update(arrby, n, n2);
        }
        catch (KeyStoreException keyStoreException) {
            this.mCachedException = keyStoreException;
            return null;
        }
        if (arrby.length != 0) return arrby;
        return null;
        catch (InvalidAlgorithmParameterException | InvalidKeyException generalSecurityException) {
            this.mCachedException = generalSecurityException;
            return null;
        }
    }

    @Override
    protected final void engineUpdateAAD(ByteBuffer arrby) {
        if (arrby != null) {
            int n;
            int n2;
            if (!arrby.hasRemaining()) {
                return;
            }
            if (arrby.hasArray()) {
                byte[] arrby2 = arrby.array();
                n2 = arrby.arrayOffset() + arrby.position();
                n = arrby.remaining();
                arrby.position(arrby.limit());
                arrby = arrby2;
            } else {
                byte[] arrby3 = new byte[arrby.remaining()];
                n2 = 0;
                n = arrby3.length;
                arrby.get(arrby3);
                arrby = arrby3;
            }
            this.engineUpdateAAD(arrby, n2, n);
            return;
        }
        throw new IllegalArgumentException("src == null");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected final void engineUpdateAAD(byte[] object, int n, int n2) {
        if (this.mCachedException != null) {
            return;
        }
        this.ensureKeystoreOperationInitialized();
        if (this.mAdditionalAuthenticationDataStreamerClosed) throw new IllegalStateException("AAD can only be provided before Cipher.update is invoked");
        byte[] arrby = this.mAdditionalAuthenticationDataStreamer;
        if (arrby == null) throw new IllegalStateException("This cipher does not support AAD");
        try {
            arrby = arrby.update((byte[])object, n, n2);
            if (arrby == null) return;
        }
        catch (KeyStoreException keyStoreException) {
            this.mCachedException = keyStoreException;
            return;
        }
        if (arrby.length <= 0) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("AAD update unexpectedly produced output: ");
        ((StringBuilder)object).append(arrby.length);
        ((StringBuilder)object).append(" bytes");
        throw new ProviderException(((StringBuilder)object).toString());
        catch (InvalidAlgorithmParameterException | InvalidKeyException generalSecurityException) {
            this.mCachedException = generalSecurityException;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final byte[] engineWrap(Key arrby) throws IllegalBlockSizeException, InvalidKeyException {
        if (this.mKey == null) {
            throw new IllegalStateException("Not initilized");
        }
        if (!this.isEncrypting()) {
            throw new IllegalStateException("Cipher must be initialized in Cipher.WRAP_MODE to wrap keys");
        }
        if (arrby == null) {
            throw new NullPointerException("key == null");
        }
        Object object = null;
        Object var3_7 = null;
        byte[] arrby2 = null;
        if (arrby instanceof SecretKey) {
            if ("RAW".equalsIgnoreCase(arrby.getFormat())) {
                arrby2 = arrby.getEncoded();
            }
            object = arrby2;
            if (arrby2 == null) {
                try {
                    object = ((SecretKeySpec)SecretKeyFactory.getInstance(arrby.getAlgorithm()).getKeySpec((SecretKey)arrby, SecretKeySpec.class)).getEncoded();
                }
                catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
                    throw new InvalidKeyException("Failed to wrap key because it does not export its key material", generalSecurityException);
                }
            }
        } else if (arrby instanceof PrivateKey) {
            arrby2 = object;
            if ("PKCS8".equalsIgnoreCase(arrby.getFormat())) {
                arrby2 = arrby.getEncoded();
            }
            object = arrby2;
            if (arrby2 == null) {
                try {
                    object = KeyFactory.getInstance(arrby.getAlgorithm()).getKeySpec((Key)arrby, PKCS8EncodedKeySpec.class).getEncoded();
                }
                catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
                    throw new InvalidKeyException("Failed to wrap key because it does not export its key material", generalSecurityException);
                }
            }
        } else {
            if (!(arrby instanceof PublicKey)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported key type: ");
                ((StringBuilder)object).append(arrby.getClass().getName());
                throw new InvalidKeyException(((StringBuilder)object).toString());
            }
            arrby2 = var3_7;
            if ("X.509".equalsIgnoreCase(arrby.getFormat())) {
                arrby2 = arrby.getEncoded();
            }
            object = arrby2;
            if (arrby2 == null) {
                try {
                    object = KeyFactory.getInstance(arrby.getAlgorithm()).getKeySpec((Key)arrby, X509EncodedKeySpec.class).getEncoded();
                }
                catch (NoSuchAlgorithmException | InvalidKeySpecException generalSecurityException) {
                    throw new InvalidKeyException("Failed to wrap key because it does not export its key material", generalSecurityException);
                }
            }
        }
        if (object == null) {
            throw new InvalidKeyException("Failed to wrap key because it does not export its key material");
        }
        try {
            return this.engineDoFinal((byte[])object, 0, ((Object)object).length);
        }
        catch (BadPaddingException badPaddingException) {
            throw (IllegalBlockSizeException)new IllegalBlockSizeException().initCause(badPaddingException);
        }
    }

    public void finalize() throws Throwable {
        block4 : {
            IBinder iBinder = this.mOperationToken;
            if (iBinder == null) break block4;
            this.mKeyStore.abort(iBinder);
        }
        return;
        finally {
            Object.super.finalize();
        }
    }

    protected abstract int getAdditionalEntropyAmountForBegin();

    protected abstract int getAdditionalEntropyAmountForFinish();

    protected final long getConsumedInputSizeBytes() {
        KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer = this.mMainDataStreamer;
        if (keyStoreCryptoOperationStreamer != null) {
            return keyStoreCryptoOperationStreamer.getConsumedInputSizeBytes();
        }
        throw new IllegalStateException("Not initialized");
    }

    protected final KeyStore getKeyStore() {
        return this.mKeyStore;
    }

    protected final int getKeymasterPurposeOverride() {
        return this.mKeymasterPurposeOverride;
    }

    @Override
    public final long getOperationHandle() {
        return this.mOperationHandle;
    }

    protected final long getProducedOutputSizeBytes() {
        KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer = this.mMainDataStreamer;
        if (keyStoreCryptoOperationStreamer != null) {
            return keyStoreCryptoOperationStreamer.getProducedOutputSizeBytes();
        }
        throw new IllegalStateException("Not initialized");
    }

    protected abstract void initAlgorithmSpecificParameters() throws InvalidKeyException;

    protected abstract void initAlgorithmSpecificParameters(AlgorithmParameters var1) throws InvalidAlgorithmParameterException;

    protected abstract void initAlgorithmSpecificParameters(AlgorithmParameterSpec var1) throws InvalidAlgorithmParameterException;

    protected abstract void initKey(int var1, Key var2) throws InvalidKeyException;

    protected final boolean isEncrypting() {
        return this.mEncrypting;
    }

    protected abstract void loadAlgorithmSpecificParametersFromBeginResult(KeymasterArguments var1);

    protected void resetAll() {
        IBinder iBinder = this.mOperationToken;
        if (iBinder != null) {
            this.mKeyStore.abort(iBinder);
        }
        this.mEncrypting = false;
        this.mKeymasterPurposeOverride = -1;
        this.mKey = null;
        this.mRng = null;
        this.mOperationToken = null;
        this.mOperationHandle = 0L;
        this.mMainDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamerClosed = false;
        this.mCachedException = null;
    }

    protected void resetWhilePreservingInitState() {
        IBinder iBinder = this.mOperationToken;
        if (iBinder != null) {
            this.mKeyStore.abort(iBinder);
        }
        this.mOperationToken = null;
        this.mOperationHandle = 0L;
        this.mMainDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamer = null;
        this.mAdditionalAuthenticationDataStreamerClosed = false;
        this.mCachedException = null;
    }

    protected final void setKey(AndroidKeyStoreKey androidKeyStoreKey) {
        this.mKey = androidKeyStoreKey;
    }

    protected final void setKeymasterPurposeOverride(int n) {
        this.mKeymasterPurposeOverride = n;
    }
}

