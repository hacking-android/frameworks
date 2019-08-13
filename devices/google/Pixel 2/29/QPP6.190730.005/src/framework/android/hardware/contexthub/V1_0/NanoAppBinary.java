/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.hardware.contexthub.V1_0.NanoAppFlags;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class NanoAppBinary {
    public long appId;
    public int appVersion;
    public ArrayList<Byte> customBinary = new ArrayList();
    public int flags;
    public byte targetChreApiMajorVersion;
    public byte targetChreApiMinorVersion;

    public static final ArrayList<NanoAppBinary> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NanoAppBinary> arrayList = new ArrayList<NanoAppBinary>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            NanoAppBinary nanoAppBinary = new NanoAppBinary();
            nanoAppBinary.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(nanoAppBinary);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NanoAppBinary> arrayList) {
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
        if (object.getClass() != NanoAppBinary.class) {
            return false;
        }
        object = (NanoAppBinary)object;
        if (this.appId != ((NanoAppBinary)object).appId) {
            return false;
        }
        if (this.appVersion != ((NanoAppBinary)object).appVersion) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.flags, ((NanoAppBinary)object).flags)) {
            return false;
        }
        if (this.targetChreApiMajorVersion != ((NanoAppBinary)object).targetChreApiMajorVersion) {
            return false;
        }
        if (this.targetChreApiMinorVersion != ((NanoAppBinary)object).targetChreApiMinorVersion) {
            return false;
        }
        return HidlSupport.deepEquals(this.customBinary, ((NanoAppBinary)object).customBinary);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.appId), HidlSupport.deepHashCode(this.appVersion), HidlSupport.deepHashCode(this.flags), HidlSupport.deepHashCode(this.targetChreApiMajorVersion), HidlSupport.deepHashCode(this.targetChreApiMinorVersion), HidlSupport.deepHashCode(this.customBinary));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.appId = hwBlob.getInt64(l + 0L);
        this.appVersion = hwBlob.getInt32(l + 8L);
        this.flags = hwBlob.getInt32(l + 12L);
        this.targetChreApiMajorVersion = hwBlob.getInt8(l + 16L);
        this.targetChreApiMinorVersion = hwBlob.getInt8(l + 17L);
        int n = hwBlob.getInt32(l + 24L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 1, hwBlob.handle(), l + 24L + 0L, true);
        this.customBinary.clear();
        for (int i = 0; i < n; ++i) {
            byte by = ((HwBlob)object).getInt8(i * 1);
            this.customBinary.add(by);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".appId = ");
        stringBuilder.append(this.appId);
        stringBuilder.append(", .appVersion = ");
        stringBuilder.append(this.appVersion);
        stringBuilder.append(", .flags = ");
        stringBuilder.append(NanoAppFlags.dumpBitfield(this.flags));
        stringBuilder.append(", .targetChreApiMajorVersion = ");
        stringBuilder.append(this.targetChreApiMajorVersion);
        stringBuilder.append(", .targetChreApiMinorVersion = ");
        stringBuilder.append(this.targetChreApiMinorVersion);
        stringBuilder.append(", .customBinary = ");
        stringBuilder.append(this.customBinary);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt64(l + 0L, this.appId);
        hwBlob.putInt32(l + 8L, this.appVersion);
        hwBlob.putInt32(l + 12L, this.flags);
        hwBlob.putInt8(16L + l, this.targetChreApiMajorVersion);
        hwBlob.putInt8(17L + l, this.targetChreApiMinorVersion);
        int n = this.customBinary.size();
        hwBlob.putInt32(l + 24L + 8L, n);
        hwBlob.putBool(l + 24L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt8(i * 1, this.customBinary.get(i));
        }
        hwBlob.putBlob(24L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

