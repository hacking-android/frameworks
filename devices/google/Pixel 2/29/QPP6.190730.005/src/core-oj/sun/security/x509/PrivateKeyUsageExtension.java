/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.CertificateParsingException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Objects;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class PrivateKeyUsageExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.PrivateKeyUsage";
    public static final String NAME = "PrivateKeyUsage";
    public static final String NOT_AFTER = "not_after";
    public static final String NOT_BEFORE = "not_before";
    private static final byte TAG_AFTER = 1;
    private static final byte TAG_BEFORE = 0;
    private Date notAfter = null;
    private Date notBefore = null;

    public PrivateKeyUsageExtension(Boolean arrderValue, Object object) throws CertificateException, IOException {
        this.extensionId = PKIXExtensions.PrivateKeyUsage_Id;
        this.critical = arrderValue.booleanValue();
        this.extensionValue = (byte[])object;
        arrderValue = new DerInputStream(this.extensionValue).getSequence(2);
        for (int i = 0; i < arrderValue.length; ++i) {
            object = arrderValue[i];
            if (((DerValue)object).isContextSpecific((byte)0) && !((DerValue)object).isConstructed()) {
                if (this.notBefore == null) {
                    ((DerValue)object).resetTag((byte)24);
                    this.notBefore = new DerInputStream(((DerValue)object).toByteArray()).getGeneralizedTime();
                    continue;
                }
                throw new CertificateParsingException("Duplicate notBefore in PrivateKeyUsage.");
            }
            if (((DerValue)object).isContextSpecific((byte)1) && !((DerValue)object).isConstructed()) {
                if (this.notAfter == null) {
                    ((DerValue)object).resetTag((byte)24);
                    this.notAfter = new DerInputStream(((DerValue)object).toByteArray()).getGeneralizedTime();
                    continue;
                }
                throw new CertificateParsingException("Duplicate notAfter in PrivateKeyUsage.");
            }
            throw new IOException("Invalid encoding of PrivateKeyUsageExtension");
        }
    }

    public PrivateKeyUsageExtension(Date date, Date date2) throws IOException {
        this.notBefore = date;
        this.notAfter = date2;
        this.extensionId = PKIXExtensions.PrivateKeyUsage_Id;
        this.critical = false;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        DerOutputStream derOutputStream;
        if (this.notBefore == null && this.notAfter == null) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream2 = new DerOutputStream();
        DerOutputStream derOutputStream3 = new DerOutputStream();
        if (this.notBefore != null) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putGeneralizedTime(this.notBefore);
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, false, (byte)0), derOutputStream);
        }
        if (this.notAfter != null) {
            derOutputStream = new DerOutputStream();
            derOutputStream.putGeneralizedTime(this.notAfter);
            derOutputStream3.writeImplicit(DerValue.createTag((byte)-128, false, (byte)1), derOutputStream);
        }
        derOutputStream2.write((byte)48, derOutputStream3);
        this.extensionValue = derOutputStream2.toByteArray();
    }

    @Override
    public void delete(String string) throws CertificateException, IOException {
        block4 : {
            block3 : {
                block2 : {
                    if (!string.equalsIgnoreCase(NOT_BEFORE)) break block2;
                    this.notBefore = null;
                    break block3;
                }
                if (!string.equalsIgnoreCase(NOT_AFTER)) break block4;
                this.notAfter = null;
            }
            this.encodeThis();
            return;
        }
        throw new CertificateException("Attribute name not recognized by CertAttrSet:PrivateKeyUsage.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.PrivateKeyUsage_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Date get(String string) throws CertificateException {
        if (string.equalsIgnoreCase(NOT_BEFORE)) {
            return new Date(this.notBefore.getTime());
        }
        if (string.equalsIgnoreCase(NOT_AFTER)) {
            return new Date(this.notAfter.getTime());
        }
        throw new CertificateException("Attribute name not recognized by CertAttrSet:PrivateKeyUsage.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(NOT_BEFORE);
        attributeNameEnumeration.addElement(NOT_AFTER);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws CertificateException, IOException {
        block2 : {
            block5 : {
                block4 : {
                    block3 : {
                        if (!(object instanceof Date)) break block2;
                        if (!string.equalsIgnoreCase(NOT_BEFORE)) break block3;
                        this.notBefore = (Date)object;
                        break block4;
                    }
                    if (!string.equalsIgnoreCase(NOT_AFTER)) break block5;
                    this.notAfter = (Date)object;
                }
                this.encodeThis();
                return;
            }
            throw new CertificateException("Attribute name not recognized by CertAttrSet:PrivateKeyUsage.");
        }
        throw new CertificateException("Attribute must be of type Date.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("PrivateKeyUsage: [\n");
        Object object = this.notBefore;
        String string = "";
        if (object == null) {
            object = "";
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("From: ");
            ((StringBuilder)object).append(this.notBefore.toString());
            ((StringBuilder)object).append(", ");
            object = ((StringBuilder)object).toString();
        }
        stringBuilder.append((String)object);
        if (this.notAfter == null) {
            object = string;
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("To: ");
            ((StringBuilder)object).append(this.notAfter.toString());
            object = ((StringBuilder)object).toString();
        }
        stringBuilder.append((String)object);
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }

    public void valid() throws CertificateNotYetValidException, CertificateExpiredException {
        this.valid(new Date());
    }

    public void valid(Date serializable) throws CertificateNotYetValidException, CertificateExpiredException {
        Objects.requireNonNull(serializable);
        Date date = this.notBefore;
        if (date != null && date.after((Date)serializable)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("NotBefore: ");
            ((StringBuilder)serializable).append(this.notBefore.toString());
            throw new CertificateNotYetValidException(((StringBuilder)serializable).toString());
        }
        date = this.notAfter;
        if (date != null && date.before((Date)serializable)) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("NotAfter: ");
            ((StringBuilder)serializable).append(this.notAfter.toString());
            throw new CertificateExpiredException(((StringBuilder)serializable).toString());
        }
    }
}

