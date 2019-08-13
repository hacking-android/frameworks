/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_4.DataCallFailCause;
import android.hardware.radio.V1_4.DataConnActiveStatus;
import android.hardware.radio.V1_4.PdpProtocolType;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class SetupDataCallResult {
    public int active;
    public ArrayList<String> addresses = new ArrayList();
    public int cause;
    public int cid;
    public ArrayList<String> dnses = new ArrayList();
    public ArrayList<String> gateways = new ArrayList();
    public String ifname = new String();
    public int mtu;
    public ArrayList<String> pcscf = new ArrayList();
    public int suggestedRetryTime;
    public int type;

    public static final ArrayList<SetupDataCallResult> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<SetupDataCallResult> arrayList = new ArrayList<SetupDataCallResult>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 112, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new SetupDataCallResult();
            ((SetupDataCallResult)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 112);
            arrayList.add((SetupDataCallResult)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<SetupDataCallResult> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 112);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 112);
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
        if (this.cause != ((SetupDataCallResult)object).cause) {
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
        if (this.type != ((SetupDataCallResult)object).type) {
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
        return Objects.hash(HidlSupport.deepHashCode(this.cause), HidlSupport.deepHashCode(this.suggestedRetryTime), HidlSupport.deepHashCode(this.cid), HidlSupport.deepHashCode(this.active), HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.ifname), HidlSupport.deepHashCode(this.addresses), HidlSupport.deepHashCode(this.dnses), HidlSupport.deepHashCode(this.gateways), HidlSupport.deepHashCode(this.pcscf), HidlSupport.deepHashCode(this.mtu));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        int n;
        Object object;
        this.cause = hwBlob.getInt32(l + 0L);
        this.suggestedRetryTime = hwBlob.getInt32(l + 4L);
        this.cid = hwBlob.getInt32(l + 8L);
        this.active = hwBlob.getInt32(l + 12L);
        this.type = hwBlob.getInt32(l + 16L);
        this.ifname = hwBlob.getString(l + 24L);
        hwParcel.readEmbeddedBuffer(this.ifname.getBytes().length + 1, hwBlob.handle(), l + 24L + 0L, false);
        int n2 = hwBlob.getInt32(l + 40L + 8L);
        Object object2 = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 40L + 0L, true);
        this.addresses.clear();
        for (n = 0; n < n2; ++n) {
            new String();
            object = ((HwBlob)object2).getString(n * 16);
            hwParcel.readEmbeddedBuffer(((String)object).getBytes().length + 1, ((HwBlob)object2).handle(), n * 16 + 0, false);
            this.addresses.add((String)object);
        }
        n2 = hwBlob.getInt32(l + 56L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 56L + 0L, true);
        this.dnses.clear();
        for (n = 0; n < n2; ++n) {
            new String();
            object2 = ((HwBlob)object).getString(n * 16);
            hwParcel.readEmbeddedBuffer(((String)object2).getBytes().length + 1, ((HwBlob)object).handle(), n * 16 + 0, false);
            this.dnses.add((String)object2);
        }
        n2 = hwBlob.getInt32(l + 72L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 72L + 0L, true);
        this.gateways.clear();
        for (n = 0; n < n2; ++n) {
            new String();
            object2 = ((HwBlob)object).getString(n * 16);
            hwParcel.readEmbeddedBuffer(((String)object2).getBytes().length + 1, ((HwBlob)object).handle(), n * 16 + 0, false);
            this.gateways.add((String)object2);
        }
        n2 = hwBlob.getInt32(l + 88L + 8L);
        object = hwParcel.readEmbeddedBuffer(n2 * 16, hwBlob.handle(), l + 88L + 0L, true);
        this.pcscf.clear();
        for (n = 0; n < n2; ++n) {
            new String();
            object2 = ((HwBlob)object).getString(n * 16);
            hwParcel.readEmbeddedBuffer(((String)object2).getBytes().length + 1, ((HwBlob)object).handle(), n * 16 + 0, false);
            this.pcscf.add((String)object2);
        }
        this.mtu = hwBlob.getInt32(l + 104L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".cause = ");
        stringBuilder.append(DataCallFailCause.toString(this.cause));
        stringBuilder.append(", .suggestedRetryTime = ");
        stringBuilder.append(this.suggestedRetryTime);
        stringBuilder.append(", .cid = ");
        stringBuilder.append(this.cid);
        stringBuilder.append(", .active = ");
        stringBuilder.append(DataConnActiveStatus.toString(this.active));
        stringBuilder.append(", .type = ");
        stringBuilder.append(PdpProtocolType.toString(this.type));
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
        int n;
        hwBlob.putInt32(l + 0L, this.cause);
        hwBlob.putInt32(l + 4L, this.suggestedRetryTime);
        hwBlob.putInt32(l + 8L, this.cid);
        hwBlob.putInt32(l + 12L, this.active);
        hwBlob.putInt32(l + 16L, this.type);
        hwBlob.putString(l + 24L, this.ifname);
        int n2 = this.addresses.size();
        hwBlob.putInt32(l + 40L + 8L, n2);
        hwBlob.putBool(l + 40L + 12L, false);
        HwBlob hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putString(n * 16, this.addresses.get(n));
        }
        hwBlob.putBlob(l + 40L + 0L, hwBlob2);
        n2 = this.dnses.size();
        hwBlob.putInt32(l + 56L + 8L, n2);
        hwBlob.putBool(l + 56L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putString(n * 16, this.dnses.get(n));
        }
        hwBlob.putBlob(l + 56L + 0L, hwBlob2);
        n2 = this.gateways.size();
        hwBlob.putInt32(l + 72L + 8L, n2);
        hwBlob.putBool(l + 72L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putString(n * 16, this.gateways.get(n));
        }
        hwBlob.putBlob(l + 72L + 0L, hwBlob2);
        n2 = this.pcscf.size();
        hwBlob.putInt32(l + 88L + 8L, n2);
        hwBlob.putBool(l + 88L + 12L, false);
        hwBlob2 = new HwBlob(n2 * 16);
        for (n = 0; n < n2; ++n) {
            hwBlob2.putString(n * 16, this.pcscf.get(n));
        }
        hwBlob.putBlob(l + 88L + 0L, hwBlob2);
        hwBlob.putInt32(l + 104L, this.mtu);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

