/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class SerialNumber {
    private BigInteger serialNum;

    public SerialNumber(int n) {
        this.serialNum = BigInteger.valueOf(n);
    }

    public SerialNumber(InputStream inputStream) throws IOException {
        this.construct(new DerValue(inputStream));
    }

    public SerialNumber(BigInteger bigInteger) {
        this.serialNum = bigInteger;
    }

    public SerialNumber(DerInputStream derInputStream) throws IOException {
        this.construct(derInputStream.getDerValue());
    }

    public SerialNumber(DerValue derValue) throws IOException {
        this.construct(derValue);
    }

    private void construct(DerValue derValue) throws IOException {
        this.serialNum = derValue.getBigInteger();
        if (derValue.data.available() == 0) {
            return;
        }
        throw new IOException("Excess SerialNumber data");
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putInteger(this.serialNum);
    }

    public BigInteger getNumber() {
        return this.serialNum;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SerialNumber: [");
        stringBuilder.append(Debug.toHexString(this.serialNum));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}

