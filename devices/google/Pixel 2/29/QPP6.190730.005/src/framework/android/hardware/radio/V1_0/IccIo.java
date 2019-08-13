/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class IccIo {
    public String aid = new String();
    public int command;
    public String data = new String();
    public int fileId;
    public int p1;
    public int p2;
    public int p3;
    public String path = new String();
    public String pin2 = new String();

    public static final ArrayList<IccIo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<IccIo> arrayList = new ArrayList<IccIo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 88, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            IccIo iccIo = new IccIo();
            iccIo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add(iccIo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<IccIo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 88);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
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
        if (object.getClass() != IccIo.class) {
            return false;
        }
        object = (IccIo)object;
        if (this.command != ((IccIo)object).command) {
            return false;
        }
        if (this.fileId != ((IccIo)object).fileId) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.path, ((IccIo)object).path)) {
            return false;
        }
        if (this.p1 != ((IccIo)object).p1) {
            return false;
        }
        if (this.p2 != ((IccIo)object).p2) {
            return false;
        }
        if (this.p3 != ((IccIo)object).p3) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.data, ((IccIo)object).data)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.pin2, ((IccIo)object).pin2)) {
            return false;
        }
        return HidlSupport.deepEquals(this.aid, ((IccIo)object).aid);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.command), HidlSupport.deepHashCode(this.fileId), HidlSupport.deepHashCode(this.path), HidlSupport.deepHashCode(this.p1), HidlSupport.deepHashCode(this.p2), HidlSupport.deepHashCode(this.p3), HidlSupport.deepHashCode(this.data), HidlSupport.deepHashCode(this.pin2), HidlSupport.deepHashCode(this.aid));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.command = hwBlob.getInt32(l + 0L);
        this.fileId = hwBlob.getInt32(l + 4L);
        this.path = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.path.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.p1 = hwBlob.getInt32(l + 24L);
        this.p2 = hwBlob.getInt32(l + 28L);
        this.p3 = hwBlob.getInt32(l + 32L);
        this.data = hwBlob.getString(l + 40L);
        hwParcel.readEmbeddedBuffer(this.data.getBytes().length + 1, hwBlob.handle(), l + 40L + 0L, false);
        this.pin2 = hwBlob.getString(l + 56L);
        hwParcel.readEmbeddedBuffer(this.pin2.getBytes().length + 1, hwBlob.handle(), l + 56L + 0L, false);
        this.aid = hwBlob.getString(l + 72L);
        hwParcel.readEmbeddedBuffer(this.aid.getBytes().length + 1, hwBlob.handle(), l + 72L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".command = ");
        stringBuilder.append(this.command);
        stringBuilder.append(", .fileId = ");
        stringBuilder.append(this.fileId);
        stringBuilder.append(", .path = ");
        stringBuilder.append(this.path);
        stringBuilder.append(", .p1 = ");
        stringBuilder.append(this.p1);
        stringBuilder.append(", .p2 = ");
        stringBuilder.append(this.p2);
        stringBuilder.append(", .p3 = ");
        stringBuilder.append(this.p3);
        stringBuilder.append(", .data = ");
        stringBuilder.append(this.data);
        stringBuilder.append(", .pin2 = ");
        stringBuilder.append(this.pin2);
        stringBuilder.append(", .aid = ");
        stringBuilder.append(this.aid);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.command);
        hwBlob.putInt32(4L + l, this.fileId);
        hwBlob.putString(8L + l, this.path);
        hwBlob.putInt32(24L + l, this.p1);
        hwBlob.putInt32(28L + l, this.p2);
        hwBlob.putInt32(32L + l, this.p3);
        hwBlob.putString(40L + l, this.data);
        hwBlob.putString(56L + l, this.pin2);
        hwBlob.putString(72L + l, this.aid);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

