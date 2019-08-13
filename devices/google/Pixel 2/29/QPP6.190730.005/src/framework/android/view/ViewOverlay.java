/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.LayoutTransition;
import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import java.util.ArrayList;
import java.util.Iterator;

public class ViewOverlay {
    OverlayViewGroup mOverlayViewGroup;

    ViewOverlay(Context context, View view) {
        this.mOverlayViewGroup = new OverlayViewGroup(context, view);
    }

    public void add(Drawable drawable2) {
        this.mOverlayViewGroup.add(drawable2);
    }

    public void clear() {
        this.mOverlayViewGroup.clear();
    }

    @UnsupportedAppUsage
    ViewGroup getOverlayView() {
        return this.mOverlayViewGroup;
    }

    @UnsupportedAppUsage
    boolean isEmpty() {
        return this.mOverlayViewGroup.isEmpty();
    }

    public void remove(Drawable drawable2) {
        this.mOverlayViewGroup.remove(drawable2);
    }

    static class OverlayViewGroup
    extends ViewGroup {
        ArrayList<Drawable> mDrawables = null;
        final View mHostView;

        OverlayViewGroup(Context context, View view) {
            super(context);
            this.mHostView = view;
            this.mAttachInfo = this.mHostView.mAttachInfo;
            this.mRight = view.getWidth();
            this.mBottom = view.getHeight();
            this.mRenderNode.setLeftTopRightBottom(0, 0, this.mRight, this.mBottom);
        }

        public void add(Drawable drawable2) {
            if (drawable2 != null) {
                if (this.mDrawables == null) {
                    this.mDrawables = new ArrayList();
                }
                if (!this.mDrawables.contains(drawable2)) {
                    this.mDrawables.add(drawable2);
                    this.invalidate(drawable2.getBounds());
                    drawable2.setCallback(this);
                }
                return;
            }
            throw new IllegalArgumentException("drawable must be non-null");
        }

        public void add(View view) {
            if (view != null) {
                if (view.getParent() instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup)view.getParent();
                    if (viewGroup != this.mHostView && viewGroup.getParent() != null && viewGroup.mAttachInfo != null) {
                        int[] arrn = new int[2];
                        int[] arrn2 = new int[2];
                        viewGroup.getLocationOnScreen(arrn);
                        this.mHostView.getLocationOnScreen(arrn2);
                        view.offsetLeftAndRight(arrn[0] - arrn2[0]);
                        view.offsetTopAndBottom(arrn[1] - arrn2[1]);
                    }
                    viewGroup.removeView(view);
                    if (viewGroup.getLayoutTransition() != null) {
                        viewGroup.getLayoutTransition().cancel(3);
                    }
                    if (view.getParent() != null) {
                        view.mParent = null;
                    }
                }
                super.addView(view);
                return;
            }
            throw new IllegalArgumentException("view must be non-null");
        }

        public void clear() {
            this.removeAllViews();
            Object object = this.mDrawables;
            if (object != null) {
                object = ((ArrayList)object).iterator();
                while (object.hasNext()) {
                    ((Drawable)object.next()).setCallback(null);
                }
                this.mDrawables.clear();
            }
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            canvas.insertReorderBarrier();
            super.dispatchDraw(canvas);
            canvas.insertInorderBarrier();
            ArrayList<Drawable> arrayList = this.mDrawables;
            int n = arrayList == null ? 0 : arrayList.size();
            for (int i = 0; i < n; ++i) {
                this.mDrawables.get(i).draw(canvas);
            }
        }

        @Override
        public void invalidate() {
            super.invalidate();
            View view = this.mHostView;
            if (view != null) {
                view.invalidate();
            }
        }

        @Override
        public void invalidate(int n, int n2, int n3, int n4) {
            super.invalidate(n, n2, n3, n4);
            View view = this.mHostView;
            if (view != null) {
                view.invalidate(n, n2, n3, n4);
            }
        }

        @Override
        public void invalidate(Rect rect) {
            super.invalidate(rect);
            View view = this.mHostView;
            if (view != null) {
                view.invalidate(rect);
            }
        }

        @Override
        public void invalidate(boolean bl) {
            super.invalidate(bl);
            View view = this.mHostView;
            if (view != null) {
                view.invalidate(bl);
            }
        }

        @Override
        public ViewParent invalidateChildInParent(int[] arrn, Rect rect) {
            if (this.mHostView != null) {
                rect.offset(arrn[0], arrn[1]);
                if (this.mHostView instanceof ViewGroup) {
                    arrn[0] = 0;
                    arrn[1] = 0;
                    super.invalidateChildInParent(arrn, rect);
                    return ((ViewGroup)this.mHostView).invalidateChildInParent(arrn, rect);
                }
                this.invalidate(rect);
            }
            return null;
        }

        @Override
        public void invalidateDrawable(Drawable drawable2) {
            this.invalidate(drawable2.getBounds());
        }

        @Override
        protected void invalidateParentCaches() {
            super.invalidateParentCaches();
            View view = this.mHostView;
            if (view != null) {
                view.invalidateParentCaches();
            }
        }

        @Override
        protected void invalidateParentIfNeeded() {
            super.invalidateParentIfNeeded();
            View view = this.mHostView;
            if (view != null) {
                view.invalidateParentIfNeeded();
            }
        }

        @Override
        void invalidateViewProperty(boolean bl, boolean bl2) {
            super.invalidateViewProperty(bl, bl2);
            View view = this.mHostView;
            if (view != null) {
                view.invalidateViewProperty(bl, bl2);
            }
        }

        boolean isEmpty() {
            ArrayList<Drawable> arrayList;
            return this.getChildCount() == 0 && ((arrayList = this.mDrawables) == null || arrayList.size() == 0);
        }

        @Override
        public void onDescendantInvalidated(View view, View view2) {
            View view3 = this.mHostView;
            if (view3 != null) {
                if (view3 instanceof ViewGroup) {
                    ((ViewGroup)view3).onDescendantInvalidated(view3, view2);
                    super.onDescendantInvalidated(view, view2);
                } else {
                    this.invalidate();
                }
            }
        }

        @Override
        protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        }

        public void remove(Drawable drawable2) {
            if (drawable2 != null) {
                ArrayList<Drawable> arrayList = this.mDrawables;
                if (arrayList != null) {
                    arrayList.remove(drawable2);
                    this.invalidate(drawable2.getBounds());
                    drawable2.setCallback(null);
                }
                return;
            }
            throw new IllegalArgumentException("drawable must be non-null");
        }

        public void remove(View view) {
            if (view != null) {
                super.removeView(view);
                return;
            }
            throw new IllegalArgumentException("view must be non-null");
        }

        @Override
        protected boolean verifyDrawable(Drawable drawable2) {
            ArrayList<Drawable> arrayList;
            boolean bl = super.verifyDrawable(drawable2) || (arrayList = this.mDrawables) != null && arrayList.contains(drawable2);
            return bl;
        }
    }

}

