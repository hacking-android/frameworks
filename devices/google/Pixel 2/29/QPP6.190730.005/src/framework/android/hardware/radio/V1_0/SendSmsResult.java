/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SendSmsResult {
    public String ackPDU = new String();
    public int errorCode;
    public int messageRef;

    public static final ArrayList<SendSmsResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SendSmsResult> arrayList = new ArrayList<SendSmsResult>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 32, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SendSmsResult();
            ((SendSmsResult)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add((SendSmsResult)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SendSmsResult> arrayList) {
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
        if (object.getClass() != SendSmsResult.class) {
            return false;
        }
        object = (SendSmsResult)object;
        if (this.messageRef != ((SendSmsResult)object).messageRef) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.ackPDU, ((SendSmsResult)object).ackPDU)) {
            return false;
        }
        return this.errorCode == ((SendSmsResult)object).errorCode;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.messageRef), HidlSupport.deepHashCode(this.ackPDU), HidlSupport.deepHashCode(this.errorCode));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.messageRef = hwBlob.getInt32(l + 0L);
        this.ackPDU = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.ackPDU.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.errorCode = hwBlob.getInt32(l + 24L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".messageRef = ");
        stringBuilder.append(this.messageRef);
        stringBuilder.append(", .ackPDU = ");
        stringBuilder.append(this.ackPDU);
        stringBuilder.append(", .errorCode = ");
        stringBuilder.append(this.errorCode);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.messageRef);
        hwBlob.putString(8L + l, this.ackPDU);
        hwBlob.putInt32(24L + l, this.errorCode);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

