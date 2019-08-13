/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.ApnAuthType;
import android.hardware.radio.V1_0.ApnTypes;
import android.hardware.radio.V1_0.DataProfileId;
import android.hardware.radio.V1_0.DataProfileInfoType;
import android.hardware.radio.V1_0.MvnoType;
import android.hardware.radio.V1_0.RadioAccessFamily;
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
    public String mvnoMatchData = new String();
    public int mvnoType;
    public String password = new String();
    public int profileId;
    public String protocol = new String();
    public String roamingProtocol = new String();
    public int supportedApnTypesBitmap;
    public int type;
    public String user = new String();
    public int waitTime;

    public static final ArrayList<DataProfileInfo> readVectorFromParcel(HwParcel hwParcel) {
        ArrayList<DataProfileInfo> arrayList = new ArrayList<DataProfileInfo>();
        Object object = hwParcel.readBuffer(16L);
        int n = ((HwBlob)object).getInt32(8L);
        HwBlob hwBlob = hwParcel.readEmbeddedBuffer(n * 152, ((HwBlob)object).handle(), 0L, true);
        arrayList.clear();
        for (int i = 0; i < n; ++i) {
            object = new DataProfileInfo();
            ((DataProfileInfo)object).readEmbeddedFromParcel(hwParcel, hwBlob, i * 152);
            arrayList.add((DataProfileInfo)object);
        }
        return arrayList;
    }

    public static final void writeVectorToParcel(HwParcel hwParcel, ArrayList<DataProfileInfo> arrayList) {
        HwBlob hwBlob = new HwBlob(16);
        int n = arrayList.size();
        hwBlob.putInt32(8L, n);
        hwBlob.putBool(12L, false);
        HwBlob hwBlob2 = new HwBlob(n * 152);
        for (int i = 0; i < n; ++i) {
            arrayList.get(i).writeEmbeddedToBlob(hwBlob2, i * 152);
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
        if (!HidlSupport.deepEquals(this.protocol, ((DataProfileInfo)object).protocol)) {
            return false;
        }
        if (!HidlSupport.deepEquals(this.roamingProtocol, ((DataProfileInfo)object).roamingProtocol)) {
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
        if (this.mvnoType != ((DataProfileInfo)object).mvnoType) {
            return false;
        }
        return HidlSupport.deepEquals(this.mvnoMatchData, ((DataProfileInfo)object).mvnoMatchData);
    }

    public final int hashCode() {
        return Objects.hash(HidlSupport.deepHashCode(this.profileId), HidlSupport.deepHashCode(this.apn), HidlSupport.deepHashCode(this.protocol), HidlSupport.deepHashCode(this.roamingProtocol), HidlSupport.deepHashCode(this.authType), HidlSupport.deepHashCode(this.user), HidlSupport.deepHashCode(this.password), HidlSupport.deepHashCode(this.type), HidlSupport.deepHashCode(this.maxConnsTime), HidlSupport.deepHashCode(this.maxConns), HidlSupport.deepHashCode(this.waitTime), HidlSupport.deepHashCode(this.enabled), HidlSupport.deepHashCode(this.supportedApnTypesBitmap), HidlSupport.deepHashCode(this.bearerBitmap), HidlSupport.deepHashCode(this.mtu), HidlSupport.deepHashCode(this.mvnoType), HidlSupport.deepHashCode(this.mvnoMatchData));
    }

    public final void readEmbeddedFromParcel(HwParcel hwParcel, HwBlob hwBlob, long l) {
        this.profileId = hwBlob.getInt32(l + 0L);
        this.apn = hwBlob.getString(l + 8L);
        hwParcel.readEmbeddedBuffer(this.apn.getBytes().length + 1, hwBlob.handle(), l + 8L + 0L, false);
        this.protocol = hwBlob.getString(l + 24L);
        hwParcel.readEmbeddedBuffer(this.protocol.getBytes().length + 1, hwBlob.handle(), l + 24L + 0L, false);
        this.roamingProtocol = hwBlob.getString(l + 40L);
        hwParcel.readEmbeddedBuffer(this.roamingProtocol.getBytes().length + 1, hwBlob.handle(), l + 40L + 0L, false);
        this.authType = hwBlob.getInt32(l + 56L);
        this.user = hwBlob.getString(l + 64L);
        hwParcel.readEmbeddedBuffer(this.user.getBytes().length + 1, hwBlob.handle(), l + 64L + 0L, false);
        this.password = hwBlob.getString(l + 80L);
        hwParcel.readEmbeddedBuffer(this.password.getBytes().length + 1, hwBlob.handle(), l + 80L + 0L, false);
        this.type = hwBlob.getInt32(l + 96L);
        this.maxConnsTime = hwBlob.getInt32(l + 100L);
        this.maxConns = hwBlob.getInt32(l + 104L);
        this.waitTime = hwBlob.getInt32(l + 108L);
        this.enabled = hwBlob.getBool(l + 112L);
        this.supportedApnTypesBitmap = hwBlob.getInt32(l + 116L);
        this.bearerBitmap = hwBlob.getInt32(l + 120L);
        this.mtu = hwBlob.getInt32(l + 124L);
        this.mvnoType = hwBlob.getInt32(l + 128L);
        this.mvnoMatchData = hwBlob.getString(l + 136L);
        hwParcel.readEmbeddedBuffer(this.mvnoMatchData.getBytes().length + 1, hwBlob.handle(), l + 136L + 0L, false);
    }

    public final void readFromParcel(HwParcel hwParcel) {
        this.readEmbeddedFromParcel(hwParcel, hwParcel.readBuffer(152L), 0L);
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append(".profileId = ");
        stringBuilder.append(DataProfileId.toString(this.profileId));
        stringBuilder.append(", .apn = ");
        stringBuilder.append(this.apn);
        stringBuilder.append(", .protocol = ");
        stringBuilder.append(this.protocol);
        stringBuilder.append(", .roamingProtocol = ");
        stringBuilder.append(this.roamingProtocol);
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
        stringBuilder.append(", .mvnoType = ");
        stringBuilder.append(MvnoType.toString(this.mvnoType));
        stringBuilder.append(", .mvnoMatchData = ");
        stringBuilder.append(this.mvnoMatchData);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public final void writeEmbeddedToBlob(HwBlob hwBlob, long l) {
        hwBlob.putInt32(0L + l, this.profileId);
        hwBlob.putString(8L + l, this.apn);
        hwBlob.putString(24L + l, this.protocol);
        hwBlob.putString(40L + l, this.roamingProtocol);
        hwBlob.putInt32(56L + l, this.authType);
        hwBlob.putString(64L + l, this.user);
        hwBlob.putString(80L + l, this.password);
        hwBlob.putInt32(96L + l, this.type);
        hwBlob.putInt32(100L + l, this.maxConnsTime);
        hwBlob.putInt32(104L + l, this.maxConns);
        hwBlob.putInt32(108L + l, this.waitTime);
        hwBlob.putBool(112L + l, this.enabled);
        hwBlob.putInt32(116L + l, this.supportedApnTypesBitmap);
        hwBlob.putInt32(120L + l, this.bearerBitmap);
        hwBlob.putInt32(124L + l, this.mtu);
        hwBlob.putInt32(128L + l, this.mvnoType);
        hwBlob.putString(136L + l, this.mvnoMatchData);
    }

    public final void writeToParcel(HwParcel hwParcel) {
        HwBlob hwBlob = new HwBlob(152);
        this.writeEmbeddedToBlob(hwBlob, 0L);
        hwParcel.writeBuffer(hwBlob);
    }
}

