/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_2.CellIdentityWcdma;
import android.hardware.radio.V1_2.WcdmaSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoWcdma {
    public CellIdentityWcdma cellIdentityWcdma = new CellIdentityWcdma();
    public WcdmaSignalStrength signalStrengthWcdma = new WcdmaSignalStrength();

    public static final ArrayList<CellInfoWcdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoWcdma> arrayList = new ArrayList<CellInfoWcdma>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 96, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellInfoWcdma();
            ((CellInfoWcdma)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 96);
            arrayList.add((CellInfoWcdma)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoWcdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 96);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
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
        if (object.getClass() != CellInfoWcdma.class) {
            return false;
        }
        object = (CellInfoWcdma)object;
        if (!HidlSupport.deepEquals(this.cellIdentityWcdma, ((CellInfoWcdma)object).cellIdentityWcdma)) {
            return false;
        }
        return HidlSupport.deepEquals(this.signalStrengthWcdma, ((CellInfoWcdma)object).signalStrengthWcdma);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cellIdentityWcdma), HidlSupport.deepHashCode(this.signalStrengthWcdma));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cellIdentityWcdma.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.signalStrengthWcdma.readEmbeddedFromParcel(hwParcel, hwBlob, 80L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cellIdentityWcdma = ");
        stringBuilder.append(this.cellIdentityWcdma);
        stringBuilder.append(", .signalStrengthWcdma = ");
        stringBuilder.append(this.signalStrengthWcdma);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.cellIdentityWcdma.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.signalStrengthWcdma.writeEmbeddedToBlob(hwBlob, 80L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

