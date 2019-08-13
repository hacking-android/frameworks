/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaSmsErrorClass;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaSmsAck {
    public int errorClass;
    public int smsCauseCode;

    public static final ArrayList<CdmaSmsAck> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsAck> arrayList = new ArrayList<CdmaSmsAck>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 8, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaSmsAck();
            ((CdmaSmsAck)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add((CdmaSmsAck)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsAck> arrayList) {
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
        if (object.getClass() != CdmaSmsAck.class) {
            return false;
        }
        object = (CdmaSmsAck)object;
        if (this.errorClass != ((CdmaSmsAck)object).errorClass) {
            return false;
        }
        return this.smsCauseCode == ((CdmaSmsAck)object).smsCauseCode;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.errorClass), HidlSupport.deepHashCode(this.smsCauseCode));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.errorClass = hwBlob.getInt32(0L + l);
        this.smsCauseCode = hwBlob.getInt32(4L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".errorClass = ");
        stringBuilder.append(CdmaSmsErrorClass.toString(this.errorClass));
        stringBuilder.append(", .smsCauseCode = ");
        stringBuilder.append(this.smsCauseCode);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.errorClass);
        hwBlob.putInt32(4L + l, this.smsCauseCode);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

