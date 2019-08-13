/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SsInfoData {
    public ArrayList<Integer> ssInfo = new ArrayList();

    public static final ArrayList<SsInfoData> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SsInfoData> arrayList = new ArrayList<SsInfoData>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 16, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SsInfoData();
            ((SsInfoData)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add((SsInfoData)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SsInfoData> arrayList) {
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
        if (object.getClass() != SsInfoData.class) {
            return false;
        }
        object = (SsInfoData)object;
        return HidlSupport.deepEquals(this.ssInfo, ((SsInfoData)object).ssInfo);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.ssInfo));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        int n = hwBlob.getInt32(l + 0L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 4, hwBlob.handle(), l + 0L + 0L, true);
        this.ssInfo.clear();
        for (int i = 0; i < n; ++i) {
            int n2 = ((HwBlob)object).getInt32(i * 4);
            this.ssInfo.add(n2);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".ssInfo = ");
        stringBuilder.append(this.ssInfo);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n = this.ssInfo.size();
        hwBlob.putInt32(l + 0L + 8L, n);
        hwBlob.putBool(l + 0L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 4);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt32(i * 4, this.ssInfo.get(i));
        }
        hwBlob.putBlob(l + 0L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

