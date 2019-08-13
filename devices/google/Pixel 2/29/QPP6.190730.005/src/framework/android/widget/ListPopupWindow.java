/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.DropDownListView;
import android.widget.ForwardingListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import com.android.internal.R;
import com.android.internal.view.menu.ShowableListMenu;

public class ListPopupWindow
implements ShowableListMenu {
    private static final boolean DEBUG = false;
    private static final int EXPAND_LIST_TIMEOUT = 250;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    private static final String TAG = "ListPopupWindow";
    public static final int WRAP_CONTENT = -2;
    private ListAdapter mAdapter;
    private Context mContext;
    private boolean mDropDownAlwaysVisible = false;
    private View mDropDownAnchorView;
    private int mDropDownGravity = 0;
    private int mDropDownHeight = -2;
    private int mDropDownHorizontalOffset;
    @UnsupportedAppUsage
    private DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth = -2;
    private int mDropDownWindowLayoutType = 1002;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch = false;
    private final Handler mHandler;
    private final ListSelectorHider mHideSelector = new ListSelectorHider();
    private boolean mIsAnimatedFromAnchor = true;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    int mListItemExpandMaximum = Integer.MAX_VALUE;
    private boolean mModal;
    private DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    @UnsupportedAppUsage
    PopupWindow mPopup;
    private int mPromptPosition = 0;
    private View mPromptView;
    private final ResizePopupRunnable mResizePopupRunnable = new ResizePopupRunnable();
    private final PopupScrollListener mScrollListener = new PopupScrollListener();
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect = new Rect();
    private final PopupTouchInterceptor mTouchInterceptor = new PopupTouchInterceptor();

    public ListPopupWindow(Context context) {
        this(context, null, 16843519, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843519, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int n, int n2) {
        this.mContext = context;
        this.mHandler = new Handler(context.getMainLooper());
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, n, n2);
        this.mDropDownHorizontalOffset = typedArray.getDimensionPixelOffset(0, 0);
        this.mDropDownVerticalOffset = typedArray.getDimensionPixelOffset(1, 0);
        if (this.mDropDownVerticalOffset != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        typedArray.recycle();
        this.mPopup = new PopupWindow(context, attributeSet, n, n2);
        this.mPopup.setInputMethodMode(1);
    }

    @UnsupportedAppUsage
    private int buildDropDown() {
        int n;
        int n2 = 0;
        int n3 = 0;
        Object object = this.mDropDownList;
        boolean bl = false;
        if (object == null) {
            Object object2 = this.mContext;
            this.mShowDropDownRunnable = new Runnable(){

                @Override
                public void run() {
                    View view = ListPopupWindow.this.getAnchorView();
                    if (view != null && view.getWindowToken() != null) {
                        ListPopupWindow.this.show();
                    }
                }
            };
            this.mDropDownList = this.createDropDownListView((Context)object2, this.mModal ^ true);
            object = this.mDropDownListHighlight;
            if (object != null) {
                this.mDropDownList.setSelector((Drawable)object);
            }
            this.mDropDownList.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                @Override
                public void onItemSelected(AdapterView<?> dropDownListView, View view, int n, long l) {
                    if (n != -1 && (dropDownListView = ListPopupWindow.this.mDropDownList) != null) {
                        dropDownListView.setListSelectionHidden(false);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            this.mDropDownList.setOnScrollListener(this.mScrollListener);
            object = this.mItemSelectedListener;
            if (object != null) {
                this.mDropDownList.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)object);
            }
            Object object3 = this.mDropDownList;
            View view = this.mPromptView;
            object = object3;
            if (view != null) {
                object = new LinearLayout((Context)object2);
                ((LinearLayout)object).setOrientation(1);
                object2 = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                n3 = this.mPromptPosition;
                if (n3 != 0) {
                    if (n3 != 1) {
                        object3 = new StringBuilder();
                        ((StringBuilder)object3).append("Invalid hint position ");
                        ((StringBuilder)object3).append(this.mPromptPosition);
                        Log.e(TAG, ((StringBuilder)object3).toString());
                    } else {
                        ((ViewGroup)object).addView((View)object3, (ViewGroup.LayoutParams)object2);
                        ((ViewGroup)object).addView(view);
                    }
                } else {
                    ((ViewGroup)object).addView(view);
                    ((ViewGroup)object).addView((View)object3, (ViewGroup.LayoutParams)object2);
                }
                if (this.mDropDownWidth >= 0) {
                    n3 = Integer.MIN_VALUE;
                    n2 = this.mDropDownWidth;
                } else {
                    n3 = 0;
                    n2 = 0;
                }
                view.measure(View.MeasureSpec.makeMeasureSpec(n2, n3), 0);
                object3 = (LinearLayout.LayoutParams)view.getLayoutParams();
                n2 = view.getMeasuredHeight();
                n = ((LinearLayout.LayoutParams)object3).topMargin;
                n3 = ((LinearLayout.LayoutParams)object3).bottomMargin;
                n3 = n2 + n + n3;
            }
            this.mPopup.setContentView((View)object);
        } else {
            object = this.mPromptView;
            n3 = n2;
            if (object != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)((View)object).getLayoutParams();
                n3 = ((View)object).getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
        }
        object = this.mPopup.getBackground();
        if (object != null) {
            ((Drawable)object).getPadding(this.mTempRect);
            n = n2 = this.mTempRect.top + this.mTempRect.bottom;
            if (!this.mDropDownVerticalOffsetSet) {
                this.mDropDownVerticalOffset = -this.mTempRect.top;
                n = n2;
            }
        } else {
            this.mTempRect.setEmpty();
            n = 0;
        }
        if (this.mPopup.getInputMethodMode() == 2) {
            bl = true;
        }
        int n4 = this.mPopup.getMaxAvailableHeight(this.getAnchorView(), this.mDropDownVerticalOffset, bl);
        if (!this.mDropDownAlwaysVisible && this.mDropDownHeight != -1) {
            n2 = this.mDropDownWidth;
            n2 = n2 != -2 ? (n2 != -1 ? View.MeasureSpec.makeMeasureSpec(n2, 1073741824) : View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), 1073741824)) : View.MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right), Integer.MIN_VALUE);
            n4 = this.mDropDownList.measureHeightOfChildren(n2, 0, -1, n4 - n3, -1);
            n2 = n3;
            if (n4 > 0) {
                n2 = n3 + (n + (this.mDropDownList.getPaddingTop() + this.mDropDownList.getPaddingBottom()));
            }
            return n4 + n2;
        }
        return n4 + n;
    }

    private void removePromptView() {
        Object object = this.mPromptView;
        if (object != null && (object = ((View)object).getParent()) instanceof ViewGroup) {
            ((ViewGroup)object).removeView(this.mPromptView);
        }
    }

    public void clearListSelection() {
        DropDownListView dropDownListView = this.mDropDownList;
        if (dropDownListView != null) {
            dropDownListView.setListSelectionHidden(true);
            dropDownListView.hideSelector();
            dropDownListView.requestLayout();
        }
    }

    public View.OnTouchListener createDragToOpenListener(View view) {
        return new ForwardingListener(view){

            @Override
            public ShowableListMenu getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    DropDownListView createDropDownListView(Context context, boolean bl) {
        return new DropDownListView(context, bl);
    }

    @Override
    public void dismiss() {
        this.mPopup.dismiss();
        this.removePromptView();
        this.mPopup.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks(this.mResizePopupRunnable);
    }

    public View getAnchorView() {
        return this.mDropDownAnchorView;
    }

    public int getAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public Drawable getBackground() {
        return this.mPopup.getBackground();
    }

    public Rect getEpicenterBounds() {
        Rect rect = this.mEpicenterBounds;
        rect = rect != null ? new Rect(rect) : null;
        return rect;
    }

    public int getHeight() {
        return this.mDropDownHeight;
    }

    public int getHorizontalOffset() {
        return this.mDropDownHorizontalOffset;
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    @Override
    public ListView getListView() {
        return this.mDropDownList;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    public Object getSelectedItem() {
        if (!this.isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedItem();
    }

    public long getSelectedItemId() {
        if (!this.isShowing()) {
            return Long.MIN_VALUE;
        }
        return this.mDropDownList.getSelectedItemId();
    }

    public int getSelectedItemPosition() {
        if (!this.isShowing()) {
            return -1;
        }
        return this.mDropDownList.getSelectedItemPosition();
    }

    public View getSelectedView() {
        if (!this.isShowing()) {
            return null;
        }
        return this.mDropDownList.getSelectedView();
    }

    public int getSoftInputMode() {
        return this.mPopup.getSoftInputMode();
    }

    public int getVerticalOffset() {
        if (!this.mDropDownVerticalOffsetSet) {
            return 0;
        }
        return this.mDropDownVerticalOffset;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    @UnsupportedAppUsage
    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public boolean isInputMethodNotNeeded() {
        boolean bl = this.mPopup.getInputMethodMode() == 2;
        return bl;
    }

    public boolean isModal() {
        return this.mModal;
    }

    @Override
    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (this.isShowing() && n != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !KeyEvent.isConfirmKey(n))) {
            int n2 = this.mDropDownList.getSelectedItemPosition();
            boolean bl = this.mPopup.isAboveAnchor() ^ true;
            ListAdapter listAdapter = this.mAdapter;
            int n3 = Integer.MAX_VALUE;
            int n4 = Integer.MIN_VALUE;
            if (listAdapter != null) {
                boolean bl2 = listAdapter.areAllItemsEnabled();
                n4 = bl2 ? 0 : this.mDropDownList.lookForSelectablePosition(0, true);
                n3 = n4;
                n4 = bl2 ? listAdapter.getCount() - 1 : this.mDropDownList.lookForSelectablePosition(listAdapter.getCount() - 1, false);
            }
            if (bl && n == 19 && n2 <= n3 || !bl && n == 20 && n2 >= n4) {
                this.clearListSelection();
                this.mPopup.setInputMethodMode(1);
                this.show();
                return true;
            }
            this.mDropDownList.setListSelectionHidden(false);
            if (this.mDropDownList.onKeyDown(n, keyEvent)) {
                this.mPopup.setInputMethodMode(2);
                this.mDropDownList.requestFocusFromTouch();
                this.show();
                if (n == 19 || n == 20 || n == 23 || n == 66) {
                    return true;
                }
            } else if (bl && n == 20 ? n2 == n4 : !bl && n == 19 && n2 == n3) {
                return true;
            }
        }
        return false;
    }

    public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
        if (n == 4 && this.isShowing()) {
            Object object = this.mDropDownAnchorView;
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                if ((object = ((View)object).getKeyDispatcherState()) != null) {
                    ((KeyEvent.DispatcherState)object).startTracking(keyEvent, this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1) {
                if ((object = ((View)object).getKeyDispatcherState()) != null) {
                    ((KeyEvent.DispatcherState)object).handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    this.dismiss();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (this.isShowing() && this.mDropDownList.getSelectedItemPosition() >= 0) {
            boolean bl = this.mDropDownList.onKeyUp(n, keyEvent);
            if (bl && KeyEvent.isConfirmKey(n)) {
                this.dismiss();
            }
            return bl;
        }
        return false;
    }

    public boolean performItemClick(int n) {
        if (this.isShowing()) {
            if (this.mItemClickListener != null) {
                DropDownListView dropDownListView = this.mDropDownList;
                View view = dropDownListView.getChildAt(n - dropDownListView.getFirstVisiblePosition());
                Adapter adapter = dropDownListView.getAdapter();
                this.mItemClickListener.onItemClick(dropDownListView, view, n, adapter.getItemId(n));
            }
            return true;
        }
        return false;
    }

    public void postShow() {
        this.mHandler.post(this.mShowDropDownRunnable);
    }

    public void setAdapter(ListAdapter object) {
        DataSetObserver dataSetObserver = this.mObserver;
        if (dataSetObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else {
            ListAdapter listAdapter = this.mAdapter;
            if (listAdapter != null) {
                listAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.mAdapter = object;
        if (this.mAdapter != null) {
            object.registerDataSetObserver(this.mObserver);
        }
        if ((object = this.mDropDownList) != null) {
            ((ListView)object).setAdapter(this.mAdapter);
        }
    }

    public void setAnchorView(View view) {
        this.mDropDownAnchorView = view;
    }

    public void setAnimationStyle(int n) {
        this.mPopup.setAnimationStyle(n);
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        this.mPopup.setBackgroundDrawable(drawable2);
    }

    public void setContentWidth(int n) {
        Drawable drawable2 = this.mPopup.getBackground();
        if (drawable2 != null) {
            drawable2.getPadding(this.mTempRect);
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + n;
        } else {
            this.setWidth(n);
        }
    }

    @UnsupportedAppUsage
    public void setDropDownAlwaysVisible(boolean bl) {
        this.mDropDownAlwaysVisible = bl;
    }

    public void setDropDownGravity(int n) {
        this.mDropDownGravity = n;
    }

    public void setEpicenterBounds(Rect rect) {
        rect = rect != null ? new Rect(rect) : null;
        this.mEpicenterBounds = rect;
    }

    @UnsupportedAppUsage
    public void setForceIgnoreOutsideTouch(boolean bl) {
        this.mForceIgnoreOutsideTouch = bl;
    }

    public void setHeight(int n) {
        if (n < 0 && -2 != n && -1 != n) {
            if (this.mContext.getApplicationInfo().targetSdkVersion < 26) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Negative value ");
                stringBuilder.append(n);
                stringBuilder.append(" passed to ListPopupWindow#setHeight produces undefined results");
                Log.e(TAG, stringBuilder.toString());
            } else {
                throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
            }
        }
        this.mDropDownHeight = n;
    }

    public void setHorizontalOffset(int n) {
        this.mDropDownHorizontalOffset = n;
    }

    public void setInputMethodMode(int n) {
        this.mPopup.setInputMethodMode(n);
    }

    @UnsupportedAppUsage
    void setListItemExpandMax(int n) {
        this.mListItemExpandMaximum = n;
    }

    public void setListSelector(Drawable drawable2) {
        this.mDropDownListHighlight = drawable2;
    }

    public void setModal(boolean bl) {
        this.mModal = bl;
        this.mPopup.setFocusable(bl);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mPopup.setOnDismissListener(onDismissListener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener;
    }

    public void setOverlapAnchor(boolean bl) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = bl;
    }

    public void setPromptPosition(int n) {
        this.mPromptPosition = n;
    }

    public void setPromptView(View view) {
        boolean bl = this.isShowing();
        if (bl) {
            this.removePromptView();
        }
        this.mPromptView = view;
        if (bl) {
            this.show();
        }
    }

    public void setSelection(int n) {
        DropDownListView dropDownListView = this.mDropDownList;
        if (this.isShowing() && dropDownListView != null) {
            dropDownListView.setListSelectionHidden(false);
            dropDownListView.setSelection(n);
            if (dropDownListView.getChoiceMode() != 0) {
                dropDownListView.setItemChecked(n, true);
            }
        }
    }

    public void setSoftInputMode(int n) {
        this.mPopup.setSoftInputMode(n);
    }

    public void setVerticalOffset(int n) {
        this.mDropDownVerticalOffset = n;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setWidth(int n) {
        this.mDropDownWidth = n;
    }

    public void setWindowLayoutType(int n) {
        this.mDropDownWindowLayoutType = n;
    }

    @Override
    public void show() {
        int n = this.buildDropDown();
        boolean bl = this.isInputMethodNotNeeded();
        this.mPopup.setAllowScrollingAnchorParent(bl ^ true);
        this.mPopup.setWindowLayoutType(this.mDropDownWindowLayoutType);
        boolean bl2 = this.mPopup.isShowing();
        boolean bl3 = true;
        boolean bl4 = true;
        if (bl2) {
            Object object;
            if (!this.getAnchorView().isAttachedToWindow()) {
                return;
            }
            int n2 = this.mDropDownWidth;
            n2 = n2 == -1 ? -1 : (n2 == -2 ? this.getAnchorView().getWidth() : this.mDropDownWidth);
            int n3 = this.mDropDownHeight;
            if (n3 == -1) {
                if (!bl) {
                    n = -1;
                }
                if (bl) {
                    object = this.mPopup;
                    n3 = this.mDropDownWidth == -1 ? -1 : 0;
                    ((PopupWindow)object).setWidth(n3);
                    this.mPopup.setHeight(0);
                } else {
                    object = this.mPopup;
                    n3 = this.mDropDownWidth == -1 ? -1 : 0;
                    ((PopupWindow)object).setWidth(n3);
                    this.mPopup.setHeight(-1);
                }
            } else if (n3 != -2) {
                n = this.mDropDownHeight;
            }
            object = this.mPopup;
            if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
                bl4 = false;
            }
            ((PopupWindow)object).setOutsideTouchable(bl4);
            PopupWindow popupWindow = this.mPopup;
            object = this.getAnchorView();
            int n4 = this.mDropDownHorizontalOffset;
            n3 = this.mDropDownVerticalOffset;
            if (n2 < 0) {
                n2 = -1;
            }
            if (n < 0) {
                n = -1;
            }
            popupWindow.update((View)object, n4, n3, n2, n);
            this.mPopup.getContentView().restoreDefaultFocus();
        } else {
            int n5 = this.mDropDownWidth;
            n5 = n5 == -1 ? -1 : (n5 == -2 ? this.getAnchorView().getWidth() : this.mDropDownWidth);
            int n6 = this.mDropDownHeight;
            if (n6 == -1) {
                n = -1;
            } else if (n6 != -2) {
                n = this.mDropDownHeight;
            }
            this.mPopup.setWidth(n5);
            this.mPopup.setHeight(n);
            this.mPopup.setIsClippedToScreen(true);
            PopupWindow popupWindow = this.mPopup;
            bl4 = !this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible ? bl3 : false;
            popupWindow.setOutsideTouchable(bl4);
            this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
            this.mPopup.setEpicenterBounds(this.mEpicenterBounds);
            if (this.mOverlapAnchorSet) {
                this.mPopup.setOverlapAnchor(this.mOverlapAnchor);
            }
            this.mPopup.showAsDropDown(this.getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
            this.mDropDownList.setSelection(-1);
            this.mPopup.getContentView().restoreDefaultFocus();
            if (!this.mModal || this.mDropDownList.isInTouchMode()) {
                this.clearListSelection();
            }
            if (!this.mModal) {
                this.mHandler.post(this.mHideSelector);
            }
        }
    }

    private class ListSelectorHider
    implements Runnable {
        private ListSelectorHider() {
        }

        @Override
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class PopupDataSetObserver
    extends DataSetObserver {
        private PopupDataSetObserver() {
        }

        @Override
        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override
        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class PopupScrollListener
    implements AbsListView.OnScrollListener {
        private PopupScrollListener() {
        }

        @Override
        public void onScroll(AbsListView absListView, int n, int n2, int n3) {
        }

        @Override
        public void onScrollStateChanged(AbsListView absListView, int n) {
            if (n == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.mPopup.getContentView() != null) {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
                ListPopupWindow.this.mResizePopupRunnable.run();
            }
        }
    }

    private class PopupTouchInterceptor
    implements View.OnTouchListener {
        private PopupTouchInterceptor() {
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int n = motionEvent.getAction();
            int n2 = (int)motionEvent.getX();
            int n3 = (int)motionEvent.getY();
            if (n == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && n2 >= 0 && n2 < ListPopupWindow.this.mPopup.getWidth() && n3 >= 0 && n3 < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250L);
            } else if (n == 1) {
                ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
            }
            return false;
        }
    }

    private class ResizePopupRunnable
    implements Runnable {
        private ResizePopupRunnable() {
        }

        @Override
        public void run() {
            if (ListPopupWindow.this.mDropDownList != null && ListPopupWindow.this.mDropDownList.isAttachedToWindow() && ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount() && ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
                ListPopupWindow.this.mPopup.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

}

