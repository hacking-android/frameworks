/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 */
package android.graphics.drawable;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.util.AttributeSet;
import com.android.internal.R;
import org.xmlpull.v1.XmlPullParser;

public class PaintDrawable
extends ShapeDrawable {
    public PaintDrawable() {
    }

    public PaintDrawable(int n) {
        this.getPaint().setColor(n);
    }

    @Override
    protected boolean inflateTag(String object, Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet) {
        if (((String)object).equals("corners")) {
            object = resources.obtainAttributes(attributeSet, R.styleable.DrawableCorners);
            int n = ((TypedArray)object).getDimensionPixelSize(0, 0);
            this.setCornerRadius(n);
            int n2 = ((TypedArray)object).getDimensionPixelSize(1, n);
            int n3 = ((TypedArray)object).getDimensionPixelSize(2, n);
            int n4 = ((TypedArray)object).getDimensionPixelSize(3, n);
            int n5 = ((TypedArray)object).getDimensionPixelSize(4, n);
            if (n2 != n || n3 != n || n4 != n || n5 != n) {
                this.setCornerRadii(new float[]{n2, n2, n3, n3, n4, n4, n5, n5});
            }
            ((TypedArray)object).recycle();
            return true;
        }
        return super.inflateTag((String)object, resources, xmlPullParser, attributeSet);
    }

    public void setCornerRadii(float[] arrf) {
        if (arrf == null) {
            if (this.getShape() != null) {
                this.setShape(null);
            }
        } else {
            this.setShape(new RoundRectShape(arrf, null, null));
        }
        this.invalidateSelf();
    }

    public void setCornerRadius(float f) {
        float[] arrf = null;
        if (f > 0.0f) {
            float[] arrf2 = new float[8];
            int n = 0;
            do {
                arrf = arrf2;
                if (n >= 8) break;
                arrf2[n] = f;
                ++n;
            } while (true);
        }
        this.setCornerRadii(arrf);
    }
}

