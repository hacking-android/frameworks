/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.x500;

import com.android.org.bouncycastle.asn1.ASN1Choice;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.ASN1TaggedObject;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x500.AttributeTypeAndValue;
import com.android.org.bouncycastle.asn1.x500.RDN;
import com.android.org.bouncycastle.asn1.x500.X500NameStyle;
import com.android.org.bouncycastle.asn1.x500.style.BCStyle;
import java.util.Enumeration;

public class X500Name
extends ASN1Object
implements ASN1Choice {
    private static X500NameStyle defaultStyle = BCStyle.INSTANCE;
    private int hashCodeValue;
    private boolean isHashCodeCalculated;
    private RDN[] rdns;
    private X500NameStyle style;

    private X500Name(ASN1Sequence aSN1Sequence) {
        this(defaultStyle, aSN1Sequence);
    }

    private X500Name(X500NameStyle object, ASN1Sequence aSN1Sequence) {
        this.style = object;
        this.rdns = new RDN[aSN1Sequence.size()];
        int n = 0;
        object = aSN1Sequence.getObjects();
        while (object.hasMoreElements()) {
            this.rdns[n] = RDN.getInstance(object.nextElement());
            ++n;
        }
    }

    public X500Name(X500NameStyle x500NameStyle, X500Name x500Name) {
        this.rdns = x500Name.rdns;
        this.style = x500NameStyle;
    }

    public X500Name(X500NameStyle x500NameStyle, String string) {
        this(x500NameStyle.fromString(string));
        this.style = x500NameStyle;
    }

    public X500Name(X500NameStyle x500NameStyle, RDN[] arrrDN) {
        this.rdns = this.copy(arrrDN);
        this.style = x500NameStyle;
    }

    public X500Name(String string) {
        this(defaultStyle, string);
    }

    public X500Name(RDN[] arrrDN) {
        this(defaultStyle, arrrDN);
    }

    private RDN[] copy(RDN[] arrrDN) {
        RDN[] arrrDN2 = new RDN[arrrDN.length];
        System.arraycopy(arrrDN, 0, arrrDN2, 0, arrrDN2.length);
        return arrrDN2;
    }

    public static X500NameStyle getDefaultStyle() {
        return defaultStyle;
    }

    public static X500Name getInstance(ASN1TaggedObject aSN1TaggedObject, boolean bl) {
        return X500Name.getInstance(ASN1Sequence.getInstance(aSN1TaggedObject, true));
    }

    public static X500Name getInstance(X500NameStyle x500NameStyle, Object object) {
        if (object instanceof X500Name) {
            return new X500Name(x500NameStyle, (X500Name)object);
        }
        if (object != null) {
            return new X500Name(x500NameStyle, ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static X500Name getInstance(Object object) {
        if (object instanceof X500Name) {
            return (X500Name)object;
        }
        if (object != null) {
            return new X500Name(ASN1Sequence.getInstance(object));
        }
        return null;
    }

    public static void setDefaultStyle(X500NameStyle x500NameStyle) {
        if (x500NameStyle != null) {
            defaultStyle = x500NameStyle;
            return;
        }
        throw new NullPointerException("cannot set style to null");
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof X500Name) && !(object instanceof ASN1Sequence)) {
            return false;
        }
        ASN1Object aSN1Object = ((ASN1Encodable)object).toASN1Primitive();
        if (this.toASN1Primitive().equals(aSN1Object)) {
            return true;
        }
        try {
            X500NameStyle x500NameStyle = this.style;
            aSN1Object = new X500Name(ASN1Sequence.getInstance(((ASN1Encodable)object).toASN1Primitive()));
            boolean bl = x500NameStyle.areEqual(this, (X500Name)aSN1Object);
            return bl;
        }
        catch (Exception exception) {
            return false;
        }
    }

    public ASN1ObjectIdentifier[] getAttributeTypes() {
        int n;
        Object object;
        ASN1Object[] arraSN1Object;
        int n2 = 0;
        for (n = 0; n != (arraSN1Object = this.rdns).length; ++n) {
            n2 += arraSN1Object[n].size();
        }
        arraSN1Object = new ASN1ObjectIdentifier[n2];
        n = 0;
        for (int i = 0; i != ((RDN[])(object = this.rdns)).length; ++i) {
            if (((RDN)(object = object[i])).isMultiValued()) {
                object = ((RDN)object).getTypesAndValues();
                n2 = 0;
                while (n2 != ((Object)object).length) {
                    arraSN1Object[n] = ((AttributeTypeAndValue)object[n2]).getType();
                    ++n2;
                    ++n;
                }
                n2 = n;
            } else {
                n2 = n;
                if (((RDN)object).size() != 0) {
                    arraSN1Object[n] = ((RDN)object).getFirst().getType();
                    n2 = n + 1;
                }
            }
            n = n2;
        }
        return arraSN1Object;
    }

    public RDN[] getRDNs() {
        RDN[] arrrDN = this.rdns;
        RDN[] arrrDN2 = new RDN[arrrDN.length];
        System.arraycopy(arrrDN, 0, arrrDN2, 0, arrrDN2.length);
        return arrrDN2;
    }

    public RDN[] getRDNs(ASN1ObjectIdentifier arrrDN) {
        ASN1Object[] arraSN1Object;
        RDN[] arrrDN2 = new RDN[this.rdns.length];
        int n = 0;
        for (int i = 0; i != (arraSN1Object = this.rdns).length; ++i) {
            int n2;
            block5 : {
                RDN rDN = arraSN1Object[i];
                if (rDN.isMultiValued()) {
                    arraSN1Object = rDN.getTypesAndValues();
                    int n3 = 0;
                    do {
                        n2 = n;
                        if (n3 == arraSN1Object.length) break block5;
                        if (((AttributeTypeAndValue)arraSN1Object[n3]).getType().equals(arrrDN)) {
                            arrrDN2[n] = rDN;
                            n2 = n + 1;
                            break block5;
                        }
                        ++n3;
                    } while (true);
                }
                n2 = n;
                if (rDN.getFirst().getType().equals(arrrDN)) {
                    arrrDN2[n] = rDN;
                    n2 = n + 1;
                }
            }
            n = n2;
        }
        arrrDN = new RDN[n];
        System.arraycopy(arrrDN2, 0, arrrDN, 0, arrrDN.length);
        return arrrDN;
    }

    @Override
    public int hashCode() {
        if (this.isHashCodeCalculated) {
            return this.hashCodeValue;
        }
        this.isHashCodeCalculated = true;
        this.hashCodeValue = this.style.calculateHashCode(this);
        return this.hashCodeValue;
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        return new DERSequence(this.rdns);
    }

    public String toString() {
        return this.style.toString(this);
    }
}

