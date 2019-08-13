/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaT53AudioControlInfoRecord {
    public byte downLink;
    public byte upLink;

    public static final ArrayList<CdmaT53AudioControlInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaT53AudioControlInfoRecord> arrayList = new ArrayList<CdmaT53AudioControlInfoRecord>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 2, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaT53AudioControlInfoRecord();
            ((CdmaT53AudioControlInfoRecord)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 2);
            arrayList.add((CdmaT53AudioControlInfoRecord)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaT53AudioControlInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 2);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
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
        if (object.getClass() != CdmaT53AudioControlInfoRecord.class) {
            return false;
        }
        object = (CdmaT53AudioControlInfoRecord)object;
        if (this.upLink != ((CdmaT53AudioControlInfoRecord)object).upLink) {
            return false;
        }
        return this.downLink == ((CdmaT53AudioControlInfoRecord)object).downLink;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.upLink), HidlSupport.deepHashCode(this.downLink));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.upLink = hwBlob.getInt8(0L + l);
        this.downLink = hwBlob.getInt8(1L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".upLink = ");
        stringBuilder.append(this.upLink);
        stringBuilder.append(", .downLink = ");
        stringBuilder.append(this.downLink);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt8(0L + l, this.upLink);
        hwBlob.putInt8(1L + l, this.downLink);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(2);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

