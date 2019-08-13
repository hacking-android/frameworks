/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.DSAParameter;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPrivateKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPrivateKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPrivateKeySpec;
import java.util.Enumeration;

public class BCDSAPrivateKey
implements DSAPrivateKey,
PKCS12BagAttributeCarrier {
    private static final long serialVersionUID = -4677259546958385734L;
    private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    private transient DSAParams dsaSpec;
    private BigInteger x;

    protected BCDSAPrivateKey() {
    }

    public BCDSAPrivateKey(PrivateKeyInfo privateKeyInfo) throws IOException {
        DSAParameter dSAParameter = DSAParameter.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
        this.x = ((ASN1Integer)privateKeyInfo.parsePrivateKey()).getValue();
        this.dsaSpec = new DSAParameterSpec(dSAParameter.getP(), dSAParameter.getQ(), dSAParameter.getG());
    }

    BCDSAPrivateKey(DSAPrivateKeyParameters dSAPrivateKeyParameters) {
        this.x = dSAPrivateKeyParameters.getX();
        this.dsaSpec = new DSAParameterSpec(dSAPrivateKeyParameters.getParameters().getP(), dSAPrivateKeyParameters.getParameters().getQ(), dSAPrivateKeyParameters.getParameters().getG());
    }

    BCDSAPrivateKey(DSAPrivateKey dSAPrivateKey) {
        this.x = dSAPrivateKey.getX();
        this.dsaSpec = dSAPrivateKey.getParams();
    }

    BCDSAPrivateKey(DSAPrivateKeySpec dSAPrivateKeySpec) {
        this.x = dSAPrivateKeySpec.getX();
        this.dsaSpec = new DSAParameterSpec(dSAPrivateKeySpec.getP(), dSAPrivateKeySpec.getQ(), dSAPrivateKeySpec.getG());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.dsaSpec = new DSAParameterSpec((BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject());
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.dsaSpec.getP());
        objectOutputStream.writeObject(this.dsaSpec.getQ());
        objectOutputStream.writeObject(this.dsaSpec.getG());
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof DSAPrivateKey;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (DSAPrivateKey)object;
            if (!this.getX().equals(object.getX()) || !this.getParams().getG().equals(object.getParams().getG()) || !this.getParams().getP().equals(object.getParams().getP()) || !this.getParams().getQ().equals(object.getParams().getQ())) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return "DSA";
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
    public byte[] getEncoded() {
        return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(this.dsaSpec.getP(), this.dsaSpec.getQ(), this.dsaSpec.getG()).toASN1Primitive()), new ASN1Integer(this.getX()));
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    @Override
    public DSAParams getParams() {
        return this.dsaSpec;
    }

    @Override
    public BigInteger getX() {
        return this.x;
    }

    public int hashCode() {
        return this.getX().hashCode() ^ this.getParams().getG().hashCode() ^ this.getParams().getP().hashCode() ^ this.getParams().getQ().hashCode();
    }

    @Override
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        BigInteger bigInteger = this.getParams().getG().modPow(this.x, this.getParams().getP());
        stringBuffer.append("DSA Private Key [");
        stringBuffer.append(DSAUtil.generateKeyFingerprint(bigInteger, this.getParams()));
        stringBuffer.append("]");
        stringBuffer.append(string);
        stringBuffer.append("            Y: ");
        stringBuffer.append(bigInteger.toString(16));
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

