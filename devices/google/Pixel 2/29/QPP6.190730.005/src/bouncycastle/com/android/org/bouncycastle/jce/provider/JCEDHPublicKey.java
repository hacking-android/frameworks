/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.pkcs.DHParameter;
import com.android.org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.DHDomainParameters;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class JCEDHPublicKey
implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private DHParameterSpec dhSpec;
    private SubjectPublicKeyInfo info;
    private BigInteger y;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    JCEDHPublicKey(SubjectPublicKeyInfo aSN1Object) {
        Object object;
        this.info = aSN1Object;
        try {
            object = (ASN1Integer)((SubjectPublicKeyInfo)aSN1Object).parsePublicKey();
            this.y = ((ASN1Integer)object).getValue();
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("invalid info structure in DH public key");
        }
        object = ASN1Sequence.getInstance(((SubjectPublicKeyInfo)aSN1Object).getAlgorithmId().getParameters());
        aSN1Object = ((SubjectPublicKeyInfo)aSN1Object).getAlgorithmId().getAlgorithm();
        if (!((ASN1Primitive)aSN1Object).equals(PKCSObjectIdentifiers.dhKeyAgreement) && !this.isPKCSParam((ASN1Sequence)object)) {
            if (((ASN1Primitive)aSN1Object).equals(X9ObjectIdentifiers.dhpublicnumber)) {
                aSN1Object = DHDomainParameters.getInstance(object);
                this.dhSpec = new DHParameterSpec(((DHDomainParameters)aSN1Object).getP().getValue(), ((DHDomainParameters)aSN1Object).getG().getValue());
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unknown algorithm type: ");
            ((StringBuilder)object).append(aSN1Object);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
        aSN1Object = DHParameter.getInstance(object);
        if (((DHParameter)aSN1Object).getL() != null) {
            this.dhSpec = new DHParameterSpec(((DHParameter)aSN1Object).getP(), ((DHParameter)aSN1Object).getG(), ((DHParameter)aSN1Object).getL().intValue());
            return;
        }
        this.dhSpec = new DHParameterSpec(((DHParameter)aSN1Object).getP(), ((DHParameter)aSN1Object).getG());
    }

    JCEDHPublicKey(DHPublicKeyParameters dHPublicKeyParameters) {
        this.y = dHPublicKeyParameters.getY();
        this.dhSpec = new DHParameterSpec(dHPublicKeyParameters.getParameters().getP(), dHPublicKeyParameters.getParameters().getG(), dHPublicKeyParameters.getParameters().getL());
    }

    JCEDHPublicKey(BigInteger bigInteger, DHParameterSpec dHParameterSpec) {
        this.y = bigInteger;
        this.dhSpec = dHParameterSpec;
    }

    JCEDHPublicKey(DHPublicKey dHPublicKey) {
        this.y = dHPublicKey.getY();
        this.dhSpec = dHPublicKey.getParams();
    }

    JCEDHPublicKey(DHPublicKeySpec dHPublicKeySpec) {
        this.y = dHPublicKeySpec.getY();
        this.dhSpec = new DHParameterSpec(dHPublicKeySpec.getP(), dHPublicKeySpec.getG());
    }

    private boolean isPKCSParam(ASN1Sequence aSN1Primitive) {
        if (((ASN1Sequence)aSN1Primitive).size() == 2) {
            return true;
        }
        if (((ASN1Sequence)aSN1Primitive).size() > 3) {
            return false;
        }
        ASN1Integer aSN1Integer = ASN1Integer.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(2));
        aSN1Primitive = ASN1Integer.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(0));
        return aSN1Integer.getValue().compareTo(BigInteger.valueOf(((ASN1Integer)aSN1Primitive).getValue().bitLength())) <= 0;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.y = (BigInteger)objectInputStream.readObject();
        this.dhSpec = new DHParameterSpec((BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject(), objectInputStream.readInt());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.getY());
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }

    @Override
    public String getAlgorithm() {
        return "DH";
    }

    @Override
    public byte[] getEncoded() {
        SubjectPublicKeyInfo subjectPublicKeyInfo = this.info;
        if (subjectPublicKeyInfo != null) {
            return KeyUtil.getEncodedSubjectPublicKeyInfo(subjectPublicKeyInfo);
        }
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())), new ASN1Integer(this.y));
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public DHParameterSpec getParams() {
        return this.dhSpec;
    }

    @Override
    public BigInteger getY() {
        return this.y;
    }
}

