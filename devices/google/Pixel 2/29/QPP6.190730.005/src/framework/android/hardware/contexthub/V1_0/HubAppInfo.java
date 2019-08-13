/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.hardware.contexthub.V1_0.MemRange;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class HubAppInfo {
    public long appId;
    public boolean enabled;
    public ArrayList<MemRange> memUsage = new ArrayList();
    public int version;

    public static final ArrayList<HubAppInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<HubAppInfo> arrayList = new ArrayList<HubAppInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            HubAppInfo hubAppInfo = new HubAppInfo();
            hubAppInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(hubAppInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<HubAppInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 40);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
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
        if (object.getClass() != HubAppInfo.class) {
            return false;
        }
        object = (HubAppInfo)object;
        if (this.appId != ((HubAppInfo)object).appId) {
            return false;
        }
        if (this.version != ((HubAppInfo)object).version) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.memUsage, ((HubAppInfo)object).memUsage)) {
            return false;
        }
        return this.enabled == ((HubAppInfo)object).enabled;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.appId), HidlSupport.deepHashCode(this.version), HidlSupport.deepHashCode(this.memUsage), HidlSupport.deepHashCode(this.enabled));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.appId = hwBlob.getInt64(l + 0L);
        this.version = hwBlob.getInt32(l + 8L);
        int n = hwBlob.getInt32(l + 16L + 8L);
        HwBlob hwBlob2 = hwParcel.readEmbeddedBuffer(n * 16, hwBlob.handle(), l + 16L + 0L, true);
        this.memUsage.clear();
        for (int i = 0; i < n; ++i) {
            MemRange memRange = new MemRange();
            memRange.readEmbeddedFromParcel(hwParcel, hwBlob2, i * 16);
            this.memUsage.add(memRange);
        }
        this.enabled = hwBlob.getBool(l + 32L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".appId = ");
        stringBuilder.append(this.appId);
        stringBuilder.append(", .version = ");
        stringBuilder.append(this.version);
        stringBuilder.append(", .memUsage = ");
        stringBuilder.append(this.memUsage);
        stringBuilder.append(", .enabled = ");
        stringBuilder.append(this.enabled);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt64(l + 0L, this.appId);
        hwBlob.putInt32(l + 8L, this.version);
        int n = this.memUsage.size();
        hwBlob.putInt32(l + 16L + 8L, n);
        hwBlob.putBool(l + 16L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 16);
        for (int i = 0; i < n; ++i) {
            this.memUsage.get(i).writeEmbeddedToBlob(hwBlob2, i * 16);
        }
        hwBlob.putBlob(16L + l + 0L, hwBlob2);
        hwBlob.putBool(32L + l, this.enabled);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

