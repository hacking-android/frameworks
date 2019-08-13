/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityGsm {
    public int arfcn;
    public byte bsic;
    public int cid;
    public int lac;
    public String mcc = new String();
    public String mnc = new String();

    public static final ArrayList<CellIdentityGsm> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityGsm> arrayList = new ArrayList<CellIdentityGsm>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 48, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellIdentityGsm();
            ((CellIdentityGsm)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 48);
            arrayList.add((CellIdentityGsm)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityGsm> arrayList) {
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
        if (object.getClass() != CellIdentityGsm.class) {
            return false;
        }
        object = (CellIdentityGsm)object;
        if (!HidlSupport.deepEquals(this.mcc, ((CellIdentityGsm)object).mcc)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.mnc, ((CellIdentityGsm)object).mnc)) {
            return false;
        }
        if (this.lac != ((CellIdentityGsm)object).lac) {
            return false;
        }
        if (this.cid != ((CellIdentityGsm)object).cid) {
            return false;
        }
        if (this.arfcn != ((CellIdentityGsm)object).arfcn) {
            return false;
        }
        return this.bsic == ((CellIdentityGsm)object).bsic;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.mcc), HidlSupport.deepHashCode(this.mnc), HidlSupport.deepHashCode(this.lac), HidlSupport.deepHashCode(this.cid), HidlSupport.deepHashCode(this.arfcn), HidlSupport.deepHashCode(this.bsic));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.mcc = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.mcc.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.mnc = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.mnc.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.lac = hwBlob.getInt32(l + 32L);
        this.cid = hwBlob.getInt32(l + 36L);
        this.arfcn = hwBlob.getInt32(l + 40L);
        this.bsic = hwBlob.getInt8(l + 44L);
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
        stringBuilder.append(", .arfcn = ");
        stringBuilder.append(this.arfcn);
        stringBuilder.append(", .bsic = ");
        stringBuilder.append(this.bsic);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.mcc);
        hwBlob.putString(16L + l, this.mnc);
        hwBlob.putInt32(32L + l, this.lac);
        hwBlob.putInt32(36L + l, this.cid);
        hwBlob.putInt32(40L + l, this.arfcn);
        hwBlob.putInt8(44L + l, this.bsic);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

