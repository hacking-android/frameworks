/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WifiP2pGroup
implements Parcelable {
    public static final Parcelable.Creator<WifiP2pGroup> CREATOR;
    public static final int PERSISTENT_NET_ID = -2;
    @UnsupportedAppUsage
    public static final int TEMPORARY_NET_ID = -1;
    private static final Pattern groupStartedPattern;
    private List<WifiP2pDevice> mClients;
    private int mFrequency;
    private String mInterface;
    private boolean mIsGroupOwner;
    private int mNetId;
    private String mNetworkName;
    private WifiP2pDevice mOwner;
    private String mPassphrase;

    static {
        groupStartedPattern = Pattern.compile("ssid=\"(.+)\" freq=(\\d+) (?:psk=)?([0-9a-fA-F]{64})?(?:passphrase=)?(?:\"(.{0,63})\")? go_dev_addr=((?:[0-9a-f]{2}:){5}[0-9a-f]{2}) ?(\\[PERSISTENT\\])?");
        CREATOR = new Parcelable.Creator<WifiP2pGroup>(){

            @Override
            public WifiP2pGroup createFromParcel(Parcel parcel) {
                WifiP2pGroup wifiP2pGroup = new WifiP2pGroup();
                wifiP2pGroup.setNetworkName(parcel.readString());
                wifiP2pGroup.setOwner((WifiP2pDevice)parcel.readParcelable(null));
                int n = parcel.readByte();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                wifiP2pGroup.setIsGroupOwner(bl);
                int n2 = parcel.readInt();
                for (n = 0; n < n2; ++n) {
                    wifiP2pGroup.addClient((WifiP2pDevice)parcel.readParcelable(null));
                }
                wifiP2pGroup.setPassphrase(parcel.readString());
                wifiP2pGroup.setInterface(parcel.readString());
                wifiP2pGroup.setNetworkId(parcel.readInt());
                wifiP2pGroup.setFrequency(parcel.readInt());
                return wifiP2pGroup;
            }

            public WifiP2pGroup[] newArray(int n) {
                return new WifiP2pGroup[n];
            }
        };
    }

    public WifiP2pGroup() {
        this.mClients = new ArrayList<WifiP2pDevice>();
    }

    public WifiP2pGroup(WifiP2pGroup wifiP2pGroup) {
        this.mClients = new ArrayList<WifiP2pDevice>();
        if (wifiP2pGroup != null) {
            this.mNetworkName = wifiP2pGroup.getNetworkName();
            this.mOwner = new WifiP2pDevice(wifiP2pGroup.getOwner());
            this.mIsGroupOwner = wifiP2pGroup.mIsGroupOwner;
            for (WifiP2pDevice wifiP2pDevice : wifiP2pGroup.getClientList()) {
                this.mClients.add(wifiP2pDevice);
            }
            this.mPassphrase = wifiP2pGroup.getPassphrase();
            this.mInterface = wifiP2pGroup.getInterface();
            this.mNetId = wifiP2pGroup.getNetworkId();
            this.mFrequency = wifiP2pGroup.getFrequency();
        }
    }

    @UnsupportedAppUsage
    public WifiP2pGroup(String arrstring) throws IllegalArgumentException {
        block5 : {
            block8 : {
                block7 : {
                    String[] arrstring2;
                    block6 : {
                        this.mClients = new ArrayList<WifiP2pDevice>();
                        arrstring2 = arrstring.split(" ");
                        if (arrstring2.length < 3) break block5;
                        if (!arrstring2[0].startsWith("P2P-GROUP")) break block6;
                        this.mInterface = arrstring2[1];
                        this.mIsGroupOwner = arrstring2[2].equals("GO");
                        if (!(arrstring = groupStartedPattern.matcher((CharSequence)arrstring)).find()) {
                            return;
                        }
                        this.mNetworkName = arrstring.group(1);
                        this.mFrequency = Integer.parseInt(arrstring.group(2));
                        this.mPassphrase = arrstring.group(4);
                        this.mOwner = new WifiP2pDevice(arrstring.group(5));
                        this.mNetId = arrstring.group(6) != null ? -2 : -1;
                        break block7;
                    }
                    if (arrstring2[0].equals("P2P-INVITATION-RECEIVED")) {
                        this.mNetId = -2;
                        int n = arrstring2.length;
                        for (int i = 0; i < n; ++i) {
                            arrstring = arrstring2[i].split("=");
                            if (arrstring.length != 2) continue;
                            if (arrstring[0].equals("sa")) {
                                Object object = arrstring[1];
                                object = new WifiP2pDevice();
                                ((WifiP2pDevice)object).deviceAddress = arrstring[1];
                                this.mClients.add((WifiP2pDevice)object);
                                continue;
                            }
                            if (arrstring[0].equals("go_dev_addr")) {
                                this.mOwner = new WifiP2pDevice(arrstring[1]);
                                continue;
                            }
                            if (!arrstring[0].equals("persistent")) continue;
                            this.mNetId = Integer.parseInt(arrstring[1]);
                        }
                    }
                    break block8;
                }
                return;
            }
            throw new IllegalArgumentException("Malformed supplicant event");
        }
        throw new IllegalArgumentException("Malformed supplicant event");
    }

    public void addClient(WifiP2pDevice wifiP2pDevice) {
        Iterator<WifiP2pDevice> iterator = this.mClients.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().equals(wifiP2pDevice)) continue;
            return;
        }
        this.mClients.add(wifiP2pDevice);
    }

    public void addClient(String string2) {
        this.addClient(new WifiP2pDevice(string2));
    }

    public boolean contains(WifiP2pDevice wifiP2pDevice) {
        return this.mOwner.equals(wifiP2pDevice) || this.mClients.contains(wifiP2pDevice);
        {
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Collection<WifiP2pDevice> getClientList() {
        return Collections.unmodifiableCollection(this.mClients);
    }

    public int getFrequency() {
        return this.mFrequency;
    }

    public String getInterface() {
        return this.mInterface;
    }

    @UnsupportedAppUsage
    public int getNetworkId() {
        return this.mNetId;
    }

    public String getNetworkName() {
        return this.mNetworkName;
    }

    public WifiP2pDevice getOwner() {
        return this.mOwner;
    }

    public String getPassphrase() {
        return this.mPassphrase;
    }

    @UnsupportedAppUsage
    public boolean isClientListEmpty() {
        boolean bl = this.mClients.size() == 0;
        return bl;
    }

    public boolean isGroupOwner() {
        return this.mIsGroupOwner;
    }

    public boolean removeClient(WifiP2pDevice wifiP2pDevice) {
        return this.mClients.remove(wifiP2pDevice);
    }

    public boolean removeClient(String string2) {
        return this.mClients.remove(new WifiP2pDevice(string2));
    }

    public void setFrequency(int n) {
        this.mFrequency = n;
    }

    @UnsupportedAppUsage
    public void setInterface(String string2) {
        this.mInterface = string2;
    }

    @UnsupportedAppUsage
    public void setIsGroupOwner(boolean bl) {
        this.mIsGroupOwner = bl;
    }

    @UnsupportedAppUsage
    public void setNetworkId(int n) {
        this.mNetId = n;
    }

    public void setNetworkName(String string2) {
        this.mNetworkName = string2;
    }

    public void setOwner(WifiP2pDevice wifiP2pDevice) {
        this.mOwner = wifiP2pDevice;
    }

    public void setPassphrase(String string2) {
        this.mPassphrase = string2;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("network: ");
        stringBuffer.append(this.mNetworkName);
        stringBuffer.append("\n isGO: ");
        stringBuffer.append(this.mIsGroupOwner);
        stringBuffer.append("\n GO: ");
        stringBuffer.append(this.mOwner);
        for (WifiP2pDevice wifiP2pDevice : this.mClients) {
            stringBuffer.append("\n Client: ");
            stringBuffer.append(wifiP2pDevice);
        }
        stringBuffer.append("\n interface: ");
        stringBuffer.append(this.mInterface);
        stringBuffer.append("\n networkId: ");
        stringBuffer.append(this.mNetId);
        stringBuffer.append("\n frequency: ");
        stringBuffer.append(this.mFrequency);
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mNetworkName);
        parcel.writeParcelable(this.mOwner, n);
        parcel.writeByte((byte)this.mIsGroupOwner);
        parcel.writeInt(this.mClients.size());
        Iterator<WifiP2pDevice> iterator = this.mClients.iterator();
        while (iterator.hasNext()) {
            parcel.writeParcelable(iterator.next(), n);
        }
        parcel.writeString(this.mPassphrase);
        parcel.writeString(this.mInterface);
        parcel.writeInt(this.mNetId);
        parcel.writeInt(this.mFrequency);
    }

}

