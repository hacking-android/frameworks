/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.os.Parcel;
import android.os.Parcelable;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

public final class NdefRecord
implements Parcelable {
    public static final Parcelable.Creator<NdefRecord> CREATOR;
    private static final byte[] EMPTY_BYTE_ARRAY;
    private static final byte FLAG_CF = 32;
    private static final byte FLAG_IL = 8;
    private static final byte FLAG_MB = -128;
    private static final byte FLAG_ME = 64;
    private static final byte FLAG_SR = 16;
    private static final int MAX_PAYLOAD_SIZE = 10485760;
    public static final byte[] RTD_ALTERNATIVE_CARRIER;
    public static final byte[] RTD_ANDROID_APP;
    public static final byte[] RTD_HANDOVER_CARRIER;
    public static final byte[] RTD_HANDOVER_REQUEST;
    public static final byte[] RTD_HANDOVER_SELECT;
    public static final byte[] RTD_SMART_POSTER;
    public static final byte[] RTD_TEXT;
    public static final byte[] RTD_URI;
    public static final short TNF_ABSOLUTE_URI = 3;
    public static final short TNF_EMPTY = 0;
    public static final short TNF_EXTERNAL_TYPE = 4;
    public static final short TNF_MIME_MEDIA = 2;
    public static final short TNF_RESERVED = 7;
    public static final short TNF_UNCHANGED = 6;
    public static final short TNF_UNKNOWN = 5;
    public static final short TNF_WELL_KNOWN = 1;
    private static final String[] URI_PREFIX_MAP;
    @UnsupportedAppUsage
    private final byte[] mId;
    private final byte[] mPayload;
    private final short mTnf;
    private final byte[] mType;

    static {
        RTD_TEXT = new byte[]{84};
        RTD_URI = new byte[]{85};
        RTD_SMART_POSTER = new byte[]{83, 112};
        RTD_ALTERNATIVE_CARRIER = new byte[]{97, 99};
        RTD_HANDOVER_CARRIER = new byte[]{72, 99};
        RTD_HANDOVER_REQUEST = new byte[]{72, 114};
        RTD_HANDOVER_SELECT = new byte[]{72, 115};
        RTD_ANDROID_APP = "android.com:pkg".getBytes();
        URI_PREFIX_MAP = new String[]{"", "http://www.", "https://www.", "http://", "https://", "tel:", "mailto:", "ftp://anonymous:anonymous@", "ftp://ftp.", "ftps://", "sftp://", "smb://", "nfs://", "ftp://", "dav://", "news:", "telnet://", "imap:", "rtsp://", "urn:", "pop:", "sip:", "sips:", "tftp:", "btspp://", "btl2cap://", "btgoep://", "tcpobex://", "irdaobex://", "file://", "urn:epc:id:", "urn:epc:tag:", "urn:epc:pat:", "urn:epc:raw:", "urn:epc:", "urn:nfc:"};
        EMPTY_BYTE_ARRAY = new byte[0];
        CREATOR = new Parcelable.Creator<NdefRecord>(){

            @Override
            public NdefRecord createFromParcel(Parcel parcel) {
                short s = (short)parcel.readInt();
                byte[] arrby = new byte[parcel.readInt()];
                parcel.readByteArray(arrby);
                byte[] arrby2 = new byte[parcel.readInt()];
                parcel.readByteArray(arrby2);
                byte[] arrby3 = new byte[parcel.readInt()];
                parcel.readByteArray(arrby3);
                return new NdefRecord(s, arrby, arrby2, arrby3);
            }

            public NdefRecord[] newArray(int n) {
                return new NdefRecord[n];
            }
        };
    }

    public NdefRecord(short s, byte[] arrby, byte[] arrby2, byte[] object) {
        byte[] arrby3 = arrby;
        if (arrby == null) {
            arrby3 = EMPTY_BYTE_ARRAY;
        }
        arrby = arrby2;
        if (arrby2 == null) {
            arrby = EMPTY_BYTE_ARRAY;
        }
        arrby2 = object;
        if (object == null) {
            arrby2 = EMPTY_BYTE_ARRAY;
        }
        if ((object = NdefRecord.validateTnf(s, arrby3, arrby, arrby2)) == null) {
            this.mTnf = s;
            this.mType = arrby3;
            this.mId = arrby;
            this.mPayload = arrby2;
            return;
        }
        throw new IllegalArgumentException((String)object);
    }

    @Deprecated
    public NdefRecord(byte[] object) throws FormatException {
        object = ByteBuffer.wrap((byte[])object);
        NdefRecord[] arrndefRecord = NdefRecord.parse((ByteBuffer)object, true);
        if (((Buffer)object).remaining() <= 0) {
            this.mTnf = arrndefRecord[0].mTnf;
            this.mType = arrndefRecord[0].mType;
            this.mId = arrndefRecord[0].mId;
            this.mPayload = arrndefRecord[0].mPayload;
            return;
        }
        throw new FormatException("data too long");
    }

    private static StringBuilder bytesToString(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = arrby.length;
        for (int i = 0; i < n; ++i) {
            stringBuilder.append(String.format("%02X", arrby[i]));
        }
        return stringBuilder;
    }

    public static NdefRecord createApplicationRecord(String string2) {
        if (string2 != null) {
            if (string2.length() != 0) {
                return new NdefRecord(4, RTD_ANDROID_APP, null, string2.getBytes(StandardCharsets.UTF_8));
            }
            throw new IllegalArgumentException("packageName is empty");
        }
        throw new NullPointerException("packageName is null");
    }

    public static NdefRecord createExternal(String arrby, String arrby2, byte[] arrby3) {
        if (arrby != null) {
            if (arrby2 != null) {
                arrby = arrby.trim().toLowerCase(Locale.ROOT);
                arrby2 = arrby2.trim().toLowerCase(Locale.ROOT);
                if (arrby.length() != 0) {
                    if (arrby2.length() != 0) {
                        arrby = arrby.getBytes(StandardCharsets.UTF_8);
                        byte[] arrby4 = arrby2.getBytes(StandardCharsets.UTF_8);
                        arrby2 = new byte[arrby.length + 1 + arrby4.length];
                        System.arraycopy(arrby, 0, arrby2, 0, arrby.length);
                        arrby2[arrby.length] = (byte)58;
                        System.arraycopy(arrby4, 0, arrby2, arrby.length + 1, arrby4.length);
                        return new NdefRecord(4, arrby2, null, arrby3);
                    }
                    throw new IllegalArgumentException("type is empty");
                }
                throw new IllegalArgumentException("domain is empty");
            }
            throw new NullPointerException("type is null");
        }
        throw new NullPointerException("domain is null");
    }

    public static NdefRecord createMime(String string2, byte[] arrby) {
        if (string2 != null) {
            if ((string2 = Intent.normalizeMimeType(string2)).length() != 0) {
                int n = string2.indexOf(47);
                if (n != 0) {
                    if (n != string2.length() - 1) {
                        return new NdefRecord(2, string2.getBytes(StandardCharsets.US_ASCII), null, arrby);
                    }
                    throw new IllegalArgumentException("mimeType must have minor type");
                }
                throw new IllegalArgumentException("mimeType must have major type");
            }
            throw new IllegalArgumentException("mimeType is empty");
        }
        throw new NullPointerException("mimeType is null");
    }

    public static NdefRecord createTextRecord(String arrby, String arrby2) {
        if (arrby2 != null) {
            arrby2 = arrby2.getBytes(StandardCharsets.UTF_8);
            if ((arrby = arrby != null && !arrby.isEmpty() ? arrby.getBytes(StandardCharsets.US_ASCII) : Locale.getDefault().getLanguage().getBytes(StandardCharsets.US_ASCII)).length < 64) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(arrby.length + 1 + arrby2.length);
                byteBuffer.put((byte)(arrby.length & 255));
                byteBuffer.put(arrby);
                byteBuffer.put(arrby2);
                return new NdefRecord(1, RTD_TEXT, null, byteBuffer.array());
            }
            throw new IllegalArgumentException("language code is too long, must be <64 bytes.");
        }
        throw new NullPointerException("text is null");
    }

    public static NdefRecord createUri(Uri object) {
        if (object != null) {
            byte[] arrby = object.normalizeScheme().toString();
            if (arrby.length() != 0) {
                byte by;
                byte by2 = 0;
                int n = 1;
                do {
                    String[] arrstring = URI_PREFIX_MAP;
                    object = arrby;
                    by = by2;
                    if (n >= arrstring.length) break;
                    if (arrby.startsWith(arrstring[n])) {
                        by = (byte)n;
                        object = arrby.substring(URI_PREFIX_MAP[n].length());
                        break;
                    }
                    ++n;
                } while (true);
                arrby = object.getBytes(StandardCharsets.UTF_8);
                object = new byte[arrby.length + 1];
                object[0] = by;
                System.arraycopy(arrby, 0, object, 1, arrby.length);
                return new NdefRecord(1, RTD_URI, null, (byte[])object);
            }
            throw new IllegalArgumentException("uri is empty");
        }
        throw new NullPointerException("uri is null");
    }

    public static NdefRecord createUri(String string2) {
        return NdefRecord.createUri(Uri.parse(string2));
    }

    private static void ensureSanePayloadSize(long l) throws FormatException {
        if (l <= 0xA00000L) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("payload above max limit: ");
        stringBuilder.append(l);
        stringBuilder.append(" > ");
        stringBuilder.append(10485760);
        throw new FormatException(stringBuilder.toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static NdefRecord[] parse(ByteBuffer object, boolean bl) throws FormatException {
        ArrayList<byte[]> arrayList = new ArrayList<byte[]>();
        byte[] arrby = null;
        byte[] arrby2 = null;
        byte[] arrby3 = null;
        try {
            ArrayList<byte[]> arrayList2 = new ArrayList<byte[]>();
            int n = 0;
            int n2 = -1;
            boolean bl2 = false;
            do {
                int n3;
                Object object22 = object;
                if (bl2) return arrayList.toArray(new NdefRecord[arrayList.size()]);
                int n4 = ((ByteBuffer)object).get();
                boolean bl3 = true;
                bl2 = (n4 & -128) != 0;
                boolean bl4 = (n4 & 64) != 0;
                boolean bl5 = (n4 & 32) != 0;
                int n5 = (n4 & 16) != 0 ? 1 : 0;
                if ((n4 & 8) == 0) {
                    bl3 = false;
                }
                n4 = n4 & 7;
                if (!bl2 && arrayList.size() == 0 && n == 0 && !bl) {
                    object = new FormatException("expected MB flag");
                    throw object;
                }
                if (bl2 && (arrayList.size() != 0 || n != 0) && !bl) {
                    object = new FormatException("unexpected MB flag");
                    throw object;
                }
                if (n != 0 && bl3) {
                    object = new FormatException("unexpected IL flag in non-leading chunk");
                    throw object;
                }
                if (bl5 && bl4) {
                    object = new FormatException("unexpected ME flag in non-trailing chunk");
                    throw object;
                }
                if (n != 0 && n4 != 6) {
                    object = new FormatException("expected TNF_UNCHANGED in non-leading chunk");
                    throw object;
                }
                if (n == 0 && n4 == 6) {
                    object = new FormatException("unexpected TNF_UNCHANGED in first chunk or unchunked record");
                    throw object;
                }
                int n6 = ((ByteBuffer)object).get() & 255;
                long l = n5 != 0 ? (long)(((ByteBuffer)object).get() & 255) : (long)((ByteBuffer)object).getInt() & 0xFFFFFFFFL;
                n5 = bl3 ? ((ByteBuffer)object).get() & 255 : 0;
                if (n != 0 && n6 != 0) {
                    object = new FormatException("expected zero-length type in non-leading chunk");
                    throw object;
                }
                if (n == 0) {
                    arrby = n6 > 0 ? new byte[n6] : EMPTY_BYTE_ARRAY;
                    arrby2 = n5 > 0 ? new byte[n5] : EMPTY_BYTE_ARRAY;
                    object22.get(arrby);
                    object22.get(arrby2);
                }
                NdefRecord.ensureSanePayloadSize(l);
                arrby3 = l > 0L ? new byte[(int)l] : EMPTY_BYTE_ARRAY;
                object22.get(arrby3);
                if (bl5 && n == 0) {
                    if (n6 == 0 && n4 != 5) {
                        object = new FormatException("expected non-zero type length in first chunk");
                        throw object;
                    }
                    arrayList2.clear();
                    n2 = n4;
                }
                if (bl5 || n != 0) {
                    arrayList2.add(arrby3);
                }
                if (!bl5 && n != 0) {
                    l = 0L;
                    object22 = arrayList2.iterator();
                    while (object22.hasNext()) {
                        l += (long)((byte[])object22.next()).length;
                    }
                    NdefRecord.ensureSanePayloadSize(l);
                    arrby3 = new byte[(int)l];
                    n = 0;
                    for (Object object22 : arrayList2) {
                        System.arraycopy(object22, 0, arrby3, n, ((byte[])object22).length);
                        n += ((byte[])object22).length;
                    }
                    n3 = n = n2;
                } else {
                    n3 = n4;
                }
                if (bl5) {
                    n = 1;
                    bl2 = bl4;
                    continue;
                }
                n = 0;
                object22 = NdefRecord.validateTnf((short)n3, arrby, arrby2, arrby3);
                if (object22 != null) {
                    object = new FormatException((String)object22);
                    throw object;
                }
                object22 = new NdefRecord((short)n3, arrby, arrby2, arrby3);
                arrayList.add((byte[])object22);
                if (bl) {
                    return arrayList.toArray(new NdefRecord[arrayList.size()]);
                }
                bl2 = bl4;
            } while (true);
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            throw new FormatException("expected more data", bufferUnderflowException);
        }
    }

    private Uri parseWktUri() {
        Object object;
        Object object2 = this.mPayload;
        if (((byte[])object2).length < 2) {
            return null;
        }
        int n = object2[0] & -1;
        if (n >= 0 && n < ((String[])(object = URI_PREFIX_MAP)).length) {
            object = object[n];
            object2 = new String(Arrays.copyOfRange(object2, 1, ((byte[])object2).length), StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)object);
            stringBuilder.append((String)object2);
            return Uri.parse(stringBuilder.toString());
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Uri toUri(boolean bl) {
        int n = this.mTnf;
        Object object = null;
        if (n != 1) {
            if (n == 3) return Uri.parse(new String(this.mType, StandardCharsets.UTF_8)).normalizeScheme();
            if (n != 4 || bl) return null;
            object = new StringBuilder();
            ((StringBuilder)object).append("vnd.android.nfc://ext/");
            ((StringBuilder)object).append(new String(this.mType, StandardCharsets.US_ASCII));
            return Uri.parse(((StringBuilder)object).toString());
        }
        if (Arrays.equals(this.mType, RTD_SMART_POSTER) && !bl) {
            int n2;
            NdefRecord[] arrndefRecord;
            try {
                object = new NdefMessage(this.mPayload);
                arrndefRecord = ((NdefMessage)object).getRecords();
                n2 = arrndefRecord.length;
                n = 0;
            }
            catch (FormatException formatException) {
                return null;
            }
            while (n < n2) {
                object = arrndefRecord[n].toUri(true);
                if (object != null) {
                    return object;
                }
                ++n;
            }
            return null;
        } else {
            if (!Arrays.equals(this.mType, RTD_URI)) return null;
            Uri uri = this.parseWktUri();
            if (uri == null) return object;
            return uri.normalizeScheme();
        }
    }

    static String validateTnf(short s, byte[] arrby, byte[] arrby2, byte[] arrby3) {
        switch (s) {
            default: {
                return String.format("unexpected tnf value: 0x%02x", s);
            }
            case 6: {
                return "unexpected TNF_UNCHANGED in first chunk or logical record";
            }
            case 5: 
            case 7: {
                if (arrby.length != 0) {
                    return "unexpected type field in TNF_UNKNOWN or TNF_RESERVEd record";
                }
                return null;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: {
                return null;
            }
            case 0: 
        }
        if (arrby.length == 0 && arrby2.length == 0 && arrby3.length == 0) {
            return null;
        }
        return "unexpected data in TNF_EMPTY record";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (NdefRecord)object;
        if (!Arrays.equals(this.mId, ((NdefRecord)object).mId)) {
            return false;
        }
        if (!Arrays.equals(this.mPayload, ((NdefRecord)object).mPayload)) {
            return false;
        }
        if (this.mTnf != ((NdefRecord)object).mTnf) {
            return false;
        }
        return Arrays.equals(this.mType, ((NdefRecord)object).mType);
    }

    int getByteLength() {
        int n = this.mType.length;
        int n2 = this.mId.length;
        byte[] arrby = this.mPayload;
        int n3 = n + 3 + n2 + arrby.length;
        n = arrby.length;
        boolean bl = true;
        n2 = n < 256 ? 1 : 0;
        if (this.mTnf != 0 && this.mId.length <= 0) {
            bl = false;
        }
        n = n3;
        if (n2 == 0) {
            n = n3 + 3;
        }
        n2 = n;
        if (bl) {
            n2 = n + 1;
        }
        return n2;
    }

    public byte[] getId() {
        return (byte[])this.mId.clone();
    }

    public byte[] getPayload() {
        return (byte[])this.mPayload.clone();
    }

    public short getTnf() {
        return this.mTnf;
    }

    public byte[] getType() {
        return (byte[])this.mType.clone();
    }

    public int hashCode() {
        return (((1 * 31 + Arrays.hashCode(this.mId)) * 31 + Arrays.hashCode(this.mPayload)) * 31 + this.mTnf) * 31 + Arrays.hashCode(this.mType);
    }

    @Deprecated
    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(this.getByteLength());
        this.writeToByteBuffer(byteBuffer, true, true);
        return byteBuffer.array();
    }

    public String toMimeType() {
        short s = this.mTnf;
        if (s != 1) {
            if (s == 2) {
                return Intent.normalizeMimeType(new String(this.mType, StandardCharsets.US_ASCII));
            }
        } else if (Arrays.equals(this.mType, RTD_TEXT)) {
            return "text/plain";
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(String.format("NdefRecord tnf=%X", this.mTnf));
        if (this.mType.length > 0) {
            stringBuilder.append(" type=");
            stringBuilder.append(NdefRecord.bytesToString(this.mType));
        }
        if (this.mId.length > 0) {
            stringBuilder.append(" id=");
            stringBuilder.append(NdefRecord.bytesToString(this.mId));
        }
        if (this.mPayload.length > 0) {
            stringBuilder.append(" payload=");
            stringBuilder.append(NdefRecord.bytesToString(this.mPayload));
        }
        return stringBuilder.toString();
    }

    public Uri toUri() {
        return this.toUri(false);
    }

    void writeToByteBuffer(ByteBuffer byteBuffer, boolean bl, boolean bl2) {
        int n = this.mPayload.length;
        boolean bl3 = true;
        int n2 = 0;
        n = n < 256 ? 1 : 0;
        if (this.mTnf != 0 && this.mId.length <= 0) {
            bl3 = false;
        }
        int n3 = bl ? -128 : 0;
        int n4 = bl2 ? 64 : 0;
        int n5 = n != 0 ? 16 : 0;
        if (bl3) {
            n2 = 8;
        }
        byteBuffer.put((byte)(n2 | (n3 | n4 | n5) | this.mTnf));
        byteBuffer.put((byte)this.mType.length);
        if (n != 0) {
            byteBuffer.put((byte)this.mPayload.length);
        } else {
            byteBuffer.putInt(this.mPayload.length);
        }
        if (bl3) {
            byteBuffer.put((byte)this.mId.length);
        }
        byteBuffer.put(this.mType);
        byteBuffer.put(this.mId);
        byteBuffer.put(this.mPayload);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mTnf);
        parcel.writeInt(this.mType.length);
        parcel.writeByteArray(this.mType);
        parcel.writeInt(this.mId.length);
        parcel.writeByteArray(this.mId);
        parcel.writeInt(this.mPayload.length);
        parcel.writeByteArray(this.mPayload);
    }

}

