/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionUtils;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.Map;

public class ChangeImageTransform
extends Transition {
    private static Property<ImageView, Matrix> ANIMATED_TRANSFORM_PROPERTY;
    private static TypeEvaluator<Matrix> NULL_MATRIX_EVALUATOR;
    private static final String PROPNAME_BOUNDS = "android:changeImageTransform:bounds";
    private static final String PROPNAME_MATRIX = "android:changeImageTransform:matrix";
    private static final String TAG = "ChangeImageTransform";
    private static final String[] sTransitionProperties;

    static {
        sTransitionProperties = new String[]{PROPNAME_MATRIX, PROPNAME_BOUNDS};
        NULL_MATRIX_EVALUATOR = new TypeEvaluator<Matrix>(){

            @Override
            public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
                return null;
            }
        };
        ANIMATED_TRANSFORM_PROPERTY = new Property<ImageView, Matrix>(Matrix.class, "animatedTransform"){

            @Override
            public Matrix get(ImageView imageView) {
                return null;
            }

            @Override
            public void set(ImageView imageView, Matrix matrix) {
                imageView.animateTransform(matrix);
            }
        };
    }

    public ChangeImageTransform() {
    }

    public ChangeImageTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void captureValues(TransitionValues object) {
        Object object2 = ((TransitionValues)object).view;
        if (object2 instanceof ImageView && ((View)object2).getVisibility() == 0) {
            ImageView imageView = (ImageView)object2;
            Drawable drawable2 = imageView.getDrawable();
            if (drawable2 == null) {
                return;
            }
            Map<String, Object> map = ((TransitionValues)object).values;
            object2 = new Rect(((View)object2).getLeft(), ((View)object2).getTop(), ((View)object2).getRight(), ((View)object2).getBottom());
            map.put(PROPNAME_BOUNDS, object2);
            object = imageView.getScaleType();
            int n = drawable2.getIntrinsicWidth();
            int n2 = drawable2.getIntrinsicHeight();
            if (object == ImageView.ScaleType.FIT_XY && n > 0 && n2 > 0) {
                float f = (float)((Rect)object2).width() / (float)n;
                float f2 = (float)((Rect)object2).height() / (float)n2;
                object = new Matrix();
                ((Matrix)object).setScale(f, f2);
            } else {
                object = new Matrix(imageView.getImageMatrix());
            }
            map.put(PROPNAME_MATRIX, object);
            return;
        }
    }

    private ObjectAnimator createMatrixAnimator(ImageView imageView, Matrix matrix, Matrix matrix2) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY, new TransitionUtils.MatrixEvaluator(), matrix, matrix2);
    }

    private ObjectAnimator createNullAnimator(ImageView imageView) {
        return ObjectAnimator.ofObject(imageView, ANIMATED_TRANSFORM_PROPERTY, NULL_MATRIX_EVALUATOR, Matrix.IDENTITY_MATRIX, Matrix.IDENTITY_MATRIX);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup object, TransitionValues object2, TransitionValues object3) {
        if (object2 != null && object3 != null) {
            Rect rect = (Rect)((TransitionValues)object2).values.get(PROPNAME_BOUNDS);
            Object object4 = (Rect)((TransitionValues)object3).values.get(PROPNAME_BOUNDS);
            object2 = (Matrix)((TransitionValues)object2).values.get(PROPNAME_MATRIX);
            object = (Matrix)((TransitionValues)object3).values.get(PROPNAME_MATRIX);
            if (rect != null && object4 != null && object2 != null && object != null) {
                if (rect.equals(object4) && ((Matrix)object2).equals(object)) {
                    return null;
                }
                object4 = (ImageView)((TransitionValues)object3).view;
                object3 = ((ImageView)object4).getDrawable();
                int n = ((Drawable)object3).getIntrinsicWidth();
                int n2 = ((Drawable)object3).getIntrinsicHeight();
                if (n > 0 && n2 > 0) {
                    ANIMATED_TRANSFORM_PROPERTY.set((ImageView)object4, (Matrix)object2);
                    object = this.createMatrixAnimator((ImageView)object4, (Matrix)object2, (Matrix)object);
                } else {
                    object = this.createNullAnimator((ImageView)object4);
                }
                return object;
            }
            return null;
        }
        return null;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

}

