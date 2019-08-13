/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import sun.misc.IOUtils;
import sun.security.util.BitArray;
import sun.security.util.DerIndefLenConverter;
import sun.security.util.DerInputBuffer;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.ObjectIdentifier;

public class DerValue {
    public static final byte TAG_APPLICATION = 64;
    public static final byte TAG_CONTEXT = -128;
    public static final byte TAG_PRIVATE = -64;
    public static final byte TAG_UNIVERSAL = 0;
    public static final byte tag_BMPString = 30;
    public static final byte tag_BitString = 3;
    public static final byte tag_Boolean = 1;
    public static final byte tag_Enumerated = 10;
    public static final byte tag_GeneralString = 27;
    public static final byte tag_GeneralizedTime = 24;
    public static final byte tag_IA5String = 22;
    public static final byte tag_Integer = 2;
    public static final byte tag_Null = 5;
    public static final byte tag_ObjectId = 6;
    public static final byte tag_OctetString = 4;
    public static final byte tag_PrintableString = 19;
    public static final byte tag_Sequence = 48;
    public static final byte tag_SequenceOf = 48;
    public static final byte tag_Set = 49;
    public static final byte tag_SetOf = 49;
    public static final byte tag_T61String = 20;
    public static final byte tag_UTF8String = 12;
    public static final byte tag_UniversalString = 28;
    public static final byte tag_UtcTime = 23;
    protected DerInputBuffer buffer;
    public final DerInputStream data;
    private int length;
    private byte[] originalEncodedForm;
    public byte tag;

    public DerValue(byte by, String string) throws IOException {
        this.data = this.init(by, string);
    }

    public DerValue(byte by, byte[] arrby) {
        this.tag = by;
        this.buffer = new DerInputBuffer((byte[])arrby.clone());
        this.length = arrby.length;
        this.data = new DerInputStream(this.buffer);
        this.data.mark(Integer.MAX_VALUE);
    }

    public DerValue(InputStream inputStream) throws IOException {
        this.data = this.init(false, inputStream);
    }

    public DerValue(String string) throws IOException {
        byte by;
        byte by2 = 1;
        int n = 0;
        do {
            by = by2;
            if (n >= string.length()) break;
            if (!DerValue.isPrintableStringChar(string.charAt(n))) {
                by = 0;
                break;
            }
            ++n;
        } while (true);
        byte by3 = by != 0 ? (by = 19) : (by = 12);
        this.data = this.init(by3, string);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    DerValue(DerInputBuffer derInputBuffer, boolean bl) throws IOException {
        int n = derInputBuffer.getPos();
        this.tag = (byte)derInputBuffer.read();
        byte by = (byte)derInputBuffer.read();
        this.length = DerInputStream.getLength(by, derInputBuffer);
        if (this.length == -1) {
            InputStream inputStream = derInputBuffer.dup();
            int n2 = ((ByteArrayInputStream)inputStream).available();
            Object object = new byte[n2 + 2];
            object[0] = this.tag;
            object[1] = by;
            inputStream = new DataInputStream(inputStream);
            ((DataInputStream)inputStream).readFully((byte[])object, 2, n2);
            ((FilterInputStream)inputStream).close();
            object = new DerInputBuffer(new DerIndefLenConverter().convert((byte[])object));
            if (this.tag != ((ByteArrayInputStream)object).read()) throw new IOException("Indefinite length encoding not supported");
            this.length = DerInputStream.getLength((InputStream)object);
            this.buffer = ((DerInputBuffer)object).dup();
            this.buffer.truncate(this.length);
            this.data = new DerInputStream(this.buffer);
            derInputBuffer.skip(this.length + 2);
        } else {
            this.buffer = derInputBuffer.dup();
            this.buffer.truncate(this.length);
            this.data = new DerInputStream(this.buffer);
            derInputBuffer.skip(this.length);
        }
        if (!bl) return;
        this.originalEncodedForm = derInputBuffer.getSlice(n, derInputBuffer.getPos() - n);
    }

    public DerValue(byte[] arrby) throws IOException {
        this.data = this.init(true, new ByteArrayInputStream(arrby));
    }

    public DerValue(byte[] arrby, int n, int n2) throws IOException {
        this.data = this.init(true, new ByteArrayInputStream(arrby, n, n2));
    }

    private byte[] append(byte[] arrby, byte[] arrby2) {
        if (arrby == null) {
            return arrby2;
        }
        byte[] arrby3 = new byte[arrby.length + arrby2.length];
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        System.arraycopy(arrby2, 0, arrby3, arrby.length, arrby2.length);
        return arrby3;
    }

    public static byte createTag(byte by, boolean bl, byte by2) {
        byte by3 = by = (byte)(by | by2);
        if (bl) {
            by3 = by = (byte)(by | 32);
        }
        return by3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static boolean doEquals(DerValue derValue, DerValue derValue2) {
        DerInputStream derInputStream = derValue.data;
        synchronized (derInputStream) {
            DerInputStream derInputStream2 = derValue2.data;
            synchronized (derInputStream2) {
                derValue.data.reset();
                derValue2.data.reset();
                return derValue.buffer.equals(derValue2.buffer);
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private DerInputStream init(byte by, String object) throws IOException {
        String string;
        block3 : {
            block0 : {
                block1 : {
                    block2 : {
                        this.tag = by;
                        if (by == 12) break block0;
                        if (by == 22 || by == 27) break block1;
                        if (by == 30) break block2;
                        if (by == 19) break block1;
                        if (by != 20) throw new IllegalArgumentException("Unsupported DER string type");
                        string = "ISO-8859-1";
                        break block3;
                    }
                    string = "UnicodeBigUnmarked";
                    break block3;
                }
                string = "ASCII";
                break block3;
            }
            string = "UTF8";
        }
        object = ((String)object).getBytes(string);
        this.length = ((Object)object).length;
        this.buffer = new DerInputBuffer((byte[])object);
        object = new DerInputStream(this.buffer);
        ((DerInputStream)object).mark(Integer.MAX_VALUE);
        return object;
    }

    private DerInputStream init(boolean bl, InputStream object) throws IOException {
        this.tag = (byte)((InputStream)object).read();
        byte by = (byte)((InputStream)object).read();
        this.length = DerInputStream.getLength(by, (InputStream)object);
        Object object2 = object;
        if (this.length == -1) {
            int n = ((InputStream)object).available();
            object2 = new byte[n + 2];
            object2[0] = this.tag;
            object2[1] = by;
            object = new DataInputStream((InputStream)object);
            ((DataInputStream)object).readFully((byte[])object2, 2, n);
            ((FilterInputStream)object).close();
            object2 = new ByteArrayInputStream(new DerIndefLenConverter().convert((byte[])object2));
            if (this.tag == ((InputStream)object2).read()) {
                this.length = DerInputStream.getLength((InputStream)object2);
            } else {
                throw new IOException("Indefinite length encoding not supported");
            }
        }
        if (bl && ((InputStream)object2).available() != this.length) {
            throw new IOException("extra data given to DerValue constructor");
        }
        this.buffer = new DerInputBuffer(IOUtils.readFully((InputStream)object2, this.length, true));
        return new DerInputStream(this.buffer);
    }

    public static boolean isPrintableStringChar(char c) {
        if (c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z' || c >= '0' && c <= '9') {
            return true;
        }
        if (c != ' ' && c != ':' && c != '=' && c != '?') {
            switch (c) {
                default: {
                    switch (c) {
                        default: {
                            return false;
                        }
                        case '+': 
                        case ',': 
                        case '-': 
                        case '.': 
                        case '/': 
                    }
                }
                case '\'': 
                case '(': 
                case ')': 
            }
        }
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void encode(DerOutputStream object) throws IOException {
        ((ByteArrayOutputStream)object).write(this.tag);
        ((DerOutputStream)object).putLength(this.length);
        int n = this.length;
        if (n <= 0) return;
        byte[] arrby = new byte[n];
        DerInputStream derInputStream = this.data;
        synchronized (derInputStream) {
            this.buffer.reset();
            if (this.buffer.read(arrby) == this.length) {
                ((OutputStream)object).write(arrby);
                return;
            }
            object = new IOException("short DER value read (encode)");
            throw object;
        }
    }

    public boolean equals(Object object) {
        if (object instanceof DerValue) {
            return this.equals((DerValue)object);
        }
        return false;
    }

    public boolean equals(DerValue derValue) {
        if (this == derValue) {
            return true;
        }
        if (this.tag != derValue.tag) {
            return false;
        }
        DerInputStream derInputStream = this.data;
        if (derInputStream == derValue.data) {
            return true;
        }
        boolean bl = System.identityHashCode(derInputStream) > System.identityHashCode(derValue.data) ? DerValue.doEquals(this, derValue) : DerValue.doEquals(derValue, this);
        return bl;
    }

    public String getAsString() throws IOException {
        byte by = this.tag;
        if (by == 12) {
            return this.getUTF8String();
        }
        if (by == 19) {
            return this.getPrintableString();
        }
        if (by == 20) {
            return this.getT61String();
        }
        if (by == 22) {
            return this.getIA5String();
        }
        if (by == 30) {
            return this.getBMPString();
        }
        if (by == 27) {
            return this.getGeneralString();
        }
        return null;
    }

    public String getBMPString() throws IOException {
        if (this.tag == 30) {
            return new String(this.getDataBytes(), "UnicodeBigUnmarked");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getBMPString, not BMP ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public BigInteger getBigInteger() throws IOException {
        if (this.tag == 2) {
            return this.buffer.getBigInteger(this.data.available(), false);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getBigInteger, not an int ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public byte[] getBitString() throws IOException {
        if (this.tag == 3) {
            return this.buffer.getBitString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getBitString, not a bit string ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public byte[] getBitString(boolean bl) throws IOException {
        if (!bl && this.tag != 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DerValue.getBitString, not a bit string ");
            stringBuilder.append(this.tag);
            throw new IOException(stringBuilder.toString());
        }
        return this.buffer.getBitString();
    }

    public boolean getBoolean() throws IOException {
        if (this.tag == 1) {
            if (this.length == 1) {
                return this.buffer.read() != 0;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DerValue.getBoolean, invalid length ");
            stringBuilder.append(this.length);
            throw new IOException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getBoolean, not a BOOLEAN ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public final DerInputStream getData() {
        return this.data;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public byte[] getDataBytes() throws IOException {
        byte[] arrby = new byte[this.length];
        DerInputStream derInputStream = this.data;
        synchronized (derInputStream) {
            this.data.reset();
            this.data.getBytes(arrby);
            return arrby;
        }
    }

    public int getEnumerated() throws IOException {
        if (this.tag == 10) {
            return this.buffer.getInteger(this.data.available());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getEnumerated, incorrect tag: ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public String getGeneralString() throws IOException {
        if (this.tag == 27) {
            return new String(this.getDataBytes(), "ASCII");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getGeneralString, not GeneralString ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public Date getGeneralizedTime() throws IOException {
        if (this.tag == 24) {
            return this.buffer.getGeneralizedTime(this.data.available());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getGeneralizedTime, not a GeneralizedTime: ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public String getIA5String() throws IOException {
        if (this.tag == 22) {
            return new String(this.getDataBytes(), "ASCII");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getIA5String, not IA5 ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public int getInteger() throws IOException {
        if (this.tag == 2) {
            return this.buffer.getInteger(this.data.available());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getInteger, not an int ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public ObjectIdentifier getOID() throws IOException {
        if (this.tag == 6) {
            return new ObjectIdentifier(this.buffer);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getOID, not an OID ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public byte[] getOctetString() throws IOException {
        if (this.tag != 4 && !this.isConstructed((byte)4)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DerValue.getOctetString, not an Octet String: ");
            stringBuilder.append(this.tag);
            throw new IOException(stringBuilder.toString());
        }
        int n = this.length;
        byte[] arrby = new byte[n];
        if (n == 0) {
            return arrby;
        }
        if (this.buffer.read(arrby) == this.length) {
            byte[] arrby2 = arrby;
            if (this.isConstructed()) {
                DerInputStream derInputStream = new DerInputStream(arrby);
                arrby = null;
                do {
                    arrby2 = arrby;
                    if (derInputStream.available() == 0) break;
                    arrby = this.append(arrby, derInputStream.getOctetString());
                } while (true);
            }
            return arrby2;
        }
        throw new IOException("short read on DerValue buffer");
    }

    public byte[] getOriginalEncodedForm() {
        Object object = this.originalEncodedForm;
        object = object != null ? (byte[])object.clone() : null;
        return object;
    }

    public BigInteger getPositiveBigInteger() throws IOException {
        if (this.tag == 2) {
            return this.buffer.getBigInteger(this.data.available(), true);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getBigInteger, not an int ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public String getPrintableString() throws IOException {
        if (this.tag == 19) {
            return new String(this.getDataBytes(), "ASCII");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getPrintableString, not a string ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public String getT61String() throws IOException {
        if (this.tag == 20) {
            return new String(this.getDataBytes(), "ISO-8859-1");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getT61String, not T61 ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public final byte getTag() {
        return this.tag;
    }

    public Date getUTCTime() throws IOException {
        if (this.tag == 23) {
            return this.buffer.getUTCTime(this.data.available());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getUTCTime, not a UtcTime: ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public String getUTF8String() throws IOException {
        if (this.tag == 12) {
            return new String(this.getDataBytes(), "UTF8");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getUTF8String, not UTF-8 ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public BitArray getUnalignedBitString() throws IOException {
        if (this.tag == 3) {
            return this.buffer.getUnalignedBitString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DerValue.getBitString, not a bit string ");
        stringBuilder.append(this.tag);
        throw new IOException(stringBuilder.toString());
    }

    public BitArray getUnalignedBitString(boolean bl) throws IOException {
        if (!bl && this.tag != 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DerValue.getBitString, not a bit string ");
            stringBuilder.append(this.tag);
            throw new IOException(stringBuilder.toString());
        }
        return this.buffer.getUnalignedBitString();
    }

    public int hashCode() {
        return this.toString().hashCode();
    }

    public boolean isApplication() {
        boolean bl = (this.tag & 192) == 64;
        return bl;
    }

    public boolean isConstructed() {
        boolean bl = (this.tag & 32) == 32;
        return bl;
    }

    public boolean isConstructed(byte by) {
        boolean bl = this.isConstructed();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if ((this.tag & 31) == by) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean isContextSpecific() {
        boolean bl = (this.tag & 192) == 128;
        return bl;
    }

    public boolean isContextSpecific(byte by) {
        boolean bl = this.isContextSpecific();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if ((this.tag & 31) == by) {
            bl2 = true;
        }
        return bl2;
    }

    boolean isPrivate() {
        boolean bl = (this.tag & 192) == 192;
        return bl;
    }

    public boolean isUniversal() {
        boolean bl = (this.tag & 192) == 0;
        return bl;
    }

    public int length() {
        return this.length;
    }

    public void resetTag(byte by) {
        this.tag = by;
    }

    public byte[] toByteArray() throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.encode(derOutputStream);
        this.data.reset();
        return derOutputStream.toByteArray();
    }

    public DerInputStream toDerInputStream() throws IOException {
        byte by = this.tag;
        if (by != 48 && by != 49) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("toDerInputStream rejects tag type ");
            stringBuilder.append(this.tag);
            throw new IOException(stringBuilder.toString());
        }
        return new DerInputStream(this.buffer);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        try {
            CharSequence charSequence = this.getAsString();
            if (charSequence != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("\"");
                stringBuilder.append((String)charSequence);
                stringBuilder.append("\"");
                return stringBuilder.toString();
            }
            if (this.tag == 5) {
                return "[DerValue, null]";
            }
            if (this.tag == 6) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("OID.");
                ((StringBuilder)charSequence).append(this.getOID());
                return ((StringBuilder)charSequence).toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[DerValue, tag = ");
            ((StringBuilder)charSequence).append(this.tag);
            ((StringBuilder)charSequence).append(", length = ");
            ((StringBuilder)charSequence).append(this.length);
            ((StringBuilder)charSequence).append("]");
            return ((StringBuilder)charSequence).toString();
        }
        catch (IOException iOException) {
            throw new IllegalArgumentException("misformatted DER value");
        }
    }
}

