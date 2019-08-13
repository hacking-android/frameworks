/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaSignalInfoRecord {
    public byte alertPitch;
    public boolean isPresent;
    public byte signal;
    public byte signalType;

    public static final ArrayList<CdmaSignalInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSignalInfoRecord> arrayList = new ArrayList<CdmaSignalInfoRecord>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 4, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaSignalInfoRecord();
            ((CdmaSignalInfoRecord)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 4);
            arrayList.add((CdmaSignalInfoRecord)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSignalInfoRecord> arrayList) {
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
        if (object.getClass() != CdmaSignalInfoRecord.class) {
            return false;
        }
        object = (CdmaSignalInfoRecord)object;
        if (this.isPresent != ((CdmaSignalInfoRecord)object).isPresent) {
            return false;
        }
        if (this.signalType != ((CdmaSignalInfoRecord)object).signalType) {
            return false;
        }
        if (this.alertPitch != ((CdmaSignalInfoRecord)object).alertPitch) {
            return false;
        }
        return this.signal == ((CdmaSignalInfoRecord)object).signal;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.isPresent), HidlSupport.deepHashCode(this.signalType), HidlSupport.deepHashCode(this.alertPitch), HidlSupport.deepHashCode(this.signal));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.isPresent = hwBlob.getBool(0L + l);
        this.signalType = hwBlob.getInt8(1L + l);
        this.alertPitch = hwBlob.getInt8(2L + l);
        this.signal = hwBlob.getInt8(3L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(4L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".isPresent = ");
        stringBuilder.append(this.isPresent);
        stringBuilder.append(", .signalType = ");
        stringBuilder.append(this.signalType);
        stringBuilder.append(", .alertPitch = ");
        stringBuilder.append(this.alertPitch);
        stringBuilder.append(", .signal = ");
        stringBuilder.append(this.signal);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putBool(0L + l, this.isPresent);
        hwBlob.putInt8(1L + l, this.signalType);
        hwBlob.putInt8(2L + l, this.alertPitch);
        hwBlob.putInt8(3L + l, this.signal);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(4);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

