/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.hardware.contexthub.V1_0.SensorType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class PhysicalSensor {
    public int fifoMaxCount;
    public int fifoReservedCount;
    public long maxDelayMs;
    public long minDelayMs;
    public String name = new String();
    public float peakPowerMw;
    public int sensorType;
    public String type = new String();
    public String vendor = new String();
    public int version;

    public static final ArrayList<PhysicalSensor> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhysicalSensor> arrayList = new ArrayList<PhysicalSensor>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 96, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            PhysicalSensor physicalSensor = new PhysicalSensor();
            physicalSensor.readEmbeddedFromParcel(hwParcel, hwBlob, i * 96);
            arrayList.add(physicalSensor);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhysicalSensor> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 96);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
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
        if (object.getClass() != PhysicalSensor.class) {
            return false;
        }
        object = (PhysicalSensor)object;
        if (this.sensorType != ((PhysicalSensor)object).sensorType) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.type, ((PhysicalSensor)object).type)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.name, ((PhysicalSensor)object).name)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.vendor, ((PhysicalSensor)object).vendor)) {
            return false;
        }
        if (this.version != ((PhysicalSensor)object).version) {
            return false;
        }
        if (this.fifoReservedCount != ((PhysicalSensor)object).fifoReservedCount) {
            return false;
        }
        if (this.fifoMaxCount != ((PhysicalSensor)object).fifoMaxCount) {
            return false;
        }
        if (this.minDelayMs != ((PhysicalSensor)object).minDelayMs) {
            return false;
        }
        if (this.maxDelayMs != ((PhysicalSensor)object).maxDelayMs) {
            return false;
        }
        return this.peakPowerMw == ((PhysicalSensor)object).peakPowerMw;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.sensorType), HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.vendor), HidlSupport.deepHashCode(this.version), HidlSupport.deepHashCode(this.fifoReservedCount), HidlSupport.deepHashCode(this.fifoMaxCount), HidlSupport.deepHashCode(this.minDelayMs), HidlSupport.deepHashCode(this.maxDelayMs), HidlSupport.deepHashCode(Float.valueOf(this.peakPowerMw)));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.sensorType = hwBlob.getInt32(l + 0L);
        this.type = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.type.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.name = hwBlob.getString(l + 24L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 24L + 0L, false);
        this.vendor = hwBlob.getString(l + 40L);
        hwParcel.readEmbeddedBuffer(this.vendor.getBytes().length + 1, hwBlob.handle(), l + 40L + 0L, false);
        this.version = hwBlob.getInt32(l + 56L);
        this.fifoReservedCount = hwBlob.getInt32(l + 60L);
        this.fifoMaxCount = hwBlob.getInt32(l + 64L);
        this.minDelayMs = hwBlob.getInt64(l + 72L);
        this.maxDelayMs = hwBlob.getInt64(l + 80L);
        this.peakPowerMw = hwBlob.getFloat(l + 88L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".sensorType = ");
        stringBuilder.append(SensorType.toString(this.sensorType));
        stringBuilder.append(", .type = ");
        stringBuilder.append(this.type);
        stringBuilder.append(", .name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .vendor = ");
        stringBuilder.append(this.vendor);
        stringBuilder.append(", .version = ");
        stringBuilder.append(this.version);
        stringBuilder.append(", .fifoReservedCount = ");
        stringBuilder.append(this.fifoReservedCount);
        stringBuilder.append(", .fifoMaxCount = ");
        stringBuilder.append(this.fifoMaxCount);
        stringBuilder.append(", .minDelayMs = ");
        stringBuilder.append(this.minDelayMs);
        stringBuilder.append(", .maxDelayMs = ");
        stringBuilder.append(this.maxDelayMs);
        stringBuilder.append(", .peakPowerMw = ");
        stringBuilder.append(this.peakPowerMw);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.sensorType);
        hwBlob.putString(8L + l, this.type);
        hwBlob.putString(24L + l, this.name);
        hwBlob.putString(40L + l, this.vendor);
        hwBlob.putInt32(56L + l, this.version);
        hwBlob.putInt32(60L + l, this.fifoReservedCount);
        hwBlob.putInt32(64L + l, this.fifoMaxCount);
        hwBlob.putInt64(72L + l, this.minDelayMs);
        hwBlob.putInt64(80L + l, this.maxDelayMs);
        hwBlob.putFloat(88L + l, this.peakPowerMw);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

