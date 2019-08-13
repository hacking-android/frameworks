/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.HardwareConfigModem;
import android.hardware.radio.V1_0.HardwareConfigSim;
import android.hardware.radio.V1_0.HardwareConfigState;
import android.hardware.radio.V1_0.HardwareConfigType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class HardwareConfig {
    public ArrayList<HardwareConfigModem> modem = new ArrayList();
    public ArrayList<HardwareConfigSim> sim = new ArrayList();
    public int state;
    public int type;
    public String uuid = new String();

    public static final ArrayList<HardwareConfig> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<HardwareConfig> arrayList = new ArrayList<HardwareConfig>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 64, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            HardwareConfig hardwareConfig = new HardwareConfig();
            hardwareConfig.readEmbeddedFromParcel(hwParcel, hwBlob, i * 64);
            arrayList.add(hardwareConfig);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<HardwareConfig> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 64);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
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
        if (object.getClass() != HardwareConfig.class) {
            return false;
        }
        object = (HardwareConfig)object;
        if (this.type != ((HardwareConfig)object).type) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.uuid, ((HardwareConfig)object).uuid)) {
            return false;
        }
        if (this.state != ((HardwareConfig)object).state) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.modem, ((HardwareConfig)object).modem)) {
            return false;
        }
        return HidlSupport.deepEquals(this.sim, ((HardwareConfig)object).sim);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.uuid), HidlSupport.deepHashCode(this.state), HidlSupport.deepHashCode(this.modem), HidlSupport.deepHashCode(this.sim));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob object, long l) {
        int n;
        Object object2;
        this.type = ((HwBlob)object).getInt32(l + 0L);
        this.uuid = ((HwBlob)object).getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.uuid.getBytes().length + 1, ((HwBlob)object).handle(), l + 8L + 0L, false);
        this.state = ((HwBlob)object).getInt32(l + 24L);
        int n2 = ((HwBlob)object).getInt32(l + 32L + 8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n2 * 20, ((HwBlob)object).handle(), l + 32L + 0L, true);
        this.modem.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new HardwareConfigModem();
            ((HardwareConfigModem)object2).readEmbeddedFromParcel(hwParcel, hwBlob, n * 20);
            this.modem.add((HardwareConfigModem)object2);
        }
        n2 = ((HwBlob)object).getInt32(l + 48L + 8L);
        object2 = hwParcel.readEmbeddedBuffer(n2 * 16, ((HwBlob)object).handle(), l + 48L + 0L, true);
        this.sim.clear();
        for (n = 0; n < n2; ++n) {
            object = new HardwareConfigSim();
            ((HardwareConfigSim)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 16);
            this.sim.add((HardwareConfigSim)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(HardwareConfigType.toString(this.type));
        stringBuilder.append(", .uuid = ");
        stringBuilder.append(this.uuid);
        stringBuilder.append(", .state = ");
        stringBuilder.append(HardwareConfigState.toString(this.state));
        stringBuilder.append(", .modem = ");
        stringBuilder.append(this.modem);
        stringBuilder.append(", .sim = ");
        stringBuilder.append(this.sim);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.type);
        hwBlob.putString(l + 8L, this.uuid);
        hwBlob.putInt32(l + 24L, this.state);
        int n2 = this.modem.size();
        hwBlob.putInt32(l + 32L + 8L, n2);
        hwBlob.putBool(l + 32L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 20);
        for (n = 0; n < n2; ++n) {
            this.modem.get(n).writeEmbeddedToBlob(hwBlob2, n * 20);
        }
        hwBlob.putBlob(l + 32L + 0L, hwBlob2);
        n2 = this.sim.size();
        hwBlob.putInt32(l + 48L + 8L, n2);
        hwBlob.putBool(l + 48L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            this.sim.get(n).writeEmbeddedToBlob(hwBlob2, n * 16);
        }
        hwBlob.putBlob(l + 48L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

