/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.UusDcs;
import android.hardware.radio.V1_0.UusType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class UusInfo {
    public String uusData = new String();
    public int uusDcs;
    public int uusType;

    public static final ArrayList<UusInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<UusInfo> arrayList = new ArrayList<UusInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new UusInfo();
            ((UusInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((UusInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<UusInfo> arrayList) {
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
        if (object.getClass() != UusInfo.class) {
            return false;
        }
        object = (UusInfo)object;
        if (this.uusType != ((UusInfo)object).uusType) {
            return false;
        }
        if (this.uusDcs != ((UusInfo)object).uusDcs) {
            return false;
        }
        return HidlSupport.deepEquals(this.uusData, ((UusInfo)object).uusData);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.uusType), HidlSupport.deepHashCode(this.uusDcs), HidlSupport.deepHashCode(this.uusData));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.uusType = hwBlob.getInt32(l + 0L);
        this.uusDcs = hwBlob.getInt32(l + 4L);
        this.uusData = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.uusData.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".uusType = ");
        stringBuilder.append(UusType.toString(this.uusType));
        stringBuilder.append(", .uusDcs = ");
        stringBuilder.append(UusDcs.toString(this.uusDcs));
        stringBuilder.append(", .uusData = ");
        stringBuilder.append(this.uusData);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.uusType);
        hwBlob.putInt32(4L + l, this.uusDcs);
        hwBlob.putString(8L + l, this.uusData);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

