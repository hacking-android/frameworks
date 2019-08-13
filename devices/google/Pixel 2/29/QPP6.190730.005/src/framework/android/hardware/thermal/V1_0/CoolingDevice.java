/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V1_0;

import android.hardware.thermal.V1_0.CoolingType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CoolingDevice {
    public float currentValue;
    public String name = new String();
    public int type;

    public static final ArrayList<CoolingDevice> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CoolingDevice> arrayList = new ArrayList<CoolingDevice>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 32, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CoolingDevice coolingDevice = new CoolingDevice();
            coolingDevice.readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add(coolingDevice);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CoolingDevice> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 32);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
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
        if (object.getClass() != CoolingDevice.class) {
            return false;
        }
        object = (CoolingDevice)object;
        if (this.type != ((CoolingDevice)object).type) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.name, ((CoolingDevice)object).name)) {
            return false;
        }
        return this.currentValue == ((CoolingDevice)object).currentValue;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(Float.valueOf(this.currentValue)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(l + 0L);
        this.name = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.currentValue = hwBlob.getFloat(l + 24L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(CoolingType.toString(this.type));
        stringBuilder.append(", .name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .currentValue = ");
        stringBuilder.append(this.currentValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.type);
        hwBlob.putString(8L + l, this.name);
        hwBlob.putFloat(24L + l, this.currentValue);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

