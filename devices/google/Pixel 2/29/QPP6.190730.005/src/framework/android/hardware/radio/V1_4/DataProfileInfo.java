/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.ApnAuthType;
import android.hardware.radio.V1_0.DataProfileId;
import android.hardware.radio.V1_0.DataProfileInfoType;
import android.hardware.radio.V1_4.ApnTypes;
import android.hardware.radio.V1_4.PdpProtocolType;
import android.hardware.radio.V1_4.RadioAccessFamily;
import android.os.HidlSupport;
import android.os.HwBlob;
import android.os.HwParcel;
import java.util.ArrayList;
import java.util.Objects;

public final class DataProfileInfo {
    public String apn = new String();
    public int authType;
    public int bearerBitmap;
    public boolean enabled;
    public int maxConns;
    public int maxConnsTime;
    public int mtu;
    public String password = new String();
    public boolean persistent;
    public boolean preferred;
    public int profileId;
    public int protocol;
    public int roamingProtocol;
    public int supportedApnTypesBitmap;
    public int type;
    public String user = new String();
    public int waitTime;

    public static final ArrayList<DataProfileInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataProfileInfo> arrayList = new ArrayList<DataProfileInfo>();
        HwBlob hwBlob = hwParcel.readBuffer(16L);
        int n = hwBlob.getInt32(8L);
        hwBlob = hwParcel.readEmbeddedBuffer(n * 112, hwBlob.handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            DataProfileInfo dataProfileInfo = new DataProfileInfo();
            dataProfileInfo.readEmbeddedFromParcel(hwParcel, hwBlob, i * 112);
            arrayList.add(dataProfileInfo);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataProfileInfo> arrayList) {
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
        if (object.getClass() != DataProfileInfo.class) {
            return false;
        }
        object = (DataProfileInfo)object;
        if (this.profileId != ((DataProfileInfo)object).profileId) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.apn, ((DataProfileInfo)object).apn)) {
            return false;
        }
        if (this.protocol != ((DataProfileInfo)object).protocol) {
            return false;
        }
        if (this.roamingProtocol != ((DataProfileInfo)object).roamingProtocol) {
            return false;
        }
        if (this.authType != ((DataProfileInfo)object).authType) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.user, ((DataProfileInfo)object).user)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.password, ((DataProfileInfo)object).password)) {
            return false;
        }
        if (this.type != ((DataProfileInfo)object).type) {
            return false;
        }
        if (this.maxConnsTime != ((DataProfileInfo)object).maxConnsTime) {
            return false;
        }
        if (this.maxConns != ((DataProfileInfo)object).maxConns) {
            return false;
        }
        if (this.waitTime != ((DataProfileInfo)object).waitTime) {
            return false;
        }
        if (this.enabled != ((DataProfileInfo)object).enabled) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.supportedApnTypesBitmap, ((DataProfileInfo)object).supportedApnTypesBitmap)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.bearerBitmap, ((DataProfileInfo)object).bearerBitmap)) {
            return false;
        }
        if (this.mtu != ((DataProfileInfo)object).mtu) {
            return false;
        }
        if (this.preferred != ((DataProfileInfo)object).preferred) {
            return false;
        }
        return this.persistent == ((DataProfileInfo)object).persistent;
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.profileId), HidlSupport.deepHashCode(this.apn), HidlSupport.deepHashCode(this.protocol), HidlSupport.deepHashCode(this.roamingProtocol), HidlSupport.deepHashCode(this.authType), HidlSupport.deepHashCode(this.user), HidlSupport.deepHashCode(this.password), HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.maxConnsTime), HidlSupport.deepHashCode(this.maxConns), HidlSupport.deepHashCode(this.waitTime), HidlSupport.deepHashCode(this.enabled), HidlSupport.deepHashCode(this.supportedApnTypesBitmap), HidlSupport.deepHashCode(this.bearerBitmap), HidlSupport.deepHashCode(this.mtu), HidlSupport.deepHashCode(this.preferred), HidlSupport.deepHashCode(this.persistent));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.profileId = hwBlob.getInt32(l + 0L);
        this.apn = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.apn.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.protocol = hwBlob.getInt32(l + 24L);
        this.roamingProtocol = hwBlob.getInt32(l + 28L);
        this.authType = hwBlob.getInt32(l + 32L);
        this.user = hwBlob.getString(l + 40L);
        hwParcel.readEmbeddedBuffer(this.user.getBytes().length + 1, hwBlob.handle(), l + 40L + 0L, false);
        this.password = hwBlob.getString(l + 56L);
        hwParcel.readEmbeddedBuffer(this.password.getBytes().length + 1, hwBlob.handle(), l + 56L + 0L, false);
        this.type = hwBlob.getInt32(l + 72L);
        this.maxConnsTime = hwBlob.getInt32(l + 76L);
        this.maxConns = hwBlob.getInt32(l + 80L);
        this.waitTime = hwBlob.getInt32(l + 84L);
        this.enabled = hwBlob.getBool(l + 88L);
        this.supportedApnTypesBitmap = hwBlob.getInt32(l + 92L);
        this.bearerBitmap = hwBlob.getInt32(l + 96L);
        this.mtu = hwBlob.getInt32(l + 100L);
        this.preferred = hwBlob.getBool(l + 104L);
        this.persistent = hwBlob.getBool(l + 105L);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(112L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".profileId = ");
        stringBuilder.append(DataProfileId.toString(this.profileId));
        stringBuilder.append(", .apn = ");
        stringBuilder.append(this.apn);
        stringBuilder.append(", .protocol = ");
        stringBuilder.append(PdpProtocolType.toString(this.protocol));
        stringBuilder.append(", .roamingProtocol = ");
        stringBuilder.append(PdpProtocolType.toString(this.roamingProtocol));
        stringBuilder.append(", .authType = ");
        stringBuilder.append(ApnAuthType.toString(this.authType));
        stringBuilder.append(", .user = ");
        stringBuilder.append(this.user);
        stringBuilder.append(", .password = ");
        stringBuilder.append(this.password);
        stringBuilder.append(", .type = ");
        stringBuilder.append(DataProfileInfoType.toString(this.type));
        stringBuilder.append(", .maxConnsTime = ");
        stringBuilder.append(this.maxConnsTime);
        stringBuilder.append(", .maxConns = ");
        stringBuilder.append(this.maxConns);
        stringBuilder.append(", .waitTime = ");
        stringBuilder.append(this.waitTime);
        stringBuilder.append(", .enabled = ");
        stringBuilder.append(this.enabled);
        stringBuilder.append(", .supportedApnTypesBitmap = ");
        stringBuilder.append(ApnTypes.dumpBitfield(this.supportedApnTypesBitmap));
        stringBuilder.append(", .bearerBitmap = ");
        stringBuilder.append(RadioAccessFamily.dumpBitfield(this.bearerBitmap));
        stringBuilder.append(", .mtu = ");
        stringBuilder.append(this.mtu);
        stringBuilder.append(", .preferred = ");
        stringBuilder.append(this.preferred);
        stringBuilder.append(", .persistent = ");
        stringBuilder.append(this.persistent);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.profileId);
        hwBlob.putString(8L + l, this.apn);
        hwBlob.putInt32(24L + l, this.protocol);
        hwBlob.putInt32(28L + l, this.roamingProtocol);
        hwBlob.putInt32(32L + l, this.authType);
        hwBlob.putString(40L + l, this.user);
        hwBlob.putString(56L + l, this.password);
        hwBlob.putInt32(72L + l, this.type);
        hwBlob.putInt32(76L + l, this.maxConnsTime);
        hwBlob.putInt32(80L + l, this.maxConns);
        hwBlob.putInt32(84L + l, this.waitTime);
        hwBlob.putBool(88L + l, this.enabled);
        hwBlob.putInt32(92L + l, this.supportedApnTypesBitmap);
        hwBlob.putInt32(96L + l, this.bearerBitmap);
        hwBlob.putInt32(100L + l, this.mtu);
        hwBlob.putBool(104L + l, this.preferred);
        hwBlob.putBool(105L + l, this.persistent);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(112);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

