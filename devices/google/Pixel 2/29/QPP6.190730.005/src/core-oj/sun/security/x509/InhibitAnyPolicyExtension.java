/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class InhibitAnyPolicyExtension
extends Extension
implements CertAttrSet<String> {
    public static ObjectIdentifier AnyPolicy_Id;
    public static final String IDENT = "x509.info.extensions.InhibitAnyPolicy";
    public static final String NAME = "InhibitAnyPolicy";
    public static final String SKIP_CERTS = "skip_certs";
    private static final Debug debug;
    private int skipCerts = Integer.MAX_VALUE;

    static {
        debug = Debug.getInstance("certpath");
        try {
            ObjectIdentifier objectIdentifier;
            AnyPolicy_Id = objectIdentifier = new ObjectIdentifier("2.5.29.32.0");
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public InhibitAnyPolicyExtension(int n) throws IOException {
        if (n >= -1) {
            this.skipCerts = n == -1 ? Integer.MAX_VALUE : n;
            this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
            this.critical = true;
            this.encodeThis();
            return;
        }
        throw new IOException("Invalid value for skipCerts");
    }

    public InhibitAnyPolicyExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
        if (((Boolean)object).booleanValue()) {
            this.critical = (Boolean)object;
            this.extensionValue = (byte[])object2;
            object = new DerValue(this.extensionValue);
            if (((DerValue)object).tag == 2) {
                if (((DerValue)object).data != null) {
                    int n = ((DerValue)object).getInteger();
                    if (n >= -1) {
                        this.skipCerts = n == -1 ? Integer.MAX_VALUE : n;
                        return;
                    }
                    throw new IOException("Invalid value for skipCerts");
                }
                throw new IOException("Invalid encoding of InhibitAnyPolicy: null data");
            }
            throw new IOException("Invalid encoding of InhibitAnyPolicy: data not integer");
        }
        throw new IOException("Criticality cannot be false for InhibitAnyPolicy");
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.skipCerts);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(SKIP_CERTS)) {
            throw new IOException("Attribute skip_certs may not be deleted.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.InhibitAnyPolicy_Id;
            this.critical = true;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Integer get(String string) throws IOException {
        if (string.equalsIgnoreCase(SKIP_CERTS)) {
            return new Integer(this.skipCerts);
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(SKIP_CERTS);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(SKIP_CERTS)) {
            if (object instanceof Integer) {
                int n = (Integer)object;
                if (n >= -1) {
                    this.skipCerts = n == -1 ? Integer.MAX_VALUE : n;
                    this.encodeThis();
                    return;
                }
                throw new IOException("Invalid value for skipCerts");
            }
            throw new IOException("Attribute value should be of type Integer.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:InhibitAnyPolicy.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("InhibitAnyPolicy: ");
        stringBuilder.append(this.skipCerts);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

