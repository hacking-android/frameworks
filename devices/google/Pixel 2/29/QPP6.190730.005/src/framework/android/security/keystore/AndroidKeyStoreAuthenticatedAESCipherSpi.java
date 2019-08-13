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
import android.security.keystore.AndroidKeyStoreCipherSpiBase;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStoreSecretKey;
import android.security.keystore.ArrayUtils;
import android.security.keystore.KeyStoreCryptoOperationChunkedStreamer;
import android.security.keystore.KeyStoreCryptoOperationStreamer;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import javax.crypto.spec.GCMParameterSpec;
import libcore.util.EmptyArray;

abstract class AndroidKeyStoreAuthenticatedAESCipherSpi
extends AndroidKeyStoreCipherSpiBase {
    private static final int BLOCK_SIZE_BYTES = 16;
    private byte[] mIv;
    private boolean mIvHasBeenUsed;
    private final int mKeymasterBlockMode;
    private final int mKeymasterPadding;

    AndroidKeyStoreAuthenticatedAESCipherSpi(int n, int n2) {
        this.mKeymasterBlockMode = n;
        this.mKeymasterPadding = n2;
    }

    @Override
    protected void addAlgorithmSpecificParametersToBegin(KeymasterArguments keymasterArguments) {
        if (this.isEncrypting() && this.mIvHasBeenUsed) {
            throw new IllegalStateException("IV has already been used. Reusing IV in encryption mode violates security best practices.");
        }
        keymasterArguments.addEnum(268435458, 32);
        keymasterArguments.addEnum(536870916, this.mKeymasterBlockMode);
        keymasterArguments.addEnum(536870918, this.mKeymasterPadding);
        byte[] arrby = this.mIv;
        if (arrby != null) {
            keymasterArguments.addBytes(-1879047191, arrby);
        }
    }

    @Override
    protected final int engineGetBlockSize() {
        return 16;
    }

    @Override
    protected final byte[] engineGetIV() {
        return ArrayUtils.cloneIfNotEmpty(this.mIv);
    }

    protected byte[] getIv() {
        return this.mIv;
    }

    @Override
    protected final void initKey(int n, Key object) throws InvalidKeyException {
        if (!(object instanceof AndroidKeyStoreSecretKey)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported key: ");
            object = object != null ? object.getClass().getName() : "null";
            stringBuilder.append((String)object);
            throw new InvalidKeyException(stringBuilder.toString());
        }
        if ("AES".equalsIgnoreCase(object.getAlgorithm())) {
            this.setKey((AndroidKeyStoreSecretKey)object);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported key algorithm: ");
        stringBuilder.append(object.getAlgorithm());
        stringBuilder.append(". Only ");
        stringBuilder.append("AES");
        stringBuilder.append(" supported");
        throw new InvalidKeyException(stringBuilder.toString());
    }

    @Override
    protected final void loadAlgorithmSpecificParametersFromBeginResult(KeymasterArguments arrby) {
        byte[] arrby2;
        this.mIvHasBeenUsed = true;
        arrby = arrby2 = arrby.getBytes(-1879047191, null);
        if (arrby2 != null) {
            arrby = arrby2;
            if (arrby2.length == 0) {
                arrby = null;
            }
        }
        if ((arrby2 = this.mIv) == null) {
            this.mIv = arrby;
        } else if (arrby != null && !Arrays.equals(arrby, arrby2)) {
            throw new ProviderException("IV in use differs from provided IV");
        }
    }

    @Override
    protected void resetAll() {
        this.mIv = null;
        this.mIvHasBeenUsed = false;
        super.resetAll();
    }

    protected void setIv(byte[] arrby) {
        this.mIv = arrby;
    }

    private static class AdditionalAuthenticationDataStream
    implements KeyStoreCryptoOperationChunkedStreamer.Stream {
        private final KeyStore mKeyStore;
        private final IBinder mOperationToken;

        private AdditionalAuthenticationDataStream(KeyStore keyStore, IBinder iBinder) {
            this.mKeyStore = keyStore;
            this.mOperationToken = iBinder;
        }

        @Override
        public OperationResult finish(byte[] arrby, byte[] arrby2) {
            if (arrby2 != null && arrby2.length > 0) {
                throw new ProviderException("AAD stream does not support additional entropy");
            }
            return new OperationResult(1, this.mOperationToken, 0L, 0, EmptyArray.BYTE, new KeymasterArguments());
        }

        @Override
        public OperationResult update(byte[] arrby) {
            Parcelable parcelable = new KeymasterArguments();
            parcelable.addBytes(-1879047192, arrby);
            OperationResult operationResult = this.mKeyStore.update(this.mOperationToken, (KeymasterArguments)parcelable, null);
            parcelable = operationResult;
            if (operationResult.resultCode == 1) {
                parcelable = new OperationResult(operationResult.resultCode, operationResult.token, operationResult.operationHandle, arrby.length, operationResult.output, operationResult.outParams);
            }
            return parcelable;
        }
    }

    private static class BufferAllOutputUntilDoFinalStreamer
    implements KeyStoreCryptoOperationStreamer {
        private ByteArrayOutputStream mBufferedOutput = new ByteArrayOutputStream();
        private final KeyStoreCryptoOperationStreamer mDelegate;
        private long mProducedOutputSizeBytes;

        private BufferAllOutputUntilDoFinalStreamer(KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer) {
            this.mDelegate = keyStoreCryptoOperationStreamer;
        }

        @Override
        public byte[] doFinal(byte[] arrby, int n, int n2, byte[] arrby2, byte[] arrby3) throws KeyStoreException {
            if ((arrby = this.mDelegate.doFinal(arrby, n, n2, arrby2, arrby3)) != null) {
                try {
                    this.mBufferedOutput.write(arrby);
                }
                catch (IOException iOException) {
                    throw new ProviderException("Failed to buffer output", iOException);
                }
            }
            arrby = this.mBufferedOutput.toByteArray();
            this.mBufferedOutput.reset();
            this.mProducedOutputSizeBytes += (long)arrby.length;
            return arrby;
        }

        @Override
        public long getConsumedInputSizeBytes() {
            return this.mDelegate.getConsumedInputSizeBytes();
        }

        @Override
        public long getProducedOutputSizeBytes() {
            return this.mProducedOutputSizeBytes;
        }

        @Override
        public byte[] update(byte[] arrby, int n, int n2) throws KeyStoreException {
            if ((arrby = this.mDelegate.update(arrby, n, n2)) != null) {
                try {
                    this.mBufferedOutput.write(arrby);
                }
                catch (IOException iOException) {
                    throw new ProviderException("Failed to buffer output", iOException);
                }
            }
            return EmptyArray.BYTE;
        }
    }

    static abstract class GCM
    extends AndroidKeyStoreAuthenticatedAESCipherSpi {
        private static final int DEFAULT_TAG_LENGTH_BITS = 128;
        private static final int IV_LENGTH_BYTES = 12;
        private static final int MAX_SUPPORTED_TAG_LENGTH_BITS = 128;
        static final int MIN_SUPPORTED_TAG_LENGTH_BITS = 96;
        private int mTagLengthBits = 128;

        GCM(int n) {
            super(32, n);
        }

        @Override
        protected final void addAlgorithmSpecificParametersToBegin(KeymasterArguments keymasterArguments) {
            super.addAlgorithmSpecificParametersToBegin(keymasterArguments);
            keymasterArguments.addUnsignedInt(805307371, this.mTagLengthBits);
        }

        @Override
        protected final KeyStoreCryptoOperationStreamer createAdditionalAuthenticationDataStreamer(KeyStore keyStore, IBinder iBinder) {
            return new KeyStoreCryptoOperationChunkedStreamer(new AdditionalAuthenticationDataStream(keyStore, iBinder));
        }

        @Override
        protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStore object, IBinder iBinder) {
            object = new KeyStoreCryptoOperationChunkedStreamer(new KeyStoreCryptoOperationChunkedStreamer.MainDataStream((KeyStore)object, iBinder));
            if (this.isEncrypting()) {
                return object;
            }
            return new BufferAllOutputUntilDoFinalStreamer((KeyStoreCryptoOperationStreamer)object);
        }

        @Override
        protected final AlgorithmParameters engineGetParameters() {
            byte[] arrby = this.getIv();
            if (arrby != null && arrby.length > 0) {
                try {
                    AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("GCM");
                    GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(this.mTagLengthBits, arrby);
                    algorithmParameters.init(gCMParameterSpec);
                    return algorithmParameters;
                }
                catch (InvalidParameterSpecException invalidParameterSpecException) {
                    throw new ProviderException("Failed to initialize GCM AlgorithmParameters", invalidParameterSpecException);
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    throw new ProviderException("Failed to obtain GCM AlgorithmParameters", noSuchAlgorithmException);
                }
            }
            return null;
        }

        @Override
        protected final int getAdditionalEntropyAmountForBegin() {
            if (this.getIv() == null && this.isEncrypting()) {
                return 12;
            }
            return 0;
        }

        @Override
        protected final int getAdditionalEntropyAmountForFinish() {
            return 0;
        }

        protected final int getTagLengthBits() {
            return this.mTagLengthBits;
        }

        @Override
        protected final void initAlgorithmSpecificParameters() throws InvalidKeyException {
            if (this.isEncrypting()) {
                return;
            }
            throw new InvalidKeyException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
        }

        @Override
        protected final void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                if (this.isEncrypting()) {
                    return;
                }
                throw new InvalidAlgorithmParameterException("IV required when decrypting. Use GCMParameterSpec or GCM AlgorithmParameters to provide it.");
            }
            if ("GCM".equalsIgnoreCase(algorithmParameters.getAlgorithm())) {
                try {
                    GCMParameterSpec gCMParameterSpec = algorithmParameters.getParameterSpec(GCMParameterSpec.class);
                    this.initAlgorithmSpecificParameters(gCMParameterSpec);
                    return;
                }
                catch (InvalidParameterSpecException invalidParameterSpecException) {
                    if (this.isEncrypting()) {
                        this.setIv(null);
                        return;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("IV and tag length required when decrypting, but not found in parameters: ");
                    stringBuilder.append(algorithmParameters);
                    throw new InvalidAlgorithmParameterException(stringBuilder.toString(), invalidParameterSpecException);
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported AlgorithmParameters algorithm: ");
            stringBuilder.append(algorithmParameters.getAlgorithm());
            stringBuilder.append(". Supported: GCM");
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }

        @Override
        protected final void initAlgorithmSpecificParameters(AlgorithmParameterSpec object) throws InvalidAlgorithmParameterException {
            if (object == null) {
                if (this.isEncrypting()) {
                    return;
                }
                throw new InvalidAlgorithmParameterException("GCMParameterSpec must be provided when decrypting");
            }
            if (object instanceof GCMParameterSpec) {
                Object object2 = (GCMParameterSpec)object;
                object = ((GCMParameterSpec)object2).getIV();
                if (object != null) {
                    if (((byte[])object).length == 12) {
                        int n = ((GCMParameterSpec)object2).getTLen();
                        if (n >= 96 && n <= 128 && n % 8 == 0) {
                            this.setIv((byte[])object);
                            this.mTagLengthBits = n;
                            return;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unsupported tag length: ");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" bits. Supported lengths: 96, 104, 112, 120, 128");
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unsupported IV length: ");
                    ((StringBuilder)object2).append(((byte[])object).length);
                    ((StringBuilder)object2).append(" bytes. Only ");
                    ((StringBuilder)object2).append(12);
                    ((StringBuilder)object2).append(" bytes long IV supported");
                    throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
                }
                throw new InvalidAlgorithmParameterException("Null IV in GCMParameterSpec");
            }
            throw new InvalidAlgorithmParameterException("Only GCMParameterSpec supported");
        }

        @Override
        protected final void resetAll() {
            this.mTagLengthBits = 128;
            super.resetAll();
        }

        @Override
        protected final void resetWhilePreservingInitState() {
            super.resetWhilePreservingInitState();
        }

        public static final class NoPadding
        extends GCM {
            public NoPadding() {
                super(1);
            }

            @Override
            protected final int engineGetOutputSize(int n) {
                int n2 = (this.getTagLengthBits() + 7) / 8;
                long l = this.isEncrypting() ? this.getConsumedInputSizeBytes() - this.getProducedOutputSizeBytes() + (long)n + (long)n2 : this.getConsumedInputSizeBytes() - this.getProducedOutputSizeBytes() + (long)n - (long)n2;
                if (l < 0L) {
                    return 0;
                }
                if (l > Integer.MAX_VALUE) {
                    return Integer.MAX_VALUE;
                }
                return (int)l;
            }
        }

    }

}

