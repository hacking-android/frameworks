/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.AbsSavedState;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AbsListView;
import android.widget.AbsSpinner;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ForwardingListener;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SpinnerAdapter;
import android.widget.ThemedSpinnerAdapter;
import com.android.internal.R;
import com.android.internal.view.menu.ShowableListMenu;

public class Spinner
extends AbsSpinner
implements DialogInterface.OnClickListener {
    private static final int MAX_ITEMS_MEASURED = 15;
    public static final int MODE_DIALOG = 0;
    public static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "Spinner";
    private boolean mDisableChildrenWhenDisabled;
    int mDropDownWidth;
    @UnsupportedAppUsage
    private ForwardingListener mForwardingListener;
    private int mGravity;
    @UnsupportedAppUsage
    private SpinnerPopup mPopup;
    private final Context mPopupContext;
    private SpinnerAdapter mTempAdapter;
    private final Rect mTempRect = new Rect();

    public Spinner(Context context) {
        this(context, null);
    }

    public Spinner(Context context, int n) {
        this(context, null, 16842881, n);
    }

    public Spinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842881);
    }

    public Spinner(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0, -1);
    }

    public Spinner(Context context, AttributeSet attributeSet, int n, int n2) {
        this(context, attributeSet, n, 0, n2);
    }

    public Spinner(Context context, AttributeSet attributeSet, int n, int n2, int n3) {
        this(context, attributeSet, n, n2, n3, null);
    }

    public Spinner(Context object, AttributeSet object2, int n, int n2, int n3, Resources.Theme theme) {
        super((Context)object, (AttributeSet)object2, n, n2);
        int n4;
        TypedArray typedArray = ((Context)object).obtainStyledAttributes((AttributeSet)object2, R.styleable.Spinner, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.Spinner, (AttributeSet)object2, typedArray, n, n2);
        this.mPopupContext = theme != null ? new ContextThemeWrapper((Context)object, theme) : ((n4 = typedArray.getResourceId(7, 0)) != 0 ? new ContextThemeWrapper((Context)object, n4) : object);
        if (n3 == -1) {
            n3 = typedArray.getInt(5, 0);
        }
        if (n3 != 0) {
            if (n3 == 1) {
                object = new DropdownPopup(this.mPopupContext, (AttributeSet)object2, n, n2);
                object2 = this.mPopupContext.obtainStyledAttributes((AttributeSet)object2, R.styleable.Spinner, n, n2);
                this.mDropDownWidth = ((TypedArray)object2).getLayoutDimension(4, -2);
                if (((TypedArray)object2).hasValueOrEmpty(1)) {
                    ((ListPopupWindow)object).setListSelector(((TypedArray)object2).getDrawable(1));
                }
                ((ListPopupWindow)object).setBackgroundDrawable(((TypedArray)object2).getDrawable(2));
                ((DropdownPopup)object).setPromptText(typedArray.getString(3));
                ((TypedArray)object2).recycle();
                this.mPopup = object;
                this.mForwardingListener = new ForwardingListener(this, (DropdownPopup)object){
                    final /* synthetic */ DropdownPopup val$popup;
                    {
                        this.val$popup = dropdownPopup;
                        super(view);
                    }

                    @Override
                    public ShowableListMenu getPopup() {
                        return this.val$popup;
                    }

                    @Override
                    public boolean onForwardingStarted() {
                        if (!Spinner.this.mPopup.isShowing()) {
                            Spinner.this.mPopup.show(Spinner.this.getTextDirection(), Spinner.this.getTextAlignment());
                        }
                        return true;
                    }
                };
            }
        } else {
            this.mPopup = new DialogPopup();
            this.mPopup.setPromptText(typedArray.getString(3));
        }
        this.mGravity = typedArray.getInt(0, 17);
        this.mDisableChildrenWhenDisabled = typedArray.getBoolean(8, false);
        typedArray.recycle();
        object = this.mTempAdapter;
        if (object != null) {
            this.setAdapter((SpinnerAdapter)object);
            this.mTempAdapter = null;
        }
    }

    static /* synthetic */ Context access$500(Spinner spinner) {
        return spinner.mContext;
    }

    private View makeView(int n, boolean bl) {
        View view;
        if (!this.mDataChanged && (view = this.mRecycler.get(n)) != null) {
            this.setUpChild(view, bl);
            return view;
        }
        view = this.mAdapter.getView(n, null, this);
        this.setUpChild(view, bl);
        return view;
    }

    private void setUpChild(View view, boolean bl) {
        ViewGroup.LayoutParams layoutParams;
        ViewGroup.LayoutParams layoutParams2 = layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams2 = this.generateDefaultLayoutParams();
        }
        this.addViewInLayout(view, 0, layoutParams2);
        view.setSelected(this.hasFocus());
        if (this.mDisableChildrenWhenDisabled) {
            view.setEnabled(this.isEnabled());
        }
        int n = ViewGroup.getChildMeasureSpec(this.mHeightMeasureSpec, this.mSpinnerPadding.top + this.mSpinnerPadding.bottom, layoutParams2.height);
        view.measure(ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, this.mSpinnerPadding.left + this.mSpinnerPadding.right, layoutParams2.width), n);
        int n2 = this.mSpinnerPadding.top + (this.getMeasuredHeight() - this.mSpinnerPadding.bottom - this.mSpinnerPadding.top - view.getMeasuredHeight()) / 2;
        n = view.getMeasuredHeight();
        view.layout(0, n2, 0 + view.getMeasuredWidth(), n + n2);
        if (!bl) {
            this.removeViewInLayout(view);
        }
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return Spinner.class.getName();
    }

    @Override
    public int getBaseline() {
        View view;
        View view2 = null;
        if (this.getChildCount() > 0) {
            view = this.getChildAt(0);
        } else {
            view = view2;
            if (this.mAdapter != null) {
                view = view2;
                if (this.mAdapter.getCount() > 0) {
                    view = this.makeView(0, false);
                    this.mRecycler.put(0, view);
                }
            }
        }
        int n = -1;
        if (view != null) {
            int n2 = view.getBaseline();
            if (n2 >= 0) {
                n = view.getTop() + n2;
            }
            return n;
        }
        return -1;
    }

    public int getDropDownHorizontalOffset() {
        return this.mPopup.getHorizontalOffset();
    }

    public int getDropDownVerticalOffset() {
        return this.mPopup.getVerticalOffset();
    }

    public int getDropDownWidth() {
        return this.mDropDownWidth;
    }

    public int getGravity() {
        return this.mGravity;
    }

    public Drawable getPopupBackground() {
        return this.mPopup.getBackground();
    }

    public Context getPopupContext() {
        return this.mPopupContext;
    }

    public CharSequence getPrompt() {
        return this.mPopup.getHintText();
    }

    public boolean isPopupShowing() {
        SpinnerPopup spinnerPopup = this.mPopup;
        boolean bl = spinnerPopup != null && spinnerPopup.isShowing();
        return bl;
    }

    @Override
    void layout(int n, boolean bl) {
        int n2 = this.mSpinnerPadding.left;
        int n3 = this.mRight - this.mLeft - this.mSpinnerPadding.left - this.mSpinnerPadding.right;
        if (this.mDataChanged) {
            this.handleDataChanged();
        }
        if (this.mItemCount == 0) {
            this.resetList();
            return;
        }
        if (this.mNextSelectedPosition >= 0) {
            this.setSelectedPositionInt(this.mNextSelectedPosition);
        }
        this.recycleAllViews();
        this.removeAllViewsInLayout();
        this.mFirstPosition = this.mSelectedPosition;
        if (this.mAdapter != null) {
            View view = this.makeView(this.mSelectedPosition, true);
            int n4 = view.getMeasuredWidth();
            n = n2;
            int n5 = this.getLayoutDirection();
            if ((n5 = Gravity.getAbsoluteGravity(this.mGravity, n5) & 7) != 1) {
                if (n5 == 5) {
                    n = n2 + n3 - n4;
                }
            } else {
                n = n3 / 2 + n2 - n4 / 2;
            }
            view.offsetLeftAndRight(n);
        }
        this.mRecycler.clear();
        this.invalidate();
        this.checkSelectionChanged();
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.setNextSelectedPositionInt(this.mSelectedPosition);
    }

    int measureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable2) {
        if (spinnerAdapter == null) {
            return 0;
        }
        int n = 0;
        View view = null;
        int n2 = 0;
        int n3 = View.MeasureSpec.makeSafeMeasureSpec(this.getMeasuredWidth(), 0);
        int n4 = View.MeasureSpec.makeSafeMeasureSpec(this.getMeasuredHeight(), 0);
        int n5 = Math.max(0, this.getSelectedItemPosition());
        int n6 = Math.min(spinnerAdapter.getCount(), n5 + 15);
        for (n5 = Math.max((int)0, (int)(n5 - (15 - (n6 - n5)))); n5 < n6; ++n5) {
            int n7 = spinnerAdapter.getItemViewType(n5);
            int n8 = n2;
            if (n7 != n2) {
                n8 = n7;
                view = null;
            }
            if ((view = spinnerAdapter.getView(n5, view, this)).getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(n3, n4);
            n = Math.max(n, view.getMeasuredWidth());
            n2 = n8;
        }
        n5 = n;
        if (drawable2 != null) {
            drawable2.getPadding(this.mTempRect);
            n5 = n + (this.mTempRect.left + this.mTempRect.right);
        }
        return n5;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int n) {
        this.setSelection(n);
        dialogInterface.dismiss();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null && spinnerPopup.isShowing()) {
            this.mPopup.dismiss();
        }
    }

    @Override
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (this.mAdapter != null) {
            accessibilityNodeInfo.setCanOpenPopup(true);
        }
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        this.mInLayout = true;
        this.layout(0, false);
        this.mInLayout = false;
    }

    @Override
    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mPopup != null && View.MeasureSpec.getMode(n) == Integer.MIN_VALUE) {
            n2 = this.getMeasuredWidth();
            this.setMeasuredDimension(Math.min(Math.max(n2, this.measureContentWidth((SpinnerAdapter)this.getAdapter(), this.getBackground())), View.MeasureSpec.getSize(n)), this.getMeasuredHeight());
        }
    }

    @Override
    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        if (this.getPointerIcon() == null && this.isClickable() && this.isEnabled()) {
            return PointerIcon.getSystemIcon(this.getContext(), 1002);
        }
        return super.onResolvePointerIcon(motionEvent, n);
    }

    @Override
    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)object;
        super.onRestoreInstanceState(((AbsSavedState)object).getSuperState());
        if (((SavedState)object).showDropdown && (object = this.getViewTreeObserver()) != null) {
            ((ViewTreeObserver)object).addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

                @Override
                public void onGlobalLayout() {
                    ViewTreeObserver viewTreeObserver;
                    if (!Spinner.this.mPopup.isShowing()) {
                        Spinner.this.mPopup.show(Spinner.this.getTextDirection(), Spinner.this.getTextAlignment());
                    }
                    if ((viewTreeObserver = Spinner.this.getViewTreeObserver()) != null) {
                        viewTreeObserver.removeOnGlobalLayoutListener(this);
                    }
                }
            });
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SpinnerPopup spinnerPopup = this.mPopup;
        boolean bl = spinnerPopup != null && spinnerPopup.isShowing();
        savedState.showDropdown = bl;
        return savedState;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ForwardingListener forwardingListener = this.mForwardingListener;
        if (forwardingListener != null && forwardingListener.onTouch(this, motionEvent)) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    @Override
    public boolean performClick() {
        boolean bl;
        boolean bl2 = bl = super.performClick();
        if (!bl) {
            bl2 = bl = true;
            if (!this.mPopup.isShowing()) {
                this.mPopup.show(this.getTextDirection(), this.getTextAlignment());
                bl2 = bl;
            }
        }
        return bl2;
    }

    @Override
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        Context context;
        if (this.mPopup == null) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        this.mRecycler.clear();
        if (this.mContext.getApplicationInfo().targetSdkVersion >= 21 && spinnerAdapter != null && spinnerAdapter.getViewTypeCount() != 1) {
            throw new IllegalArgumentException("Spinner adapter view type count must be 1");
        }
        Context context2 = context = this.mPopupContext;
        if (context == null) {
            context2 = this.mContext;
        }
        this.mPopup.setAdapter(new DropDownAdapter(spinnerAdapter, context2.getTheme()));
    }

    public void setDropDownHorizontalOffset(int n) {
        this.mPopup.setHorizontalOffset(n);
    }

    public void setDropDownVerticalOffset(int n) {
        this.mPopup.setVerticalOffset(n);
    }

    public void setDropDownWidth(int n) {
        if (!(this.mPopup instanceof DropdownPopup)) {
            Log.e(TAG, "Cannot set dropdown width for MODE_DIALOG, ignoring");
            return;
        }
        this.mDropDownWidth = n;
    }

    @Override
    public void setEnabled(boolean bl) {
        super.setEnabled(bl);
        if (this.mDisableChildrenWhenDisabled) {
            int n = this.getChildCount();
            for (int i = 0; i < n; ++i) {
                this.getChildAt(i).setEnabled(bl);
            }
        }
    }

    public void setGravity(int n) {
        if (this.mGravity != n) {
            int n2 = n;
            if ((n & 7) == 0) {
                n2 = n | 8388611;
            }
            this.mGravity = n2;
            this.requestLayout();
        }
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        throw new RuntimeException("setOnItemClickListener cannot be used with a spinner.");
    }

    @UnsupportedAppUsage
    public void setOnItemClickListenerInt(AdapterView.OnItemClickListener onItemClickListener) {
        super.setOnItemClickListener(onItemClickListener);
    }

    public void setPopupBackgroundDrawable(Drawable drawable2) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (!(spinnerPopup instanceof DropdownPopup)) {
            Log.e(TAG, "setPopupBackgroundDrawable: incompatible spinner mode; ignoring...");
            return;
        }
        spinnerPopup.setBackgroundDrawable(drawable2);
    }

    public void setPopupBackgroundResource(int n) {
        this.setPopupBackgroundDrawable(this.getPopupContext().getDrawable(n));
    }

    public void setPrompt(CharSequence charSequence) {
        this.mPopup.setPromptText(charSequence);
    }

    public void setPromptId(int n) {
        this.setPrompt(this.getContext().getText(n));
    }

    private class DialogPopup
    implements SpinnerPopup,
    DialogInterface.OnClickListener {
        private ListAdapter mListAdapter;
        private AlertDialog mPopup;
        private CharSequence mPrompt;

        private DialogPopup() {
        }

        @Override
        public void dismiss() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog != null) {
                alertDialog.dismiss();
                this.mPopup = null;
            }
        }

        @Override
        public Drawable getBackground() {
            return null;
        }

        @Override
        public CharSequence getHintText() {
            return this.mPrompt;
        }

        @Override
        public int getHorizontalOffset() {
            return 0;
        }

        @Override
        public int getVerticalOffset() {
            return 0;
        }

        @UnsupportedAppUsage
        @Override
        public boolean isShowing() {
            AlertDialog alertDialog = this.mPopup;
            boolean bl = alertDialog != null ? alertDialog.isShowing() : false;
            return bl;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int n) {
            Spinner.this.setSelection(n);
            if (Spinner.this.mOnItemClickListener != null) {
                Spinner.this.performItemClick(null, n, this.mListAdapter.getItemId(n));
            }
            this.dismiss();
        }

        @Override
        public void setAdapter(ListAdapter listAdapter) {
            this.mListAdapter = listAdapter;
        }

        @Override
        public void setBackgroundDrawable(Drawable drawable2) {
            Log.e(Spinner.TAG, "Cannot set popup background for MODE_DIALOG, ignoring");
        }

        @Override
        public void setHorizontalOffset(int n) {
            Log.e(Spinner.TAG, "Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void setPromptText(CharSequence charSequence) {
            this.mPrompt = charSequence;
        }

        @Override
        public void setVerticalOffset(int n) {
            Log.e(Spinner.TAG, "Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void show(int n, int n2) {
            if (this.mListAdapter == null) {
                return;
            }
            Object object = new AlertDialog.Builder(Spinner.this.getPopupContext());
            CharSequence charSequence = this.mPrompt;
            if (charSequence != null) {
                ((AlertDialog.Builder)object).setTitle(charSequence);
            }
            this.mPopup = ((AlertDialog.Builder)object).setSingleChoiceItems(this.mListAdapter, Spinner.this.getSelectedItemPosition(), (DialogInterface.OnClickListener)this).create();
            object = this.mPopup.getListView();
            ((View)object).setTextDirection(n);
            ((View)object).setTextAlignment(n2);
            this.mPopup.show();
        }
    }

    private static class DropDownAdapter
    implements ListAdapter,
    SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter)((Object)spinnerAdapter);
            }
            if (theme != null && spinnerAdapter instanceof ThemedSpinnerAdapter && (spinnerAdapter = (ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() == null) {
                spinnerAdapter.setDropDownViewTheme(theme);
            }
        }

        @Override
        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.areAllItemsEnabled();
            }
            return true;
        }

        @Override
        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            int n = spinnerAdapter == null ? 0 : spinnerAdapter.getCount();
            return n;
        }

        @Override
        public View getDropDownView(int n, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            view = spinnerAdapter == null ? null : spinnerAdapter.getDropDownView(n, view, viewGroup);
            return view;
        }

        @Override
        public Object getItem(int n) {
            Object object = this.mAdapter;
            object = object == null ? null : object.getItem(n);
            return object;
        }

        @Override
        public long getItemId(int n) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            long l = spinnerAdapter == null ? -1L : spinnerAdapter.getItemId(n);
            return l;
        }

        @Override
        public int getItemViewType(int n) {
            return 0;
        }

        @Override
        public View getView(int n, View view, ViewGroup viewGroup) {
            return this.getDropDownView(n, view, viewGroup);
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            boolean bl = spinnerAdapter != null && spinnerAdapter.hasStableIds();
            return bl;
        }

        @Override
        public boolean isEmpty() {
            boolean bl = this.getCount() == 0;
            return bl;
        }

        @Override
        public boolean isEnabled(int n) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter != null) {
                return listAdapter.isEnabled(n);
            }
            return true;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.registerDataSetObserver(dataSetObserver);
            }
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) {
                spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
    }

    private class DropdownPopup
    extends ListPopupWindow
    implements SpinnerPopup {
        private ListAdapter mAdapter;
        private CharSequence mHintText;

        public DropdownPopup(Context context, AttributeSet attributeSet, int n, int n2) {
            super(context, attributeSet, n, n2);
            this.setAnchorView(Spinner.this);
            this.setModal(true);
            this.setPromptPosition(0);
            this.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView adapterView, View view, int n, long l) {
                    Spinner.this.setSelection(n);
                    if (Spinner.this.mOnItemClickListener != null) {
                        Spinner.this.performItemClick(view, n, DropdownPopup.this.mAdapter.getItemId(n));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        void computeContentWidth() {
            Object object = this.getBackground();
            int n = 0;
            if (object != null) {
                ((Drawable)object).getPadding(Spinner.this.mTempRect);
                n = Spinner.this.isLayoutRtl() ? Spinner.access$400((Spinner)Spinner.this).right : -Spinner.access$400((Spinner)Spinner.this).left;
            } else {
                object = Spinner.this.mTempRect;
                Spinner.access$400((Spinner)Spinner.this).right = 0;
                ((Rect)object).left = 0;
            }
            int n2 = Spinner.this.getPaddingLeft();
            int n3 = Spinner.this.getPaddingRight();
            int n4 = Spinner.this.getWidth();
            if (Spinner.this.mDropDownWidth == -2) {
                int n5 = Spinner.this.measureContentWidth((SpinnerAdapter)((Object)this.mAdapter), this.getBackground());
                int n6 = Spinner.access$500((Spinner)Spinner.this).getResources().getDisplayMetrics().widthPixels - Spinner.access$400((Spinner)Spinner.this).left - Spinner.access$400((Spinner)Spinner.this).right;
                int n7 = n5;
                if (n5 > n6) {
                    n7 = n6;
                }
                this.setContentWidth(Math.max(n7, n4 - n2 - n3));
            } else if (Spinner.this.mDropDownWidth == -1) {
                this.setContentWidth(n4 - n2 - n3);
            } else {
                this.setContentWidth(Spinner.this.mDropDownWidth);
            }
            n = Spinner.this.isLayoutRtl() ? (n += n4 - n3 - this.getWidth()) : (n += n2);
            this.setHorizontalOffset(n);
        }

        @Override
        public CharSequence getHintText() {
            return this.mHintText;
        }

        @Override
        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        @Override
        public void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        @Override
        public void show(int n, int n2) {
            boolean bl = this.isShowing();
            this.computeContentWidth();
            this.setInputMethodMode(2);
            super.show();
            Object object = this.getListView();
            ((AbsListView)object).setChoiceMode(1);
            ((View)object).setTextDirection(n);
            ((View)object).setTextAlignment(n2);
            this.setSelection(Spinner.this.getSelectedItemPosition());
            if (bl) {
                return;
            }
            object = Spinner.this.getViewTreeObserver();
            if (object != null) {
                final ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

                    @Override
                    public void onGlobalLayout() {
                        if (!Spinner.this.isVisibleToUser()) {
                            DropdownPopup.this.dismiss();
                        } else {
                            DropdownPopup.this.computeContentWidth();
                            DropdownPopup.super.show();
                        }
                    }
                };
                ((ViewTreeObserver)object).addOnGlobalLayoutListener(onGlobalLayoutListener);
                this.setOnDismissListener(new PopupWindow.OnDismissListener(){

                    @Override
                    public void onDismiss() {
                        ViewTreeObserver viewTreeObserver = Spinner.this.getViewTreeObserver();
                        if (viewTreeObserver != null) {
                            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
                        }
                    }
                });
            }
        }

    }

    static class SavedState
    extends AbsSpinner.SavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean showDropdown;

        private SavedState(Parcel parcel) {
            super(parcel);
            boolean bl = parcel.readByte() != 0;
            this.showDropdown = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeByte((byte)(this.showDropdown ? 1 : 0));
        }

    }

    private static interface SpinnerPopup {
        public void dismiss();

        public Drawable getBackground();

        public CharSequence getHintText();

        public int getHorizontalOffset();

        public int getVerticalOffset();

        @UnsupportedAppUsage
        public boolean isShowing();

        public void setAdapter(ListAdapter var1);

        public void setBackgroundDrawable(Drawable var1);

        public void setHorizontalOffset(int var1);

        public void setPromptText(CharSequence var1);

        public void setVerticalOffset(int var1);

        public void show(int var1, int var2);
    }

}

