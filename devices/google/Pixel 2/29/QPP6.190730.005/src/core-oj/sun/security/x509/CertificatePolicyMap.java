/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificatePolicyId;

public class CertificatePolicyMap {
    private CertificatePolicyId issuerDomain;
    private CertificatePolicyId subjectDomain;

    public CertificatePolicyMap(DerValue derValue) throws IOException {
        if (derValue.tag == 48) {
            this.issuerDomain = new CertificatePolicyId(derValue.data.getDerValue());
            this.subjectDomain = new CertificatePolicyId(derValue.data.getDerValue());
            return;
        }
        throw new IOException("Invalid encoding for CertificatePolicyMap");
    }

    public CertificatePolicyMap(CertificatePolicyId certificatePolicyId, CertificatePolicyId certificatePolicyId2) {
        this.issuerDomain = certificatePolicyId;
        this.subjectDomain = certificatePolicyId2;
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.issuerDomain.encode(derOutputStream2);
        this.subjectDomain.encode(derOutputStream2);
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public CertificatePolicyId getIssuerIdentifier() {
        return this.issuerDomain;
    }

    public CertificatePolicyId getSubjectIdentifier() {
        return this.subjectDomain;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CertificatePolicyMap: [\nIssuerDomain:");
        stringBuilder.append(this.issuerDomain.toString());
        stringBuilder.append("SubjectDomain:");
        stringBuilder.append(this.subjectDomain.toString());
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

