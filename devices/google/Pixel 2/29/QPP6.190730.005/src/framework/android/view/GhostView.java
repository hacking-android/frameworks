/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RecordingCanvas;
import android.graphics.RenderNode;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.widget.FrameLayout;
import java.util.ArrayList;

public class GhostView
extends View {
    private boolean mBeingMoved;
    private int mReferences;
    private final View mView;

    private GhostView(View view) {
        super(view.getContext());
        view = this.mView = view;
        view.mGhostView = this;
        view = (ViewGroup)view.getParent();
        this.mView.setTransitionVisibility(4);
        view.invalidate();
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public static GhostView addGhost(View view, ViewGroup viewGroup) {
        return GhostView.addGhost(view, viewGroup, null);
    }

    @UnsupportedAppUsage
    public static GhostView addGhost(View object, ViewGroup object2, Matrix object3) {
        if (((View)object).getParent() instanceof ViewGroup) {
            ViewGroupOverlay viewGroupOverlay = ((ViewGroup)object2).getOverlay();
            ViewOverlay.OverlayViewGroup overlayViewGroup = viewGroupOverlay.mOverlayViewGroup;
            GhostView ghostView = ((View)object).mGhostView;
            int n = 0;
            Object object4 = ghostView;
            int n2 = n;
            if (ghostView != null) {
                View view = (View)((Object)ghostView.getParent());
                ViewGroup viewGroup = (ViewGroup)view.getParent();
                object4 = ghostView;
                n2 = n;
                if (viewGroup != overlayViewGroup) {
                    n2 = ghostView.mReferences;
                    viewGroup.removeView(view);
                    object4 = null;
                }
            }
            if (object4 == null) {
                object4 = object3;
                if (object3 == null) {
                    object4 = new Matrix();
                    GhostView.calculateMatrix((View)object, (ViewGroup)object2, (Matrix)object4);
                }
                object3 = new GhostView((View)object);
                ((GhostView)object3).setMatrix((Matrix)object4);
                object = new FrameLayout(((View)object).getContext());
                ((ViewGroup)object).setClipChildren(false);
                GhostView.copySize((View)object2, (View)object);
                GhostView.copySize((View)object2, (View)object3);
                ((ViewGroup)object).addView((View)object3);
                object2 = new ArrayList();
                n = GhostView.moveGhostViewsToTop(viewGroupOverlay.mOverlayViewGroup, (ArrayList<View>)object2);
                GhostView.insertIntoOverlay(viewGroupOverlay.mOverlayViewGroup, (ViewGroup)object, (GhostView)object3, (ArrayList<View>)object2, n);
                ((GhostView)object3).mReferences = n2;
                object = object3;
            } else {
                object = object4;
                if (object3 != null) {
                    ((GhostView)object4).setMatrix((Matrix)object3);
                    object = object4;
                }
            }
            ++((GhostView)object).mReferences;
            return object;
        }
        throw new IllegalArgumentException("Ghosted views must be parented by a ViewGroup");
    }

    public static void calculateMatrix(View view, ViewGroup viewGroup, Matrix matrix) {
        view = (ViewGroup)view.getParent();
        matrix.reset();
        view.transformMatrixToGlobal(matrix);
        matrix.preTranslate(-view.getScrollX(), -view.getScrollY());
        viewGroup.transformMatrixToLocal(matrix);
    }

    private static void copySize(View view, View view2) {
        view2.setLeft(0);
        view2.setTop(0);
        view2.setRight(view.getWidth());
        view2.setBottom(view.getHeight());
    }

    public static GhostView getGhost(View view) {
        return view.mGhostView;
    }

    private static int getInsertIndex(ViewGroup viewGroup, ArrayList<View> arrayList, ArrayList<View> arrayList2, int n) {
        int n2 = n;
        n = viewGroup.getChildCount() - 1;
        while (n2 <= n) {
            int n3 = (n2 + n) / 2;
            GhostView.getParents(((GhostView)((ViewGroup)viewGroup.getChildAt((int)n3)).getChildAt((int)0)).mView, arrayList2);
            if (GhostView.isOnTop(arrayList, arrayList2)) {
                n2 = n3 + 1;
            } else {
                n = n3 - 1;
            }
            arrayList2.clear();
        }
        return n2;
    }

    private static void getParents(View view, ArrayList<View> arrayList) {
        ViewParent viewParent = view.getParent();
        if (viewParent != null && viewParent instanceof ViewGroup) {
            GhostView.getParents((View)((Object)viewParent), arrayList);
        }
        arrayList.add(view);
    }

    private static void insertIntoOverlay(ViewGroup viewGroup, ViewGroup viewGroup2, GhostView ghostView, ArrayList<View> arrayList, int n) {
        if (n == -1) {
            viewGroup.addView(viewGroup2);
        } else {
            ArrayList<View> arrayList2 = new ArrayList<View>();
            GhostView.getParents(ghostView.mView, arrayList2);
            n = GhostView.getInsertIndex(viewGroup, arrayList2, arrayList, n);
            if (n >= 0 && n < viewGroup.getChildCount()) {
                viewGroup.addView((View)viewGroup2, n);
            } else {
                viewGroup.addView(viewGroup2);
            }
        }
    }

    private static boolean isGhostWrapper(View view) {
        if (view instanceof FrameLayout && ((ViewGroup)(view = (FrameLayout)view)).getChildCount() == 1) {
            return ((ViewGroup)view).getChildAt(0) instanceof GhostView;
        }
        return false;
    }

    private static boolean isOnTop(View view, View view2) {
        boolean bl;
        ViewGroup viewGroup = (ViewGroup)view.getParent();
        int n = viewGroup.getChildCount();
        ArrayList<View> arrayList = viewGroup.buildOrderedChildList();
        boolean bl2 = arrayList == null && viewGroup.isChildrenDrawingOrderEnabled();
        boolean bl3 = true;
        int n2 = 0;
        do {
            bl = bl3;
            if (n2 >= n) break;
            int n3 = bl2 ? viewGroup.getChildDrawingOrder(n, n2) : n2;
            View view3 = arrayList == null ? viewGroup.getChildAt(n3) : arrayList.get(n3);
            if (view3 == view) {
                bl = false;
                break;
            }
            if (view3 == view2) {
                bl = true;
                break;
            }
            ++n2;
        } while (true);
        if (arrayList != null) {
            arrayList.clear();
        }
        return bl;
    }

    private static boolean isOnTop(ArrayList<View> arrayList, ArrayList<View> arrayList2) {
        if (!arrayList.isEmpty() && !arrayList2.isEmpty()) {
            boolean bl = false;
            if (arrayList.get(0) == arrayList2.get(0)) {
                int n = Math.min(arrayList.size(), arrayList2.size());
                for (int i = 1; i < n; ++i) {
                    View view;
                    View view2 = arrayList.get(i);
                    if (view2 == (view = arrayList2.get(i))) continue;
                    return GhostView.isOnTop(view2, view);
                }
                if (arrayList2.size() == n) {
                    bl = true;
                }
                return bl;
            }
        }
        return true;
    }

    private static int moveGhostViewsToTop(ViewGroup viewGroup, ArrayList<View> arrayList) {
        int n = viewGroup.getChildCount();
        if (n == 0) {
            return -1;
        }
        if (GhostView.isGhostWrapper(viewGroup.getChildAt(n - 1))) {
            int n2 = n - 1;
            n -= 2;
            while (n >= 0 && GhostView.isGhostWrapper(viewGroup.getChildAt(n))) {
                n2 = n--;
            }
            return n2;
        }
        n -= 2;
        while (n >= 0) {
            View view = viewGroup.getChildAt(n);
            if (GhostView.isGhostWrapper(view)) {
                arrayList.add(view);
                view = (GhostView)((ViewGroup)view).getChildAt(0);
                ((GhostView)view).mBeingMoved = true;
                viewGroup.removeViewAt(n);
                ((GhostView)view).mBeingMoved = false;
            }
            --n;
        }
        if (arrayList.isEmpty()) {
            n = -1;
        } else {
            int n3 = viewGroup.getChildCount();
            for (n = arrayList.size() - 1; n >= 0; --n) {
                viewGroup.addView(arrayList.get(n));
            }
            arrayList.clear();
            n = n3;
        }
        return n;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public static void removeGhost(View view) {
        view = view.mGhostView;
        if (view != null) {
            --((GhostView)view).mReferences;
            if (((GhostView)view).mReferences == 0) {
                view = (ViewGroup)view.getParent();
                ((ViewGroup)view.getParent()).removeView(view);
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!this.mBeingMoved) {
            this.mView.setTransitionVisibility(0);
            View view = this.mView;
            view.mGhostView = null;
            view = (ViewGroup)view.getParent();
            if (view != null) {
                view.invalidate();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (canvas instanceof RecordingCanvas) {
            canvas = (RecordingCanvas)canvas;
            Object object = this.mView;
            ((View)object).mRecreateDisplayList = true;
            if (((RenderNode)(object = ((View)object).updateDisplayListIfDirty())).hasDisplayList()) {
                canvas.insertReorderBarrier();
                ((RecordingCanvas)canvas).drawRenderNode((RenderNode)object);
                canvas.insertInorderBarrier();
            }
        }
    }

    public void setMatrix(Matrix matrix) {
        this.mRenderNode.setAnimationMatrix(matrix);
    }

    @Override
    public void setVisibility(int n) {
        super.setVisibility(n);
        if (this.mView.mGhostView == this) {
            n = n == 0 ? 4 : 0;
            this.mView.setTransitionVisibility(n);
        }
    }
}

