/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1.cms;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Set;
import com.android.org.bouncycastle.asn1.DERSet;
import com.android.org.bouncycastle.asn1.cms.Attribute;
import com.android.org.bouncycastle.asn1.cms.Attributes;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class AttributeTable {
    private Hashtable attributes = new Hashtable();

    public AttributeTable(ASN1EncodableVector aSN1EncodableVector) {
        for (int i = 0; i != aSN1EncodableVector.size(); ++i) {
            Attribute attribute = Attribute.getInstance(aSN1EncodableVector.get(i));
            this.addAttribute(attribute.getAttrType(), attribute);
        }
    }

    public AttributeTable(ASN1Set aSN1Set) {
        for (int i = 0; i != aSN1Set.size(); ++i) {
            Attribute attribute = Attribute.getInstance(aSN1Set.getObjectAt(i));
            this.addAttribute(attribute.getAttrType(), attribute);
        }
    }

    public AttributeTable(Attribute attribute) {
        this.addAttribute(attribute.getAttrType(), attribute);
    }

    public AttributeTable(Attributes attributes) {
        this(ASN1Set.getInstance(attributes.toASN1Primitive()));
    }

    public AttributeTable(Hashtable hashtable) {
        this.attributes = this.copyTable(hashtable);
    }

    private void addAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, Attribute vector) {
        Object v = this.attributes.get(aSN1ObjectIdentifier);
        if (v == null) {
            this.attributes.put(aSN1ObjectIdentifier, vector);
        } else {
            if (v instanceof Attribute) {
                Vector<Object> vector2 = new Vector<Object>();
                vector2.addElement(v);
                vector2.addElement(vector);
                vector = vector2;
            } else {
                Vector vector3 = (Vector)v;
                vector3.addElement(vector);
                vector = vector3;
            }
            this.attributes.put(aSN1ObjectIdentifier, vector);
        }
    }

    private Hashtable copyTable(Hashtable hashtable) {
        Hashtable hashtable2 = new Hashtable();
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            Object k = enumeration.nextElement();
            hashtable2.put(k, hashtable.get(k));
        }
        return hashtable2;
    }

    public AttributeTable add(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        AttributeTable attributeTable = new AttributeTable(this.attributes);
        attributeTable.addAttribute(aSN1ObjectIdentifier, new Attribute(aSN1ObjectIdentifier, new DERSet(aSN1Encodable)));
        return attributeTable;
    }

    public Attribute get(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        if ((aSN1ObjectIdentifier = this.attributes.get(aSN1ObjectIdentifier)) instanceof Vector) {
            return (Attribute)((Vector)((Object)aSN1ObjectIdentifier)).elementAt(0);
        }
        return (Attribute)((Object)aSN1ObjectIdentifier);
    }

    public ASN1EncodableVector getAll(ASN1ObjectIdentifier object) {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        if ((object = this.attributes.get(object)) instanceof Vector) {
            object = ((Vector)object).elements();
            while (object.hasMoreElements()) {
                aSN1EncodableVector.add((Attribute)object.nextElement());
            }
        } else if (object != null) {
            aSN1EncodableVector.add((Attribute)object);
        }
        return aSN1EncodableVector;
    }

    public AttributeTable remove(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        AttributeTable attributeTable = new AttributeTable(this.attributes);
        attributeTable.attributes.remove(aSN1ObjectIdentifier);
        return attributeTable;
    }

    public int size() {
        int n = 0;
        Enumeration enumeration = this.attributes.elements();
        while (enumeration.hasMoreElements()) {
            Object v = enumeration.nextElement();
            if (v instanceof Vector) {
                n += ((Vector)v).size();
                continue;
            }
            ++n;
        }
        return n;
    }

    public ASN1EncodableVector toASN1EncodableVector() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        Enumeration enumeration = this.attributes.elements();
        while (enumeration.hasMoreElements()) {
            Object object = enumeration.nextElement();
            if (object instanceof Vector) {
                object = ((Vector)object).elements();
                while (object.hasMoreElements()) {
                    aSN1EncodableVector.add(Attribute.getInstance(object.nextElement()));
                }
                continue;
            }
            aSN1EncodableVector.add(Attribute.getInstance(object));
        }
        return aSN1EncodableVector;
    }

    public Attributes toASN1Structure() {
        return new Attributes(this.toASN1EncodableVector());
    }

    public Hashtable toHashtable() {
        return this.copyTable(this.attributes);
    }
}

