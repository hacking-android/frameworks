/*
 * Decompiled with CFR 0.145.
 */
package sun.security.pkcs;

import java.io.IOException;
import sun.misc.HexDumpEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralNames;
import sun.security.x509.SerialNumber;

class ESSCertId {
    private static volatile HexDumpEncoder hexDumper;
    private byte[] certHash;
    private GeneralNames issuer;
    private SerialNumber serialNumber;

    ESSCertId(DerValue derValue) throws IOException {
        this.certHash = derValue.data.getDerValue().toByteArray();
        if (derValue.data.available() > 0) {
            derValue = derValue.data.getDerValue();
            this.issuer = new GeneralNames(derValue.data.getDerValue());
            this.serialNumber = new SerialNumber(derValue.data.getDerValue());
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[\n\tCertificate hash (SHA-1):\n");
        if (hexDumper == null) {
            hexDumper = new HexDumpEncoder();
        }
        stringBuffer.append(hexDumper.encode(this.certHash));
        if (this.issuer != null && this.serialNumber != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("\n\tIssuer: ");
            stringBuilder.append(this.issuer);
            stringBuilder.append("\n");
            stringBuffer.append(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("\t");
            stringBuilder.append(this.serialNumber);
            stringBuffer.append(stringBuilder.toString());
        }
        stringBuffer.append("\n]");
        return stringBuffer.toString();
    }
}

