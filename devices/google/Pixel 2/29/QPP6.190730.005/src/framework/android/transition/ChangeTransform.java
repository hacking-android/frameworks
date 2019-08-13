/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.FloatArrayEvaluator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.transition.PathMotion;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.util.Property;
import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.android.internal.R;
import java.util.Map;

public class ChangeTransform
extends Transition {
    private static final Property<PathAnimatorMatrix, float[]> NON_TRANSLATIONS_PROPERTY;
    private static final String PROPNAME_INTERMEDIATE_MATRIX = "android:changeTransform:intermediateMatrix";
    private static final String PROPNAME_INTERMEDIATE_PARENT_MATRIX = "android:changeTransform:intermediateParentMatrix";
    private static final String PROPNAME_MATRIX = "android:changeTransform:matrix";
    private static final String PROPNAME_PARENT = "android:changeTransform:parent";
    private static final String PROPNAME_PARENT_MATRIX = "android:changeTransform:parentMatrix";
    private static final String PROPNAME_TRANSFORMS = "android:changeTransform:transforms";
    private static final String TAG = "ChangeTransform";
    private static final Property<PathAnimatorMatrix, PointF> TRANSLATIONS_PROPERTY;
    private static final String[] sTransitionProperties;
    private boolean mReparent = true;
    private Matrix mTempMatrix = new Matrix();
    private boolean mUseOverlay = true;

    static {
        sTransitionProperties = new String[]{PROPNAME_MATRIX, PROPNAME_TRANSFORMS, PROPNAME_PARENT_MATRIX};
        NON_TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, float[]>(float[].class, "nonTranslations"){

            @Override
            public float[] get(PathAnimatorMatrix pathAnimatorMatrix) {
                return null;
            }

            @Override
            public void set(PathAnimatorMatrix pathAnimatorMatrix, float[] arrf) {
                pathAnimatorMatrix.setValues(arrf);
            }
        };
        TRANSLATIONS_PROPERTY = new Property<PathAnimatorMatrix, PointF>(PointF.class, "translations"){

            @Override
            public PointF get(PathAnimatorMatrix pathAnimatorMatrix) {
                return null;
            }

            @Override
            public void set(PathAnimatorMatrix pathAnimatorMatrix, PointF pointF) {
                pathAnimatorMatrix.setTranslation(pointF);
            }
        };
    }

    public ChangeTransform() {
    }

    public ChangeTransform(Context object, AttributeSet attributeSet) {
        super((Context)object, attributeSet);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ChangeTransform);
        this.mUseOverlay = ((TypedArray)object).getBoolean(1, true);
        this.mReparent = ((TypedArray)object).getBoolean(0, true);
        ((TypedArray)object).recycle();
    }

    private void captureValues(TransitionValues transitionValues) {
        View view = transitionValues.view;
        if (view.getVisibility() == 8) {
            return;
        }
        transitionValues.values.put(PROPNAME_PARENT, view.getParent());
        Object object = new Transforms(view);
        transitionValues.values.put(PROPNAME_TRANSFORMS, object);
        object = view.getMatrix();
        object = object != null && !((Matrix)object).isIdentity() ? new Matrix((Matrix)object) : null;
        transitionValues.values.put(PROPNAME_MATRIX, object);
        if (this.mReparent) {
            object = new Matrix();
            ViewGroup viewGroup = (ViewGroup)view.getParent();
            viewGroup.transformMatrixToGlobal((Matrix)object);
            ((Matrix)object).preTranslate(-viewGroup.getScrollX(), -viewGroup.getScrollY());
            transitionValues.values.put(PROPNAME_PARENT_MATRIX, object);
            transitionValues.values.put(PROPNAME_INTERMEDIATE_MATRIX, view.getTag(16909490));
            transitionValues.values.put(PROPNAME_INTERMEDIATE_PARENT_MATRIX, view.getTag(16909215));
        }
    }

    private void createGhostView(ViewGroup object, TransitionValues transitionValues, TransitionValues transitionValues2) {
        View view = transitionValues2.view;
        Object object2 = new Matrix((Matrix)transitionValues2.values.get(PROPNAME_PARENT_MATRIX));
        ((View)object).transformMatrixToLocal((Matrix)object2);
        object2 = GhostView.addGhost(view, (ViewGroup)object, (Matrix)object2);
        object = this;
        while (((Transition)object).mParent != null) {
            object = ((Transition)object).mParent;
        }
        ((Transition)object).addListener(new GhostListener(view, transitionValues.view, (GhostView)object2));
        if (transitionValues.view != transitionValues2.view) {
            transitionValues.view.setTransitionAlpha(0.0f);
        }
        view.setTransitionAlpha(1.0f);
    }

    private ObjectAnimator createTransformAnimator(TransitionValues object, TransitionValues object2, final boolean bl) {
        object = (Matrix)((TransitionValues)object).values.get(PROPNAME_MATRIX);
        Object object3 = (Matrix)((TransitionValues)object2).values.get(PROPNAME_MATRIX);
        Object object4 = object;
        if (object == null) {
            object4 = Matrix.IDENTITY_MATRIX;
        }
        object = object3;
        if (object3 == null) {
            object = Matrix.IDENTITY_MATRIX;
        }
        if (((Matrix)object4).equals(object)) {
            return null;
        }
        object3 = (Transforms)((TransitionValues)object2).values.get(PROPNAME_TRANSFORMS);
        object2 = ((TransitionValues)object2).view;
        ChangeTransform.setIdentityTransforms((View)object2);
        Object object5 = new float[9];
        ((Matrix)object4).getValues((float[])object5);
        float[] arrf = new float[9];
        ((Matrix)object).getValues(arrf);
        object4 = new PathAnimatorMatrix((View)object2, (float[])object5);
        PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofObject(NON_TRANSLATIONS_PROPERTY, new FloatArrayEvaluator(new float[9]), object5, arrf);
        object5 = this.getPathMotion().getPath(object5[2], object5[5], arrf[2], arrf[5]);
        object5 = ObjectAnimator.ofPropertyValuesHolder(object4, propertyValuesHolder, PropertyValuesHolder.ofObject(TRANSLATIONS_PROPERTY, null, (Path)object5));
        object = new AnimatorListenerAdapter((Matrix)object, (View)object2, (Transforms)object3, (PathAnimatorMatrix)object4){
            private boolean mIsCanceled;
            private Matrix mTempMatrix = new Matrix();
            final /* synthetic */ Matrix val$finalEndMatrix;
            final /* synthetic */ PathAnimatorMatrix val$pathAnimatorMatrix;
            final /* synthetic */ Transforms val$transforms;
            final /* synthetic */ View val$view;
            {
                this.val$finalEndMatrix = matrix;
                this.val$view = view;
                this.val$transforms = transforms;
                this.val$pathAnimatorMatrix = pathAnimatorMatrix;
            }

            private void setCurrentMatrix(Matrix matrix) {
                this.mTempMatrix.set(matrix);
                this.val$view.setTagInternal(16909490, this.mTempMatrix);
                this.val$transforms.restore(this.val$view);
            }

            @Override
            public void onAnimationCancel(Animator animator2) {
                this.mIsCanceled = true;
            }

            @Override
            public void onAnimationEnd(Animator animator2) {
                if (!this.mIsCanceled) {
                    if (bl && ChangeTransform.this.mUseOverlay) {
                        this.setCurrentMatrix(this.val$finalEndMatrix);
                    } else {
                        this.val$view.setTagInternal(16909490, null);
                        this.val$view.setTagInternal(16909215, null);
                    }
                }
                this.val$view.setAnimationMatrix(null);
                this.val$transforms.restore(this.val$view);
            }

            @Override
            public void onAnimationPause(Animator animator2) {
                this.setCurrentMatrix(this.val$pathAnimatorMatrix.getMatrix());
            }

            @Override
            public void onAnimationResume(Animator animator2) {
                ChangeTransform.setIdentityTransforms(this.val$view);
            }
        };
        ((Animator)object5).addListener((Animator.AnimatorListener)object);
        ((Animator)object5).addPauseListener((Animator.AnimatorPauseListener)object);
        return object5;
    }

    private boolean parentsMatch(ViewGroup object, ViewGroup viewGroup) {
        boolean bl = false;
        boolean bl2 = this.isValidTarget((View)object);
        boolean bl3 = false;
        boolean bl4 = false;
        if (bl2 && this.isValidTarget(viewGroup)) {
            if ((object = this.getMatchedTransitionValues((View)object, true)) != null) {
                bl = bl4;
                if (viewGroup == ((TransitionValues)object).view) {
                    bl = true;
                }
            }
        } else {
            bl = bl3;
            if (object == viewGroup) {
                bl = true;
            }
        }
        return bl;
    }

    private static void setIdentityTransforms(View view) {
        ChangeTransform.setTransforms(view, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f);
    }

    private void setMatricesForParent(TransitionValues transitionValues, TransitionValues object) {
        Matrix matrix = (Matrix)((TransitionValues)object).values.get(PROPNAME_PARENT_MATRIX);
        ((TransitionValues)object).view.setTagInternal(16909215, matrix);
        Matrix matrix2 = this.mTempMatrix;
        matrix2.reset();
        matrix.invert(matrix2);
        matrix = (Matrix)transitionValues.values.get(PROPNAME_MATRIX);
        object = matrix;
        if (matrix == null) {
            object = new Matrix();
            transitionValues.values.put(PROPNAME_MATRIX, object);
        }
        ((Matrix)object).postConcat((Matrix)transitionValues.values.get(PROPNAME_PARENT_MATRIX));
        ((Matrix)object).postConcat(matrix2);
    }

    private static void setTransforms(View view, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        view.setTranslationZ(f3);
        view.setScaleX(f4);
        view.setScaleY(f5);
        view.setRotationX(f6);
        view.setRotationY(f7);
        view.setRotation(f8);
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
        if (transitionValues != null && transitionValues2 != null && transitionValues.values.containsKey(PROPNAME_PARENT) && transitionValues2.values.containsKey(PROPNAME_PARENT)) {
            ViewGroup viewGroup2 = (ViewGroup)transitionValues.values.get(PROPNAME_PARENT);
            Object object = (ViewGroup)transitionValues2.values.get(PROPNAME_PARENT);
            boolean bl = this.mReparent && !this.parentsMatch(viewGroup2, (ViewGroup)object);
            object = (Matrix)transitionValues.values.get(PROPNAME_INTERMEDIATE_MATRIX);
            if (object != null) {
                transitionValues.values.put(PROPNAME_MATRIX, object);
            }
            if ((object = (Matrix)transitionValues.values.get(PROPNAME_INTERMEDIATE_PARENT_MATRIX)) != null) {
                transitionValues.values.put(PROPNAME_PARENT_MATRIX, object);
            }
            if (bl) {
                this.setMatricesForParent(transitionValues, transitionValues2);
            }
            object = this.createTransformAnimator(transitionValues, transitionValues2, bl);
            if (bl && object != null && this.mUseOverlay) {
                this.createGhostView(viewGroup, transitionValues, transitionValues2);
            }
            return object;
        }
        return null;
    }

    public boolean getReparent() {
        return this.mReparent;
    }

    public boolean getReparentWithOverlay() {
        return this.mUseOverlay;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public void setReparent(boolean bl) {
        this.mReparent = bl;
    }

    public void setReparentWithOverlay(boolean bl) {
        this.mUseOverlay = bl;
    }

    private static class GhostListener
    extends TransitionListenerAdapter {
        private GhostView mGhostView;
        private View mStartView;
        private View mView;

        public GhostListener(View view, View view2, GhostView ghostView) {
            this.mView = view;
            this.mStartView = view2;
            this.mGhostView = ghostView;
        }

        @Override
        public void onTransitionEnd(Transition transition2) {
            transition2.removeListener(this);
            GhostView.removeGhost(this.mView);
            this.mView.setTagInternal(16909490, null);
            this.mView.setTagInternal(16909215, null);
            this.mStartView.setTransitionAlpha(1.0f);
        }

        @Override
        public void onTransitionPause(Transition transition2) {
            this.mGhostView.setVisibility(4);
        }

        @Override
        public void onTransitionResume(Transition transition2) {
            this.mGhostView.setVisibility(0);
        }
    }

    private static class PathAnimatorMatrix {
        private final Matrix mMatrix = new Matrix();
        private float mTranslationX;
        private float mTranslationY;
        private final float[] mValues;
        private final View mView;

        public PathAnimatorMatrix(View arrf, float[] arrf2) {
            this.mView = arrf;
            arrf = this.mValues = (float[])arrf2.clone();
            this.mTranslationX = arrf[2];
            this.mTranslationY = arrf[5];
            this.setAnimationMatrix();
        }

        private void setAnimationMatrix() {
            float[] arrf = this.mValues;
            arrf[2] = this.mTranslationX;
            arrf[5] = this.mTranslationY;
            this.mMatrix.setValues(arrf);
            this.mView.setAnimationMatrix(this.mMatrix);
        }

        public Matrix getMatrix() {
            return this.mMatrix;
        }

        public void setTranslation(PointF pointF) {
            this.mTranslationX = pointF.x;
            this.mTranslationY = pointF.y;
            this.setAnimationMatrix();
        }

        public void setValues(float[] arrf) {
            System.arraycopy(arrf, 0, this.mValues, 0, arrf.length);
            this.setAnimationMatrix();
        }
    }

    private static class Transforms {
        public final float rotationX;
        public final float rotationY;
        public final float rotationZ;
        public final float scaleX;
        public final float scaleY;
        public final float translationX;
        public final float translationY;
        public final float translationZ;

        public Transforms(View view) {
            this.translationX = view.getTranslationX();
            this.translationY = view.getTranslationY();
            this.translationZ = view.getTranslationZ();
            this.scaleX = view.getScaleX();
            this.scaleY = view.getScaleY();
            this.rotationX = view.getRotationX();
            this.rotationY = view.getRotationY();
            this.rotationZ = view.getRotation();
        }

        public boolean equals(Object object) {
            boolean bl = object instanceof Transforms;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Transforms)object;
            bl = bl2;
            if (((Transforms)object).translationX == this.translationX) {
                bl = bl2;
                if (((Transforms)object).translationY == this.translationY) {
                    bl = bl2;
                    if (((Transforms)object).translationZ == this.translationZ) {
                        bl = bl2;
                        if (((Transforms)object).scaleX == this.scaleX) {
                            bl = bl2;
                            if (((Transforms)object).scaleY == this.scaleY) {
                                bl = bl2;
                                if (((Transforms)object).rotationX == this.rotationX) {
                                    bl = bl2;
                                    if (((Transforms)object).rotationY == this.rotationY) {
                                        bl = bl2;
                                        if (((Transforms)object).rotationZ == this.rotationZ) {
                                            bl = true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return bl;
        }

        public void restore(View view) {
            ChangeTransform.setTransforms(view, this.translationX, this.translationY, this.translationZ, this.scaleX, this.scaleY, this.rotationX, this.rotationY, this.rotationZ);
        }
    }

}

