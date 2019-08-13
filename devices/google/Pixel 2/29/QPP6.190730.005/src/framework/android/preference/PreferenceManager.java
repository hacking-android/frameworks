/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 */
package android.preference;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.XmlResourceParser;
import android.os.BaseBundle;
import android.os.Bundle;
import android.preference.GenericInflater;
import android.preference.Preference;
import android.preference.PreferenceDataStore;
import android.preference.PreferenceFragment;
import android.preference.PreferenceInflater;
import android.preference.PreferenceScreen;
import android.util.AttributeSet;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

@Deprecated
public class PreferenceManager {
    public static final String KEY_HAS_SET_DEFAULT_VALUES = "_has_set_default_values";
    public static final String METADATA_KEY_PREFERENCES = "android.preference";
    private static final int STORAGE_CREDENTIAL_PROTECTED = 2;
    private static final int STORAGE_DEFAULT = 0;
    private static final int STORAGE_DEVICE_PROTECTED = 1;
    private static final String TAG = "PreferenceManager";
    private Activity mActivity;
    @UnsupportedAppUsage
    private List<OnActivityDestroyListener> mActivityDestroyListeners;
    private List<OnActivityResultListener> mActivityResultListeners;
    private List<OnActivityStopListener> mActivityStopListeners;
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private PreferenceFragment mFragment;
    private long mNextId = 0L;
    private int mNextRequestCode;
    private boolean mNoCommit;
    @UnsupportedAppUsage
    private OnPreferenceTreeClickListener mOnPreferenceTreeClickListener;
    private PreferenceDataStore mPreferenceDataStore;
    private PreferenceScreen mPreferenceScreen;
    private List<DialogInterface> mPreferencesScreens;
    @UnsupportedAppUsage
    private SharedPreferences mSharedPreferences;
    private int mSharedPreferencesMode;
    private String mSharedPreferencesName;
    private int mStorage = 0;

    @UnsupportedAppUsage
    public PreferenceManager(Activity activity, int n) {
        this.mActivity = activity;
        this.mNextRequestCode = n;
        this.init(activity);
    }

    @UnsupportedAppUsage
    PreferenceManager(Context context) {
        this.init(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void dismissAllScreens() {
        ArrayList<DialogInterface> arrayList;
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                return;
            }
            arrayList = new ArrayList<DialogInterface>(this.mPreferencesScreens);
            this.mPreferencesScreens.clear();
        }
        int n = arrayList.size() - 1;
        while (n >= 0) {
            arrayList.get(n).dismiss();
            --n;
        }
        return;
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences(PreferenceManager.getDefaultSharedPreferencesName(context), PreferenceManager.getDefaultSharedPreferencesMode());
    }

    private static int getDefaultSharedPreferencesMode() {
        return 0;
    }

    public static String getDefaultSharedPreferencesName(Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(context.getPackageName());
        stringBuilder.append("_preferences");
        return stringBuilder.toString();
    }

    private void init(Context context) {
        this.mContext = context;
        this.setSharedPreferencesName(PreferenceManager.getDefaultSharedPreferencesName(context));
    }

    private List<ResolveInfo> queryIntentActivities(Intent intent) {
        return this.mContext.getPackageManager().queryIntentActivities(intent, 128);
    }

    public static void setDefaultValues(Context context, int n, boolean bl) {
        PreferenceManager.setDefaultValues(context, PreferenceManager.getDefaultSharedPreferencesName(context), PreferenceManager.getDefaultSharedPreferencesMode(), n, bl);
    }

    public static void setDefaultValues(Context object, String string2, int n, int n2, boolean bl) {
        SharedPreferences sharedPreferences = ((Context)object).getSharedPreferences(KEY_HAS_SET_DEFAULT_VALUES, 0);
        if (bl || !sharedPreferences.getBoolean(KEY_HAS_SET_DEFAULT_VALUES, false)) {
            PreferenceManager preferenceManager = new PreferenceManager((Context)object);
            preferenceManager.setSharedPreferencesName(string2);
            preferenceManager.setSharedPreferencesMode(n);
            preferenceManager.inflateFromResource((Context)object, n2, null);
            object = sharedPreferences.edit().putBoolean(KEY_HAS_SET_DEFAULT_VALUES, true);
            try {
                object.apply();
            }
            catch (AbstractMethodError abstractMethodError) {
                object.commit();
            }
        }
    }

    @UnsupportedAppUsage
    private void setNoCommit(boolean bl) {
        SharedPreferences.Editor editor;
        if (!bl && (editor = this.mEditor) != null) {
            try {
                editor.apply();
            }
            catch (AbstractMethodError abstractMethodError) {
                this.mEditor.commit();
            }
        }
        this.mNoCommit = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void addPreferencesScreen(DialogInterface dialogInterface) {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                ArrayList<DialogInterface> arrayList = new ArrayList<DialogInterface>();
                this.mPreferencesScreens = arrayList;
            }
            this.mPreferencesScreens.add(dialogInterface);
            return;
        }
    }

    public PreferenceScreen createPreferenceScreen(Context object) {
        object = new PreferenceScreen((Context)object, null);
        ((Preference)object).onAttachedToHierarchy(this);
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    void dispatchActivityDestroy() {
        ArrayList<OnActivityDestroyListener> arrayList = null;
        // MONITORENTER : this
        if (this.mActivityDestroyListeners != null) {
            arrayList = new ArrayList<OnActivityDestroyListener>(this.mActivityDestroyListeners);
        }
        // MONITOREXIT : this
        if (arrayList != null) {
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                ((OnActivityDestroyListener)arrayList.get(i)).onActivityDestroy();
            }
        }
        this.dismissAllScreens();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void dispatchActivityResult(int n, int n2, Intent intent) {
        ArrayList<OnActivityResultListener> arrayList;
        synchronized (this) {
            if (this.mActivityResultListeners == null) {
                return;
            }
            arrayList = new ArrayList<OnActivityResultListener>(this.mActivityResultListeners);
        }
        int n3 = arrayList.size();
        int n4 = 0;
        while (n4 < n3) {
            if (((OnActivityResultListener)arrayList.get(n4)).onActivityResult(n, n2, intent)) {
                return;
            }
            ++n4;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void dispatchActivityStop() {
        ArrayList<OnActivityStopListener> arrayList;
        synchronized (this) {
            if (this.mActivityStopListeners == null) {
                return;
            }
            arrayList = new ArrayList<OnActivityStopListener>(this.mActivityStopListeners);
        }
        int n = arrayList.size();
        int n2 = 0;
        while (n2 < n) {
            ((OnActivityStopListener)arrayList.get(n2)).onActivityStop();
            ++n2;
        }
        return;
    }

    void dispatchNewIntent(Intent intent) {
        this.dismissAllScreens();
    }

    public Preference findPreference(CharSequence charSequence) {
        PreferenceScreen preferenceScreen = this.mPreferenceScreen;
        if (preferenceScreen == null) {
            return null;
        }
        return preferenceScreen.findPreference(charSequence);
    }

    @UnsupportedAppUsage
    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    @UnsupportedAppUsage
    SharedPreferences.Editor getEditor() {
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (this.mNoCommit) {
            if (this.mEditor == null) {
                this.mEditor = this.getSharedPreferences().edit();
            }
            return this.mEditor;
        }
        return this.getSharedPreferences().edit();
    }

    @UnsupportedAppUsage
    PreferenceFragment getFragment() {
        return this.mFragment;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    long getNextId() {
        synchronized (this) {
            long l = this.mNextId;
            this.mNextId = 1L + l;
            return l;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    int getNextRequestCode() {
        synchronized (this) {
            int n = this.mNextRequestCode;
            this.mNextRequestCode = n + 1;
            return n;
        }
    }

    OnPreferenceTreeClickListener getOnPreferenceTreeClickListener() {
        return this.mOnPreferenceTreeClickListener;
    }

    public PreferenceDataStore getPreferenceDataStore() {
        return this.mPreferenceDataStore;
    }

    @UnsupportedAppUsage
    PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceScreen;
    }

    public SharedPreferences getSharedPreferences() {
        if (this.mPreferenceDataStore != null) {
            return null;
        }
        if (this.mSharedPreferences == null) {
            int n = this.mStorage;
            Context context = n != 1 ? (n != 2 ? this.mContext : this.mContext.createCredentialProtectedStorageContext()) : this.mContext.createDeviceProtectedStorageContext();
            this.mSharedPreferences = context.getSharedPreferences(this.mSharedPreferencesName, this.mSharedPreferencesMode);
        }
        return this.mSharedPreferences;
    }

    public int getSharedPreferencesMode() {
        return this.mSharedPreferencesMode;
    }

    public String getSharedPreferencesName() {
        return this.mSharedPreferencesName;
    }

    @UnsupportedAppUsage
    PreferenceScreen inflateFromIntent(Intent object, PreferenceScreen object2) {
        List<ResolveInfo> list = this.queryIntentActivities((Intent)object);
        HashSet<Object> hashSet = new HashSet<Object>();
        for (int i = list.size() - 1; i >= 0; --i) {
            Object object3 = list.get((int)i).activityInfo;
            Object object4 = ((ActivityInfo)object3).metaData;
            object = object2;
            if (object4 != null) {
                if (!((BaseBundle)object4).containsKey(METADATA_KEY_PREFERENCES)) {
                    object = object2;
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(((ActivityInfo)object3).packageName);
                    ((StringBuilder)object).append(":");
                    ((StringBuilder)object).append(((ActivityInfo)object3).metaData.getInt(METADATA_KEY_PREFERENCES));
                    object4 = ((StringBuilder)object).toString();
                    object = object2;
                    if (!hashSet.contains(object4)) {
                        hashSet.add(object4);
                        try {
                            object4 = this.mContext.createPackageContext(((ActivityInfo)object3).packageName, 0);
                            object = new PreferenceInflater((Context)object4, this);
                        }
                        catch (PackageManager.NameNotFoundException nameNotFoundException) {
                            object4 = new StringBuilder();
                            ((StringBuilder)object4).append("Could not create context for ");
                            ((StringBuilder)object4).append(((ActivityInfo)object3).packageName);
                            ((StringBuilder)object4).append(": ");
                            ((StringBuilder)object4).append(Log.getStackTraceString(nameNotFoundException));
                            Log.w(TAG, ((StringBuilder)object4).toString());
                            object = object2;
                        }
                        object3 = ((PackageItemInfo)object3).loadXmlMetaData(((Context)object4).getPackageManager(), METADATA_KEY_PREFERENCES);
                        object = (PreferenceScreen)((GenericInflater)object).inflate((XmlPullParser)object3, object2, true);
                        object3.close();
                    }
                }
            }
            object2 = object;
        }
        ((Preference)object2).onAttachedToHierarchy(this);
        return object2;
    }

    @UnsupportedAppUsage
    public PreferenceScreen inflateFromResource(Context object, int n, PreferenceScreen preferenceScreen) {
        this.setNoCommit(true);
        object = (PreferenceScreen)new PreferenceInflater((Context)object, this).inflate(n, preferenceScreen, true);
        ((Preference)object).onAttachedToHierarchy(this);
        this.setNoCommit(false);
        return object;
    }

    @SystemApi
    public boolean isStorageCredentialProtected() {
        boolean bl = this.mStorage == 2;
        return bl;
    }

    public boolean isStorageDefault() {
        boolean bl = this.mStorage == 0;
        return bl;
    }

    public boolean isStorageDeviceProtected() {
        int n = this.mStorage;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void registerOnActivityDestroyListener(OnActivityDestroyListener onActivityDestroyListener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners == null) {
                ArrayList<OnActivityDestroyListener> arrayList = new ArrayList<OnActivityDestroyListener>();
                this.mActivityDestroyListeners = arrayList;
            }
            if (!this.mActivityDestroyListeners.contains(onActivityDestroyListener)) {
                this.mActivityDestroyListeners.add(onActivityDestroyListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void registerOnActivityResultListener(OnActivityResultListener onActivityResultListener) {
        synchronized (this) {
            if (this.mActivityResultListeners == null) {
                ArrayList<OnActivityResultListener> arrayList = new ArrayList<OnActivityResultListener>();
                this.mActivityResultListeners = arrayList;
            }
            if (!this.mActivityResultListeners.contains(onActivityResultListener)) {
                this.mActivityResultListeners.add(onActivityResultListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void registerOnActivityStopListener(OnActivityStopListener onActivityStopListener) {
        synchronized (this) {
            if (this.mActivityStopListeners == null) {
                ArrayList<OnActivityStopListener> arrayList = new ArrayList<OnActivityStopListener>();
                this.mActivityStopListeners = arrayList;
            }
            if (!this.mActivityStopListeners.contains(onActivityStopListener)) {
                this.mActivityStopListeners.add(onActivityStopListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void removePreferencesScreen(DialogInterface dialogInterface) {
        synchronized (this) {
            if (this.mPreferencesScreens == null) {
                return;
            }
            this.mPreferencesScreens.remove(dialogInterface);
            return;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    void setFragment(PreferenceFragment preferenceFragment) {
        this.mFragment = preferenceFragment;
    }

    void setOnPreferenceTreeClickListener(OnPreferenceTreeClickListener onPreferenceTreeClickListener) {
        this.mOnPreferenceTreeClickListener = onPreferenceTreeClickListener;
    }

    public void setPreferenceDataStore(PreferenceDataStore preferenceDataStore) {
        this.mPreferenceDataStore = preferenceDataStore;
    }

    @UnsupportedAppUsage
    boolean setPreferences(PreferenceScreen preferenceScreen) {
        if (preferenceScreen != this.mPreferenceScreen) {
            this.mPreferenceScreen = preferenceScreen;
            return true;
        }
        return false;
    }

    public void setSharedPreferencesMode(int n) {
        this.mSharedPreferencesMode = n;
        this.mSharedPreferences = null;
    }

    public void setSharedPreferencesName(String string2) {
        this.mSharedPreferencesName = string2;
        this.mSharedPreferences = null;
    }

    @SystemApi
    public void setStorageCredentialProtected() {
        this.mStorage = 2;
        this.mSharedPreferences = null;
    }

    public void setStorageDefault() {
        this.mStorage = 0;
        this.mSharedPreferences = null;
    }

    public void setStorageDeviceProtected() {
        this.mStorage = 1;
        this.mSharedPreferences = null;
    }

    @UnsupportedAppUsage
    boolean shouldCommit() {
        return this.mNoCommit ^ true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void unregisterOnActivityDestroyListener(OnActivityDestroyListener onActivityDestroyListener) {
        synchronized (this) {
            if (this.mActivityDestroyListeners != null) {
                this.mActivityDestroyListeners.remove(onActivityDestroyListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    void unregisterOnActivityResultListener(OnActivityResultListener onActivityResultListener) {
        synchronized (this) {
            if (this.mActivityResultListeners != null) {
                this.mActivityResultListeners.remove(onActivityResultListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void unregisterOnActivityStopListener(OnActivityStopListener onActivityStopListener) {
        synchronized (this) {
            if (this.mActivityStopListeners != null) {
                this.mActivityStopListeners.remove(onActivityStopListener);
            }
            return;
        }
    }

    @Deprecated
    public static interface OnActivityDestroyListener {
        public void onActivityDestroy();
    }

    @Deprecated
    public static interface OnActivityResultListener {
        public boolean onActivityResult(int var1, int var2, Intent var3);
    }

    @Deprecated
    public static interface OnActivityStopListener {
        public void onActivityStop();
    }

    @Deprecated
    public static interface OnPreferenceTreeClickListener {
        public boolean onPreferenceTreeClick(PreferenceScreen var1, Preference var2);
    }

}

