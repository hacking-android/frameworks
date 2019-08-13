/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityOperatorNames {
    public String alphaLong = new String();
    public String alphaShort = new String();

    public static final ArrayList<CellIdentityOperatorNames> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityOperatorNames> arrayList = new ArrayList<CellIdentityOperatorNames>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 32, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellIdentityOperatorNames cellIdentityOperatorNames = new CellIdentityOperatorNames();
            cellIdentityOperatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add(cellIdentityOperatorNames);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityOperatorNames> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 32);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
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
        if (object.getClass() != CellIdentityOperatorNames.class) {
            return false;
        }
        object = (CellIdentityOperatorNames)object;
        if (!HidlSupport.deepEquals(this.alphaLong, ((CellIdentityOperatorNames)object).alphaLong)) {
            return false;
        }
        return HidlSupport.deepEquals(this.alphaShort, ((CellIdentityOperatorNames)object).alphaShort);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.alphaLong), HidlSupport.deepHashCode(this.alphaShort));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.alphaLong = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.alphaLong.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.alphaShort = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.alphaShort.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".alphaLong = ");
        stringBuilder.append(this.alphaLong);
        stringBuilder.append(", .alphaShort = ");
        stringBuilder.append(this.alphaShort);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.alphaLong);
        hwBlob.putString(16L + l, this.alphaShort);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

