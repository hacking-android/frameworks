/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.SimRefreshType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SimRefreshResult {
    public String aid = new String();
    public int efId;
    public int type;

    public static final ArrayList<SimRefreshResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SimRefreshResult> arrayList = new ArrayList<SimRefreshResult>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SimRefreshResult();
            ((SimRefreshResult)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((SimRefreshResult)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SimRefreshResult> arrayList) {
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
        if (object.getClass() != SimRefreshResult.class) {
            return false;
        }
        object = (SimRefreshResult)object;
        if (this.type != ((SimRefreshResult)object).type) {
            return false;
        }
        if (this.efId != ((SimRefreshResult)object).efId) {
            return false;
        }
        return HidlSupport.deepEquals(this.aid, ((SimRefreshResult)object).aid);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.efId), HidlSupport.deepHashCode(this.aid));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(l + 0L);
        this.efId = hwBlob.getInt32(l + 4L);
        this.aid = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.aid.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(SimRefreshType.toString(this.type));
        stringBuilder.append(", .efId = ");
        stringBuilder.append(this.efId);
        stringBuilder.append(", .aid = ");
        stringBuilder.append(this.aid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.type);
        hwBlob.putInt32(4L + l, this.efId);
        hwBlob.putString(8L + l, this.aid);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

