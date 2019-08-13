/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class NrIndicators {
    public boolean isDcNrRestricted;
    public boolean isEndcAvailable;
    public boolean isNrAvailable;

    public static final ArrayList<NrIndicators> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<NrIndicators> arrayList = new ArrayList<NrIndicators>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 3, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            NrIndicators nrIndicators = new NrIndicators();
            nrIndicators.readEmbeddedFromParcel(hwParcel, hwBlob, i * 3);
            arrayList.add(nrIndicators);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<NrIndicators> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 3);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 3);
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
        if (object.getClass() != NrIndicators.class) {
            return false;
        }
        object = (NrIndicators)object;
        if (this.isEndcAvailable != ((NrIndicators)object).isEndcAvailable) {
            return false;
        }
        if (this.isDcNrRestricted != ((NrIndicators)object).isDcNrRestricted) {
            return false;
        }
        return this.isNrAvailable == ((NrIndicators)object).isNrAvailable;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.isEndcAvailable), HidlSupport.deepHashCode(this.isDcNrRestricted), HidlSupport.deepHashCode(this.isNrAvailable));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.isEndcAvailable = hwBlob.getBool(0L + l);
        this.isDcNrRestricted = hwBlob.getBool(1L + l);
        this.isNrAvailable = hwBlob.getBool(2L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(3L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".isEndcAvailable = ");
        stringBuilder.append(this.isEndcAvailable);
        stringBuilder.append(", .isDcNrRestricted = ");
        stringBuilder.append(this.isDcNrRestricted);
        stringBuilder.append(", .isNrAvailable = ");
        stringBuilder.append(this.isNrAvailable);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putBool(0L + l, this.isEndcAvailable);
        hwBlob.putBool(1L + l, this.isDcNrRestricted);
        hwBlob.putBool(2L + l, this.isNrAvailable);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(3);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

