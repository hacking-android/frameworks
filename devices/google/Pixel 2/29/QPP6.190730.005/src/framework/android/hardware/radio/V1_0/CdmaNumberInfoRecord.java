/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaNumberInfoRecord {
    public String number = new String();
    public byte numberPlan;
    public byte numberType;
    public byte pi;
    public byte si;

    public static final ArrayList<CdmaNumberInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaNumberInfoRecord> arrayList = new ArrayList<CdmaNumberInfoRecord>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 24, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CdmaNumberInfoRecord cdmaNumberInfoRecord = new CdmaNumberInfoRecord();
            cdmaNumberInfoRecord.readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add(cdmaNumberInfoRecord);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaNumberInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 24);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
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
        if (object.getClass() != CdmaNumberInfoRecord.class) {
            return false;
        }
        object = (CdmaNumberInfoRecord)object;
        if (!HidlSupport.deepEquals(this.number, ((CdmaNumberInfoRecord)object).number)) {
            return false;
        }
        if (this.numberType != ((CdmaNumberInfoRecord)object).numberType) {
            return false;
        }
        if (this.numberPlan != ((CdmaNumberInfoRecord)object).numberPlan) {
            return false;
        }
        if (this.pi != ((CdmaNumberInfoRecord)object).pi) {
            return false;
        }
        return this.si == ((CdmaNumberInfoRecord)object).si;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.number), HidlSupport.deepHashCode(this.numberType), HidlSupport.deepHashCode(this.numberPlan), HidlSupport.deepHashCode(this.pi), HidlSupport.deepHashCode(this.si));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.number = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.numberType = hwBlob.getInt8(16L + l);
        this.numberPlan = hwBlob.getInt8(17L + l);
        this.pi = hwBlob.getInt8(18L + l);
        this.si = hwBlob.getInt8(19L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".number = ");
        stringBuilder.append(this.number);
        stringBuilder.append(", .numberType = ");
        stringBuilder.append(this.numberType);
        stringBuilder.append(", .numberPlan = ");
        stringBuilder.append(this.numberPlan);
        stringBuilder.append(", .pi = ");
        stringBuilder.append(this.pi);
        stringBuilder.append(", .si = ");
        stringBuilder.append(this.si);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.number);
        hwBlob.putInt8(16L + l, this.numberType);
        hwBlob.putInt8(17L + l, this.numberPlan);
        hwBlob.putInt8(18L + l, this.pi);
        hwBlob.putInt8(19L + l, this.si);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

