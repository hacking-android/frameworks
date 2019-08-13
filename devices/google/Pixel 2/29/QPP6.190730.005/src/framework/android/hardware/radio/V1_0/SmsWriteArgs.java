/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.SmsWriteArgsStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SmsWriteArgs {
    public String pdu = new String();
    public String smsc = new String();
    public int status;

    public static final ArrayList<SmsWriteArgs> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SmsWriteArgs> arrayList = new ArrayList<SmsWriteArgs>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            SmsWriteArgs smsWriteArgs = new SmsWriteArgs();
            smsWriteArgs.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(smsWriteArgs);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SmsWriteArgs> arrayList) {
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
        if (object.getClass() != SmsWriteArgs.class) {
            return false;
        }
        object = (SmsWriteArgs)object;
        if (this.status != ((SmsWriteArgs)object).status) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.pdu, ((SmsWriteArgs)object).pdu)) {
            return false;
        }
        return HidlSupport.deepEquals(this.smsc, ((SmsWriteArgs)object).smsc);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.status), HidlSupport.deepHashCode(this.pdu), HidlSupport.deepHashCode(this.smsc));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.status = hwBlob.getInt32(l + 0L);
        this.pdu = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.pdu.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.smsc = hwBlob.getString(l + 24L);
        hwParcel.readEmbeddedBuffer(this.smsc.getBytes().length + 1, hwBlob.handle(), l + 24L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".status = ");
        stringBuilder.append(SmsWriteArgsStatus.toString(this.status));
        stringBuilder.append(", .pdu = ");
        stringBuilder.append(this.pdu);
        stringBuilder.append(", .smsc = ");
        stringBuilder.append(this.smsc);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.status);
        hwBlob.putString(8L + l, this.pdu);
        hwBlob.putString(24L + l, this.smsc);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

