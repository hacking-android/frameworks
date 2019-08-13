/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaSmsDigitMode;
import android.hardware.radio.V1_0.CdmaSmsNumberMode;
import android.hardware.radio.V1_0.CdmaSmsNumberPlan;
import android.hardware.radio.V1_0.CdmaSmsNumberType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaSmsAddress {
    public int digitMode;
    public ArrayList<Byte> digits = new ArrayList();
    public int numberMode;
    public int numberPlan;
    public int numberType;

    public static final ArrayList<CdmaSmsAddress> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsAddress> arrayList = new ArrayList<CdmaSmsAddress>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 32, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CdmaSmsAddress cdmaSmsAddress = new CdmaSmsAddress();
            cdmaSmsAddress.readEmbeddedFromParcel(hwParcel, hwBlob, i * 32);
            arrayList.add(cdmaSmsAddress);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsAddress> arrayList) {
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
        if (object.getClass() != CdmaSmsAddress.class) {
            return false;
        }
        object = (CdmaSmsAddress)object;
        if (this.digitMode != ((CdmaSmsAddress)object).digitMode) {
            return false;
        }
        if (this.numberMode != ((CdmaSmsAddress)object).numberMode) {
            return false;
        }
        if (this.numberType != ((CdmaSmsAddress)object).numberType) {
            return false;
        }
        if (this.numberPlan != ((CdmaSmsAddress)object).numberPlan) {
            return false;
        }
        return HidlSupport.deepEquals(this.digits, ((CdmaSmsAddress)object).digits);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.digitMode), HidlSupport.deepHashCode(this.numberMode), HidlSupport.deepHashCode(this.numberType), HidlSupport.deepHashCode(this.numberPlan), HidlSupport.deepHashCode(this.digits));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.digitMode = hwBlob.getInt32(l + 0L);
        this.numberMode = hwBlob.getInt32(l + 4L);
        this.numberType = hwBlob.getInt32(l + 8L);
        this.numberPlan = hwBlob.getInt32(l + 12L);
        int n = hwBlob.getInt32(l + 16L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 1, hwBlob.handle(), l + 16L + 0L, true);
        this.digits.clear();
        for (int i = 0; i < n; ++i) {
            byte by = ((HwBlob)object).getInt8(i * 1);
            this.digits.add(by);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(32L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".digitMode = ");
        stringBuilder.append(CdmaSmsDigitMode.toString(this.digitMode));
        stringBuilder.append(", .numberMode = ");
        stringBuilder.append(CdmaSmsNumberMode.toString(this.numberMode));
        stringBuilder.append(", .numberType = ");
        stringBuilder.append(CdmaSmsNumberType.toString(this.numberType));
        stringBuilder.append(", .numberPlan = ");
        stringBuilder.append(CdmaSmsNumberPlan.toString(this.numberPlan));
        stringBuilder.append(", .digits = ");
        stringBuilder.append(this.digits);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.digitMode);
        hwBlob.putInt32(4L + l, this.numberMode);
        hwBlob.putInt32(l + 8L, this.numberType);
        hwBlob.putInt32(l + 12L, this.numberPlan);
        int n = this.digits.size();
        hwBlob.putInt32(l + 16L + 8L, n);
        hwBlob.putBool(l + 16L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt8(i * 1, this.digits.get(i));
        }
        hwBlob.putBlob(16L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(32);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

