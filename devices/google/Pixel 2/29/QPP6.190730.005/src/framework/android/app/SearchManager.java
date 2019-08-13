/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityTaskManager;
import android.app.ISearchManager;
import android.app.SearchDialog;
import android.app.SearchableInfo;
import android.app.UiModeManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.Log;
import java.util.List;

public class SearchManager
implements DialogInterface.OnDismissListener,
DialogInterface.OnCancelListener {
    public static final String ACTION_KEY = "action_key";
    public static final String ACTION_MSG = "action_msg";
    public static final String APP_DATA = "app_data";
    public static final String CONTEXT_IS_VOICE = "android.search.CONTEXT_IS_VOICE";
    public static final String CURSOR_EXTRA_KEY_IN_PROGRESS = "in_progress";
    private static final boolean DBG = false;
    @UnsupportedAppUsage
    public static final String DISABLE_VOICE_SEARCH = "android.search.DISABLE_VOICE_SEARCH";
    public static final String EXTRA_DATA_KEY = "intent_extra_data_key";
    public static final String EXTRA_NEW_SEARCH = "new_search";
    public static final String EXTRA_SELECT_QUERY = "select_query";
    public static final String EXTRA_WEB_SEARCH_PENDINGINTENT = "web_search_pendingintent";
    public static final int FLAG_QUERY_REFINEMENT = 1;
    public static final String INTENT_ACTION_GLOBAL_SEARCH = "android.search.action.GLOBAL_SEARCH";
    public static final String INTENT_ACTION_SEARCHABLES_CHANGED = "android.search.action.SEARCHABLES_CHANGED";
    public static final String INTENT_ACTION_SEARCH_SETTINGS = "android.search.action.SEARCH_SETTINGS";
    public static final String INTENT_ACTION_SEARCH_SETTINGS_CHANGED = "android.search.action.SETTINGS_CHANGED";
    public static final String INTENT_ACTION_WEB_SEARCH_SETTINGS = "android.search.action.WEB_SEARCH_SETTINGS";
    public static final String INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED = "android.search.action.GLOBAL_SEARCH_ACTIVITY_CHANGED";
    public static final char MENU_KEY = 's';
    public static final int MENU_KEYCODE = 47;
    public static final String QUERY = "query";
    public static final String SEARCH_MODE = "search_mode";
    public static final String SHORTCUT_MIME_TYPE = "vnd.android.cursor.item/vnd.android.search.suggest";
    public static final String SUGGEST_COLUMN_AUDIO_CHANNEL_CONFIG = "suggest_audio_channel_config";
    public static final String SUGGEST_COLUMN_CONTENT_TYPE = "suggest_content_type";
    public static final String SUGGEST_COLUMN_DURATION = "suggest_duration";
    public static final String SUGGEST_COLUMN_FLAGS = "suggest_flags";
    public static final String SUGGEST_COLUMN_FORMAT = "suggest_format";
    public static final String SUGGEST_COLUMN_ICON_1 = "suggest_icon_1";
    public static final String SUGGEST_COLUMN_ICON_2 = "suggest_icon_2";
    public static final String SUGGEST_COLUMN_INTENT_ACTION = "suggest_intent_action";
    public static final String SUGGEST_COLUMN_INTENT_DATA = "suggest_intent_data";
    public static final String SUGGEST_COLUMN_INTENT_DATA_ID = "suggest_intent_data_id";
    public static final String SUGGEST_COLUMN_INTENT_EXTRA_DATA = "suggest_intent_extra_data";
    public static final String SUGGEST_COLUMN_IS_LIVE = "suggest_is_live";
    public static final String SUGGEST_COLUMN_LAST_ACCESS_HINT = "suggest_last_access_hint";
    public static final String SUGGEST_COLUMN_PRODUCTION_YEAR = "suggest_production_year";
    public static final String SUGGEST_COLUMN_PURCHASE_PRICE = "suggest_purchase_price";
    public static final String SUGGEST_COLUMN_QUERY = "suggest_intent_query";
    public static final String SUGGEST_COLUMN_RATING_SCORE = "suggest_rating_score";
    public static final String SUGGEST_COLUMN_RATING_STYLE = "suggest_rating_style";
    public static final String SUGGEST_COLUMN_RENTAL_PRICE = "suggest_rental_price";
    public static final String SUGGEST_COLUMN_RESULT_CARD_IMAGE = "suggest_result_card_image";
    public static final String SUGGEST_COLUMN_SHORTCUT_ID = "suggest_shortcut_id";
    public static final String SUGGEST_COLUMN_SPINNER_WHILE_REFRESHING = "suggest_spinner_while_refreshing";
    public static final String SUGGEST_COLUMN_TEXT_1 = "suggest_text_1";
    public static final String SUGGEST_COLUMN_TEXT_2 = "suggest_text_2";
    public static final String SUGGEST_COLUMN_TEXT_2_URL = "suggest_text_2_url";
    public static final String SUGGEST_COLUMN_VIDEO_HEIGHT = "suggest_video_height";
    public static final String SUGGEST_COLUMN_VIDEO_WIDTH = "suggest_video_width";
    public static final String SUGGEST_MIME_TYPE = "vnd.android.cursor.dir/vnd.android.search.suggest";
    public static final String SUGGEST_NEVER_MAKE_SHORTCUT = "_-1";
    public static final String SUGGEST_PARAMETER_LIMIT = "limit";
    public static final String SUGGEST_URI_PATH_QUERY = "search_suggest_query";
    public static final String SUGGEST_URI_PATH_SHORTCUT = "search_suggest_shortcut";
    private static final String TAG = "SearchManager";
    public static final String USER_QUERY = "user_query";
    OnCancelListener mCancelListener = null;
    private final Context mContext;
    OnDismissListener mDismissListener = null;
    final Handler mHandler;
    @UnsupportedAppUsage
    private SearchDialog mSearchDialog;
    private final ISearchManager mService;

    @UnsupportedAppUsage
    SearchManager(Context context, Handler handler) throws ServiceManager.ServiceNotFoundException {
        this.mContext = context;
        this.mHandler = handler;
        this.mService = ISearchManager.Stub.asInterface(ServiceManager.getServiceOrThrow("search"));
    }

    private void ensureSearchDialog() {
        if (this.mSearchDialog == null) {
            this.mSearchDialog = new SearchDialog(this.mContext, this);
            this.mSearchDialog.setOnCancelListener(this);
            this.mSearchDialog.setOnDismissListener(this);
        }
    }

    public Intent getAssistIntent(boolean bl) {
        Intent intent;
        block4 : {
            try {
                intent = new Intent("android.intent.action.ASSIST");
                if (!bl) break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            Bundle bundle = ActivityTaskManager.getService().getAssistContextExtras(0);
            if (bundle == null) break block4;
            intent.replaceExtras(bundle);
        }
        return intent;
    }

    public List<ResolveInfo> getGlobalSearchActivities() {
        try {
            List<ResolveInfo> list = this.mService.getGlobalSearchActivities();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ComponentName getGlobalSearchActivity() {
        try {
            ComponentName componentName = this.mService.getGlobalSearchActivity();
            return componentName;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public SearchableInfo getSearchableInfo(ComponentName parcelable) {
        try {
            parcelable = this.mService.getSearchableInfo((ComponentName)parcelable);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public List<SearchableInfo> getSearchablesInGlobalSearch() {
        try {
            List<SearchableInfo> list = this.mService.getSearchablesInGlobalSearch();
            return list;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public Cursor getSuggestions(SearchableInfo searchableInfo, String string2) {
        return this.getSuggestions(searchableInfo, string2, -1);
    }

    @UnsupportedAppUsage
    public Cursor getSuggestions(SearchableInfo arrstring, String object, int n) {
        if (arrstring == null) {
            return null;
        }
        Object object2 = arrstring.getSuggestAuthority();
        if (object2 == null) {
            return null;
        }
        object2 = new Uri.Builder().scheme("content").authority((String)object2).query("").fragment("");
        String string2 = arrstring.getSuggestPath();
        if (string2 != null) {
            ((Uri.Builder)object2).appendEncodedPath(string2);
        }
        ((Uri.Builder)object2).appendPath(SUGGEST_URI_PATH_QUERY);
        string2 = arrstring.getSuggestSelection();
        if (string2 != null) {
            arrstring = new String[]{object};
        } else {
            ((Uri.Builder)object2).appendPath((String)object);
            arrstring = null;
        }
        if (n > 0) {
            ((Uri.Builder)object2).appendQueryParameter(SUGGEST_PARAMETER_LIMIT, String.valueOf(n));
        }
        object = ((Uri.Builder)object2).build();
        return this.mContext.getContentResolver().query((Uri)object, null, string2, arrstring, null);
    }

    @UnsupportedAppUsage
    public ComponentName getWebSearchActivity() {
        try {
            ComponentName componentName = this.mService.getWebSearchActivity();
            return componentName;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isVisible() {
        SearchDialog searchDialog = this.mSearchDialog;
        boolean bl = searchDialog == null ? false : searchDialog.isShowing();
        return bl;
    }

    @UnsupportedAppUsage
    public void launchAssist(Bundle bundle) {
        try {
            if (this.mService == null) {
                return;
            }
            this.mService.launchAssist(bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean launchLegacyAssist(String string2, int n, Bundle bundle) {
        block3 : {
            try {
                if (this.mService != null) break block3;
                return false;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        boolean bl = this.mService.launchLegacyAssist(string2, n, bundle);
        return bl;
    }

    @Deprecated
    @Override
    public void onCancel(DialogInterface object) {
        object = this.mCancelListener;
        if (object != null) {
            object.onCancel();
        }
    }

    @Deprecated
    @Override
    public void onDismiss(DialogInterface object) {
        object = this.mDismissListener;
        if (object != null) {
            object.onDismiss();
        }
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.mCancelListener = onCancelListener;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.mDismissListener = onDismissListener;
    }

    void startGlobalSearch(String string2, boolean bl, Bundle bundle, Rect rect) {
        ComponentName componentName = this.getGlobalSearchActivity();
        if (componentName == null) {
            Log.w(TAG, "No global search activity found.");
            return;
        }
        Intent intent = new Intent(INTENT_ACTION_GLOBAL_SEARCH);
        intent.addFlags(268435456);
        intent.setComponent(componentName);
        bundle = bundle == null ? new Bundle() : new Bundle(bundle);
        if (!bundle.containsKey("source")) {
            bundle.putString("source", this.mContext.getPackageName());
        }
        intent.putExtra(APP_DATA, bundle);
        if (!TextUtils.isEmpty(string2)) {
            intent.putExtra(QUERY, string2);
        }
        if (bl) {
            intent.putExtra(EXTRA_SELECT_QUERY, bl);
        }
        intent.setSourceBounds(rect);
        try {
            this.mContext.startActivity(intent);
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Global search activity not found: ");
            stringBuilder.append(componentName);
            Log.e(TAG, stringBuilder.toString());
        }
    }

    public void startSearch(String string2, boolean bl, ComponentName componentName, Bundle bundle, boolean bl2) {
        this.startSearch(string2, bl, componentName, bundle, bl2, null);
    }

    @UnsupportedAppUsage
    public void startSearch(String string2, boolean bl, ComponentName componentName, Bundle bundle, boolean bl2, Rect rect) {
        if (bl2) {
            this.startGlobalSearch(string2, bl, bundle, rect);
            return;
        }
        if (this.mContext.getSystemService(UiModeManager.class).getCurrentModeType() != 4) {
            this.ensureSearchDialog();
            this.mSearchDialog.show(string2, bl, componentName, bundle);
        }
    }

    public void stopSearch() {
        SearchDialog searchDialog = this.mSearchDialog;
        if (searchDialog != null) {
            searchDialog.cancel();
        }
    }

    public void triggerSearch(String string2, ComponentName componentName, Bundle bundle) {
        if (string2 != null && TextUtils.getTrimmedLength(string2) != 0) {
            this.startSearch(string2, false, componentName, bundle, false);
            this.mSearchDialog.launchQuerySearch();
            return;
        }
        Log.w(TAG, "triggerSearch called with empty query, ignoring.");
    }

    public static interface OnCancelListener {
        public void onCancel();
    }

    public static interface OnDismissListener {
        public void onDismiss();
    }

}

