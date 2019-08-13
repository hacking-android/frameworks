/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CellIdentity;
import android.hardware.radio.V1_0.RegState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class DataRegStateResult {
    public CellIdentity cellIdentity = new CellIdentity();
    public int maxDataCalls;
    public int rat;
    public int reasonDataDenied;
    public int regState;

    public static final ArrayList<DataRegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataRegStateResult> arrayList = new ArrayList<DataRegStateResult>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 104, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            DataRegStateResult dataRegStateResult = new DataRegStateResult();
            dataRegStateResult.readEmbeddedFromParcel(hwParcel, hwBlob, i * 104);
            arrayList.add(dataRegStateResult);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataRegStateResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 104);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 104);
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
        if (object.getClass() != DataRegStateResult.class) {
            return false;
        }
        object = (DataRegStateResult)object;
        if (this.regState != ((DataRegStateResult)object).regState) {
            return false;
        }
        if (this.rat != ((DataRegStateResult)object).rat) {
            return false;
        }
        if (this.reasonDataDenied != ((DataRegStateResult)object).reasonDataDenied) {
            return false;
        }
        if (this.maxDataCalls != ((DataRegStateResult)object).maxDataCalls) {
            return false;
        }
        return HidlSupport.deepEquals(this.cellIdentity, ((DataRegStateResult)object).cellIdentity);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.regState), HidlSupport.deepHashCode(this.rat), HidlSupport.deepHashCode(this.reasonDataDenied), HidlSupport.deepHashCode(this.maxDataCalls), HidlSupport.deepHashCode(this.cellIdentity));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.regState = hwBlob.getInt32(0L + l);
        this.rat = hwBlob.getInt32(4L + l);
        this.reasonDataDenied = hwBlob.getInt32(8L + l);
        this.maxDataCalls = hwBlob.getInt32(12L + l);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, 16L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(104L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".regState = ");
        stringBuilder.append(RegState.toString(this.regState));
        stringBuilder.append(", .rat = ");
        stringBuilder.append(this.rat);
        stringBuilder.append(", .reasonDataDenied = ");
        stringBuilder.append(this.reasonDataDenied);
        stringBuilder.append(", .maxDataCalls = ");
        stringBuilder.append(this.maxDataCalls);
        stringBuilder.append(", .cellIdentity = ");
        stringBuilder.append(this.cellIdentity);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.regState);
        hwBlob.putInt32(4L + l, this.rat);
        hwBlob.putInt32(8L + l, this.reasonDataDenied);
        hwBlob.putInt32(12L + l, this.maxDataCalls);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, 16L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(104);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

