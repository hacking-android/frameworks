/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.util.BitArray;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;

public class KeyIdentifier {
    private byte[] octetString;

    public KeyIdentifier(PublicKey object) throws IOException {
        object = new DerValue(object.getEncoded());
        if (((DerValue)object).tag == 48) {
            AlgorithmId.parse(((DerValue)object).data.getDerValue());
            byte[] arrby = ((DerValue)object).data.getUnalignedBitString().toByteArray();
            try {
                object = MessageDigest.getInstance("SHA1");
                ((MessageDigest)object).update(arrby);
                this.octetString = ((MessageDigest)object).digest();
                return;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new IOException("SHA1 not supported");
            }
        }
        throw new IOException("PublicKey value is not a valid X.509 public key");
    }

    public KeyIdentifier(DerValue derValue) throws IOException {
        this.octetString = derValue.getOctetString();
    }

    public KeyIdentifier(byte[] arrby) {
        this.octetString = (byte[])arrby.clone();
    }

    void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.putOctetString(this.octetString);
    }

    public boolean equals(Object arrby) {
        if (this == arrby) {
            return true;
        }
        if (!(arrby instanceof KeyIdentifier)) {
            return false;
        }
        arrby = ((KeyIdentifier)arrby).octetString;
        return Arrays.equals(this.octetString, arrby);
    }

    public byte[] getIdentifier() {
        return (byte[])this.octetString.clone();
    }

    public int hashCode() {
        byte[] arrby;
        int n = 0;
        for (int i = 0; i < (arrby = this.octetString).length; ++i) {
            n += arrby[i] * i;
        }
        return n;
    }

    public String toString() {
        Object object = new HexDumpEncoder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("KeyIdentifier [\n");
        stringBuilder.append(((CharacterEncoder)object).encodeBuffer(this.octetString));
        object = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append((String)object);
        stringBuilder.append("]\n");
        return stringBuilder.toString();
    }
}

