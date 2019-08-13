/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import android.hardware.radio.V1_1.KeepaliveStatusCode;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class KeepaliveStatus {
    public int code;
    public int sessionHandle;

    public static final ArrayList<KeepaliveStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<KeepaliveStatus> arrayList = new ArrayList<KeepaliveStatus>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 8, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new KeepaliveStatus();
            ((KeepaliveStatus)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add((KeepaliveStatus)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<KeepaliveStatus> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 8);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
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
        if (object.getClass() != KeepaliveStatus.class) {
            return false;
        }
        object = (KeepaliveStatus)object;
        if (this.sessionHandle != ((KeepaliveStatus)object).sessionHandle) {
            return false;
        }
        return this.code == ((KeepaliveStatus)object).code;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.sessionHandle), HidlSupport.deepHashCode(this.code));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.sessionHandle = hwBlob.getInt32(0L + l);
        this.code = hwBlob.getInt32(4L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".sessionHandle = ");
        stringBuilder.append(this.sessionHandle);
        stringBuilder.append(", .code = ");
        stringBuilder.append(KeepaliveStatusCode.toString(this.code));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.sessionHandle);
        hwBlob.putInt32(4L + l, this.code);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

