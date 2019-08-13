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
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.AttributeSet;
import android.util.StateSet;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateListDrawable
extends DrawableContainer {
    private static final boolean DEBUG = false;
    private static final String TAG = "StateListDrawable";
    private boolean mMutated;
    @UnsupportedAppUsage
    private StateListState mStateListState;

    public StateListDrawable() {
        this(null, null);
    }

    StateListDrawable(StateListState stateListState) {
        if (stateListState != null) {
            this.setConstantState(stateListState);
        }
    }

    private StateListDrawable(StateListState stateListState, Resources resources) {
        this.setConstantState(new StateListState(stateListState, this, resources));
        this.onStateChange(this.getState());
    }

    private void inflateChildElements(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        StateListState stateListState = this.mStateListState;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3 || !xmlPullParser.getName().equals("item")) continue;
            Object object2 = StateListDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.StateListDrawableItem);
            Drawable drawable2 = ((TypedArray)object2).getDrawable(0);
            ((TypedArray)object2).recycle();
            int[] arrn = this.extractStateSet(attributeSet);
            object2 = drawable2;
            if (drawable2 == null) {
                while ((n = xmlPullParser.next()) == 4) {
                }
                if (n == 2) {
                    object2 = Drawable.createFromXmlInner((Resources)object, xmlPullParser, attributeSet, theme);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                    ((StringBuilder)object).append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                    throw new XmlPullParserException(((StringBuilder)object).toString());
                }
            }
            stateListState.addStateSet(arrn, (Drawable)object2);
        }
    }

    @UnsupportedAppUsage
    private void updateStateFromTypedArray(TypedArray typedArray) {
        StateListState stateListState = this.mStateListState;
        stateListState.mChangingConfigurations |= typedArray.getChangingConfigurations();
        stateListState.mThemeAttrs = typedArray.extractThemeAttrs();
        stateListState.mVariablePadding = typedArray.getBoolean(2, stateListState.mVariablePadding);
        stateListState.mConstantSize = typedArray.getBoolean(3, stateListState.mConstantSize);
        stateListState.mEnterFadeDuration = typedArray.getInt(4, stateListState.mEnterFadeDuration);
        stateListState.mExitFadeDuration = typedArray.getInt(5, stateListState.mExitFadeDuration);
        stateListState.mDither = typedArray.getBoolean(0, stateListState.mDither);
        stateListState.mAutoMirrored = typedArray.getBoolean(6, stateListState.mAutoMirrored);
    }

    public void addState(int[] arrn, Drawable drawable2) {
        if (drawable2 != null) {
            this.mStateListState.addStateSet(arrn, drawable2);
            this.onStateChange(this.getState());
        }
    }

    @Override
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        this.onStateChange(this.getState());
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    StateListState cloneConstantState() {
        return new StateListState(this.mStateListState, this, null);
    }

    @UnsupportedAppUsage
    int[] extractStateSet(AttributeSet attributeSet) {
        int n = 0;
        int n2 = attributeSet.getAttributeCount();
        int[] arrn = new int[n2];
        for (int i = 0; i < n2; ++i) {
            int n3 = attributeSet.getAttributeNameResource(i);
            if (n3 == 0 || n3 == 16842960 || n3 == 16843161) continue;
            if (!attributeSet.getAttributeBooleanValue(i, false)) {
                n3 = -n3;
            }
            arrn[n] = n3;
            ++n;
        }
        return StateSet.trimStateSet(arrn, n);
    }

    public int findStateDrawableIndex(int[] arrn) {
        return this.mStateListState.indexOfStateSet(arrn);
    }

    public int getStateCount() {
        return this.mStateListState.getChildCount();
    }

    public Drawable getStateDrawable(int n) {
        return this.mStateListState.getChild(n);
    }

    StateListState getStateListState() {
        return this.mStateListState;
    }

    public int[] getStateSet(int n) {
        return this.mStateListState.mStateSets[n];
    }

    @Override
    public boolean hasFocusStateSpecified() {
        return this.mStateListState.hasFocusStateSpecified();
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArray = StateListDrawable.obtainAttributes(resources, theme, attributeSet, R.styleable.StateListDrawable);
        super.inflateWithAttributes(resources, xmlPullParser, typedArray, 1);
        this.updateStateFromTypedArray(typedArray);
        this.updateDensity(resources);
        typedArray.recycle();
        this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
        this.onStateChange(this.getState());
    }

    @Override
    public boolean isStateful() {
        return true;
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mStateListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected boolean onStateChange(int[] arrn) {
        int n;
        boolean bl = super.onStateChange(arrn);
        int n2 = n = this.mStateListState.indexOfStateSet(arrn);
        if (n < 0) {
            n2 = this.mStateListState.indexOfStateSet(StateSet.WILD_CARD);
        }
        bl = this.selectDrawable(n2) || bl;
        return bl;
    }

    @Override
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof StateListState) {
            this.mStateListState = (StateListState)drawableContainerState;
        }
    }

    static class StateListState
    extends DrawableContainer.DrawableContainerState {
        int[][] mStateSets;
        int[] mThemeAttrs;

        StateListState(StateListState stateListState, StateListDrawable stateListDrawable, Resources resources) {
            super(stateListState, stateListDrawable, resources);
            if (stateListState != null) {
                this.mThemeAttrs = stateListState.mThemeAttrs;
                this.mStateSets = stateListState.mStateSets;
            } else {
                this.mThemeAttrs = null;
                this.mStateSets = new int[this.getCapacity()][];
            }
        }

        @UnsupportedAppUsage
        int addStateSet(int[] arrn, Drawable drawable2) {
            int n = this.addChild(drawable2);
            this.mStateSets[n] = arrn;
            return n;
        }

        @Override
        public boolean canApplyTheme() {
            boolean bl = this.mThemeAttrs != null || super.canApplyTheme();
            return bl;
        }

        @Override
        public void growArray(int n, int n2) {
            super.growArray(n, n2);
            int[][] arrarrn = new int[n2][];
            System.arraycopy(this.mStateSets, 0, arrarrn, 0, n);
            this.mStateSets = arrarrn;
        }

        boolean hasFocusStateSpecified() {
            return StateSet.containsAttribute(this.mStateSets, 16842908);
        }

        int indexOfStateSet(int[] arrn) {
            int[][] arrn2 = this.mStateSets;
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                if (!StateSet.stateSetMatches(arrn2[i], arrn)) continue;
                return i;
            }
            return -1;
        }

        void mutate() {
            Object object = this.mThemeAttrs;
            object = object != null ? (int[])object.clone() : null;
            this.mThemeAttrs = object;
            object = this.mStateSets;
            int[][] arrarrn = new int[((int[])object).length][];
            for (int i = ((int[])object).length - 1; i >= 0; --i) {
                object = this.mStateSets;
                object = object[i] != null ? (int[])object[i].clone() : null;
                arrarrn[i] = object;
            }
            this.mStateSets = arrarrn;
        }

        @Override
        public Drawable newDrawable() {
            return new StateListDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new StateListDrawable(this, resources);
        }
    }

}

