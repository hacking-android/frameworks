/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.Credentials;
import android.security.KeyStore;
import android.security.keymaster.KeyCharacteristics;
import android.security.keymaster.KeymasterArguments;
import android.security.keystore.AndroidKeyStoreSecretKey;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.security.keystore.KeyStoreCryptoOperationUtils;
import android.security.keystore.KeymasterUtils;
import android.security.keystore.StrongBoxUnavailableException;
import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.ProviderException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Date;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;

public abstract class AndroidKeyStoreKeyGeneratorSpi
extends KeyGeneratorSpi {
    private final int mDefaultKeySizeBits;
    protected int mKeySizeBits;
    private final KeyStore mKeyStore = KeyStore.getInstance();
    private final int mKeymasterAlgorithm;
    private int[] mKeymasterBlockModes;
    private final int mKeymasterDigest;
    private int[] mKeymasterDigests;
    private int[] mKeymasterPaddings;
    private int[] mKeymasterPurposes;
    private SecureRandom mRng;
    private KeyGenParameterSpec mSpec;

    protected AndroidKeyStoreKeyGeneratorSpi(int n, int n2) {
        this(n, -1, n2);
    }

    protected AndroidKeyStoreKeyGeneratorSpi(int n, int n2, int n3) {
        this.mKeymasterAlgorithm = n;
        this.mKeymasterDigest = n2;
        this.mDefaultKeySizeBits = n3;
        if (this.mDefaultKeySizeBits > 0) {
            if (this.mKeymasterAlgorithm == 128 && this.mKeymasterDigest == -1) {
                throw new IllegalArgumentException("Digest algorithm must be specified for HMAC key");
            }
            return;
        }
        throw new IllegalArgumentException("Default key size must be positive");
    }

    private void resetAll() {
        this.mSpec = null;
        this.mRng = null;
        this.mKeySizeBits = -1;
        this.mKeymasterPurposes = null;
        this.mKeymasterPaddings = null;
        this.mKeymasterBlockModes = null;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    @Override
    protected SecretKey engineGenerateKey() {
        KeyGenParameterSpec keyGenParameterSpec = this.mSpec;
        if (keyGenParameterSpec != null) {
            Object object = new KeymasterArguments();
            ((KeymasterArguments)object).addUnsignedInt(805306371, this.mKeySizeBits);
            ((KeymasterArguments)object).addEnum(268435458, this.mKeymasterAlgorithm);
            ((KeymasterArguments)object).addEnums(536870913, this.mKeymasterPurposes);
            ((KeymasterArguments)object).addEnums(536870916, this.mKeymasterBlockModes);
            ((KeymasterArguments)object).addEnums(536870918, this.mKeymasterPaddings);
            ((KeymasterArguments)object).addEnums(536870917, this.mKeymasterDigests);
            KeymasterUtils.addUserAuthArgs((KeymasterArguments)object, keyGenParameterSpec);
            KeymasterUtils.addMinMacLengthAuthorizationIfNecessary((KeymasterArguments)object, this.mKeymasterAlgorithm, this.mKeymasterBlockModes, this.mKeymasterDigests);
            ((KeymasterArguments)object).addDateIfNotNull(1610613136, keyGenParameterSpec.getKeyValidityStart());
            ((KeymasterArguments)object).addDateIfNotNull(1610613137, keyGenParameterSpec.getKeyValidityForOriginationEnd());
            ((KeymasterArguments)object).addDateIfNotNull(1610613138, keyGenParameterSpec.getKeyValidityForConsumptionEnd());
            if ((keyGenParameterSpec.getPurposes() & 1) != 0 && !keyGenParameterSpec.isRandomizedEncryptionRequired()) {
                ((KeymasterArguments)object).addBoolean(1879048199);
            }
            byte[] arrby = KeyStoreCryptoOperationUtils.getRandomBytesToMixIntoKeystoreRng(this.mRng, (this.mKeySizeBits + 7) / 8);
            int n = keyGenParameterSpec.isStrongBoxBacked() ? 0 | 16 : 0;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("USRPKEY_");
            ((StringBuilder)object2).append(keyGenParameterSpec.getKeystoreAlias());
            object2 = ((StringBuilder)object2).toString();
            KeyCharacteristics keyCharacteristics = new KeyCharacteristics();
            try {
                Credentials.deleteAllTypesForAlias(this.mKeyStore, keyGenParameterSpec.getKeystoreAlias(), keyGenParameterSpec.getUid());
                n = this.mKeyStore.generateKey((String)object2, (KeymasterArguments)object, arrby, keyGenParameterSpec.getUid(), n, keyCharacteristics);
                if (n != 1) {
                    if (n == -68) {
                        object2 = new StrongBoxUnavailableException("Failed to generate key");
                        throw object2;
                    }
                    object2 = new ProviderException("Keystore operation failed", KeyStore.getKeyStoreException(n));
                    throw object2;
                }
                try {
                    object = KeyProperties.KeyAlgorithm.fromKeymasterSecretKeyAlgorithm(this.mKeymasterAlgorithm, this.mKeymasterDigest);
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    object2 = new ProviderException("Failed to obtain JCA secret key algorithm name", illegalArgumentException);
                    throw object2;
                }
                object2 = new AndroidKeyStoreSecretKey((String)object2, keyGenParameterSpec.getUid(), (String)object);
                return object2;
            }
            finally {
                if (!true) {
                    Credentials.deleteAllTypesForAlias(this.mKeyStore, keyGenParameterSpec.getKeystoreAlias(), keyGenParameterSpec.getUid());
                }
            }
        }
        throw new IllegalStateException("Not initialized");
    }

    @Override
    protected void engineInit(int n, SecureRandom serializable) {
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Cannot initialize without a ");
        ((StringBuilder)serializable).append(KeyGenParameterSpec.class.getName());
        ((StringBuilder)serializable).append(" parameter");
        throw new UnsupportedOperationException(((StringBuilder)serializable).toString());
    }

    @Override
    protected void engineInit(SecureRandom serializable) {
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Cannot initialize without a ");
        ((StringBuilder)serializable).append(KeyGenParameterSpec.class.getName());
        ((StringBuilder)serializable).append(" parameter");
        throw new UnsupportedOperationException(((StringBuilder)serializable).toString());
    }

    /*
     * Exception decompiling
     */
    @Override
    protected void engineInit(AlgorithmParameterSpec var1_1, SecureRandom var2_3) throws InvalidAlgorithmParameterException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 8[FORLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public static class AES
    extends AndroidKeyStoreKeyGeneratorSpi {
        public AES() {
            super(32, 128);
        }

        @Override
        protected void engineInit(AlgorithmParameterSpec object, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
            super.engineInit((AlgorithmParameterSpec)object, secureRandom);
            if (this.mKeySizeBits != 128 && this.mKeySizeBits != 192 && this.mKeySizeBits != 256) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported key size: ");
                ((StringBuilder)object).append(this.mKeySizeBits);
                ((StringBuilder)object).append(". Supported: 128, 192, 256.");
                throw new InvalidAlgorithmParameterException(((StringBuilder)object).toString());
            }
        }
    }

    public static class DESede
    extends AndroidKeyStoreKeyGeneratorSpi {
        public DESede() {
            super(33, 168);
        }
    }

    protected static abstract class HmacBase
    extends AndroidKeyStoreKeyGeneratorSpi {
        protected HmacBase(int n) {
            super(128, n, KeymasterUtils.getDigestOutputSizeBits(n));
        }
    }

    public static class HmacSHA1
    extends HmacBase {
        public HmacSHA1() {
            super(2);
        }
    }

    public static class HmacSHA224
    extends HmacBase {
        public HmacSHA224() {
            super(3);
        }
    }

    public static class HmacSHA256
    extends HmacBase {
        public HmacSHA256() {
            super(4);
        }
    }

    public static class HmacSHA384
    extends HmacBase {
        public HmacSHA384() {
            super(5);
        }
    }

    public static class HmacSHA512
    extends HmacBase {
        public HmacSHA512() {
            super(6);
        }
    }

}

