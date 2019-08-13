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
import android.view.Gravity;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ScaleDrawable
extends DrawableWrapper {
    private static final int MAX_LEVEL = 10000;
    @UnsupportedAppUsage
    private ScaleState mState;
    private final Rect mTmpRect = new Rect();

    ScaleDrawable() {
        this(new ScaleState(null, null), null);
    }

    public ScaleDrawable(Drawable drawable2, int n, float f, float f2) {
        this(new ScaleState(null, null), null);
        ScaleState scaleState = this.mState;
        scaleState.mGravity = n;
        scaleState.mScaleWidth = f;
        scaleState.mScaleHeight = f2;
        this.setDrawable(drawable2);
    }

    private ScaleDrawable(ScaleState scaleState, Resources resources) {
        super(scaleState, resources);
        this.mState = scaleState;
        this.updateLocalState();
    }

    private static float getPercent(TypedArray object, int n, float f) {
        int n2 = ((TypedArray)object).getType(n);
        if (n2 != 6 && n2 != 0) {
            if ((object = ((TypedArray)object).getString(n)) != null && ((String)object).endsWith("%")) {
                return Float.parseFloat(((String)object).substring(0, ((String)object).length() - 1)) / 100.0f;
            }
            return f;
        }
        return ((TypedArray)object).getFraction(n, 1, 1, f);
    }

    private void updateLocalState() {
        this.setLevel(this.mState.mInitialLevel);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ScaleState scaleState = this.mState;
        if (scaleState == null) {
            return;
        }
        scaleState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        scaleState.mThemeAttrs = typedArray.extractThemeAttrs();
        scaleState.mScaleWidth = ScaleDrawable.getPercent(typedArray, 1, scaleState.mScaleWidth);
        scaleState.mScaleHeight = ScaleDrawable.getPercent(typedArray, 2, scaleState.mScaleHeight);
        scaleState.mGravity = typedArray.getInt(3, scaleState.mGravity);
        scaleState.mUseIntrinsicSizeAsMin = typedArray.getBoolean(4, scaleState.mUseIntrinsicSizeAsMin);
        scaleState.mInitialLevel = typedArray.getInt(5, scaleState.mInitialLevel);
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (this.getDrawable() == null && (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[0] == 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(typedArray.getPositionDescription());
            stringBuilder.append(": <scale> tag requires a 'drawable' attribute or child tag defining a drawable");
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
            if (ScaleState.access$000(var2_2) != null) {
                var1_1 = var1_1.resolveAttributes(ScaleState.access$000(var2_2), R.styleable.ScaleDrawable);
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
                        ScaleDrawable.rethrowAsRuntimeException((Exception)var2_4);
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
        if (drawable2 != null && drawable2.getLevel() != 0) {
            drawable2.draw(canvas);
        }
    }

    @Override
    public int getOpacity() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2.getLevel() == 0) {
            return -2;
        }
        int n = drawable2.getOpacity();
        if (n == -1 && drawable2.getLevel() < 10000) {
            return -3;
        }
        return n;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = ScaleDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.ScaleDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateStateFromTypedArray(typedArray);
        this.verifyRequiredAttributes(typedArray);
        typedArray.recycle();
        this.updateLocalState();
    }

    @Override
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new ScaleState(this.mState, null);
        return this.mState;
    }

    @Override
    protected void onBoundsChange(Rect rect) {
        int n;
        Drawable drawable2 = this.getDrawable();
        Rect rect2 = this.mTmpRect;
        boolean bl = this.mState.mUseIntrinsicSizeAsMin;
        int n2 = this.getLevel();
        int n3 = rect.width();
        float f = this.mState.mScaleWidth;
        int n4 = 0;
        if (f > 0.0f) {
            n = bl ? drawable2.getIntrinsicWidth() : 0;
            n = n3 - (int)((float)((n3 - n) * (10000 - n2)) * this.mState.mScaleWidth / 10000.0f);
        } else {
            n = n3;
        }
        n3 = rect.height();
        if (this.mState.mScaleHeight > 0.0f) {
            if (bl) {
                n4 = drawable2.getIntrinsicHeight();
            }
            n4 = n3 - (int)((float)((n3 - n4) * (10000 - n2)) * this.mState.mScaleHeight / 10000.0f);
        } else {
            n4 = n3;
        }
        n3 = this.getLayoutDirection();
        Gravity.apply(this.mState.mGravity, n, n4, rect, rect2, n3);
        if (n > 0 && n4 > 0) {
            drawable2.setBounds(rect2.left, rect2.top, rect2.right, rect2.bottom);
        }
    }

    @Override
    protected boolean onLevelChange(int n) {
        super.onLevelChange(n);
        this.onBoundsChange(this.getBounds());
        this.invalidateSelf();
        return true;
    }

    static final class ScaleState
    extends DrawableWrapper.DrawableWrapperState {
        private static final float DO_NOT_SCALE = -1.0f;
        int mGravity = 3;
        int mInitialLevel = 0;
        float mScaleHeight = -1.0f;
        float mScaleWidth = -1.0f;
        private int[] mThemeAttrs;
        boolean mUseIntrinsicSizeAsMin = false;

        ScaleState(ScaleState scaleState, Resources resources) {
            super(scaleState, resources);
            if (scaleState != null) {
                this.mScaleWidth = scaleState.mScaleWidth;
                this.mScaleHeight = scaleState.mScaleHeight;
                this.mGravity = scaleState.mGravity;
                this.mUseIntrinsicSizeAsMin = scaleState.mUseIntrinsicSizeAsMin;
                this.mInitialLevel = scaleState.mInitialLevel;
            }
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new ScaleDrawable(this, resources);
        }
    }

}

