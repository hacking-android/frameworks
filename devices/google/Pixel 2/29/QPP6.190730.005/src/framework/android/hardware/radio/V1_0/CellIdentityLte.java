/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityLte {
    public int ci;
    public int earfcn;
    public String mcc = new String();
    public String mnc = new String();
    public int pci;
    public int tac;

    public static final ArrayList<CellIdentityLte> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityLte> arrayList = new ArrayList<CellIdentityLte>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 48, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellIdentityLte cellIdentityLte = new CellIdentityLte();
            cellIdentityLte.readEmbeddedFromParcel(hwParcel, hwBlob, i * 48);
            arrayList.add(cellIdentityLte);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityLte> arrayList) {
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
        if (object.getClass() != CellIdentityLte.class) {
            return false;
        }
        object = (CellIdentityLte)object;
        if (!HidlSupport.deepEquals(this.mcc, ((CellIdentityLte)object).mcc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mnc, ((CellIdentityLte)object).mnc)) {
            return false;
        }
        if (this.ci != ((CellIdentityLte)object).ci) {
            return false;
        }
        if (this.pci != ((CellIdentityLte)object).pci) {
            return false;
        }
        if (this.tac != ((CellIdentityLte)object).tac) {
            return false;
        }
        return this.earfcn == ((CellIdentityLte)object).earfcn;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.mcc), HidlSupport.deepHashCode(this.mnc), HidlSupport.deepHashCode(this.ci), HidlSupport.deepHashCode(this.pci), HidlSupport.deepHashCode(this.tac), HidlSupport.deepHashCode(this.earfcn));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.mcc = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.mnc = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.ci = hwBlob.getInt32(l + 32L);
        this.pci = hwBlob.getInt32(l + 36L);
        this.tac = hwBlob.getInt32(l + 40L);
        this.earfcn = hwBlob.getInt32(l + 44L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".mcc = ");
        stringBuilder.append(this.mcc);
        stringBuilder.append(", .mnc = ");
        stringBuilder.append(this.mnc);
        stringBuilder.append(", .ci = ");
        stringBuilder.append(this.ci);
        stringBuilder.append(", .pci = ");
        stringBuilder.append(this.pci);
        stringBuilder.append(", .tac = ");
        stringBuilder.append(this.tac);
        stringBuilder.append(", .earfcn = ");
        stringBuilder.append(this.earfcn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.mcc);
        hwBlob.putString(16L + l, this.mnc);
        hwBlob.putInt32(32L + l, this.ci);
        hwBlob.putInt32(36L + l, this.pci);
        hwBlob.putInt32(40L + l, this.tac);
        hwBlob.putInt32(44L + l, this.earfcn);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

