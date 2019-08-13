/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.org.bouncycastle.math.ec.ECAlgorithms;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.math.field.FiniteField;
import com.android.org.bouncycastle.math.field.Polynomial;
import com.android.org.bouncycastle.math.field.PolynomialExtensionField;
import com.android.org.bouncycastle.util.Arrays;
import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EC5Util {
    private static Map customCurves = new HashMap();

    static {
        Enumeration enumeration = CustomNamedCurves.getNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            X9ECParameters x9ECParameters = ECNamedCurveTable.getByName(string);
            if (x9ECParameters == null) continue;
            customCurves.put(x9ECParameters.getCurve(), CustomNamedCurves.getByName(string).getCurve());
        }
    }

    public static ECCurve convertCurve(EllipticCurve object) {
        int[] arrn = ((EllipticCurve)object).getField();
        BigInteger bigInteger = ((EllipticCurve)object).getA();
        object = ((EllipticCurve)object).getB();
        if (arrn instanceof ECFieldFp) {
            object = new ECCurve.Fp(((ECFieldFp)arrn).getP(), bigInteger, (BigInteger)object);
            if (customCurves.containsKey(object)) {
                return (ECCurve)customCurves.get(object);
            }
            return object;
        }
        arrn = (ECFieldF2m)arrn;
        int n = arrn.getM();
        arrn = ECUtil.convertMidTerms(arrn.getMidTermsOfReductionPolynomial());
        return new ECCurve.F2m(n, arrn[0], arrn[1], arrn[2], bigInteger, (BigInteger)object);
    }

    public static EllipticCurve convertCurve(ECCurve eCCurve, byte[] arrby) {
        return new EllipticCurve(EC5Util.convertField(eCCurve.getField()), eCCurve.getA().toBigInteger(), eCCurve.getB().toBigInteger(), null);
    }

    public static ECField convertField(FiniteField object) {
        if (ECAlgorithms.isFpField((FiniteField)object)) {
            return new ECFieldFp(object.getCharacteristic());
        }
        object = ((PolynomialExtensionField)object).getMinimalPolynomial();
        int[] arrn = object.getExponentsPresent();
        arrn = Arrays.reverse(Arrays.copyOfRange(arrn, 1, arrn.length - 1));
        return new ECFieldF2m(object.getDegree(), arrn);
    }

    public static ECPoint convertPoint(ECCurve eCCurve, java.security.spec.ECPoint eCPoint, boolean bl) {
        return eCCurve.createPoint(eCPoint.getAffineX(), eCPoint.getAffineY());
    }

    public static ECPoint convertPoint(ECParameterSpec eCParameterSpec, java.security.spec.ECPoint eCPoint, boolean bl) {
        return EC5Util.convertPoint(EC5Util.convertCurve(eCParameterSpec.getCurve()), eCPoint, bl);
    }

    public static java.security.spec.ECPoint convertPoint(ECPoint eCPoint) {
        eCPoint = eCPoint.normalize();
        return new java.security.spec.ECPoint(eCPoint.getAffineXCoord().toBigInteger(), eCPoint.getAffineYCoord().toBigInteger());
    }

    public static com.android.org.bouncycastle.jce.spec.ECParameterSpec convertSpec(ECParameterSpec eCParameterSpec, boolean bl) {
        ECCurve eCCurve = EC5Util.convertCurve(eCParameterSpec.getCurve());
        if (eCParameterSpec instanceof ECNamedCurveSpec) {
            return new ECNamedCurveParameterSpec(((ECNamedCurveSpec)eCParameterSpec).getName(), eCCurve, EC5Util.convertPoint(eCCurve, eCParameterSpec.getGenerator(), bl), eCParameterSpec.getOrder(), BigInteger.valueOf(eCParameterSpec.getCofactor()), eCParameterSpec.getCurve().getSeed());
        }
        return new com.android.org.bouncycastle.jce.spec.ECParameterSpec(eCCurve, EC5Util.convertPoint(eCCurve, eCParameterSpec.getGenerator(), bl), eCParameterSpec.getOrder(), BigInteger.valueOf(eCParameterSpec.getCofactor()), eCParameterSpec.getCurve().getSeed());
    }

    public static ECParameterSpec convertSpec(EllipticCurve ellipticCurve, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec instanceof ECNamedCurveParameterSpec) {
            return new ECNamedCurveSpec(((ECNamedCurveParameterSpec)eCParameterSpec).getName(), ellipticCurve, EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH());
        }
        return new ECParameterSpec(ellipticCurve, EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
    }

    public static ECParameterSpec convertToSpec(X962Parameters object, ECCurve object2) {
        if (((X962Parameters)object).isNamedCurve()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)((X962Parameters)object).getParameters();
            X9ECParameters x9ECParameters = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            object = x9ECParameters;
            if (x9ECParameters == null) {
                Map map = BouncyCastleProvider.CONFIGURATION.getAdditionalECParameters();
                object = x9ECParameters;
                if (!map.isEmpty()) {
                    object = (X9ECParameters)map.get(aSN1ObjectIdentifier);
                }
            }
            object2 = EC5Util.convertCurve((ECCurve)object2, ((X9ECParameters)object).getSeed());
            object = new ECNamedCurveSpec(ECUtil.getCurveName(aSN1ObjectIdentifier), (EllipticCurve)object2, EC5Util.convertPoint(((X9ECParameters)object).getG()), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH());
        } else if (((X962Parameters)object).isImplicitlyCA()) {
            object = null;
        } else {
            object = X9ECParameters.getInstance(((X962Parameters)object).getParameters());
            object2 = EC5Util.convertCurve((ECCurve)object2, ((X9ECParameters)object).getSeed());
            object = ((X9ECParameters)object).getH() != null ? new ECParameterSpec((EllipticCurve)object2, EC5Util.convertPoint(((X9ECParameters)object).getG()), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH().intValue()) : new ECParameterSpec((EllipticCurve)object2, EC5Util.convertPoint(((X9ECParameters)object).getG()), ((X9ECParameters)object).getN(), 1);
        }
        return object;
    }

    public static ECParameterSpec convertToSpec(X9ECParameters x9ECParameters) {
        return new ECParameterSpec(EC5Util.convertCurve(x9ECParameters.getCurve(), null), EC5Util.convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH().intValue());
    }

    public static ECParameterSpec convertToSpec(ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(EC5Util.convertCurve(eCDomainParameters.getCurve(), null), EC5Util.convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    public static ECCurve getCurve(ProviderConfiguration object, X962Parameters object2) {
        block8 : {
            block6 : {
                Object object3;
                block7 : {
                    block5 : {
                        object3 = object.getAcceptableNamedCurves();
                        if (!((X962Parameters)object2).isNamedCurve()) break block5;
                        ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(((X962Parameters)object2).getParameters());
                        if (!object3.isEmpty() && !object3.contains(aSN1ObjectIdentifier)) {
                            throw new IllegalStateException("named curve not acceptable");
                        }
                        object2 = object3 = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
                        if (object3 == null) {
                            object2 = (X9ECParameters)object.getAdditionalECParameters().get(aSN1ObjectIdentifier);
                        }
                        object = ((X9ECParameters)object2).getCurve();
                        break block6;
                    }
                    if (!((X962Parameters)object2).isImplicitlyCA()) break block7;
                    object = object.getEcImplicitlyCa().getCurve();
                    break block6;
                }
                if (!object3.isEmpty()) break block8;
                object = X9ECParameters.getInstance(((X962Parameters)object2).getParameters()).getCurve();
            }
            return object;
        }
        throw new IllegalStateException("encoded parameters not acceptable");
    }

    public static ECDomainParameters getDomainParameters(ProviderConfiguration object, ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec == null) {
            object = object.getEcImplicitlyCa();
            object = new ECDomainParameters(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed());
        } else {
            object = ECUtil.getDomainParameters((ProviderConfiguration)object, EC5Util.convertSpec(eCParameterSpec, false));
        }
        return object;
    }
}

