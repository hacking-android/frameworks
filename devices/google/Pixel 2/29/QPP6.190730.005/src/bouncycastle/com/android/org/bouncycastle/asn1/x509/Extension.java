/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Boolean;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DEROctetString;
import com.android.org.bouncycastle.asn1.DERSequence;
import java.io.IOException;

public class Extension
extends ASN1Object {
    public static final ASN1ObjectIdentifier auditIdentity;
    public static final ASN1ObjectIdentifier authorityInfoAccess;
    public static final ASN1ObjectIdentifier authorityKeyIdentifier;
    public static final ASN1ObjectIdentifier basicConstraints;
    public static final ASN1ObjectIdentifier biometricInfo;
    public static final ASN1ObjectIdentifier cRLDistributionPoints;
    public static final ASN1ObjectIdentifier cRLNumber;
    public static final ASN1ObjectIdentifier certificateIssuer;
    public static final ASN1ObjectIdentifier certificatePolicies;
    public static final ASN1ObjectIdentifier deltaCRLIndicator;
    public static final ASN1ObjectIdentifier expiredCertsOnCRL;
    public static final ASN1ObjectIdentifier extendedKeyUsage;
    public static final ASN1ObjectIdentifier freshestCRL;
    public static final ASN1ObjectIdentifier inhibitAnyPolicy;
    public static final ASN1ObjectIdentifier instructionCode;
    public static final ASN1ObjectIdentifier invalidityDate;
    public static final ASN1ObjectIdentifier issuerAlternativeName;
    public static final ASN1ObjectIdentifier issuingDistributionPoint;
    public static final ASN1ObjectIdentifier keyUsage;
    public static final ASN1ObjectIdentifier logoType;
    public static final ASN1ObjectIdentifier nameConstraints;
    public static final ASN1ObjectIdentifier noRevAvail;
    public static final ASN1ObjectIdentifier policyConstraints;
    public static final ASN1ObjectIdentifier policyMappings;
    public static final ASN1ObjectIdentifier privateKeyUsagePeriod;
    public static final ASN1ObjectIdentifier qCStatements;
    public static final ASN1ObjectIdentifier reasonCode;
    public static final ASN1ObjectIdentifier subjectAlternativeName;
    public static final ASN1ObjectIdentifier subjectDirectoryAttributes;
    public static final ASN1ObjectIdentifier subjectInfoAccess;
    public static final ASN1ObjectIdentifier subjectKeyIdentifier;
    public static final ASN1ObjectIdentifier targetInformation;
    private boolean critical;
    private ASN1ObjectIdentifier extnId;
    private ASN1OctetString value;

    static {
        subjectDirectoryAttributes = new ASN1ObjectIdentifier("2.5.29.9").intern();
        subjectKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.14").intern();
        keyUsage = new ASN1ObjectIdentifier("2.5.29.15").intern();
        privateKeyUsagePeriod = new ASN1ObjectIdentifier("2.5.29.16").intern();
        subjectAlternativeName = new ASN1ObjectIdentifier("2.5.29.17").intern();
        issuerAlternativeName = new ASN1ObjectIdentifier("2.5.29.18").intern();
        basicConstraints = new ASN1ObjectIdentifier("2.5.29.19").intern();
        cRLNumber = new ASN1ObjectIdentifier("2.5.29.20").intern();
        reasonCode = new ASN1ObjectIdentifier("2.5.29.21").intern();
        instructionCode = new ASN1ObjectIdentifier("2.5.29.23").intern();
        invalidityDate = new ASN1ObjectIdentifier("2.5.29.24").intern();
        deltaCRLIndicator = new ASN1ObjectIdentifier("2.5.29.27").intern();
        issuingDistributionPoint = new ASN1ObjectIdentifier("2.5.29.28").intern();
        certificateIssuer = new ASN1ObjectIdentifier("2.5.29.29").intern();
        nameConstraints = new ASN1ObjectIdentifier("2.5.29.30").intern();
        cRLDistributionPoints = new ASN1ObjectIdentifier("2.5.29.31").intern();
        certificatePolicies = new ASN1ObjectIdentifier("2.5.29.32").intern();
        policyMappings = new ASN1ObjectIdentifier("2.5.29.33").intern();
        authorityKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.35").intern();
        policyConstraints = new ASN1ObjectIdentifier("2.5.29.36").intern();
        extendedKeyUsage = new ASN1ObjectIdentifier("2.5.29.37").intern();
        freshestCRL = new ASN1ObjectIdentifier("2.5.29.46").intern();
        inhibitAnyPolicy = new ASN1ObjectIdentifier("2.5.29.54").intern();
        authorityInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1").intern();
        subjectInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11").intern();
        logoType = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12").intern();
        biometricInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2").intern();
        qCStatements = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3").intern();
        auditIdentity = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4").intern();
        noRevAvail = new ASN1ObjectIdentifier("2.5.29.56").intern();
        targetInformation = new ASN1ObjectIdentifier("2.5.29.55").intern();
        expiredCertsOnCRL = new ASN1ObjectIdentifier("2.5.29.60").intern();
    }

    public Extension(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Boolean aSN1Boolean, ASN1OctetString aSN1OctetString) {
        this(aSN1ObjectIdentifier, aSN1Boolean.isTrue(), aSN1OctetString);
    }

    public Extension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, ASN1OctetString aSN1OctetString) {
        this.extnId = aSN1ObjectIdentifier;
        this.critical = bl;
        this.value = aSN1OctetString;
    }

    public Extension(ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean bl, byte[] arrby) {
        this(aSN1ObjectIdentifier, bl, (ASN1OctetString)new DEROctetString(arrby));
    }

    private Extension(ASN1Sequence aSN1Sequence) {
        block4 : {
            block3 : {
                block2 : {
                    if (aSN1Sequence.size() != 2) break block2;
                    this.extnId = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
                    this.critical = false;
                    this.value = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1));
                    break block3;
                }
                if (aSN1Sequence.size() != 3) break block4;
                this.extnId = ASN1ObjectIdentifier.getInstance(aSN1Sequence.getObjectAt(0));
                this.critical = ASN1Boolean.getInstance(aSN1Sequence.getObjectAt(1)).isTrue();
                this.value = ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(2));
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(aSN1Sequence.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static ASN1Primitive convertValueToObject(Extension object) throws IllegalArgumentException {
        try {
            object = ASN1Primitive.fromByteArray(((Extension)object).getExtnValue().getOctets());
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("can't convert extension: ");
            ((StringBuilder)object).append(iOException);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    public static Extension getInstance(Object object) {
        if (object instanceof Extension) {
            return (Extension)object;
        }
        if (object != null) {
            return new Extension(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    @Override
    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof Extension;
            bl = false;
            if (!bl2) {
                return false;
            }
            if (!((Extension)(object = (Extension)object)).getExtnId().equals(this.getExtnId()) || !((Extension)object).getExtnValue().equals(this.getExtnValue()) || ((Extension)object).isCritical() != this.isCritical()) break block1;
            bl = true;
        }
        return bl;
    }

    public ASN1ObjectIdentifier getExtnId() {
        return this.extnId;
    }

    public ASN1OctetString getExtnValue() {
        return this.value;
    }

    public ASN1Encodable getParsedValue() {
        return Extension.convertValueToObject(this);
    }

    @Override
    public int hashCode() {
        if (this.isCritical()) {
            return this.getExtnValue().hashCode() ^ this.getExtnId().hashCode();
        }
        return this.getExtnValue().hashCode() ^ this.getExtnId().hashCode();
    }

    public boolean isCritical() {
        return this.critical;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.extnId);
        if (this.critical) {
            aSN1EncodableVector.add(ASN1Boolean.getInstance(true));
        }
        aSN1EncodableVector.add(this.value);
        return new DERSequence(aSN1EncodableVector);
    }
}

