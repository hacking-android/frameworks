/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.Enumeration;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class InvalidityDateExtension
extends Extension
implements CertAttrSet<String> {
    public static final String DATE = "date";
    public static final String NAME = "InvalidityDate";
    private Date date;

    public InvalidityDateExtension(Boolean bl, Object object) throws IOException {
        this.extensionId = PKIXExtensions.InvalidityDate_Id;
        this.critical = bl;
        this.extensionValue = (byte[])object;
        this.date = new DerValue(this.extensionValue).getGeneralizedTime();
    }

    public InvalidityDateExtension(Date date) throws IOException {
        this(false, date);
    }

    public InvalidityDateExtension(boolean bl, Date date) throws IOException {
        this.extensionId = PKIXExtensions.InvalidityDate_Id;
        this.critical = bl;
        this.date = date;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        if (this.date == null) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putGeneralizedTime(this.date);
        this.extensionValue = derOutputStream.toByteArray();
    }

    public static InvalidityDateExtension toImpl(java.security.cert.Extension extension) throws IOException {
        if (extension instanceof InvalidityDateExtension) {
            return (InvalidityDateExtension)extension;
        }
        return new InvalidityDateExtension((Boolean)extension.isCritical(), extension.getValue());
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(DATE)) {
            this.date = null;
            this.encodeThis();
            return;
        }
        throw new IOException("Name not supported by InvalidityDateExtension");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.InvalidityDate_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Date get(String object) throws IOException {
        if (((String)object).equalsIgnoreCase(DATE)) {
            object = this.date;
            if (object == null) {
                return null;
            }
            return new Date(((Date)object).getTime());
        }
        throw new IOException("Name not supported by InvalidityDateExtension");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(DATE);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof Date) {
            if (string.equalsIgnoreCase(DATE)) {
                this.date = (Date)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Name not supported by InvalidityDateExtension");
        }
        throw new IOException("Attribute must be of type Date.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("    Invalidity Date: ");
        stringBuilder.append(String.valueOf(this.date));
        return stringBuilder.toString();
    }
}

