/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.utils.HashCodeHelpers;
import android.util.Size;
import com.android.internal.util.Preconditions;

public final class MeteringRectangle {
    public static final int METERING_WEIGHT_DONT_CARE = 0;
    public static final int METERING_WEIGHT_MAX = 1000;
    public static final int METERING_WEIGHT_MIN = 0;
    private final int mHeight;
    private final int mWeight;
    private final int mWidth;
    private final int mX;
    private final int mY;

    public MeteringRectangle(int n, int n2, int n3, int n4, int n5) {
        this.mX = Preconditions.checkArgumentNonnegative(n, "x must be nonnegative");
        this.mY = Preconditions.checkArgumentNonnegative(n2, "y must be nonnegative");
        this.mWidth = Preconditions.checkArgumentNonnegative(n3, "width must be nonnegative");
        this.mHeight = Preconditions.checkArgumentNonnegative(n4, "height must be nonnegative");
        this.mWeight = Preconditions.checkArgumentInRange(n5, 0, 1000, "meteringWeight");
    }

    public MeteringRectangle(Point point, Size size, int n) {
        Preconditions.checkNotNull(point, "xy must not be null");
        Preconditions.checkNotNull(size, "dimensions must not be null");
        this.mX = Preconditions.checkArgumentNonnegative(point.x, "x must be nonnegative");
        this.mY = Preconditions.checkArgumentNonnegative(point.y, "y must be nonnegative");
        this.mWidth = Preconditions.checkArgumentNonnegative(size.getWidth(), "width must be nonnegative");
        this.mHeight = Preconditions.checkArgumentNonnegative(size.getHeight(), "height must be nonnegative");
        this.mWeight = Preconditions.checkArgumentNonnegative(n, "meteringWeight must be nonnegative");
    }

    public MeteringRectangle(Rect rect, int n) {
        Preconditions.checkNotNull(rect, "rect must not be null");
        this.mX = Preconditions.checkArgumentNonnegative(rect.left, "rect.left must be nonnegative");
        this.mY = Preconditions.checkArgumentNonnegative(rect.top, "rect.top must be nonnegative");
        this.mWidth = Preconditions.checkArgumentNonnegative(rect.width(), "rect.width must be nonnegative");
        this.mHeight = Preconditions.checkArgumentNonnegative(rect.height(), "rect.height must be nonnegative");
        this.mWeight = Preconditions.checkArgumentNonnegative(n, "meteringWeight must be nonnegative");
    }

    public boolean equals(MeteringRectangle meteringRectangle) {
        boolean bl = false;
        if (meteringRectangle == null) {
            return false;
        }
        boolean bl2 = bl;
        if (this.mX == meteringRectangle.mX) {
            bl2 = bl;
            if (this.mY == meteringRectangle.mY) {
                bl2 = bl;
                if (this.mWidth == meteringRectangle.mWidth) {
                    bl2 = bl;
                    if (this.mHeight == meteringRectangle.mHeight) {
                        bl2 = bl;
                        if (this.mWeight == meteringRectangle.mWeight) {
                            bl2 = true;
                        }
                    }
                }
            }
        }
        return bl2;
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof MeteringRectangle && this.equals((MeteringRectangle)object);
        return bl;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int getMeteringWeight() {
        return this.mWeight;
    }

    public Rect getRect() {
        int n = this.mX;
        int n2 = this.mY;
        return new Rect(n, n2, this.mWidth + n, this.mHeight + n2);
    }

    public Size getSize() {
        return new Size(this.mWidth, this.mHeight);
    }

    public Point getUpperLeftPoint() {
        return new Point(this.mX, this.mY);
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getX() {
        return this.mX;
    }

    public int getY() {
        return this.mY;
    }

    public int hashCode() {
        return HashCodeHelpers.hashCode(new int[]{this.mX, this.mY, this.mWidth, this.mHeight, this.mWeight});
    }

    public String toString() {
        return String.format("(x:%d, y:%d, w:%d, h:%d, wt:%d)", this.mX, this.mY, this.mWidth, this.mHeight, this.mWeight);
    }
}

