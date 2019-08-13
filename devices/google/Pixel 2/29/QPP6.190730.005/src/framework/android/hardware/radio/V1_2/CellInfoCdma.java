/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_0.CdmaSignalStrength;
import android.hardware.radio.V1_0.EvdoSignalStrength;
import android.hardware.radio.V1_2.CellIdentityCdma;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoCdma {
    public CellIdentityCdma cellIdentityCdma = new CellIdentityCdma();
    public CdmaSignalStrength signalStrengthCdma = new CdmaSignalStrength();
    public EvdoSignalStrength signalStrengthEvdo = new EvdoSignalStrength();

    public static final ArrayList<CellInfoCdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoCdma> arrayList = new ArrayList<CellInfoCdma>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 80, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellInfoCdma();
            ((CellInfoCdma)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 80);
            arrayList.add((CellInfoCdma)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoCdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 80);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
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
        if (object.getClass() != CellInfoCdma.class) {
            return false;
        }
        object = (CellInfoCdma)object;
        if (!HidlSupport.deepEquals(this.cellIdentityCdma, ((CellInfoCdma)object).cellIdentityCdma)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.signalStrengthCdma, ((CellInfoCdma)object).signalStrengthCdma)) {
            return false;
        }
        return HidlSupport.deepEquals(this.signalStrengthEvdo, ((CellInfoCdma)object).signalStrengthEvdo);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cellIdentityCdma), HidlSupport.deepHashCode(this.signalStrengthCdma), HidlSupport.deepHashCode(this.signalStrengthEvdo));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cellIdentityCdma.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.signalStrengthCdma.readEmbeddedFromParcel(hwParcel, hwBlob, 56L + l);
        this.signalStrengthEvdo.readEmbeddedFromParcel(hwParcel, hwBlob, 64L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cellIdentityCdma = ");
        stringBuilder.append(this.cellIdentityCdma);
        stringBuilder.append(", .signalStrengthCdma = ");
        stringBuilder.append(this.signalStrengthCdma);
        stringBuilder.append(", .signalStrengthEvdo = ");
        stringBuilder.append(this.signalStrengthEvdo);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.cellIdentityCdma.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.signalStrengthCdma.writeEmbeddedToBlob(hwBlob, 56L + l);
        this.signalStrengthEvdo.writeEmbeddedToBlob(hwBlob, 64L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

