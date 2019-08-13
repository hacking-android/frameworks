/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.HidlSupport
 *  android.os.HwBlob
 *  android.os.HwParcel
 */
package android.hardware.radio.config.V1_1;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class ModemInfo {
    public byte modemId;

    public static final ArrayList<ModemInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<ModemInfo> arrayList = new ArrayList<ModemInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = object.getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer((long)(n * 1), object.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new ModemInfo();
            ((ModemInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 1);
            arrayList.add((ModemInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<ModemInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 1);
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
        if (object.getClass() != ModemInfo.class) {
            return false;
        }
        object = (ModemInfo)object;
        return this.modemId == ((ModemInfo)object).modemId;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode((Object)this.modemId));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.modemId = hwBlob.getInt8(0L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(1L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".modemId = ");
        stringBuilder.append(this.modemId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt8(0L + l, this.modemId);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(1);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

