/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.CallPresentation;
import android.hardware.radio.V1_0.CallState;
import android.hardware.radio.V1_0.UusInfo;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class Call {
    public byte als;
    public int index;
    public boolean isMT;
    public boolean isMpty;
    public boolean isVoice;
    public boolean isVoicePrivacy;
    public String name = new String();
    public int namePresentation;
    public String number = new String();
    public int numberPresentation;
    public int state;
    public int toa;
    public ArrayList<UusInfo> uusInfo = new ArrayList();

    public static final ArrayList<Call> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<Call> arrayList = new ArrayList<Call>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 88, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            Call call = new Call();
            call.readEmbeddedFromParcel(hwParcel, hwBlob, i * 88);
            arrayList.add(call);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<Call> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 88);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 88);
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
        if (object.getClass() != Call.class) {
            return false;
        }
        object = (Call)object;
        if (this.state != ((Call)object).state) {
            return false;
        }
        if (this.index != ((Call)object).index) {
            return false;
        }
        if (this.toa != ((Call)object).toa) {
            return false;
        }
        if (this.isMpty != ((Call)object).isMpty) {
            return false;
        }
        if (this.isMT != ((Call)object).isMT) {
            return false;
        }
        if (this.als != ((Call)object).als) {
            return false;
        }
        if (this.isVoice != ((Call)object).isVoice) {
            return false;
        }
        if (this.isVoicePrivacy != ((Call)object).isVoicePrivacy) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.number, ((Call)object).number)) {
            return false;
        }
        if (this.numberPresentation != ((Call)object).numberPresentation) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.name, ((Call)object).name)) {
            return false;
        }
        if (this.namePresentation != ((Call)object).namePresentation) {
            return false;
        }
        return HidlSupport.deepEquals(this.uusInfo, ((Call)object).uusInfo);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.state), HidlSupport.deepHashCode(this.index), HidlSupport.deepHashCode(this.toa), HidlSupport.deepHashCode(this.isMpty), HidlSupport.deepHashCode(this.isMT), HidlSupport.deepHashCode(this.als), HidlSupport.deepHashCode(this.isVoice), HidlSupport.deepHashCode(this.isVoicePrivacy), HidlSupport.deepHashCode(this.number), HidlSupport.deepHashCode(this.numberPresentation), HidlSupport.deepHashCode(this.name), HidlSupport.deepHashCode(this.namePresentation), HidlSupport.deepHashCode(this.uusInfo));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob object, long l) {
        this.state = ((HwBlob)object).getInt32(l + 0L);
        this.index = ((HwBlob)object).getInt32(l + 4L);
        this.toa = ((HwBlob)object).getInt32(l + 8L);
        this.isMpty = ((HwBlob)object).getBool(l + 12L);
        this.isMT = ((HwBlob)object).getBool(l + 13L);
        this.als = ((HwBlob)object).getInt8(l + 14L);
        this.isVoice = ((HwBlob)object).getBool(l + 15L);
        this.isVoicePrivacy = ((HwBlob)object).getBool(l + 16L);
        this.number = ((HwBlob)object).getString(l + 24L);
        hwParcel.readEmbeddedBuffer(this.number.getBytes().length + 1, ((HwBlob)object).handle(), l + 24L + 0L, false);
        this.numberPresentation = ((HwBlob)object).getInt32(l + 40L);
        this.name = ((HwBlob)object).getString(l + 48L);
        hwParcel.readEmbeddedBuffer(this.name.getBytes().length + 1, ((HwBlob)object).handle(), l + 48L + 0L, false);
        this.namePresentation = ((HwBlob)object).getInt32(l + 64L);
        int n = ((HwBlob)object).getInt32(l + 72L + 8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 24, ((HwBlob)object).handle(), l + 72L + 0L, true);
        this.uusInfo.clear();
        for (int i = 0; i < n; ++i) {
            object = new UusInfo();
            ((UusInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 24);
            this.uusInfo.add((UusInfo)object);
        }
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(88L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".state = ");
        stringBuilder.append(CallState.toString(this.state));
        stringBuilder.append(", .index = ");
        stringBuilder.append(this.index);
        stringBuilder.append(", .toa = ");
        stringBuilder.append(this.toa);
        stringBuilder.append(", .isMpty = ");
        stringBuilder.append(this.isMpty);
        stringBuilder.append(", .isMT = ");
        stringBuilder.append(this.isMT);
        stringBuilder.append(", .als = ");
        stringBuilder.append(this.als);
        stringBuilder.append(", .isVoice = ");
        stringBuilder.append(this.isVoice);
        stringBuilder.append(", .isVoicePrivacy = ");
        stringBuilder.append(this.isVoicePrivacy);
        stringBuilder.append(", .number = ");
        stringBuilder.append(this.number);
        stringBuilder.append(", .numberPresentation = ");
        stringBuilder.append(CallPresentation.toString(this.numberPresentation));
        stringBuilder.append(", .name = ");
        stringBuilder.append(this.name);
        stringBuilder.append(", .namePresentation = ");
        stringBuilder.append(CallPresentation.toString(this.namePresentation));
        stringBuilder.append(", .uusInfo = ");
        stringBuilder.append(this.uusInfo);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(l + 0L, this.state);
        hwBlob.putInt32(4L + l, this.index);
        hwBlob.putInt32(l + 8L, this.toa);
        hwBlob.putBool(l + 12L, this.isMpty);
        hwBlob.putBool(13L + l, this.isMT);
        hwBlob.putInt8(14L + l, this.als);
        hwBlob.putBool(15L + l, this.isVoice);
        hwBlob.putBool(16L + l, this.isVoicePrivacy);
        hwBlob.putString(24L + l, this.number);
        hwBlob.putInt32(40L + l, this.numberPresentation);
        hwBlob.putString(48L + l, this.name);
        hwBlob.putInt32(64L + l, this.namePresentation);
        int n = this.uusInfo.size();
        hwBlob.putInt32(l + 72L + 8L, n);
        hwBlob.putBool(l + 72L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 24);
        for (int i = 0; i < n; ++i) {
            this.uusInfo.get(i).writeEmbeddedToBlob(hwBlob2, i * 24);
        }
        hwBlob.putBlob(72L + l + 0L, hwBlob2);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(88);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

