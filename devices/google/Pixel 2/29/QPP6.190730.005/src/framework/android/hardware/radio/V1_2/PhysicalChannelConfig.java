/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_2.CellConnectionStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class PhysicalChannelConfig {
    public int cellBandwidthDownlink;
    public int status;

    public static final ArrayList<PhysicalChannelConfig> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhysicalChannelConfig> arrayList = new ArrayList<PhysicalChannelConfig>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 8, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            PhysicalChannelConfig physicalChannelConfig = new PhysicalChannelConfig();
            physicalChannelConfig.readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add(physicalChannelConfig);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhysicalChannelConfig> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 8);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
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
        if (object.getClass() != PhysicalChannelConfig.class) {
            return false;
        }
        object = (PhysicalChannelConfig)object;
        if (this.status != ((PhysicalChannelConfig)object).status) {
            return false;
        }
        return this.cellBandwidthDownlink == ((PhysicalChannelConfig)object).cellBandwidthDownlink;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.status), HidlSupport.deepHashCode(this.cellBandwidthDownlink));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.status = hwBlob.getInt32(0L + l);
        this.cellBandwidthDownlink = hwBlob.getInt32(4L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".status = ");
        stringBuilder.append(CellConnectionStatus.toString(this.status));
        stringBuilder.append(", .cellBandwidthDownlink = ");
        stringBuilder.append(this.cellBandwidthDownlink);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.status);
        hwBlob.putInt32(4L + l, this.cellBandwidthDownlink);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

