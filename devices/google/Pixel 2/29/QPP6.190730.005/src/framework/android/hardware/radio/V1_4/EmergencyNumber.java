/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_4.EmergencyNumberSource;
import android.hardware.radio.V1_4.EmergencyServiceCategory;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class EmergencyNumber {
    public int categories;
    public String mcc = new String();
    public String mnc = new String();
    public String number = new String();
    public int sources;
    public ArrayList<String> urns = new ArrayList();

    public static final ArrayList<EmergencyNumber> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<EmergencyNumber> arrayList = new ArrayList<EmergencyNumber>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 80, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            EmergencyNumber emergencyNumber = new EmergencyNumber();
            emergencyNumber.readEmbeddedFromParcel(hwParcel, hwBlob, i * 80);
            arrayList.add(emergencyNumber);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<EmergencyNumber> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 80);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 80);
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
        if (object.getClass() != EmergencyNumber.class) {
            return false;
        }
        object = (EmergencyNumber)object;
        if (!HidlSupport.deepEquals(this.number, ((EmergencyNumber)object).number)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mcc, ((EmergencyNumber)object).mcc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mnc, ((EmergencyNumber)object).mnc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.categories, ((EmergencyNumber)object).categories)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.urns, ((EmergencyNumber)object).urns)) {
            return false;
        }
        return HidlSupport.deepEquals(this.sources, ((EmergencyNumber)object).sources);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.number), HidlSupport.deepHashCode(this.mcc), HidlSupport.deepHashCode(this.mnc), HidlSupport.deepHashCode(this.categories), HidlSupport.deepHashCode(this.urns), HidlSupport.deepHashCode(this.sources));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.number = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.mcc = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.mnc = hwBlob.getString(l + 32L);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), l + 32L + 0L, false);
        this.categories = hwBlob.getInt32(l + 48L);
        int n = hwBlob.getInt32(l + 56L + 8L);
        HwBlob hwBlob2 = hwParcel.readEmbeddedBuffer(n * 16, hwBlob.handle(), l + 56L + 0L, true);
        this.urns.clear();
        for (int i = 0; i < n; ++i) {
            new String();
            String string2 = hwBlob2.getString(i * 16);
            hwParcel.readEmbeddedBuffer(string2.getBytes().length + 1, hwBlob2.handle(), i * 16 + 0, false);
            this.urns.add(string2);
        }
        this.sources = hwBlob.getInt32(l + 72L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(80L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".number = ");
        stringBuilder.append(this.number);
        stringBuilder.append(", .mcc = ");
        stringBuilder.append(this.mcc);
        stringBuilder.append(", .mnc = ");
        stringBuilder.append(this.mnc);
        stringBuilder.append(", .categories = ");
        stringBuilder.append(EmergencyServiceCategory.dumpBitfield(this.categories));
        stringBuilder.append(", .urns = ");
        stringBuilder.append(this.urns);
        stringBuilder.append(", .sources = ");
        stringBuilder.append(EmergencyNumberSource.dumpBitfield(this.sources));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(l + 0L, this.number);
        hwBlob.putString(16L + l, this.mcc);
        hwBlob.putString(32L + l, this.mnc);
        hwBlob.putInt32(48L + l, this.categories);
        int n = this.urns.size();
        hwBlob.putInt32(l + 56L + 8L, n);
        hwBlob.putBool(l + 56L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 16);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putString(i * 16, this.urns.get(i));
        }
        hwBlob.putBlob(56L + l + 0L, hwBlob2);
        hwBlob.putInt32(72L + l, this.sources);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(80);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

