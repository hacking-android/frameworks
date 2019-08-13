/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.util.EmptyArray
 */
package android.security.keystore;

import android.os.IBinder;
import android.security.KeyStore;
import android.security.KeyStoreException;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.OperationResult;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStorePrivateKey;
import android.security.keystore.AndroidKeyStorePublicKey;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyStoreConnectException;
import android.security.keystore.KeyStoreCryptoOperation;
import android.security.keystore.KeyStoreCryptoOperationChunkedStreamer;
import android.security.keystore.KeyStoreCryptoOperationStreamer;
import android.security.keystore.KeyStoreCryptoOperationUtils;
import java.io.Serializable;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import libcore.util.EmptyArray;

abstract class AndroidKeyStoreSignatureSpiBase
extends SignatureSpi
implements KeyStoreCryptoOperation {
    private Exception mCachedException;
    private AndroidKeyStoreKey mKey;
    private final KeyStore mKeyStore = KeyStore.getInstance();
    private KeyStoreCryptoOperationStreamer mMessageStreamer;
    private long mOperationHandle;
    private IBinder mOperationToken;
    private boolean mSigning;

    AndroidKeyStoreSignatureSpiBase() {
    }

    private void ensureKeystoreOperationInitialized() throws InvalidKeyException {
        if (this.mMessageStreamer != null) {
            return;
        }
        if (this.mCachedException != null) {
            return;
        }
        if (this.mKey != null) {
            KeymasterArguments keymasterArguments = new KeymasterArguments();
            this.addAlgorithmSpecificParametersToBegin(keymasterArguments);
            Object object = this.mKeyStore;
            Object object2 = this.mKey.getAlias();
            int n = this.mSigning ? 2 : 3;
            object = ((KeyStore)object).begin((String)object2, n, true, keymasterArguments, null, this.mKey.getUid());
            if (object != null) {
                this.mOperationToken = ((OperationResult)object).token;
                this.mOperationHandle = ((OperationResult)object).operationHandle;
                object2 = KeyStoreCryptoOperationUtils.getInvalidKeyExceptionForInit(this.mKeyStore, this.mKey, ((OperationResult)object).resultCode);
                if (object2 == null) {
                    if (this.mOperationToken != null) {
                        if (this.mOperationHandle != 0L) {
                            this.mMessageStreamer = this.createMainDataStreamer(this.mKeyStore, ((OperationResult)object).token);
                            return;
                        }
                        throw new ProviderException("Keystore returned invalid operation handle");
                    }
                    throw new ProviderException("Keystore returned null operation token");
                }
                throw object2;
            }
            throw new KeyStoreConnectException();
        }
        throw new IllegalStateException("Not initialized");
    }

    protected abstract void addAlgorithmSpecificParametersToBegin(KeymasterArguments var1);

    protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStore keyStore, IBinder iBinder) {
        return new KeyStoreCryptoOperationChunkedStreamer(new KeyStoreCryptoOperationChunkedStreamer.MainDataStream(keyStore, iBinder));
    }

    @Deprecated
    @Override
    protected final Object engineGetParameter(String string2) throws InvalidParameterException {
        throw new InvalidParameterException();
    }

    @Override
    protected final void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        this.engineInitSign(privateKey, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void engineInitSign(PrivateKey serializable, SecureRandom serializable2) throws InvalidKeyException {
        Throwable throwable2;
        this.resetAll();
        if (serializable != null) {
            try {
                if (serializable instanceof AndroidKeyStorePrivateKey) {
                    serializable = (AndroidKeyStoreKey)serializable;
                    this.mSigning = true;
                    this.initKey((AndroidKeyStoreKey)serializable);
                    this.appRandom = serializable2;
                    this.ensureKeystoreOperationInitialized();
                    if (!true) {
                        this.resetAll();
                    }
                    return;
                }
                serializable2 = new StringBuilder();
                ((StringBuilder)serializable2).append("Unsupported private key type: ");
                ((StringBuilder)serializable2).append(serializable);
                InvalidKeyException invalidKeyException = new InvalidKeyException(((StringBuilder)serializable2).toString());
                throw invalidKeyException;
            }
            catch (Throwable throwable2) {}
        } else {
            serializable = new InvalidKeyException("Unsupported key: null");
            throw serializable;
        }
        if (!false) {
            this.resetAll();
        }
        throw throwable2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    protected final void engineInitVerify(PublicKey serializable) throws InvalidKeyException {
        Throwable throwable2;
        this.resetAll();
        if (serializable != null) {
            try {
                if (serializable instanceof AndroidKeyStorePublicKey) {
                    serializable = (AndroidKeyStorePublicKey)serializable;
                    this.mSigning = false;
                    this.initKey((AndroidKeyStoreKey)serializable);
                    this.appRandom = null;
                    this.ensureKeystoreOperationInitialized();
                    if (!true) {
                        this.resetAll();
                    }
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported public key type: ");
                stringBuilder.append(serializable);
                InvalidKeyException invalidKeyException = new InvalidKeyException(stringBuilder.toString());
                throw invalidKeyException;
            }
            catch (Throwable throwable2) {}
        } else {
            serializable = new InvalidKeyException("Unsupported key: null");
            throw serializable;
        }
        if (!false) {
            this.resetAll();
        }
        throw throwable2;
    }

    @Deprecated
    @Override
    protected final void engineSetParameter(String string2, Object object) throws InvalidParameterException {
        throw new InvalidParameterException();
    }

    @Override
    protected final int engineSign(byte[] arrby, int n, int n2) throws SignatureException {
        return super.engineSign(arrby, n, n2);
    }

    @Override
    protected final byte[] engineSign() throws SignatureException {
        byte[] arrby = this.mCachedException;
        if (arrby == null) {
            try {
                this.ensureKeystoreOperationInitialized();
                arrby = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.appRandom, this.getAdditionalEntropyAmountForSign());
                arrby = this.mMessageStreamer.doFinal(EmptyArray.BYTE, 0, 0, null, arrby);
                this.resetWhilePreservingInitState();
                return arrby;
            }
            catch (KeyStoreException | InvalidKeyException exception) {
                throw new SignatureException(exception);
            }
        }
        throw new SignatureException((Throwable)arrby);
    }

    @Override
    protected final void engineUpdate(byte by) throws SignatureException {
        this.engineUpdate(new byte[]{by}, 0, 1);
    }

    @Override
    protected final void engineUpdate(ByteBuffer arrby) {
        int n;
        int n2 = arrby.remaining();
        if (arrby.hasArray()) {
            byte[] arrby2 = arrby.array();
            n = arrby.arrayOffset() + arrby.position();
            arrby.position(arrby.limit());
            arrby = arrby2;
        } else {
            byte[] arrby3 = new byte[n2];
            n = 0;
            arrby.get(arrby3);
            arrby = arrby3;
        }
        try {
            this.engineUpdate(arrby, n, n2);
        }
        catch (SignatureException signatureException) {
            this.mCachedException = signatureException;
        }
    }

    @Override
    protected final void engineUpdate(byte[] object, int n, int n2) throws SignatureException {
        byte[] arrby = this.mCachedException;
        if (arrby == null) {
            block6 : {
                try {
                    this.ensureKeystoreOperationInitialized();
                    if (n2 != 0) break block6;
                    return;
                }
                catch (InvalidKeyException invalidKeyException) {
                    throw new SignatureException(invalidKeyException);
                }
            }
            try {
                arrby = this.mMessageStreamer.update((byte[])object, n, n2);
            }
            catch (KeyStoreException keyStoreException) {
                throw new SignatureException(keyStoreException);
            }
            if (arrby.length == 0) {
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Update operation unexpectedly produced output: ");
            ((StringBuilder)object).append(arrby.length);
            ((StringBuilder)object).append(" bytes");
            throw new ProviderException(((StringBuilder)object).toString());
        }
        throw new SignatureException((Throwable)arrby);
    }

    @Override
    protected final boolean engineVerify(byte[] arrby) throws SignatureException {
        Serializable serializable = this.mCachedException;
        if (serializable == null) {
            KeyStoreException keyStoreException2;
            block7 : {
                boolean bl;
                block6 : {
                    try {
                        this.ensureKeystoreOperationInitialized();
                    }
                    catch (InvalidKeyException invalidKeyException) {
                        throw new SignatureException(invalidKeyException);
                    }
                    arrby = this.mMessageStreamer.doFinal(EmptyArray.BYTE, 0, 0, arrby, null);
                    if (arrby.length != 0) break block6;
                    bl = true;
                }
                try {
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Signature verification unexpected produced output: ");
                    ((StringBuilder)serializable).append(arrby.length);
                    ((StringBuilder)serializable).append(" bytes");
                    ProviderException providerException = new ProviderException(((StringBuilder)serializable).toString());
                    throw providerException;
                }
                catch (KeyStoreException keyStoreException2) {
                    if (keyStoreException2.getErrorCode() != -30) break block7;
                    bl = false;
                }
                this.resetWhilePreservingInitState();
                return bl;
            }
            throw new SignatureException(keyStoreException2);
        }
        throw new SignatureException((Throwable)serializable);
    }

    @Override
    protected final boolean engineVerify(byte[] arrby, int n, int n2) throws SignatureException {
        return this.engineVerify(ArrayUtils.subarray(arrby, n, n2));
    }

    protected abstract int getAdditionalEntropyAmountForSign();

    protected final KeyStore getKeyStore() {
        return this.mKeyStore;
    }

    @Override
    public final long getOperationHandle() {
        return this.mOperationHandle;
    }

    protected void initKey(AndroidKeyStoreKey androidKeyStoreKey) throws InvalidKeyException {
        this.mKey = androidKeyStoreKey;
    }

    protected final boolean isSigning() {
        return this.mSigning;
    }

    protected void resetAll() {
        IBinder iBinder = this.mOperationToken;
        if (iBinder != null) {
            this.mOperationToken = null;
            this.mKeyStore.abort(iBinder);
        }
        this.mSigning = false;
        this.mKey = null;
        this.appRandom = null;
        this.mOperationToken = null;
        this.mOperationHandle = 0L;
        this.mMessageStreamer = null;
        this.mCachedException = null;
    }

    protected void resetWhilePreservingInitState() {
        IBinder iBinder = this.mOperationToken;
        if (iBinder != null) {
            this.mOperationToken = null;
            this.mKeyStore.abort(iBinder);
        }
        this.mOperationHandle = 0L;
        this.mMessageStreamer = null;
        this.mCachedException = null;
    }
}

