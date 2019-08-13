/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.RectEvaluator;
import android.animation.TypeEvaluator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionUtils;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import com.android.internal.R;
import java.util.Map;

public class ChangeBounds
extends Transition {
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static final Property<View, PointF> BOTTOM_RIGHT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> BOTTOM_RIGHT_PROPERTY;
    private static final Property<Drawable, PointF> DRAWABLE_ORIGIN_PROPERTY;
    private static final String LOG_TAG = "ChangeBounds";
    @UnsupportedAppUsage(maxTargetSdk=28)
    private static final Property<View, PointF> POSITION_PROPERTY;
    private static final String PROPNAME_BOUNDS = "android:changeBounds:bounds";
    private static final String PROPNAME_CLIP = "android:changeBounds:clip";
    private static final String PROPNAME_PARENT = "android:changeBounds:parent";
    private static final String PROPNAME_WINDOW_X = "android:changeBounds:windowX";
    private static final String PROPNAME_WINDOW_Y = "android:changeBounds:windowY";
    private static final Property<View, PointF> TOP_LEFT_ONLY_PROPERTY;
    private static final Property<ViewBounds, PointF> TOP_LEFT_PROPERTY;
    private static RectEvaluator sRectEvaluator;
    private static final String[] sTransitionProperties;
    boolean mReparent = false;
    boolean mResizeClip = false;
    int[] tempLocation = new int[2];

    static {
        sTransitionProperties = new String[]{PROPNAME_BOUNDS, PROPNAME_CLIP, PROPNAME_PARENT, PROPNAME_WINDOW_X, PROPNAME_WINDOW_Y};
        DRAWABLE_ORIGIN_PROPERTY = new Property<Drawable, PointF>(PointF.class, "boundsOrigin"){
            private Rect mBounds = new Rect();

            @Override
            public PointF get(Drawable drawable2) {
                drawable2.copyBounds(this.mBounds);
                return new PointF(this.mBounds.left, this.mBounds.top);
            }

            @Override
            public void set(Drawable drawable2, PointF pointF) {
                drawable2.copyBounds(this.mBounds);
                this.mBounds.offsetTo(Math.round(pointF.x), Math.round(pointF.y));
                drawable2.setBounds(this.mBounds);
            }
        };
        TOP_LEFT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "topLeft"){

            @Override
            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            @Override
            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setTopLeft(pointF);
            }
        };
        BOTTOM_RIGHT_PROPERTY = new Property<ViewBounds, PointF>(PointF.class, "bottomRight"){

            @Override
            public PointF get(ViewBounds viewBounds) {
                return null;
            }

            @Override
            public void set(ViewBounds viewBounds, PointF pointF) {
                viewBounds.setBottomRight(pointF);
            }
        };
        BOTTOM_RIGHT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "bottomRight"){

            @Override
            public PointF get(View view) {
                return null;
            }

            @Override
            public void set(View view, PointF pointF) {
                view.setLeftTopRightBottom(view.getLeft(), view.getTop(), Math.round(pointF.x), Math.round(pointF.y));
            }
        };
        TOP_LEFT_ONLY_PROPERTY = new Property<View, PointF>(PointF.class, "topLeft"){

            @Override
            public PointF get(View view) {
                return null;
            }

            @Override
            public void set(View view, PointF pointF) {
                view.setLeftTopRightBottom(Math.round(pointF.x), Math.round(pointF.y), view.getRight(), view.getBottom());
            }
        };
        POSITION_PROPERTY = new Property<View, PointF>(PointF.class, "position"){

            @Override
            public PointF get(View view) {
                return null;
            }

            @Override
            public void set(View view, PointF pointF) {
                int n = Math.round(pointF.x);
                int n2 = Math.round(pointF.y);
                view.setLeftTopRightBottom(n, n2, view.getWidth() + n, view.getHeight() + n2);
            }
        };
        sRectEvaluator = new RectEvaluator();
    }

    public ChangeBounds() {
    }

    public ChangeBounds(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ChangeBounds);
        boolean bl = ((TypedArray)object).getBoolean(0, false);
        ((TypedArray)object).recycle();
        this.setResizeClip(bl);
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.isLaidOut() || view.getWidth() != 0 || view.getHeight() != 0) {
            transitionValues.values.put(PROPNAME_BOUNDS, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
            if (this.mReparent) {
                transitionValues.view.getLocationInWindow(this.tempLocation);
                transitionValues.values.put(PROPNAME_WINDOW_X, this.tempLocation[0]);
                transitionValues.values.put(PROPNAME_WINDOW_Y, this.tempLocation[1]);
            }
            if (this.mResizeClip) {
                transitionValues.values.put(PROPNAME_CLIP, view.getClipBounds());
            }
        }
    }

    private boolean parentMatches(View view, View view2) {
        boolean bl = true;
        if (this.mReparent) {
            boolean bl2 = true;
            bl = true;
            TransitionValues transitionValues = this.getMatchedTransitionValues(view, true);
            if (transitionValues == null) {
                if (view != view2) {
                    bl = false;
                }
            } else {
                bl = view2 == transitionValues.view ? bl2 : false;
            }
        }
        return bl;
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
        block28 : {
            block29 : {
                int n;
                int n2;
                Object object4;
                int n3;
                int n4;
                block37 : {
                    block36 : {
                        block30 : {
                            int n5;
                            int n6;
                            int n7;
                            int n8;
                            int n9;
                            int n10;
                            int n11;
                            int n12;
                            int n13;
                            int n14;
                            Object object5;
                            int n15;
                            int n16;
                            int n17;
                            int n18;
                            block35 : {
                                block34 : {
                                    block32 : {
                                        block33 : {
                                            block31 : {
                                                if (object2 == null || object3 == null) break block28;
                                                object5 = ((TransitionValues)object2).values;
                                                object4 = ((TransitionValues)object3).values;
                                                object5 = (ViewGroup)object5.get(PROPNAME_PARENT);
                                                ViewGroup viewGroup = (ViewGroup)object4.get(PROPNAME_PARENT);
                                                if (object5 == null || viewGroup == null) break block29;
                                                object4 = ((TransitionValues)object3).view;
                                                if (!this.parentMatches((View)object5, viewGroup)) break block30;
                                                object5 = (Rect)((TransitionValues)object2).values.get(PROPNAME_BOUNDS);
                                                object = (Rect)((TransitionValues)object3).values.get(PROPNAME_BOUNDS);
                                                n10 = ((Rect)object5).left;
                                                n17 = ((Rect)object).left;
                                                n14 = ((Rect)object5).top;
                                                n11 = ((Rect)object).top;
                                                n15 = ((Rect)object5).right;
                                                n6 = ((Rect)object).right;
                                                n13 = ((Rect)object5).bottom;
                                                n18 = ((Rect)object).bottom;
                                                n9 = n15 - n10;
                                                n16 = n13 - n14;
                                                n8 = n6 - n17;
                                                n5 = n18 - n11;
                                                object2 = (Rect)((TransitionValues)object2).values.get(PROPNAME_CLIP);
                                                object3 = (Rect)((TransitionValues)object3).values.get(PROPNAME_CLIP);
                                                int n19 = 0;
                                                n7 = 0;
                                                if (n9 != 0 && n16 != 0) break block31;
                                                n12 = n19;
                                                if (n8 == 0) break block32;
                                                n12 = n19;
                                                if (n5 == 0) break block32;
                                            }
                                            if (n10 != n17 || n14 != n11) {
                                                n7 = 0 + 1;
                                            }
                                            if (n15 != n6) break block33;
                                            n12 = n7;
                                            if (n13 == n18) break block32;
                                        }
                                        n12 = n7 + 1;
                                    }
                                    if (object2 != null && !((Rect)object2).equals(object3)) break block34;
                                    n7 = n12;
                                    if (object2 != null) break block35;
                                    n7 = n12;
                                    if (object3 == null) break block35;
                                }
                                n7 = n12 + 1;
                            }
                            if (n7 > 0) {
                                if (((View)object4).getParent() instanceof ViewGroup) {
                                    object5 = (ViewGroup)((View)object4).getParent();
                                    ((ViewGroup)object5).suppressLayout(true);
                                    object = this;
                                    ((Transition)object).addListener(new TransitionListenerAdapter((ViewGroup)object5){
                                        boolean mCanceled = false;
                                        final /* synthetic */ ViewGroup val$parent;
                                        {
                                            this.val$parent = viewGroup;
                                        }

                                        @Override
                                        public void onTransitionCancel(Transition transition2) {
                                            this.val$parent.suppressLayout(false);
                                            this.mCanceled = true;
                                        }

                                        @Override
                                        public void onTransitionEnd(Transition transition2) {
                                            if (!this.mCanceled) {
                                                this.val$parent.suppressLayout(false);
                                            }
                                            transition2.removeListener(this);
                                        }

                                        @Override
                                        public void onTransitionPause(Transition transition2) {
                                            this.val$parent.suppressLayout(false);
                                        }

                                        @Override
                                        public void onTransitionResume(Transition transition2) {
                                            this.val$parent.suppressLayout(true);
                                        }
                                    });
                                }
                                object = object3;
                                object3 = object2;
                                object2 = this;
                                if (!((ChangeBounds)object2).mResizeClip) {
                                    ((View)object4).setLeftTopRightBottom(n10, n14, n15, n13);
                                    if (n7 == 2) {
                                        if (n9 == n8 && n16 == n5) {
                                            object = this.getPathMotion().getPath(n10, n14, n17, n11);
                                            object = ObjectAnimator.ofObject(object4, POSITION_PROPERTY, null, (Path)object);
                                        } else {
                                            object3 = new ViewBounds((View)object4);
                                            object = this.getPathMotion().getPath(n10, n14, n17, n11);
                                            object5 = ObjectAnimator.ofObject(object3, TOP_LEFT_PROPERTY, null, (Path)object);
                                            object = this.getPathMotion().getPath(n15, n13, n6, n18);
                                            object4 = ObjectAnimator.ofObject(object3, BOTTOM_RIGHT_PROPERTY, null, (Path)object);
                                            object = new AnimatorSet();
                                            ((AnimatorSet)object).playTogether(new Animator[]{object5, object4});
                                            ((Animator)object).addListener(new AnimatorListenerAdapter((ViewBounds)object3){
                                                private ViewBounds mViewBounds;
                                                final /* synthetic */ ViewBounds val$viewBounds;
                                                {
                                                    this.val$viewBounds = viewBounds;
                                                    this.mViewBounds = this.val$viewBounds;
                                                }
                                            });
                                        }
                                    } else if (n10 == n17 && n14 == n11) {
                                        object = this.getPathMotion().getPath(n15, n13, n6, n18);
                                        object = ObjectAnimator.ofObject(object4, BOTTOM_RIGHT_ONLY_PROPERTY, null, (Path)object);
                                    } else {
                                        object = this.getPathMotion().getPath(n10, n14, n17, n11);
                                        object = ObjectAnimator.ofObject(object4, TOP_LEFT_ONLY_PROPERTY, null, (Path)object);
                                    }
                                } else {
                                    n12 = Math.max(n9, n8);
                                    ((View)object4).setLeftTopRightBottom(n10, n14, n10 + n12, n14 + Math.max(n16, n5));
                                    if (n10 == n17 && n14 == n11) {
                                        object2 = null;
                                    } else {
                                        object2 = this.getPathMotion().getPath(n10, n14, n17, n11);
                                        object2 = ObjectAnimator.ofObject(object4, POSITION_PROPERTY, null, (Path)object2);
                                    }
                                    if (object3 == null) {
                                        object3 = new Rect(0, 0, n9, n16);
                                    }
                                    if (!((Rect)object3).equals(object5 = object == null ? new Rect(0, 0, n8, n5) : object)) {
                                        ((View)object4).setClipBounds((Rect)object3);
                                        object3 = ObjectAnimator.ofObject(object4, "clipBounds", (TypeEvaluator)sRectEvaluator, object3, object5);
                                        ((Animator)object3).addListener(new AnimatorListenerAdapter((View)object4, (Rect)object, n17, n11, n6, n18){
                                            private boolean mIsCanceled;
                                            final /* synthetic */ int val$endBottom;
                                            final /* synthetic */ int val$endLeft;
                                            final /* synthetic */ int val$endRight;
                                            final /* synthetic */ int val$endTop;
                                            final /* synthetic */ Rect val$finalClip;
                                            final /* synthetic */ View val$view;
                                            {
                                                this.val$view = view;
                                                this.val$finalClip = rect;
                                                this.val$endLeft = n;
                                                this.val$endTop = n2;
                                                this.val$endRight = n3;
                                                this.val$endBottom = n4;
                                            }

                                            @Override
                                            public void onAnimationCancel(Animator animator2) {
                                                this.mIsCanceled = true;
                                            }

                                            @Override
                                            public void onAnimationEnd(Animator animator2) {
                                                if (!this.mIsCanceled) {
                                                    this.val$view.setClipBounds(this.val$finalClip);
                                                    this.val$view.setLeftTopRightBottom(this.val$endLeft, this.val$endTop, this.val$endRight, this.val$endBottom);
                                                }
                                            }
                                        });
                                        object = object3;
                                    } else {
                                        object = null;
                                    }
                                    object = TransitionUtils.mergeAnimators((Animator)object2, (Animator)object);
                                }
                                return object;
                            }
                            break block36;
                        }
                        ((View)object).getLocationInWindow(this.tempLocation);
                        n4 = (Integer)((TransitionValues)object2).values.get(PROPNAME_WINDOW_X) - this.tempLocation[0];
                        n2 = (Integer)((TransitionValues)object2).values.get(PROPNAME_WINDOW_Y) - this.tempLocation[1];
                        n = (Integer)((TransitionValues)object3).values.get(PROPNAME_WINDOW_X) - this.tempLocation[0];
                        n3 = (Integer)((TransitionValues)object3).values.get(PROPNAME_WINDOW_Y) - this.tempLocation[1];
                        if (n4 != n || n2 != n3) break block37;
                    }
                    return null;
                }
                int n20 = ((View)object4).getWidth();
                int n21 = ((View)object4).getHeight();
                object2 = Bitmap.createBitmap(n20, n21, Bitmap.Config.ARGB_8888);
                ((View)object4).draw(new Canvas((Bitmap)object2));
                object2 = new BitmapDrawable((Bitmap)object2);
                ((Drawable)object2).setBounds(n4, n2, n4 + n20, n2 + n21);
                float f = ((View)object4).getTransitionAlpha();
                ((View)object4).setTransitionAlpha(0.0f);
                ((ViewGroup)object).getOverlay().add((Drawable)object2);
                object3 = this.getPathMotion().getPath(n4, n2, n, n3);
                object3 = ObjectAnimator.ofPropertyValuesHolder(object2, PropertyValuesHolder.ofObject(DRAWABLE_ORIGIN_PROPERTY, null, (Path)object3));
                ((Animator)object3).addListener(new AnimatorListenerAdapter((ViewGroup)object, (BitmapDrawable)object2, (View)object4, f){
                    final /* synthetic */ BitmapDrawable val$drawable;
                    final /* synthetic */ ViewGroup val$sceneRoot;
                    final /* synthetic */ float val$transitionAlpha;
                    final /* synthetic */ View val$view;
                    {
                        this.val$sceneRoot = viewGroup;
                        this.val$drawable = bitmapDrawable;
                        this.val$view = view;
                        this.val$transitionAlpha = f;
                    }

                    @Override
                    public void onAnimationEnd(Animator animator2) {
                        this.val$sceneRoot.getOverlay().remove(this.val$drawable);
                        this.val$view.setTransitionAlpha(this.val$transitionAlpha);
                    }
                });
                return object3;
            }
            return null;
        }
        return null;
    }

    public boolean getResizeClip() {
        return this.mResizeClip;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Deprecated
    public void setReparent(boolean bl) {
        this.mReparent = bl;
    }

    public void setResizeClip(boolean bl) {
        this.mResizeClip = bl;
    }

    private static class ViewBounds {
        private int mBottom;
        private int mBottomRightCalls;
        private int mLeft;
        private int mRight;
        private int mTop;
        private int mTopLeftCalls;
        private View mView;

        public ViewBounds(View view) {
            this.mView = view;
        }

        private void setLeftTopRightBottom() {
            this.mView.setLeftTopRightBottom(this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mTopLeftCalls = 0;
            this.mBottomRightCalls = 0;
        }

        public void setBottomRight(PointF pointF) {
            this.mRight = Math.round(pointF.x);
            this.mBottom = Math.round(pointF.y);
            ++this.mBottomRightCalls;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                this.setLeftTopRightBottom();
            }
        }

        public void setTopLeft(PointF pointF) {
            this.mLeft = Math.round(pointF.x);
            this.mTop = Math.round(pointF.y);
            ++this.mTopLeftCalls;
            if (this.mTopLeftCalls == this.mBottomRightCalls) {
                this.setLeftTopRightBottom();
            }
        }
    }

}

