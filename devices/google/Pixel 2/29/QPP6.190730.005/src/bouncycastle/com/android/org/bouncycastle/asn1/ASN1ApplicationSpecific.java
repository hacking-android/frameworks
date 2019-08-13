/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.StreamUtil;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.IOException;

public abstract class ASN1ApplicationSpecific
extends ASN1Primitive {
    protected final boolean isConstructed;
    protected final byte[] octets;
    protected final int tag;

    ASN1ApplicationSpecific(boolean bl, int n, byte[] arrby) {
        this.isConstructed = bl;
        this.tag = n;
        this.octets = Arrays.clone(arrby);
    }

    public static ASN1ApplicationSpecific getInstance(Object object) {
        if (object != null && !(object instanceof ASN1ApplicationSpecific)) {
            if (object instanceof byte[]) {
                try {
                    object = ASN1ApplicationSpecific.getInstance(ASN1Primitive.fromByteArray((byte[])object));
                    return object;
                }
                catch (IOException iOException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Failed to construct object from byte[]: ");
                    stringBuilder.append(iOException.getMessage());
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unknown object in getInstance: ");
            stringBuilder.append(object.getClass().getName());
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return (ASN1ApplicationSpecific)object;
    }

    protected static int getLengthOfHeader(byte[] object) {
        int n = object[1] & 255;
        if (n == 128) {
            return 2;
        }
        if (n > 127) {
            if ((n &= 127) <= 4) {
                return n + 2;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("DER length more than 4 bytes: ");
            ((StringBuilder)object).append(n);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        return 2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private byte[] replaceTagNumber(int n, byte[] arrby) throws IOException {
        int n2;
        if ((arrby[0] & 31) == 31) {
            int n3 = 1 + 1;
            int n4 = arrby[1] & 255;
            if ((n4 & 127) == 0) throw new IOException("corrupted stream - invalid high tag number found");
            do {
                n2 = ++n3;
                if ((n4 & 128) != 0) {
                    n4 = arrby[n3] & 255;
                    continue;
                }
                break;
            } while (true);
        } else {
            n2 = 1;
        }
        byte[] arrby2 = new byte[arrby.length - n2 + 1];
        System.arraycopy((byte[])arrby, (int)n2, (byte[])arrby2, (int)1, (int)(arrby2.length - 1));
        arrby2[0] = (byte)n;
        return arrby2;
    }

    @Override
    boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        boolean bl;
        block1 : {
            boolean bl2 = aSN1Primitive instanceof ASN1ApplicationSpecific;
            bl = false;
            if (!bl2) {
                return false;
            }
            aSN1Primitive = (ASN1ApplicationSpecific)aSN1Primitive;
            if (this.isConstructed != ((ASN1ApplicationSpecific)aSN1Primitive).isConstructed || this.tag != ((ASN1ApplicationSpecific)aSN1Primitive).tag || !Arrays.areEqual(this.octets, ((ASN1ApplicationSpecific)aSN1Primitive).octets)) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    void encode(ASN1OutputStream aSN1OutputStream) throws IOException {
        int n = 64;
        if (this.isConstructed) {
            n = 64 | 32;
        }
        aSN1OutputStream.writeEncoded(n, this.tag, this.octets);
    }

    @Override
    int encodedLength() throws IOException {
        return StreamUtil.calculateTagLength(this.tag) + StreamUtil.calculateBodyLength(this.octets.length) + this.octets.length;
    }

    public int getApplicationTag() {
        return this.tag;
    }

    public byte[] getContents() {
        return Arrays.clone(this.octets);
    }

    public ASN1Primitive getObject() throws IOException {
        return ASN1Primitive.fromByteArray(this.getContents());
    }

    public ASN1Primitive getObject(int n) throws IOException {
        if (n < 31) {
            byte[] arrby = this.getEncoded();
            byte[] arrby2 = this.replaceTagNumber(n, arrby);
            if ((arrby[0] & 32) != 0) {
                arrby2[0] = (byte)(arrby2[0] | 32);
            }
            return ASN1Primitive.fromByteArray(arrby2);
        }
        throw new IOException("unsupported tag number");
    }

    @Override
    public int hashCode() {
        return this.isConstructed ^ this.tag ^ Arrays.hashCode(this.octets);
    }

    @Override
    public boolean isConstructed() {
        return this.isConstructed;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        if (this.isConstructed()) {
            stringBuffer.append("CONSTRUCTED ");
        }
        stringBuffer.append("APPLICATION ");
        stringBuffer.append(Integer.toString(this.getApplicationTag()));
        stringBuffer.append("]");
        if (this.octets != null) {
            stringBuffer.append(" #");
            stringBuffer.append(Hex.toHexString(this.octets));
        } else {
            stringBuffer.append(" #null");
        }
        stringBuffer.append(" ");
        return stringBuffer.toString();
    }
}

