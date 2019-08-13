/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERNull;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.DSAParameter;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.DSAParameters;
import com.android.org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;

public class JDKDSAPublicKey
implements DSAPublicKey {
    private static final long serialVersionUID = 1752452449903495175L;
    private DSAParams dsaSpec;
    private BigInteger y;

    JDKDSAPublicKey(SubjectPublicKeyInfo aSN1Object) {
        block2 : {
            try {
                ASN1Integer aSN1Integer = (ASN1Integer)((SubjectPublicKeyInfo)aSN1Object).parsePublicKey();
                this.y = aSN1Integer.getValue();
                if (!this.isNotNull(((SubjectPublicKeyInfo)aSN1Object).getAlgorithm().getParameters())) break block2;
            }
            catch (IOException iOException) {
                throw new IllegalArgumentException("invalid info structure in DSA public key");
            }
            aSN1Object = DSAParameter.getInstance(((SubjectPublicKeyInfo)aSN1Object).getAlgorithm().getParameters());
            this.dsaSpec = new DSAParameterSpec(((DSAParameter)aSN1Object).getP(), ((DSAParameter)aSN1Object).getQ(), ((DSAParameter)aSN1Object).getG());
        }
    }

    JDKDSAPublicKey(DSAPublicKeyParameters dSAPublicKeyParameters) {
        this.y = dSAPublicKeyParameters.getY();
        this.dsaSpec = new DSAParameterSpec(dSAPublicKeyParameters.getParameters().getP(), dSAPublicKeyParameters.getParameters().getQ(), dSAPublicKeyParameters.getParameters().getG());
    }

    JDKDSAPublicKey(BigInteger bigInteger, DSAParameterSpec dSAParameterSpec) {
        this.y = bigInteger;
        this.dsaSpec = dSAParameterSpec;
    }

    JDKDSAPublicKey(DSAPublicKey dSAPublicKey) {
        this.y = dSAPublicKey.getY();
        this.dsaSpec = dSAPublicKey.getParams();
    }

    JDKDSAPublicKey(DSAPublicKeySpec dSAPublicKeySpec) {
        this.y = dSAPublicKeySpec.getY();
        this.dsaSpec = new DSAParameterSpec(dSAPublicKeySpec.getP(), dSAPublicKeySpec.getQ(), dSAPublicKeySpec.getG());
    }

    private boolean isNotNull(ASN1Encodable aSN1Encodable) {
        boolean bl = aSN1Encodable != null && !DERNull.INSTANCE.equals(aSN1Encodable);
        return bl;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        this.y = (BigInteger)objectInputStream.readObject();
        this.dsaSpec = new DSAParameterSpec((BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeObject(this.y);
        objectOutputStream.writeObject(this.dsaSpec.getP());
        objectOutputStream.writeObject(this.dsaSpec.getQ());
        objectOutputStream.writeObject(this.dsaSpec.getG());
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof DSAPublicKey;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (DSAPublicKey)object;
            if (!this.getY().equals(object.getY()) || !this.getParams().getG().equals(object.getParams().getG()) || !this.getParams().getP().equals(object.getParams().getP()) || !this.getParams().getQ().equals(object.getParams().getQ())) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public String getAlgorithm() {
        return "DSA";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public byte[] getEncoded() {
        try {
            byte[] arrby = this.dsaSpec;
            if (arrby == null) {
                arrby = new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa);
                ASN1Integer aSN1Integer = new ASN1Integer(this.y);
                SubjectPublicKeyInfo subjectPublicKeyInfo = new SubjectPublicKeyInfo((AlgorithmIdentifier)arrby, aSN1Integer);
                return subjectPublicKeyInfo.getEncoded("DER");
            }
            ASN1ObjectIdentifier aSN1ObjectIdentifier = X9ObjectIdentifiers.id_dsa;
            DSAParameter dSAParameter = new DSAParameter(this.dsaSpec.getP(), this.dsaSpec.getQ(), this.dsaSpec.getG());
            AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(aSN1ObjectIdentifier, dSAParameter);
            ASN1Integer aSN1Integer = new ASN1Integer(this.y);
            arrby = new SubjectPublicKeyInfo(algorithmIdentifier, aSN1Integer);
            return arrby.getEncoded("DER");
        }
        catch (IOException iOException) {
            return null;
        }
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    @Override
    public DSAParams getParams() {
        return this.dsaSpec;
    }

    @Override
    public BigInteger getY() {
        return this.y;
    }

    public int hashCode() {
        return this.getY().hashCode() ^ this.getParams().getG().hashCode() ^ this.getParams().getP().hashCode() ^ this.getParams().getQ().hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("DSA Public Key");
        stringBuffer.append(string);
        stringBuffer.append("            y: ");
        stringBuffer.append(this.getY().toString(16));
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

