/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.RSAPublicKey;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.crypto.params.RSAKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.RSAPublicKeySpec;

public class BCRSAPublicKey
implements java.security.interfaces.RSAPublicKey {
    private static final AlgorithmIdentifier DEFAULT_ALGORITHM_IDENTIFIER = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
    static final long serialVersionUID = 2675817738516720772L;
    private transient AlgorithmIdentifier algorithmIdentifier;
    private BigInteger modulus;
    private BigInteger publicExponent;

    BCRSAPublicKey(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        this.populateFromPublicKeyInfo(subjectPublicKeyInfo);
    }

    BCRSAPublicKey(RSAKeyParameters rSAKeyParameters) {
        this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
        this.modulus = rSAKeyParameters.getModulus();
        this.publicExponent = rSAKeyParameters.getExponent();
    }

    BCRSAPublicKey(java.security.interfaces.RSAPublicKey rSAPublicKey) {
        this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
        this.modulus = rSAPublicKey.getModulus();
        this.publicExponent = rSAPublicKey.getPublicExponent();
    }

    BCRSAPublicKey(RSAPublicKeySpec rSAPublicKeySpec) {
        this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
        this.modulus = rSAPublicKeySpec.getModulus();
        this.publicExponent = rSAPublicKeySpec.getPublicExponent();
    }

    private void populateFromPublicKeyInfo(SubjectPublicKeyInfo subjectPublicKeyInfo) {
        try {
            RSAPublicKey rSAPublicKey = RSAPublicKey.getInstance(subjectPublicKeyInfo.parsePublicKey());
            this.algorithmIdentifier = subjectPublicKeyInfo.getAlgorithm();
            this.modulus = rSAPublicKey.getModulus();
            this.publicExponent = rSAPublicKey.getPublicExponent();
            return;
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("invalid info structure in RSA public key");
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        try {
            this.algorithmIdentifier = AlgorithmIdentifier.getInstance(objectInputStream.readObject());
        }
        catch (Exception exception) {
            this.algorithmIdentifier = DEFAULT_ALGORITHM_IDENTIFIER;
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        if (!this.algorithmIdentifier.equals(DEFAULT_ALGORITHM_IDENTIFIER)) {
            objectOutputStream.writeObject(this.algorithmIdentifier.getEncoded());
        }
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof java.security.interfaces.RSAPublicKey)) {
            return false;
        }
        object = (java.security.interfaces.RSAPublicKey)object;
        if (!this.getModulus().equals(object.getModulus()) || !this.getPublicExponent().equals(object.getPublicExponent())) {
            bl = false;
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return "RSA";
    }

    @Override
    public byte[] getEncoded() {
        return KeyUtil.getEncodedSubjectPublicKeyInfo(this.algorithmIdentifier, new RSAPublicKey(this.getModulus(), this.getPublicExponent()));
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public BigInteger getModulus() {
        return this.modulus;
    }

    @Override
    public BigInteger getPublicExponent() {
        return this.publicExponent;
    }

    public int hashCode() {
        return this.getModulus().hashCode() ^ this.getPublicExponent().hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("RSA Public Key [");
        stringBuffer.append(RSAUtil.generateKeyFingerprint(this.getModulus()));
        stringBuffer.append("]");
        stringBuffer.append(",[");
        stringBuffer.append(RSAUtil.generateExponentFingerprint(this.getPublicExponent()));
        stringBuffer.append("]");
        stringBuffer.append(string);
        stringBuffer.append("        modulus: ");
        stringBuffer.append(this.getModulus().toString(16));
        stringBuffer.append(string);
        stringBuffer.append("public exponent: ");
        stringBuffer.append(this.getPublicExponent().toString(16));
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

