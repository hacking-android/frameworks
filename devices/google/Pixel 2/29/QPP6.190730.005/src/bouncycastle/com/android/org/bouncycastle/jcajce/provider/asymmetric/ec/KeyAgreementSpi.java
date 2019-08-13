/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.asn1.x9.X9IntegerConverter;
import com.android.org.bouncycastle.crypto.BasicAgreement;
import com.android.org.bouncycastle.crypto.CipherParameters;
import com.android.org.bouncycastle.crypto.DerivationFunction;
import com.android.org.bouncycastle.crypto.agreement.ECDHBasicAgreement;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.BaseAgreementSpi;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jcajce.spec.UserKeyingMaterialSpec;
import com.android.org.bouncycastle.jce.interfaces.ECPrivateKey;
import com.android.org.bouncycastle.jce.interfaces.ECPublicKey;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.util.Arrays;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

public class KeyAgreementSpi
extends BaseAgreementSpi {
    private static final X9IntegerConverter converter = new X9IntegerConverter();
    private Object agreement;
    private String kaAlgorithm;
    private ECDomainParameters parameters;
    private byte[] result;

    protected KeyAgreementSpi(String string, BasicAgreement basicAgreement, DerivationFunction derivationFunction) {
        super(string, derivationFunction);
        this.kaAlgorithm = string;
        this.agreement = basicAgreement;
    }

    private static String getSimpleName(Class object) {
        object = ((Class)object).getName();
        return ((String)object).substring(((String)object).lastIndexOf(46) + 1);
    }

    private void initFromKey(Key serializable, AlgorithmParameterSpec algorithmParameterSpec) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (serializable instanceof PrivateKey) {
            if (this.kdf == null && algorithmParameterSpec instanceof UserKeyingMaterialSpec) {
                throw new InvalidAlgorithmParameterException("no KDF specified for UserKeyingMaterialSpec");
            }
            ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters)ECUtil.generatePrivateKeyParameter((PrivateKey)serializable);
            this.parameters = eCPrivateKeyParameters.getParameters();
            serializable = algorithmParameterSpec instanceof UserKeyingMaterialSpec ? ((UserKeyingMaterialSpec)algorithmParameterSpec).getUserKeyingMaterial() : null;
            this.ukmParameters = serializable;
            ((BasicAgreement)this.agreement).init(eCPrivateKeyParameters);
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(this.kaAlgorithm);
        ((StringBuilder)serializable).append(" key agreement requires ");
        ((StringBuilder)serializable).append(KeyAgreementSpi.getSimpleName(ECPrivateKey.class));
        ((StringBuilder)serializable).append(" for initialisation");
        throw new InvalidKeyException(((StringBuilder)serializable).toString());
    }

    protected byte[] bigIntToBytes(BigInteger bigInteger) {
        X9IntegerConverter x9IntegerConverter = converter;
        return x9IntegerConverter.integerToBytes(bigInteger, x9IntegerConverter.getByteLength(this.parameters.getCurve()));
    }

    @Override
    protected byte[] calcSecret() {
        return Arrays.clone(this.result);
    }

    @Override
    protected Key engineDoPhase(Key object, boolean bl) throws InvalidKeyException, IllegalStateException {
        if (this.parameters != null) {
            if (bl) {
                if (object instanceof PublicKey) {
                    object = ECUtils.generatePublicKeyParameter((PublicKey)object);
                    try {
                        this.result = this.bigIntToBytes(((BasicAgreement)this.agreement).calculateAgreement((CipherParameters)object));
                        return null;
                    }
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("calculation failed: ");
                        stringBuilder.append(exception.getMessage());
                        throw new InvalidKeyException(stringBuilder.toString()){

                            @Override
                            public Throwable getCause() {
                                return exception;
                            }
                        };
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(this.kaAlgorithm);
                ((StringBuilder)object).append(" key agreement requires ");
                ((StringBuilder)object).append(KeyAgreementSpi.getSimpleName(ECPublicKey.class));
                ((StringBuilder)object).append(" for doPhase");
                throw new InvalidKeyException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(this.kaAlgorithm);
            ((StringBuilder)object).append(" can only be between two parties.");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.kaAlgorithm);
        ((StringBuilder)object).append(" not initialised.");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @Override
    protected void engineInit(Key key, SecureRandom secureRandom) throws InvalidKeyException {
        try {
            this.initFromKey(key, null);
            return;
        }
        catch (InvalidAlgorithmParameterException invalidAlgorithmParameterException) {
            throw new InvalidKeyException(invalidAlgorithmParameterException.getMessage());
        }
    }

    @Override
    protected void engineInit(Key key, AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameterSpec != null && !(algorithmParameterSpec instanceof UserKeyingMaterialSpec)) {
            throw new InvalidAlgorithmParameterException("No algorithm parameters supported");
        }
        this.initFromKey(key, algorithmParameterSpec);
    }

    public static class DH
    extends KeyAgreementSpi {
        public DH() {
            super("ECDH", new ECDHBasicAgreement(), null);
        }
    }

}

