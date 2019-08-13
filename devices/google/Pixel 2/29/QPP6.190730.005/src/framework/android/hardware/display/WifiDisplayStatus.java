/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.display;

import android.annotation.UnsupportedAppUsage;
import android.hardware.display.WifiDisplay;
import android.hardware.display.WifiDisplaySessionInfo;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public final class WifiDisplayStatus
implements Parcelable {
    public static final Parcelable.Creator<WifiDisplayStatus> CREATOR = new Parcelable.Creator<WifiDisplayStatus>(){

        @Override
        public WifiDisplayStatus createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            int n3 = parcel.readInt();
            WifiDisplay wifiDisplay = parcel.readInt() != 0 ? WifiDisplay.CREATOR.createFromParcel(parcel) : null;
            WifiDisplay[] arrwifiDisplay = WifiDisplay.CREATOR.newArray(parcel.readInt());
            for (int i = 0; i < arrwifiDisplay.length; ++i) {
                arrwifiDisplay[i] = WifiDisplay.CREATOR.createFromParcel(parcel);
            }
            return new WifiDisplayStatus(n, n2, n3, wifiDisplay, arrwifiDisplay, WifiDisplaySessionInfo.CREATOR.createFromParcel(parcel));
        }

        public WifiDisplayStatus[] newArray(int n) {
            return new WifiDisplayStatus[n];
        }
    };
    @UnsupportedAppUsage
    public static final int DISPLAY_STATE_CONNECTED = 2;
    @UnsupportedAppUsage
    public static final int DISPLAY_STATE_CONNECTING = 1;
    @UnsupportedAppUsage
    public static final int DISPLAY_STATE_NOT_CONNECTED = 0;
    public static final int FEATURE_STATE_DISABLED = 1;
    public static final int FEATURE_STATE_OFF = 2;
    @UnsupportedAppUsage
    public static final int FEATURE_STATE_ON = 3;
    public static final int FEATURE_STATE_UNAVAILABLE = 0;
    @UnsupportedAppUsage
    public static final int SCAN_STATE_NOT_SCANNING = 0;
    public static final int SCAN_STATE_SCANNING = 1;
    @UnsupportedAppUsage
    private final WifiDisplay mActiveDisplay;
    private final int mActiveDisplayState;
    @UnsupportedAppUsage
    private final WifiDisplay[] mDisplays;
    private final int mFeatureState;
    private final int mScanState;
    private final WifiDisplaySessionInfo mSessionInfo;

    public WifiDisplayStatus() {
        this(0, 0, 0, null, WifiDisplay.EMPTY_ARRAY, null);
    }

    public WifiDisplayStatus(int n, int n2, int n3, WifiDisplay parcelable, WifiDisplay[] arrwifiDisplay, WifiDisplaySessionInfo wifiDisplaySessionInfo) {
        if (arrwifiDisplay != null) {
            this.mFeatureState = n;
            this.mScanState = n2;
            this.mActiveDisplayState = n3;
            this.mActiveDisplay = parcelable;
            this.mDisplays = arrwifiDisplay;
            parcelable = wifiDisplaySessionInfo != null ? wifiDisplaySessionInfo : new WifiDisplaySessionInfo();
            this.mSessionInfo = parcelable;
            return;
        }
        throw new IllegalArgumentException("displays must not be null");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @UnsupportedAppUsage
    public WifiDisplay getActiveDisplay() {
        return this.mActiveDisplay;
    }

    @UnsupportedAppUsage
    public int getActiveDisplayState() {
        return this.mActiveDisplayState;
    }

    @UnsupportedAppUsage
    public WifiDisplay[] getDisplays() {
        return this.mDisplays;
    }

    @UnsupportedAppUsage
    public int getFeatureState() {
        return this.mFeatureState;
    }

    @UnsupportedAppUsage
    public int getScanState() {
        return this.mScanState;
    }

    public WifiDisplaySessionInfo getSessionInfo() {
        return this.mSessionInfo;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WifiDisplayStatus{featureState=");
        stringBuilder.append(this.mFeatureState);
        stringBuilder.append(", scanState=");
        stringBuilder.append(this.mScanState);
        stringBuilder.append(", activeDisplayState=");
        stringBuilder.append(this.mActiveDisplayState);
        stringBuilder.append(", activeDisplay=");
        stringBuilder.append(this.mActiveDisplay);
        stringBuilder.append(", displays=");
        stringBuilder.append(Arrays.toString(this.mDisplays));
        stringBuilder.append(", sessionInfo=");
        stringBuilder.append(this.mSessionInfo);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mFeatureState);
        parcel.writeInt(this.mScanState);
        parcel.writeInt(this.mActiveDisplayState);
        WifiDisplay[] arrwifiDisplay = this.mActiveDisplay;
        int n2 = 0;
        if (arrwifiDisplay != null) {
            parcel.writeInt(1);
            this.mActiveDisplay.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeInt(this.mDisplays.length);
        arrwifiDisplay = this.mDisplays;
        int n3 = arrwifiDisplay.length;
        while (n2 < n3) {
            arrwifiDisplay[n2].writeToParcel(parcel, n);
            ++n2;
        }
        this.mSessionInfo.writeToParcel(parcel, n);
    }

}

