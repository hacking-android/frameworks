/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.GeneralNames;
import sun.security.x509.PKIXExtensions;

public class CertificateIssuerExtension
extends Extension
implements CertAttrSet<String> {
    public static final String ISSUER = "issuer";
    public static final String NAME = "CertificateIssuer";
    private GeneralNames names;

    public CertificateIssuerExtension(Boolean bl, Object object) throws IOException {
        this.extensionId = PKIXExtensions.CertificateIssuer_Id;
        this.critical = bl;
        this.extensionValue = (byte[])object;
        this.names = new GeneralNames(new DerValue(this.extensionValue));
    }

    public CertificateIssuerExtension(GeneralNames generalNames) throws IOException {
        this.extensionId = PKIXExtensions.CertificateIssuer_Id;
        this.critical = true;
        this.names = generalNames;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        Object object = this.names;
        if (object != null && !((GeneralNames)object).isEmpty()) {
            object = new DerOutputStream();
            this.names.encode((DerOutputStream)object);
            this.extensionValue = ((ByteArrayOutputStream)object).toByteArray();
            return;
        }
        this.extensionValue = null;
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(ISSUER)) {
            this.names = null;
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuer");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.CertificateIssuer_Id;
            this.critical = true;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public GeneralNames get(String string) throws IOException {
        if (string.equalsIgnoreCase(ISSUER)) {
            return this.names;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuer");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(ISSUER);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(ISSUER)) {
            if (object instanceof GeneralNames) {
                this.names = (GeneralNames)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value must be of type GeneralNames");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateIssuer");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("Certificate Issuer [\n");
        stringBuilder.append(String.valueOf(this.names));
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

