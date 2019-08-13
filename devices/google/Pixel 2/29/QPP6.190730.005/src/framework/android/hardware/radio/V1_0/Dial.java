/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.Clir;
import android.hardware.radio.V1_0.UusInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class Dial {
    public String address = new String();
    public int clir;
    public ArrayList<UusInfo> uusInfo = new ArrayList();

    public static final ArrayList<Dial> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Dial> arrayList = new ArrayList<Dial>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 40, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new Dial();
            ((Dial)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add((Dial)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Dial> arrayList) {
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
        if (object.getClass() != Dial.class) {
            return false;
        }
        object = (Dial)object;
        if (!HidlSupport.deepEquals(this.address, ((Dial)object).address)) {
            return false;
        }
        if (this.clir != ((Dial)object).clir) {
            return false;
        }
        return HidlSupport.deepEquals(this.uusInfo, ((Dial)object).uusInfo);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.address), HidlSupport.deepHashCode(this.clir), HidlSupport.deepHashCode(this.uusInfo));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob object, long l) {
        this.address = ((HwBlob)object).getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.address.getBytes().length + 1, ((HwBlob)object).handle(), l + 0L + 0L, false);
        this.clir = ((HwBlob)object).getInt32(l + 16L);
        int n = ((HwBlob)object).getInt32(l + 24L + 8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), l + 24L + 0L, true);
        this.uusInfo.clear();
        for (int i = 0; i < n; ++i) {
            object = new UusInfo();
            ((UusInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            this.uusInfo.add((UusInfo)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".address = ");
        stringBuilder.append(this.address);
        stringBuilder.append(", .clir = ");
        stringBuilder.append(Clir.toString(this.clir));
        stringBuilder.append(", .uusInfo = ");
        stringBuilder.append(this.uusInfo);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(l + 0L, this.address);
        hwBlob.putInt32(16L + l, this.clir);
        int n = this.uusInfo.size();
        hwBlob.putInt32(l + 24L + 8L, n);
        hwBlob.putBool(l + 24L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 24);
        for (int i = 0; i < n; ++i) {
            this.uusInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(24L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

