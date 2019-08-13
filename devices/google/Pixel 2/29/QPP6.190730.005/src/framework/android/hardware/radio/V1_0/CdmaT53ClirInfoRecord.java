/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaT53ClirInfoRecord {
    public byte cause;

    public static final ArrayList<CdmaT53ClirInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaT53ClirInfoRecord> arrayList = new ArrayList<CdmaT53ClirInfoRecord>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 1, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CdmaT53ClirInfoRecord cdmaT53ClirInfoRecord = new CdmaT53ClirInfoRecord();
            cdmaT53ClirInfoRecord.readEmbeddedFromParcel(hwParcel, hwBlob, i * 1);
            arrayList.add(cdmaT53ClirInfoRecord);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaT53ClirInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 1);
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
        if (object.getClass() != CdmaT53ClirInfoRecord.class) {
            return false;
        }
        object = (CdmaT53ClirInfoRecord)object;
        return this.cause == ((CdmaT53ClirInfoRecord)object).cause;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cause));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cause = hwBlob.getInt8(0L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(1L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cause = ");
        stringBuilder.append(this.cause);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt8(0L + l, this.cause);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(1);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

