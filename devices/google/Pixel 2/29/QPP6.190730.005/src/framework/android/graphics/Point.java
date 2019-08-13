/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;

public class Point
implements Parcelable {
    public static final Parcelable.Creator<Point> CREATOR = new Parcelable.Creator<Point>(){

        @Override
        public Point createFromParcel(Parcel parcel) {
            Point point = new Point();
            point.readFromParcel(parcel);
            return point;
        }

        public Point[] newArray(int n) {
            return new Point[n];
        }
    };
    public int x;
    public int y;

    public Point() {
    }

    public Point(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public static Point convert(Size size) {
        return new Point(size.getWidth(), size.getHeight());
    }

    public static Size convert(Point point) {
        return new Size(point.x, point.y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final boolean equals(int n, int n2) {
        boolean bl = this.x == n && this.y == n2;
        return bl;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Point)object;
            if (this.x != ((Point)object).x) {
                return false;
            }
            return this.y == ((Point)object).y;
        }
        return false;
    }

    public int hashCode() {
        return this.x * 31 + this.y;
    }

    public final void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public final void offset(int n, int n2) {
        this.x += n;
        this.y += n2;
    }

    public void printShortString(PrintWriter printWriter) {
        printWriter.print("[");
        printWriter.print(this.x);
        printWriter.print(",");
        printWriter.print(this.y);
        printWriter.print("]");
    }

    public void readFromParcel(Parcel parcel) {
        this.x = parcel.readInt();
        this.y = parcel.readInt();
    }

    public void set(int n, int n2) {
        this.x = n;
        this.y = n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Point(");
        stringBuilder.append(this.x);
        stringBuilder.append(", ");
        stringBuilder.append(this.y);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.x);
        parcel.writeInt(this.y);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1120986464257L, this.x);
        protoOutputStream.write(1120986464258L, this.y);
        protoOutputStream.end(l);
    }

}

