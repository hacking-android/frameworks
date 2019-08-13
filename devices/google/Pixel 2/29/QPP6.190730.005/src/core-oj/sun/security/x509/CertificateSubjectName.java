/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import javax.security.auth.x500.X500Principal;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.X500Name;

public class CertificateSubjectName
implements CertAttrSet<String> {
    public static final String DN_NAME = "dname";
    public static final String DN_PRINCIPAL = "x500principal";
    public static final String IDENT = "x509.info.subject";
    public static final String NAME = "subject";
    private X500Name dnName;
    private X500Principal dnPrincipal;

    public CertificateSubjectName(InputStream inputStream) throws IOException {
        this.dnName = new X500Name(new DerValue(inputStream));
    }

    public CertificateSubjectName(DerInputStream derInputStream) throws IOException {
        this.dnName = new X500Name(derInputStream);
    }

    public CertificateSubjectName(X500Name x500Name) {
        this.dnName = x500Name;
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(DN_NAME)) {
            this.dnName = null;
            this.dnPrincipal = null;
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSubjectName.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.dnName.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public Object get(String object) throws IOException {
        if (((String)object).equalsIgnoreCase(DN_NAME)) {
            return this.dnName;
        }
        if (((String)object).equalsIgnoreCase(DN_PRINCIPAL)) {
            if (this.dnPrincipal == null && (object = this.dnName) != null) {
                this.dnPrincipal = ((X500Name)object).asX500Principal();
            }
            return this.dnPrincipal;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSubjectName.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(DN_NAME);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof X500Name) {
            if (string.equalsIgnoreCase(DN_NAME)) {
                this.dnName = (X500Name)object;
                this.dnPrincipal = null;
                return;
            }
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSubjectName.");
        }
        throw new IOException("Attribute must be of type X500Name.");
    }

    @Override
    public String toString() {
        X500Name x500Name = this.dnName;
        if (x500Name == null) {
            return "";
        }
        return x500Name.toString();
    }
}

