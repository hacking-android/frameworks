/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_1.RadioAccessSpecifier;
import android.hardware.radio.V1_1.ScanType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class NetworkScanRequest {
    public boolean incrementalResults;
    public int incrementalResultsPeriodicity;
    public int interval;
    public int maxSearchTime;
    public ArrayList<String> mccMncs = new ArrayList();
    public ArrayList<RadioAccessSpecifier> specifiers = new ArrayList();
    public int type;

    public static final ArrayList<NetworkScanRequest> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NetworkScanRequest> arrayList = new ArrayList<NetworkScanRequest>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 56, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            NetworkScanRequest networkScanRequest = new NetworkScanRequest();
            networkScanRequest.readEmbeddedFromParcel(hwParcel, hwBlob, i * 56);
            arrayList.add(networkScanRequest);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NetworkScanRequest> arrayList) {
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
        if (object.getClass() != NetworkScanRequest.class) {
            return false;
        }
        object = (NetworkScanRequest)object;
        if (this.type != ((NetworkScanRequest)object).type) {
            return false;
        }
        if (this.interval != ((NetworkScanRequest)object).interval) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.specifiers, ((NetworkScanRequest)object).specifiers)) {
            return false;
        }
        if (this.maxSearchTime != ((NetworkScanRequest)object).maxSearchTime) {
            return false;
        }
        if (this.incrementalResults != ((NetworkScanRequest)object).incrementalResults) {
            return false;
        }
        if (this.incrementalResultsPeriodicity != ((NetworkScanRequest)object).incrementalResultsPeriodicity) {
            return false;
        }
        return HidlSupport.deepEquals(this.mccMncs, ((NetworkScanRequest)object).mccMncs);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.interval), HidlSupport.deepHashCode(this.specifiers), HidlSupport.deepHashCode(this.maxSearchTime), HidlSupport.deepHashCode(this.incrementalResults), HidlSupport.deepHashCode(this.incrementalResultsPeriodicity), HidlSupport.deepHashCode(this.mccMncs));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        this.type = hwBlob.getInt32(l + 0L);
        this.interval = hwBlob.getInt32(l + 4L);
        int n2 = hwBlob.getInt32(l + 8L + 8L);
        Object object = hwParcel.readEmbeddedBuffer(n2 * 72, hwBlob.handle(), l + 8L + 0L, true);
        this.specifiers.clear();
        for (n = 0; n < n2; ++n) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, (HwBlob)object, n * 72);
            this.specifiers.add(radioAccessSpecifier);
        }
        this.maxSearchTime = hwBlob.getInt32(l + 24L);
        this.incrementalResults = hwBlob.getBool(l + 28L);
        this.incrementalResultsPeriodicity = hwBlob.getInt32(l + 32L);
        n2 = hwBlob.getInt32(l + 40L + 8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 40L + 0L, true);
        this.mccMncs.clear();
        for (n = 0; n < n2; ++n) {
            new String();
            object = hwBlob.getString(n * 16);
            hwParcel.readEmbeddedBuffer(((String)object).getBytes().length + 1, hwBlob.handle(), n * 16 + 0, false);
            this.mccMncs.add((String)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(ScanType.toString(this.type));
        stringBuilder.append(", .interval = ");
        stringBuilder.append(this.interval);
        stringBuilder.append(", .specifiers = ");
        stringBuilder.append(this.specifiers);
        stringBuilder.append(", .maxSearchTime = ");
        stringBuilder.append(this.maxSearchTime);
        stringBuilder.append(", .incrementalResults = ");
        stringBuilder.append(this.incrementalResults);
        stringBuilder.append(", .incrementalResultsPeriodicity = ");
        stringBuilder.append(this.incrementalResultsPeriodicity);
        stringBuilder.append(", .mccMncs = ");
        stringBuilder.append(this.mccMncs);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.type);
        hwBlob.putInt32(l + 4L, this.interval);
        int n2 = this.specifiers.size();
        hwBlob.putInt32(l + 8L + 8L, n2);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 72);
        for (n = 0; n < n2; ++n) {
            this.specifiers.get(n).writeEmbeddedToBlob(hwBlob2, n * 72);
        }
        hwBlob.putBlob(l + 8L + 0L, hwBlob2);
        hwBlob.putInt32(l + 24L, this.maxSearchTime);
        hwBlob.putBool(l + 28L, this.incrementalResults);
        hwBlob.putInt32(l + 32L, this.incrementalResultsPeriodicity);
        n2 = this.mccMncs.size();
        hwBlob.putInt32(l + 40L + 8L, n2);
        hwBlob.putBool(l + 40L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putString(n * 16, this.mccMncs.get(n));
        }
        hwBlob.putBlob(l + 40L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

