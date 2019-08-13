/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.transition.Transition;
import android.transition.TransitionValues;
import android.util.Property;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.view.ViewParent;
import java.util.Map;

public class Crossfade
extends Transition {
    public static final int FADE_BEHAVIOR_CROSSFADE = 0;
    public static final int FADE_BEHAVIOR_OUT_IN = 2;
    public static final int FADE_BEHAVIOR_REVEAL = 1;
    private static final String LOG_TAG = "Crossfade";
    private static final String PROPNAME_BITMAP = "android:crossfade:bitmap";
    private static final String PROPNAME_BOUNDS = "android:crossfade:bounds";
    private static final String PROPNAME_DRAWABLE = "android:crossfade:drawable";
    public static final int RESIZE_BEHAVIOR_NONE = 0;
    public static final int RESIZE_BEHAVIOR_SCALE = 1;
    private static RectEvaluator sRectEvaluator = new RectEvaluator();
    private int mFadeBehavior = 1;
    private int mResizeBehavior = 1;

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        Rect rect = new Rect(0, 0, view.getWidth(), view.getHeight());
        if (this.mFadeBehavior != 1) {
            rect.offset(view.getLeft(), view.getTop());
        }
        transitionValues.values.put(PROPNAME_BOUNDS, rect);
        Object object = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        if (view instanceof TextureView) {
            object = ((TextureView)view).getBitmap();
        } else {
            view.draw(new Canvas((Bitmap)object));
        }
        transitionValues.values.put(PROPNAME_BITMAP, object);
        object = new BitmapDrawable((Bitmap)object);
        ((Drawable)object).setBounds(rect);
        transitionValues.values.put(PROPNAME_DRAWABLE, object);
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
            final boolean bl = this.mFadeBehavior != 1;
            Object object4 = ((TransitionValues)object3).view;
            Object object5 = ((TransitionValues)object2).values;
            object3 = ((TransitionValues)object3).values;
            Rect rect = (Rect)object5.get(PROPNAME_BOUNDS);
            Rect rect2 = (Rect)object3.get(PROPNAME_BOUNDS);
            object2 = (Bitmap)object5.get(PROPNAME_BITMAP);
            object = (Bitmap)object3.get(PROPNAME_BITMAP);
            object5 = (BitmapDrawable)object5.get(PROPNAME_DRAWABLE);
            object3 = (BitmapDrawable)object3.get(PROPNAME_DRAWABLE);
            if (object5 != null && object3 != null && !((Bitmap)object2).sameAs((Bitmap)object)) {
                object = bl ? ((ViewGroup)((View)object4).getParent()).getOverlay() : ((View)object4).getOverlay();
                if (this.mFadeBehavior == 1) {
                    ((ViewOverlay)object).add((Drawable)object3);
                }
                ((ViewOverlay)object).add((Drawable)object5);
                object2 = this.mFadeBehavior == 2 ? ObjectAnimator.ofInt(object5, "alpha", 255, 0, 0) : ObjectAnimator.ofInt(object5, "alpha", 0);
                ((ValueAnimator)object2).addUpdateListener(new ValueAnimator.AnimatorUpdateListener((View)object4, (BitmapDrawable)object5){
                    final /* synthetic */ BitmapDrawable val$startDrawable;
                    final /* synthetic */ View val$view;
                    {
                        this.val$view = view;
                        this.val$startDrawable = bitmapDrawable;
                    }

                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        this.val$view.invalidate(this.val$startDrawable.getBounds());
                    }
                });
                object = null;
                int n = this.mFadeBehavior;
                if (n == 2) {
                    object = ObjectAnimator.ofFloat(object4, View.ALPHA, 0.0f, 0.0f, 1.0f);
                } else if (n == 0) {
                    object = ObjectAnimator.ofFloat(object4, View.ALPHA, 0.0f, 1.0f);
                }
                ((Animator)object2).addListener(new AnimatorListenerAdapter((View)object4, (BitmapDrawable)object5, (BitmapDrawable)object3){
                    final /* synthetic */ BitmapDrawable val$endDrawable;
                    final /* synthetic */ BitmapDrawable val$startDrawable;
                    final /* synthetic */ View val$view;
                    {
                        this.val$view = view;
                        this.val$startDrawable = bitmapDrawable;
                        this.val$endDrawable = bitmapDrawable2;
                    }

                    @Override
                    public void onAnimationEnd(Animator object) {
                        object = bl ? ((ViewGroup)this.val$view.getParent()).getOverlay() : this.val$view.getOverlay();
                        ((ViewOverlay)object).remove(this.val$startDrawable);
                        if (Crossfade.this.mFadeBehavior == 1) {
                            ((ViewOverlay)object).remove(this.val$endDrawable);
                        }
                    }
                });
                object4 = new AnimatorSet();
                ((AnimatorSet)object4).playTogether(new Animator[]{object2});
                if (object != null) {
                    ((AnimatorSet)object4).playTogether(new Animator[]{object});
                }
                if (this.mResizeBehavior == 1 && !rect.equals(rect2)) {
                    ((AnimatorSet)object4).playTogether(ObjectAnimator.ofObject(object5, "bounds", (TypeEvaluator)sRectEvaluator, rect, rect2));
                    if (this.mResizeBehavior == 1) {
                        ((AnimatorSet)object4).playTogether(ObjectAnimator.ofObject(object3, "bounds", (TypeEvaluator)sRectEvaluator, rect, rect2));
                    }
                }
                return object4;
            }
            return null;
        }
        return null;
    }

    public int getFadeBehavior() {
        return this.mFadeBehavior;
    }

    public int getResizeBehavior() {
        return this.mResizeBehavior;
    }

    public Crossfade setFadeBehavior(int n) {
        if (n >= 0 && n <= 2) {
            this.mFadeBehavior = n;
        }
        return this;
    }

    public Crossfade setResizeBehavior(int n) {
        if (n >= 0 && n <= 1) {
            this.mResizeBehavior = n;
        }
        return this;
    }

}

