/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CardStatus {
    public String atr = new String();
    public android.hardware.radio.V1_0.CardStatus base = new android.hardware.radio.V1_0.CardStatus();
    public String iccid = new String();
    public int physicalSlotId;

    public static final ArrayList<CardStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CardStatus> arrayList = new ArrayList<CardStatus>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 80, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CardStatus cardStatus = new CardStatus();
            cardStatus.readEmbeddedFromParcel(hwParcel, hwBlob, i * 80);
            arrayList.add(cardStatus);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CardStatus> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 80);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
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
        if (object.getClass() != CardStatus.class) {
            return false;
        }
        object = (CardStatus)object;
        if (!HidlSupport.deepEquals(this.base, ((CardStatus)object).base)) {
            return false;
        }
        if (this.physicalSlotId != ((CardStatus)object).physicalSlotId) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.atr, ((CardStatus)object).atr)) {
            return false;
        }
        return HidlSupport.deepEquals(this.iccid, ((CardStatus)object).iccid);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.base), HidlSupport.deepHashCode(this.physicalSlotId), HidlSupport.deepHashCode(this.atr), HidlSupport.deepHashCode(this.iccid));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.base.readEmbeddedFromParcel(hwParcel, hwBlob, l + 0L);
        this.physicalSlotId = hwBlob.getInt32(l + 40L);
        this.atr = hwBlob.getString(l + 48L);
        hwParcel.readEmbeddedBuffer(this.atr.getBytes().length + 1, hwBlob.handle(), l + 48L + 0L, false);
        this.iccid = hwBlob.getString(l + 64L);
        hwParcel.readEmbeddedBuffer(this.iccid.getBytes().length + 1, hwBlob.handle(), l + 64L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".base = ");
        stringBuilder.append(this.base);
        stringBuilder.append(", .physicalSlotId = ");
        stringBuilder.append(this.physicalSlotId);
        stringBuilder.append(", .atr = ");
        stringBuilder.append(this.atr);
        stringBuilder.append(", .iccid = ");
        stringBuilder.append(this.iccid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.base.writeEmbeddedToBlob(hwBlob, 0L + l);
        hwBlob.putInt32(40L + l, this.physicalSlotId);
        hwBlob.putString(48L + l, this.atr);
        hwBlob.putString(64L + l, this.iccid);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

