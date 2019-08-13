/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_0.CellInfoType;
import android.hardware.radio.V1_0.TimeStampType;
import android.hardware.radio.V1_2.CellConnectionStatus;
import android.hardware.radio.V1_2.CellInfoCdma;
import android.hardware.radio.V1_2.CellInfoGsm;
import android.hardware.radio.V1_2.CellInfoLte;
import android.hardware.radio.V1_2.CellInfoTdscdma;
import android.hardware.radio.V1_2.CellInfoWcdma;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellInfo {
    public ArrayList<CellInfoCdma> cdma = new ArrayList();
    public int cellInfoType;
    public int connectionStatus;
    public ArrayList<CellInfoGsm> gsm = new ArrayList();
    public ArrayList<CellInfoLte> lte = new ArrayList();
    public boolean registered;
    public ArrayList<CellInfoTdscdma> tdscdma = new ArrayList();
    public long timeStamp;
    public int timeStampType;
    public ArrayList<CellInfoWcdma> wcdma = new ArrayList();

    public static final ArrayList<CellInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellInfo> arrayList = new ArrayList<CellInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 112, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellInfo cellInfo = new CellInfo();
            cellInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 112);
            arrayList.add(cellInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 112);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
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
        if (object.getClass() != CellInfo.class) {
            return false;
        }
        object = (CellInfo)object;
        if (this.cellInfoType != ((CellInfo)object).cellInfoType) {
            return false;
        }
        if (this.registered != ((CellInfo)object).registered) {
            return false;
        }
        if (this.timeStampType != ((CellInfo)object).timeStampType) {
            return false;
        }
        if (this.timeStamp != ((CellInfo)object).timeStamp) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.gsm, ((CellInfo)object).gsm)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.cdma, ((CellInfo)object).cdma)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.lte, ((CellInfo)object).lte)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.wcdma, ((CellInfo)object).wcdma)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.tdscdma, ((CellInfo)object).tdscdma)) {
            return false;
        }
        return this.connectionStatus == ((CellInfo)object).connectionStatus;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cellInfoType), HidlSupport.deepHashCode(this.registered), HidlSupport.deepHashCode(this.timeStampType), HidlSupport.deepHashCode(this.timeStamp), HidlSupport.deepHashCode(this.gsm), HidlSupport.deepHashCode(this.cdma), HidlSupport.deepHashCode(this.lte), HidlSupport.deepHashCode(this.wcdma), HidlSupport.deepHashCode(this.tdscdma), HidlSupport.deepHashCode(this.connectionStatus));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        Object object;
        this.cellInfoType = hwBlob.getInt32(l + 0L);
        this.registered = hwBlob.getBool(l + 4L);
        this.timeStampType = hwBlob.getInt32(l + 8L);
        this.timeStamp = hwBlob.getInt64(l + 16L);
        int n2 = hwBlob.getInt32(l + 24L + 8L);
        Object object2 = hwParcel.readEmbeddedBuffer(n2 * 96, hwBlob.handle(), l + 24L + 0L, true);
        this.gsm.clear();
        for (n = 0; n < n2; ++n) {
            object = new CellInfoGsm();
            ((CellInfoGsm)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 96);
            this.gsm.add((CellInfoGsm)object);
        }
        n2 = hwBlob.getInt32(l + 40L + 8L);
        object2 = hwParcel.readEmbeddedBuffer(n2 * 80, hwBlob.handle(), l + 40L + 0L, true);
        this.cdma.clear();
        for (n = 0; n < n2; ++n) {
            object = new CellInfoCdma();
            ((CellInfoCdma)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 80);
            this.cdma.add((CellInfoCdma)object);
        }
        n2 = hwBlob.getInt32(l + 56L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 112, hwBlob.handle(), l + 56L + 0L, true);
        this.lte.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CellInfoLte();
            ((CellInfoLte)object2).readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 112);
            this.lte.add((CellInfoLte)object2);
        }
        n2 = hwBlob.getInt32(l + 72L + 8L);
        object2 = hwParcel.readEmbeddedBuffer(n2 * 96, hwBlob.handle(), l + 72L + 0L, true);
        this.wcdma.clear();
        for (n = 0; n < n2; ++n) {
            object = new CellInfoWcdma();
            ((CellInfoWcdma)object).readEmbeddedFromParcel(hwParcel, (HwBlob)object2, n * 96);
            this.wcdma.add((CellInfoWcdma)object);
        }
        n2 = hwBlob.getInt32(l + 88L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 104, hwBlob.handle(), l + 88L + 0L, true);
        this.tdscdma.clear();
        for (n = 0; n < n2; ++n) {
            object2 = new CellInfoTdscdma();
            ((CellInfoTdscdma)object2).readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 104);
            this.tdscdma.add((CellInfoTdscdma)object2);
        }
        this.connectionStatus = hwBlob.getInt32(l + 104L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cellInfoType = ");
        stringBuilder.append(CellInfoType.toString(this.cellInfoType));
        stringBuilder.append(", .registered = ");
        stringBuilder.append(this.registered);
        stringBuilder.append(", .timeStampType = ");
        stringBuilder.append(TimeStampType.toString(this.timeStampType));
        stringBuilder.append(", .timeStamp = ");
        stringBuilder.append(this.timeStamp);
        stringBuilder.append(", .gsm = ");
        stringBuilder.append(this.gsm);
        stringBuilder.append(", .cdma = ");
        stringBuilder.append(this.cdma);
        stringBuilder.append(", .lte = ");
        stringBuilder.append(this.lte);
        stringBuilder.append(", .wcdma = ");
        stringBuilder.append(this.wcdma);
        stringBuilder.append(", .tdscdma = ");
        stringBuilder.append(this.tdscdma);
        stringBuilder.append(", .connectionStatus = ");
        stringBuilder.append(CellConnectionStatus.toString(this.connectionStatus));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.cellInfoType);
        hwBlob.putBool(l + 4L, this.registered);
        hwBlob.putInt32(l + 8L, this.timeStampType);
        hwBlob.putInt64(l + 16L, this.timeStamp);
        int n2 = this.gsm.size();
        hwBlob.putInt32(l + 24L + 8L, n2);
        hwBlob.putBool(l + 24L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 96);
        for (n = 0; n < n2; ++n) {
            this.gsm.get(n).writeEmbeddedToBlob(hwBlob2, n * 96);
        }
        hwBlob.putBlob(l + 24L + 0L, hwBlob2);
        n2 = this.cdma.size();
        hwBlob.putInt32(l + 40L + 8L, n2);
        hwBlob.putBool(l + 40L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 80);
        for (n = 0; n < n2; ++n) {
            this.cdma.get(n).writeEmbeddedToBlob(hwBlob2, n * 80);
        }
        hwBlob.putBlob(l + 40L + 0L, hwBlob2);
        n2 = this.lte.size();
        hwBlob.putInt32(l + 56L + 8L, n2);
        hwBlob.putBool(l + 56L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 112);
        for (n = 0; n < n2; ++n) {
            this.lte.get(n).writeEmbeddedToBlob(hwBlob2, n * 112);
        }
        hwBlob.putBlob(l + 56L + 0L, hwBlob2);
        n2 = this.wcdma.size();
        hwBlob.putInt32(l + 72L + 8L, n2);
        hwBlob.putBool(l + 72L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 96);
        for (n = 0; n < n2; ++n) {
            this.wcdma.get(n).writeEmbeddedToBlob(hwBlob2, n * 96);
        }
        hwBlob.putBlob(l + 72L + 0L, hwBlob2);
        n2 = this.tdscdma.size();
        hwBlob.putInt32(l + 88L + 8L, n2);
        hwBlob.putBool(l + 88L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 104);
        for (n = 0; n < n2; ++n) {
            this.tdscdma.get(n).writeEmbeddedToBlob(hwBlob2, n * 104);
        }
        hwBlob.putBlob(l + 88L + 0L, hwBlob2);
        hwBlob.putInt32(l + 104L, this.connectionStatus);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

