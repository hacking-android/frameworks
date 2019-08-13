/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.CollapsibleActionView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.CursorAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SuggestionsAdapter;
import android.widget.TextView;
import android.widget._$$Lambda$SearchView$SearchAutoComplete$qdPU54FiW6QTzCbsg7P4cSs3cJ8;
import com.android.internal.R;
import java.util.WeakHashMap;

public class SearchView
extends LinearLayout
implements CollapsibleActionView {
    private static final boolean DBG = false;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final String LOG_TAG = "SearchView";
    private Bundle mAppSearchData;
    @UnsupportedAppUsage
    private boolean mClearingFocus;
    @UnsupportedAppUsage
    private final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    @UnsupportedAppUsage
    private int mCollapsedImeOptions;
    private final CharSequence mDefaultQueryHint;
    private final View mDropDownAnchor;
    @UnsupportedAppUsage
    private boolean mExpandedInActionView;
    private final ImageView mGoButton;
    @UnsupportedAppUsage
    private boolean mIconified;
    @UnsupportedAppUsage
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private CharSequence mOldQueryText;
    @UnsupportedAppUsage
    private final View.OnClickListener mOnClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            if (view == SearchView.this.mSearchButton) {
                SearchView.this.onSearchClicked();
            } else if (view == SearchView.this.mCloseButton) {
                SearchView.this.onCloseClicked();
            } else if (view == SearchView.this.mGoButton) {
                SearchView.this.onSubmitQuery();
            } else if (view == SearchView.this.mVoiceButton) {
                SearchView.this.onVoiceClicked();
            } else if (view == SearchView.this.mSearchSrcTextView) {
                SearchView.this.forceSuggestionQuery();
            }
        }
    };
    private OnCloseListener mOnCloseListener;
    private final TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener(){

        @Override
        public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
            SearchView.this.onSubmitQuery();
            return true;
        }
    };
    @UnsupportedAppUsage
    private final AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
            SearchView.this.onItemClicked(n, 0, null);
        }
    };
    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
            SearchView.this.onItemSelected(n);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    @UnsupportedAppUsage
    private OnQueryTextListener mOnQueryChangeListener;
    private View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private View.OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache = new WeakHashMap();
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable = new Runnable(){

        @Override
        public void run() {
            if (SearchView.this.mSuggestionsAdapter != null && SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
                SearchView.this.mSuggestionsAdapter.changeCursor(null);
            }
        }
    };
    @UnsupportedAppUsage
    private final ImageView mSearchButton;
    @UnsupportedAppUsage
    private final View mSearchEditFrame;
    @UnsupportedAppUsage
    private final Drawable mSearchHintIcon;
    @UnsupportedAppUsage
    private final View mSearchPlate;
    @UnsupportedAppUsage
    private final SearchAutoComplete mSearchSrcTextView;
    private Rect mSearchSrcTextViewBounds = new Rect();
    private Rect mSearchSrtTextViewBoundsExpanded = new Rect();
    private SearchableInfo mSearchable;
    @UnsupportedAppUsage
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    @UnsupportedAppUsage
    private CursorAdapter mSuggestionsAdapter;
    private int[] mTemp = new int[2];
    private int[] mTemp2 = new int[2];
    View.OnKeyListener mTextKeyListener = new View.OnKeyListener(){

        @Override
        public boolean onKey(View object, int n, KeyEvent keyEvent) {
            if (SearchView.this.mSearchable == null) {
                return false;
            }
            if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                return SearchView.this.onSuggestionsKey((View)object, n, keyEvent);
            }
            if (!SearchView.this.mSearchSrcTextView.isEmpty() && keyEvent.hasNoModifiers()) {
                if (keyEvent.getAction() == 1 && n == 66) {
                    ((View)object).cancelLongPress();
                    object = SearchView.this;
                    ((SearchView)object).launchQuerySearch(0, null, ((SearchView)object).mSearchSrcTextView.getText().toString());
                    return true;
                }
                if (keyEvent.getAction() == 0 && (object = SearchView.this.mSearchable.findActionKey(n)) != null && ((SearchableInfo.ActionKeyInfo)object).getQueryActionMsg() != null) {
                    SearchView.this.launchQuerySearch(n, ((SearchableInfo.ActionKeyInfo)object).getQueryActionMsg(), SearchView.this.mSearchSrcTextView.getText().toString());
                    return true;
                }
            }
            return false;
        }
    };
    private TextWatcher mTextWatcher = new TextWatcher(){

        @Override
        public void afterTextChanged(Editable editable) {
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            SearchView.this.onTextChanged(charSequence);
        }
    };
    private UpdatableTouchDelegate mTouchDelegate;
    private Runnable mUpdateDrawableStateRunnable = new Runnable(){

        @Override
        public void run() {
            SearchView.this.updateFocusedState();
        }
    };
    @UnsupportedAppUsage
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    @UnsupportedAppUsage
    private final ImageView mVoiceButton;
    @UnsupportedAppUsage
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843904);
    }

    public SearchView(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SearchView(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.SearchView, n, n2);
        this.saveAttributeDataForStyleable((Context)object, R.styleable.SearchView, attributeSet, typedArray, n, n2);
        ((LayoutInflater)((Context)object).getSystemService("layout_inflater")).inflate(typedArray.getResourceId(0, 17367278), (ViewGroup)this, true);
        this.mSearchSrcTextView = (SearchAutoComplete)this.findViewById(16909323);
        this.mSearchSrcTextView.setSearchView(this);
        this.mSearchEditFrame = this.findViewById(16909319);
        this.mSearchPlate = this.findViewById(16909322);
        this.mSubmitArea = this.findViewById(16909414);
        this.mSearchButton = (ImageView)this.findViewById(16909317);
        this.mGoButton = (ImageView)this.findViewById(16909320);
        this.mCloseButton = (ImageView)this.findViewById(16909318);
        this.mVoiceButton = (ImageView)this.findViewById(16909325);
        this.mCollapsedIcon = (ImageView)this.findViewById(16909321);
        this.mSearchPlate.setBackground(typedArray.getDrawable(12));
        this.mSubmitArea.setBackground(typedArray.getDrawable(13));
        this.mSearchButton.setImageDrawable(typedArray.getDrawable(8));
        this.mGoButton.setImageDrawable(typedArray.getDrawable(7));
        this.mCloseButton.setImageDrawable(typedArray.getDrawable(6));
        this.mVoiceButton.setImageDrawable(typedArray.getDrawable(9));
        this.mCollapsedIcon.setImageDrawable(typedArray.getDrawable(8));
        this.mSearchHintIcon = typedArray.hasValueOrEmpty(14) ? typedArray.getDrawable(14) : typedArray.getDrawable(8);
        this.mSuggestionRowLayout = typedArray.getResourceId(11, 17367277);
        this.mSuggestionCommitIconResId = typedArray.getResourceId(10, 0);
        this.mSearchButton.setOnClickListener(this.mOnClickListener);
        this.mCloseButton.setOnClickListener(this.mOnClickListener);
        this.mGoButton.setOnClickListener(this.mOnClickListener);
        this.mVoiceButton.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
        this.mSearchSrcTextView.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View view, boolean bl) {
                if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                    SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, bl);
                }
            }
        });
        this.setIconifiedByDefault(typedArray.getBoolean(4, true));
        n = typedArray.getDimensionPixelSize(1, -1);
        if (n != -1) {
            this.setMaxWidth(n);
        }
        this.mDefaultQueryHint = typedArray.getText(15);
        this.mQueryHint = typedArray.getText(5);
        n = typedArray.getInt(3, -1);
        if (n != -1) {
            this.setImeOptions(n);
        }
        if ((n = typedArray.getInt(2, -1)) != -1) {
            this.setInputType(n);
        }
        if (this.getFocusable() == 16) {
            this.setFocusable(1);
        }
        typedArray.recycle();
        this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
        this.mVoiceWebSearchIntent.addFlags(268435456);
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.mVoiceAppSearchIntent.addFlags(268435456);
        this.mDropDownAnchor = this.findViewById(this.mSearchSrcTextView.getDropDownAnchor());
        object = this.mDropDownAnchor;
        if (object != null) {
            ((View)object).addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

                @Override
                public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                    SearchView.this.adjustDropDownSizeAndPosition();
                }
            });
        }
        this.updateViewsVisibility(this.mIconifiedByDefault);
        this.updateQueryHint();
    }

    private void adjustDropDownSizeAndPosition() {
        if (this.mDropDownAnchor.getWidth() > 1) {
            Resources resources = this.getContext().getResources();
            int n = this.mSearchPlate.getPaddingLeft();
            Rect rect = new Rect();
            boolean bl = this.isLayoutRtl();
            int n2 = this.mIconifiedByDefault ? resources.getDimensionPixelSize(17105144) + resources.getDimensionPixelSize(17105145) : 0;
            this.mSearchSrcTextView.getDropDownBackground().getPadding(rect);
            int n3 = bl ? -rect.left : n - (rect.left + n2);
            this.mSearchSrcTextView.setDropDownHorizontalOffset(n3);
            int n4 = this.mDropDownAnchor.getWidth();
            int n5 = rect.left;
            n3 = rect.right;
            this.mSearchSrcTextView.setDropDownWidth(n4 + n5 + n3 + n2 - n);
        }
    }

    private Intent createIntent(String object, Uri parcelable, String string2, String string3, int n, String string4) {
        object = new Intent((String)object);
        ((Intent)object).addFlags(268435456);
        if (parcelable != null) {
            ((Intent)object).setData((Uri)parcelable);
        }
        ((Intent)object).putExtra("user_query", this.mUserQuery);
        if (string3 != null) {
            ((Intent)object).putExtra("query", string3);
        }
        if (string2 != null) {
            ((Intent)object).putExtra("intent_extra_data_key", string2);
        }
        if ((parcelable = this.mAppSearchData) != null) {
            ((Intent)object).putExtra("app_data", (Bundle)parcelable);
        }
        if (n != 0) {
            ((Intent)object).putExtra("action_key", n);
            ((Intent)object).putExtra("action_msg", string4);
        }
        ((Intent)object).setComponent(this.mSearchable.getSearchActivity());
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Intent createIntentFromSuggestion(Cursor object, int n, String object2) {
        try {
            String string2;
            String string3 = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_action");
            Object object3 = string3;
            if (string3 == null) {
                object3 = this.mSearchable.getSuggestIntentAction();
            }
            string3 = object3;
            if (object3 == null) {
                string3 = "android.intent.action.SEARCH";
            }
            CharSequence charSequence = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_data");
            object3 = charSequence;
            if (charSequence == null) {
                object3 = this.mSearchable.getSuggestIntentData();
            }
            if (object3 != null && (string2 = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_data_id")) != null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)object3);
                ((StringBuilder)charSequence).append("/");
                ((StringBuilder)charSequence).append(Uri.encode(string2));
                object3 = ((StringBuilder)charSequence).toString();
            }
            object3 = object3 == null ? null : Uri.parse((String)object3);
            charSequence = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_query");
            return this.createIntent(string3, (Uri)object3, SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_extra_data"), (String)charSequence, n, (String)object2);
        }
        catch (RuntimeException runtimeException) {
            try {
                n = object.getPosition();
            }
            catch (RuntimeException runtimeException2) {
                n = -1;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Search suggestions cursor at row ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" returned exception.");
            Log.w(LOG_TAG, ((StringBuilder)object).toString(), runtimeException);
            return null;
        }
    }

    private Intent createVoiceAppSearchIntent(Intent object, SearchableInfo searchableInfo) {
        ComponentName componentName = searchableInfo.getSearchActivity();
        Object object2 = new Intent("android.intent.action.SEARCH");
        ((Intent)object2).setComponent(componentName);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getContext(), 0, (Intent)object2, 1073741824);
        Bundle bundle = new Bundle();
        object2 = this.mAppSearchData;
        if (object2 != null) {
            bundle.putParcelable("app_data", (Parcelable)object2);
        }
        Intent intent = new Intent((Intent)object);
        object = "free_form";
        object2 = null;
        String string2 = null;
        int n = 1;
        Resources resources = this.getResources();
        if (searchableInfo.getVoiceLanguageModeId() != 0) {
            object = resources.getString(searchableInfo.getVoiceLanguageModeId());
        }
        if (searchableInfo.getVoicePromptTextId() != 0) {
            object2 = resources.getString(searchableInfo.getVoicePromptTextId());
        }
        if (searchableInfo.getVoiceLanguageId() != 0) {
            string2 = resources.getString(searchableInfo.getVoiceLanguageId());
        }
        if (searchableInfo.getVoiceMaxResults() != 0) {
            n = searchableInfo.getVoiceMaxResults();
        }
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", (String)object);
        intent.putExtra("android.speech.extra.PROMPT", (String)object2);
        intent.putExtra("android.speech.extra.LANGUAGE", string2);
        intent.putExtra("android.speech.extra.MAX_RESULTS", n);
        object = componentName == null ? null : componentName.flattenToShortString();
        intent.putExtra("calling_package", (String)object);
        intent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", pendingIntent);
        intent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent;
    }

    private Intent createVoiceWebSearchIntent(Intent object, SearchableInfo searchableInfo) {
        Intent intent = new Intent((Intent)object);
        object = searchableInfo.getSearchActivity();
        object = object == null ? null : ((ComponentName)object).flattenToShortString();
        intent.putExtra("calling_package", (String)object);
        return intent;
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    private void forceSuggestionQuery() {
        this.mSearchSrcTextView.doBeforeTextChanged();
        this.mSearchSrcTextView.doAfterTextChanged();
    }

    private static String getActionKeyMessage(Cursor object, SearchableInfo.ActionKeyInfo actionKeyInfo) {
        String string2 = null;
        String string3 = actionKeyInfo.getSuggestActionMsgColumn();
        if (string3 != null) {
            string2 = SuggestionsAdapter.getColumnString((Cursor)object, string3);
        }
        object = string2;
        if (string2 == null) {
            object = actionKeyInfo.getSuggestActionMsg();
        }
        return object;
    }

    private void getChildBoundsWithinSearchView(View view, Rect rect) {
        view.getLocationInWindow(this.mTemp);
        this.getLocationInWindow(this.mTemp2);
        int[] arrn = this.mTemp;
        int n = arrn[1];
        int[] arrn2 = this.mTemp2;
        int n2 = arrn[0] - arrn2[0];
        rect.set(n2, n -= arrn2[1], view.getWidth() + n2, view.getHeight() + n);
    }

    private CharSequence getDecoratedHint(CharSequence charSequence) {
        if (this.mIconifiedByDefault && this.mSearchHintIcon != null) {
            int n = (int)((double)this.mSearchSrcTextView.getTextSize() * 1.25);
            this.mSearchHintIcon.setBounds(0, 0, n, n);
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder("   ");
            spannableStringBuilder.setSpan(new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
            spannableStringBuilder.append(charSequence);
            return spannableStringBuilder;
        }
        return charSequence;
    }

    private int getPreferredHeight() {
        return this.getContext().getResources().getDimensionPixelSize(17105407);
    }

    private int getPreferredWidth() {
        return this.getContext().getResources().getDimensionPixelSize(17105408);
    }

    private boolean hasVoiceSearch() {
        Parcelable parcelable = this.mSearchable;
        boolean bl = false;
        if (parcelable != null && parcelable.getVoiceSearchEnabled()) {
            parcelable = null;
            if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
                parcelable = this.mVoiceWebSearchIntent;
            } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
                parcelable = this.mVoiceAppSearchIntent;
            }
            if (parcelable != null) {
                if (this.getContext().getPackageManager().resolveActivity((Intent)parcelable, 65536) != null) {
                    bl = true;
                }
                return bl;
            }
        }
        return false;
    }

    static boolean isLandscapeMode(Context context) {
        boolean bl = context.getResources().getConfiguration().orientation == 2;
        return bl;
    }

    private boolean isSubmitAreaEnabled() {
        boolean bl = (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !this.isIconified();
        return bl;
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            this.getContext().startActivity(intent);
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed launch activity: ");
            stringBuilder.append(intent);
            Log.e(LOG_TAG, stringBuilder.toString(), runtimeException);
        }
    }

    private void launchQuerySearch(int n, String object, String string2) {
        object = this.createIntent("android.intent.action.SEARCH", null, null, string2, n, (String)object);
        this.getContext().startActivity((Intent)object);
    }

    private boolean launchSuggestion(int n, int n2, String string2) {
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor != null && cursor.moveToPosition(n)) {
            this.launchIntent(this.createIntentFromSuggestion(cursor, n2, string2));
            return true;
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void onCloseClicked() {
        if (TextUtils.isEmpty(this.mSearchSrcTextView.getText())) {
            OnCloseListener onCloseListener;
            if (this.mIconifiedByDefault && ((onCloseListener = this.mOnCloseListener) == null || !onCloseListener.onClose())) {
                this.clearFocus();
                this.updateViewsVisibility(true);
            }
        } else {
            this.mSearchSrcTextView.setText("");
            this.mSearchSrcTextView.requestFocus();
            this.mSearchSrcTextView.setImeVisibility(true);
        }
    }

    private boolean onItemClicked(int n, int n2, String object) {
        object = this.mOnSuggestionListener;
        if (object != null && object.onSuggestionClick(n)) {
            return false;
        }
        this.launchSuggestion(n, 0, null);
        this.mSearchSrcTextView.setImeVisibility(false);
        this.dismissSuggestions();
        return true;
    }

    private boolean onItemSelected(int n) {
        OnSuggestionListener onSuggestionListener = this.mOnSuggestionListener;
        if (onSuggestionListener != null && onSuggestionListener.onSuggestionSelect(n)) {
            return false;
        }
        this.rewriteQueryFromSuggestion(n);
        return true;
    }

    private void onSearchClicked() {
        this.updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        this.mSearchSrcTextView.setImeVisibility(true);
        View.OnClickListener onClickListener = this.mOnSearchClickListener;
        if (onClickListener != null) {
            onClickListener.onClick(this);
        }
    }

    private void onSubmitQuery() {
        OnQueryTextListener onQueryTextListener;
        Editable editable = this.mSearchSrcTextView.getText();
        if (!(editable == null || TextUtils.getTrimmedLength(editable) <= 0 || (onQueryTextListener = this.mOnQueryChangeListener) != null && onQueryTextListener.onQueryTextSubmit(editable.toString()))) {
            if (this.mSearchable != null) {
                this.launchQuerySearch(0, null, editable.toString());
            }
            this.mSearchSrcTextView.setImeVisibility(false);
            this.dismissSuggestions();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean onSuggestionsKey(View object, int n, KeyEvent object2) {
        if (this.mSearchable == null) {
            return false;
        }
        if (this.mSuggestionsAdapter == null) {
            return false;
        }
        if (((KeyEvent)object2).getAction() != 0 || !((KeyEvent)object2).hasNoModifiers()) return false;
        if (n == 66 || n == 84 || n == 61) return this.onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
        if (n != 21 && n != 22) {
            int n2;
            if (n == 19 && this.mSearchSrcTextView.getListSelection() == 0) {
                return false;
            }
            object = this.mSearchable.findActionKey(n);
            if (object == null || ((SearchableInfo.ActionKeyInfo)object).getSuggestActionMsg() == null && ((SearchableInfo.ActionKeyInfo)object).getSuggestActionMsgColumn() == null || (n2 = this.mSearchSrcTextView.getListSelection()) == -1 || !(object2 = this.mSuggestionsAdapter.getCursor()).moveToPosition(n2) || (object = SearchView.getActionKeyMessage((Cursor)object2, (SearchableInfo.ActionKeyInfo)object)) == null || ((String)object).length() <= 0) return false;
            return this.onItemClicked(n2, n, (String)object);
        }
        n = n == 21 ? 0 : this.mSearchSrcTextView.length();
        this.mSearchSrcTextView.setSelection(n);
        this.mSearchSrcTextView.setListSelection(0);
        this.mSearchSrcTextView.clearListSelection();
        this.mSearchSrcTextView.ensureImeVisible(true);
        return true;
    }

    private void onTextChanged(CharSequence charSequence) {
        Editable editable = this.mSearchSrcTextView.getText();
        this.mUserQuery = editable;
        boolean bl = TextUtils.isEmpty(editable);
        boolean bl2 = true;
        this.updateSubmitButton(bl ^= true);
        if (bl) {
            bl2 = false;
        }
        this.updateVoiceButton(bl2);
        this.updateCloseButton();
        this.updateSubmitArea();
        if (this.mOnQueryChangeListener != null && !TextUtils.equals(charSequence, this.mOldQueryText)) {
            this.mOnQueryChangeListener.onQueryTextChange(charSequence.toString());
        }
        this.mOldQueryText = charSequence.toString();
    }

    private void onVoiceClicked() {
        if (this.mSearchable == null) {
            return;
        }
        Parcelable parcelable = this.mSearchable;
        try {
            if (parcelable.getVoiceSearchLaunchWebSearch()) {
                parcelable = this.createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, (SearchableInfo)parcelable);
                this.getContext().startActivity((Intent)parcelable);
            } else if (parcelable.getVoiceSearchLaunchRecognizer()) {
                parcelable = this.createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, (SearchableInfo)parcelable);
                this.getContext().startActivity((Intent)parcelable);
            }
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.w(LOG_TAG, "Could not find voice search activity");
        }
    }

    private void postUpdateFocusedState() {
        this.post(this.mUpdateDrawableStateRunnable);
    }

    private void rewriteQueryFromSuggestion(int n) {
        Editable editable = this.mSearchSrcTextView.getText();
        Object object = this.mSuggestionsAdapter.getCursor();
        if (object == null) {
            return;
        }
        if (object.moveToPosition(n)) {
            if ((object = this.mSuggestionsAdapter.convertToString((Cursor)object)) != null) {
                this.setQuery((CharSequence)object);
            } else {
                this.setQuery(editable);
            }
        } else {
            this.setQuery(editable);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private void setQuery(CharSequence charSequence) {
        this.mSearchSrcTextView.setText(charSequence, true);
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        int n = TextUtils.isEmpty(charSequence) ? 0 : charSequence.length();
        searchAutoComplete.setSelection(n);
    }

    private void updateCloseButton() {
        boolean bl = TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        int n = 1;
        boolean bl2 = bl ^ true;
        int n2 = 0;
        int n3 = n;
        if (!bl2) {
            n3 = this.mIconifiedByDefault && !this.mExpandedInActionView ? n : 0;
        }
        int[] arrn = this.mCloseButton;
        n3 = n3 != 0 ? n2 : 8;
        arrn.setVisibility(n3);
        Drawable drawable2 = this.mCloseButton.getDrawable();
        if (drawable2 != null) {
            arrn = bl2 ? ENABLED_STATE_SET : EMPTY_STATE_SET;
            drawable2.setState(arrn);
        }
    }

    private void updateFocusedState() {
        int[] arrn = this.mSearchSrcTextView.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable drawable2 = this.mSearchPlate.getBackground();
        if (drawable2 != null) {
            drawable2.setState(arrn);
        }
        if ((drawable2 = this.mSubmitArea.getBackground()) != null) {
            drawable2.setState(arrn);
        }
        this.invalidate();
    }

    private void updateQueryHint() {
        CharSequence charSequence = this.getQueryHint();
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        if (charSequence == null) {
            charSequence = "";
        }
        searchAutoComplete.setHint(this.getDecoratedHint(charSequence));
    }

    private void updateSearchAutoComplete() {
        this.mSearchSrcTextView.setDropDownAnimationStyle(0);
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int n = this.mSearchable.getInputType();
        int n2 = 1;
        int n3 = n;
        if ((n & 15) == 1) {
            n3 = n &= -65537;
            if (this.mSearchable.getSuggestAuthority() != null) {
                n3 = n | 65536 | 524288;
            }
        }
        this.mSearchSrcTextView.setInputType(n3);
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            this.mSuggestionsAdapter = new SuggestionsAdapter(this.getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
            cursorAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
            n3 = this.mQueryRefinement ? 2 : n2;
            ((SuggestionsAdapter)cursorAdapter).setQueryRefinement(n3);
        }
    }

    @UnsupportedAppUsage
    private void updateSubmitArea() {
        int n;
        block2 : {
            block3 : {
                int n2;
                n = n2 = 8;
                if (!this.isSubmitAreaEnabled()) break block2;
                if (this.mGoButton.getVisibility() == 0) break block3;
                n = n2;
                if (this.mVoiceButton.getVisibility() != 0) break block2;
            }
            n = 0;
        }
        this.mSubmitArea.setVisibility(n);
    }

    @UnsupportedAppUsage
    private void updateSubmitButton(boolean bl) {
        int n;
        block2 : {
            block3 : {
                int n2;
                n = n2 = 8;
                if (!this.mSubmitButtonEnabled) break block2;
                n = n2;
                if (!this.isSubmitAreaEnabled()) break block2;
                n = n2;
                if (!this.hasFocus()) break block2;
                if (bl) break block3;
                n = n2;
                if (this.mVoiceButtonEnabled) break block2;
            }
            n = 0;
        }
        this.mGoButton.setVisibility(n);
    }

    @UnsupportedAppUsage
    private void updateViewsVisibility(boolean bl) {
        this.mIconified = bl;
        int n = 8;
        boolean bl2 = false;
        int n2 = bl ? 0 : 8;
        boolean bl3 = TextUtils.isEmpty(this.mSearchSrcTextView.getText()) ^ true;
        this.mSearchButton.setVisibility(n2);
        this.updateSubmitButton(bl3);
        View view = this.mSearchEditFrame;
        n2 = bl ? n : 0;
        view.setVisibility(n2);
        n2 = this.mCollapsedIcon.getDrawable() != null && !this.mIconifiedByDefault ? 0 : 8;
        this.mCollapsedIcon.setVisibility(n2);
        this.updateCloseButton();
        bl = bl2;
        if (!bl3) {
            bl = true;
        }
        this.updateVoiceButton(bl);
        this.updateSubmitArea();
    }

    private void updateVoiceButton(boolean bl) {
        int n;
        int n2 = n = 8;
        if (this.mVoiceButtonEnabled) {
            n2 = n;
            if (!this.isIconified()) {
                n2 = n;
                if (bl) {
                    n2 = 0;
                    this.mGoButton.setVisibility(8);
                }
            }
        }
        this.mVoiceButton.setVisibility(n2);
    }

    @Override
    public void clearFocus() {
        this.mClearingFocus = true;
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mSearchSrcTextView.setImeVisibility(false);
        this.mClearingFocus = false;
    }

    @Override
    public CharSequence getAccessibilityClassName() {
        return SearchView.class.getName();
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public CharSequence getQueryHint() {
        Object object;
        object = this.mQueryHint != null ? this.mQueryHint : ((object = this.mSearchable) != null && ((SearchableInfo)object).getHintId() != 0 ? this.getContext().getText(this.mSearchable.getHintId()) : this.mDefaultQueryHint);
        return object;
    }

    int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    @Deprecated
    public boolean isIconfiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    public boolean isIconifiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public boolean isQueryRefinementEnabled() {
        return this.mQueryRefinement;
    }

    public boolean isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled;
    }

    @Override
    public void onActionViewCollapsed() {
        this.setQuery("", false);
        this.clearFocus();
        this.updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override
    public void onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions();
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions | 33554432);
        this.mSearchSrcTextView.setText("");
        this.setIconified(false);
    }

    @Override
    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mUpdateDrawableStateRunnable);
        this.post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        Parcelable parcelable = this.mSearchable;
        if (parcelable == null) {
            return false;
        }
        if ((parcelable = ((SearchableInfo)parcelable).findActionKey(n)) != null && ((SearchableInfo.ActionKeyInfo)parcelable).getQueryActionMsg() != null) {
            this.launchQuerySearch(n, ((SearchableInfo.ActionKeyInfo)parcelable).getQueryActionMsg(), this.mSearchSrcTextView.getText().toString());
            return true;
        }
        return super.onKeyDown(n, keyEvent);
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (bl) {
            this.getChildBoundsWithinSearchView(this.mSearchSrcTextView, this.mSearchSrcTextViewBounds);
            this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, n4 - n2);
            UpdatableTouchDelegate updatableTouchDelegate = this.mTouchDelegate;
            if (updatableTouchDelegate == null) {
                this.mTouchDelegate = new UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, this.mSearchSrcTextView);
                this.setTouchDelegate(this.mTouchDelegate);
            } else {
                updatableTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds);
            }
        }
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.isIconified()) {
            super.onMeasure(n, n2);
            return;
        }
        int n3 = View.MeasureSpec.getMode(n);
        int n4 = View.MeasureSpec.getSize(n);
        if (n3 != Integer.MIN_VALUE) {
            if (n3 != 0) {
                if (n3 != 1073741824) {
                    n = n4;
                } else {
                    n3 = this.mMaxWidth;
                    n = n4;
                    if (n3 > 0) {
                        n = Math.min(n3, n4);
                    }
                }
            } else {
                n = this.mMaxWidth;
                if (n <= 0) {
                    n = this.getPreferredWidth();
                }
            }
        } else {
            n = this.mMaxWidth;
            n = n > 0 ? Math.min(n, n4) : Math.min(this.getPreferredWidth(), n4);
        }
        n4 = View.MeasureSpec.getMode(n2);
        n2 = View.MeasureSpec.getSize(n2);
        if (n4 != Integer.MIN_VALUE) {
            if (n4 == 0) {
                n2 = this.getPreferredHeight();
            }
        } else {
            n2 = Math.min(this.getPreferredHeight(), n2);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec(n, 1073741824), View.MeasureSpec.makeMeasureSpec(n2, 1073741824));
    }

    void onQueryRefine(CharSequence charSequence) {
        this.setQuery(charSequence);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.updateViewsVisibility(((SavedState)parcelable).isIconified);
        this.requestLayout();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isIconified = this.isIconified();
        return savedState;
    }

    void onTextFocusChanged() {
        this.updateViewsVisibility(this.isIconified());
        this.postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            this.forceSuggestionQuery();
        }
    }

    @Override
    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.postUpdateFocusedState();
    }

    @Override
    public boolean requestFocus(int n, Rect rect) {
        if (this.mClearingFocus) {
            return false;
        }
        if (!this.isFocusable()) {
            return false;
        }
        if (!this.isIconified()) {
            boolean bl = this.mSearchSrcTextView.requestFocus(n, rect);
            if (bl) {
                this.updateViewsVisibility(false);
            }
            return bl;
        }
        return super.requestFocus(n, rect);
    }

    public void setAppSearchData(Bundle bundle) {
        this.mAppSearchData = bundle;
    }

    public void setIconified(boolean bl) {
        if (bl) {
            this.onCloseClicked();
        } else {
            this.onSearchClicked();
        }
    }

    public void setIconifiedByDefault(boolean bl) {
        if (this.mIconifiedByDefault == bl) {
            return;
        }
        this.mIconifiedByDefault = bl;
        this.updateViewsVisibility(bl);
        this.updateQueryHint();
    }

    public void setImeOptions(int n) {
        this.mSearchSrcTextView.setImeOptions(n);
    }

    public void setInputType(int n) {
        this.mSearchSrcTextView.setInputType(n);
    }

    public void setMaxWidth(int n) {
        this.mMaxWidth = n;
        this.requestLayout();
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnQueryTextFocusChangeListener = onFocusChangeListener;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener;
    }

    public void setQuery(CharSequence charSequence, boolean bl) {
        this.mSearchSrcTextView.setText(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.mUserQuery = charSequence;
        }
        if (bl && !TextUtils.isEmpty(charSequence)) {
            this.onSubmitQuery();
        }
    }

    public void setQueryHint(CharSequence charSequence) {
        this.mQueryHint = charSequence;
        this.updateQueryHint();
    }

    public void setQueryRefinementEnabled(boolean bl) {
        this.mQueryRefinement = bl;
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (cursorAdapter instanceof SuggestionsAdapter) {
            cursorAdapter = (SuggestionsAdapter)cursorAdapter;
            int n = bl ? 2 : 1;
            ((SuggestionsAdapter)cursorAdapter).setQueryRefinement(n);
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.mSearchable = searchableInfo;
        if (this.mSearchable != null) {
            this.updateSearchAutoComplete();
            this.updateQueryHint();
        }
        this.mVoiceButtonEnabled = this.hasVoiceSearch();
        if (this.mVoiceButtonEnabled) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSubmitButtonEnabled(boolean bl) {
        this.mSubmitButtonEnabled = bl;
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.mSuggestionsAdapter = cursorAdapter;
        this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
    }

    public static interface OnCloseListener {
        public boolean onClose();
    }

    public static interface OnQueryTextListener {
        public boolean onQueryTextChange(String var1);

        public boolean onQueryTextSubmit(String var1);
    }

    public static interface OnSuggestionListener {
        public boolean onSuggestionClick(int var1);

        public boolean onSuggestionSelect(int var1);
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
        boolean isIconified;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.isIconified = (Boolean)parcel.readValue(null);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SearchView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" isIconified=");
            stringBuilder.append(this.isIconified);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeValue(this.isIconified);
        }

    }

    public static class SearchAutoComplete
    extends AutoCompleteTextView {
        private boolean mHasPendingShowSoftInputRequest;
        final Runnable mRunShowSoftInputIfNecessary = new _$$Lambda$SearchView$SearchAutoComplete$qdPU54FiW6QTzCbsg7P4cSs3cJ8(this);
        private SearchView mSearchView;
        private int mThreshold = this.getThreshold();

        public SearchAutoComplete(Context context) {
            super(context);
        }

        @UnsupportedAppUsage
        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int n) {
            super(context, attributeSet, n);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int n, int n2) {
            super(context, attributeSet, n, n2);
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = this.getResources().getConfiguration();
            int n = configuration.screenWidthDp;
            int n2 = configuration.screenHeightDp;
            int n3 = configuration.orientation;
            if (n >= 960 && n2 >= 720 && n3 == 2) {
                return 256;
            }
            if (n < 600 && (n < 640 || n2 < 480)) {
                return 160;
            }
            return 192;
        }

        private boolean isEmpty() {
            boolean bl = TextUtils.getTrimmedLength(this.getText()) == 0;
            return bl;
        }

        private void setImeVisibility(boolean bl) {
            InputMethodManager inputMethodManager = this.getContext().getSystemService(InputMethodManager.class);
            if (!bl) {
                this.mHasPendingShowSoftInputRequest = false;
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
                return;
            }
            if (inputMethodManager.isActive(this)) {
                this.mHasPendingShowSoftInputRequest = false;
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.showSoftInput(this, 0);
                return;
            }
            this.mHasPendingShowSoftInputRequest = true;
        }

        private void showSoftInputIfNecessary() {
            if (this.mHasPendingShowSoftInputRequest) {
                this.getContext().getSystemService(InputMethodManager.class).showSoftInput(this, 0);
                this.mHasPendingShowSoftInputRequest = false;
            }
        }

        @Override
        public boolean checkInputConnectionProxy(View view) {
            boolean bl = view == this.mSearchView;
            return bl;
        }

        @Override
        public boolean enoughToFilter() {
            boolean bl = this.mThreshold <= 0 || super.enoughToFilter();
            return bl;
        }

        public /* synthetic */ void lambda$new$0$SearchView$SearchAutoComplete() {
            this.showSoftInputIfNecessary();
        }

        @Override
        public InputConnection onCreateInputConnection(EditorInfo object) {
            object = super.onCreateInputConnection((EditorInfo)object);
            if (this.mHasPendingShowSoftInputRequest) {
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                this.post(this.mRunShowSoftInputIfNecessary);
            }
            return object;
        }

        @Override
        protected void onFinishInflate() {
            super.onFinishInflate();
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            this.setMinWidth((int)TypedValue.applyDimension(1, this.getSearchViewTextMinWidthDp(), displayMetrics));
        }

        @Override
        protected void onFocusChanged(boolean bl, int n, Rect rect) {
            super.onFocusChanged(bl, n, rect);
            this.mSearchView.onTextFocusChanged();
        }

        @Override
        public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
            boolean bl = super.onKeyPreIme(n, keyEvent);
            if (bl && n == 4 && keyEvent.getAction() == 1) {
                this.setImeVisibility(false);
            }
            return bl;
        }

        @Override
        public void onWindowFocusChanged(boolean bl) {
            super.onWindowFocusChanged(bl);
            if (bl && this.mSearchView.hasFocus() && this.getVisibility() == 0) {
                this.mHasPendingShowSoftInputRequest = true;
                if (SearchView.isLandscapeMode(this.getContext())) {
                    this.ensureImeVisible(true);
                }
            }
        }

        @Override
        public void performCompletion() {
        }

        @Override
        protected void replaceText(CharSequence charSequence) {
        }

        void setSearchView(SearchView searchView) {
            this.mSearchView = searchView;
        }

        @Override
        public void setThreshold(int n) {
            super.setThreshold(n);
            this.mThreshold = n;
        }
    }

    private static class UpdatableTouchDelegate
    extends TouchDelegate {
        private final Rect mActualBounds;
        private boolean mDelegateTargeted;
        private final View mDelegateView;
        private final int mSlop;
        private final Rect mSlopBounds;
        private final Rect mTargetBounds;

        public UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.mSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
            this.mTargetBounds = new Rect();
            this.mSlopBounds = new Rect();
            this.mActualBounds = new Rect();
            this.setBounds(rect, rect2);
            this.mDelegateView = view;
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            int n = (int)motionEvent.getX();
            int n2 = (int)motionEvent.getY();
            boolean bl = false;
            int n3 = 1;
            boolean bl2 = false;
            int n4 = motionEvent.getAction();
            if (n4 != 0) {
                if (n4 != 1 && n4 != 2) {
                    if (n4 != 3) {
                        n4 = n3;
                    } else {
                        bl = this.mDelegateTargeted;
                        this.mDelegateTargeted = false;
                        n4 = n3;
                    }
                } else {
                    boolean bl3;
                    bl = bl3 = this.mDelegateTargeted;
                    n4 = n3;
                    if (bl3) {
                        bl = bl3;
                        n4 = n3;
                        if (!this.mSlopBounds.contains(n, n2)) {
                            n4 = 0;
                            bl = bl3;
                        }
                    }
                }
            } else {
                n4 = n3;
                if (this.mTargetBounds.contains(n, n2)) {
                    this.mDelegateTargeted = true;
                    bl = true;
                    n4 = n3;
                }
            }
            if (bl) {
                if (n4 != 0 && !this.mActualBounds.contains(n, n2)) {
                    motionEvent.setLocation(this.mDelegateView.getWidth() / 2, this.mDelegateView.getHeight() / 2);
                } else {
                    motionEvent.setLocation(n - this.mActualBounds.left, n2 - this.mActualBounds.top);
                }
                bl2 = this.mDelegateView.dispatchTouchEvent(motionEvent);
            }
            return bl2;
        }

        public void setBounds(Rect rect, Rect rect2) {
            this.mTargetBounds.set(rect);
            this.mSlopBounds.set(rect);
            rect = this.mSlopBounds;
            int n = this.mSlop;
            rect.inset(-n, -n);
            this.mActualBounds.set(rect2);
        }
    }

}

