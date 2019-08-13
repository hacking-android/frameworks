/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaSignalStrength {
    public int dbm;
    public int ecio;

    public static final ArrayList<CdmaSignalStrength> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSignalStrength> arrayList = new ArrayList<CdmaSignalStrength>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 8, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CdmaSignalStrength cdmaSignalStrength = new CdmaSignalStrength();
            cdmaSignalStrength.readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add(cdmaSignalStrength);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSignalStrength> arrayList) {
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
        if (object.getClass() != CdmaSignalStrength.class) {
            return false;
        }
        object = (CdmaSignalStrength)object;
        if (this.dbm != ((CdmaSignalStrength)object).dbm) {
            return false;
        }
        return this.ecio == ((CdmaSignalStrength)object).ecio;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.dbm), HidlSupport.deepHashCode(this.ecio));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.dbm = hwBlob.getInt32(0L + l);
        this.ecio = hwBlob.getInt32(4L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".dbm = ");
        stringBuilder.append(this.dbm);
        stringBuilder.append(", .ecio = ");
        stringBuilder.append(this.ecio);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.dbm);
        hwBlob.putInt32(4L + l, this.ecio);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

