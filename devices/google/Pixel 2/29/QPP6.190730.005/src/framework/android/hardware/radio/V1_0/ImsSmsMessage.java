/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.GsmSmsMessage;
import android.hardware.radio.V1_0.RadioTechnologyFamily;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class ImsSmsMessage {
    public ArrayList<CdmaSmsMessage> cdmaMessage = new ArrayList();
    public ArrayList<GsmSmsMessage> gsmMessage = new ArrayList();
    public int messageRef;
    public boolean retry;
    public int tech;

    public static final ArrayList<ImsSmsMessage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ImsSmsMessage> arrayList = new ArrayList<ImsSmsMessage>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 48, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            ImsSmsMessage imsSmsMessage = new ImsSmsMessage();
            imsSmsMessage.readEmbeddedFromParcel(hwParcel, hwBlob, i * 48);
            arrayList.add(imsSmsMessage);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ImsSmsMessage> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 48);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
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
        if (object.getClass() != ImsSmsMessage.class) {
            return false;
        }
        object = (ImsSmsMessage)object;
        if (this.tech != ((ImsSmsMessage)object).tech) {
            return false;
        }
        if (this.retry != ((ImsSmsMessage)object).retry) {
            return false;
        }
        if (this.messageRef != ((ImsSmsMessage)object).messageRef) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.cdmaMessage, ((ImsSmsMessage)object).cdmaMessage)) {
            return false;
        }
        return HidlSupport.deepEquals(this.gsmMessage, ((ImsSmsMessage)object).gsmMessage);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.tech), HidlSupport.deepHashCode(this.retry), HidlSupport.deepHashCode(this.messageRef), HidlSupport.deepHashCode(this.cdmaMessage), HidlSupport.deepHashCode(this.gsmMessage));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        Object object;
        this.tech = hwBlob.getInt32(l + 0L);
        this.retry = hwBlob.getBool(l + 4L);
        this.messageRef = hwBlob.getInt32(l + 8L);
        int n2 = hwBlob.getInt32(l + 16L + 8L);
        HwBlob hwBlob2 = hwParcel.readEmbeddedBuffer(n2 * 88, hwBlob.handle(), l + 16L + 0L, true);
        this.cdmaMessage.clear();
        for (n = 0; n < n2; ++n) {
            object = new CdmaSmsMessage();
            ((CdmaSmsMessage)object).readEmbeddedFromParcel(hwParcel, hwBlob2, n * 88);
            this.cdmaMessage.add((CdmaSmsMessage)object);
        }
        n2 = hwBlob.getInt32(l + 32L + 8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n2 * 32, hwBlob.handle(), l + 32L + 0L, true);
        this.gsmMessage.clear();
        for (n = 0; n < n2; ++n) {
            object = new GsmSmsMessage();
            ((GsmSmsMessage)object).readEmbeddedFromParcel(hwParcel, hwBlob, n * 32);
            this.gsmMessage.add((GsmSmsMessage)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".tech = ");
        stringBuilder.append(RadioTechnologyFamily.toString(this.tech));
        stringBuilder.append(", .retry = ");
        stringBuilder.append(this.retry);
        stringBuilder.append(", .messageRef = ");
        stringBuilder.append(this.messageRef);
        stringBuilder.append(", .cdmaMessage = ");
        stringBuilder.append(this.cdmaMessage);
        stringBuilder.append(", .gsmMessage = ");
        stringBuilder.append(this.gsmMessage);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.tech);
        hwBlob.putBool(l + 4L, this.retry);
        hwBlob.putInt32(l + 8L, this.messageRef);
        int n2 = this.cdmaMessage.size();
        hwBlob.putInt32(l + 16L + 8L, n2);
        hwBlob.putBool(l + 16L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 88);
        for (n = 0; n < n2; ++n) {
            this.cdmaMessage.get(n).writeEmbeddedToBlob(hwBlob2, n * 88);
        }
        hwBlob.putBlob(l + 16L + 0L, hwBlob2);
        n2 = this.gsmMessage.size();
        hwBlob.putInt32(l + 32L + 8L, n2);
        hwBlob.putBool(l + 32L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 32);
        for (n = 0; n < n2; ++n) {
            this.gsmMessage.get(n).writeEmbeddedToBlob(hwBlob2, n * 32);
        }
        hwBlob.putBlob(l + 32L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

