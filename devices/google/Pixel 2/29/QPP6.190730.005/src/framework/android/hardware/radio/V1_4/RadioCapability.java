/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.RadioCapabilityPhase;
import android.hardware.radio.V1_0.RadioCapabilityStatus;
import android.hardware.radio.V1_4.RadioAccessFamily;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class RadioCapability {
    public String logicalModemUuid = new String();
    public int phase;
    public int raf;
    public int session;
    public int status;

    public static final ArrayList<RadioCapability> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioCapability> arrayList = new ArrayList<RadioCapability>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 40, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new RadioCapability();
            ((RadioCapability)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add((RadioCapability)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioCapability> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 40);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
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
        if (object.getClass() != RadioCapability.class) {
            return false;
        }
        object = (RadioCapability)object;
        if (this.session != ((RadioCapability)object).session) {
            return false;
        }
        if (this.phase != ((RadioCapability)object).phase) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.raf, ((RadioCapability)object).raf)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.logicalModemUuid, ((RadioCapability)object).logicalModemUuid)) {
            return false;
        }
        return this.status == ((RadioCapability)object).status;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.session), HidlSupport.deepHashCode(this.phase), HidlSupport.deepHashCode(this.raf), HidlSupport.deepHashCode(this.logicalModemUuid), HidlSupport.deepHashCode(this.status));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.session = hwBlob.getInt32(l + 0L);
        this.phase = hwBlob.getInt32(l + 4L);
        this.raf = hwBlob.getInt32(l + 8L);
        this.logicalModemUuid = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.logicalModemUuid.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.status = hwBlob.getInt32(l + 32L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".session = ");
        stringBuilder.append(this.session);
        stringBuilder.append(", .phase = ");
        stringBuilder.append(RadioCapabilityPhase.toString(this.phase));
        stringBuilder.append(", .raf = ");
        stringBuilder.append(RadioAccessFamily.dumpBitfield(this.raf));
        stringBuilder.append(", .logicalModemUuid = ");
        stringBuilder.append(this.logicalModemUuid);
        stringBuilder.append(", .status = ");
        stringBuilder.append(RadioCapabilityStatus.toString(this.status));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.session);
        hwBlob.putInt32(4L + l, this.phase);
        hwBlob.putInt32(8L + l, this.raf);
        hwBlob.putString(16L + l, this.logicalModemUuid);
        hwBlob.putInt32(32L + l, this.status);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

