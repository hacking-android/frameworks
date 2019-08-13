/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaDisplayInfoRecord {
    public String alphaBuf = new String();

    public static final ArrayList<CdmaDisplayInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaDisplayInfoRecord> arrayList = new ArrayList<CdmaDisplayInfoRecord>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 16, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaDisplayInfoRecord();
            ((CdmaDisplayInfoRecord)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add((CdmaDisplayInfoRecord)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaDisplayInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 16);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
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
        if (object.getClass() != CdmaDisplayInfoRecord.class) {
            return false;
        }
        object = (CdmaDisplayInfoRecord)object;
        return HidlSupport.deepEquals(this.alphaBuf, ((CdmaDisplayInfoRecord)object).alphaBuf);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.alphaBuf));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.alphaBuf = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.alphaBuf.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".alphaBuf = ");
        stringBuilder.append(this.alphaBuf);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.alphaBuf);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

