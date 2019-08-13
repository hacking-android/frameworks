/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_0.LteSignalStrength;
import android.hardware.radio.V1_2.CellIdentityLte;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoLte {
    public CellIdentityLte cellIdentityLte = new CellIdentityLte();
    public LteSignalStrength signalStrengthLte = new LteSignalStrength();

    public static final ArrayList<CellInfoLte> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoLte> arrayList = new ArrayList<CellInfoLte>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 112, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellInfoLte cellInfoLte = new CellInfoLte();
            cellInfoLte.readEmbeddedFromParcel(hwParcel, hwBlob, i * 112);
            arrayList.add(cellInfoLte);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoLte> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 112);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
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
        if (!HidlSupport.deepEquals(this.cellIdentityLte, ((CellInfoLte)object).cellIdentityLte)) {
            return false;
        }
        return HidlSupport.deepEquals(this.signalStrengthLte, ((CellInfoLte)object).signalStrengthLte);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cellIdentityLte), HidlSupport.deepHashCode(this.signalStrengthLte));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cellIdentityLte.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.signalStrengthLte.readEmbeddedFromParcel(hwParcel, hwBlob, 88L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cellIdentityLte = ");
        stringBuilder.append(this.cellIdentityLte);
        stringBuilder.append(", .signalStrengthLte = ");
        stringBuilder.append(this.signalStrengthLte);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.cellIdentityLte.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.signalStrengthLte.writeEmbeddedToBlob(hwBlob, 88L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

