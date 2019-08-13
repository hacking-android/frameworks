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
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.X509Extension;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class X509Extensions
extends ASN1Object {
    public static final ASN1ObjectIdentifier AuditIdentity;
    public static final ASN1ObjectIdentifier AuthorityInfoAccess;
    public static final ASN1ObjectIdentifier AuthorityKeyIdentifier;
    public static final ASN1ObjectIdentifier BasicConstraints;
    public static final ASN1ObjectIdentifier BiometricInfo;
    public static final ASN1ObjectIdentifier CRLDistributionPoints;
    public static final ASN1ObjectIdentifier CRLNumber;
    public static final ASN1ObjectIdentifier CertificateIssuer;
    public static final ASN1ObjectIdentifier CertificatePolicies;
    public static final ASN1ObjectIdentifier DeltaCRLIndicator;
    public static final ASN1ObjectIdentifier ExtendedKeyUsage;
    public static final ASN1ObjectIdentifier FreshestCRL;
    public static final ASN1ObjectIdentifier InhibitAnyPolicy;
    public static final ASN1ObjectIdentifier InstructionCode;
    public static final ASN1ObjectIdentifier InvalidityDate;
    public static final ASN1ObjectIdentifier IssuerAlternativeName;
    public static final ASN1ObjectIdentifier IssuingDistributionPoint;
    public static final ASN1ObjectIdentifier KeyUsage;
    public static final ASN1ObjectIdentifier LogoType;
    public static final ASN1ObjectIdentifier NameConstraints;
    public static final ASN1ObjectIdentifier NoRevAvail;
    public static final ASN1ObjectIdentifier PolicyConstraints;
    public static final ASN1ObjectIdentifier PolicyMappings;
    public static final ASN1ObjectIdentifier PrivateKeyUsagePeriod;
    public static final ASN1ObjectIdentifier QCStatements;
    public static final ASN1ObjectIdentifier ReasonCode;
    public static final ASN1ObjectIdentifier SubjectAlternativeName;
    public static final ASN1ObjectIdentifier SubjectDirectoryAttributes;
    public static final ASN1ObjectIdentifier SubjectInfoAccess;
    public static final ASN1ObjectIdentifier SubjectKeyIdentifier;
    public static final ASN1ObjectIdentifier TargetInformation;
    private Hashtable extensions = new Hashtable();
    private Vector ordering = new Vector();

    static {
        SubjectDirectoryAttributes = new ASN1ObjectIdentifier("2.5.29.9");
        SubjectKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.14");
        KeyUsage = new ASN1ObjectIdentifier("2.5.29.15");
        PrivateKeyUsagePeriod = new ASN1ObjectIdentifier("2.5.29.16");
        SubjectAlternativeName = new ASN1ObjectIdentifier("2.5.29.17");
        IssuerAlternativeName = new ASN1ObjectIdentifier("2.5.29.18");
        BasicConstraints = new ASN1ObjectIdentifier("2.5.29.19");
        CRLNumber = new ASN1ObjectIdentifier("2.5.29.20");
        ReasonCode = new ASN1ObjectIdentifier("2.5.29.21");
        InstructionCode = new ASN1ObjectIdentifier("2.5.29.23");
        InvalidityDate = new ASN1ObjectIdentifier("2.5.29.24");
        DeltaCRLIndicator = new ASN1ObjectIdentifier("2.5.29.27");
        IssuingDistributionPoint = new ASN1ObjectIdentifier("2.5.29.28");
        CertificateIssuer = new ASN1ObjectIdentifier("2.5.29.29");
        NameConstraints = new ASN1ObjectIdentifier("2.5.29.30");
        CRLDistributionPoints = new ASN1ObjectIdentifier("2.5.29.31");
        CertificatePolicies = new ASN1ObjectIdentifier("2.5.29.32");
        PolicyMappings = new ASN1ObjectIdentifier("2.5.29.33");
        AuthorityKeyIdentifier = new ASN1ObjectIdentifier("2.5.29.35");
        PolicyConstraints = new ASN1ObjectIdentifier("2.5.29.36");
        ExtendedKeyUsage = new ASN1ObjectIdentifier("2.5.29.37");
        FreshestCRL = new ASN1ObjectIdentifier("2.5.29.46");
        InhibitAnyPolicy = new ASN1ObjectIdentifier("2.5.29.54");
        AuthorityInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.1");
        SubjectInfoAccess = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.11");
        LogoType = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.12");
        BiometricInfo = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.2");
        QCStatements = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.3");
        AuditIdentity = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.1.4");
        NoRevAvail = new ASN1ObjectIdentifier("2.5.29.56");
        TargetInformation = new ASN1ObjectIdentifier("2.5.29.55");
    }

    public X509Extensions(ASN1Sequence aSN1Sequence) {
        Object object = aSN1Sequence.getObjects();
        while (object.hasMoreElements()) {
            block5 : {
                block4 : {
                    block3 : {
                        aSN1Sequence = ASN1Sequence.getInstance(object.nextElement());
                        if (aSN1Sequence.size() != 3) break block3;
                        this.extensions.put(aSN1Sequence.getObjectAt(0), new X509Extension(ASN1Boolean.getInstance(aSN1Sequence.getObjectAt(1)), ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(2))));
                        break block4;
                    }
                    if (aSN1Sequence.size() != 2) break block5;
                    this.extensions.put(aSN1Sequence.getObjectAt(0), new X509Extension(false, ASN1OctetString.getInstance(aSN1Sequence.getObjectAt(1))));
                }
                this.ordering.addElement(aSN1Sequence.getObjectAt(0));
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Bad sequence size: ");
            ((StringBuilder)object).append(aSN1Sequence.size());
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    public X509Extensions(Hashtable hashtable) {
        this(null, hashtable);
    }

    public X509Extensions(Vector enumeration, Hashtable hashtable) {
        enumeration = enumeration == null ? hashtable.keys() : ((Vector)((Object)enumeration)).elements();
        while (enumeration.hasMoreElements()) {
            this.ordering.addElement(ASN1ObjectIdentifier.getInstance(enumeration.nextElement()));
        }
        Enumeration enumeration2 = this.ordering.elements();
        while (enumeration2.hasMoreElements()) {
            enumeration = ASN1ObjectIdentifier.getInstance(enumeration2.nextElement());
            X509Extension x509Extension = (X509Extension)hashtable.get(enumeration);
            this.extensions.put(enumeration, x509Extension);
        }
    }

    public X509Extensions(Vector object, Vector vector) {
        object = ((Vector)object).elements();
        while (object.hasMoreElements()) {
            this.ordering.addElement(object.nextElement());
        }
        int n = 0;
        Enumeration enumeration = this.ordering.elements();
        while (enumeration.hasMoreElements()) {
            object = (ASN1ObjectIdentifier)enumeration.nextElement();
            X509Extension x509Extension = (X509Extension)vector.elementAt(n);
            this.extensions.put(object, x509Extension);
            ++n;
        }
    }

    private ASN1ObjectIdentifier[] getExtensionOIDs(boolean bl) {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); ++i) {
            Object e = this.ordering.elementAt(i);
            if (((X509Extension)this.extensions.get(e)).isCritical() != bl) continue;
            vector.addElement(e);
        }
        return this.toOidArray(vector);
    }

    public static X509Extensions getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return X509Extensions.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static X509Extensions getInstance(Object object) {
        if (object != null && !(object instanceof X509Extensions)) {
            if (object instanceof ASN1Sequence) {
                return new X509Extensions((ASN1Sequence)object);
            }
            if (object instanceof Extensions) {
                return new X509Extensions((ASN1Sequence)((Extensions)object).toASN1Primitive());
            }
            if (object instanceof ASN1TaggedObject) {
                return X509Extensions.getInstance(((ASN1TaggedObject)object).getObject());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("illegal object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (X509Extensions)object;
    }

    private ASN1ObjectIdentifier[] toOidArray(Vector vector) {
        ASN1ObjectIdentifier[] arraSN1ObjectIdentifier = new ASN1ObjectIdentifier[vector.size()];
        for (int i = 0; i != arraSN1ObjectIdentifier.length; ++i) {
            arraSN1ObjectIdentifier[i] = (ASN1ObjectIdentifier)vector.elementAt(i);
        }
        return arraSN1ObjectIdentifier;
    }

    public boolean equivalent(X509Extensions x509Extensions) {
        if (this.extensions.size() != x509Extensions.extensions.size()) {
            return false;
        }
        Enumeration enumeration = this.extensions.keys();
        while (enumeration.hasMoreElements()) {
            Object k = enumeration.nextElement();
            if (this.extensions.get(k).equals(x509Extensions.extensions.get(k))) continue;
            return false;
        }
        return true;
    }

    public ASN1ObjectIdentifier[] getCriticalExtensionOIDs() {
        return this.getExtensionOIDs(true);
    }

    public X509Extension getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (X509Extension)this.extensions.get(aSN1ObjectIdentifier);
    }

    public ASN1ObjectIdentifier[] getExtensionOIDs() {
        return this.toOidArray(this.ordering);
    }

    public ASN1ObjectIdentifier[] getNonCriticalExtensionOIDs() {
        return this.getExtensionOIDs(false);
    }

    public Enumeration oids() {
        return this.ordering.elements();
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration enumeration = this.ordering.elements();
        while (enumeration.hasMoreElements()) {
            ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
            X509Extension x509Extension = (X509Extension)this.extensions.get(aSN1ObjectIdentifier);
            ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
            aSN1EncodableVector2.add(aSN1ObjectIdentifier);
            if (x509Extension.isCritical()) {
                aSN1EncodableVector2.add(ASN1Boolean.TRUE);
            }
            aSN1EncodableVector2.add(x509Extension.getValue());
            aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        }
        return new DERSequence(aSN1EncodableVector);
    }
}

