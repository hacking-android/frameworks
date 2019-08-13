/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.params;

import android.graphics.Point;
import android.graphics.Rect;

public final class Face {
    public static final int ID_UNSUPPORTED = -1;
    public static final int SCORE_MAX = 100;
    public static final int SCORE_MIN = 1;
    private final Rect mBounds;
    private final int mId;
    private final Point mLeftEye;
    private final Point mMouth;
    private final Point mRightEye;
    private final int mScore;

    public Face(Rect rect, int n) {
        this(rect, n, -1, null, null, null);
    }

    public Face(Rect rect, int n, int n2, Point point, Point point2, Point point3) {
        Face.checkNotNull("bounds", rect);
        if (n >= 1 && n <= 100) {
            if (n2 < 0 && n2 != -1) {
                throw new IllegalArgumentException("Id out of range");
            }
            if (n2 == -1) {
                Face.checkNull("leftEyePosition", point);
                Face.checkNull("rightEyePosition", point2);
                Face.checkNull("mouthPosition", point3);
            }
            this.mBounds = rect;
            this.mScore = n;
            this.mId = n2;
            this.mLeftEye = point;
            this.mRightEye = point2;
            this.mMouth = point3;
            return;
        }
        throw new IllegalArgumentException("Confidence out of range");
    }

    private static void checkNotNull(String string2, Object object) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" was required, but it was null");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static void checkNull(String string2, Object object) {
        if (object == null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(" was required to be null, but it wasn't");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    public int getId() {
        return this.mId;
    }

    public Point getLeftEyePosition() {
        return this.mLeftEye;
    }

    public Point getMouthPosition() {
        return this.mMouth;
    }

    public Point getRightEyePosition() {
        return this.mRightEye;
    }

    public int getScore() {
        return this.mScore;
    }

    public String toString() {
        return String.format("{ bounds: %s, score: %s, id: %d, leftEyePosition: %s, rightEyePosition: %s, mouthPosition: %s }", this.mBounds, this.mScore, this.mId, this.mLeftEye, this.mRightEye, this.mMouth);
    }
}

