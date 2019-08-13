/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import sun.security.util.BitArray;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;

public class UniqueIdentity {
    private BitArray id;

    public UniqueIdentity(BitArray bitArray) {
        this.id = bitArray;
    }

    public UniqueIdentity(DerInputStream derInputStream) throws IOException {
        this.id = derInputStream.getDerValue().getUnalignedBitString(true);
    }

    public UniqueIdentity(DerValue derValue) throws IOException {
        this.id = derValue.getUnalignedBitString(true);
    }

    public UniqueIdentity(byte[] arrby) {
        this.id = new BitArray(arrby.length * 8, arrby);
    }

    public void encode(DerOutputStream derOutputStream, byte by) throws IOException {
        byte[] arrby = this.id.toByteArray();
        int n = arrby.length;
        int n2 = this.id.length();
        derOutputStream.write(by);
        derOutputStream.putLength(arrby.length + 1);
        derOutputStream.write(n * 8 - n2);
        derOutputStream.write(arrby);
    }

    public boolean[] getId() {
        BitArray bitArray = this.id;
        if (bitArray == null) {
            return null;
        }
        return bitArray.toBooleanArray();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UniqueIdentity:");
        stringBuilder.append(this.id.toString());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }
}

