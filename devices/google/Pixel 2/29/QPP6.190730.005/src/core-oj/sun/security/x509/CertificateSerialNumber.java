/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.cert.CertificateException;
import java.util.Enumeration;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;
import sun.security.x509.SerialNumber;

public class CertificateSerialNumber
implements CertAttrSet<String> {
    public static final String IDENT = "x509.info.serialNumber";
    public static final String NAME = "serialNumber";
    public static final String NUMBER = "number";
    private SerialNumber serial;

    public CertificateSerialNumber(int n) {
        this.serial = new SerialNumber(n);
    }

    public CertificateSerialNumber(InputStream inputStream) throws IOException {
        this.serial = new SerialNumber(inputStream);
    }

    public CertificateSerialNumber(BigInteger bigInteger) {
        this.serial = new SerialNumber(bigInteger);
    }

    public CertificateSerialNumber(DerInputStream derInputStream) throws IOException {
        this.serial = new SerialNumber(derInputStream);
    }

    public CertificateSerialNumber(DerValue derValue) throws IOException {
        this.serial = new SerialNumber(derValue);
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(NUMBER)) {
            this.serial = null;
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSerialNumber.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.serial.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public SerialNumber get(String string) throws IOException {
        if (string.equalsIgnoreCase(NUMBER)) {
            return this.serial;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSerialNumber.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(NUMBER);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof SerialNumber) {
            if (string.equalsIgnoreCase(NUMBER)) {
                this.serial = (SerialNumber)object;
                return;
            }
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateSerialNumber.");
        }
        throw new IOException("Attribute must be of type SerialNumber.");
    }

    @Override
    public String toString() {
        SerialNumber serialNumber = this.serial;
        if (serialNumber == null) {
            return "";
        }
        return serialNumber.toString();
    }
}

