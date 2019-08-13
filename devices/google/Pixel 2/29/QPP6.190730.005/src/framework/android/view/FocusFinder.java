/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.view.-$
 *  android.view.-$$Lambda
 *  android.view.-$$Lambda$FocusFinder
 *  android.view.-$$Lambda$FocusFinder$P8rLvOJhymJH5ALAgUjGaM5gxKA
 *  android.view.-$$Lambda$FocusFinder$Pgx6IETuqCkrhJYdiBes48tolG4
 */
package android.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.view.-$;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view._$$Lambda$FocusFinder$FocusSorter$h0f2ZYL6peSaaEeCCkAoYs_YZvU;
import android.view._$$Lambda$FocusFinder$FocusSorter$kW7K1t9q7Y62V38r_7g6xRzqqq8;
import android.view._$$Lambda$FocusFinder$P8rLvOJhymJH5ALAgUjGaM5gxKA;
import android.view._$$Lambda$FocusFinder$Pgx6IETuqCkrhJYdiBes48tolG4;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class FocusFinder {
    private static final ThreadLocal<FocusFinder> tlFocusFinder = new ThreadLocal<FocusFinder>(){

        @Override
        protected FocusFinder initialValue() {
            return new FocusFinder();
        }
    };
    final Rect mBestCandidateRect = new Rect();
    private final FocusSorter mFocusSorter = new FocusSorter();
    final Rect mFocusedRect = new Rect();
    final Rect mOtherRect = new Rect();
    private final ArrayList<View> mTempList = new ArrayList();
    private final UserSpecifiedFocusComparator mUserSpecifiedClusterComparator = new UserSpecifiedFocusComparator((UserSpecifiedFocusComparator.NextFocusGetter)_$$Lambda$FocusFinder$P8rLvOJhymJH5ALAgUjGaM5gxKA.INSTANCE);
    private final UserSpecifiedFocusComparator mUserSpecifiedFocusComparator = new UserSpecifiedFocusComparator((UserSpecifiedFocusComparator.NextFocusGetter)_$$Lambda$FocusFinder$Pgx6IETuqCkrhJYdiBes48tolG4.INSTANCE);

    private FocusFinder() {
    }

    private View findNextFocus(ViewGroup view, View view2, Rect rect, int n) {
        ArrayList<View> arrayList = null;
        ViewGroup viewGroup = this.getEffectiveRoot((ViewGroup)view, view2);
        view = arrayList;
        if (view2 != null) {
            view = this.findNextUserSpecifiedFocus(viewGroup, view2, n);
        }
        if (view != null) {
            return view;
        }
        arrayList = this.mTempList;
        try {
            arrayList.clear();
            viewGroup.addFocusables(arrayList, n);
            if (!arrayList.isEmpty()) {
                view = this.findNextFocus(viewGroup, view2, rect, n, arrayList);
            }
            return view;
        }
        finally {
            arrayList.clear();
        }
    }

    private View findNextFocus(ViewGroup object, View view, Rect rect, int n, ArrayList<View> arrayList) {
        if (view != null) {
            if (rect == null) {
                rect = this.mFocusedRect;
            }
            view.getFocusedRect(rect);
            ((ViewGroup)object).offsetDescendantRectToMyCoords(view, rect);
        } else if (rect == null) {
            rect = this.mFocusedRect;
            if (n != 1) {
                if (n != 2) {
                    if (n != 17 && n != 33) {
                        if (n == 66 || n == 130) {
                            this.setFocusTopLeft((ViewGroup)object, rect);
                        }
                    } else {
                        this.setFocusBottomRight((ViewGroup)object, rect);
                    }
                } else if (((View)object).isLayoutRtl()) {
                    this.setFocusBottomRight((ViewGroup)object, rect);
                } else {
                    this.setFocusTopLeft((ViewGroup)object, rect);
                }
            } else if (((View)object).isLayoutRtl()) {
                this.setFocusTopLeft((ViewGroup)object, rect);
            } else {
                this.setFocusBottomRight((ViewGroup)object, rect);
            }
        }
        if (n != 1 && n != 2) {
            if (n != 17 && n != 33 && n != 66 && n != 130) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown direction: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return this.findNextFocusInAbsoluteDirection(arrayList, (ViewGroup)object, view, rect, n);
        }
        return this.findNextFocusInRelativeDirection(arrayList, (ViewGroup)object, view, rect, n);
    }

    private View findNextFocusInRelativeDirection(ArrayList<View> arrayList, ViewGroup viewGroup, View view, Rect rect, int n) {
        int n2;
        block3 : {
            block4 : {
                this.mUserSpecifiedFocusComparator.setFocusables(arrayList, viewGroup);
                Collections.sort(arrayList, this.mUserSpecifiedFocusComparator);
                n2 = arrayList.size();
                if (n == 1) break block3;
                if (n == 2) break block4;
                return arrayList.get(n2 - 1);
            }
            return FocusFinder.getNextFocusable(view, arrayList, n2);
        }
        return FocusFinder.getPreviousFocusable(view, arrayList, n2);
        finally {
            this.mUserSpecifiedFocusComparator.recycle();
        }
    }

    private View findNextKeyboardNavigationCluster(View object, View view, List<View> list, int n) {
        int n2;
        block3 : {
            block4 : {
                this.mUserSpecifiedClusterComparator.setFocusables(list, (View)object);
                Collections.sort(list, this.mUserSpecifiedClusterComparator);
                n2 = list.size();
                if (n == 1) break block3;
                if (n == 2) break block4;
                if (n == 17 || n == 33) break block3;
                if (n == 66 || n == 130) break block4;
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown direction: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return FocusFinder.getNextKeyboardNavigationCluster((View)object, view, list, n2);
        }
        return FocusFinder.getPreviousKeyboardNavigationCluster((View)object, view, list, n2);
        finally {
            this.mUserSpecifiedClusterComparator.recycle();
        }
    }

    private View findNextUserSpecifiedFocus(ViewGroup viewGroup, View view, int n) {
        View view2;
        view = view2 = view.findUserSetNextFocus(viewGroup, n);
        boolean bl = true;
        while (view2 != null) {
            if (view2.isFocusable() && view2.getVisibility() == 0 && (!view2.isInTouchMode() || view2.isFocusableInTouchMode())) {
                return view2;
            }
            View view3 = view2.findUserSetNextFocus(viewGroup, n);
            boolean bl2 = !bl;
            boolean bl3 = bl2;
            view2 = view3;
            bl = bl3;
            if (!bl2) continue;
            View view4 = view.findUserSetNextFocus(viewGroup, n);
            view2 = view3;
            view = view4;
            bl = bl3;
            if (view4 != view3) continue;
        }
        return null;
    }

    private View findNextUserSpecifiedKeyboardNavigationCluster(View view, View view2, int n) {
        if ((view = view2.findUserSetNextKeyboardNavigationCluster(view, n)) != null && view.hasFocusable()) {
            return view;
        }
        return null;
    }

    private ViewGroup getEffectiveRoot(ViewGroup viewGroup, View view) {
        if (view != null && view != viewGroup) {
            ViewParent viewParent;
            ViewParent viewParent2 = null;
            ViewParent viewParent3 = view.getParent();
            do {
                if (viewParent3 == viewGroup) {
                    if (viewParent2 != null) {
                        viewGroup = viewParent2;
                    }
                    return viewGroup;
                }
                viewParent = (ViewGroup)viewParent3;
                ViewParent viewParent4 = viewParent2;
                if (((ViewGroup)viewParent).getTouchscreenBlocksFocus()) {
                    viewParent4 = viewParent2;
                    if (view.getContext().getPackageManager().hasSystemFeature("android.hardware.touchscreen")) {
                        viewParent4 = viewParent2;
                        if (((View)((Object)viewParent)).isKeyboardNavigationCluster()) {
                            viewParent4 = viewParent;
                        }
                    }
                }
                viewParent = viewParent3.getParent();
                viewParent2 = viewParent4;
                viewParent3 = viewParent;
            } while (viewParent instanceof ViewGroup);
            return viewGroup;
        }
        return viewGroup;
    }

    public static FocusFinder getInstance() {
        return tlFocusFinder.get();
    }

    private static View getNextFocusable(View view, ArrayList<View> arrayList, int n) {
        int n2;
        if (view != null && (n2 = arrayList.lastIndexOf(view)) >= 0 && n2 + 1 < n) {
            return arrayList.get(n2 + 1);
        }
        if (!arrayList.isEmpty()) {
            return arrayList.get(0);
        }
        return null;
    }

    private static View getNextKeyboardNavigationCluster(View view, View view2, List<View> list, int n) {
        if (view2 == null) {
            return list.get(0);
        }
        int n2 = list.lastIndexOf(view2);
        if (n2 >= 0 && n2 + 1 < n) {
            return list.get(n2 + 1);
        }
        return view;
    }

    private static View getPreviousFocusable(View view, ArrayList<View> arrayList, int n) {
        int n2;
        if (view != null && (n2 = arrayList.indexOf(view)) > 0) {
            return arrayList.get(n2 - 1);
        }
        if (!arrayList.isEmpty()) {
            return arrayList.get(n - 1);
        }
        return null;
    }

    private static View getPreviousKeyboardNavigationCluster(View view, View view2, List<View> list, int n) {
        if (view2 == null) {
            return list.get(n - 1);
        }
        n = list.indexOf(view2);
        if (n > 0) {
            return list.get(n - 1);
        }
        return view;
    }

    private boolean isTouchCandidate(int n, int n2, Rect rect, int n3) {
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        if (n3 != 17) {
            if (n3 != 33) {
                if (n3 != 66) {
                    if (n3 == 130) {
                        if (rect.top < n2 || rect.left > n || n > rect.right) {
                            bl4 = false;
                        }
                        return bl4;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                bl4 = rect.left >= n && rect.top <= n2 && n2 <= rect.bottom ? bl : false;
                return bl4;
            }
            bl4 = rect.top <= n2 && rect.left <= n && n <= rect.right ? bl2 : false;
            return bl4;
        }
        bl4 = rect.left <= n && rect.top <= n2 && n2 <= rect.bottom ? bl3 : false;
        return bl4;
    }

    private static final boolean isValidId(int n) {
        boolean bl = n != 0 && n != -1;
        return bl;
    }

    static /* synthetic */ View lambda$new$0(View view, View view2) {
        view = FocusFinder.isValidId(view2.getNextFocusForwardId()) ? view2.findUserSetNextFocus(view, 2) : null;
        return view;
    }

    static /* synthetic */ View lambda$new$1(View view, View view2) {
        view = FocusFinder.isValidId(view2.getNextClusterForwardId()) ? view2.findUserSetNextKeyboardNavigationCluster(view, 2) : null;
        return view;
    }

    static int majorAxisDistance(int n, Rect rect, Rect rect2) {
        return Math.max(0, FocusFinder.majorAxisDistanceRaw(n, rect, rect2));
    }

    static int majorAxisDistanceRaw(int n, Rect rect, Rect rect2) {
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n == 130) {
                        return rect2.top - rect.bottom;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                return rect2.left - rect.right;
            }
            return rect.top - rect2.bottom;
        }
        return rect.left - rect2.right;
    }

    static int majorAxisDistanceToFarEdge(int n, Rect rect, Rect rect2) {
        return Math.max(1, FocusFinder.majorAxisDistanceToFarEdgeRaw(n, rect, rect2));
    }

    static int majorAxisDistanceToFarEdgeRaw(int n, Rect rect, Rect rect2) {
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n == 130) {
                        return rect2.bottom - rect.bottom;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                return rect2.right - rect.right;
            }
            return rect.top - rect2.top;
        }
        return rect.left - rect2.left;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static int minorAxisDistance(int n, Rect rect, Rect rect2) {
        if (n == 17) return Math.abs(rect.top + rect.height() / 2 - (rect2.top + rect2.height() / 2));
        if (n == 33) return Math.abs(rect.left + rect.width() / 2 - (rect2.left + rect2.width() / 2));
        if (n == 66) return Math.abs(rect.top + rect.height() / 2 - (rect2.top + rect2.height() / 2));
        if (n == 130) return Math.abs(rect.left + rect.width() / 2 - (rect2.left + rect2.width() / 2));
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    private void setFocusBottomRight(ViewGroup viewGroup, Rect rect) {
        int n = viewGroup.getScrollY() + viewGroup.getHeight();
        int n2 = viewGroup.getScrollX() + viewGroup.getWidth();
        rect.set(n2, n, n2, n);
    }

    private void setFocusTopLeft(ViewGroup viewGroup, Rect rect) {
        int n = viewGroup.getScrollY();
        int n2 = viewGroup.getScrollX();
        rect.set(n2, n, n2, n);
    }

    public static void sort(View[] arrview, int n, int n2, ViewGroup viewGroup, boolean bl) {
        FocusFinder.getInstance().mFocusSorter.sort(arrview, n, n2, viewGroup, bl);
    }

    boolean beamBeats(int n, Rect rect, Rect rect2, Rect rect3) {
        boolean bl = this.beamsOverlap(n, rect, rect2);
        boolean bl2 = this.beamsOverlap(n, rect, rect3);
        boolean bl3 = false;
        if (!bl2 && bl) {
            if (!this.isToDirectionOf(n, rect, rect3)) {
                return true;
            }
            if (n != 17 && n != 66) {
                if (FocusFinder.majorAxisDistance(n, rect, rect2) < FocusFinder.majorAxisDistanceToFarEdge(n, rect, rect3)) {
                    bl3 = true;
                }
                return bl3;
            }
            return true;
        }
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    boolean beamsOverlap(int n, Rect rect, Rect rect2) {
        boolean bl;
        block2 : {
            boolean bl2;
            block3 : {
                bl = true;
                bl2 = true;
                if (n == 17) break block2;
                if (n == 33) break block3;
                if (n == 66) break block2;
                if (n != 130) throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
            }
            if (rect2.right <= rect.left) return false;
            if (rect2.left >= rect.right) return false;
            return bl2;
        }
        if (rect2.bottom <= rect.top) return false;
        if (rect2.top >= rect.bottom) return false;
        return bl;
    }

    public View findNearestTouchable(ViewGroup viewGroup, int n, int n2, int n3, int[] arrn) {
        ArrayList<View> arrayList = viewGroup.getTouchables();
        int n4 = Integer.MAX_VALUE;
        View view = null;
        int n5 = arrayList.size();
        int n6 = ViewConfiguration.get(viewGroup.mContext).getScaledEdgeSlop();
        Rect rect = new Rect();
        Rect rect2 = this.mOtherRect;
        for (int i = 0; i < n5; ++i) {
            int n7;
            View view2;
            block19 : {
                int n8;
                View view3;
                block20 : {
                    block18 : {
                        view3 = arrayList.get(i);
                        view3.getDrawingRect(rect2);
                        viewGroup.offsetRectBetweenParentAndChild(view3, rect2, true, true);
                        if (this.isTouchCandidate(n, n2, rect2, n3)) break block18;
                        n7 = n4;
                        view2 = view;
                        break block19;
                    }
                    n8 = Integer.MAX_VALUE;
                    if (n3 != 17) {
                        if (n3 != 33) {
                            if (n3 != 66) {
                                if (n3 == 130) {
                                    n8 = rect2.top;
                                }
                            } else {
                                n8 = rect2.left;
                            }
                        } else {
                            n8 = n2 - rect2.bottom + 1;
                        }
                    } else {
                        n8 = n - rect2.right + 1;
                    }
                    n7 = n4;
                    view2 = view;
                    if (n8 >= n6) break block19;
                    if (view == null || rect.contains(rect2)) break block20;
                    n7 = n4;
                    view2 = view;
                    if (rect2.contains(rect)) break block19;
                    n7 = n4;
                    view2 = view;
                    if (n8 >= n4) break block19;
                }
                n7 = n8;
                view2 = view3;
                rect.set(rect2);
                if (n3 != 17) {
                    if (n3 != 33) {
                        if (n3 != 66) {
                            if (n3 == 130) {
                                arrn[1] = n8;
                            }
                        } else {
                            arrn[0] = n8;
                        }
                    } else {
                        arrn[1] = -n8;
                    }
                } else {
                    arrn[0] = -n8;
                }
            }
            n4 = n7;
            view = view2;
        }
        return view;
    }

    public final View findNextFocus(ViewGroup viewGroup, View view, int n) {
        return this.findNextFocus(viewGroup, view, null, n);
    }

    public View findNextFocusFromRect(ViewGroup viewGroup, Rect rect, int n) {
        this.mFocusedRect.set(rect);
        return this.findNextFocus(viewGroup, null, this.mFocusedRect, n);
    }

    View findNextFocusInAbsoluteDirection(ArrayList<View> arrayList, ViewGroup viewGroup, View view, Rect rect, int n) {
        this.mBestCandidateRect.set(rect);
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n == 130) {
                        this.mBestCandidateRect.offset(0, -(rect.height() + 1));
                    }
                } else {
                    this.mBestCandidateRect.offset(-(rect.width() + 1), 0);
                }
            } else {
                this.mBestCandidateRect.offset(0, rect.height() + 1);
            }
        } else {
            this.mBestCandidateRect.offset(rect.width() + 1, 0);
        }
        View view2 = null;
        int n2 = arrayList.size();
        for (int i = 0; i < n2; ++i) {
            View view3 = arrayList.get(i);
            View view4 = view2;
            if (view3 != view) {
                if (view3 == viewGroup) {
                    view4 = view2;
                } else {
                    view3.getFocusedRect(this.mOtherRect);
                    viewGroup.offsetDescendantRectToMyCoords(view3, this.mOtherRect);
                    view4 = view2;
                    if (this.isBetterCandidate(n, rect, this.mOtherRect, this.mBestCandidateRect)) {
                        this.mBestCandidateRect.set(this.mOtherRect);
                        view4 = view3;
                    }
                }
            }
            view2 = view4;
        }
        return view2;
    }

    public View findNextKeyboardNavigationCluster(View view, View view2, int n) {
        Object object;
        Object object2 = null;
        if (view2 != null) {
            object2 = object = this.findNextUserSpecifiedKeyboardNavigationCluster(view, view2, n);
            if (object != null) {
                return object;
            }
        }
        object = this.mTempList;
        try {
            ((ArrayList)object).clear();
            view.addKeyboardNavigationClusters((Collection<View>)object, n);
            if (!((ArrayList)object).isEmpty()) {
                object2 = this.findNextKeyboardNavigationCluster(view, view2, (List<View>)object, n);
            }
            return object2;
        }
        finally {
            ((ArrayList)object).clear();
        }
    }

    long getWeightedDistanceFor(long l, long l2) {
        return 13L * l * l + l2 * l2;
    }

    boolean isBetterCandidate(int n, Rect rect, Rect rect2, Rect rect3) {
        boolean bl = this.isCandidate(rect, rect2, n);
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (!this.isCandidate(rect, rect3, n)) {
            return true;
        }
        if (this.beamBeats(n, rect, rect2, rect3)) {
            return true;
        }
        if (this.beamBeats(n, rect, rect3, rect2)) {
            return false;
        }
        if (this.getWeightedDistanceFor(FocusFinder.majorAxisDistance(n, rect, rect2), FocusFinder.minorAxisDistance(n, rect, rect2)) < this.getWeightedDistanceFor(FocusFinder.majorAxisDistance(n, rect, rect3), FocusFinder.minorAxisDistance(n, rect, rect3))) {
            bl2 = true;
        }
        return bl2;
    }

    boolean isCandidate(Rect rect, Rect rect2, int n) {
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n == 130) {
                        if (rect.top >= rect2.top && rect.bottom > rect2.top || rect.bottom >= rect2.bottom) {
                            bl4 = false;
                        }
                        return bl4;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                bl4 = (rect.left < rect2.left || rect.right <= rect2.left) && rect.right < rect2.right ? bl : false;
                return bl4;
            }
            bl4 = (rect.bottom > rect2.bottom || rect.top >= rect2.bottom) && rect.top > rect2.top ? bl2 : false;
            return bl4;
        }
        bl4 = (rect.right > rect2.right || rect.left >= rect2.right) && rect.left > rect2.left ? bl3 : false;
        return bl4;
    }

    boolean isToDirectionOf(int n, Rect rect, Rect rect2) {
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        if (n != 17) {
            if (n != 33) {
                if (n != 66) {
                    if (n == 130) {
                        if (rect.bottom > rect2.top) {
                            bl4 = false;
                        }
                        return bl4;
                    }
                    throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                }
                bl4 = rect.right <= rect2.left ? bl : false;
                return bl4;
            }
            bl4 = rect.top >= rect2.bottom ? bl2 : false;
            return bl4;
        }
        bl4 = rect.left >= rect2.right ? bl3 : false;
        return bl4;
    }

    static final class FocusSorter {
        private int mLastPoolRect;
        private HashMap<View, Rect> mRectByView = null;
        private ArrayList<Rect> mRectPool = new ArrayList();
        private int mRtlMult;
        private Comparator<View> mSidesComparator = new _$$Lambda$FocusFinder$FocusSorter$h0f2ZYL6peSaaEeCCkAoYs_YZvU(this);
        private Comparator<View> mTopsComparator = new _$$Lambda$FocusFinder$FocusSorter$kW7K1t9q7Y62V38r_7g6xRzqqq8(this);

        FocusSorter() {
        }

        public /* synthetic */ int lambda$new$0$FocusFinder$FocusSorter(View object, View object2) {
            if (object == object2) {
                return 0;
            }
            object = this.mRectByView.get(object);
            object2 = this.mRectByView.get(object2);
            int n = ((Rect)object).top - ((Rect)object2).top;
            if (n == 0) {
                return ((Rect)object).bottom - ((Rect)object2).bottom;
            }
            return n;
        }

        public /* synthetic */ int lambda$new$1$FocusFinder$FocusSorter(View object, View object2) {
            if (object == object2) {
                return 0;
            }
            object = this.mRectByView.get(object);
            object2 = this.mRectByView.get(object2);
            int n = ((Rect)object).left - ((Rect)object2).left;
            if (n == 0) {
                return ((Rect)object).right - ((Rect)object2).right;
            }
            return this.mRtlMult * n;
        }

        public void sort(View[] arrview, int n, int n2, ViewGroup object, boolean bl) {
            int n3;
            int n4 = n2 - n;
            if (n4 < 2) {
                return;
            }
            if (this.mRectByView == null) {
                this.mRectByView = new HashMap();
            }
            int n5 = bl ? -1 : 1;
            this.mRtlMult = n5;
            for (n5 = this.mRectPool.size(); n5 < n4; ++n5) {
                this.mRectPool.add(new Rect());
            }
            for (n5 = n; n5 < n2; ++n5) {
                Object object2 = this.mRectPool;
                n3 = this.mLastPoolRect;
                this.mLastPoolRect = n3 + 1;
                object2 = ((ArrayList)object2).get(n3);
                arrview[n5].getDrawingRect((Rect)object2);
                ((ViewGroup)object).offsetDescendantRectToMyCoords(arrview[n5], (Rect)object2);
                this.mRectByView.put(arrview[n5], (Rect)object2);
            }
            Arrays.sort(arrview, n, n4, this.mTopsComparator);
            n3 = this.mRectByView.get((Object)arrview[n]).bottom;
            n5 = n++;
            while (n < n2) {
                object = this.mRectByView.get(arrview[n]);
                if (((Rect)object).top >= n3) {
                    if (n - n5 > 1) {
                        Arrays.sort(arrview, n5, n, this.mSidesComparator);
                    }
                    n5 = ((Rect)object).bottom;
                    n4 = n;
                } else {
                    n3 = Math.max(n3, ((Rect)object).bottom);
                    n4 = n5;
                    n5 = n3;
                }
                ++n;
                n3 = n5;
                n5 = n4;
            }
            if (n - n5 > 1) {
                Arrays.sort(arrview, n5, n, this.mSidesComparator);
            }
            this.mLastPoolRect = 0;
            this.mRectByView.clear();
        }
    }

    private static final class UserSpecifiedFocusComparator
    implements Comparator<View> {
        private final ArrayMap<View, View> mHeadsOfChains = new ArrayMap();
        private final ArraySet<View> mIsConnectedTo = new ArraySet();
        private final ArrayMap<View, View> mNextFoci = new ArrayMap();
        private final NextFocusGetter mNextFocusGetter;
        private final ArrayMap<View, Integer> mOriginalOrdinal = new ArrayMap();
        private View mRoot;

        UserSpecifiedFocusComparator(NextFocusGetter nextFocusGetter) {
            this.mNextFocusGetter = nextFocusGetter;
        }

        private void setHeadOfChain(View view) {
            View view2 = view;
            while (view2 != null) {
                View view3 = this.mHeadsOfChains.get(view2);
                View view4 = view;
                if (view3 != null) {
                    if (view3 == view) {
                        return;
                    }
                    view4 = view3;
                    view2 = view;
                }
                this.mHeadsOfChains.put(view2, view4);
                view2 = this.mNextFoci.get(view2);
                view = view4;
            }
        }

        @Override
        public int compare(View view, View view2) {
            if (view == view2) {
                return 0;
            }
            View view3 = this.mHeadsOfChains.get(view);
            View view4 = this.mHeadsOfChains.get(view2);
            int n = 1;
            if (view3 == view4 && view3 != null) {
                if (view == view3) {
                    return -1;
                }
                if (view2 == view3) {
                    return 1;
                }
                if (this.mNextFoci.get(view) != null) {
                    return -1;
                }
                return 1;
            }
            int n2 = 0;
            if (view3 != null) {
                view = view3;
                n2 = 1;
            }
            if (view4 != null) {
                view2 = view4;
                n2 = 1;
            }
            if (n2 != 0) {
                n2 = n;
                if (this.mOriginalOrdinal.get(view) < this.mOriginalOrdinal.get(view2)) {
                    n2 = -1;
                }
                return n2;
            }
            return 0;
        }

        public void recycle() {
            this.mRoot = null;
            this.mHeadsOfChains.clear();
            this.mIsConnectedTo.clear();
            this.mOriginalOrdinal.clear();
            this.mNextFoci.clear();
        }

        public void setFocusables(List<View> list, View view) {
            int n;
            this.mRoot = view;
            for (n = 0; n < list.size(); ++n) {
                this.mOriginalOrdinal.put(list.get(n), n);
            }
            for (n = list.size() - 1; n >= 0; --n) {
                View view2 = list.get(n);
                view = this.mNextFocusGetter.get(this.mRoot, view2);
                if (view == null || !this.mOriginalOrdinal.containsKey(view)) continue;
                this.mNextFoci.put(view2, view);
                this.mIsConnectedTo.add(view);
            }
            for (n = list.size() - 1; n >= 0; --n) {
                view = list.get(n);
                if (this.mNextFoci.get(view) == null || this.mIsConnectedTo.contains(view)) continue;
                this.setHeadOfChain(view);
            }
        }

        public static interface NextFocusGetter {
            public View get(View var1, View var2);
        }

    }

}

