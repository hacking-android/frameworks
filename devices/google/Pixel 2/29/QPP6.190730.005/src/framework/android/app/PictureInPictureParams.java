/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.RemoteAction;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Rational;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class PictureInPictureParams
implements Parcelable {
    public static final Parcelable.Creator<PictureInPictureParams> CREATOR = new Parcelable.Creator<PictureInPictureParams>(){

        @Override
        public PictureInPictureParams createFromParcel(Parcel parcel) {
            return new PictureInPictureParams(parcel);
        }

        public PictureInPictureParams[] newArray(int n) {
            return new PictureInPictureParams[n];
        }
    };
    private Rational mAspectRatio;
    private Rect mSourceRectHint;
    private List<RemoteAction> mUserActions;

    PictureInPictureParams() {
    }

    PictureInPictureParams(Parcel parcel) {
        if (parcel.readInt() != 0) {
            this.mAspectRatio = new Rational(parcel.readInt(), parcel.readInt());
        }
        if (parcel.readInt() != 0) {
            this.mUserActions = new ArrayList<RemoteAction>();
            parcel.readParcelableList(this.mUserActions, RemoteAction.class.getClassLoader());
        }
        if (parcel.readInt() != 0) {
            this.mSourceRectHint = Rect.CREATOR.createFromParcel(parcel);
        }
    }

    PictureInPictureParams(Rational rational, List<RemoteAction> list, Rect rect) {
        this.mAspectRatio = rational;
        this.mUserActions = list;
        this.mSourceRectHint = rect;
    }

    public void copyOnlySet(PictureInPictureParams pictureInPictureParams) {
        if (pictureInPictureParams.hasSetAspectRatio()) {
            this.mAspectRatio = pictureInPictureParams.mAspectRatio;
        }
        if (pictureInPictureParams.hasSetActions()) {
            this.mUserActions = pictureInPictureParams.mUserActions;
        }
        if (pictureInPictureParams.hasSourceBoundsHint()) {
            this.mSourceRectHint = new Rect(pictureInPictureParams.getSourceRectHint());
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<RemoteAction> getActions() {
        return this.mUserActions;
    }

    public float getAspectRatio() {
        Rational rational = this.mAspectRatio;
        if (rational != null) {
            return rational.floatValue();
        }
        return 0.0f;
    }

    public Rational getAspectRatioRational() {
        return this.mAspectRatio;
    }

    public Rect getSourceRectHint() {
        return this.mSourceRectHint;
    }

    public boolean hasSetActions() {
        boolean bl = this.mUserActions != null;
        return bl;
    }

    public boolean hasSetAspectRatio() {
        boolean bl = this.mAspectRatio != null;
        return bl;
    }

    public boolean hasSourceBoundsHint() {
        Rect rect = this.mSourceRectHint;
        boolean bl = rect != null && !rect.isEmpty();
        return bl;
    }

    public void truncateActions(int n) {
        if (this.hasSetActions()) {
            List<RemoteAction> list = this.mUserActions;
            this.mUserActions = list.subList(0, Math.min(list.size(), n));
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        if (this.mAspectRatio != null) {
            parcel.writeInt(1);
            parcel.writeInt(this.mAspectRatio.getNumerator());
            parcel.writeInt(this.mAspectRatio.getDenominator());
        } else {
            parcel.writeInt(0);
        }
        if (this.mUserActions != null) {
            parcel.writeInt(1);
            parcel.writeParcelableList(this.mUserActions, 0);
        } else {
            parcel.writeInt(0);
        }
        if (this.mSourceRectHint != null) {
            parcel.writeInt(1);
            this.mSourceRectHint.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(0);
        }
    }

    public static class Builder {
        private Rational mAspectRatio;
        private Rect mSourceRectHint;
        private List<RemoteAction> mUserActions;

        public PictureInPictureParams build() {
            return new PictureInPictureParams(this.mAspectRatio, this.mUserActions, this.mSourceRectHint);
        }

        public Builder setActions(List<RemoteAction> list) {
            if (this.mUserActions != null) {
                this.mUserActions = null;
            }
            if (list != null) {
                this.mUserActions = new ArrayList<RemoteAction>(list);
            }
            return this;
        }

        public Builder setAspectRatio(Rational rational) {
            this.mAspectRatio = rational;
            return this;
        }

        public Builder setSourceRectHint(Rect rect) {
            this.mSourceRectHint = rect == null ? null : new Rect(rect);
            return this;
        }
    }

}

