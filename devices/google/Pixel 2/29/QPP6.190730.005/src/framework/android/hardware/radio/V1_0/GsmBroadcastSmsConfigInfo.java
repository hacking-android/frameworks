/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class GsmBroadcastSmsConfigInfo {
    public int fromCodeScheme;
    public int fromServiceId;
    public boolean selected;
    public int toCodeScheme;
    public int toServiceId;

    public static final ArrayList<GsmBroadcastSmsConfigInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<GsmBroadcastSmsConfigInfo> arrayList = new ArrayList<GsmBroadcastSmsConfigInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 20, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            GsmBroadcastSmsConfigInfo gsmBroadcastSmsConfigInfo = new GsmBroadcastSmsConfigInfo();
            gsmBroadcastSmsConfigInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 20);
            arrayList.add(gsmBroadcastSmsConfigInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 20);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
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
        if (object.getClass() != GsmBroadcastSmsConfigInfo.class) {
            return false;
        }
        object = (GsmBroadcastSmsConfigInfo)object;
        if (this.fromServiceId != ((GsmBroadcastSmsConfigInfo)object).fromServiceId) {
            return false;
        }
        if (this.toServiceId != ((GsmBroadcastSmsConfigInfo)object).toServiceId) {
            return false;
        }
        if (this.fromCodeScheme != ((GsmBroadcastSmsConfigInfo)object).fromCodeScheme) {
            return false;
        }
        if (this.toCodeScheme != ((GsmBroadcastSmsConfigInfo)object).toCodeScheme) {
            return false;
        }
        return this.selected == ((GsmBroadcastSmsConfigInfo)object).selected;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.fromServiceId), HidlSupport.deepHashCode(this.toServiceId), HidlSupport.deepHashCode(this.fromCodeScheme), HidlSupport.deepHashCode(this.toCodeScheme), HidlSupport.deepHashCode(this.selected));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.fromServiceId = hwBlob.getInt32(0L + l);
        this.toServiceId = hwBlob.getInt32(4L + l);
        this.fromCodeScheme = hwBlob.getInt32(8L + l);
        this.toCodeScheme = hwBlob.getInt32(12L + l);
        this.selected = hwBlob.getBool(16L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".fromServiceId = ");
        stringBuilder.append(this.fromServiceId);
        stringBuilder.append(", .toServiceId = ");
        stringBuilder.append(this.toServiceId);
        stringBuilder.append(", .fromCodeScheme = ");
        stringBuilder.append(this.fromCodeScheme);
        stringBuilder.append(", .toCodeScheme = ");
        stringBuilder.append(this.toCodeScheme);
        stringBuilder.append(", .selected = ");
        stringBuilder.append(this.selected);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.fromServiceId);
        hwBlob.putInt32(4L + l, this.toServiceId);
        hwBlob.putInt32(8L + l, this.fromCodeScheme);
        hwBlob.putInt32(12L + l, this.toCodeScheme);
        hwBlob.putBool(16L + l, this.selected);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

