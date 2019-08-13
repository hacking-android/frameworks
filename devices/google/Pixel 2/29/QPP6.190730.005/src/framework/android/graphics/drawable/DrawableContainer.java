/*
 * Decompiled with CFR 0.145.
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Insets;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.SparseArray;

public class DrawableContainer
extends Drawable
implements Drawable.Callback {
    private static final boolean DEBUG = false;
    private static final boolean DEFAULT_DITHER = true;
    private static final String TAG = "DrawableContainer";
    private int mAlpha = 255;
    private Runnable mAnimationRunnable;
    private BlockInvalidateCallback mBlockInvalidateCallback;
    private int mCurIndex = -1;
    private Drawable mCurrDrawable;
    @UnsupportedAppUsage
    private DrawableContainerState mDrawableContainerState;
    private long mEnterAnimationEnd;
    private long mExitAnimationEnd;
    private boolean mHasAlpha;
    private Rect mHotspotBounds;
    @UnsupportedAppUsage
    private Drawable mLastDrawable;
    private int mLastIndex = -1;
    private boolean mMutated;

    private void initializeDrawableForDisplay(Drawable drawable2) {
        block10 : {
            if (this.mBlockInvalidateCallback == null) {
                this.mBlockInvalidateCallback = new BlockInvalidateCallback();
            }
            drawable2.setCallback(this.mBlockInvalidateCallback.wrap(drawable2.getCallback()));
            if (this.mDrawableContainerState.mEnterFadeDuration <= 0 && this.mHasAlpha) {
                drawable2.setAlpha(this.mAlpha);
            }
            if (this.mDrawableContainerState.mHasColorFilter) {
                drawable2.setColorFilter(this.mDrawableContainerState.mColorFilter);
            } else {
                if (this.mDrawableContainerState.mHasTintList) {
                    drawable2.setTintList(this.mDrawableContainerState.mTintList);
                }
                if (this.mDrawableContainerState.mHasTintMode) {
                    drawable2.setTintBlendMode(this.mDrawableContainerState.mBlendMode);
                }
            }
            drawable2.setVisible(this.isVisible(), true);
            drawable2.setDither(this.mDrawableContainerState.mDither);
            drawable2.setState(this.getState());
            drawable2.setLevel(this.getLevel());
            drawable2.setBounds(this.getBounds());
            drawable2.setLayoutDirection(this.getLayoutDirection());
            drawable2.setAutoMirrored(this.mDrawableContainerState.mAutoMirrored);
            Rect rect = this.mHotspotBounds;
            if (rect == null) break block10;
            drawable2.setHotspotBounds(rect.left, rect.top, rect.right, rect.bottom);
        }
        return;
        finally {
            drawable2.setCallback(this.mBlockInvalidateCallback.unwrap());
        }
    }

    private boolean needsMirroring() {
        boolean bl = this.isAutoMirrored();
        boolean bl2 = true;
        if (!bl || this.getLayoutDirection() != 1) {
            bl2 = false;
        }
        return bl2;
    }

    void animate(boolean bl) {
        int n;
        long l;
        this.mHasAlpha = true;
        long l2 = SystemClock.uptimeMillis();
        int n2 = 0;
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            l = this.mEnterAnimationEnd;
            n = n2;
            if (l != 0L) {
                if (l <= l2) {
                    drawable2.setAlpha(this.mAlpha);
                    this.mEnterAnimationEnd = 0L;
                    n = n2;
                } else {
                    n = (int)((l - l2) * 255L) / this.mDrawableContainerState.mEnterFadeDuration;
                    this.mCurrDrawable.setAlpha((255 - n) * this.mAlpha / 255);
                    n = 1;
                }
            }
        } else {
            this.mEnterAnimationEnd = 0L;
            n = n2;
        }
        if ((drawable2 = this.mLastDrawable) != null) {
            l = this.mExitAnimationEnd;
            n2 = n;
            if (l != 0L) {
                if (l <= l2) {
                    drawable2.setVisible(false, false);
                    this.mLastDrawable = null;
                    this.mLastIndex = -1;
                    this.mExitAnimationEnd = 0L;
                    n2 = n;
                } else {
                    n = (int)((l - l2) * 255L) / this.mDrawableContainerState.mExitFadeDuration;
                    this.mLastDrawable.setAlpha(this.mAlpha * n / 255);
                    n2 = 1;
                }
            }
        } else {
            this.mExitAnimationEnd = 0L;
            n2 = n;
        }
        if (bl && n2 != 0) {
            this.scheduleSelf(this.mAnimationRunnable, 16L + l2);
        }
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        this.mDrawableContainerState.applyTheme(theme);
    }

    @Override
    public boolean canApplyTheme() {
        return this.mDrawableContainerState.canApplyTheme();
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mDrawableContainerState.clearMutated();
        this.mMutated = false;
    }

    DrawableContainerState cloneConstantState() {
        return this.mDrawableContainerState;
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.draw(canvas);
        }
        if ((drawable2 = this.mLastDrawable) != null) {
            drawable2.draw(canvas);
        }
    }

    @Override
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override
    public int getChangingConfigurations() {
        return super.getChangingConfigurations() | this.mDrawableContainerState.getChangingConfigurations();
    }

    @Override
    public Drawable.ConstantState getConstantState() {
        if (this.mDrawableContainerState.canConstantState()) {
            this.mDrawableContainerState.mChangingConfigurations = this.getChangingConfigurations();
            return this.mDrawableContainerState;
        }
        return null;
    }

    @Override
    public Drawable getCurrent() {
        return this.mCurrDrawable;
    }

    public int getCurrentIndex() {
        return this.mCurIndex;
    }

    @Override
    public void getHotspotBounds(Rect rect) {
        Rect rect2 = this.mHotspotBounds;
        if (rect2 != null) {
            rect.set(rect2);
        } else {
            super.getHotspotBounds(rect);
        }
    }

    @Override
    public int getIntrinsicHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantHeight();
        }
        Drawable drawable2 = this.mCurrDrawable;
        int n = drawable2 != null ? drawable2.getIntrinsicHeight() : -1;
        return n;
    }

    @Override
    public int getIntrinsicWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantWidth();
        }
        Drawable drawable2 = this.mCurrDrawable;
        int n = drawable2 != null ? drawable2.getIntrinsicWidth() : -1;
        return n;
    }

    @Override
    public int getMinimumHeight() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumHeight();
        }
        Drawable drawable2 = this.mCurrDrawable;
        int n = drawable2 != null ? drawable2.getMinimumHeight() : 0;
        return n;
    }

    @Override
    public int getMinimumWidth() {
        if (this.mDrawableContainerState.isConstantSize()) {
            return this.mDrawableContainerState.getConstantMinimumWidth();
        }
        Drawable drawable2 = this.mCurrDrawable;
        int n = drawable2 != null ? drawable2.getMinimumWidth() : 0;
        return n;
    }

    @Override
    public int getOpacity() {
        Drawable drawable2 = this.mCurrDrawable;
        int n = drawable2 != null && drawable2.isVisible() ? this.mDrawableContainerState.getOpacity() : -2;
        return n;
    }

    @Override
    public Insets getOpticalInsets() {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.getOpticalInsets();
        }
        return Insets.NONE;
    }

    @Override
    public void getOutline(Outline outline) {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.getOutline(outline);
        }
    }

    @Override
    public boolean getPadding(Rect rect) {
        boolean bl;
        Object object = this.mDrawableContainerState.getConstantPadding();
        if (object != null) {
            rect.set((Rect)object);
            bl = (((Rect)object).left | ((Rect)object).top | ((Rect)object).bottom | ((Rect)object).right) != 0;
        } else {
            object = this.mCurrDrawable;
            bl = object != null ? ((Drawable)object).getPadding(rect) : super.getPadding(rect);
        }
        if (this.needsMirroring()) {
            int n = rect.left;
            rect.left = rect.right;
            rect.right = n;
        }
        return bl;
    }

    @Override
    public boolean hasFocusStateSpecified() {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.hasFocusStateSpecified();
        }
        drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            return drawable2.hasFocusStateSpecified();
        }
        return false;
    }

    @Override
    public void invalidateDrawable(Drawable drawable2) {
        DrawableContainerState drawableContainerState = this.mDrawableContainerState;
        if (drawableContainerState != null) {
            drawableContainerState.invalidateCache();
        }
        if (drawable2 == this.mCurrDrawable && this.getCallback() != null) {
            this.getCallback().invalidateDrawable(this);
        }
    }

    @Override
    public boolean isAutoMirrored() {
        return this.mDrawableContainerState.mAutoMirrored;
    }

    @Override
    public boolean isStateful() {
        return this.mDrawableContainerState.isStateful();
    }

    @Override
    public void jumpToCurrentState() {
        boolean bl = false;
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.jumpToCurrentState();
            this.mLastDrawable = null;
            this.mLastIndex = -1;
            bl = true;
        }
        if ((drawable2 = this.mCurrDrawable) != null) {
            drawable2.jumpToCurrentState();
            if (this.mHasAlpha) {
                this.mCurrDrawable.setAlpha(this.mAlpha);
            }
        }
        if (this.mExitAnimationEnd != 0L) {
            this.mExitAnimationEnd = 0L;
            bl = true;
        }
        if (this.mEnterAnimationEnd != 0L) {
            this.mEnterAnimationEnd = 0L;
            bl = true;
        }
        if (bl) {
            this.invalidateSelf();
        }
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            DrawableContainerState drawableContainerState = this.cloneConstantState();
            drawableContainerState.mutate();
            this.setConstantState(drawableContainerState);
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.setBounds(rect);
        }
        if ((drawable2 = this.mCurrDrawable) != null) {
            drawable2.setBounds(rect);
        }
    }

    @Override
    public boolean onLayoutDirectionChanged(int n) {
        return this.mDrawableContainerState.setLayoutDirection(n, this.getCurrentIndex());
    }

    @Override
    protected boolean onLevelChange(int n) {
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            return drawable2.setLevel(n);
        }
        drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setLevel(n);
        }
        return false;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            return drawable2.setState(arrn);
        }
        drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            return drawable2.setState(arrn);
        }
        return false;
    }

    @Override
    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        if (drawable2 == this.mCurrDrawable && this.getCallback() != null) {
            this.getCallback().scheduleDrawable(this, runnable, l);
        }
    }

    public boolean selectDrawable(int n) {
        Object object;
        if (n == this.mCurIndex) {
            return false;
        }
        long l = SystemClock.uptimeMillis();
        if (this.mDrawableContainerState.mExitFadeDuration > 0) {
            object = this.mLastDrawable;
            if (object != null) {
                ((Drawable)object).setVisible(false, false);
            }
            if ((object = this.mCurrDrawable) != null) {
                this.mLastDrawable = object;
                this.mLastIndex = this.mCurIndex;
                this.mExitAnimationEnd = (long)this.mDrawableContainerState.mExitFadeDuration + l;
            } else {
                this.mLastDrawable = null;
                this.mLastIndex = -1;
                this.mExitAnimationEnd = 0L;
            }
        } else {
            object = this.mCurrDrawable;
            if (object != null) {
                ((Drawable)object).setVisible(false, false);
            }
        }
        if (n >= 0 && n < this.mDrawableContainerState.mNumChildren) {
            object = this.mDrawableContainerState.getChild(n);
            this.mCurrDrawable = object;
            this.mCurIndex = n;
            if (object != null) {
                if (this.mDrawableContainerState.mEnterFadeDuration > 0) {
                    this.mEnterAnimationEnd = (long)this.mDrawableContainerState.mEnterFadeDuration + l;
                }
                this.initializeDrawableForDisplay((Drawable)object);
            }
        } else {
            this.mCurrDrawable = null;
            this.mCurIndex = -1;
        }
        if (this.mEnterAnimationEnd != 0L || this.mExitAnimationEnd != 0L) {
            object = this.mAnimationRunnable;
            if (object == null) {
                this.mAnimationRunnable = new Runnable(){

                    @Override
                    public void run() {
                        DrawableContainer.this.animate(true);
                        DrawableContainer.this.invalidateSelf();
                    }
                };
            } else {
                this.unscheduleSelf((Runnable)object);
            }
            this.animate(true);
        }
        this.invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(int n) {
        if (!this.mHasAlpha || this.mAlpha != n) {
            this.mHasAlpha = true;
            this.mAlpha = n;
            Drawable drawable2 = this.mCurrDrawable;
            if (drawable2 != null) {
                if (this.mEnterAnimationEnd == 0L) {
                    drawable2.setAlpha(n);
                } else {
                    this.animate(false);
                }
            }
        }
    }

    @Override
    public void setAutoMirrored(boolean bl) {
        if (this.mDrawableContainerState.mAutoMirrored != bl) {
            DrawableContainerState drawableContainerState = this.mDrawableContainerState;
            drawableContainerState.mAutoMirrored = bl;
            Drawable drawable2 = this.mCurrDrawable;
            if (drawable2 != null) {
                drawable2.setAutoMirrored(drawableContainerState.mAutoMirrored);
            }
        }
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        Object object = this.mDrawableContainerState;
        ((DrawableContainerState)object).mHasColorFilter = true;
        if (((DrawableContainerState)object).mColorFilter != colorFilter) {
            this.mDrawableContainerState.mColorFilter = colorFilter;
            object = this.mCurrDrawable;
            if (object != null) {
                ((Drawable)object).setColorFilter(colorFilter);
            }
        }
    }

    protected void setConstantState(DrawableContainerState object) {
        this.mDrawableContainerState = object;
        int n = this.mCurIndex;
        if (n >= 0) {
            this.mCurrDrawable = ((DrawableContainerState)object).getChild(n);
            object = this.mCurrDrawable;
            if (object != null) {
                this.initializeDrawableForDisplay((Drawable)object);
            }
        }
        this.mLastIndex = -1;
        this.mLastDrawable = null;
    }

    public void setCurrentIndex(int n) {
        this.selectDrawable(n);
    }

    @Override
    public void setDither(boolean bl) {
        if (this.mDrawableContainerState.mDither != bl) {
            DrawableContainerState drawableContainerState = this.mDrawableContainerState;
            drawableContainerState.mDither = bl;
            Drawable drawable2 = this.mCurrDrawable;
            if (drawable2 != null) {
                drawable2.setDither(drawableContainerState.mDither);
            }
        }
    }

    public void setEnterFadeDuration(int n) {
        this.mDrawableContainerState.mEnterFadeDuration = n;
    }

    public void setExitFadeDuration(int n) {
        this.mDrawableContainerState.mExitFadeDuration = n;
    }

    @Override
    public void setHotspot(float f, float f2) {
        Drawable drawable2 = this.mCurrDrawable;
        if (drawable2 != null) {
            drawable2.setHotspot(f, f2);
        }
    }

    @Override
    public void setHotspotBounds(int n, int n2, int n3, int n4) {
        Object object = this.mHotspotBounds;
        if (object == null) {
            this.mHotspotBounds = new Rect(n, n2, n3, n4);
        } else {
            ((Rect)object).set(n, n2, n3, n4);
        }
        object = this.mCurrDrawable;
        if (object != null) {
            ((Drawable)object).setHotspotBounds(n, n2, n3, n4);
        }
    }

    @Override
    public void setTintBlendMode(BlendMode blendMode) {
        Object object = this.mDrawableContainerState;
        ((DrawableContainerState)object).mHasTintMode = true;
        if (((DrawableContainerState)object).mBlendMode != blendMode) {
            this.mDrawableContainerState.mBlendMode = blendMode;
            object = this.mCurrDrawable;
            if (object != null) {
                ((Drawable)object).setTintBlendMode(blendMode);
            }
        }
    }

    @Override
    public void setTintList(ColorStateList colorStateList) {
        Object object = this.mDrawableContainerState;
        ((DrawableContainerState)object).mHasTintList = true;
        if (((DrawableContainerState)object).mTintList != colorStateList) {
            this.mDrawableContainerState.mTintList = colorStateList;
            object = this.mCurrDrawable;
            if (object != null) {
                ((Drawable)object).setTintList(colorStateList);
            }
        }
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        Drawable drawable2 = this.mLastDrawable;
        if (drawable2 != null) {
            drawable2.setVisible(bl, bl2);
        }
        if ((drawable2 = this.mCurrDrawable) != null) {
            drawable2.setVisible(bl, bl2);
        }
        return bl3;
    }

    @Override
    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        if (drawable2 == this.mCurrDrawable && this.getCallback() != null) {
            this.getCallback().unscheduleDrawable(this, runnable);
        }
    }

    protected final void updateDensity(Resources resources) {
        this.mDrawableContainerState.updateDensity(resources);
    }

    private static class BlockInvalidateCallback
    implements Drawable.Callback {
        private Drawable.Callback mCallback;

        private BlockInvalidateCallback() {
        }

        @Override
        public void invalidateDrawable(Drawable drawable2) {
        }

        @Override
        public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.scheduleDrawable(drawable2, runnable, l);
            }
        }

        @Override
        public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
            Drawable.Callback callback = this.mCallback;
            if (callback != null) {
                callback.unscheduleDrawable(drawable2, runnable);
            }
        }

        public Drawable.Callback unwrap() {
            Drawable.Callback callback = this.mCallback;
            this.mCallback = null;
            return callback;
        }

        public BlockInvalidateCallback wrap(Drawable.Callback callback) {
            this.mCallback = callback;
            return this;
        }
    }

    public static abstract class DrawableContainerState
    extends Drawable.ConstantState {
        boolean mAutoMirrored;
        BlendMode mBlendMode;
        boolean mCanConstantState;
        int mChangingConfigurations;
        boolean mCheckedConstantSize;
        boolean mCheckedConstantState;
        boolean mCheckedOpacity;
        boolean mCheckedPadding;
        boolean mCheckedStateful;
        int mChildrenChangingConfigurations;
        ColorFilter mColorFilter;
        int mConstantHeight;
        int mConstantMinimumHeight;
        int mConstantMinimumWidth;
        @UnsupportedAppUsage
        Rect mConstantPadding;
        boolean mConstantSize = false;
        int mConstantWidth;
        int mDensity = 160;
        boolean mDither = true;
        SparseArray<Drawable.ConstantState> mDrawableFutures;
        @UnsupportedAppUsage
        Drawable[] mDrawables;
        int mEnterFadeDuration = 0;
        int mExitFadeDuration = 0;
        @UnsupportedAppUsage
        boolean mHasColorFilter;
        boolean mHasTintList;
        boolean mHasTintMode;
        int mLayoutDirection;
        boolean mMutated;
        int mNumChildren;
        int mOpacity;
        final DrawableContainer mOwner;
        Resources mSourceRes;
        boolean mStateful;
        ColorStateList mTintList;
        boolean mVariablePadding = false;

        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        protected DrawableContainerState(DrawableContainerState object, DrawableContainer object2, Resources resources) {
            this.mOwner = object2;
            object2 = resources != null ? resources : (object != null ? ((DrawableContainerState)object).mSourceRes : null);
            this.mSourceRes = object2;
            int n = object != null ? ((DrawableContainerState)object).mDensity : 0;
            this.mDensity = Drawable.resolveDensity(resources, n);
            if (object != null) {
                this.mChangingConfigurations = ((DrawableContainerState)object).mChangingConfigurations;
                this.mChildrenChangingConfigurations = ((DrawableContainerState)object).mChildrenChangingConfigurations;
                this.mCheckedConstantState = true;
                this.mCanConstantState = true;
                this.mVariablePadding = ((DrawableContainerState)object).mVariablePadding;
                this.mConstantSize = ((DrawableContainerState)object).mConstantSize;
                this.mDither = ((DrawableContainerState)object).mDither;
                this.mMutated = ((DrawableContainerState)object).mMutated;
                this.mLayoutDirection = ((DrawableContainerState)object).mLayoutDirection;
                this.mEnterFadeDuration = ((DrawableContainerState)object).mEnterFadeDuration;
                this.mExitFadeDuration = ((DrawableContainerState)object).mExitFadeDuration;
                this.mAutoMirrored = ((DrawableContainerState)object).mAutoMirrored;
                this.mColorFilter = ((DrawableContainerState)object).mColorFilter;
                this.mHasColorFilter = ((DrawableContainerState)object).mHasColorFilter;
                this.mTintList = ((DrawableContainerState)object).mTintList;
                this.mBlendMode = ((DrawableContainerState)object).mBlendMode;
                this.mHasTintList = ((DrawableContainerState)object).mHasTintList;
                this.mHasTintMode = ((DrawableContainerState)object).mHasTintMode;
                if (((DrawableContainerState)object).mDensity == this.mDensity) {
                    if (((DrawableContainerState)object).mCheckedPadding) {
                        this.mConstantPadding = new Rect(((DrawableContainerState)object).mConstantPadding);
                        this.mCheckedPadding = true;
                    }
                    if (((DrawableContainerState)object).mCheckedConstantSize) {
                        this.mConstantWidth = ((DrawableContainerState)object).mConstantWidth;
                        this.mConstantHeight = ((DrawableContainerState)object).mConstantHeight;
                        this.mConstantMinimumWidth = ((DrawableContainerState)object).mConstantMinimumWidth;
                        this.mConstantMinimumHeight = ((DrawableContainerState)object).mConstantMinimumHeight;
                        this.mCheckedConstantSize = true;
                    }
                }
                if (((DrawableContainerState)object).mCheckedOpacity) {
                    this.mOpacity = ((DrawableContainerState)object).mOpacity;
                    this.mCheckedOpacity = true;
                }
                if (((DrawableContainerState)object).mCheckedStateful) {
                    this.mStateful = ((DrawableContainerState)object).mStateful;
                    this.mCheckedStateful = true;
                }
                object2 = ((DrawableContainerState)object).mDrawables;
                this.mDrawables = new Drawable[((Drawable[])object2).length];
                this.mNumChildren = ((DrawableContainerState)object).mNumChildren;
                object = ((DrawableContainerState)object).mDrawableFutures;
                this.mDrawableFutures = object != null ? ((SparseArray)object).clone() : new SparseArray(this.mNumChildren);
                int n2 = this.mNumChildren;
                for (n = 0; n < n2; ++n) {
                    if (object2[n] == null) continue;
                    object = object2[n].getConstantState();
                    if (object != null) {
                        this.mDrawableFutures.put(n, (Drawable.ConstantState)object);
                        continue;
                    }
                    this.mDrawables[n] = object2[n];
                }
            } else {
                this.mDrawables = new Drawable[10];
                this.mNumChildren = 0;
            }
        }

        private void createAllFutures() {
            SparseArray<Drawable.ConstantState> sparseArray = this.mDrawableFutures;
            if (sparseArray != null) {
                int n = sparseArray.size();
                for (int i = 0; i < n; ++i) {
                    int n2 = this.mDrawableFutures.keyAt(i);
                    sparseArray = this.mDrawableFutures.valueAt(i);
                    this.mDrawables[n2] = this.prepareDrawable(((Drawable.ConstantState)((Object)sparseArray)).newDrawable(this.mSourceRes));
                }
                this.mDrawableFutures = null;
            }
        }

        private void mutate() {
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            for (int i = 0; i < n; ++i) {
                if (arrdrawable[i] == null) continue;
                arrdrawable[i].mutate();
            }
            this.mMutated = true;
        }

        private Drawable prepareDrawable(Drawable drawable2) {
            drawable2.setLayoutDirection(this.mLayoutDirection);
            drawable2 = drawable2.mutate();
            drawable2.setCallback(this.mOwner);
            return drawable2;
        }

        public final int addChild(Drawable drawable2) {
            int n = this.mNumChildren;
            if (n >= this.mDrawables.length) {
                this.growArray(n, n + 10);
            }
            drawable2.mutate();
            drawable2.setVisible(false, true);
            drawable2.setCallback(this.mOwner);
            this.mDrawables[n] = drawable2;
            ++this.mNumChildren;
            this.mChildrenChangingConfigurations |= drawable2.getChangingConfigurations();
            this.invalidateCache();
            this.mConstantPadding = null;
            this.mCheckedPadding = false;
            this.mCheckedConstantSize = false;
            this.mCheckedConstantState = false;
            return n;
        }

        final void applyTheme(Resources.Theme theme) {
            if (theme != null) {
                this.createAllFutures();
                int n = this.mNumChildren;
                Drawable[] arrdrawable = this.mDrawables;
                for (int i = 0; i < n; ++i) {
                    if (arrdrawable[i] == null || !arrdrawable[i].canApplyTheme()) continue;
                    arrdrawable[i].applyTheme(theme);
                    this.mChildrenChangingConfigurations |= arrdrawable[i].getChangingConfigurations();
                }
                this.updateDensity(theme.getResources());
            }
        }

        @Override
        public boolean canApplyTheme() {
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            for (int i = 0; i < n; ++i) {
                Object object = arrdrawable[i];
                if (!(object != null ? ((Drawable)object).canApplyTheme() : (object = this.mDrawableFutures.get(i)) != null && ((Drawable.ConstantState)object).canApplyTheme())) continue;
                return true;
            }
            return false;
        }

        public boolean canConstantState() {
            synchronized (this) {
                block7 : {
                    if (!this.mCheckedConstantState) break block7;
                    boolean bl = this.mCanConstantState;
                    return bl;
                }
                this.createAllFutures();
                this.mCheckedConstantState = true;
                int n = this.mNumChildren;
                Drawable[] arrdrawable = this.mDrawables;
                for (int i = 0; i < n; ++i) {
                    if (arrdrawable[i].getConstantState() != null) continue;
                    this.mCanConstantState = false;
                    return false;
                }
                this.mCanConstantState = true;
                return true;
            }
        }

        final void clearMutated() {
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            for (int i = 0; i < n; ++i) {
                if (arrdrawable[i] == null) continue;
                arrdrawable[i].clearMutated();
            }
            this.mMutated = false;
        }

        protected void computeConstantSize() {
            this.mCheckedConstantSize = true;
            this.createAllFutures();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            this.mConstantHeight = -1;
            this.mConstantWidth = -1;
            this.mConstantMinimumHeight = 0;
            this.mConstantMinimumWidth = 0;
            for (int i = 0; i < n; ++i) {
                Drawable drawable2 = arrdrawable[i];
                int n2 = drawable2.getIntrinsicWidth();
                if (n2 > this.mConstantWidth) {
                    this.mConstantWidth = n2;
                }
                if ((n2 = drawable2.getIntrinsicHeight()) > this.mConstantHeight) {
                    this.mConstantHeight = n2;
                }
                if ((n2 = drawable2.getMinimumWidth()) > this.mConstantMinimumWidth) {
                    this.mConstantMinimumWidth = n2;
                }
                if ((n2 = drawable2.getMinimumHeight()) <= this.mConstantMinimumHeight) continue;
                this.mConstantMinimumHeight = n2;
            }
        }

        final int getCapacity() {
            return this.mDrawables.length;
        }

        @Override
        public int getChangingConfigurations() {
            return this.mChangingConfigurations | this.mChildrenChangingConfigurations;
        }

        public final Drawable getChild(int n) {
            int n2;
            SparseArray<Drawable.ConstantState> sparseArray = this.mDrawables[n];
            if (sparseArray != null) {
                return sparseArray;
            }
            sparseArray = this.mDrawableFutures;
            if (sparseArray != null && (n2 = sparseArray.indexOfKey(n)) >= 0) {
                this.mDrawables[n] = sparseArray = this.prepareDrawable(this.mDrawableFutures.valueAt(n2).newDrawable(this.mSourceRes));
                this.mDrawableFutures.removeAt(n2);
                if (this.mDrawableFutures.size() == 0) {
                    this.mDrawableFutures = null;
                }
                return sparseArray;
            }
            return null;
        }

        public final int getChildCount() {
            return this.mNumChildren;
        }

        public final Drawable[] getChildren() {
            this.createAllFutures();
            return this.mDrawables;
        }

        public final int getConstantHeight() {
            if (!this.mCheckedConstantSize) {
                this.computeConstantSize();
            }
            return this.mConstantHeight;
        }

        public final int getConstantMinimumHeight() {
            if (!this.mCheckedConstantSize) {
                this.computeConstantSize();
            }
            return this.mConstantMinimumHeight;
        }

        public final int getConstantMinimumWidth() {
            if (!this.mCheckedConstantSize) {
                this.computeConstantSize();
            }
            return this.mConstantMinimumWidth;
        }

        public final Rect getConstantPadding() {
            if (this.mVariablePadding) {
                return null;
            }
            if (this.mConstantPadding == null && !this.mCheckedPadding) {
                this.createAllFutures();
                Rect rect = null;
                Rect rect2 = new Rect();
                int n = this.mNumChildren;
                Drawable[] arrdrawable = this.mDrawables;
                for (int i = 0; i < n; ++i) {
                    Rect rect3 = rect;
                    if (arrdrawable[i].getPadding(rect2)) {
                        Rect rect4 = rect;
                        if (rect == null) {
                            rect4 = new Rect(0, 0, 0, 0);
                        }
                        if (rect2.left > rect4.left) {
                            rect4.left = rect2.left;
                        }
                        if (rect2.top > rect4.top) {
                            rect4.top = rect2.top;
                        }
                        if (rect2.right > rect4.right) {
                            rect4.right = rect2.right;
                        }
                        rect3 = rect4;
                        if (rect2.bottom > rect4.bottom) {
                            rect4.bottom = rect2.bottom;
                            rect3 = rect4;
                        }
                    }
                    rect = rect3;
                }
                this.mCheckedPadding = true;
                this.mConstantPadding = rect;
                return rect;
            }
            return this.mConstantPadding;
        }

        public final int getConstantWidth() {
            if (!this.mCheckedConstantSize) {
                this.computeConstantSize();
            }
            return this.mConstantWidth;
        }

        public final int getEnterFadeDuration() {
            return this.mEnterFadeDuration;
        }

        public final int getExitFadeDuration() {
            return this.mExitFadeDuration;
        }

        public final int getOpacity() {
            if (this.mCheckedOpacity) {
                return this.mOpacity;
            }
            this.createAllFutures();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            int n2 = n > 0 ? arrdrawable[0].getOpacity() : -2;
            for (int i = 1; i < n; ++i) {
                n2 = Drawable.resolveOpacity(n2, arrdrawable[i].getOpacity());
            }
            this.mOpacity = n2;
            this.mCheckedOpacity = true;
            return n2;
        }

        public void growArray(int n, int n2) {
            Drawable[] arrdrawable = new Drawable[n2];
            System.arraycopy(this.mDrawables, 0, arrdrawable, 0, n);
            this.mDrawables = arrdrawable;
        }

        void invalidateCache() {
            this.mCheckedOpacity = false;
            this.mCheckedStateful = false;
        }

        public final boolean isConstantSize() {
            return this.mConstantSize;
        }

        public final boolean isStateful() {
            boolean bl;
            if (this.mCheckedStateful) {
                return this.mStateful;
            }
            this.createAllFutures();
            int n = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            boolean bl2 = false;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                if (arrdrawable[n2].isStateful()) {
                    bl = true;
                    break;
                }
                ++n2;
            } while (true);
            this.mStateful = bl;
            this.mCheckedStateful = true;
            return bl;
        }

        public final void setConstantSize(boolean bl) {
            this.mConstantSize = bl;
        }

        public final void setEnterFadeDuration(int n) {
            this.mEnterFadeDuration = n;
        }

        public final void setExitFadeDuration(int n) {
            this.mExitFadeDuration = n;
        }

        final boolean setLayoutDirection(int n, int n2) {
            boolean bl = false;
            int n3 = this.mNumChildren;
            Drawable[] arrdrawable = this.mDrawables;
            for (int i = 0; i < n3; ++i) {
                boolean bl2 = bl;
                if (arrdrawable[i] != null) {
                    boolean bl3 = arrdrawable[i].setLayoutDirection(n);
                    bl2 = bl;
                    if (i == n2) {
                        bl2 = bl3;
                    }
                }
                bl = bl2;
            }
            this.mLayoutDirection = n;
            return bl;
        }

        public final void setVariablePadding(boolean bl) {
            this.mVariablePadding = bl;
        }

        final void updateDensity(Resources resources) {
            if (resources != null) {
                this.mSourceRes = resources;
                int n = Drawable.resolveDensity(resources, this.mDensity);
                int n2 = this.mDensity;
                this.mDensity = n;
                if (n2 != n) {
                    this.mCheckedConstantSize = false;
                    this.mCheckedPadding = false;
                }
            }
        }
    }

}

