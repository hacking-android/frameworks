/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.ec;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import com.android.org.bouncycastle.asn1.x9.X962Parameters;
import com.android.org.bouncycastle.asn1.x9.X9ECPoint;
import com.android.org.bouncycastle.asn1.x9.X9IntegerConverter;
import com.android.org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import com.android.org.bouncycastle.crypto.params.ECDomainParameters;
import com.android.org.bouncycastle.crypto.params.ECPublicKeyParameters;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.ec.ECUtils;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import com.android.org.bouncycastle.jcajce.provider.asymmetric.util.KeyUtil;
import com.android.org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import com.android.org.bouncycastle.jce.interfaces.ECPointEncoder;
import com.android.org.bouncycastle.jce.provider.BouncyCastleProvider;
import com.android.org.bouncycastle.jce.spec.ECPublicKeySpec;
import com.android.org.bouncycastle.math.ec.ECCurve;
import com.android.org.bouncycastle.math.ec.ECFieldElement;
import com.android.org.bouncycastle.math.ec.ECPoint;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;

public class BCECPublicKey
implements ECPublicKey,
com.android.org.bouncycastle.jce.interfaces.ECPublicKey,
ECPointEncoder {
    static final long serialVersionUID = 2422789860422731812L;
    private String algorithm = "EC";
    private transient ProviderConfiguration configuration;
    private transient ECPublicKeyParameters ecPublicKey;
    private transient ECParameterSpec ecSpec;
    private boolean withCompression;

    BCECPublicKey(String string, SubjectPublicKeyInfo subjectPublicKeyInfo, ProviderConfiguration providerConfiguration) {
        this.algorithm = string;
        this.configuration = providerConfiguration;
        this.populateFromPubKeyInfo(subjectPublicKeyInfo);
    }

    public BCECPublicKey(String string, ECPublicKeyParameters eCPublicKeyParameters, ProviderConfiguration providerConfiguration) {
        this.algorithm = string;
        this.ecPublicKey = eCPublicKeyParameters;
        this.ecSpec = null;
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(String string, ECPublicKeyParameters eCPublicKeyParameters, com.android.org.bouncycastle.jce.spec.ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        ECDomainParameters eCDomainParameters = eCPublicKeyParameters.getParameters();
        this.algorithm = string;
        this.ecSpec = eCParameterSpec == null ? this.createSpec(EC5Util.convertCurve(eCDomainParameters.getCurve(), eCDomainParameters.getSeed()), eCDomainParameters) : EC5Util.convertSpec(EC5Util.convertCurve(eCParameterSpec.getCurve(), eCParameterSpec.getSeed()), eCParameterSpec);
        this.ecPublicKey = eCPublicKeyParameters;
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(String string, ECPublicKeyParameters eCPublicKeyParameters, ECParameterSpec eCParameterSpec, ProviderConfiguration providerConfiguration) {
        ECDomainParameters eCDomainParameters = eCPublicKeyParameters.getParameters();
        this.algorithm = string;
        this.ecPublicKey = eCPublicKeyParameters;
        this.ecSpec = eCParameterSpec == null ? this.createSpec(EC5Util.convertCurve(eCDomainParameters.getCurve(), eCDomainParameters.getSeed()), eCDomainParameters) : eCParameterSpec;
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(String string, BCECPublicKey bCECPublicKey) {
        this.algorithm = string;
        this.ecPublicKey = bCECPublicKey.ecPublicKey;
        this.ecSpec = bCECPublicKey.ecSpec;
        this.withCompression = bCECPublicKey.withCompression;
        this.configuration = bCECPublicKey.configuration;
    }

    public BCECPublicKey(String object, ECPublicKeySpec eCPublicKeySpec, ProviderConfiguration providerConfiguration) {
        this.algorithm = object;
        if (eCPublicKeySpec.getParams() != null) {
            object = EC5Util.convertCurve(eCPublicKeySpec.getParams().getCurve(), eCPublicKeySpec.getParams().getSeed());
            this.ecPublicKey = new ECPublicKeyParameters(eCPublicKeySpec.getQ(), ECUtil.getDomainParameters(providerConfiguration, eCPublicKeySpec.getParams()));
            this.ecSpec = EC5Util.convertSpec((EllipticCurve)object, eCPublicKeySpec.getParams());
        } else {
            this.ecPublicKey = new ECPublicKeyParameters(providerConfiguration.getEcImplicitlyCa().getCurve().createPoint(eCPublicKeySpec.getQ().getAffineXCoord().toBigInteger(), eCPublicKeySpec.getQ().getAffineYCoord().toBigInteger()), EC5Util.getDomainParameters(providerConfiguration, null));
            this.ecSpec = null;
        }
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(String string, java.security.spec.ECPublicKeySpec eCPublicKeySpec, ProviderConfiguration providerConfiguration) {
        this.algorithm = string;
        this.ecSpec = eCPublicKeySpec.getParams();
        this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(this.ecSpec, eCPublicKeySpec.getW(), false), EC5Util.getDomainParameters(providerConfiguration, eCPublicKeySpec.getParams()));
        this.configuration = providerConfiguration;
    }

    public BCECPublicKey(ECPublicKey eCPublicKey, ProviderConfiguration providerConfiguration) {
        this.algorithm = eCPublicKey.getAlgorithm();
        this.ecSpec = eCPublicKey.getParams();
        this.ecPublicKey = new ECPublicKeyParameters(EC5Util.convertPoint(this.ecSpec, eCPublicKey.getW(), false), EC5Util.getDomainParameters(providerConfiguration, eCPublicKey.getParams()));
    }

    private ECParameterSpec createSpec(EllipticCurve ellipticCurve, ECDomainParameters eCDomainParameters) {
        return new ECParameterSpec(ellipticCurve, EC5Util.convertPoint(eCDomainParameters.getG()), eCDomainParameters.getN(), eCDomainParameters.getH().intValue());
    }

    private void populateFromPubKeyInfo(SubjectPublicKeyInfo aSN1Object) {
        ECCurve eCCurve;
        X962Parameters x962Parameters;
        block6 : {
            byte[] arrby;
            DEROctetString dEROctetString;
            block7 : {
                x962Parameters = X962Parameters.getInstance(aSN1Object.getAlgorithm().getParameters());
                eCCurve = EC5Util.getCurve(this.configuration, x962Parameters);
                this.ecSpec = EC5Util.convertToSpec(x962Parameters, eCCurve);
                arrby = aSN1Object.getPublicKeyData().getBytes();
                dEROctetString = new DEROctetString(arrby);
                aSN1Object = dEROctetString;
                if (arrby[0] != 4) break block6;
                aSN1Object = dEROctetString;
                if (arrby[1] != arrby.length - 2) break block6;
                if (arrby[2] == 2) break block7;
                aSN1Object = dEROctetString;
                if (arrby[2] != 3) break block6;
            }
            aSN1Object = dEROctetString;
            if (new X9IntegerConverter().getByteLength(eCCurve) >= arrby.length - 3) {
                try {
                    aSN1Object = (ASN1OctetString)ASN1Primitive.fromByteArray(arrby);
                }
                catch (IOException iOException) {
                    throw new IllegalArgumentException("error recovering public key");
                }
            }
        }
        this.ecPublicKey = new ECPublicKeyParameters(new X9ECPoint(eCCurve, (ASN1OctetString)aSN1Object).getPoint(), ECUtil.getDomainParameters(this.configuration, x962Parameters));
    }

    private void readObject(ObjectInputStream arrby) throws IOException, ClassNotFoundException {
        arrby.defaultReadObject();
        arrby = (byte[])arrby.readObject();
        this.configuration = BouncyCastleProvider.CONFIGURATION;
        this.populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(arrby)));
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.getEncoded());
    }

    ECPublicKeyParameters engineGetKeyParameters() {
        return this.ecPublicKey;
    }

    com.android.org.bouncycastle.jce.spec.ECParameterSpec engineGetSpec() {
        ECParameterSpec eCParameterSpec = this.ecSpec;
        if (eCParameterSpec != null) {
            return EC5Util.convertSpec(eCParameterSpec, this.withCompression);
        }
        return this.configuration.getEcImplicitlyCa();
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof BCECPublicKey;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (BCECPublicKey)object;
        bl = bl2;
        if (this.ecPublicKey.getQ().equals(((BCECPublicKey)object).ecPublicKey.getQ())) {
            bl = bl2;
            if (this.engineGetSpec().equals(((BCECPublicKey)object).engineGetSpec())) {
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
    public byte[] getEncoded() {
        X962Parameters x962Parameters = ECUtils.getDomainParametersFromName(this.ecSpec, this.withCompression);
        ASN1OctetString aSN1OctetString = ASN1OctetString.getInstance(new X9ECPoint(this.ecPublicKey.getQ(), this.withCompression).toASN1Primitive());
        return KeyUtil.getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, x962Parameters), aSN1OctetString.getOctets()));
    }

    @Override
    public String getFormat() {
        return "X.509";
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
    public ECPoint getQ() {
        ECPoint eCPoint = this.ecPublicKey.getQ();
        if (this.ecSpec == null) {
            return eCPoint.getDetachedPoint();
        }
        return eCPoint;
    }

    @Override
    public java.security.spec.ECPoint getW() {
        return EC5Util.convertPoint(this.ecPublicKey.getQ());
    }

    public int hashCode() {
        return this.ecPublicKey.getQ().hashCode() ^ this.engineGetSpec().hashCode();
    }

    @Override
    public void setPointFormat(String string) {
        this.withCompression = "UNCOMPRESSED".equalsIgnoreCase(string) ^ true;
    }

    public String toString() {
        return ECUtil.publicKeyToString("EC", this.ecPublicKey.getQ(), this.engineGetSpec());
    }
}

