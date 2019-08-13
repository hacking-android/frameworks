/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class PointFEvaluator
implements TypeEvaluator<PointF> {
    private PointF mPoint;

    public PointFEvaluator() {
    }

    public PointFEvaluator(PointF pointF) {
        this.mPoint = pointF;
    }

    @Override
    public PointF evaluate(float f, PointF pointF, PointF pointF2) {
        float f2 = pointF.x + (pointF2.x - pointF.x) * f;
        f = pointF.y + (pointF2.y - pointF.y) * f;
        pointF = this.mPoint;
        if (pointF != null) {
            pointF.set(f2, f);
            return this.mPoint;
        }
        return new PointF(f2, f);
    }
}

