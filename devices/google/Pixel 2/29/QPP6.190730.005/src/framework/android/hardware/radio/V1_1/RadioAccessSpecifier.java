/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import android.hardware.radio.V1_1.RadioAccessNetworks;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class RadioAccessSpecifier {
    public ArrayList<Integer> channels = new ArrayList();
    public ArrayList<Integer> eutranBands = new ArrayList();
    public ArrayList<Integer> geranBands = new ArrayList();
    public int radioAccessNetwork;
    public ArrayList<Integer> utranBands = new ArrayList();

    public static final ArrayList<RadioAccessSpecifier> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<RadioAccessSpecifier> arrayList = new ArrayList<RadioAccessSpecifier>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 72, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            RadioAccessSpecifier radioAccessSpecifier = new RadioAccessSpecifier();
            radioAccessSpecifier.readEmbeddedFromParcel(hwParcel, hwBlob, i * 72);
            arrayList.add(radioAccessSpecifier);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<RadioAccessSpecifier> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 72);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 72);
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
        if (object.getClass() != RadioAccessSpecifier.class) {
            return false;
        }
        object = (RadioAccessSpecifier)object;
        if (this.radioAccessNetwork != ((RadioAccessSpecifier)object).radioAccessNetwork) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.geranBands, ((RadioAccessSpecifier)object).geranBands)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.utranBands, ((RadioAccessSpecifier)object).utranBands)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.eutranBands, ((RadioAccessSpecifier)object).eutranBands)) {
            return false;
        }
        return HidlSupport.deepEquals(this.channels, ((RadioAccessSpecifier)object).channels);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.radioAccessNetwork), HidlSupport.deepHashCode(this.geranBands), HidlSupport.deepHashCode(this.utranBands), HidlSupport.deepHashCode(this.eutranBands), HidlSupport.deepHashCode(this.channels));
    }

    public final void readEmbeddedFromParcel(HwParcel object, HwBlob hwBlob, long l) {
        int n;
        int n2;
        this.radioAccessNetwork = hwBlob.getInt32(l + 0L);
        int n3 = hwBlob.getInt32(l + 8L + 8L);
        HwBlob hwBlob2 = ((HwParcel)object).readEmbeddedBuffer(n3 * 4, hwBlob.handle(), l + 8L + 0L, true);
        this.geranBands.clear();
        for (n2 = 0; n2 < n3; ++n2) {
            n = hwBlob2.getInt32(n2 * 4);
            this.geranBands.add(n);
        }
        n3 = hwBlob.getInt32(l + 24L + 8L);
        hwBlob2 = ((HwParcel)object).readEmbeddedBuffer(n3 * 4, hwBlob.handle(), l + 24L + 0L, true);
        this.utranBands.clear();
        for (n2 = 0; n2 < n3; ++n2) {
            n = hwBlob2.getInt32(n2 * 4);
            this.utranBands.add(n);
        }
        n3 = hwBlob.getInt32(l + 40L + 8L);
        hwBlob2 = ((HwParcel)object).readEmbeddedBuffer(n3 * 4, hwBlob.handle(), l + 40L + 0L, true);
        this.eutranBands.clear();
        for (n2 = 0; n2 < n3; ++n2) {
            n = hwBlob2.getInt32(n2 * 4);
            this.eutranBands.add(n);
        }
        n3 = hwBlob.getInt32(l + 56L + 8L);
        object = ((HwParcel)object).readEmbeddedBuffer(n3 * 4, hwBlob.handle(), l + 56L + 0L, true);
        this.channels.clear();
        for (n2 = 0; n2 < n3; ++n2) {
            n = ((HwBlob)object).getInt32(n2 * 4);
            this.channels.add(n);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(72L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".radioAccessNetwork = ");
        stringBuilder.append(RadioAccessNetworks.toString(this.radioAccessNetwork));
        stringBuilder.append(", .geranBands = ");
        stringBuilder.append(this.geranBands);
        stringBuilder.append(", .utranBands = ");
        stringBuilder.append(this.utranBands);
        stringBuilder.append(", .eutranBands = ");
        stringBuilder.append(this.eutranBands);
        stringBuilder.append(", .channels = ");
        stringBuilder.append(this.channels);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        int n;
        hwBlob.putInt32(l + 0L, this.radioAccessNetwork);
        int n2 = this.geranBands.size();
        hwBlob.putInt32(l + 8L + 8L, n2);
        hwBlob.putBool(l + 8L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 4);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putInt32(n * 4, this.geranBands.get(n));
        }
        hwBlob.putBlob(l + 8L + 0L, hwBlob2);
        n2 = this.utranBands.size();
        hwBlob.putInt32(l + 24L + 8L, n2);
        hwBlob.putBool(l + 24L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 4);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putInt32(n * 4, this.utranBands.get(n));
        }
        hwBlob.putBlob(l + 24L + 0L, hwBlob2);
        n2 = this.eutranBands.size();
        hwBlob.putInt32(l + 40L + 8L, n2);
        hwBlob.putBool(l + 40L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 4);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putInt32(n * 4, this.eutranBands.get(n));
        }
        hwBlob.putBlob(l + 40L + 0L, hwBlob2);
        n2 = this.channels.size();
        hwBlob.putInt32(l + 56L + 8L, n2);
        hwBlob.putBool(l + 56L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 4);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putInt32(n * 4, this.channels.get(n));
        }
        hwBlob.putBlob(l + 56L + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(72);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

