/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityWcdma {
    public int cid;
    public int lac;
    public String mcc = new String();
    public String mnc = new String();
    public int psc;
    public int uarfcn;

    public static final ArrayList<CellIdentityWcdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityWcdma> arrayList = new ArrayList<CellIdentityWcdma>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 48, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellIdentityWcdma();
            ((CellIdentityWcdma)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 48);
            arrayList.add((CellIdentityWcdma)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityWcdma> arrayList) {
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
        if (object.getClass() != CellIdentityWcdma.class) {
            return false;
        }
        object = (CellIdentityWcdma)object;
        if (!HidlSupport.deepEquals(this.mcc, ((CellIdentityWcdma)object).mcc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mnc, ((CellIdentityWcdma)object).mnc)) {
            return false;
        }
        if (this.lac != ((CellIdentityWcdma)object).lac) {
            return false;
        }
        if (this.cid != ((CellIdentityWcdma)object).cid) {
            return false;
        }
        if (this.psc != ((CellIdentityWcdma)object).psc) {
            return false;
        }
        return this.uarfcn == ((CellIdentityWcdma)object).uarfcn;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.mcc), HidlSupport.deepHashCode(this.mnc), HidlSupport.deepHashCode(this.lac), HidlSupport.deepHashCode(this.cid), HidlSupport.deepHashCode(this.psc), HidlSupport.deepHashCode(this.uarfcn));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.mcc = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.mnc = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.lac = hwBlob.getInt32(l + 32L);
        this.cid = hwBlob.getInt32(l + 36L);
        this.psc = hwBlob.getInt32(l + 40L);
        this.uarfcn = hwBlob.getInt32(l + 44L);
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
        stringBuilder.append(", .lac = ");
        stringBuilder.append(this.lac);
        stringBuilder.append(", .cid = ");
        stringBuilder.append(this.cid);
        stringBuilder.append(", .psc = ");
        stringBuilder.append(this.psc);
        stringBuilder.append(", .uarfcn = ");
        stringBuilder.append(this.uarfcn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.mcc);
        hwBlob.putString(16L + l, this.mnc);
        hwBlob.putInt32(32L + l, this.lac);
        hwBlob.putInt32(36L + l, this.cid);
        hwBlob.putInt32(40L + l, this.psc);
        hwBlob.putInt32(44L + l, this.uarfcn);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

