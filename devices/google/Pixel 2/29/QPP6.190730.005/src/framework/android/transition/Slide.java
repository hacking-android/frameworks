/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.SidePropagation;
import android.transition.TransitionPropagation;
import android.transition.TransitionValues;
import android.transition.TranslationAnimationCreator;
import android.transition.Visibility;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public class Slide
extends Visibility {
    private static final String PROPNAME_SCREEN_POSITION = "android:slide:screenPosition";
    private static final String TAG = "Slide";
    private static final TimeInterpolator sAccelerate;
    private static final CalculateSlide sCalculateBottom;
    private static final CalculateSlide sCalculateEnd;
    private static final CalculateSlide sCalculateLeft;
    private static final CalculateSlide sCalculateRight;
    private static final CalculateSlide sCalculateStart;
    private static final CalculateSlide sCalculateTop;
    private static final TimeInterpolator sDecelerate;
    private CalculateSlide mSlideCalculator = sCalculateBottom;
    private int mSlideEdge = 80;
    private float mSlideFraction = 1.0f;

    static {
        sDecelerate = new DecelerateInterpolator();
        sAccelerate = new AccelerateInterpolator();
        sCalculateLeft = new CalculateSlideHorizontal(){

            @Override
            public float getGoneX(ViewGroup viewGroup, View view, float f) {
                return view.getTranslationX() - (float)viewGroup.getWidth() * f;
            }
        };
        sCalculateStart = new CalculateSlideHorizontal(){

            @Override
            public float getGoneX(ViewGroup viewGroup, View view, float f) {
                int n = viewGroup.getLayoutDirection();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                f = bl ? view.getTranslationX() + (float)viewGroup.getWidth() * f : view.getTranslationX() - (float)viewGroup.getWidth() * f;
                return f;
            }
        };
        sCalculateTop = new CalculateSlideVertical(){

            @Override
            public float getGoneY(ViewGroup viewGroup, View view, float f) {
                return view.getTranslationY() - (float)viewGroup.getHeight() * f;
            }
        };
        sCalculateRight = new CalculateSlideHorizontal(){

            @Override
            public float getGoneX(ViewGroup viewGroup, View view, float f) {
                return view.getTranslationX() + (float)viewGroup.getWidth() * f;
            }
        };
        sCalculateEnd = new CalculateSlideHorizontal(){

            @Override
            public float getGoneX(ViewGroup viewGroup, View view, float f) {
                int n = viewGroup.getLayoutDirection();
                boolean bl = true;
                if (n != 1) {
                    bl = false;
                }
                f = bl ? view.getTranslationX() - (float)viewGroup.getWidth() * f : view.getTranslationX() + (float)viewGroup.getWidth() * f;
                return f;
            }
        };
        sCalculateBottom = new CalculateSlideVertical(){

            @Override
            public float getGoneY(ViewGroup viewGroup, View view, float f) {
                return view.getTranslationY() + (float)viewGroup.getHeight() * f;
            }
        };
    }

    public Slide() {
        this.setSlideEdge(80);
    }

    public Slide(int n) {
        this.setSlideEdge(n);
    }

    public Slide(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.Slide);
        int n = ((TypedArray)object).getInt(0, 80);
        ((TypedArray)object).recycle();
        this.setSlideEdge(n);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        int[] arrn = new int[2];
        view.getLocationOnScreen(arrn);
        transitionValues.values.put(PROPNAME_SCREEN_POSITION, arrn);
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        this.captureValues(transitionValues);
    }

    public int getSlideEdge() {
        return this.mSlideEdge;
    }

    @Override
    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues arrn, TransitionValues transitionValues) {
        if (transitionValues == null) {
            return null;
        }
        arrn = (int[])transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        float f = view.getTranslationX();
        float f2 = view.getTranslationY();
        float f3 = this.mSlideCalculator.getGoneX(viewGroup, view, this.mSlideFraction);
        float f4 = this.mSlideCalculator.getGoneY(viewGroup, view, this.mSlideFraction);
        return TranslationAnimationCreator.createAnimation(view, transitionValues, arrn[0], arrn[1], f3, f4, f, f2, sDecelerate, this);
    }

    @Override
    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues arrn) {
        if (transitionValues == null) {
            return null;
        }
        arrn = (int[])transitionValues.values.get(PROPNAME_SCREEN_POSITION);
        float f = view.getTranslationX();
        float f2 = view.getTranslationY();
        float f3 = this.mSlideCalculator.getGoneX(viewGroup, view, this.mSlideFraction);
        float f4 = this.mSlideCalculator.getGoneY(viewGroup, view, this.mSlideFraction);
        return TranslationAnimationCreator.createAnimation(view, transitionValues, arrn[0], arrn[1], f, f2, f3, f4, sAccelerate, this);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void setSlideEdge(int n) {
        if (n != 3) {
            if (n != 5) {
                if (n != 48) {
                    if (n != 80) {
                        if (n != 8388611) {
                            if (n != 8388613) throw new IllegalArgumentException("Invalid slide direction");
                            this.mSlideCalculator = sCalculateEnd;
                        } else {
                            this.mSlideCalculator = sCalculateStart;
                        }
                    } else {
                        this.mSlideCalculator = sCalculateBottom;
                    }
                } else {
                    this.mSlideCalculator = sCalculateTop;
                }
            } else {
                this.mSlideCalculator = sCalculateRight;
            }
        } else {
            this.mSlideCalculator = sCalculateLeft;
        }
        this.mSlideEdge = n;
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.setSide(n);
        this.setPropagation(sidePropagation);
    }

    public void setSlideFraction(float f) {
        this.mSlideFraction = f;
    }

    private static interface CalculateSlide {
        public float getGoneX(ViewGroup var1, View var2, float var3);

        public float getGoneY(ViewGroup var1, View var2, float var3);
    }

    private static abstract class CalculateSlideHorizontal
    implements CalculateSlide {
        private CalculateSlideHorizontal() {
        }

        @Override
        public float getGoneY(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationY();
        }
    }

    private static abstract class CalculateSlideVertical
    implements CalculateSlide {
        private CalculateSlideVertical() {
        }

        @Override
        public float getGoneX(ViewGroup viewGroup, View view, float f) {
            return view.getTranslationX();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface GravityFlag {
    }

}

