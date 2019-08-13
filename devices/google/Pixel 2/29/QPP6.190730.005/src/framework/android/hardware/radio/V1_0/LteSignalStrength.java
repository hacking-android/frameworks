/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class LteSignalStrength {
    public int cqi;
    public int rsrp;
    public int rsrq;
    public int rssnr;
    public int signalStrength;
    public int timingAdvance;

    public static final ArrayList<LteSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LteSignalStrength> arrayList = new ArrayList<LteSignalStrength>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 24, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            LteSignalStrength lteSignalStrength = new LteSignalStrength();
            lteSignalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add(lteSignalStrength);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LteSignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 24);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
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
        if (object.getClass() != LteSignalStrength.class) {
            return false;
        }
        object = (LteSignalStrength)object;
        if (this.signalStrength != ((LteSignalStrength)object).signalStrength) {
            return false;
        }
        if (this.rsrp != ((LteSignalStrength)object).rsrp) {
            return false;
        }
        if (this.rsrq != ((LteSignalStrength)object).rsrq) {
            return false;
        }
        if (this.rssnr != ((LteSignalStrength)object).rssnr) {
            return false;
        }
        if (this.cqi != ((LteSignalStrength)object).cqi) {
            return false;
        }
        return this.timingAdvance == ((LteSignalStrength)object).timingAdvance;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.signalStrength), HidlSupport.deepHashCode(this.rsrp), HidlSupport.deepHashCode(this.rsrq), HidlSupport.deepHashCode(this.rssnr), HidlSupport.deepHashCode(this.cqi), HidlSupport.deepHashCode(this.timingAdvance));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.signalStrength = hwBlob.getInt32(0L + l);
        this.rsrp = hwBlob.getInt32(4L + l);
        this.rsrq = hwBlob.getInt32(8L + l);
        this.rssnr = hwBlob.getInt32(12L + l);
        this.cqi = hwBlob.getInt32(16L + l);
        this.timingAdvance = hwBlob.getInt32(20L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".signalStrength = ");
        stringBuilder.append(this.signalStrength);
        stringBuilder.append(", .rsrp = ");
        stringBuilder.append(this.rsrp);
        stringBuilder.append(", .rsrq = ");
        stringBuilder.append(this.rsrq);
        stringBuilder.append(", .rssnr = ");
        stringBuilder.append(this.rssnr);
        stringBuilder.append(", .cqi = ");
        stringBuilder.append(this.cqi);
        stringBuilder.append(", .timingAdvance = ");
        stringBuilder.append(this.timingAdvance);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.signalStrength);
        hwBlob.putInt32(4L + l, this.rsrp);
        hwBlob.putInt32(8L + l, this.rsrq);
        hwBlob.putInt32(12L + l, this.rssnr);
        hwBlob.putInt32(16L + l, this.cqi);
        hwBlob.putInt32(20L + l, this.timingAdvance);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

