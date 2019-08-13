/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.IOException;
import sun.security.pkcs.ESSCertId;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

public class SigningCertificateInfo {
    private byte[] ber = null;
    private ESSCertId[] certId = null;

    public SigningCertificateInfo(byte[] arrby) throws IOException {
        this.parse(arrby);
    }

    public void parse(byte[] arrobject) throws IOException {
        DerValue derValue = new DerValue((byte[])arrobject);
        if (derValue.tag == 48) {
            int n;
            arrobject = derValue.data.getSequence(1);
            this.certId = new ESSCertId[arrobject.length];
            for (n = 0; n < arrobject.length; ++n) {
                this.certId[n] = new ESSCertId((DerValue)arrobject[n]);
            }
            if (derValue.data.available() > 0) {
                arrobject = derValue.data.getSequence(1);
                for (n = 0; n < arrobject.length; ++n) {
                }
            }
            return;
        }
        throw new IOException("Bad encoding for signingCertificate");
    }

    public String toString() {
        ESSCertId[] arreSSCertId;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[\n");
        for (int i = 0; i < (arreSSCertId = this.certId).length; ++i) {
            stringBuffer.append(arreSSCertId[i].toString());
        }
        stringBuffer.append("\n]");
        return stringBuffer.toString();
    }
}

