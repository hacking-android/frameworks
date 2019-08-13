/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.OperatorStatus;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class OperatorInfo {
    public String alphaLong = new String();
    public String alphaShort = new String();
    public String operatorNumeric = new String();
    public int status;

    public static final ArrayList<OperatorInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<OperatorInfo> arrayList = new ArrayList<OperatorInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 56, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            OperatorInfo operatorInfo = new OperatorInfo();
            operatorInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 56);
            arrayList.add(operatorInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<OperatorInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 56);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 56);
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
        if (object.getClass() != OperatorInfo.class) {
            return false;
        }
        object = (OperatorInfo)object;
        if (!HidlSupport.deepEquals(this.alphaLong, ((OperatorInfo)object).alphaLong)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.alphaShort, ((OperatorInfo)object).alphaShort)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.operatorNumeric, ((OperatorInfo)object).operatorNumeric)) {
            return false;
        }
        return this.status == ((OperatorInfo)object).status;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.alphaLong), HidlSupport.deepHashCode(this.alphaShort), HidlSupport.deepHashCode(this.operatorNumeric), HidlSupport.deepHashCode(this.status));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.alphaLong = hwBlob.getString(l + 0L);
        hwParcel.readEmbeddedBuffer(this.alphaLong.getBytes().length + 1, hwBlob.handle(), l + 0L + 0L, false);
        this.alphaShort = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.alphaShort.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.operatorNumeric = hwBlob.getString(l + 32L);
        hwParcel.readEmbeddedBuffer(this.operatorNumeric.getBytes().length + 1, hwBlob.handle(), l + 32L + 0L, false);
        this.status = hwBlob.getInt32(l + 48L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(56L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".alphaLong = ");
        stringBuilder.append(this.alphaLong);
        stringBuilder.append(", .alphaShort = ");
        stringBuilder.append(this.alphaShort);
        stringBuilder.append(", .operatorNumeric = ");
        stringBuilder.append(this.operatorNumeric);
        stringBuilder.append(", .status = ");
        stringBuilder.append(OperatorStatus.toString(this.status));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putString(0L + l, this.alphaLong);
        hwBlob.putString(16L + l, this.alphaShort);
        hwBlob.putString(32L + l, this.operatorNumeric);
        hwBlob.putInt32(48L + l, this.status);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(56);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

