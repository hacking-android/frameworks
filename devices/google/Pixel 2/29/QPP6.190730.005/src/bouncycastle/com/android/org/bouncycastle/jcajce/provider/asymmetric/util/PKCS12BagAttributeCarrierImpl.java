/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jcajce.provider.asymmetric.util;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class PKCS12BagAttributeCarrierImpl
implements PKCS12BagAttributeCarrier {
    private Hashtable pkcs12Attributes;
    private Vector pkcs12Ordering;

    public PKCS12BagAttributeCarrierImpl() {
        this(new Hashtable(), new Vector());
    }

    PKCS12BagAttributeCarrierImpl(Hashtable hashtable, Vector vector) {
        this.pkcs12Attributes = hashtable;
        this.pkcs12Ordering = vector;
    }

    Hashtable getAttributes() {
        return this.pkcs12Attributes;
    }

    @Override
    public ASN1Encodable getBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return (ASN1Encodable)this.pkcs12Attributes.get(aSN1ObjectIdentifier);
    }

    @Override
    public Enumeration getBagAttributeKeys() {
        return this.pkcs12Ordering.elements();
    }

    Vector getOrdering() {
        return this.pkcs12Ordering;
    }

    public void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        Object object = ((ObjectInputStream)inputStream).readObject();
        if (object instanceof Hashtable) {
            this.pkcs12Attributes = (Hashtable)object;
            this.pkcs12Ordering = (Vector)((ObjectInputStream)inputStream).readObject();
        } else {
            inputStream = new ASN1InputStream((byte[])object);
            while ((object = (ASN1ObjectIdentifier)((ASN1InputStream)inputStream).readObject()) != null) {
                this.setBagAttribute((ASN1ObjectIdentifier)object, ((ASN1InputStream)inputStream).readObject());
            }
        }
    }

    @Override
    public void setBagAttribute(ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1Encodable aSN1Encodable) {
        if (this.pkcs12Attributes.containsKey(aSN1ObjectIdentifier)) {
            this.pkcs12Attributes.put(aSN1ObjectIdentifier, aSN1Encodable);
        } else {
            this.pkcs12Attributes.put(aSN1ObjectIdentifier, aSN1Encodable);
            this.pkcs12Ordering.addElement(aSN1ObjectIdentifier);
        }
    }

    int size() {
        return this.pkcs12Ordering.size();
    }

    public void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (this.pkcs12Ordering.size() == 0) {
            objectOutputStream.writeObject(new Hashtable());
            objectOutputStream.writeObject(new Vector());
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ASN1OutputStream aSN1OutputStream = new ASN1OutputStream(byteArrayOutputStream);
            Enumeration enumeration = this.getBagAttributeKeys();
            while (enumeration.hasMoreElements()) {
                ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier)enumeration.nextElement();
                aSN1OutputStream.writeObject(aSN1ObjectIdentifier);
                aSN1OutputStream.writeObject((ASN1Encodable)this.pkcs12Attributes.get(aSN1ObjectIdentifier));
            }
            objectOutputStream.writeObject(byteArrayOutputStream.toByteArray());
        }
    }
}

