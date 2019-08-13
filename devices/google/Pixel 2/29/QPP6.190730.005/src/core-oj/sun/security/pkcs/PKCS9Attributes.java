/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Hashtable;
import sun.security.pkcs.PKCS9Attribute;
import sun.security.pkcs.ParsingException;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class PKCS9Attributes {
    private final Hashtable<ObjectIdentifier, PKCS9Attribute> attributes = new Hashtable(3);
    private final byte[] derEncoding;
    private boolean ignoreUnsupportedAttributes = false;
    private final Hashtable<ObjectIdentifier, ObjectIdentifier> permittedAttributes;

    public PKCS9Attributes(DerInputStream derInputStream) throws IOException {
        this(derInputStream, false);
    }

    public PKCS9Attributes(DerInputStream derInputStream, boolean bl) throws IOException {
        this.ignoreUnsupportedAttributes = bl;
        this.derEncoding = this.decode(derInputStream);
        this.permittedAttributes = null;
    }

    public PKCS9Attributes(PKCS9Attribute[] arrpKCS9Attribute) throws IllegalArgumentException, IOException {
        for (int i = 0; i < arrpKCS9Attribute.length; ++i) {
            Serializable serializable = arrpKCS9Attribute[i].getOID();
            if (!this.attributes.containsKey(serializable)) {
                this.attributes.put((ObjectIdentifier)serializable, arrpKCS9Attribute[i]);
                continue;
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("PKCSAttribute ");
            ((StringBuilder)serializable).append(arrpKCS9Attribute[i].getOID());
            ((StringBuilder)serializable).append(" duplicated while constructing PKCS9Attributes.");
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        this.derEncoding = this.generateDerEncoding();
        this.permittedAttributes = null;
    }

    public PKCS9Attributes(ObjectIdentifier[] arrobjectIdentifier, DerInputStream derInputStream) throws IOException {
        if (arrobjectIdentifier != null) {
            this.permittedAttributes = new Hashtable(arrobjectIdentifier.length);
            for (int i = 0; i < arrobjectIdentifier.length; ++i) {
                this.permittedAttributes.put(arrobjectIdentifier[i], arrobjectIdentifier[i]);
            }
        } else {
            this.permittedAttributes = null;
        }
        this.derEncoding = this.decode(derInputStream);
    }

    static DerEncoder[] castToDerEncoder(Object[] arrobject) {
        DerEncoder[] arrderEncoder = new DerEncoder[arrobject.length];
        for (int i = 0; i < arrderEncoder.length; ++i) {
            arrderEncoder[i] = (DerEncoder)arrobject[i];
        }
        return arrderEncoder;
    }

    private byte[] decode(DerInputStream object) throws IOException {
        object = ((DerInputStream)object).getDerValue().toByteArray();
        object[0] = (byte)49;
        DerValue[] arrderValue = new DerInputStream((byte[])object).getSet(3, true);
        boolean bl = true;
        for (int i = 0; i < arrderValue.length; ++i) {
            ParsingException parsingException2;
            block6 : {
                ObjectIdentifier objectIdentifier;
                block4 : {
                    PKCS9Attribute pKCS9Attribute;
                    block5 : {
                        try {
                            pKCS9Attribute = new PKCS9Attribute(arrderValue[i]);
                            objectIdentifier = pKCS9Attribute.getOID();
                            if (this.attributes.get(objectIdentifier) != null) break block4;
                            Hashtable<ObjectIdentifier, ObjectIdentifier> hashtable = this.permittedAttributes;
                            if (hashtable == null || hashtable.containsKey(objectIdentifier)) break block5;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Attribute ");
                        }
                        catch (ParsingException parsingException2) {
                            if (!this.ignoreUnsupportedAttributes) break block6;
                            bl = false;
                        }
                        ((StringBuilder)object).append(objectIdentifier);
                        ((StringBuilder)object).append(" not permitted in this attribute set");
                        throw new IOException(((StringBuilder)object).toString());
                    }
                    this.attributes.put(objectIdentifier, pKCS9Attribute);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Duplicate PKCS9 attribute: ");
                ((StringBuilder)object).append(objectIdentifier);
                throw new IOException(((StringBuilder)object).toString());
                continue;
            }
            throw parsingException2;
        }
        if (!bl) {
            object = this.generateDerEncoding();
        }
        return object;
    }

    private byte[] generateDerEncoding() throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        Object[] arrobject = this.attributes.values().toArray();
        derOutputStream.putOrderedSetOf((byte)49, PKCS9Attributes.castToDerEncoder(arrobject));
        return derOutputStream.toByteArray();
    }

    public void encode(byte by, OutputStream outputStream) throws IOException {
        outputStream.write(by);
        byte[] arrby = this.derEncoding;
        outputStream.write(arrby, 1, arrby.length - 1);
    }

    public PKCS9Attribute getAttribute(String string) {
        return this.attributes.get(PKCS9Attribute.getOID(string));
    }

    public PKCS9Attribute getAttribute(ObjectIdentifier objectIdentifier) {
        return this.attributes.get(objectIdentifier);
    }

    public Object getAttributeValue(String string) throws IOException {
        Serializable serializable = PKCS9Attribute.getOID(string);
        if (serializable != null) {
            return this.getAttributeValue((ObjectIdentifier)serializable);
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Attribute name ");
        ((StringBuilder)serializable).append(string);
        ((StringBuilder)serializable).append(" not recognized or not supported.");
        throw new IOException(((StringBuilder)serializable).toString());
    }

    public Object getAttributeValue(ObjectIdentifier objectIdentifier) throws IOException {
        try {
            Object object = this.getAttribute(objectIdentifier).getValue();
            return object;
        }
        catch (NullPointerException nullPointerException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No value found for attribute ");
            stringBuilder.append(objectIdentifier);
            throw new IOException(stringBuilder.toString());
        }
    }

    public PKCS9Attribute[] getAttributes() {
        PKCS9Attribute[] arrpKCS9Attribute = new PKCS9Attribute[this.attributes.size()];
        int n = 0;
        for (int i = 1; i < PKCS9Attribute.PKCS9_OIDS.length && n < arrpKCS9Attribute.length; ++i) {
            arrpKCS9Attribute[n] = this.getAttribute(PKCS9Attribute.PKCS9_OIDS[i]);
            int n2 = n;
            if (arrpKCS9Attribute[n] != null) {
                n2 = n + 1;
            }
            n = n2;
        }
        return arrpKCS9Attribute;
    }

    public byte[] getDerEncoding() throws IOException {
        return (byte[])this.derEncoding.clone();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(200);
        stringBuffer.append("PKCS9 Attributes: [\n\t");
        boolean bl = true;
        for (int i = 1; i < PKCS9Attribute.PKCS9_OIDS.length; ++i) {
            PKCS9Attribute pKCS9Attribute = this.getAttribute(PKCS9Attribute.PKCS9_OIDS[i]);
            if (pKCS9Attribute == null) continue;
            if (bl) {
                bl = false;
            } else {
                stringBuffer.append(";\n\t");
            }
            stringBuffer.append(pKCS9Attribute.toString());
        }
        stringBuffer.append("\n\t] (end PKCS9 Attributes)");
        return stringBuffer.toString();
    }
}

