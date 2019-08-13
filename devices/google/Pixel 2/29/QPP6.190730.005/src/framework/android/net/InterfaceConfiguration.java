/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.LinkAddress;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.collect.Sets;
import java.net.InetAddress;
import java.util.HashSet;
import java.util.Iterator;

public class InterfaceConfiguration
implements Parcelable {
    public static final Parcelable.Creator<InterfaceConfiguration> CREATOR;
    private static final String[] EMPTY_STRING_ARRAY;
    private static final String FLAG_DOWN = "down";
    private static final String FLAG_UP = "up";
    private LinkAddress mAddr;
    private HashSet<String> mFlags = Sets.newHashSet();
    private String mHwAddr;

    static {
        EMPTY_STRING_ARRAY = new String[0];
        CREATOR = new Parcelable.Creator<InterfaceConfiguration>(){

            @Override
            public InterfaceConfiguration createFromParcel(Parcel parcel) {
                InterfaceConfiguration interfaceConfiguration = new InterfaceConfiguration();
                interfaceConfiguration.mHwAddr = parcel.readString();
                if (parcel.readByte() == 1) {
                    interfaceConfiguration.mAddr = (LinkAddress)parcel.readParcelable(null);
                }
                int n = parcel.readInt();
                for (int i = 0; i < n; ++i) {
                    interfaceConfiguration.mFlags.add(parcel.readString());
                }
                return interfaceConfiguration;
            }

            public InterfaceConfiguration[] newArray(int n) {
                return new InterfaceConfiguration[n];
            }
        };
    }

    private static void validateFlag(String string2) {
        if (string2.indexOf(32) < 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("flag contains space: ");
        stringBuilder.append(string2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void clearFlag(String string2) {
        InterfaceConfiguration.validateFlag(string2);
        this.mFlags.remove(string2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public Iterable<String> getFlags() {
        return this.mFlags;
    }

    public String getHardwareAddress() {
        return this.mHwAddr;
    }

    public LinkAddress getLinkAddress() {
        return this.mAddr;
    }

    public boolean hasFlag(String string2) {
        InterfaceConfiguration.validateFlag(string2);
        return this.mFlags.contains(string2);
    }

    public void ignoreInterfaceUpDownStatus() {
        this.mFlags.remove(FLAG_UP);
        this.mFlags.remove(FLAG_DOWN);
    }

    public boolean isActive() {
        block3 : {
            try {
                if (!this.isUp()) break block3;
            }
            catch (NullPointerException nullPointerException) {
                return false;
            }
            for (byte by : this.mAddr.getAddress().getAddress()) {
                if (by == 0) continue;
                return true;
            }
        }
        return false;
    }

    public boolean isUp() {
        return this.hasFlag(FLAG_UP);
    }

    @UnsupportedAppUsage
    public void setFlag(String string2) {
        InterfaceConfiguration.validateFlag(string2);
        this.mFlags.add(string2);
    }

    public void setHardwareAddress(String string2) {
        this.mHwAddr = string2;
    }

    @UnsupportedAppUsage
    public void setInterfaceDown() {
        this.mFlags.remove(FLAG_UP);
        this.mFlags.add(FLAG_DOWN);
    }

    @UnsupportedAppUsage
    public void setInterfaceUp() {
        this.mFlags.remove(FLAG_DOWN);
        this.mFlags.add(FLAG_UP);
    }

    @UnsupportedAppUsage
    public void setLinkAddress(LinkAddress linkAddress) {
        this.mAddr = linkAddress;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mHwAddr=");
        stringBuilder.append(this.mHwAddr);
        stringBuilder.append(" mAddr=");
        stringBuilder.append(String.valueOf(this.mAddr));
        stringBuilder.append(" mFlags=");
        stringBuilder.append(this.getFlags());
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mHwAddr);
        if (this.mAddr != null) {
            parcel.writeByte((byte)1);
            parcel.writeParcelable(this.mAddr, n);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeInt(this.mFlags.size());
        Iterator<String> iterator = this.mFlags.iterator();
        while (iterator.hasNext()) {
            parcel.writeString(iterator.next());
        }
    }

}

