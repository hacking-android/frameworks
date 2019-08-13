/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.LceStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class LceStatusInfo {
    public byte actualIntervalMs;
    public int lceStatus;

    public static final ArrayList<LceStatusInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LceStatusInfo> arrayList = new ArrayList<LceStatusInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 8, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new LceStatusInfo();
            ((LceStatusInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add((LceStatusInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LceStatusInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 8);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
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
        if (object.getClass() != LceStatusInfo.class) {
            return false;
        }
        object = (LceStatusInfo)object;
        if (this.lceStatus != ((LceStatusInfo)object).lceStatus) {
            return false;
        }
        return this.actualIntervalMs == ((LceStatusInfo)object).actualIntervalMs;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.lceStatus), HidlSupport.deepHashCode(this.actualIntervalMs));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.lceStatus = hwBlob.getInt32(0L + l);
        this.actualIntervalMs = hwBlob.getInt8(4L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".lceStatus = ");
        stringBuilder.append(LceStatus.toString(this.lceStatus));
        stringBuilder.append(", .actualIntervalMs = ");
        stringBuilder.append(this.actualIntervalMs);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.lceStatus);
        hwBlob.putInt8(4L + l, this.actualIntervalMs);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

