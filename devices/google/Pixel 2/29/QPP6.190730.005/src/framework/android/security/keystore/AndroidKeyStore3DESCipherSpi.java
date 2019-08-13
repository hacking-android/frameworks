/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keymaster.KeymasterArguments;
import android.security.keystore.AndroidKeyStoreCipherSpiBase;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStoreSecretKey;
import android.security.keystore.ArrayUtils;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Arrays;
import javax.crypto.spec.IvParameterSpec;

public class AndroidKeyStore3DESCipherSpi
extends AndroidKeyStoreCipherSpiBase {
    private static final int BLOCK_SIZE_BYTES = 8;
    private byte[] mIv;
    private boolean mIvHasBeenUsed;
    private final boolean mIvRequired;
    private final int mKeymasterBlockMode;
    private final int mKeymasterPadding;

    AndroidKeyStore3DESCipherSpi(int n, int n2, boolean bl) {
        this.mKeymasterBlockMode = n;
        this.mKeymasterPadding = n2;
        this.mIvRequired = bl;
    }

    @Override
    protected void addAlgorithmSpecificParametersToBegin(KeymasterArguments keymasterArguments) {
        byte[] arrby;
        if (this.isEncrypting() && this.mIvRequired && this.mIvHasBeenUsed) {
            throw new IllegalStateException("IV has already been used. Reusing IV in encryption mode violates security best practices.");
        }
        keymasterArguments.addEnum(268435458, 33);
        keymasterArguments.addEnum(536870916, this.mKeymasterBlockMode);
        keymasterArguments.addEnum(536870918, this.mKeymasterPadding);
        if (this.mIvRequired && (arrby = this.mIv) != null) {
            keymasterArguments.addBytes(-1879047191, arrby);
        }
    }

    @Override
    protected int engineGetBlockSize() {
        return 8;
    }

    @Override
    protected final byte[] engineGetIV() {
        return ArrayUtils.cloneIfNotEmpty(this.mIv);
    }

    @Override
    protected int engineGetOutputSize(int n) {
        return n + 24;
    }

    @Override
    protected AlgorithmParameters engineGetParameters() {
        if (!this.mIvRequired) {
            return null;
        }
        Object object = this.mIv;
        if (object != null && ((byte[])object).length > 0) {
            try {
                object = AlgorithmParameters.getInstance("DESede");
                IvParameterSpec ivParameterSpec = new IvParameterSpec(this.mIv);
                ((AlgorithmParameters)object).init(ivParameterSpec);
                return object;
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                throw new ProviderException("Failed to initialize 3DES AlgorithmParameters with an IV", invalidParameterSpecException);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new ProviderException("Failed to obtain 3DES AlgorithmParameters", noSuchAlgorithmException);
            }
        }
        return null;
    }

    @Override
    protected final int getAdditionalEntropyAmountForBegin() {
        if (this.mIvRequired && this.mIv == null && this.isEncrypting()) {
            return 8;
        }
        return 0;
    }

    @Override
    protected int getAdditionalEntropyAmountForFinish() {
        return 0;
    }

    @Override
    protected void initAlgorithmSpecificParameters() throws InvalidKeyException {
        if (!this.mIvRequired) {
            return;
        }
        if (this.isEncrypting()) {
            return;
        }
        throw new InvalidKeyException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
    }

    @Override
    protected void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (algorithmParameters == null) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported parameters: ");
            stringBuilder.append(algorithmParameters);
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }
        if (algorithmParameters == null) {
            if (this.isEncrypting()) {
                return;
            }
            throw new InvalidAlgorithmParameterException("IV required when decrypting. Use IvParameterSpec or AlgorithmParameters to provide it.");
        }
        if ("DESede".equalsIgnoreCase(algorithmParameters.getAlgorithm())) {
            try {
                IvParameterSpec ivParameterSpec = algorithmParameters.getParameterSpec(IvParameterSpec.class);
                this.mIv = ivParameterSpec.getIV();
                if (this.mIv != null) {
                    return;
                }
                throw new InvalidAlgorithmParameterException("Null IV in AlgorithmParameters");
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                if (this.isEncrypting()) {
                    this.mIv = null;
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("IV required when decrypting, but not found in parameters: ");
                stringBuilder.append(algorithmParameters);
                throw new InvalidAlgorithmParameterException(stringBuilder.toString(), invalidParameterSpecException);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported AlgorithmParameters algorithm: ");
        stringBuilder.append(algorithmParameters.getAlgorithm());
        stringBuilder.append(". Supported: DESede");
        throw new InvalidAlgorithmParameterException(stringBuilder.toString());
    }

    @Override
    protected void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
        if (!this.mIvRequired) {
            if (algorithmParameterSpec == null) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported parameters: ");
            stringBuilder.append(algorithmParameterSpec);
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }
        if (algorithmParameterSpec == null) {
            if (this.isEncrypting()) {
                return;
            }
            throw new InvalidAlgorithmParameterException("IvParameterSpec must be provided when decrypting");
        }
        if (algorithmParameterSpec instanceof IvParameterSpec) {
            this.mIv = ((IvParameterSpec)algorithmParameterSpec).getIV();
            if (this.mIv != null) {
                return;
            }
            throw new InvalidAlgorithmParameterException("Null IV in IvParameterSpec");
        }
        throw new InvalidAlgorithmParameterException("Only IvParameterSpec supported");
    }

    @Override
    protected void initKey(int n, Key object) throws InvalidKeyException {
        if (!(object instanceof AndroidKeyStoreSecretKey)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported key: ");
            object = object != null ? object.getClass().getName() : "null";
            stringBuilder.append((String)object);
            throw new InvalidKeyException(stringBuilder.toString());
        }
        if ("DESede".equalsIgnoreCase(object.getAlgorithm())) {
            this.setKey((AndroidKeyStoreSecretKey)object);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported key algorithm: ");
        stringBuilder.append(object.getAlgorithm());
        stringBuilder.append(". Only ");
        stringBuilder.append("DESede");
        stringBuilder.append(" supported");
        throw new InvalidKeyException(stringBuilder.toString());
    }

    @Override
    protected void loadAlgorithmSpecificParametersFromBeginResult(KeymasterArguments arrby) {
        block11 : {
            block10 : {
                block9 : {
                    byte[] arrby2;
                    this.mIvHasBeenUsed = true;
                    arrby = arrby2 = arrby.getBytes(-1879047191, null);
                    if (arrby2 != null) {
                        arrby = arrby2;
                        if (arrby2.length == 0) {
                            arrby = null;
                        }
                    }
                    if (!this.mIvRequired) break block9;
                    arrby2 = this.mIv;
                    if (arrby2 == null) {
                        this.mIv = arrby;
                    } else if (arrby != null && !Arrays.equals(arrby, arrby2)) {
                        throw new ProviderException("IV in use differs from provided IV");
                    }
                    break block10;
                }
                if (arrby != null) break block11;
            }
            return;
        }
        throw new ProviderException("IV in use despite IV not being used by this transformation");
    }

    @Override
    protected final void resetAll() {
        this.mIv = null;
        this.mIvHasBeenUsed = false;
        super.resetAll();
    }

    static abstract class CBC
    extends AndroidKeyStore3DESCipherSpi {
        protected CBC(int n) {
            super(2, n, true);
        }

        public static class NoPadding
        extends CBC {
            public NoPadding() {
                super(1);
            }
        }

        public static class PKCS7Padding
        extends CBC {
            public PKCS7Padding() {
                super(64);
            }
        }

    }

    static abstract class ECB
    extends AndroidKeyStore3DESCipherSpi {
        protected ECB(int n) {
            super(1, n, false);
        }

        public static class NoPadding
        extends ECB {
            public NoPadding() {
                super(1);
            }
        }

        public static class PKCS7Padding
        extends ECB {
            public PKCS7Padding() {
                super(64);
            }
        }

    }

}

