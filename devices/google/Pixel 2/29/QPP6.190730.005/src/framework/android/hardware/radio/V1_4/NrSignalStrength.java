/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class NrSignalStrength {
    public int csiRsrp;
    public int csiRsrq;
    public int csiSinr;
    public int ssRsrp;
    public int ssRsrq;
    public int ssSinr;

    public static final ArrayList<NrSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NrSignalStrength> arrayList = new ArrayList<NrSignalStrength>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 24, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            NrSignalStrength nrSignalStrength = new NrSignalStrength();
            nrSignalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add(nrSignalStrength);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NrSignalStrength> arrayList) {
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
        if (object.getClass() != NrSignalStrength.class) {
            return false;
        }
        object = (NrSignalStrength)object;
        if (this.ssRsrp != ((NrSignalStrength)object).ssRsrp) {
            return false;
        }
        if (this.ssRsrq != ((NrSignalStrength)object).ssRsrq) {
            return false;
        }
        if (this.ssSinr != ((NrSignalStrength)object).ssSinr) {
            return false;
        }
        if (this.csiRsrp != ((NrSignalStrength)object).csiRsrp) {
            return false;
        }
        if (this.csiRsrq != ((NrSignalStrength)object).csiRsrq) {
            return false;
        }
        return this.csiSinr == ((NrSignalStrength)object).csiSinr;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.ssRsrp), HidlSupport.deepHashCode(this.ssRsrq), HidlSupport.deepHashCode(this.ssSinr), HidlSupport.deepHashCode(this.csiRsrp), HidlSupport.deepHashCode(this.csiRsrq), HidlSupport.deepHashCode(this.csiSinr));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.ssRsrp = hwBlob.getInt32(0L + l);
        this.ssRsrq = hwBlob.getInt32(4L + l);
        this.ssSinr = hwBlob.getInt32(8L + l);
        this.csiRsrp = hwBlob.getInt32(12L + l);
        this.csiRsrq = hwBlob.getInt32(16L + l);
        this.csiSinr = hwBlob.getInt32(20L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".ssRsrp = ");
        stringBuilder.append(this.ssRsrp);
        stringBuilder.append(", .ssRsrq = ");
        stringBuilder.append(this.ssRsrq);
        stringBuilder.append(", .ssSinr = ");
        stringBuilder.append(this.ssSinr);
        stringBuilder.append(", .csiRsrp = ");
        stringBuilder.append(this.csiRsrp);
        stringBuilder.append(", .csiRsrq = ");
        stringBuilder.append(this.csiRsrq);
        stringBuilder.append(", .csiSinr = ");
        stringBuilder.append(this.csiSinr);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.ssRsrp);
        hwBlob.putInt32(4L + l, this.ssRsrq);
        hwBlob.putInt32(8L + l, this.ssSinr);
        hwBlob.putInt32(12L + l, this.csiRsrp);
        hwBlob.putInt32(16L + l, this.csiRsrq);
        hwBlob.putInt32(20L + l, this.csiSinr);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

