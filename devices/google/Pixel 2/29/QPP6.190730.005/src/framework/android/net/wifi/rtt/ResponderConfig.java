/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.annotation.SystemApi;
import android.net.MacAddress;
import android.net.wifi.ScanResult;
import android.net.wifi.aware.PeerHandle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

@SystemApi
public final class ResponderConfig
implements Parcelable {
    private static final int AWARE_BAND_2_DISCOVERY_CHANNEL = 2437;
    public static final int CHANNEL_WIDTH_160MHZ = 3;
    public static final int CHANNEL_WIDTH_20MHZ = 0;
    public static final int CHANNEL_WIDTH_40MHZ = 1;
    public static final int CHANNEL_WIDTH_80MHZ = 2;
    public static final int CHANNEL_WIDTH_80MHZ_PLUS_MHZ = 4;
    public static final Parcelable.Creator<ResponderConfig> CREATOR = new Parcelable.Creator<ResponderConfig>(){

        @Override
        public ResponderConfig createFromParcel(Parcel parcel) {
            boolean bl = parcel.readBoolean();
            MacAddress macAddress = null;
            if (bl) {
                macAddress = MacAddress.CREATOR.createFromParcel(parcel);
            }
            PeerHandle peerHandle = parcel.readBoolean() ? new PeerHandle(parcel.readInt()) : null;
            int n = parcel.readInt();
            bl = parcel.readInt() == 1;
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            int n4 = parcel.readInt();
            int n5 = parcel.readInt();
            int n6 = parcel.readInt();
            if (peerHandle == null) {
                return new ResponderConfig(macAddress, n, bl, n2, n3, n4, n5, n6);
            }
            return new ResponderConfig(peerHandle, n, bl, n2, n3, n4, n5, n6);
        }

        public ResponderConfig[] newArray(int n) {
            return new ResponderConfig[n];
        }
    };
    public static final int PREAMBLE_HT = 1;
    public static final int PREAMBLE_LEGACY = 0;
    public static final int PREAMBLE_VHT = 2;
    public static final int RESPONDER_AP = 0;
    public static final int RESPONDER_AWARE = 4;
    public static final int RESPONDER_P2P_CLIENT = 3;
    public static final int RESPONDER_P2P_GO = 2;
    public static final int RESPONDER_STA = 1;
    private static final String TAG = "ResponderConfig";
    public final int centerFreq0;
    public final int centerFreq1;
    public final int channelWidth;
    public final int frequency;
    public final MacAddress macAddress;
    public final PeerHandle peerHandle;
    public final int preamble;
    public final int responderType;
    public final boolean supports80211mc;

    public ResponderConfig(MacAddress macAddress, int n, boolean bl, int n2, int n3, int n4, int n5, int n6) {
        if (macAddress != null) {
            this.macAddress = macAddress;
            this.peerHandle = null;
            this.responderType = n;
            this.supports80211mc = bl;
            this.channelWidth = n2;
            this.frequency = n3;
            this.centerFreq0 = n4;
            this.centerFreq1 = n5;
            this.preamble = n6;
            return;
        }
        throw new IllegalArgumentException("Invalid ResponderConfig - must specify a MAC address");
    }

    public ResponderConfig(MacAddress macAddress, PeerHandle peerHandle, int n, boolean bl, int n2, int n3, int n4, int n5, int n6) {
        this.macAddress = macAddress;
        this.peerHandle = peerHandle;
        this.responderType = n;
        this.supports80211mc = bl;
        this.channelWidth = n2;
        this.frequency = n3;
        this.centerFreq0 = n4;
        this.centerFreq1 = n5;
        this.preamble = n6;
    }

    public ResponderConfig(PeerHandle peerHandle, int n, boolean bl, int n2, int n3, int n4, int n5, int n6) {
        this.macAddress = null;
        this.peerHandle = peerHandle;
        this.responderType = n;
        this.supports80211mc = bl;
        this.channelWidth = n2;
        this.frequency = n3;
        this.centerFreq0 = n4;
        this.centerFreq1 = n5;
        this.preamble = n6;
    }

    public static ResponderConfig fromScanResult(ScanResult object2) {
        int n;
        MacAddress macAddress = MacAddress.fromString(((ScanResult)object2).BSSID);
        boolean bl = ((ScanResult)object2).is80211mcResponder();
        int n2 = ResponderConfig.translateScanResultChannelWidth(((ScanResult)object2).channelWidth);
        int n3 = ((ScanResult)object2).frequency;
        int n4 = ((ScanResult)object2).centerFreq0;
        int n5 = ((ScanResult)object2).centerFreq1;
        if (((ScanResult)object2).informationElements != null && ((ScanResult)object2).informationElements.length != 0) {
            boolean bl2 = false;
            boolean bl3 = false;
            for (ScanResult.InformationElement informationElement : ((ScanResult)object2).informationElements) {
                boolean bl4;
                if (informationElement.id == 45) {
                    bl4 = true;
                } else {
                    bl4 = bl2;
                    if (informationElement.id == 191) {
                        bl3 = true;
                        bl4 = bl2;
                    }
                }
                bl2 = bl4;
            }
            n = bl3 ? 2 : (bl2 ? 1 : 0);
        } else {
            Log.e(TAG, "Scan Results do not contain IEs - using backup method to select preamble");
            n = n2 != 2 && n2 != 3 ? 1 : 2;
        }
        return new ResponderConfig(macAddress, 0, bl, n2, n3, n4, n5, n);
    }

    public static ResponderConfig fromWifiAwarePeerHandleWithDefaults(PeerHandle peerHandle) {
        return new ResponderConfig(peerHandle, 4, true, 0, 2437, 0, 0, 1);
    }

    public static ResponderConfig fromWifiAwarePeerMacAddressWithDefaults(MacAddress macAddress) {
        return new ResponderConfig(macAddress, 4, true, 0, 2437, 0, 0, 1);
    }

    static int translateScanResultChannelWidth(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            return 4;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("translateScanResultChannelWidth: bad ");
                        stringBuilder.append(n);
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    return 3;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof ResponderConfig)) {
            return false;
        }
        object = (ResponderConfig)object;
        if (!Objects.equals(this.macAddress, ((ResponderConfig)object).macAddress) || !Objects.equals(this.peerHandle, ((ResponderConfig)object).peerHandle) || this.responderType != ((ResponderConfig)object).responderType || this.supports80211mc != ((ResponderConfig)object).supports80211mc || this.channelWidth != ((ResponderConfig)object).channelWidth || this.frequency != ((ResponderConfig)object).frequency || this.centerFreq0 != ((ResponderConfig)object).centerFreq0 || this.centerFreq1 != ((ResponderConfig)object).centerFreq1 || this.preamble != ((ResponderConfig)object).preamble) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.macAddress, this.peerHandle, this.responderType, this.supports80211mc, this.channelWidth, this.frequency, this.centerFreq0, this.centerFreq1, this.preamble);
    }

    public boolean isValid(boolean bl) {
        if (this.macAddress == null && this.peerHandle == null || this.macAddress != null && this.peerHandle != null) {
            return false;
        }
        return bl || this.responderType != 4;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("ResponderConfig: macAddress=");
        stringBuffer.append(this.macAddress);
        stringBuffer.append(", peerHandle=");
        Object object = this.peerHandle;
        object = object == null ? "<null>" : Integer.valueOf(((PeerHandle)object).peerId);
        stringBuffer.append(object);
        stringBuffer.append(", responderType=");
        stringBuffer.append(this.responderType);
        stringBuffer.append(", supports80211mc=");
        stringBuffer.append(this.supports80211mc);
        stringBuffer.append(", channelWidth=");
        stringBuffer.append(this.channelWidth);
        stringBuffer.append(", frequency=");
        stringBuffer.append(this.frequency);
        stringBuffer.append(", centerFreq0=");
        stringBuffer.append(this.centerFreq0);
        stringBuffer.append(", centerFreq1=");
        stringBuffer.append(this.centerFreq1);
        stringBuffer.append(", preamble=");
        stringBuffer.append(this.preamble);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.macAddress == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            this.macAddress.writeToParcel(parcel, n);
        }
        if (this.peerHandle == null) {
            parcel.writeBoolean(false);
        } else {
            parcel.writeBoolean(true);
            parcel.writeInt(this.peerHandle.peerId);
        }
        parcel.writeInt(this.responderType);
        parcel.writeInt((int)this.supports80211mc);
        parcel.writeInt(this.channelWidth);
        parcel.writeInt(this.frequency);
        parcel.writeInt(this.centerFreq0);
        parcel.writeInt(this.centerFreq1);
        parcel.writeInt(this.preamble);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ChannelWidth {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PreambleType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResponderType {
    }

}

