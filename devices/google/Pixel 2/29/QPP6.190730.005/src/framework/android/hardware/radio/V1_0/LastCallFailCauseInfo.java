/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.LastCallFailCause;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class LastCallFailCauseInfo {
    public int causeCode;
    public String vendorCause = new String();

    public static final ArrayList<LastCallFailCauseInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LastCallFailCauseInfo> arrayList = new ArrayList<LastCallFailCauseInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new LastCallFailCauseInfo();
            ((LastCallFailCauseInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((LastCallFailCauseInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LastCallFailCauseInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 24);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
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
        if (object.getClass() != LastCallFailCauseInfo.class) {
            return false;
        }
        object = (LastCallFailCauseInfo)object;
        if (this.causeCode != ((LastCallFailCauseInfo)object).causeCode) {
            return false;
        }
        return HidlSupport.deepEquals(this.vendorCause, ((LastCallFailCauseInfo)object).vendorCause);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.causeCode), HidlSupport.deepHashCode(this.vendorCause));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.causeCode = hwBlob.getInt32(l + 0L);
        this.vendorCause = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.vendorCause.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".causeCode = ");
        stringBuilder.append(LastCallFailCause.toString(this.causeCode));
        stringBuilder.append(", .vendorCause = ");
        stringBuilder.append(this.vendorCause);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.causeCode);
        hwBlob.putString(8L + l, this.vendorCause);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

