/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class PolicyConstraintsExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.PolicyConstraints";
    public static final String INHIBIT = "inhibit";
    public static final String NAME = "PolicyConstraints";
    public static final String REQUIRE = "require";
    private static final byte TAG_INHIBIT = 1;
    private static final byte TAG_REQUIRE = 0;
    private int inhibit = -1;
    private int require = -1;

    public PolicyConstraintsExtension(int n, int n2) throws IOException {
        this(Boolean.FALSE, n, n2);
    }

    public PolicyConstraintsExtension(Boolean bl, int n, int n2) throws IOException {
        this.require = n;
        this.inhibit = n2;
        this.extensionId = PKIXExtensions.PolicyConstraints_Id;
        this.critical = bl;
        this.encodeThis();
    }

    public PolicyConstraintsExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.PolicyConstraints_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object = new DerValue(this.extensionValue);
        if (((DerValue)object).tag == 48) {
            object = ((DerValue)object).data;
            while (object != null && ((DerInputStream)object).available() != 0) {
                object2 = ((DerInputStream)object).getDerValue();
                if (((DerValue)object2).isContextSpecific((byte)0) && !((DerValue)object2).isConstructed()) {
                    if (this.require == -1) {
                        ((DerValue)object2).resetTag((byte)2);
                        this.require = ((DerValue)object2).getInteger();
                        continue;
                    }
                    throw new IOException("Duplicate requireExplicitPolicyfound in the PolicyConstraintsExtension");
                }
                if (((DerValue)object2).isContextSpecific((byte)1) && !((DerValue)object2).isConstructed()) {
                    if (this.inhibit == -1) {
                        ((DerValue)object2).resetTag((byte)2);
                        this.inhibit = ((DerValue)object2).getInteger();
                        continue;
                    }
                    throw new IOException("Duplicate inhibitPolicyMappingfound in the PolicyConstraintsExtension");
                }
                throw new IOException("Invalid encoding of PolicyConstraint");
            }
            return;
        }
        throw new IOException("Sequence tag missing for PolicyConstraint.");
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream;
        if (this.require == -1 && this.inhibit == -1) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.require != -1) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putInteger(this.require);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, false, (byte)0), derOutputStream);
        }
        if (this.inhibit != -1) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putInteger(this.inhibit);
            derOutputStream2.writeImplicit(DerValue.createTag((byte)-128, false, (byte)1), derOutputStream);
        }
        derOutputStream3.write((byte)48, derOutputStream2);
        this.extensionValue = derOutputStream3.toByteArray();
    }

    @Override
    public void delete(String string) throws IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!string.equalsIgnoreCase(REQUIRE)) break block2;
                    this.require = -1;
                    break block3;
                }
                if (!string.equalsIgnoreCase(INHIBIT)) break block4;
                this.inhibit = -1;
            }
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:PolicyConstraints.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.PolicyConstraints_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Integer get(String string) throws IOException {
        if (string.equalsIgnoreCase(REQUIRE)) {
            return new Integer(this.require);
        }
        if (string.equalsIgnoreCase(INHIBIT)) {
            return new Integer(this.inhibit);
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:PolicyConstraints.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(REQUIRE);
        attributeNameEnumeration.addElement(INHIBIT);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        block2 : {
            block5 : {
                block4 : {
                    block3 : {
                        if (!(object instanceof Integer)) break block2;
                        if (!string.equalsIgnoreCase(REQUIRE)) break block3;
                        this.require = (Integer)object;
                        break block4;
                    }
                    if (!string.equalsIgnoreCase(INHIBIT)) break block5;
                    this.inhibit = (Integer)object;
                }
                this.encodeThis();
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Attribute name [");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append("] not recognized by CertAttrSet:PolicyConstraints.");
            throw new IOException(((StringBuilder)object).toString());
        }
        throw new IOException("Attribute value should be of type Integer.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder;
        CharSequence charSequence = new StringBuilder();
        charSequence.append(super.toString());
        charSequence.append("PolicyConstraints: [  Require: ");
        charSequence = charSequence.toString();
        if (this.require == -1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("unspecified;");
            charSequence = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(this.require);
            stringBuilder.append(";");
            charSequence = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("\tInhibit: ");
        charSequence = stringBuilder.toString();
        if (this.inhibit == -1) {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append("unspecified");
            charSequence = stringBuilder.toString();
        } else {
            stringBuilder = new StringBuilder();
            stringBuilder.append((String)charSequence);
            stringBuilder.append(this.inhibit);
            charSequence = stringBuilder.toString();
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" ]\n");
        return stringBuilder.toString();
    }
}

