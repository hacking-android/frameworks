/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AccessDescription;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.Extension;
import sun.security.x509.PKIXExtensions;

public class AuthorityInfoAccessExtension
extends Extension
implements CertAttrSet<String> {
    public static final String DESCRIPTIONS = "descriptions";
    public static final String IDENT = "x509.info.extensions.AuthorityInfoAccess";
    public static final String NAME = "AuthorityInfoAccess";
    private List<AccessDescription> accessDescriptions;

    public AuthorityInfoAccessExtension(Boolean object, Object object2) throws IOException {
        this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
        this.critical = (Boolean)object;
        if (object2 instanceof byte[]) {
            this.extensionValue = (byte[])object2;
            object = new DerValue(this.extensionValue);
            if (((DerValue)object).tag == 48) {
                this.accessDescriptions = new ArrayList<AccessDescription>();
                while (((DerValue)object).data.available() != 0) {
                    object2 = new AccessDescription(((DerValue)object).data.getDerValue());
                    this.accessDescriptions.add((AccessDescription)object2);
                }
                return;
            }
            throw new IOException("Invalid encoding for AuthorityInfoAccessExtension.");
        }
        throw new IOException("Illegal argument type");
    }

    public AuthorityInfoAccessExtension(List<AccessDescription> list) throws IOException {
        this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
        this.critical = false;
        this.accessDescriptions = list;
        this.encodeThis();
    }

    private void encodeThis() throws IOException {
        if (this.accessDescriptions.isEmpty()) {
            this.extensionValue = null;
        } else {
            DerOutputStream derOutputStream = new DerOutputStream();
            Object object = this.accessDescriptions.iterator();
            while (object.hasNext()) {
                object.next().encode(derOutputStream);
            }
            object = new DerOutputStream();
            ((DerOutputStream)object).write((byte)48, derOutputStream);
            this.extensionValue = ((ByteArrayOutputStream)object).toByteArray();
        }
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(DESCRIPTIONS)) {
            this.accessDescriptions = new ArrayList<AccessDescription>();
            this.encodeThis();
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:AuthorityInfoAccessExtension.");
        throw new IOException(stringBuilder.toString());
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        if (this.extensionValue == null) {
            this.extensionId = PKIXExtensions.AuthInfoAccess_Id;
            this.critical = false;
            this.encodeThis();
        }
        super.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public List<AccessDescription> get(String string) throws IOException {
        if (string.equalsIgnoreCase(DESCRIPTIONS)) {
            return this.accessDescriptions;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attribute name [");
        stringBuilder.append(string);
        stringBuilder.append("] not recognized by CertAttrSet:AuthorityInfoAccessExtension.");
        throw new IOException(stringBuilder.toString());
    }

    public List<AccessDescription> getAccessDescriptions() {
        return this.accessDescriptions;
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(DESCRIPTIONS);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(DESCRIPTIONS)) {
            if (object instanceof List) {
                this.accessDescriptions = (List)object;
                this.encodeThis();
                return;
            }
            throw new IOException("Attribute value should be of type List.");
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Attribute name [");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("] not recognized by CertAttrSet:AuthorityInfoAccessExtension.");
        throw new IOException(((StringBuilder)object).toString());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(super.toString());
        stringBuilder.append("AuthorityInfoAccess [\n  ");
        stringBuilder.append(this.accessDescriptions);
        stringBuilder.append("\n]\n");
        return stringBuilder.toString();
    }
}

