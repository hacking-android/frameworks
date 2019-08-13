/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CellIdentityCdma;
import android.hardware.radio.V1_0.CellIdentityGsm;
import android.hardware.radio.V1_0.CellIdentityLte;
import android.hardware.radio.V1_0.CellIdentityTdscdma;
import android.hardware.radio.V1_0.CellIdentityWcdma;
import android.hardware.radio.V1_0.CellInfoType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentity {
    public ArrayList<CellIdentityCdma> cellIdentityCdma = new ArrayList();
    public ArrayList<CellIdentityGsm> cellIdentityGsm = new ArrayList();
    public ArrayList<CellIdentityLte> cellIdentityLte = new ArrayList();
    public ArrayList<CellIdentityTdscdma> cellIdentityTdscdma = new ArrayList();
    public ArrayList<CellIdentityWcdma> cellIdentityWcdma = new ArrayList();
    public int cellInfoType;

    public static final ArrayList<CellIdentity> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentity> arrayList = new ArrayList<CellIdentity>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 88, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellIdentity();
            ((CellIdentity)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add((CellIdentity)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentity> arrayList) {
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
        if (object.getClass() != CellIdentity.class) {
            return false;
        }
        object = (CellIdentity)object;
        if (this.cellInfoType != ((CellIdentity)object).cellInfoType) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.cellIdentityGsm, ((CellIdentity)object).cellIdentityGsm)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.cellIdentityWcdma, ((CellIdentity)object).cellIdentityWcdma)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.cellIdentityCdma, ((CellIdentity)object).cellIdentityCdma)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.cellIdentityLte, ((CellIdentity)object).cellIdentityLte)) {
            return false;
        }
        return HidlSupport.deepEquals(this.cellIdentityTdscdma, ((CellIdentity)object).cellIdentityTdscdma);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cellInfoType), HidlSupport.deepHashCode(this.cellIdentityGsm), HidlSupport.deepHashCode(this.cellIdentityWcdma), HidlSupport.deepHashCode(this.cellIdentityCdma), HidlSupport.deepHashCode(this.cellIdentityLte), HidlSupport.deepHashCode(this.cellIdentityTdscdma));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        Object object;
        this.cellInfoType = hwBlob.getInt32(l + 0L);
        int n2 = hwBlob.getInt32(l + 8L + 8L);
        Object object2 = hwParcel.readEmbeddedBuffer(n2 * 48, hwBlob.handle(), l + 8L + 0L, true);
        this.cellIdentityGsm.clear();
        for (n = 0; n < n2; ++n) {
            object = new CellIdentityGsm();
            ((CellIdentityGsm)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 48);
            this.cellIdentityGsm.add((CellIdentityGsm)object);
        }
        n2 = hwBlob.getInt32(l + 24L + 8L);
        object2 = hwParcel.readEmbeddedBuffer(n2 * 48, hwBlob.handle(), l + 24L + 0L, true);
        this.cellIdentityWcdma.clear();
        for (n = 0; n < n2; ++n) {
            object = new CellIdentityWcdma();
            ((CellIdentityWcdma)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 48);
            this.cellIdentityWcdma.add((CellIdentityWcdma)object);
        }
        n2 = hwBlob.getInt32(l + 40L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 20, hwBlob.handle(), l + 40L + 0L, true);
        this.cellIdentityCdma.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CellIdentityCdma();
            ((CellIdentityCdma)object2).readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 20);
            this.cellIdentityCdma.add((CellIdentityCdma)object2);
        }
        n2 = hwBlob.getInt32(l + 56L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 48, hwBlob.handle(), l + 56L + 0L, true);
        this.cellIdentityLte.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CellIdentityLte();
            ((CellIdentityLte)object2).readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 48);
            this.cellIdentityLte.add((CellIdentityLte)object2);
        }
        n2 = hwBlob.getInt32(l + 72L + 8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n2 * 48, hwBlob.handle(), l + 72L + 0L, true);
        this.cellIdentityTdscdma.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CellIdentityTdscdma();
            ((CellIdentityTdscdma)object2).readEmbeddedFromParcel(hwParcel, hwBlob, n * 48);
            this.cellIdentityTdscdma.add((CellIdentityTdscdma)object2);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cellInfoType = ");
        stringBuilder.append(CellInfoType.toString(this.cellInfoType));
        stringBuilder.append(", .cellIdentityGsm = ");
        stringBuilder.append(this.cellIdentityGsm);
        stringBuilder.append(", .cellIdentityWcdma = ");
        stringBuilder.append(this.cellIdentityWcdma);
        stringBuilder.append(", .cellIdentityCdma = ");
        stringBuilder.append(this.cellIdentityCdma);
        stringBuilder.append(", .cellIdentityLte = ");
        stringBuilder.append(this.cellIdentityLte);
        stringBuilder.append(", .cellIdentityTdscdma = ");
        stringBuilder.append(this.cellIdentityTdscdma);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.cellInfoType);
        int n2 = this.cellIdentityGsm.size();
        hwBlob.putInt32(l + 8L + 8L, n2);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 48);
        for (n = 0; n < n2; ++n) {
            this.cellIdentityGsm.get(n).writeEmbeddedToBlob(hwBlob2, n * 48);
        }
        hwBlob.putBlob(l + 8L + 0L, hwBlob2);
        n2 = this.cellIdentityWcdma.size();
        hwBlob.putInt32(l + 24L + 8L, n2);
        hwBlob.putBool(l + 24L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 48);
        for (n = 0; n < n2; ++n) {
            this.cellIdentityWcdma.get(n).writeEmbeddedToBlob(hwBlob2, n * 48);
        }
        hwBlob.putBlob(l + 24L + 0L, hwBlob2);
        n2 = this.cellIdentityCdma.size();
        hwBlob.putInt32(l + 40L + 8L, n2);
        hwBlob.putBool(l + 40L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 20);
        for (n = 0; n < n2; ++n) {
            this.cellIdentityCdma.get(n).writeEmbeddedToBlob(hwBlob2, n * 20);
        }
        hwBlob.putBlob(l + 40L + 0L, hwBlob2);
        n2 = this.cellIdentityLte.size();
        hwBlob.putInt32(l + 56L + 8L, n2);
        hwBlob.putBool(l + 56L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 48);
        for (n = 0; n < n2; ++n) {
            this.cellIdentityLte.get(n).writeEmbeddedToBlob(hwBlob2, n * 48);
        }
        hwBlob.putBlob(l + 56L + 0L, hwBlob2);
        n2 = this.cellIdentityTdscdma.size();
        hwBlob.putInt32(l + 72L + 8L, n2);
        hwBlob.putBool(l + 72L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 48);
        for (n = 0; n < n2; ++n) {
            this.cellIdentityTdscdma.get(n).writeEmbeddedToBlob(hwBlob2, n * 48);
        }
        hwBlob.putBlob(l + 72L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

