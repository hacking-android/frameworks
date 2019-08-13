/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V1_0;

import android.hardware.thermal.V1_0.TemperatureType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class Temperature {
    public float currentValue;
    public String name = new String();
    public float shutdownThreshold;
    public float throttlingThreshold;
    public int type;
    public float vrThrottlingThreshold;

    public static final ArrayList<Temperature> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Temperature> arrayList = new ArrayList<Temperature>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            Temperature temperature = new Temperature();
            temperature.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(temperature);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Temperature> arrayList) {
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
        if (object.getClass() != Temperature.class) {
            return false;
        }
        object = (Temperature)object;
        if (this.type != ((Temperature)object).type) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.name, ((Temperature)object).name)) {
            return false;
        }
        if (this.currentValue != ((Temperature)object).currentValue) {
            return false;
        }
        if (this.throttlingThreshold != ((Temperature)object).throttlingThreshold) {
            return false;
        }
        if (this.shutdownThreshold != ((Temperature)object).shutdownThreshold) {
            return false;
        }
        return this.vrThrottlingThreshold == ((Temperature)object).vrThrottlingThreshold;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(Float.valueOf(this.currentValue)), HidlSupport.deepHashCode(Float.valueOf(this.throttlingThreshold)), HidlSupport.deepHashCode(Float.valueOf(this.shutdownThreshold)), HidlSupport.deepHashCode(Float.valueOf(this.vrThrottlingThreshold)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(l + 0L);
        this.name = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.currentValue = hwBlob.getFloat(l + 24L);
        this.throttlingThreshold = hwBlob.getFloat(l + 28L);
        this.shutdownThreshold = hwBlob.getFloat(l + 32L);
        this.vrThrottlingThreshold = hwBlob.getFloat(l + 36L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(TemperatureType.toString(this.type));
        stringBuilder.append(", .name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .currentValue = ");
        stringBuilder.append(this.currentValue);
        stringBuilder.append(", .throttlingThreshold = ");
        stringBuilder.append(this.throttlingThreshold);
        stringBuilder.append(", .shutdownThreshold = ");
        stringBuilder.append(this.shutdownThreshold);
        stringBuilder.append(", .vrThrottlingThreshold = ");
        stringBuilder.append(this.vrThrottlingThreshold);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.type);
        hwBlob.putString(8L + l, this.name);
        hwBlob.putFloat(24L + l, this.currentValue);
        hwBlob.putFloat(28L + l, this.throttlingThreshold);
        hwBlob.putFloat(32L + l, this.shutdownThreshold);
        hwBlob.putFloat(36L + l, this.vrThrottlingThreshold);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

