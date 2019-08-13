/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x9.DHDomainParameters;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;

public class JCEDHPrivateKey
implements DHPrivateKey,
PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    private PKCS12BagAttributeCarrier attrCarrier;
    private DHParameterSpec dhSpec;
    private PrivateKeyInfo info;
    BigInteger x;

    protected JCEDHPrivateKey() {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    JCEDHPrivateKey(PrivateKeyInfo object) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        block5 : {
            block4 : {
                ASN1Sequence aSN1Sequence;
                block3 : {
                    this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
                    aSN1Sequence = ASN1Sequence.getInstance(((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getParameters());
                    ASN1Integer aSN1Integer = ASN1Integer.getInstance(((PrivateKeyInfo)object).parsePrivateKey());
                    aSN1ObjectIdentifier = ((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm();
                    this.info = object;
                    this.x = aSN1Integer.getValue();
                    if (!aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.dhKeyAgreement)) break block3;
                    object = DHParameter.getInstance(aSN1Sequence);
                    this.dhSpec = ((DHParameter)object).getL() != null ? new DHParameterSpec(((DHParameter)object).getP(), ((DHParameter)object).getG(), ((DHParameter)object).getL().intValue()) : new DHParameterSpec(((DHParameter)object).getP(), ((DHParameter)object).getG());
                    break block4;
                }
                if (!aSN1ObjectIdentifier.equals(X9ObjectIdentifiers.dhpublicnumber)) break block5;
                object = DHDomainParameters.getInstance(aSN1Sequence);
                this.dhSpec = new DHParameterSpec(((DHDomainParameters)object).getP().getValue(), ((DHDomainParameters)object).getG().getValue());
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unknown algorithm type: ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    JCEDHPrivateKey(DHPrivateKeyParameters dHPrivateKeyParameters) {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.x = dHPrivateKeyParameters.getX();
        this.dhSpec = new DHParameterSpec(dHPrivateKeyParameters.getParameters().getP(), dHPrivateKeyParameters.getParameters().getG(), dHPrivateKeyParameters.getParameters().getL());
    }

    JCEDHPrivateKey(DHPrivateKey dHPrivateKey) {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.x = dHPrivateKey.getX();
        this.dhSpec = dHPrivateKey.getParams();
    }

    JCEDHPrivateKey(DHPrivateKeySpec dHPrivateKeySpec) {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.x = dHPrivateKeySpec.getX();
        this.dhSpec = new DHParameterSpec(dHPrivateKeySpec.getP(), dHPrivateKeySpec.getG());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.x = (BigInteger)objectInputStream.readObject();
        this.dhSpec = new DHParameterSpec((BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject(), objectInputStream.readInt());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.getX());
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }

    @Override
    public String getAlgorithm() {
        return "DH";
    }

    @Override
    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return this.attrCarrier.getBagAttribute(aSN1ObjectIdentifier);
    }

    @Override
    public Enumeration getBagAttributeKeys() {
        return this.attrCarrier.getBagAttributeKeys();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getEncoded() {
        try {
            byte[] arrby = this.info;
            if (arrby != null) {
                return this.info.getEncoded("DER");
            }
            ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.dhKeyAgreement;
            DHParameter dHParameter = new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL());
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(aSN1ObjectIdentifier, dHParameter);
            ASN1Integer aSN1Integer = new ASN1Integer(this.getX());
            arrby = new PrivateKeyInfo(algorithmIdentifier, aSN1Integer);
            return arrby.getEncoded("DER");
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
    public DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override
    public BigInteger getX() {
        return this.x;
    }

    @Override
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }
}

