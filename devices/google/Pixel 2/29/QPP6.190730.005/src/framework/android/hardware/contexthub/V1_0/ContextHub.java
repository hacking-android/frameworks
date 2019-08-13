/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.hardware.contexthub.V1_0.PhysicalSensor;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class ContextHub {
    public byte chreApiMajorVersion;
    public byte chreApiMinorVersion;
    public short chrePatchVersion;
    public long chrePlatformId;
    public ArrayList<PhysicalSensor> connectedSensors = new ArrayList();
    public int hubId;
    public int maxSupportedMsgLen;
    public String name = new String();
    public float peakMips;
    public float peakPowerDrawMw;
    public int platformVersion;
    public float sleepPowerDrawMw;
    public float stoppedPowerDrawMw;
    public String toolchain = new String();
    public int toolchainVersion;
    public String vendor = new String();

    public static final ArrayList<ContextHub> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ContextHub> arrayList = new ArrayList<ContextHub>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 120, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            ContextHub contextHub = new ContextHub();
            contextHub.readEmbeddedFromParcel(hwParcel, hwBlob, i * 120);
            arrayList.add(contextHub);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ContextHub> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 120);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
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
        if (object.getClass() != ContextHub.class) {
            return false;
        }
        object = (ContextHub)object;
        if (!HidlSupport.deepEquals(this.name, ((ContextHub)object).name)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.vendor, ((ContextHub)object).vendor)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.toolchain, ((ContextHub)object).toolchain)) {
            return false;
        }
        if (this.platformVersion != ((ContextHub)object).platformVersion) {
            return false;
        }
        if (this.toolchainVersion != ((ContextHub)object).toolchainVersion) {
            return false;
        }
        if (this.hubId != ((ContextHub)object).hubId) {
            return false;
        }
        if (this.peakMips != ((ContextHub)object).peakMips) {
            return false;
        }
        if (this.stoppedPowerDrawMw != ((ContextHub)object).stoppedPowerDrawMw) {
            return false;
        }
        if (this.sleepPowerDrawMw != ((ContextHub)object).sleepPowerDrawMw) {
            return false;
        }
        if (this.peakPowerDrawMw != ((ContextHub)object).peakPowerDrawMw) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.connectedSensors, ((ContextHub)object).connectedSensors)) {
            return false;
        }
        if (this.maxSupportedMsgLen != ((ContextHub)object).maxSupportedMsgLen) {
            return false;
        }
        if (this.chrePlatformId != ((ContextHub)object).chrePlatformId) {
            return false;
        }
        if (this.chreApiMajorVersion != ((ContextHub)object).chreApiMajorVersion) {
            return false;
        }
        if (this.chreApiMinorVersion != ((ContextHub)object).chreApiMinorVersion) {
            return false;
        }
        return this.chrePatchVersion == ((ContextHub)object).chrePatchVersion;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.vendor), HidlSupport.deepHashCode(this.toolchain), HidlSupport.deepHashCode(this.platformVersion), HidlSupport.deepHashCode(this.toolchainVersion), HidlSupport.deepHashCode(this.hubId), HidlSupport.deepHashCode(Float.valueOf(this.peakMips)), HidlSupport.deepHashCode(Float.valueOf(this.stoppedPowerDrawMw)), HidlSupport.deepHashCode(Float.valueOf(this.sleepPowerDrawMw)), HidlSupport.deepHashCode(Float.valueOf(this.peakPowerDrawMw)), HidlSupport.deepHashCode(this.connectedSensors), HidlSupport.deepHashCode(this.maxSupportedMsgLen), HidlSupport.deepHashCode(this.chrePlatformId), HidlSupport.deepHashCode(this.chreApiMajorVersion), HidlSupport.deepHashCode(this.chreApiMinorVersion), HidlSupport.deepHashCode(this.chrePatchVersion));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.name = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.vendor = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.vendor.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.toolchain = hwBlob.getString(l + 32L);
        hwParcel.readEmbeddedBuffer(this.toolchain.getBytes().length + 1, hwBlob.handle(), l + 32L + 0L, false);
        this.platformVersion = hwBlob.getInt32(l + 48L);
        this.toolchainVersion = hwBlob.getInt32(l + 52L);
        this.hubId = hwBlob.getInt32(l + 56L);
        this.peakMips = hwBlob.getFloat(l + 60L);
        this.stoppedPowerDrawMw = hwBlob.getFloat(l + 64L);
        this.sleepPowerDrawMw = hwBlob.getFloat(l + 68L);
        this.peakPowerDrawMw = hwBlob.getFloat(l + 72L);
        int n = hwBlob.getInt32(l + 80L + 8L);
        HwBlob hwBlob2 = hwParcel.readEmbeddedBuffer(n * 96, hwBlob.handle(), l + 80L + 0L, true);
        this.connectedSensors.clear();
        for (int i = 0; i < n; ++i) {
            PhysicalSensor physicalSensor = new PhysicalSensor();
            physicalSensor.readEmbeddedFromParcel(hwParcel, hwBlob2, i * 96);
            this.connectedSensors.add(physicalSensor);
        }
        this.maxSupportedMsgLen = hwBlob.getInt32(l + 96L);
        this.chrePlatformId = hwBlob.getInt64(l + 104L);
        this.chreApiMajorVersion = hwBlob.getInt8(l + 112L);
        this.chreApiMinorVersion = hwBlob.getInt8(l + 113L);
        this.chrePatchVersion = hwBlob.getInt16(l + 114L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .vendor = ");
        stringBuilder.append(this.vendor);
        stringBuilder.append(", .toolchain = ");
        stringBuilder.append(this.toolchain);
        stringBuilder.append(", .platformVersion = ");
        stringBuilder.append(this.platformVersion);
        stringBuilder.append(", .toolchainVersion = ");
        stringBuilder.append(this.toolchainVersion);
        stringBuilder.append(", .hubId = ");
        stringBuilder.append(this.hubId);
        stringBuilder.append(", .peakMips = ");
        stringBuilder.append(this.peakMips);
        stringBuilder.append(", .stoppedPowerDrawMw = ");
        stringBuilder.append(this.stoppedPowerDrawMw);
        stringBuilder.append(", .sleepPowerDrawMw = ");
        stringBuilder.append(this.sleepPowerDrawMw);
        stringBuilder.append(", .peakPowerDrawMw = ");
        stringBuilder.append(this.peakPowerDrawMw);
        stringBuilder.append(", .connectedSensors = ");
        stringBuilder.append(this.connectedSensors);
        stringBuilder.append(", .maxSupportedMsgLen = ");
        stringBuilder.append(this.maxSupportedMsgLen);
        stringBuilder.append(", .chrePlatformId = ");
        stringBuilder.append(this.chrePlatformId);
        stringBuilder.append(", .chreApiMajorVersion = ");
        stringBuilder.append(this.chreApiMajorVersion);
        stringBuilder.append(", .chreApiMinorVersion = ");
        stringBuilder.append(this.chreApiMinorVersion);
        stringBuilder.append(", .chrePatchVersion = ");
        stringBuilder.append(this.chrePatchVersion);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(l + 0L, this.name);
        hwBlob.putString(16L + l, this.vendor);
        hwBlob.putString(32L + l, this.toolchain);
        hwBlob.putInt32(48L + l, this.platformVersion);
        hwBlob.putInt32(52L + l, this.toolchainVersion);
        hwBlob.putInt32(56L + l, this.hubId);
        hwBlob.putFloat(60L + l, this.peakMips);
        hwBlob.putFloat(64L + l, this.stoppedPowerDrawMw);
        hwBlob.putFloat(68L + l, this.sleepPowerDrawMw);
        hwBlob.putFloat(72L + l, this.peakPowerDrawMw);
        int n = this.connectedSensors.size();
        hwBlob.putInt32(l + 80L + 8L, n);
        hwBlob.putBool(l + 80L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 96);
        for (int i = 0; i < n; ++i) {
            this.connectedSensors.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
        }
        hwBlob.putBlob(80L + l + 0L, hwBlob2);
        hwBlob.putInt32(96L + l, this.maxSupportedMsgLen);
        hwBlob.putInt64(104L + l, this.chrePlatformId);
        hwBlob.putInt8(112L + l, this.chreApiMajorVersion);
        hwBlob.putInt8(113L + l, this.chreApiMinorVersion);
        hwBlob.putInt16(114L + l, this.chrePatchVersion);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

