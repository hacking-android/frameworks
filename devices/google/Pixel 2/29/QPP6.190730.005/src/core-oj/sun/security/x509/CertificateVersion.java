/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;

public class CertificateVersion
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.version";
    public static final String NAME = "version";
    public static final int V1 = 0;
    public static final int V2 = 1;
    public static final int V3 = 2;
    public static final String VERSION = "number";
    int version = 0;

    public CertificateVersion() {
        this.version = 0;
    }

    public CertificateVersion(int n) throws IOException {
        if (n != 0 && n != 1 && n != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("X.509 Certificate version ");
            stringBuilder.append(n);
            stringBuilder.append(" not supported.\n");
            throw new IOException(stringBuilder.toString());
        }
        this.version = n;
    }

    public CertificateVersion(InputStream inputStream) throws IOException {
        this.version = 0;
        this.construct(new DerValue(inputStream));
    }

    public CertificateVersion(DerInputStream derInputStream) throws IOException {
        this.version = 0;
        this.construct(derInputStream.getDerValue());
    }

    public CertificateVersion(DerValue derValue) throws IOException {
        this.version = 0;
        this.construct(derValue);
    }

    private void construct(DerValue derValue) throws IOException {
        if (derValue.isConstructed() && derValue.isContextSpecific()) {
            derValue = derValue.data.getDerValue();
            this.version = derValue.getInteger();
            if (derValue.data.available() != 0) {
                throw new IOException("X.509 version, bad format");
            }
        }
    }

    private int getVersion() {
        return this.version;
    }

    public int compare(int n) {
        return this.version - n;
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(VERSION)) {
            this.version = 0;
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        if (this.version == 0) {
            return;
        }
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.version);
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream2.write(DerValue.createTag((byte)-128, true, (byte)0), derOutputStream);
        outputStream.write(derOutputStream2.toByteArray());
    }

    @Override
    public Integer get(String string) throws IOException {
        if (string.equalsIgnoreCase(VERSION)) {
            return new Integer(this.getVersion());
        }
        throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(VERSION);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof Integer) {
            if (string.equalsIgnoreCase(VERSION)) {
                this.version = (Integer)object;
                return;
            }
            throw new IOException("Attribute name not recognized by CertAttrSet: CertificateVersion.");
        }
        throw new IOException("Attribute must be of type Integer.");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Version: V");
        stringBuilder.append(this.version + 1);
        return stringBuilder.toString();
    }
}

