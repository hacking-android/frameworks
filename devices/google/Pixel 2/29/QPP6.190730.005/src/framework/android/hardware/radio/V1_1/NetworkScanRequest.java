/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import android.hardware.radio.V1_1.RadioAccessSpecifier;
import android.hardware.radio.V1_1.ScanType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class NetworkScanRequest {
    public int interval;
    public ArrayList<RadioAccessSpecifier> specifiers = new ArrayList();
    public int type;

    public static final ArrayList<NetworkScanRequest> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NetworkScanRequest> arrayList = new ArrayList<NetworkScanRequest>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new NetworkScanRequest();
            ((NetworkScanRequest)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((NetworkScanRequest)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NetworkScanRequest> arrayList) {
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
        return HidlSupport.deepEquals(this.specifiers, ((NetworkScanRequest)object).specifiers);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.interval), HidlSupport.deepHashCode(this.specifiers));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(l + 0L);
        this.interval = hwBlob.getInt32(l + 4L);
        int n = hwBlob.getInt32(l + 8L + 8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 72, hwBlob.handle(), l + 8L + 0L, true);
        this.specifiers.clear();
        for (int i = 0; i < n; ++i) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, hwBlob, i * 72);
            this.specifiers.add(radioAccessSpecifier);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
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
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.type);
        hwBlob.putInt32(4L + l, this.interval);
        int n = this.specifiers.size();
        hwBlob.putInt32(l + 8L + 8L, n);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 72);
        for (int i = 0; i < n; ++i) {
            this.specifiers.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
        }
        hwBlob.putBlob(8L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

