/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class HardwareConfigSim {
    public String modemUuid = new String();

    public static final ArrayList<HardwareConfigSim> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<HardwareConfigSim> arrayList = new ArrayList<HardwareConfigSim>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 16, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            HardwareConfigSim hardwareConfigSim = new HardwareConfigSim();
            hardwareConfigSim.readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add(hardwareConfigSim);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<HardwareConfigSim> arrayList) {
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
        if (object.getClass() != HardwareConfigSim.class) {
            return false;
        }
        object = (HardwareConfigSim)object;
        return HidlSupport.deepEquals(this.modemUuid, ((HardwareConfigSim)object).modemUuid);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.modemUuid));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.modemUuid = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.modemUuid.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".modemUuid = ");
        stringBuilder.append(this.modemUuid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.modemUuid);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

