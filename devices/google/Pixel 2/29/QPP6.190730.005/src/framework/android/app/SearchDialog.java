/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.Dialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SearchView;
import android.widget.TextView;

public class SearchDialog
extends Dialog {
    private static final boolean DBG = false;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final String INSTANCE_KEY_APPDATA = "data";
    private static final String INSTANCE_KEY_COMPONENT = "comp";
    private static final String INSTANCE_KEY_USER_QUERY = "uQry";
    private static final String LOG_TAG = "SearchDialog";
    private static final int SEARCH_PLATE_LEFT_PADDING_NON_GLOBAL = 7;
    private Context mActivityContext;
    private ImageView mAppIcon;
    private Bundle mAppSearchData;
    private TextView mBadgeLabel;
    private View mCloseSearch;
    private BroadcastReceiver mConfChangeListener = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.CONFIGURATION_CHANGED")) {
                SearchDialog.this.onConfigurationChanged();
            }
        }
    };
    private ComponentName mLaunchComponent;
    private final SearchView.OnCloseListener mOnCloseListener = new SearchView.OnCloseListener(){

        @Override
        public boolean onClose() {
            return SearchDialog.this.onClosePressed();
        }
    };
    private final SearchView.OnQueryTextListener mOnQueryChangeListener = new SearchView.OnQueryTextListener(){

        @Override
        public boolean onQueryTextChange(String string2) {
            return false;
        }

        @Override
        public boolean onQueryTextSubmit(String string2) {
            SearchDialog.this.dismiss();
            return false;
        }
    };
    private final SearchView.OnSuggestionListener mOnSuggestionSelectionListener = new SearchView.OnSuggestionListener(){

        @Override
        public boolean onSuggestionClick(int n) {
            SearchDialog.this.dismiss();
            return false;
        }

        @Override
        public boolean onSuggestionSelect(int n) {
            return false;
        }
    };
    private AutoCompleteTextView mSearchAutoComplete;
    private int mSearchAutoCompleteImeOptions;
    private View mSearchPlate;
    private SearchView mSearchView;
    private SearchableInfo mSearchable;
    private String mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    private final Intent mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
    private Drawable mWorkingSpinner;

    public SearchDialog(Context context, SearchManager searchManager) {
        super(context, SearchDialog.resolveDialogTheme(context));
        this.mVoiceWebSearchIntent.addFlags(268435456);
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.mVoiceAppSearchIntent.addFlags(268435456);
    }

    private void createContentView() {
        this.setContentView(17367276);
        this.mSearchView = (SearchView)this.findViewById(16909324);
        this.mSearchView.setIconified(false);
        this.mSearchView.setOnCloseListener(this.mOnCloseListener);
        this.mSearchView.setOnQueryTextListener(this.mOnQueryChangeListener);
        this.mSearchView.setOnSuggestionListener(this.mOnSuggestionSelectionListener);
        this.mSearchView.onActionViewExpanded();
        this.mCloseSearch = this.findViewById(16908327);
        this.mCloseSearch.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                SearchDialog.this.dismiss();
            }
        });
        this.mBadgeLabel = (TextView)this.mSearchView.findViewById(16909315);
        this.mSearchAutoComplete = (AutoCompleteTextView)this.mSearchView.findViewById(16909323);
        this.mAppIcon = (ImageView)this.findViewById(16909314);
        this.mSearchPlate = this.mSearchView.findViewById(16909322);
        this.mWorkingSpinner = this.getContext().getDrawable(17303419);
        this.setWorking(false);
        this.mBadgeLabel.setVisibility(8);
        this.mSearchAutoCompleteImeOptions = this.mSearchAutoComplete.getImeOptions();
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

    private boolean doShow(String string2, boolean bl, ComponentName componentName, Bundle bundle) {
        if (!this.show(componentName, bundle)) {
            return false;
        }
        this.setUserQuery(string2);
        if (bl) {
            this.mSearchAutoComplete.selectAll();
        }
        return true;
    }

    private boolean enoughToFilter() {
        Filterable filterable = (Filterable)((Object)this.mSearchAutoComplete.getAdapter());
        if (filterable != null && filterable.getFilter() != null) {
            return this.mSearchAutoComplete.enoughToFilter();
        }
        return false;
    }

    private boolean isEmpty(AutoCompleteTextView autoCompleteTextView) {
        boolean bl = TextUtils.getTrimmedLength(autoCompleteTextView.getText()) == 0;
        return bl;
    }

    @UnsupportedAppUsage
    static boolean isLandscapeMode(Context context) {
        boolean bl = context.getResources().getConfiguration().orientation == 2;
        return bl;
    }

    private boolean isOutOfBounds(View view, MotionEvent motionEvent) {
        int n = (int)motionEvent.getX();
        int n2 = (int)motionEvent.getY();
        int n3 = ViewConfiguration.get(this.mContext).getScaledWindowTouchSlop();
        boolean bl = n < -n3 || n2 < -n3 || n > view.getWidth() + n3 || n2 > view.getHeight() + n3;
        return bl;
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("launching ");
        stringBuilder.append(intent);
        Log.d(LOG_TAG, stringBuilder.toString());
        try {
            this.getContext().startActivity(intent);
            this.dismiss();
        }
        catch (RuntimeException runtimeException) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Failed launch activity: ");
            stringBuilder.append(intent);
            Log.e(LOG_TAG, stringBuilder.toString(), runtimeException);
        }
    }

    private boolean onClosePressed() {
        if (this.isEmpty(this.mSearchAutoComplete)) {
            this.dismiss();
            return true;
        }
        return false;
    }

    static int resolveDialogTheme(Context context) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(17957062, typedValue, true);
        return typedValue.resourceId;
    }

    private void setUserQuery(String string2) {
        String string3 = string2;
        if (string2 == null) {
            string3 = "";
        }
        this.mUserQuery = string3;
        this.mSearchAutoComplete.setText(string3);
        this.mSearchAutoComplete.setSelection(string3.length());
    }

    private boolean show(ComponentName componentName, Bundle bundle) {
        this.mSearchable = ((SearchManager)this.mContext.getSystemService("search")).getSearchableInfo(componentName);
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo == null) {
            return false;
        }
        this.mLaunchComponent = componentName;
        this.mAppSearchData = bundle;
        this.mActivityContext = searchableInfo.getActivityContext(this.getContext());
        if (!this.isShowing()) {
            this.createContentView();
            this.mSearchView.setSearchableInfo(this.mSearchable);
            this.mSearchView.setAppSearchData(this.mAppSearchData);
            this.show();
        }
        this.updateUI();
        return true;
    }

    private void updateSearchAppIcon() {
        Object object;
        Object object2 = this.getContext().getPackageManager();
        try {
            object = ((PackageManager)object2).getApplicationIcon(object2.getActivityInfo((ComponentName)this.mLaunchComponent, (int)0).applicationInfo);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            object = ((PackageManager)object2).getDefaultActivityIcon();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(this.mLaunchComponent);
            ((StringBuilder)object2).append(" not found, using generic app icon");
            Log.w(LOG_TAG, ((StringBuilder)object2).toString());
        }
        this.mAppIcon.setImageDrawable((Drawable)object);
        this.mAppIcon.setVisibility(0);
        object = this.mSearchPlate;
        ((View)object).setPadding(7, ((View)object).getPaddingTop(), this.mSearchPlate.getPaddingRight(), this.mSearchPlate.getPaddingBottom());
    }

    private void updateSearchAutoComplete() {
        this.mSearchAutoComplete.setDropDownDismissedOnCompletion(false);
        this.mSearchAutoComplete.setForceIgnoreOutsideTouch(false);
    }

    private void updateSearchBadge() {
        Drawable drawable2;
        int n = 8;
        Drawable drawable3 = null;
        String string2 = null;
        if (this.mSearchable.useBadgeIcon()) {
            drawable2 = this.mActivityContext.getDrawable(this.mSearchable.getIconId());
            n = 0;
        } else {
            drawable2 = drawable3;
            if (this.mSearchable.useBadgeLabel()) {
                string2 = this.mActivityContext.getResources().getText(this.mSearchable.getLabelId()).toString();
                n = 0;
                drawable2 = drawable3;
            }
        }
        this.mBadgeLabel.setCompoundDrawablesWithIntrinsicBounds(drawable2, null, null, null);
        this.mBadgeLabel.setText(string2);
        this.mBadgeLabel.setVisibility(n);
    }

    private void updateUI() {
        if (this.mSearchable != null) {
            int n;
            this.mDecor.setVisibility(0);
            this.updateSearchAutoComplete();
            this.updateSearchAppIcon();
            this.updateSearchBadge();
            int n2 = n = this.mSearchable.getInputType();
            if ((n & 15) == 1) {
                n2 = n &= -65537;
                if (this.mSearchable.getSuggestAuthority() != null) {
                    n2 = n | 65536;
                }
            }
            this.mSearchAutoComplete.setInputType(n2);
            this.mSearchAutoCompleteImeOptions = this.mSearchable.getImeOptions();
            this.mSearchAutoComplete.setImeOptions(this.mSearchAutoCompleteImeOptions);
            if (this.mSearchable.getVoiceSearchEnabled()) {
                this.mSearchAutoComplete.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
            } else {
                this.mSearchAutoComplete.setPrivateImeOptions(null);
            }
        }
    }

    @Override
    public void hide() {
        if (!this.isShowing()) {
            return;
        }
        InputMethodManager inputMethodManager = this.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0);
        }
        super.hide();
    }

    @UnsupportedAppUsage
    public void launchQuerySearch() {
        this.launchQuerySearch(0, null);
    }

    @UnsupportedAppUsage
    protected void launchQuerySearch(int n, String string2) {
        this.launchIntent(this.createIntent("android.intent.action.SEARCH", null, null, this.mSearchAutoComplete.getText().toString(), n, string2));
    }

    @Override
    public void onBackPressed() {
        InputMethodManager inputMethodManager = this.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager != null && inputMethodManager.isFullscreenMode() && inputMethodManager.hideSoftInputFromWindow(this.getWindow().getDecorView().getWindowToken(), 0)) {
            return;
        }
        this.cancel();
    }

    public void onConfigurationChanged() {
        if (this.mSearchable != null && this.isShowing()) {
            this.updateSearchAppIcon();
            this.updateSearchBadge();
            if (SearchDialog.isLandscapeMode(this.getContext())) {
                this.mSearchAutoComplete.setInputMethodMode(1);
                if (this.mSearchAutoComplete.isDropDownAlwaysVisible() || this.enoughToFilter()) {
                    this.mSearchAutoComplete.showDropDown();
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = this.getWindow();
        WindowManager.LayoutParams layoutParams = ((Window)object).getAttributes();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.gravity = 55;
        layoutParams.softInputMode = 16;
        ((Window)object).setAttributes(layoutParams);
        this.setCanceledOnTouchOutside(true);
    }

    @Override
    public void onRestoreInstanceState(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        ComponentName componentName = (ComponentName)bundle.getParcelable(INSTANCE_KEY_COMPONENT);
        Bundle bundle2 = bundle.getBundle(INSTANCE_KEY_APPDATA);
        if (!this.doShow(bundle.getString(INSTANCE_KEY_USER_QUERY), false, componentName, bundle2)) {
            return;
        }
    }

    @Override
    public Bundle onSaveInstanceState() {
        if (!this.isShowing()) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_KEY_COMPONENT, this.mLaunchComponent);
        bundle.putBundle(INSTANCE_KEY_APPDATA, this.mAppSearchData);
        bundle.putString(INSTANCE_KEY_USER_QUERY, this.mUserQuery);
        return bundle;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.CONFIGURATION_CHANGED");
        this.getContext().registerReceiver(this.mConfChangeListener, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.getContext().unregisterReceiver(this.mConfChangeListener);
        this.mLaunchComponent = null;
        this.mAppSearchData = null;
        this.mSearchable = null;
        this.mUserQuery = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mSearchAutoComplete.isPopupShowing() && this.isOutOfBounds(this.mSearchPlate, motionEvent)) {
            this.cancel();
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setListSelection(int n) {
        this.mSearchAutoComplete.setListSelection(n);
    }

    @UnsupportedAppUsage
    public void setWorking(boolean bl) {
        Drawable drawable2 = this.mWorkingSpinner;
        int n = bl ? 255 : 0;
        drawable2.setAlpha(n);
        this.mWorkingSpinner.setVisible(bl, false);
        this.mWorkingSpinner.invalidateSelf();
    }

    public boolean show(String string2, boolean bl, ComponentName componentName, Bundle bundle) {
        if (bl = this.doShow(string2, bl, componentName, bundle)) {
            this.mSearchAutoComplete.showDropDownAfterLayout();
        }
        return bl;
    }

    public static class SearchBar
    extends LinearLayout {
        public SearchBar(Context context) {
            super(context);
        }

        public SearchBar(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        @Override
        public ActionMode startActionModeForChild(View view, ActionMode.Callback callback, int n) {
            if (n != 0) {
                return super.startActionModeForChild(view, callback, n);
            }
            return null;
        }
    }

}

