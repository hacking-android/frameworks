/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.BCECPublicKey;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.interfaces.ECPointEncoder;
import com.android.org.bouncycastle.jce.interfaces.ECPrivateKey;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECPrivateKeySpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;

public class BCECPrivateKey
implements java.security.interfaces.ECPrivateKey,
ECPrivateKey,
PKCS12BagAttributeCarrier,
ECPointEncoder {
    static final long serialVersionUID = 994553197664784084L;
    private String algorithm = "EC";
    private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private transient ProviderConfiguration configuration;
    private transient BigInteger d;
    private transient ECParameterSpec ecSpec;
    private transient DERBitString publicKey;
    private boolean withCompression;

    protected BCECPrivateKey() {
    }

    BCECPrivateKey(String string, PrivateKeyInfo privateKeyInfo, ProviderConfiguration providerConfiguration) throws IOException {
        this.algorithm = string;
        this.configuration = providerConfiguration;
        this.populateFromPrivKeyInfo(privateKeyInfo);
    }

    public BCECPrivateKey(String object, ECPrivateKeyParameters eCPrivateKeyParameters, BCECPublicKey bCECPublicKey, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        this.algorithm = object;
        this.d = eCPrivateKeyParameters.getD();
        this.configuration = providerConfiguration;
        if (eCParameterSpec == null) {
            object = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(((ECDomainParameters)object).getCurve(), ((ECDomainParameters)object).getSeed()), EC5Util.convertPoint(((ECDomainParameters)object).getG()), ((ECDomainParameters)object).getN(), ((ECDomainParameters)object).getH().intValue());
        } else {
            this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        }
        try {
            this.publicKey = this.getPublicKeyDetails(bCECPublicKey);
        }
        catch (Exception exception) {
            this.publicKey = null;
        }
    }

    public BCECPrivateKey(String object, ECPrivateKeyParameters eCPrivateKeyParameters, BCECPublicKey bCECPublicKey, ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        this.algorithm = object;
        this.d = eCPrivateKeyParameters.getD();
        this.configuration = providerConfiguration;
        if (eCParameterSpec == null) {
            object = eCPrivateKeyParameters.getParameters();
            this.ecSpec = new ECParameterSpec(EC5Util.convertCurve(((ECDomainParameters)object).getCurve(), ((ECDomainParameters)object).getSeed()), EC5Util.convertPoint(((ECDomainParameters)object).getG()), ((ECDomainParameters)object).getN(), ((ECDomainParameters)object).getH().intValue());
        } else {
            this.ecSpec = eCParameterSpec;
        }
        this.publicKey = this.getPublicKeyDetails(bCECPublicKey);
    }

    public BCECPrivateKey(String string, ECPrivateKeyParameters eCPrivateKeyParameters, ProviderConfiguration providerConfiguration) {
        this.algorithm = string;
        this.d = eCPrivateKeyParameters.getD();
        this.ecSpec = null;
        this.configuration = providerConfiguration;
    }

    public BCECPrivateKey(String string, BCECPrivateKey bCECPrivateKey) {
        this.algorithm = string;
        this.d = bCECPrivateKey.d;
        this.ecSpec = bCECPrivateKey.ecSpec;
        this.withCompression = bCECPrivateKey.withCompression;
        this.attrCarrier = bCECPrivateKey.attrCarrier;
        this.publicKey = bCECPrivateKey.publicKey;
        this.configuration = bCECPrivateKey.configuration;
    }

    public BCECPrivateKey(String string, ECPrivateKeySpec eCPrivateKeySpec, ProviderConfiguration providerConfiguration) {
        this.algorithm = string;
        this.d = eCPrivateKeySpec.getD();
        this.ecSpec = eCPrivateKeySpec.getParams() != null ? EC5Util.convertSpec(EC5Util.convertCurve(eCPrivateKeySpec.getParams().getCurve(), eCPrivateKeySpec.getParams().getSeed()), eCPrivateKeySpec.getParams()) : null;
        this.configuration = providerConfiguration;
    }

    public BCECPrivateKey(String string, java.security.spec.ECPrivateKeySpec eCPrivateKeySpec, ProviderConfiguration providerConfiguration) {
        this.algorithm = string;
        this.d = eCPrivateKeySpec.getS();
        this.ecSpec = eCPrivateKeySpec.getParams();
        this.configuration = providerConfiguration;
    }

    public BCECPrivateKey(java.security.interfaces.ECPrivateKey eCPrivateKey, ProviderConfiguration providerConfiguration) {
        this.d = eCPrivateKey.getS();
        this.algorithm = eCPrivateKey.getAlgorithm();
        this.ecSpec = eCPrivateKey.getParams();
        this.configuration = providerConfiguration;
    }

    private ECPoint calculateQ(com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec) {
        return eCParameterSpec.getG().multiply(this.d).normalize();
    }

    private DERBitString getPublicKeyDetails(BCECPublicKey object) {
        try {
            object = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(((BCECPublicKey)object).getEncoded())).getPublicKeyData();
            return object;
        }
        catch (IOException iOException) {
            return null;
        }
    }

    private void populateFromPrivKeyInfo(PrivateKeyInfo aSN1Encodable) throws IOException {
        X962Parameters x962Parameters = X962Parameters.getInstance(((PrivateKeyInfo)aSN1Encodable).getPrivateKeyAlgorithm().getParameters());
        this.ecSpec = EC5Util.convertToSpec(x962Parameters, EC5Util.getCurve(this.configuration, x962Parameters));
        if ((aSN1Encodable = ((PrivateKeyInfo)aSN1Encodable).parsePrivateKey()) instanceof ASN1Integer) {
            this.d = ASN1Integer.getInstance(aSN1Encodable).getValue();
        } else {
            aSN1Encodable = com.android.org.bouncycastle.asn1.sec.ECPrivateKey.getInstance(aSN1Encodable);
            this.d = ((com.android.org.bouncycastle.asn1.sec.ECPrivateKey)aSN1Encodable).getKey();
            this.publicKey = ((com.android.org.bouncycastle.asn1.sec.ECPrivateKey)aSN1Encodable).getPublicKey();
        }
    }

    private void readObject(ObjectInputStream arrby) throws IOException, ClassNotFoundException {
        arrby.defaultReadObject();
        arrby = (byte[])arrby.readObject();
        this.configuration = BouncyCastleProvider.CONFIGURATION;
        this.populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(arrby)));
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.getEncoded());
    }

    com.android.org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec != null) {
            return EC5Util.convertSpec(eCParameterSpec, this.withCompression);
        }
        return this.configuration.getEcImplicitlyCa();
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BCECPrivateKey;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (BCECPrivateKey)object;
        bl = bl2;
        if (this.getD().equals(((BCECPrivateKey)object).getD())) {
            bl = bl2;
            if (this.engineGetSpec().equals(((BCECPrivateKey)object).engineGetSpec())) {
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
        X962Parameters x962Parameters = ECUtils.getDomainParametersFromName(this.ecSpec, this.withCompression);
        Object object = this.ecSpec;
        int n = object == null ? ECUtil.getOrderBitLength(this.configuration, null, this.getS()) : ECUtil.getOrderBitLength(this.configuration, object.getOrder(), this.getS());
        object = this.publicKey != null ? new com.android.org.bouncycastle.asn1.sec.ECPrivateKey(n, this.getS(), this.publicKey, x962Parameters) : new com.android.org.bouncycastle.asn1.sec.ECPrivateKey(n, this.getS(), (ASN1Encodable)x962Parameters);
        try {
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, x962Parameters);
            PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, (ASN1Encodable)object);
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
        return ECUtil.privateKeyToString("EC", this.d, this.engineGetSpec());
    }
}

