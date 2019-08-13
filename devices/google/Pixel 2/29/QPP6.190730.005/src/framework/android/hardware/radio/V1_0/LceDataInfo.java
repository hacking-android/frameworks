/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class LceDataInfo {
    public byte confidenceLevel;
    public int lastHopCapacityKbps;
    public boolean lceSuspended;

    public static final ArrayList<LceDataInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LceDataInfo> arrayList = new ArrayList<LceDataInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 8, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new LceDataInfo();
            ((LceDataInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add((LceDataInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LceDataInfo> arrayList) {
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
        if (object.getClass() != LceDataInfo.class) {
            return false;
        }
        object = (LceDataInfo)object;
        if (this.lastHopCapacityKbps != ((LceDataInfo)object).lastHopCapacityKbps) {
            return false;
        }
        if (this.confidenceLevel != ((LceDataInfo)object).confidenceLevel) {
            return false;
        }
        return this.lceSuspended == ((LceDataInfo)object).lceSuspended;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.lastHopCapacityKbps), HidlSupport.deepHashCode(this.confidenceLevel), HidlSupport.deepHashCode(this.lceSuspended));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.lastHopCapacityKbps = hwBlob.getInt32(0L + l);
        this.confidenceLevel = hwBlob.getInt8(4L + l);
        this.lceSuspended = hwBlob.getBool(5L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".lastHopCapacityKbps = ");
        stringBuilder.append(this.lastHopCapacityKbps);
        stringBuilder.append(", .confidenceLevel = ");
        stringBuilder.append(this.confidenceLevel);
        stringBuilder.append(", .lceSuspended = ");
        stringBuilder.append(this.lceSuspended);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.lastHopCapacityKbps);
        hwBlob.putInt8(4L + l, this.confidenceLevel);
        hwBlob.putBool(5L + l, this.lceSuspended);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

