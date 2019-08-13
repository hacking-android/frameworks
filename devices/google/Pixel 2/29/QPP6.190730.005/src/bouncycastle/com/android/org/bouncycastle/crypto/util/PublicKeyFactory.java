/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.util;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.RSAPublicKey;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.DSAParameter;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x9.DHPublicKey;
import com.android.org.bouncycastle.asn1.x9.DomainParameters;
import com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.org.bouncycastle.asn1.x9.ValidationParams;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.asn1.x9.X9ECPoint;
import com.android.org.bouncycastle.asn1.x9.X9IntegerConverter;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHValidationParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECNamedDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class PublicKeyFactory {
    private static Map converters = new HashMap();

    static {
        converters.put(PKCSObjectIdentifiers.rsaEncryption, new RSAConverter());
        converters.put(PKCSObjectIdentifiers.id_RSASSA_PSS, new RSAConverter());
        converters.put(X509ObjectIdentifiers.id_ea_rsa, new RSAConverter());
        converters.put(X9ObjectIdentifiers.dhpublicnumber, new DHPublicNumberConverter());
        converters.put(PKCSObjectIdentifiers.dhKeyAgreement, new DHAgreementConverter());
        converters.put(X9ObjectIdentifiers.id_dsa, new DSAConverter());
        converters.put(OIWObjectIdentifiers.dsaWithSHA1, new DSAConverter());
        converters.put(X9ObjectIdentifiers.id_ecPublicKey, new ECConverter());
    }

    public static AsymmetricKeyParameter createKey(SubjectPublicKeyInfo subjectPublicKeyInfo) throws IOException {
        return PublicKeyFactory.createKey(subjectPublicKeyInfo, null);
    }

    public static AsymmetricKeyParameter createKey(SubjectPublicKeyInfo object, Object object2) throws IOException {
        AlgorithmIdentifier algorithmIdentifier = ((SubjectPublicKeyInfo)object).getAlgorithm();
        SubjectPublicKeyInfoConverter subjectPublicKeyInfoConverter = (SubjectPublicKeyInfoConverter)converters.get(algorithmIdentifier.getAlgorithm());
        if (subjectPublicKeyInfoConverter != null) {
            return subjectPublicKeyInfoConverter.getPublicKeyParameters((SubjectPublicKeyInfo)object, object2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("algorithm identifier in public key not recognised: ");
        ((StringBuilder)object).append(algorithmIdentifier.getAlgorithm());
        throw new IOException(((StringBuilder)object).toString());
    }

    public static AsymmetricKeyParameter createKey(InputStream inputStream) throws IOException {
        return PublicKeyFactory.createKey(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(inputStream).readObject()));
    }

    public static AsymmetricKeyParameter createKey(byte[] arrby) throws IOException {
        return PublicKeyFactory.createKey(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(arrby)));
    }

    private static byte[] getRawKey(SubjectPublicKeyInfo arrby, Object object, int n) {
        if (n == (arrby = arrby.getPublicKeyData().getOctets()).length) {
            return arrby;
        }
        throw new RuntimeException("public key encoding has incorrect length");
    }

    private static class DHAgreementConverter
    extends SubjectPublicKeyInfoConverter {
        private DHAgreementConverter() {
        }

        @Override
        AsymmetricKeyParameter getPublicKeyParameters(SubjectPublicKeyInfo aSN1Object, Object object) throws IOException {
            object = DHParameter.getInstance(((SubjectPublicKeyInfo)aSN1Object).getAlgorithm().getParameters());
            aSN1Object = (ASN1Integer)((SubjectPublicKeyInfo)aSN1Object).parsePublicKey();
            BigInteger bigInteger = ((DHParameter)object).getL();
            int n = bigInteger == null ? 0 : bigInteger.intValue();
            object = new DHParameters(((DHParameter)object).getP(), ((DHParameter)object).getG(), null, n);
            return new DHPublicKeyParameters(((ASN1Integer)aSN1Object).getValue(), (DHParameters)object);
        }
    }

    private static class DHPublicNumberConverter
    extends SubjectPublicKeyInfoConverter {
        private DHPublicNumberConverter() {
        }

        @Override
        AsymmetricKeyParameter getPublicKeyParameters(SubjectPublicKeyInfo object, Object object2) throws IOException {
            BigInteger bigInteger = DHPublicKey.getInstance(((SubjectPublicKeyInfo)object).parsePublicKey()).getY();
            object2 = DomainParameters.getInstance(((SubjectPublicKeyInfo)object).getAlgorithm().getParameters());
            BigInteger bigInteger2 = ((DomainParameters)object2).getP();
            BigInteger bigInteger3 = ((DomainParameters)object2).getG();
            BigInteger bigInteger4 = ((DomainParameters)object2).getQ();
            object = ((DomainParameters)object2).getJ() != null ? ((DomainParameters)object2).getJ() : null;
            object2 = ((DomainParameters)object2).getValidationParams();
            object2 = object2 != null ? new DHValidationParameters(((ValidationParams)object2).getSeed(), ((ValidationParams)object2).getPgenCounter().intValue()) : null;
            return new DHPublicKeyParameters(bigInteger, new DHParameters(bigInteger2, bigInteger3, bigInteger4, (BigInteger)object, (DHValidationParameters)object2));
        }
    }

    private static class DSAConverter
    extends SubjectPublicKeyInfoConverter {
        private DSAConverter() {
        }

        @Override
        AsymmetricKeyParameter getPublicKeyParameters(SubjectPublicKeyInfo object, Object object2) throws IOException {
            object2 = (ASN1Integer)((SubjectPublicKeyInfo)object).parsePublicKey();
            ASN1Encodable aSN1Encodable = ((SubjectPublicKeyInfo)object).getAlgorithm().getParameters();
            object = null;
            if (aSN1Encodable != null) {
                object = DSAParameter.getInstance(aSN1Encodable.toASN1Primitive());
                object = new DSAParameters(((DSAParameter)object).getP(), ((DSAParameter)object).getQ(), ((DSAParameter)object).getG());
            }
            return new DSAPublicKeyParameters(((ASN1Integer)object2).getValue(), (DSAParameters)object);
        }
    }

    private static class ECConverter
    extends SubjectPublicKeyInfoConverter {
        private ECConverter() {
        }

        @Override
        AsymmetricKeyParameter getPublicKeyParameters(SubjectPublicKeyInfo aSN1Object, Object object) {
            block11 : {
                byte[] arrby;
                ASN1Object aSN1Object2;
                block12 : {
                    aSN1Object2 = X962Parameters.getInstance(aSN1Object.getAlgorithm().getParameters());
                    if (((X962Parameters)aSN1Object2).isNamedCurve()) {
                        object = CustomNamedCurves.getByOID((ASN1ObjectIdentifier)(aSN1Object2 = (ASN1ObjectIdentifier)((X962Parameters)aSN1Object2).getParameters()));
                        if (object == null) {
                            object = ECNamedCurveTable.getByOID((ASN1ObjectIdentifier)aSN1Object2);
                        }
                        object = new ECNamedDomainParameters((ASN1ObjectIdentifier)aSN1Object2, ((X9ECParameters)object).getCurve(), ((X9ECParameters)object).getG(), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH(), ((X9ECParameters)object).getSeed());
                    } else if (((X962Parameters)aSN1Object2).isImplicitlyCA()) {
                        object = (ECDomainParameters)object;
                    } else {
                        object = X9ECParameters.getInstance(((X962Parameters)aSN1Object2).getParameters());
                        object = new ECDomainParameters(((X9ECParameters)object).getCurve(), ((X9ECParameters)object).getG(), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH(), ((X9ECParameters)object).getSeed());
                    }
                    arrby = aSN1Object.getPublicKeyData().getBytes();
                    aSN1Object2 = new DEROctetString(arrby);
                    aSN1Object = aSN1Object2;
                    if (arrby[0] != 4) break block11;
                    aSN1Object = aSN1Object2;
                    if (arrby[1] != arrby.length - 2) break block11;
                    if (arrby[2] == 2) break block12;
                    aSN1Object = aSN1Object2;
                    if (arrby[2] != 3) break block11;
                }
                aSN1Object = aSN1Object2;
                if (new X9IntegerConverter().getByteLength(((ECDomainParameters)object).getCurve()) >= arrby.length - 3) {
                    try {
                        aSN1Object = (ASN1OctetString)ASN1Primitive.fromByteArray(arrby);
                    }
                    catch (IOException iOException) {
                        throw new IllegalArgumentException("error recovering public key");
                    }
                }
            }
            return new ECPublicKeyParameters(new X9ECPoint(((ECDomainParameters)object).getCurve(), (ASN1OctetString)aSN1Object).getPoint(), (ECDomainParameters)object);
        }
    }

    private static class RSAConverter
    extends SubjectPublicKeyInfoConverter {
        private RSAConverter() {
        }

        @Override
        AsymmetricKeyParameter getPublicKeyParameters(SubjectPublicKeyInfo aSN1Object, Object object) throws IOException {
            aSN1Object = RSAPublicKey.getInstance(((SubjectPublicKeyInfo)aSN1Object).parsePublicKey());
            return new RSAKeyParameters(false, ((RSAPublicKey)aSN1Object).getModulus(), ((RSAPublicKey)aSN1Object).getPublicExponent());
        }
    }

    private static abstract class SubjectPublicKeyInfoConverter {
        private SubjectPublicKeyInfoConverter() {
        }

        abstract AsymmetricKeyParameter getPublicKeyParameters(SubjectPublicKeyInfo var1, Object var2) throws IOException;
    }

}

