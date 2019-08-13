/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.transition.PathMotion;
import android.util.AttributeSet;
import android.util.PathParser;
import com.android.internal.R;

public class PatternPathMotion
extends PathMotion {
    private Path mOriginalPatternPath;
    private final Path mPatternPath = new Path();
    private final Matrix mTempMatrix = new Matrix();

    public PatternPathMotion() {
        this.mPatternPath.lineTo(1.0f, 0.0f);
        this.mOriginalPatternPath = this.mPatternPath;
    }

    public PatternPathMotion(Context object, AttributeSet object2) {
        object = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.PatternPathMotion);
        try {
            object2 = ((TypedArray)object).getString(0);
            if (object2 != null) {
                this.setPatternPath(PathParser.createPathFromPathData((String)object2));
                return;
            }
            object2 = new RuntimeException("pathData must be supplied for patternPathMotion");
            throw object2;
        }
        finally {
            ((TypedArray)object).recycle();
        }
    }

    public PatternPathMotion(Path path) {
        this.setPatternPath(path);
    }

    @Override
    public Path getPath(float f, float f2, float f3, float f4) {
        double d = f3 - f;
        double d2 = f4 - f2;
        f3 = (float)Math.hypot(d, d2);
        d = Math.atan2(d2, d);
        this.mTempMatrix.setScale(f3, f3);
        this.mTempMatrix.postRotate((float)Math.toDegrees(d));
        this.mTempMatrix.postTranslate(f, f2);
        Path path = new Path();
        this.mPatternPath.transform(this.mTempMatrix, path);
        return path;
    }

    public Path getPatternPath() {
        return this.mOriginalPatternPath;
    }

    public void setPatternPath(Path path) {
        PathMeasure pathMeasure = new PathMeasure(path, false);
        float f = pathMeasure.getLength();
        float[] arrf = new float[2];
        pathMeasure.getPosTan(f, arrf, null);
        float f2 = arrf[0];
        f = arrf[1];
        pathMeasure.getPosTan(0.0f, arrf, null);
        float f3 = arrf[0];
        float f4 = arrf[1];
        if (f3 == f2 && f4 == f) {
            throw new IllegalArgumentException("pattern must not end at the starting point");
        }
        this.mTempMatrix.setTranslate(-f3, -f4);
        f3 = f2 - f3;
        f -= f4;
        f4 = 1.0f / (float)Math.hypot(f3, f);
        this.mTempMatrix.postScale(f4, f4);
        double d = Math.atan2(f, f3);
        this.mTempMatrix.postRotate((float)Math.toDegrees(-d));
        path.transform(this.mTempMatrix, this.mPatternPath);
        this.mOriginalPatternPath = path;
    }
}

