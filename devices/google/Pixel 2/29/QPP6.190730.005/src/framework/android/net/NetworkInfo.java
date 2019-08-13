/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.annotation.UnsupportedAppUsage;
import android.net.ConnectivityManager;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.annotations.VisibleForTesting;
import java.util.EnumMap;

@Deprecated
public class NetworkInfo
implements Parcelable {
    public static final Parcelable.Creator<NetworkInfo> CREATOR;
    private static final EnumMap<DetailedState, State> stateMap;
    private DetailedState mDetailedState;
    private String mExtraInfo;
    private boolean mIsAvailable;
    private boolean mIsFailover;
    private boolean mIsRoaming;
    private int mNetworkType;
    private String mReason;
    private State mState;
    private int mSubtype;
    private String mSubtypeName;
    private String mTypeName;

    static {
        stateMap = new EnumMap(DetailedState.class);
        stateMap.put(DetailedState.IDLE, State.DISCONNECTED);
        stateMap.put(DetailedState.SCANNING, State.DISCONNECTED);
        stateMap.put(DetailedState.CONNECTING, State.CONNECTING);
        stateMap.put(DetailedState.AUTHENTICATING, State.CONNECTING);
        stateMap.put(DetailedState.OBTAINING_IPADDR, State.CONNECTING);
        stateMap.put(DetailedState.VERIFYING_POOR_LINK, State.CONNECTING);
        stateMap.put(DetailedState.CAPTIVE_PORTAL_CHECK, State.CONNECTING);
        stateMap.put(DetailedState.CONNECTED, State.CONNECTED);
        stateMap.put(DetailedState.SUSPENDED, State.SUSPENDED);
        stateMap.put(DetailedState.DISCONNECTING, State.DISCONNECTING);
        stateMap.put(DetailedState.DISCONNECTED, State.DISCONNECTED);
        stateMap.put(DetailedState.FAILED, State.DISCONNECTED);
        stateMap.put(DetailedState.BLOCKED, State.DISCONNECTED);
        CREATOR = new Parcelable.Creator<NetworkInfo>(){

            @Override
            public NetworkInfo createFromParcel(Parcel parcel) {
                NetworkInfo networkInfo = new NetworkInfo(parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readString());
                networkInfo.mState = State.valueOf(parcel.readString());
                networkInfo.mDetailedState = DetailedState.valueOf(parcel.readString());
                int n = parcel.readInt();
                boolean bl = true;
                boolean bl2 = n != 0;
                networkInfo.mIsFailover = bl2;
                bl2 = parcel.readInt() != 0;
                networkInfo.mIsAvailable = bl2;
                bl2 = parcel.readInt() != 0 ? bl : false;
                networkInfo.mIsRoaming = bl2;
                networkInfo.mReason = parcel.readString();
                networkInfo.mExtraInfo = parcel.readString();
                return networkInfo;
            }

            public NetworkInfo[] newArray(int n) {
                return new NetworkInfo[n];
            }
        };
    }

    @UnsupportedAppUsage
    public NetworkInfo(int n, int n2, String charSequence, String string2) {
        if (!ConnectivityManager.isNetworkTypeValid(n) && n != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid network type: ");
            ((StringBuilder)charSequence).append(n);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        this.mNetworkType = n;
        this.mSubtype = n2;
        this.mTypeName = charSequence;
        this.mSubtypeName = string2;
        this.setDetailedState(DetailedState.IDLE, null, null);
        this.mState = State.UNKNOWN;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public NetworkInfo(NetworkInfo networkInfo) {
        if (networkInfo == null) return;
        synchronized (networkInfo) {
            this.mNetworkType = networkInfo.mNetworkType;
            this.mSubtype = networkInfo.mSubtype;
            this.mTypeName = networkInfo.mTypeName;
            this.mSubtypeName = networkInfo.mSubtypeName;
            this.mState = networkInfo.mState;
            this.mDetailedState = networkInfo.mDetailedState;
            this.mReason = networkInfo.mReason;
            this.mExtraInfo = networkInfo.mExtraInfo;
            this.mIsFailover = networkInfo.mIsFailover;
            this.mIsAvailable = networkInfo.mIsAvailable;
            this.mIsRoaming = networkInfo.mIsRoaming;
            return;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public DetailedState getDetailedState() {
        synchronized (this) {
            return this.mDetailedState;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public String getExtraInfo() {
        synchronized (this) {
            return this.mExtraInfo;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getReason() {
        synchronized (this) {
            return this.mReason;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public State getState() {
        synchronized (this) {
            return this.mState;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public int getSubtype() {
        synchronized (this) {
            return this.mSubtype;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public String getSubtypeName() {
        synchronized (this) {
            return this.mSubtypeName;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public int getType() {
        synchronized (this) {
            return this.mNetworkType;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public String getTypeName() {
        synchronized (this) {
            return this.mTypeName;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean isAvailable() {
        synchronized (this) {
            return this.mIsAvailable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean isConnected() {
        synchronized (this) {
            if (this.mState != State.CONNECTED) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean isConnectedOrConnecting() {
        synchronized (this) {
            if (this.mState == State.CONNECTED) return true;
            if (this.mState != State.CONNECTING) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean isFailover() {
        synchronized (this) {
            return this.mIsFailover;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public boolean isRoaming() {
        synchronized (this) {
            return this.mIsRoaming;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public void setDetailedState(DetailedState detailedState, String string2, String string3) {
        synchronized (this) {
            this.mDetailedState = detailedState;
            this.mState = stateMap.get((Object)detailedState);
            this.mReason = string2;
            this.mExtraInfo = string3;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setExtraInfo(String string2) {
        synchronized (this) {
            this.mExtraInfo = string2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public void setFailover(boolean bl) {
        synchronized (this) {
            this.mIsFailover = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    public void setIsAvailable(boolean bl) {
        synchronized (this) {
            this.mIsAvailable = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    @UnsupportedAppUsage
    @VisibleForTesting
    public void setRoaming(boolean bl) {
        synchronized (this) {
            this.mIsRoaming = bl;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void setSubtype(int n, String string2) {
        synchronized (this) {
            this.mSubtype = n;
            this.mSubtypeName = string2;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Deprecated
    public void setType(int n) {
        synchronized (this) {
            this.mNetworkType = n;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder("[");
            stringBuilder.append("type: ");
            stringBuilder.append(this.getTypeName());
            stringBuilder.append("[");
            stringBuilder.append(this.getSubtypeName());
            stringBuilder.append("], state: ");
            stringBuilder.append((Object)this.mState);
            stringBuilder.append("/");
            stringBuilder.append((Object)this.mDetailedState);
            stringBuilder.append(", reason: ");
            String string2 = this.mReason == null ? "(unspecified)" : this.mReason;
            stringBuilder.append(string2);
            stringBuilder.append(", extra: ");
            string2 = this.mExtraInfo == null ? "(none)" : this.mExtraInfo;
            stringBuilder.append(string2);
            stringBuilder.append(", failover: ");
            stringBuilder.append(this.mIsFailover);
            stringBuilder.append(", available: ");
            stringBuilder.append(this.mIsAvailable);
            stringBuilder.append(", roaming: ");
            stringBuilder.append(this.mIsRoaming);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void writeToParcel(Parcel parcel, int n) {
        synchronized (this) {
            parcel.writeInt(this.mNetworkType);
            parcel.writeInt(this.mSubtype);
            parcel.writeString(this.mTypeName);
            parcel.writeString(this.mSubtypeName);
            parcel.writeString(this.mState.name());
            parcel.writeString(this.mDetailedState.name());
            boolean bl = this.mIsFailover;
            int n2 = 1;
            n = bl ? 1 : 0;
            parcel.writeInt(n);
            n = this.mIsAvailable ? 1 : 0;
            parcel.writeInt(n);
            n = this.mIsRoaming ? n2 : 0;
            parcel.writeInt(n);
            parcel.writeString(this.mReason);
            parcel.writeString(this.mExtraInfo);
            return;
        }
    }

    @Deprecated
    public static enum DetailedState {
        IDLE,
        SCANNING,
        CONNECTING,
        AUTHENTICATING,
        OBTAINING_IPADDR,
        CONNECTED,
        SUSPENDED,
        DISCONNECTING,
        DISCONNECTED,
        FAILED,
        BLOCKED,
        VERIFYING_POOR_LINK,
        CAPTIVE_PORTAL_CHECK;
        
    }

    @Deprecated
    public static enum State {
        CONNECTING,
        CONNECTED,
        SUSPENDED,
        DISCONNECTING,
        DISCONNECTED,
        UNKNOWN;
        
    }

}

