/*
 * Decompiled with CFR 0.145.
 */
package android.security.keystore;

import android.security.keymaster.KeymasterArguments;
import android.security.keystore.AndroidKeyStoreKey;
import android.security.keystore.AndroidKeyStoreSignatureSpiBase;
import java.security.InvalidKeyException;

abstract class AndroidKeyStoreRSASignatureSpi
extends AndroidKeyStoreSignatureSpiBase {
    private final int mKeymasterDigest;
    private final int mKeymasterPadding;

    AndroidKeyStoreRSASignatureSpi(int n, int n2) {
        this.mKeymasterDigest = n;
        this.mKeymasterPadding = n2;
    }

    @Override
    protected final void addAlgorithmSpecificParametersToBegin(KeymasterArguments keymasterArguments) {
        keymasterArguments.addEnum(268435458, 1);
        keymasterArguments.addEnum(536870917, this.mKeymasterDigest);
        keymasterArguments.addEnum(536870918, this.mKeymasterPadding);
    }

    @Override
    protected final void initKey(AndroidKeyStoreKey androidKeyStoreKey) throws InvalidKeyException {
        if ("RSA".equalsIgnoreCase(androidKeyStoreKey.getAlgorithm())) {
            super.initKey(androidKeyStoreKey);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unsupported key algorithm: ");
        stringBuilder.append(androidKeyStoreKey.getAlgorithm());
        stringBuilder.append(". Only");
        stringBuilder.append("RSA");
        stringBuilder.append(" supported");
        throw new InvalidKeyException(stringBuilder.toString());
    }

    @Override
    protected final void resetAll() {
        super.resetAll();
    }

    @Override
    protected final void resetWhilePreservingInitState() {
        super.resetWhilePreservingInitState();
    }

    public static final class MD5WithPKCS1Padding
    extends PKCS1Padding {
        public MD5WithPKCS1Padding() {
            super(1);
        }
    }

    public static final class NONEWithPKCS1Padding
    extends PKCS1Padding {
        public NONEWithPKCS1Padding() {
            super(0);
        }
    }

    static abstract class PKCS1Padding
    extends AndroidKeyStoreRSASignatureSpi {
        PKCS1Padding(int n) {
            super(n, 5);
        }

        @Override
        protected final int getAdditionalEntropyAmountForSign() {
            return 0;
        }
    }

    static abstract class PSSPadding
    extends AndroidKeyStoreRSASignatureSpi {
        private static final int SALT_LENGTH_BYTES = 20;

        PSSPadding(int n) {
            super(n, 3);
        }

        @Override
        protected final int getAdditionalEntropyAmountForSign() {
            return 20;
        }
    }

    public static final class SHA1WithPKCS1Padding
    extends PKCS1Padding {
        public SHA1WithPKCS1Padding() {
            super(2);
        }
    }

    public static final class SHA1WithPSSPadding
    extends PSSPadding {
        public SHA1WithPSSPadding() {
            super(2);
        }
    }

    public static final class SHA224WithPKCS1Padding
    extends PKCS1Padding {
        public SHA224WithPKCS1Padding() {
            super(3);
        }
    }

    public static final class SHA224WithPSSPadding
    extends PSSPadding {
        public SHA224WithPSSPadding() {
            super(3);
        }
    }

    public static final class SHA256WithPKCS1Padding
    extends PKCS1Padding {
        public SHA256WithPKCS1Padding() {
            super(4);
        }
    }

    public static final class SHA256WithPSSPadding
    extends PSSPadding {
        public SHA256WithPSSPadding() {
            super(4);
        }
    }

    public static final class SHA384WithPKCS1Padding
    extends PKCS1Padding {
        public SHA384WithPKCS1Padding() {
            super(5);
        }
    }

    public static final class SHA384WithPSSPadding
    extends PSSPadding {
        public SHA384WithPSSPadding() {
            super(5);
        }
    }

    public static final class SHA512WithPKCS1Padding
    extends PKCS1Padding {
        public SHA512WithPKCS1Padding() {
            super(6);
        }
    }

    public static final class SHA512WithPSSPadding
    extends PSSPadding {
        public SHA512WithPSSPadding() {
            super(6);
        }
    }

}

