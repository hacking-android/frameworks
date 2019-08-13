/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNames;
import sun.security.x509.PKIXExtensions;

public class IssuerAlternativeNameExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.IssuerAlternativeName";
    public static final String ISSUER_NAME = "issuer_name";
    public static final String NAME = "IssuerAlternativeName";
    GeneralNames names = null;

    public IssuerAlternativeNameExtension() {
        this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
        this.critical = false;
        this.names = new GeneralNames();
    }

    public IssuerAlternativeNameExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
        this.critical = (Boolean)object;
        this.extensionValue = (byte[])object2;
        object = new DerValue(this.extensionValue);
        if (((DerValue)object).data == null) {
            this.names = new GeneralNames();
            return;
        }
        this.names = new GeneralNames((DerValue)object);
    }

    public IssuerAlternativeNameExtension(Boolean bl, GeneralNames generalNames) throws IOException {
        this.names = generalNames;
        this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
        this.critical = bl;
        this.encodeThis();
    }

    public IssuerAlternativeNameExtension(GeneralNames generalNames) throws IOException {
        this.names = generalNames;
        this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
        this.critical = false;
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
        if (string.equalsIgnoreCase(ISSUER_NAME)) {
            this.names = null;
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:IssuerAlternativeName.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.IssuerAlternativeName_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public GeneralNames get(String string) throws IOException {
        if (string.equalsIgnoreCase(ISSUER_NAME)) {
            return this.names;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:IssuerAlternativeName.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(ISSUER_NAME);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(ISSUER_NAME)) {
            if (object instanceof GeneralNames) {
                this.names = (GeneralNames)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type GeneralNames.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:IssuerAlternativeName.");
    }

    @Override
    public String toString() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(super.toString());
        ((StringBuilder)charSequence).append("IssuerAlternativeName [\n");
        charSequence = ((StringBuilder)charSequence).toString();
        Object object = this.names;
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("  null\n");
            object = ((StringBuilder)object).toString();
        } else {
            Iterator<GeneralName> iterator = ((GeneralNames)object).names().iterator();
            do {
                object = charSequence;
                if (!iterator.hasNext()) break;
                GeneralName generalName = iterator.next();
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append("  ");
                ((StringBuilder)object).append(generalName);
                ((StringBuilder)object).append("\n");
                charSequence = ((StringBuilder)object).toString();
            } while (true);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("]\n");
        return ((StringBuilder)charSequence).toString();
    }
}

