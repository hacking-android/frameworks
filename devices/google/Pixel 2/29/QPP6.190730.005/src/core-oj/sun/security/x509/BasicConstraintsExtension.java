/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class BasicConstraintsExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.BasicConstraints";
    public static final String IS_CA = "is_ca";
    public static final String NAME = "BasicConstraints";
    public static final String PATH_LEN = "path_len";
    private boolean ca = false;
    private int pathLen = -1;

    public BasicConstraintsExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.BasicConstraints_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object2 = new DerValue(this.extensionValue);
        if (((DerValue)object2).tag == 48) {
            if (((DerValue)object2).data != null && ((DerValue)object2).data.available() != 0) {
                object = ((DerValue)object2).data.getDerValue();
                if (((DerValue)object).tag != 1) {
                    return;
                }
                this.ca = ((DerValue)object).getBoolean();
                if (((DerValue)object2).data.available() == 0) {
                    this.pathLen = Integer.MAX_VALUE;
                    return;
                }
                object = ((DerValue)object2).data.getDerValue();
                if (((DerValue)object).tag == 2) {
                    this.pathLen = ((DerValue)object).getInteger();
                    return;
                }
                throw new IOException("Invalid encoding of BasicConstraints");
            }
            return;
        }
        throw new IOException("Invalid encoding of BasicConstraints");
    }

    public BasicConstraintsExtension(Boolean bl, boolean bl2, int n) throws IOException {
        this.ca = bl2;
        this.pathLen = n;
        this.extensionId = PKIXExtensions.BasicConstraints_Id;
        this.critical = bl;
        this.encodeThis();
    }

    public BasicConstraintsExtension(boolean bl, int n) throws IOException {
        this(bl, bl, n);
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        boolean bl = this.ca;
        if (bl) {
            derOutputStream2.putBoolean(bl);
            int n = this.pathLen;
            if (n >= 0) {
                derOutputStream2.putInteger(n);
            }
        }
        derOutputStream.write((byte)48, derOutputStream2);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override
    public void delete(String string) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!string.equalsIgnoreCase(IS_CA)) break block2;
                    this.ca = false;
                    break block3;
                }
                if (!string.equalsIgnoreCase(PATH_LEN)) break block4;
                this.pathLen = -1;
            }
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.BasicConstraints_Id;
            this.critical = this.ca;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Object get(String string) throws IOException {
        if (string.equalsIgnoreCase(IS_CA)) {
            return this.ca;
        }
        if (string.equalsIgnoreCase(PATH_LEN)) {
            return this.pathLen;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(IS_CA);
        attributeNameEnumeration.addElement(PATH_LEN);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        block5 : {
            block6 : {
                block4 : {
                    block2 : {
                        block3 : {
                            if (!string.equalsIgnoreCase(IS_CA)) break block2;
                            if (!(object instanceof Boolean)) break block3;
                            this.ca = (Boolean)object;
                            break block4;
                        }
                        throw new IOException("Attribute value should be of type Boolean.");
                    }
                    if (!string.equalsIgnoreCase(PATH_LEN)) break block5;
                    if (!(object instanceof Integer)) break block6;
                    this.pathLen = (Integer)object;
                }
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type Integer.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:BasicConstraints.");
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        charSequence.append(super.toString());
        charSequence.append("BasicConstraints:[\n");
        charSequence = charSequence.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        charSequence = this.ca ? "  CA:true" : "  CA:false";
        stringBuilder.append((String)charSequence);
        stringBuilder.append("\n");
        charSequence = stringBuilder.toString();
        if (this.pathLen >= 0) {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("  PathLen:");
            stringBuilder.append(this.pathLen);
            stringBuilder.append("\n");
            charSequence = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("  PathLen: undefined\n");
            charSequence = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

