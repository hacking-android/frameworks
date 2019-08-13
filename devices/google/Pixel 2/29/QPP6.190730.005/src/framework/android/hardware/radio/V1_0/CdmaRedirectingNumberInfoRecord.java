/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaNumberInfoRecord;
import android.hardware.radio.V1_0.CdmaRedirectingReason;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaRedirectingNumberInfoRecord {
    public CdmaNumberInfoRecord redirectingNumber = new CdmaNumberInfoRecord();
    public int redirectingReason;

    public static final ArrayList<CdmaRedirectingNumberInfoRecord> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaRedirectingNumberInfoRecord> arrayList = new ArrayList<CdmaRedirectingNumberInfoRecord>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 32, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaRedirectingNumberInfoRecord();
            ((CdmaRedirectingNumberInfoRecord)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add((CdmaRedirectingNumberInfoRecord)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaRedirectingNumberInfoRecord> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 32);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 32);
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
        if (object.getClass() != CdmaRedirectingNumberInfoRecord.class) {
            return false;
        }
        object = (CdmaRedirectingNumberInfoRecord)object;
        if (!HidlSupport.deepEquals(this.redirectingNumber, ((CdmaRedirectingNumberInfoRecord)object).redirectingNumber)) {
            return false;
        }
        return this.redirectingReason == ((CdmaRedirectingNumberInfoRecord)object).redirectingReason;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.redirectingNumber), HidlSupport.deepHashCode(this.redirectingReason));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.redirectingNumber.readEmbeddedFromParcel(hwParcel, hwBlob, 0L + l);
        this.redirectingReason = hwBlob.getInt32(24L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".redirectingNumber = ");
        stringBuilder.append(this.redirectingNumber);
        stringBuilder.append(", .redirectingReason = ");
        stringBuilder.append(CdmaRedirectingReason.toString(this.redirectingReason));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        this.redirectingNumber.writeEmbeddedToBlob(hwBlob, 0L + l);
        hwBlob.putInt32(24L + l, this.redirectingReason);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

