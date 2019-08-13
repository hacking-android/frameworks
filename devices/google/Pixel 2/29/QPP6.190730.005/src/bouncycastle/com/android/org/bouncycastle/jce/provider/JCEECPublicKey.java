/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.asn1.x9.X9ECPoint;
import com.android.org.bouncycastle.asn1.x9.X9IntegerConverter;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.interfaces.ECPointEncoder;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.org.bouncycastle.jce.spec.ECPublicKeySpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;

public class JCEECPublicKey
implements ECPublicKey,
com.android.org.bouncycastle.jce.interfaces.ECPublicKey,
ECPointEncoder {
    private String algorithm = "EC";
    private ECParameterSpec ecSpec;
    private ECPoint q;
    private boolean withCompression;

    JCEECPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.populateFromPubKeyInfo(subjectPublicKeyInfo);
    }

    public JCEECPublicKey(String string, ECPublicKeyParameters eCPublicKeyParameters) {
        this.algorithm = string;
        this.q = eCPublicKeyParameters.getQ();
        this.ecSpec = null;
    }

    public JCEECPublicKey(String string, ECPublicKeyParameters eCPublicKeyParameters, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        ECDomainParameters eCDomainParameters = eCPublicKeyParameters.getParameters();
        this.algorithm = string;
        this.q = eCPublicKeyParameters.getQ();
        this.ecSpec = eCParameterSpec == null ? this.createSpec(EC5Util.convertCurve(eCDomainParameters.getCurve(), eCDomainParameters.getSeed()), eCDomainParameters) : EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
    }

    public JCEECPublicKey(String string, ECPublicKeyParameters eCPublicKeyParameters, ECParameterSpec eCParameterSpec) {
        ECDomainParameters eCDomainParameters = eCPublicKeyParameters.getParameters();
        this.algorithm = string;
        this.q = eCPublicKeyParameters.getQ();
        this.ecSpec = eCParameterSpec == null ? this.createSpec(EC5Util.convertCurve(eCDomainParameters.getCurve(), eCDomainParameters.getSeed()), eCDomainParameters) : eCParameterSpec;
    }

    public JCEECPublicKey(String string, JCEECPublicKey jCEECPublicKey) {
        this.algorithm = string;
        this.q = jCEECPublicKey.q;
        this.ecSpec = jCEECPublicKey.ecSpec;
        this.withCompression = jCEECPublicKey.withCompression;
    }

    public JCEECPublicKey(String string, ECPublicKeySpec eCPublicKeySpec) {
        this.algorithm = string;
        this.q = eCPublicKeySpec.getQ();
        if (eCPublicKeySpec.getParams() != null) {
            this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed()), eCPublicKeySpec.getParams());
        } else {
            if (this.q.getCurve() == null) {
                this.q = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve().createPoint(this.q.getAffineXCoord().toBigInteger(), this.q.getAffineYCoord().toBigInteger(), false);
            }
            this.ecSpec = null;
        }
    }

    public JCEECPublicKey(String string, java.security.spec.ECPublicKeySpec eCPublicKeySpec) {
        this.algorithm = string;
        this.ecSpec = eCPublicKeySpec.getParams();
        this.q = EC5Util.convertPoint(this.ecSpec, eCPublicKeySpec.getW(), false);
    }

    public JCEECPublicKey(ECPublicKey eCPublicKey) {
        this.algorithm = eCPublicKey.getAlgorithm();
        this.ecSpec = eCPublicKey.getParams();
        this.q = EC5Util.convertPoint(this.ecSpec, eCPublicKey.getW(), false);
    }

    private ECParameterSpec createSpec(EllipticCurve ellipticCurve, ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(ellipticCurve, EC5Util.convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    private void extractBytes(byte[] arrby, int n, BigInteger arrby2) {
        byte[] arrby3;
        arrby2 = arrby3 = arrby2.toByteArray();
        if (arrby3.length < 32) {
            arrby2 = new byte[32];
            System.arraycopy((byte[])arrby3, (int)0, (byte[])arrby2, (int)(arrby2.length - arrby3.length), (int)arrby3.length);
        }
        for (int i = 0; i != 32; ++i) {
            arrby[n + i] = arrby2[arrby2.length - 1 - i];
        }
    }

    private void populateFromPubKeyInfo(SubjectPublicKeyInfo object) {
        Object object2;
        block10 : {
            byte[] arrby;
            Object object3;
            block11 : {
                object2 = new X962Parameters((ASN1Primitive)((SubjectPublicKeyInfo)object).getAlgorithmId().getParameters());
                if (((X962Parameters)object2).isNamedCurve()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)((X962Parameters)object2).getParameters();
                    arrby = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
                    object2 = arrby.getCurve();
                    object3 = EC5Util.convertCurve((ECCurve)object2, arrby.getSeed());
                    this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(aSN1ObjectIdentifier), (EllipticCurve)object3, EC5Util.convertPoint(arrby.getG()), arrby.getN(), arrby.getH());
                } else if (((X962Parameters)object2).isImplicitlyCA()) {
                    this.ecSpec = null;
                    object2 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve();
                } else {
                    object3 = X9ECParameters.getInstance(((X962Parameters)object2).getParameters());
                    object2 = ((X9ECParameters)object3).getCurve();
                    this.ecSpec = new ECParameterSpec(EC5Util.convertCurve((ECCurve)object2, ((X9ECParameters)object3).getSeed()), EC5Util.convertPoint(((X9ECParameters)object3).getG()), ((X9ECParameters)object3).getN(), ((X9ECParameters)object3).getH().intValue());
                }
                arrby = ((SubjectPublicKeyInfo)object).getPublicKeyData().getBytes();
                object = object3 = new DEROctetString(arrby);
                if (arrby[0] != 4) break block10;
                object = object3;
                if (arrby[1] != arrby.length - 2) break block10;
                if (arrby[2] == 2) break block11;
                object = object3;
                if (arrby[2] != 3) break block10;
            }
            object = object3;
            if (new X9IntegerConverter().getByteLength((ECCurve)object2) >= arrby.length - 3) {
                try {
                    object = (ASN1OctetString)ASN1Primitive.fromByteArray(arrby);
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("error recovering public key");
                }
            }
        }
        this.q = new X9ECPoint((ECCurve)object2, (ASN1OctetString)object).getPoint();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[])objectInputStream.readObject())));
        this.algorithm = (String)objectInputStream.readObject();
        this.withCompression = objectInputStream.readBoolean();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.getEncoded());
        objectOutputStream.writeObject(this.algorithm);
        objectOutputStream.writeBoolean(this.withCompression);
    }

    public ECPoint engineGetQ() {
        return this.q;
    }

    com.android.org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec != null) {
            return EC5Util.convertSpec(eCParameterSpec, this.withCompression);
        }
        return BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof JCEECPublicKey;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (JCEECPublicKey)object;
        bl = bl2;
        if (this.engineGetQ().equals(((JCEECPublicKey)object).engineGetQ())) {
            bl = bl2;
            if (this.engineGetSpec().equals(((JCEECPublicKey)object).engineGetSpec())) {
                bl = true;
            }
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return this.algorithm;
    }

    @Override
    public byte[] getEncoded() {
        ASN1Primitive aSN1Primitive;
        Object object = this.ecSpec;
        if (object instanceof ECNamedCurveSpec) {
            aSN1Primitive = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)object).getName());
            object = aSN1Primitive;
            if (aSN1Primitive == null) {
                object = new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
            }
            object = new X962Parameters((ASN1ObjectIdentifier)object);
        } else if (object == null) {
            object = new X962Parameters(DERNull.INSTANCE);
        } else {
            object = EC5Util.convertCurve(((ECParameterSpec)object).getCurve());
            object = new X962Parameters(new X9ECParameters((ECCurve)object, EC5Util.convertPoint((ECCurve)object, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
        }
        aSN1Primitive = (ASN1OctetString)new X9ECPoint(this.engineGetQ().getCurve().createPoint(this.getQ().getAffineXCoord().toBigInteger(), this.getQ().getAffineYCoord().toBigInteger(), this.withCompression)).toASN1Primitive();
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, (ASN1Encodable)object), ((ASN1OctetString)aSN1Primitive).getOctets()));
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public com.android.org.bouncycastle.jce.spec.ECParameterSpec getParameters() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec == null) {
            return null;
        }
        return EC5Util.convertSpec(eCParameterSpec, this.withCompression);
    }

    @Override
    public ECParameterSpec getParams() {
        return this.ecSpec;
    }

    @Override
    public ECPoint getQ() {
        if (this.ecSpec == null) {
            return this.q.getDetachedPoint();
        }
        return this.q;
    }

    @Override
    public java.security.spec.ECPoint getW() {
        return EC5Util.convertPoint(this.q);
    }

    public int hashCode() {
        return this.engineGetQ().hashCode() ^ this.engineGetSpec().hashCode();
    }

    @Override
    public void setPointFormat(String string) {
        this.withCompression = "UNCOMPRESSED".equalsIgnoreCase(string) ^ true;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("EC Public Key");
        stringBuffer.append(string);
        stringBuffer.append("            X: ");
        stringBuffer.append(this.q.getAffineXCoord().toBigInteger().toString(16));
        stringBuffer.append(string);
        stringBuffer.append("            Y: ");
        stringBuffer.append(this.q.getAffineYCoord().toBigInteger().toString(16));
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

