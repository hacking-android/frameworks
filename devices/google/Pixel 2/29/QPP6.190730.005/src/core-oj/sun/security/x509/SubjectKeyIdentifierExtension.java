/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

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
import sun.security.x509.KeyIdentifier;
import sun.security.x509.PKIXExtensions;

public class SubjectKeyIdentifierExtension
extends Extension
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.extensions.SubjectKeyIdentifier";
    public static final String KEY_ID = "key_id";
    public static final String NAME = "SubjectKeyIdentifier";
    private KeyIdentifier id = null;

    public SubjectKeyIdentifierExtension(Boolean bl, Object object) throws IOException {
        this.extensionId = PKIXExtensions.SubjectKey_Id;
        this.critical = bl;
        this.extensionValue = (byte[])object;
        this.id = new KeyIdentifier(new DerValue(this.extensionValue));
    }

    public SubjectKeyIdentifierExtension(byte[] arrby) throws IOException {
        this.id = new KeyIdentifier(arrby);
        this.extensionId = PKIXExtensions.SubjectKey_Id;
        this.critical = false;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        if (this.id == null) {
            this.extensionValue = null;
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        this.id.encode(derOutputStream);
        this.extensionValue = derOutputStream.toByteArray();
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(KEY_ID)) {
            this.id = null;
            this.encodeThis();
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.SubjectKey_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public KeyIdentifier get(String string) throws IOException {
        if (string.equalsIgnoreCase(KEY_ID)) {
            return this.id;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(KEY_ID);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(KEY_ID)) {
            if (object instanceof KeyIdentifier) {
                this.id = (KeyIdentifier)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type KeyIdentifier.");
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:SubjectKeyIdentifierExtension.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("SubjectKeyIdentifier [\n");
        stringBuilder.append(String.valueOf(this.id));
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

