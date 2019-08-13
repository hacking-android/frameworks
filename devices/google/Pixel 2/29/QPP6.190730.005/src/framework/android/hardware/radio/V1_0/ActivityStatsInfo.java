/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class ActivityStatsInfo {
    public int idleModeTimeMs;
    public int rxModeTimeMs;
    public int sleepModeTimeMs;
    public int[] txmModetimeMs = new int[5];

    public static final ArrayList<ActivityStatsInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ActivityStatsInfo> arrayList = new ArrayList<ActivityStatsInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 32, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new ActivityStatsInfo();
            ((ActivityStatsInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add((ActivityStatsInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ActivityStatsInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 32);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
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
        if (object.getClass() != ActivityStatsInfo.class) {
            return false;
        }
        object = (ActivityStatsInfo)object;
        if (this.sleepModeTimeMs != ((ActivityStatsInfo)object).sleepModeTimeMs) {
            return false;
        }
        if (this.idleModeTimeMs != ((ActivityStatsInfo)object).idleModeTimeMs) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.txmModetimeMs, ((ActivityStatsInfo)object).txmModetimeMs)) {
            return false;
        }
        return this.rxModeTimeMs == ((ActivityStatsInfo)object).rxModeTimeMs;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.sleepModeTimeMs), HidlSupport.deepHashCode(this.idleModeTimeMs), HidlSupport.deepHashCode(this.txmModetimeMs), HidlSupport.deepHashCode(this.rxModeTimeMs));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.sleepModeTimeMs = hwBlob.getInt32(0L + l);
        this.idleModeTimeMs = hwBlob.getInt32(4L + l);
        hwBlob.copyToInt32Array(8L + l, this.txmModetimeMs, 5);
        this.rxModeTimeMs = hwBlob.getInt32(28L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".sleepModeTimeMs = ");
        stringBuilder.append(this.sleepModeTimeMs);
        stringBuilder.append(", .idleModeTimeMs = ");
        stringBuilder.append(this.idleModeTimeMs);
        stringBuilder.append(", .txmModetimeMs = ");
        stringBuilder.append(Arrays.toString(this.txmModetimeMs));
        stringBuilder.append(", .rxModeTimeMs = ");
        stringBuilder.append(this.rxModeTimeMs);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.sleepModeTimeMs);
        hwBlob.putInt32(4L + l, this.idleModeTimeMs);
        int[] arrn = this.txmModetimeMs;
        if (arrn != null && arrn.length == 5) {
            hwBlob.putInt32Array(8L + l, arrn);
            hwBlob.putInt32(28L + l, this.rxModeTimeMs);
            return;
        }
        throw new IllegalArgumentException("Array element is not of the expected length");
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

