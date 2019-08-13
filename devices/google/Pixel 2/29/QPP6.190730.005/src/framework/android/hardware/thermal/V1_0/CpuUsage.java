/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CpuUsage {
    public long active;
    public boolean isOnline;
    public String name = new String();
    public long total;

    public static final ArrayList<CpuUsage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CpuUsage> arrayList = new ArrayList<CpuUsage>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 40, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CpuUsage();
            ((CpuUsage)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add((CpuUsage)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CpuUsage> arrayList) {
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
        if (object.getClass() != CpuUsage.class) {
            return false;
        }
        object = (CpuUsage)object;
        if (!HidlSupport.deepEquals(this.name, ((CpuUsage)object).name)) {
            return false;
        }
        if (this.active != ((CpuUsage)object).active) {
            return false;
        }
        if (this.total != ((CpuUsage)object).total) {
            return false;
        }
        return this.isOnline == ((CpuUsage)object).isOnline;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.active), HidlSupport.deepHashCode(this.total), HidlSupport.deepHashCode(this.isOnline));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.name = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.active = hwBlob.getInt64(16L + l);
        this.total = hwBlob.getInt64(24L + l);
        this.isOnline = hwBlob.getBool(32L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .active = ");
        stringBuilder.append(this.active);
        stringBuilder.append(", .total = ");
        stringBuilder.append(this.total);
        stringBuilder.append(", .isOnline = ");
        stringBuilder.append(this.isOnline);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.name);
        hwBlob.putInt64(16L + l, this.active);
        hwBlob.putInt64(24L + l, this.total);
        hwBlob.putBool(32L + l, this.isOnline);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

