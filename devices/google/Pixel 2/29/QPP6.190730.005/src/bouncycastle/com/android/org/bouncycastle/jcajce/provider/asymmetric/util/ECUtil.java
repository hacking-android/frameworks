/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECNamedDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.interfaces.ECPrivateKey;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.Fingerprint;
import com.android.org.bouncycastle.util.Strings;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.util.Enumeration;
import java.util.Map;

public class ECUtil {
    private static ECPoint calculateQ(BigInteger bigInteger, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        return eCParameterSpec.getG().multiply(bigInteger).normalize();
    }

    static int[] convertMidTerms(int[] arrn) {
        block15 : {
            int[] arrn2;
            block14 : {
                block13 : {
                    arrn2 = new int[3];
                    if (arrn.length != 1) break block13;
                    arrn2[0] = arrn[0];
                    break block14;
                }
                if (arrn.length != 3) break block15;
                if (arrn[0] < arrn[1] && arrn[0] < arrn[2]) {
                    arrn2[0] = arrn[0];
                    if (arrn[1] < arrn[2]) {
                        arrn2[1] = arrn[1];
                        arrn2[2] = arrn[2];
                    } else {
                        arrn2[1] = arrn[2];
                        arrn2[2] = arrn[1];
                    }
                } else if (arrn[1] < arrn[2]) {
                    arrn2[0] = arrn[1];
                    if (arrn[0] < arrn[2]) {
                        arrn2[1] = arrn[0];
                        arrn2[2] = arrn[2];
                    } else {
                        arrn2[1] = arrn[2];
                        arrn2[2] = arrn[0];
                    }
                } else {
                    arrn2[0] = arrn[2];
                    if (arrn[0] < arrn[1]) {
                        arrn2[1] = arrn[0];
                        arrn2[2] = arrn[1];
                    } else {
                        arrn2[1] = arrn[1];
                        arrn2[2] = arrn[0];
                    }
                }
            }
            return arrn2;
        }
        throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
    }

    public static String generateKeyFingerprint(ECPoint eCPoint, com.android.org.bouncycastle.jce.spec.ECParameterSpec object) {
        ECCurve eCCurve = ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve();
        object = ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG();
        if (eCCurve != null) {
            return new Fingerprint(Arrays.concatenate(eCPoint.getEncoded(false), eCCurve.getA().getEncoded(), eCCurve.getB().getEncoded(), ((ECPoint)object).getEncoded(false))).toString();
        }
        return new Fingerprint(eCPoint.getEncoded(false)).toString();
    }

    public static AsymmetricKeyParameter generatePrivateKeyParameter(PrivateKey object) throws InvalidKeyException {
        block8 : {
            block9 : {
                if (object instanceof ECPrivateKey) {
                    Object object2;
                    ECPrivateKey eCPrivateKey = (ECPrivateKey)object;
                    object = object2 = eCPrivateKey.getParameters();
                    if (object2 == null) {
                        object = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
                    }
                    if (eCPrivateKey.getParameters() instanceof ECNamedCurveParameterSpec) {
                        object2 = ((ECNamedCurveParameterSpec)eCPrivateKey.getParameters()).getName();
                        return new ECPrivateKeyParameters(eCPrivateKey.getD(), new ECNamedDomainParameters(ECNamedCurveTable.getOID((String)object2), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed()));
                    }
                    return new ECPrivateKeyParameters(eCPrivateKey.getD(), new ECDomainParameters(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed()));
                }
                if (object instanceof java.security.interfaces.ECPrivateKey) {
                    object = (java.security.interfaces.ECPrivateKey)object;
                    com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec = EC5Util.convertSpec(object.getParams(), false);
                    return new ECPrivateKeyParameters(object.getS(), new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH(), eCParameterSpec.getSeed()));
                }
                try {
                    object = object.getEncoded();
                    if (object == null) break block8;
                }
                catch (Exception exception) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("cannot identify EC private key: ");
                    stringBuilder.append(exception.toString());
                    throw new InvalidKeyException(stringBuilder.toString());
                }
                object = BouncyCastleProvider.getPrivateKey(PrivateKeyInfo.getInstance(object));
                if (!(object instanceof java.security.interfaces.ECPrivateKey)) break block9;
                object = ECUtil.generatePrivateKeyParameter((PrivateKey)object);
                return object;
            }
            throw new InvalidKeyException("can't identify EC private key.");
        }
        object = new InvalidKeyException("no encoding for EC private key");
        throw object;
    }

    public static AsymmetricKeyParameter generatePublicKeyParameter(PublicKey object) throws InvalidKeyException {
        block6 : {
            block7 : {
                if (object instanceof com.android.org.bouncycastle.jce.interfaces.ECPublicKey) {
                    com.android.org.bouncycastle.jce.interfaces.ECPublicKey eCPublicKey = (com.android.org.bouncycastle.jce.interfaces.ECPublicKey)object;
                    object = eCPublicKey.getParameters();
                    return new ECPublicKeyParameters(eCPublicKey.getQ(), new ECDomainParameters(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed()));
                }
                if (object instanceof ECPublicKey) {
                    object = (ECPublicKey)object;
                    com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec = EC5Util.convertSpec(object.getParams(), false);
                    return new ECPublicKeyParameters(EC5Util.convertPoint(object.getParams(), object.getW(), false), new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH(), eCParameterSpec.getSeed()));
                }
                try {
                    object = object.getEncoded();
                    if (object == null) break block6;
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("cannot identify EC public key: ");
                    ((StringBuilder)object).append(exception.toString());
                    throw new InvalidKeyException(((StringBuilder)object).toString());
                }
                object = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(object));
                if (!(object instanceof ECPublicKey)) break block7;
                object = ECUtil.generatePublicKeyParameter((PublicKey)object);
                return object;
            }
            throw new InvalidKeyException("cannot identify EC public key.");
        }
        object = new InvalidKeyException("no encoding for EC public key");
        throw object;
    }

    public static String getCurveName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return ECNamedCurveTable.getName(aSN1ObjectIdentifier);
    }

    public static ECDomainParameters getDomainParameters(ProviderConfiguration object, X962Parameters aSN1Object) {
        if (aSN1Object.isNamedCurve()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(aSN1Object.getParameters());
            aSN1Object = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            object = aSN1Object == null ? (X9ECParameters)object.getAdditionalECParameters().get(aSN1ObjectIdentifier) : aSN1Object;
            object = new ECNamedDomainParameters(aSN1ObjectIdentifier, ((X9ECParameters)object).getCurve(), ((X9ECParameters)object).getG(), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH(), ((X9ECParameters)object).getSeed());
        } else if (aSN1Object.isImplicitlyCA()) {
            object = object.getEcImplicitlyCa();
            object = new ECDomainParameters(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed());
        } else {
            object = X9ECParameters.getInstance(aSN1Object.getParameters());
            object = new ECDomainParameters(((X9ECParameters)object).getCurve(), ((X9ECParameters)object).getG(), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH(), ((X9ECParameters)object).getSeed());
        }
        return object;
    }

    public static ECDomainParameters getDomainParameters(ProviderConfiguration object, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        if (eCParameterSpec instanceof ECNamedCurveParameterSpec) {
            object = (ECNamedCurveParameterSpec)eCParameterSpec;
            object = new ECNamedDomainParameters(ECUtil.getNamedCurveOid(((ECNamedCurveParameterSpec)object).getName()), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed());
        } else if (eCParameterSpec == null) {
            object = object.getEcImplicitlyCa();
            object = new ECDomainParameters(((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getCurve(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getG(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getH(), ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getSeed());
        } else {
            object = new ECDomainParameters(eCParameterSpec.getCurve(), eCParameterSpec.getG(), eCParameterSpec.getN(), eCParameterSpec.getH(), eCParameterSpec.getSeed());
        }
        return object;
    }

    public static X9ECParameters getNamedCurveByName(String string) {
        X9ECParameters x9ECParameters;
        X9ECParameters x9ECParameters2 = x9ECParameters = CustomNamedCurves.getByName(string);
        if (x9ECParameters == null) {
            x9ECParameters2 = ECNamedCurveTable.getByName(string);
        }
        return x9ECParameters2;
    }

    public static X9ECParameters getNamedCurveByOid(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        X9ECParameters x9ECParameters;
        X9ECParameters x9ECParameters2 = x9ECParameters = CustomNamedCurves.getByOID(aSN1ObjectIdentifier);
        if (x9ECParameters == null) {
            x9ECParameters2 = ECNamedCurveTable.getByOID(aSN1ObjectIdentifier);
        }
        return x9ECParameters2;
    }

    public static ASN1ObjectIdentifier getNamedCurveOid(com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        Enumeration enumeration = ECNamedCurveTable.getNames();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            X9ECParameters x9ECParameters = ECNamedCurveTable.getByName(string);
            if (!x9ECParameters.getN().equals(eCParameterSpec.getN()) || !x9ECParameters.getH().equals(eCParameterSpec.getH()) || !x9ECParameters.getCurve().equals(eCParameterSpec.getCurve()) || !x9ECParameters.getG().equals(eCParameterSpec.getG())) continue;
            return ECNamedCurveTable.getOID(string);
        }
        return null;
    }

    public static ASN1ObjectIdentifier getNamedCurveOid(String string) {
        Object object = string;
        int n = ((String)object).indexOf(32);
        string = object;
        if (n > 0) {
            string = ((String)object).substring(n + 1);
        }
        try {
            if (string.charAt(0) >= '0' && string.charAt(0) <= '2') {
                object = new ASN1ObjectIdentifier(string);
                return object;
            }
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        return ECNamedCurveTable.getOID(string);
    }

    public static int getOrderBitLength(ProviderConfiguration object, BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger == null) {
            if ((object = object.getEcImplicitlyCa()) == null) {
                return bigInteger2.bitLength();
            }
            return ((com.android.org.bouncycastle.jce.spec.ECParameterSpec)object).getN().bitLength();
        }
        return bigInteger.bitLength();
    }

    public static String privateKeyToString(String string, BigInteger object, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = Strings.lineSeparator();
        object = ECUtil.calculateQ((BigInteger)object, eCParameterSpec);
        stringBuffer.append(string);
        stringBuffer.append(" Private Key [");
        stringBuffer.append(ECUtil.generateKeyFingerprint((ECPoint)object, eCParameterSpec));
        stringBuffer.append("]");
        stringBuffer.append(string2);
        stringBuffer.append("            X: ");
        stringBuffer.append(((ECPoint)object).getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(string2);
        stringBuffer.append("            Y: ");
        stringBuffer.append(((ECPoint)object).getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(string2);
        return stringBuffer.toString();
    }

    public static String publicKeyToString(String string, ECPoint eCPoint, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        StringBuffer stringBuffer = new StringBuffer();
        String string2 = Strings.lineSeparator();
        stringBuffer.append(string);
        stringBuffer.append(" Public Key [");
        stringBuffer.append(ECUtil.generateKeyFingerprint(eCPoint, eCParameterSpec));
        stringBuffer.append("]");
        stringBuffer.append(string2);
        stringBuffer.append("            X: ");
        stringBuffer.append(eCPoint.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(string2);
        stringBuffer.append("            Y: ");
        stringBuffer.append(eCPoint.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(string2);
        return stringBuffer.toString();
    }
}

