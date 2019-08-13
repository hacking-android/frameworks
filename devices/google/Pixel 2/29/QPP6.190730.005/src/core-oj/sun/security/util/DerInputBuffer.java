/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Date;
import java.util.TimeZone;
import sun.security.util.BitArray;
import sun.util.calendar.CalendarDate;
import sun.util.calendar.CalendarSystem;
import sun.util.calendar.Gregorian;

class DerInputBuffer
extends ByteArrayInputStream
implements Cloneable {
    DerInputBuffer(byte[] arrby) {
        super(arrby);
    }

    DerInputBuffer(byte[] arrby, int n, int n2) {
        super(arrby, n, n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Date getTime(int object, boolean bl) throws IOException {
        int n;
        String string;
        int n2;
        int n3;
        int n4;
        Object object2;
        int n5;
        int n6;
        int n7;
        block19 : {
            block17 : {
                int n8;
                Object object3;
                block18 : {
                    if (bl) {
                        string = "Generalized";
                        object2 = this.buf;
                        n = this.pos;
                        this.pos = n + 1;
                        n = Character.digit((char)object2[n], 10);
                        object2 = this.buf;
                        n6 = this.pos;
                        this.pos = n6 + 1;
                        n6 = Character.digit((char)object2[n6], 10);
                        object2 = this.buf;
                        n4 = this.pos;
                        this.pos = n4 + 1;
                        object3 = Character.digit((char)object2[n4], 10);
                        object2 = this.buf;
                        n4 = this.pos;
                        this.pos = n4 + 1;
                        n = n * 1000 + n6 * 100 + object3 * 10 + Character.digit((char)object2[n4], 10);
                        object -= 2;
                    } else {
                        string = "UTC";
                        object2 = this.buf;
                        n = this.pos;
                        this.pos = n + 1;
                        n6 = Character.digit((char)object2[n], 10);
                        object2 = this.buf;
                        n = this.pos;
                        this.pos = n + 1;
                        n = (n = n6 * 10 + Character.digit((char)object2[n], 10)) < 50 ? (n += 2000) : (n += 1900);
                    }
                    object2 = this.buf;
                    n6 = this.pos;
                    this.pos = n6 + 1;
                    n6 = Character.digit((char)object2[n6], 10);
                    object2 = this.buf;
                    n4 = this.pos;
                    this.pos = n4 + 1;
                    n7 = n6 * 10 + Character.digit((char)object2[n4], 10);
                    object2 = this.buf;
                    n6 = this.pos;
                    this.pos = n6 + 1;
                    n4 = Character.digit((char)object2[n6], 10);
                    object2 = this.buf;
                    n6 = this.pos;
                    this.pos = n6 + 1;
                    n5 = n4 * 10 + Character.digit((char)object2[n6], 10);
                    object2 = this.buf;
                    n6 = this.pos;
                    this.pos = n6 + 1;
                    n6 = Character.digit((char)object2[n6], 10);
                    object2 = this.buf;
                    n4 = this.pos;
                    this.pos = n4 + 1;
                    n3 = n6 * 10 + Character.digit((char)object2[n4], 10);
                    object2 = this.buf;
                    n6 = this.pos;
                    this.pos = n6 + 1;
                    n6 = Character.digit((char)object2[n6], 10);
                    object2 = this.buf;
                    n4 = this.pos;
                    this.pos = n4 + 1;
                    n2 = n6 * 10 + Character.digit((char)object2[n4], 10);
                    n6 = object - 10;
                    n4 = 0;
                    if (n6 <= 2 || n6 >= 12) break block17;
                    object2 = this.buf;
                    object = this.pos;
                    this.pos = object + 1;
                    object3 = Character.digit((char)object2[object], 10);
                    object2 = this.buf;
                    object = this.pos;
                    this.pos = object + 1;
                    object3 = object3 * 10 + Character.digit((char)object2[object], 10);
                    n8 = n6 - 2;
                    if (this.buf[this.pos] == 46) break block18;
                    n6 = n8;
                    object = object3;
                    if (this.buf[this.pos] != 44) break block19;
                }
                ++this.pos;
                n6 = 0;
                object = this.pos;
                while (this.buf[object] != 90 && this.buf[object] != 43 && this.buf[object] != 45) {
                    ++object;
                    ++n6;
                }
                if (n6 != 1) {
                    if (n6 != 2) {
                        if (n6 != 3) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Parse ");
                            ((StringBuilder)object2).append(string);
                            ((StringBuilder)object2).append(" time, unsupported precision for seconds value");
                            throw new IOException(((StringBuilder)object2).toString());
                        }
                        object2 = this.buf;
                        object = this.pos;
                        this.pos = object + 1;
                        object = Character.digit((char)object2[object], 10);
                        object2 = this.buf;
                        n4 = this.pos;
                        this.pos = n4 + 1;
                        n4 = Character.digit((char)object2[n4], 10);
                        object2 = this.buf;
                        int n9 = this.pos;
                        this.pos = n9 + 1;
                        object = 0 + object * 100 + n4 * 10 + Character.digit((char)object2[n9], 10);
                    } else {
                        object2 = this.buf;
                        object = this.pos;
                        this.pos = object + 1;
                        object = Character.digit((char)object2[object], 10);
                        object2 = this.buf;
                        n4 = this.pos;
                        this.pos = n4 + 1;
                        object = 0 + object * 100 + Character.digit((char)object2[n4], 10) * 10;
                    }
                } else {
                    object2 = this.buf;
                    object = this.pos;
                    this.pos = object + 1;
                    object = 0 + Character.digit((char)object2[object], 10) * 100;
                }
                n6 = n8 - 1 - n6;
                n4 = object;
                object = object3;
                break block19;
            }
            object = 0;
        }
        if (n7 != 0 && n5 != 0 && n7 <= 12 && n5 <= 31 && n3 < 24 && n2 < 60 && object < 60) {
            Gregorian gregorian = CalendarSystem.getGregorianCalendar();
            object2 = ((CalendarSystem)gregorian).newCalendarDate(null);
            ((CalendarDate)object2).setDate(n, n7, n5);
            ((CalendarDate)object2).setTimeOfDay(n3, n2, (int)object, n4);
            long l = ((CalendarSystem)gregorian).getTime((CalendarDate)object2);
            if (n6 != 1 && n6 != 5) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Parse ");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append(" time, invalid offset");
                throw new IOException(((StringBuilder)object2).toString());
            }
            object2 = this.buf;
            object = this.pos;
            this.pos = object + 1;
            if ((object = (Object)object2[object]) != 43) {
                if (object != 45) {
                    if (object == 90) {
                        return new Date(l);
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Parse ");
                    ((StringBuilder)object2).append(string);
                    ((StringBuilder)object2).append(" time, garbage offset");
                    throw new IOException(((StringBuilder)object2).toString());
                }
                object2 = this.buf;
                object = this.pos;
                this.pos = object + 1;
                n = Character.digit((char)object2[object], 10);
                object2 = this.buf;
                object = this.pos;
                this.pos = object + 1;
                object = n * 10 + Character.digit((char)object2[object], 10);
                object2 = this.buf;
                n = this.pos;
                this.pos = n + 1;
                n = Character.digit((char)object2[n], 10);
                object2 = this.buf;
                n6 = this.pos;
                this.pos = n6 + 1;
                n = n * 10 + Character.digit((char)object2[n6], 10);
                if (object < 24 && n < 60) {
                    return new Date(l += (long)((object * 60 + n) * 60 * 1000));
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Parse ");
                ((StringBuilder)object2).append(string);
                ((StringBuilder)object2).append(" time, -hhmm");
                throw new IOException(((StringBuilder)object2).toString());
            }
            object2 = this.buf;
            object = this.pos;
            this.pos = object + 1;
            n = Character.digit((char)object2[object], 10);
            object2 = this.buf;
            object = this.pos;
            this.pos = object + 1;
            object = n * 10 + Character.digit((char)object2[object], 10);
            object2 = this.buf;
            n = this.pos;
            this.pos = n + 1;
            n = Character.digit((char)object2[n], 10);
            object2 = this.buf;
            n6 = this.pos;
            this.pos = n6 + 1;
            n = n * 10 + Character.digit((char)object2[n6], 10);
            if (object < 24 && n < 60) {
                l -= (long)((object * 60 + n) * 60 * 1000);
                return new Date(l);
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Parse ");
            ((StringBuilder)object2).append(string);
            ((StringBuilder)object2).append(" time, +hhmm");
            throw new IOException(((StringBuilder)object2).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Parse ");
        ((StringBuilder)object2).append(string);
        ((StringBuilder)object2).append(" time, invalid format");
        throw new IOException(((StringBuilder)object2).toString());
    }

    DerInputBuffer dup() {
        try {
            DerInputBuffer derInputBuffer = (DerInputBuffer)this.clone();
            derInputBuffer.mark(Integer.MAX_VALUE);
            return derInputBuffer;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new IllegalArgumentException(cloneNotSupportedException.toString());
        }
    }

    public boolean equals(Object object) {
        if (object instanceof DerInputBuffer) {
            return this.equals((DerInputBuffer)object);
        }
        return false;
    }

    boolean equals(DerInputBuffer derInputBuffer) {
        if (this == derInputBuffer) {
            return true;
        }
        int n = this.available();
        if (derInputBuffer.available() != n) {
            return false;
        }
        for (int i = 0; i < n; ++i) {
            if (this.buf[this.pos + i] == derInputBuffer.buf[derInputBuffer.pos + i]) continue;
            return false;
        }
        return true;
    }

    BigInteger getBigInteger(int n, boolean bl) throws IOException {
        if (n <= this.available()) {
            if (n != 0) {
                byte[] arrby = new byte[n];
                System.arraycopy(this.buf, this.pos, arrby, 0, n);
                this.skip(n);
                if (n >= 2 && arrby[0] == 0 && arrby[1] >= 0) {
                    throw new IOException("Invalid encoding: redundant leading 0s");
                }
                if (bl) {
                    return new BigInteger(1, arrby);
                }
                return new BigInteger(arrby);
            }
            throw new IOException("Invalid encoding: zero length Int value");
        }
        throw new IOException("short read of integer");
    }

    byte[] getBitString() throws IOException {
        return this.getBitString(this.available());
    }

    public byte[] getBitString(int n) throws IOException {
        if (n <= this.available()) {
            if (n != 0) {
                byte by = this.buf[this.pos];
                if (by >= 0 && by <= 7) {
                    byte[] arrby = new byte[n - 1];
                    System.arraycopy(this.buf, this.pos + 1, arrby, 0, n - 1);
                    if (by != 0) {
                        int n2 = n - 2;
                        arrby[n2] = (byte)(arrby[n2] & 255 << by);
                    }
                    this.skip(n);
                    return arrby;
                }
                throw new IOException("Invalid number of padding bits");
            }
            throw new IOException("Invalid encoding: zero length bit string");
        }
        throw new IOException("short read of bit string");
    }

    public Date getGeneralizedTime(int n) throws IOException {
        if (n <= this.available()) {
            if (n >= 13 && n <= 23) {
                return this.getTime(n, true);
            }
            throw new IOException("DER Generalized Time length error");
        }
        throw new IOException("short read of DER Generalized Time");
    }

    public int getInteger(int n) throws IOException {
        BigInteger bigInteger = this.getBigInteger(n, false);
        if (bigInteger.compareTo(BigInteger.valueOf(Integer.MIN_VALUE)) >= 0) {
            if (bigInteger.compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) <= 0) {
                return bigInteger.intValue();
            }
            throw new IOException("Integer exceeds maximum valid value");
        }
        throw new IOException("Integer below minimum valid value");
    }

    int getPos() {
        return this.pos;
    }

    byte[] getSlice(int n, int n2) {
        byte[] arrby = new byte[n2];
        System.arraycopy(this.buf, n, arrby, 0, n2);
        return arrby;
    }

    public Date getUTCTime(int n) throws IOException {
        if (n <= this.available()) {
            if (n >= 11 && n <= 17) {
                return this.getTime(n, false);
            }
            throw new IOException("DER UTC Time length error");
        }
        throw new IOException("short read of DER UTC Time");
    }

    BitArray getUnalignedBitString() throws IOException {
        if (this.pos >= this.count) {
            return null;
        }
        int n = this.available();
        int n2 = this.buf[this.pos] & 255;
        if (n2 <= 7) {
            Object object = new byte[n - 1];
            n2 = ((byte[])object).length == 0 ? 0 : ((byte[])object).length * 8 - n2;
            System.arraycopy(this.buf, this.pos + 1, object, 0, n - 1);
            object = new BitArray(n2, (byte[])object);
            this.pos = this.count;
            return object;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid value for unused bits: ");
        stringBuilder.append(n2);
        throw new IOException(stringBuilder.toString());
    }

    public int hashCode() {
        int n = 0;
        int n2 = this.available();
        int n3 = this.pos;
        for (int i = 0; i < n2; ++i) {
            n += this.buf[n3 + i] * i;
        }
        return n;
    }

    int peek() throws IOException {
        if (this.pos < this.count) {
            return this.buf[this.pos];
        }
        throw new IOException("out of data");
    }

    byte[] toByteArray() {
        int n = this.available();
        if (n <= 0) {
            return null;
        }
        byte[] arrby = new byte[n];
        System.arraycopy(this.buf, this.pos, arrby, 0, n);
        return arrby;
    }

    void truncate(int n) throws IOException {
        if (n <= this.available()) {
            this.count = this.pos + n;
            return;
        }
        throw new IOException("insufficient data");
    }
}

