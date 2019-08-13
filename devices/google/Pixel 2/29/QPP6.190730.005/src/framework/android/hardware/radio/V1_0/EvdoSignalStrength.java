/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class EvdoSignalStrength {
    public int dbm;
    public int ecio;
    public int signalNoiseRatio;

    public static final ArrayList<EvdoSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<EvdoSignalStrength> arrayList = new ArrayList<EvdoSignalStrength>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 12, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new EvdoSignalStrength();
            ((EvdoSignalStrength)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 12);
            arrayList.add((EvdoSignalStrength)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<EvdoSignalStrength> arrayList) {
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
        if (object.getClass() != EvdoSignalStrength.class) {
            return false;
        }
        object = (EvdoSignalStrength)object;
        if (this.dbm != ((EvdoSignalStrength)object).dbm) {
            return false;
        }
        if (this.ecio != ((EvdoSignalStrength)object).ecio) {
            return false;
        }
        return this.signalNoiseRatio == ((EvdoSignalStrength)object).signalNoiseRatio;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.dbm), HidlSupport.deepHashCode(this.ecio), HidlSupport.deepHashCode(this.signalNoiseRatio));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.dbm = hwBlob.getInt32(0L + l);
        this.ecio = hwBlob.getInt32(4L + l);
        this.signalNoiseRatio = hwBlob.getInt32(8L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".dbm = ");
        stringBuilder.append(this.dbm);
        stringBuilder.append(", .ecio = ");
        stringBuilder.append(this.ecio);
        stringBuilder.append(", .signalNoiseRatio = ");
        stringBuilder.append(this.signalNoiseRatio);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.dbm);
        hwBlob.putInt32(4L + l, this.ecio);
        hwBlob.putInt32(8L + l, this.signalNoiseRatio);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

