/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.CertificatePolicyId;

public class CertificatePolicySet {
    private final Vector<CertificatePolicyId> ids;

    public CertificatePolicySet(Vector<CertificatePolicyId> vector) {
        this.ids = vector;
    }

    public CertificatePolicySet(DerInputStream object) throws IOException {
        this.ids = new Vector();
        DerValue[] arrderValue = ((DerInputStream)object).getSequence(5);
        for (int i = 0; i < arrderValue.length; ++i) {
            object = new CertificatePolicyId(arrderValue[i]);
            this.ids.addElement((CertificatePolicyId)object);
        }
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        for (int i = 0; i < this.ids.size(); ++i) {
            this.ids.elementAt(i).encode(derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public List<CertificatePolicyId> getCertPolicyIds() {
        return Collections.unmodifiableList(this.ids);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CertificatePolicySet:[\n");
        stringBuilder.append(this.ids.toString());
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

