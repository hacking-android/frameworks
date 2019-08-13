/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.AppStatus;
import android.hardware.radio.V1_0.CardState;
import android.hardware.radio.V1_0.PinState;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class CardStatus {
    public ArrayList<AppStatus> applications = new ArrayList();
    public int cardState;
    public int cdmaSubscriptionAppIndex;
    public int gsmUmtsSubscriptionAppIndex;
    public int imsSubscriptionAppIndex;
    public int universalPinState;

    public static final ArrayList<CardStatus> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<CardStatus> arrayList = new ArrayList<CardStatus>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 40, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            CardStatus cardStatus = new CardStatus();
            cardStatus.readEmbeddedFromParcel(hwParcel, hwBlob, i * 40);
            arrayList.add(cardStatus);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<CardStatus> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 40);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 40);
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
        if (object.getClass() != CardStatus.class) {
            return false;
        }
        object = (CardStatus)object;
        if (this.cardState != ((CardStatus)object).cardState) {
            return false;
        }
        if (this.universalPinState != ((CardStatus)object).universalPinState) {
            return false;
        }
        if (this.gsmUmtsSubscriptionAppIndex != ((CardStatus)object).gsmUmtsSubscriptionAppIndex) {
            return false;
        }
        if (this.cdmaSubscriptionAppIndex != ((CardStatus)object).cdmaSubscriptionAppIndex) {
            return false;
        }
        if (this.imsSubscriptionAppIndex != ((CardStatus)object).imsSubscriptionAppIndex) {
            return false;
        }
        return HidlSupport.deepEquals(this.applications, ((CardStatus)object).applications);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.cardState), HidlSupport.deepHashCode(this.universalPinState), HidlSupport.deepHashCode(this.gsmUmtsSubscriptionAppIndex), HidlSupport.deepHashCode(this.cdmaSubscriptionAppIndex), HidlSupport.deepHashCode(this.imsSubscriptionAppIndex), HidlSupport.deepHashCode(this.applications));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.cardState = hwBlob.getInt32(l + 0L);
        this.universalPinState = hwBlob.getInt32(l + 4L);
        this.gsmUmtsSubscriptionAppIndex = hwBlob.getInt32(l + 8L);
        this.cdmaSubscriptionAppIndex = hwBlob.getInt32(l + 12L);
        this.imsSubscriptionAppIndex = hwBlob.getInt32(l + 16L);
        int n = hwBlob.getInt32(l + 24L + 8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 64, hwBlob.handle(), l + 24L + 0L, true);
        this.applications.clear();
        for (int i = 0; i < n; ++i) {
            AppStatus appStatus = new AppStatus();
            appStatus.readEmbeddedFromParcel(hwParcel, hwBlob, i * 64);
            this.applications.add(appStatus);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(40L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cardState = ");
        stringBuilder.append(CardState.toString(this.cardState));
        stringBuilder.append(", .universalPinState = ");
        stringBuilder.append(PinState.toString(this.universalPinState));
        stringBuilder.append(", .gsmUmtsSubscriptionAppIndex = ");
        stringBuilder.append(this.gsmUmtsSubscriptionAppIndex);
        stringBuilder.append(", .cdmaSubscriptionAppIndex = ");
        stringBuilder.append(this.cdmaSubscriptionAppIndex);
        stringBuilder.append(", .imsSubscriptionAppIndex = ");
        stringBuilder.append(this.imsSubscriptionAppIndex);
        stringBuilder.append(", .applications = ");
        stringBuilder.append(this.applications);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.cardState);
        hwBlob.putInt32(4L + l, this.universalPinState);
        hwBlob.putInt32(l + 8L, this.gsmUmtsSubscriptionAppIndex);
        hwBlob.putInt32(l + 12L, this.cdmaSubscriptionAppIndex);
        hwBlob.putInt32(16L + l, this.imsSubscriptionAppIndex);
        int n = this.applications.size();
        hwBlob.putInt32(l + 24L + 8L, n);
        hwBlob.putBool(l + 24L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 64);
        for (int i = 0; i < n; ++i) {
            this.applications.get(i).writeEmbeddedToBlob(hwBlob2, i * 64);
        }
        hwBlob.putBlob(24L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(40);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

