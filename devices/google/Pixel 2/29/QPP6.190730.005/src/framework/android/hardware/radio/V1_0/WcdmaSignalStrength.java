/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class WcdmaSignalStrength {
    public int bitErrorRate;
    public int signalStrength;

    public static final ArrayList<WcdmaSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<WcdmaSignalStrength> arrayList = new ArrayList<WcdmaSignalStrength>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 8, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new WcdmaSignalStrength();
            ((WcdmaSignalStrength)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add((WcdmaSignalStrength)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<WcdmaSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 8);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
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
        if (this.signalStrength != ((WcdmaSignalStrength)object).signalStrength) {
            return false;
        }
        return this.bitErrorRate == ((WcdmaSignalStrength)object).bitErrorRate;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.signalStrength), HidlSupport.deepHashCode(this.bitErrorRate));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.signalStrength = hwBlob.getInt32(0L + l);
        this.bitErrorRate = hwBlob.getInt32(4L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".signalStrength = ");
        stringBuilder.append(this.signalStrength);
        stringBuilder.append(", .bitErrorRate = ");
        stringBuilder.append(this.bitErrorRate);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.signalStrength);
        hwBlob.putInt32(4L + l, this.bitErrorRate);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

