/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SuppSvcNotification {
    public int code;
    public int index;
    public boolean isMT;
    public String number = new String();
    public int type;

    public static final ArrayList<SuppSvcNotification> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SuppSvcNotification> arrayList = new ArrayList<SuppSvcNotification>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 32, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SuppSvcNotification();
            ((SuppSvcNotification)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add((SuppSvcNotification)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SuppSvcNotification> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 32);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
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
        if (object.getClass() != SuppSvcNotification.class) {
            return false;
        }
        object = (SuppSvcNotification)object;
        if (this.isMT != ((SuppSvcNotification)object).isMT) {
            return false;
        }
        if (this.code != ((SuppSvcNotification)object).code) {
            return false;
        }
        if (this.index != ((SuppSvcNotification)object).index) {
            return false;
        }
        if (this.type != ((SuppSvcNotification)object).type) {
            return false;
        }
        return HidlSupport.deepEquals(this.number, ((SuppSvcNotification)object).number);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.isMT), HidlSupport.deepHashCode(this.code), HidlSupport.deepHashCode(this.index), HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.number));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.isMT = hwBlob.getBool(l + 0L);
        this.code = hwBlob.getInt32(l + 4L);
        this.index = hwBlob.getInt32(l + 8L);
        this.type = hwBlob.getInt32(l + 12L);
        this.number = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".isMT = ");
        stringBuilder.append(this.isMT);
        stringBuilder.append(", .code = ");
        stringBuilder.append(this.code);
        stringBuilder.append(", .index = ");
        stringBuilder.append(this.index);
        stringBuilder.append(", .type = ");
        stringBuilder.append(this.type);
        stringBuilder.append(", .number = ");
        stringBuilder.append(this.number);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putBool(0L + l, this.isMT);
        hwBlob.putInt32(4L + l, this.code);
        hwBlob.putInt32(8L + l, this.index);
        hwBlob.putInt32(12L + l, this.type);
        hwBlob.putString(16L + l, this.number);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

