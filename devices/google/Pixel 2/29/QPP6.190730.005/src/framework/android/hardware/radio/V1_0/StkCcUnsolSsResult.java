/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CfData;
import android.hardware.radio.V1_0.RadioError;
import android.hardware.radio.V1_0.SsInfoData;
import android.hardware.radio.V1_0.SsRequestType;
import android.hardware.radio.V1_0.SsServiceType;
import android.hardware.radio.V1_0.SsTeleserviceType;
import android.hardware.radio.V1_0.SuppServiceClass;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class StkCcUnsolSsResult {
    public ArrayList<CfData> cfData = new ArrayList();
    public int requestType;
    public int result;
    public int serviceClass;
    public int serviceType;
    public ArrayList<SsInfoData> ssInfo = new ArrayList();
    public int teleserviceType;

    public static final ArrayList<StkCcUnsolSsResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<StkCcUnsolSsResult> arrayList = new ArrayList<StkCcUnsolSsResult>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 56, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            StkCcUnsolSsResult stkCcUnsolSsResult = new StkCcUnsolSsResult();
            stkCcUnsolSsResult.readEmbeddedFromParcel(hwParcel, hwBlob, i * 56);
            arrayList.add(stkCcUnsolSsResult);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<StkCcUnsolSsResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 56);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
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
        if (object.getClass() != StkCcUnsolSsResult.class) {
            return false;
        }
        object = (StkCcUnsolSsResult)object;
        if (this.serviceType != ((StkCcUnsolSsResult)object).serviceType) {
            return false;
        }
        if (this.requestType != ((StkCcUnsolSsResult)object).requestType) {
            return false;
        }
        if (this.teleserviceType != ((StkCcUnsolSsResult)object).teleserviceType) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.serviceClass, ((StkCcUnsolSsResult)object).serviceClass)) {
            return false;
        }
        if (this.result != ((StkCcUnsolSsResult)object).result) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.ssInfo, ((StkCcUnsolSsResult)object).ssInfo)) {
            return false;
        }
        return HidlSupport.deepEquals(this.cfData, ((StkCcUnsolSsResult)object).cfData);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.serviceType), HidlSupport.deepHashCode(this.requestType), HidlSupport.deepHashCode(this.teleserviceType), HidlSupport.deepHashCode(this.serviceClass), HidlSupport.deepHashCode(this.result), HidlSupport.deepHashCode(this.ssInfo), HidlSupport.deepHashCode(this.cfData));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        this.serviceType = hwBlob.getInt32(l + 0L);
        this.requestType = hwBlob.getInt32(l + 4L);
        this.teleserviceType = hwBlob.getInt32(l + 8L);
        this.serviceClass = hwBlob.getInt32(l + 12L);
        this.result = hwBlob.getInt32(l + 16L);
        int n2 = hwBlob.getInt32(l + 24L + 8L);
        Object object = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 24L + 0L, true);
        this.ssInfo.clear();
        for (n = 0; n < n2; ++n) {
            SsInfoData ssInfoData = new SsInfoData();
            ssInfoData.readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 16);
            this.ssInfo.add(ssInfoData);
        }
        n2 = hwBlob.getInt32(l + 40L + 8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 40L + 0L, true);
        this.cfData.clear();
        for (n = 0; n < n2; ++n) {
            object = new CfData();
            ((CfData)object).readEmbeddedFromParcel(hwParcel, hwBlob, n * 16);
            this.cfData.add((CfData)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".serviceType = ");
        stringBuilder.append(SsServiceType.toString(this.serviceType));
        stringBuilder.append(", .requestType = ");
        stringBuilder.append(SsRequestType.toString(this.requestType));
        stringBuilder.append(", .teleserviceType = ");
        stringBuilder.append(SsTeleserviceType.toString(this.teleserviceType));
        stringBuilder.append(", .serviceClass = ");
        stringBuilder.append(SuppServiceClass.dumpBitfield(this.serviceClass));
        stringBuilder.append(", .result = ");
        stringBuilder.append(RadioError.toString(this.result));
        stringBuilder.append(", .ssInfo = ");
        stringBuilder.append(this.ssInfo);
        stringBuilder.append(", .cfData = ");
        stringBuilder.append(this.cfData);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.serviceType);
        hwBlob.putInt32(l + 4L, this.requestType);
        hwBlob.putInt32(l + 8L, this.teleserviceType);
        hwBlob.putInt32(l + 12L, this.serviceClass);
        hwBlob.putInt32(l + 16L, this.result);
        int n2 = this.ssInfo.size();
        hwBlob.putInt32(l + 24L + 8L, n2);
        hwBlob.putBool(l + 24L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            this.ssInfo.get(n).writeEmbeddedToBlob(hwBlob2, n * 16);
        }
        hwBlob.putBlob(l + 24L + 0L, hwBlob2);
        n2 = this.cfData.size();
        hwBlob.putInt32(l + 40L + 8L, n2);
        hwBlob.putBool(l + 40L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            this.cfData.get(n).writeEmbeddedToBlob(hwBlob2, n * 16);
        }
        hwBlob.putBlob(l + 40L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

