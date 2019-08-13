/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CellIdentityLte;
import android.hardware.radio.V1_0.LteSignalStrength;
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
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 72, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellInfoLte();
            ((CellInfoLte)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 72);
            arrayList.add((CellInfoLte)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoLte> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 72);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
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
        this.signalStrengthLte.readEmbeddedFromParcel(hwParcel, hwBlob, 48L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
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
        this.signalStrengthLte.writeEmbeddedToBlob(hwBlob, 48L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

