/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaCallWaitingNumberPlan;
import android.hardware.radio.V1_0.CdmaCallWaitingNumberPresentation;
import android.hardware.radio.V1_0.CdmaCallWaitingNumberType;
import android.hardware.radio.V1_0.CdmaSignalInfoRecord;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaCallWaiting {
    public String name = new String();
    public String number = new String();
    public int numberPlan;
    public int numberPresentation;
    public int numberType;
    public CdmaSignalInfoRecord signalInfoRecord = new CdmaSignalInfoRecord();

    public static final ArrayList<CdmaCallWaiting> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaCallWaiting> arrayList = new ArrayList<CdmaCallWaiting>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 56, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaCallWaiting();
            ((CdmaCallWaiting)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 56);
            arrayList.add((CdmaCallWaiting)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaCallWaiting> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 56);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
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
        if (object.getClass() != CdmaCallWaiting.class) {
            return false;
        }
        object = (CdmaCallWaiting)object;
        if (!HidlSupport.deepEquals(this.number, ((CdmaCallWaiting)object).number)) {
            return false;
        }
        if (this.numberPresentation != ((CdmaCallWaiting)object).numberPresentation) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.name, ((CdmaCallWaiting)object).name)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.signalInfoRecord, ((CdmaCallWaiting)object).signalInfoRecord)) {
            return false;
        }
        if (this.numberType != ((CdmaCallWaiting)object).numberType) {
            return false;
        }
        return this.numberPlan == ((CdmaCallWaiting)object).numberPlan;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.number), HidlSupport.deepHashCode(this.numberPresentation), HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.signalInfoRecord), HidlSupport.deepHashCode(this.numberType), HidlSupport.deepHashCode(this.numberPlan));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.number = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.numberPresentation = hwBlob.getInt32(l + 16L);
        this.name = hwBlob.getString(l + 24L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 24L + 0L, false);
        this.signalInfoRecord.readEmbeddedFromParcel(hwParcel, hwBlob, l + 40L);
        this.numberType = hwBlob.getInt32(l + 44L);
        this.numberPlan = hwBlob.getInt32(l + 48L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".number = ");
        stringBuilder.append(this.number);
        stringBuilder.append(", .numberPresentation = ");
        stringBuilder.append(CdmaCallWaitingNumberPresentation.toString(this.numberPresentation));
        stringBuilder.append(", .name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .signalInfoRecord = ");
        stringBuilder.append(this.signalInfoRecord);
        stringBuilder.append(", .numberType = ");
        stringBuilder.append(CdmaCallWaitingNumberType.toString(this.numberType));
        stringBuilder.append(", .numberPlan = ");
        stringBuilder.append(CdmaCallWaitingNumberPlan.toString(this.numberPlan));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.number);
        hwBlob.putInt32(16L + l, this.numberPresentation);
        hwBlob.putString(24L + l, this.name);
        this.signalInfoRecord.writeEmbeddedToBlob(hwBlob, 40L + l);
        hwBlob.putInt32(44L + l, this.numberType);
        hwBlob.putInt32(48L + l, this.numberPlan);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

