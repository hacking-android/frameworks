/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_4.CellConfigLte;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoLte {
    public android.hardware.radio.V1_2.CellInfoLte base = new android.hardware.radio.V1_2.CellInfoLte();
    public CellConfigLte cellConfig = new CellConfigLte();

    public static final ArrayList<CellInfoLte> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoLte> arrayList = new ArrayList<CellInfoLte>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 120, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellInfoLte();
            ((CellInfoLte)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 120);
            arrayList.add((CellInfoLte)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoLte> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 120);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
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
        if (object.getClass() != CellInfoLte.class) {
            return false;
        }
        object = (CellInfoLte)object;
        if (!HidlSupport.deepEquals(this.base, ((CellInfoLte)object).base)) {
            return false;
        }
        return HidlSupport.deepEquals(this.cellConfig, ((CellInfoLte)object).cellConfig);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.cellConfig));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.cellConfig.readEmbeddedFromParcel(hwParcel, hwBlob, 112L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .cellConfig = ");
        stringBuilder.append(this.cellConfig);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.cellConfig.writeEmbeddedToBlob(hwBlob, 112L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

