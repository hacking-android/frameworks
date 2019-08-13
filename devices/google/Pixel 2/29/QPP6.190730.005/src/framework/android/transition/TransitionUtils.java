/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import android.widget.ImageView;

public class TransitionUtils {
    private static int MAX_IMAGE_SIZE = 1048576;

    public static View copyViewImage(ViewGroup object, View view, View view2) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(-view2.getScrollX(), -view2.getScrollY());
        view.transformMatrixToGlobal(matrix);
        ((View)object).transformMatrixToLocal(matrix);
        RectF rectF = new RectF(0.0f, 0.0f, view.getWidth(), view.getHeight());
        matrix.mapRect(rectF);
        int n = Math.round(rectF.left);
        int n2 = Math.round(rectF.top);
        int n3 = Math.round(rectF.right);
        int n4 = Math.round(rectF.bottom);
        view2 = new ImageView(view.getContext());
        ((ImageView)view2).setScaleType(ImageView.ScaleType.CENTER_CROP);
        object = TransitionUtils.createViewBitmap(view, matrix, rectF, (ViewGroup)object);
        if (object != null) {
            ((ImageView)view2).setImageBitmap((Bitmap)object);
        }
        view2.measure(View.MeasureSpec.makeMeasureSpec(n3 - n, 1073741824), View.MeasureSpec.makeMeasureSpec(n4 - n2, 1073741824));
        view2.layout(n, n2, n3, n4);
        return view2;
    }

    public static Bitmap createDrawableBitmap(Drawable drawable2, View object) {
        int n = drawable2.getIntrinsicWidth();
        int n2 = drawable2.getIntrinsicHeight();
        if (n > 0 && n2 > 0) {
            float f = Math.min(1.0f, (float)MAX_IMAGE_SIZE / (float)(n * n2));
            if (drawable2 instanceof BitmapDrawable && f == 1.0f) {
                return ((BitmapDrawable)drawable2).getBitmap();
            }
            int n3 = (int)((float)n * f);
            int n4 = (int)((float)n2 * f);
            Picture picture = new Picture();
            object = picture.beginRecording(n, n2);
            Rect rect = drawable2.getBounds();
            n = rect.left;
            n2 = rect.top;
            int n5 = rect.right;
            int n6 = rect.bottom;
            drawable2.setBounds(0, 0, n3, n4);
            drawable2.draw((Canvas)object);
            drawable2.setBounds(n, n2, n5, n6);
            picture.endRecording();
            return Bitmap.createBitmap(picture);
        }
        return null;
    }

    public static Bitmap createViewBitmap(View view, Matrix matrix, RectF object, ViewGroup viewGroup) {
        boolean bl = view.isAttachedToWindow() ^ true;
        ViewGroup viewGroup2 = null;
        int n = 0;
        if (bl) {
            if (viewGroup != null && viewGroup.isAttachedToWindow()) {
                viewGroup2 = (ViewGroup)view.getParent();
                n = viewGroup2.indexOfChild(view);
                viewGroup.getOverlay().add(view);
            } else {
                return null;
            }
        }
        Canvas canvas = null;
        int n2 = Math.round(((RectF)object).width());
        int n3 = Math.round(((RectF)object).height());
        Object object2 = canvas;
        if (n2 > 0) {
            object2 = canvas;
            if (n3 > 0) {
                float f = Math.min(1.0f, (float)MAX_IMAGE_SIZE / (float)(n2 * n3));
                n2 = (int)((float)n2 * f);
                n3 = (int)((float)n3 * f);
                matrix.postTranslate(-((RectF)object).left, -((RectF)object).top);
                matrix.postScale(f, f);
                object = new Picture();
                object2 = ((Picture)object).beginRecording(n2, n3);
                ((Canvas)object2).concat(matrix);
                view.draw((Canvas)object2);
                ((Picture)object).endRecording();
                object2 = Bitmap.createBitmap((Picture)object);
            }
        }
        if (bl) {
            viewGroup.getOverlay().remove(view);
            viewGroup2.addView(view, n);
        }
        return object2;
    }

    static Animator mergeAnimators(Animator animator2, Animator animator3) {
        if (animator2 == null) {
            return animator3;
        }
        if (animator3 == null) {
            return animator2;
        }
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator2, animator3);
        return animatorSet;
    }

    public static Transition mergeTransitions(Transition ... arrtransition) {
        int n;
        int n2 = 0;
        int n3 = -1;
        for (n = 0; n < arrtransition.length; ++n) {
            int n4 = n2;
            if (arrtransition[n] != null) {
                n4 = n2 + 1;
                n3 = n;
            }
            n2 = n4;
        }
        if (n2 == 0) {
            return null;
        }
        if (n2 == 1) {
            return arrtransition[n3];
        }
        TransitionSet transitionSet = new TransitionSet();
        for (n = 0; n < arrtransition.length; ++n) {
            if (arrtransition[n] == null) continue;
            transitionSet.addTransition(arrtransition[n]);
        }
        return transitionSet;
    }

    public static class MatrixEvaluator
    implements TypeEvaluator<Matrix> {
        float[] mTempEndValues = new float[9];
        Matrix mTempMatrix = new Matrix();
        float[] mTempStartValues = new float[9];

        @Override
        public Matrix evaluate(float f, Matrix arrf, Matrix arrf2) {
            arrf.getValues(this.mTempStartValues);
            arrf2.getValues(this.mTempEndValues);
            for (int i = 0; i < 9; ++i) {
                arrf2 = this.mTempEndValues;
                float f2 = arrf2[i];
                arrf = this.mTempStartValues;
                float f3 = arrf[i];
                arrf2[i] = arrf[i] + f * (f2 - f3);
            }
            this.mTempMatrix.setValues(this.mTempEndValues);
            return this.mTempMatrix;
        }
    }

}

