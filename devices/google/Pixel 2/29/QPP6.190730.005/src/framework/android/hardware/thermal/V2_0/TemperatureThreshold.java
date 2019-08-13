/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V2_0;

import android.hardware.thermal.V2_0.TemperatureType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class TemperatureThreshold {
    public float[] coldThrottlingThresholds = new float[7];
    public float[] hotThrottlingThresholds = new float[7];
    public String name = new String();
    public int type;
    public float vrThrottlingThreshold;

    public static final ArrayList<TemperatureThreshold> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<TemperatureThreshold> arrayList = new ArrayList<TemperatureThreshold>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 88, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            TemperatureThreshold temperatureThreshold = new TemperatureThreshold();
            temperatureThreshold.readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add(temperatureThreshold);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<TemperatureThreshold> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 88);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
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
        if (object.getClass() != TemperatureThreshold.class) {
            return false;
        }
        object = (TemperatureThreshold)object;
        if (this.type != ((TemperatureThreshold)object).type) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.name, ((TemperatureThreshold)object).name)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.hotThrottlingThresholds, ((TemperatureThreshold)object).hotThrottlingThresholds)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.coldThrottlingThresholds, ((TemperatureThreshold)object).coldThrottlingThresholds)) {
            return false;
        }
        return this.vrThrottlingThreshold == ((TemperatureThreshold)object).vrThrottlingThreshold;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.hotThrottlingThresholds), HidlSupport.deepHashCode(this.coldThrottlingThresholds), HidlSupport.deepHashCode(Float.valueOf(this.vrThrottlingThreshold)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(l + 0L);
        this.name = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        hwBlob.copyToFloatArray(l + 24L, this.hotThrottlingThresholds, 7);
        hwBlob.copyToFloatArray(l + 52L, this.coldThrottlingThresholds, 7);
        this.vrThrottlingThreshold = hwBlob.getFloat(l + 80L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(TemperatureType.toString(this.type));
        stringBuilder.append(", .name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .hotThrottlingThresholds = ");
        stringBuilder.append(Arrays.toString(this.hotThrottlingThresholds));
        stringBuilder.append(", .coldThrottlingThresholds = ");
        stringBuilder.append(Arrays.toString(this.coldThrottlingThresholds));
        stringBuilder.append(", .vrThrottlingThreshold = ");
        stringBuilder.append(this.vrThrottlingThreshold);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.type);
        hwBlob.putString(8L + l, this.name);
        float[] arrf = this.hotThrottlingThresholds;
        if (arrf != null && arrf.length == 7) {
            hwBlob.putFloatArray(24L + l, arrf);
            arrf = this.coldThrottlingThresholds;
            if (arrf != null && arrf.length == 7) {
                hwBlob.putFloatArray(52L + l, arrf);
                hwBlob.putFloat(80L + l, this.vrThrottlingThreshold);
                return;
            }
            throw new IllegalArgumentException("Array element is not of the expected length");
        }
        throw new IllegalArgumentException("Array element is not of the expected length");
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

