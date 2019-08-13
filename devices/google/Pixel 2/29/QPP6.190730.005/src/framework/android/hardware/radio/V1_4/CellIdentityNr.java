/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_2.CellIdentityOperatorNames;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityNr {
    public String mcc = new String();
    public String mnc = new String();
    public long nci;
    public int nrarfcn;
    public CellIdentityOperatorNames operatorNames = new CellIdentityOperatorNames();
    public int pci;
    public int tac;

    public static final ArrayList<CellIdentityNr> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityNr> arrayList = new ArrayList<CellIdentityNr>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 88, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellIdentityNr();
            ((CellIdentityNr)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add((CellIdentityNr)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityNr> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 88);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
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
        if (object.getClass() != CellIdentityNr.class) {
            return false;
        }
        object = (CellIdentityNr)object;
        if (!HidlSupport.deepEquals(this.mcc, ((CellIdentityNr)object).mcc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mnc, ((CellIdentityNr)object).mnc)) {
            return false;
        }
        if (this.nci != ((CellIdentityNr)object).nci) {
            return false;
        }
        if (this.pci != ((CellIdentityNr)object).pci) {
            return false;
        }
        if (this.tac != ((CellIdentityNr)object).tac) {
            return false;
        }
        if (this.nrarfcn != ((CellIdentityNr)object).nrarfcn) {
            return false;
        }
        return HidlSupport.deepEquals(this.operatorNames, ((CellIdentityNr)object).operatorNames);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.mcc), HidlSupport.deepHashCode(this.mnc), HidlSupport.deepHashCode(this.nci), HidlSupport.deepHashCode(this.pci), HidlSupport.deepHashCode(this.tac), HidlSupport.deepHashCode(this.nrarfcn), HidlSupport.deepHashCode(this.operatorNames));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.mcc = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.mnc = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.nci = hwBlob.getInt64(l + 32L);
        this.pci = hwBlob.getInt32(l + 40L);
        this.tac = hwBlob.getInt32(l + 44L);
        this.nrarfcn = hwBlob.getInt32(l + 48L);
        this.operatorNames.readEmbeddedFromParcel(hwParcel, hwBlob, l + 56L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".mcc = ");
        stringBuilder.append(this.mcc);
        stringBuilder.append(", .mnc = ");
        stringBuilder.append(this.mnc);
        stringBuilder.append(", .nci = ");
        stringBuilder.append(this.nci);
        stringBuilder.append(", .pci = ");
        stringBuilder.append(this.pci);
        stringBuilder.append(", .tac = ");
        stringBuilder.append(this.tac);
        stringBuilder.append(", .nrarfcn = ");
        stringBuilder.append(this.nrarfcn);
        stringBuilder.append(", .operatorNames = ");
        stringBuilder.append(this.operatorNames);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.mcc);
        hwBlob.putString(16L + l, this.mnc);
        hwBlob.putInt64(32L + l, this.nci);
        hwBlob.putInt32(40L + l, this.pci);
        hwBlob.putInt32(44L + l, this.tac);
        hwBlob.putInt32(48L + l, this.nrarfcn);
        this.operatorNames.writeEmbeddedToBlob(hwBlob, 56L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

