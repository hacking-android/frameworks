/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CellIdentityGsm;
import android.hardware.radio.V1_0.GsmSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoGsm {
    public CellIdentityGsm cellIdentityGsm = new CellIdentityGsm();
    public GsmSignalStrength signalStrengthGsm = new GsmSignalStrength();

    public static final ArrayList<CellInfoGsm> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoGsm> arrayList = new ArrayList<CellInfoGsm>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 64, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellInfoGsm cellInfoGsm = new CellInfoGsm();
            cellInfoGsm.readEmbeddedFromParcel(hwParcel, hwBlob, i * 64);
            arrayList.add(cellInfoGsm);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoGsm> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 64);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
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
        if (object.getClass() != CellInfoGsm.class) {
            return false;
        }
        object = (CellInfoGsm)object;
        if (!HidlSupport.deepEquals(this.cellIdentityGsm, ((CellInfoGsm)object).cellIdentityGsm)) {
            return false;
        }
        return HidlSupport.deepEquals(this.signalStrengthGsm, ((CellInfoGsm)object).signalStrengthGsm);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cellIdentityGsm), HidlSupport.deepHashCode(this.signalStrengthGsm));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cellIdentityGsm.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.signalStrengthGsm.readEmbeddedFromParcel(hwParcel, hwBlob, 48L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cellIdentityGsm = ");
        stringBuilder.append(this.cellIdentityGsm);
        stringBuilder.append(", .signalStrengthGsm = ");
        stringBuilder.append(this.signalStrengthGsm);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.cellIdentityGsm.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.signalStrengthGsm.writeEmbeddedToBlob(hwBlob, 48L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

