/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class WcdmaSignalStrength {
    public android.hardware.radio.V1_0.WcdmaSignalStrength base = new android.hardware.radio.V1_0.WcdmaSignalStrength();
    public int ecno;
    public int rscp;

    public static final ArrayList<WcdmaSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<WcdmaSignalStrength> arrayList = new ArrayList<WcdmaSignalStrength>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 16, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            WcdmaSignalStrength wcdmaSignalStrength = new WcdmaSignalStrength();
            wcdmaSignalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add(wcdmaSignalStrength);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<WcdmaSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 16);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
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
        if (object.getClass() != WcdmaSignalStrength.class) {
            return false;
        }
        object = (WcdmaSignalStrength)object;
        if (!HidlSupport.deepEquals(this.base, ((WcdmaSignalStrength)object).base)) {
            return false;
        }
        if (this.rscp != ((WcdmaSignalStrength)object).rscp) {
            return false;
        }
        return this.ecno == ((WcdmaSignalStrength)object).ecno;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.rscp), HidlSupport.deepHashCode(this.ecno));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.rscp = hwBlob.getInt32(8L + l);
        this.ecno = hwBlob.getInt32(12L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .rscp = ");
        stringBuilder.append(this.rscp);
        stringBuilder.append(", .ecno = ");
        stringBuilder.append(this.ecno);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        hwBlob.putInt32(8L + l, this.rscp);
        hwBlob.putInt32(12L + l, this.ecno);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

