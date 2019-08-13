/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.X509Key;

public class CertificateX509Key
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.key";
    public static final String KEY = "value";
    public static final String NAME = "key";
    private PublicKey key;

    public CertificateX509Key(InputStream inputStream) throws IOException {
        this.key = X509Key.parse(new DerValue(inputStream));
    }

    public CertificateX509Key(PublicKey publicKey) {
        this.key = publicKey;
    }

    public CertificateX509Key(DerInputStream derInputStream) throws IOException {
        this.key = X509Key.parse(derInputStream.getDerValue());
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(KEY)) {
            this.key = null;
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.write(this.key.getEncoded());
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public PublicKey get(String string) throws IOException {
        if (string.equalsIgnoreCase(KEY)) {
            return this.key;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(KEY);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (string.equalsIgnoreCase(KEY)) {
            this.key = (PublicKey)object;
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateX509Key.");
    }

    @Override
    public String toString() {
        PublicKey publicKey = this.key;
        if (publicKey == null) {
            return "";
        }
        return publicKey.toString();
    }
}

