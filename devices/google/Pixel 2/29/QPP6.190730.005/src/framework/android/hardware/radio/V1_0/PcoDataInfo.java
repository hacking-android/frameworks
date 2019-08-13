/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class PcoDataInfo {
    public String bearerProto = new String();
    public int cid;
    public ArrayList<Byte> contents = new ArrayList();
    public int pcoId;

    public static final ArrayList<PcoDataInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PcoDataInfo> arrayList = new ArrayList<PcoDataInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 48, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            PcoDataInfo pcoDataInfo = new PcoDataInfo();
            pcoDataInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 48);
            arrayList.add(pcoDataInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PcoDataInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 48);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 48);
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
        if (object.getClass() != PcoDataInfo.class) {
            return false;
        }
        object = (PcoDataInfo)object;
        if (this.cid != ((PcoDataInfo)object).cid) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.bearerProto, ((PcoDataInfo)object).bearerProto)) {
            return false;
        }
        if (this.pcoId != ((PcoDataInfo)object).pcoId) {
            return false;
        }
        return HidlSupport.deepEquals(this.contents, ((PcoDataInfo)object).contents);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cid), HidlSupport.deepHashCode(this.bearerProto), HidlSupport.deepHashCode(this.pcoId), HidlSupport.deepHashCode(this.contents));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.cid = hwBlob.getInt32(l + 0L);
        this.bearerProto = hwBlob.getString(l + 8L);
        ((HwParcel)object).readEmbeddedBuffer(this.bearerProto.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.pcoId = hwBlob.getInt32(l + 24L);
        int n = hwBlob.getInt32(l + 32L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 1, hwBlob.handle(), l + 32L + 0L, true);
        this.contents.clear();
        for (int i = 0; i < n; ++i) {
            byte by = ((HwBlob)object).getInt8(i * 1);
            this.contents.add(by);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(48L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cid = ");
        stringBuilder.append(this.cid);
        stringBuilder.append(", .bearerProto = ");
        stringBuilder.append(this.bearerProto);
        stringBuilder.append(", .pcoId = ");
        stringBuilder.append(this.pcoId);
        stringBuilder.append(", .contents = ");
        stringBuilder.append(this.contents);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.cid);
        hwBlob.putString(l + 8L, this.bearerProto);
        hwBlob.putInt32(24L + l, this.pcoId);
        int n = this.contents.size();
        hwBlob.putInt32(l + 32L + 8L, n);
        hwBlob.putBool(l + 32L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt8(i * 1, this.contents.get(i));
        }
        hwBlob.putBlob(32L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(48);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

