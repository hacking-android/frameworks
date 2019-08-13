/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.Debug;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class CRLNumberExtension
extends Extension
implements CertAttrSet<String> {
    private static final String LABEL = "CRL Number";
    public static final String NAME = "CRLNumber";
    public static final String NUMBER = "value";
    private BigInteger crlNumber = null;
    private String extensionLabel;
    private String extensionName;

    public CRLNumberExtension(int n) throws IOException {
        this(PKIXExtensions.CRLNumber_Id, false, BigInteger.valueOf(n), NAME, LABEL);
    }

    public CRLNumberExtension(Boolean bl, Object object) throws IOException {
        this(PKIXExtensions.CRLNumber_Id, bl, object, NAME, LABEL);
    }

    public CRLNumberExtension(BigInteger bigInteger) throws IOException {
        this(PKIXExtensions.CRLNumber_Id, false, bigInteger, NAME, LABEL);
    }

    protected CRLNumberExtension(ObjectIdentifier objectIdentifier, Boolean bl, Object object, String string, String string2) throws IOException {
        this.extensionId = objectIdentifier;
        this.critical = bl;
        this.extensionValue = (byte[])object;
        this.crlNumber = new DerValue(this.extensionValue).getBigInteger();
        this.extensionName = string;
        this.extensionLabel = string2;
    }

    protected CRLNumberExtension(ObjectIdentifier objectIdentifier, boolean bl, BigInteger bigInteger, String string, String string2) throws IOException {
        this.extensionId = objectIdentifier;
        this.critical = bl;
        this.crlNumber = bigInteger;
        this.extensionName = string;
        this.extensionLabel = string2;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        if (this.crlNumber == null) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.crlNumber);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override
    public void delete(String charSequence) throws IOException {
        if (((String)charSequence).equalsIgnoreCase(NUMBER)) {
            this.crlNumber = null;
            this.encodeThis();
            return;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Attribute name not recognized by CertAttrSet:");
        ((StringBuilder)charSequence).append(this.extensionName);
        ((StringBuilder)charSequence).append(".");
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        new DerOutputStream();
        this.encode(outputStream, PKIXExtensions.CRLNumber_Id, true);
    }

    protected void encode(OutputStream outputStream, ObjectIdentifier objectIdentifier, boolean bl) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = objectIdentifier;
            this.critical = bl;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public BigInteger get(String charSequence) throws IOException {
        if (((String)charSequence).equalsIgnoreCase(NUMBER)) {
            return this.crlNumber;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Attribute name not recognized by CertAttrSet:");
        ((StringBuilder)charSequence).append(this.extensionName);
        ((StringBuilder)charSequence).append('.');
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(NUMBER);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return this.extensionName;
    }

    @Override
    public void set(String charSequence, Object object) throws IOException {
        if (((String)charSequence).equalsIgnoreCase(NUMBER)) {
            if (object instanceof BigInteger) {
                this.crlNumber = (BigInteger)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute must be of type BigInteger.");
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Attribute name not recognized by CertAttrSet:");
        ((StringBuilder)charSequence).append(this.extensionName);
        ((StringBuilder)charSequence).append(".");
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append(this.extensionLabel);
        stringBuilder.append(": ");
        Object object = this.crlNumber;
        object = object == null ? "" : Debug.toHexString((BigInteger)object);
        stringBuilder.append((String)object);
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

