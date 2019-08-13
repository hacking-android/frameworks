/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaBroadcastSmsConfigInfo {
    public int language;
    public boolean selected;
    public int serviceCategory;

    public static final ArrayList<CdmaBroadcastSmsConfigInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaBroadcastSmsConfigInfo> arrayList = new ArrayList<CdmaBroadcastSmsConfigInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 12, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaBroadcastSmsConfigInfo();
            ((CdmaBroadcastSmsConfigInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 12);
            arrayList.add((CdmaBroadcastSmsConfigInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 12);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 12);
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
        if (object.getClass() != CdmaBroadcastSmsConfigInfo.class) {
            return false;
        }
        object = (CdmaBroadcastSmsConfigInfo)object;
        if (this.serviceCategory != ((CdmaBroadcastSmsConfigInfo)object).serviceCategory) {
            return false;
        }
        if (this.language != ((CdmaBroadcastSmsConfigInfo)object).language) {
            return false;
        }
        return this.selected == ((CdmaBroadcastSmsConfigInfo)object).selected;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.serviceCategory), HidlSupport.deepHashCode(this.language), HidlSupport.deepHashCode(this.selected));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.serviceCategory = hwBlob.getInt32(0L + l);
        this.language = hwBlob.getInt32(4L + l);
        this.selected = hwBlob.getBool(8L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(12L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".serviceCategory = ");
        stringBuilder.append(this.serviceCategory);
        stringBuilder.append(", .language = ");
        stringBuilder.append(this.language);
        stringBuilder.append(", .selected = ");
        stringBuilder.append(this.selected);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.serviceCategory);
        hwBlob.putInt32(4L + l, this.language);
        hwBlob.putBool(8L + l, this.selected);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(12);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

