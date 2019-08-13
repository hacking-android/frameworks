/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidParameterSpecException;

public class AlgorithmParametersSpi
extends java.security.AlgorithmParametersSpi {
    private String curveName;
    private ECParameterSpec ecParameterSpec;

    @Override
    protected byte[] engineGetEncoded() throws IOException {
        return this.engineGetEncoded("ASN.1");
    }

    @Override
    protected byte[] engineGetEncoded(String object) throws IOException {
        if (this.isASN1FormatString((String)object)) {
            object = this.ecParameterSpec;
            if (object == null) {
                object = new X962Parameters(DERNull.INSTANCE);
            } else {
                String string = this.curveName;
                if (string != null) {
                    object = new X962Parameters(ECUtil.getNamedCurveOid(string));
                } else {
                    object = EC5Util.convertSpec((ECParameterSpec)object, false);
                    object = new X962Parameters(new X9ECParameters(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed()));
                }
            }
            return ((ASN1Object)object).getEncoded();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unknown parameters format in AlgorithmParameters object: ");
        stringBuilder.append((String)object);
        throw new IOException(stringBuilder.toString());
    }

    @Override
    protected <T extends AlgorithmParameterSpec> T engineGetParameterSpec(Class<T> object) throws InvalidParameterSpecException {
        if (!ECParameterSpec.class.isAssignableFrom((Class<?>)object) && object != AlgorithmParameterSpec.class) {
            Object object2;
            if (ECGenParameterSpec.class.isAssignableFrom((Class<?>)object)) {
                object2 = this.curveName;
                if (object2 != null) {
                    object = ECUtil.getNamedCurveOid((String)object2);
                    if (object != null) {
                        return (T)new ECGenParameterSpec(((ASN1ObjectIdentifier)object).getId());
                    }
                    return (T)new ECGenParameterSpec(this.curveName);
                }
                object2 = ECUtil.getNamedCurveOid(EC5Util.convertSpec(this.ecParameterSpec, false));
                if (object2 != null) {
                    return (T)new ECGenParameterSpec(((ASN1ObjectIdentifier)object2).getId());
                }
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("EC AlgorithmParameters cannot convert to ");
            ((StringBuilder)object2).append(((Class)object).getName());
            throw new InvalidParameterSpecException(((StringBuilder)object2).toString());
        }
        return (T)this.ecParameterSpec;
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec) throws InvalidParameterSpecException {
        block5 : {
            block4 : {
                block2 : {
                    Object object;
                    block3 : {
                        if (!(algorithmParameterSpec instanceof ECGenParameterSpec)) break block2;
                        object = ECUtils.getDomainParametersFromGenSpec((ECGenParameterSpec)(algorithmParameterSpec = (ECGenParameterSpec)algorithmParameterSpec));
                        if (object == null) break block3;
                        this.curveName = ((ECGenParameterSpec)algorithmParameterSpec).getName();
                        algorithmParameterSpec = EC5Util.convertToSpec((X9ECParameters)object);
                        this.ecParameterSpec = new ECNamedCurveSpec(this.curveName, ((ECParameterSpec)algorithmParameterSpec).getCurve(), ((ECParameterSpec)algorithmParameterSpec).getGenerator(), ((ECParameterSpec)algorithmParameterSpec).getOrder(), BigInteger.valueOf(((ECParameterSpec)algorithmParameterSpec).getCofactor()));
                        break block4;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("EC curve name not recognized: ");
                    ((StringBuilder)object).append(((ECGenParameterSpec)algorithmParameterSpec).getName());
                    throw new InvalidParameterSpecException(((StringBuilder)object).toString());
                }
                if (!(algorithmParameterSpec instanceof ECParameterSpec)) break block5;
                this.curveName = algorithmParameterSpec instanceof ECNamedCurveSpec ? ((ECNamedCurveSpec)algorithmParameterSpec).getName() : null;
                this.ecParameterSpec = (ECParameterSpec)algorithmParameterSpec;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AlgorithmParameterSpec class not recognized: ");
        stringBuilder.append(algorithmParameterSpec.getClass().getName());
        throw new InvalidParameterSpecException(stringBuilder.toString());
    }

    @Override
    protected void engineInit(byte[] arrby) throws IOException {
        this.engineInit(arrby, "ASN.1");
    }

    @Override
    protected void engineInit(byte[] object, String object2) throws IOException {
        if (this.isASN1FormatString((String)object2)) {
            object = X962Parameters.getInstance(object);
            ECCurve eCCurve = EC5Util.getCurve(BouncyCastleProvider.CONFIGURATION, (X962Parameters)object);
            if (((X962Parameters)object).isNamedCurve()) {
                object2 = ASN1ObjectIdentifier.getInstance(((X962Parameters)object).getParameters());
                this.curveName = ECNamedCurveTable.getName((ASN1ObjectIdentifier)object2);
                if (this.curveName == null) {
                    this.curveName = ((ASN1ObjectIdentifier)object2).getId();
                }
            }
            this.ecParameterSpec = EC5Util.convertToSpec((X962Parameters)object, eCCurve);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown encoded parameters format in AlgorithmParameters object: ");
        ((StringBuilder)object).append((String)object2);
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    protected String engineToString() {
        return "EC AlgorithmParameters ";
    }

    protected boolean isASN1FormatString(String string) {
        boolean bl = string == null || string.equals("ASN.1");
        return bl;
    }
}

