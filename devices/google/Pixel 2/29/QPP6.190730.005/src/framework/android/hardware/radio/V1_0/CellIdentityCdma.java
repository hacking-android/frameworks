/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CellIdentityCdma {
    public int baseStationId;
    public int latitude;
    public int longitude;
    public int networkId;
    public int systemId;

    public static final ArrayList<CellIdentityCdma> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CellIdentityCdma> arrayList = new ArrayList<CellIdentityCdma>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 20, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CellIdentityCdma cellIdentityCdma = new CellIdentityCdma();
            cellIdentityCdma.readEmbeddedFromParcel(hwParcel, hwBlob, i * 20);
            arrayList.add(cellIdentityCdma);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CellIdentityCdma> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 20);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
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
        if (object.getClass() != CellIdentityCdma.class) {
            return false;
        }
        object = (CellIdentityCdma)object;
        if (this.networkId != ((CellIdentityCdma)object).networkId) {
            return false;
        }
        if (this.systemId != ((CellIdentityCdma)object).systemId) {
            return false;
        }
        if (this.baseStationId != ((CellIdentityCdma)object).baseStationId) {
            return false;
        }
        if (this.longitude != ((CellIdentityCdma)object).longitude) {
            return false;
        }
        return this.latitude == ((CellIdentityCdma)object).latitude;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.networkId), HidlSupport.deepHashCode(this.systemId), HidlSupport.deepHashCode(this.baseStationId), HidlSupport.deepHashCode(this.longitude), HidlSupport.deepHashCode(this.latitude));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.networkId = hwBlob.getInt32(0L + l);
        this.systemId = hwBlob.getInt32(4L + l);
        this.baseStationId = hwBlob.getInt32(8L + l);
        this.longitude = hwBlob.getInt32(12L + l);
        this.latitude = hwBlob.getInt32(16L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".networkId = ");
        stringBuilder.append(this.networkId);
        stringBuilder.append(", .systemId = ");
        stringBuilder.append(this.systemId);
        stringBuilder.append(", .baseStationId = ");
        stringBuilder.append(this.baseStationId);
        stringBuilder.append(", .longitude = ");
        stringBuilder.append(this.longitude);
        stringBuilder.append(", .latitude = ");
        stringBuilder.append(this.latitude);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.networkId);
        hwBlob.putInt32(4L + l, this.systemId);
        hwBlob.putInt32(8L + l, this.baseStationId);
        hwBlob.putInt32(12L + l, this.longitude);
        hwBlob.putInt32(16L + l, this.latitude);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

