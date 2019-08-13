/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.os.IBinder;
import android.security.KeyStore;
import android.security.KeyStoreException;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.OperationResult;
import android.security.keystore.AndroidKeyStoreSecretKey;
import android.security.keystore.KeyStoreConnectException;
import android.security.keystore.KeyStoreCryptoOperation;
import android.security.keystore.KeyStoreCryptoOperationChunkedStreamer;
import android.security.keystore.KeyStoreCryptoOperationUtils;
import android.security.keystore.KeymasterUtils;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.MacSpi;

public abstract class AndroidKeyStoreHmacSpi
extends MacSpi
implements KeyStoreCryptoOperation {
    private KeyStoreCryptoOperationChunkedStreamer mChunkedStreamer;
    private AndroidKeyStoreSecretKey mKey;
    private final KeyStore mKeyStore = KeyStore.getInstance();
    private final int mKeymasterDigest;
    private final int mMacSizeBits;
    private long mOperationHandle;
    private IBinder mOperationToken;

    protected AndroidKeyStoreHmacSpi(int n) {
        this.mKeymasterDigest = n;
        this.mMacSizeBits = KeymasterUtils.getDigestOutputSizeBits(n);
    }

    private void ensureKeystoreOperationInitialized() throws InvalidKeyException {
        if (this.mChunkedStreamer != null) {
            return;
        }
        if (this.mKey != null) {
            Object object = new KeymasterArguments();
            ((KeymasterArguments)object).addEnum(268435458, 128);
            ((KeymasterArguments)object).addEnum(536870917, this.mKeymasterDigest);
            ((KeymasterArguments)object).addUnsignedInt(805307371, this.mMacSizeBits);
            object = this.mKeyStore.begin(this.mKey.getAlias(), 2, true, (KeymasterArguments)object, null, this.mKey.getUid());
            if (object != null) {
                this.mOperationToken = ((OperationResult)object).token;
                this.mOperationHandle = ((OperationResult)object).operationHandle;
                object = KeyStoreCryptoOperationUtils.getInvalidKeyExceptionForInit(this.mKeyStore, this.mKey, ((OperationResult)object).resultCode);
                if (object == null) {
                    object = this.mOperationToken;
                    if (object != null) {
                        if (this.mOperationHandle != 0L) {
                            this.mChunkedStreamer = new KeyStoreCryptoOperationChunkedStreamer(new KeyStoreCryptoOperationChunkedStreamer.MainDataStream(this.mKeyStore, (IBinder)object));
                            return;
                        }
                        throw new ProviderException("Keystore returned invalid operation handle");
                    }
                    throw new ProviderException("Keystore returned null operation token");
                }
                throw object;
            }
            throw new KeyStoreConnectException();
        }
        throw new IllegalStateException("Not initialized");
    }

    private void init(Key serializable, AlgorithmParameterSpec object) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (serializable != null) {
            if (serializable instanceof AndroidKeyStoreSecretKey) {
                this.mKey = (AndroidKeyStoreSecretKey)serializable;
                if (object == null) {
                    return;
                }
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Unsupported algorithm parameters: ");
                ((StringBuilder)serializable).append(object);
                throw new InvalidAlgorithmParameterException(((StringBuilder)serializable).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Only Android KeyStore secret keys supported. Key: ");
            ((StringBuilder)object).append(serializable);
            throw new InvalidKeyException(((StringBuilder)object).toString());
        }
        throw new InvalidKeyException("key == null");
    }

    private void resetAll() {
        this.mKey = null;
        IBinder iBinder = this.mOperationToken;
        if (iBinder != null) {
            this.mKeyStore.abort(iBinder);
        }
        this.mOperationToken = null;
        this.mOperationHandle = 0L;
        this.mChunkedStreamer = null;
    }

    private void resetWhilePreservingInitState() {
        IBinder iBinder = this.mOperationToken;
        if (iBinder != null) {
            this.mKeyStore.abort(iBinder);
        }
        this.mOperationToken = null;
        this.mOperationHandle = 0L;
        this.mChunkedStreamer = null;
    }

    @Override
    protected byte[] engineDoFinal() {
        try {
            this.ensureKeystoreOperationInitialized();
        }
        catch (InvalidKeyException invalidKeyException) {
            throw new ProviderException("Failed to reinitialize MAC", invalidKeyException);
        }
        try {
            byte[] arrby = this.mChunkedStreamer.doFinal(null, 0, 0, null, null);
            this.resetWhilePreservingInitState();
            return arrby;
        }
        catch (KeyStoreException keyStoreException) {
            throw new ProviderException("Keystore operation failed", keyStoreException);
        }
    }

    @Override
    protected int engineGetMacLength() {
        return (this.mMacSizeBits + 7) / 8;
    }

    @Override
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        this.resetAll();
        try {
            this.init(key, algorithmParameterSpec);
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
    protected void engineReset() {
        this.resetWhilePreservingInitState();
    }

    @Override
    protected void engineUpdate(byte by) {
        this.engineUpdate(new byte[]{by}, 0, 1);
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) {
        try {
            this.ensureKeystoreOperationInitialized();
        }
        catch (InvalidKeyException invalidKeyException) {
            throw new ProviderException("Failed to reinitialize MAC", invalidKeyException);
        }
        try {
            arrby = this.mChunkedStreamer.update(arrby, n, n2);
        }
        catch (KeyStoreException keyStoreException) {
            throw new ProviderException("Keystore operation failed", keyStoreException);
        }
        if (arrby != null && arrby.length != 0) {
            throw new ProviderException("Update operation unexpectedly produced output");
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

    @Override
    public long getOperationHandle() {
        return this.mOperationHandle;
    }

    public static class HmacSHA1
    extends AndroidKeyStoreHmacSpi {
        public HmacSHA1() {
            super(2);
        }
    }

    public static class HmacSHA224
    extends AndroidKeyStoreHmacSpi {
        public HmacSHA224() {
            super(3);
        }
    }

    public static class HmacSHA256
    extends AndroidKeyStoreHmacSpi {
        public HmacSHA256() {
            super(4);
        }
    }

    public static class HmacSHA384
    extends AndroidKeyStoreHmacSpi {
        public HmacSHA384() {
            super(5);
        }
    }

    public static class HmacSHA512
    extends AndroidKeyStoreHmacSpi {
        public HmacSHA512() {
            super(6);
        }
    }

}

