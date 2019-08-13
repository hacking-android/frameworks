/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewParent;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class GestureExclusionTracker {
    private List<Rect> mGestureExclusionRects = Collections.emptyList();
    private List<GestureExclusionViewInfo> mGestureExclusionViewInfos = new ArrayList<GestureExclusionViewInfo>();
    private boolean mGestureExclusionViewsChanged = false;
    private List<Rect> mRootGestureExclusionRects = Collections.emptyList();
    private boolean mRootGestureExclusionRectsChanged = false;

    GestureExclusionTracker() {
    }

    public List<Rect> computeChangedRects() {
        boolean bl = this.mRootGestureExclusionRectsChanged;
        Iterator<GestureExclusionViewInfo> iterator = this.mGestureExclusionViewInfos.iterator();
        ArrayList<Rect> arrayList = new ArrayList<Rect>(this.mRootGestureExclusionRects);
        while (iterator.hasNext()) {
            boolean bl2;
            block7 : {
                GestureExclusionViewInfo gestureExclusionViewInfo;
                block6 : {
                    block5 : {
                        gestureExclusionViewInfo = iterator.next();
                        int n = gestureExclusionViewInfo.update();
                        if (n == 0) break block5;
                        bl2 = bl;
                        if (n == 1) break block6;
                        if (n != 2) {
                            bl2 = bl;
                        } else {
                            this.mGestureExclusionViewsChanged = true;
                            iterator.remove();
                            bl2 = bl;
                        }
                        break block7;
                    }
                    bl2 = true;
                }
                arrayList.addAll(gestureExclusionViewInfo.mExclusionRects);
            }
            bl = bl2;
        }
        if (bl || this.mGestureExclusionViewsChanged) {
            this.mGestureExclusionViewsChanged = false;
            this.mRootGestureExclusionRectsChanged = false;
            if (!this.mGestureExclusionRects.equals(arrayList)) {
                this.mGestureExclusionRects = arrayList;
                return arrayList;
            }
        }
        return null;
    }

    public List<Rect> getRootSystemGestureExclusionRects() {
        return this.mRootGestureExclusionRects;
    }

    public void setRootSystemGestureExclusionRects(List<Rect> list) {
        Preconditions.checkNotNull(list, "rects must not be null");
        this.mRootGestureExclusionRects = list;
        this.mRootGestureExclusionRectsChanged = true;
    }

    public void updateRectsForView(View view) {
        boolean bl;
        boolean bl2 = false;
        Iterator<GestureExclusionViewInfo> iterator = this.mGestureExclusionViewInfos.iterator();
        do {
            bl = bl2;
            if (!iterator.hasNext()) break;
            GestureExclusionViewInfo gestureExclusionViewInfo = iterator.next();
            View view2 = gestureExclusionViewInfo.getView();
            if (view2 != null && view2.isAttachedToWindow()) {
                if (view2 != view) continue;
                bl = true;
                gestureExclusionViewInfo.mDirty = true;
                break;
            }
            this.mGestureExclusionViewsChanged = true;
            iterator.remove();
        } while (true);
        if (!bl && view.isAttachedToWindow()) {
            this.mGestureExclusionViewInfos.add(new GestureExclusionViewInfo(view));
            this.mGestureExclusionViewsChanged = true;
        }
    }

    private static class GestureExclusionViewInfo {
        public static final int CHANGED = 0;
        public static final int GONE = 2;
        public static final int UNCHANGED = 1;
        boolean mDirty = true;
        List<Rect> mExclusionRects = Collections.emptyList();
        private final WeakReference<View> mView;

        GestureExclusionViewInfo(View view) {
            this.mView = new WeakReference<View>(view);
        }

        public View getView() {
            return (View)this.mView.get();
        }

        public int update() {
            View view = this.getView();
            if (view != null && view.isAttachedToWindow()) {
                List<Rect> list = view.getSystemGestureExclusionRects();
                ArrayList<Rect> arrayList = new ArrayList<Rect>(list.size());
                Iterator<Rect> iterator = list.iterator();
                while (iterator.hasNext()) {
                    Rect rect = new Rect(iterator.next());
                    ViewParent viewParent = view.getParent();
                    if (viewParent == null || !viewParent.getChildVisibleRect(view, rect, null)) continue;
                    arrayList.add(rect);
                }
                if (this.mExclusionRects.equals(list)) {
                    return 1;
                }
                this.mExclusionRects = arrayList;
                return 0;
            }
            return 2;
        }
    }

}

