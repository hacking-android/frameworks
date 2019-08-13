/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;

public class InsetsSourceControl
implements Parcelable {
    public static final Parcelable.Creator<InsetsSourceControl> CREATOR = new Parcelable.Creator<InsetsSourceControl>(){

        @Override
        public InsetsSourceControl createFromParcel(Parcel parcel) {
            return new InsetsSourceControl(parcel);
        }

        public InsetsSourceControl[] newArray(int n) {
            return new InsetsSourceControl[n];
        }
    };
    private final SurfaceControl mLeash;
    private final Point mSurfacePosition;
    private final int mType;

    public InsetsSourceControl(int n, SurfaceControl surfaceControl, Point point) {
        this.mType = n;
        this.mLeash = surfaceControl;
        this.mSurfacePosition = point;
    }

    public InsetsSourceControl(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mLeash = (SurfaceControl)parcel.readParcelable(null);
        this.mSurfacePosition = (Point)parcel.readParcelable(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public SurfaceControl getLeash() {
        return this.mLeash;
    }

    public Point getSurfacePosition() {
        return this.mSurfacePosition;
    }

    public int getType() {
        return this.mType;
    }

    public boolean setSurfacePosition(int n, int n2) {
        if (this.mSurfacePosition.equals(n, n2)) {
            return false;
        }
        this.mSurfacePosition.set(n, n2);
        return true;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mLeash, 0);
        parcel.writeParcelable(this.mSurfacePosition, 0);
    }

}

