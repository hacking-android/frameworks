/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dh;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x9.DomainParameters;
import com.android.org.bouncycastle.asn1.x9.ValidationParams;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHValidationParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.DHUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import com.android.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;

public class BCDHPrivateKey
implements DHPrivateKey,
PKCS12BagAttributeCarrier {
    static final long serialVersionUID = 311058815616901812L;
    private transient PKCS12BagAttributeCarrierImpl attrCarrier;
    private transient DHPrivateKeyParameters dhPrivateKey;
    private transient DHParameterSpec dhSpec;
    private transient PrivateKeyInfo info;
    private BigInteger x;

    protected BCDHPrivateKey() {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    public BCDHPrivateKey(PrivateKeyInfo object) throws IOException {
        ASN1ObjectIdentifier aSN1ObjectIdentifier;
        block7 : {
            block6 : {
                ASN1Sequence aSN1Sequence;
                block5 : {
                    this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
                    aSN1Sequence = ASN1Sequence.getInstance(((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getParameters());
                    ASN1Integer aSN1Integer = (ASN1Integer)((PrivateKeyInfo)object).parsePrivateKey();
                    aSN1ObjectIdentifier = ((PrivateKeyInfo)object).getPrivateKeyAlgorithm().getAlgorithm();
                    this.info = object;
                    this.x = aSN1Integer.getValue();
                    if (!aSN1ObjectIdentifier.equals(PKCSObjectIdentifiers.dhKeyAgreement)) break block5;
                    object = DHParameter.getInstance(aSN1Sequence);
                    if (((DHParameter)object).getL() != null) {
                        this.dhSpec = new DHParameterSpec(((DHParameter)object).getP(), ((DHParameter)object).getG(), ((DHParameter)object).getL().intValue());
                        this.dhPrivateKey = new DHPrivateKeyParameters(this.x, new DHParameters(((DHParameter)object).getP(), ((DHParameter)object).getG(), null, ((DHParameter)object).getL().intValue()));
                    } else {
                        this.dhSpec = new DHParameterSpec(((DHParameter)object).getP(), ((DHParameter)object).getG());
                        this.dhPrivateKey = new DHPrivateKeyParameters(this.x, new DHParameters(((DHParameter)object).getP(), ((DHParameter)object).getG()));
                    }
                    break block6;
                }
                if (!aSN1ObjectIdentifier.equals(X9ObjectIdentifiers.dhpublicnumber)) break block7;
                object = DomainParameters.getInstance(aSN1Sequence);
                this.dhSpec = new DHDomainParameterSpec(((DomainParameters)object).getP(), ((DomainParameters)object).getQ(), ((DomainParameters)object).getG(), ((DomainParameters)object).getJ(), 0);
                this.dhPrivateKey = new DHPrivateKeyParameters(this.x, new DHParameters(((DomainParameters)object).getP(), ((DomainParameters)object).getG(), ((DomainParameters)object).getQ(), ((DomainParameters)object).getJ(), null));
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("unknown algorithm type: ");
        ((StringBuilder)object).append(aSN1ObjectIdentifier);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    BCDHPrivateKey(DHPrivateKeyParameters dHPrivateKeyParameters) {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.x = dHPrivateKeyParameters.getX();
        this.dhSpec = new DHDomainParameterSpec(dHPrivateKeyParameters.getParameters());
    }

    BCDHPrivateKey(DHPrivateKey dHPrivateKey) {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.x = dHPrivateKey.getX();
        this.dhSpec = dHPrivateKey.getParams();
    }

    BCDHPrivateKey(DHPrivateKeySpec dHPrivateKeySpec) {
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
        this.x = dHPrivateKeySpec.getX();
        this.dhSpec = new DHParameterSpec(dHPrivateKeySpec.getP(), dHPrivateKeySpec.getG());
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.dhSpec = new DHParameterSpec((BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject(), objectInputStream.readInt());
        this.info = null;
        this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }

    DHPrivateKeyParameters engineGetKeyParameters() {
        Object object = this.dhPrivateKey;
        if (object != null) {
            return object;
        }
        object = this.dhSpec;
        if (object instanceof DHDomainParameterSpec) {
            return new DHPrivateKeyParameters(this.x, ((DHDomainParameterSpec)object).getDomainParameters());
        }
        return new DHPrivateKeyParameters(this.x, new DHParameters(((DHParameterSpec)object).getP(), this.dhSpec.getG(), null, this.dhSpec.getL()));
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof DHPrivateKey;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (DHPrivateKey)object;
            if (!this.getX().equals(object.getX()) || !this.getParams().getG().equals(object.getParams().getG()) || !this.getParams().getP().equals(object.getParams().getP()) || this.getParams().getL() != object.getParams().getL()) break block1;
            bl = true;
        }
        return bl;
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
            ASN1Object aSN1Object = this.info;
            if (aSN1Object != null) {
                return this.info.getEncoded("DER");
            }
            if (this.dhSpec instanceof DHDomainParameterSpec && ((DHDomainParameterSpec)this.dhSpec).getQ() != null) {
                DHParameters dHParameters = ((DHDomainParameterSpec)this.dhSpec).getDomainParameters();
                DHValidationParameters dHValidationParameters = dHParameters.getValidationParameters();
                aSN1Object = dHValidationParameters != null ? new ValidationParams(dHValidationParameters.getSeed(), dHValidationParameters.getCounter()) : null;
                ASN1ObjectIdentifier aSN1ObjectIdentifier = X9ObjectIdentifiers.dhpublicnumber;
                DomainParameters domainParameters = new DomainParameters(dHParameters.getP(), dHParameters.getG(), dHParameters.getQ(), dHParameters.getJ(), (ValidationParams)aSN1Object);
                AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(aSN1ObjectIdentifier, domainParameters.toASN1Primitive());
                aSN1Object = new ASN1Integer(this.getX());
                PrivateKeyInfo privateKeyInfo = new PrivateKeyInfo(algorithmIdentifier, aSN1Object);
                aSN1Object = privateKeyInfo;
                return aSN1Object.getEncoded("DER");
            } else {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.dhKeyAgreement;
                DHParameter dHParameter = new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL());
                aSN1Object = new AlgorithmIdentifier(aSN1ObjectIdentifier, dHParameter.toASN1Primitive());
                ASN1Integer aSN1Integer = new ASN1Integer(this.getX());
                aSN1Object = new PrivateKeyInfo((AlgorithmIdentifier)aSN1Object, aSN1Integer);
            }
            return aSN1Object.getEncoded("DER");
        }
        catch (Exception exception) {
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

    public int hashCode() {
        return this.getX().hashCode() ^ this.getParams().getG().hashCode() ^ this.getParams().getP().hashCode() ^ this.getParams().getL();
    }

    @Override
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        this.attrCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
    }

    public String toString() {
        return DHUtil.privateKeyToString("DH", this.x, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }
}

