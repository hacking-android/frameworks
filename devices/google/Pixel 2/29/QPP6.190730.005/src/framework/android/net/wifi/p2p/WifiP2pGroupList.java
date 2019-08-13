/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.p2p;

import android.annotation.UnsupportedAppUsage;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.LruCache;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WifiP2pGroupList
implements Parcelable {
    public static final Parcelable.Creator<WifiP2pGroupList> CREATOR = new Parcelable.Creator<WifiP2pGroupList>(){

        @Override
        public WifiP2pGroupList createFromParcel(Parcel parcel) {
            WifiP2pGroupList wifiP2pGroupList = new WifiP2pGroupList();
            int n = parcel.readInt();
            for (int i = 0; i < n; ++i) {
                wifiP2pGroupList.add((WifiP2pGroup)parcel.readParcelable(null));
            }
            return wifiP2pGroupList;
        }

        public WifiP2pGroupList[] newArray(int n) {
            return new WifiP2pGroupList[n];
        }
    };
    private static final int CREDENTIAL_MAX_NUM = 32;
    private boolean isClearCalled = false;
    @UnsupportedAppUsage
    private final LruCache<Integer, WifiP2pGroup> mGroups;
    private final GroupDeleteListener mListener;

    public WifiP2pGroupList() {
        this(null, null);
    }

    @UnsupportedAppUsage
    public WifiP2pGroupList(WifiP2pGroupList object, GroupDeleteListener object22) {
        this.mListener = object22;
        this.mGroups = new LruCache<Integer, WifiP2pGroup>(32){

            @Override
            protected void entryRemoved(boolean bl, Integer n, WifiP2pGroup wifiP2pGroup, WifiP2pGroup wifiP2pGroup2) {
                if (WifiP2pGroupList.this.mListener != null && !WifiP2pGroupList.this.isClearCalled) {
                    WifiP2pGroupList.this.mListener.onDeleteGroup(wifiP2pGroup.getNetworkId());
                }
            }
        };
        if (object != null) {
            for (Map.Entry<Integer, WifiP2pGroup> entry : ((WifiP2pGroupList)object).mGroups.snapshot().entrySet()) {
                this.mGroups.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public void add(WifiP2pGroup wifiP2pGroup) {
        this.mGroups.put(wifiP2pGroup.getNetworkId(), wifiP2pGroup);
    }

    public boolean clear() {
        if (this.mGroups.size() == 0) {
            return false;
        }
        this.isClearCalled = true;
        this.mGroups.evictAll();
        this.isClearCalled = false;
        return true;
    }

    public boolean contains(int n) {
        Iterator<WifiP2pGroup> iterator = this.mGroups.snapshot().values().iterator();
        while (iterator.hasNext()) {
            if (n != iterator.next().getNetworkId()) continue;
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public Collection<WifiP2pGroup> getGroupList() {
        return this.mGroups.snapshot().values();
    }

    public int getNetworkId(String string2) {
        if (string2 == null) {
            return -1;
        }
        for (WifiP2pGroup wifiP2pGroup : this.mGroups.snapshot().values()) {
            if (!string2.equalsIgnoreCase(wifiP2pGroup.getOwner().deviceAddress)) continue;
            this.mGroups.get(wifiP2pGroup.getNetworkId());
            return wifiP2pGroup.getNetworkId();
        }
        return -1;
    }

    public int getNetworkId(String string2, String string3) {
        if (string2 != null && string3 != null) {
            for (WifiP2pGroup wifiP2pGroup : this.mGroups.snapshot().values()) {
                if (!string2.equalsIgnoreCase(wifiP2pGroup.getOwner().deviceAddress) || !string3.equals(wifiP2pGroup.getNetworkName())) continue;
                this.mGroups.get(wifiP2pGroup.getNetworkId());
                return wifiP2pGroup.getNetworkId();
            }
            return -1;
        }
        return -1;
    }

    public String getOwnerAddr(int n) {
        WifiP2pGroup wifiP2pGroup = this.mGroups.get(n);
        if (wifiP2pGroup != null) {
            return wifiP2pGroup.getOwner().deviceAddress;
        }
        return null;
    }

    public void remove(int n) {
        this.mGroups.remove(n);
    }

    void remove(String string2) {
        this.remove(this.getNetworkId(string2));
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator<WifiP2pGroup> iterator = this.mGroups.snapshot().values().iterator();
        while (iterator.hasNext()) {
            stringBuffer.append(iterator.next());
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        Object object = this.mGroups.snapshot().values();
        parcel.writeInt(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            parcel.writeParcelable((WifiP2pGroup)object.next(), n);
        }
    }

    public static interface GroupDeleteListener {
        public void onDeleteGroup(int var1);
    }

}

