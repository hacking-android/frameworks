/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.widget.AutoScrollHelper;

public class DropDownListView
extends ListView {
    private boolean mDrawsInPressedState;
    private boolean mHijackFocus;
    private boolean mListSelectionHidden;
    private ResolveHoverRunnable mResolveHoverRunnable;
    private AutoScrollHelper.AbsListViewAutoScroller mScrollHelper;

    public DropDownListView(Context context, boolean bl) {
        this(context, bl, 16842861);
    }

    public DropDownListView(Context context, boolean bl, int n) {
        super(context, null, n);
        this.mHijackFocus = bl;
        this.setCacheColorHint(0);
    }

    private void clearPressedItem() {
        this.mDrawsInPressedState = false;
        this.setPressed(false);
        this.updateSelectorState();
        View view = this.getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (view != null) {
            view.setPressed(false);
        }
    }

    private void setPressedItem(View view, int n, float f, float f2) {
        View view2;
        this.mDrawsInPressedState = true;
        this.drawableHotspotChanged(f, f2);
        if (!this.isPressed()) {
            this.setPressed(true);
        }
        if (this.mDataChanged) {
            this.layoutChildren();
        }
        if ((view2 = this.getChildAt(this.mMotionPosition - this.mFirstPosition)) != null && view2 != view && view2.isPressed()) {
            view2.setPressed(false);
        }
        this.mMotionPosition = n;
        view.drawableHotspotChanged(f - (float)view.getLeft(), f2 - (float)view.getTop());
        if (!view.isPressed()) {
            view.setPressed(true);
        }
        this.setSelectedPositionInt(n);
        this.positionSelectorLikeTouch(n, view, f, f2);
        this.refreshDrawableState();
    }

    @Override
    protected void drawableStateChanged() {
        if (this.mResolveHoverRunnable == null) {
            super.drawableStateChanged();
        }
    }

    @Override
    public boolean hasFocus() {
        boolean bl = this.mHijackFocus || super.hasFocus();
        return bl;
    }

    @Override
    public boolean hasWindowFocus() {
        boolean bl = this.mHijackFocus || super.hasWindowFocus();
        return bl;
    }

    @Override
    public boolean isFocused() {
        boolean bl = this.mHijackFocus || super.isFocused();
        return bl;
    }

    @Override
    public boolean isInTouchMode() {
        boolean bl = this.mHijackFocus && this.mListSelectionHidden || super.isInTouchMode();
        return bl;
    }

    @Override
    View obtainView(int n, boolean[] object) {
        if ((object = super.obtainView(n, (boolean[])object)) instanceof TextView) {
            ((TextView)object).setHorizontallyScrolling(true);
        }
        return object;
    }

    public boolean onForwardedEvent(MotionEvent object, int n) {
        boolean bl;
        block14 : {
            int n2;
            int n3;
            int n4;
            boolean bl2;
            block13 : {
                block12 : {
                    bl2 = true;
                    bl = true;
                    n2 = 0;
                    n4 = ((MotionEvent)object).getActionMasked();
                    if (n4 == 1) break block12;
                    if (n4 == 2) break block13;
                    if (n4 != 3) {
                        bl = bl2;
                        n = n2;
                    } else {
                        bl = false;
                        n = n2;
                    }
                    break block14;
                }
                bl = false;
            }
            if ((n3 = ((MotionEvent)object).findPointerIndex(n)) < 0) {
                bl = false;
                n = n2;
            } else {
                n = (int)((MotionEvent)object).getX(n3);
                int n5 = (int)((MotionEvent)object).getY(n3);
                n3 = this.pointToPosition(n, n5);
                if (n3 == -1) {
                    n = 1;
                } else {
                    View view = this.getChildAt(n3 - this.getFirstVisiblePosition());
                    this.setPressedItem(view, n3, n, n5);
                    bl = bl2 = true;
                    n = n2;
                    if (n4 == 1) {
                        this.performItemClick(view, n3, this.getItemIdAtPosition(n3));
                        n = n2;
                        bl = bl2;
                    }
                }
            }
        }
        if (!bl || n != 0) {
            this.clearPressedItem();
        }
        if (bl) {
            if (this.mScrollHelper == null) {
                this.mScrollHelper = new AutoScrollHelper.AbsListViewAutoScroller(this);
            }
            this.mScrollHelper.setEnabled(true);
            this.mScrollHelper.onTouch(this, (MotionEvent)object);
        } else {
            object = this.mScrollHelper;
            if (object != null) {
                ((AutoScrollHelper)object).setEnabled(false);
            }
        }
        return bl;
    }

    @Override
    public boolean onHoverEvent(MotionEvent object) {
        int n = ((MotionEvent)object).getActionMasked();
        if (n == 10 && this.mResolveHoverRunnable == null) {
            this.mResolveHoverRunnable = new ResolveHoverRunnable();
            this.mResolveHoverRunnable.post();
        }
        boolean bl = super.onHoverEvent((MotionEvent)object);
        if (n != 9 && n != 7) {
            if (!super.shouldShowSelector()) {
                this.setSelectedPositionInt(-1);
                this.setNextSelectedPositionInt(-1);
            }
        } else {
            n = this.pointToPosition((int)((MotionEvent)object).getX(), (int)((MotionEvent)object).getY());
            if (n != -1 && n != this.mSelectedPosition) {
                object = this.getChildAt(n - this.getFirstVisiblePosition());
                if (((View)object).isEnabled()) {
                    this.requestFocus();
                    this.positionSelector(n, (View)object);
                    this.setSelectedPositionInt(n);
                    this.setNextSelectedPositionInt(n);
                }
                this.updateSelectorState();
            }
        }
        return bl;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ResolveHoverRunnable resolveHoverRunnable = this.mResolveHoverRunnable;
        if (resolveHoverRunnable != null) {
            resolveHoverRunnable.cancel();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setListSelectionHidden(boolean bl) {
        this.mListSelectionHidden = bl;
    }

    @Override
    boolean shouldShowSelector() {
        boolean bl = this.isHovered() || super.shouldShowSelector();
        return bl;
    }

    @Override
    boolean touchModeDrawsInPressedState() {
        boolean bl = this.mDrawsInPressedState || super.touchModeDrawsInPressedState();
        return bl;
    }

    private class ResolveHoverRunnable
    implements Runnable {
        private ResolveHoverRunnable() {
        }

        public void cancel() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.removeCallbacks(this);
        }

        public void post() {
            DropDownListView.this.post(this);
        }

        @Override
        public void run() {
            DropDownListView.this.mResolveHoverRunnable = null;
            DropDownListView.this.drawableStateChanged();
        }
    }

}

