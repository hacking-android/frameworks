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
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.DomainParameters;
import com.android.org.bouncycastle.asn1.x9.ValidationParams;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.DHParameters;
import com.android.org.bouncycastle.crypto.params.DHPublicKeyParameters;
import com.android.org.bouncycastle.crypto.params.DHValidationParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dh.DHUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.org.bouncycastle.jcajce.spec.DHDomainParameterSpec;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;

public class BCDHPublicKey
implements DHPublicKey {
    static final long serialVersionUID = -216691575254424324L;
    private transient DHPublicKeyParameters dhPublicKey;
    private transient DHParameterSpec dhSpec;
    private transient SubjectPublicKeyInfo info;
    private BigInteger y;

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public BCDHPublicKey(SubjectPublicKeyInfo aSN1Object) {
        Object object;
        this.info = aSN1Object;
        try {
            object = (ASN1Integer)((SubjectPublicKeyInfo)aSN1Object).parsePublicKey();
            this.y = ((ASN1Integer)object).getValue();
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("invalid info structure in DH public key");
        }
        object = ASN1Sequence.getInstance(((SubjectPublicKeyInfo)aSN1Object).getAlgorithm().getParameters());
        aSN1Object = ((SubjectPublicKeyInfo)aSN1Object).getAlgorithm().getAlgorithm();
        if (!((ASN1Primitive)aSN1Object).equals(PKCSObjectIdentifiers.dhKeyAgreement) && !this.isPKCSParam((ASN1Sequence)object)) {
            if (!((ASN1Primitive)aSN1Object).equals(X9ObjectIdentifiers.dhpublicnumber)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("unknown algorithm type: ");
                ((StringBuilder)object).append(aSN1Object);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            aSN1Object = ((DomainParameters)(object = DomainParameters.getInstance(object))).getValidationParams();
            this.dhPublicKey = aSN1Object != null ? new DHPublicKeyParameters(this.y, new DHParameters(((DomainParameters)object).getP(), ((DomainParameters)object).getG(), ((DomainParameters)object).getQ(), ((DomainParameters)object).getJ(), new DHValidationParameters(((ValidationParams)aSN1Object).getSeed(), ((ValidationParams)aSN1Object).getPgenCounter().intValue()))) : new DHPublicKeyParameters(this.y, new DHParameters(((DomainParameters)object).getP(), ((DomainParameters)object).getG(), ((DomainParameters)object).getQ(), ((DomainParameters)object).getJ(), null));
            this.dhSpec = new DHDomainParameterSpec(this.dhPublicKey.getParameters());
            return;
        }
        aSN1Object = DHParameter.getInstance(object);
        this.dhSpec = ((DHParameter)aSN1Object).getL() != null ? new DHParameterSpec(((DHParameter)aSN1Object).getP(), ((DHParameter)aSN1Object).getG(), ((DHParameter)aSN1Object).getL().intValue()) : new DHParameterSpec(((DHParameter)aSN1Object).getP(), ((DHParameter)aSN1Object).getG());
        this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }

    BCDHPublicKey(DHPublicKeyParameters dHPublicKeyParameters) {
        this.y = dHPublicKeyParameters.getY();
        this.dhSpec = new DHDomainParameterSpec(dHPublicKeyParameters.getParameters());
        this.dhPublicKey = dHPublicKeyParameters;
    }

    BCDHPublicKey(BigInteger bigInteger, DHParameterSpec dHParameterSpec) {
        this.y = bigInteger;
        this.dhSpec = dHParameterSpec;
        this.dhPublicKey = dHParameterSpec instanceof DHDomainParameterSpec ? new DHPublicKeyParameters(bigInteger, ((DHDomainParameterSpec)dHParameterSpec).getDomainParameters()) : new DHPublicKeyParameters(bigInteger, new DHParameters(dHParameterSpec.getP(), dHParameterSpec.getG()));
    }

    BCDHPublicKey(DHPublicKey dHPublicKey) {
        this.y = dHPublicKey.getY();
        this.dhSpec = dHPublicKey.getParams();
        this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }

    BCDHPublicKey(DHPublicKeySpec dHPublicKeySpec) {
        this.y = dHPublicKeySpec.getY();
        this.dhSpec = new DHParameterSpec(dHPublicKeySpec.getP(), dHPublicKeySpec.getG());
        this.dhPublicKey = new DHPublicKeyParameters(this.y, new DHParameters(dHPublicKeySpec.getP(), dHPublicKeySpec.getG()));
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
        objectInputStream.defaultReadObject();
        this.dhSpec = new DHParameterSpec((BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject(), objectInputStream.readInt());
        this.info = null;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.dhSpec.getP());
        objectOutputStream.writeObject(this.dhSpec.getG());
        objectOutputStream.writeInt(this.dhSpec.getL());
    }

    public DHPublicKeyParameters engineGetKeyParameters() {
        return this.dhPublicKey;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof DHPublicKey;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (DHPublicKey)object;
            if (!this.getY().equals(object.getY()) || !this.getParams().getG().equals(object.getParams().getG()) || !this.getParams().getP().equals(object.getParams().getP()) || this.getParams().getL() != object.getParams().getL()) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return "DH";
    }

    @Override
    public byte[] getEncoded() {
        Object object = this.info;
        if (object != null) {
            return KeyUtil.getEncodedSubjectPublicKeyInfo((SubjectPublicKeyInfo)object);
        }
        object = this.dhSpec;
        if (object instanceof DHDomainParameterSpec && ((DHDomainParameterSpec)object).getQ() != null) {
            DHParameters dHParameters = ((DHDomainParameterSpec)this.dhSpec).getDomainParameters();
            DHValidationParameters dHValidationParameters = dHParameters.getValidationParameters();
            object = null;
            if (dHValidationParameters != null) {
                object = new ValidationParams(dHValidationParameters.getSeed(), dHValidationParameters.getCounter());
            }
            return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.dhpublicnumber, new DomainParameters(dHParameters.getP(), dHParameters.getG(), dHParameters.getQ(), dHParameters.getJ(), (ValidationParams)object).toASN1Primitive()), new ASN1Integer(this.y));
        }
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL()).toASN1Primitive()), new ASN1Integer(this.y));
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

    public int hashCode() {
        return this.getY().hashCode() ^ this.getParams().getG().hashCode() ^ this.getParams().getP().hashCode() ^ this.getParams().getL();
    }

    public String toString() {
        return DHUtil.publicKeyToString("DH", this.y, new DHParameters(this.dhSpec.getP(), this.dhSpec.getG()));
    }
}

