/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_4.FrequencyRange;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class RadioFrequencyInfo {
    private byte hidl_d = (byte)(false ? 1 : 0);
    private Object hidl_o = null;

    public static final ArrayList<RadioFrequencyInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioFrequencyInfo> arrayList = new ArrayList<RadioFrequencyInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 8, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            RadioFrequencyInfo radioFrequencyInfo = new RadioFrequencyInfo();
            radioFrequencyInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 8);
            arrayList.add(radioFrequencyInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioFrequencyInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 8);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 8);
        }
        hwBlob.putBlob(0L, hwBlob2);
        hwParcel.writeBuffer(hwBlob);
    }

    public int channelNumber() {
        if (this.hidl_d != 1) {
            Object object = this.hidl_o;
            object = object != null ? object.getClass().getName() : "null";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Read access to inactive union components is disallowed. Discriminator value is ");
            stringBuilder.append(this.hidl_d);
            stringBuilder.append(" (corresponding to ");
            stringBuilder.append(hidl_discriminator.getName(this.hidl_d));
            stringBuilder.append("), and hidl_o is of type ");
            stringBuilder.append((String)object);
            stringBuilder.append(".");
            throw new IllegalStateException(stringBuilder.toString());
        }
        Object object = this.hidl_o;
        if (object != null && !Integer.class.isInstance(object)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (Integer)this.hidl_o;
    }

    public void channelNumber(int n) {
        this.hidl_d = (byte)(true ? 1 : 0);
        this.hidl_o = n;
    }

    public final boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != RadioFrequencyInfo.class) {
            return false;
        }
        object = (RadioFrequencyInfo)object;
        if (this.hidl_d != ((RadioFrequencyInfo)object).hidl_d) {
            return false;
        }
        return HidlSupport.deepEquals(this.hidl_o, ((RadioFrequencyInfo)object).hidl_o);
    }

    public byte getDiscriminator() {
        return this.hidl_d;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.hidl_o), Objects.hashCode(this.hidl_d));
    }

    public int range() {
        if (this.hidl_d != 0) {
            Object object = this.hidl_o;
            object = object != null ? object.getClass().getName() : "null";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Read access to inactive union components is disallowed. Discriminator value is ");
            stringBuilder.append(this.hidl_d);
            stringBuilder.append(" (corresponding to ");
            stringBuilder.append(hidl_discriminator.getName(this.hidl_d));
            stringBuilder.append("), and hidl_o is of type ");
            stringBuilder.append((String)object);
            stringBuilder.append(".");
            throw new IllegalStateException(stringBuilder.toString());
        }
        Object object = this.hidl_o;
        if (object != null && !Integer.class.isInstance(object)) {
            throw new Error("Union is in a corrupted state.");
        }
        return (Integer)this.hidl_o;
    }

    public void range(int n) {
        this.hidl_d = (byte)(false ? 1 : 0);
        this.hidl_o = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        this.hidl_d = hwBlob.getInt8(0L + l);
        byte by = this.hidl_d;
        if (by == 0) {
            this.hidl_o = hwBlob.getInt32(4L + l);
            return;
        }
        if (by == 1) {
            this.hidl_o = hwBlob.getInt32(4L + l);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown union discriminator (value: ");
        ((StringBuilder)object).append(this.hidl_d);
        ((StringBuilder)object).append(").");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(8L), 0L);
    }

    /*
     * Enabled aggressive block sorting
     */
    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        byte by = this.hidl_d;
        if (by != 0) {
            if (by != 1) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown union discriminator (value: ");
                stringBuilder.append(this.hidl_d);
                stringBuilder.append(").");
                throw new Error(stringBuilder.toString());
            }
            stringBuilder.append(".channelNumber = ");
            stringBuilder.append(this.channelNumber());
        } else {
            stringBuilder.append(".range = ");
            stringBuilder.append(FrequencyRange.toString(this.range()));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeEmbeddedToBlob(HwBlob object, long l) {
        ((HwBlob)object).putInt8(0L + l, this.hidl_d);
        byte by = this.hidl_d;
        if (by == 0) {
            ((HwBlob)object).putInt32(4L + l, this.range());
            return;
        }
        if (by == 1) {
            ((HwBlob)object).putInt32(4L + l, this.channelNumber());
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown union discriminator (value: ");
        ((StringBuilder)object).append(this.hidl_d);
        ((StringBuilder)object).append(").");
        throw new Error(((StringBuilder)object).toString());
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(8);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }

    public static final class hidl_discriminator {
        public static final byte channelNumber = 1;
        public static final byte range = 0;

        private hidl_discriminator() {
        }

        public static final String getName(byte by) {
            if (by != 0) {
                if (by != 1) {
                    return "Unknown";
                }
                return "channelNumber";
            }
            return "range";
        }
    }

}

