/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class TdscdmaSignalStrength {
    public int bitErrorRate;
    public int rscp;
    public int signalStrength;

    public static final ArrayList<TdscdmaSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<TdscdmaSignalStrength> arrayList = new ArrayList<TdscdmaSignalStrength>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 12, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new TdscdmaSignalStrength();
            ((TdscdmaSignalStrength)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 12);
            arrayList.add((TdscdmaSignalStrength)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<TdscdmaSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 12);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
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
        if (object.getClass() != TdscdmaSignalStrength.class) {
            return false;
        }
        object = (TdscdmaSignalStrength)object;
        if (this.signalStrength != ((TdscdmaSignalStrength)object).signalStrength) {
            return false;
        }
        if (this.bitErrorRate != ((TdscdmaSignalStrength)object).bitErrorRate) {
            return false;
        }
        return this.rscp == ((TdscdmaSignalStrength)object).rscp;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.signalStrength), HidlSupport.deepHashCode(this.bitErrorRate), HidlSupport.deepHashCode(this.rscp));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.signalStrength = hwBlob.getInt32(0L + l);
        this.bitErrorRate = hwBlob.getInt32(4L + l);
        this.rscp = hwBlob.getInt32(8L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".signalStrength = ");
        stringBuilder.append(this.signalStrength);
        stringBuilder.append(", .bitErrorRate = ");
        stringBuilder.append(this.bitErrorRate);
        stringBuilder.append(", .rscp = ");
        stringBuilder.append(this.rscp);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.signalStrength);
        hwBlob.putInt32(4L + l, this.bitErrorRate);
        hwBlob.putInt32(8L + l, this.rscp);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

