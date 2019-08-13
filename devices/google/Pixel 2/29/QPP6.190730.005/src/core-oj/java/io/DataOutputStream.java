/*
 * Decompiled with CFR 0.145.
 */
package java.io;

import java.io.DataOutput;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UTFDataFormatException;

public class DataOutputStream
extends FilterOutputStream
implements DataOutput {
    private byte[] bytearr = null;
    private byte[] writeBuffer = new byte[8];
    protected int written;

    public DataOutputStream(OutputStream outputStream) {
        super(outputStream);
    }

    private void incCount(int n) {
        int n2;
        n = n2 = this.written + n;
        if (n2 < 0) {
            n = Integer.MAX_VALUE;
        }
        this.written = n;
    }

    static int writeUTF(String charSequence, DataOutput dataOutput) throws IOException {
        int n;
        int n2;
        int n3 = ((String)charSequence).length();
        int n4 = 0;
        for (n2 = 0; n2 < n3; ++n2) {
            n = ((String)charSequence).charAt(n2);
            n = n >= 1 && n <= 127 ? n4 + 1 : (n > 2047 ? n4 + 3 : n4 + 2);
            n4 = n;
        }
        if (n4 <= 65535) {
            int n5;
            int n6;
            byte[] arrby;
            int n7;
            if (dataOutput instanceof DataOutputStream) {
                arrby = (byte[])dataOutput;
                byte[] arrby2 = arrby.bytearr;
                if (arrby2 == null || arrby2.length < n4 + 2) {
                    arrby.bytearr = new byte[n4 * 2 + 2];
                }
                arrby = arrby.bytearr;
            } else {
                arrby = new byte[n4 + 2];
            }
            n2 = 0 + 1;
            arrby[0] = (byte)(n4 >>> 8 & 255);
            n = n2 + 1;
            arrby[n2] = (byte)(n4 >>> 0 & 255);
            n2 = 0;
            do {
                n5 = n;
                n7 = n2;
                if (n2 >= n3) break;
                n6 = ((String)charSequence).charAt(n2);
                n5 = n++;
                n7 = n2++;
                if (n6 < 1) break;
                if (n6 > 127) {
                    n5 = n;
                    n7 = n2;
                    break;
                }
                arrby[n] = (byte)n6;
            } while (true);
            while (n7 < n3) {
                n2 = ((String)charSequence).charAt(n7);
                if (n2 >= 1 && n2 <= 127) {
                    arrby[n5] = (byte)n2;
                    n = n5 + 1;
                } else if (n2 > 2047) {
                    n = n5 + 1;
                    arrby[n5] = (byte)(n2 >> 12 & 15 | 224);
                    n5 = n + 1;
                    arrby[n] = (byte)(n2 >> 6 & 63 | 128);
                    arrby[n5] = (byte)(n2 >> 0 & 63 | 128);
                    n = n5 + 1;
                } else {
                    n6 = n5 + 1;
                    arrby[n5] = (byte)(n2 >> 6 & 31 | 192);
                    n = n6 + 1;
                    arrby[n6] = (byte)(n2 >> 0 & 63 | 128);
                }
                ++n7;
                n5 = n;
            }
            dataOutput.write(arrby, 0, n4 + 2);
            return n4 + 2;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("encoded string too long: ");
        ((StringBuilder)charSequence).append(n4);
        ((StringBuilder)charSequence).append(" bytes");
        throw new UTFDataFormatException(((StringBuilder)charSequence).toString());
    }

    @Override
    public void flush() throws IOException {
        this.out.flush();
    }

    public final int size() {
        return this.written;
    }

    @Override
    public void write(int n) throws IOException {
        synchronized (this) {
            this.out.write(n);
            this.incCount(1);
            return;
        }
    }

    @Override
    public void write(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            this.out.write(arrby, n, n2);
            this.incCount(n2);
            return;
        }
    }

    @Override
    public final void writeBoolean(boolean bl) throws IOException {
        this.out.write((int)bl);
        this.incCount(1);
    }

    @Override
    public final void writeByte(int n) throws IOException {
        this.out.write(n);
        this.incCount(1);
    }

    @Override
    public final void writeBytes(String string) throws IOException {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            this.out.write((byte)string.charAt(i));
        }
        this.incCount(n);
    }

    @Override
    public final void writeChar(int n) throws IOException {
        this.out.write(n >>> 8 & 255);
        this.out.write(n >>> 0 & 255);
        this.incCount(2);
    }

    @Override
    public final void writeChars(String string) throws IOException {
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            this.out.write(c >>> 8 & 255);
            this.out.write(c >>> 0 & 255);
        }
        this.incCount(n * 2);
    }

    @Override
    public final void writeDouble(double d) throws IOException {
        this.writeLong(Double.doubleToLongBits(d));
    }

    @Override
    public final void writeFloat(float f) throws IOException {
        this.writeInt(Float.floatToIntBits(f));
    }

    @Override
    public final void writeInt(int n) throws IOException {
        this.out.write(n >>> 24 & 255);
        this.out.write(n >>> 16 & 255);
        this.out.write(n >>> 8 & 255);
        this.out.write(n >>> 0 & 255);
        this.incCount(4);
    }

    @Override
    public final void writeLong(long l) throws IOException {
        byte[] arrby = this.writeBuffer;
        arrby[0] = (byte)(l >>> 56);
        arrby[1] = (byte)(l >>> 48);
        arrby[2] = (byte)(l >>> 40);
        arrby[3] = (byte)(l >>> 32);
        arrby[4] = (byte)(l >>> 24);
        arrby[5] = (byte)(l >>> 16);
        arrby[6] = (byte)(l >>> 8);
        arrby[7] = (byte)(l >>> 0);
        this.out.write(this.writeBuffer, 0, 8);
        this.incCount(8);
    }

    @Override
    public final void writeShort(int n) throws IOException {
        this.out.write(n >>> 8 & 255);
        this.out.write(n >>> 0 & 255);
        this.incCount(2);
    }

    @Override
    public final void writeUTF(String string) throws IOException {
        DataOutputStream.writeUTF(string, this);
    }
}

