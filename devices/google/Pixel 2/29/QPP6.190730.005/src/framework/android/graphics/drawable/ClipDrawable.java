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

public class ClipDrawable
extends DrawableWrapper {
    public static final int HORIZONTAL = 1;
    private static final int MAX_LEVEL = 10000;
    public static final int VERTICAL = 2;
    @UnsupportedAppUsage
    private ClipState mState;
    private final Rect mTmpRect = new Rect();

    ClipDrawable() {
        this(new ClipState(null, null), null);
    }

    private ClipDrawable(ClipState clipState, Resources resources) {
        super(clipState, resources);
        this.mState = clipState;
    }

    public ClipDrawable(Drawable drawable2, int n, int n2) {
        this(new ClipState(null, null), null);
        ClipState clipState = this.mState;
        clipState.mGravity = n;
        clipState.mOrientation = n2;
        this.setDrawable(drawable2);
    }

    private void updateStateFromTypedArray(TypedArray typedArray) {
        ClipState clipState = this.mState;
        if (clipState == null) {
            return;
        }
        clipState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        clipState.mThemeAttrs = typedArray.extractThemeAttrs();
        clipState.mOrientation = typedArray.getInt(2, clipState.mOrientation);
        clipState.mGravity = typedArray.getInt(0, clipState.mGravity);
    }

    private void verifyRequiredAttributes(TypedArray typedArray) throws XmlPullParserException {
        if (this.getDrawable() == null && (this.mState.mThemeAttrs == null || this.mState.mThemeAttrs[1] == 0)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(typedArray.getPositionDescription());
            stringBuilder.append(": <clip> tag requires a 'drawable' attribute or child tag defining a drawable");
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
        if (ClipState.access$000(var2_2) == null) return;
        var1_1 = var1_1.resolveAttributes(ClipState.access$000(var2_2), R.styleable.ClipDrawable);
        this.updateStateFromTypedArray((TypedArray)var1_1);
        this.verifyRequiredAttributes((TypedArray)var1_1);
        var1_1.recycle();
        return;
        {
            catch (XmlPullParserException var2_4) {}
            {
                ClipDrawable.rethrowAsRuntimeException((Exception)var2_4);
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
        if (drawable2.getLevel() == 0) {
            return;
        }
        Rect rect = this.mTmpRect;
        Rect rect2 = this.getBounds();
        int n = this.getLevel();
        int n2 = rect2.width();
        if ((this.mState.mOrientation & 1) != 0) {
            n2 -= (n2 + 0) * (10000 - n) / 10000;
        }
        int n3 = rect2.height();
        if ((this.mState.mOrientation & 2) != 0) {
            n3 -= (n3 + 0) * (10000 - n) / 10000;
        }
        n = this.getLayoutDirection();
        Gravity.apply(this.mState.mGravity, n2, n3, rect2, rect, n);
        if (n2 > 0 && n3 > 0) {
            canvas.save();
            canvas.clipRect(rect);
            drawable2.draw(canvas);
            canvas.restore();
        }
    }

    @Override
    public int getOpacity() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2.getOpacity() != -2 && drawable2.getLevel() != 0) {
            if (this.getLevel() >= 10000) {
                return drawable2.getOpacity();
            }
            return -3;
        }
        return -2;
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = ClipDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.ClipDrawable);
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateStateFromTypedArray(typedArray);
        this.verifyRequiredAttributes(typedArray);
        typedArray.recycle();
    }

    @Override
    DrawableWrapper.DrawableWrapperState mutateConstantState() {
        this.mState = new ClipState(this.mState, null);
        return this.mState;
    }

    @Override
    protected boolean onLevelChange(int n) {
        super.onLevelChange(n);
        this.invalidateSelf();
        return true;
    }

    static final class ClipState
    extends DrawableWrapper.DrawableWrapperState {
        int mGravity = 3;
        int mOrientation = 1;
        private int[] mThemeAttrs;

        ClipState(ClipState clipState, Resources resources) {
            super(clipState, resources);
            if (clipState != null) {
                this.mOrientation = clipState.mOrientation;
                this.mGravity = clipState.mGravity;
            }
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new ClipDrawable(this, resources);
        }
    }

}

