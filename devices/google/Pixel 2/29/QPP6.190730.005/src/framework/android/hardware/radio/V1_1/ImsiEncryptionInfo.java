/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class ImsiEncryptionInfo {
    public ArrayList<Byte> carrierKey = new ArrayList();
    public long expirationTime;
    public String keyIdentifier = new String();
    public String mcc = new String();
    public String mnc = new String();

    public static final ArrayList<ImsiEncryptionInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ImsiEncryptionInfo> arrayList = new ArrayList<ImsiEncryptionInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 72, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            ImsiEncryptionInfo imsiEncryptionInfo = new ImsiEncryptionInfo();
            imsiEncryptionInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 72);
            arrayList.add(imsiEncryptionInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ImsiEncryptionInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 72);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != ImsiEncryptionInfo.class) {
            return false;
        }
        object = (ImsiEncryptionInfo)object;
        if (!HidlSupport.deepEquals(this.mcc, ((ImsiEncryptionInfo)object).mcc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mnc, ((ImsiEncryptionInfo)object).mnc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.carrierKey, ((ImsiEncryptionInfo)object).carrierKey)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.keyIdentifier, ((ImsiEncryptionInfo)object).keyIdentifier)) {
            return false;
        }
        return this.expirationTime == ((ImsiEncryptionInfo)object).expirationTime;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.mcc), HidlSupport.deepHashCode(this.mnc), HidlSupport.deepHashCode(this.carrierKey), HidlSupport.deepHashCode(this.keyIdentifier), HidlSupport.deepHashCode(this.expirationTime));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.mcc = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.mnc = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        int n = hwBlob.getInt32(l + 32L + 8L);
        HwBlob hwBlob2 = hwParcel.readEmbeddedBuffer(n * 1, hwBlob.handle(), l + 32L + 0L, true);
        this.carrierKey.clear();
        for (int i = 0; i < n; ++i) {
            byte by = hwBlob2.getInt8(i * 1);
            this.carrierKey.add(by);
        }
        this.keyIdentifier = hwBlob.getString(l + 48L);
        hwParcel.readEmbeddedBuffer(this.keyIdentifier.getBytes().length + 1, hwBlob.handle(), l + 48L + 0L, false);
        this.expirationTime = hwBlob.getInt64(l + 64L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".mcc = ");
        stringBuilder.append(this.mcc);
        stringBuilder.append(", .mnc = ");
        stringBuilder.append(this.mnc);
        stringBuilder.append(", .carrierKey = ");
        stringBuilder.append(this.carrierKey);
        stringBuilder.append(", .keyIdentifier = ");
        stringBuilder.append(this.keyIdentifier);
        stringBuilder.append(", .expirationTime = ");
        stringBuilder.append(this.expirationTime);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(l + 0L, this.mcc);
        hwBlob.putString(16L + l, this.mnc);
        int n = this.carrierKey.size();
        hwBlob.putInt32(l + 32L + 8L, n);
        hwBlob.putBool(l + 32L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt8(i * 1, this.carrierKey.get(i));
        }
        hwBlob.putBlob(32L + l + 0L, hwBlob2);
        hwBlob.putString(48L + l, this.keyIdentifier);
        hwBlob.putInt64(64L + l, this.expirationTime);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

