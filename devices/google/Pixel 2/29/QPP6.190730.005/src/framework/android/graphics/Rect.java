/*
 * Decompiled with CFR 0.145.
 */
package android.graphics;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Insets;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.proto.ProtoInputStream;
import android.util.proto.ProtoOutputStream;
import android.util.proto.WireTypeMismatchException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class Rect
implements Parcelable {
    public static final Parcelable.Creator<Rect> CREATOR = new Parcelable.Creator<Rect>(){

        @Override
        public Rect createFromParcel(Parcel parcel) {
            Rect rect = new Rect();
            rect.readFromParcel(parcel);
            return rect;
        }

        public Rect[] newArray(int n) {
            return new Rect[n];
        }
    };
    public int bottom;
    public int left;
    public int right;
    public int top;

    public Rect() {
    }

    public Rect(int n, int n2, int n3, int n4) {
        this.left = n;
        this.top = n2;
        this.right = n3;
        this.bottom = n4;
    }

    public Rect(Insets insets) {
        if (insets == null) {
            this.bottom = 0;
            this.right = 0;
            this.top = 0;
            this.left = 0;
        } else {
            this.left = insets.left;
            this.top = insets.top;
            this.right = insets.right;
            this.bottom = insets.bottom;
        }
    }

    public Rect(Rect rect) {
        if (rect == null) {
            this.bottom = 0;
            this.right = 0;
            this.top = 0;
            this.left = 0;
        } else {
            this.left = rect.left;
            this.top = rect.top;
            this.right = rect.right;
            this.bottom = rect.bottom;
        }
    }

    public static Rect copyOrNull(Rect rect) {
        rect = rect == null ? null : new Rect(rect);
        return rect;
    }

    public static boolean intersects(Rect rect, Rect rect2) {
        boolean bl = rect.left < rect2.right && rect2.left < rect.right && rect.top < rect2.bottom && rect2.top < rect.bottom;
        return bl;
    }

    public static Rect unflattenFromString(String object) {
        if (TextUtils.isEmpty((CharSequence)object)) {
            return null;
        }
        if (!((Matcher)(object = UnflattenHelper.getMatcher((String)object))).matches()) {
            return null;
        }
        return new Rect(Integer.parseInt(((Matcher)object).group(1)), Integer.parseInt(((Matcher)object).group(2)), Integer.parseInt(((Matcher)object).group(3)), Integer.parseInt(((Matcher)object).group(4)));
    }

    public final int centerX() {
        return this.left + this.right >> 1;
    }

    public final int centerY() {
        return this.top + this.bottom >> 1;
    }

    public boolean contains(int n, int n2) {
        int n3;
        int n4;
        int n5 = this.left;
        int n6 = this.right;
        boolean bl = n5 < n6 && (n3 = this.top) < (n4 = this.bottom) && n >= n5 && n < n6 && n2 >= n3 && n2 < n4;
        return bl;
    }

    public boolean contains(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        int n7 = this.left;
        int n8 = this.right;
        boolean bl = n7 < n8 && (n6 = this.top) < (n5 = this.bottom) && n7 <= n && n6 <= n2 && n8 >= n3 && n5 >= n4;
        return bl;
    }

    public boolean contains(Rect rect) {
        int n;
        int n2;
        int n3 = this.left;
        int n4 = this.right;
        boolean bl = n3 < n4 && (n2 = this.top) < (n = this.bottom) && n3 <= rect.left && n2 <= rect.top && n4 >= rect.right && n >= rect.bottom;
        return bl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (Rect)object;
            if (this.left != ((Rect)object).left || this.top != ((Rect)object).top || this.right != ((Rect)object).right || this.bottom != ((Rect)object).bottom) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public final float exactCenterX() {
        return (float)(this.left + this.right) * 0.5f;
    }

    public final float exactCenterY() {
        return (float)(this.top + this.bottom) * 0.5f;
    }

    public String flattenToString() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append(this.left);
        stringBuilder.append(' ');
        stringBuilder.append(this.top);
        stringBuilder.append(' ');
        stringBuilder.append(this.right);
        stringBuilder.append(' ');
        stringBuilder.append(this.bottom);
        return stringBuilder.toString();
    }

    public int hashCode() {
        return ((this.left * 31 + this.top) * 31 + this.right) * 31 + this.bottom;
    }

    public final int height() {
        return this.bottom - this.top;
    }

    public void inset(int n, int n2) {
        this.left += n;
        this.top += n2;
        this.right -= n;
        this.bottom -= n2;
    }

    public void inset(int n, int n2, int n3, int n4) {
        this.left += n;
        this.top += n2;
        this.right -= n3;
        this.bottom -= n4;
    }

    public void inset(Insets insets) {
        this.left += insets.left;
        this.top += insets.top;
        this.right -= insets.right;
        this.bottom -= insets.bottom;
    }

    public void inset(Rect rect) {
        this.left += rect.left;
        this.top += rect.top;
        this.right -= rect.right;
        this.bottom -= rect.bottom;
    }

    public boolean intersect(int n, int n2, int n3, int n4) {
        int n5 = this.left;
        if (n5 < n3 && n < this.right && this.top < n4 && n2 < this.bottom) {
            if (n5 < n) {
                this.left = n;
            }
            if (this.top < n2) {
                this.top = n2;
            }
            if (this.right > n3) {
                this.right = n3;
            }
            if (this.bottom > n4) {
                this.bottom = n4;
            }
            return true;
        }
        return false;
    }

    public boolean intersect(Rect rect) {
        return this.intersect(rect.left, rect.top, rect.right, rect.bottom);
    }

    public void intersectUnchecked(Rect rect) {
        this.left = Math.max(this.left, rect.left);
        this.top = Math.max(this.top, rect.top);
        this.right = Math.min(this.right, rect.right);
        this.bottom = Math.min(this.bottom, rect.bottom);
    }

    public boolean intersects(int n, int n2, int n3, int n4) {
        boolean bl = this.left < n3 && n < this.right && this.top < n4 && n2 < this.bottom;
        return bl;
    }

    public final boolean isEmpty() {
        boolean bl = this.left >= this.right || this.top >= this.bottom;
        return bl;
    }

    public void offset(int n, int n2) {
        this.left += n;
        this.top += n2;
        this.right += n;
        this.bottom += n2;
    }

    public void offsetTo(int n, int n2) {
        this.right += n - this.left;
        this.bottom += n2 - this.top;
        this.left = n;
        this.top = n2;
    }

    @UnsupportedAppUsage
    public void printShortString(PrintWriter printWriter) {
        printWriter.print('[');
        printWriter.print(this.left);
        printWriter.print(',');
        printWriter.print(this.top);
        printWriter.print("][");
        printWriter.print(this.right);
        printWriter.print(',');
        printWriter.print(this.bottom);
        printWriter.print(']');
    }

    public void readFromParcel(Parcel parcel) {
        this.left = parcel.readInt();
        this.top = parcel.readInt();
        this.right = parcel.readInt();
        this.bottom = parcel.readInt();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void readFromProto(ProtoInputStream protoInputStream, long l) throws IOException, WireTypeMismatchException {
        l = protoInputStream.start(l);
        try {
            while (protoInputStream.nextField() != -1) {
                int n = protoInputStream.getFieldNumber();
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n != 4) continue;
                            this.bottom = protoInputStream.readInt(1120986464260L);
                            continue;
                        }
                        this.right = protoInputStream.readInt(1120986464259L);
                        continue;
                    }
                    this.top = protoInputStream.readInt(1120986464258L);
                    continue;
                }
                this.left = protoInputStream.readInt(1120986464257L);
            }
            return;
        }
        finally {
            protoInputStream.end(l);
        }
    }

    @UnsupportedAppUsage
    public void scale(float f) {
        if (f != 1.0f) {
            this.left = (int)((float)this.left * f + 0.5f);
            this.top = (int)((float)this.top * f + 0.5f);
            this.right = (int)((float)this.right * f + 0.5f);
            this.bottom = (int)((float)this.bottom * f + 0.5f);
        }
    }

    public void set(int n, int n2, int n3, int n4) {
        this.left = n;
        this.top = n2;
        this.right = n3;
        this.bottom = n4;
    }

    public void set(Rect rect) {
        this.left = rect.left;
        this.top = rect.top;
        this.right = rect.right;
        this.bottom = rect.bottom;
    }

    public void setEmpty() {
        this.bottom = 0;
        this.top = 0;
        this.right = 0;
        this.left = 0;
    }

    public boolean setIntersect(Rect rect, Rect rect2) {
        int n;
        int n2 = rect.left;
        if (n2 < rect2.right && (n = rect2.left) < rect.right && rect.top < rect2.bottom && rect2.top < rect.bottom) {
            this.left = Math.max(n2, n);
            this.top = Math.max(rect.top, rect2.top);
            this.right = Math.min(rect.right, rect2.right);
            this.bottom = Math.min(rect.bottom, rect2.bottom);
            return true;
        }
        return false;
    }

    public void sort() {
        int n = this.left;
        int n2 = this.right;
        if (n > n2) {
            n = this.left;
            this.left = n2;
            this.right = n;
        }
        if ((n = this.top) > (n2 = this.bottom)) {
            n = this.top;
            this.top = n2;
            this.bottom = n;
        }
    }

    public String toShortString() {
        return this.toShortString(new StringBuilder(32));
    }

    public String toShortString(StringBuilder stringBuilder) {
        stringBuilder.setLength(0);
        stringBuilder.append('[');
        stringBuilder.append(this.left);
        stringBuilder.append(',');
        stringBuilder.append(this.top);
        stringBuilder.append("][");
        stringBuilder.append(this.right);
        stringBuilder.append(',');
        stringBuilder.append(this.bottom);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append("Rect(");
        stringBuilder.append(this.left);
        stringBuilder.append(", ");
        stringBuilder.append(this.top);
        stringBuilder.append(" - ");
        stringBuilder.append(this.right);
        stringBuilder.append(", ");
        stringBuilder.append(this.bottom);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public void union(int n, int n2) {
        if (n < this.left) {
            this.left = n;
        } else if (n > this.right) {
            this.right = n;
        }
        if (n2 < this.top) {
            this.top = n2;
        } else if (n2 > this.bottom) {
            this.bottom = n2;
        }
    }

    public void union(int n, int n2, int n3, int n4) {
        if (n < n3 && n2 < n4) {
            int n5 = this.left;
            if (n5 < this.right && this.top < this.bottom) {
                if (n5 > n) {
                    this.left = n;
                }
                if (this.top > n2) {
                    this.top = n2;
                }
                if (this.right < n3) {
                    this.right = n3;
                }
                if (this.bottom < n4) {
                    this.bottom = n4;
                }
            } else {
                this.left = n;
                this.top = n2;
                this.right = n3;
                this.bottom = n4;
            }
        }
    }

    public void union(Rect rect) {
        this.union(rect.left, rect.top, rect.right, rect.bottom);
    }

    public final int width() {
        return this.right - this.left;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.left);
        parcel.writeInt(this.top);
        parcel.writeInt(this.right);
        parcel.writeInt(this.bottom);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1120986464257L, this.left);
        protoOutputStream.write(1120986464258L, this.top);
        protoOutputStream.write(1120986464259L, this.right);
        protoOutputStream.write(1120986464260L, this.bottom);
        protoOutputStream.end(l);
    }

    private static final class UnflattenHelper {
        private static final Pattern FLATTENED_PATTERN = Pattern.compile("(-?\\d+) (-?\\d+) (-?\\d+) (-?\\d+)");

        private UnflattenHelper() {
        }

        static Matcher getMatcher(String string2) {
            return FLATTENED_PATTERN.matcher(string2);
        }
    }

}

