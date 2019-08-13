/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class IccIoResult {
    public String simResponse = new String();
    public int sw1;
    public int sw2;

    public static final ArrayList<IccIoResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<IccIoResult> arrayList = new ArrayList<IccIoResult>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new IccIoResult();
            ((IccIoResult)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((IccIoResult)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<IccIoResult> arrayList) {
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
        if (object.getClass() != IccIoResult.class) {
            return false;
        }
        object = (IccIoResult)object;
        if (this.sw1 != ((IccIoResult)object).sw1) {
            return false;
        }
        if (this.sw2 != ((IccIoResult)object).sw2) {
            return false;
        }
        return HidlSupport.deepEquals(this.simResponse, ((IccIoResult)object).simResponse);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.sw1), HidlSupport.deepHashCode(this.sw2), HidlSupport.deepHashCode(this.simResponse));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.sw1 = hwBlob.getInt32(l + 0L);
        this.sw2 = hwBlob.getInt32(l + 4L);
        this.simResponse = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.simResponse.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".sw1 = ");
        stringBuilder.append(this.sw1);
        stringBuilder.append(", .sw2 = ");
        stringBuilder.append(this.sw2);
        stringBuilder.append(", .simResponse = ");
        stringBuilder.append(this.simResponse);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.sw1);
        hwBlob.putInt32(4L + l, this.sw2);
        hwBlob.putString(8L + l, this.simResponse);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

