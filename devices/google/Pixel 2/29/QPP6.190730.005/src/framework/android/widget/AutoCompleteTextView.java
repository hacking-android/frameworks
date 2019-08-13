/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.android.internal.R;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;

public class AutoCompleteTextView
extends EditText
implements Filter.FilterListener {
    static final boolean DEBUG = false;
    static final int EXPAND_MAX = 3;
    static final String TAG = "AutoCompleteTextView";
    private ListAdapter mAdapter;
    private MyWatcher mAutoCompleteTextWatcher;
    private boolean mBlockCompletion;
    private int mDropDownAnchorId;
    private boolean mDropDownDismissedOnCompletion = true;
    private Filter mFilter;
    private int mHintResource;
    private CharSequence mHintText;
    @UnsupportedAppUsage
    private TextView mHintView;
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    private int mLastKeyCode = 0;
    @UnsupportedAppUsage
    private PopupDataSetObserver mObserver;
    @UnsupportedAppUsage
    private final PassThroughClickListener mPassThroughClickListener;
    @UnsupportedAppUsage
    private final ListPopupWindow mPopup;
    private boolean mPopupCanBeUpdated = true;
    private final Context mPopupContext;
    private int mThreshold;
    private Validator mValidator = null;

    public AutoCompleteTextView(Context context) {
        this(context, null);
    }

    public AutoCompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842859);
    }

    public AutoCompleteTextView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public AutoCompleteTextView(Context context, AttributeSet attributeSet, int n, int n2) {
        this(context, attributeSet, n, n2, null);
    }

    public AutoCompleteTextView(Context object, AttributeSet attributeSet, int n, int n2, Resources.Theme object2) {
        super((Context)object, attributeSet, n, n2);
        int n3;
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.AutoCompleteTextView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.AutoCompleteTextView, attributeSet, typedArray, n, n2);
        this.mPopupContext = object2 != null ? new ContextThemeWrapper((Context)object, (Resources.Theme)object2) : ((n3 = typedArray.getResourceId(8, 0)) != 0 ? new ContextThemeWrapper((Context)object, n3) : object);
        object2 = this.mPopupContext;
        if (object2 != object) {
            object2 = ((Context)object2).obtainStyledAttributes(attributeSet, R.styleable.AutoCompleteTextView, n, n2);
            this.saveAttributeDataForStyleable((Context)object, R.styleable.AutoCompleteTextView, attributeSet, typedArray, n, n2);
            object = object2;
        } else {
            object = typedArray;
        }
        object2 = ((TypedArray)object).getDrawable(3);
        int n4 = ((TypedArray)object).getLayoutDimension(5, -2);
        int n5 = ((TypedArray)object).getLayoutDimension(7, -2);
        n3 = ((TypedArray)object).getResourceId(1, 17367290);
        CharSequence charSequence = ((TypedArray)object).getText(0);
        if (object != typedArray) {
            ((TypedArray)object).recycle();
        }
        this.mPopup = new ListPopupWindow(this.mPopupContext, attributeSet, n, n2);
        this.mPopup.setSoftInputMode(16);
        this.mPopup.setPromptPosition(1);
        this.mPopup.setListSelector((Drawable)object2);
        this.mPopup.setOnItemClickListener(new DropDownItemClickListener());
        this.mPopup.setWidth(n4);
        this.mPopup.setHeight(n5);
        this.mHintResource = n3;
        this.setCompletionHint(charSequence);
        this.mDropDownAnchorId = typedArray.getResourceId(6, -1);
        this.mThreshold = typedArray.getInt(2, 2);
        typedArray.recycle();
        n = this.getInputType();
        if ((n & 15) == 1) {
            this.setRawInputType(n | 65536);
        }
        this.setFocusable(true);
        this.mAutoCompleteTextWatcher = new MyWatcher();
        this.addTextChangedListener(this.mAutoCompleteTextWatcher);
        this.mPassThroughClickListener = new PassThroughClickListener();
        super.setOnClickListener(this.mPassThroughClickListener);
    }

    private void buildImeCompletions() {
        InputMethodManager inputMethodManager;
        ListAdapter listAdapter = this.mAdapter;
        if (listAdapter != null && (inputMethodManager = this.getContext().getSystemService(InputMethodManager.class)) != null) {
            Object object;
            int n = Math.min(listAdapter.getCount(), 20);
            CompletionInfo[] arrcompletionInfo = new CompletionInfo[n];
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                int n3 = n2;
                if (listAdapter.isEnabled(i)) {
                    object = listAdapter.getItem(i);
                    arrcompletionInfo[n2] = new CompletionInfo(listAdapter.getItemId(i), n2, this.convertSelectionToString(object));
                    n3 = n2 + 1;
                }
                n2 = n3;
            }
            object = arrcompletionInfo;
            if (n2 != n) {
                object = new CompletionInfo[n2];
                System.arraycopy(arrcompletionInfo, 0, object, 0, n2);
            }
            inputMethodManager.displayCompletions(this, (CompletionInfo[])object);
        }
    }

    private void onClickImpl() {
        if (this.isPopupShowing()) {
            this.ensureImeVisible(true);
        }
    }

    private void performCompletion(View view, int n, long l) {
        block5 : {
            Object object;
            int n2;
            block7 : {
                block6 : {
                    if (!this.isPopupShowing()) break block5;
                    object = n < 0 ? this.mPopup.getSelectedItem() : this.mAdapter.getItem(n);
                    if (object == null) {
                        Log.w(TAG, "performCompletion: no selected item");
                        return;
                    }
                    this.mBlockCompletion = true;
                    this.replaceText(this.convertSelectionToString(object));
                    this.mBlockCompletion = false;
                    if (this.mItemClickListener == null) break block5;
                    object = this.mPopup;
                    if (view == null) break block6;
                    n2 = n;
                    if (n >= 0) break block7;
                }
                view = ((ListPopupWindow)object).getSelectedView();
                n2 = ((ListPopupWindow)object).getSelectedItemPosition();
                l = ((ListPopupWindow)object).getSelectedItemId();
            }
            this.mItemClickListener.onItemClick(((ListPopupWindow)object).getListView(), view, n2, l);
        }
        if (this.mDropDownDismissedOnCompletion && !this.mPopup.isDropDownAlwaysVisible()) {
            this.dismissDropDown();
        }
    }

    private void updateDropDownForFilter(int n) {
        if (this.getWindowVisibility() == 8) {
            return;
        }
        boolean bl = this.mPopup.isDropDownAlwaysVisible();
        boolean bl2 = this.enoughToFilter();
        if ((n > 0 || bl) && bl2) {
            if (this.hasFocus() && this.hasWindowFocus() && this.mPopupCanBeUpdated) {
                this.showDropDown();
            }
        } else if (!bl && this.isPopupShowing()) {
            this.dismissDropDown();
            this.mPopupCanBeUpdated = true;
        }
    }

    public void clearListSelection() {
        this.mPopup.clearListSelection();
    }

    protected CharSequence convertSelectionToString(Object object) {
        return this.mFilter.convertResultToString(object);
    }

    public void dismissDropDown() {
        InputMethodManager inputMethodManager = this.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.displayCompletions(this, null);
        }
        this.mPopup.dismiss();
        this.mPopupCanBeUpdated = false;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    void doAfterTextChanged() {
        this.mAutoCompleteTextWatcher.afterTextChanged(null);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    void doBeforeTextChanged() {
        this.mAutoCompleteTextWatcher.beforeTextChanged(null, 0, 0, 0);
    }

    public boolean enoughToFilter() {
        boolean bl = this.getText().length() >= this.mThreshold;
        return bl;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768913L)
    public void ensureImeVisible(boolean bl) {
        ListPopupWindow listPopupWindow = this.mPopup;
        int n = bl ? 1 : 2;
        listPopupWindow.setInputMethodMode(n);
        if (this.mPopup.isDropDownAlwaysVisible() || this.mFilter != null && this.enoughToFilter()) {
            this.showDropDown();
        }
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public CharSequence getCompletionHint() {
        return this.mHintText;
    }

    public int getDropDownAnchor() {
        return this.mDropDownAnchorId;
    }

    public int getDropDownAnimationStyle() {
        return this.mPopup.getAnimationStyle();
    }

    public Drawable getDropDownBackground() {
        return this.mPopup.getBackground();
    }

    public int getDropDownHeight() {
        return this.mPopup.getHeight();
    }

    public int getDropDownHorizontalOffset() {
        return this.mPopup.getHorizontalOffset();
    }

    public int getDropDownVerticalOffset() {
        return this.mPopup.getVerticalOffset();
    }

    public int getDropDownWidth() {
        return this.mPopup.getWidth();
    }

    protected Filter getFilter() {
        return this.mFilter;
    }

    public int getInputMethodMode() {
        return this.mPopup.getInputMethodMode();
    }

    @Deprecated
    public AdapterView.OnItemClickListener getItemClickListener() {
        return this.mItemClickListener;
    }

    @Deprecated
    public AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return this.mItemSelectedListener;
    }

    public int getListSelection() {
        return this.mPopup.getSelectedItemPosition();
    }

    public AdapterView.OnItemClickListener getOnItemClickListener() {
        return this.mItemClickListener;
    }

    public AdapterView.OnItemSelectedListener getOnItemSelectedListener() {
        return this.mItemSelectedListener;
    }

    public int getThreshold() {
        return this.mThreshold;
    }

    public Validator getValidator() {
        return this.mValidator;
    }

    public boolean isDropDownAlwaysVisible() {
        return this.mPopup.isDropDownAlwaysVisible();
    }

    public boolean isDropDownDismissedOnCompletion() {
        return this.mDropDownDismissedOnCompletion;
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    public boolean isInputMethodNotNeeded() {
        boolean bl = this.mPopup.getInputMethodMode() == 2;
        return bl;
    }

    public boolean isPerformingCompletion() {
        return this.mBlockCompletion;
    }

    public boolean isPopupShowing() {
        return this.mPopup.isShowing();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public void onCommitCompletion(CompletionInfo completionInfo) {
        if (this.isPopupShowing()) {
            this.mPopup.performItemClick(completionInfo.getPosition());
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        this.dismissDropDown();
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDisplayHint(int n) {
        super.onDisplayHint(n);
        if (n == 4 && !this.mPopup.isDropDownAlwaysVisible()) {
            this.dismissDropDown();
        }
    }

    @Override
    public void onFilterComplete(int n) {
        this.updateDropDownForFilter(n);
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect rect) {
        super.onFocusChanged(bl, n, rect);
        if (this.isTemporarilyDetached()) {
            return;
        }
        if (!bl) {
            this.performValidation();
        }
        if (!bl && !this.mPopup.isDropDownAlwaysVisible()) {
            this.dismissDropDown();
        }
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (this.mPopup.onKeyDown(n, keyEvent)) {
            return true;
        }
        if (!this.isPopupShowing() && n == 20 && keyEvent.hasNoModifiers()) {
            this.performValidation();
        }
        if (this.isPopupShowing() && n == 61 && keyEvent.hasNoModifiers()) {
            return true;
        }
        this.mLastKeyCode = n;
        boolean bl = super.onKeyDown(n, keyEvent);
        this.mLastKeyCode = 0;
        if (bl && this.isPopupShowing()) {
            this.clearListSelection();
        }
        return bl;
    }

    @Override
    public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
        if (n == 4 && this.isPopupShowing() && !this.mPopup.isDropDownAlwaysVisible()) {
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                if (dispatcherState != null) {
                    dispatcherState.startTracking(keyEvent, this);
                }
                return true;
            }
            if (keyEvent.getAction() == 1) {
                KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                if (dispatcherState != null) {
                    dispatcherState.handleUpEvent(keyEvent);
                }
                if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                    this.dismissDropDown();
                    return true;
                }
            }
        }
        return super.onKeyPreIme(n, keyEvent);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (this.mPopup.onKeyUp(n, keyEvent) && (n == 23 || n == 61 || n == 66)) {
            if (keyEvent.hasNoModifiers()) {
                this.performCompletion();
            }
            return true;
        }
        if (this.isPopupShowing() && n == 61 && keyEvent.hasNoModifiers()) {
            this.performCompletion();
            return true;
        }
        return super.onKeyUp(n, keyEvent);
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        if (!bl && !this.mPopup.isDropDownAlwaysVisible()) {
            this.dismissDropDown();
        }
    }

    public void performCompletion() {
        this.performCompletion(null, -1, -1L);
    }

    protected void performFiltering(CharSequence charSequence, int n) {
        this.mFilter.filter(charSequence, this);
    }

    public void performValidation() {
        if (this.mValidator == null) {
            return;
        }
        Editable editable = this.getText();
        if (!TextUtils.isEmpty(editable) && !this.mValidator.isValid(editable)) {
            this.setText(this.mValidator.fixText(editable));
        }
    }

    public final void refreshAutoCompleteResults() {
        if (this.enoughToFilter()) {
            if (this.mFilter != null) {
                this.mPopupCanBeUpdated = true;
                this.performFiltering(this.getText(), this.mLastKeyCode);
            }
        } else {
            Filter filter;
            if (!this.mPopup.isDropDownAlwaysVisible()) {
                this.dismissDropDown();
            }
            if ((filter = this.mFilter) != null) {
                filter.filter(null);
            }
        }
    }

    protected void replaceText(CharSequence charSequence) {
        this.clearComposingText();
        this.setText(charSequence);
        charSequence = this.getText();
        Selection.setSelection((Spannable)charSequence, charSequence.length());
    }

    public <T extends ListAdapter & Filterable> void setAdapter(T t) {
        ListAdapter listAdapter;
        PopupDataSetObserver popupDataSetObserver = this.mObserver;
        if (popupDataSetObserver == null) {
            this.mObserver = new PopupDataSetObserver(this);
        } else {
            listAdapter = this.mAdapter;
            if (listAdapter != null) {
                listAdapter.unregisterDataSetObserver(popupDataSetObserver);
            }
        }
        listAdapter = this.mAdapter = t;
        if (listAdapter != null) {
            this.mFilter = ((Filterable)((Object)listAdapter)).getFilter();
            t.registerDataSetObserver(this.mObserver);
        } else {
            this.mFilter = null;
        }
        this.mPopup.setAdapter(this.mAdapter);
    }

    public void setCompletionHint(CharSequence object) {
        this.mHintText = object;
        if (object != null) {
            TextView textView = this.mHintView;
            if (textView == null) {
                object = (TextView)LayoutInflater.from(this.mPopupContext).inflate(this.mHintResource, null).findViewById(16908308);
                ((TextView)object).setText(this.mHintText);
                this.mHintView = object;
                this.mPopup.setPromptView((View)object);
            } else {
                textView.setText((CharSequence)object);
            }
        } else {
            this.mPopup.setPromptView(null);
            this.mHintView = null;
        }
    }

    @UnsupportedAppUsage
    public void setDropDownAlwaysVisible(boolean bl) {
        this.mPopup.setDropDownAlwaysVisible(bl);
    }

    public void setDropDownAnchor(int n) {
        this.mDropDownAnchorId = n;
        this.mPopup.setAnchorView(null);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void setDropDownAnimationStyle(int n) {
        this.mPopup.setAnimationStyle(n);
    }

    public void setDropDownBackgroundDrawable(Drawable drawable2) {
        this.mPopup.setBackgroundDrawable(drawable2);
    }

    public void setDropDownBackgroundResource(int n) {
        this.mPopup.setBackgroundDrawable(this.getContext().getDrawable(n));
    }

    @UnsupportedAppUsage
    public void setDropDownDismissedOnCompletion(boolean bl) {
        this.mDropDownDismissedOnCompletion = bl;
    }

    public void setDropDownHeight(int n) {
        this.mPopup.setHeight(n);
    }

    public void setDropDownHorizontalOffset(int n) {
        this.mPopup.setHorizontalOffset(n);
    }

    public void setDropDownVerticalOffset(int n) {
        this.mPopup.setVerticalOffset(n);
    }

    public void setDropDownWidth(int n) {
        this.mPopup.setWidth(n);
    }

    @UnsupportedAppUsage
    public void setForceIgnoreOutsideTouch(boolean bl) {
        this.mPopup.setForceIgnoreOutsideTouch(bl);
    }

    @Override
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = super.setFrame(n, n2, n3, n4);
        if (this.isPopupShowing()) {
            this.showDropDown();
        }
        return bl;
    }

    public void setInputMethodMode(int n) {
        this.mPopup.setInputMethodMode(n);
    }

    public void setListSelection(int n) {
        this.mPopup.setSelection(n);
    }

    @Override
    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.mPassThroughClickListener.mWrapped = onClickListener;
    }

    public void setOnDismissListener(final OnDismissListener onDismissListener) {
        PopupWindow.OnDismissListener onDismissListener2 = null;
        if (onDismissListener != null) {
            onDismissListener2 = new PopupWindow.OnDismissListener(){

                @Override
                public void onDismiss() {
                    onDismissListener.onDismiss();
                }
            };
        }
        this.mPopup.setOnDismissListener(onDismissListener2);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener;
    }

    public void setText(CharSequence charSequence, boolean bl) {
        if (bl) {
            this.setText(charSequence);
        } else {
            this.mBlockCompletion = true;
            this.setText(charSequence);
            this.mBlockCompletion = false;
        }
    }

    public void setThreshold(int n) {
        int n2 = n;
        if (n <= 0) {
            n2 = 1;
        }
        this.mThreshold = n2;
    }

    public void setValidator(Validator validator) {
        this.mValidator = validator;
    }

    public void showDropDown() {
        this.buildImeCompletions();
        if (this.mPopup.getAnchorView() == null) {
            if (this.mDropDownAnchorId != -1) {
                this.mPopup.setAnchorView((View)this.getRootView().findViewById(this.mDropDownAnchorId));
            } else {
                this.mPopup.setAnchorView(this);
            }
        }
        if (!this.isPopupShowing()) {
            this.mPopup.setInputMethodMode(1);
            this.mPopup.setListItemExpandMax(3);
        }
        this.mPopup.show();
        this.mPopup.getListView().setOverScrollMode(0);
    }

    @UnsupportedAppUsage
    public void showDropDownAfterLayout() {
        this.mPopup.postShow();
    }

    private class DropDownItemClickListener
    implements AdapterView.OnItemClickListener {
        private DropDownItemClickListener() {
        }

        public void onItemClick(AdapterView adapterView, View view, int n, long l) {
            AutoCompleteTextView.this.performCompletion(view, n, l);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface InputMethodMode {
    }

    private class MyWatcher
    implements TextWatcher {
        private boolean mOpenBefore;

        private MyWatcher() {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (AutoCompleteTextView.this.mBlockCompletion) {
                return;
            }
            if (this.mOpenBefore && !AutoCompleteTextView.this.isPopupShowing()) {
                return;
            }
            AutoCompleteTextView.this.refreshAutoCompleteResults();
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            if (AutoCompleteTextView.this.mBlockCompletion) {
                return;
            }
            this.mOpenBefore = AutoCompleteTextView.this.isPopupShowing();
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }
    }

    public static interface OnDismissListener {
        public void onDismiss();
    }

    private class PassThroughClickListener
    implements View.OnClickListener {
        private View.OnClickListener mWrapped;

        private PassThroughClickListener() {
        }

        @Override
        public void onClick(View view) {
            AutoCompleteTextView.this.onClickImpl();
            View.OnClickListener onClickListener = this.mWrapped;
            if (onClickListener != null) {
                onClickListener.onClick(view);
            }
        }
    }

    private static class PopupDataSetObserver
    extends DataSetObserver {
        private final WeakReference<AutoCompleteTextView> mViewReference;
        private final Runnable updateRunnable = new Runnable(){

            @Override
            public void run() {
                AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)PopupDataSetObserver.this.mViewReference.get();
                if (autoCompleteTextView == null) {
                    return;
                }
                ListAdapter listAdapter = autoCompleteTextView.mAdapter;
                if (listAdapter == null) {
                    return;
                }
                autoCompleteTextView.updateDropDownForFilter(listAdapter.getCount());
            }
        };

        private PopupDataSetObserver(AutoCompleteTextView autoCompleteTextView) {
            this.mViewReference = new WeakReference<AutoCompleteTextView>(autoCompleteTextView);
        }

        @Override
        public void onChanged() {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)this.mViewReference.get();
            if (autoCompleteTextView != null && autoCompleteTextView.mAdapter != null) {
                autoCompleteTextView.post(this.updateRunnable);
            }
        }

    }

    public static interface Validator {
        public CharSequence fixText(CharSequence var1);

        public boolean isValid(CharSequence var1);
    }

}

