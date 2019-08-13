/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.HidlSupport
 *  android.os.HwBlob
 *  android.os.HwParcel
 */
package android.hardware.radio.config.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SimSlotStatus {
    public android.hardware.radio.config.V1_0.SimSlotStatus base = new android.hardware.radio.config.V1_0.SimSlotStatus();
    public String eid = new String();

    public static final ArrayList<SimSlotStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SimSlotStatus> arrayList = new ArrayList<SimSlotStatus>();
        Object object = hwParcel.readBuffer(16L);
        int n = object.getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer((long)(n * 64), object.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SimSlotStatus();
            ((SimSlotStatus)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 64);
            arrayList.add((SimSlotStatus)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SimSlotStatus> arrayList) {
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
        if (object.getClass() != SimSlotStatus.class) {
            return false;
        }
        object = (SimSlotStatus)object;
        if (!HidlSupport.deepEquals((Object)this.base, (Object)((SimSlotStatus)object).base)) {
            return false;
        }
        return HidlSupport.deepEquals((Object)this.eid, (Object)((SimSlotStatus)object).eid);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode((Object)this.base), HidlSupport.deepHashCode((Object)this.eid));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, l + 0L);
        this.eid = hwBlob.getString(l + 48L);
        hwParcel.readEmbeddedBuffer((long)(this.eid.getBytes().length + 1), hwBlob.handle(), l + 48L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .eid = ");
        stringBuilder.append(this.eid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        hwBlob.putString(48L + l, this.eid);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

