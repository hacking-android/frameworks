/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa;

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
import com.android.org.bouncycastle.jcajce.provider.asymmetric.dsa.DSAUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.org.bouncycastle.util.Strings;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.DSAParams;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.DSAParameterSpec;
import java.security.spec.DSAPublicKeySpec;

public class BCDSAPublicKey
implements DSAPublicKey {
    private static BigInteger ZERO = BigInteger.valueOf(0L);
    private static final long serialVersionUID = 1752452449903495175L;
    private transient DSAParams dsaSpec;
    private transient DSAPublicKeyParameters lwKeyParams;
    private BigInteger y;

    public BCDSAPublicKey(SubjectPublicKeyInfo aSN1Object) {
        block3 : {
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
                break block3;
            }
            this.dsaSpec = null;
        }
        this.lwKeyParams = new DSAPublicKeyParameters(this.y, DSAUtil.toDSAParameters(this.dsaSpec));
    }

    BCDSAPublicKey(DSAPublicKeyParameters dSAPublicKeyParameters) {
        this.y = dSAPublicKeyParameters.getY();
        this.dsaSpec = new DSAParameterSpec(dSAPublicKeyParameters.getParameters().getP(), dSAPublicKeyParameters.getParameters().getQ(), dSAPublicKeyParameters.getParameters().getG());
        this.lwKeyParams = dSAPublicKeyParameters;
    }

    BCDSAPublicKey(DSAPublicKey dSAPublicKey) {
        this.y = dSAPublicKey.getY();
        this.dsaSpec = dSAPublicKey.getParams();
        this.lwKeyParams = new DSAPublicKeyParameters(this.y, DSAUtil.toDSAParameters(this.dsaSpec));
    }

    BCDSAPublicKey(DSAPublicKeySpec dSAPublicKeySpec) {
        this.y = dSAPublicKeySpec.getY();
        this.dsaSpec = new DSAParameterSpec(dSAPublicKeySpec.getP(), dSAPublicKeySpec.getQ(), dSAPublicKeySpec.getG());
        this.lwKeyParams = new DSAPublicKeyParameters(this.y, DSAUtil.toDSAParameters(this.dsaSpec));
    }

    private boolean isNotNull(ASN1Encodable aSN1Encodable) {
        boolean bl = aSN1Encodable != null && !DERNull.INSTANCE.equals(aSN1Encodable.toASN1Primitive());
        return bl;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        BigInteger bigInteger = (BigInteger)objectInputStream.readObject();
        this.dsaSpec = bigInteger.equals(ZERO) ? null : new DSAParameterSpec(bigInteger, (BigInteger)objectInputStream.readObject(), (BigInteger)objectInputStream.readObject());
        this.lwKeyParams = new DSAPublicKeyParameters(this.y, DSAUtil.toDSAParameters(this.dsaSpec));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        DSAParams dSAParams = this.dsaSpec;
        if (dSAParams == null) {
            objectOutputStream.writeObject(ZERO);
        } else {
            objectOutputStream.writeObject(dSAParams.getP());
            objectOutputStream.writeObject(this.dsaSpec.getQ());
            objectOutputStream.writeObject(this.dsaSpec.getG());
        }
    }

    DSAPublicKeyParameters engineGetKeyParameters() {
        return this.lwKeyParams;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DSAPublicKey;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            return false;
        }
        object = (DSAPublicKey)object;
        if (this.dsaSpec != null) {
            if (this.getY().equals(object.getY()) && object.getParams() != null && this.getParams().getG().equals(object.getParams().getG()) && this.getParams().getP().equals(object.getParams().getP()) && this.getParams().getQ().equals(object.getParams().getQ())) {
                bl3 = true;
            }
            return bl3;
        }
        bl3 = bl2;
        if (this.getY().equals(object.getY())) {
            bl3 = bl2;
            if (object.getParams() == null) {
                bl3 = true;
            }
        }
        return bl3;
    }

    @Override
    public String getAlgorithm() {
        return "DSA";
    }

    @Override
    public byte[] getEncoded() {
        if (this.dsaSpec == null) {
            return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa), new ASN1Integer(this.y));
        }
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_dsa, new DSAParameter(this.dsaSpec.getP(), this.dsaSpec.getQ(), this.dsaSpec.getG()).toASN1Primitive()), new ASN1Integer(this.y));
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
        if (this.dsaSpec != null) {
            return this.getY().hashCode() ^ this.getParams().getG().hashCode() ^ this.getParams().getP().hashCode() ^ this.getParams().getQ().hashCode();
        }
        return this.getY().hashCode();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        String string = Strings.lineSeparator();
        stringBuffer.append("DSA Public Key [");
        stringBuffer.append(DSAUtil.generateKeyFingerprint(this.y, this.getParams()));
        stringBuffer.append("]");
        stringBuffer.append(string);
        stringBuffer.append("            Y: ");
        stringBuffer.append(this.getY().toString(16));
        stringBuffer.append(string);
        return stringBuffer.toString();
    }
}

