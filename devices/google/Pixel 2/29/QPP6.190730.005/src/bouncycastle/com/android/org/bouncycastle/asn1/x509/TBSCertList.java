/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x509;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1GeneralizedTime;
import com.android.org.bouncycastle.asn1.ASN1Integer;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.ASN1UTCTime;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.DERTaggedObject;
import com.android.org.bouncycastle.asn1.x500.X500Name;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.Extensions;
import com.android.org.bouncycastle.asn1.x509.Time;
import java.math.BigInteger;
import java.util.Enumeration;
import java.util.NoSuchElementException;

public class TBSCertList
extends ASN1Object {
    Extensions crlExtensions;
    X500Name issuer;
    Time nextUpdate;
    ASN1Sequence revokedCertificates;
    AlgorithmIdentifier signature;
    Time thisUpdate;
    ASN1Integer version;

    public TBSCertList(ASN1Sequence aSN1Sequence) {
        if (aSN1Sequence.size() >= 3 && aSN1Sequence.size() <= 7) {
            int n = 0;
            if (aSN1Sequence.getObjectAt(0) instanceof ASN1Integer) {
                this.version = ASN1Integer.getInstance(aSN1Sequence.getObjectAt(0));
                n = 0 + 1;
            } else {
                this.version = null;
            }
            int n2 = n + 1;
            this.signature = AlgorithmIdentifier.getInstance(aSN1Sequence.getObjectAt(n));
            int n3 = n2 + 1;
            this.issuer = X500Name.getInstance(aSN1Sequence.getObjectAt(n2));
            n = n3 + 1;
            this.thisUpdate = Time.getInstance(aSN1Sequence.getObjectAt(n3));
            if (n < aSN1Sequence.size() && (aSN1Sequence.getObjectAt(n) instanceof ASN1UTCTime || aSN1Sequence.getObjectAt(n) instanceof ASN1GeneralizedTime || aSN1Sequence.getObjectAt(n) instanceof Time)) {
                n3 = n + 1;
                this.nextUpdate = Time.getInstance(aSN1Sequence.getObjectAt(n));
                n = n3;
            }
            n3 = n;
            if (n < aSN1Sequence.size()) {
                n3 = n;
                if (!(aSN1Sequence.getObjectAt(n) instanceof ASN1TaggedObject)) {
                    this.revokedCertificates = ASN1Sequence.getInstance(aSN1Sequence.getObjectAt(n));
                    n3 = n + 1;
                }
            }
            if (n3 < aSN1Sequence.size() && aSN1Sequence.getObjectAt(n3) instanceof ASN1TaggedObject) {
                this.crlExtensions = Extensions.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)aSN1Sequence.getObjectAt(n3), true));
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad sequence size: ");
        stringBuilder.append(aSN1Sequence.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static TBSCertList getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return TBSCertList.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, bl));
    }

    public static TBSCertList getInstance(Object object) {
        if (object instanceof TBSCertList) {
            return (TBSCertList)object;
        }
        if (object != null) {
            return new TBSCertList(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public Extensions getExtensions() {
        return this.crlExtensions;
    }

    public X500Name getIssuer() {
        return this.issuer;
    }

    public Time getNextUpdate() {
        return this.nextUpdate;
    }

    public Enumeration getRevokedCertificateEnumeration() {
        ASN1Sequence aSN1Sequence = this.revokedCertificates;
        if (aSN1Sequence == null) {
            return new EmptyEnumeration();
        }
        return new RevokedCertificatesEnumeration(aSN1Sequence.getObjects());
    }

    public CRLEntry[] getRevokedCertificates() {
        CRLEntry[] arrcRLEntry = this.revokedCertificates;
        if (arrcRLEntry == null) {
            return new CRLEntry[0];
        }
        arrcRLEntry = new CRLEntry[arrcRLEntry.size()];
        for (int i = 0; i < arrcRLEntry.length; ++i) {
            arrcRLEntry[i] = CRLEntry.getInstance(this.revokedCertificates.getObjectAt(i));
        }
        return arrcRLEntry;
    }

    public AlgorithmIdentifier getSignature() {
        return this.signature;
    }

    public Time getThisUpdate() {
        return this.thisUpdate;
    }

    public ASN1Integer getVersion() {
        return this.version;
    }

    public int getVersionNumber() {
        ASN1Integer aSN1Integer = this.version;
        if (aSN1Integer == null) {
            return 1;
        }
        return aSN1Integer.getValue().intValue() + 1;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1Object aSN1Object = this.version;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        aSN1EncodableVector.add(this.signature);
        aSN1EncodableVector.add(this.issuer);
        aSN1EncodableVector.add(this.thisUpdate);
        aSN1Object = this.nextUpdate;
        if (aSN1Object != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        if ((aSN1Object = this.revokedCertificates) != null) {
            aSN1EncodableVector.add(aSN1Object);
        }
        if ((aSN1Object = this.crlExtensions) != null) {
            aSN1EncodableVector.add(new DERTaggedObject(0, aSN1Object));
        }
        return new DERSequence(aSN1EncodableVector);
    }

    public static class CRLEntry
    extends ASN1Object {
        Extensions crlEntryExtensions;
        ASN1Sequence seq;

        private CRLEntry(ASN1Sequence aSN1Sequence) {
            if (aSN1Sequence.size() >= 2 && aSN1Sequence.size() <= 3) {
                this.seq = aSN1Sequence;
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad sequence size: ");
            stringBuilder.append(aSN1Sequence.size());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public static CRLEntry getInstance(Object object) {
            if (object instanceof CRLEntry) {
                return (CRLEntry)object;
            }
            if (object != null) {
                return new CRLEntry(ASN1Sequence.getInstance(object));
            }
            return null;
        }

        public Extensions getExtensions() {
            if (this.crlEntryExtensions == null && this.seq.size() == 3) {
                this.crlEntryExtensions = Extensions.getInstance(this.seq.getObjectAt(2));
            }
            return this.crlEntryExtensions;
        }

        public Time getRevocationDate() {
            return Time.getInstance(this.seq.getObjectAt(1));
        }

        public ASN1Integer getUserCertificate() {
            return ASN1Integer.getInstance(this.seq.getObjectAt(0));
        }

        public boolean hasExtensions() {
            boolean bl = this.seq.size() == 3;
            return bl;
        }

        @Override
        public ASN1Primitive toASN1Primitive() {
            return this.seq;
        }
    }

    private class EmptyEnumeration
    implements Enumeration {
        private EmptyEnumeration() {
        }

        @Override
        public boolean hasMoreElements() {
            return false;
        }

        public Object nextElement() {
            throw new NoSuchElementException("Empty Enumeration");
        }
    }

    private class RevokedCertificatesEnumeration
    implements Enumeration {
        private final Enumeration en;

        RevokedCertificatesEnumeration(Enumeration enumeration) {
            this.en = enumeration;
        }

        @Override
        public boolean hasMoreElements() {
            return this.en.hasMoreElements();
        }

        public Object nextElement() {
            return CRLEntry.getInstance(this.en.nextElement());
        }
    }

}

