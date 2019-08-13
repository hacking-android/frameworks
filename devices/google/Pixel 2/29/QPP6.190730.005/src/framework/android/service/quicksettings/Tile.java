/*
 * Decompiled with CFR 0.145.
 */
package android.service.quicksettings;

import android.graphics.drawable.Icon;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.quicksettings.IQSService;
import android.text.TextUtils;
import android.util.Log;

public final class Tile
implements Parcelable {
    public static final Parcelable.Creator<Tile> CREATOR = new Parcelable.Creator<Tile>(){

        @Override
        public Tile createFromParcel(Parcel parcel) {
            return new Tile(parcel);
        }

        public Tile[] newArray(int n) {
            return new Tile[n];
        }
    };
    public static final int STATE_ACTIVE = 2;
    public static final int STATE_INACTIVE = 1;
    public static final int STATE_UNAVAILABLE = 0;
    private static final String TAG = "Tile";
    private CharSequence mContentDescription;
    private Icon mIcon;
    private CharSequence mLabel;
    private IQSService mService;
    private int mState = 1;
    private CharSequence mSubtitle;
    private IBinder mToken;

    public Tile() {
    }

    public Tile(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.mIcon = parcel.readByte() != 0 ? Icon.CREATOR.createFromParcel(parcel) : null;
        this.mState = parcel.readInt();
        this.mLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mSubtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mContentDescription = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public Icon getIcon() {
        return this.mIcon;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public int getState() {
        return this.mState;
    }

    public CharSequence getSubtitle() {
        return this.mSubtitle;
    }

    public void setContentDescription(CharSequence charSequence) {
        this.mContentDescription = charSequence;
    }

    public void setIcon(Icon icon) {
        this.mIcon = icon;
    }

    public void setLabel(CharSequence charSequence) {
        this.mLabel = charSequence;
    }

    public void setService(IQSService iQSService, IBinder iBinder) {
        this.mService = iQSService;
        this.mToken = iBinder;
    }

    public void setState(int n) {
        this.mState = n;
    }

    public void setSubtitle(CharSequence charSequence) {
        this.mSubtitle = charSequence;
    }

    public void updateTile() {
        try {
            this.mService.updateQsTile(this, this.mToken);
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Couldn't update tile");
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mIcon != null) {
            parcel.writeByte((byte)1);
            this.mIcon.writeToParcel(parcel, n);
        } else {
            parcel.writeByte((byte)0);
        }
        parcel.writeInt(this.mState);
        TextUtils.writeToParcel(this.mLabel, parcel, n);
        TextUtils.writeToParcel(this.mSubtitle, parcel, n);
        TextUtils.writeToParcel(this.mContentDescription, parcel, n);
    }

}

