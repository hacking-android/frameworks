/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.Rect;
import android.transition.PathMotion;
import android.transition.TransitionPropagation;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.transition.TransitionValuesMaps;
import android.util.ArrayMap;
import android.util.AttributeSet;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseLongArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowId;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public abstract class Transition
implements Cloneable {
    static final boolean DBG = false;
    private static final int[] DEFAULT_MATCH_ORDER = new int[]{2, 1, 3, 4};
    private static final String LOG_TAG = "Transition";
    private static final int MATCH_FIRST = 1;
    public static final int MATCH_ID = 3;
    private static final String MATCH_ID_STR = "id";
    public static final int MATCH_INSTANCE = 1;
    private static final String MATCH_INSTANCE_STR = "instance";
    public static final int MATCH_ITEM_ID = 4;
    private static final String MATCH_ITEM_ID_STR = "itemId";
    private static final int MATCH_LAST = 4;
    public static final int MATCH_NAME = 2;
    private static final String MATCH_NAME_STR = "name";
    private static final String MATCH_VIEW_NAME_STR = "viewName";
    private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion(){

        @Override
        public Path getPath(float f, float f2, float f3, float f4) {
            Path path = new Path();
            path.moveTo(f, f2);
            path.lineTo(f3, f4);
            return path;
        }
    };
    private static ThreadLocal<ArrayMap<Animator, AnimationInfo>> sRunningAnimators = new ThreadLocal();
    ArrayList<Animator> mAnimators = new ArrayList();
    boolean mCanRemoveViews = false;
    private ArrayList<Animator> mCurrentAnimators = new ArrayList();
    long mDuration = -1L;
    private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
    ArrayList<TransitionValues> mEndValuesList;
    private boolean mEnded = false;
    EpicenterCallback mEpicenterCallback;
    TimeInterpolator mInterpolator = null;
    ArrayList<TransitionListener> mListeners = null;
    int[] mMatchOrder = DEFAULT_MATCH_ORDER;
    private String mName = this.getClass().getName();
    ArrayMap<String, String> mNameOverrides;
    int mNumInstances = 0;
    TransitionSet mParent = null;
    PathMotion mPathMotion = STRAIGHT_PATH_MOTION;
    boolean mPaused = false;
    TransitionPropagation mPropagation;
    ViewGroup mSceneRoot = null;
    long mStartDelay = -1L;
    private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
    ArrayList<TransitionValues> mStartValuesList;
    ArrayList<View> mTargetChildExcludes = null;
    ArrayList<View> mTargetExcludes = null;
    ArrayList<Integer> mTargetIdChildExcludes = null;
    ArrayList<Integer> mTargetIdExcludes = null;
    ArrayList<Integer> mTargetIds = new ArrayList();
    ArrayList<String> mTargetNameExcludes = null;
    ArrayList<String> mTargetNames = null;
    ArrayList<Class> mTargetTypeChildExcludes = null;
    ArrayList<Class> mTargetTypeExcludes = null;
    ArrayList<Class> mTargetTypes = null;
    ArrayList<View> mTargets = new ArrayList();

    public Transition() {
    }

    public Transition(Context object, AttributeSet object2) {
        int n;
        object2 = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.Transition);
        long l = ((TypedArray)object2).getInt(1, -1);
        if (l >= 0L) {
            this.setDuration(l);
        }
        if ((l = (long)((TypedArray)object2).getInt(2, -1)) > 0L) {
            this.setStartDelay(l);
        }
        if ((n = ((TypedArray)object2).getResourceId(0, 0)) > 0) {
            this.setInterpolator(AnimationUtils.loadInterpolator((Context)object, n));
        }
        if ((object = ((TypedArray)object2).getString(3)) != null) {
            this.setMatchOrder(Transition.parseMatchOrder((String)object));
        }
        ((TypedArray)object2).recycle();
    }

    private void addUnmatched(ArrayMap<View, TransitionValues> object, ArrayMap<View, TransitionValues> arrayMap) {
        int n;
        for (n = 0; n < ((ArrayMap)object).size(); ++n) {
            TransitionValues transitionValues = ((ArrayMap)object).valueAt(n);
            if (!this.isValidTarget(transitionValues.view)) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(null);
        }
        for (n = 0; n < arrayMap.size(); ++n) {
            object = arrayMap.valueAt(n);
            if (!this.isValidTarget(((TransitionValues)object).view)) continue;
            this.mEndValuesList.add((TransitionValues)object);
            this.mStartValuesList.add(null);
        }
    }

    static void addViewValues(TransitionValuesMaps transitionValuesMaps, View view, TransitionValues object) {
        transitionValuesMaps.viewValues.put(view, (TransitionValues)object);
        int n = view.getId();
        if (n >= 0) {
            if (transitionValuesMaps.idValues.indexOfKey(n) >= 0) {
                transitionValuesMaps.idValues.put(n, null);
            } else {
                transitionValuesMaps.idValues.put(n, view);
            }
        }
        if ((object = view.getTransitionName()) != null) {
            if (transitionValuesMaps.nameValues.containsKey(object)) {
                transitionValuesMaps.nameValues.put((String)object, null);
            } else {
                transitionValuesMaps.nameValues.put((String)object, view);
            }
        }
        if (view.getParent() instanceof ListView && ((ListView)(object = (ListView)view.getParent())).getAdapter().hasStableIds()) {
            long l = ((AdapterView)object).getItemIdAtPosition(((AdapterView)object).getPositionForView(view));
            if (transitionValuesMaps.itemIdValues.indexOfKey(l) >= 0) {
                view = transitionValuesMaps.itemIdValues.get(l);
                if (view != null) {
                    view.setHasTransientState(false);
                    transitionValuesMaps.itemIdValues.put(l, null);
                }
            } else {
                view.setHasTransientState(true);
                transitionValuesMaps.itemIdValues.put(l, view);
            }
        }
    }

    private static boolean alreadyContains(int[] arrn, int n) {
        int n2 = arrn[n];
        for (int i = 0; i < n; ++i) {
            if (arrn[i] != n2) continue;
            return true;
        }
        return false;
    }

    private void captureHierarchy(View view, boolean bl) {
        int n;
        int n2;
        if (view == null) {
            return;
        }
        int n3 = view.getId();
        ArrayList<Object> arrayList = this.mTargetIdExcludes;
        if (arrayList != null && arrayList.contains(n3)) {
            return;
        }
        arrayList = this.mTargetExcludes;
        if (arrayList != null && arrayList.contains(view)) {
            return;
        }
        arrayList = this.mTargetTypeExcludes;
        if (arrayList != null) {
            n = arrayList.size();
            for (n2 = 0; n2 < n; ++n2) {
                if (!this.mTargetTypeExcludes.get(n2).isInstance(view)) continue;
                return;
            }
        }
        if (view.getParent() instanceof ViewGroup) {
            arrayList = new TransitionValues(view);
            if (bl) {
                this.captureStartValues((TransitionValues)((Object)arrayList));
            } else {
                this.captureEndValues((TransitionValues)((Object)arrayList));
            }
            ((TransitionValues)arrayList).targetedTransitions.add(this);
            this.capturePropagationValues((TransitionValues)((Object)arrayList));
            if (bl) {
                Transition.addViewValues(this.mStartValues, view, arrayList);
            } else {
                Transition.addViewValues(this.mEndValues, view, arrayList);
            }
        }
        if (view instanceof ViewGroup) {
            arrayList = this.mTargetIdChildExcludes;
            if (arrayList != null && arrayList.contains(n3)) {
                return;
            }
            arrayList = this.mTargetChildExcludes;
            if (arrayList != null && arrayList.contains(view)) {
                return;
            }
            arrayList = this.mTargetTypeChildExcludes;
            if (arrayList != null) {
                n = arrayList.size();
                for (n2 = 0; n2 < n; ++n2) {
                    if (!this.mTargetTypeChildExcludes.get(n2).isInstance(view)) continue;
                    return;
                }
            }
            view = (ViewGroup)view;
            for (n2 = 0; n2 < ((ViewGroup)view).getChildCount(); ++n2) {
                this.captureHierarchy(((ViewGroup)view).getChildAt(n2), bl);
            }
        }
    }

    private static <T> ArrayList<T> excludeObject(ArrayList<T> arrayList, T t, boolean bl) {
        ArrayList<T> arrayList2 = arrayList;
        if (t != null) {
            arrayList2 = bl ? ArrayListManager.add(arrayList, t) : ArrayListManager.remove(arrayList, t);
        }
        return arrayList2;
    }

    @UnsupportedAppUsage
    private static ArrayMap<Animator, AnimationInfo> getRunningAnimators() {
        ArrayMap<Animator, AnimationInfo> arrayMap;
        ArrayMap<Animator, AnimationInfo> arrayMap2 = arrayMap = sRunningAnimators.get();
        if (arrayMap == null) {
            arrayMap2 = new ArrayMap();
            sRunningAnimators.set(arrayMap2);
        }
        return arrayMap2;
    }

    private static boolean isValidMatch(int n) {
        boolean bl = true;
        if (n < 1 || n > 4) {
            bl = false;
        }
        return bl;
    }

    private static boolean isValueChanged(TransitionValues object, TransitionValues object2, String string2) {
        if (((TransitionValues)object).values.containsKey(string2) != ((TransitionValues)object2).values.containsKey(string2)) {
            return false;
        }
        object = ((TransitionValues)object).values.get(string2);
        object2 = ((TransitionValues)object2).values.get(string2);
        boolean bl = object == null && object2 == null ? false : (object != null && object2 != null ? object.equals(object2) ^ true : true);
        return bl;
    }

    private void matchIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, SparseArray<View> sparseArray, SparseArray<View> sparseArray2) {
        int n = sparseArray.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = sparseArray.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = sparseArray2.get(sparseArray.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = arrayMap.get(view2);
            TransitionValues transitionValues2 = arrayMap2.get(view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove(view2);
            arrayMap2.remove(view);
        }
    }

    private void matchInstances(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2) {
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            Object object = arrayMap.keyAt(i);
            if (object == null || !this.isValidTarget((View)object) || (object = arrayMap2.remove(object)) == null || !this.isValidTarget(((TransitionValues)object).view)) continue;
            TransitionValues transitionValues = arrayMap.removeAt(i);
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add((TransitionValues)object);
        }
    }

    private void matchItemIds(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, LongSparseArray<View> longSparseArray, LongSparseArray<View> longSparseArray2) {
        int n = longSparseArray.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = longSparseArray.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = longSparseArray2.get(longSparseArray.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = arrayMap.get(view2);
            TransitionValues transitionValues2 = arrayMap2.get(view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove(view2);
            arrayMap2.remove(view);
        }
    }

    private void matchNames(ArrayMap<View, TransitionValues> arrayMap, ArrayMap<View, TransitionValues> arrayMap2, ArrayMap<String, View> arrayMap3, ArrayMap<String, View> arrayMap4) {
        int n = arrayMap3.size();
        for (int i = 0; i < n; ++i) {
            View view;
            View view2 = arrayMap3.valueAt(i);
            if (view2 == null || !this.isValidTarget(view2) || (view = arrayMap4.get(arrayMap3.keyAt(i))) == null || !this.isValidTarget(view)) continue;
            TransitionValues transitionValues = arrayMap.get(view2);
            TransitionValues transitionValues2 = arrayMap2.get(view);
            if (transitionValues == null || transitionValues2 == null) continue;
            this.mStartValuesList.add(transitionValues);
            this.mEndValuesList.add(transitionValues2);
            arrayMap.remove(view2);
            arrayMap2.remove(view);
        }
    }

    private void matchStartAndEnd(TransitionValuesMaps transitionValuesMaps, TransitionValuesMaps transitionValuesMaps2) {
        int[] arrn;
        ArrayMap<View, TransitionValues> arrayMap = new ArrayMap<View, TransitionValues>(transitionValuesMaps.viewValues);
        ArrayMap<View, TransitionValues> arrayMap2 = new ArrayMap<View, TransitionValues>(transitionValuesMaps2.viewValues);
        for (int i = 0; i < (arrn = this.mMatchOrder).length; ++i) {
            int n = arrn[i];
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) continue;
                        this.matchItemIds(arrayMap, arrayMap2, transitionValuesMaps.itemIdValues, transitionValuesMaps2.itemIdValues);
                        continue;
                    }
                    this.matchIds(arrayMap, arrayMap2, transitionValuesMaps.idValues, transitionValuesMaps2.idValues);
                    continue;
                }
                this.matchNames(arrayMap, arrayMap2, transitionValuesMaps.nameValues, transitionValuesMaps2.nameValues);
                continue;
            }
            this.matchInstances(arrayMap, arrayMap2);
        }
        this.addUnmatched(arrayMap, arrayMap2);
    }

    private static int[] parseMatchOrder(String object) {
        StringTokenizer stringTokenizer = new StringTokenizer((String)object, ",");
        object = new int[stringTokenizer.countTokens()];
        int n = 0;
        while (stringTokenizer.hasMoreTokens()) {
            int[] arrn;
            block9 : {
                block4 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                block5 : {
                                    block3 : {
                                        arrn = stringTokenizer.nextToken().trim();
                                        if (!MATCH_ID_STR.equalsIgnoreCase((String)arrn)) break block3;
                                        object[n] = 3;
                                        break block4;
                                    }
                                    if (!MATCH_INSTANCE_STR.equalsIgnoreCase((String)arrn)) break block5;
                                    object[n] = 1;
                                    break block4;
                                }
                                if (!MATCH_NAME_STR.equalsIgnoreCase((String)arrn)) break block6;
                                object[n] = 2;
                                break block4;
                            }
                            if (!MATCH_VIEW_NAME_STR.equalsIgnoreCase((String)arrn)) break block7;
                            object[n] = 2;
                            break block4;
                        }
                        if (!MATCH_ITEM_ID_STR.equalsIgnoreCase((String)arrn)) break block8;
                        object[n] = 4;
                        break block4;
                    }
                    if (!arrn.isEmpty()) break block9;
                    arrn = new int[((int[])object).length - 1];
                    System.arraycopy(object, 0, arrn, 0, n);
                    object = arrn;
                    --n;
                }
                ++n;
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unknown match type in matchOrder: '");
            ((StringBuilder)object).append((String)arrn);
            ((StringBuilder)object).append("'");
            throw new InflateException(((StringBuilder)object).toString());
        }
        return object;
    }

    private void runAnimator(Animator animator2, final ArrayMap<Animator, AnimationInfo> arrayMap) {
        if (animator2 != null) {
            animator2.addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2) {
                    arrayMap.remove(animator2);
                    Transition.this.mCurrentAnimators.remove(animator2);
                }

                @Override
                public void onAnimationStart(Animator animator2) {
                    Transition.this.mCurrentAnimators.add(animator2);
                }
            });
            this.animate(animator2);
        }
    }

    public Transition addListener(TransitionListener transitionListener) {
        if (this.mListeners == null) {
            this.mListeners = new ArrayList();
        }
        this.mListeners.add(transitionListener);
        return this;
    }

    public Transition addTarget(int n) {
        if (n > 0) {
            this.mTargetIds.add(n);
        }
        return this;
    }

    public Transition addTarget(View view) {
        this.mTargets.add(view);
        return this;
    }

    public Transition addTarget(Class class_) {
        if (class_ != null) {
            if (this.mTargetTypes == null) {
                this.mTargetTypes = new ArrayList();
            }
            this.mTargetTypes.add(class_);
        }
        return this;
    }

    public Transition addTarget(String string2) {
        if (string2 != null) {
            if (this.mTargetNames == null) {
                this.mTargetNames = new ArrayList();
            }
            this.mTargetNames.add(string2);
        }
        return this;
    }

    protected void animate(Animator animator2) {
        if (animator2 == null) {
            this.end();
        } else {
            if (this.getDuration() >= 0L) {
                animator2.setDuration(this.getDuration());
            }
            if (this.getStartDelay() >= 0L) {
                animator2.setStartDelay(this.getStartDelay() + animator2.getStartDelay());
            }
            if (this.getInterpolator() != null) {
                animator2.setInterpolator(this.getInterpolator());
            }
            animator2.addListener(new AnimatorListenerAdapter(){

                @Override
                public void onAnimationEnd(Animator animator2) {
                    Transition.this.end();
                    animator2.removeListener(this);
                }
            });
            animator2.start();
        }
    }

    public boolean canRemoveViews() {
        return this.mCanRemoveViews;
    }

    @UnsupportedAppUsage
    protected void cancel() {
        int n;
        for (n = this.mCurrentAnimators.size() - 1; n >= 0; --n) {
            this.mCurrentAnimators.get(n).cancel();
        }
        ArrayList arrayList = this.mListeners;
        if (arrayList != null && arrayList.size() > 0) {
            arrayList = (ArrayList)this.mListeners.clone();
            int n2 = arrayList.size();
            for (n = 0; n < n2; ++n) {
                ((TransitionListener)arrayList.get(n)).onTransitionCancel(this);
            }
        }
    }

    public abstract void captureEndValues(TransitionValues var1);

    void capturePropagationValues(TransitionValues transitionValues) {
        if (this.mPropagation != null && !transitionValues.values.isEmpty()) {
            boolean bl;
            String[] arrstring = this.mPropagation.getPropagationProperties();
            if (arrstring == null) {
                return;
            }
            boolean bl2 = true;
            int n = 0;
            do {
                bl = bl2;
                if (n >= arrstring.length) break;
                if (!transitionValues.values.containsKey(arrstring[n])) {
                    bl = false;
                    break;
                }
                ++n;
            } while (true);
            if (!bl) {
                this.mPropagation.captureValues(transitionValues);
            }
        }
    }

    public abstract void captureStartValues(TransitionValues var1);

    void captureValues(ViewGroup object, boolean bl) {
        Object object2;
        Object object3;
        int n;
        this.clearValues(bl);
        if (this.mTargetIds.size() <= 0 && this.mTargets.size() <= 0 || (object3 = this.mTargetNames) != null && !((ArrayList)object3).isEmpty() || (object3 = this.mTargetTypes) != null && !((ArrayList)object3).isEmpty()) {
            this.captureHierarchy((View)object, bl);
        } else {
            for (n = 0; n < this.mTargetIds.size(); ++n) {
                object3 = ((View)object).findViewById(this.mTargetIds.get(n));
                if (object3 == null) continue;
                object2 = new TransitionValues((View)object3);
                if (bl) {
                    this.captureStartValues((TransitionValues)object2);
                } else {
                    this.captureEndValues((TransitionValues)object2);
                }
                ((TransitionValues)object2).targetedTransitions.add(this);
                this.capturePropagationValues((TransitionValues)object2);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, (View)object3, (TransitionValues)object2);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, (View)object3, (TransitionValues)object2);
            }
            for (n = 0; n < this.mTargets.size(); ++n) {
                object = this.mTargets.get(n);
                object3 = new TransitionValues((View)object);
                if (bl) {
                    this.captureStartValues((TransitionValues)object3);
                } else {
                    this.captureEndValues((TransitionValues)object3);
                }
                ((TransitionValues)object3).targetedTransitions.add(this);
                this.capturePropagationValues((TransitionValues)object3);
                if (bl) {
                    Transition.addViewValues(this.mStartValues, (View)object, (TransitionValues)object3);
                    continue;
                }
                Transition.addViewValues(this.mEndValues, (View)object, (TransitionValues)object3);
            }
        }
        if (!bl && (object = this.mNameOverrides) != null) {
            int n2 = ((ArrayMap)object).size();
            object = new ArrayList<E>(n2);
            for (n = 0; n < n2; ++n) {
                object3 = this.mNameOverrides.keyAt(n);
                ((ArrayList)object).add(this.mStartValues.nameValues.remove(object3));
            }
            for (n = 0; n < n2; ++n) {
                object2 = (View)((ArrayList)object).get(n);
                if (object2 == null) continue;
                object3 = this.mNameOverrides.valueAt(n);
                this.mStartValues.nameValues.put((String)object3, (View)object2);
            }
        }
    }

    void clearValues(boolean bl) {
        if (bl) {
            this.mStartValues.viewValues.clear();
            this.mStartValues.idValues.clear();
            this.mStartValues.itemIdValues.clear();
            this.mStartValues.nameValues.clear();
            this.mStartValuesList = null;
        } else {
            this.mEndValues.viewValues.clear();
            this.mEndValues.idValues.clear();
            this.mEndValues.itemIdValues.clear();
            this.mEndValues.nameValues.clear();
            this.mEndValuesList = null;
        }
    }

    public Transition clone() {
        Transition transition2;
        Transition transition3 = null;
        transition3 = transition2 = (Transition)super.clone();
        transition3 = transition2;
        Object object = new ArrayList();
        transition3 = transition2;
        transition2.mAnimators = object;
        transition3 = transition2;
        transition3 = transition2;
        object = new TransitionValuesMaps();
        transition3 = transition2;
        transition2.mStartValues = object;
        transition3 = transition2;
        transition3 = transition2;
        object = new TransitionValuesMaps();
        transition3 = transition2;
        transition2.mEndValues = object;
        transition3 = transition2;
        transition2.mStartValuesList = null;
        transition3 = transition2;
        try {
            transition2.mEndValuesList = null;
            transition3 = transition2;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            // empty catch block
        }
        return transition3;
    }

    public Animator createAnimator(ViewGroup viewGroup, TransitionValues transitionValues, TransitionValues transitionValues2) {
        return null;
    }

    protected void createAnimators(ViewGroup object, TransitionValuesMaps object2, TransitionValuesMaps transitionValuesMaps, ArrayList<TransitionValues> arrayList, ArrayList<TransitionValues> arrayList2) {
        int n;
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        long l = Long.MAX_VALUE;
        int n2 = this.mAnimators.size();
        SparseLongArray sparseLongArray = new SparseLongArray();
        int n3 = arrayList.size();
        for (n = 0; n < n3; ++n) {
            Object object3;
            Object object4;
            Object object5;
            TransitionValues transitionValues = arrayList.get(n);
            TransitionValues transitionValues2 = arrayList2.get(n);
            if (transitionValues != null && !transitionValues.targetedTransitions.contains(this)) {
                transitionValues = null;
            }
            if (transitionValues2 != null && !transitionValues2.targetedTransitions.contains(this)) {
                transitionValues2 = null;
            }
            if (transitionValues == null && transitionValues2 == null) continue;
            int n4 = transitionValues != null && transitionValues2 != null && !this.isTransitionRequired(transitionValues, transitionValues2) ? 0 : 1;
            if (n4 == 0 || (object2 = this.createAnimator((ViewGroup)object, transitionValues, transitionValues2)) == null) continue;
            if (transitionValues2 != null) {
                block14 : {
                    object3 = transitionValues2.view;
                    Object object6 = this.getTransitionProperties();
                    if (object6 != null && ((String[])object6).length > 0) {
                        object4 = new TransitionValues((View)object3);
                        object5 = transitionValuesMaps.viewValues.get(object3);
                        if (object5 != null) {
                            for (n4 = 0; n4 < ((String[])object6).length; ++n4) {
                                ((TransitionValues)object4).values.put(object6[n4], object5.values.get(object6[n4]));
                            }
                        }
                        int n5 = arrayMap.size();
                        object5 = object6;
                        for (n4 = 0; n4 < n5; ++n4) {
                            object6 = arrayMap.get(arrayMap.keyAt(n4));
                            if (object6.values == null || object6.view != object3) continue;
                            if (object6.name != null || this.getName() != null) {
                                if (!object6.name.equals(this.getName())) continue;
                            }
                            if (!object6.values.equals(object4)) continue;
                            object2 = null;
                            object5 = object4;
                            break block14;
                        }
                        object5 = object4;
                    } else {
                        object5 = null;
                    }
                }
                object4 = object5;
                object5 = object3;
                object3 = object4;
            } else {
                object3 = null;
                object5 = transitionValues != null ? transitionValues.view : null;
            }
            if (object2 == null) continue;
            object4 = this.mPropagation;
            if (object4 != null) {
                long l2 = ((TransitionPropagation)object4).getStartDelay((ViewGroup)object, this, transitionValues, transitionValues2);
                sparseLongArray.put(this.mAnimators.size(), l2);
                l = Math.min(l2, l);
            }
            arrayMap.put((Animator)object2, new AnimationInfo((View)object5, this.getName(), this, ((View)object).getWindowId(), (TransitionValues)object3));
            this.mAnimators.add((Animator)object2);
        }
        if (sparseLongArray.size() != 0) {
            for (n = 0; n < sparseLongArray.size(); ++n) {
                n3 = sparseLongArray.keyAt(n);
                object = this.mAnimators.get(n3);
                ((Animator)object).setStartDelay(sparseLongArray.valueAt(n) - l + ((Animator)object).getStartDelay());
            }
        }
    }

    @UnsupportedAppUsage
    protected void end() {
        --this.mNumInstances;
        if (this.mNumInstances == 0) {
            int n;
            Object object = this.mListeners;
            if (object != null && ((ArrayList)object).size() > 0) {
                object = (ArrayList)this.mListeners.clone();
                int n2 = ((ArrayList)object).size();
                for (n = 0; n < n2; ++n) {
                    ((TransitionListener)((ArrayList)object).get(n)).onTransitionEnd(this);
                }
            }
            for (n = 0; n < this.mStartValues.itemIdValues.size(); ++n) {
                object = this.mStartValues.itemIdValues.valueAt(n);
                if (object == null) continue;
                ((View)object).setHasTransientState(false);
            }
            for (n = 0; n < this.mEndValues.itemIdValues.size(); ++n) {
                object = this.mEndValues.itemIdValues.valueAt(n);
                if (object == null) continue;
                ((View)object).setHasTransientState(false);
            }
            this.mEnded = true;
        }
    }

    public Transition excludeChildren(int n, boolean bl) {
        if (n >= 0) {
            this.mTargetIdChildExcludes = Transition.excludeObject(this.mTargetIdChildExcludes, n, bl);
        }
        return this;
    }

    public Transition excludeChildren(View view, boolean bl) {
        this.mTargetChildExcludes = Transition.excludeObject(this.mTargetChildExcludes, view, bl);
        return this;
    }

    public Transition excludeChildren(Class class_, boolean bl) {
        this.mTargetTypeChildExcludes = Transition.excludeObject(this.mTargetTypeChildExcludes, class_, bl);
        return this;
    }

    public Transition excludeTarget(int n, boolean bl) {
        if (n >= 0) {
            this.mTargetIdExcludes = Transition.excludeObject(this.mTargetIdExcludes, n, bl);
        }
        return this;
    }

    public Transition excludeTarget(View view, boolean bl) {
        this.mTargetExcludes = Transition.excludeObject(this.mTargetExcludes, view, bl);
        return this;
    }

    public Transition excludeTarget(Class class_, boolean bl) {
        this.mTargetTypeExcludes = Transition.excludeObject(this.mTargetTypeExcludes, class_, bl);
        return this;
    }

    public Transition excludeTarget(String string2, boolean bl) {
        this.mTargetNameExcludes = Transition.excludeObject(this.mTargetNameExcludes, string2, bl);
        return this;
    }

    void forceToEnd(ViewGroup object) {
        Object object2 = Transition.getRunningAnimators();
        int n = ((ArrayMap)object2).size();
        if (object != null && n != 0) {
            WindowId windowId = ((View)object).getWindowId();
            object = new ArrayMap<Animator, AnimationInfo>((ArrayMap<Animator, AnimationInfo>)object2);
            ((ArrayMap)object2).clear();
            --n;
            while (n >= 0) {
                object2 = (AnimationInfo)((ArrayMap)object).valueAt(n);
                if (((AnimationInfo)object2).view != null && windowId != null && windowId.equals(((AnimationInfo)object2).windowId)) {
                    ((Animator)((ArrayMap)object).keyAt(n)).end();
                }
                --n;
            }
            return;
        }
    }

    public long getDuration() {
        return this.mDuration;
    }

    public Rect getEpicenter() {
        EpicenterCallback epicenterCallback = this.mEpicenterCallback;
        if (epicenterCallback == null) {
            return null;
        }
        return epicenterCallback.onGetEpicenter(this);
    }

    public EpicenterCallback getEpicenterCallback() {
        return this.mEpicenterCallback;
    }

    public TimeInterpolator getInterpolator() {
        return this.mInterpolator;
    }

    TransitionValues getMatchedTransitionValues(View arrayList, boolean bl) {
        int n;
        Cloneable cloneable = this.mParent;
        if (cloneable != null) {
            return ((Transition)cloneable).getMatchedTransitionValues((View)((Object)arrayList), bl);
        }
        cloneable = bl ? this.mStartValuesList : this.mEndValuesList;
        if (cloneable == null) {
            return null;
        }
        int n2 = ((ArrayList)cloneable).size();
        int n3 = -1;
        int n4 = 0;
        do {
            n = n3;
            if (n4 >= n2) break;
            TransitionValues transitionValues = (TransitionValues)((ArrayList)cloneable).get(n4);
            if (transitionValues == null) {
                return null;
            }
            if (transitionValues.view == arrayList) {
                n = n4;
                break;
            }
            ++n4;
        } while (true);
        arrayList = null;
        if (n >= 0) {
            arrayList = bl ? this.mEndValuesList : this.mStartValuesList;
            arrayList = arrayList.get(n);
        }
        return arrayList;
    }

    public String getName() {
        return this.mName;
    }

    public ArrayMap<String, String> getNameOverrides() {
        return this.mNameOverrides;
    }

    public PathMotion getPathMotion() {
        return this.mPathMotion;
    }

    public TransitionPropagation getPropagation() {
        return this.mPropagation;
    }

    public long getStartDelay() {
        return this.mStartDelay;
    }

    public List<Integer> getTargetIds() {
        return this.mTargetIds;
    }

    public List<String> getTargetNames() {
        return this.mTargetNames;
    }

    public List<Class> getTargetTypes() {
        return this.mTargetTypes;
    }

    public List<String> getTargetViewNames() {
        return this.mTargetNames;
    }

    public List<View> getTargets() {
        return this.mTargets;
    }

    public String[] getTransitionProperties() {
        return null;
    }

    public TransitionValues getTransitionValues(View view, boolean bl) {
        Object object = this.mParent;
        if (object != null) {
            return ((Transition)object).getTransitionValues(view, bl);
        }
        object = bl ? this.mStartValues : this.mEndValues;
        return ((TransitionValuesMaps)object).viewValues.get(view);
    }

    public boolean isTransitionRequired(TransitionValues transitionValues, TransitionValues transitionValues2) {
        boolean bl;
        block5 : {
            boolean bl2 = false;
            boolean bl3 = false;
            bl = bl2;
            if (transitionValues == null) break block5;
            bl = bl2;
            if (transitionValues2 != null) {
                Object object = this.getTransitionProperties();
                if (object != null) {
                    int n = ((String[])object).length;
                    int n2 = 0;
                    do {
                        bl = bl3;
                        if (n2 >= n) break block5;
                        if (Transition.isValueChanged(transitionValues, transitionValues2, (String)object[n2])) {
                            bl = true;
                            break block5;
                        }
                        ++n2;
                    } while (true);
                }
                object = transitionValues.values.keySet().iterator();
                do {
                    bl = bl2;
                    if (!object.hasNext()) break block5;
                } while (!Transition.isValueChanged(transitionValues, transitionValues2, (String)object.next()));
                bl = true;
            }
        }
        return bl;
    }

    public boolean isValidTarget(View view) {
        int n;
        if (view == null) {
            return false;
        }
        int n2 = view.getId();
        ArrayList<Object> arrayList = this.mTargetIdExcludes;
        if (arrayList != null && arrayList.contains(n2)) {
            return false;
        }
        arrayList = this.mTargetExcludes;
        if (arrayList != null && arrayList.contains(view)) {
            return false;
        }
        arrayList = this.mTargetTypeExcludes;
        if (arrayList != null) {
            int n3 = arrayList.size();
            for (n = 0; n < n3; ++n) {
                if (!this.mTargetTypeExcludes.get(n).isInstance(view)) continue;
                return false;
            }
        }
        if (this.mTargetNameExcludes != null && view.getTransitionName() != null && this.mTargetNameExcludes.contains(view.getTransitionName())) {
            return false;
        }
        if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0 && ((arrayList = this.mTargetTypes) == null || arrayList.isEmpty()) && ((arrayList = this.mTargetNames) == null || arrayList.isEmpty())) {
            return true;
        }
        if (!this.mTargetIds.contains(n2) && !this.mTargets.contains(view)) {
            arrayList = this.mTargetNames;
            if (arrayList != null && arrayList.contains(view.getTransitionName())) {
                return true;
            }
            if (this.mTargetTypes != null) {
                for (n = 0; n < this.mTargetTypes.size(); ++n) {
                    if (!this.mTargetTypes.get(n).isInstance(view)) continue;
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    public void pause(View arrayList) {
        if (!this.mEnded) {
            ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
            int n = arrayMap.size();
            if (arrayList != null) {
                arrayList = ((View)((Object)arrayList)).getWindowId();
                --n;
                while (n >= 0) {
                    AnimationInfo animationInfo = arrayMap.valueAt(n);
                    if (animationInfo.view != null && arrayList != null && ((WindowId)((Object)arrayList)).equals(animationInfo.windowId)) {
                        arrayMap.keyAt(n).pause();
                    }
                    --n;
                }
            }
            if ((arrayList = this.mListeners) != null && arrayList.size() > 0) {
                arrayList = (ArrayList)this.mListeners.clone();
                int n2 = arrayList.size();
                for (n = 0; n < n2; ++n) {
                    ((TransitionListener)arrayList.get(n)).onTransitionPause(this);
                }
            }
            this.mPaused = true;
        }
    }

    void playTransition(ViewGroup viewGroup) {
        this.mStartValuesList = new ArrayList<E>();
        this.mEndValuesList = new ArrayList<E>();
        this.matchStartAndEnd(this.mStartValues, this.mEndValues);
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        int n = arrayMap.size();
        WindowId windowId = viewGroup.getWindowId();
        --n;
        while (n >= 0) {
            AnimationInfo animationInfo;
            Animator animator2 = arrayMap.keyAt(n);
            if (animator2 != null && (animationInfo = arrayMap.get(animator2)) != null && animationInfo.view != null && animationInfo.windowId == windowId) {
                TransitionValues transitionValues;
                TransitionValues transitionValues2 = animationInfo.values;
                View view = animationInfo.view;
                boolean bl = true;
                TransitionValues transitionValues3 = this.getTransitionValues(view, true);
                TransitionValues transitionValues4 = transitionValues = this.getMatchedTransitionValues(view, true);
                if (transitionValues3 == null) {
                    transitionValues4 = transitionValues;
                    if (transitionValues == null) {
                        transitionValues4 = this.mEndValues.viewValues.get(view);
                    }
                }
                if (transitionValues3 == null && transitionValues4 == null || !animationInfo.transition.isTransitionRequired(transitionValues2, transitionValues4)) {
                    bl = false;
                }
                if (bl) {
                    if (!animator2.isRunning() && !animator2.isStarted()) {
                        arrayMap.remove(animator2);
                    } else {
                        animator2.cancel();
                    }
                }
            }
            --n;
        }
        this.createAnimators(viewGroup, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
        this.runAnimators();
    }

    public Transition removeListener(TransitionListener transitionListener) {
        ArrayList<TransitionListener> arrayList = this.mListeners;
        if (arrayList == null) {
            return this;
        }
        arrayList.remove(transitionListener);
        if (this.mListeners.size() == 0) {
            this.mListeners = null;
        }
        return this;
    }

    public Transition removeTarget(int n) {
        if (n > 0) {
            this.mTargetIds.remove((Object)n);
        }
        return this;
    }

    public Transition removeTarget(View view) {
        if (view != null) {
            this.mTargets.remove(view);
        }
        return this;
    }

    public Transition removeTarget(Class class_) {
        if (class_ != null) {
            this.mTargetTypes.remove(class_);
        }
        return this;
    }

    public Transition removeTarget(String string2) {
        ArrayList<String> arrayList;
        if (string2 != null && (arrayList = this.mTargetNames) != null) {
            arrayList.remove(string2);
        }
        return this;
    }

    public void resume(View arrayList) {
        if (this.mPaused) {
            if (!this.mEnded) {
                ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
                int n = arrayMap.size();
                WindowId windowId = ((View)((Object)arrayList)).getWindowId();
                --n;
                while (n >= 0) {
                    arrayList = arrayMap.valueAt(n);
                    if (((AnimationInfo)arrayList).view != null && windowId != null && windowId.equals(((AnimationInfo)arrayList).windowId)) {
                        arrayMap.keyAt(n).resume();
                    }
                    --n;
                }
                arrayList = this.mListeners;
                if (arrayList != null && arrayList.size() > 0) {
                    arrayList = (ArrayList)this.mListeners.clone();
                    int n2 = arrayList.size();
                    for (n = 0; n < n2; ++n) {
                        ((TransitionListener)arrayList.get(n)).onTransitionResume(this);
                    }
                }
            }
            this.mPaused = false;
        }
    }

    protected void runAnimators() {
        this.start();
        ArrayMap<Animator, AnimationInfo> arrayMap = Transition.getRunningAnimators();
        for (Animator animator2 : this.mAnimators) {
            if (!arrayMap.containsKey(animator2)) continue;
            this.start();
            this.runAnimator(animator2, arrayMap);
        }
        this.mAnimators.clear();
        this.end();
    }

    void setCanRemoveViews(boolean bl) {
        this.mCanRemoveViews = bl;
    }

    public Transition setDuration(long l) {
        this.mDuration = l;
        return this;
    }

    public void setEpicenterCallback(EpicenterCallback epicenterCallback) {
        this.mEpicenterCallback = epicenterCallback;
    }

    public Transition setInterpolator(TimeInterpolator timeInterpolator) {
        this.mInterpolator = timeInterpolator;
        return this;
    }

    public void setMatchOrder(int ... arrn) {
        if (arrn != null && arrn.length != 0) {
            for (int i = 0; i < arrn.length; ++i) {
                if (Transition.isValidMatch(arrn[i])) {
                    if (!Transition.alreadyContains(arrn, i)) {
                        continue;
                    }
                    throw new IllegalArgumentException("matches contains a duplicate value");
                }
                throw new IllegalArgumentException("matches contains invalid value");
            }
            this.mMatchOrder = (int[])arrn.clone();
        } else {
            this.mMatchOrder = DEFAULT_MATCH_ORDER;
        }
    }

    public void setNameOverrides(ArrayMap<String, String> arrayMap) {
        this.mNameOverrides = arrayMap;
    }

    public void setPathMotion(PathMotion pathMotion) {
        this.mPathMotion = pathMotion == null ? STRAIGHT_PATH_MOTION : pathMotion;
    }

    public void setPropagation(TransitionPropagation transitionPropagation) {
        this.mPropagation = transitionPropagation;
    }

    Transition setSceneRoot(ViewGroup viewGroup) {
        this.mSceneRoot = viewGroup;
        return this;
    }

    public Transition setStartDelay(long l) {
        this.mStartDelay = l;
        return this;
    }

    protected void start() {
        if (this.mNumInstances == 0) {
            ArrayList arrayList = this.mListeners;
            if (arrayList != null && arrayList.size() > 0) {
                arrayList = (ArrayList)this.mListeners.clone();
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    ((TransitionListener)arrayList.get(i)).onTransitionStart(this);
                }
            }
            this.mEnded = false;
        }
        ++this.mNumInstances;
    }

    public String toString() {
        return this.toString("");
    }

    String toString(String charSequence) {
        CharSequence charSequence2;
        block13 : {
            int n;
            block12 : {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(this.getClass().getSimpleName());
                ((StringBuilder)charSequence2).append("@");
                ((StringBuilder)charSequence2).append(Integer.toHexString(this.hashCode()));
                ((StringBuilder)charSequence2).append(": ");
                charSequence2 = ((StringBuilder)charSequence2).toString();
                charSequence = charSequence2;
                if (this.mDuration != -1L) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append("dur(");
                    ((StringBuilder)charSequence).append(this.mDuration);
                    ((StringBuilder)charSequence).append(") ");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                charSequence2 = charSequence;
                if (this.mStartDelay != -1L) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append("dly(");
                    ((StringBuilder)charSequence2).append(this.mStartDelay);
                    ((StringBuilder)charSequence2).append(") ");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                charSequence = charSequence2;
                if (this.mInterpolator != null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append("interp(");
                    ((StringBuilder)charSequence).append(this.mInterpolator);
                    ((StringBuilder)charSequence).append(") ");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                if (this.mTargetIds.size() > 0) break block12;
                charSequence2 = charSequence;
                if (this.mTargets.size() <= 0) break block13;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("tgts(");
            charSequence = ((StringBuilder)charSequence2).toString();
            charSequence2 = charSequence;
            if (this.mTargetIds.size() > 0) {
                n = 0;
                do {
                    charSequence2 = charSequence;
                    if (n >= this.mTargetIds.size()) break;
                    charSequence2 = charSequence;
                    if (n > 0) {
                        charSequence2 = new StringBuilder();
                        ((StringBuilder)charSequence2).append((String)charSequence);
                        ((StringBuilder)charSequence2).append(", ");
                        charSequence2 = ((StringBuilder)charSequence2).toString();
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(this.mTargetIds.get(n));
                    charSequence = ((StringBuilder)charSequence).toString();
                    ++n;
                } while (true);
            }
            charSequence = charSequence2;
            if (this.mTargets.size() > 0) {
                n = 0;
                do {
                    charSequence = charSequence2;
                    if (n >= this.mTargets.size()) break;
                    charSequence = charSequence2;
                    if (n > 0) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append((String)charSequence2);
                        ((StringBuilder)charSequence).append(", ");
                        charSequence = ((StringBuilder)charSequence).toString();
                    }
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(this.mTargets.get(n));
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                    ++n;
                } while (true);
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append(")");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }

    public static class AnimationInfo {
        String name;
        Transition transition;
        TransitionValues values;
        public View view;
        WindowId windowId;

        AnimationInfo(View view, String string2, Transition transition2, WindowId windowId, TransitionValues transitionValues) {
            this.view = view;
            this.name = string2;
            this.values = transitionValues;
            this.windowId = windowId;
            this.transition = transition2;
        }
    }

    private static class ArrayListManager {
        private ArrayListManager() {
        }

        static <T> ArrayList<T> add(ArrayList<T> arrayList, T t) {
            ArrayList<Object> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList();
            }
            if (!arrayList2.contains(t)) {
                arrayList2.add(t);
            }
            return arrayList2;
        }

        static <T> ArrayList<T> remove(ArrayList<T> arrayList, T t) {
            ArrayList<T> arrayList2 = arrayList;
            if (arrayList != null) {
                arrayList.remove(t);
                arrayList2 = arrayList;
                if (arrayList.isEmpty()) {
                    arrayList2 = null;
                }
            }
            return arrayList2;
        }
    }

    public static abstract class EpicenterCallback {
        public abstract Rect onGetEpicenter(Transition var1);
    }

    public static interface TransitionListener {
        public void onTransitionCancel(Transition var1);

        public void onTransitionEnd(Transition var1);

        public void onTransitionPause(Transition var1);

        public void onTransitionResume(Transition var1);

        public void onTransitionStart(Transition var1);
    }

}

