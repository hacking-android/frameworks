/*
 * Decompiled with CFR 0.145.
 */
package android.net.nsd;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class DnsSdTxtRecord
implements Parcelable {
    public static final Parcelable.Creator<DnsSdTxtRecord> CREATOR = new Parcelable.Creator<DnsSdTxtRecord>(){

        @Override
        public DnsSdTxtRecord createFromParcel(Parcel parcel) {
            DnsSdTxtRecord dnsSdTxtRecord = new DnsSdTxtRecord();
            parcel.readByteArray(dnsSdTxtRecord.mData);
            return dnsSdTxtRecord;
        }

        public DnsSdTxtRecord[] newArray(int n) {
            return new DnsSdTxtRecord[n];
        }
    };
    private static final byte mSeperator = 61;
    private byte[] mData;

    public DnsSdTxtRecord() {
        this.mData = new byte[0];
    }

    public DnsSdTxtRecord(DnsSdTxtRecord arrby) {
        if (arrby != null && (arrby = arrby.mData) != null) {
            this.mData = (byte[])arrby.clone();
        }
    }

    public DnsSdTxtRecord(byte[] arrby) {
        this.mData = (byte[])arrby.clone();
    }

    private String getKey(int n) {
        byte[] arrby;
        int n2;
        int n3 = 0;
        for (n2 = 0; n2 < n && n3 < (arrby = this.mData).length; n3 += arrby[n3] + 1, ++n2) {
        }
        arrby = this.mData;
        if (n3 < arrby.length) {
            n2 = arrby[n3];
            for (n = 0; n < n2 && this.mData[n3 + n + 1] != 61; ++n) {
            }
            return new String(this.mData, n3 + 1, n);
        }
        return null;
    }

    private byte[] getValue(int n) {
        byte[] arrby;
        int n2;
        int n3 = 0;
        Object var3_3 = null;
        for (n2 = 0; n2 < n && n3 < (arrby = this.mData).length; n3 += arrby[n3] + 1, ++n2) {
        }
        byte[] arrby2 = this.mData;
        arrby = var3_3;
        if (n3 < arrby2.length) {
            n2 = arrby2[n3];
            n = 0;
            do {
                arrby = var3_3;
                if (n >= n2) break;
                arrby2 = this.mData;
                if (arrby2[n3 + n + 1] == 61) {
                    arrby = new byte[n2 - n - 1];
                    System.arraycopy(arrby2, n3 + n + 2, arrby, 0, n2 - n - 1);
                    break;
                }
                ++n;
            } while (true);
        }
        return arrby;
    }

    private byte[] getValue(String string2) {
        String string3;
        int n = 0;
        while ((string3 = this.getKey(n)) != null) {
            if (string2.compareToIgnoreCase(string3) == 0) {
                return this.getValue(n);
            }
            ++n;
        }
        return null;
    }

    private String getValueAsString(int n) {
        Object object = this.getValue(n);
        object = object != null ? new String((byte[])object) : null;
        return object;
    }

    private void insert(byte[] arrby, byte[] arrby2, int n) {
        byte[] arrby3;
        int n2;
        byte[] arrby4 = this.mData;
        int n3 = arrby2 != null ? arrby2.length : 0;
        int n4 = 0;
        for (n2 = 0; n2 < n && n4 < (arrby3 = this.mData).length; n4 += arrby3[n4] + 1 & 255, ++n2) {
        }
        n2 = arrby.length;
        n = arrby2 != null ? 1 : 0;
        n2 = n2 + n3 + n;
        n = arrby4.length + n2 + 1;
        this.mData = new byte[n];
        System.arraycopy(arrby4, 0, this.mData, 0, n4);
        int n5 = arrby4.length - n4;
        System.arraycopy(arrby4, n4, this.mData, n - n5, n5);
        arrby4 = this.mData;
        arrby4[n4] = (byte)n2;
        System.arraycopy(arrby, 0, arrby4, n4 + 1, arrby.length);
        if (arrby2 != null) {
            arrby4 = this.mData;
            arrby4[n4 + 1 + arrby.length] = (byte)61;
            System.arraycopy(arrby2, 0, arrby4, arrby.length + n4 + 2, n3);
        }
    }

    public boolean contains(String string2) {
        String string3;
        int n = 0;
        while ((string3 = this.getKey(n)) != null) {
            if (string2.compareToIgnoreCase(string3) == 0) {
                return true;
            }
            ++n;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof DnsSdTxtRecord)) {
            return false;
        }
        return Arrays.equals(((DnsSdTxtRecord)object).mData, this.mData);
    }

    public String get(String object) {
        object = (object = this.getValue((String)object)) != null ? new String((byte[])object) : null;
        return object;
    }

    public byte[] getRawData() {
        return (byte[])this.mData.clone();
    }

    public int hashCode() {
        return Arrays.hashCode(this.mData);
    }

    public int keyCount() {
        byte[] arrby;
        int n = 0;
        int n2 = 0;
        while (n2 < (arrby = this.mData).length) {
            n2 += arrby[n2] + 1 & 255;
            ++n;
        }
        return n;
    }

    public int remove(String arrby) {
        byte[] arrby2;
        int n = 0;
        int n2 = 0;
        while (n < (arrby2 = this.mData).length) {
            byte by = arrby2[n];
            if (arrby.length() <= by && (arrby.length() == by || this.mData[arrby.length() + n + 1] == 61) && arrby.compareToIgnoreCase(new String(this.mData, n + 1, arrby.length())) == 0) {
                arrby = this.mData;
                this.mData = new byte[arrby.length - by - 1];
                System.arraycopy(arrby, 0, this.mData, 0, n);
                System.arraycopy(arrby, n + by + 1, this.mData, n, arrby.length - n - by - 1);
                return n2;
            }
            n += by + 1 & 255;
            ++n2;
        }
        return -1;
    }

    public void set(String string2, String arrby) {
        int n;
        byte[] arrby2;
        int n2;
        if (arrby != null) {
            arrby = arrby.getBytes();
            n2 = arrby.length;
        } else {
            arrby = null;
            n2 = 0;
        }
        try {
            arrby2 = string2.getBytes("US-ASCII");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new IllegalArgumentException("key should be US-ASCII");
        }
        for (n = 0; n < arrby2.length; ++n) {
            if (arrby2[n] != 61) {
                continue;
            }
            throw new IllegalArgumentException("= is not a valid character in key");
        }
        if (arrby2.length + n2 < 255) {
            n2 = n = this.remove(string2);
            if (n == -1) {
                n2 = this.keyCount();
            }
            this.insert(arrby2, arrby, n2);
            return;
        }
        throw new IllegalArgumentException("Key and Value length cannot exceed 255 bytes");
    }

    public int size() {
        return this.mData.length;
    }

    public String toString() {
        String string2;
        String string3 = null;
        int n = 0;
        while ((string2 = this.getKey(n)) != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{");
            stringBuilder.append(string2);
            string2 = stringBuilder.toString();
            String string4 = this.getValueAsString(n);
            if (string4 != null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("=");
                stringBuilder.append(string4);
                stringBuilder.append("}");
                string2 = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("}");
                string2 = stringBuilder.toString();
            }
            if (string3 == null) {
                string3 = string2;
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(string3);
                stringBuilder.append(", ");
                stringBuilder.append(string2);
                string3 = stringBuilder.toString();
            }
            ++n;
        }
        if (string3 == null) {
            string3 = "";
        }
        return string3;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByteArray(this.mData);
    }

}

