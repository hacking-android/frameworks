/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.KeyStore;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keymaster.KeymasterBlob;
import android.security.keystore.AndroidKeyStoreCipherSpiBase;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStorePrivateKey;
import android.security.keystore.AndroidKeyStorePublicKey;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeymasterUtils;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.ProviderException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

abstract class AndroidKeyStoreRSACipherSpi
extends AndroidKeyStoreCipherSpiBase {
    private final int mKeymasterPadding;
    private int mKeymasterPaddingOverride;
    private int mModulusSizeBytes = -1;

    AndroidKeyStoreRSACipherSpi(int n) {
        this.mKeymasterPadding = n;
    }

    @Override
    protected void addAlgorithmSpecificParametersToBegin(KeymasterArguments keymasterArguments) {
        int n;
        keymasterArguments.addEnum(268435458, 1);
        int n2 = n = this.getKeymasterPaddingOverride();
        if (n == -1) {
            n2 = this.mKeymasterPadding;
        }
        keymasterArguments.addEnum(536870918, n2);
        n2 = this.getKeymasterPurposeOverride();
        if (n2 != -1 && (n2 == 2 || n2 == 3)) {
            keymasterArguments.addEnum(536870917, 0);
        }
    }

    protected boolean adjustConfigForEncryptingWithPrivateKey() {
        return false;
    }

    @Override
    protected final int engineGetBlockSize() {
        return 0;
    }

    @Override
    protected final byte[] engineGetIV() {
        return null;
    }

    @Override
    protected final int engineGetOutputSize(int n) {
        return this.getModulusSizeBytes();
    }

    protected final int getKeymasterPaddingOverride() {
        return this.mKeymasterPaddingOverride;
    }

    protected final int getModulusSizeBytes() {
        int n = this.mModulusSizeBytes;
        if (n != -1) {
            return n;
        }
        throw new IllegalStateException("Not initialized");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected final void initKey(int var1_1, Key var2_2) throws InvalidKeyException {
        block15 : {
            block13 : {
                block14 : {
                    if (var2_2 == null) throw new InvalidKeyException("Unsupported key: null");
                    if (!"RSA".equalsIgnoreCase(var2_2.getAlgorithm())) {
                        var3_5 = new StringBuilder();
                        var3_5.append("Unsupported key algorithm: ");
                        var3_5.append(var2_2.getAlgorithm());
                        var3_5.append(". Only ");
                        var3_5.append("RSA");
                        var3_5.append(" supported");
                        throw new InvalidKeyException(var3_5.toString());
                    }
                    if (var2_2 instanceof AndroidKeyStorePrivateKey) {
                        var2_2 = (AndroidKeyStoreKey)var2_2;
                    } else {
                        if (!(var2_2 instanceof AndroidKeyStorePublicKey)) {
                            var3_4 = new StringBuilder();
                            var3_4.append("Unsupported key type: ");
                            var3_4.append(var2_2);
                            throw new InvalidKeyException(var3_4.toString());
                        }
                        var2_2 = (AndroidKeyStoreKey)var2_2;
                    }
                    if (!(var2_2 instanceof PrivateKey)) break block13;
                    if (var1_1 == 1) break block14;
                    if (var1_1 == 2) break block15;
                    if (var1_1 != 3) {
                        if (var1_1 != 4) {
                            var2_2 = new StringBuilder();
                            var2_2.append("RSA private keys cannot be used with opmode: ");
                            var2_2.append(var1_1);
                            throw new InvalidKeyException(var2_2.toString());
                        } else {
                            ** GOTO lbl38
                        }
                    }
                    break block14;
lbl38: // 2 sources:
                    break block15;
                }
                if (!this.adjustConfigForEncryptingWithPrivateKey()) {
                    var2_2 = new StringBuilder();
                    var2_2.append("RSA private keys cannot be used with ");
                    var2_2.append(AndroidKeyStoreRSACipherSpi.opmodeToString(var1_1));
                    var2_2.append(" and padding ");
                    var2_2.append(KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding));
                    var2_2.append(". Only RSA public keys supported for this mode");
                    throw new InvalidKeyException(var2_2.toString());
                }
                break block15;
            }
            if (var1_1 != 1) {
                if (var1_1 != 2) {
                    if (var1_1 != 3) {
                        if (var1_1 != 4) {
                            var2_2 = new StringBuilder();
                            var2_2.append("RSA public keys cannot be used with ");
                            var2_2.append(AndroidKeyStoreRSACipherSpi.opmodeToString(var1_1));
                            throw new InvalidKeyException(var2_2.toString());
                        } else {
                            ** GOTO lbl-1000
                        }
                    }
                } else lbl-1000: // 3 sources:
                {
                    var2_2 = new StringBuilder();
                    var2_2.append("RSA public keys cannot be used with ");
                    var2_2.append(AndroidKeyStoreRSACipherSpi.opmodeToString(var1_1));
                    var2_2.append(" and padding ");
                    var2_2.append(KeyProperties.EncryptionPadding.fromKeymaster(this.mKeymasterPadding));
                    var2_2.append(". Only RSA private keys supported for this opmode.");
                    throw new InvalidKeyException(var2_2.toString());
                }
            }
        }
        var3_3 = new KeyCharacteristics();
        var1_1 = this.getKeyStore().getKeyCharacteristics(var2_2.getAlias(), null, null, var2_2.getUid(), var3_3);
        if (var1_1 != 1) throw this.getKeyStore().getInvalidKeyException(var2_2.getAlias(), var2_2.getUid(), var1_1);
        var4_6 = var3_3.getUnsignedInt(805306371, -1L);
        if (var4_6 == -1L) throw new InvalidKeyException("Size of key not known");
        if (var4_6 <= Integer.MAX_VALUE) {
            this.mModulusSizeBytes = (int)((7L + var4_6) / 8L);
            this.setKey((AndroidKeyStoreKey)var2_2);
            return;
        }
        var2_2 = new StringBuilder();
        var2_2.append("Key too large: ");
        var2_2.append(var4_6);
        var2_2.append(" bits");
        throw new InvalidKeyException(var2_2.toString());
    }

    @Override
    protected void loadAlgorithmSpecificParametersFromBeginResult(KeymasterArguments keymasterArguments) {
    }

    @Override
    protected final void resetAll() {
        this.mModulusSizeBytes = -1;
        this.mKeymasterPaddingOverride = -1;
        super.resetAll();
    }

    @Override
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    protected final void setKeymasterPaddingOverride(int n) {
        this.mKeymasterPaddingOverride = n;
    }

    public static final class NoPadding
    extends AndroidKeyStoreRSACipherSpi {
        public NoPadding() {
            super(1);
        }

        @Override
        protected boolean adjustConfigForEncryptingWithPrivateKey() {
            this.setKeymasterPurposeOverride(2);
            return true;
        }

        @Override
        protected AlgorithmParameters engineGetParameters() {
            return null;
        }

        @Override
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override
        protected final int getAdditionalEntropyAmountForFinish() {
            return 0;
        }

        @Override
        protected void initAlgorithmSpecificParameters() throws InvalidKeyException {
        }

        @Override
        protected void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected parameters: ");
            stringBuilder.append(algorithmParameters);
            stringBuilder.append(". No parameters supported");
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }

        @Override
        protected void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected parameters: ");
            stringBuilder.append(algorithmParameterSpec);
            stringBuilder.append(". No parameters supported");
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }
    }

    static abstract class OAEPWithMGF1Padding
    extends AndroidKeyStoreRSACipherSpi {
        private static final String MGF_ALGORITGM_MGF1 = "MGF1";
        private int mDigestOutputSizeBytes;
        private int mKeymasterDigest = -1;

        OAEPWithMGF1Padding(int n) {
            super(2);
            this.mKeymasterDigest = n;
            this.mDigestOutputSizeBytes = (KeymasterUtils.getDigestOutputSizeBits(n) + 7) / 8;
        }

        @Override
        protected final void addAlgorithmSpecificParametersToBegin(KeymasterArguments keymasterArguments) {
            super.addAlgorithmSpecificParametersToBegin(keymasterArguments);
            keymasterArguments.addEnum(536870917, this.mKeymasterDigest);
        }

        @Override
        protected final AlgorithmParameters engineGetParameters() {
            OAEPParameterSpec oAEPParameterSpec = new OAEPParameterSpec(KeyProperties.Digest.fromKeymaster(this.mKeymasterDigest), MGF_ALGORITGM_MGF1, MGF1ParameterSpec.SHA1, PSource.PSpecified.DEFAULT);
            try {
                AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("OAEP");
                algorithmParameters.init(oAEPParameterSpec);
                return algorithmParameters;
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                throw new ProviderException("Failed to initialize OAEP AlgorithmParameters with an IV", invalidParameterSpecException);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new ProviderException("Failed to obtain OAEP AlgorithmParameters", noSuchAlgorithmException);
            }
        }

        @Override
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override
        protected final int getAdditionalEntropyAmountForFinish() {
            int n = this.isEncrypting() ? this.mDigestOutputSizeBytes : 0;
            return n;
        }

        @Override
        protected final void initAlgorithmSpecificParameters() throws InvalidKeyException {
        }

        @Override
        protected final void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            Object object;
            if (algorithmParameters == null) {
                return;
            }
            try {
                object = algorithmParameters.getParameterSpec(OAEPParameterSpec.class);
                if (object != null) {
                    this.initAlgorithmSpecificParameters((AlgorithmParameterSpec)object);
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("OAEP parameters required, but not provided in parameters: ");
            }
            catch (InvalidParameterSpecException invalidParameterSpecException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("OAEP parameters required, but not found in parameters: ");
                stringBuilder.append(algorithmParameters);
                throw new InvalidAlgorithmParameterException(stringBuilder.toString(), invalidParameterSpecException);
            }
            ((StringBuilder)object).append(algorithmParameters);
            throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
        }

        @Override
        protected final void initAlgorithmSpecificParameters(AlgorithmParameterSpec object) throws InvalidAlgorithmParameterException {
            if (object == null) {
                return;
            }
            if (object instanceof OAEPParameterSpec) {
                Object object2 = (OAEPParameterSpec)object;
                if (MGF_ALGORITGM_MGF1.equalsIgnoreCase(((OAEPParameterSpec)object2).getMGFAlgorithm())) {
                    int n;
                    block10 : {
                        object = ((OAEPParameterSpec)object2).getDigestAlgorithm();
                        try {
                            n = KeyProperties.Digest.toKeymaster((String)object);
                            if (n == 2 || n == 3 || n == 4 || n == 5 || n == 6) break block10;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unsupported digest: ");
                            stringBuilder.append((String)object);
                            throw new InvalidAlgorithmParameterException(stringBuilder.toString(), illegalArgumentException);
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unsupported digest: ");
                        ((StringBuilder)object2).append((String)object);
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
                    }
                    object = ((OAEPParameterSpec)object2).getMGFParameters();
                    if (object != null) {
                        if (object instanceof MGF1ParameterSpec) {
                            if ("SHA-1".equalsIgnoreCase((String)(object = ((MGF1ParameterSpec)object).getDigestAlgorithm()))) {
                                object = ((OAEPParameterSpec)object2).getPSource();
                                if (object instanceof PSource.PSpecified) {
                                    object2 = ((PSource.PSpecified)object).getValue();
                                    if (object2 != null && ((Object)object2).length > 0) {
                                        object2 = new StringBuilder();
                                        ((StringBuilder)object2).append("Unsupported source of encoding input P: ");
                                        ((StringBuilder)object2).append(object);
                                        ((StringBuilder)object2).append(". Only pSpecifiedEmpty (PSource.PSpecified.DEFAULT) supported");
                                        throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
                                    }
                                    this.mKeymasterDigest = n;
                                    this.mDigestOutputSizeBytes = (KeymasterUtils.getDigestOutputSizeBits(n) + 7) / 8;
                                    return;
                                }
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Unsupported source of encoding input P: ");
                                ((StringBuilder)object2).append(object);
                                ((StringBuilder)object2).append(". Only pSpecifiedEmpty (PSource.PSpecified.DEFAULT) supported");
                                throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
                            }
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Unsupported MGF1 digest: ");
                            ((StringBuilder)object2).append((String)object);
                            ((StringBuilder)object2).append(". Only ");
                            ((StringBuilder)object2).append("SHA-1");
                            ((StringBuilder)object2).append(" supported");
                            throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Unsupported MGF parameters: ");
                        ((StringBuilder)object2).append(object);
                        ((StringBuilder)object2).append(". Only MGF1ParameterSpec supported");
                        throw new InvalidAlgorithmParameterException(((StringBuilder)object2).toString());
                    }
                    throw new InvalidAlgorithmParameterException("MGF parameters must be provided");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported MGF: ");
                ((StringBuilder)object).append(((OAEPParameterSpec)object2).getMGFAlgorithm());
                ((StringBuilder)object).append(". Only ");
                ((StringBuilder)object).append(MGF_ALGORITGM_MGF1);
                ((StringBuilder)object).append(" supported");
                throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported parameter spec: ");
            stringBuilder.append(object);
            stringBuilder.append(". Only OAEPParameterSpec supported");
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }

        @Override
        protected final void loadAlgorithmSpecificParametersFromBeginResult(KeymasterArguments keymasterArguments) {
            super.loadAlgorithmSpecificParametersFromBeginResult(keymasterArguments);
        }
    }

    public static class OAEPWithSHA1AndMGF1Padding
    extends OAEPWithMGF1Padding {
        public OAEPWithSHA1AndMGF1Padding() {
            super(2);
        }
    }

    public static class OAEPWithSHA224AndMGF1Padding
    extends OAEPWithMGF1Padding {
        public OAEPWithSHA224AndMGF1Padding() {
            super(3);
        }
    }

    public static class OAEPWithSHA256AndMGF1Padding
    extends OAEPWithMGF1Padding {
        public OAEPWithSHA256AndMGF1Padding() {
            super(4);
        }
    }

    public static class OAEPWithSHA384AndMGF1Padding
    extends OAEPWithMGF1Padding {
        public OAEPWithSHA384AndMGF1Padding() {
            super(5);
        }
    }

    public static class OAEPWithSHA512AndMGF1Padding
    extends OAEPWithMGF1Padding {
        public OAEPWithSHA512AndMGF1Padding() {
            super(6);
        }
    }

    public static final class PKCS1Padding
    extends AndroidKeyStoreRSACipherSpi {
        public PKCS1Padding() {
            super(4);
        }

        @Override
        protected boolean adjustConfigForEncryptingWithPrivateKey() {
            this.setKeymasterPurposeOverride(2);
            this.setKeymasterPaddingOverride(5);
            return true;
        }

        @Override
        protected AlgorithmParameters engineGetParameters() {
            return null;
        }

        @Override
        protected final int getAdditionalEntropyAmountForBegin() {
            return 0;
        }

        @Override
        protected final int getAdditionalEntropyAmountForFinish() {
            int n = this.isEncrypting() ? this.getModulusSizeBytes() : 0;
            return n;
        }

        @Override
        protected void initAlgorithmSpecificParameters() throws InvalidKeyException {
        }

        @Override
        protected void initAlgorithmSpecificParameters(AlgorithmParameters algorithmParameters) throws InvalidAlgorithmParameterException {
            if (algorithmParameters == null) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected parameters: ");
            stringBuilder.append(algorithmParameters);
            stringBuilder.append(". No parameters supported");
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }

        @Override
        protected void initAlgorithmSpecificParameters(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidAlgorithmParameterException {
            if (algorithmParameterSpec == null) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected parameters: ");
            stringBuilder.append(algorithmParameterSpec);
            stringBuilder.append(". No parameters supported");
            throw new InvalidAlgorithmParameterException(stringBuilder.toString());
        }
    }

}

