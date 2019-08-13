/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class LteVopsInfo {
    public boolean isEmcBearerSupported;
    public boolean isVopsSupported;

    public static final ArrayList<LteVopsInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<LteVopsInfo> arrayList = new ArrayList<LteVopsInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 2, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            LteVopsInfo lteVopsInfo = new LteVopsInfo();
            lteVopsInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 2);
            arrayList.add(lteVopsInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<LteVopsInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 2);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 2);
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
        if (object.getClass() != LteVopsInfo.class) {
            return false;
        }
        object = (LteVopsInfo)object;
        if (this.isVopsSupported != ((LteVopsInfo)object).isVopsSupported) {
            return false;
        }
        return this.isEmcBearerSupported == ((LteVopsInfo)object).isEmcBearerSupported;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.isVopsSupported), HidlSupport.deepHashCode(this.isEmcBearerSupported));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.isVopsSupported = hwBlob.getBool(0L + l);
        this.isEmcBearerSupported = hwBlob.getBool(1L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(2L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".isVopsSupported = ");
        stringBuilder.append(this.isVopsSupported);
        stringBuilder.append(", .isEmcBearerSupported = ");
        stringBuilder.append(this.isEmcBearerSupported);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putBool(0L + l, this.isVopsSupported);
        hwBlob.putBool(1L + l, this.isEmcBearerSupported);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(2);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

