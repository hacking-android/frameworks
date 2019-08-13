/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_3;

import android.hardware.radio.V1_0.RadioError;
import android.hardware.radio.V1_0.RadioResponseType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class RadioResponseInfoModem {
    public int error;
    public boolean isEnabled;
    public int serial;
    public int type;

    public static final ArrayList<RadioResponseInfoModem> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioResponseInfoModem> arrayList = new ArrayList<RadioResponseInfoModem>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 16, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            RadioResponseInfoModem radioResponseInfoModem = new RadioResponseInfoModem();
            radioResponseInfoModem.readEmbeddedFromParcel(hwParcel, hwBlob, i * 16);
            arrayList.add(radioResponseInfoModem);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioResponseInfoModem> arrayList) {
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
        if (object.getClass() != RadioResponseInfoModem.class) {
            return false;
        }
        object = (RadioResponseInfoModem)object;
        if (this.type != ((RadioResponseInfoModem)object).type) {
            return false;
        }
        if (this.serial != ((RadioResponseInfoModem)object).serial) {
            return false;
        }
        if (this.error != ((RadioResponseInfoModem)object).error) {
            return false;
        }
        return this.isEnabled == ((RadioResponseInfoModem)object).isEnabled;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.serial), HidlSupport.deepHashCode(this.error), HidlSupport.deepHashCode(this.isEnabled));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.type = hwBlob.getInt32(0L + l);
        this.serial = hwBlob.getInt32(4L + l);
        this.error = hwBlob.getInt32(8L + l);
        this.isEnabled = hwBlob.getBool(12L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(16L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".type = ");
        stringBuilder.append(RadioResponseType.toString(this.type));
        stringBuilder.append(", .serial = ");
        stringBuilder.append(this.serial);
        stringBuilder.append(", .error = ");
        stringBuilder.append(RadioError.toString(this.error));
        stringBuilder.append(", .isEnabled = ");
        stringBuilder.append(this.isEnabled);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.type);
        hwBlob.putInt32(4L + l, this.serial);
        hwBlob.putInt32(8L + l, this.error);
        hwBlob.putBool(12L + l, this.isEnabled);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(16);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

