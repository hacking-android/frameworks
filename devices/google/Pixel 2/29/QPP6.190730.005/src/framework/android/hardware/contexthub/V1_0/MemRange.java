/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.hardware.contexthub.V1_0.HubMemoryFlag;
import android.hardware.contexthub.V1_0.HubMemoryType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class MemRange {
    public int flags;
    public int freeBytes;
    public int totalBytes;
    public int type;

    public static final ArrayList<MemRange> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<MemRange> arrayList = new ArrayList<MemRange>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 16, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new MemRange();
            ((MemRange)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add((MemRange)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<MemRange> arrayList) {
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
        if (object.getClass() != MemRange.class) {
            return false;
        }
        object = (MemRange)object;
        if (this.totalBytes != ((MemRange)object).totalBytes) {
            return false;
        }
        if (this.freeBytes != ((MemRange)object).freeBytes) {
            return false;
        }
        if (this.type != ((MemRange)object).type) {
            return false;
        }
        return HidlSupport.deepEquals(this.flags, ((MemRange)object).flags);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.totalBytes), HidlSupport.deepHashCode(this.freeBytes), HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.flags));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.totalBytes = hwBlob.getInt32(0L + l);
        this.freeBytes = hwBlob.getInt32(4L + l);
        this.type = hwBlob.getInt32(8L + l);
        this.flags = hwBlob.getInt32(12L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".totalBytes = ");
        stringBuilder.append(this.totalBytes);
        stringBuilder.append(", .freeBytes = ");
        stringBuilder.append(this.freeBytes);
        stringBuilder.append(", .type = ");
        stringBuilder.append(HubMemoryType.toString(this.type));
        stringBuilder.append(", .flags = ");
        stringBuilder.append(HubMemoryFlag.dumpBitfield(this.flags));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.totalBytes);
        hwBlob.putInt32(4L + l, this.freeBytes);
        hwBlob.putInt32(8L + l, this.type);
        hwBlob.putInt32(12L + l, this.flags);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

