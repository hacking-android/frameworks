/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.InsetsState;
import java.io.PrintWriter;

public class InsetsSource
implements Parcelable {
    public static final Parcelable.Creator<InsetsSource> CREATOR = new Parcelable.Creator<InsetsSource>(){

        @Override
        public InsetsSource createFromParcel(Parcel parcel) {
            return new InsetsSource(parcel);
        }

        public InsetsSource[] newArray(int n) {
            return new InsetsSource[n];
        }
    };
    private final Rect mFrame;
    private final Rect mTmpFrame = new Rect();
    private final int mType;
    private boolean mVisible;

    public InsetsSource(int n) {
        this.mType = n;
        this.mFrame = new Rect();
    }

    public InsetsSource(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mFrame = (Rect)parcel.readParcelable(null);
        this.mVisible = parcel.readBoolean();
    }

    public InsetsSource(InsetsSource insetsSource) {
        this.mType = insetsSource.mType;
        this.mFrame = new Rect(insetsSource.mFrame);
        this.mVisible = insetsSource.mVisible;
    }

    public Insets calculateInsets(Rect rect, boolean bl) {
        if (!bl && !this.mVisible) {
            return Insets.NONE;
        }
        if (!this.mTmpFrame.setIntersect(this.mFrame, rect)) {
            return Insets.NONE;
        }
        if (this.mTmpFrame.width() == rect.width()) {
            if (this.mTmpFrame.top == rect.top) {
                return Insets.of(0, this.mTmpFrame.height(), 0, 0);
            }
            return Insets.of(0, 0, 0, this.mTmpFrame.height());
        }
        if (this.mTmpFrame.height() == rect.height()) {
            if (this.mTmpFrame.left == rect.left) {
                return Insets.of(this.mTmpFrame.width(), 0, 0, 0);
            }
            return Insets.of(0, 0, this.mTmpFrame.width(), 0);
        }
        return Insets.NONE;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(String string2, PrintWriter printWriter) {
        printWriter.print(string2);
        printWriter.print("InsetsSource type=");
        printWriter.print(InsetsState.typeToString(this.mType));
        printWriter.print(" frame=");
        printWriter.print(this.mFrame.toShortString());
        printWriter.print(" visible=");
        printWriter.print(this.mVisible);
        printWriter.println();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (InsetsSource)object;
            if (this.mType != ((InsetsSource)object).mType) {
                return false;
            }
            if (this.mVisible != ((InsetsSource)object).mVisible) {
                return false;
            }
            return this.mFrame.equals(((InsetsSource)object).mFrame);
        }
        return false;
    }

    public Rect getFrame() {
        return this.mFrame;
    }

    public int getType() {
        return this.mType;
    }

    public int hashCode() {
        return (this.mType * 31 + this.mFrame.hashCode()) * 31 + this.mVisible;
    }

    public boolean isVisible() {
        return this.mVisible;
    }

    public void setFrame(Rect rect) {
        this.mFrame.set(rect);
    }

    public void setVisible(boolean bl) {
        this.mVisible = bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mFrame, 0);
        parcel.writeBoolean(this.mVisible);
    }

}

