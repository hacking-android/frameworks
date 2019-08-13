/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaInformationRecord;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaInformationRecords {
    public ArrayList<CdmaInformationRecord> infoRec = new ArrayList();

    public static final ArrayList<CdmaInformationRecords> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaInformationRecords> arrayList = new ArrayList<CdmaInformationRecords>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 16, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CdmaInformationRecords cdmaInformationRecords = new CdmaInformationRecords();
            cdmaInformationRecords.readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add(cdmaInformationRecords);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaInformationRecords> arrayList) {
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
        if (object.getClass() != CdmaInformationRecords.class) {
            return false;
        }
        object = (CdmaInformationRecords)object;
        return HidlSupport.deepEquals(this.infoRec, ((CdmaInformationRecords)object).infoRec);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.infoRec));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob object, long l) {
        int n = ((HwBlob)object).getInt32(l + 0L + 8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 120, ((HwBlob)object).handle(), l + 0L + 0L, true);
        this.infoRec.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaInformationRecord();
            ((CdmaInformationRecord)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 120);
            this.infoRec.add((CdmaInformationRecord)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".infoRec = ");
        stringBuilder.append(this.infoRec);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n = this.infoRec.size();
        hwBlob.putInt32(l + 0L + 8L, n);
        hwBlob.putBool(l + 0L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 120);
        for (int i = 0; i < n; ++i) {
            this.infoRec.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
        }
        hwBlob.putBlob(l + 0L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

