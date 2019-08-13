/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.RSAPrivateKey;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.RSAPrivateKeySpec;
import java.util.Enumeration;

public class BCRSAPrivateKey
implements java.security.interfaces.RSAPrivateKey,
PKCS12BagAttributeCarrier {
    private static BigInteger ZERO = BigInteger.valueOf(0L);
    static final long serialVersionUID = 5110188922551353628L;
    private transient PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
    protected BigInteger modulus;
    protected BigInteger privateExponent;

    protected BCRSAPrivateKey() {
    }

    BCRSAPrivateKey(RSAPrivateKey rSAPrivateKey) {
        this.modulus = rSAPrivateKey.getModulus();
        this.privateExponent = rSAPrivateKey.getPrivateExponent();
    }

    BCRSAPrivateKey(RSAKeyParameters rSAKeyParameters) {
        this.modulus = rSAKeyParameters.getModulus();
        this.privateExponent = rSAKeyParameters.getExponent();
    }

    BCRSAPrivateKey(java.security.interfaces.RSAPrivateKey rSAPrivateKey) {
        this.modulus = rSAPrivateKey.getModulus();
        this.privateExponent = rSAPrivateKey.getPrivateExponent();
    }

    BCRSAPrivateKey(RSAPrivateKeySpec rSAPrivateKeySpec) {
        this.modulus = rSAPrivateKeySpec.getModulus();
        this.privateExponent = rSAPrivateKeySpec.getPrivateExponent();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    public boolean equals(Object object) {
        if (!(object instanceof java.security.interfaces.RSAPrivateKey)) {
            return false;
        }
        boolean bl = true;
        if (object == this) {
            return true;
        }
        object = (java.security.interfaces.RSAPrivateKey)object;
        if (!this.getModulus().equals(object.getModulus()) || !this.getPrivateExponent().equals(object.getPrivateExponent())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return "RSA";
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
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
        BigInteger bigInteger = this.getModulus();
        BigInteger bigInteger2 = ZERO;
        BigInteger bigInteger3 = this.getPrivateExponent();
        BigInteger bigInteger4 = ZERO;
        return KeyUtil.getEncodedPrivateKeyInfo(algorithmIdentifier, new RSAPrivateKey(bigInteger, bigInteger2, bigInteger3, bigInteger4, bigInteger4, bigInteger4, bigInteger4, bigInteger4));
    }

    @Override
    public String getFormat() {
        return "PKCS#8";
    }

    @Override
    public BigInteger getModulus() {
        return this.modulus;
    }

    @Override
    public BigInteger getPrivateExponent() {
        return this.privateExponent;
    }

    public int hashCode() {
        return this.getModulus().hashCode() ^ this.getPrivateExponent().hashCode();
    }

    @Override
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("RSA Private Key [");
        stringBuffer.append(RSAUtil.generateKeyFingerprint(this.getModulus()));
        stringBuffer.append("],[]");
        stringBuffer.append(string);
        stringBuffer.append("            modulus: ");
        stringBuffer.append(this.getModulus().toString(16));
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

