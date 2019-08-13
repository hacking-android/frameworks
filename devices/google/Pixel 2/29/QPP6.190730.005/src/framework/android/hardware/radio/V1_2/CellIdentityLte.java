/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_2.CellIdentityOperatorNames;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityLte {
    public int bandwidth;
    public android.hardware.radio.V1_0.CellIdentityLte base = new android.hardware.radio.V1_0.CellIdentityLte();
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();

    public static final ArrayList<CellIdentityLte> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityLte> arrayList = new ArrayList<CellIdentityLte>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 88, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellIdentityLte cellIdentityLte = new CellIdentityLte();
            cellIdentityLte.readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add(cellIdentityLte);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityLte> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 88);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
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
        if (object.getClass() != CellIdentityLte.class) {
            return false;
        }
        object = (CellIdentityLte)object;
        if (!HidlSupport.deepEquals(this.base, ((CellIdentityLte)object).base)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.operatorNames, ((CellIdentityLte)object).operatorNames)) {
            return false;
        }
        return this.bandwidth == ((CellIdentityLte)object).bandwidth;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.operatorNames), HidlSupport.deepHashCode(this.bandwidth));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, 48L + l);
        this.bandwidth = hwBlob.getInt32(80L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .operatorNames = ");
        stringBuilder.append(this.operatorNames);
        stringBuilder.append(", .bandwidth = ");
        stringBuilder.append(this.bandwidth);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, 48L + l);
        hwBlob.putInt32(80L + l, this.bandwidth);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

