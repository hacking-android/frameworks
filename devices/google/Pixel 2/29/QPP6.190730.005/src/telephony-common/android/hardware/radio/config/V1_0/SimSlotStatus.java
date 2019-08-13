/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.V1_0.CardState
 *  android.os.HidlSupport
 *  android.os.HwBlob
 *  android.os.HwParcel
 */
package android.hardware.radio.config.V1_0;

import android.hardware.radio.V1_0.CardState;
import android.hardware.radio.config.V1_0.SlotState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SimSlotStatus {
    public String atr = new String();
    public int cardState;
    public String iccid = new String();
    public int logicalSlotId;
    public int slotState;

    public static final ArrayList<SimSlotStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SimSlotStatus> arrayList = new ArrayList<SimSlotStatus>();
        Object object = hwParcel.readBuffer(16L);
        int n = object.getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer((long)(n * 48), object.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SimSlotStatus();
            ((SimSlotStatus)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 48);
            arrayList.add((SimSlotStatus)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SimSlotStatus> arrayList) {
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
        if (object.getClass() != SimSlotStatus.class) {
            return false;
        }
        object = (SimSlotStatus)object;
        if (this.cardState != ((SimSlotStatus)object).cardState) {
            return false;
        }
        if (this.slotState != ((SimSlotStatus)object).slotState) {
            return false;
        }
        if (!HidlSupport.deepEquals((Object)this.atr, (Object)((SimSlotStatus)object).atr)) {
            return false;
        }
        if (this.logicalSlotId != ((SimSlotStatus)object).logicalSlotId) {
            return false;
        }
        return HidlSupport.deepEquals((Object)this.iccid, (Object)((SimSlotStatus)object).iccid);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode((Object)this.cardState), HidlSupport.deepHashCode((Object)this.slotState), HidlSupport.deepHashCode((Object)this.atr), HidlSupport.deepHashCode((Object)this.logicalSlotId), HidlSupport.deepHashCode((Object)this.iccid));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cardState = hwBlob.getInt32(l + 0L);
        this.slotState = hwBlob.getInt32(l + 4L);
        this.atr = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer((long)(this.atr.getBytes().length + 1), hwBlob.handle(), l + 8L + 0L, false);
        this.logicalSlotId = hwBlob.getInt32(l + 24L);
        this.iccid = hwBlob.getString(l + 32L);
        hwParcel.readEmbeddedBuffer((long)(this.iccid.getBytes().length + 1), hwBlob.handle(), l + 32L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cardState = ");
        stringBuilder.append(CardState.toString((int)this.cardState));
        stringBuilder.append(", .slotState = ");
        stringBuilder.append(SlotState.toString(this.slotState));
        stringBuilder.append(", .atr = ");
        stringBuilder.append(this.atr);
        stringBuilder.append(", .logicalSlotId = ");
        stringBuilder.append(this.logicalSlotId);
        stringBuilder.append(", .iccid = ");
        stringBuilder.append(this.iccid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.cardState);
        hwBlob.putInt32(4L + l, this.slotState);
        hwBlob.putString(8L + l, this.atr);
        hwBlob.putInt32(24L + l, this.logicalSlotId);
        hwBlob.putString(32L + l, this.iccid);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

