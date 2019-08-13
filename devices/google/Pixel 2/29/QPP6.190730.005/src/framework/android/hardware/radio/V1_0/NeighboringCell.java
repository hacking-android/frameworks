/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class NeighboringCell {
    public String cid = new String();
    public int rssi;

    public static final ArrayList<NeighboringCell> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NeighboringCell> arrayList = new ArrayList<NeighboringCell>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new NeighboringCell();
            ((NeighboringCell)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((NeighboringCell)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NeighboringCell> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 24);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
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
        if (object.getClass() != NeighboringCell.class) {
            return false;
        }
        object = (NeighboringCell)object;
        if (!HidlSupport.deepEquals(this.cid, ((NeighboringCell)object).cid)) {
            return false;
        }
        return this.rssi == ((NeighboringCell)object).rssi;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cid), HidlSupport.deepHashCode(this.rssi));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cid = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.cid.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.rssi = hwBlob.getInt32(16L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cid = ");
        stringBuilder.append(this.cid);
        stringBuilder.append(", .rssi = ");
        stringBuilder.append(this.rssi);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.cid);
        hwBlob.putInt32(16L + l, this.rssi);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

