/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.SubscriptionType;
import android.hardware.radio.V1_0.UiccSubActStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SelectUiccSub {
    public int actStatus;
    public int appIndex;
    public int slot;
    public int subType;

    public static final ArrayList<SelectUiccSub> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SelectUiccSub> arrayList = new ArrayList<SelectUiccSub>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 16, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SelectUiccSub();
            ((SelectUiccSub)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add((SelectUiccSub)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SelectUiccSub> arrayList) {
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
        if (object.getClass() != SelectUiccSub.class) {
            return false;
        }
        object = (SelectUiccSub)object;
        if (this.slot != ((SelectUiccSub)object).slot) {
            return false;
        }
        if (this.appIndex != ((SelectUiccSub)object).appIndex) {
            return false;
        }
        if (this.subType != ((SelectUiccSub)object).subType) {
            return false;
        }
        return this.actStatus == ((SelectUiccSub)object).actStatus;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.slot), HidlSupport.deepHashCode(this.appIndex), HidlSupport.deepHashCode(this.subType), HidlSupport.deepHashCode(this.actStatus));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.slot = hwBlob.getInt32(0L + l);
        this.appIndex = hwBlob.getInt32(4L + l);
        this.subType = hwBlob.getInt32(8L + l);
        this.actStatus = hwBlob.getInt32(12L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".slot = ");
        stringBuilder.append(this.slot);
        stringBuilder.append(", .appIndex = ");
        stringBuilder.append(this.appIndex);
        stringBuilder.append(", .subType = ");
        stringBuilder.append(SubscriptionType.toString(this.subType));
        stringBuilder.append(", .actStatus = ");
        stringBuilder.append(UiccSubActStatus.toString(this.actStatus));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.slot);
        hwBlob.putInt32(4L + l, this.appIndex);
        hwBlob.putInt32(8L + l, this.subType);
        hwBlob.putInt32(12L + l, this.actStatus);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

