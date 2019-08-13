/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CallForwardInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CfData {
    public ArrayList<CallForwardInfo> cfInfo = new ArrayList();

    public static final ArrayList<CfData> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CfData> arrayList = new ArrayList<CfData>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 16, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CfData cfData = new CfData();
            cfData.readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add(cfData);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CfData> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 16);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
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
        if (object.getClass() != CfData.class) {
            return false;
        }
        object = (CfData)object;
        return HidlSupport.deepEquals(this.cfInfo, ((CfData)object).cfInfo);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cfInfo));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob object, long l) {
        int n = ((HwBlob)object).getInt32(l + 0L + 8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 40, ((HwBlob)object).handle(), l + 0L + 0L, true);
        this.cfInfo.clear();
        for (int i = 0; i < n; ++i) {
            object = new CallForwardInfo();
            ((CallForwardInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            this.cfInfo.add((CallForwardInfo)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cfInfo = ");
        stringBuilder.append(this.cfInfo);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n = this.cfInfo.size();
        hwBlob.putInt32(l + 0L + 8L, n);
        hwBlob.putBool(l + 0L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 40);
        for (int i = 0; i < n; ++i) {
            this.cfInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
        }
        hwBlob.putBlob(l + 0L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

