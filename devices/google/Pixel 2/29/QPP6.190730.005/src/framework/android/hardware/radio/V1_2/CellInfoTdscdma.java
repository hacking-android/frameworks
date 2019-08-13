/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_2.CellIdentityTdscdma;
import android.hardware.radio.V1_2.TdscdmaSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoTdscdma {
    public CellIdentityTdscdma cellIdentityTdscdma = new CellIdentityTdscdma();
    public TdscdmaSignalStrength signalStrengthTdscdma = new TdscdmaSignalStrength();

    public static final ArrayList<CellInfoTdscdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoTdscdma> arrayList = new ArrayList<CellInfoTdscdma>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 104, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellInfoTdscdma cellInfoTdscdma = new CellInfoTdscdma();
            cellInfoTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, i * 104);
            arrayList.add(cellInfoTdscdma);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoTdscdma> arrayList) {
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
        if (object.getClass() != CellInfoTdscdma.class) {
            return false;
        }
        object = (CellInfoTdscdma)object;
        if (!HidlSupport.deepEquals(this.cellIdentityTdscdma, ((CellInfoTdscdma)object).cellIdentityTdscdma)) {
            return false;
        }
        return HidlSupport.deepEquals(this.signalStrengthTdscdma, ((CellInfoTdscdma)object).signalStrengthTdscdma);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cellIdentityTdscdma), HidlSupport.deepHashCode(this.signalStrengthTdscdma));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cellIdentityTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.signalStrengthTdscdma.readEmbeddedFromParcel(hwParcel, hwBlob, 88L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(104L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cellIdentityTdscdma = ");
        stringBuilder.append(this.cellIdentityTdscdma);
        stringBuilder.append(", .signalStrengthTdscdma = ");
        stringBuilder.append(this.signalStrengthTdscdma);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.cellIdentityTdscdma.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.signalStrengthTdscdma.writeEmbeddedToBlob(hwBlob, 88L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(104);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

