/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.Memory
 */
package java.io;

import java.io.DataInput;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UTFDataFormatException;
import java.nio.ByteOrder;
import libcore.io.Memory;

public class DataInputStream
extends FilterInputStream
implements DataInput {
    private byte[] bytearr = new byte[80];
    private char[] chararr = new char[80];
    private char[] lineBuffer;
    private byte[] readBuffer = new byte[8];

    public DataInputStream(InputStream inputStream) {
        super(inputStream);
    }

    public static final String readUTF(DataInput object) throws IOException {
        byte[] arrby;
        char[] arrc;
        int n;
        int n2;
        int n3 = object.readUnsignedShort();
        if (object instanceof DataInputStream) {
            arrby = (byte[])object;
            if (arrby.bytearr.length < n3) {
                arrby.bytearr = new byte[n3 * 2];
                arrby.chararr = new char[n3 * 2];
            }
            arrc = arrby.chararr;
            arrby = arrby.bytearr;
        } else {
            arrby = new byte[n3];
            arrc = new char[n3];
        }
        int n4 = 0;
        int n5 = 0;
        object.readFully(arrby, 0, n3);
        do {
            n = ++n4;
            n2 = n5;
            if (n4 >= n3) break;
            n2 = arrby[n4] & 255;
            if (n2 > 127) {
                n = n4;
                n2 = n5;
                break;
            }
            arrc[n5] = (char)n2;
            ++n5;
        } while (true);
        block6 : while (n < n3) {
            n5 = arrby[n] & 255;
            switch (n5 >> 4) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("malformed input around byte ");
                    ((StringBuilder)object).append(n);
                    throw new UTFDataFormatException(((StringBuilder)object).toString());
                }
                case 14: {
                    if ((n += 3) <= n3) {
                        byte by = arrby[n - 2];
                        n4 = arrby[n - 1];
                        if ((by & 192) == 128 && (n4 & 192) == 128) {
                            arrc[n2] = (char)((n5 & 15) << 12 | (by & 63) << 6 | (n4 & 63) << 0);
                            ++n2;
                            continue block6;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("malformed input around byte ");
                        ((StringBuilder)object).append(n - 1);
                        throw new UTFDataFormatException(((StringBuilder)object).toString());
                    }
                    throw new UTFDataFormatException("malformed input: partial character at end");
                }
                case 12: 
                case 13: {
                    if ((n += 2) <= n3) {
                        n4 = arrby[n - 1];
                        if ((n4 & 192) == 128) {
                            arrc[n2] = (char)((n5 & 31) << 6 | n4 & 63);
                            ++n2;
                            continue block6;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("malformed input around byte ");
                        ((StringBuilder)object).append(n);
                        throw new UTFDataFormatException(((StringBuilder)object).toString());
                    }
                    throw new UTFDataFormatException("malformed input: partial character at end");
                }
                case 0: 
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
            }
            ++n;
            arrc[n2] = (char)n5;
            ++n2;
        }
        return new String(arrc, 0, n2);
    }

    @Override
    public final int read(byte[] arrby) throws IOException {
        return this.in.read(arrby, 0, arrby.length);
    }

    @Override
    public final int read(byte[] arrby, int n, int n2) throws IOException {
        return this.in.read(arrby, n, n2);
    }

    @Override
    public final boolean readBoolean() throws IOException {
        int n = this.in.read();
        if (n >= 0) {
            boolean bl = n != 0;
            return bl;
        }
        throw new EOFException();
    }

    @Override
    public final byte readByte() throws IOException {
        int n = this.in.read();
        if (n >= 0) {
            return (byte)n;
        }
        throw new EOFException();
    }

    @Override
    public final char readChar() throws IOException {
        this.readFully(this.readBuffer, 0, 2);
        return (char)Memory.peekShort((byte[])this.readBuffer, (int)0, (ByteOrder)ByteOrder.BIG_ENDIAN);
    }

    @Override
    public final double readDouble() throws IOException {
        return Double.longBitsToDouble(this.readLong());
    }

    @Override
    public final float readFloat() throws IOException {
        return Float.intBitsToFloat(this.readInt());
    }

    @Override
    public final void readFully(byte[] arrby) throws IOException {
        this.readFully(arrby, 0, arrby.length);
    }

    @Override
    public final void readFully(byte[] arrby, int n, int n2) throws IOException {
        if (n2 >= 0) {
            int n3;
            for (int i = 0; i < n2; i += n3) {
                n3 = this.in.read(arrby, n + i, n2 - i);
                if (n3 >= 0) {
                    continue;
                }
                throw new EOFException();
            }
            return;
        }
        throw new IndexOutOfBoundsException();
    }

    @Override
    public final int readInt() throws IOException {
        this.readFully(this.readBuffer, 0, 4);
        return Memory.peekInt((byte[])this.readBuffer, (int)0, (ByteOrder)ByteOrder.BIG_ENDIAN);
    }

    @Deprecated
    @Override
    public final String readLine() throws IOException {
        int n;
        char[] arrc;
        char[] arrc2 = arrc = this.lineBuffer;
        if (arrc == null) {
            this.lineBuffer = arrc2 = new char[128];
        }
        int n2 = arrc2.length;
        int n3 = 0;
        while ((n = this.in.read()) != -1 && n != 10) {
            if (n != 13) {
                int n4;
                n2 = n4 = n2 - 1;
                if (n4 < 0) {
                    arrc2 = new char[n3 + 128];
                    n2 = arrc2.length;
                    System.arraycopy((Object)this.lineBuffer, 0, (Object)arrc2, 0, n3);
                    this.lineBuffer = arrc2;
                    n2 = n2 - n3 - 1;
                }
                arrc2[n3] = (char)n;
                ++n3;
                continue;
            }
            n2 = this.in.read();
            if (n2 == 10 || n2 == -1) break;
            if (!(this.in instanceof PushbackInputStream)) {
                this.in = new PushbackInputStream(this.in);
            }
            ((PushbackInputStream)this.in).unread(n2);
            break;
        }
        if (n == -1 && n3 == 0) {
            return null;
        }
        return String.copyValueOf(arrc2, 0, n3);
    }

    @Override
    public final long readLong() throws IOException {
        this.readFully(this.readBuffer, 0, 8);
        byte[] arrby = this.readBuffer;
        return ((long)arrby[0] << 56) + ((long)(arrby[1] & 255) << 48) + ((long)(arrby[2] & 255) << 40) + ((long)(arrby[3] & 255) << 32) + ((long)(arrby[4] & 255) << 24) + (long)((arrby[5] & 255) << 16) + (long)((arrby[6] & 255) << 8) + (long)((arrby[7] & 255) << 0);
    }

    @Override
    public final short readShort() throws IOException {
        this.readFully(this.readBuffer, 0, 2);
        return Memory.peekShort((byte[])this.readBuffer, (int)0, (ByteOrder)ByteOrder.BIG_ENDIAN);
    }

    @Override
    public final String readUTF() throws IOException {
        return DataInputStream.readUTF(this);
    }

    @Override
    public final int readUnsignedByte() throws IOException {
        int n = this.in.read();
        if (n >= 0) {
            return n;
        }
        throw new EOFException();
    }

    @Override
    public final int readUnsignedShort() throws IOException {
        this.readFully(this.readBuffer, 0, 2);
        return Memory.peekShort((byte[])this.readBuffer, (int)0, (ByteOrder)ByteOrder.BIG_ENDIAN) & 65535;
    }

    @Override
    public final int skipBytes(int n) throws IOException {
        int n2;
        int n3;
        for (n3 = 0; n3 < n && (n2 = (int)this.in.skip(n - n3)) > 0; n3 += n2) {
        }
        return n3;
    }
}

