/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class HardwareConfigModem {
    public int maxData;
    public int maxStandby;
    public int maxVoice;
    public int rat;
    public int rilModel;

    public static final ArrayList<HardwareConfigModem> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<HardwareConfigModem> arrayList = new ArrayList<HardwareConfigModem>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 20, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            HardwareConfigModem hardwareConfigModem = new HardwareConfigModem();
            hardwareConfigModem.readEmbeddedFromParcel(hwParcel, hwBlob, i * 20);
            arrayList.add(hardwareConfigModem);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<HardwareConfigModem> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 20);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 20);
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
        if (object.getClass() != HardwareConfigModem.class) {
            return false;
        }
        object = (HardwareConfigModem)object;
        if (this.rilModel != ((HardwareConfigModem)object).rilModel) {
            return false;
        }
        if (this.rat != ((HardwareConfigModem)object).rat) {
            return false;
        }
        if (this.maxVoice != ((HardwareConfigModem)object).maxVoice) {
            return false;
        }
        if (this.maxData != ((HardwareConfigModem)object).maxData) {
            return false;
        }
        return this.maxStandby == ((HardwareConfigModem)object).maxStandby;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.rilModel), HidlSupport.deepHashCode(this.rat), HidlSupport.deepHashCode(this.maxVoice), HidlSupport.deepHashCode(this.maxData), HidlSupport.deepHashCode(this.maxStandby));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.rilModel = hwBlob.getInt32(0L + l);
        this.rat = hwBlob.getInt32(4L + l);
        this.maxVoice = hwBlob.getInt32(8L + l);
        this.maxData = hwBlob.getInt32(12L + l);
        this.maxStandby = hwBlob.getInt32(16L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(20L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".rilModel = ");
        stringBuilder.append(this.rilModel);
        stringBuilder.append(", .rat = ");
        stringBuilder.append(this.rat);
        stringBuilder.append(", .maxVoice = ");
        stringBuilder.append(this.maxVoice);
        stringBuilder.append(", .maxData = ");
        stringBuilder.append(this.maxData);
        stringBuilder.append(", .maxStandby = ");
        stringBuilder.append(this.maxStandby);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.rilModel);
        hwBlob.putInt32(4L + l, this.rat);
        hwBlob.putInt32(8L + l, this.maxVoice);
        hwBlob.putInt32(12L + l, this.maxData);
        hwBlob.putInt32(16L + l, this.maxStandby);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(20);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

