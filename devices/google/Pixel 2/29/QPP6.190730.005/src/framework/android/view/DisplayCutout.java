/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Pair;
import android.util.PathParser;
import android.util.proto.ProtoOutputStream;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class DisplayCutout {
    private static final String BOTTOM_MARKER = "@bottom";
    public static final int BOUNDS_POSITION_BOTTOM = 3;
    public static final int BOUNDS_POSITION_LEFT = 0;
    public static final int BOUNDS_POSITION_LENGTH = 4;
    public static final int BOUNDS_POSITION_RIGHT = 2;
    public static final int BOUNDS_POSITION_TOP = 1;
    private static final Object CACHE_LOCK;
    private static final String DP_MARKER = "@dp";
    public static final String EMULATION_OVERLAY_CATEGORY = "com.android.internal.display_cutout_emulation";
    public static final DisplayCutout NO_CUTOUT;
    private static final Pair<Path, DisplayCutout> NULL_PAIR;
    private static final String RIGHT_MARKER = "@right";
    private static final String TAG = "DisplayCutout";
    private static final Rect ZERO_RECT;
    @GuardedBy(value={"CACHE_LOCK"})
    private static Pair<Path, DisplayCutout> sCachedCutout;
    @GuardedBy(value={"CACHE_LOCK"})
    private static float sCachedDensity;
    @GuardedBy(value={"CACHE_LOCK"})
    private static int sCachedDisplayHeight;
    @GuardedBy(value={"CACHE_LOCK"})
    private static int sCachedDisplayWidth;
    @GuardedBy(value={"CACHE_LOCK"})
    private static String sCachedSpec;
    private final Bounds mBounds;
    private final Rect mSafeInsets;

    static {
        Rect rect = ZERO_RECT = new Rect();
        NO_CUTOUT = new DisplayCutout(rect, rect, rect, rect, rect, false);
        NULL_PAIR = new Pair<Object, Object>(null, null);
        CACHE_LOCK = new Object();
        sCachedCutout = NULL_PAIR;
    }

    public DisplayCutout(Insets insets, Rect rect, Rect rect2, Rect rect3, Rect rect4) {
        this(insets.toRect(), rect, rect2, rect3, rect4, true);
    }

    private DisplayCutout(Rect rect, Rect rect2, Rect rect3, Rect rect4, Rect rect5, boolean bl) {
        this.mSafeInsets = DisplayCutout.getCopyOrRef(rect, bl);
        this.mBounds = new Bounds(rect2, rect3, rect4, rect5, bl);
    }

    private DisplayCutout(Rect rect, Bounds bounds) {
        this.mSafeInsets = rect;
        this.mBounds = bounds;
    }

    @Deprecated
    public DisplayCutout(Rect rect, List<Rect> list) {
        this(rect, DisplayCutout.extractBoundsFromList(rect, list), true);
    }

    private DisplayCutout(Rect rect, Rect[] arrrect, boolean bl) {
        this.mSafeInsets = DisplayCutout.getCopyOrRef(rect, bl);
        this.mBounds = new Bounds(arrrect, bl);
    }

    private static int atLeastZero(int n) {
        block0 : {
            if (n >= 0) break block0;
            n = 0;
        }
        return n;
    }

    public static Rect[] extractBoundsFromList(Rect rect, List<Rect> object) {
        Rect[] arrrect = new Rect[4];
        for (int i = 0; i < arrrect.length; ++i) {
            arrrect[i] = ZERO_RECT;
        }
        if (rect != null && object != null) {
            Iterator<Rect> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                if (((Rect)object).left == 0) {
                    arrrect[0] = object;
                    continue;
                }
                if (((Rect)object).top == 0) {
                    arrrect[1] = object;
                    continue;
                }
                if (rect.right > 0) {
                    arrrect[2] = object;
                    continue;
                }
                if (rect.bottom <= 0) continue;
                arrrect[3] = object;
            }
        }
        return arrrect;
    }

    @VisibleForTesting
    public static DisplayCutout fromBoundingRect(int n, int n2, int n3, int n4, int n5) {
        Rect[] arrrect = new Rect[4];
        for (int i = 0; i < 4; ++i) {
            Rect rect = n5 == i ? new Rect(n, n2, n3, n4) : new Rect();
            arrrect[i] = rect;
        }
        return new DisplayCutout(ZERO_RECT, arrrect, false);
    }

    public static DisplayCutout fromBounds(Rect[] arrrect) {
        return new DisplayCutout(ZERO_RECT, arrrect, false);
    }

    public static DisplayCutout fromResourcesRectApproximation(Resources resources, int n, int n2) {
        return DisplayCutout.fromSpec(resources.getString(17039749), n, n2, (float)DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public static DisplayCutout fromSpec(String string2, int n, int n2, float f) {
        return (DisplayCutout)DisplayCutout.pathAndDisplayCutoutFromSpec((String)string2, (int)n, (int)n2, (float)f).second;
    }

    private static Rect getCopyOrRef(Rect rect, boolean bl) {
        if (rect == null) {
            return ZERO_RECT;
        }
        if (bl) {
            return new Rect(rect);
        }
        return rect;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static Pair<Path, DisplayCutout> pathAndDisplayCutoutFromSpec(String object, int n, int n2, float f) {
        int n4;
        int n3;
        Rect rect;
        Object object2;
        Pair<Object, DisplayCutout> pair;
        block20 : {
            block19 : {
                float f2;
                Region region;
                Matrix matrix;
                block17 : {
                    block18 : {
                        block16 : {
                            if (TextUtils.isEmpty((CharSequence)object)) {
                                return NULL_PAIR;
                            }
                            object2 = CACHE_LOCK;
                            // MONITORENTER : object2
                            pair = sCachedSpec;
                            if (((String)object).equals(pair) && sCachedDisplayWidth == n && sCachedDisplayHeight == n2 && sCachedDensity == f) {
                                object = sCachedCutout;
                                // MONITOREXIT : object2
                                return object;
                            }
                            // MONITOREXIT : object2
                            object2 = ((String)object).trim();
                            if (!object2.endsWith(RIGHT_MARKER)) break block16;
                            {
                                catch (Throwable throwable) {}
                                throw throwable;
                            }
                            f2 = n;
                            object2 = object2.substring(0, object2.length() - RIGHT_MARKER.length()).trim();
                            break block18;
                        }
                        f2 = (float)n / 2.0f;
                    }
                    boolean bl = object2.endsWith(DP_MARKER);
                    object = object2;
                    if (bl) {
                        object = object2.substring(0, object2.length() - DP_MARKER.length());
                    }
                    if (((String)object).contains(BOTTOM_MARKER)) {
                        object2 = ((String)object).split(BOTTOM_MARKER, 2);
                        object = object2[0].trim();
                        object2 = object2[1].trim();
                    } else {
                        object2 = null;
                    }
                    region = Region.obtain();
                    try {
                        pair = PathParser.createPathFromPathData((String)object);
                        matrix = new Matrix();
                        if (!bl) break block17;
                        matrix.postScale(f, f);
                    }
                    catch (Throwable throwable) {
                        Log.wtf(TAG, "Could not inflate cutout: ", throwable);
                        return NULL_PAIR;
                    }
                }
                matrix.postTranslate(f2, 0.0f);
                ((Path)((Object)pair)).transform(matrix);
                rect = new Rect();
                DisplayCutout.toRectAndAddToRegion((Path)((Object)pair), region, rect);
                n4 = rect.bottom;
                if (object2 == null) break block19;
                Path path = PathParser.createPathFromPathData((String)object2);
                matrix.postTranslate(0.0f, n2);
                catch (Throwable throwable) {
                    Log.wtf(TAG, "Could not inflate bottom cutout: ", throwable);
                    return NULL_PAIR;
                }
                path.transform(matrix);
                ((Path)((Object)pair)).addPath(path);
                object2 = new Rect();
                DisplayCutout.toRectAndAddToRegion(path, region, (Rect)object2);
                n3 = n2 - object2.top;
                break block20;
            }
            n3 = 0;
            object2 = null;
        }
        pair = new Pair<Object, DisplayCutout>(pair, new DisplayCutout(new Rect(0, n4, 0, n3), null, rect, null, (Rect)object2, false));
        object2 = CACHE_LOCK;
        // MONITORENTER : object2
        sCachedSpec = object;
        sCachedDisplayWidth = n;
        sCachedDisplayHeight = n2;
        sCachedDensity = f;
        sCachedCutout = pair;
        // MONITOREXIT : object2
        return pair;
    }

    public static Path pathFromResources(Resources resources, int n, int n2) {
        return (Path)DisplayCutout.pathAndDisplayCutoutFromSpec((String)resources.getString((int)17039748), (int)n, (int)n2, (float)((float)DisplayMetrics.DENSITY_DEVICE_STABLE / 160.0f)).first;
    }

    private static void toRectAndAddToRegion(Path path, Region region, Rect rect) {
        RectF rectF = new RectF();
        path.computeBounds(rectF, false);
        rectF.round(rect);
        region.op(rect, Region.Op.UNION);
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object instanceof DisplayCutout) {
            object = (DisplayCutout)object;
            if (!this.mSafeInsets.equals(((DisplayCutout)object).mSafeInsets) || !this.mBounds.equals(((DisplayCutout)object).mBounds)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public Rect getBoundingRectBottom() {
        return this.mBounds.getRect(3);
    }

    public Rect getBoundingRectLeft() {
        return this.mBounds.getRect(0);
    }

    public Rect getBoundingRectRight() {
        return this.mBounds.getRect(2);
    }

    public Rect getBoundingRectTop() {
        return this.mBounds.getRect(1);
    }

    public List<Rect> getBoundingRects() {
        ArrayList<Rect> arrayList = new ArrayList<Rect>();
        for (Rect rect : this.getBoundingRectsAll()) {
            if (rect.isEmpty()) continue;
            arrayList.add(new Rect(rect));
        }
        return arrayList;
    }

    public Rect[] getBoundingRectsAll() {
        return this.mBounds.getRects();
    }

    public int getSafeInsetBottom() {
        return this.mSafeInsets.bottom;
    }

    public int getSafeInsetLeft() {
        return this.mSafeInsets.left;
    }

    public int getSafeInsetRight() {
        return this.mSafeInsets.right;
    }

    public int getSafeInsetTop() {
        return this.mSafeInsets.top;
    }

    public Rect getSafeInsets() {
        return new Rect(this.mSafeInsets);
    }

    public int hashCode() {
        return this.mSafeInsets.hashCode() * 48271 + this.mBounds.hashCode();
    }

    public DisplayCutout inset(int n, int n2, int n3, int n4) {
        if (n == 0 && n2 == 0 && n3 == 0 && n4 == 0 || this.isBoundsEmpty()) {
            return this;
        }
        Rect rect = new Rect(this.mSafeInsets);
        if (n2 > 0 || rect.top > 0) {
            rect.top = DisplayCutout.atLeastZero(rect.top - n2);
        }
        if (n4 > 0 || rect.bottom > 0) {
            rect.bottom = DisplayCutout.atLeastZero(rect.bottom - n4);
        }
        if (n > 0 || rect.left > 0) {
            rect.left = DisplayCutout.atLeastZero(rect.left - n);
        }
        if (n3 > 0 || rect.right > 0) {
            rect.right = DisplayCutout.atLeastZero(rect.right - n3);
        }
        if (n == 0 && n2 == 0 && this.mSafeInsets.equals(rect)) {
            return this;
        }
        Rect[] arrrect = this.mBounds.getRects();
        for (n3 = 0; n3 < arrrect.length; ++n3) {
            if (arrrect[n3].equals(ZERO_RECT)) continue;
            arrrect[n3].offset(-n, -n2);
        }
        return new DisplayCutout(rect, arrrect, false);
    }

    public boolean isBoundsEmpty() {
        return this.mBounds.isEmpty();
    }

    public boolean isEmpty() {
        return this.mSafeInsets.equals(ZERO_RECT);
    }

    public DisplayCutout replaceSafeInsets(Rect rect) {
        return new DisplayCutout(new Rect(rect), this.mBounds);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DisplayCutout{insets=");
        stringBuilder.append(this.mSafeInsets);
        stringBuilder.append(" boundingRect={");
        stringBuilder.append(this.mBounds);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        this.mSafeInsets.writeToProto(protoOutputStream, 1146756268033L);
        this.mBounds.getRect(0).writeToProto(protoOutputStream, 1146756268035L);
        this.mBounds.getRect(1).writeToProto(protoOutputStream, 1146756268036L);
        this.mBounds.getRect(2).writeToProto(protoOutputStream, 1146756268037L);
        this.mBounds.getRect(3).writeToProto(protoOutputStream, 1146756268038L);
        protoOutputStream.end(l);
    }

    private static class Bounds {
        private final Rect[] mRects;

        private Bounds(Rect rect, Rect rect2, Rect rect3, Rect rect4, boolean bl) {
            this.mRects = new Rect[4];
            this.mRects[0] = DisplayCutout.getCopyOrRef(rect, bl);
            this.mRects[1] = DisplayCutout.getCopyOrRef(rect2, bl);
            this.mRects[2] = DisplayCutout.getCopyOrRef(rect3, bl);
            this.mRects[3] = DisplayCutout.getCopyOrRef(rect4, bl);
        }

        private Bounds(Rect[] arrrect, boolean bl) {
            if (arrrect.length == 4) {
                if (bl) {
                    this.mRects = new Rect[4];
                    for (int i = 0; i < 4; ++i) {
                        this.mRects[i] = new Rect(arrrect[i]);
                    }
                } else {
                    int n = arrrect.length;
                    for (int i = 0; i < n; ++i) {
                        if (arrrect[i] != null) {
                            continue;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("rects must have non-null elements: rects=");
                        stringBuilder.append(Arrays.toString(arrrect));
                        throw new IllegalArgumentException(stringBuilder.toString());
                    }
                    this.mRects = arrrect;
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("rects must have exactly 4 elements: rects=");
            stringBuilder.append(Arrays.toString(arrrect));
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        private Rect getRect(int n) {
            return new Rect(this.mRects[n]);
        }

        private Rect[] getRects() {
            Rect[] arrrect = new Rect[4];
            for (int i = 0; i < 4; ++i) {
                arrrect[i] = new Rect(this.mRects[i]);
            }
            return arrrect;
        }

        private boolean isEmpty() {
            Rect[] arrrect = this.mRects;
            int n = arrrect.length;
            for (int i = 0; i < n; ++i) {
                if (arrrect[i].isEmpty()) continue;
                return false;
            }
            return true;
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (object instanceof Bounds) {
                object = (Bounds)object;
                return Arrays.deepEquals(this.mRects, ((Bounds)object).mRects);
            }
            return false;
        }

        public int hashCode() {
            int n = 0;
            Rect[] arrrect = this.mRects;
            int n2 = arrrect.length;
            for (int i = 0; i < n2; ++i) {
                n = 48271 * n + arrrect[i].hashCode();
            }
            return n;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bounds=");
            stringBuilder.append(Arrays.toString(this.mRects));
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BoundsPosition {
    }

    public static final class ParcelableWrapper
    implements Parcelable {
        public static final Parcelable.Creator<ParcelableWrapper> CREATOR = new Parcelable.Creator<ParcelableWrapper>(){

            @Override
            public ParcelableWrapper createFromParcel(Parcel parcel) {
                return new ParcelableWrapper(ParcelableWrapper.readCutoutFromParcel(parcel));
            }

            public ParcelableWrapper[] newArray(int n) {
                return new ParcelableWrapper[n];
            }
        };
        private DisplayCutout mInner;

        public ParcelableWrapper() {
            this(NO_CUTOUT);
        }

        public ParcelableWrapper(DisplayCutout displayCutout) {
            this.mInner = displayCutout;
        }

        public static DisplayCutout readCutoutFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            if (n == -1) {
                return null;
            }
            if (n == 0) {
                return NO_CUTOUT;
            }
            Rect rect = parcel.readTypedObject(Rect.CREATOR);
            Rect[] arrrect = new Rect[4];
            parcel.readTypedArray(arrrect, Rect.CREATOR);
            return new DisplayCutout(rect, arrrect, false);
        }

        public static void writeCutoutToParcel(DisplayCutout displayCutout, Parcel parcel, int n) {
            if (displayCutout == null) {
                parcel.writeInt(-1);
            } else if (displayCutout == NO_CUTOUT) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcel.writeTypedObject(displayCutout.mSafeInsets, n);
                parcel.writeTypedArray((Parcelable[])displayCutout.mBounds.getRects(), n);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof ParcelableWrapper && this.mInner.equals(((ParcelableWrapper)object).mInner);
            return bl;
        }

        public DisplayCutout get() {
            return this.mInner;
        }

        public int hashCode() {
            return this.mInner.hashCode();
        }

        public void readFromParcel(Parcel parcel) {
            this.mInner = ParcelableWrapper.readCutoutFromParcel(parcel);
        }

        public void set(ParcelableWrapper parcelableWrapper) {
            this.mInner = parcelableWrapper.get();
        }

        public void set(DisplayCutout displayCutout) {
            this.mInner = displayCutout;
        }

        public String toString() {
            return String.valueOf(this.mInner);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            ParcelableWrapper.writeCutoutToParcel(this.mInner, parcel, n);
        }

    }

}

