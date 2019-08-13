/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.crypto.util;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import com.android.org.bouncycastle.asn1.sec.ECPrivateKey;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.DSAParameter;
import com.android.org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x9.ECNamedCurveTable;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.ec.CustomNamedCurves;
import com.android.org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECNamedDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;

public class PrivateKeyFactory {
    public static AsymmetricKeyParameter createKey(PrivateKeyInfo object) throws IOException {
        Object object2 = ((PrivateKeyInfo)object).getPrivateKeyAlgorithm();
        Object object3 = ((AlgorithmIdentifier)object2).getAlgorithm();
        if (!(((ASN1Primitive)object3).equals(PKCSObjectIdentifiers.rsaEncryption) || ((ASN1Primitive)object3).equals(PKCSObjectIdentifiers.id_RSASSA_PSS) || ((ASN1Primitive)object3).equals(X509ObjectIdentifiers.id_ea_rsa))) {
            if (((ASN1Primitive)object3).equals(PKCSObjectIdentifiers.dhKeyAgreement)) {
                object3 = DHParameter.getInstance(((AlgorithmIdentifier)object2).getParameters());
                object = (ASN1Integer)((PrivateKeyInfo)object).parsePrivateKey();
                object2 = ((DHParameter)object3).getL();
                int n = object2 == null ? 0 : ((BigInteger)object2).intValue();
                object3 = new DHParameters(((DHParameter)object3).getP(), ((DHParameter)object3).getG(), null, n);
                return new DHPrivateKeyParameters(((ASN1Integer)object).getValue(), (DHParameters)object3);
            }
            if (((ASN1Primitive)object3).equals(X9ObjectIdentifiers.id_dsa)) {
                object3 = (ASN1Integer)((PrivateKeyInfo)object).parsePrivateKey();
                object2 = ((AlgorithmIdentifier)object2).getParameters();
                object = null;
                if (object2 != null) {
                    object = DSAParameter.getInstance(object2.toASN1Primitive());
                    object = new DSAParameters(((DSAParameter)object).getP(), ((DSAParameter)object).getQ(), ((DSAParameter)object).getG());
                }
                return new DSAPrivateKeyParameters(((ASN1Integer)object3).getValue(), (DSAParameters)object);
            }
            if (((ASN1Primitive)object3).equals(X9ObjectIdentifiers.id_ecPublicKey)) {
                object3 = new X962Parameters((ASN1Primitive)((AlgorithmIdentifier)object2).getParameters());
                if (((X962Parameters)object3).isNamedCurve()) {
                    object2 = (ASN1ObjectIdentifier)((X962Parameters)object3).getParameters();
                    object3 = CustomNamedCurves.getByOID((ASN1ObjectIdentifier)object2);
                    if (object3 == null) {
                        object3 = ECNamedCurveTable.getByOID((ASN1ObjectIdentifier)object2);
                    }
                    object3 = new ECNamedDomainParameters((ASN1ObjectIdentifier)object2, ((X9ECParameters)object3).getCurve(), ((X9ECParameters)object3).getG(), ((X9ECParameters)object3).getN(), ((X9ECParameters)object3).getH(), ((X9ECParameters)object3).getSeed());
                } else {
                    object3 = X9ECParameters.getInstance(((X962Parameters)object3).getParameters());
                    object3 = new ECDomainParameters(((X9ECParameters)object3).getCurve(), ((X9ECParameters)object3).getG(), ((X9ECParameters)object3).getN(), ((X9ECParameters)object3).getH(), ((X9ECParameters)object3).getSeed());
                }
                return new ECPrivateKeyParameters(ECPrivateKey.getInstance(((PrivateKeyInfo)object).parsePrivateKey()).getKey(), (ECDomainParameters)object3);
            }
            throw new RuntimeException("algorithm identifier in private key not recognised");
        }
        object = RSAPrivateKey.getInstance(((PrivateKeyInfo)object).parsePrivateKey());
        return new RSAPrivateCrtKeyParameters(((RSAPrivateKey)object).getModulus(), ((RSAPrivateKey)object).getPublicExponent(), ((RSAPrivateKey)object).getPrivateExponent(), ((RSAPrivateKey)object).getPrime1(), ((RSAPrivateKey)object).getPrime2(), ((RSAPrivateKey)object).getExponent1(), ((RSAPrivateKey)object).getExponent2(), ((RSAPrivateKey)object).getCoefficient());
    }

    public static AsymmetricKeyParameter createKey(InputStream inputStream) throws IOException {
        return PrivateKeyFactory.createKey(PrivateKeyInfo.getInstance(new ASN1InputStream(inputStream).readObject()));
    }

    public static AsymmetricKeyParameter createKey(byte[] arrby) throws IOException {
        return PrivateKeyFactory.createKey(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(arrby)));
    }

    private static byte[] getRawKey(PrivateKeyInfo arrby, int n) throws IOException {
        if (n == (arrby = ASN1OctetString.getInstance(arrby.parsePrivateKey()).getOctets()).length) {
            return arrby;
        }
        throw new RuntimeException("private key encoding has incorrect length");
    }
}

