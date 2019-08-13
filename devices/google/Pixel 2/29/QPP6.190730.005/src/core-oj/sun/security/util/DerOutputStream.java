/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import sun.security.util.BitArray;
import sun.security.util.ByteArrayLexOrder;
import sun.security.util.ByteArrayTagOrder;
import sun.security.util.DerEncoder;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class DerOutputStream
extends ByteArrayOutputStream
implements DerEncoder {
    private static ByteArrayLexOrder lexOrder = new ByteArrayLexOrder();
    private static ByteArrayTagOrder tagOrder = new ByteArrayTagOrder();

    public DerOutputStream() {
    }

    public DerOutputStream(int n) {
        super(n);
    }

    private void putIntegerContents(int n) throws IOException {
        byte[] arrby = new byte[4];
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        arrby[3] = (byte)(n & 255);
        arrby[2] = (byte)((65280 & n) >>> 8);
        arrby[1] = (byte)((16711680 & n) >>> 16);
        arrby[0] = (byte)((-16777216 & n) >>> 24);
        if (arrby[0] == -1) {
            n = n4;
            for (n2 = 0; n2 < 3 && arrby[n2] == -1 && (arrby[n2 + 1] & 128) == 128; ++n2) {
                ++n;
            }
        } else {
            n = n3;
            if (arrby[0] == 0) {
                n4 = 0;
                do {
                    n = n2;
                    if (n4 >= 3) break;
                    n = n2;
                    if (arrby[n4] != 0) break;
                    n = n2++;
                    if ((arrby[n4 + 1] & 128) != 0) break;
                    ++n4;
                } while (true);
            }
        }
        this.putLength(4 - n);
        while (n < 4) {
            this.write(arrby[n]);
            ++n;
        }
    }

    private void putOrderedSet(byte by, DerEncoder[] arrderEncoder, Comparator<byte[]> object) throws IOException {
        int n;
        DerOutputStream[] arrderOutputStream = new DerOutputStream[arrderEncoder.length];
        for (n = 0; n < arrderEncoder.length; ++n) {
            arrderOutputStream[n] = new DerOutputStream();
            arrderEncoder[n].derEncode(arrderOutputStream[n]);
        }
        arrderEncoder = new byte[arrderOutputStream.length][];
        for (n = 0; n < arrderOutputStream.length; ++n) {
            arrderEncoder[n] = arrderOutputStream[n].toByteArray();
        }
        Arrays.sort(arrderEncoder, object);
        object = new DerOutputStream();
        for (n = 0; n < arrderOutputStream.length; ++n) {
            ((OutputStream)object).write((byte[])arrderEncoder[n]);
        }
        this.write(by, (DerOutputStream)object);
    }

    private void putTime(Date arrby, byte by) throws IOException {
        Object object;
        TimeZone timeZone = TimeZone.getTimeZone("GMT");
        if (by == 23) {
            object = "yyMMddHHmmss'Z'";
        } else {
            by = (byte)24;
            object = "yyyyMMddHHmmss'Z'";
        }
        object = new SimpleDateFormat((String)object, Locale.US);
        ((DateFormat)object).setTimeZone(timeZone);
        arrby = ((DateFormat)object).format((Date)arrby).getBytes("ISO-8859-1");
        this.write(by);
        this.putLength(arrby.length);
        this.write(arrby);
    }

    private void writeString(String arrby, byte by, String string) throws IOException {
        arrby = arrby.getBytes(string);
        this.write(by);
        this.putLength(arrby.length);
        this.write(arrby);
    }

    @Override
    public void derEncode(OutputStream outputStream) throws IOException {
        outputStream.write(this.toByteArray());
    }

    public void putBMPString(String string) throws IOException {
        this.writeString(string, (byte)30, "UnicodeBigUnmarked");
    }

    public void putBitString(byte[] arrby) throws IOException {
        this.write(3);
        this.putLength(arrby.length + 1);
        this.write(0);
        this.write(arrby);
    }

    public void putBoolean(boolean bl) throws IOException {
        this.write(1);
        this.putLength(1);
        if (bl) {
            this.write(255);
        } else {
            this.write(0);
        }
    }

    public void putDerValue(DerValue derValue) throws IOException {
        derValue.encode(this);
    }

    public void putEnumerated(int n) throws IOException {
        this.write(10);
        this.putIntegerContents(n);
    }

    public void putGeneralString(String string) throws IOException {
        this.writeString(string, (byte)27, "ASCII");
    }

    public void putGeneralizedTime(Date date) throws IOException {
        this.putTime(date, (byte)24);
    }

    public void putIA5String(String string) throws IOException {
        this.writeString(string, (byte)22, "ASCII");
    }

    public void putInteger(int n) throws IOException {
        this.write(2);
        this.putIntegerContents(n);
    }

    public void putInteger(Integer n) throws IOException {
        this.putInteger((int)n);
    }

    public void putInteger(BigInteger arrby) throws IOException {
        this.write(2);
        arrby = arrby.toByteArray();
        this.putLength(arrby.length);
        this.write(arrby, 0, arrby.length);
    }

    public void putLength(int n) throws IOException {
        if (n < 128) {
            this.write((byte)n);
        } else if (n < 256) {
            this.write(-127);
            this.write((byte)n);
        } else if (n < 65536) {
            this.write(-126);
            this.write((byte)(n >> 8));
            this.write((byte)n);
        } else if (n < 16777216) {
            this.write(-125);
            this.write((byte)(n >> 16));
            this.write((byte)(n >> 8));
            this.write((byte)n);
        } else {
            this.write(-124);
            this.write((byte)(n >> 24));
            this.write((byte)(n >> 16));
            this.write((byte)(n >> 8));
            this.write((byte)n);
        }
    }

    public void putNull() throws IOException {
        this.write(5);
        this.putLength(0);
    }

    public void putOID(ObjectIdentifier objectIdentifier) throws IOException {
        objectIdentifier.encode(this);
    }

    public void putOctetString(byte[] arrby) throws IOException {
        this.write((byte)4, arrby);
    }

    public void putOrderedSet(byte by, DerEncoder[] arrderEncoder) throws IOException {
        this.putOrderedSet(by, arrderEncoder, tagOrder);
    }

    public void putOrderedSetOf(byte by, DerEncoder[] arrderEncoder) throws IOException {
        this.putOrderedSet(by, arrderEncoder, lexOrder);
    }

    public void putPrintableString(String string) throws IOException {
        this.writeString(string, (byte)19, "ASCII");
    }

    public void putSequence(DerValue[] arrderValue) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        for (int i = 0; i < arrderValue.length; ++i) {
            arrderValue[i].encode(derOutputStream);
        }
        this.write((byte)48, derOutputStream);
    }

    public void putSet(DerValue[] arrderValue) throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        for (int i = 0; i < arrderValue.length; ++i) {
            arrderValue[i].encode(derOutputStream);
        }
        this.write((byte)49, derOutputStream);
    }

    public void putT61String(String string) throws IOException {
        this.writeString(string, (byte)20, "ISO-8859-1");
    }

    public void putTag(byte by, boolean bl, byte by2) {
        by = by2 = (byte)(by | by2);
        if (bl) {
            by = (byte)(by2 | 32);
        }
        this.write(by);
    }

    public void putTruncatedUnalignedBitString(BitArray bitArray) throws IOException {
        this.putUnalignedBitString(bitArray.truncate());
    }

    public void putUTCTime(Date date) throws IOException {
        this.putTime(date, (byte)23);
    }

    public void putUTF8String(String string) throws IOException {
        this.writeString(string, (byte)12, "UTF8");
    }

    public void putUnalignedBitString(BitArray bitArray) throws IOException {
        byte[] arrby = bitArray.toByteArray();
        this.write(3);
        this.putLength(arrby.length + 1);
        this.write(arrby.length * 8 - bitArray.length());
        this.write(arrby);
    }

    public void write(byte by, DerOutputStream derOutputStream) throws IOException {
        this.write(by);
        this.putLength(derOutputStream.count);
        this.write(derOutputStream.buf, 0, derOutputStream.count);
    }

    public void write(byte by, byte[] arrby) throws IOException {
        this.write(by);
        this.putLength(arrby.length);
        this.write(arrby, 0, arrby.length);
    }

    public void writeImplicit(byte by, DerOutputStream derOutputStream) throws IOException {
        this.write(by);
        this.write(derOutputStream.buf, 1, derOutputStream.count - 1);
    }
}

