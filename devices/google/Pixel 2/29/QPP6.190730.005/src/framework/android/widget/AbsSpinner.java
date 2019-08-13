/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.AbsSavedState;
import android.view.View;
import android.view.ViewGroup;
import android.view.autofill.AutofillValue;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import com.android.internal.R;

public abstract class AbsSpinner
extends AdapterView<SpinnerAdapter> {
    private static final String LOG_TAG = AbsSpinner.class.getSimpleName();
    SpinnerAdapter mAdapter;
    private DataSetObserver mDataSetObserver;
    int mHeightMeasureSpec;
    final RecycleBin mRecycler = new RecycleBin();
    int mSelectionBottomPadding = 0;
    int mSelectionLeftPadding = 0;
    int mSelectionRightPadding = 0;
    int mSelectionTopPadding = 0;
    final Rect mSpinnerPadding = new Rect();
    private Rect mTouchFrame;
    int mWidthMeasureSpec;

    public AbsSpinner(Context context) {
        super(context);
        this.initAbsSpinner();
    }

    public AbsSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbsSpinner(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AbsSpinner(Context object, AttributeSet arrcharSequence, int n, int n2) {
        super((Context)object, (AttributeSet)arrcharSequence, n, n2);
        if (this.getImportantForAutofill() == 0) {
            this.setImportantForAutofill(1);
        }
        this.initAbsSpinner();
        TypedArray typedArray = ((Context)object).obtainStyledAttributes((AttributeSet)arrcharSequence, R.styleable.AbsSpinner, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.AbsSpinner, (AttributeSet)arrcharSequence, typedArray, n, n2);
        arrcharSequence = typedArray.getTextArray(0);
        if (arrcharSequence != null) {
            object = new ArrayAdapter<CharSequence>((Context)object, 17367048, arrcharSequence);
            ((ArrayAdapter)object).setDropDownViewResource(17367049);
            this.setAdapter((SpinnerAdapter)object);
        }
        typedArray.recycle();
    }

    private void initAbsSpinner() {
        this.setFocusable(true);
        this.setWillNotDraw(false);
    }

    @Override
    public void autofill(AutofillValue autofillValue) {
        if (!this.isEnabled()) {
            return;
        }
        if (!autofillValue.isList()) {
            String string2 = LOG_TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(autofillValue);
            stringBuilder.append(" could not be autofilled into ");
            stringBuilder.append(this);
            Log.w(string2, stringBuilder.toString());
            return;
        }
        this.setSelection(autofillValue.getListValue());
    }

    @Override
    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        super.dispatchRestoreInstanceState(sparseArray);
        this.handleDataChanged();
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new ViewGroup.LayoutParams(-1, -2);
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return AbsSpinner.class.getName();
    }

    @Override
    public SpinnerAdapter getAdapter() {
        return this.mAdapter;
    }

    @Override
    public int getAutofillType() {
        int n = this.isEnabled() ? 3 : 0;
        return n;
    }

    @Override
    public AutofillValue getAutofillValue() {
        AutofillValue autofillValue = this.isEnabled() ? AutofillValue.forList(this.getSelectedItemPosition()) : null;
        return autofillValue;
    }

    int getChildHeight(View view) {
        return view.getMeasuredHeight();
    }

    int getChildWidth(View view) {
        return view.getMeasuredWidth();
    }

    @Override
    public int getCount() {
        return this.mItemCount;
    }

    @Override
    public View getSelectedView() {
        if (this.mItemCount > 0 && this.mSelectedPosition >= 0) {
            return this.getChildAt(this.mSelectedPosition - this.mFirstPosition);
        }
        return null;
    }

    abstract void layout(int var1, boolean var2);

    @Override
    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getMode(n);
        Object object = this.mSpinnerPadding;
        int n4 = this.mPaddingLeft;
        int n5 = this.mSelectionLeftPadding;
        if (n4 > n5) {
            n5 = this.mPaddingLeft;
        }
        ((Rect)object).left = n5;
        object = this.mSpinnerPadding;
        n4 = this.mPaddingTop;
        n5 = this.mSelectionTopPadding;
        if (n4 > n5) {
            n5 = this.mPaddingTop;
        }
        ((Rect)object).top = n5;
        object = this.mSpinnerPadding;
        n4 = this.mPaddingRight;
        n5 = this.mSelectionRightPadding;
        if (n4 > n5) {
            n5 = this.mPaddingRight;
        }
        ((Rect)object).right = n5;
        object = this.mSpinnerPadding;
        n4 = this.mPaddingBottom;
        n5 = this.mSelectionBottomPadding;
        if (n4 > n5) {
            n5 = this.mPaddingBottom;
        }
        ((Rect)object).bottom = n5;
        if (this.mDataChanged) {
            this.handleDataChanged();
        }
        int n6 = 0;
        int n7 = 0;
        int n8 = 1;
        int n9 = this.getSelectedItemPosition();
        n4 = n6;
        n5 = n7;
        int n10 = n8;
        if (n9 >= 0) {
            object = this.mAdapter;
            n4 = n6;
            n5 = n7;
            n10 = n8;
            if (object != null) {
                n4 = n6;
                n5 = n7;
                n10 = n8;
                if (n9 < object.getCount()) {
                    View view = this.mRecycler.get(n9);
                    object = view;
                    if (view == null) {
                        view = this.mAdapter.getView(n9, null, this);
                        object = view;
                        if (view.getImportantForAccessibility() == 0) {
                            view.setImportantForAccessibility(1);
                            object = view;
                        }
                    }
                    this.mRecycler.put(n9, (View)object);
                    if (((View)object).getLayoutParams() == null) {
                        this.mBlockLayoutRequests = true;
                        ((View)object).setLayoutParams(this.generateDefaultLayoutParams());
                        this.mBlockLayoutRequests = false;
                    }
                    this.measureChild((View)object, n, n2);
                    n4 = this.getChildHeight((View)object) + this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
                    n5 = this.getChildWidth((View)object) + this.mSpinnerPadding.left + this.mSpinnerPadding.right;
                    n10 = 0;
                }
            }
        }
        n8 = n4;
        n4 = n5;
        if (n10 != 0) {
            n8 = n10 = this.mSpinnerPadding.top + this.mSpinnerPadding.bottom;
            n4 = n5;
            if (n3 == 0) {
                n4 = this.mSpinnerPadding.left + this.mSpinnerPadding.right;
                n8 = n10;
            }
        }
        n10 = Math.max(n8, this.getSuggestedMinimumHeight());
        n5 = Math.max(n4, this.getSuggestedMinimumWidth());
        n4 = AbsSpinner.resolveSizeAndState(n10, n2, 0);
        this.setMeasuredDimension(AbsSpinner.resolveSizeAndState(n5, n, 0), n4);
        this.mHeightMeasureSpec = n2;
        this.mWidthMeasureSpec = n;
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        if (((SavedState)parcelable).selectedId >= 0L) {
            this.mDataChanged = true;
            this.mNeedSync = true;
            this.mSyncRowId = ((SavedState)parcelable).selectedId;
            this.mSyncPosition = ((SavedState)parcelable).position;
            this.mSyncMode = 0;
            this.requestLayout();
        }
    }

    @Override
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.selectedId = this.getSelectedItemId();
        savedState.position = savedState.selectedId >= 0L ? this.getSelectedItemPosition() : -1;
        return savedState;
    }

    public int pointToPosition(int n, int n2) {
        Object object = this.mTouchFrame;
        Rect rect = object;
        if (object == null) {
            rect = this.mTouchFrame = new Rect();
        }
        for (int i = this.getChildCount() - 1; i >= 0; --i) {
            object = this.getChildAt(i);
            if (((View)object).getVisibility() != 0) continue;
            ((View)object).getHitRect(rect);
            if (!rect.contains(n, n2)) continue;
            return this.mFirstPosition + i;
        }
        return -1;
    }

    void recycleAllViews() {
        int n = this.getChildCount();
        RecycleBin recycleBin = this.mRecycler;
        int n2 = this.mFirstPosition;
        for (int i = 0; i < n; ++i) {
            recycleBin.put(n2 + i, this.getChildAt(i));
        }
    }

    @Override
    public void requestLayout() {
        if (!this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    void resetList() {
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.removeAllViewsInLayout();
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.setSelectedPositionInt(-1);
        this.setNextSelectedPositionInt(-1);
        this.invalidate();
    }

    @Override
    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        SpinnerAdapter spinnerAdapter2 = this.mAdapter;
        if (spinnerAdapter2 != null) {
            spinnerAdapter2.unregisterDataSetObserver(this.mDataSetObserver);
            this.resetList();
        }
        this.mAdapter = spinnerAdapter;
        int n = -1;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
            this.checkFocus();
            this.mDataSetObserver = new AdapterView.AdapterDataSetObserver();
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            if (this.mItemCount > 0) {
                n = 0;
            }
            this.setSelectedPositionInt(n);
            this.setNextSelectedPositionInt(n);
            if (this.mItemCount == 0) {
                this.checkSelectionChanged();
            }
        } else {
            this.checkFocus();
            this.resetList();
            this.checkSelectionChanged();
        }
        this.requestLayout();
    }

    @Override
    public void setSelection(int n) {
        this.setNextSelectedPositionInt(n);
        this.requestLayout();
        this.invalidate();
    }

    public void setSelection(int n, boolean bl) {
        boolean bl2 = true;
        bl = bl && this.mFirstPosition <= n && n <= this.mFirstPosition + this.getChildCount() - 1 ? bl2 : false;
        this.setSelectionInt(n, bl);
    }

    void setSelectionInt(int n, boolean bl) {
        if (n != this.mOldSelectedPosition) {
            this.mBlockLayoutRequests = true;
            int n2 = this.mSelectedPosition;
            this.setNextSelectedPositionInt(n);
            this.layout(n - n2, bl);
            this.mBlockLayoutRequests = false;
        }
    }

    class RecycleBin {
        private final SparseArray<View> mScrapHeap = new SparseArray();

        RecycleBin() {
        }

        void clear() {
            SparseArray<View> sparseArray = this.mScrapHeap;
            int n = sparseArray.size();
            for (int i = 0; i < n; ++i) {
                View view = sparseArray.valueAt(i);
                if (view == null) continue;
                AbsSpinner.this.removeDetachedView(view, true);
            }
            sparseArray.clear();
        }

        View get(int n) {
            View view = this.mScrapHeap.get(n);
            if (view != null) {
                this.mScrapHeap.delete(n);
            }
            return view;
        }

        public void put(int n, View view) {
            this.mScrapHeap.put(n, view);
        }
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int position;
        long selectedId;

        SavedState(Parcel parcel) {
            super(parcel);
            this.selectedId = parcel.readLong();
            this.position = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AbsSpinner.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" selectedId=");
            stringBuilder.append(this.selectedId);
            stringBuilder.append(" position=");
            stringBuilder.append(this.position);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeLong(this.selectedId);
            parcel.writeInt(this.position);
        }

    }

}

