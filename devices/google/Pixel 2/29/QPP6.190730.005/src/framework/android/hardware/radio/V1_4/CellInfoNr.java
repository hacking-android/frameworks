/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_4.CellIdentityNr;
import android.hardware.radio.V1_4.NrSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfoNr {
    public CellIdentityNr cellidentity = new CellIdentityNr();
    public NrSignalStrength signalStrength = new NrSignalStrength();

    public static final ArrayList<CellInfoNr> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfoNr> arrayList = new ArrayList<CellInfoNr>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 112, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellInfoNr cellInfoNr = new CellInfoNr();
            cellInfoNr.readEmbeddedFromParcel(hwParcel, hwBlob, i * 112);
            arrayList.add(cellInfoNr);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfoNr> arrayList) {
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
        if (object.getClass() != CellInfoNr.class) {
            return false;
        }
        object = (CellInfoNr)object;
        if (!HidlSupport.deepEquals(this.signalStrength, ((CellInfoNr)object).signalStrength)) {
            return false;
        }
        return HidlSupport.deepEquals(this.cellidentity, ((CellInfoNr)object).cellidentity);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.signalStrength), HidlSupport.deepHashCode(this.cellidentity));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.signalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.cellidentity.readEmbeddedFromParcel(hwParcel, hwBlob, 24L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".signalStrength = ");
        stringBuilder.append(this.signalStrength);
        stringBuilder.append(", .cellidentity = ");
        stringBuilder.append(this.cellidentity);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.signalStrength.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.cellidentity.writeEmbeddedToBlob(hwBlob, 24L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

