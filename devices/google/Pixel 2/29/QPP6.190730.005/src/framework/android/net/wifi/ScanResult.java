/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.net.wifi.AnqpInformationElement;
import android.net.wifi.WifiSsid;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ScanResult
implements Parcelable {
    public static final int CHANNEL_WIDTH_160MHZ = 3;
    public static final int CHANNEL_WIDTH_20MHZ = 0;
    public static final int CHANNEL_WIDTH_40MHZ = 1;
    public static final int CHANNEL_WIDTH_80MHZ = 2;
    public static final int CHANNEL_WIDTH_80MHZ_PLUS_MHZ = 4;
    public static final int CIPHER_CCMP = 3;
    public static final int CIPHER_GCMP_256 = 4;
    public static final int CIPHER_NONE = 0;
    public static final int CIPHER_NO_GROUP_ADDRESSED = 1;
    public static final int CIPHER_TKIP = 2;
    @UnsupportedAppUsage
    public static final Parcelable.Creator<ScanResult> CREATOR = new Parcelable.Creator<ScanResult>(){

        @Override
        public ScanResult createFromParcel(Parcel parcel) {
            int n;
            byte[] arrby = null;
            int n2 = parcel.readInt();
            boolean bl = true;
            if (n2 == 1) {
                arrby = WifiSsid.CREATOR.createFromParcel(parcel);
            }
            ScanResult scanResult = new ScanResult((WifiSsid)arrby, parcel.readString(), parcel.readString(), parcel.readLong(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readLong(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt(), false);
            scanResult.seen = parcel.readLong();
            boolean bl2 = parcel.readInt() != 0;
            scanResult.untrusted = bl2;
            scanResult.numUsage = parcel.readInt();
            scanResult.venueName = parcel.readString();
            scanResult.operatorFriendlyName = parcel.readString();
            scanResult.flags = parcel.readLong();
            int n3 = parcel.readInt();
            if (n3 != 0) {
                scanResult.informationElements = new InformationElement[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    scanResult.informationElements[n2] = new InformationElement();
                    scanResult.informationElements[n2].id = parcel.readInt();
                    n = parcel.readInt();
                    scanResult.informationElements[n2].bytes = new byte[n];
                    parcel.readByteArray(scanResult.informationElements[n2].bytes);
                }
            }
            if ((n3 = parcel.readInt()) != 0) {
                scanResult.anqpLines = new ArrayList<String>();
                for (n2 = 0; n2 < n3; ++n2) {
                    scanResult.anqpLines.add(parcel.readString());
                }
            }
            if ((n3 = parcel.readInt()) != 0) {
                scanResult.anqpElements = new AnqpInformationElement[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    n = parcel.readInt();
                    int n4 = parcel.readInt();
                    arrby = new byte[parcel.readInt()];
                    parcel.readByteArray(arrby);
                    scanResult.anqpElements[n2] = new AnqpInformationElement(n, n4, arrby);
                }
            }
            bl2 = parcel.readInt() != 0 ? bl : false;
            scanResult.isCarrierAp = bl2;
            scanResult.carrierApEapType = parcel.readInt();
            scanResult.carrierName = parcel.readString();
            n3 = parcel.readInt();
            if (n3 != 0) {
                scanResult.radioChainInfos = new RadioChainInfo[n3];
                for (n2 = 0; n2 < n3; ++n2) {
                    scanResult.radioChainInfos[n2] = new RadioChainInfo();
                    scanResult.radioChainInfos[n2].id = parcel.readInt();
                    scanResult.radioChainInfos[n2].level = parcel.readInt();
                }
            }
            return scanResult;
        }

        public ScanResult[] newArray(int n) {
            return new ScanResult[n];
        }
    };
    public static final long FLAG_80211mc_RESPONDER = 2L;
    public static final long FLAG_PASSPOINT_NETWORK = 1L;
    public static final int KEY_MGMT_EAP = 2;
    public static final int KEY_MGMT_EAP_SHA256 = 6;
    public static final int KEY_MGMT_EAP_SUITE_B_192 = 10;
    public static final int KEY_MGMT_FT_EAP = 4;
    public static final int KEY_MGMT_FT_PSK = 3;
    public static final int KEY_MGMT_FT_SAE = 11;
    public static final int KEY_MGMT_NONE = 0;
    public static final int KEY_MGMT_OSEN = 7;
    public static final int KEY_MGMT_OWE = 9;
    public static final int KEY_MGMT_OWE_TRANSITION = 12;
    public static final int KEY_MGMT_PSK = 1;
    public static final int KEY_MGMT_PSK_SHA256 = 5;
    public static final int KEY_MGMT_SAE = 8;
    public static final int PROTOCOL_NONE = 0;
    public static final int PROTOCOL_OSEN = 3;
    public static final int PROTOCOL_RSN = 2;
    public static final int PROTOCOL_WPA = 1;
    public static final int UNSPECIFIED = -1;
    public String BSSID;
    public String SSID;
    @UnsupportedAppUsage
    public int anqpDomainId;
    public AnqpInformationElement[] anqpElements;
    @UnsupportedAppUsage
    public List<String> anqpLines;
    public String capabilities;
    public int carrierApEapType;
    public String carrierName;
    public int centerFreq0;
    public int centerFreq1;
    public int channelWidth;
    @UnsupportedAppUsage
    public int distanceCm;
    @UnsupportedAppUsage
    public int distanceSdCm;
    @UnsupportedAppUsage
    public long flags;
    public int frequency;
    @UnsupportedAppUsage
    public long hessid;
    @UnsupportedAppUsage
    public InformationElement[] informationElements;
    @UnsupportedAppUsage
    public boolean is80211McRTTResponder;
    public boolean isCarrierAp;
    public int level;
    @UnsupportedAppUsage
    public int numUsage;
    public CharSequence operatorFriendlyName;
    public RadioChainInfo[] radioChainInfos;
    @UnsupportedAppUsage
    public long seen;
    public long timestamp;
    @SystemApi
    public boolean untrusted;
    public CharSequence venueName;
    @UnsupportedAppUsage
    public WifiSsid wifiSsid;

    public ScanResult() {
    }

    public ScanResult(ScanResult scanResult) {
        if (scanResult != null) {
            this.wifiSsid = scanResult.wifiSsid;
            this.SSID = scanResult.SSID;
            this.BSSID = scanResult.BSSID;
            this.hessid = scanResult.hessid;
            this.anqpDomainId = scanResult.anqpDomainId;
            this.informationElements = scanResult.informationElements;
            this.anqpElements = scanResult.anqpElements;
            this.capabilities = scanResult.capabilities;
            this.level = scanResult.level;
            this.frequency = scanResult.frequency;
            this.channelWidth = scanResult.channelWidth;
            this.centerFreq0 = scanResult.centerFreq0;
            this.centerFreq1 = scanResult.centerFreq1;
            this.timestamp = scanResult.timestamp;
            this.distanceCm = scanResult.distanceCm;
            this.distanceSdCm = scanResult.distanceSdCm;
            this.seen = scanResult.seen;
            this.untrusted = scanResult.untrusted;
            this.numUsage = scanResult.numUsage;
            this.venueName = scanResult.venueName;
            this.operatorFriendlyName = scanResult.operatorFriendlyName;
            this.flags = scanResult.flags;
            this.isCarrierAp = scanResult.isCarrierAp;
            this.carrierApEapType = scanResult.carrierApEapType;
            this.carrierName = scanResult.carrierName;
            this.radioChainInfos = scanResult.radioChainInfos;
        }
    }

    public ScanResult(WifiSsid object, String string2, long l, int n, byte[] arrby, String string3, int n2, int n3, long l2) {
        this.wifiSsid = object;
        object = object != null ? ((WifiSsid)object).toString() : "<unknown ssid>";
        this.SSID = object;
        this.BSSID = string2;
        this.hessid = l;
        this.anqpDomainId = n;
        if (arrby != null) {
            this.anqpElements = new AnqpInformationElement[1];
            this.anqpElements[0] = new AnqpInformationElement(5271450, 8, arrby);
        }
        this.capabilities = string3;
        this.level = n2;
        this.frequency = n3;
        this.timestamp = l2;
        this.distanceCm = -1;
        this.distanceSdCm = -1;
        this.channelWidth = -1;
        this.centerFreq0 = -1;
        this.centerFreq1 = -1;
        this.flags = 0L;
        this.isCarrierAp = false;
        this.carrierApEapType = -1;
        this.carrierName = null;
        this.radioChainInfos = null;
    }

    public ScanResult(WifiSsid object, String string2, String string3, int n, int n2, long l, int n3, int n4) {
        this.wifiSsid = object;
        object = object != null ? ((WifiSsid)object).toString() : "<unknown ssid>";
        this.SSID = object;
        this.BSSID = string2;
        this.capabilities = string3;
        this.level = n;
        this.frequency = n2;
        this.timestamp = l;
        this.distanceCm = n3;
        this.distanceSdCm = n4;
        this.channelWidth = -1;
        this.centerFreq0 = -1;
        this.centerFreq1 = -1;
        this.flags = 0L;
        this.isCarrierAp = false;
        this.carrierApEapType = -1;
        this.carrierName = null;
        this.radioChainInfos = null;
    }

    public ScanResult(WifiSsid wifiSsid, String string2, String string3, long l, int n, String string4, int n2, int n3, long l2, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        this(string2, string3, l, n, string4, n2, n3, l2, n4, n5, n6, n7, n8, bl);
        this.wifiSsid = wifiSsid;
    }

    public ScanResult(String string2, String string3, long l, int n, String string4, int n2, int n3, long l2, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        this.SSID = string2;
        this.BSSID = string3;
        this.hessid = l;
        this.anqpDomainId = n;
        this.capabilities = string4;
        this.level = n2;
        this.frequency = n3;
        this.timestamp = l2;
        this.distanceCm = n4;
        this.distanceSdCm = n5;
        this.channelWidth = n6;
        this.centerFreq0 = n7;
        this.centerFreq1 = n8;
        this.flags = bl ? 2L : 0L;
        this.isCarrierAp = false;
        this.carrierApEapType = -1;
        this.carrierName = null;
        this.radioChainInfos = null;
    }

    public static boolean is24GHz(int n) {
        boolean bl = n > 2400 && n < 2500;
        return bl;
    }

    public static boolean is5GHz(int n) {
        boolean bl = n > 4900 && n < 5900;
        return bl;
    }

    public void clearFlag(long l) {
        this.flags &= l;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean is24GHz() {
        return ScanResult.is24GHz(this.frequency);
    }

    public boolean is5GHz() {
        return ScanResult.is5GHz(this.frequency);
    }

    public boolean is80211mcResponder() {
        boolean bl = (this.flags & 2L) != 0L;
        return bl;
    }

    public boolean isPasspointNetwork() {
        boolean bl = (this.flags & 1L) != 0L;
        return bl;
    }

    public void setFlag(long l) {
        this.flags |= l;
    }

    public String toString() {
        Object object;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("SSID: ");
        Object object2 = object = this.wifiSsid;
        if (object == null) {
            object2 = "<unknown ssid>";
        }
        stringBuffer.append(object2);
        stringBuffer.append(", BSSID: ");
        object2 = object = this.BSSID;
        if (object == null) {
            object2 = "<none>";
        }
        stringBuffer.append((String)object2);
        stringBuffer.append(", capabilities: ");
        object2 = object = this.capabilities;
        if (object == null) {
            object2 = "<none>";
        }
        stringBuffer.append((String)object2);
        stringBuffer.append(", level: ");
        stringBuffer.append(this.level);
        stringBuffer.append(", frequency: ");
        stringBuffer.append(this.frequency);
        stringBuffer.append(", timestamp: ");
        stringBuffer.append(this.timestamp);
        stringBuffer.append(", distance: ");
        int n = this.distanceCm;
        object = "?";
        object2 = n != -1 ? Integer.valueOf(n) : "?";
        stringBuffer.append(object2);
        stringBuffer.append("(cm)");
        stringBuffer.append(", distanceSd: ");
        n = this.distanceSdCm;
        object2 = object;
        if (n != -1) {
            object2 = n;
        }
        stringBuffer.append(object2);
        stringBuffer.append("(cm)");
        stringBuffer.append(", passpoint: ");
        long l = this.flags;
        object = "yes";
        object2 = (l & 1L) != 0L ? "yes" : "no";
        stringBuffer.append((String)object2);
        stringBuffer.append(", ChannelBandwidth: ");
        stringBuffer.append(this.channelWidth);
        stringBuffer.append(", centerFreq0: ");
        stringBuffer.append(this.centerFreq0);
        stringBuffer.append(", centerFreq1: ");
        stringBuffer.append(this.centerFreq1);
        stringBuffer.append(", 80211mcResponder: ");
        object2 = (this.flags & 2L) != 0L ? "is supported" : "is not supported";
        stringBuffer.append((String)object2);
        stringBuffer.append(", Carrier AP: ");
        object2 = this.isCarrierAp ? object : "no";
        stringBuffer.append((String)object2);
        stringBuffer.append(", Carrier AP EAP Type: ");
        stringBuffer.append(this.carrierApEapType);
        stringBuffer.append(", Carrier name: ");
        stringBuffer.append(this.carrierName);
        stringBuffer.append(", Radio Chain Infos: ");
        stringBuffer.append(Arrays.toString(this.radioChainInfos));
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.wifiSsid != null) {
            parcel.writeInt(1);
            this.wifiSsid.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.SSID);
        parcel.writeString(this.BSSID);
        parcel.writeLong(this.hessid);
        parcel.writeInt(this.anqpDomainId);
        parcel.writeString(this.capabilities);
        parcel.writeInt(this.level);
        parcel.writeInt(this.frequency);
        parcel.writeLong(this.timestamp);
        parcel.writeInt(this.distanceCm);
        parcel.writeInt(this.distanceSdCm);
        parcel.writeInt(this.channelWidth);
        parcel.writeInt(this.centerFreq0);
        parcel.writeInt(this.centerFreq1);
        parcel.writeLong(this.seen);
        parcel.writeInt((int)this.untrusted);
        parcel.writeInt(this.numUsage);
        Object object = this.venueName;
        String object22 = "";
        object = object != null ? object.toString() : "";
        parcel.writeString((String)object);
        CharSequence charSequence = this.operatorFriendlyName;
        object = object22;
        if (charSequence != null) {
            object = charSequence.toString();
        }
        parcel.writeString((String)object);
        parcel.writeLong(this.flags);
        object = this.informationElements;
        if (object != null) {
            parcel.writeInt(((InformationElement[])object).length);
            for (n = 0; n < ((Object)(object = this.informationElements)).length; ++n) {
                parcel.writeInt(((InformationElement)object[n]).id);
                parcel.writeInt(this.informationElements[n].bytes.length);
                parcel.writeByteArray(this.informationElements[n].bytes);
            }
        } else {
            parcel.writeInt(0);
        }
        object = this.anqpLines;
        if (object != null) {
            parcel.writeInt(object.size());
            for (n = 0; n < this.anqpLines.size(); ++n) {
                parcel.writeString(this.anqpLines.get(n));
            }
        } else {
            parcel.writeInt(0);
        }
        object = this.anqpElements;
        if (object != null) {
            parcel.writeInt(((Object)object).length);
            for (AnqpInformationElement anqpInformationElement : this.anqpElements) {
                parcel.writeInt(anqpInformationElement.getVendorId());
                parcel.writeInt(anqpInformationElement.getElementId());
                parcel.writeInt(anqpInformationElement.getPayload().length);
                parcel.writeByteArray(anqpInformationElement.getPayload());
            }
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt((int)this.isCarrierAp);
        parcel.writeInt(this.carrierApEapType);
        parcel.writeString(this.carrierName);
        object = this.radioChainInfos;
        if (object != null) {
            parcel.writeInt(((Object)object).length);
            for (n = 0; n < ((Object)(object = this.radioChainInfos)).length; ++n) {
                parcel.writeInt(((RadioChainInfo)object[n]).id);
                parcel.writeInt(this.radioChainInfos[n].level);
            }
        } else {
            parcel.writeInt(0);
        }
    }

    public static class InformationElement {
        @UnsupportedAppUsage
        public static final int EID_BSS_LOAD = 11;
        @UnsupportedAppUsage
        public static final int EID_ERP = 42;
        @UnsupportedAppUsage
        public static final int EID_EXTENDED_CAPS = 127;
        @UnsupportedAppUsage
        public static final int EID_EXTENDED_SUPPORTED_RATES = 50;
        public static final int EID_HT_CAPABILITIES = 45;
        @UnsupportedAppUsage
        public static final int EID_HT_OPERATION = 61;
        @UnsupportedAppUsage
        public static final int EID_INTERWORKING = 107;
        @UnsupportedAppUsage
        public static final int EID_ROAMING_CONSORTIUM = 111;
        @UnsupportedAppUsage
        public static final int EID_RSN = 48;
        @UnsupportedAppUsage
        public static final int EID_SSID = 0;
        @UnsupportedAppUsage
        public static final int EID_SUPPORTED_RATES = 1;
        @UnsupportedAppUsage
        public static final int EID_TIM = 5;
        public static final int EID_VHT_CAPABILITIES = 191;
        @UnsupportedAppUsage
        public static final int EID_VHT_OPERATION = 192;
        @UnsupportedAppUsage
        public static final int EID_VSA = 221;
        @UnsupportedAppUsage
        public byte[] bytes;
        @UnsupportedAppUsage
        public int id;

        public InformationElement() {
        }

        public InformationElement(InformationElement informationElement) {
            this.id = informationElement.id;
            this.bytes = (byte[])informationElement.bytes.clone();
        }
    }

    public static class RadioChainInfo {
        public int id;
        public int level;

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof RadioChainInfo)) {
                return false;
            }
            object = (RadioChainInfo)object;
            if (this.id != ((RadioChainInfo)object).id || this.level != ((RadioChainInfo)object).level) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return Objects.hash(this.id, this.level);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RadioChainInfo: id=");
            stringBuilder.append(this.id);
            stringBuilder.append(", level=");
            stringBuilder.append(this.level);
            return stringBuilder.toString();
        }
    }

}

