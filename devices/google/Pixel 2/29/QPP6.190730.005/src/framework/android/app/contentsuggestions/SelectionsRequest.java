/*
 * Decompiled with CFR 0.145.
 */
package android.app.contentsuggestions;

import android.annotation.SystemApi;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@SystemApi
public final class SelectionsRequest
implements Parcelable {
    public static final Parcelable.Creator<SelectionsRequest> CREATOR = new Parcelable.Creator<SelectionsRequest>(){

        @Override
        public SelectionsRequest createFromParcel(Parcel parcel) {
            return new SelectionsRequest(parcel.readInt(), parcel.readTypedObject(Point.CREATOR), parcel.readBundle());
        }

        public SelectionsRequest[] newArray(int n) {
            return new SelectionsRequest[n];
        }
    };
    private final Bundle mExtras;
    private final Point mInterestPoint;
    private final int mTaskId;

    private SelectionsRequest(int n, Point point, Bundle bundle) {
        this.mTaskId = n;
        this.mInterestPoint = point;
        this.mExtras = bundle;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Bundle getExtras() {
        Bundle bundle;
        Bundle bundle2 = bundle = this.mExtras;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        return bundle2;
    }

    public Point getInterestPoint() {
        return this.mInterestPoint;
    }

    public int getTaskId() {
        return this.mTaskId;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mTaskId);
        parcel.writeTypedObject(this.mInterestPoint, n);
        parcel.writeBundle(this.mExtras);
    }

    @SystemApi
    public static final class Builder {
        private Bundle mExtras;
        private Point mInterestPoint;
        private final int mTaskId;

        public Builder(int n) {
            this.mTaskId = n;
        }

        public SelectionsRequest build() {
            return new SelectionsRequest(this.mTaskId, this.mInterestPoint, this.mExtras);
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setInterestPoint(Point point) {
            this.mInterestPoint = point;
            return this;
        }
    }

}

