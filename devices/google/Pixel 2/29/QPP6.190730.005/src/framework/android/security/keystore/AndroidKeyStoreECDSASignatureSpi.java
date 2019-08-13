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
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterBlob;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStoreSignatureSpiBase;
import android.security.keystore.KeyStoreCryptoOperationStreamer;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import libcore.util.EmptyArray;

abstract class AndroidKeyStoreECDSASignatureSpi
extends AndroidKeyStoreSignatureSpiBase {
    private int mGroupSizeBits = -1;
    private final int mKeymasterDigest;

    AndroidKeyStoreECDSASignatureSpi(int n) {
        this.mKeymasterDigest = n;
    }

    @Override
    protected final void addAlgorithmSpecificParametersToBegin(KeymasterArguments keymasterArguments) {
        keymasterArguments.addEnum(268435458, 3);
        keymasterArguments.addEnum(536870917, this.mKeymasterDigest);
    }

    @Override
    protected final int getAdditionalEntropyAmountForSign() {
        return (this.mGroupSizeBits + 7) / 8;
    }

    protected final int getGroupSizeBits() {
        int n = this.mGroupSizeBits;
        if (n != -1) {
            return n;
        }
        throw new IllegalStateException("Not initialized");
    }

    @Override
    protected final void initKey(AndroidKeyStoreKey serializable) throws InvalidKeyException {
        if ("EC".equalsIgnoreCase(((AndroidKeyStoreKey)serializable).getAlgorithm())) {
            KeyCharacteristics keyCharacteristics = new KeyCharacteristics();
            int n = this.getKeyStore().getKeyCharacteristics(((AndroidKeyStoreKey)serializable).getAlias(), null, null, ((AndroidKeyStoreKey)serializable).getUid(), keyCharacteristics);
            if (n == 1) {
                long l = keyCharacteristics.getUnsignedInt(805306371, -1L);
                if (l != -1L) {
                    if (l <= Integer.MAX_VALUE) {
                        this.mGroupSizeBits = (int)l;
                        super.initKey((AndroidKeyStoreKey)serializable);
                        return;
                    }
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("Key too large: ");
                    ((StringBuilder)serializable).append(l);
                    ((StringBuilder)serializable).append(" bits");
                    throw new InvalidKeyException(((StringBuilder)serializable).toString());
                }
                throw new InvalidKeyException("Size of key not known");
            }
            throw this.getKeyStore().getInvalidKeyException(((AndroidKeyStoreKey)serializable).getAlias(), ((AndroidKeyStoreKey)serializable).getUid(), n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported key algorithm: ");
        stringBuilder.append(((AndroidKeyStoreKey)serializable).getAlgorithm());
        stringBuilder.append(". Only");
        stringBuilder.append("EC");
        stringBuilder.append(" supported");
        throw new InvalidKeyException(stringBuilder.toString());
    }

    @Override
    protected final void resetAll() {
        this.mGroupSizeBits = -1;
        super.resetAll();
    }

    @Override
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    public static final class NONE
    extends AndroidKeyStoreECDSASignatureSpi {
        public NONE() {
            super(0);
        }

        @Override
        protected KeyStoreCryptoOperationStreamer createMainDataStreamer(KeyStore keyStore, IBinder iBinder) {
            return new TruncateToFieldSizeMessageStreamer(super.createMainDataStreamer(keyStore, iBinder), this.getGroupSizeBits());
        }

        private static class TruncateToFieldSizeMessageStreamer
        implements KeyStoreCryptoOperationStreamer {
            private long mConsumedInputSizeBytes;
            private final KeyStoreCryptoOperationStreamer mDelegate;
            private final int mGroupSizeBits;
            private final ByteArrayOutputStream mInputBuffer = new ByteArrayOutputStream();

            private TruncateToFieldSizeMessageStreamer(KeyStoreCryptoOperationStreamer keyStoreCryptoOperationStreamer, int n) {
                this.mDelegate = keyStoreCryptoOperationStreamer;
                this.mGroupSizeBits = n;
            }

            @Override
            public byte[] doFinal(byte[] arrby, int n, int n2, byte[] arrby2, byte[] arrby3) throws KeyStoreException {
                if (n2 > 0) {
                    this.mConsumedInputSizeBytes += (long)n2;
                    this.mInputBuffer.write(arrby, n, n2);
                }
                arrby = this.mInputBuffer.toByteArray();
                this.mInputBuffer.reset();
                return this.mDelegate.doFinal(arrby, 0, Math.min(arrby.length, (this.mGroupSizeBits + 7) / 8), arrby2, arrby3);
            }

            @Override
            public long getConsumedInputSizeBytes() {
                return this.mConsumedInputSizeBytes;
            }

            @Override
            public long getProducedOutputSizeBytes() {
                return this.mDelegate.getProducedOutputSizeBytes();
            }

            @Override
            public byte[] update(byte[] arrby, int n, int n2) throws KeyStoreException {
                if (n2 > 0) {
                    this.mInputBuffer.write(arrby, n, n2);
                    this.mConsumedInputSizeBytes += (long)n2;
                }
                return EmptyArray.BYTE;
            }
        }

    }

    public static final class SHA1
    extends AndroidKeyStoreECDSASignatureSpi {
        public SHA1() {
            super(2);
        }
    }

    public static final class SHA224
    extends AndroidKeyStoreECDSASignatureSpi {
        public SHA224() {
            super(3);
        }
    }

    public static final class SHA256
    extends AndroidKeyStoreECDSASignatureSpi {
        public SHA256() {
            super(4);
        }
    }

    public static final class SHA384
    extends AndroidKeyStoreECDSASignatureSpi {
        public SHA384() {
            super(5);
        }
    }

    public static final class SHA512
    extends AndroidKeyStoreECDSASignatureSpi {
        public SHA512() {
            super(6);
        }
    }

}

