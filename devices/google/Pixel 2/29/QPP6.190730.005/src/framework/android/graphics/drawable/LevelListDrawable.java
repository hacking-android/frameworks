/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.util.AttributeSet;
import com.android.internal.R;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class LevelListDrawable
extends DrawableContainer {
    private LevelListState mLevelListState;
    private boolean mMutated;

    public LevelListDrawable() {
        this(null, null);
    }

    private LevelListDrawable(LevelListState levelListState, Resources resources) {
        this.setConstantState(new LevelListState(levelListState, this, resources));
        this.onLevelChange(this.getLevel());
    }

    private void inflateChildElements(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = xmlPullParser.getDepth() + 1;
        while ((n2 = xmlPullParser.next()) != 1 && ((n = xmlPullParser.getDepth()) >= n3 || n2 != 3)) {
            block4 : {
                block7 : {
                    Object object2;
                    block6 : {
                        int n4;
                        block5 : {
                            if (n2 != 2 || n > n3 || !xmlPullParser.getName().equals("item")) continue;
                            object2 = LevelListDrawable.obtainAttributes((Resources)object, theme, attributeSet, R.styleable.LevelListDrawableItem);
                            n2 = ((TypedArray)object2).getInt(1, 0);
                            n = ((TypedArray)object2).getInt(2, 0);
                            n4 = ((TypedArray)object2).getResourceId(0, 0);
                            ((TypedArray)object2).recycle();
                            if (n < 0) break block4;
                            if (n4 == 0) break block5;
                            object2 = ((Resources)object).getDrawable(n4, theme);
                            break block6;
                        }
                        while ((n4 = xmlPullParser.next()) == 4) {
                        }
                        if (n4 != 2) break block7;
                        object2 = Drawable.createFromXmlInner((Resources)object, xmlPullParser, attributeSet, theme);
                    }
                    this.mLevelListState.addLevel(n2, n, (Drawable)object2);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
                ((StringBuilder)object).append(": <item> tag requires a 'drawable' attribute or child tag defining a drawable");
                throw new XmlPullParserException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
            ((StringBuilder)object).append(": <item> tag requires a 'maxLevel' attribute");
            throw new XmlPullParserException(((StringBuilder)object).toString());
        }
        this.onLevelChange(this.getLevel());
    }

    public void addLevel(int n, int n2, Drawable drawable2) {
        if (drawable2 != null) {
            this.mLevelListState.addLevel(n, n2, drawable2);
            this.onLevelChange(this.getLevel());
        }
    }

    @Override
    public void clearMutated() {
        super.clearMutated();
        this.mMutated = false;
    }

    @Override
    LevelListState cloneConstantState() {
        return new LevelListState(this.mLevelListState, this, null);
    }

    @Override
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        this.updateDensity(resources);
        this.inflateChildElements(resources, xmlPullParser, attributeSet, theme);
    }

    @Override
    public Drawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            this.mLevelListState.mutate();
            this.mMutated = true;
        }
        return this;
    }

    @Override
    protected boolean onLevelChange(int n) {
        if (this.selectDrawable(this.mLevelListState.indexOfLevel(n))) {
            return true;
        }
        return super.onLevelChange(n);
    }

    @Override
    protected void setConstantState(DrawableContainer.DrawableContainerState drawableContainerState) {
        super.setConstantState(drawableContainerState);
        if (drawableContainerState instanceof LevelListState) {
            this.mLevelListState = (LevelListState)drawableContainerState;
        }
    }

    private static final class LevelListState
    extends DrawableContainer.DrawableContainerState {
        private int[] mHighs;
        private int[] mLows;

        LevelListState(LevelListState levelListState, LevelListDrawable levelListDrawable, Resources resources) {
            super(levelListState, levelListDrawable, resources);
            if (levelListState != null) {
                this.mLows = levelListState.mLows;
                this.mHighs = levelListState.mHighs;
            } else {
                this.mLows = new int[this.getCapacity()];
                this.mHighs = new int[this.getCapacity()];
            }
        }

        private void mutate() {
            this.mLows = (int[])this.mLows.clone();
            this.mHighs = (int[])this.mHighs.clone();
        }

        public void addLevel(int n, int n2, Drawable drawable2) {
            int n3 = this.addChild(drawable2);
            this.mLows[n3] = n;
            this.mHighs[n3] = n2;
        }

        @Override
        public void growArray(int n, int n2) {
            super.growArray(n, n2);
            int[] arrn = new int[n2];
            System.arraycopy(this.mLows, 0, arrn, 0, n);
            this.mLows = arrn;
            arrn = new int[n2];
            System.arraycopy(this.mHighs, 0, arrn, 0, n);
            this.mHighs = arrn;
        }

        public int indexOfLevel(int n) {
            int[] arrn = this.mLows;
            int[] arrn2 = this.mHighs;
            int n2 = this.getChildCount();
            for (int i = 0; i < n2; ++i) {
                if (n < arrn[i] || n > arrn2[i]) continue;
                return i;
            }
            return -1;
        }

        @Override
        public Drawable newDrawable() {
            return new LevelListDrawable(this, null);
        }

        @Override
        public Drawable newDrawable(Resources resources) {
            return new LevelListDrawable(this, resources);
        }
    }

}

