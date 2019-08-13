/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_4.RadioFrequencyInfo;
import android.hardware.radio.V1_4.RadioTechnology;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class PhysicalChannelConfig {
    public android.hardware.radio.V1_2.PhysicalChannelConfig base = new android.hardware.radio.V1_2.PhysicalChannelConfig();
    public ArrayList<Integer> contextIds = new ArrayList();
    public int physicalCellId;
    public int rat;
    public RadioFrequencyInfo rfInfo = new RadioFrequencyInfo();

    public static final ArrayList<PhysicalChannelConfig> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhysicalChannelConfig> arrayList = new ArrayList<PhysicalChannelConfig>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 48, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new PhysicalChannelConfig();
            ((PhysicalChannelConfig)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 48);
            arrayList.add((PhysicalChannelConfig)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhysicalChannelConfig> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 48);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
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
        if (!HidlSupport.deepEquals(this.base, ((PhysicalChannelConfig)object).base)) {
            return false;
        }
        if (this.rat != ((PhysicalChannelConfig)object).rat) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.rfInfo, ((PhysicalChannelConfig)object).rfInfo)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.contextIds, ((PhysicalChannelConfig)object).contextIds)) {
            return false;
        }
        return this.physicalCellId == ((PhysicalChannelConfig)object).physicalCellId;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.rat), HidlSupport.deepHashCode(this.rfInfo), HidlSupport.deepHashCode(this.contextIds), HidlSupport.deepHashCode(this.physicalCellId));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel((HwParcel)object, hwBlob, l + 0L);
        this.rat = hwBlob.getInt32(l + 8L);
        this.rfInfo.readEmbeddedFromParcel((HwParcel)object, hwBlob, l + 12L);
        int n = hwBlob.getInt32(l + 24L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 4, hwBlob.handle(), l + 24L + 0L, true);
        this.contextIds.clear();
        for (int i = 0; i < n; ++i) {
            int n2 = ((HwBlob)object).getInt32(i * 4);
            this.contextIds.add(n2);
        }
        this.physicalCellId = hwBlob.getInt32(l + 40L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .rat = ");
        stringBuilder.append(RadioTechnology.toString(this.rat));
        stringBuilder.append(", .rfInfo = ");
        stringBuilder.append(this.rfInfo);
        stringBuilder.append(", .contextIds = ");
        stringBuilder.append(this.contextIds);
        stringBuilder.append(", .physicalCellId = ");
        stringBuilder.append(this.physicalCellId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, l + 0L);
        hwBlob.putInt32(l + 8L, this.rat);
        this.rfInfo.writeEmbeddedToBlob(hwBlob, l + 12L);
        int n = this.contextIds.size();
        hwBlob.putInt32(l + 24L + 8L, n);
        hwBlob.putBool(l + 24L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 4);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt32(i * 4, this.contextIds.get(i));
        }
        hwBlob.putBlob(24L + l + 0L, hwBlob2);
        hwBlob.putInt32(40L + l, this.physicalCellId);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

