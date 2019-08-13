/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.Carrier;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CarrierRestrictionsWithPriority {
    public ArrayList<Carrier> allowedCarriers = new ArrayList();
    public boolean allowedCarriersPrioritized;
    public ArrayList<Carrier> excludedCarriers = new ArrayList();

    public static final ArrayList<CarrierRestrictionsWithPriority> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CarrierRestrictionsWithPriority> arrayList = new ArrayList<CarrierRestrictionsWithPriority>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = new CarrierRestrictionsWithPriority();
            carrierRestrictionsWithPriority.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(carrierRestrictionsWithPriority);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CarrierRestrictionsWithPriority> arrayList) {
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
        if (object.getClass() != CarrierRestrictionsWithPriority.class) {
            return false;
        }
        object = (CarrierRestrictionsWithPriority)object;
        if (!HidlSupport.deepEquals(this.allowedCarriers, ((CarrierRestrictionsWithPriority)object).allowedCarriers)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.excludedCarriers, ((CarrierRestrictionsWithPriority)object).excludedCarriers)) {
            return false;
        }
        return this.allowedCarriersPrioritized == ((CarrierRestrictionsWithPriority)object).allowedCarriersPrioritized;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.allowedCarriers), HidlSupport.deepHashCode(this.excludedCarriers), HidlSupport.deepHashCode(this.allowedCarriersPrioritized));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        Carrier carrier;
        int n2 = hwBlob.getInt32(l + 0L + 8L);
        HwBlob hwBlob2 = hwParcel.readEmbeddedBuffer(n2 * 56, hwBlob.handle(), l + 0L + 0L, true);
        this.allowedCarriers.clear();
        for (n = 0; n < n2; ++n) {
            carrier = new Carrier();
            carrier.readEmbeddedFromParcel(hwParcel, hwBlob2, n * 56);
            this.allowedCarriers.add(carrier);
        }
        n2 = hwBlob.getInt32(l + 16L + 8L);
        hwBlob2 = hwParcel.readEmbeddedBuffer(n2 * 56, hwBlob.handle(), l + 16L + 0L, true);
        this.excludedCarriers.clear();
        for (n = 0; n < n2; ++n) {
            carrier = new Carrier();
            carrier.readEmbeddedFromParcel(hwParcel, hwBlob2, n * 56);
            this.excludedCarriers.add(carrier);
        }
        this.allowedCarriersPrioritized = hwBlob.getBool(l + 32L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".allowedCarriers = ");
        stringBuilder.append(this.allowedCarriers);
        stringBuilder.append(", .excludedCarriers = ");
        stringBuilder.append(this.excludedCarriers);
        stringBuilder.append(", .allowedCarriersPrioritized = ");
        stringBuilder.append(this.allowedCarriersPrioritized);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        int n2 = this.allowedCarriers.size();
        hwBlob.putInt32(l + 0L + 8L, n2);
        hwBlob.putBool(l + 0L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 56);
        for (n = 0; n < n2; ++n) {
            this.allowedCarriers.get(n).writeEmbeddedToBlob(hwBlob2, n * 56);
        }
        hwBlob.putBlob(l + 0L + 0L, hwBlob2);
        n2 = this.excludedCarriers.size();
        hwBlob.putInt32(l + 16L + 8L, n2);
        hwBlob.putBool(l + 16L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 56);
        for (n = 0; n < n2; ++n) {
            this.excludedCarriers.get(n).writeEmbeddedToBlob(hwBlob2, n * 56);
        }
        hwBlob.putBlob(l + 16L + 0L, hwBlob2);
        hwBlob.putBool(l + 32L, this.allowedCarriersPrioritized);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

