/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V2_0;

import android.hardware.thermal.V2_0.CoolingType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CoolingDevice {
    public String name = new String();
    public int type;
    public long value;

    public static final ArrayList<CoolingDevice> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CoolingDevice> arrayList = new ArrayList<CoolingDevice>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 32, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CoolingDevice();
            ((CoolingDevice)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add((CoolingDevice)object);
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
        return this.value == ((CoolingDevice)object).value;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.value));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(l + 0L);
        this.name = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.value = hwBlob.getInt64(l + 24L);
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
        stringBuilder.append(", .value = ");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.type);
        hwBlob.putString(8L + l, this.name);
        hwBlob.putInt64(24L + l, this.value);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

