/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import android.hardware.radio.V1_1.KeepaliveType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class KeepaliveRequest {
    public int cid;
    public ArrayList<Byte> destinationAddress = new ArrayList();
    public int destinationPort;
    public int maxKeepaliveIntervalMillis;
    public ArrayList<Byte> sourceAddress = new ArrayList();
    public int sourcePort;
    public int type;

    public static final ArrayList<KeepaliveRequest> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<KeepaliveRequest> arrayList = new ArrayList<KeepaliveRequest>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 64, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            KeepaliveRequest keepaliveRequest = new KeepaliveRequest();
            keepaliveRequest.readEmbeddedFromParcel(hwParcel, hwBlob, i * 64);
            arrayList.add(keepaliveRequest);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<KeepaliveRequest> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 64);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
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
        if (object.getClass() != KeepaliveRequest.class) {
            return false;
        }
        object = (KeepaliveRequest)object;
        if (this.type != ((KeepaliveRequest)object).type) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.sourceAddress, ((KeepaliveRequest)object).sourceAddress)) {
            return false;
        }
        if (this.sourcePort != ((KeepaliveRequest)object).sourcePort) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.destinationAddress, ((KeepaliveRequest)object).destinationAddress)) {
            return false;
        }
        if (this.destinationPort != ((KeepaliveRequest)object).destinationPort) {
            return false;
        }
        if (this.maxKeepaliveIntervalMillis != ((KeepaliveRequest)object).maxKeepaliveIntervalMillis) {
            return false;
        }
        return this.cid == ((KeepaliveRequest)object).cid;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.sourceAddress), HidlSupport.deepHashCode(this.sourcePort), HidlSupport.deepHashCode(this.destinationAddress), HidlSupport.deepHashCode(this.destinationPort), HidlSupport.deepHashCode(this.maxKeepaliveIntervalMillis), HidlSupport.deepHashCode(this.cid));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        byte by;
        int n;
        this.type = hwBlob.getInt32(l + 0L);
        int n2 = hwBlob.getInt32(l + 8L + 8L);
        HwBlob hwBlob2 = ((HwParcel)object).readEmbeddedBuffer(n2 * 1, hwBlob.handle(), l + 8L + 0L, true);
        this.sourceAddress.clear();
        for (n = 0; n < n2; ++n) {
            by = hwBlob2.getInt8(n * 1);
            this.sourceAddress.add(by);
        }
        this.sourcePort = hwBlob.getInt32(l + 24L);
        n2 = hwBlob.getInt32(l + 32L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n2 * 1, hwBlob.handle(), l + 32L + 0L, true);
        this.destinationAddress.clear();
        for (n = 0; n < n2; ++n) {
            by = ((HwBlob)object).getInt8(n * 1);
            this.destinationAddress.add(by);
        }
        this.destinationPort = hwBlob.getInt32(l + 48L);
        this.maxKeepaliveIntervalMillis = hwBlob.getInt32(l + 52L);
        this.cid = hwBlob.getInt32(l + 56L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(KeepaliveType.toString(this.type));
        stringBuilder.append(", .sourceAddress = ");
        stringBuilder.append(this.sourceAddress);
        stringBuilder.append(", .sourcePort = ");
        stringBuilder.append(this.sourcePort);
        stringBuilder.append(", .destinationAddress = ");
        stringBuilder.append(this.destinationAddress);
        stringBuilder.append(", .destinationPort = ");
        stringBuilder.append(this.destinationPort);
        stringBuilder.append(", .maxKeepaliveIntervalMillis = ");
        stringBuilder.append(this.maxKeepaliveIntervalMillis);
        stringBuilder.append(", .cid = ");
        stringBuilder.append(this.cid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.type);
        int n2 = this.sourceAddress.size();
        hwBlob.putInt32(l + 8L + 8L, n2);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 1);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putInt8(n * 1, this.sourceAddress.get(n));
        }
        hwBlob.putBlob(l + 8L + 0L, hwBlob2);
        hwBlob.putInt32(l + 24L, this.sourcePort);
        n2 = this.destinationAddress.size();
        hwBlob.putInt32(l + 32L + 8L, n2);
        hwBlob.putBool(l + 32L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 1);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putInt8(n * 1, this.destinationAddress.get(n));
        }
        hwBlob.putBlob(l + 32L + 0L, hwBlob2);
        hwBlob.putInt32(l + 48L, this.destinationPort);
        hwBlob.putInt32(l + 52L, this.maxKeepaliveIntervalMillis);
        hwBlob.putInt32(l + 56L, this.cid);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

