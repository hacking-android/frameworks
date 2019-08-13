/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class CertificatePolicyId {
    private ObjectIdentifier id;

    public CertificatePolicyId(DerValue derValue) throws IOException {
        this.id = derValue.getOID();
    }

    public CertificatePolicyId(ObjectIdentifier objectIdentifier) {
        this.id = objectIdentifier;
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putOID(this.id);
    }

    public boolean equals(Object object) {
        if (object instanceof CertificatePolicyId) {
            return this.id.equals((Object)((CertificatePolicyId)object).getIdentifier());
        }
        return false;
    }

    public ObjectIdentifier getIdentifier() {
        return this.id;
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CertificatePolicyId: [");
        stringBuilder.append(this.id.toString());
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

