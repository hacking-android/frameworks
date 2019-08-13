/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.KeyframeSet;
import android.animation.Keyframes;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.annotation.UnsupportedAppUsage;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LayoutTransition {
    private static TimeInterpolator ACCEL_DECEL_INTERPOLATOR;
    public static final int APPEARING = 2;
    public static final int CHANGE_APPEARING = 0;
    public static final int CHANGE_DISAPPEARING = 1;
    public static final int CHANGING = 4;
    private static TimeInterpolator DECEL_INTERPOLATOR;
    private static long DEFAULT_DURATION = 0L;
    public static final int DISAPPEARING = 3;
    private static final int FLAG_APPEARING = 1;
    private static final int FLAG_CHANGE_APPEARING = 4;
    private static final int FLAG_CHANGE_DISAPPEARING = 8;
    private static final int FLAG_CHANGING = 16;
    private static final int FLAG_DISAPPEARING = 2;
    private static ObjectAnimator defaultChange;
    private static ObjectAnimator defaultChangeIn;
    private static ObjectAnimator defaultChangeOut;
    private static ObjectAnimator defaultFadeIn;
    private static ObjectAnimator defaultFadeOut;
    private static TimeInterpolator sAppearingInterpolator;
    private static TimeInterpolator sChangingAppearingInterpolator;
    private static TimeInterpolator sChangingDisappearingInterpolator;
    private static TimeInterpolator sChangingInterpolator;
    private static TimeInterpolator sDisappearingInterpolator;
    private final LinkedHashMap<View, Animator> currentAppearingAnimations;
    private final LinkedHashMap<View, Animator> currentChangingAnimations;
    private final LinkedHashMap<View, Animator> currentDisappearingAnimations;
    private final HashMap<View, View.OnLayoutChangeListener> layoutChangeListenerMap;
    private boolean mAnimateParentHierarchy;
    private Animator mAppearingAnim = null;
    private long mAppearingDelay;
    private long mAppearingDuration;
    private TimeInterpolator mAppearingInterpolator;
    private Animator mChangingAnim = null;
    private Animator mChangingAppearingAnim = null;
    private long mChangingAppearingDelay;
    private long mChangingAppearingDuration;
    private TimeInterpolator mChangingAppearingInterpolator;
    private long mChangingAppearingStagger;
    private long mChangingDelay;
    private Animator mChangingDisappearingAnim = null;
    private long mChangingDisappearingDelay;
    private long mChangingDisappearingDuration;
    private TimeInterpolator mChangingDisappearingInterpolator;
    private long mChangingDisappearingStagger;
    private long mChangingDuration;
    private TimeInterpolator mChangingInterpolator;
    private long mChangingStagger;
    private Animator mDisappearingAnim = null;
    private long mDisappearingDelay;
    private long mDisappearingDuration;
    private TimeInterpolator mDisappearingInterpolator;
    private ArrayList<TransitionListener> mListeners;
    private int mTransitionTypes;
    private final HashMap<View, Animator> pendingAnimations;
    private long staggerDelay;

    static {
        TimeInterpolator timeInterpolator;
        DEFAULT_DURATION = 300L;
        ACCEL_DECEL_INTERPOLATOR = new AccelerateDecelerateInterpolator();
        DECEL_INTERPOLATOR = new DecelerateInterpolator();
        sAppearingInterpolator = timeInterpolator = ACCEL_DECEL_INTERPOLATOR;
        sDisappearingInterpolator = timeInterpolator;
        sChangingAppearingInterpolator = timeInterpolator = DECEL_INTERPOLATOR;
        sChangingDisappearingInterpolator = timeInterpolator;
        sChangingInterpolator = timeInterpolator;
    }

    public LayoutTransition() {
        long l;
        this.mChangingAppearingDuration = l = DEFAULT_DURATION;
        this.mChangingDisappearingDuration = l;
        this.mChangingDuration = l;
        this.mAppearingDuration = l;
        this.mDisappearingDuration = l;
        this.mAppearingDelay = l;
        this.mDisappearingDelay = 0L;
        this.mChangingAppearingDelay = 0L;
        this.mChangingDisappearingDelay = l;
        this.mChangingDelay = 0L;
        this.mChangingAppearingStagger = 0L;
        this.mChangingDisappearingStagger = 0L;
        this.mChangingStagger = 0L;
        this.mAppearingInterpolator = sAppearingInterpolator;
        this.mDisappearingInterpolator = sDisappearingInterpolator;
        this.mChangingAppearingInterpolator = sChangingAppearingInterpolator;
        this.mChangingDisappearingInterpolator = sChangingDisappearingInterpolator;
        this.mChangingInterpolator = sChangingInterpolator;
        this.pendingAnimations = new HashMap();
        this.currentChangingAnimations = new LinkedHashMap();
        this.currentAppearingAnimations = new LinkedHashMap();
        this.currentDisappearingAnimations = new LinkedHashMap();
        this.layoutChangeListenerMap = new HashMap();
        this.mTransitionTypes = 15;
        this.mAnimateParentHierarchy = true;
        if (defaultChangeIn == null) {
            defaultChangeIn = ObjectAnimator.ofPropertyValuesHolder(null, PropertyValuesHolder.ofInt("left", 0, 1), PropertyValuesHolder.ofInt("top", 0, 1), PropertyValuesHolder.ofInt("right", 0, 1), PropertyValuesHolder.ofInt("bottom", 0, 1), PropertyValuesHolder.ofInt("scrollX", 0, 1), PropertyValuesHolder.ofInt("scrollY", 0, 1));
            defaultChangeIn.setDuration(DEFAULT_DURATION);
            defaultChangeIn.setStartDelay(this.mChangingAppearingDelay);
            defaultChangeIn.setInterpolator(this.mChangingAppearingInterpolator);
            defaultChangeOut = defaultChangeIn.clone();
            defaultChangeOut.setStartDelay(this.mChangingDisappearingDelay);
            defaultChangeOut.setInterpolator(this.mChangingDisappearingInterpolator);
            defaultChange = defaultChangeIn.clone();
            defaultChange.setStartDelay(this.mChangingDelay);
            defaultChange.setInterpolator(this.mChangingInterpolator);
            defaultFadeIn = ObjectAnimator.ofFloat(null, "alpha", 0.0f, 1.0f);
            defaultFadeIn.setDuration(DEFAULT_DURATION);
            defaultFadeIn.setStartDelay(this.mAppearingDelay);
            defaultFadeIn.setInterpolator(this.mAppearingInterpolator);
            defaultFadeOut = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.0f);
            defaultFadeOut.setDuration(DEFAULT_DURATION);
            defaultFadeOut.setStartDelay(this.mDisappearingDelay);
            defaultFadeOut.setInterpolator(this.mDisappearingInterpolator);
        }
        this.mChangingAppearingAnim = defaultChangeIn;
        this.mChangingDisappearingAnim = defaultChangeOut;
        this.mChangingAnim = defaultChange;
        this.mAppearingAnim = defaultFadeIn;
        this.mDisappearingAnim = defaultFadeOut;
    }

    static /* synthetic */ long access$214(LayoutTransition layoutTransition, long l) {
        layoutTransition.staggerDelay = l = layoutTransition.staggerDelay + l;
        return l;
    }

    private void addChild(ViewGroup viewGroup, View view, boolean bl) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 1) == 1) {
            this.cancel(3);
        }
        if (bl && (this.mTransitionTypes & 4) == 4) {
            this.cancel(0);
            this.cancel(4);
        }
        if (this.hasListeners() && (this.mTransitionTypes & 1) == 1) {
            Iterator iterator = ((ArrayList)this.mListeners.clone()).iterator();
            while (iterator.hasNext()) {
                ((TransitionListener)iterator.next()).startTransition(this, viewGroup, view, 2);
            }
        }
        if (bl && (this.mTransitionTypes & 4) == 4) {
            this.runChangeTransition(viewGroup, view, 2);
        }
        if ((this.mTransitionTypes & 1) == 1) {
            this.runAppearingTransition(viewGroup, view);
        }
    }

    private boolean hasListeners() {
        ArrayList<TransitionListener> arrayList = this.mListeners;
        boolean bl = arrayList != null && arrayList.size() > 0;
        return bl;
    }

    private void removeChild(ViewGroup viewGroup, View view, boolean bl) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 2) == 2) {
            this.cancel(2);
        }
        if (bl && (this.mTransitionTypes & 8) == 8) {
            this.cancel(1);
            this.cancel(4);
        }
        if (this.hasListeners() && (this.mTransitionTypes & 2) == 2) {
            Iterator iterator = ((ArrayList)this.mListeners.clone()).iterator();
            while (iterator.hasNext()) {
                ((TransitionListener)iterator.next()).startTransition(this, viewGroup, view, 3);
            }
        }
        if (bl && (this.mTransitionTypes & 8) == 8) {
            this.runChangeTransition(viewGroup, view, 3);
        }
        if ((this.mTransitionTypes & 2) == 2) {
            this.runDisappearingTransition(viewGroup, view);
        }
    }

    private void runAppearingTransition(final ViewGroup viewGroup, final View view) {
        Object object = this.currentDisappearingAnimations.get(view);
        if (object != null) {
            ((Animator)object).cancel();
        }
        if ((object = this.mAppearingAnim) == null) {
            if (this.hasListeners()) {
                object = ((ArrayList)this.mListeners.clone()).iterator();
                while (object.hasNext()) {
                    ((TransitionListener)object.next()).endTransition(this, viewGroup, view, 2);
                }
            }
            return;
        }
        Animator animator2 = ((Animator)object).clone();
        animator2.setTarget(view);
        animator2.setStartDelay(this.mAppearingDelay);
        animator2.setDuration(this.mAppearingDuration);
        object = this.mAppearingInterpolator;
        if (object != sAppearingInterpolator) {
            animator2.setInterpolator((TimeInterpolator)object);
        }
        if (animator2 instanceof ObjectAnimator) {
            ((ObjectAnimator)animator2).setCurrentPlayTime(0L);
        }
        animator2.addListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator object) {
                LayoutTransition.this.currentAppearingAnimations.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    object = ((ArrayList)LayoutTransition.this.mListeners.clone()).iterator();
                    while (object.hasNext()) {
                        ((TransitionListener)object.next()).endTransition(LayoutTransition.this, viewGroup, view, 2);
                    }
                }
            }
        });
        this.currentAppearingAnimations.put(view, animator2);
        animator2.start();
    }

    private void runChangeTransition(ViewGroup viewGroup, View object, int n) {
        ObjectAnimator objectAnimator;
        Object object2;
        long l;
        if (n != 2) {
            if (n != 3) {
                if (n != 4) {
                    object2 = null;
                    objectAnimator = null;
                    l = 0L;
                } else {
                    object2 = this.mChangingAnim;
                    l = this.mChangingDuration;
                    objectAnimator = defaultChange;
                }
            } else {
                object2 = this.mChangingDisappearingAnim;
                l = this.mChangingDisappearingDuration;
                objectAnimator = defaultChangeOut;
            }
        } else {
            object2 = this.mChangingAppearingAnim;
            l = this.mChangingAppearingDuration;
            objectAnimator = defaultChangeIn;
        }
        if (object2 == null) {
            return;
        }
        this.staggerDelay = 0L;
        ViewTreeObserver viewTreeObserver = viewGroup.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return;
        }
        int n2 = viewGroup.getChildCount();
        for (int i = 0; i < n2; ++i) {
            View view = viewGroup.getChildAt(i);
            if (view == object) continue;
            this.setupChangeAnimation(viewGroup, n, (Animator)object2, l, view);
        }
        if (this.mAnimateParentHierarchy) {
            object = viewGroup;
            while (object != null) {
                object2 = ((View)object).getParent();
                if (object2 instanceof ViewGroup) {
                    this.setupChangeAnimation((ViewGroup)object2, n, objectAnimator, l, (View)object);
                    object = (ViewGroup)object2;
                    continue;
                }
                object = null;
            }
        }
        object = new CleanupCallback(this.layoutChangeListenerMap, viewGroup);
        viewTreeObserver.addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object);
        viewGroup.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object);
    }

    private void runDisappearingTransition(ViewGroup viewGroup, final View view) {
        Object object = this.currentAppearingAnimations.get(view);
        if (object != null) {
            ((Animator)object).cancel();
        }
        if ((object = this.mDisappearingAnim) == null) {
            if (this.hasListeners()) {
                object = ((ArrayList)this.mListeners.clone()).iterator();
                while (object.hasNext()) {
                    ((TransitionListener)object.next()).endTransition(this, viewGroup, view, 3);
                }
            }
            return;
        }
        object = ((Animator)object).clone();
        ((Animator)object).setStartDelay(this.mDisappearingDelay);
        ((Animator)object).setDuration(this.mDisappearingDuration);
        TimeInterpolator timeInterpolator = this.mDisappearingInterpolator;
        if (timeInterpolator != sDisappearingInterpolator) {
            ((Animator)object).setInterpolator(timeInterpolator);
        }
        ((Animator)object).setTarget(view);
        ((Animator)object).addListener(new AnimatorListenerAdapter(view.getAlpha(), viewGroup){
            final /* synthetic */ ViewGroup val$parent;
            final /* synthetic */ float val$preAnimAlpha;
            {
                this.val$preAnimAlpha = f;
                this.val$parent = viewGroup;
            }

            @Override
            public void onAnimationEnd(Animator object) {
                LayoutTransition.this.currentDisappearingAnimations.remove(view);
                view.setAlpha(this.val$preAnimAlpha);
                if (LayoutTransition.this.hasListeners()) {
                    object = ((ArrayList)LayoutTransition.this.mListeners.clone()).iterator();
                    while (object.hasNext()) {
                        ((TransitionListener)object.next()).endTransition(LayoutTransition.this, this.val$parent, view, 3);
                    }
                }
            }
        });
        if (object instanceof ObjectAnimator) {
            ((ObjectAnimator)object).setCurrentPlayTime(0L);
        }
        this.currentDisappearingAnimations.put(view, (Animator)object);
        ((Animator)object).start();
    }

    private void setupChangeAnimation(final ViewGroup viewGroup, final int n, final Animator animator2, final long l, final View view) {
        if (this.layoutChangeListenerMap.get(view) != null) {
            return;
        }
        if (view.getWidth() == 0 && view.getHeight() == 0) {
            return;
        }
        animator2 = animator2.clone();
        animator2.setTarget(view);
        animator2.setupStartValues();
        Object object = this.pendingAnimations.get(view);
        if (object != null) {
            ((Animator)object).cancel();
            this.pendingAnimations.remove(view);
        }
        this.pendingAnimations.put(view, animator2);
        object = ValueAnimator.ofFloat(0.0f, 1.0f).setDuration(l + 100L);
        ((Animator)object).addListener(new AnimatorListenerAdapter(){

            @Override
            public void onAnimationEnd(Animator animator2) {
                LayoutTransition.this.pendingAnimations.remove(view);
            }
        });
        ((ValueAnimator)object).start();
        object = new View.OnLayoutChangeListener(){

            @Override
            public void onLayoutChange(View object, int n9, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                animator2.setupEndValues();
                object = animator2;
                if (object instanceof ValueAnimator) {
                    n9 = 0;
                    object = ((ValueAnimator)object).getValues();
                    for (n2 = 0; n2 < ((PropertyValuesHolder[])object).length; ++n2) {
                        Cloneable cloneable = object[n2];
                        if (cloneable.mKeyframes instanceof KeyframeSet) {
                            cloneable = (KeyframeSet)cloneable.mKeyframes;
                            if (((KeyframeSet)cloneable).mFirstKeyframe != null && ((KeyframeSet)cloneable).mLastKeyframe != null && ((KeyframeSet)cloneable).mFirstKeyframe.getValue().equals(((KeyframeSet)cloneable).mLastKeyframe.getValue())) continue;
                            n9 = 1;
                            continue;
                        }
                        if (cloneable.mKeyframes.getValue(0.0f).equals(cloneable.mKeyframes.getValue(1.0f))) continue;
                        n9 = 1;
                    }
                    if (n9 == 0) {
                        return;
                    }
                }
                long l5 = 0L;
                n9 = n;
                if (n9 != 2) {
                    if (n9 != 3) {
                        if (n9 == 4) {
                            long l2 = LayoutTransition.this.mChangingDelay + LayoutTransition.this.staggerDelay;
                            object = LayoutTransition.this;
                            LayoutTransition.access$214((LayoutTransition)object, ((LayoutTransition)object).mChangingStagger);
                            l5 = l2;
                            if (LayoutTransition.this.mChangingInterpolator != sChangingInterpolator) {
                                animator2.setInterpolator(LayoutTransition.this.mChangingInterpolator);
                                l5 = l2;
                            }
                        }
                    } else {
                        long l3 = LayoutTransition.this.mChangingDisappearingDelay + LayoutTransition.this.staggerDelay;
                        object = LayoutTransition.this;
                        LayoutTransition.access$214((LayoutTransition)object, ((LayoutTransition)object).mChangingDisappearingStagger);
                        l5 = l3;
                        if (LayoutTransition.this.mChangingDisappearingInterpolator != sChangingDisappearingInterpolator) {
                            animator2.setInterpolator(LayoutTransition.this.mChangingDisappearingInterpolator);
                            l5 = l3;
                        }
                    }
                } else {
                    long l4 = LayoutTransition.this.mChangingAppearingDelay + LayoutTransition.this.staggerDelay;
                    object = LayoutTransition.this;
                    LayoutTransition.access$214((LayoutTransition)object, ((LayoutTransition)object).mChangingAppearingStagger);
                    l5 = l4;
                    if (LayoutTransition.this.mChangingAppearingInterpolator != sChangingAppearingInterpolator) {
                        animator2.setInterpolator(LayoutTransition.this.mChangingAppearingInterpolator);
                        l5 = l4;
                    }
                }
                animator2.setStartDelay(l5);
                animator2.setDuration(l);
                object = (Animator)LayoutTransition.this.currentChangingAnimations.get(view);
                if (object != null) {
                    ((Animator)object).cancel();
                }
                if ((Animator)LayoutTransition.this.pendingAnimations.get(view) != null) {
                    LayoutTransition.this.pendingAnimations.remove(view);
                }
                LayoutTransition.this.currentChangingAnimations.put(view, animator2);
                viewGroup.requestTransitionStart(LayoutTransition.this);
                view.removeOnLayoutChangeListener(this);
                LayoutTransition.this.layoutChangeListenerMap.remove(view);
            }
        };
        animator2.addListener(new AnimatorListenerAdapter((View.OnLayoutChangeListener)object){
            final /* synthetic */ View.OnLayoutChangeListener val$listener;
            {
                this.val$listener = onLayoutChangeListener;
            }

            @Override
            public void onAnimationCancel(Animator animator2) {
                view.removeOnLayoutChangeListener(this.val$listener);
                LayoutTransition.this.layoutChangeListenerMap.remove(view);
            }

            @Override
            public void onAnimationEnd(Animator object) {
                LayoutTransition.this.currentChangingAnimations.remove(view);
                if (LayoutTransition.this.hasListeners()) {
                    for (TransitionListener transitionListener : (ArrayList)LayoutTransition.this.mListeners.clone()) {
                        LayoutTransition layoutTransition = LayoutTransition.this;
                        object = viewGroup;
                        View view2 = view;
                        int n2 = n;
                        n2 = n2 == 2 ? 0 : (n2 == 3 ? 1 : 4);
                        transitionListener.endTransition(layoutTransition, (ViewGroup)object, view2, n2);
                    }
                }
            }

            @Override
            public void onAnimationStart(Animator object) {
                if (LayoutTransition.this.hasListeners()) {
                    for (TransitionListener transitionListener : (ArrayList)LayoutTransition.this.mListeners.clone()) {
                        LayoutTransition layoutTransition = LayoutTransition.this;
                        ViewGroup viewGroup2 = viewGroup;
                        View view2 = view;
                        int n2 = n;
                        n2 = n2 == 2 ? 0 : (n2 == 3 ? 1 : 4);
                        transitionListener.startTransition(layoutTransition, viewGroup2, view2, n2);
                    }
                }
            }
        });
        view.addOnLayoutChangeListener((View.OnLayoutChangeListener)object);
        this.layoutChangeListenerMap.put(view, (View.OnLayoutChangeListener)object);
    }

    public void addChild(ViewGroup viewGroup, View view) {
        this.addChild(viewGroup, view, true);
    }

    public void addTransitionListener(TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(transitionListener);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public void cancel() {
        Iterator iterator;
        if (this.currentChangingAnimations.size() > 0) {
            iterator = ((LinkedHashMap)this.currentChangingAnimations.clone()).values().iterator();
            while (iterator.hasNext()) {
                ((Animator)iterator.next()).cancel();
            }
            this.currentChangingAnimations.clear();
        }
        if (this.currentAppearingAnimations.size() > 0) {
            iterator = ((LinkedHashMap)this.currentAppearingAnimations.clone()).values().iterator();
            while (iterator.hasNext()) {
                ((Animator)iterator.next()).end();
            }
            this.currentAppearingAnimations.clear();
        }
        if (this.currentDisappearingAnimations.size() > 0) {
            iterator = ((LinkedHashMap)this.currentDisappearingAnimations.clone()).values().iterator();
            while (iterator.hasNext()) {
                ((Animator)iterator.next()).end();
            }
            this.currentDisappearingAnimations.clear();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public void cancel(int n) {
        block9 : {
            block6 : {
                block7 : {
                    block8 : {
                        if (n == 0 || n == 1) break block6;
                        if (n == 2) break block7;
                        if (n == 3) break block8;
                        if (n == 4) break block6;
                        break block9;
                    }
                    if (this.currentDisappearingAnimations.size() > 0) {
                        Iterator iterator = ((LinkedHashMap)this.currentDisappearingAnimations.clone()).values().iterator();
                        while (iterator.hasNext()) {
                            ((Animator)iterator.next()).end();
                        }
                        this.currentDisappearingAnimations.clear();
                    }
                    break block9;
                }
                if (this.currentAppearingAnimations.size() > 0) {
                    Iterator iterator = ((LinkedHashMap)this.currentAppearingAnimations.clone()).values().iterator();
                    while (iterator.hasNext()) {
                        ((Animator)iterator.next()).end();
                    }
                    this.currentAppearingAnimations.clear();
                }
                break block9;
            }
            if (this.currentChangingAnimations.size() > 0) {
                Iterator iterator = ((LinkedHashMap)this.currentChangingAnimations.clone()).values().iterator();
                while (iterator.hasNext()) {
                    ((Animator)iterator.next()).cancel();
                }
                this.currentChangingAnimations.clear();
            }
        }
    }

    public void disableTransitionType(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mTransitionTypes &= -17;
                        }
                    } else {
                        this.mTransitionTypes &= -3;
                    }
                } else {
                    this.mTransitionTypes &= -2;
                }
            } else {
                this.mTransitionTypes &= -9;
            }
        } else {
            this.mTransitionTypes &= -5;
        }
    }

    public void enableTransitionType(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mTransitionTypes |= 16;
                        }
                    } else {
                        this.mTransitionTypes |= 2;
                    }
                } else {
                    this.mTransitionTypes |= 1;
                }
            } else {
                this.mTransitionTypes |= 8;
            }
        } else {
            this.mTransitionTypes = 4 | this.mTransitionTypes;
        }
    }

    public void endChangingAnimations() {
        for (Animator animator2 : ((LinkedHashMap)this.currentChangingAnimations.clone()).values()) {
            animator2.start();
            animator2.end();
        }
        this.currentChangingAnimations.clear();
    }

    public Animator getAnimator(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return this.mChangingAnim;
                    }
                    return this.mDisappearingAnim;
                }
                return this.mAppearingAnim;
            }
            return this.mChangingDisappearingAnim;
        }
        return this.mChangingAppearingAnim;
    }

    public long getDuration(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return 0L;
                        }
                        return this.mChangingDuration;
                    }
                    return this.mDisappearingDuration;
                }
                return this.mAppearingDuration;
            }
            return this.mChangingDisappearingDuration;
        }
        return this.mChangingAppearingDuration;
    }

    public TimeInterpolator getInterpolator(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return this.mChangingInterpolator;
                    }
                    return this.mDisappearingInterpolator;
                }
                return this.mAppearingInterpolator;
            }
            return this.mChangingDisappearingInterpolator;
        }
        return this.mChangingAppearingInterpolator;
    }

    public long getStagger(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 4) {
                    return 0L;
                }
                return this.mChangingStagger;
            }
            return this.mChangingDisappearingStagger;
        }
        return this.mChangingAppearingStagger;
    }

    public long getStartDelay(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return 0L;
                        }
                        return this.mChangingDelay;
                    }
                    return this.mDisappearingDelay;
                }
                return this.mAppearingDelay;
            }
            return this.mChangingDisappearingDelay;
        }
        return this.mChangingAppearingDelay;
    }

    public List<TransitionListener> getTransitionListeners() {
        return this.mListeners;
    }

    @Deprecated
    public void hideChild(ViewGroup viewGroup, View view) {
        this.removeChild(viewGroup, view, true);
    }

    public void hideChild(ViewGroup viewGroup, View view, int n) {
        boolean bl = n == 8;
        this.removeChild(viewGroup, view, bl);
    }

    public boolean isChangingLayout() {
        boolean bl = this.currentChangingAnimations.size() > 0;
        return bl;
    }

    public boolean isRunning() {
        boolean bl = this.currentChangingAnimations.size() > 0 || this.currentAppearingAnimations.size() > 0 || this.currentDisappearingAnimations.size() > 0;
        return bl;
    }

    public boolean isTransitionTypeEnabled(int n) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return false;
                        }
                        if ((this.mTransitionTypes & 16) == 16) {
                            bl5 = true;
                        }
                        return bl5;
                    }
                    bl5 = bl;
                    if ((this.mTransitionTypes & 2) == 2) {
                        bl5 = true;
                    }
                    return bl5;
                }
                bl5 = bl2;
                if ((this.mTransitionTypes & 1) == 1) {
                    bl5 = true;
                }
                return bl5;
            }
            bl5 = bl3;
            if ((this.mTransitionTypes & 8) == 8) {
                bl5 = true;
            }
            return bl5;
        }
        bl5 = bl4;
        if ((this.mTransitionTypes & 4) == 4) {
            bl5 = true;
        }
        return bl5;
    }

    public void layoutChange(ViewGroup viewGroup) {
        if (viewGroup.getWindowVisibility() != 0) {
            return;
        }
        if ((this.mTransitionTypes & 16) == 16 && !this.isRunning()) {
            this.runChangeTransition(viewGroup, null, 4);
        }
    }

    public void removeChild(ViewGroup viewGroup, View view) {
        this.removeChild(viewGroup, view, true);
    }

    public void removeTransitionListener(TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return;
        }
        arrayList.remove(transitionListener);
    }

    public void setAnimateParentHierarchy(boolean bl) {
        this.mAnimateParentHierarchy = bl;
    }

    public void setAnimator(int n, Animator animator2) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mChangingAnim = animator2;
                        }
                    } else {
                        this.mDisappearingAnim = animator2;
                    }
                } else {
                    this.mAppearingAnim = animator2;
                }
            } else {
                this.mChangingDisappearingAnim = animator2;
            }
        } else {
            this.mChangingAppearingAnim = animator2;
        }
    }

    public void setDuration(int n, long l) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mChangingDuration = l;
                        }
                    } else {
                        this.mDisappearingDuration = l;
                    }
                } else {
                    this.mAppearingDuration = l;
                }
            } else {
                this.mChangingDisappearingDuration = l;
            }
        } else {
            this.mChangingAppearingDuration = l;
        }
    }

    public void setDuration(long l) {
        this.mChangingAppearingDuration = l;
        this.mChangingDisappearingDuration = l;
        this.mChangingDuration = l;
        this.mAppearingDuration = l;
        this.mDisappearingDuration = l;
    }

    public void setInterpolator(int n, TimeInterpolator timeInterpolator) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mChangingInterpolator = timeInterpolator;
                        }
                    } else {
                        this.mDisappearingInterpolator = timeInterpolator;
                    }
                } else {
                    this.mAppearingInterpolator = timeInterpolator;
                }
            } else {
                this.mChangingDisappearingInterpolator = timeInterpolator;
            }
        } else {
            this.mChangingAppearingInterpolator = timeInterpolator;
        }
    }

    public void setStagger(int n, long l) {
        if (n != 0) {
            if (n != 1) {
                if (n == 4) {
                    this.mChangingStagger = l;
                }
            } else {
                this.mChangingDisappearingStagger = l;
            }
        } else {
            this.mChangingAppearingStagger = l;
        }
    }

    public void setStartDelay(int n, long l) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n == 4) {
                            this.mChangingDelay = l;
                        }
                    } else {
                        this.mDisappearingDelay = l;
                    }
                } else {
                    this.mAppearingDelay = l;
                }
            } else {
                this.mChangingDisappearingDelay = l;
            }
        } else {
            this.mChangingAppearingDelay = l;
        }
    }

    @Deprecated
    public void showChild(ViewGroup viewGroup, View view) {
        this.addChild(viewGroup, view, true);
    }

    public void showChild(ViewGroup viewGroup, View view, int n) {
        boolean bl = n == 8;
        this.addChild(viewGroup, view, bl);
    }

    public void startChangingAnimations() {
        for (Animator animator2 : ((LinkedHashMap)this.currentChangingAnimations.clone()).values()) {
            if (animator2 instanceof ObjectAnimator) {
                ((ObjectAnimator)animator2).setCurrentPlayTime(0L);
            }
            animator2.start();
        }
    }

    private static final class CleanupCallback
    implements ViewTreeObserver.OnPreDrawListener,
    View.OnAttachStateChangeListener {
        final Map<View, View.OnLayoutChangeListener> layoutChangeListenerMap;
        final ViewGroup parent;

        CleanupCallback(Map<View, View.OnLayoutChangeListener> map, ViewGroup viewGroup) {
            this.layoutChangeListenerMap = map;
            this.parent = viewGroup;
        }

        private void cleanup() {
            this.parent.getViewTreeObserver().removeOnPreDrawListener(this);
            this.parent.removeOnAttachStateChangeListener(this);
            if (this.layoutChangeListenerMap.size() > 0) {
                for (View view : this.layoutChangeListenerMap.keySet()) {
                    view.removeOnLayoutChangeListener(this.layoutChangeListenerMap.get(view));
                }
                this.layoutChangeListenerMap.clear();
            }
        }

        @Override
        public boolean onPreDraw() {
            this.cleanup();
            return true;
        }

        @Override
        public void onViewAttachedToWindow(View view) {
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            this.cleanup();
        }
    }

    public static interface TransitionListener {
        public void endTransition(LayoutTransition var1, ViewGroup var2, View var3, int var4);

        public void startTransition(LayoutTransition var1, ViewGroup var2, View var3, int var4);
    }

}

