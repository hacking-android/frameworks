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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.util.AttributeSet;
import android.util.MathUtils;
import android.util.TypedValue;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class RotateDrawable
extends DrawableWrapper {
    private static final int MAX_LEVEL = 10000;
    @UnsupportedAppUsage
    private RotateState mState;

    public RotateDrawable() {
        this(new RotateState(null, null), null);
    }

    private RotateDrawable(RotateState rotateState, Resources resources) {
        super(rotateState, resources);
        this.mState = rotateState;
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        TypedValue typedValue;
        float f;
        RotateState rotateState = this.mState;
        if (rotateState == null) {
            return;
        }
        rotateState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        rotateState.mThemeAttrs = typedArray.extractThemeAttrs();
        boolean bl = typedArray.hasValue(4);
        boolean bl2 = true;
        if (bl) {
            typedValue = typedArray.peekValue(4);
            bl = typedValue.type == 6;
            rotateState.mPivotXRel = bl;
            f = rotateState.mPivotXRel ? typedValue.getFraction(1.0f, 1.0f) : typedValue.getFloat();
            rotateState.mPivotX = f;
        }
        if (typedArray.hasValue(5)) {
            typedValue = typedArray.peekValue(5);
            bl = typedValue.type == 6 ? bl2 : false;
            rotateState.mPivotYRel = bl;
            f = rotateState.mPivotYRel ? typedValue.getFraction(1.0f, 1.0f) : typedValue.getFloat();
            rotateState.mPivotY = f;
        }
        rotateState.mFromDegrees = typedArray.getFloat(2, rotateState.mFromDegrees);
        rotateState.mToDegrees = typedArray.getFloat(3, rotateState.mToDegrees);
        rotateState.mCurrentDegrees = rotateState.mFromDegrees;
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (this.getDrawable() == null && (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(typedArray.getPositionDescription());
            stringBuilder.append(": <rotate> tag requires a 'drawable' attribute or child tag defining a drawable");
            throw new XmlPullParserException(stringBuilder.toString());
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void applyTheme(Resources.Theme var1_1) {
        super.applyTheme((Resources.Theme)var1_1);
        var2_2 = this.mState;
        if (var2_2 == null) {
            return;
        }
        if (RotateState.access$000(var2_2) == null) return;
        var1_1 = var1_1.resolveAttributes(RotateState.access$000(var2_2), R.styleable.RotateDrawable);
        this.updateStateFromTypedArray((TypedArray)var1_1);
        this.verifyRequiredAttributes((TypedArray)var1_1);
        var1_1.recycle();
        return;
        {
            catch (XmlPullParserException var2_4) {}
            {
                RotateDrawable.rethrowAsRuntimeException((Exception)var2_4);
            }
        }
        ** finally { 
lbl15: // 1 sources:
        var1_1.recycle();
        throw var2_3;
    }

    @Override
    public void draw(Canvas canvas) {
        Drawable drawable2 = this.getDrawable();
        Rect rect = drawable2.getBounds();
        int n = rect.right;
        int n2 = rect.left;
        int n3 = rect.bottom;
        int n4 = rect.top;
        RotateState rotateState = this.mState;
        float f = rotateState.mPivotXRel ? (float)(n - n2) * rotateState.mPivotX : rotateState.mPivotX;
        float f2 = rotateState.mPivotYRel ? (float)(n3 - n4) * rotateState.mPivotY : rotateState.mPivotY;
        n3 = canvas.save();
        canvas.rotate(rotateState.mCurrentDegrees, (float)rect.left + f, (float)rect.top + f2);
        drawable2.draw(canvas);
        canvas.restoreToCount(n3);
    }

    public float getFromDegrees() {
        return this.mState.mFromDegrees;
    }

    public float getPivotX() {
        return this.mState.mPivotX;
    }

    public float getPivotY() {
        return this.mState.mPivotY;
    }

    public float getToDegrees() {
        return this.mState.mToDegrees;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = RotateDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.RotateDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateStateFromTypedArray(typedArray);
        this.verifyRequiredAttributes(typedArray);
        typedArray.recycle();
    }

    public boolean isPivotXRelative() {
        return this.mState.mPivotXRel;
    }

    public boolean isPivotYRelative() {
        return this.mState.mPivotYRel;
    }

    @Override
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new RotateState(this.mState, null);
        return this.mState;
    }

    @Override
    protected boolean onLevelChange(int n) {
        super.onLevelChange(n);
        float f = (float)n / 10000.0f;
        this.mState.mCurrentDegrees = f = MathUtils.lerp(this.mState.mFromDegrees, this.mState.mToDegrees, f);
        this.invalidateSelf();
        return true;
    }

    public void setFromDegrees(float f) {
        if (this.mState.mFromDegrees != f) {
            this.mState.mFromDegrees = f;
            this.invalidateSelf();
        }
    }

    public void setPivotX(float f) {
        if (this.mState.mPivotX != f) {
            this.mState.mPivotX = f;
            this.invalidateSelf();
        }
    }

    public void setPivotXRelative(boolean bl) {
        if (this.mState.mPivotXRel != bl) {
            this.mState.mPivotXRel = bl;
            this.invalidateSelf();
        }
    }

    public void setPivotY(float f) {
        if (this.mState.mPivotY != f) {
            this.mState.mPivotY = f;
            this.invalidateSelf();
        }
    }

    public void setPivotYRelative(boolean bl) {
        if (this.mState.mPivotYRel != bl) {
            this.mState.mPivotYRel = bl;
            this.invalidateSelf();
        }
    }

    public void setToDegrees(float f) {
        if (this.mState.mToDegrees != f) {
            this.mState.mToDegrees = f;
            this.invalidateSelf();
        }
    }

    static final class RotateState
    extends DrawableWrapper.DrawableWrapperState {
        float mCurrentDegrees = 0.0f;
        float mFromDegrees = 0.0f;
        float mPivotX = 0.5f;
        boolean mPivotXRel = true;
        float mPivotY = 0.5f;
        boolean mPivotYRel = true;
        private int[] mThemeAttrs;
        float mToDegrees = 360.0f;

        RotateState(RotateState rotateState, Resources resources) {
            super(rotateState, resources);
            if (rotateState != null) {
                this.mPivotXRel = rotateState.mPivotXRel;
                this.mPivotX = rotateState.mPivotX;
                this.mPivotYRel = rotateState.mPivotYRel;
                this.mPivotY = rotateState.mPivotY;
                this.mFromDegrees = rotateState.mFromDegrees;
                this.mToDegrees = rotateState.mToDegrees;
                this.mCurrentDegrees = rotateState.mCurrentDegrees;
            }
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new RotateDrawable(this, resources);
        }
    }

}

