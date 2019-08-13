/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.annotation.UnsupportedAppUsage;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatedRotateDrawable
extends DrawableWrapper
implements Animatable {
    private float mCurrentDegrees;
    private float mIncrement;
    private final Runnable mNextFrame = new Runnable(){

        @Override
        public void run() {
            AnimatedRotateDrawable animatedRotateDrawable = AnimatedRotateDrawable.this;
            AnimatedRotateDrawable.access$216(animatedRotateDrawable, animatedRotateDrawable.mIncrement);
            if (AnimatedRotateDrawable.this.mCurrentDegrees > 360.0f - AnimatedRotateDrawable.this.mIncrement) {
                AnimatedRotateDrawable.this.mCurrentDegrees = 0.0f;
            }
            AnimatedRotateDrawable.this.invalidateSelf();
            AnimatedRotateDrawable.this.nextFrame();
        }
    };
    private boolean mRunning;
    private AnimatedRotateState mState;

    public AnimatedRotateDrawable() {
        this(new AnimatedRotateState(null, null), null);
    }

    private AnimatedRotateDrawable(AnimatedRotateState animatedRotateState, Resources resources) {
        super(animatedRotateState, resources);
        this.mState = animatedRotateState;
        this.updateLocalState();
    }

    static /* synthetic */ float access$216(AnimatedRotateDrawable animatedRotateDrawable, float f) {
        animatedRotateDrawable.mCurrentDegrees = f = animatedRotateDrawable.mCurrentDegrees + f;
        return f;
    }

    private void nextFrame() {
        this.unscheduleSelf(this.mNextFrame);
        this.scheduleSelf(this.mNextFrame, SystemClock.uptimeMillis() + (long)this.mState.mFrameDuration);
    }

    private void updateLocalState() {
        this.mIncrement = 360.0f / (float)this.mState.mFramesCount;
        Drawable drawable2 = this.getDrawable();
        if (drawable2 != null) {
            drawable2.setFilterBitmap(true);
            if (drawable2 instanceof BitmapDrawable) {
                ((BitmapDrawable)drawable2).setAntiAlias(true);
            }
        }
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        TypedValue typedValue;
        float f;
        AnimatedRotateState animatedRotateState = this.mState;
        if (animatedRotateState == null) {
            return;
        }
        animatedRotateState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        animatedRotateState.mThemeAttrs = typedArray.extractThemeAttrs();
        boolean bl = typedArray.hasValue(2);
        boolean bl2 = true;
        if (bl) {
            typedValue = typedArray.peekValue(2);
            bl = typedValue.type == 6;
            animatedRotateState.mPivotXRel = bl;
            f = animatedRotateState.mPivotXRel ? typedValue.getFraction(1.0f, 1.0f) : typedValue.getFloat();
            animatedRotateState.mPivotX = f;
        }
        if (typedArray.hasValue(3)) {
            typedValue = typedArray.peekValue(3);
            bl = typedValue.type == 6 ? bl2 : false;
            animatedRotateState.mPivotYRel = bl;
            f = animatedRotateState.mPivotYRel ? typedValue.getFraction(1.0f, 1.0f) : typedValue.getFloat();
            animatedRotateState.mPivotY = f;
        }
        this.setFramesCount(typedArray.getInt(5, animatedRotateState.mFramesCount));
        this.setFramesDuration(typedArray.getInt(4, animatedRotateState.mFrameDuration));
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (this.getDrawable() == null && (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(typedArray.getPositionDescription());
            stringBuilder.append(": <animated-rotate> tag requires a 'drawable' attribute or child tag defining a drawable");
            throw new XmlPullParserException(stringBuilder.toString());
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void applyTheme(Resources.Theme var1_1) {
        block7 : {
            super.applyTheme((Resources.Theme)var1_1);
            var2_2 = this.mState;
            if (var2_2 == null) {
                return;
            }
            if (AnimatedRotateState.access$000(var2_2) != null) {
                var1_1 = var1_1.resolveAttributes(AnimatedRotateState.access$000(var2_2), R.styleable.AnimatedRotateDrawable);
                this.updateStateFromTypedArray((TypedArray)var1_1);
                this.verifyRequiredAttributes((TypedArray)var1_1);
lbl10: // 2 sources:
                do {
                    var1_1.recycle();
                    break block7;
                    break;
                } while (true);
                {
                    catch (Throwable var2_3) {
                    }
                    catch (XmlPullParserException var2_4) {}
                    {
                        AnimatedRotateDrawable.rethrowAsRuntimeException((Exception)var2_4);
                        ** continue;
                    }
                }
                var1_1.recycle();
                throw var2_3;
            }
        }
        this.updateLocalState();
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable2 = this.getDrawable();
        Rect rect = drawable2.getBounds();
        int n = rect.right;
        int n2 = rect.left;
        int n3 = rect.bottom;
        int n4 = rect.top;
        AnimatedRotateState animatedRotateState = this.mState;
        float f = animatedRotateState.mPivotXRel ? (float)(n - n2) * animatedRotateState.mPivotX : animatedRotateState.mPivotX;
        float f2 = animatedRotateState.mPivotYRel ? (float)(n3 - n4) * animatedRotateState.mPivotY : animatedRotateState.mPivotY;
        n4 = canvas.save();
        canvas.rotate(this.mCurrentDegrees, (float)rect.left + f, (float)rect.top + f2);
        drawable2.draw(canvas);
        canvas.restoreToCount(n4);
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = AnimatedRotateDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.AnimatedRotateDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateStateFromTypedArray(typedArray);
        this.verifyRequiredAttributes(typedArray);
        typedArray.recycle();
        this.updateLocalState();
    }

    @Override
    public boolean isRunning() {
        return this.mRunning;
    }

    @Override
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new AnimatedRotateState(this.mState, null);
        return this.mState;
    }

    @UnsupportedAppUsage
    public void setFramesCount(int n) {
        AnimatedRotateState animatedRotateState = this.mState;
        animatedRotateState.mFramesCount = n;
        this.mIncrement = 360.0f / (float)animatedRotateState.mFramesCount;
    }

    @UnsupportedAppUsage
    public void setFramesDuration(int n) {
        this.mState.mFrameDuration = n;
    }

    @Override
    public boolean setVisible(boolean bl, boolean bl2) {
        boolean bl3 = super.setVisible(bl, bl2);
        if (bl) {
            if (bl3 || bl2) {
                this.mCurrentDegrees = 0.0f;
                this.nextFrame();
            }
        } else {
            this.unscheduleSelf(this.mNextFrame);
        }
        return bl3;
    }

    @Override
    public void start() {
        if (!this.mRunning) {
            this.mRunning = true;
            this.nextFrame();
        }
    }

    @Override
    public void stop() {
        this.mRunning = false;
        this.unscheduleSelf(this.mNextFrame);
    }

    static final class AnimatedRotateState
    extends DrawableWrapper.DrawableWrapperState {
        int mFrameDuration = 150;
        int mFramesCount = 12;
        float mPivotX = 0.0f;
        boolean mPivotXRel = false;
        float mPivotY = 0.0f;
        boolean mPivotYRel = false;
        private int[] mThemeAttrs;

        public AnimatedRotateState(AnimatedRotateState animatedRotateState, Resources resources) {
            super(animatedRotateState, resources);
            if (animatedRotateState != null) {
                this.mPivotXRel = animatedRotateState.mPivotXRel;
                this.mPivotX = animatedRotateState.mPivotX;
                this.mPivotYRel = animatedRotateState.mPivotYRel;
                this.mPivotY = animatedRotateState.mPivotY;
                this.mFramesCount = animatedRotateState.mFramesCount;
                this.mFrameDuration = animatedRotateState.mFrameDuration;
            }
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new AnimatedRotateDrawable(this, resources);
        }
    }

}

