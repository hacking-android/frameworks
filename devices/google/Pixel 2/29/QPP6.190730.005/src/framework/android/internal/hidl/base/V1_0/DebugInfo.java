/*
 * Decompiled with CFR 0.145.
 */
package android.internal.hidl.base.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class DebugInfo {
    public int arch;
    public int pid;
    public long ptr;

    public static final ArrayList<DebugInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DebugInfo> arrayList = new ArrayList<DebugInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new DebugInfo();
            ((DebugInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((DebugInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DebugInfo> arrayList) {
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
        if (object.getClass() != DebugInfo.class) {
            return false;
        }
        object = (DebugInfo)object;
        if (this.pid != ((DebugInfo)object).pid) {
            return false;
        }
        if (this.ptr != ((DebugInfo)object).ptr) {
            return false;
        }
        return this.arch == ((DebugInfo)object).arch;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.pid), HidlSupport.deepHashCode(this.ptr), HidlSupport.deepHashCode(this.arch));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.pid = hwBlob.getInt32(0L + l);
        this.ptr = hwBlob.getInt64(8L + l);
        this.arch = hwBlob.getInt32(16L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".pid = ");
        stringBuilder.append(this.pid);
        stringBuilder.append(", .ptr = ");
        stringBuilder.append(this.ptr);
        stringBuilder.append(", .arch = ");
        stringBuilder.append(Architecture.toString(this.arch));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.pid);
        hwBlob.putInt64(8L + l, this.ptr);
        hwBlob.putInt32(16L + l, this.arch);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final class Architecture {
        public static final int IS_32BIT = 2;
        public static final int IS_64BIT = 1;
        public static final int UNKNOWN = 0;

        public static final String dumpBitfield(int n) {
            ArrayList<String> arrayList = new ArrayList<String>();
            int n2 = 0;
            arrayList.add("UNKNOWN");
            if ((n & 1) == 1) {
                arrayList.add("IS_64BIT");
                n2 = false | true;
            }
            int n3 = n2;
            if ((n & 2) == 2) {
                arrayList.add("IS_32BIT");
                n3 = n2 | 2;
            }
            if (n != n3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("0x");
                stringBuilder.append(Integer.toHexString(n3 & n));
                arrayList.add(stringBuilder.toString());
            }
            return String.join((CharSequence)" | ", arrayList);
        }

        public static final String toString(int n) {
            if (n == 0) {
                return "UNKNOWN";
            }
            if (n == 1) {
                return "IS_64BIT";
            }
            if (n == 2) {
                return "IS_32BIT";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(n));
            return stringBuilder.toString();
        }
    }

}

