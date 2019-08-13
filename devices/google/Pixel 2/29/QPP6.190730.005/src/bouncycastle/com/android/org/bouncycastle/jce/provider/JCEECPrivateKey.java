/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Null;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.sec.ECPrivateKeyStructure;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECParameters;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.interfaces.ECPointEncoder;
import com.android.org.bouncycastle.jce.interfaces.ECPrivateKey;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.provider.JCEECPublicKey;
import com.android.org.bouncycastle.jce.spec.ECNamedCurveSpec;
import com.android.org.bouncycastle.jce.spec.ECPrivateKeySpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;

public class JCEECPrivateKey
implements java.security.interfaces.ECPrivateKey,
ECPrivateKey,
PKCS12BagAttributeCarrier,
ECPointEncoder {
    private String algorithm = "EC";
    private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private BigInteger d;
    private ECParameterSpec ecSpec;
    private DERBitString publicKey;
    private boolean withCompression;

    protected JCEECPrivateKey() {
    }

    JCEECPrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        this.populateFromPrivKeyInfo(privateKeyInfo);
    }

    public JCEECPrivateKey(String string, ECPrivateKeyParameters eCPrivateKeyParameters) {
        this.algorithm = string;
        this.d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
    }

    public JCEECPrivateKey(String object, ECPrivateKeyParameters eCPrivateKeyParameters, JCEECPublicKey jCEECPublicKey, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        this.algorithm = object;
        this.d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            object = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(((ECDomainParameters)object).getCurve(), ((ECDomainParameters)object).getSeed()), EC5Util.convertPoint(((ECDomainParameters)object).getG()), ((ECDomainParameters)object).getN(), ((ECDomainParameters)object).getH().intValue());
        } else {
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), EC5Util.convertPoint(eCParameterSpec.getG()), eCParameterSpec.getN(), eCParameterSpec.getH().intValue());
        }
        this.publicKey = this.getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(String object, ECPrivateKeyParameters eCPrivateKeyParameters, JCEECPublicKey jCEECPublicKey, ECParameterSpec eCParameterSpec) {
        this.algorithm = object;
        this.d = eCPrivateKeyParameters.getD();
        if (eCParameterSpec == null) {
            object = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(((ECDomainParameters)object).getCurve(), ((ECDomainParameters)object).getSeed()), EC5Util.convertPoint(((ECDomainParameters)object).getG()), ((ECDomainParameters)object).getN(), ((ECDomainParameters)object).getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.publicKey = this.getPublicKeyDetails(jCEECPublicKey);
    }

    public JCEECPrivateKey(String string, JCEECPrivateKey jCEECPrivateKey) {
        this.algorithm = string;
        this.d = jCEECPrivateKey.d;
        this.ecSpec = jCEECPrivateKey.ecSpec;
        this.withCompression = jCEECPrivateKey.withCompression;
        this.attrCarrier = jCEECPrivateKey.attrCarrier;
        this.publicKey = jCEECPrivateKey.publicKey;
    }

    public JCEECPrivateKey(String string, ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = string;
        this.d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
    }

    public JCEECPrivateKey(String string, java.security.spec.ECPrivateKeySpec eCPrivateKeySpec) {
        this.algorithm = string;
        this.d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
    }

    public JCEECPrivateKey(java.security.interfaces.ECPrivateKey eCPrivateKey) {
        this.d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
    }

    private DERBitString getPublicKeyDetails(JCEECPublicKey object) {
        try {
            object = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(((JCEECPublicKey)object).getEncoded())).getPublicKeyData();
            return object;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    private void populateFromPrivKeyInfo(PrivateKeyInfo aSN1Encodable) throws IOException {
        Object object = new X962Parameters((ASN1Primitive)((PrivateKeyInfo)aSN1Encodable).getPrivateKeyAlgorithm().getParameters());
        if (((X962Parameters)object).isNamedCurve()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(((X962Parameters)object).getParameters());
            X9ECParameters x9ECParameters = ECUtil.getNamedCurveByOid(aSN1ObjectIdentifier);
            object = EC5Util.convertCurve(x9ECParameters.getCurve(), x9ECParameters.getSeed());
            this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(aSN1ObjectIdentifier), (EllipticCurve)object, EC5Util.convertPoint(x9ECParameters.getG()), x9ECParameters.getN(), x9ECParameters.getH());
        } else if (((X962Parameters)object).isImplicitlyCA()) {
            this.ecSpec = null;
        } else {
            object = X9ECParameters.getInstance(((X962Parameters)object).getParameters());
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(((X9ECParameters)object).getCurve(), ((X9ECParameters)object).getSeed()), EC5Util.convertPoint(((X9ECParameters)object).getG()), ((X9ECParameters)object).getN(), ((X9ECParameters)object).getH().intValue());
        }
        aSN1Encodable = ((PrivateKeyInfo)aSN1Encodable).parsePrivateKey();
        if (aSN1Encodable instanceof ASN1Integer) {
            this.d = ASN1Integer.getInstance(aSN1Encodable).getValue();
        } else {
            aSN1Encodable = new ECPrivateKeyStructure((ASN1Sequence)aSN1Encodable);
            this.d = ((ECPrivateKeyStructure)aSN1Encodable).getKey();
            this.publicKey = ((ECPrivateKeyStructure)aSN1Encodable).getPublicKey();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[])objectInputStream.readObject())));
        this.algorithm = (String)objectInputStream.readObject();
        this.withCompression = objectInputStream.readBoolean();
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.attrCarrier.readObject(objectInputStream);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.getEncoded());
        objectOutputStream.writeObject(this.algorithm);
        objectOutputStream.writeBoolean(this.withCompression);
        this.attrCarrier.writeObject(objectOutputStream);
    }

    com.android.org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec != null) {
            return EC5Util.convertSpec(eCParameterSpec, this.withCompression);
        }
        return BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof JCEECPrivateKey;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (JCEECPrivateKey)object;
        bl = bl2;
        if (this.getD().equals(((JCEECPrivateKey)object).getD())) {
            bl = bl2;
            if (this.engineGetSpec().equals(((JCEECPrivateKey)object).engineGetSpec())) {
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
    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    @Override
    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    @Override
    public BigInteger getD() {
        return this.d;
    }

    @Override
    public byte[] getEncoded() {
        ASN1Object aSN1Object;
        Object object = this.ecSpec;
        if (object instanceof ECNamedCurveSpec) {
            aSN1Object = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)object).getName());
            object = aSN1Object;
            if (aSN1Object == null) {
                object = new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
            }
            object = new X962Parameters((ASN1ObjectIdentifier)object);
        } else if (object == null) {
            object = new X962Parameters(DERNull.INSTANCE);
        } else {
            object = EC5Util.convertCurve(object.getCurve());
            object = new X962Parameters(new X9ECParameters((ECCurve)object, EC5Util.convertPoint((ECCurve)object, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
        }
        aSN1Object = this.publicKey != null ? new ECPrivateKeyStructure(this.getS(), this.publicKey, (ASN1Encodable)object) : new ECPrivateKeyStructure(this.getS(), (ASN1Encodable)object);
        try {
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, object.toASN1Primitive());
            PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, ((ECPrivateKeyStructure)aSN1Object).toASN1Primitive());
            object = privateKeyInfo.getEncoded("DER");
            return object;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
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
    public BigInteger getS() {
        return this.d;
    }

    public int hashCode() {
        return this.getD().hashCode() ^ this.engineGetSpec().hashCode();
    }

    @Override
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    @Override
    public void setPointFormat(String string) {
        this.withCompression = "UNCOMPRESSED".equalsIgnoreCase(string) ^ true;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("EC Private Key");
        stringBuffer.append(string);
        stringBuffer.append("             S: ");
        stringBuffer.append(this.d.toString(16));
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

