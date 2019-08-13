/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import com.android.internal.R;
import com.android.internal.util.XmlUtils;
import org.xmlpull.v1.XmlPullParser;

public final class PointerIcon
implements Parcelable {
    public static final Parcelable.Creator<PointerIcon> CREATOR;
    private static final String TAG = "PointerIcon";
    public static final int TYPE_ALIAS = 1010;
    public static final int TYPE_ALL_SCROLL = 1013;
    public static final int TYPE_ARROW = 1000;
    public static final int TYPE_CELL = 1006;
    public static final int TYPE_CONTEXT_MENU = 1001;
    public static final int TYPE_COPY = 1011;
    public static final int TYPE_CROSSHAIR = 1007;
    public static final int TYPE_CUSTOM = -1;
    public static final int TYPE_DEFAULT = 1000;
    public static final int TYPE_GRAB = 1020;
    public static final int TYPE_GRABBING = 1021;
    public static final int TYPE_HAND = 1002;
    public static final int TYPE_HELP = 1003;
    public static final int TYPE_HORIZONTAL_DOUBLE_ARROW = 1014;
    public static final int TYPE_NOT_SPECIFIED = 1;
    public static final int TYPE_NO_DROP = 1012;
    public static final int TYPE_NULL = 0;
    private static final int TYPE_OEM_FIRST = 10000;
    public static final int TYPE_SPOT_ANCHOR = 2002;
    public static final int TYPE_SPOT_HOVER = 2000;
    public static final int TYPE_SPOT_TOUCH = 2001;
    public static final int TYPE_TEXT = 1008;
    public static final int TYPE_TOP_LEFT_DIAGONAL_DOUBLE_ARROW = 1017;
    public static final int TYPE_TOP_RIGHT_DIAGONAL_DOUBLE_ARROW = 1016;
    public static final int TYPE_VERTICAL_DOUBLE_ARROW = 1015;
    public static final int TYPE_VERTICAL_TEXT = 1009;
    public static final int TYPE_WAIT = 1004;
    public static final int TYPE_ZOOM_IN = 1018;
    public static final int TYPE_ZOOM_OUT = 1019;
    private static final PointerIcon gNullIcon;
    private static final SparseArray<SparseArray<PointerIcon>> gSystemIconsByDisplay;
    private static DisplayManager.DisplayListener sDisplayListener;
    private static boolean sUseLargeIcons;
    @UnsupportedAppUsage
    private Bitmap mBitmap;
    @UnsupportedAppUsage
    private Bitmap[] mBitmapFrames;
    @UnsupportedAppUsage
    private int mDurationPerFrame;
    @UnsupportedAppUsage
    private float mHotSpotX;
    @UnsupportedAppUsage
    private float mHotSpotY;
    private int mSystemIconResourceId;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private final int mType;

    static {
        gNullIcon = new PointerIcon(0);
        gSystemIconsByDisplay = new SparseArray();
        sUseLargeIcons = false;
        CREATOR = new Parcelable.Creator<PointerIcon>(){

            @Override
            public PointerIcon createFromParcel(Parcel object) {
                int n = ((Parcel)object).readInt();
                if (n == 0) {
                    return PointerIcon.getNullIcon();
                }
                int n2 = ((Parcel)object).readInt();
                if (n2 != 0) {
                    object = new PointerIcon(n);
                    ((PointerIcon)object).mSystemIconResourceId = n2;
                    return object;
                }
                return PointerIcon.create(Bitmap.CREATOR.createFromParcel((Parcel)object), ((Parcel)object).readFloat(), ((Parcel)object).readFloat());
            }

            public PointerIcon[] newArray(int n) {
                return new PointerIcon[n];
            }
        };
    }

    private PointerIcon(int n) {
        this.mType = n;
    }

    public static PointerIcon create(Bitmap bitmap, float f, float f2) {
        if (bitmap != null) {
            PointerIcon.validateHotSpot(bitmap, f, f2);
            PointerIcon pointerIcon = new PointerIcon(-1);
            pointerIcon.mBitmap = bitmap;
            pointerIcon.mHotSpotX = f;
            pointerIcon.mHotSpotY = f2;
            return pointerIcon;
        }
        throw new IllegalArgumentException("bitmap must not be null");
    }

    private Bitmap getBitmapFromDrawable(BitmapDrawable object) {
        Bitmap bitmap = ((BitmapDrawable)object).getBitmap();
        int n = ((BitmapDrawable)object).getIntrinsicWidth();
        int n2 = ((BitmapDrawable)object).getIntrinsicHeight();
        if (n == bitmap.getWidth() && n2 == bitmap.getHeight()) {
            return bitmap;
        }
        object = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(0.0f, 0.0f, n, n2);
        Bitmap bitmap2 = Bitmap.createBitmap(n, n2, bitmap.getConfig());
        Canvas canvas = new Canvas(bitmap2);
        Paint paint = new Paint();
        paint.setFilterBitmap(true);
        canvas.drawBitmap(bitmap, (Rect)object, rectF, paint);
        return bitmap2;
    }

    public static PointerIcon getDefaultIcon(Context context) {
        return PointerIcon.getSystemIcon(context, 1000);
    }

    public static PointerIcon getNullIcon() {
        return gNullIcon;
    }

    public static PointerIcon getSystemIcon(Context object, int n) {
        if (object != null) {
            int n2;
            Object object2;
            if (n == 0) {
                return gNullIcon;
            }
            if (sDisplayListener == null) {
                PointerIcon.registerDisplayListener((Context)object);
            }
            int n3 = ((Context)object).getDisplayId();
            Object object3 = object2 = gSystemIconsByDisplay.get(n3);
            if (object2 == null) {
                object3 = new SparseArray();
                gSystemIconsByDisplay.put(n3, (SparseArray<PointerIcon>)object3);
            }
            if ((object2 = ((SparseArray)object3).get(n)) != null) {
                return object2;
            }
            n3 = n2 = PointerIcon.getSystemIconTypeIndex(n);
            if (n2 == 0) {
                n3 = PointerIcon.getSystemIconTypeIndex(1000);
            }
            n2 = sUseLargeIcons ? 16974639 : 16974647;
            object2 = ((Context)object).obtainStyledAttributes(null, R.styleable.Pointer, 0, n2);
            n3 = ((TypedArray)object2).getResourceId(n3, -1);
            ((TypedArray)object2).recycle();
            if (n3 == -1) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("Missing theme resources for pointer icon type ");
                ((StringBuilder)object3).append(n);
                Log.w(TAG, ((StringBuilder)object3).toString());
                object = n == 1000 ? gNullIcon : PointerIcon.getSystemIcon((Context)object, 1000);
                return object;
            }
            object2 = new PointerIcon(n);
            if ((-16777216 & n3) == 16777216) {
                ((PointerIcon)object2).mSystemIconResourceId = n3;
            } else {
                PointerIcon.super.loadResource((Context)object, ((Context)object).getResources(), n3);
            }
            ((SparseArray)object3).append(n, object2);
            return object2;
        }
        throw new IllegalArgumentException("context must not be null");
    }

    private static int getSystemIconTypeIndex(int n) {
        switch (n) {
            default: {
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                return 0;
                            }
                            case 2002: {
                                return 13;
                            }
                            case 2001: {
                                return 15;
                            }
                            case 2000: 
                        }
                        return 14;
                    }
                    case 1021: {
                        return 8;
                    }
                    case 1020: {
                        return 7;
                    }
                    case 1019: {
                        return 23;
                    }
                    case 1018: {
                        return 22;
                    }
                    case 1017: {
                        return 17;
                    }
                    case 1016: {
                        return 18;
                    }
                    case 1015: {
                        return 19;
                    }
                    case 1014: {
                        return 11;
                    }
                    case 1013: {
                        return 1;
                    }
                    case 1012: {
                        return 12;
                    }
                    case 1011: {
                        return 5;
                    }
                    case 1010: {
                        return 0;
                    }
                    case 1009: {
                        return 20;
                    }
                    case 1008: {
                        return 16;
                    }
                    case 1007: {
                        return 6;
                    }
                    case 1006: 
                }
                return 3;
            }
            case 1004: {
                return 21;
            }
            case 1003: {
                return 10;
            }
            case 1002: {
                return 9;
            }
            case 1001: {
                return 4;
            }
            case 1000: 
        }
        return 2;
    }

    public static PointerIcon load(Resources resources, int n) {
        if (resources != null) {
            PointerIcon pointerIcon = new PointerIcon(-1);
            pointerIcon.loadResource(null, resources, n);
            return pointerIcon;
        }
        throw new IllegalArgumentException("resources must not be null");
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadResource(Context object, Resources object2, int n) {
        Throwable throwable2222;
        Object object3 = ((Resources)object2).getXml(n);
        XmlUtils.beginDocument((XmlPullParser)object3, "pointer-icon");
        TypedArray typedArray = ((Resources)object2).obtainAttributes((AttributeSet)object3, R.styleable.PointerIcon);
        n = typedArray.getResourceId(0, 0);
        float f = typedArray.getDimension(1, 0.0f);
        float f2 = typedArray.getDimension(2, 0.0f);
        typedArray.recycle();
        object3.close();
        if (n == 0) throw new IllegalArgumentException("<pointer-icon> is missing bitmap attribute.");
        object = object == null ? ((Resources)object2).getDrawable(n) : ((Context)object).getDrawable(n);
        object2 = object;
        if (object instanceof AnimationDrawable) {
            object = (AnimationDrawable)object;
            int n2 = ((AnimationDrawable)object).getNumberOfFrames();
            object2 = ((AnimationDrawable)object).getFrame(0);
            if (n2 == 1) {
                Log.w(TAG, "Animation icon with single frame -- simply treating the first frame as a normal bitmap icon.");
            } else {
                this.mDurationPerFrame = ((AnimationDrawable)object).getDuration(0);
                this.mBitmapFrames = new Bitmap[n2 - 1];
                int n3 = ((Drawable)object2).getIntrinsicWidth();
                int n4 = ((Drawable)object2).getIntrinsicHeight();
                for (n = 1; n < n2; ++n) {
                    object3 = ((AnimationDrawable)object).getFrame(n);
                    if (!(object3 instanceof BitmapDrawable)) throw new IllegalArgumentException("Frame of an animated pointer icon must refer to a bitmap drawable.");
                    if (((Drawable)object3).getIntrinsicWidth() == n3 && ((Drawable)object3).getIntrinsicHeight() == n4) {
                        object3 = (BitmapDrawable)object3;
                        this.mBitmapFrames[n - 1] = this.getBitmapFromDrawable((BitmapDrawable)object3);
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("The bitmap size of ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append("-th frame is different. All frames should have the exact same size and share the same hotspot.");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            }
        }
        if (!(object2 instanceof BitmapDrawable)) throw new IllegalArgumentException("<pointer-icon> bitmap attribute must refer to a bitmap drawable.");
        object = this.getBitmapFromDrawable((BitmapDrawable)object2);
        PointerIcon.validateHotSpot((Bitmap)object, f, f2);
        this.mBitmap = object;
        this.mHotSpotX = f;
        this.mHotSpotY = f2;
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                object = new IllegalArgumentException("Exception parsing pointer icon resource.", exception);
                throw object;
            }
        }
        object3.close();
        throw throwable2222;
    }

    private static void registerDisplayListener(Context context) {
        sDisplayListener = new DisplayManager.DisplayListener(){

            @Override
            public void onDisplayAdded(int n) {
            }

            @Override
            public void onDisplayChanged(int n) {
                gSystemIconsByDisplay.remove(n);
            }

            @Override
            public void onDisplayRemoved(int n) {
                gSystemIconsByDisplay.remove(n);
            }
        };
        context.getSystemService(DisplayManager.class).registerDisplayListener(sDisplayListener, null);
    }

    public static void setUseLargeIcons(boolean bl) {
        sUseLargeIcons = bl;
        gSystemIconsByDisplay.clear();
    }

    private static void validateHotSpot(Bitmap bitmap, float f, float f2) {
        if (!(f < 0.0f) && !(f >= (float)bitmap.getWidth())) {
            if (!(f2 < 0.0f) && !(f2 >= (float)bitmap.getHeight())) {
                return;
            }
            throw new IllegalArgumentException("y hotspot lies outside of the bitmap area");
        }
        throw new IllegalArgumentException("x hotspot lies outside of the bitmap area");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && object instanceof PointerIcon) {
            int n;
            object = (PointerIcon)object;
            if (this.mType == ((PointerIcon)object).mType && (n = this.mSystemIconResourceId) == ((PointerIcon)object).mSystemIconResourceId) {
                return n != 0 || this.mBitmap == ((PointerIcon)object).mBitmap && this.mHotSpotX == ((PointerIcon)object).mHotSpotX && this.mHotSpotY == ((PointerIcon)object).mHotSpotY;
            }
            return false;
        }
        return false;
    }

    public int getType() {
        return this.mType;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public PointerIcon load(Context context) {
        if (context != null) {
            if (this.mSystemIconResourceId != 0 && this.mBitmap == null) {
                PointerIcon pointerIcon = new PointerIcon(this.mType);
                pointerIcon.mSystemIconResourceId = this.mSystemIconResourceId;
                pointerIcon.loadResource(context, context.getResources(), this.mSystemIconResourceId);
                return pointerIcon;
            }
            return this;
        }
        throw new IllegalArgumentException("context must not be null");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        if (this.mType != 0) {
            parcel.writeInt(this.mSystemIconResourceId);
            if (this.mSystemIconResourceId == 0) {
                this.mBitmap.writeToParcel(parcel, n);
                parcel.writeFloat(this.mHotSpotX);
                parcel.writeFloat(this.mHotSpotY);
            }
        }
    }

}

