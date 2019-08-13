/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.app.ActivityThread;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.graphics.drawable._$$Lambda$AnimatedVectorDrawable$VectorDrawableAnimatorRT$PzjgSeyQweoFjbEZJP80UteZqm8;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.Log;
import android.util.LongArray;
import android.util.PathParser;
import android.util.Property;
import android.view.Choreographer;
import android.view.NativeVectorDrawableAnimator;
import android.view.RenderNodeAnimatorSetHelper;
import com.android.internal.R;
import com.android.internal.util.VirtualRefBasePtr;
import dalvik.annotation.optimization.FastNative;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedVectorDrawable
extends Drawable
implements Animatable2 {
    private static final String ANIMATED_VECTOR = "animated-vector";
    private static final boolean DBG_ANIMATION_VECTOR_DRAWABLE = false;
    private static final String LOGTAG = "AnimatedVectorDrawable";
    private static final String TARGET = "target";
    @UnsupportedAppUsage
    private AnimatedVectorDrawableState mAnimatedVectorState;
    private ArrayList<Animatable2.AnimationCallback> mAnimationCallbacks = null;
    private Animator.AnimatorListener mAnimatorListener = null;
    @UnsupportedAppUsage
    private VectorDrawableAnimator mAnimatorSet;
    private AnimatorSet mAnimatorSetFromXml = null;
    private final Drawable.Callback mCallback = new Drawable.Callback(){

        @Override
        public void invalidateDrawable(Drawable drawable2) {
            AnimatedVectorDrawable.this.invalidateSelf();
        }

        @Override
        public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
            AnimatedVectorDrawable.this.scheduleSelf(runnable, l);
        }

        @Override
        public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
            AnimatedVectorDrawable.this.unscheduleSelf(runnable);
        }
    };
    private boolean mMutated;
    private Resources mRes;

    public AnimatedVectorDrawable() {
        this(null, null);
    }

    private AnimatedVectorDrawable(AnimatedVectorDrawableState animatedVectorDrawableState, Resources resources) {
        this.mAnimatedVectorState = new AnimatedVectorDrawableState(animatedVectorDrawableState, this.mCallback, resources);
        this.mAnimatorSet = new VectorDrawableAnimatorRT(this);
        this.mRes = resources;
    }

    static /* synthetic */ boolean access$400() {
        return AnimatedVectorDrawable.shouldIgnoreInvalidAnimation();
    }

    private static boolean containsSameValueType(PropertyValuesHolder object, Property object2) {
        boolean bl;
        block12 : {
            block11 : {
                boolean bl2;
                Class class_;
                block8 : {
                    block10 : {
                        block9 : {
                            object = ((PropertyValuesHolder)object).getValueType();
                            class_ = ((Property)object2).getType();
                            object2 = Float.TYPE;
                            boolean bl3 = false;
                            bl2 = false;
                            bl = false;
                            if (object == object2 || object == Float.class) break block8;
                            if (object != Integer.TYPE && object != Integer.class) {
                                if (object == class_) {
                                    bl = true;
                                }
                                return bl;
                            }
                            if (class_ == Integer.TYPE) break block9;
                            bl = bl3;
                            if (class_ != Integer.class) break block10;
                        }
                        bl = true;
                    }
                    return bl;
                }
                if (class_ == Float.TYPE) break block11;
                bl = bl2;
                if (class_ != Float.class) break block12;
            }
            bl = true;
        }
        return bl;
    }

    private void ensureAnimatorSet() {
        if (this.mAnimatorSetFromXml == null) {
            this.mAnimatorSetFromXml = new AnimatorSet();
            this.mAnimatedVectorState.prepareLocalAnimators(this.mAnimatorSetFromXml, this.mRes);
            this.mAnimatorSet.init(this.mAnimatorSetFromXml);
            this.mRes = null;
        }
    }

    private void fallbackOntoUI() {
        Object object = this.mAnimatorSet;
        if (object instanceof VectorDrawableAnimatorRT) {
            VectorDrawableAnimatorRT vectorDrawableAnimatorRT = (VectorDrawableAnimatorRT)object;
            this.mAnimatorSet = new VectorDrawableAnimatorUI(this);
            object = this.mAnimatorSetFromXml;
            if (object != null) {
                this.mAnimatorSet.init((AnimatorSet)object);
            }
            if (vectorDrawableAnimatorRT.mListener != null) {
                this.mAnimatorSet.setListener(vectorDrawableAnimatorRT.mListener);
            }
            vectorDrawableAnimatorRT.transferPendingActions(this.mAnimatorSet);
        }
    }

    private static native void nAddAnimator(long var0, long var2, long var4, long var6, long var8, int var10, int var11);

    private static native long nCreateAnimatorSet();

    @FastNative
    private static native long nCreateGroupPropertyHolder(long var0, int var2, float var3, float var4);

    @FastNative
    private static native long nCreatePathColorPropertyHolder(long var0, int var2, int var3, int var4);

    @FastNative
    private static native long nCreatePathDataPropertyHolder(long var0, long var2, long var4);

    @FastNative
    private static native long nCreatePathPropertyHolder(long var0, int var2, float var3, float var4);

    @FastNative
    private static native long nCreateRootAlphaPropertyHolder(long var0, float var2, float var3);

    @FastNative
    private static native void nEnd(long var0);

    @FastNative
    private static native void nReset(long var0);

    private static native void nReverse(long var0, VectorDrawableAnimatorRT var2, int var3);

    private static native void nSetPropertyHolderData(long var0, float[] var2, int var3);

    private static native void nSetPropertyHolderData(long var0, int[] var2, int var3);

    private static native void nSetVectorDrawableTarget(long var0, long var2);

    private static native void nStart(long var0, VectorDrawableAnimatorRT var2, int var3);

    private void removeAnimatorSetListener() {
        Animator.AnimatorListener animatorListener = this.mAnimatorListener;
        if (animatorListener != null) {
            this.mAnimatorSet.removeListener(animatorListener);
            this.mAnimatorListener = null;
        }
    }

    private static boolean shouldIgnoreInvalidAnimation() {
        Application application = ActivityThread.currentApplication();
        if (application != null && application.getApplicationInfo() != null) {
            return application.getApplicationInfo().targetSdkVersion < 24;
        }
        return true;
    }

    private static void updateAnimatorProperty(Animator object, String charSequence, VectorDrawable vectorDrawable, boolean bl) {
        block7 : {
            block6 : {
                if (!(object instanceof ObjectAnimator)) break block6;
                PropertyValuesHolder[] arrpropertyValuesHolder = ((ObjectAnimator)object).getValues();
                for (int i = 0; i < arrpropertyValuesHolder.length; ++i) {
                    PropertyValuesHolder propertyValuesHolder = arrpropertyValuesHolder[i];
                    String string2 = propertyValuesHolder.getPropertyName();
                    Object object2 = vectorDrawable.getTargetByName((String)charSequence);
                    object = null;
                    if (object2 instanceof VectorDrawable.VObject) {
                        object = ((VectorDrawable.VObject)object2).getProperty(string2);
                    } else if (object2 instanceof VectorDrawable.VectorDrawableState) {
                        object = ((VectorDrawable.VectorDrawableState)object2).getProperty(string2);
                    }
                    if (object == null) continue;
                    if (AnimatedVectorDrawable.containsSameValueType(propertyValuesHolder, (Property)object)) {
                        propertyValuesHolder.setProperty((Property)object);
                        continue;
                    }
                    if (bl) continue;
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Wrong valueType for Property: ");
                    ((StringBuilder)charSequence).append(string2);
                    ((StringBuilder)charSequence).append(".  Expected type: ");
                    ((StringBuilder)charSequence).append(((Property)object).getType().toString());
                    ((StringBuilder)charSequence).append(". Actual type defined in resources: ");
                    ((StringBuilder)charSequence).append(propertyValuesHolder.getValueType().toString());
                    throw new RuntimeException(((StringBuilder)charSequence).toString());
                }
                break block7;
            }
            if (!(object instanceof AnimatorSet)) break block7;
            object = ((AnimatorSet)object).getChildAnimations().iterator();
            while (object.hasNext()) {
                AnimatedVectorDrawable.updateAnimatorProperty((Animator)object.next(), (String)charSequence, vectorDrawable, bl);
            }
        }
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        VectorDrawable vectorDrawable = this.mAnimatedVectorState.mVectorDrawable;
        if (vectorDrawable != null && vectorDrawable.canApplyTheme()) {
            vectorDrawable.applyTheme(theme);
        }
        if (theme != null) {
            this.mAnimatedVectorState.inflatePendingAnimators(theme.getResources(), theme);
        }
        if (this.mAnimatedVectorState.mPendingAnims == null) {
            this.mRes = null;
        }
    }

    @Override
    public boolean canApplyTheme() {
        AnimatedVectorDrawableState animatedVectorDrawableState = this.mAnimatedVectorState;
        boolean bl = animatedVectorDrawableState != null && animatedVectorDrawableState.canApplyTheme() || super.canApplyTheme();
        return bl;
    }

    public boolean canReverse() {
        return this.mAnimatorSet.canReverse();
    }

    @Override
    public void clearAnimationCallbacks() {
        this.removeAnimatorSetListener();
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList == null) {
            return;
        }
        arrayList.clear();
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        if (this.mAnimatedVectorState.mVectorDrawable != null) {
            this.mAnimatedVectorState.mVectorDrawable.clearMutated();
        }
        this.mMutated = false;
    }

    @Override
    public void draw(Canvas canvas) {
        VectorDrawableAnimator vectorDrawableAnimator;
        if (!canvas.isHardwareAccelerated() && (vectorDrawableAnimator = this.mAnimatorSet) instanceof VectorDrawableAnimatorRT && !vectorDrawableAnimator.isRunning() && ((VectorDrawableAnimatorRT)this.mAnimatorSet).mPendingAnimationActions.size() > 0) {
            this.fallbackOntoUI();
        }
        this.mAnimatorSet.onDraw(canvas);
        this.mAnimatedVectorState.mVectorDrawable.draw(canvas);
    }

    @UnsupportedAppUsage
    public void forceAnimationOnUI() {
        VectorDrawableAnimator vectorDrawableAnimator = this.mAnimatorSet;
        if (vectorDrawableAnimator instanceof VectorDrawableAnimatorRT) {
            if (!((VectorDrawableAnimatorRT)vectorDrawableAnimator).isRunning()) {
                this.fallbackOntoUI();
            } else {
                throw new UnsupportedOperationException("Cannot force Animated Vector Drawable to run on UI thread when the animation has started on RenderThread.");
            }
        }
    }

    @Override
    public int getAlpha() {
        return this.mAnimatedVectorState.mVectorDrawable.getAlpha();
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mAnimatedVectorState.getChangingConfigurations();
    }

    @Override
    public ColorFilter getColorFilter() {
        return this.mAnimatedVectorState.mVectorDrawable.getColorFilter();
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        this.mAnimatedVectorState.mChangingConfigurations = this.getChangingConfigurations();
        return this.mAnimatedVectorState;
    }

    @Override
    public int getIntrinsicHeight() {
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicWidth();
    }

    @Override
    public int getOpacity() {
        return -3;
    }

    @Override
    public Insets getOpticalInsets() {
        return this.mAnimatedVectorState.mVectorDrawable.getOpticalInsets();
    }

    @Override
    public void getOutline(Outline outline) {
        this.mAnimatedVectorState.mVectorDrawable.getOutline(outline);
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        Object object;
        AnimatedVectorDrawableState animatedVectorDrawableState = this.mAnimatedVectorState;
        int n = xmlPullParser.getEventType();
        float f = 1.0f;
        int n2 = xmlPullParser.getDepth();
        do {
            object = null;
            if (n == 1 || xmlPullParser.getDepth() < n2 + 1 && n == 3) break;
            float f2 = f;
            if (n == 2) {
                Object object2;
                object = xmlPullParser.getName();
                if ("animated-vector".equals(object)) {
                    object = AnimatedVectorDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedVectorDrawable);
                    n = ((TypedArray)object).getResourceId(0, 0);
                    if (n != 0) {
                        object2 = (VectorDrawable)resources.getDrawable(n, theme).mutate();
                        ((VectorDrawable)object2).setAllowCaching(false);
                        ((Drawable)object2).setCallback(this.mCallback);
                        f = ((VectorDrawable)object2).getPixelSize();
                        if (animatedVectorDrawableState.mVectorDrawable != null) {
                            animatedVectorDrawableState.mVectorDrawable.setCallback(null);
                        }
                        animatedVectorDrawableState.mVectorDrawable = object2;
                    }
                    ((TypedArray)object).recycle();
                    f2 = f;
                } else {
                    f2 = f;
                    if ("target".equals(object)) {
                        object2 = AnimatedVectorDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedVectorDrawableTarget);
                        String string2 = ((TypedArray)object2).getString(0);
                        n = ((TypedArray)object2).getResourceId(1, 0);
                        if (n != 0) {
                            if (theme != null) {
                                object = AnimatorInflater.loadAnimator(resources, theme, n, f);
                                AnimatedVectorDrawable.updateAnimatorProperty((Animator)object, string2, animatedVectorDrawableState.mVectorDrawable, animatedVectorDrawableState.mShouldIgnoreInvalidAnim);
                                animatedVectorDrawableState.addTargetAnimator(string2, (Animator)object);
                            } else {
                                animatedVectorDrawableState.addPendingAnimator(n, f, string2);
                            }
                        }
                        ((TypedArray)object2).recycle();
                        f2 = f;
                    }
                }
            }
            n = xmlPullParser.next();
            f = f2;
        } while (true);
        if (animatedVectorDrawableState.mPendingAnims == null) {
            resources = object;
        }
        this.mRes = resources;
    }

    @Override
    public boolean isRunning() {
        return this.mAnimatorSet.isRunning();
    }

    @Override
    public boolean isStateful() {
        return this.mAnimatedVectorState.mVectorDrawable.isStateful();
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mAnimatedVectorState = new AnimatedVectorDrawableState(this.mAnimatedVectorState, this.mCallback, this.mRes);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        this.mAnimatedVectorState.mVectorDrawable.setBounds(rect);
    }

    @Override
    public boolean onLayoutDirectionChanged(int n) {
        return this.mAnimatedVectorState.mVectorDrawable.setLayoutDirection(n);
    }

    @Override
    protected boolean onLevelChange(int n) {
        return this.mAnimatedVectorState.mVectorDrawable.setLevel(n);
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        return this.mAnimatedVectorState.mVectorDrawable.setState(arrn);
    }

    @Override
    public void registerAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        if (animationCallback == null) {
            return;
        }
        if (this.mAnimationCallbacks == null) {
            this.mAnimationCallbacks = new ArrayList<E>();
        }
        this.mAnimationCallbacks.add(animationCallback);
        if (this.mAnimatorListener == null) {
            this.mAnimatorListener = new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator cloneable) {
                    cloneable = new ArrayList(AnimatedVectorDrawable.this.mAnimationCallbacks);
                    int n = ((ArrayList)cloneable).size();
                    for (int i = 0; i < n; ++i) {
                        ((Animatable2.AnimationCallback)((ArrayList)cloneable).get(i)).onAnimationEnd(AnimatedVectorDrawable.this);
                    }
                }

                @Override
                public void onAnimationStart(Animator cloneable) {
                    cloneable = new ArrayList(AnimatedVectorDrawable.this.mAnimationCallbacks);
                    int n = ((ArrayList)cloneable).size();
                    for (int i = 0; i < n; ++i) {
                        ((Animatable2.AnimationCallback)((ArrayList)cloneable).get(i)).onAnimationStart(AnimatedVectorDrawable.this);
                    }
                }
            };
        }
        this.mAnimatorSet.setListener(this.mAnimatorListener);
    }

    public void reset() {
        this.ensureAnimatorSet();
        this.mAnimatorSet.reset();
    }

    public void reverse() {
        this.ensureAnimatorSet();
        if (!this.canReverse()) {
            Log.w("AnimatedVectorDrawable", "AnimatedVectorDrawable can't reverse()");
            return;
        }
        this.mAnimatorSet.reverse();
    }

    @Override
    public void setAlpha(int n) {
        this.mAnimatedVectorState.mVectorDrawable.setAlpha(n);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        this.mAnimatedVectorState.mVectorDrawable.setColorFilter(colorFilter);
    }

    @Override
    public void setHotspot(float f, float f2) {
        this.mAnimatedVectorState.mVectorDrawable.setHotspot(f, f2);
    }

    @Override
    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        this.mAnimatedVectorState.mVectorDrawable.setHotspotBounds(n, n2, n3, n4);
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        this.mAnimatedVectorState.mVectorDrawable.setTintBlendMode(blendMode);
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        this.mAnimatedVectorState.mVectorDrawable.setTintList(colorStateList);
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        if (this.mAnimatorSet.isInfinite() && this.mAnimatorSet.isStarted()) {
            if (bl) {
                this.mAnimatorSet.resume();
            } else {
                this.mAnimatorSet.pause();
            }
        }
        this.mAnimatedVectorState.mVectorDrawable.setVisible(bl, bl2);
        return super.setVisible(bl, bl2);
    }

    @Override
    public void start() {
        this.ensureAnimatorSet();
        this.mAnimatorSet.start();
    }

    @Override
    public void stop() {
        this.mAnimatorSet.end();
    }

    @Override
    public boolean unregisterAnimationCallback(Animatable2.AnimationCallback animationCallback) {
        ArrayList<Animatable2.AnimationCallback> arrayList = this.mAnimationCallbacks;
        if (arrayList != null && animationCallback != null) {
            boolean bl = arrayList.remove(animationCallback);
            if (this.mAnimationCallbacks.size() == 0) {
                this.removeAnimatorSetListener();
            }
            return bl;
        }
        return false;
    }

    private static class AnimatedVectorDrawableState
    extends Drawable.ConstantState {
        ArrayList<Animator> mAnimators;
        int mChangingConfigurations;
        ArrayList<PendingAnimator> mPendingAnims;
        private final boolean mShouldIgnoreInvalidAnim = AnimatedVectorDrawable.access$400();
        ArrayMap<Animator, String> mTargetNameMap;
        VectorDrawable mVectorDrawable;

        public AnimatedVectorDrawableState(AnimatedVectorDrawableState object, Drawable.Callback arrayMap, Resources resources) {
            if (object != null) {
                this.mChangingConfigurations = ((AnimatedVectorDrawableState)object).mChangingConfigurations;
                Object object2 = ((AnimatedVectorDrawableState)object).mVectorDrawable;
                if (object2 != null) {
                    object2 = ((VectorDrawable)object2).getConstantState();
                    this.mVectorDrawable = resources != null ? (VectorDrawable)((Drawable.ConstantState)object2).newDrawable(resources) : (VectorDrawable)((Drawable.ConstantState)object2).newDrawable();
                    this.mVectorDrawable = (VectorDrawable)this.mVectorDrawable.mutate();
                    this.mVectorDrawable.setCallback((Drawable.Callback)((Object)arrayMap));
                    this.mVectorDrawable.setLayoutDirection(((AnimatedVectorDrawableState)object).mVectorDrawable.getLayoutDirection());
                    this.mVectorDrawable.setBounds(((AnimatedVectorDrawableState)object).mVectorDrawable.getBounds());
                    this.mVectorDrawable.setAllowCaching(false);
                }
                if ((arrayMap = ((AnimatedVectorDrawableState)object).mAnimators) != null) {
                    this.mAnimators = new ArrayList(arrayMap);
                }
                if ((arrayMap = ((AnimatedVectorDrawableState)object).mTargetNameMap) != null) {
                    this.mTargetNameMap = new ArrayMap<Animator, String>(arrayMap);
                }
                if ((object = ((AnimatedVectorDrawableState)object).mPendingAnims) != null) {
                    this.mPendingAnims = new ArrayList(object);
                }
            } else {
                this.mVectorDrawable = new VectorDrawable();
            }
        }

        private Animator prepareLocalAnimator(int n) {
            Object object = this.mAnimators.get(n);
            Animator animator2 = ((Animator)object).clone();
            CharSequence charSequence = this.mTargetNameMap.get(object);
            object = this.mVectorDrawable.getTargetByName((String)charSequence);
            if (!this.mShouldIgnoreInvalidAnim) {
                if (object != null) {
                    if (!(object instanceof VectorDrawable.VectorDrawableState) && !(object instanceof VectorDrawable.VObject)) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append("Target should be either VGroup, VPath, or ConstantState, ");
                        ((StringBuilder)charSequence).append(object.getClass());
                        ((StringBuilder)charSequence).append(" is not supported");
                        throw new UnsupportedOperationException(((StringBuilder)charSequence).toString());
                    }
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Target with the name \"");
                    ((StringBuilder)object).append((String)charSequence);
                    ((StringBuilder)object).append("\" cannot be found in the VectorDrawable to be animated.");
                    throw new IllegalStateException(((StringBuilder)object).toString());
                }
            }
            animator2.setTarget(object);
            return animator2;
        }

        public void addPendingAnimator(int n, float f, String string2) {
            if (this.mPendingAnims == null) {
                this.mPendingAnims = new ArrayList(1);
            }
            this.mPendingAnims.add(new PendingAnimator(n, f, string2));
        }

        public void addTargetAnimator(String string2, Animator animator2) {
            if (this.mAnimators == null) {
                this.mAnimators = new ArrayList(1);
                this.mTargetNameMap = new ArrayMap(1);
            }
            this.mAnimators.add(animator2);
            this.mTargetNameMap.put(animator2, string2);
        }

        @Override
        public boolean canApplyTheme() {
            VectorDrawable vectorDrawable = this.mVectorDrawable;
            boolean bl = vectorDrawable != null && vectorDrawable.canApplyTheme() || this.mPendingAnims != null || super.canApplyTheme();
            return bl;
        }

        @Override
        public int getChangingConfigurations() {
            return this.mChangingConfigurations;
        }

        public void inflatePendingAnimators(Resources resources, Resources.Theme theme) {
            ArrayList<PendingAnimator> arrayList = this.mPendingAnims;
            if (arrayList != null) {
                this.mPendingAnims = null;
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    PendingAnimator pendingAnimator = arrayList.get(i);
                    Animator animator2 = pendingAnimator.newInstance(resources, theme);
                    AnimatedVectorDrawable.updateAnimatorProperty(animator2, pendingAnimator.target, this.mVectorDrawable, this.mShouldIgnoreInvalidAnim);
                    this.addTargetAnimator(pendingAnimator.target, animator2);
                }
            }
        }

        @Override
        public Drawable newDrawable() {
            return new AnimatedVectorDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new AnimatedVectorDrawable(this, resources);
        }

        public void prepareLocalAnimators(AnimatorSet object, Resources object2) {
            int n;
            if (this.mPendingAnims != null) {
                if (object2 != null) {
                    this.inflatePendingAnimators((Resources)object2, null);
                } else {
                    Log.e(AnimatedVectorDrawable.LOGTAG, "Failed to load animators. Either the AnimatedVectorDrawable must be created using a Resources object or applyTheme() must be called with a non-null Theme object.");
                }
                this.mPendingAnims = null;
            }
            if ((n = (object2 = this.mAnimators) == null ? 0 : ((ArrayList)object2).size()) > 0) {
                object = ((AnimatorSet)object).play(this.prepareLocalAnimator(0));
                for (int i = 1; i < n; ++i) {
                    ((AnimatorSet.Builder)object).with(this.prepareLocalAnimator(i));
                }
            }
        }

        private static class PendingAnimator {
            public final int animResId;
            public final float pathErrorScale;
            public final String target;

            public PendingAnimator(int n, float f, String string2) {
                this.animResId = n;
                this.pathErrorScale = f;
                this.target = string2;
            }

            public Animator newInstance(Resources resources, Resources.Theme theme) {
                return AnimatorInflater.loadAnimator(resources, theme, this.animResId, this.pathErrorScale);
            }
        }

    }

    private static interface VectorDrawableAnimator {
        public boolean canReverse();

        public void end();

        public void init(AnimatorSet var1);

        public boolean isInfinite();

        public boolean isRunning();

        public boolean isStarted();

        public void onDraw(Canvas var1);

        public void pause();

        public void removeListener(Animator.AnimatorListener var1);

        public void reset();

        public void resume();

        public void reverse();

        public void setListener(Animator.AnimatorListener var1);

        public void start();
    }

    public static class VectorDrawableAnimatorRT
    implements VectorDrawableAnimator,
    NativeVectorDrawableAnimator {
        private static final int END_ANIMATION = 4;
        private static final int MAX_SAMPLE_POINTS = 300;
        private static final int RESET_ANIMATION = 3;
        private static final int REVERSE_ANIMATION = 2;
        private static final int START_ANIMATION = 1;
        private boolean mContainsSequentialAnimators = false;
        private final AnimatedVectorDrawable mDrawable;
        private Handler mHandler;
        private boolean mInitialized = false;
        private boolean mIsInfinite = false;
        private boolean mIsReversible = false;
        private int mLastListenerId = 0;
        private WeakReference<RenderNode> mLastSeenTarget = null;
        private Animator.AnimatorListener mListener = null;
        private final IntArray mPendingAnimationActions = new IntArray();
        private long mSetPtr = 0L;
        private final VirtualRefBasePtr mSetRefBasePtr;
        private final LongArray mStartDelays = new LongArray();
        private boolean mStarted = false;
        private PropertyValuesHolder.PropertyValues mTmpValues = new PropertyValuesHolder.PropertyValues();

        VectorDrawableAnimatorRT(AnimatedVectorDrawable animatedVectorDrawable) {
            this.mDrawable = animatedVectorDrawable;
            this.mSetPtr = AnimatedVectorDrawable.nCreateAnimatorSet();
            this.mSetRefBasePtr = new VirtualRefBasePtr(this.mSetPtr);
        }

        private void addPendingAction(int n) {
            this.invalidateOwningView();
            this.mPendingAnimationActions.add(n);
        }

        @UnsupportedAppUsage
        private static void callOnFinished(VectorDrawableAnimatorRT vectorDrawableAnimatorRT, int n) {
            vectorDrawableAnimatorRT.mHandler.post(new _$$Lambda$AnimatedVectorDrawable$VectorDrawableAnimatorRT$PzjgSeyQweoFjbEZJP80UteZqm8(vectorDrawableAnimatorRT, n));
        }

        private static float[] createFloatDataPoints(PropertyValuesHolder.PropertyValues.DataSource dataSource, long l) {
            int n = VectorDrawableAnimatorRT.getFrameCount(l);
            float[] arrf = new float[n];
            float f = n - 1;
            for (int i = 0; i < n; ++i) {
                arrf[i] = ((Float)dataSource.getValueAtFraction((float)i / f)).floatValue();
            }
            return arrf;
        }

        private static int[] createIntDataPoints(PropertyValuesHolder.PropertyValues.DataSource dataSource, long l) {
            int n = VectorDrawableAnimatorRT.getFrameCount(l);
            int[] arrn = new int[n];
            float f = n - 1;
            for (int i = 0; i < n; ++i) {
                arrn[i] = (Integer)dataSource.getValueAtFraction((float)i / f);
            }
            return arrn;
        }

        private void createNativeChildAnimator(long l, long l2, ObjectAnimator objectAnimator) {
            long l3 = objectAnimator.getDuration();
            int n = objectAnimator.getRepeatCount();
            long l4 = objectAnimator.getStartDelay();
            TimeInterpolator timeInterpolator = objectAnimator.getInterpolator();
            long l5 = RenderNodeAnimatorSetHelper.createNativeInterpolator(timeInterpolator, l3);
            l2 = (long)((float)(l2 + l4) * ValueAnimator.getDurationScale());
            l3 = (long)((float)l3 * ValueAnimator.getDurationScale());
            this.mStartDelays.add(l2);
            AnimatedVectorDrawable.nAddAnimator(this.mSetPtr, l, l5, l2, l3, n, objectAnimator.getRepeatMode());
        }

        private void createRTAnimator(ObjectAnimator objectAnimator, long l) {
            PropertyValuesHolder[] arrpropertyValuesHolder = objectAnimator.getValues();
            Object object = objectAnimator.getTarget();
            if (object instanceof VectorDrawable.VGroup) {
                this.createRTAnimatorForGroup(arrpropertyValuesHolder, objectAnimator, (VectorDrawable.VGroup)object, l);
            } else if (object instanceof VectorDrawable.VPath) {
                for (int i = 0; i < arrpropertyValuesHolder.length; ++i) {
                    arrpropertyValuesHolder[i].getPropertyValues(this.mTmpValues);
                    if (this.mTmpValues.endValue instanceof PathParser.PathData && this.mTmpValues.propertyName.equals("pathData")) {
                        this.createRTAnimatorForPath(objectAnimator, (VectorDrawable.VPath)object, l);
                        continue;
                    }
                    if (object instanceof VectorDrawable.VFullPath) {
                        this.createRTAnimatorForFullPath(objectAnimator, (VectorDrawable.VFullPath)object, l);
                        continue;
                    }
                    if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        continue;
                    }
                    throw new IllegalArgumentException("ClipPath only supports PathData property");
                }
            } else if (object instanceof VectorDrawable.VectorDrawableState) {
                this.createRTAnimatorForRootGroup(arrpropertyValuesHolder, objectAnimator, (VectorDrawable.VectorDrawableState)object, l);
            }
        }

        private void createRTAnimatorForFullPath(ObjectAnimator object, VectorDrawable.VFullPath arrf, long l) {
            int n = arrf.getPropertyIndex(this.mTmpValues.propertyName);
            long l2 = arrf.getNativePtr();
            if (this.mTmpValues.type != Float.class && this.mTmpValues.type != Float.TYPE) {
                long l3;
                if (this.mTmpValues.type != Integer.class && this.mTmpValues.type != Integer.TYPE) {
                    if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unsupported type: ");
                    ((StringBuilder)object).append(this.mTmpValues.type);
                    ((StringBuilder)object).append(". Only float, int or PathData value is supported for Paths.");
                    throw new UnsupportedOperationException(((StringBuilder)object).toString());
                }
                l2 = l3 = AnimatedVectorDrawable.nCreatePathColorPropertyHolder(l2, n, (Integer)this.mTmpValues.startValue, (Integer)this.mTmpValues.endValue);
                if (this.mTmpValues.dataSource != null) {
                    arrf = VectorDrawableAnimatorRT.createIntDataPoints(this.mTmpValues.dataSource, ((ValueAnimator)object).getDuration());
                    AnimatedVectorDrawable.nSetPropertyHolderData(l3, (int[])arrf, arrf.length);
                    l2 = l3;
                }
            } else {
                long l4;
                if (n < 0) {
                    if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Property: ");
                    ((StringBuilder)object).append(this.mTmpValues.propertyName);
                    ((StringBuilder)object).append(" is not supported for FullPath");
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                l2 = l4 = AnimatedVectorDrawable.nCreatePathPropertyHolder(l2, n, ((Float)this.mTmpValues.startValue).floatValue(), ((Float)this.mTmpValues.endValue).floatValue());
                if (this.mTmpValues.dataSource != null) {
                    arrf = VectorDrawableAnimatorRT.createFloatDataPoints(this.mTmpValues.dataSource, ((ValueAnimator)object).getDuration());
                    AnimatedVectorDrawable.nSetPropertyHolderData(l4, arrf, arrf.length);
                    l2 = l4;
                }
            }
            this.createNativeChildAnimator(l2, l, (ObjectAnimator)object);
        }

        private void createRTAnimatorForGroup(PropertyValuesHolder[] arrpropertyValuesHolder, ObjectAnimator objectAnimator, VectorDrawable.VGroup arrf, long l) {
            long l2 = arrf.getNativePtr();
            for (int i = 0; i < arrpropertyValuesHolder.length; ++i) {
                arrpropertyValuesHolder[i].getPropertyValues(this.mTmpValues);
                int n = VectorDrawable.VGroup.getPropertyIndex(this.mTmpValues.propertyName);
                if (this.mTmpValues.type != Float.class && this.mTmpValues.type != Float.TYPE || n < 0) continue;
                long l3 = AnimatedVectorDrawable.nCreateGroupPropertyHolder(l2, n, ((Float)this.mTmpValues.startValue).floatValue(), ((Float)this.mTmpValues.endValue).floatValue());
                if (this.mTmpValues.dataSource != null) {
                    arrf = VectorDrawableAnimatorRT.createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                    AnimatedVectorDrawable.nSetPropertyHolderData(l3, arrf, arrf.length);
                }
                this.createNativeChildAnimator(l3, l, objectAnimator);
            }
        }

        private void createRTAnimatorForPath(ObjectAnimator objectAnimator, VectorDrawable.VPath vPath, long l) {
            this.createNativeChildAnimator(AnimatedVectorDrawable.nCreatePathDataPropertyHolder(vPath.getNativePtr(), ((PathParser.PathData)this.mTmpValues.startValue).getNativePtr(), ((PathParser.PathData)this.mTmpValues.endValue).getNativePtr()), l, objectAnimator);
        }

        private void createRTAnimatorForRootGroup(PropertyValuesHolder[] object, ObjectAnimator objectAnimator, VectorDrawable.VectorDrawableState object2, long l) {
            long l2;
            block6 : {
                l2 = ((VectorDrawable.VectorDrawableState)object2).getNativeRenderer();
                if (!objectAnimator.getPropertyName().equals("alpha")) {
                    if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                        return;
                    }
                    throw new UnsupportedOperationException("Only alpha is supported for root group");
                }
                for (int i = 0; i < ((PropertyValuesHolder[])object).length; ++i) {
                    object[i].getPropertyValues(this.mTmpValues);
                    if (!this.mTmpValues.propertyName.equals("alpha")) continue;
                    object2 = (Float)this.mTmpValues.startValue;
                    object = (Float)this.mTmpValues.endValue;
                    break block6;
                }
                object2 = null;
                object = null;
            }
            if (object2 == null && object == null) {
                if (this.mDrawable.mAnimatedVectorState.mShouldIgnoreInvalidAnim) {
                    return;
                }
                throw new UnsupportedOperationException("No alpha values are specified");
            }
            l2 = AnimatedVectorDrawable.nCreateRootAlphaPropertyHolder(l2, ((Float)object2).floatValue(), object.floatValue());
            if (this.mTmpValues.dataSource != null) {
                object = VectorDrawableAnimatorRT.createFloatDataPoints(this.mTmpValues.dataSource, objectAnimator.getDuration());
                AnimatedVectorDrawable.nSetPropertyHolderData(l2, (float[])object, ((PropertyValuesHolder[])object).length);
            }
            this.createNativeChildAnimator(l2, l, objectAnimator);
        }

        private void endAnimation() {
            AnimatedVectorDrawable.nEnd(this.mSetPtr);
            this.invalidateOwningView();
        }

        private static int getFrameCount(long l) {
            int n;
            int n2 = (int)(Choreographer.getInstance().getFrameIntervalNanos() / 1000000L);
            n2 = n = Math.max(2, (int)Math.ceil((double)l / (double)n2));
            if (n > 300) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Duration for the animation is too long :");
                stringBuilder.append(l);
                stringBuilder.append(", the animation will subsample the keyframe or path data.");
                Log.w(AnimatedVectorDrawable.LOGTAG, stringBuilder.toString());
                n2 = 300;
            }
            return n2;
        }

        private void handlePendingAction(int n) {
            block6 : {
                block3 : {
                    block5 : {
                        block4 : {
                            block2 : {
                                if (n != 1) break block2;
                                this.startAnimation();
                                break block3;
                            }
                            if (n != 2) break block4;
                            this.reverseAnimation();
                            break block3;
                        }
                        if (n != 3) break block5;
                        this.resetAnimation();
                        break block3;
                    }
                    if (n != 4) break block6;
                    this.endAnimation();
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Animation action ");
            stringBuilder.append(n);
            stringBuilder.append("is not supported");
            throw new UnsupportedOperationException(stringBuilder.toString());
        }

        private void invalidateOwningView() {
            this.mDrawable.invalidateSelf();
        }

        static /* synthetic */ void lambda$callOnFinished$0(VectorDrawableAnimatorRT vectorDrawableAnimatorRT, int n) {
            vectorDrawableAnimatorRT.onAnimationEnd(n);
        }

        private void onAnimationEnd(int n) {
            if (n != this.mLastListenerId) {
                return;
            }
            this.mStarted = false;
            this.invalidateOwningView();
            Animator.AnimatorListener animatorListener = this.mListener;
            if (animatorListener != null) {
                animatorListener.onAnimationEnd(null);
            }
        }

        private void parseAnimatorSet(AnimatorSet animator2, long l) {
            ArrayList<Animator> arrayList = ((AnimatorSet)animator2).getChildAnimations();
            boolean bl = ((AnimatorSet)animator2).shouldPlayTogether();
            for (int i = 0; i < arrayList.size(); ++i) {
                animator2 = arrayList.get(i);
                if (animator2 instanceof AnimatorSet) {
                    this.parseAnimatorSet((AnimatorSet)animator2, l);
                } else if (animator2 instanceof ObjectAnimator) {
                    this.createRTAnimator((ObjectAnimator)animator2, l);
                }
                long l2 = l;
                if (!bl) {
                    l2 = l + animator2.getTotalDuration();
                    this.mContainsSequentialAnimators = true;
                }
                l = l2;
            }
        }

        private void resetAnimation() {
            AnimatedVectorDrawable.nReset(this.mSetPtr);
            this.invalidateOwningView();
        }

        private void reverseAnimation() {
            int n;
            this.mStarted = true;
            long l = this.mSetPtr;
            this.mLastListenerId = n = this.mLastListenerId + 1;
            AnimatedVectorDrawable.nReverse(l, this, n);
            this.invalidateOwningView();
            Animator.AnimatorListener animatorListener = this.mListener;
            if (animatorListener != null) {
                animatorListener.onAnimationStart(null);
            }
        }

        private void startAnimation() {
            int n;
            this.mStarted = true;
            if (this.mHandler == null) {
                this.mHandler = new Handler();
            }
            long l = this.mSetPtr;
            this.mLastListenerId = n = this.mLastListenerId + 1;
            AnimatedVectorDrawable.nStart(l, this, n);
            this.invalidateOwningView();
            Animator.AnimatorListener animatorListener = this.mListener;
            if (animatorListener != null) {
                animatorListener.onAnimationStart(null);
            }
        }

        private void transferPendingActions(VectorDrawableAnimator object) {
            for (int i = 0; i < this.mPendingAnimationActions.size(); ++i) {
                int n = this.mPendingAnimationActions.get(i);
                if (n == 1) {
                    object.start();
                    continue;
                }
                if (n == 4) {
                    object.end();
                    continue;
                }
                if (n == 2) {
                    object.reverse();
                    continue;
                }
                if (n == 3) {
                    object.reset();
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Animation action ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("is not supported");
                throw new UnsupportedOperationException(((StringBuilder)object).toString());
            }
            this.mPendingAnimationActions.clear();
        }

        private boolean useLastSeenTarget() {
            WeakReference<RenderNode> weakReference = this.mLastSeenTarget;
            if (weakReference != null) {
                return this.useTarget((RenderNode)weakReference.get());
            }
            return false;
        }

        private boolean useTarget(RenderNode renderNode) {
            if (renderNode != null && renderNode.isAttached()) {
                renderNode.registerVectorDrawableAnimator(this);
                return true;
            }
            return false;
        }

        @Override
        public boolean canReverse() {
            return this.mIsReversible;
        }

        @Override
        public void end() {
            if (!this.mInitialized) {
                return;
            }
            if (this.useLastSeenTarget()) {
                this.endAnimation();
            } else {
                this.addPendingAction(4);
            }
        }

        @Override
        public long getAnimatorNativePtr() {
            return this.mSetPtr;
        }

        @Override
        public void init(AnimatorSet animatorSet) {
            if (!this.mInitialized) {
                this.parseAnimatorSet(animatorSet, 0L);
                long l = AnimatedVectorDrawable.access$900((AnimatedVectorDrawable)this.mDrawable).mVectorDrawable.getNativeTree();
                AnimatedVectorDrawable.nSetVectorDrawableTarget(this.mSetPtr, l);
                this.mInitialized = true;
                boolean bl = animatorSet.getTotalDuration() == -1L;
                this.mIsInfinite = bl;
                this.mIsReversible = true;
                if (this.mContainsSequentialAnimators) {
                    this.mIsReversible = false;
                } else {
                    for (int i = 0; i < this.mStartDelays.size(); ++i) {
                        if (this.mStartDelays.get(i) <= 0L) continue;
                        this.mIsReversible = false;
                        return;
                    }
                }
                return;
            }
            throw new UnsupportedOperationException("VectorDrawableAnimator cannot be re-initialized");
        }

        @Override
        public boolean isInfinite() {
            return this.mIsInfinite;
        }

        @Override
        public boolean isRunning() {
            if (!this.mInitialized) {
                return false;
            }
            return this.mStarted;
        }

        @Override
        public boolean isStarted() {
            return this.mStarted;
        }

        @Override
        public void onDraw(Canvas canvas) {
            if (canvas.isHardwareAccelerated()) {
                this.recordLastSeenTarget((RecordingCanvas)canvas);
            }
        }

        @Override
        public void pause() {
        }

        protected void recordLastSeenTarget(RecordingCanvas object) {
            object = RenderNodeAnimatorSetHelper.getTarget((RecordingCanvas)object);
            this.mLastSeenTarget = new WeakReference<Object>(object);
            if ((this.mInitialized || this.mPendingAnimationActions.size() > 0) && this.useTarget((RenderNode)object)) {
                for (int i = 0; i < this.mPendingAnimationActions.size(); ++i) {
                    this.handlePendingAction(this.mPendingAnimationActions.get(i));
                }
                this.mPendingAnimationActions.clear();
            }
        }

        @Override
        public void removeListener(Animator.AnimatorListener animatorListener) {
            this.mListener = null;
        }

        @Override
        public void reset() {
            if (!this.mInitialized) {
                return;
            }
            if (this.useLastSeenTarget()) {
                this.resetAnimation();
            } else {
                this.addPendingAction(3);
            }
        }

        @Override
        public void resume() {
        }

        @Override
        public void reverse() {
            if (this.mIsReversible && this.mInitialized) {
                if (this.useLastSeenTarget()) {
                    this.reverseAnimation();
                } else {
                    this.addPendingAction(2);
                }
                return;
            }
        }

        @Override
        public void setListener(Animator.AnimatorListener animatorListener) {
            this.mListener = animatorListener;
        }

        @Override
        public void start() {
            if (!this.mInitialized) {
                return;
            }
            if (this.useLastSeenTarget()) {
                this.startAnimation();
            } else {
                this.addPendingAction(1);
            }
        }
    }

    private static class VectorDrawableAnimatorUI
    implements VectorDrawableAnimator {
        private final Drawable mDrawable;
        private boolean mIsInfinite = false;
        private ArrayList<Animator.AnimatorListener> mListenerArray = null;
        private AnimatorSet mSet = null;

        VectorDrawableAnimatorUI(AnimatedVectorDrawable animatedVectorDrawable) {
            this.mDrawable = animatedVectorDrawable;
        }

        private void invalidateOwningView() {
            this.mDrawable.invalidateSelf();
        }

        @Override
        public boolean canReverse() {
            AnimatorSet animatorSet = this.mSet;
            boolean bl = animatorSet != null && animatorSet.canReverse();
            return bl;
        }

        @Override
        public void end() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.end();
        }

        @Override
        public void init(AnimatorSet cloneable) {
            if (this.mSet == null) {
                this.mSet = ((AnimatorSet)cloneable).clone();
                boolean bl = this.mSet.getTotalDuration() == -1L;
                this.mIsInfinite = bl;
                cloneable = this.mListenerArray;
                if (cloneable != null && !((ArrayList)cloneable).isEmpty()) {
                    for (int i = 0; i < this.mListenerArray.size(); ++i) {
                        this.mSet.addListener(this.mListenerArray.get(i));
                    }
                    this.mListenerArray.clear();
                    this.mListenerArray = null;
                }
                return;
            }
            throw new UnsupportedOperationException("VectorDrawableAnimator cannot be re-initialized");
        }

        @Override
        public boolean isInfinite() {
            return this.mIsInfinite;
        }

        @Override
        public boolean isRunning() {
            AnimatorSet animatorSet = this.mSet;
            boolean bl = animatorSet != null && animatorSet.isRunning();
            return bl;
        }

        @Override
        public boolean isStarted() {
            AnimatorSet animatorSet = this.mSet;
            boolean bl = animatorSet != null && animatorSet.isStarted();
            return bl;
        }

        @Override
        public void onDraw(Canvas object) {
            object = this.mSet;
            if (object != null && ((AnimatorSet)object).isStarted()) {
                this.invalidateOwningView();
            }
        }

        @Override
        public void pause() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.pause();
        }

        @Override
        public void removeListener(Animator.AnimatorListener animatorListener) {
            Cloneable cloneable = this.mSet;
            if (cloneable == null) {
                cloneable = this.mListenerArray;
                if (cloneable == null) {
                    return;
                }
                ((ArrayList)cloneable).remove(animatorListener);
            } else {
                ((Animator)cloneable).removeListener(animatorListener);
            }
        }

        @Override
        public void reset() {
            if (this.mSet == null) {
                return;
            }
            this.start();
            this.mSet.cancel();
        }

        @Override
        public void resume() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.resume();
        }

        @Override
        public void reverse() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                return;
            }
            animatorSet.reverse();
            this.invalidateOwningView();
        }

        @Override
        public void setListener(Animator.AnimatorListener animatorListener) {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet == null) {
                if (this.mListenerArray == null) {
                    this.mListenerArray = new ArrayList();
                }
                this.mListenerArray.add(animatorListener);
            } else {
                animatorSet.addListener(animatorListener);
            }
        }

        @Override
        public void start() {
            AnimatorSet animatorSet = this.mSet;
            if (animatorSet != null && !animatorSet.isStarted()) {
                this.mSet.start();
                this.invalidateOwningView();
                return;
            }
        }
    }

}

