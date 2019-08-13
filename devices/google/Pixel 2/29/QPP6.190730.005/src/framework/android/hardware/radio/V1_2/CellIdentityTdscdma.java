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

public final class CellIdentityTdscdma {
    public android.hardware.radio.V1_0.CellIdentityTdscdma base = new android.hardware.radio.V1_0.CellIdentityTdscdma();
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();
    public int uarfcn;

    public static final ArrayList<CellIdentityTdscdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityTdscdma> arrayList = new ArrayList<CellIdentityTdscdma>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 88, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellIdentityTdscdma();
            ((CellIdentityTdscdma)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add((CellIdentityTdscdma)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityTdscdma> arrayList) {
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
        if (object.getClass() != CellIdentityTdscdma.class) {
            return false;
        }
        object = (CellIdentityTdscdma)object;
        if (!HidlSupport.deepEquals(this.base, ((CellIdentityTdscdma)object).base)) {
            return false;
        }
        if (this.uarfcn != ((CellIdentityTdscdma)object).uarfcn) {
            return false;
        }
        return HidlSupport.deepEquals(this.operatorNames, ((CellIdentityTdscdma)object).operatorNames);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.uarfcn), HidlSupport.deepHashCode(this.operatorNames));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.uarfcn = hwBlob.getInt32(48L + l);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, 56L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .uarfcn = ");
        stringBuilder.append(this.uarfcn);
        stringBuilder.append(", .operatorNames = ");
        stringBuilder.append(this.operatorNames);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        hwBlob.putInt32(48L + l, this.uarfcn);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, 56L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

