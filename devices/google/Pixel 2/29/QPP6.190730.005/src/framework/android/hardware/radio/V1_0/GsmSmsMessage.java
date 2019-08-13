/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class GsmSmsMessage {
    public String pdu = new String();
    public String smscPdu = new String();

    public static final ArrayList<GsmSmsMessage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GsmSmsMessage> arrayList = new ArrayList<GsmSmsMessage>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 32, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            GsmSmsMessage gsmSmsMessage = new GsmSmsMessage();
            gsmSmsMessage.readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add(gsmSmsMessage);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GsmSmsMessage> arrayList) {
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
        if (object.getClass() != GsmSmsMessage.class) {
            return false;
        }
        object = (GsmSmsMessage)object;
        if (!HidlSupport.deepEquals(this.smscPdu, ((GsmSmsMessage)object).smscPdu)) {
            return false;
        }
        return HidlSupport.deepEquals(this.pdu, ((GsmSmsMessage)object).pdu);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.smscPdu), HidlSupport.deepHashCode(this.pdu));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.smscPdu = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.smscPdu.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.pdu = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.pdu.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".smscPdu = ");
        stringBuilder.append(this.smscPdu);
        stringBuilder.append(", .pdu = ");
        stringBuilder.append(this.pdu);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.smscPdu);
        hwBlob.putString(16L + l, this.pdu);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

