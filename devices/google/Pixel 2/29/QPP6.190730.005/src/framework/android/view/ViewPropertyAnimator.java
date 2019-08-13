/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.graphics.Paint;
import android.graphics.RenderNode;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ViewPropertyAnimator {
    static final int ALPHA = 2048;
    static final int NONE = 0;
    static final int ROTATION = 32;
    static final int ROTATION_X = 64;
    static final int ROTATION_Y = 128;
    static final int SCALE_X = 8;
    static final int SCALE_Y = 16;
    private static final int TRANSFORM_MASK = 2047;
    static final int TRANSLATION_X = 1;
    static final int TRANSLATION_Y = 2;
    static final int TRANSLATION_Z = 4;
    static final int X = 256;
    static final int Y = 512;
    static final int Z = 1024;
    private Runnable mAnimationStarter = new Runnable(){

        @Override
        public void run() {
            ViewPropertyAnimator.this.startAnimation();
        }
    };
    private HashMap<Animator, Runnable> mAnimatorCleanupMap;
    private AnimatorEventListener mAnimatorEventListener = new AnimatorEventListener();
    private HashMap<Animator, PropertyBundle> mAnimatorMap = new HashMap();
    private HashMap<Animator, Runnable> mAnimatorOnEndMap;
    private HashMap<Animator, Runnable> mAnimatorOnStartMap;
    private HashMap<Animator, Runnable> mAnimatorSetupMap;
    private long mDuration;
    private boolean mDurationSet = false;
    private TimeInterpolator mInterpolator;
    private boolean mInterpolatorSet = false;
    private Animator.AnimatorListener mListener = null;
    ArrayList<NameValuesHolder> mPendingAnimations = new ArrayList();
    private Runnable mPendingCleanupAction;
    private Runnable mPendingOnEndAction;
    private Runnable mPendingOnStartAction;
    private Runnable mPendingSetupAction;
    private long mStartDelay = 0L;
    private boolean mStartDelaySet = false;
    private ValueAnimator mTempValueAnimator;
    private ValueAnimator.AnimatorUpdateListener mUpdateListener = null;
    final View mView;

    ViewPropertyAnimator(View view) {
        this.mView = view;
        view.ensureTransformationInfo();
    }

    private void animateProperty(int n, float f) {
        float f2 = this.getValue(n);
        this.animatePropertyBy(n, f2, f - f2);
    }

    private void animatePropertyBy(int n, float f) {
        this.animatePropertyBy(n, this.getValue(n), f);
    }

    private void animatePropertyBy(int n, float f, float f2) {
        Object object;
        if (this.mAnimatorMap.size() > 0) {
            PropertyBundle propertyBundle;
            Object var4_4 = null;
            Iterator<Animator> iterator = this.mAnimatorMap.keySet().iterator();
            do {
                object = var4_4;
            } while (iterator.hasNext() && (!(propertyBundle = this.mAnimatorMap.get(object = iterator.next())).cancel(n) || propertyBundle.mPropertyMask != 0));
            if (object != null) {
                ((Animator)object).cancel();
            }
        }
        object = new NameValuesHolder(n, f, f2);
        this.mPendingAnimations.add((NameValuesHolder)object);
        this.mView.removeCallbacks(this.mAnimationStarter);
        this.mView.postOnAnimation(this.mAnimationStarter);
    }

    private float getValue(int n) {
        RenderNode renderNode = this.mView.mRenderNode;
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        if (n != 256) {
                                            if (n != 512) {
                                                if (n != 1024) {
                                                    if (n != 2048) {
                                                        return 0.0f;
                                                    }
                                                    return this.mView.getAlpha();
                                                }
                                                return renderNode.getElevation() + renderNode.getTranslationZ();
                                            }
                                            return (float)this.mView.mTop + renderNode.getTranslationY();
                                        }
                                        return (float)this.mView.mLeft + renderNode.getTranslationX();
                                    }
                                    return renderNode.getRotationY();
                                }
                                return renderNode.getRotationX();
                            }
                            return renderNode.getRotationZ();
                        }
                        return renderNode.getScaleY();
                    }
                    return renderNode.getScaleX();
                }
                return renderNode.getTranslationZ();
            }
            return renderNode.getTranslationY();
        }
        return renderNode.getTranslationX();
    }

    private void setValue(int n, float f) {
        RenderNode renderNode = this.mView.mRenderNode;
        if (n != 1) {
            if (n != 2) {
                if (n != 4) {
                    if (n != 8) {
                        if (n != 16) {
                            if (n != 32) {
                                if (n != 64) {
                                    if (n != 128) {
                                        if (n != 256) {
                                            if (n != 512) {
                                                if (n != 1024) {
                                                    if (n == 2048) {
                                                        this.mView.setAlphaInternal(f);
                                                        renderNode.setAlpha(f);
                                                    }
                                                } else {
                                                    renderNode.setTranslationZ(f - renderNode.getElevation());
                                                }
                                            } else {
                                                renderNode.setTranslationY(f - (float)this.mView.mTop);
                                            }
                                        } else {
                                            renderNode.setTranslationX(f - (float)this.mView.mLeft);
                                        }
                                    } else {
                                        renderNode.setRotationY(f);
                                    }
                                } else {
                                    renderNode.setRotationX(f);
                                }
                            } else {
                                renderNode.setRotationZ(f);
                            }
                        } else {
                            renderNode.setScaleY(f);
                        }
                    } else {
                        renderNode.setScaleX(f);
                    }
                } else {
                    renderNode.setTranslationZ(f);
                }
            } else {
                renderNode.setTranslationY(f);
            }
        } else {
            renderNode.setTranslationX(f);
        }
    }

    private void startAnimation() {
        this.mView.setHasTransientState(true);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1.0f);
        Object object = (ArrayList)this.mPendingAnimations.clone();
        this.mPendingAnimations.clear();
        int n = 0;
        int n2 = ((ArrayList)object).size();
        for (int i = 0; i < n2; ++i) {
            n |= ((NameValuesHolder)object.get((int)i)).mNameConstant;
        }
        this.mAnimatorMap.put(valueAnimator, new PropertyBundle(n, (ArrayList<NameValuesHolder>)object));
        object = this.mPendingSetupAction;
        if (object != null) {
            this.mAnimatorSetupMap.put(valueAnimator, (Runnable)object);
            this.mPendingSetupAction = null;
        }
        if ((object = this.mPendingCleanupAction) != null) {
            this.mAnimatorCleanupMap.put(valueAnimator, (Runnable)object);
            this.mPendingCleanupAction = null;
        }
        if ((object = this.mPendingOnStartAction) != null) {
            this.mAnimatorOnStartMap.put(valueAnimator, (Runnable)object);
            this.mPendingOnStartAction = null;
        }
        if ((object = this.mPendingOnEndAction) != null) {
            this.mAnimatorOnEndMap.put(valueAnimator, (Runnable)object);
            this.mPendingOnEndAction = null;
        }
        valueAnimator.addUpdateListener(this.mAnimatorEventListener);
        valueAnimator.addListener(this.mAnimatorEventListener);
        if (this.mStartDelaySet) {
            valueAnimator.setStartDelay(this.mStartDelay);
        }
        if (this.mDurationSet) {
            valueAnimator.setDuration(this.mDuration);
        }
        if (this.mInterpolatorSet) {
            valueAnimator.setInterpolator(this.mInterpolator);
        }
        valueAnimator.start();
    }

    public ViewPropertyAnimator alpha(float f) {
        this.animateProperty(2048, f);
        return this;
    }

    public ViewPropertyAnimator alphaBy(float f) {
        this.animatePropertyBy(2048, f);
        return this;
    }

    public void cancel() {
        if (this.mAnimatorMap.size() > 0) {
            Iterator iterator = ((HashMap)this.mAnimatorMap.clone()).keySet().iterator();
            while (iterator.hasNext()) {
                ((Animator)iterator.next()).cancel();
            }
        }
        this.mPendingAnimations.clear();
        this.mPendingSetupAction = null;
        this.mPendingCleanupAction = null;
        this.mPendingOnStartAction = null;
        this.mPendingOnEndAction = null;
        this.mView.removeCallbacks(this.mAnimationStarter);
    }

    public long getDuration() {
        if (this.mDurationSet) {
            return this.mDuration;
        }
        if (this.mTempValueAnimator == null) {
            this.mTempValueAnimator = new ValueAnimator();
        }
        return this.mTempValueAnimator.getDuration();
    }

    public TimeInterpolator getInterpolator() {
        if (this.mInterpolatorSet) {
            return this.mInterpolator;
        }
        if (this.mTempValueAnimator == null) {
            this.mTempValueAnimator = new ValueAnimator();
        }
        return this.mTempValueAnimator.getInterpolator();
    }

    Animator.AnimatorListener getListener() {
        return this.mListener;
    }

    public long getStartDelay() {
        if (this.mStartDelaySet) {
            return this.mStartDelay;
        }
        return 0L;
    }

    ValueAnimator.AnimatorUpdateListener getUpdateListener() {
        return this.mUpdateListener;
    }

    boolean hasActions() {
        boolean bl = this.mPendingSetupAction != null || this.mPendingCleanupAction != null || this.mPendingOnStartAction != null || this.mPendingOnEndAction != null;
        return bl;
    }

    public ViewPropertyAnimator rotation(float f) {
        this.animateProperty(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationBy(float f) {
        this.animatePropertyBy(32, f);
        return this;
    }

    public ViewPropertyAnimator rotationX(float f) {
        this.animateProperty(64, f);
        return this;
    }

    public ViewPropertyAnimator rotationXBy(float f) {
        this.animatePropertyBy(64, f);
        return this;
    }

    public ViewPropertyAnimator rotationY(float f) {
        this.animateProperty(128, f);
        return this;
    }

    public ViewPropertyAnimator rotationYBy(float f) {
        this.animatePropertyBy(128, f);
        return this;
    }

    public ViewPropertyAnimator scaleX(float f) {
        this.animateProperty(8, f);
        return this;
    }

    public ViewPropertyAnimator scaleXBy(float f) {
        this.animatePropertyBy(8, f);
        return this;
    }

    public ViewPropertyAnimator scaleY(float f) {
        this.animateProperty(16, f);
        return this;
    }

    public ViewPropertyAnimator scaleYBy(float f) {
        this.animatePropertyBy(16, f);
        return this;
    }

    public ViewPropertyAnimator setDuration(long l) {
        if (l >= 0L) {
            this.mDurationSet = true;
            this.mDuration = l;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Animators cannot have negative duration: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ViewPropertyAnimator setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolatorSet = true;
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public ViewPropertyAnimator setListener(Animator.AnimatorListener animatorListener) {
        this.mListener = animatorListener;
        return this;
    }

    public ViewPropertyAnimator setStartDelay(long l) {
        if (l >= 0L) {
            this.mStartDelaySet = true;
            this.mStartDelay = l;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Animators cannot have negative start delay: ");
        stringBuilder.append(l);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ViewPropertyAnimator setUpdateListener(ValueAnimator.AnimatorUpdateListener animatorUpdateListener) {
        this.mUpdateListener = animatorUpdateListener;
        return this;
    }

    public void start() {
        this.mView.removeCallbacks(this.mAnimationStarter);
        this.startAnimation();
    }

    public ViewPropertyAnimator translationX(float f) {
        this.animateProperty(1, f);
        return this;
    }

    public ViewPropertyAnimator translationXBy(float f) {
        this.animatePropertyBy(1, f);
        return this;
    }

    public ViewPropertyAnimator translationY(float f) {
        this.animateProperty(2, f);
        return this;
    }

    public ViewPropertyAnimator translationYBy(float f) {
        this.animatePropertyBy(2, f);
        return this;
    }

    public ViewPropertyAnimator translationZ(float f) {
        this.animateProperty(4, f);
        return this;
    }

    public ViewPropertyAnimator translationZBy(float f) {
        this.animatePropertyBy(4, f);
        return this;
    }

    public ViewPropertyAnimator withEndAction(Runnable runnable) {
        this.mPendingOnEndAction = runnable;
        if (runnable != null && this.mAnimatorOnEndMap == null) {
            this.mAnimatorOnEndMap = new HashMap();
        }
        return this;
    }

    public ViewPropertyAnimator withLayer() {
        this.mPendingSetupAction = new Runnable(){

            @Override
            public void run() {
                ViewPropertyAnimator.this.mView.setLayerType(2, null);
                if (ViewPropertyAnimator.this.mView.isAttachedToWindow()) {
                    ViewPropertyAnimator.this.mView.buildLayer();
                }
            }
        };
        this.mPendingCleanupAction = new Runnable(this.mView.getLayerType()){
            final /* synthetic */ int val$currentLayerType;
            {
                this.val$currentLayerType = n;
            }

            @Override
            public void run() {
                ViewPropertyAnimator.this.mView.setLayerType(this.val$currentLayerType, null);
            }
        };
        if (this.mAnimatorSetupMap == null) {
            this.mAnimatorSetupMap = new HashMap();
        }
        if (this.mAnimatorCleanupMap == null) {
            this.mAnimatorCleanupMap = new HashMap();
        }
        return this;
    }

    public ViewPropertyAnimator withStartAction(Runnable runnable) {
        this.mPendingOnStartAction = runnable;
        if (runnable != null && this.mAnimatorOnStartMap == null) {
            this.mAnimatorOnStartMap = new HashMap();
        }
        return this;
    }

    public ViewPropertyAnimator x(float f) {
        this.animateProperty(256, f);
        return this;
    }

    public ViewPropertyAnimator xBy(float f) {
        this.animatePropertyBy(256, f);
        return this;
    }

    public ViewPropertyAnimator y(float f) {
        this.animateProperty(512, f);
        return this;
    }

    public ViewPropertyAnimator yBy(float f) {
        this.animatePropertyBy(512, f);
        return this;
    }

    public ViewPropertyAnimator z(float f) {
        this.animateProperty(1024, f);
        return this;
    }

    public ViewPropertyAnimator zBy(float f) {
        this.animatePropertyBy(1024, f);
        return this;
    }

    private class AnimatorEventListener
    implements Animator.AnimatorListener,
    ValueAnimator.AnimatorUpdateListener {
        private AnimatorEventListener() {
        }

        @Override
        public void onAnimationCancel(Animator animator2) {
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationCancel(animator2);
            }
            if (ViewPropertyAnimator.this.mAnimatorOnEndMap != null) {
                ViewPropertyAnimator.this.mAnimatorOnEndMap.remove(animator2);
            }
        }

        @Override
        public void onAnimationEnd(Animator animator2) {
            Runnable runnable;
            ViewPropertyAnimator.this.mView.setHasTransientState(false);
            if (ViewPropertyAnimator.this.mAnimatorCleanupMap != null) {
                runnable = (Runnable)ViewPropertyAnimator.this.mAnimatorCleanupMap.get(animator2);
                if (runnable != null) {
                    runnable.run();
                }
                ViewPropertyAnimator.this.mAnimatorCleanupMap.remove(animator2);
            }
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationEnd(animator2);
            }
            if (ViewPropertyAnimator.this.mAnimatorOnEndMap != null) {
                runnable = (Runnable)ViewPropertyAnimator.this.mAnimatorOnEndMap.get(animator2);
                if (runnable != null) {
                    runnable.run();
                }
                ViewPropertyAnimator.this.mAnimatorOnEndMap.remove(animator2);
            }
            ViewPropertyAnimator.this.mAnimatorMap.remove(animator2);
        }

        @Override
        public void onAnimationRepeat(Animator animator2) {
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationRepeat(animator2);
            }
        }

        @Override
        public void onAnimationStart(Animator animator2) {
            Runnable runnable;
            if (ViewPropertyAnimator.this.mAnimatorSetupMap != null) {
                runnable = (Runnable)ViewPropertyAnimator.this.mAnimatorSetupMap.get(animator2);
                if (runnable != null) {
                    runnable.run();
                }
                ViewPropertyAnimator.this.mAnimatorSetupMap.remove(animator2);
            }
            if (ViewPropertyAnimator.this.mAnimatorOnStartMap != null) {
                runnable = (Runnable)ViewPropertyAnimator.this.mAnimatorOnStartMap.get(animator2);
                if (runnable != null) {
                    runnable.run();
                }
                ViewPropertyAnimator.this.mAnimatorOnStartMap.remove(animator2);
            }
            if (ViewPropertyAnimator.this.mListener != null) {
                ViewPropertyAnimator.this.mListener.onAnimationStart(animator2);
            }
        }

        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Object object = (PropertyBundle)ViewPropertyAnimator.this.mAnimatorMap.get(valueAnimator);
            if (object == null) {
                return;
            }
            boolean bl = ViewPropertyAnimator.this.mView.isHardwareAccelerated();
            if (!bl) {
                ViewPropertyAnimator.this.mView.invalidateParentCaches();
            }
            float f = valueAnimator.getAnimatedFraction();
            int n = ((PropertyBundle)object).mPropertyMask;
            if ((n & 2047) != 0) {
                ViewPropertyAnimator.this.mView.invalidateViewProperty(bl, false);
            }
            if ((object = ((PropertyBundle)object).mNameValuesHolder) != null) {
                int n2 = ((ArrayList)object).size();
                for (int i = 0; i < n2; ++i) {
                    NameValuesHolder nameValuesHolder = (NameValuesHolder)((ArrayList)object).get(i);
                    float f2 = nameValuesHolder.mFromValue;
                    float f3 = nameValuesHolder.mDeltaValue;
                    ViewPropertyAnimator.this.setValue(nameValuesHolder.mNameConstant, f2 + f3 * f);
                }
            }
            if ((n & 2047) != 0 && !bl) {
                object = ViewPropertyAnimator.this.mView;
                ((View)object).mPrivateFlags |= 32;
            }
            ViewPropertyAnimator.this.mView.invalidateViewProperty(false, false);
            if (ViewPropertyAnimator.this.mUpdateListener != null) {
                ViewPropertyAnimator.this.mUpdateListener.onAnimationUpdate(valueAnimator);
            }
        }
    }

    static class NameValuesHolder {
        float mDeltaValue;
        float mFromValue;
        int mNameConstant;

        NameValuesHolder(int n, float f, float f2) {
            this.mNameConstant = n;
            this.mFromValue = f;
            this.mDeltaValue = f2;
        }
    }

    private static class PropertyBundle {
        ArrayList<NameValuesHolder> mNameValuesHolder;
        int mPropertyMask;

        PropertyBundle(int n, ArrayList<NameValuesHolder> arrayList) {
            this.mPropertyMask = n;
            this.mNameValuesHolder = arrayList;
        }

        boolean cancel(int n) {
            ArrayList<NameValuesHolder> arrayList;
            if ((this.mPropertyMask & n) != 0 && (arrayList = this.mNameValuesHolder) != null) {
                int n2 = arrayList.size();
                for (int i = 0; i < n2; ++i) {
                    if (this.mNameValuesHolder.get((int)i).mNameConstant != n) continue;
                    this.mNameValuesHolder.remove(i);
                    this.mPropertyMask &= n;
                    return true;
                }
            }
            return false;
        }
    }

}

