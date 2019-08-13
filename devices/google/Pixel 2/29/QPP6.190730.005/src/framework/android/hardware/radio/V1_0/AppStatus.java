/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.AppState;
import android.hardware.radio.V1_0.AppType;
import android.hardware.radio.V1_0.PersoSubstate;
import android.hardware.radio.V1_0.PinState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class AppStatus {
    public String aidPtr = new String();
    public String appLabelPtr = new String();
    public int appState;
    public int appType;
    public int persoSubstate;
    public int pin1;
    public int pin1Replaced;
    public int pin2;

    public static final ArrayList<AppStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<AppStatus> arrayList = new ArrayList<AppStatus>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 64, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new AppStatus();
            ((AppStatus)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 64);
            arrayList.add((AppStatus)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<AppStatus> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 64);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
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
        if (object.getClass() != AppStatus.class) {
            return false;
        }
        object = (AppStatus)object;
        if (this.appType != ((AppStatus)object).appType) {
            return false;
        }
        if (this.appState != ((AppStatus)object).appState) {
            return false;
        }
        if (this.persoSubstate != ((AppStatus)object).persoSubstate) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.aidPtr, ((AppStatus)object).aidPtr)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.appLabelPtr, ((AppStatus)object).appLabelPtr)) {
            return false;
        }
        if (this.pin1Replaced != ((AppStatus)object).pin1Replaced) {
            return false;
        }
        if (this.pin1 != ((AppStatus)object).pin1) {
            return false;
        }
        return this.pin2 == ((AppStatus)object).pin2;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.appType), HidlSupport.deepHashCode(this.appState), HidlSupport.deepHashCode(this.persoSubstate), HidlSupport.deepHashCode(this.aidPtr), HidlSupport.deepHashCode(this.appLabelPtr), HidlSupport.deepHashCode(this.pin1Replaced), HidlSupport.deepHashCode(this.pin1), HidlSupport.deepHashCode(this.pin2));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.appType = hwBlob.getInt32(l + 0L);
        this.appState = hwBlob.getInt32(l + 4L);
        this.persoSubstate = hwBlob.getInt32(l + 8L);
        this.aidPtr = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.aidPtr.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.appLabelPtr = hwBlob.getString(l + 32L);
        hwParcel.readEmbeddedBuffer(this.appLabelPtr.getBytes().length + 1, hwBlob.handle(), l + 32L + 0L, false);
        this.pin1Replaced = hwBlob.getInt32(l + 48L);
        this.pin1 = hwBlob.getInt32(l + 52L);
        this.pin2 = hwBlob.getInt32(l + 56L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(64L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".appType = ");
        stringBuilder.append(AppType.toString(this.appType));
        stringBuilder.append(", .appState = ");
        stringBuilder.append(AppState.toString(this.appState));
        stringBuilder.append(", .persoSubstate = ");
        stringBuilder.append(PersoSubstate.toString(this.persoSubstate));
        stringBuilder.append(", .aidPtr = ");
        stringBuilder.append(this.aidPtr);
        stringBuilder.append(", .appLabelPtr = ");
        stringBuilder.append(this.appLabelPtr);
        stringBuilder.append(", .pin1Replaced = ");
        stringBuilder.append(this.pin1Replaced);
        stringBuilder.append(", .pin1 = ");
        stringBuilder.append(PinState.toString(this.pin1));
        stringBuilder.append(", .pin2 = ");
        stringBuilder.append(PinState.toString(this.pin2));
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.appType);
        hwBlob.putInt32(4L + l, this.appState);
        hwBlob.putInt32(8L + l, this.persoSubstate);
        hwBlob.putString(16L + l, this.aidPtr);
        hwBlob.putString(32L + l, this.appLabelPtr);
        hwBlob.putInt32(48L + l, this.pin1Replaced);
        hwBlob.putInt32(52L + l, this.pin1);
        hwBlob.putInt32(56L + l, this.pin2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(64);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

