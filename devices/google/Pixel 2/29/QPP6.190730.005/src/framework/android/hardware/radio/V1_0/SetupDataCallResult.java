/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.DataCallFailCause;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SetupDataCallResult {
    public int active;
    public String addresses = new String();
    public int cid;
    public String dnses = new String();
    public String gateways = new String();
    public String ifname = new String();
    public int mtu;
    public String pcscf = new String();
    public int status;
    public int suggestedRetryTime;
    public String type = new String();

    public static final ArrayList<SetupDataCallResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SetupDataCallResult> arrayList = new ArrayList<SetupDataCallResult>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 120, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SetupDataCallResult();
            ((SetupDataCallResult)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 120);
            arrayList.add((SetupDataCallResult)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SetupDataCallResult> arrayList) {
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
        if (object.getClass() != SetupDataCallResult.class) {
            return false;
        }
        object = (SetupDataCallResult)object;
        if (this.status != ((SetupDataCallResult)object).status) {
            return false;
        }
        if (this.suggestedRetryTime != ((SetupDataCallResult)object).suggestedRetryTime) {
            return false;
        }
        if (this.cid != ((SetupDataCallResult)object).cid) {
            return false;
        }
        if (this.active != ((SetupDataCallResult)object).active) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.type, ((SetupDataCallResult)object).type)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.ifname, ((SetupDataCallResult)object).ifname)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.addresses, ((SetupDataCallResult)object).addresses)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.dnses, ((SetupDataCallResult)object).dnses)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.gateways, ((SetupDataCallResult)object).gateways)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.pcscf, ((SetupDataCallResult)object).pcscf)) {
            return false;
        }
        return this.mtu == ((SetupDataCallResult)object).mtu;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.status), HidlSupport.deepHashCode(this.suggestedRetryTime), HidlSupport.deepHashCode(this.cid), HidlSupport.deepHashCode(this.active), HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.ifname), HidlSupport.deepHashCode(this.addresses), HidlSupport.deepHashCode(this.dnses), HidlSupport.deepHashCode(this.gateways), HidlSupport.deepHashCode(this.pcscf), HidlSupport.deepHashCode(this.mtu));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.status = hwBlob.getInt32(l + 0L);
        this.suggestedRetryTime = hwBlob.getInt32(l + 4L);
        this.cid = hwBlob.getInt32(l + 8L);
        this.active = hwBlob.getInt32(l + 12L);
        this.type = hwBlob.getString(l + 16L);
        hwParcel.readEmbeddedBuffer(this.type.getBytes().length + 1, hwBlob.handle(), l + 16L + 0L, false);
        this.ifname = hwBlob.getString(l + 32L);
        hwParcel.readEmbeddedBuffer(this.ifname.getBytes().length + 1, hwBlob.handle(), l + 32L + 0L, false);
        this.addresses = hwBlob.getString(l + 48L);
        hwParcel.readEmbeddedBuffer(this.addresses.getBytes().length + 1, hwBlob.handle(), l + 48L + 0L, false);
        this.dnses = hwBlob.getString(l + 64L);
        hwParcel.readEmbeddedBuffer(this.dnses.getBytes().length + 1, hwBlob.handle(), l + 64L + 0L, false);
        this.gateways = hwBlob.getString(l + 80L);
        hwParcel.readEmbeddedBuffer(this.gateways.getBytes().length + 1, hwBlob.handle(), l + 80L + 0L, false);
        this.pcscf = hwBlob.getString(l + 96L);
        hwParcel.readEmbeddedBuffer(this.pcscf.getBytes().length + 1, hwBlob.handle(), l + 96L + 0L, false);
        this.mtu = hwBlob.getInt32(l + 112L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(120L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".status = ");
        stringBuilder.append(DataCallFailCause.toString(this.status));
        stringBuilder.append(", .suggestedRetryTime = ");
        stringBuilder.append(this.suggestedRetryTime);
        stringBuilder.append(", .cid = ");
        stringBuilder.append(this.cid);
        stringBuilder.append(", .active = ");
        stringBuilder.append(this.active);
        stringBuilder.append(", .type = ");
        stringBuilder.append(this.type);
        stringBuilder.append(", .ifname = ");
        stringBuilder.append(this.ifname);
        stringBuilder.append(", .addresses = ");
        stringBuilder.append(this.addresses);
        stringBuilder.append(", .dnses = ");
        stringBuilder.append(this.dnses);
        stringBuilder.append(", .gateways = ");
        stringBuilder.append(this.gateways);
        stringBuilder.append(", .pcscf = ");
        stringBuilder.append(this.pcscf);
        stringBuilder.append(", .mtu = ");
        stringBuilder.append(this.mtu);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.status);
        hwBlob.putInt32(4L + l, this.suggestedRetryTime);
        hwBlob.putInt32(8L + l, this.cid);
        hwBlob.putInt32(12L + l, this.active);
        hwBlob.putString(16L + l, this.type);
        hwBlob.putString(32L + l, this.ifname);
        hwBlob.putString(48L + l, this.addresses);
        hwBlob.putString(64L + l, this.dnses);
        hwBlob.putString(80L + l, this.gateways);
        hwBlob.putString(96L + l, this.pcscf);
        hwBlob.putInt32(112L + l, this.mtu);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(120);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

