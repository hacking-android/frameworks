/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CdmaSmsSubaddressType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CdmaSmsSubaddress {
    public ArrayList<Byte> digits = new ArrayList();
    public boolean odd;
    public int subaddressType;

    public static final ArrayList<CdmaSmsSubaddress> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CdmaSmsSubaddress> arrayList = new ArrayList<CdmaSmsSubaddress>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new CdmaSmsSubaddress();
            ((CdmaSmsSubaddress)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            arrayList.add((CdmaSmsSubaddress)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CdmaSmsSubaddress> arrayList) {
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
        if (object.getClass() != CdmaSmsSubaddress.class) {
            return false;
        }
        object = (CdmaSmsSubaddress)object;
        if (this.subaddressType != ((CdmaSmsSubaddress)object).subaddressType) {
            return false;
        }
        if (this.odd != ((CdmaSmsSubaddress)object).odd) {
            return false;
        }
        return HidlSupport.deepEquals(this.digits, ((CdmaSmsSubaddress)object).digits);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.subaddressType), HidlSupport.deepHashCode(this.odd), HidlSupport.deepHashCode(this.digits));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.subaddressType = hwBlob.getInt32(l + 0L);
        this.odd = hwBlob.getBool(l + 4L);
        int n = hwBlob.getInt32(l + 8L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n * 1, hwBlob.handle(), l + 8L + 0L, true);
        this.digits.clear();
        for (int i = 0; i < n; ++i) {
            byte by = ((HwBlob)object).getInt8(i * 1);
            this.digits.add(by);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(24L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".subaddressType = ");
        stringBuilder.append(CdmaSmsSubaddressType.toString(this.subaddressType));
        stringBuilder.append(", .odd = ");
        stringBuilder.append(this.odd);
        stringBuilder.append(", .digits = ");
        stringBuilder.append(this.digits);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.subaddressType);
        hwBlob.putBool(4L + l, this.odd);
        int n = this.digits.size();
        hwBlob.putInt32(l + 8L + 8L, n);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 1);
        for (int i = 0; i < n; ++i) {
            hwBlob2.putInt8(i * 1, this.digits.get(i));
        }
        hwBlob.putBlob(8L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(24);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

