/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.SharedElementCallback;
import android.app._$$Lambda$ActivityTransitionCoordinator$_HMo0E_15AzCK9fwQ8WHzdz8ZIw;
import android.app._$$Lambda$ActivityTransitionCoordinator$fkaPvc8GCghP2GMwEgS_J5m_T_4;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionSet;
import android.transition.Visibility;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.GhostView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ImageView;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

abstract class ActivityTransitionCoordinator
extends ResultReceiver {
    protected static final String KEY_ELEVATION = "shared_element:elevation";
    protected static final String KEY_IMAGE_MATRIX = "shared_element:imageMatrix";
    static final String KEY_REMOTE_RECEIVER = "android:remoteReceiver";
    protected static final String KEY_SCALE_TYPE = "shared_element:scaleType";
    protected static final String KEY_SCREEN_BOTTOM = "shared_element:screenBottom";
    protected static final String KEY_SCREEN_LEFT = "shared_element:screenLeft";
    protected static final String KEY_SCREEN_RIGHT = "shared_element:screenRight";
    protected static final String KEY_SCREEN_TOP = "shared_element:screenTop";
    protected static final String KEY_SNAPSHOT = "shared_element:bitmap";
    protected static final String KEY_TRANSLATION_Z = "shared_element:translationZ";
    public static final int MSG_ALLOW_RETURN_TRANSITION = 108;
    public static final int MSG_CANCEL = 106;
    public static final int MSG_EXIT_TRANSITION_COMPLETE = 104;
    public static final int MSG_HIDE_SHARED_ELEMENTS = 101;
    public static final int MSG_SET_REMOTE_RECEIVER = 100;
    public static final int MSG_SHARED_ELEMENT_DESTINATION = 107;
    public static final int MSG_START_EXIT_TRANSITION = 105;
    public static final int MSG_TAKE_SHARED_ELEMENTS = 103;
    protected static final ImageView.ScaleType[] SCALE_TYPE_VALUES = ImageView.ScaleType.values();
    private static final String TAG = "ActivityTransitionCoordinator";
    protected final ArrayList<String> mAllSharedElementNames;
    private boolean mBackgroundAnimatorComplete;
    private final FixedEpicenterCallback mEpicenterCallback = new FixedEpicenterCallback();
    private ArrayList<GhostViewListeners> mGhostViewListeners = new ArrayList();
    protected final boolean mIsReturning;
    private boolean mIsStartingTransition;
    protected SharedElementCallback mListener;
    private ArrayMap<View, Float> mOriginalAlphas = new ArrayMap();
    private Runnable mPendingTransition;
    protected ResultReceiver mResultReceiver;
    protected final ArrayList<String> mSharedElementNames = new ArrayList();
    private ArrayList<Matrix> mSharedElementParentMatrices;
    private boolean mSharedElementTransitionComplete;
    protected final ArrayList<View> mSharedElements = new ArrayList();
    private ArrayList<View> mStrippedTransitioningViews = new ArrayList();
    protected ArrayList<View> mTransitioningViews = new ArrayList();
    private boolean mViewsTransitionComplete;
    private Window mWindow;

    public ActivityTransitionCoordinator(Window window, ArrayList<String> arrayList, SharedElementCallback sharedElementCallback, boolean bl) {
        super(new Handler());
        this.mWindow = window;
        this.mListener = sharedElementCallback;
        this.mAllSharedElementNames = arrayList;
        this.mIsReturning = bl;
    }

    private static void findIncludedViews(Transition transition2, ArrayList<View> arrayList, ArraySet<View> arraySet) {
        if (transition2 instanceof TransitionSet) {
            int n;
            TransitionSet transitionSet = (TransitionSet)transition2;
            ArrayList<View> arrayList2 = new ArrayList<View>();
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                View view = arrayList.get(n);
                if (!transition2.isValidTarget(view)) continue;
                arrayList2.add(view);
            }
            n2 = transitionSet.getTransitionCount();
            for (n = 0; n < n2; ++n) {
                ActivityTransitionCoordinator.findIncludedViews(transitionSet.getTransitionAt(n), arrayList2, arraySet);
            }
        } else {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                View view = arrayList.get(i);
                if (!transition2.isValidTarget(view)) continue;
                arraySet.add(view);
            }
        }
    }

    private static SharedElementOriginalState getOldSharedElementState(View view, String object, Bundle bundle) {
        SharedElementOriginalState sharedElementOriginalState = new SharedElementOriginalState();
        sharedElementOriginalState.mLeft = view.getLeft();
        sharedElementOriginalState.mTop = view.getTop();
        sharedElementOriginalState.mRight = view.getRight();
        sharedElementOriginalState.mBottom = view.getBottom();
        sharedElementOriginalState.mMeasuredWidth = view.getMeasuredWidth();
        sharedElementOriginalState.mMeasuredHeight = view.getMeasuredHeight();
        sharedElementOriginalState.mTranslationZ = view.getTranslationZ();
        sharedElementOriginalState.mElevation = view.getElevation();
        if (!(view instanceof ImageView)) {
            return sharedElementOriginalState;
        }
        if ((object = bundle.getBundle((String)object)) == null) {
            return sharedElementOriginalState;
        }
        if (((BaseBundle)object).getInt(KEY_SCALE_TYPE, -1) < 0) {
            return sharedElementOriginalState;
        }
        view = (ImageView)view;
        sharedElementOriginalState.mScaleType = ((ImageView)view).getScaleType();
        if (sharedElementOriginalState.mScaleType == ImageView.ScaleType.MATRIX) {
            sharedElementOriginalState.mMatrix = new Matrix(((ImageView)view).getImageMatrix());
        }
        return sharedElementOriginalState;
    }

    private void getSharedElementParentMatrix(View object, Matrix matrix) {
        int n = this.mSharedElementParentMatrices == null ? -1 : this.mSharedElements.indexOf(object);
        if (n < 0) {
            matrix.reset();
            object = ((View)object).getParent();
            if (object instanceof ViewGroup) {
                object = (ViewGroup)object;
                ((View)object).transformMatrixToLocal(matrix);
                matrix.postTranslate(((View)object).getScrollX(), ((View)object).getScrollY());
            }
        } else {
            matrix.set(this.mSharedElementParentMatrices.get(n));
        }
    }

    public static boolean isInTransitionGroup(ViewParent viewParent, ViewGroup viewGroup) {
        if (viewParent != viewGroup && viewParent instanceof ViewGroup) {
            if (((ViewGroup)(viewParent = (ViewGroup)viewParent)).isTransitionGroup()) {
                return true;
            }
            return ActivityTransitionCoordinator.isInTransitionGroup(((View)((Object)viewParent)).getParent(), viewGroup);
        }
        return false;
    }

    private static boolean isNested(View object, ArrayMap<String, View> arrayMap) {
        boolean bl;
        object = ((View)object).getParent();
        boolean bl2 = false;
        do {
            bl = bl2;
            if (!(object instanceof View)) break;
            if (arrayMap.containsValue(object = (View)object)) {
                bl = true;
                break;
            }
            object = ((View)object).getParent();
        } while (true);
        return bl;
    }

    protected static Transition mergeTransitions(Transition transition2, Transition transition3) {
        if (transition2 == null) {
            return transition3;
        }
        if (transition3 == null) {
            return transition2;
        }
        TransitionSet transitionSet = new TransitionSet();
        transitionSet.addTransition(transition2);
        transitionSet.addTransition(transition3);
        return transitionSet;
    }

    private static void noLayoutSuppressionForVisibilityTransitions(Transition transition2) {
        if (transition2 instanceof Visibility) {
            ((Visibility)transition2).setSuppressLayout(false);
        } else if (transition2 instanceof TransitionSet) {
            transition2 = (TransitionSet)transition2;
            int n = ((TransitionSet)transition2).getTransitionCount();
            for (int i = 0; i < n; ++i) {
                ActivityTransitionCoordinator.noLayoutSuppressionForVisibilityTransitions(((TransitionSet)transition2).getTransitionAt(i));
            }
        }
    }

    protected static void removeExcludedViews(Transition transition2, ArrayList<View> arrayList) {
        ArraySet<View> arraySet = new ArraySet<View>();
        ActivityTransitionCoordinator.findIncludedViews(transition2, arrayList, arraySet);
        arrayList.clear();
        arrayList.addAll(arraySet);
    }

    private static int scaleTypeToInt(ImageView.ScaleType scaleType) {
        ImageView.ScaleType[] arrscaleType;
        for (int i = 0; i < (arrscaleType = SCALE_TYPE_VALUES).length; ++i) {
            if (scaleType != arrscaleType[i]) continue;
            return i;
        }
        return -1;
    }

    private void setEpicenter(View view) {
        if (view == null) {
            this.mEpicenterCallback.setEpicenter(null);
        } else {
            Rect rect = new Rect();
            view.getBoundsOnScreen(rect);
            this.mEpicenterCallback.setEpicenter(rect);
        }
    }

    protected static void setOriginalSharedElementState(ArrayList<View> arrayList, ArrayList<SharedElementOriginalState> arrayList2) {
        for (int i = 0; i < arrayList2.size(); ++i) {
            View view = arrayList.get(i);
            SharedElementOriginalState sharedElementOriginalState = arrayList2.get(i);
            if (view instanceof ImageView && sharedElementOriginalState.mScaleType != null) {
                ImageView imageView = (ImageView)view;
                imageView.setScaleType(sharedElementOriginalState.mScaleType);
                if (sharedElementOriginalState.mScaleType == ImageView.ScaleType.MATRIX) {
                    imageView.setImageMatrix(sharedElementOriginalState.mMatrix);
                }
            }
            view.setElevation(sharedElementOriginalState.mElevation);
            view.setTranslationZ(sharedElementOriginalState.mTranslationZ);
            view.measure(View.MeasureSpec.makeMeasureSpec(sharedElementOriginalState.mMeasuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(sharedElementOriginalState.mMeasuredHeight, 1073741824));
            view.layout(sharedElementOriginalState.mLeft, sharedElementOriginalState.mTop, sharedElementOriginalState.mRight, sharedElementOriginalState.mBottom);
        }
    }

    private void setSharedElementMatrices() {
        int n = this.mSharedElements.size();
        if (n > 0) {
            this.mSharedElementParentMatrices = new ArrayList(n);
        }
        for (int i = 0; i < n; ++i) {
            ViewGroup viewGroup = (ViewGroup)this.mSharedElements.get(i).getParent();
            Matrix matrix = new Matrix();
            if (viewGroup != null) {
                viewGroup.transformMatrixToLocal(matrix);
                matrix.postTranslate(viewGroup.getScrollX(), viewGroup.getScrollY());
            }
            this.mSharedElementParentMatrices.add(matrix);
        }
    }

    private void setSharedElementState(View view, String object, Bundle object2, Matrix matrix, RectF rectF, int[] arrn) {
        int n;
        Bundle bundle = object2.getBundle((String)object);
        if (bundle == null) {
            return;
        }
        if (view instanceof ImageView && (n = bundle.getInt(KEY_SCALE_TYPE, -1)) >= 0) {
            object = (ImageView)view;
            object2 = SCALE_TYPE_VALUES[n];
            ((ImageView)object).setScaleType((ImageView.ScaleType)((Object)object2));
            if (object2 == ImageView.ScaleType.MATRIX) {
                matrix.setValues(bundle.getFloatArray(KEY_IMAGE_MATRIX));
                ((ImageView)object).setImageMatrix(matrix);
            }
        }
        view.setTranslationZ(bundle.getFloat(KEY_TRANSLATION_Z));
        view.setElevation(bundle.getFloat(KEY_ELEVATION));
        float f = bundle.getFloat(KEY_SCREEN_LEFT);
        float f2 = bundle.getFloat(KEY_SCREEN_TOP);
        float f3 = bundle.getFloat(KEY_SCREEN_RIGHT);
        float f4 = bundle.getFloat(KEY_SCREEN_BOTTOM);
        if (arrn != null) {
            f -= (float)arrn[0];
            f2 -= (float)arrn[1];
            f3 -= (float)arrn[0];
            f4 -= (float)arrn[1];
        } else {
            this.getSharedElementParentMatrix(view, matrix);
            rectF.set(f, f2, f3, f4);
            matrix.mapRect(rectF);
            f = rectF.left;
            f2 = rectF.top;
            view.getInverseMatrix().mapRect(rectF);
            f3 = rectF.width();
            f4 = rectF.height();
            view.setLeft(0);
            view.setTop(0);
            view.setRight(Math.round(f3));
            view.setBottom(Math.round(f4));
            rectF.set(0.0f, 0.0f, f3, f4);
            view.getMatrix().mapRect(rectF);
            f3 = (f -= rectF.left) + f3;
            f4 = (f2 -= rectF.top) + f4;
        }
        n = Math.round(f);
        int n2 = Math.round(f2);
        int n3 = Math.round(f3) - n;
        int n4 = Math.round(f4) - n2;
        view.measure(View.MeasureSpec.makeMeasureSpec(n3, 1073741824), View.MeasureSpec.makeMeasureSpec(n4, 1073741824));
        view.layout(n, n2, n + n3, n2 + n4);
    }

    private void setSharedElements(ArrayMap<String, View> arrayMap) {
        boolean bl = true;
        while (!arrayMap.isEmpty()) {
            for (int i = arrayMap.size() - 1; i >= 0; --i) {
                View view = arrayMap.valueAt(i);
                String string2 = arrayMap.keyAt(i);
                if (bl && (view == null || !view.isAttachedToWindow() || string2 == null)) {
                    arrayMap.removeAt(i);
                    continue;
                }
                if (ActivityTransitionCoordinator.isNested(view, arrayMap)) continue;
                this.mSharedElementNames.add(string2);
                this.mSharedElements.add(view);
                arrayMap.removeAt(i);
            }
            bl = false;
        }
    }

    private void showView(View view, boolean bl) {
        Float f = this.mOriginalAlphas.remove(view);
        if (f != null) {
            view.setAlpha(f.floatValue());
        }
        if (bl) {
            view.setTransitionAlpha(1.0f);
        }
    }

    private void startInputWhenTransitionsComplete() {
        if (this.mViewsTransitionComplete && this.mSharedElementTransitionComplete) {
            ViewParent viewParent = this.getDecor();
            if (viewParent != null && (viewParent = ((View)((Object)viewParent)).getViewRootImpl()) != null) {
                ((ViewRootImpl)viewParent).setPausedForTransition(false);
            }
            this.onTransitionsComplete();
        }
    }

    protected void backgroundAnimatorComplete() {
        this.mBackgroundAnimatorComplete = true;
    }

    protected boolean cancelPendingTransitions() {
        this.mPendingTransition = null;
        return this.mIsStartingTransition;
    }

    protected Bundle captureSharedElementState() {
        Bundle bundle = new Bundle();
        RectF rectF = new RectF();
        Matrix matrix = new Matrix();
        for (int i = 0; i < this.mSharedElements.size(); ++i) {
            this.captureSharedElementState(this.mSharedElements.get(i), this.mSharedElementNames.get(i), bundle, matrix, rectF);
        }
        return bundle;
    }

    protected void captureSharedElementState(View view, String string2, Bundle bundle, Matrix arrf, RectF rectF) {
        Bundle bundle2 = new Bundle();
        arrf.reset();
        view.transformMatrixToGlobal((Matrix)arrf);
        rectF.set(0.0f, 0.0f, view.getWidth(), view.getHeight());
        arrf.mapRect(rectF);
        bundle2.putFloat(KEY_SCREEN_LEFT, rectF.left);
        bundle2.putFloat(KEY_SCREEN_RIGHT, rectF.right);
        bundle2.putFloat(KEY_SCREEN_TOP, rectF.top);
        bundle2.putFloat(KEY_SCREEN_BOTTOM, rectF.bottom);
        bundle2.putFloat(KEY_TRANSLATION_Z, view.getTranslationZ());
        bundle2.putFloat(KEY_ELEVATION, view.getElevation());
        Parcelable parcelable = null;
        SharedElementCallback sharedElementCallback = this.mListener;
        if (sharedElementCallback != null) {
            parcelable = sharedElementCallback.onCaptureSharedElementSnapshot(view, (Matrix)arrf, rectF);
        }
        if (parcelable != null) {
            bundle2.putParcelable(KEY_SNAPSHOT, parcelable);
        }
        if (view instanceof ImageView) {
            view = (ImageView)view;
            bundle2.putInt(KEY_SCALE_TYPE, ActivityTransitionCoordinator.scaleTypeToInt(((ImageView)view).getScaleType()));
            if (((ImageView)view).getScaleType() == ImageView.ScaleType.MATRIX) {
                arrf = new float[9];
                ((ImageView)view).getImageMatrix().getValues(arrf);
                bundle2.putFloatArray(KEY_IMAGE_MATRIX, arrf);
            }
        }
        bundle.putBundle(string2, bundle2);
    }

    protected void clearState() {
        this.mWindow = null;
        this.mSharedElements.clear();
        this.mTransitioningViews = null;
        this.mStrippedTransitioningViews = null;
        this.mOriginalAlphas.clear();
        this.mResultReceiver = null;
        this.mPendingTransition = null;
        this.mListener = null;
        this.mSharedElementParentMatrices = null;
    }

    protected Transition configureTransition(Transition transition2, boolean bl) {
        Transition transition3 = transition2;
        if (transition2 != null) {
            transition2 = transition2.clone();
            transition2.setEpicenterCallback(this.mEpicenterCallback);
            transition3 = this.setTargets(transition2, bl);
        }
        ActivityTransitionCoordinator.noLayoutSuppressionForVisibilityTransitions(transition3);
        return transition3;
    }

    public ArrayList<View> copyMappedViews() {
        return new ArrayList<View>(this.mSharedElements);
    }

    protected ArrayList<View> createSnapshots(Bundle bundle, Collection<String> object) {
        int n = object.size();
        ArrayList<View> arrayList = new ArrayList<View>(n);
        if (n == 0) {
            return arrayList;
        }
        Context context = this.getWindow().getContext();
        int[] arrn = new int[2];
        Object object2 = this.getDecor();
        if (object2 != null) {
            ((View)object2).getLocationOnScreen(arrn);
        }
        object2 = new Matrix();
        Iterator<String> iterator = object.iterator();
        while (iterator.hasNext()) {
            String string2 = iterator.next();
            Object object3 = bundle.getBundle(string2);
            object = null;
            if (object3 != null && (object = (object = ((Bundle)object3).getParcelable(KEY_SNAPSHOT)) != null && (object3 = this.mListener) != null ? ((SharedElementCallback)object3).onCreateSnapshotView(context, (Parcelable)object) : null) != null) {
                this.setSharedElementState((View)object, string2, bundle, (Matrix)object2, null, arrn);
            }
            arrayList.add((View)object);
        }
        return arrayList;
    }

    public ArrayList<String> getAcceptedNames() {
        return this.mSharedElementNames;
    }

    public ViewGroup getDecor() {
        Object object = this.mWindow;
        object = object == null ? null : (ViewGroup)((Window)object).getDecorView();
        return object;
    }

    protected long getFadeDuration() {
        return this.getWindow().getTransitionBackgroundFadeDuration();
    }

    public ArrayList<String> getMappedNames() {
        ArrayList<String> arrayList = new ArrayList<String>(this.mSharedElements.size());
        for (int i = 0; i < this.mSharedElements.size(); ++i) {
            arrayList.add(this.mSharedElements.get(i).getTransitionName());
        }
        return arrayList;
    }

    protected abstract Transition getViewsTransition();

    protected Window getWindow() {
        return this.mWindow;
    }

    protected void hideViews(ArrayList<View> arrayList) {
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            View view = arrayList.get(i);
            if (!this.mOriginalAlphas.containsKey(view)) {
                this.mOriginalAlphas.put(view, Float.valueOf(view.getAlpha()));
            }
            view.setAlpha(0.0f);
        }
    }

    public boolean isTransitionRunning() {
        boolean bl = !(this.mViewsTransitionComplete && this.mSharedElementTransitionComplete && this.mBackgroundAnimatorComplete);
        return bl;
    }

    protected boolean isViewsTransitionComplete() {
        return this.mViewsTransitionComplete;
    }

    public /* synthetic */ void lambda$scheduleGhostVisibilityChange$1$ActivityTransitionCoordinator(int n) {
        this.setGhostVisibility(n);
    }

    public /* synthetic */ void lambda$scheduleSetSharedElementEnd$0$ActivityTransitionCoordinator(ArrayList arrayList) {
        this.notifySharedElementEnd(arrayList);
    }

    protected ArrayMap<String, View> mapSharedElements(ArrayList<String> object, ArrayList<View> arrayList) {
        ArrayMap<String, View> arrayMap = new ArrayMap<String, View>();
        if (object != null) {
            for (int i = 0; i < ((ArrayList)object).size(); ++i) {
                arrayMap.put((String)((ArrayList)object).get(i), arrayList.get(i));
            }
        } else {
            object = this.getDecor();
            if (object != null) {
                ((ViewGroup)object).findNamedViews(arrayMap);
            }
        }
        return arrayMap;
    }

    protected boolean moveSharedElementWithParent() {
        return true;
    }

    protected void moveSharedElementsFromOverlay() {
        int n;
        int n2 = this.mGhostViewListeners.size();
        for (n = 0; n < n2; ++n) {
            this.mGhostViewListeners.get(n).removeListener();
        }
        this.mGhostViewListeners.clear();
        Object object = this.mWindow;
        if (object != null && ((Window)object).getSharedElementsUseOverlay()) {
            object = this.getDecor();
            if (object != null) {
                ((ViewGroup)object).getOverlay();
                n2 = this.mSharedElements.size();
                for (n = 0; n < n2; ++n) {
                    GhostView.removeGhost(this.mSharedElements.get(n));
                }
            }
            return;
        }
    }

    protected void moveSharedElementsToOverlay() {
        Object object = this.mWindow;
        if (object != null && ((Window)object).getSharedElementsUseOverlay()) {
            this.setSharedElementMatrices();
            int n = this.mSharedElements.size();
            object = this.getDecor();
            if (object != null) {
                boolean bl = this.moveSharedElementWithParent();
                Matrix matrix = new Matrix();
                for (int i = 0; i < n; ++i) {
                    Object object2 = this.mSharedElements.get(i);
                    if (!((View)object2).isAttachedToWindow()) continue;
                    matrix.reset();
                    this.mSharedElementParentMatrices.get(i).invert(matrix);
                    GhostView.addGhost((View)object2, (ViewGroup)object, matrix);
                    ViewGroup viewGroup = (ViewGroup)((View)object2).getParent();
                    if (!bl || ActivityTransitionCoordinator.isInTransitionGroup(viewGroup, (ViewGroup)object)) continue;
                    object2 = new GhostViewListeners((View)object2, viewGroup, (ViewGroup)object);
                    viewGroup.getViewTreeObserver().addOnPreDrawListener((ViewTreeObserver.OnPreDrawListener)object2);
                    viewGroup.addOnAttachStateChangeListener((View.OnAttachStateChangeListener)object2);
                    this.mGhostViewListeners.add((GhostViewListeners)object2);
                }
            }
            return;
        }
    }

    protected void notifySharedElementEnd(ArrayList<View> arrayList) {
        SharedElementCallback sharedElementCallback = this.mListener;
        if (sharedElementCallback != null) {
            sharedElementCallback.onSharedElementEnd(this.mSharedElementNames, this.mSharedElements, arrayList);
        }
    }

    protected void onTransitionsComplete() {
    }

    protected void pauseInput() {
        ViewParent viewParent = this.getDecor();
        viewParent = viewParent == null ? null : viewParent.getViewRootImpl();
        if (viewParent != null) {
            ((ViewRootImpl)viewParent).setPausedForTransition(true);
        }
    }

    protected void scheduleGhostVisibilityChange(int n) {
        ViewGroup viewGroup = this.getDecor();
        if (viewGroup != null) {
            OneShotPreDrawListener.add(viewGroup, new _$$Lambda$ActivityTransitionCoordinator$_HMo0E_15AzCK9fwQ8WHzdz8ZIw(this, n));
        }
    }

    protected void scheduleSetSharedElementEnd(ArrayList<View> arrayList) {
        ViewGroup viewGroup = this.getDecor();
        if (viewGroup != null) {
            OneShotPreDrawListener.add(viewGroup, new _$$Lambda$ActivityTransitionCoordinator$fkaPvc8GCghP2GMwEgS_J5m_T_4(this, arrayList));
        }
    }

    protected void setEpicenter() {
        View view;
        View view2 = view = null;
        if (!this.mAllSharedElementNames.isEmpty()) {
            view2 = view;
            if (!this.mSharedElementNames.isEmpty()) {
                int n = this.mSharedElementNames.indexOf(this.mAllSharedElementNames.get(0));
                view2 = view;
                if (n >= 0) {
                    view2 = this.mSharedElements.get(n);
                }
            }
        }
        this.setEpicenter(view2);
    }

    protected void setGhostVisibility(int n) {
        int n2 = this.mSharedElements.size();
        for (int i = 0; i < n2; ++i) {
            GhostView ghostView = GhostView.getGhost(this.mSharedElements.get(i));
            if (ghostView == null) continue;
            ghostView.setVisibility(n);
        }
    }

    protected void setResultReceiver(ResultReceiver resultReceiver) {
        this.mResultReceiver = resultReceiver;
    }

    protected ArrayList<SharedElementOriginalState> setSharedElementState(Bundle object, ArrayList<View> arrayList) {
        ArrayList<SharedElementOriginalState> arrayList2 = new ArrayList<SharedElementOriginalState>();
        if (object != null) {
            Matrix matrix = new Matrix();
            RectF rectF = new RectF();
            int n = this.mSharedElements.size();
            for (int i = 0; i < n; ++i) {
                View view = this.mSharedElements.get(i);
                String string2 = this.mSharedElementNames.get(i);
                arrayList2.add(ActivityTransitionCoordinator.getOldSharedElementState(view, string2, (Bundle)object));
                this.setSharedElementState(view, string2, (Bundle)object, matrix, rectF, null);
            }
        }
        if ((object = this.mListener) != null) {
            ((SharedElementCallback)object).onSharedElementStart(this.mSharedElementNames, this.mSharedElements, arrayList);
        }
        return arrayList2;
    }

    protected Transition setTargets(Transition cloneable, boolean bl) {
        Cloneable cloneable2;
        if (cloneable != null && (!bl || (cloneable2 = this.mTransitioningViews) != null && !((ArrayList)cloneable2).isEmpty())) {
            int n;
            cloneable2 = new TransitionSet();
            ArrayList<View> arrayList = this.mTransitioningViews;
            if (arrayList != null) {
                for (n = arrayList.size() - 1; n >= 0; --n) {
                    arrayList = this.mTransitioningViews.get(n);
                    if (bl) {
                        ((TransitionSet)cloneable2).addTarget((View)((Object)arrayList));
                        continue;
                    }
                    ((TransitionSet)cloneable2).excludeTarget((View)((Object)arrayList), true);
                }
            }
            if ((arrayList = this.mStrippedTransitioningViews) != null) {
                for (n = arrayList.size() - 1; n >= 0; --n) {
                    ((TransitionSet)cloneable2).excludeTarget(this.mStrippedTransitioningViews.get(n), true);
                }
            }
            ((TransitionSet)cloneable2).addTransition((Transition)cloneable);
            cloneable = cloneable2;
            if (!bl) {
                arrayList = this.mTransitioningViews;
                cloneable = cloneable2;
                if (arrayList != null) {
                    cloneable = cloneable2;
                    if (!arrayList.isEmpty()) {
                        cloneable = new TransitionSet().addTransition((Transition)cloneable2);
                    }
                }
            }
            return cloneable;
        }
        return null;
    }

    protected void setTransitioningViewsVisiblity(int n, boolean bl) {
        ArrayList<View> arrayList = this.mTransitioningViews;
        int n2 = arrayList == null ? 0 : arrayList.size();
        for (int i = 0; i < n2; ++i) {
            arrayList = this.mTransitioningViews.get(i);
            if (bl) {
                ((View)((Object)arrayList)).setVisibility(n);
                continue;
            }
            ((View)((Object)arrayList)).setTransitionVisibility(n);
        }
    }

    protected void sharedElementTransitionComplete() {
        this.mSharedElementTransitionComplete = true;
        this.startInputWhenTransitionsComplete();
    }

    protected void showViews(ArrayList<View> arrayList, boolean bl) {
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            this.showView(arrayList.get(i), bl);
        }
    }

    protected void startTransition(Runnable runnable) {
        if (this.mIsStartingTransition) {
            this.mPendingTransition = runnable;
        } else {
            this.mIsStartingTransition = true;
            runnable.run();
        }
    }

    protected void stripOffscreenViews() {
        if (this.mTransitioningViews == null) {
            return;
        }
        Rect rect = new Rect();
        for (int i = this.mTransitioningViews.size() - 1; i >= 0; --i) {
            View view = this.mTransitioningViews.get(i);
            if (view.getGlobalVisibleRect(rect)) continue;
            this.mTransitioningViews.remove(i);
            this.mStrippedTransitioningViews.add(view);
        }
    }

    protected void transitionStarted() {
        this.mIsStartingTransition = false;
    }

    protected void viewsReady(ArrayMap<String, View> object) {
        ((ArrayMap)object).retainAll(this.mAllSharedElementNames);
        SharedElementCallback sharedElementCallback = this.mListener;
        if (sharedElementCallback != null) {
            sharedElementCallback.onMapSharedElements((List<String>)this.mAllSharedElementNames, (Map<String, View>)object);
        }
        this.setSharedElements((ArrayMap<String, View>)object);
        if (this.getViewsTransition() != null && this.mTransitioningViews != null) {
            object = this.getDecor();
            if (object != null) {
                ((ViewGroup)object).captureTransitioningViews(this.mTransitioningViews);
            }
            this.mTransitioningViews.removeAll(this.mSharedElements);
        }
        this.setEpicenter();
    }

    protected void viewsTransitionComplete() {
        this.mViewsTransitionComplete = true;
        this.startInputWhenTransitionsComplete();
    }

    protected class ContinueTransitionListener
    extends TransitionListenerAdapter {
        protected ContinueTransitionListener() {
        }

        @Override
        public void onTransitionEnd(Transition transition2) {
            transition2.removeListener(this);
        }

        @Override
        public void onTransitionStart(Transition object) {
            ActivityTransitionCoordinator.this.mIsStartingTransition = false;
            object = ActivityTransitionCoordinator.this.mPendingTransition;
            ActivityTransitionCoordinator.this.mPendingTransition = null;
            if (object != null) {
                ActivityTransitionCoordinator.this.startTransition((Runnable)object);
            }
        }
    }

    private static class FixedEpicenterCallback
    extends Transition.EpicenterCallback {
        private Rect mEpicenter;

        private FixedEpicenterCallback() {
        }

        @Override
        public Rect onGetEpicenter(Transition transition2) {
            return this.mEpicenter;
        }

        public void setEpicenter(Rect rect) {
            this.mEpicenter = rect;
        }
    }

    private static class GhostViewListeners
    implements ViewTreeObserver.OnPreDrawListener,
    View.OnAttachStateChangeListener {
        private ViewGroup mDecor;
        private Matrix mMatrix = new Matrix();
        private View mParent;
        private View mView;
        private ViewTreeObserver mViewTreeObserver;

        public GhostViewListeners(View view, View view2, ViewGroup viewGroup) {
            this.mView = view;
            this.mParent = view2;
            this.mDecor = viewGroup;
            this.mViewTreeObserver = view2.getViewTreeObserver();
        }

        public View getView() {
            return this.mView;
        }

        @Override
        public boolean onPreDraw() {
            GhostView ghostView = GhostView.getGhost(this.mView);
            if (ghostView != null && this.mView.isAttachedToWindow()) {
                GhostView.calculateMatrix(this.mView, this.mDecor, this.mMatrix);
                ghostView.setMatrix(this.mMatrix);
            } else {
                this.removeListener();
            }
            return true;
        }

        @Override
        public void onViewAttachedToWindow(View view) {
            this.mViewTreeObserver = view.getViewTreeObserver();
        }

        @Override
        public void onViewDetachedFromWindow(View view) {
            this.removeListener();
        }

        public void removeListener() {
            if (this.mViewTreeObserver.isAlive()) {
                this.mViewTreeObserver.removeOnPreDrawListener(this);
            } else {
                this.mParent.getViewTreeObserver().removeOnPreDrawListener(this);
            }
            this.mParent.removeOnAttachStateChangeListener(this);
        }
    }

    static class SharedElementOriginalState {
        int mBottom;
        float mElevation;
        int mLeft;
        Matrix mMatrix;
        int mMeasuredHeight;
        int mMeasuredWidth;
        int mRight;
        ImageView.ScaleType mScaleType;
        int mTop;
        float mTranslationZ;

        SharedElementOriginalState() {
        }
    }

}

