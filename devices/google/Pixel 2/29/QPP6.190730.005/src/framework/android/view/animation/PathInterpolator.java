/*
 * Decompiled with CFR 0.145.
 */
package android.view.animation;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.PathParser;
import android.view.InflateException;
import android.view.animation.BaseInterpolator;
import com.android.internal.R;
import com.android.internal.view.animation.HasNativeInterpolator;
import com.android.internal.view.animation.NativeInterpolatorFactory;
import com.android.internal.view.animation.NativeInterpolatorFactoryHelper;

@HasNativeInterpolator
public class PathInterpolator
extends BaseInterpolator
implements NativeInterpolatorFactory {
    private static final float PRECISION = 0.002f;
    private float[] mX;
    private float[] mY;

    public PathInterpolator(float f, float f2) {
        this.initQuad(f, f2);
    }

    public PathInterpolator(float f, float f2, float f3, float f4) {
        this.initCubic(f, f2, f3, f4);
    }

    public PathInterpolator(Context context, AttributeSet attributeSet) {
        this(context.getResources(), context.getTheme(), attributeSet);
    }

    public PathInterpolator(Resources object, Resources.Theme theme, AttributeSet attributeSet) {
        object = theme != null ? theme.obtainStyledAttributes(attributeSet, R.styleable.PathInterpolator, 0, 0) : ((Resources)object).obtainAttributes(attributeSet, R.styleable.PathInterpolator);
        this.parseInterpolatorFromTypeArray((TypedArray)object);
        this.setChangingConfiguration(((TypedArray)object).getChangingConfigurations());
        ((TypedArray)object).recycle();
    }

    public PathInterpolator(Path path) {
        this.initPath(path);
    }

    private void initCubic(float f, float f2, float f3, float f4) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.cubicTo(f, f2, f3, f4, 1.0f, 1.0f);
        this.initPath(path);
    }

    private void initPath(Path arrf) {
        arrf = arrf.approximate(0.002f);
        int n = arrf.length / 3;
        if (arrf[1] == 0.0f && arrf[2] == 0.0f && arrf[arrf.length - 2] == 1.0f && arrf[arrf.length - 1] == 1.0f) {
            this.mX = new float[n];
            this.mY = new float[n];
            float f = 0.0f;
            float f2 = 0.0f;
            int n2 = 0;
            int n3 = 0;
            while (n3 < n) {
                int n4 = n2 + 1;
                float f3 = arrf[n2];
                n2 = n4 + 1;
                float f4 = arrf[n4];
                float f5 = arrf[n2];
                if (f3 == f2 && f4 != f) {
                    throw new IllegalArgumentException("The Path cannot have discontinuity in the X axis.");
                }
                if (!(f4 < f)) {
                    this.mX[n3] = f4;
                    this.mY[n3] = f5;
                    f = f4;
                    f2 = f3;
                    ++n3;
                    ++n2;
                    continue;
                }
                throw new IllegalArgumentException("The Path cannot loop back on itself.");
            }
            return;
        }
        throw new IllegalArgumentException("The Path must start at (0,0) and end at (1,1)");
    }

    private void initQuad(float f, float f2) {
        Path path = new Path();
        path.moveTo(0.0f, 0.0f);
        path.quadTo(f, f2, 1.0f, 1.0f);
        this.initPath(path);
    }

    private void parseInterpolatorFromTypeArray(TypedArray object) {
        block8 : {
            block9 : {
                block10 : {
                    block7 : {
                        block5 : {
                            Object object2;
                            block6 : {
                                if (!((TypedArray)object).hasValue(4)) break block5;
                                object2 = PathParser.createPathFromPathData((String)(object = ((TypedArray)object).getString(4)));
                                if (object2 == null) break block6;
                                this.initPath((Path)object2);
                                break block7;
                            }
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("The path is null, which is created from ");
                            ((StringBuilder)object2).append((String)object);
                            throw new InflateException(((StringBuilder)object2).toString());
                        }
                        if (!((TypedArray)object).hasValue(0)) break block8;
                        if (!((TypedArray)object).hasValue(1)) break block9;
                        float f = ((TypedArray)object).getFloat(0, 0.0f);
                        float f2 = ((TypedArray)object).getFloat(1, 0.0f);
                        boolean bl = ((TypedArray)object).hasValue(2);
                        if (bl != ((TypedArray)object).hasValue(3)) break block10;
                        if (!bl) {
                            this.initQuad(f, f2);
                        } else {
                            this.initCubic(f, f2, ((TypedArray)object).getFloat(2, 0.0f), ((TypedArray)object).getFloat(3, 0.0f));
                        }
                    }
                    return;
                }
                throw new InflateException("pathInterpolator requires both controlX2 and controlY2 for cubic Beziers.");
            }
            throw new InflateException("pathInterpolator requires the controlY1 attribute");
        }
        throw new InflateException("pathInterpolator requires the controlX1 attribute");
    }

    @Override
    public long createNativeInterpolator() {
        return NativeInterpolatorFactoryHelper.createPathInterpolator(this.mX, this.mY);
    }

    @Override
    public float getInterpolation(float f) {
        if (f <= 0.0f) {
            return 0.0f;
        }
        if (f >= 1.0f) {
            return 1.0f;
        }
        int n = 0;
        int n2 = this.mX.length - 1;
        while (n2 - n > 1) {
            int n3 = (n + n2) / 2;
            if (f < this.mX[n3]) {
                n2 = n3;
                continue;
            }
            n = n3;
        }
        float[] arrf = this.mX;
        float f2 = arrf[n2] - arrf[n];
        if (f2 == 0.0f) {
            return this.mY[n];
        }
        f = (f - arrf[n]) / f2;
        arrf = this.mY;
        f2 = arrf[n];
        return (arrf[n2] - f2) * f + f2;
    }
}

