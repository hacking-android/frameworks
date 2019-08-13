/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.BackStackRecord;
import android.app.Fragment;
import android.app.FragmentContainer;
import android.app.FragmentHostCallback;
import android.app.FragmentManagerImpl;
import android.app.SharedElementCallback;
import android.app._$$Lambda$FragmentTransition$8Ei4ls5jlZcfRvuLcweFAxtFBFs;
import android.app._$$Lambda$FragmentTransition$Ip0LktADPhG_3ouNBXgzufWpFfY;
import android.app._$$Lambda$FragmentTransition$PZ32bJ_FSMpbzYzBl8x73NJPidQ;
import android.app._$$Lambda$FragmentTransition$jurn0WXuKw3bRQ_2d5zCWdeZWuI;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.util.ArrayMap;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.view.OneShotPreDrawListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

class FragmentTransition {
    private static final int[] INVERSE_OPS = new int[]{0, 3, 0, 1, 5, 4, 7, 6, 9, 8};

    FragmentTransition() {
    }

    private static void addSharedElementsWithMatchingNames(ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, Collection<String> collection) {
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            View view = arrayMap.valueAt(i);
            if (view == null || !collection.contains(view.getTransitionName())) continue;
            arrayList.add(view);
        }
    }

    public static void addTargets(Transition transition2, ArrayList<View> arrayList) {
        block4 : {
            block3 : {
                if (transition2 == null) {
                    return;
                }
                if (!(transition2 instanceof TransitionSet)) break block3;
                transition2 = (TransitionSet)transition2;
                int n = ((TransitionSet)transition2).getTransitionCount();
                for (int i = 0; i < n; ++i) {
                    FragmentTransition.addTargets(((TransitionSet)transition2).getTransitionAt(i), arrayList);
                }
                break block4;
            }
            if (FragmentTransition.hasSimpleTarget(transition2) || !FragmentTransition.isNullOrEmpty(transition2.getTargets())) break block4;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                transition2.addTarget(arrayList.get(i));
            }
        }
    }

    private static void addToFirstInLastOut(BackStackRecord backStackRecord, BackStackRecord.Op object, SparseArray<FragmentContainerTransition> sparseArray, boolean bl, boolean bl2) {
        int n;
        Fragment fragment;
        block38 : {
            Object object2;
            int n2;
            block39 : {
                int n3;
                boolean bl3;
                int n4;
                block37 : {
                    boolean bl4;
                    block33 : {
                        int n5;
                        int n6;
                        block34 : {
                            block35 : {
                                boolean bl5;
                                block36 : {
                                    fragment = ((BackStackRecord.Op)object).fragment;
                                    if (fragment == null) {
                                        return;
                                    }
                                    n2 = fragment.mContainerId;
                                    if (n2 == 0) {
                                        return;
                                    }
                                    n4 = bl ? INVERSE_OPS[((BackStackRecord.Op)object).cmd] : ((BackStackRecord.Op)object).cmd;
                                    n = 0;
                                    n3 = 0;
                                    n6 = 0;
                                    n5 = 0;
                                    bl4 = false;
                                    bl5 = false;
                                    if (n4 == 1) break block33;
                                    if (n4 == 3) break block34;
                                    if (n4 == 4) break block35;
                                    if (n4 == 5) break block36;
                                    if (n4 == 6) break block34;
                                    if (n4 == 7) break block33;
                                    bl3 = false;
                                    n = 0;
                                    n3 = 0;
                                    n4 = 0;
                                    break block37;
                                }
                                if (bl2) {
                                    bl3 = bl5;
                                    if (fragment.mHiddenChanged) {
                                        bl3 = bl5;
                                        if (!fragment.mHidden) {
                                            bl3 = bl5;
                                            if (fragment.mAdded) {
                                                bl3 = true;
                                            }
                                        }
                                    }
                                } else {
                                    bl3 = fragment.mHidden;
                                }
                                n = 0;
                                n3 = 0;
                                n4 = 1;
                                break block37;
                            }
                            if (bl2) {
                                n4 = n;
                                if (fragment.mHiddenChanged) {
                                    n4 = n;
                                    if (fragment.mAdded) {
                                        n4 = n;
                                        if (fragment.mHidden) {
                                            n4 = 1;
                                        }
                                    }
                                }
                            } else {
                                n4 = n3;
                                if (fragment.mAdded) {
                                    n4 = n3;
                                    if (!fragment.mHidden) {
                                        n4 = 1;
                                    }
                                }
                            }
                            bl3 = false;
                            n = 1;
                            n3 = n4;
                            n4 = 0;
                            break block37;
                        }
                        if (bl2) {
                            n4 = !fragment.mAdded && fragment.mView != null && fragment.mView.getVisibility() == 0 && fragment.mView.getTransitionAlpha() > 0.0f ? 1 : n6;
                        } else {
                            n4 = n5;
                            if (fragment.mAdded) {
                                n4 = n5;
                                if (!fragment.mHidden) {
                                    n4 = 1;
                                }
                            }
                        }
                        bl3 = false;
                        n = 1;
                        n3 = n4;
                        n4 = 0;
                        break block37;
                    }
                    if (bl2) {
                        bl3 = fragment.mIsNewlyAdded;
                    } else {
                        bl3 = bl4;
                        if (!fragment.mAdded) {
                            bl3 = bl4;
                            if (!fragment.mHidden) {
                                bl3 = true;
                            }
                        }
                    }
                    n = 0;
                    n3 = 0;
                    n4 = 1;
                }
                object = sparseArray.get(n2);
                if (bl3) {
                    object = FragmentTransition.ensureContainer((FragmentContainerTransition)object, sparseArray, n2);
                    ((FragmentContainerTransition)object).lastIn = fragment;
                    ((FragmentContainerTransition)object).lastInIsPop = bl;
                    ((FragmentContainerTransition)object).lastInTransaction = backStackRecord;
                }
                if (!bl2 && n4 != 0) {
                    if (object != null && ((FragmentContainerTransition)object).firstOut == fragment) {
                        ((FragmentContainerTransition)object).firstOut = null;
                    }
                    object2 = backStackRecord.mManager;
                    if (fragment.mState < 1 && ((FragmentManagerImpl)object2).mCurState >= 1 && object2.mHost.getContext().getApplicationInfo().targetSdkVersion >= 24 && !backStackRecord.mReorderingAllowed) {
                        ((FragmentManagerImpl)object2).makeActive(fragment);
                        ((FragmentManagerImpl)object2).moveToState(fragment, 1, 0, 0, false);
                    }
                }
                if (n3 == 0) break block38;
                object2 = object;
                if (object2 == null) break block39;
                object = object2;
                if (((FragmentContainerTransition)object2).firstOut != null) break block38;
            }
            object = FragmentTransition.ensureContainer((FragmentContainerTransition)object2, sparseArray, n2);
            ((FragmentContainerTransition)object).firstOut = fragment;
            ((FragmentContainerTransition)object).firstOutIsPop = bl;
            ((FragmentContainerTransition)object).firstOutTransaction = backStackRecord;
        }
        if (!bl2 && n != 0 && object != null && ((FragmentContainerTransition)object).lastIn == fragment) {
            ((FragmentContainerTransition)object).lastIn = null;
        }
    }

    private static void bfsAddViewChildren(List<View> list, View view) {
        int n = list.size();
        if (FragmentTransition.containedBeforeIndex(list, view, n)) {
            return;
        }
        list.add(view);
        for (int i = n; i < list.size(); ++i) {
            view = list.get(i);
            if (!(view instanceof ViewGroup)) continue;
            ViewGroup viewGroup = (ViewGroup)view;
            int n2 = viewGroup.getChildCount();
            for (int j = 0; j < n2; ++j) {
                view = viewGroup.getChildAt(j);
                if (FragmentTransition.containedBeforeIndex(list, view, n)) continue;
                list.add(view);
            }
        }
    }

    public static void calculateFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean bl) {
        int n = backStackRecord.mOps.size();
        for (int i = 0; i < n; ++i) {
            FragmentTransition.addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(i), sparseArray, false, bl);
        }
    }

    private static ArrayMap<String, String> calculateNameOverrides(int n, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n2, int n3) {
        ArrayMap<String, String> arrayMap = new ArrayMap<String, String>();
        --n3;
        while (n3 >= n2) {
            Object object = arrayList.get(n3);
            if (((BackStackRecord)object).interactsWith(n)) {
                boolean bl = arrayList2.get(n3);
                if (((BackStackRecord)object).mSharedElementSourceNames != null) {
                    ArrayList<String> arrayList3;
                    ArrayList<String> arrayList4;
                    int n4 = ((BackStackRecord)object).mSharedElementSourceNames.size();
                    if (bl) {
                        arrayList4 = ((BackStackRecord)object).mSharedElementSourceNames;
                        arrayList3 = ((BackStackRecord)object).mSharedElementTargetNames;
                    } else {
                        arrayList3 = ((BackStackRecord)object).mSharedElementSourceNames;
                        arrayList4 = ((BackStackRecord)object).mSharedElementTargetNames;
                    }
                    for (int i = 0; i < n4; ++i) {
                        String string2 = arrayList3.get(i);
                        object = arrayList4.get(i);
                        String string3 = arrayMap.remove(object);
                        if (string3 != null) {
                            arrayMap.put(string2, string3);
                            continue;
                        }
                        arrayMap.put(string2, (String)object);
                    }
                }
            }
            --n3;
        }
        return arrayMap;
    }

    public static void calculatePopFragments(BackStackRecord backStackRecord, SparseArray<FragmentContainerTransition> sparseArray, boolean bl) {
        if (!backStackRecord.mManager.mContainer.onHasView()) {
            return;
        }
        for (int i = backStackRecord.mOps.size() - 1; i >= 0; --i) {
            FragmentTransition.addToFirstInLastOut(backStackRecord, backStackRecord.mOps.get(i), sparseArray, true, bl);
        }
    }

    private static void callSharedElementStartEnd(Fragment object, Fragment object2, boolean bl, ArrayMap<String, View> arrayMap, boolean bl2) {
        object = bl ? ((Fragment)object2).getEnterTransitionCallback() : ((Fragment)object).getEnterTransitionCallback();
        if (object != null) {
            object2 = new ArrayList();
            ArrayList<String> arrayList = new ArrayList<String>();
            int n = arrayMap == null ? 0 : arrayMap.size();
            for (int i = 0; i < n; ++i) {
                arrayList.add(arrayMap.keyAt(i));
                ((ArrayList)object2).add(arrayMap.valueAt(i));
            }
            if (bl2) {
                ((SharedElementCallback)object).onSharedElementStart(arrayList, (List<View>)object2, null);
            } else {
                ((SharedElementCallback)object).onSharedElementEnd(arrayList, (List<View>)object2, null);
            }
        }
    }

    private static ArrayMap<String, View> captureInSharedElements(ArrayMap<String, String> arrayMap, TransitionSet arrayList, FragmentContainerTransition object) {
        Object object2 = ((FragmentContainerTransition)object).lastIn;
        View view = ((Fragment)object2).getView();
        if (!arrayMap.isEmpty() && arrayList != null && view != null) {
            ArrayMap<String, View> arrayMap2 = new ArrayMap<String, View>();
            view.findNamedViews(arrayMap2);
            arrayList = ((FragmentContainerTransition)object).lastInTransaction;
            if (((FragmentContainerTransition)object).lastInIsPop) {
                object = ((Fragment)object2).getExitTransitionCallback();
                arrayList = ((BackStackRecord)arrayList).mSharedElementSourceNames;
            } else {
                object = ((Fragment)object2).getEnterTransitionCallback();
                arrayList = ((BackStackRecord)arrayList).mSharedElementTargetNames;
            }
            if (arrayList != null) {
                arrayMap2.retainAll(arrayList);
            }
            if (arrayList != null && object != null) {
                ((SharedElementCallback)object).onMapSharedElements(arrayList, arrayMap2);
                for (int i = arrayList.size() - 1; i >= 0; --i) {
                    object2 = arrayList.get(i);
                    object = arrayMap2.get(object2);
                    if (object == null) {
                        object = FragmentTransition.findKeyForValue(arrayMap, (String)object2);
                        if (object == null) continue;
                        arrayMap.remove(object);
                        continue;
                    }
                    if (((String)object2).equals(((View)object).getTransitionName()) || (object2 = FragmentTransition.findKeyForValue(arrayMap, (String)object2)) == null) continue;
                    arrayMap.put((String)object2, ((View)object).getTransitionName());
                }
            } else {
                FragmentTransition.retainValues(arrayMap, arrayMap2);
            }
            return arrayMap2;
        }
        arrayMap.clear();
        return null;
    }

    private static ArrayMap<String, View> captureOutSharedElements(ArrayMap<String, String> arrayMap, TransitionSet arrayList, FragmentContainerTransition object) {
        if (!arrayMap.isEmpty() && arrayList != null) {
            Object object2 = ((FragmentContainerTransition)object).firstOut;
            ArrayMap<String, View> arrayMap2 = new ArrayMap<String, View>();
            ((Fragment)object2).getView().findNamedViews(arrayMap2);
            arrayList = ((FragmentContainerTransition)object).firstOutTransaction;
            if (((FragmentContainerTransition)object).firstOutIsPop) {
                object = ((Fragment)object2).getEnterTransitionCallback();
                arrayList = ((BackStackRecord)arrayList).mSharedElementTargetNames;
            } else {
                object = ((Fragment)object2).getExitTransitionCallback();
                arrayList = ((BackStackRecord)arrayList).mSharedElementSourceNames;
            }
            arrayMap2.retainAll(arrayList);
            if (object != null) {
                ((SharedElementCallback)object).onMapSharedElements(arrayList, arrayMap2);
                for (int i = arrayList.size() - 1; i >= 0; --i) {
                    object2 = arrayList.get(i);
                    object = arrayMap2.get(object2);
                    if (object == null) {
                        arrayMap.remove(object2);
                        continue;
                    }
                    if (((String)object2).equals(((View)object).getTransitionName())) continue;
                    object2 = arrayMap.remove(object2);
                    arrayMap.put(((View)object).getTransitionName(), (String)object2);
                }
            } else {
                arrayMap.retainAll(arrayMap2.keySet());
            }
            return arrayMap2;
        }
        arrayMap.clear();
        return null;
    }

    private static Transition cloneTransition(Transition transition2) {
        Transition transition3 = transition2;
        if (transition2 != null) {
            transition3 = transition2.clone();
        }
        return transition3;
    }

    private static ArrayList<View> configureEnteringExitingViews(Transition transition2, Fragment object, ArrayList<View> arrayList, View view) {
        ArrayList<View> arrayList2 = null;
        if (transition2 != null) {
            ArrayList<View> arrayList3 = new ArrayList<View>();
            if ((object = ((Fragment)object).getView()) != null) {
                ((View)object).captureTransitioningViews(arrayList3);
            }
            if (arrayList != null) {
                arrayList3.removeAll(arrayList);
            }
            arrayList2 = arrayList3;
            if (!arrayList3.isEmpty()) {
                arrayList3.add(view);
                FragmentTransition.addTargets(transition2, arrayList3);
                arrayList2 = arrayList3;
            }
        }
        return arrayList2;
    }

    private static TransitionSet configureSharedElementsOrdered(ViewGroup viewGroup, View view, ArrayMap<String, String> arrayMap, FragmentContainerTransition fragmentContainerTransition, ArrayList<View> arrayList, ArrayList<View> arrayList2, Transition transition2, Transition object) {
        Fragment fragment = fragmentContainerTransition.lastIn;
        Fragment fragment2 = fragmentContainerTransition.firstOut;
        if (fragment != null && fragment2 != null) {
            boolean bl = fragmentContainerTransition.lastInIsPop;
            TransitionSet transitionSet = arrayMap.isEmpty() ? null : FragmentTransition.getSharedElementTransition(fragment, fragment2, bl);
            ArrayMap<String, View> arrayMap2 = FragmentTransition.captureOutSharedElements(arrayMap, transitionSet, fragmentContainerTransition);
            if (arrayMap.isEmpty()) {
                transitionSet = null;
            } else {
                arrayList.addAll(arrayMap2.values());
            }
            if (transition2 == null && object == null && transitionSet == null) {
                return null;
            }
            FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap2, true);
            if (transitionSet != null) {
                Rect rect = new Rect();
                FragmentTransition.setSharedElementTargets(transitionSet, view, arrayList);
                FragmentTransition.setOutEpicenter(transitionSet, (Transition)object, arrayMap2, fragmentContainerTransition.firstOutIsPop, fragmentContainerTransition.firstOutTransaction);
                if (transition2 != null) {
                    transition2.setEpicenterCallback(new Transition.EpicenterCallback(){

                        @Override
                        public Rect onGetEpicenter(Transition transition2) {
                            if (Rect.this.isEmpty()) {
                                return null;
                            }
                            return Rect.this;
                        }
                    });
                }
                object = rect;
            } else {
                object = null;
            }
            OneShotPreDrawListener.add(viewGroup, new _$$Lambda$FragmentTransition$Ip0LktADPhG_3ouNBXgzufWpFfY(arrayMap, transitionSet, fragmentContainerTransition, arrayList2, view, fragment, fragment2, bl, arrayList, transition2, (Rect)object));
            return transitionSet;
        }
        return null;
    }

    private static TransitionSet configureSharedElementsReordered(ViewGroup viewGroup, View view, ArrayMap<String, String> object, FragmentContainerTransition object2, ArrayList<View> object3, ArrayList<View> arrayList, Transition transition2, Transition transition3) {
        Fragment fragment = ((FragmentContainerTransition)object2).lastIn;
        Fragment fragment2 = ((FragmentContainerTransition)object2).firstOut;
        if (fragment != null) {
            fragment.getView().setVisibility(0);
        }
        if (fragment != null && fragment2 != null) {
            boolean bl = ((FragmentContainerTransition)object2).lastInIsPop;
            TransitionSet transitionSet = ((ArrayMap)object).isEmpty() ? null : FragmentTransition.getSharedElementTransition(fragment, fragment2, bl);
            ArrayMap<String, View> arrayMap = FragmentTransition.captureOutSharedElements(object, transitionSet, (FragmentContainerTransition)object2);
            ArrayMap<String, View> arrayMap2 = FragmentTransition.captureInSharedElements(object, transitionSet, (FragmentContainerTransition)object2);
            if (((ArrayMap)object).isEmpty()) {
                if (arrayMap != null) {
                    arrayMap.clear();
                }
                if (arrayMap2 != null) {
                    arrayMap2.clear();
                }
                object = null;
            } else {
                FragmentTransition.addSharedElementsWithMatchingNames(object3, arrayMap, ((ArrayMap)object).keySet());
                FragmentTransition.addSharedElementsWithMatchingNames(arrayList, arrayMap2, ((ArrayMap)object).values());
                object = transitionSet;
            }
            if (transition2 == null && transition3 == null && object == null) {
                return null;
            }
            FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap, true);
            if (object != null) {
                arrayList.add(view);
                FragmentTransition.setSharedElementTargets((TransitionSet)object, view, object3);
                FragmentTransition.setOutEpicenter((TransitionSet)object, transition3, arrayMap, ((FragmentContainerTransition)object2).firstOutIsPop, ((FragmentContainerTransition)object2).firstOutTransaction);
                object3 = new Rect();
                view = FragmentTransition.getInEpicenterView(arrayMap2, (FragmentContainerTransition)object2, transition2, bl);
                if (view != null) {
                    transition2.setEpicenterCallback(new Transition.EpicenterCallback(){

                        @Override
                        public Rect onGetEpicenter(Transition transition2) {
                            return Rect.this;
                        }
                    });
                }
                object2 = object3;
            } else {
                object2 = null;
                view = null;
            }
            OneShotPreDrawListener.add(viewGroup, new _$$Lambda$FragmentTransition$jurn0WXuKw3bRQ_2d5zCWdeZWuI(fragment, fragment2, bl, arrayMap2, view, (Rect)object2));
            return object;
        }
        return null;
    }

    private static void configureTransitionsOrdered(FragmentManagerImpl object, int n, FragmentContainerTransition object2, View view, ArrayMap<String, String> object3) {
        block4 : {
            object = ((FragmentManagerImpl)object).mContainer.onHasView() ? (ViewGroup)((FragmentManagerImpl)object).mContainer.onFindViewById(n) : null;
            if (object == null) {
                return;
            }
            Fragment fragment = ((FragmentContainerTransition)object2).lastIn;
            Object object4 = ((FragmentContainerTransition)object2).firstOut;
            boolean bl = ((FragmentContainerTransition)object2).lastInIsPop;
            boolean bl2 = ((FragmentContainerTransition)object2).firstOutIsPop;
            Transition transition2 = FragmentTransition.getEnterTransition(fragment, bl);
            Transition transition3 = FragmentTransition.getExitTransition((Fragment)object4, bl2);
            ArrayList<View> arrayList = new ArrayList<View>();
            ArrayList<View> arrayList2 = new ArrayList<View>();
            TransitionSet transitionSet = FragmentTransition.configureSharedElementsOrdered((ViewGroup)object, view, object3, (FragmentContainerTransition)object2, arrayList, arrayList2, transition2, transition3);
            if (transition2 == null && transitionSet == null && transition3 == null) {
                return;
            }
            if ((object4 = FragmentTransition.configureEnteringExitingViews(transition3, (Fragment)object4, arrayList, view)) == null || ((ArrayList)object4).isEmpty()) {
                transition3 = null;
            }
            if (transition2 != null) {
                transition2.addTarget(view);
            }
            if ((object2 = FragmentTransition.mergeTransitions(transition2, transition3, transitionSet, fragment, ((FragmentContainerTransition)object2).lastInIsPop)) == null) break block4;
            ((Transition)object2).setNameOverrides((ArrayMap<String, String>)object3);
            object3 = new ArrayList();
            FragmentTransition.scheduleRemoveTargets((Transition)object2, transition2, (ArrayList<View>)object3, transition3, (ArrayList<View>)object4, transitionSet, arrayList2);
            FragmentTransition.scheduleTargetChange((ViewGroup)object, fragment, view, arrayList2, transition2, (ArrayList<View>)object3, transition3, (ArrayList<View>)object4);
            TransitionManager.beginDelayedTransition((ViewGroup)object, (Transition)object2);
        }
    }

    private static void configureTransitionsReordered(FragmentManagerImpl object, int n, FragmentContainerTransition object2, View object3, ArrayMap<String, String> arrayMap) {
        block3 : {
            object = ((FragmentManagerImpl)object).mContainer.onHasView() ? (ViewGroup)((FragmentManagerImpl)object).mContainer.onFindViewById(n) : null;
            if (object == null) {
                return;
            }
            Object object4 = ((FragmentContainerTransition)object2).lastIn;
            Fragment fragment = ((FragmentContainerTransition)object2).firstOut;
            boolean bl = ((FragmentContainerTransition)object2).lastInIsPop;
            boolean bl2 = ((FragmentContainerTransition)object2).firstOutIsPop;
            ArrayList<View> arrayList = new ArrayList<View>();
            ArrayList<View> arrayList2 = new ArrayList<View>();
            Transition transition2 = FragmentTransition.getEnterTransition((Fragment)object4, bl);
            Cloneable cloneable = FragmentTransition.getExitTransition(fragment, bl2);
            TransitionSet transitionSet = FragmentTransition.configureSharedElementsReordered((ViewGroup)object, (View)object3, arrayMap, (FragmentContainerTransition)object2, arrayList2, arrayList, transition2, cloneable);
            if (transition2 == null && transitionSet == null && cloneable == null) {
                return;
            }
            object2 = cloneable;
            cloneable = FragmentTransition.configureEnteringExitingViews((Transition)object2, fragment, arrayList2, (View)object3);
            object3 = FragmentTransition.configureEnteringExitingViews(transition2, (Fragment)object4, arrayList, (View)object3);
            FragmentTransition.setViewVisibility((ArrayList<View>)object3, 4);
            object4 = FragmentTransition.mergeTransitions(transition2, (Transition)object2, transitionSet, (Fragment)object4, bl);
            if (object4 == null) break block3;
            FragmentTransition.replaceHide((Transition)object2, fragment, (ArrayList<View>)cloneable);
            ((Transition)object4).setNameOverrides(arrayMap);
            FragmentTransition.scheduleRemoveTargets((Transition)object4, transition2, (ArrayList<View>)object3, (Transition)object2, (ArrayList<View>)cloneable, transitionSet, arrayList);
            TransitionManager.beginDelayedTransition((ViewGroup)object, (Transition)object4);
            FragmentTransition.setViewVisibility((ArrayList<View>)object3, 0);
            if (transitionSet != null) {
                transitionSet.getTargets().clear();
                transitionSet.getTargets().addAll(arrayList);
                FragmentTransition.replaceTargets(transitionSet, arrayList2, arrayList);
            }
        }
    }

    private static boolean containedBeforeIndex(List<View> list, View view, int n) {
        for (int i = 0; i < n; ++i) {
            if (list.get(i) != view) continue;
            return true;
        }
        return false;
    }

    private static FragmentContainerTransition ensureContainer(FragmentContainerTransition fragmentContainerTransition, SparseArray<FragmentContainerTransition> sparseArray, int n) {
        FragmentContainerTransition fragmentContainerTransition2 = fragmentContainerTransition;
        if (fragmentContainerTransition == null) {
            fragmentContainerTransition2 = new FragmentContainerTransition();
            sparseArray.put(n, fragmentContainerTransition2);
        }
        return fragmentContainerTransition2;
    }

    private static String findKeyForValue(ArrayMap<String, String> arrayMap, String string2) {
        int n = arrayMap.size();
        for (int i = 0; i < n; ++i) {
            if (!string2.equals(arrayMap.valueAt(i))) continue;
            return arrayMap.keyAt(i);
        }
        return null;
    }

    private static Transition getEnterTransition(Fragment object, boolean bl) {
        if (object == null) {
            return null;
        }
        object = bl ? ((Fragment)object).getReenterTransition() : ((Fragment)object).getEnterTransition();
        return FragmentTransition.cloneTransition((Transition)object);
    }

    private static Transition getExitTransition(Fragment object, boolean bl) {
        if (object == null) {
            return null;
        }
        object = bl ? ((Fragment)object).getReturnTransition() : ((Fragment)object).getExitTransition();
        return FragmentTransition.cloneTransition((Transition)object);
    }

    private static View getInEpicenterView(ArrayMap<String, View> arrayMap, FragmentContainerTransition object, Transition transition2, boolean bl) {
        object = ((FragmentContainerTransition)object).lastInTransaction;
        if (transition2 != null && arrayMap != null && ((BackStackRecord)object).mSharedElementSourceNames != null && !((BackStackRecord)object).mSharedElementSourceNames.isEmpty()) {
            object = bl ? ((BackStackRecord)object).mSharedElementSourceNames.get(0) : ((BackStackRecord)object).mSharedElementTargetNames.get(0);
            return arrayMap.get(object);
        }
        return null;
    }

    private static TransitionSet getSharedElementTransition(Fragment object, Fragment object2, boolean bl) {
        if (object != null && object2 != null) {
            object = bl ? ((Fragment)object2).getSharedElementReturnTransition() : ((Fragment)object).getSharedElementEnterTransition();
            if ((object = FragmentTransition.cloneTransition((Transition)object)) == null) {
                return null;
            }
            object2 = new TransitionSet();
            ((TransitionSet)object2).addTransition((Transition)object);
            return object2;
        }
        return null;
    }

    private static boolean hasSimpleTarget(Transition transition2) {
        boolean bl = !(FragmentTransition.isNullOrEmpty(transition2.getTargetIds()) && FragmentTransition.isNullOrEmpty(transition2.getTargetNames()) && FragmentTransition.isNullOrEmpty(transition2.getTargetTypes()));
        return bl;
    }

    private static boolean isNullOrEmpty(List list) {
        boolean bl = list == null || list.isEmpty();
        return bl;
    }

    static /* synthetic */ void lambda$configureSharedElementsOrdered$3(ArrayMap object, TransitionSet transitionSet, FragmentContainerTransition fragmentContainerTransition, ArrayList arrayList, View view, Fragment fragment, Fragment fragment2, boolean bl, ArrayList arrayList2, Transition transition2, Rect rect) {
        if ((object = FragmentTransition.captureInSharedElements((ArrayMap<String, String>)object, transitionSet, fragmentContainerTransition)) != null) {
            arrayList.addAll(((ArrayMap)object).values());
            arrayList.add(view);
        }
        FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, object, false);
        if (transitionSet != null) {
            transitionSet.getTargets().clear();
            transitionSet.getTargets().addAll(arrayList);
            FragmentTransition.replaceTargets(transitionSet, arrayList2, arrayList);
            object = FragmentTransition.getInEpicenterView(object, fragmentContainerTransition, transition2, bl);
            if (object != null) {
                ((View)object).getBoundsOnScreen(rect);
            }
        }
    }

    static /* synthetic */ void lambda$configureSharedElementsReordered$2(Fragment fragment, Fragment fragment2, boolean bl, ArrayMap arrayMap, View view, Rect rect) {
        FragmentTransition.callSharedElementStartEnd(fragment, fragment2, bl, arrayMap, false);
        if (view != null) {
            view.getBoundsOnScreen(rect);
        }
    }

    static /* synthetic */ void lambda$replaceHide$0(ArrayList arrayList) {
        FragmentTransition.setViewVisibility(arrayList, 4);
    }

    static /* synthetic */ void lambda$scheduleTargetChange$1(Transition cloneable, View view, Fragment fragment, ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, Transition transition2) {
        if (cloneable != null) {
            ((Transition)cloneable).removeTarget(view);
            arrayList2.addAll(FragmentTransition.configureEnteringExitingViews((Transition)cloneable, fragment, arrayList, view));
        }
        if (arrayList3 != null) {
            if (transition2 != null) {
                cloneable = new ArrayList();
                ((ArrayList)cloneable).add(view);
                FragmentTransition.replaceTargets(transition2, arrayList3, (ArrayList<View>)cloneable);
            }
            arrayList3.clear();
            arrayList3.add(view);
        }
    }

    private static Transition mergeTransitions(Transition transition2, Transition object, Transition transition3, Fragment object2, boolean bl) {
        block12 : {
            block11 : {
                boolean bl2;
                boolean bl3 = bl2 = true;
                if (transition2 != null) {
                    bl3 = bl2;
                    if (object != null) {
                        bl3 = bl2;
                        if (object2 != null) {
                            bl = bl ? ((Fragment)object2).getAllowReturnTransitionOverlap() : ((Fragment)object2).getAllowEnterTransitionOverlap();
                            bl3 = bl;
                        }
                    }
                }
                if (!bl3) break block11;
                object2 = new TransitionSet();
                if (transition2 != null) {
                    ((TransitionSet)object2).addTransition(transition2);
                }
                if (object != null) {
                    ((TransitionSet)object2).addTransition((Transition)object);
                }
                if (transition3 != null) {
                    ((TransitionSet)object2).addTransition(transition3);
                }
                object = object2;
                break block12;
            }
            object2 = null;
            if (object != null && transition2 != null) {
                object = new TransitionSet().addTransition((Transition)object).addTransition(transition2).setOrdering(1);
            } else if (object == null) {
                object = object2;
                if (transition2 != null) {
                    object = transition2;
                }
            }
            if (transition3 == null) break block12;
            transition2 = new TransitionSet();
            if (object != null) {
                ((TransitionSet)transition2).addTransition((Transition)object);
            }
            ((TransitionSet)transition2).addTransition(transition3);
            object = transition2;
        }
        return object;
    }

    private static void replaceHide(Transition transition2, Fragment fragment, final ArrayList<View> arrayList) {
        if (fragment != null && transition2 != null && fragment.mAdded && fragment.mHidden && fragment.mHiddenChanged) {
            fragment.setHideReplaced(true);
            View view = fragment.getView();
            OneShotPreDrawListener.add(fragment.mContainer, new _$$Lambda$FragmentTransition$PZ32bJ_FSMpbzYzBl8x73NJPidQ(arrayList));
            transition2.addListener(new TransitionListenerAdapter(){

                @Override
                public void onTransitionEnd(Transition transition2) {
                    transition2.removeListener(this);
                    View.this.setVisibility(8);
                    FragmentTransition.setViewVisibility(arrayList, 0);
                }
            });
        }
    }

    public static void replaceTargets(Transition transition2, ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        block4 : {
            List<View> list;
            block3 : {
                if (!(transition2 instanceof TransitionSet)) break block3;
                transition2 = (TransitionSet)transition2;
                int n = ((TransitionSet)transition2).getTransitionCount();
                for (int i = 0; i < n; ++i) {
                    FragmentTransition.replaceTargets(((TransitionSet)transition2).getTransitionAt(i), arrayList, arrayList2);
                }
                break block4;
            }
            if (FragmentTransition.hasSimpleTarget(transition2) || (list = transition2.getTargets()) == null || list.size() != arrayList.size() || !list.containsAll(arrayList)) break block4;
            int n = arrayList2 == null ? 0 : arrayList2.size();
            for (int i = 0; i < n; ++i) {
                transition2.addTarget(arrayList2.get(i));
            }
            for (n = arrayList.size() - 1; n >= 0; --n) {
                transition2.removeTarget(arrayList.get(n));
            }
        }
    }

    private static void retainValues(ArrayMap<String, String> arrayMap, ArrayMap<String, View> arrayMap2) {
        for (int i = arrayMap.size() - 1; i >= 0; --i) {
            if (arrayMap2.containsKey(arrayMap.valueAt(i))) continue;
            arrayMap.removeAt(i);
        }
    }

    private static void scheduleRemoveTargets(Transition transition2, Transition transition3, final ArrayList<View> arrayList, final Transition transition4, final ArrayList<View> arrayList2, final TransitionSet transitionSet, final ArrayList<View> arrayList3) {
        transition2.addListener(new TransitionListenerAdapter(){

            @Override
            public void onTransitionEnd(Transition transition2) {
                transition2.removeListener(this);
            }

            @Override
            public void onTransitionStart(Transition transition2) {
                transition2 = Transition.this;
                if (transition2 != null) {
                    FragmentTransition.replaceTargets(transition2, arrayList, null);
                }
                if ((transition2 = transition4) != null) {
                    FragmentTransition.replaceTargets(transition2, arrayList2, null);
                }
                if ((transition2 = transitionSet) != null) {
                    FragmentTransition.replaceTargets(transition2, arrayList3, null);
                }
            }
        });
    }

    private static void scheduleTargetChange(ViewGroup viewGroup, Fragment fragment, View view, ArrayList<View> arrayList, Transition transition2, ArrayList<View> arrayList2, Transition transition3, ArrayList<View> arrayList3) {
        OneShotPreDrawListener.add(viewGroup, new _$$Lambda$FragmentTransition$8Ei4ls5jlZcfRvuLcweFAxtFBFs(transition2, view, fragment, arrayList, arrayList2, arrayList3, transition3));
    }

    private static void setEpicenter(Transition transition2, View view) {
        if (view != null) {
            Rect rect = new Rect();
            view.getBoundsOnScreen(rect);
            transition2.setEpicenterCallback(new Transition.EpicenterCallback(){

                @Override
                public Rect onGetEpicenter(Transition transition2) {
                    return Rect.this;
                }
            });
        }
    }

    private static void setOutEpicenter(TransitionSet transitionSet, Transition transition2, ArrayMap<String, View> object, boolean bl, BackStackRecord object2) {
        if (((BackStackRecord)object2).mSharedElementSourceNames != null && !((BackStackRecord)object2).mSharedElementSourceNames.isEmpty()) {
            object2 = bl ? ((BackStackRecord)object2).mSharedElementTargetNames.get(0) : ((BackStackRecord)object2).mSharedElementSourceNames.get(0);
            object = ((ArrayMap)object).get(object2);
            FragmentTransition.setEpicenter(transitionSet, (View)object);
            if (transition2 != null) {
                FragmentTransition.setEpicenter(transition2, (View)object);
            }
        }
    }

    private static void setSharedElementTargets(TransitionSet transitionSet, View view, ArrayList<View> arrayList) {
        List<View> list = transitionSet.getTargets();
        list.clear();
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            FragmentTransition.bfsAddViewChildren(list, arrayList.get(i));
        }
        list.add(view);
        arrayList.add(view);
        FragmentTransition.addTargets(transitionSet, arrayList);
    }

    private static void setViewVisibility(ArrayList<View> arrayList, int n) {
        if (arrayList == null) {
            return;
        }
        for (int i = arrayList.size() - 1; i >= 0; --i) {
            arrayList.get(i).setVisibility(n);
        }
    }

    static void startTransitions(FragmentManagerImpl fragmentManagerImpl, ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2, int n, int n2, boolean bl) {
        int n3;
        Object object;
        if (fragmentManagerImpl.mCurState < 1) {
            return;
        }
        SparseArray<FragmentContainerTransition> sparseArray = new SparseArray<FragmentContainerTransition>();
        for (n3 = n; n3 < n2; ++n3) {
            object = arrayList.get(n3);
            if (arrayList2.get(n3).booleanValue()) {
                FragmentTransition.calculatePopFragments((BackStackRecord)object, sparseArray, bl);
                continue;
            }
            FragmentTransition.calculateFragments((BackStackRecord)object, sparseArray, bl);
        }
        if (sparseArray.size() != 0) {
            object = new View(fragmentManagerImpl.mHost.getContext());
            int n4 = sparseArray.size();
            for (n3 = 0; n3 < n4; ++n3) {
                int n5 = sparseArray.keyAt(n3);
                ArrayMap<String, String> arrayMap = FragmentTransition.calculateNameOverrides(n5, arrayList, arrayList2, n, n2);
                FragmentContainerTransition fragmentContainerTransition = sparseArray.valueAt(n3);
                if (bl) {
                    FragmentTransition.configureTransitionsReordered(fragmentManagerImpl, n5, fragmentContainerTransition, (View)object, arrayMap);
                    continue;
                }
                FragmentTransition.configureTransitionsOrdered(fragmentManagerImpl, n5, fragmentContainerTransition, (View)object, arrayMap);
            }
        }
    }

    public static class FragmentContainerTransition {
        public Fragment firstOut;
        public boolean firstOutIsPop;
        public BackStackRecord firstOutTransaction;
        public Fragment lastIn;
        public boolean lastInIsPop;
        public BackStackRecord lastInTransaction;
    }

}

