/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.internal.widget.-$
 *  com.android.internal.widget.-$$Lambda
 *  com.android.internal.widget.-$$Lambda$MessagingPropertyAnimator
 *  com.android.internal.widget.-$$Lambda$MessagingPropertyAnimator$7coWc0tjIUC7grCXucNFbpYTxDI
 */
package com.android.internal.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Paint;
import android.util.IntProperty;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.internal.widget.-$;
import com.android.internal.widget.MessagingLayout;
import com.android.internal.widget.MessagingLinearLayout;
import com.android.internal.widget.ViewClippingUtil;
import com.android.internal.widget._$$Lambda$MessagingPropertyAnimator$7coWc0tjIUC7grCXucNFbpYTxDI;

public class MessagingPropertyAnimator
implements View.OnLayoutChangeListener {
    private static final Interpolator ALPHA_IN = new PathInterpolator(0.4f, 0.0f, 1.0f, 1.0f);
    public static final Interpolator ALPHA_OUT = new PathInterpolator(0.0f, 0.0f, 0.8f, 1.0f);
    private static final long APPEAR_ANIMATION_LENGTH = 210L;
    private static final ViewClippingUtil.ClippingParameters CLIPPING_PARAMETERS = _$$Lambda$MessagingPropertyAnimator$7coWc0tjIUC7grCXucNFbpYTxDI.INSTANCE;
    private static final int TAG_ALPHA_ANIMATOR = 16909424;
    private static final int TAG_FIRST_LAYOUT = 16909425;
    private static final int TAG_LAYOUT_TOP = 16909426;
    private static final int TAG_TOP = 16909428;
    private static final int TAG_TOP_ANIMATOR = 16909427;
    private static final IntProperty<View> TOP = new IntProperty<View>("top"){

        @Override
        public Integer get(View view) {
            return MessagingPropertyAnimator.getTop(view);
        }

        @Override
        public void setValue(View view, int n) {
            MessagingPropertyAnimator.setTop(view, n);
        }
    };

    public static void fadeIn(View view) {
        ObjectAnimator objectAnimator = (ObjectAnimator)view.getTag(16909424);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (view.getVisibility() == 4) {
            view.setVisibility(0);
        }
        objectAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, 0.0f, 1.0f);
        view.setAlpha(0.0f);
        objectAnimator.setInterpolator(ALPHA_IN);
        objectAnimator.setDuration(210L);
        objectAnimator.addListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                View.this.setTagInternal(16909424, null);
                MessagingPropertyAnimator.updateLayerType(View.this, false);
            }
        });
        MessagingPropertyAnimator.updateLayerType(view, true);
        view.setTagInternal(16909424, objectAnimator);
        objectAnimator.start();
    }

    public static void fadeOut(View view, final Runnable runnable) {
        ObjectAnimator objectAnimator = (ObjectAnimator)view.getTag(16909424);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (view.isShown() && (!MessagingLinearLayout.isGone(view) || MessagingPropertyAnimator.isHidingAnimated(view))) {
            objectAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, view.getAlpha(), 0.0f);
            objectAnimator.setInterpolator(ALPHA_OUT);
            objectAnimator.setDuration(210L);
            objectAnimator.addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator object) {
                    View.this.setTagInternal(16909424, null);
                    MessagingPropertyAnimator.updateLayerType(View.this, false);
                    object = runnable;
                    if (object != null) {
                        object.run();
                    }
                }
            });
            MessagingPropertyAnimator.updateLayerType(view, true);
            view.setTagInternal(16909424, objectAnimator);
            objectAnimator.start();
            return;
        }
        view.setAlpha(0.0f);
        if (runnable != null) {
            runnable.run();
        }
    }

    public static int getLayoutTop(View view) {
        Integer n = (Integer)view.getTag(16909426);
        if (n == null) {
            return MessagingPropertyAnimator.getTop(view);
        }
        return n;
    }

    public static int getTop(View view) {
        Integer n = (Integer)view.getTag(16909428);
        if (n == null) {
            return view.getTop();
        }
        return n;
    }

    public static boolean isAnimatingAlpha(View view) {
        boolean bl = view.getTag(16909424) != null;
        return bl;
    }

    public static boolean isAnimatingTranslation(View view) {
        boolean bl = view.getTag(16909427) != null;
        return bl;
    }

    private static boolean isFirstLayout(View object) {
        if ((object = (Boolean)((View)object).getTag(16909425)) == null) {
            return true;
        }
        return (Boolean)object;
    }

    private static boolean isHidingAnimated(View view) {
        if (view instanceof MessagingLinearLayout.MessagingChild) {
            return ((MessagingLinearLayout.MessagingChild)((Object)view)).isHidingAnimated();
        }
        return false;
    }

    static /* synthetic */ boolean lambda$static$0(View view) {
        boolean bl = view.getId() == 16909179;
        return bl;
    }

    public static void recycle(View view) {
        MessagingPropertyAnimator.setFirstLayout(view, true);
    }

    public static void setClippingDeactivated(View view, boolean bl) {
        ViewClippingUtil.setClippingDeactivated(view, bl, CLIPPING_PARAMETERS);
    }

    private static void setFirstLayout(View view, boolean bl) {
        view.setTagInternal(16909425, bl);
    }

    private static void setLayoutTop(View view, int n) {
        view.setTagInternal(16909426, n);
    }

    public static void setToLaidOutPosition(View view) {
        MessagingPropertyAnimator.setTop(view, MessagingPropertyAnimator.getLayoutTop(view));
    }

    private static void setTop(View view, int n) {
        view.setTagInternal(16909428, n);
        MessagingPropertyAnimator.updateTopAndBottom(view);
    }

    public static void startLocalTranslationFrom(View view, int n, Interpolator interpolator2) {
        MessagingPropertyAnimator.startTopAnimation(view, MessagingPropertyAnimator.getTop(view) + n, MessagingPropertyAnimator.getLayoutTop(view), interpolator2);
    }

    public static void startLocalTranslationTo(View view, int n, Interpolator interpolator2) {
        int n2 = MessagingPropertyAnimator.getTop(view);
        MessagingPropertyAnimator.startTopAnimation(view, n2, n2 + n, interpolator2);
    }

    private static void startTopAnimation(View view, int n, int n2, Interpolator interpolator2) {
        ObjectAnimator objectAnimator = (ObjectAnimator)view.getTag(16909427);
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        if (view.isShown() && n != n2 && (!MessagingLinearLayout.isGone(view) || MessagingPropertyAnimator.isHidingAnimated(view))) {
            objectAnimator = ObjectAnimator.ofInt(view, TOP, n, n2);
            MessagingPropertyAnimator.setTop(view, n);
            objectAnimator.setInterpolator(interpolator2);
            objectAnimator.setDuration(210L);
            objectAnimator.addListener(new AnimatorListenerAdapter(){
                public boolean mCancelled;

                @Override
                public void onAnimationCancel(Animator animator2) {
                    this.mCancelled = true;
                }

                @Override
                public void onAnimationEnd(Animator animator2) {
                    View.this.setTagInternal(16909427, null);
                    MessagingPropertyAnimator.setClippingDeactivated(View.this, false);
                }
            });
            MessagingPropertyAnimator.setClippingDeactivated(view, true);
            view.setTagInternal(16909427, objectAnimator);
            objectAnimator.start();
            return;
        }
        MessagingPropertyAnimator.setTop(view, n2);
    }

    private static void updateLayerType(View view, boolean bl) {
        if (view.hasOverlappingRendering() && bl) {
            view.setLayerType(2, null);
        } else if (view.getLayerType() == 2) {
            view.setLayerType(0, null);
        }
    }

    private static void updateTopAndBottom(View view) {
        int n = MessagingPropertyAnimator.getTop(view);
        int n2 = view.getHeight();
        view.setTop(n);
        view.setBottom(n2 + n);
    }

    @Override
    public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
        MessagingPropertyAnimator.setLayoutTop(view, n2);
        if (MessagingPropertyAnimator.isFirstLayout(view)) {
            MessagingPropertyAnimator.setFirstLayout(view, false);
            MessagingPropertyAnimator.setTop(view, n2);
            return;
        }
        MessagingPropertyAnimator.startTopAnimation(view, MessagingPropertyAnimator.getTop(view), n2, MessagingLayout.FAST_OUT_SLOW_IN);
    }

}

