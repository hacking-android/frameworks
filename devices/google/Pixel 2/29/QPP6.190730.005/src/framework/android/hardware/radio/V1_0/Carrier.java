/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CarrierMatchType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class Carrier {
    public String matchData = new String();
    public int matchType;
    public String mcc = new String();
    public String mnc = new String();

    public static final ArrayList<Carrier> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Carrier> arrayList = new ArrayList<Carrier>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 56, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new Carrier();
            ((Carrier)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 56);
            arrayList.add((Carrier)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Carrier> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 56);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
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
        if (object.getClass() != Carrier.class) {
            return false;
        }
        object = (Carrier)object;
        if (!HidlSupport.deepEquals(this.mcc, ((Carrier)object).mcc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mnc, ((Carrier)object).mnc)) {
            return false;
        }
        if (this.matchType != ((Carrier)object).matchType) {
            return false;
        }
        return HidlSupport.deepEquals(this.matchData, ((Carrier)object).matchData);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.mcc), HidlSupport.deepHashCode(this.mnc), HidlSupport.deepHashCode(this.matchType), HidlSupport.deepHashCode(this.matchData));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.mcc = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.mnc = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.matchType = hwBlob.getInt32(l + 32L);
        this.matchData = hwBlob.getString(l + 40L);
        hwParcel.readEmbeddedBuffer(this.matchData.getBytes().length + 1, hwBlob.handle(), l + 40L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".mcc = ");
        stringBuilder.append(this.mcc);
        stringBuilder.append(", .mnc = ");
        stringBuilder.append(this.mnc);
        stringBuilder.append(", .matchType = ");
        stringBuilder.append(CarrierMatchType.toString(this.matchType));
        stringBuilder.append(", .matchData = ");
        stringBuilder.append(this.matchData);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.mcc);
        hwBlob.putString(16L + l, this.mnc);
        hwBlob.putInt32(32L + l, this.matchType);
        hwBlob.putString(40L + l, this.matchData);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

