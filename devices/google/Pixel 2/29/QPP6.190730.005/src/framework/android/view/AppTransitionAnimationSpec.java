/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.GraphicBuffer;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

public class AppTransitionAnimationSpec
implements Parcelable {
    public static final Parcelable.Creator<AppTransitionAnimationSpec> CREATOR = new Parcelable.Creator<AppTransitionAnimationSpec>(){

        @Override
        public AppTransitionAnimationSpec createFromParcel(Parcel parcel) {
            return new AppTransitionAnimationSpec(parcel);
        }

        public AppTransitionAnimationSpec[] newArray(int n) {
            return new AppTransitionAnimationSpec[n];
        }
    };
    public final GraphicBuffer buffer;
    public final Rect rect;
    public final int taskId;

    @UnsupportedAppUsage
    public AppTransitionAnimationSpec(int n, GraphicBuffer graphicBuffer, Rect rect) {
        this.taskId = n;
        this.rect = rect;
        this.buffer = graphicBuffer;
    }

    public AppTransitionAnimationSpec(Parcel parcel) {
        this.taskId = parcel.readInt();
        this.rect = (Rect)parcel.readParcelable(null);
        this.buffer = (GraphicBuffer)parcel.readParcelable(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{taskId: ");
        stringBuilder.append(this.taskId);
        stringBuilder.append(", buffer: ");
        stringBuilder.append(this.buffer);
        stringBuilder.append(", rect: ");
        stringBuilder.append(this.rect);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.taskId);
        parcel.writeParcelable(this.rect, 0);
        parcel.writeParcelable(this.buffer, 0);
    }

}

