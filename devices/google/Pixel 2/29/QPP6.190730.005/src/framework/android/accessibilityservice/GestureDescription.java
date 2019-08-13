/*
 * Decompiled with CFR 0.145.
 */
package android.accessibilityservice;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class GestureDescription {
    private static final long MAX_GESTURE_DURATION_MS = 60000L;
    private static final int MAX_STROKE_COUNT = 10;
    private final List<StrokeDescription> mStrokes = new ArrayList<StrokeDescription>();
    private final float[] mTempPos = new float[2];

    private GestureDescription() {
    }

    private GestureDescription(List<StrokeDescription> list) {
        this.mStrokes.addAll(list);
    }

    public static long getMaxGestureDuration() {
        return 60000L;
    }

    public static int getMaxStrokeCount() {
        return 10;
    }

    private long getNextKeyPointAtLeast(long l) {
        long l2;
        block3 : {
            l2 = Long.MAX_VALUE;
            for (int i = 0; i < this.mStrokes.size(); ++i) {
                long l3 = this.mStrokes.get((int)i).mStartTime;
                long l4 = l2;
                if (l3 < l2) {
                    l4 = l2;
                    if (l3 >= l) {
                        l4 = l3;
                    }
                }
                l3 = this.mStrokes.get((int)i).mEndTime;
                l2 = l4;
                if (l3 >= l4) continue;
                l2 = l4;
                if (l3 < l) continue;
                l2 = l3;
            }
            if (l2 != Long.MAX_VALUE) break block3;
            l2 = -1L;
        }
        return l2;
    }

    private int getPointsForTime(long l, TouchPoint[] arrtouchPoint) {
        int n = 0;
        for (int i = 0; i < this.mStrokes.size(); ++i) {
            StrokeDescription strokeDescription = this.mStrokes.get(i);
            int n2 = n;
            if (strokeDescription.hasPointForTime(l)) {
                arrtouchPoint[n].mStrokeId = strokeDescription.getId();
                arrtouchPoint[n].mContinuedStrokeId = strokeDescription.getContinuedStrokeId();
                TouchPoint touchPoint = arrtouchPoint[n];
                boolean bl = strokeDescription.getContinuedStrokeId() < 0 && l == strokeDescription.mStartTime;
                touchPoint.mIsStartOfPath = bl;
                touchPoint = arrtouchPoint[n];
                bl = !strokeDescription.willContinue() && l == strokeDescription.mEndTime;
                touchPoint.mIsEndOfPath = bl;
                strokeDescription.getPosForTime(l, this.mTempPos);
                arrtouchPoint[n].mX = Math.round(this.mTempPos[0]);
                arrtouchPoint[n].mY = Math.round(this.mTempPos[1]);
                n2 = n + 1;
            }
            n = n2;
        }
        return n;
    }

    private static long getTotalDuration(List<StrokeDescription> list) {
        long l = Long.MIN_VALUE;
        for (int i = 0; i < list.size(); ++i) {
            l = Math.max(l, list.get((int)i).mEndTime);
        }
        return Math.max(l, 0L);
    }

    public StrokeDescription getStroke(int n) {
        return this.mStrokes.get(n);
    }

    public int getStrokeCount() {
        return this.mStrokes.size();
    }

    public static class Builder {
        private final List<StrokeDescription> mStrokes = new ArrayList<StrokeDescription>();

        public Builder addStroke(StrokeDescription strokeDescription) {
            if (this.mStrokes.size() < 10) {
                this.mStrokes.add(strokeDescription);
                if (GestureDescription.getTotalDuration(this.mStrokes) <= 60000L) {
                    return this;
                }
                this.mStrokes.remove(strokeDescription);
                throw new IllegalStateException("Gesture would exceed maximum duration with new stroke");
            }
            throw new IllegalStateException("Attempting to add too many strokes to a gesture");
        }

        public GestureDescription build() {
            if (this.mStrokes.size() != 0) {
                return new GestureDescription(this.mStrokes);
            }
            throw new IllegalStateException("Gestures must have at least one stroke");
        }
    }

    public static class GestureStep
    implements Parcelable {
        public static final Parcelable.Creator<GestureStep> CREATOR = new Parcelable.Creator<GestureStep>(){

            @Override
            public GestureStep createFromParcel(Parcel parcel) {
                return new GestureStep(parcel);
            }

            public GestureStep[] newArray(int n) {
                return new GestureStep[n];
            }
        };
        public int numTouchPoints;
        public long timeSinceGestureStart;
        public TouchPoint[] touchPoints;

        public GestureStep(long l, int n, TouchPoint[] arrtouchPoint) {
            this.timeSinceGestureStart = l;
            this.numTouchPoints = n;
            this.touchPoints = new TouchPoint[n];
            for (int i = 0; i < n; ++i) {
                this.touchPoints[i] = new TouchPoint(arrtouchPoint[i]);
            }
        }

        public GestureStep(Parcel arrparcelable) {
            this.timeSinceGestureStart = arrparcelable.readLong();
            arrparcelable = arrparcelable.readParcelableArray(TouchPoint.class.getClassLoader());
            int n = arrparcelable == null ? 0 : arrparcelable.length;
            this.numTouchPoints = n;
            this.touchPoints = new TouchPoint[this.numTouchPoints];
            for (n = 0; n < this.numTouchPoints; ++n) {
                this.touchPoints[n] = (TouchPoint)arrparcelable[n];
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.timeSinceGestureStart);
            parcel.writeParcelableArray((Parcelable[])this.touchPoints, n);
        }

    }

    public static class MotionEventGenerator {
        private static TouchPoint[] sCurrentTouchPoints;

        private static TouchPoint[] getCurrentTouchPoints(int n) {
            TouchPoint[] arrtouchPoint = sCurrentTouchPoints;
            if (arrtouchPoint == null || arrtouchPoint.length < n) {
                sCurrentTouchPoints = new TouchPoint[n];
                for (int i = 0; i < n; ++i) {
                    MotionEventGenerator.sCurrentTouchPoints[i] = new TouchPoint();
                }
            }
            return sCurrentTouchPoints;
        }

        public static List<GestureStep> getGestureStepsFromGestureDescription(GestureDescription gestureDescription, int n) {
            ArrayList<GestureStep> arrayList = new ArrayList<GestureStep>();
            TouchPoint[] arrtouchPoint = MotionEventGenerator.getCurrentTouchPoints(gestureDescription.getStrokeCount());
            int n2 = 0;
            long l = 0L;
            long l2 = gestureDescription.getNextKeyPointAtLeast(0L);
            while (l2 >= 0L) {
                if (n2 != 0) {
                    l2 = Math.min(l2, (long)n + l);
                }
                n2 = gestureDescription.getPointsForTime(l2, arrtouchPoint);
                arrayList.add(new GestureStep(l2, n2, arrtouchPoint));
                long l3 = gestureDescription.getNextKeyPointAtLeast(1L + l2);
                l = l2;
                l2 = l3;
            }
            return arrayList;
        }
    }

    public static class StrokeDescription {
        private static final int INVALID_STROKE_ID = -1;
        static int sIdCounter;
        boolean mContinued;
        int mContinuedStrokeId = -1;
        long mEndTime;
        int mId;
        Path mPath;
        private PathMeasure mPathMeasure;
        long mStartTime;
        float[] mTapLocation;
        private float mTimeToLengthConversion;

        public StrokeDescription(Path path, long l, long l2) {
            this(path, l, l2, false);
        }

        public StrokeDescription(Path path, long l, long l2, boolean bl) {
            this.mContinued = bl;
            boolean bl2 = true;
            bl = l2 > 0L;
            Preconditions.checkArgument(bl, "Duration must be positive");
            bl = l >= 0L;
            Preconditions.checkArgument(bl, "Start time must not be negative");
            Preconditions.checkArgument(path.isEmpty() ^ true, "Path is empty");
            RectF rectF = new RectF();
            path.computeBounds(rectF, false);
            bl = rectF.bottom >= 0.0f && rectF.top >= 0.0f && rectF.right >= 0.0f && rectF.left >= 0.0f ? bl2 : false;
            Preconditions.checkArgument(bl, "Path bounds must not be negative");
            this.mPath = new Path(path);
            this.mPathMeasure = new PathMeasure(path, false);
            if (this.mPathMeasure.getLength() == 0.0f) {
                path = new Path(path);
                path.lineTo(-1.0f, -1.0f);
                this.mTapLocation = new float[2];
                new PathMeasure(path, false).getPosTan(0.0f, this.mTapLocation, null);
            }
            if (!this.mPathMeasure.nextContour()) {
                this.mPathMeasure.setPath(this.mPath, false);
                this.mStartTime = l;
                this.mEndTime = l + l2;
                this.mTimeToLengthConversion = this.getLength() / (float)l2;
                int n = sIdCounter;
                sIdCounter = n + 1;
                this.mId = n;
                return;
            }
            throw new IllegalArgumentException("Path has more than one contour");
        }

        public StrokeDescription continueStroke(Path object, long l, long l2, boolean bl) {
            if (this.mContinued) {
                object = new StrokeDescription((Path)object, l, l2, bl);
                ((StrokeDescription)object).mContinuedStrokeId = this.mId;
                return object;
            }
            throw new IllegalStateException("Only strokes marked willContinue can be continued");
        }

        public int getContinuedStrokeId() {
            return this.mContinuedStrokeId;
        }

        public long getDuration() {
            return this.mEndTime - this.mStartTime;
        }

        public int getId() {
            return this.mId;
        }

        float getLength() {
            return this.mPathMeasure.getLength();
        }

        public Path getPath() {
            return new Path(this.mPath);
        }

        boolean getPosForTime(long l, float[] arrf) {
            float[] arrf2 = this.mTapLocation;
            if (arrf2 != null) {
                arrf[0] = arrf2[0];
                arrf[1] = arrf2[1];
                return true;
            }
            if (l == this.mEndTime) {
                return this.mPathMeasure.getPosTan(this.getLength(), arrf, null);
            }
            float f = this.mTimeToLengthConversion;
            float f2 = l - this.mStartTime;
            return this.mPathMeasure.getPosTan(f * f2, arrf, null);
        }

        public long getStartTime() {
            return this.mStartTime;
        }

        boolean hasPointForTime(long l) {
            boolean bl = l >= this.mStartTime && l <= this.mEndTime;
            return bl;
        }

        public boolean willContinue() {
            return this.mContinued;
        }
    }

    public static class TouchPoint
    implements Parcelable {
        public static final Parcelable.Creator<TouchPoint> CREATOR = new Parcelable.Creator<TouchPoint>(){

            @Override
            public TouchPoint createFromParcel(Parcel parcel) {
                return new TouchPoint(parcel);
            }

            public TouchPoint[] newArray(int n) {
                return new TouchPoint[n];
            }
        };
        private static final int FLAG_IS_END_OF_PATH = 2;
        private static final int FLAG_IS_START_OF_PATH = 1;
        public int mContinuedStrokeId;
        public boolean mIsEndOfPath;
        public boolean mIsStartOfPath;
        public int mStrokeId;
        public float mX;
        public float mY;

        public TouchPoint() {
        }

        public TouchPoint(TouchPoint touchPoint) {
            this.copyFrom(touchPoint);
        }

        public TouchPoint(Parcel parcel) {
            this.mStrokeId = parcel.readInt();
            this.mContinuedStrokeId = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = false;
            boolean bl2 = (n & 1) != 0;
            this.mIsStartOfPath = bl2;
            bl2 = bl;
            if ((n & 2) != 0) {
                bl2 = true;
            }
            this.mIsEndOfPath = bl2;
            this.mX = parcel.readFloat();
            this.mY = parcel.readFloat();
        }

        public void copyFrom(TouchPoint touchPoint) {
            this.mStrokeId = touchPoint.mStrokeId;
            this.mContinuedStrokeId = touchPoint.mContinuedStrokeId;
            this.mIsStartOfPath = touchPoint.mIsStartOfPath;
            this.mIsEndOfPath = touchPoint.mIsEndOfPath;
            this.mX = touchPoint.mX;
            this.mY = touchPoint.mY;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("TouchPoint{mStrokeId=");
            stringBuilder.append(this.mStrokeId);
            stringBuilder.append(", mContinuedStrokeId=");
            stringBuilder.append(this.mContinuedStrokeId);
            stringBuilder.append(", mIsStartOfPath=");
            stringBuilder.append(this.mIsStartOfPath);
            stringBuilder.append(", mIsEndOfPath=");
            stringBuilder.append(this.mIsEndOfPath);
            stringBuilder.append(", mX=");
            stringBuilder.append(this.mX);
            stringBuilder.append(", mY=");
            stringBuilder.append(this.mY);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mStrokeId);
            parcel.writeInt(this.mContinuedStrokeId);
            int n2 = this.mIsStartOfPath;
            n = this.mIsEndOfPath ? 2 : 0;
            parcel.writeInt(n2 | n);
            parcel.writeFloat(this.mX);
            parcel.writeFloat(this.mY);
        }

    }

}

