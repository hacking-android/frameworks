/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.RadioError;
import android.hardware.radio.V1_0.RadioResponseType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class RadioResponseInfo {
    public int error;
    public int serial;
    public int type;

    public static final ArrayList<RadioResponseInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioResponseInfo> arrayList = new ArrayList<RadioResponseInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 12, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
            radioResponseInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 12);
            arrayList.add(radioResponseInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioResponseInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 12);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
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
        if (object.getClass() != RadioResponseInfo.class) {
            return false;
        }
        object = (RadioResponseInfo)object;
        if (this.type != ((RadioResponseInfo)object).type) {
            return false;
        }
        if (this.serial != ((RadioResponseInfo)object).serial) {
            return false;
        }
        return this.error == ((RadioResponseInfo)object).error;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.serial), HidlSupport.deepHashCode(this.error));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(0L + l);
        this.serial = hwBlob.getInt32(4L + l);
        this.error = hwBlob.getInt32(8L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(RadioResponseType.toString(this.type));
        stringBuilder.append(", .serial = ");
        stringBuilder.append(this.serial);
        stringBuilder.append(", .error = ");
        stringBuilder.append(RadioError.toString(this.error));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.type);
        hwBlob.putInt32(4L + l, this.serial);
        hwBlob.putInt32(8L + l, this.error);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

