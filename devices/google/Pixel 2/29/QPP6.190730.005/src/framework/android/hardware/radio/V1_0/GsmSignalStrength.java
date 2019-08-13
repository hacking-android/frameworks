/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class GsmSignalStrength {
    public int bitErrorRate;
    public int signalStrength;
    public int timingAdvance;

    public static final ArrayList<GsmSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GsmSignalStrength> arrayList = new ArrayList<GsmSignalStrength>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 12, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            GsmSignalStrength gsmSignalStrength = new GsmSignalStrength();
            gsmSignalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, i * 12);
            arrayList.add(gsmSignalStrength);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GsmSignalStrength> arrayList) {
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
        if (object.getClass() != GsmSignalStrength.class) {
            return false;
        }
        object = (GsmSignalStrength)object;
        if (this.signalStrength != ((GsmSignalStrength)object).signalStrength) {
            return false;
        }
        if (this.bitErrorRate != ((GsmSignalStrength)object).bitErrorRate) {
            return false;
        }
        return this.timingAdvance == ((GsmSignalStrength)object).timingAdvance;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.signalStrength), HidlSupport.deepHashCode(this.bitErrorRate), HidlSupport.deepHashCode(this.timingAdvance));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.signalStrength = hwBlob.getInt32(0L + l);
        this.bitErrorRate = hwBlob.getInt32(4L + l);
        this.timingAdvance = hwBlob.getInt32(8L + l);
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
        stringBuilder.append(", .timingAdvance = ");
        stringBuilder.append(this.timingAdvance);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.signalStrength);
        hwBlob.putInt32(4L + l, this.bitErrorRate);
        hwBlob.putInt32(8L + l, this.timingAdvance);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

