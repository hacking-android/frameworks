/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SimApdu {
    public int cla;
    public String data = new String();
    public int instruction;
    public int p1;
    public int p2;
    public int p3;
    public int sessionId;

    public static final ArrayList<SimApdu> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SimApdu> arrayList = new ArrayList<SimApdu>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            SimApdu simApdu = new SimApdu();
            simApdu.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(simApdu);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SimApdu> arrayList) {
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
        if (object.getClass() != SimApdu.class) {
            return false;
        }
        object = (SimApdu)object;
        if (this.sessionId != ((SimApdu)object).sessionId) {
            return false;
        }
        if (this.cla != ((SimApdu)object).cla) {
            return false;
        }
        if (this.instruction != ((SimApdu)object).instruction) {
            return false;
        }
        if (this.p1 != ((SimApdu)object).p1) {
            return false;
        }
        if (this.p2 != ((SimApdu)object).p2) {
            return false;
        }
        if (this.p3 != ((SimApdu)object).p3) {
            return false;
        }
        return HidlSupport.deepEquals(this.data, ((SimApdu)object).data);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.sessionId), HidlSupport.deepHashCode(this.cla), HidlSupport.deepHashCode(this.instruction), HidlSupport.deepHashCode(this.p1), HidlSupport.deepHashCode(this.p2), HidlSupport.deepHashCode(this.p3), HidlSupport.deepHashCode(this.data));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.sessionId = hwBlob.getInt32(l + 0L);
        this.cla = hwBlob.getInt32(l + 4L);
        this.instruction = hwBlob.getInt32(l + 8L);
        this.p1 = hwBlob.getInt32(l + 12L);
        this.p2 = hwBlob.getInt32(l + 16L);
        this.p3 = hwBlob.getInt32(l + 20L);
        this.data = hwBlob.getString(l + 24L);
        hwParcel.readEmbeddedBuffer(this.data.getBytes().length + 1, hwBlob.handle(), l + 24L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".sessionId = ");
        stringBuilder.append(this.sessionId);
        stringBuilder.append(", .cla = ");
        stringBuilder.append(this.cla);
        stringBuilder.append(", .instruction = ");
        stringBuilder.append(this.instruction);
        stringBuilder.append(", .p1 = ");
        stringBuilder.append(this.p1);
        stringBuilder.append(", .p2 = ");
        stringBuilder.append(this.p2);
        stringBuilder.append(", .p3 = ");
        stringBuilder.append(this.p3);
        stringBuilder.append(", .data = ");
        stringBuilder.append(this.data);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.sessionId);
        hwBlob.putInt32(4L + l, this.cla);
        hwBlob.putInt32(8L + l, this.instruction);
        hwBlob.putInt32(12L + l, this.p1);
        hwBlob.putInt32(16L + l, this.p2);
        hwBlob.putInt32(20L + l, this.p3);
        hwBlob.putString(24L + l, this.data);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

