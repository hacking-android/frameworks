/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CallForwardInfoStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CallForwardInfo {
    public String number = new String();
    public int reason;
    public int serviceClass;
    public int status;
    public int timeSeconds;
    public int toa;

    public static final ArrayList<CallForwardInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CallForwardInfo> arrayList = new ArrayList<CallForwardInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CallForwardInfo callForwardInfo = new CallForwardInfo();
            callForwardInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(callForwardInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CallForwardInfo> arrayList) {
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
        if (object.getClass() != CallForwardInfo.class) {
            return false;
        }
        object = (CallForwardInfo)object;
        if (this.status != ((CallForwardInfo)object).status) {
            return false;
        }
        if (this.reason != ((CallForwardInfo)object).reason) {
            return false;
        }
        if (this.serviceClass != ((CallForwardInfo)object).serviceClass) {
            return false;
        }
        if (this.toa != ((CallForwardInfo)object).toa) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.number, ((CallForwardInfo)object).number)) {
            return false;
        }
        return this.timeSeconds == ((CallForwardInfo)object).timeSeconds;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.status), HidlSupport.deepHashCode(this.reason), HidlSupport.deepHashCode(this.serviceClass), HidlSupport.deepHashCode(this.toa), HidlSupport.deepHashCode(this.number), HidlSupport.deepHashCode(this.timeSeconds));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.status = hwBlob.getInt32(l + 0L);
        this.reason = hwBlob.getInt32(l + 4L);
        this.serviceClass = hwBlob.getInt32(l + 8L);
        this.toa = hwBlob.getInt32(l + 12L);
        this.number = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.timeSeconds = hwBlob.getInt32(l + 32L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".status = ");
        stringBuilder.append(CallForwardInfoStatus.toString(this.status));
        stringBuilder.append(", .reason = ");
        stringBuilder.append(this.reason);
        stringBuilder.append(", .serviceClass = ");
        stringBuilder.append(this.serviceClass);
        stringBuilder.append(", .toa = ");
        stringBuilder.append(this.toa);
        stringBuilder.append(", .number = ");
        stringBuilder.append(this.number);
        stringBuilder.append(", .timeSeconds = ");
        stringBuilder.append(this.timeSeconds);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.status);
        hwBlob.putInt32(4L + l, this.reason);
        hwBlob.putInt32(8L + l, this.serviceClass);
        hwBlob.putInt32(12L + l, this.toa);
        hwBlob.putString(16L + l, this.number);
        hwBlob.putInt32(32L + l, this.timeSeconds);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

