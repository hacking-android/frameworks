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
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.AttributeNameEnumeration;
import sun.security.x509.CertAttrSet;

public class CertificateAlgorithmId
implements CertAttrSet<String> {
    public static final String ALGORITHM = "algorithm";
    public static final String IDENT = "x509.info.algorithmID";
    public static final String NAME = "algorithmID";
    private AlgorithmId algId;

    public CertificateAlgorithmId(InputStream inputStream) throws IOException {
        this.algId = AlgorithmId.parse(new DerValue(inputStream));
    }

    public CertificateAlgorithmId(DerInputStream derInputStream) throws IOException {
        this.algId = AlgorithmId.parse(derInputStream.getDerValue());
    }

    public CertificateAlgorithmId(AlgorithmId algorithmId) {
        this.algId = algorithmId;
    }

    @Override
    public void delete(String string) throws IOException {
        if (string.equalsIgnoreCase(ALGORITHM)) {
            this.algId = null;
            return;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateAlgorithmId.");
    }

    @Override
    public void encode(OutputStream outputStream) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.algId.encode(derOutputStream);
        outputStream.write(derOutputStream.toByteArray());
    }

    @Override
    public AlgorithmId get(String string) throws IOException {
        if (string.equalsIgnoreCase(ALGORITHM)) {
            return this.algId;
        }
        throw new IOException("Attribute name not recognized by CertAttrSet:CertificateAlgorithmId.");
    }

    @Override
    public Enumeration<String> getElements() {
        AttributeNameEnumeration attributeNameEnumeration = new AttributeNameEnumeration();
        attributeNameEnumeration.addElement(ALGORITHM);
        return attributeNameEnumeration.elements();
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void set(String string, Object object) throws IOException {
        if (object instanceof AlgorithmId) {
            if (string.equalsIgnoreCase(ALGORITHM)) {
                this.algId = (AlgorithmId)object;
                return;
            }
            throw new IOException("Attribute name not recognized by CertAttrSet:CertificateAlgorithmId.");
        }
        throw new IOException("Attribute must be of type AlgorithmId.");
    }

    @Override
    public String toString() {
        if (this.algId == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.algId.toString());
        stringBuilder.append(", OID = ");
        stringBuilder.append(this.algId.getOID().toString());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

