/*
 * Decompiled with CFR 0.145.
 */
package android.telephony;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.ModemInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PhoneCapability
implements Parcelable {
    public static final Parcelable.Creator<PhoneCapability> CREATOR;
    public static final PhoneCapability DEFAULT_DSDS_CAPABILITY;
    public static final PhoneCapability DEFAULT_SSSS_CAPABILITY;
    public final List<ModemInfo> logicalModemList;
    public final int max5G;
    public final int maxActiveData;
    public final int maxActiveVoiceCalls;
    public final boolean validationBeforeSwitchSupported;

    static {
        ModemInfo modemInfo = new ModemInfo(0, 0, true, true);
        Object object = new ModemInfo(1, 0, true, true);
        ArrayList<ModemInfo> arrayList = new ArrayList<ModemInfo>();
        arrayList.add(modemInfo);
        arrayList.add((ModemInfo)object);
        DEFAULT_DSDS_CAPABILITY = new PhoneCapability(1, 1, 0, arrayList, false);
        object = new ArrayList();
        object.add(modemInfo);
        DEFAULT_SSSS_CAPABILITY = new PhoneCapability(1, 1, 0, (List<ModemInfo>)object, false);
        CREATOR = new Parcelable.Creator(){

            public PhoneCapability createFromParcel(Parcel parcel) {
                return new PhoneCapability(parcel);
            }

            public PhoneCapability[] newArray(int n) {
                return new PhoneCapability[n];
            }
        };
    }

    public PhoneCapability(int n, int n2, int n3, List<ModemInfo> list, boolean bl) {
        this.maxActiveVoiceCalls = n;
        this.maxActiveData = n2;
        this.max5G = n3;
        if (list == null) {
            list = new ArrayList<ModemInfo>();
        }
        this.logicalModemList = list;
        this.validationBeforeSwitchSupported = bl;
    }

    private PhoneCapability(Parcel parcel) {
        this.maxActiveVoiceCalls = parcel.readInt();
        this.maxActiveData = parcel.readInt();
        this.max5G = parcel.readInt();
        this.validationBeforeSwitchSupported = parcel.readBoolean();
        this.logicalModemList = new ArrayList<ModemInfo>();
        parcel.readList(this.logicalModemList, ModemInfo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof PhoneCapability && this.hashCode() == object.hashCode()) {
            if (this == object) {
                return true;
            }
            object = (PhoneCapability)object;
            if (this.maxActiveVoiceCalls == ((PhoneCapability)object).maxActiveVoiceCalls && this.maxActiveData == ((PhoneCapability)object).maxActiveData && this.max5G == ((PhoneCapability)object).max5G && this.validationBeforeSwitchSupported == ((PhoneCapability)object).validationBeforeSwitchSupported && this.logicalModemList.equals(((PhoneCapability)object).logicalModemList)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.maxActiveVoiceCalls, this.maxActiveData, this.max5G, this.logicalModemList, this.validationBeforeSwitchSupported);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("maxActiveVoiceCalls=");
        stringBuilder.append(this.maxActiveVoiceCalls);
        stringBuilder.append(" maxActiveData=");
        stringBuilder.append(this.maxActiveData);
        stringBuilder.append(" max5G=");
        stringBuilder.append(this.max5G);
        stringBuilder.append("logicalModemList:");
        stringBuilder.append(Arrays.toString(this.logicalModemList.toArray()));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.maxActiveVoiceCalls);
        parcel.writeInt(this.maxActiveData);
        parcel.writeInt(this.max5G);
        parcel.writeBoolean(this.validationBeforeSwitchSupported);
        parcel.writeList(this.logicalModemList);
    }

}

