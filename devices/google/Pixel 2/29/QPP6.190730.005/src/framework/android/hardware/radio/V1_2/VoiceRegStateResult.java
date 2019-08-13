/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_2;

import android.hardware.radio.V1_0.RegState;
import android.hardware.radio.V1_2.CellIdentity;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class VoiceRegStateResult {
    public CellIdentity cellIdentity = new CellIdentity();
    public boolean cssSupported;
    public int defaultRoamingIndicator;
    public int rat;
    public int reasonForDenial;
    public int regState;
    public int roamingIndicator;
    public int systemIsInPrl;

    public static final ArrayList<VoiceRegStateResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<VoiceRegStateResult> arrayList = new ArrayList<VoiceRegStateResult>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 120, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new VoiceRegStateResult();
            ((VoiceRegStateResult)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 120);
            arrayList.add((VoiceRegStateResult)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<VoiceRegStateResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 120);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 120);
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
        if (object.getClass() != VoiceRegStateResult.class) {
            return false;
        }
        object = (VoiceRegStateResult)object;
        if (this.regState != ((VoiceRegStateResult)object).regState) {
            return false;
        }
        if (this.rat != ((VoiceRegStateResult)object).rat) {
            return false;
        }
        if (this.cssSupported != ((VoiceRegStateResult)object).cssSupported) {
            return false;
        }
        if (this.roamingIndicator != ((VoiceRegStateResult)object).roamingIndicator) {
            return false;
        }
        if (this.systemIsInPrl != ((VoiceRegStateResult)object).systemIsInPrl) {
            return false;
        }
        if (this.defaultRoamingIndicator != ((VoiceRegStateResult)object).defaultRoamingIndicator) {
            return false;
        }
        if (this.reasonForDenial != ((VoiceRegStateResult)object).reasonForDenial) {
            return false;
        }
        return HidlSupport.deepEquals(this.cellIdentity, ((VoiceRegStateResult)object).cellIdentity);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.regState), HidlSupport.deepHashCode(this.rat), HidlSupport.deepHashCode(this.cssSupported), HidlSupport.deepHashCode(this.roamingIndicator), HidlSupport.deepHashCode(this.systemIsInPrl), HidlSupport.deepHashCode(this.defaultRoamingIndicator), HidlSupport.deepHashCode(this.reasonForDenial), HidlSupport.deepHashCode(this.cellIdentity));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.regState = hwBlob.getInt32(0L + l);
        this.rat = hwBlob.getInt32(4L + l);
        this.cssSupported = hwBlob.getBool(8L + l);
        this.roamingIndicator = hwBlob.getInt32(12L + l);
        this.systemIsInPrl = hwBlob.getInt32(16L + l);
        this.defaultRoamingIndicator = hwBlob.getInt32(20L + l);
        this.reasonForDenial = hwBlob.getInt32(24L + l);
        this.cellIdentity.readEmbeddedFromParcel(hwParcel, hwBlob, 32L + l);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".regState = ");
        stringBuilder.append(RegState.toString(this.regState));
        stringBuilder.append(", .rat = ");
        stringBuilder.append(this.rat);
        stringBuilder.append(", .cssSupported = ");
        stringBuilder.append(this.cssSupported);
        stringBuilder.append(", .roamingIndicator = ");
        stringBuilder.append(this.roamingIndicator);
        stringBuilder.append(", .systemIsInPrl = ");
        stringBuilder.append(this.systemIsInPrl);
        stringBuilder.append(", .defaultRoamingIndicator = ");
        stringBuilder.append(this.defaultRoamingIndicator);
        stringBuilder.append(", .reasonForDenial = ");
        stringBuilder.append(this.reasonForDenial);
        stringBuilder.append(", .cellIdentity = ");
        stringBuilder.append(this.cellIdentity);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.regState);
        hwBlob.putInt32(4L + l, this.rat);
        hwBlob.putBool(8L + l, this.cssSupported);
        hwBlob.putInt32(12L + l, this.roamingIndicator);
        hwBlob.putInt32(16L + l, this.systemIsInPrl);
        hwBlob.putInt32(20L + l, this.defaultRoamingIndicator);
        hwBlob.putInt32(24L + l, this.reasonForDenial);
        this.cellIdentity.writeEmbeddedToBlob(hwBlob, 32L + l);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

