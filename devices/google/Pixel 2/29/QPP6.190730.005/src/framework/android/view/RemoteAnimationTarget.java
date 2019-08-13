/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.app.WindowConfiguration;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RemoteAnimationTarget
implements Parcelable {
    public static final Parcelable.Creator<RemoteAnimationTarget> CREATOR = new Parcelable.Creator<RemoteAnimationTarget>(){

        @Override
        public RemoteAnimationTarget createFromParcel(Parcel parcel) {
            return new RemoteAnimationTarget(parcel);
        }

        public RemoteAnimationTarget[] newArray(int n) {
            return new RemoteAnimationTarget[n];
        }
    };
    public static final int MODE_CHANGING = 2;
    public static final int MODE_CLOSING = 1;
    public static final int MODE_OPENING = 0;
    @UnsupportedAppUsage
    public final Rect clipRect;
    @UnsupportedAppUsage
    public final Rect contentInsets;
    @UnsupportedAppUsage
    public boolean isNotInRecents;
    @UnsupportedAppUsage
    public final boolean isTranslucent;
    @UnsupportedAppUsage
    public final SurfaceControl leash;
    @UnsupportedAppUsage
    public final int mode;
    @UnsupportedAppUsage
    public final Point position;
    @UnsupportedAppUsage
    public final int prefixOrderIndex;
    @UnsupportedAppUsage
    public final Rect sourceContainerBounds;
    @UnsupportedAppUsage
    public final Rect startBounds;
    @UnsupportedAppUsage
    public final SurfaceControl startLeash;
    @UnsupportedAppUsage
    public final int taskId;
    @UnsupportedAppUsage
    public final WindowConfiguration windowConfiguration;

    public RemoteAnimationTarget(int n, int n2, SurfaceControl parcelable, boolean bl, Rect rect, Rect rect2, int n3, Point point, Rect rect3, WindowConfiguration windowConfiguration, boolean bl2, SurfaceControl surfaceControl, Rect rect4) {
        this.mode = n2;
        this.taskId = n;
        this.leash = parcelable;
        this.isTranslucent = bl;
        this.clipRect = new Rect(rect);
        this.contentInsets = new Rect(rect2);
        this.prefixOrderIndex = n3;
        this.position = new Point(point);
        this.sourceContainerBounds = new Rect(rect3);
        this.windowConfiguration = windowConfiguration;
        this.isNotInRecents = bl2;
        this.startLeash = surfaceControl;
        parcelable = rect4 == null ? null : new Rect(rect4);
        this.startBounds = parcelable;
    }

    public RemoteAnimationTarget(Parcel parcel) {
        this.taskId = parcel.readInt();
        this.mode = parcel.readInt();
        this.leash = (SurfaceControl)parcel.readParcelable(null);
        this.isTranslucent = parcel.readBoolean();
        this.clipRect = (Rect)parcel.readParcelable(null);
        this.contentInsets = (Rect)parcel.readParcelable(null);
        this.prefixOrderIndex = parcel.readInt();
        this.position = (Point)parcel.readParcelable(null);
        this.sourceContainerBounds = (Rect)parcel.readParcelable(null);
        this.windowConfiguration = (WindowConfiguration)parcel.readParcelable(null);
        this.isNotInRecents = parcel.readBoolean();
        this.startLeash = (SurfaceControl)parcel.readParcelable(null);
        this.startBounds = (Rect)parcel.readParcelable(null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(PrintWriter printWriter, String string2) {
        printWriter.print(string2);
        printWriter.print("mode=");
        printWriter.print(this.mode);
        printWriter.print(" taskId=");
        printWriter.print(this.taskId);
        printWriter.print(" isTranslucent=");
        printWriter.print(this.isTranslucent);
        printWriter.print(" clipRect=");
        this.clipRect.printShortString(printWriter);
        printWriter.print(" contentInsets=");
        this.contentInsets.printShortString(printWriter);
        printWriter.print(" prefixOrderIndex=");
        printWriter.print(this.prefixOrderIndex);
        printWriter.print(" position=");
        this.position.printShortString(printWriter);
        printWriter.print(" sourceContainerBounds=");
        this.sourceContainerBounds.printShortString(printWriter);
        printWriter.println();
        printWriter.print(string2);
        printWriter.print("windowConfiguration=");
        printWriter.println(this.windowConfiguration);
        printWriter.print(string2);
        printWriter.print("leash=");
        printWriter.println(this.leash);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.taskId);
        parcel.writeInt(this.mode);
        parcel.writeParcelable(this.leash, 0);
        parcel.writeBoolean(this.isTranslucent);
        parcel.writeParcelable(this.clipRect, 0);
        parcel.writeParcelable(this.contentInsets, 0);
        parcel.writeInt(this.prefixOrderIndex);
        parcel.writeParcelable(this.position, 0);
        parcel.writeParcelable(this.sourceContainerBounds, 0);
        parcel.writeParcelable(this.windowConfiguration, 0);
        parcel.writeBoolean(this.isNotInRecents);
        parcel.writeParcelable(this.startLeash, 0);
        parcel.writeParcelable(this.startBounds, 0);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1120986464257L, this.taskId);
        protoOutputStream.write(1120986464258L, this.mode);
        this.leash.writeToProto(protoOutputStream, 1146756268035L);
        protoOutputStream.write(1133871366148L, this.isTranslucent);
        this.clipRect.writeToProto(protoOutputStream, 1146756268037L);
        this.contentInsets.writeToProto(protoOutputStream, 1146756268038L);
        protoOutputStream.write(1120986464263L, this.prefixOrderIndex);
        this.position.writeToProto(protoOutputStream, 1146756268040L);
        this.sourceContainerBounds.writeToProto(protoOutputStream, 1146756268041L);
        this.windowConfiguration.writeToProto(protoOutputStream, 1146756268042L);
        Parcelable parcelable = this.startLeash;
        if (parcelable != null) {
            ((SurfaceControl)parcelable).writeToProto(protoOutputStream, 1146756268043L);
        }
        if ((parcelable = this.startBounds) != null) {
            ((Rect)parcelable).writeToProto(protoOutputStream, 1146756268044L);
        }
        protoOutputStream.end(l);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Mode {
    }

}

