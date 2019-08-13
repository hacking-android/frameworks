/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CdmaSmsWriteArgsStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaSmsWriteArgs {
    public CdmaSmsMessage message = new CdmaSmsMessage();
    public int status;

    public static final ArrayList<CdmaSmsWriteArgs> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsWriteArgs> arrayList = new ArrayList<CdmaSmsWriteArgs>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 96, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaSmsWriteArgs();
            ((CdmaSmsWriteArgs)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 96);
            arrayList.add((CdmaSmsWriteArgs)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsWriteArgs> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 96);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 96);
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
        if (object.getClass() != CdmaSmsWriteArgs.class) {
            return false;
        }
        object = (CdmaSmsWriteArgs)object;
        if (this.status != ((CdmaSmsWriteArgs)object).status) {
            return false;
        }
        return HidlSupport.deepEquals(this.message, ((CdmaSmsWriteArgs)object).message);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.status), HidlSupport.deepHashCode(this.message));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.status = hwBlob.getInt32(0L + l);
        this.message.readEmbeddedFromParcel(hwParcel, hwBlob, 8L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(96L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".status = ");
        stringBuilder.append(CdmaSmsWriteArgsStatus.toString(this.status));
        stringBuilder.append(", .message = ");
        stringBuilder.append(this.message);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.status);
        this.message.writeEmbeddedToBlob(hwBlob, 8L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(96);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

