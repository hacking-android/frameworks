/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.Vector;
import sun.security.util.BitArray;
import sun.security.util.DerIndefLenConverter;
import sun.security.util.DerInputBuffer;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class DerInputStream {
    DerInputBuffer buffer;
    public byte tag;

    DerInputStream(DerInputBuffer derInputBuffer) {
        this.buffer = derInputBuffer;
        this.buffer.mark(Integer.MAX_VALUE);
    }

    public DerInputStream(byte[] arrby) throws IOException {
        this.init(arrby, 0, arrby.length, true);
    }

    public DerInputStream(byte[] arrby, int n, int n2) throws IOException {
        this.init(arrby, n, n2, true);
    }

    public DerInputStream(byte[] arrby, int n, int n2, boolean bl) throws IOException {
        this.init(arrby, n, n2, bl);
    }

    static int getLength(int n, InputStream object) throws IOException {
        block5 : {
            int n2;
            block7 : {
                block8 : {
                    block9 : {
                        block10 : {
                            block6 : {
                                if (n == -1) break block5;
                                if ((n & 128) == 0) break block6;
                                n2 = n & 127;
                                if (n2 == 0) {
                                    return -1;
                                }
                                if (n2 < 0 || n2 > 4) break block7;
                                n = ((InputStream)object).read() & 255;
                                --n2;
                                if (n == 0) break block8;
                                while (n2 > 0) {
                                    n = (n << 8) + (((InputStream)object).read() & 255);
                                    --n2;
                                }
                                if (n < 0) break block9;
                                if (n <= 127) break block10;
                            }
                            return n;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("DerInputStream.getLength(): ");
                        ((StringBuilder)object).append("Should use short form for length");
                        throw new IOException(((StringBuilder)object).toString());
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("DerInputStream.getLength(): ");
                    ((StringBuilder)object).append("Invalid length bytes");
                    throw new IOException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("DerInputStream.getLength(): ");
                ((StringBuilder)object).append("Redundant length bytes found");
                throw new IOException(((StringBuilder)object).toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DerInputStream.getLength(): ");
            stringBuilder.append("lengthTag=");
            stringBuilder.append(n2);
            stringBuilder.append(", ");
            object = n2 < 0 ? "incorrect DER encoding." : "too big.";
            stringBuilder.append((String)object);
            throw new IOException(stringBuilder.toString());
        }
        throw new IOException("Short read of DER length");
    }

    static int getLength(InputStream inputStream) throws IOException {
        return DerInputStream.getLength(inputStream.read(), inputStream);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void init(byte[] arrby, int n, int n2, boolean bl) throws IOException {
        if (n + 2 > arrby.length || n + n2 > arrby.length) throw new IOException("Encoding bytes too short");
        if (DerIndefLenConverter.isIndefinite(arrby[n + 1])) {
            if (!bl) throw new IOException("Indefinite length BER encoding found");
            byte[] arrby2 = new byte[n2];
            System.arraycopy(arrby, n, arrby2, 0, n2);
            this.buffer = new DerInputBuffer(new DerIndefLenConverter().convert(arrby2));
        } else {
            this.buffer = new DerInputBuffer(arrby, n, n2);
        }
        this.buffer.mark(Integer.MAX_VALUE);
    }

    private String readString(byte by, String string, String charSequence) throws IOException {
        if (this.buffer.read() == by) {
            by = (byte)DerInputStream.getLength(this.buffer);
            byte[] arrby = new byte[by];
            if (by != 0 && this.buffer.read(arrby) != by) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Short read of DER ");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" string");
                throw new IOException(((StringBuilder)charSequence).toString());
            }
            return new String(arrby, (String)charSequence);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("DER input not a ");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(" string");
        throw new IOException(((StringBuilder)charSequence).toString());
    }

    public int available() {
        return this.buffer.available();
    }

    public String getBMPString() throws IOException {
        return this.readString((byte)30, "BMP", "UnicodeBigUnmarked");
    }

    public BigInteger getBigInteger() throws IOException {
        if (this.buffer.read() == 2) {
            DerInputBuffer derInputBuffer = this.buffer;
            return derInputBuffer.getBigInteger(DerInputStream.getLength(derInputBuffer), false);
        }
        throw new IOException("DER input, Integer tag error");
    }

    public byte[] getBitString() throws IOException {
        if (this.buffer.read() == 3) {
            DerInputBuffer derInputBuffer = this.buffer;
            return derInputBuffer.getBitString(DerInputStream.getLength(derInputBuffer));
        }
        throw new IOException("DER input not an bit string");
    }

    int getByte() throws IOException {
        return this.buffer.read() & 255;
    }

    public void getBytes(byte[] arrby) throws IOException {
        if (arrby.length != 0 && this.buffer.read(arrby) != arrby.length) {
            throw new IOException("Short read of DER octet string");
        }
    }

    public DerValue getDerValue() throws IOException {
        return new DerValue(this.buffer);
    }

    public int getEnumerated() throws IOException {
        if (this.buffer.read() == 10) {
            DerInputBuffer derInputBuffer = this.buffer;
            return derInputBuffer.getInteger(DerInputStream.getLength(derInputBuffer));
        }
        throw new IOException("DER input, Enumerated tag error");
    }

    public String getGeneralString() throws IOException {
        return this.readString((byte)27, "General", "ASCII");
    }

    public Date getGeneralizedTime() throws IOException {
        if (this.buffer.read() == 24) {
            DerInputBuffer derInputBuffer = this.buffer;
            return derInputBuffer.getGeneralizedTime(DerInputStream.getLength(derInputBuffer));
        }
        throw new IOException("DER input, GeneralizedTime tag invalid ");
    }

    public String getIA5String() throws IOException {
        return this.readString((byte)22, "IA5", "ASCII");
    }

    public int getInteger() throws IOException {
        if (this.buffer.read() == 2) {
            DerInputBuffer derInputBuffer = this.buffer;
            return derInputBuffer.getInteger(DerInputStream.getLength(derInputBuffer));
        }
        throw new IOException("DER input, Integer tag error");
    }

    int getLength() throws IOException {
        return DerInputStream.getLength(this.buffer);
    }

    public void getNull() throws IOException {
        if (this.buffer.read() == 5 && this.buffer.read() == 0) {
            return;
        }
        throw new IOException("getNull, bad data");
    }

    public ObjectIdentifier getOID() throws IOException {
        return new ObjectIdentifier(this);
    }

    public byte[] getOctetString() throws IOException {
        if (this.buffer.read() == 4) {
            int n = DerInputStream.getLength(this.buffer);
            byte[] arrby = new byte[n];
            if (n != 0 && this.buffer.read(arrby) != n) {
                throw new IOException("Short read of DER octet string");
            }
            return arrby;
        }
        throw new IOException("DER input not an octet string");
    }

    public BigInteger getPositiveBigInteger() throws IOException {
        if (this.buffer.read() == 2) {
            DerInputBuffer derInputBuffer = this.buffer;
            return derInputBuffer.getBigInteger(DerInputStream.getLength(derInputBuffer), true);
        }
        throw new IOException("DER input, Integer tag error");
    }

    public String getPrintableString() throws IOException {
        return this.readString((byte)19, "Printable", "ASCII");
    }

    public DerValue[] getSequence(int n) throws IOException {
        return this.getSequence(n, false);
    }

    public DerValue[] getSequence(int n, boolean bl) throws IOException {
        this.tag = (byte)this.buffer.read();
        if (this.tag == 48) {
            return this.readVector(n, bl);
        }
        throw new IOException("Sequence tag error");
    }

    public DerValue[] getSet(int n) throws IOException {
        this.tag = (byte)this.buffer.read();
        if (this.tag == 49) {
            return this.readVector(n);
        }
        throw new IOException("Set tag error");
    }

    public DerValue[] getSet(int n, boolean bl) throws IOException {
        return this.getSet(n, bl, false);
    }

    public DerValue[] getSet(int n, boolean bl, boolean bl2) throws IOException {
        this.tag = (byte)this.buffer.read();
        if (!bl && this.tag != 49) {
            throw new IOException("Set tag error");
        }
        return this.readVector(n, bl2);
    }

    public String getT61String() throws IOException {
        return this.readString((byte)20, "T61", "ISO-8859-1");
    }

    public Date getUTCTime() throws IOException {
        if (this.buffer.read() == 23) {
            DerInputBuffer derInputBuffer = this.buffer;
            return derInputBuffer.getUTCTime(DerInputStream.getLength(derInputBuffer));
        }
        throw new IOException("DER input, UTCtime tag invalid ");
    }

    public String getUTF8String() throws IOException {
        return this.readString((byte)12, "UTF-8", "UTF8");
    }

    public BitArray getUnalignedBitString() throws IOException {
        if (this.buffer.read() == 3) {
            int n = DerInputStream.getLength(this.buffer) - 1;
            int n2 = this.buffer.read();
            if (n2 >= 0) {
                if ((n2 = n * 8 - n2) >= 0) {
                    byte[] arrby = new byte[n];
                    if (n != 0 && this.buffer.read(arrby) != n) {
                        throw new IOException("Short read of DER bit string");
                    }
                    return new BitArray(n2, arrby);
                }
                throw new IOException("Valid bits of bit string invalid");
            }
            throw new IOException("Unused bits of bit string invalid");
        }
        throw new IOException("DER input not a bit string");
    }

    public void mark(int n) {
        this.buffer.mark(n);
    }

    public int peekByte() throws IOException {
        return this.buffer.peek();
    }

    protected DerValue[] readVector(int n) throws IOException {
        return this.readVector(n, false);
    }

    protected DerValue[] readVector(int n, boolean bl) throws IOException {
        int n2;
        Object object;
        Object object2;
        byte by = (byte)this.buffer.read();
        int n3 = n2 = DerInputStream.getLength(by, this.buffer);
        if (n2 == -1) {
            n3 = this.buffer.available();
            object2 = new byte[n3 + 2];
            object2[0] = this.tag;
            object2[1] = by;
            object = new DataInputStream(this.buffer);
            ((DataInputStream)object).readFully((byte[])object2, 2, n3);
            ((FilterInputStream)object).close();
            this.buffer = new DerInputBuffer(new DerIndefLenConverter().convert((byte[])object2));
            if (this.tag == this.buffer.read()) {
                n3 = DerInputStream.getLength(this.buffer);
            } else {
                throw new IOException("Indefinite length encoding not supported");
            }
        }
        if (n3 == 0) {
            return new DerValue[0];
        }
        object2 = this.buffer.available() == n3 ? this : this.subStream(n3, true);
        object = new Vector<DerValue>(n);
        do {
            ((Vector)object).addElement(new DerValue(((DerInputStream)object2).buffer, bl));
        } while (((DerInputStream)object2).available() > 0);
        if (((DerInputStream)object2).available() == 0) {
            n3 = ((Vector)object).size();
            object2 = new DerValue[n3];
            for (n = 0; n < n3; ++n) {
                object2[n] = (DerValue)((Vector)object).elementAt(n);
            }
            return object2;
        }
        throw new IOException("Extra data at end of vector");
    }

    public void reset() {
        this.buffer.reset();
    }

    public DerInputStream subStream(int n, boolean bl) throws IOException {
        DerInputBuffer derInputBuffer = this.buffer.dup();
        derInputBuffer.truncate(n);
        if (bl) {
            this.buffer.skip(n);
        }
        return new DerInputStream(derInputBuffer);
    }

    public byte[] toByteArray() {
        return this.buffer.toByteArray();
    }
}

