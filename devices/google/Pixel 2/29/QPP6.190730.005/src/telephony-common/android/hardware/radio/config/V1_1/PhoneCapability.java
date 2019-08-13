/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.HidlSupport
 *  android.os.HwBlob
 *  android.os.HwParcel
 */
package android.hardware.radio.config.V1_1;

import android.hardware.radio.config.V1_1.ModemInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class PhoneCapability {
    public boolean isInternetLingeringSupported;
    public ArrayList<ModemInfo> logicalModemList = new ArrayList();
    public byte maxActiveData;
    public byte maxActiveInternetData;

    public static final ArrayList<PhoneCapability> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<PhoneCapability> arrayList = new ArrayList<PhoneCapability>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer((long)(n * 24), hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            PhoneCapability phoneCapability = new PhoneCapability();
            phoneCapability.readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add(phoneCapability);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<PhoneCapability> arrayList) {
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
        if (object.getClass() != PhoneCapability.class) {
            return false;
        }
        object = (PhoneCapability)object;
        if (this.maxActiveData != ((PhoneCapability)object).maxActiveData) {
            return false;
        }
        if (this.maxActiveInternetData != ((PhoneCapability)object).maxActiveInternetData) {
            return false;
        }
        if (this.isInternetLingeringSupported != ((PhoneCapability)object).isInternetLingeringSupported) {
            return false;
        }
        return HidlSupport.deepEquals(this.logicalModemList, ((PhoneCapability)object).logicalModemList);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode((Object)this.maxActiveData), HidlSupport.deepHashCode((Object)this.maxActiveInternetData), HidlSupport.deepHashCode((Object)this.isInternetLingeringSupported), HidlSupport.deepHashCode(this.logicalModemList));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob object, long l) {
        this.maxActiveData = object.getInt8(l + 0L);
        this.maxActiveInternetData = object.getInt8(l + 1L);
        this.isInternetLingeringSupported = object.getBool(l + 2L);
        int n = object.getInt32(l + 8L + 8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer((long)(n * 1), object.handle(), l + 8L + 0L, true);
        this.logicalModemList.clear();
        for (int i = 0; i < n; ++i) {
            object = new ModemInfo();
            ((ModemInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 1);
            this.logicalModemList.add((ModemInfo)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".maxActiveData = ");
        stringBuilder.append(this.maxActiveData);
        stringBuilder.append(", .maxActiveInternetData = ");
        stringBuilder.append(this.maxActiveInternetData);
        stringBuilder.append(", .isInternetLingeringSupported = ");
        stringBuilder.append(this.isInternetLingeringSupported);
        stringBuilder.append(", .logicalModemList = ");
        stringBuilder.append(this.logicalModemList);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt8(l + 0L, this.maxActiveData);
        hwBlob.putInt8(1L + l, this.maxActiveInternetData);
        hwBlob.putBool(2L + l, this.isInternetLingeringSupported);
        int n = this.logicalModemList.size();
        hwBlob.putInt32(l + 8L + 8L, n);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            this.logicalModemList.get(i).writeEmbeddedToBlob(hwBlob2, i * 1);
        }
        hwBlob.putBlob(8L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

