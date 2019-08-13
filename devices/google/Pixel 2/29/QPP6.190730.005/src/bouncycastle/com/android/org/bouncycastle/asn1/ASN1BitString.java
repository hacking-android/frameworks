/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.asn1;

import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1OutputStream;
import com.android.org.bouncycastle.asn1.ASN1ParsingException;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1String;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DLBitString;
import com.android.org.bouncycastle.util.Arrays;
import com.android.org.bouncycastle.util.io.Streams;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class ASN1BitString
extends ASN1Primitive
implements ASN1String {
    private static final char[] table = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    protected final byte[] data;
    protected final int padBits;

    public ASN1BitString(byte[] arrby, int n) {
        if (arrby != null) {
            if (arrby.length == 0 && n != 0) {
                throw new IllegalArgumentException("zero length data with non-zero pad bits");
            }
            if (n <= 7 && n >= 0) {
                this.data = Arrays.clone(arrby);
                this.padBits = n;
                return;
            }
            throw new IllegalArgumentException("pad bits cannot be greater than 7 or less than 0");
        }
        throw new NullPointerException("data cannot be null");
    }

    protected static byte[] derForm(byte[] arrby, int n) {
        byte[] arrby2 = Arrays.clone(arrby);
        if (n > 0) {
            int n2 = arrby.length - 1;
            arrby2[n2] = (byte)(arrby2[n2] & 255 << n);
        }
        return arrby2;
    }

    static ASN1BitString fromInputStream(int n, InputStream inputStream) throws IOException {
        if (n >= 1) {
            int n2 = inputStream.read();
            byte[] arrby = new byte[n - 1];
            if (arrby.length != 0) {
                if (Streams.readFully(inputStream, arrby) == arrby.length) {
                    if (n2 > 0 && n2 < 8 && arrby[arrby.length - 1] != (byte)(arrby[arrby.length - 1] & 255 << n2)) {
                        return new DLBitString(arrby, n2);
                    }
                } else {
                    throw new EOFException("EOF encountered in middle of BIT STRING");
                }
            }
            return new DERBitString(arrby, n2);
        }
        throw new IllegalArgumentException("truncated BIT STRING detected");
    }

    protected static byte[] getBytes(int n) {
        int n2;
        if (n == 0) {
            return new byte[0];
        }
        int n3 = 4;
        for (n2 = 3; n2 >= 1 && (255 << n2 * 8 & n) == 0; --n2) {
            --n3;
        }
        byte[] arrby = new byte[n3];
        for (n2 = 0; n2 < n3; ++n2) {
            arrby[n2] = (byte)(n >> n2 * 8 & 255);
        }
        return arrby;
    }

    protected static int getPadBits(int n) {
        int n2;
        int n3 = 0;
        int n4 = 3;
        do {
            n2 = n3;
            if (n4 < 0) break;
            if (n4 != 0) {
                if (n >> n4 * 8 != 0) {
                    n2 = n >> n4 * 8 & 255;
                    break;
                }
            } else if (n != 0) {
                n2 = n & 255;
                break;
            }
            --n4;
        } while (true);
        if (n2 == 0) {
            return 0;
        }
        n = 1;
        do {
            n2 = n4 = n2 << 1;
            if ((n4 & 255) == 0) break;
            ++n;
        } while (true);
        return 8 - n;
    }

    @Override
    protected boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        boolean bl;
        block1 : {
            boolean bl2 = aSN1Primitive instanceof ASN1BitString;
            bl = false;
            if (!bl2) {
                return false;
            }
            aSN1Primitive = (ASN1BitString)aSN1Primitive;
            if (this.padBits != ((ASN1BitString)aSN1Primitive).padBits || !Arrays.areEqual(this.getBytes(), ((ASN1BitString)aSN1Primitive).getBytes())) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    abstract void encode(ASN1OutputStream var1) throws IOException;

    public byte[] getBytes() {
        return ASN1BitString.derForm(this.data, this.padBits);
    }

    public ASN1Primitive getLoadedObject() {
        return this.toASN1Primitive();
    }

    public byte[] getOctets() {
        if (this.padBits == 0) {
            return Arrays.clone(this.data);
        }
        throw new IllegalStateException("attempt to get non-octet aligned data from BIT STRING");
    }

    public int getPadBits() {
        return this.padBits;
    }

    @Override
    public String getString() {
        StringBuffer stringBuffer = new StringBuffer("#");
        Object object = new ByteArrayOutputStream();
        ASN1OutputStream aSN1OutputStream = new ASN1OutputStream((OutputStream)object);
        try {
            aSN1OutputStream.writeObject(this);
            object = ((ByteArrayOutputStream)object).toByteArray();
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Internal error encoding BitString: ");
            ((StringBuilder)object).append(iOException.getMessage());
            throw new ASN1ParsingException(((StringBuilder)object).toString(), iOException);
        }
        for (int i = 0; i != ((Object)object).length; ++i) {
            stringBuffer.append(table[object[i] >>> 4 & 15]);
            stringBuffer.append(table[object[i] & 15]);
        }
        return stringBuffer.toString();
    }

    @Override
    public int hashCode() {
        return this.padBits ^ Arrays.hashCode(this.getBytes());
    }

    public int intValue() {
        int n = 0;
        byte[] arrby = this.data;
        int n2 = this.padBits;
        byte[] arrby2 = arrby;
        if (n2 > 0) {
            byte[] arrby3 = this.data;
            arrby2 = arrby;
            if (arrby3.length <= 4) {
                arrby2 = ASN1BitString.derForm(arrby3, n2);
            }
        }
        for (n2 = 0; n2 != arrby2.length && n2 != 4; ++n2) {
            n |= (arrby2[n2] & 255) << n2 * 8;
        }
        return n;
    }

    @Override
    ASN1Primitive toDERObject() {
        return new DERBitString(this.data, this.padBits);
    }

    @Override
    ASN1Primitive toDLObject() {
        return new DLBitString(this.data, this.padBits);
    }

    public String toString() {
        return this.getString();
    }
}

