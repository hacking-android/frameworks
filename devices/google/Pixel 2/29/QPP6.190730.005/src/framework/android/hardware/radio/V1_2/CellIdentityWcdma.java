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

public final class CellIdentityWcdma {
    public android.hardware.radio.V1_0.CellIdentityWcdma base = new android.hardware.radio.V1_0.CellIdentityWcdma();
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();

    public static final ArrayList<CellIdentityWcdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityWcdma> arrayList = new ArrayList<CellIdentityWcdma>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 80, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellIdentityWcdma();
            ((CellIdentityWcdma)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 80);
            arrayList.add((CellIdentityWcdma)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityWcdma> arrayList) {
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
        if (object.getClass() != CellIdentityWcdma.class) {
            return false;
        }
        object = (CellIdentityWcdma)object;
        if (!HidlSupport.deepEquals(this.base, ((CellIdentityWcdma)object).base)) {
            return false;
        }
        return HidlSupport.deepEquals(this.operatorNames, ((CellIdentityWcdma)object).operatorNames);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.operatorNames));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, 48L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .operatorNames = ");
        stringBuilder.append(this.operatorNames);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, 48L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

