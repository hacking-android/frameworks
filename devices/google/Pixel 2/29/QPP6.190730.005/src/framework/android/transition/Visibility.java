/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.content.Context;
import android.content.res.TypedArray;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionUtils;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Map;

public abstract class Visibility
extends Transition {
    public static final int MODE_IN = 1;
    public static final int MODE_OUT = 2;
    private static final String PROPNAME_PARENT = "android:visibility:parent";
    private static final String PROPNAME_SCREEN_LOCATION = "android:visibility:screenLocation";
    static final String PROPNAME_VISIBILITY = "android:visibility:visibility";
    private static final String[] sTransitionProperties = new String[]{"android:visibility:visibility", "android:visibility:parent"};
    private int mMode = 3;
    private boolean mSuppressLayout = true;

    public Visibility() {
    }

    public Visibility(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.VisibilityTransition);
        int n = ((TypedArray)object).getInt(0, 0);
        ((TypedArray)object).recycle();
        if (n != 0) {
            this.setMode(n);
        }
    }

    private void captureValues(TransitionValues transitionValues) {
        int n = transitionValues.view.getVisibility();
        transitionValues.values.put(PROPNAME_VISIBILITY, n);
        transitionValues.values.put(PROPNAME_PARENT, transitionValues.view.getParent());
        int[] arrn = new int[2];
        transitionValues.view.getLocationOnScreen(arrn);
        transitionValues.values.put(PROPNAME_SCREEN_LOCATION, arrn);
    }

    private static VisibilityInfo getVisibilityChangeInfo(TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = new VisibilityInfo();
        visibilityInfo.visibilityChange = false;
        visibilityInfo.fadeIn = false;
        if (transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.startVisibility = (Integer)transitionValues.values.get(PROPNAME_VISIBILITY);
            visibilityInfo.startParent = (ViewGroup)transitionValues.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.startVisibility = -1;
            visibilityInfo.startParent = null;
        }
        if (transitionValues2 != null && transitionValues2.values.containsKey(PROPNAME_VISIBILITY)) {
            visibilityInfo.endVisibility = (Integer)transitionValues2.values.get(PROPNAME_VISIBILITY);
            visibilityInfo.endParent = (ViewGroup)transitionValues2.values.get(PROPNAME_PARENT);
        } else {
            visibilityInfo.endVisibility = -1;
            visibilityInfo.endParent = null;
        }
        if (transitionValues != null && transitionValues2 != null) {
            if (visibilityInfo.startVisibility == visibilityInfo.endVisibility && visibilityInfo.startParent == visibilityInfo.endParent) {
                return visibilityInfo;
            }
            if (visibilityInfo.startVisibility != visibilityInfo.endVisibility) {
                if (visibilityInfo.startVisibility == 0) {
                    visibilityInfo.fadeIn = false;
                    visibilityInfo.visibilityChange = true;
                } else if (visibilityInfo.endVisibility == 0) {
                    visibilityInfo.fadeIn = true;
                    visibilityInfo.visibilityChange = true;
                }
            } else if (visibilityInfo.startParent != visibilityInfo.endParent) {
                if (visibilityInfo.endParent == null) {
                    visibilityInfo.fadeIn = false;
                    visibilityInfo.visibilityChange = true;
                } else if (visibilityInfo.startParent == null) {
                    visibilityInfo.fadeIn = true;
                    visibilityInfo.visibilityChange = true;
                }
            }
        } else if (transitionValues == null && visibilityInfo.endVisibility == 0) {
            visibilityInfo.fadeIn = true;
            visibilityInfo.visibilityChange = true;
        } else if (transitionValues2 == null && visibilityInfo.startVisibility == 0) {
            visibilityInfo.fadeIn = false;
            visibilityInfo.visibilityChange = true;
        }
        return visibilityInfo;
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
    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        VisibilityInfo visibilityInfo = Visibility.getVisibilityChangeInfo(transitionValues, transitionValues2);
        if (visibilityInfo.visibilityChange && (visibilityInfo.startParent != null || visibilityInfo.endParent != null)) {
            if (visibilityInfo.fadeIn) {
                return this.onAppear(viewGroup, transitionValues, visibilityInfo.startVisibility, transitionValues2, visibilityInfo.endVisibility);
            }
            return this.onDisappear(viewGroup, transitionValues, visibilityInfo.startVisibility, transitionValues2, visibilityInfo.endVisibility);
        }
        return null;
    }

    public int getMode() {
        return this.mMode;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    @Override
    public boolean isTransitionRequired(TransitionValues object, TransitionValues transitionValues) {
        boolean bl;
        block5 : {
            block6 : {
                boolean bl2 = false;
                if (object == null && transitionValues == null) {
                    return false;
                }
                if (object != null && transitionValues != null && transitionValues.values.containsKey(PROPNAME_VISIBILITY) != ((TransitionValues)object).values.containsKey(PROPNAME_VISIBILITY)) {
                    return false;
                }
                object = Visibility.getVisibilityChangeInfo((TransitionValues)object, transitionValues);
                bl = bl2;
                if (!((VisibilityInfo)object).visibilityChange) break block5;
                if (((VisibilityInfo)object).startVisibility == 0) break block6;
                bl = bl2;
                if (((VisibilityInfo)object).endVisibility != 0) break block5;
            }
            bl = true;
        }
        return bl;
    }

    public boolean isVisible(TransitionValues object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        int n = (Integer)((TransitionValues)object).values.get(PROPNAME_VISIBILITY);
        object = (View)((TransitionValues)object).values.get(PROPNAME_PARENT);
        boolean bl2 = bl;
        if (n == 0) {
            bl2 = bl;
            if (object != null) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public Animator onAppear(ViewGroup viewGroup, TransitionValues transitionValues, int n, TransitionValues transitionValues2, int n2) {
        if ((this.mMode & 1) == 1 && transitionValues2 != null) {
            if (transitionValues == null) {
                Object object = (View)((Object)transitionValues2.view.getParent());
                TransitionValues transitionValues3 = this.getMatchedTransitionValues((View)object, false);
                object = this.getTransitionValues((View)object, false);
                if (Visibility.getVisibilityChangeInfo((TransitionValues)transitionValues3, (TransitionValues)object).visibilityChange) {
                    return null;
                }
            }
            return this.onAppear(viewGroup, transitionValues2.view, transitionValues, transitionValues2);
        }
        return null;
    }

    public Animator onAppear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public Animator onDisappear(ViewGroup object, TransitionValues object2, int n, TransitionValues transitionValues, int n2) {
        boolean bl;
        if ((this.mMode & 2) != 2) {
            return null;
        }
        if (object2 == null) {
            return null;
        }
        View view = ((TransitionValues)object2).view;
        Object object3 = transitionValues != null ? transitionValues.view : null;
        int[] arrn = null;
        Object object4 = null;
        Object object5 = null;
        boolean bl2 = false;
        Object object6 = (View)view.getTag(16909491);
        if (object6 != null) {
            object3 = object6;
            bl = true;
        } else {
            n = 0;
            if (object3 != null && ((View)object3).getParent() != null) {
                if (n2 == 4) {
                    object5 = object3;
                } else if (view == object3) {
                    object5 = object3;
                } else {
                    n = 1;
                }
            } else if (object3 != null) {
                arrn = object3;
            } else {
                n = 1;
            }
            object3 = arrn;
            object4 = object5;
            bl = bl2;
            if (n != 0) {
                if (view.getParent() == null) {
                    object3 = view;
                    object4 = object5;
                    bl = bl2;
                } else {
                    object3 = arrn;
                    object4 = object5;
                    bl = bl2;
                    if (view.getParent() instanceof View) {
                        object3 = (View)((Object)view.getParent());
                        object6 = this.getTransitionValues((View)object3, true);
                        object4 = this.getMatchedTransitionValues((View)object3, true);
                        if (!Visibility.getVisibilityChangeInfo((TransitionValues)object6, (TransitionValues)object4).visibilityChange) {
                            object3 = TransitionUtils.copyViewImage((ViewGroup)object, view, (View)object3);
                            object4 = object5;
                            bl = bl2;
                        } else {
                            n = ((View)object3).getId();
                            if (((View)object3).getParent() == null) {
                                object3 = arrn;
                                object4 = object5;
                                bl = bl2;
                                if (n != -1) {
                                    object3 = arrn;
                                    object4 = object5;
                                    bl = bl2;
                                    if (((View)object).findViewById(n) != null) {
                                        object3 = arrn;
                                        object4 = object5;
                                        bl = bl2;
                                        if (this.mCanRemoveViews) {
                                            object3 = view;
                                            object4 = object5;
                                            bl = bl2;
                                        }
                                    }
                                }
                            } else {
                                bl = bl2;
                                object4 = object5;
                                object3 = arrn;
                            }
                        }
                    }
                }
            }
        }
        if (object3 != null) {
            if (!bl) {
                object5 = ((ViewGroup)object).getOverlay();
                arrn = (int[])((TransitionValues)object2).values.get(PROPNAME_SCREEN_LOCATION);
                n = arrn[0];
                n2 = arrn[1];
                arrn = new int[2];
                ((View)object).getLocationOnScreen(arrn);
                ((View)object3).offsetLeftAndRight(n - arrn[0] - ((View)object3).getLeft());
                ((View)object3).offsetTopAndBottom(n2 - arrn[1] - ((View)object3).getTop());
                ((ViewGroupOverlay)object5).add((View)object3);
            } else {
                object5 = null;
            }
            object = this.onDisappear((ViewGroup)object, (View)object3, (TransitionValues)object2, transitionValues);
            if (!bl) {
                if (object == null) {
                    ((ViewGroupOverlay)object5).remove((View)object3);
                } else {
                    view.setTagInternal(16909491, object3);
                    this.addListener(new TransitionListenerAdapter((ViewGroupOverlay)object5, (View)object3, view){
                        final /* synthetic */ View val$finalOverlayView;
                        final /* synthetic */ ViewGroupOverlay val$overlay;
                        final /* synthetic */ View val$startView;
                        {
                            this.val$overlay = viewGroupOverlay;
                            this.val$finalOverlayView = view;
                            this.val$startView = view2;
                        }

                        @Override
                        public void onTransitionEnd(Transition transition2) {
                            this.val$startView.setTagInternal(16909491, null);
                            this.val$overlay.remove(this.val$finalOverlayView);
                            transition2.removeListener(this);
                        }

                        @Override
                        public void onTransitionPause(Transition transition2) {
                            this.val$overlay.remove(this.val$finalOverlayView);
                        }

                        @Override
                        public void onTransitionResume(Transition transition2) {
                            if (this.val$finalOverlayView.getParent() == null) {
                                this.val$overlay.add(this.val$finalOverlayView);
                            } else {
                                Visibility.this.cancel();
                            }
                        }
                    });
                }
            }
            return object;
        }
        if (object4 != null) {
            n = ((View)object4).getVisibility();
            ((View)object4).setTransitionVisibility(0);
            object = this.onDisappear((ViewGroup)object, (View)object4, (TransitionValues)object2, transitionValues);
            if (object != null) {
                object2 = new DisappearListener((View)object4, n2, this.mSuppressLayout);
                ((Animator)object).addListener((Animator.AnimatorListener)object2);
                ((Animator)object).addPauseListener((Animator.AnimatorPauseListener)object2);
                this.addListener((Transition.TransitionListener)object2);
            } else {
                ((View)object4).setTransitionVisibility(n);
            }
            return object;
        }
        return null;
    }

    public Animator onDisappear(ViewGroup viewGroup, View view, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    public void setMode(int n) {
        if ((n & -4) == 0) {
            this.mMode = n;
            return;
        }
        throw new IllegalArgumentException("Only MODE_IN and MODE_OUT flags are allowed");
    }

    public void setSuppressLayout(boolean bl) {
        this.mSuppressLayout = bl;
    }

    private static class DisappearListener
    extends TransitionListenerAdapter
    implements Animator.AnimatorListener,
    Animator.AnimatorPauseListener {
        boolean mCanceled = false;
        private final int mFinalVisibility;
        private boolean mLayoutSuppressed;
        private final ViewGroup mParent;
        private final boolean mSuppressLayout;
        private final View mView;

        public DisappearListener(View view, int n, boolean bl) {
            this.mView = view;
            this.mFinalVisibility = n;
            this.mParent = (ViewGroup)view.getParent();
            this.mSuppressLayout = bl;
            this.suppressLayout(true);
        }

        private void hideViewWhenNotCanceled() {
            if (!this.mCanceled) {
                this.mView.setTransitionVisibility(this.mFinalVisibility);
                ViewGroup viewGroup = this.mParent;
                if (viewGroup != null) {
                    viewGroup.invalidate();
                }
            }
            this.suppressLayout(false);
        }

        private void suppressLayout(boolean bl) {
            ViewGroup viewGroup;
            if (this.mSuppressLayout && this.mLayoutSuppressed != bl && (viewGroup = this.mParent) != null) {
                this.mLayoutSuppressed = bl;
                viewGroup.suppressLayout(bl);
            }
        }

        @Override
        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
            this.hideViewWhenNotCanceled();
        }

        @Override
        public void onAnimationPause(Animator animator2) {
            if (!this.mCanceled) {
                this.mView.setTransitionVisibility(this.mFinalVisibility);
            }
        }

        @Override
        public void onAnimationRepeat(Animator animator2) {
        }

        @Override
        public void onAnimationResume(Animator animator2) {
            if (!this.mCanceled) {
                this.mView.setTransitionVisibility(0);
            }
        }

        @Override
        public void onAnimationStart(Animator animator2) {
        }

        @Override
        public void onTransitionEnd(Transition transition2) {
            this.hideViewWhenNotCanceled();
            transition2.removeListener(this);
        }

        @Override
        public void onTransitionPause(Transition transition2) {
            this.suppressLayout(false);
        }

        @Override
        public void onTransitionResume(Transition transition2) {
            this.suppressLayout(true);
        }
    }

    private static class VisibilityInfo {
        ViewGroup endParent;
        int endVisibility;
        boolean fadeIn;
        ViewGroup startParent;
        int startVisibility;
        boolean visibilityChange;

        private VisibilityInfo() {
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface VisibilityMode {
    }

}

