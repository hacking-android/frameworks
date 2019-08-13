/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaSmsAddress;
import android.hardware.radio.V1_0.CdmaSmsSubaddress;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaSmsMessage {
    public CdmaSmsAddress address = new CdmaSmsAddress();
    public ArrayList<Byte> bearerData = new ArrayList();
    public boolean isServicePresent;
    public int serviceCategory;
    public CdmaSmsSubaddress subAddress = new CdmaSmsSubaddress();
    public int teleserviceId;

    public static final ArrayList<CdmaSmsMessage> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsMessage> arrayList = new ArrayList<CdmaSmsMessage>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 88, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaSmsMessage();
            ((CdmaSmsMessage)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add((CdmaSmsMessage)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsMessage> arrayList) {
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
        if (object.getClass() != CdmaSmsMessage.class) {
            return false;
        }
        object = (CdmaSmsMessage)object;
        if (this.teleserviceId != ((CdmaSmsMessage)object).teleserviceId) {
            return false;
        }
        if (this.isServicePresent != ((CdmaSmsMessage)object).isServicePresent) {
            return false;
        }
        if (this.serviceCategory != ((CdmaSmsMessage)object).serviceCategory) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.address, ((CdmaSmsMessage)object).address)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.subAddress, ((CdmaSmsMessage)object).subAddress)) {
            return false;
        }
        return HidlSupport.deepEquals(this.bearerData, ((CdmaSmsMessage)object).bearerData);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.teleserviceId), HidlSupport.deepHashCode(this.isServicePresent), HidlSupport.deepHashCode(this.serviceCategory), HidlSupport.deepHashCode(this.address), HidlSupport.deepHashCode(this.subAddress), HidlSupport.deepHashCode(this.bearerData));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.teleserviceId = hwBlob.getInt32(l + 0L);
        this.isServicePresent = hwBlob.getBool(l + 4L);
        this.serviceCategory = hwBlob.getInt32(l + 8L);
        this.address.readEmbeddedFromParcel((HwParcel)object, hwBlob, l + 16L);
        this.subAddress.readEmbeddedFromParcel((HwParcel)object, hwBlob, l + 48L);
        int n = hwBlob.getInt32(l + 72L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 1, hwBlob.handle(), l + 72L + 0L, true);
        this.bearerData.clear();
        for (int i = 0; i < n; ++i) {
            byte by = ((HwBlob)object).getInt8(i * 1);
            this.bearerData.add(by);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".teleserviceId = ");
        stringBuilder.append(this.teleserviceId);
        stringBuilder.append(", .isServicePresent = ");
        stringBuilder.append(this.isServicePresent);
        stringBuilder.append(", .serviceCategory = ");
        stringBuilder.append(this.serviceCategory);
        stringBuilder.append(", .address = ");
        stringBuilder.append(this.address);
        stringBuilder.append(", .subAddress = ");
        stringBuilder.append(this.subAddress);
        stringBuilder.append(", .bearerData = ");
        stringBuilder.append(this.bearerData);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.teleserviceId);
        hwBlob.putBool(4L + l, this.isServicePresent);
        hwBlob.putInt32(l + 8L, this.serviceCategory);
        this.address.writeEmbeddedToBlob(hwBlob, 16L + l);
        this.subAddress.writeEmbeddedToBlob(hwBlob, 48L + l);
        int n = this.bearerData.size();
        hwBlob.putInt32(l + 72L + 8L, n);
        hwBlob.putBool(l + 72L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt8(i * 1, this.bearerData.get(i));
        }
        hwBlob.putBlob(72L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

