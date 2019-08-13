/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaLineControlInfoRecord {
    public byte lineCtrlPolarityIncluded;
    public byte lineCtrlPowerDenial;
    public byte lineCtrlReverse;
    public byte lineCtrlToggle;

    public static final ArrayList<CdmaLineControlInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaLineControlInfoRecord> arrayList = new ArrayList<CdmaLineControlInfoRecord>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 4, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaLineControlInfoRecord();
            ((CdmaLineControlInfoRecord)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 4);
            arrayList.add((CdmaLineControlInfoRecord)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaLineControlInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 4);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 4);
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
        if (object.getClass() != CdmaLineControlInfoRecord.class) {
            return false;
        }
        object = (CdmaLineControlInfoRecord)object;
        if (this.lineCtrlPolarityIncluded != ((CdmaLineControlInfoRecord)object).lineCtrlPolarityIncluded) {
            return false;
        }
        if (this.lineCtrlToggle != ((CdmaLineControlInfoRecord)object).lineCtrlToggle) {
            return false;
        }
        if (this.lineCtrlReverse != ((CdmaLineControlInfoRecord)object).lineCtrlReverse) {
            return false;
        }
        return this.lineCtrlPowerDenial == ((CdmaLineControlInfoRecord)object).lineCtrlPowerDenial;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.lineCtrlPolarityIncluded), HidlSupport.deepHashCode(this.lineCtrlToggle), HidlSupport.deepHashCode(this.lineCtrlReverse), HidlSupport.deepHashCode(this.lineCtrlPowerDenial));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.lineCtrlPolarityIncluded = hwBlob.getInt8(0L + l);
        this.lineCtrlToggle = hwBlob.getInt8(1L + l);
        this.lineCtrlReverse = hwBlob.getInt8(2L + l);
        this.lineCtrlPowerDenial = hwBlob.getInt8(3L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".lineCtrlPolarityIncluded = ");
        stringBuilder.append(this.lineCtrlPolarityIncluded);
        stringBuilder.append(", .lineCtrlToggle = ");
        stringBuilder.append(this.lineCtrlToggle);
        stringBuilder.append(", .lineCtrlReverse = ");
        stringBuilder.append(this.lineCtrlReverse);
        stringBuilder.append(", .lineCtrlPowerDenial = ");
        stringBuilder.append(this.lineCtrlPowerDenial);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt8(0L + l, this.lineCtrlPolarityIncluded);
        hwBlob.putInt8(1L + l, this.lineCtrlToggle);
        hwBlob.putInt8(2L + l, this.lineCtrlReverse);
        hwBlob.putInt8(3L + l, this.lineCtrlPowerDenial);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(4);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

