/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Boolean;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OctetString;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import java.io.IOException;

public class X509Extension {
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
    boolean critical;
    ASN1OctetString value;

    static {
        subjectDirectoryAttributes = new ASN1ObjectIdentifier("2.5.29.9");
        subjectKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.14");
        keyUsage = new ASN1ObjectIdentifier("2.5.29.15");
        privateKeyUsagePeriod = new ASN1ObjectIdentifier("2.5.29.16");
        subjectAlternativeName = new ASN1ObjectIdentifier("2.5.29.17");
        issuerAlternativeName = new ASN1ObjectIdentifier("2.5.29.18");
        basicConstraints = new ASN1ObjectIdentifier("2.5.29.19");
        cRLNumber = new ASN1ObjectIdentifier("2.5.29.20");
        reasonCode = new ASN1ObjectIdentifier("2.5.29.21");
        instructionCode = new ASN1ObjectIdentifier("2.5.29.23");
        invalidityDate = new ASN1ObjectIdentifier("2.5.29.24");
        deltaCRLIndicator = new ASN1ObjectIdentifier("2.5.29.27");
        issuingDistributionPoint = new ASN1ObjectIdentifier("2.5.29.28");
        certificateIssuer = new ASN1ObjectIdentifier("2.5.29.29");
        nameConstraints = new ASN1ObjectIdentifier("2.5.29.30");
        cRLDistributionPoints = new ASN1ObjectIdentifier("2.5.29.31");
        certificatePolicies = new ASN1ObjectIdentifier("2.5.29.32");
        policyMappings = new ASN1ObjectIdentifier("2.5.29.33");
        authorityKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.35");
        policyConstraints = new ASN1ObjectIdentifier("2.5.29.36");
        extendedKeyUsage = new ASN1ObjectIdentifier("2.5.29.37");
        freshestCRL = new ASN1ObjectIdentifier("2.5.29.46");
        inhibitAnyPolicy = new ASN1ObjectIdentifier("2.5.29.54");
        authorityInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
        subjectInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
        logoType = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
        biometricInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
        qCStatements = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
        auditIdentity = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
        noRevAvail = new ASN1ObjectIdentifier("2.5.29.56");
        targetInformation = new ASN1ObjectIdentifier("2.5.29.55");
    }

    public X509Extension(ASN1Boolean aSN1Boolean, ASN1OctetString aSN1OctetString) {
        this.critical = aSN1Boolean.isTrue();
        this.value = aSN1OctetString;
    }

    public X509Extension(boolean bl, ASN1OctetString aSN1OctetString) {
        this.critical = bl;
        this.value = aSN1OctetString;
    }

    public static ASN1Primitive convertValueToObject(X509Extension object) throws IllegalArgumentException {
        try {
            object = ASN1Primitive.fromByteArray(((X509Extension)object).getValue().getOctets());
            return object;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("can't convert extension: ");
            stringBuilder.append(iOException);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof X509Extension;
            bl = false;
            if (!bl2) {
                return false;
            }
            if (!((X509Extension)(object = (X509Extension)object)).getValue().equals(this.getValue()) || ((X509Extension)object).isCritical() != this.isCritical()) break block1;
            bl = true;
        }
        return bl;
    }

    public ASN1Encodable getParsedValue() {
        return X509Extension.convertValueToObject(this);
    }

    public ASN1OctetString getValue() {
        return this.value;
    }

    public int hashCode() {
        if (this.isCritical()) {
            return this.getValue().hashCode();
        }
        return this.getValue().hashCode();
    }

    public boolean isCritical() {
        return this.critical;
    }
}

