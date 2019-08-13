/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaSignalStrength;
import android.hardware.radio.V1_0.EvdoSignalStrength;
import android.hardware.radio.V1_0.GsmSignalStrength;
import android.hardware.radio.V1_0.LteSignalStrength;
import android.hardware.radio.V1_0.TdScdmaSignalStrength;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SignalStrength {
    public CdmaSignalStrength cdma = new CdmaSignalStrength();
    public EvdoSignalStrength evdo = new EvdoSignalStrength();
    public GsmSignalStrength gw = new GsmSignalStrength();
    public LteSignalStrength lte = new LteSignalStrength();
    public TdScdmaSignalStrength tdScdma = new TdScdmaSignalStrength();

    public static final ArrayList<SignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SignalStrength> arrayList = new ArrayList<SignalStrength>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 60, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SignalStrength();
            ((SignalStrength)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 60);
            arrayList.add((SignalStrength)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SignalStrength> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 60);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 60);
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
        if (object.getClass() != SignalStrength.class) {
            return false;
        }
        object = (SignalStrength)object;
        if (!HidlSupport.deepEquals(this.gw, ((SignalStrength)object).gw)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.cdma, ((SignalStrength)object).cdma)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.evdo, ((SignalStrength)object).evdo)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.lte, ((SignalStrength)object).lte)) {
            return false;
        }
        return HidlSupport.deepEquals(this.tdScdma, ((SignalStrength)object).tdScdma);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.gw), HidlSupport.deepHashCode(this.cdma), HidlSupport.deepHashCode(this.evdo), HidlSupport.deepHashCode(this.lte), HidlSupport.deepHashCode(this.tdScdma));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.gw.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.cdma.readEmbeddedFromParcel(hwParcel, hwBlob, 12L + l);
        this.evdo.readEmbeddedFromParcel(hwParcel, hwBlob, 20L + l);
        this.lte.readEmbeddedFromParcel(hwParcel, hwBlob, 32L + l);
        this.tdScdma.readEmbeddedFromParcel(hwParcel, hwBlob, 56L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(60L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".gw = ");
        stringBuilder.append(this.gw);
        stringBuilder.append(", .cdma = ");
        stringBuilder.append(this.cdma);
        stringBuilder.append(", .evdo = ");
        stringBuilder.append(this.evdo);
        stringBuilder.append(", .lte = ");
        stringBuilder.append(this.lte);
        stringBuilder.append(", .tdScdma = ");
        stringBuilder.append(this.tdScdma);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.gw.writeEmbeddedToBlob(hwBlob, 0L + l);
        this.cdma.writeEmbeddedToBlob(hwBlob, 12L + l);
        this.evdo.writeEmbeddedToBlob(hwBlob, 20L + l);
        this.lte.writeEmbeddedToBlob(hwBlob, 32L + l);
        this.tdScdma.writeEmbeddedToBlob(hwBlob, 56L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(60);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

