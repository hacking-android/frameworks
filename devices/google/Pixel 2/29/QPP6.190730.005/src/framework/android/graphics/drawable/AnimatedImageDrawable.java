/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.graphics.drawable.-$
 *  android.graphics.drawable.-$$Lambda
 *  android.graphics.drawable.-$$Lambda$AnimatedImageDrawable
 *  android.graphics.drawable.-$$Lambda$AnimatedImageDrawable$Cgt3NliB7ZYUONyDd-eQGdYbEKc
 *  dalvik.annotation.optimization.FastNative
 *  libcore.util.NativeAllocationRegistry
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.ImageDecoder;
import android.graphics.Rect;
import android.graphics.drawable.-$;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.graphics.drawable._$$Lambda$AlQeVq8Y_kfuQeb_JLZ0ueV4DE8;
import android.graphics.drawable._$$Lambda$AnimatedImageDrawable$6aWLU8OYhdfACSejz5_iGirYxUk;
import android.graphics.drawable._$$Lambda$AnimatedImageDrawable$Cgt3NliB7ZYUONyDd_eQGdYbEKc;
import android.graphics.drawable._$$Lambda$AnimatedImageDrawable$dGAkP_tKNvqn_qCWdrQRL806ExQ;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import com.android.internal.R;
import dalvik.annotation.optimization.FastNative;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import libcore.util.NativeAllocationRegistry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedImageDrawable
extends Drawable
implements Animatable2 {
    private static final int FINISHED = -1;
    @Deprecated
    public static final int LOOP_INFINITE = -1;
    public static final int REPEAT_INFINITE = -1;
    private static final int REPEAT_UNDEFINED = -2;
    private ArrayList<Animatable2.AnimationCallback> mAnimationCallbacks = null;
    private ColorFilter mColorFilter;
    private Handler mHandler;
    private int mIntrinsicHeight;
    private int mIntrinsicWidth;
    private Runnable mRunnable;
    private boolean mStarting;
    private State mState;

    public AnimatedImageDrawable() {
        this.mState = new State(0L, null, null);
    }

    public AnimatedImageDrawable(long l, ImageDecoder object, int n, int n2, long l2, boolean bl, int n3, int n4, Rect rect, InputStream inputStream, AssetFileDescriptor assetFileDescriptor) throws IOException {
        n = Bitmap.scaleFromDensity(n, n3, n4);
        n2 = Bitmap.scaleFromDensity(n2, n3, n4);
        if (rect == null) {
            this.mIntrinsicWidth = n;
            this.mIntrinsicHeight = n2;
        } else {
            rect.set(Bitmap.scaleFromDensity(rect.left, n3, n4), Bitmap.scaleFromDensity(rect.top, n3, n4), Bitmap.scaleFromDensity(rect.right, n3, n4), Bitmap.scaleFromDensity(rect.bottom, n3, n4));
            this.mIntrinsicWidth = rect.width();
            this.mIntrinsicHeight = rect.height();
        }
        this.mState = new State(AnimatedImageDrawable.nCreate(l, (ImageDecoder)object, n, n2, l2, bl, rect), inputStream, assetFileDescriptor);
        l = AnimatedImageDrawable.nNativeByteSize(this.mState.mNativePtr);
        rect = NativeAllocationRegistry.createMalloced((ClassLoader)AnimatedImageDrawable.class.getClassLoader(), (long)AnimatedImageDrawable.nGetNativeFinalizer(), (long)l);
        object = this.mState;
        rect.registerNativeAllocation(object, ((State)object).mNativePtr);
    }

    private Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(Looper.getMainLooper());
        }
        return this.mHandler;
    }

    static /* synthetic */ void lambda$updateStateFromTypedArray$0(ImageDecoder imageDecoder, ImageDecoder.ImageInfo imageInfo, ImageDecoder.Source source) {
        if (imageInfo.isAnimated()) {
            return;
        }
        throw new IllegalArgumentException("image is not animated");
    }

    private static native long nCreate(long var0, ImageDecoder var2, int var3, int var4, long var5, boolean var7, Rect var8) throws IOException;

    private static native long nDraw(long var0, long var2);

    @FastNative
    private static native int nGetAlpha(long var0);

    @FastNative
    private static native long nGetNativeFinalizer();

    @FastNative
    private static native int nGetRepeatCount(long var0);

    @FastNative
    private static native boolean nIsRunning(long var0);

    @FastNative
    private static native long nNativeByteSize(long var0);

    @FastNative
    private static native void nSetAlpha(long var0, int var2);

    @FastNative
    private static native void nSetColorFilter(long var0, long var2);

    @FastNative
    private static native void nSetMirrored(long var0, boolean var2);

    private static native void nSetOnAnimationEndListener(long var0, AnimatedImageDrawable var2);

    @FastNative
    private static native void nSetRepeatCount(long var0, int var2);

    @FastNative
    private static native boolean nStart(long var0);

    @FastNative
    private static native boolean nStop(long var0);

    @UnsupportedAppUsage
    private void onAnimationEnd() {
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList != null) {
            arrayList = arrayList.iterator();
            while (arrayList.hasNext()) {
                ((Animatable2.AnimationCallback)arrayList.next()).onAnimationEnd(this);
            }
        }
    }

    private void postOnAnimationEnd() {
        if (this.mAnimationCallbacks == null) {
            return;
        }
        this.getHandler().post(new _$$Lambda$AnimatedImageDrawable$dGAkP_tKNvqn_qCWdrQRL806ExQ(this));
    }

    private void postOnAnimationStart() {
        if (this.mAnimationCallbacks == null) {
            return;
        }
        this.getHandler().post(new _$$Lambda$AnimatedImageDrawable$6aWLU8OYhdfACSejz5_iGirYxUk(this));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateStateFromTypedArray(TypedArray typedArray, int n) throws XmlPullParserException {
        Object object = this.mState;
        Resources resources = typedArray.getResources();
        int n2 = typedArray.getResourceId(0, 0);
        if (n2 != 0) {
            Object object2 = new TypedValue();
            resources.getValueForDensity(n2, n, (TypedValue)object2, true);
            if (n > 0 && ((TypedValue)object2).density > 0 && ((TypedValue)object2).density != 65535) {
                ((TypedValue)object2).density = ((TypedValue)object2).density == n ? resources.getDisplayMetrics().densityDpi : ((TypedValue)object2).density * resources.getDisplayMetrics().densityDpi / n;
            }
            n = 0;
            if (((TypedValue)object2).density == 0) {
                n = 160;
            } else if (((TypedValue)object2).density != 65535) {
                n = ((TypedValue)object2).density;
            }
            try {
                object2 = ImageDecoder.decodeDrawable(ImageDecoder.createSource(resources, resources.openRawResource(n2, (TypedValue)object2), n), (ImageDecoder.OnHeaderDecodedListener)_$$Lambda$AnimatedImageDrawable$Cgt3NliB7ZYUONyDd_eQGdYbEKc.INSTANCE);
            }
            catch (IOException iOException) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(typedArray.getPositionDescription());
                ((StringBuilder)object2).append(": <animated-image> requires a valid 'src' attribute");
                throw new XmlPullParserException(((StringBuilder)object2).toString(), null, (Throwable)iOException);
            }
            if (!(object2 instanceof AnimatedImageDrawable)) {
                object = new StringBuilder();
                ((StringBuilder)object).append(typedArray.getPositionDescription());
                ((StringBuilder)object).append(": <animated-image> did not decode animated");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            n = this.mState.mRepeatCount;
            object2 = (AnimatedImageDrawable)object2;
            this.mState = ((AnimatedImageDrawable)object2).mState;
            ((AnimatedImageDrawable)object2).mState = null;
            this.mIntrinsicWidth = ((AnimatedImageDrawable)object2).mIntrinsicWidth;
            this.mIntrinsicHeight = ((AnimatedImageDrawable)object2).mIntrinsicHeight;
            if (n != -2) {
                this.setRepeatCount(n);
            }
        }
        this.mState.mThemeAttrs = typedArray.extractThemeAttrs();
        if (this.mState.mNativePtr == 0L && (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[0] == 0)) {
            object = new StringBuilder();
            ((StringBuilder)object).append(typedArray.getPositionDescription());
            ((StringBuilder)object).append(": <animated-image> requires a valid 'src' attribute");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        this.mState.mAutoMirrored = typedArray.getBoolean(3, ((State)object).mAutoMirrored);
        n = typedArray.getInt(1, -2);
        if (n != -2) {
            this.setRepeatCount(n);
        }
        if (typedArray.getBoolean(2, false) && this.mState.mNativePtr != 0L) {
            this.start();
        }
    }

    @Override
    public void clearAnimationCallbacks() {
        if (this.mAnimationCallbacks != null) {
            this.mAnimationCallbacks = null;
            AnimatedImageDrawable.nSetOnAnimationEndListener(this.mState.mNativePtr, null);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        if (this.mState.mNativePtr != 0L) {
            long l;
            if (this.mStarting) {
                this.mStarting = false;
                this.postOnAnimationStart();
            }
            if ((l = AnimatedImageDrawable.nDraw(this.mState.mNativePtr, canvas.getNativeCanvasWrapper())) > 0L) {
                if (this.mRunnable == null) {
                    this.mRunnable = new _$$Lambda$AlQeVq8Y_kfuQeb_JLZ0ueV4DE8(this);
                }
                this.scheduleSelf(this.mRunnable, SystemClock.uptimeMillis() + l);
            } else if (l == -1L) {
                this.postOnAnimationEnd();
            }
            return;
        }
        throw new IllegalStateException("called draw on empty AnimatedImageDrawable");
    }

    @Override
    public int getAlpha() {
        if (this.mState.mNativePtr != 0L) {
            return AnimatedImageDrawable.nGetAlpha(this.mState.mNativePtr);
        }
        throw new IllegalStateException("called getAlpha on empty AnimatedImageDrawable");
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mIntrinsicHeight;
    }

    @Override
    public int getIntrinsicWidth() {
        return this.mIntrinsicWidth;
    }

    @Deprecated
    public int getLoopCount(int n) {
        return this.getRepeatCount();
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    public int getRepeatCount() {
        if (this.mState.mNativePtr != 0L) {
            if (this.mState.mRepeatCount == -2) {
                State state = this.mState;
                state.mRepeatCount = AnimatedImageDrawable.nGetRepeatCount(state.mNativePtr);
            }
            return this.mState.mRepeatCount;
        }
        throw new IllegalStateException("called getRepeatCount on empty AnimatedImageDrawable");
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateStateFromTypedArray(AnimatedImageDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedImageDrawable), this.mSrcDensityOverride);
    }

    @Override
    public final boolean isAutoMirrored() {
        return this.mState.mAutoMirrored;
    }

    @Override
    public boolean isRunning() {
        if (this.mState.mNativePtr != 0L) {
            return AnimatedImageDrawable.nIsRunning(this.mState.mNativePtr);
        }
        throw new IllegalStateException("called isRunning on empty AnimatedImageDrawable");
    }

    public /* synthetic */ void lambda$postOnAnimationEnd$2$AnimatedImageDrawable() {
        Iterator<Animatable2.AnimationCallback> iterator = this.mAnimationCallbacks.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAnimationEnd(this);
        }
    }

    public /* synthetic */ void lambda$postOnAnimationStart$1$AnimatedImageDrawable() {
        Iterator<Animatable2.AnimationCallback> iterator = this.mAnimationCallbacks.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAnimationStart(this);
        }
    }

    @Override
    public boolean onLayoutDirectionChanged(int n) {
        boolean bl = this.mState.mAutoMirrored;
        boolean bl2 = false;
        if (bl && this.mState.mNativePtr != 0L) {
            if (n == 1) {
                bl2 = true;
            }
            AnimatedImageDrawable.nSetMirrored(this.mState.mNativePtr, bl2);
            return true;
        }
        return false;
    }

    @Override
    public void registerAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        if (animationCallback == null) {
            return;
        }
        if (this.mAnimationCallbacks == null) {
            this.mAnimationCallbacks = new ArrayList<E>();
            AnimatedImageDrawable.nSetOnAnimationEndListener(this.mState.mNativePtr, this);
        }
        if (!this.mAnimationCallbacks.contains(animationCallback)) {
            this.mAnimationCallbacks.add(animationCallback);
        }
    }

    @Override
    public void setAlpha(int n) {
        if (n >= 0 && n <= 255) {
            if (this.mState.mNativePtr != 0L) {
                AnimatedImageDrawable.nSetAlpha(this.mState.mNativePtr, n);
                this.invalidateSelf();
                return;
            }
            throw new IllegalStateException("called setAlpha on empty AnimatedImageDrawable");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Alpha must be between 0 and 255! provided ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void setAutoMirrored(boolean bl) {
        if (this.mState.mAutoMirrored != bl) {
            this.mState.mAutoMirrored = bl;
            if (this.getLayoutDirection() == 1 && this.mState.mNativePtr != 0L) {
                AnimatedImageDrawable.nSetMirrored(this.mState.mNativePtr, bl);
                this.invalidateSelf();
            }
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        long l = this.mState.mNativePtr;
        long l2 = 0L;
        if (l != 0L) {
            if (colorFilter != this.mColorFilter) {
                this.mColorFilter = colorFilter;
                if (colorFilter != null) {
                    l2 = colorFilter.getNativeInstance();
                }
                AnimatedImageDrawable.nSetColorFilter(this.mState.mNativePtr, l2);
                this.invalidateSelf();
            }
            return;
        }
        throw new IllegalStateException("called setColorFilter on empty AnimatedImageDrawable");
    }

    @Deprecated
    public void setLoopCount(int n) {
        this.setRepeatCount(n);
    }

    public void setRepeatCount(int n) {
        if (n >= -1) {
            if (this.mState.mRepeatCount != n) {
                State state = this.mState;
                state.mRepeatCount = n;
                if (state.mNativePtr != 0L) {
                    AnimatedImageDrawable.nSetRepeatCount(this.mState.mNativePtr, n);
                }
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("invalid value passed to setRepeatCount");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void start() {
        if (this.mState.mNativePtr != 0L) {
            if (AnimatedImageDrawable.nStart(this.mState.mNativePtr)) {
                this.mStarting = true;
                this.invalidateSelf();
            }
            return;
        }
        throw new IllegalStateException("called start on empty AnimatedImageDrawable");
    }

    @Override
    public void stop() {
        if (this.mState.mNativePtr != 0L) {
            if (AnimatedImageDrawable.nStop(this.mState.mNativePtr)) {
                this.postOnAnimationEnd();
            }
            return;
        }
        throw new IllegalStateException("called stop on empty AnimatedImageDrawable");
    }

    @Override
    public boolean unregisterAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        ArrayList<Animatable2.AnimationCallback> arrayList;
        if (animationCallback != null && (arrayList = this.mAnimationCallbacks) != null && arrayList.remove(animationCallback)) {
            if (this.mAnimationCallbacks.isEmpty()) {
                this.clearAnimationCallbacks();
            }
            return true;
        }
        return false;
    }

    private class State {
        private final AssetFileDescriptor mAssetFd;
        boolean mAutoMirrored = false;
        private final InputStream mInputStream;
        final long mNativePtr;
        int mRepeatCount = -2;
        int[] mThemeAttrs = null;

        State(long l, InputStream inputStream, AssetFileDescriptor assetFileDescriptor) {
            this.mNativePtr = l;
            this.mInputStream = inputStream;
            this.mAssetFd = assetFileDescriptor;
        }
    }

}

