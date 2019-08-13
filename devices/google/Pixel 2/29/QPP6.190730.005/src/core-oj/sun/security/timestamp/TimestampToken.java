/*
 * Decompiled with CFR 0.145.
 */
package sun.security.timestamp;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class TimestampToken {
    private Date genTime;
    private AlgorithmId hashAlgorithm;
    private byte[] hashedMessage;
    private BigInteger nonce;
    private ObjectIdentifier policy;
    private BigInteger serialNumber;
    private int version;

    public TimestampToken(byte[] arrby) throws IOException {
        if (arrby != null) {
            this.parse(arrby);
            return;
        }
        throw new IOException("No timestamp token info");
    }

    private void parse(byte[] object) throws IOException {
        object = new DerValue((byte[])object);
        if (object.tag == 48) {
            this.version = object.data.getInteger();
            this.policy = object.data.getOID();
            DerValue derValue = object.data.getDerValue();
            this.hashAlgorithm = AlgorithmId.parse(derValue.data.getDerValue());
            this.hashedMessage = derValue.data.getOctetString();
            this.serialNumber = object.data.getBigInteger();
            this.genTime = object.data.getGeneralizedTime();
            while (object.data.available() > 0) {
                derValue = object.data.getDerValue();
                if (derValue.tag != 2) continue;
                this.nonce = derValue.getBigInteger();
                break;
            }
            return;
        }
        throw new IOException("Bad encoding for timestamp token info");
    }

    public Date getDate() {
        return this.genTime;
    }

    public AlgorithmId getHashAlgorithm() {
        return this.hashAlgorithm;
    }

    public byte[] getHashedMessage() {
        return this.hashedMessage;
    }

    public BigInteger getNonce() {
        return this.nonce;
    }

    public String getPolicyID() {
        return this.policy.toString();
    }

    public BigInteger getSerialNumber() {
        return this.serialNumber;
    }
}

