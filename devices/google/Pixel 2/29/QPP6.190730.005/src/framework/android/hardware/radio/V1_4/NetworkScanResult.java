/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.RadioError;
import android.hardware.radio.V1_1.ScanStatus;
import android.hardware.radio.V1_4.CellInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class NetworkScanResult {
    public int error;
    public ArrayList<CellInfo> networkInfos = new ArrayList();
    public int status;

    public static final ArrayList<NetworkScanResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NetworkScanResult> arrayList = new ArrayList<NetworkScanResult>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 24, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            NetworkScanResult networkScanResult = new NetworkScanResult();
            networkScanResult.readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add(networkScanResult);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NetworkScanResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 24);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
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
        if (object.getClass() != NetworkScanResult.class) {
            return false;
        }
        object = (NetworkScanResult)object;
        if (this.status != ((NetworkScanResult)object).status) {
            return false;
        }
        if (this.error != ((NetworkScanResult)object).error) {
            return false;
        }
        return HidlSupport.deepEquals(this.networkInfos, ((NetworkScanResult)object).networkInfos);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.status), HidlSupport.deepHashCode(this.error), HidlSupport.deepHashCode(this.networkInfos));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob object, long l) {
        this.status = ((HwBlob)object).getInt32(l + 0L);
        this.error = ((HwBlob)object).getInt32(l + 4L);
        int n = ((HwBlob)object).getInt32(l + 8L + 8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 136, ((HwBlob)object).handle(), l + 8L + 0L, true);
        this.networkInfos.clear();
        for (int i = 0; i < n; ++i) {
            object = new CellInfo();
            ((CellInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 136);
            this.networkInfos.add((CellInfo)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".status = ");
        stringBuilder.append(ScanStatus.toString(this.status));
        stringBuilder.append(", .error = ");
        stringBuilder.append(RadioError.toString(this.error));
        stringBuilder.append(", .networkInfos = ");
        stringBuilder.append(this.networkInfos);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.status);
        hwBlob.putInt32(4L + l, this.error);
        int n = this.networkInfos.size();
        hwBlob.putInt32(l + 8L + 8L, n);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 136);
        for (int i = 0; i < n; ++i) {
            this.networkInfos.get(i).writeEmbeddedToBlob(hwBlob2, i * 136);
        }
        hwBlob.putBlob(8L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

