/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.android.internal.R;

@Deprecated
public abstract class PreferenceFragment
extends Fragment
implements PreferenceManager.OnPreferenceTreeClickListener {
    private static final int FIRST_REQUEST_CODE = 100;
    private static final int MSG_BIND_PREFERENCES = 1;
    private static final String PREFERENCES_TAG = "android:preferences";
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message message) {
            if (message.what == 1) {
                PreferenceFragment.this.bindPreferences();
            }
        }
    };
    private boolean mHavePrefs;
    private boolean mInitDone;
    private int mLayoutResId = 17367244;
    private ListView mList;
    private View.OnKeyListener mListOnKeyListener = new View.OnKeyListener(){

        @Override
        public boolean onKey(View view, int n, KeyEvent keyEvent) {
            Object object = PreferenceFragment.this.mList.getSelectedItem();
            if (object instanceof Preference) {
                view = PreferenceFragment.this.mList.getSelectedView();
                return ((Preference)object).onKey(view, n, keyEvent);
            }
            return false;
        }
    };
    @UnsupportedAppUsage
    private PreferenceManager mPreferenceManager;
    private final Runnable mRequestFocus = new Runnable(){

        @Override
        public void run() {
            PreferenceFragment.this.mList.focusableViewAvailable(PreferenceFragment.this.mList);
        }
    };

    private void bindPreferences() {
        PreferenceScreen preferenceScreen = this.getPreferenceScreen();
        if (preferenceScreen != null) {
            View view = this.getView();
            if (view != null && (view = view.findViewById(16908310)) instanceof TextView) {
                CharSequence charSequence = preferenceScreen.getTitle();
                if (TextUtils.isEmpty(charSequence)) {
                    view.setVisibility(8);
                } else {
                    ((TextView)view).setText(charSequence);
                    view.setVisibility(0);
                }
            }
            preferenceScreen.bind(this.getListView());
        }
        this.onBindPreferences();
    }

    private void ensureList() {
        if (this.mList != null) {
            return;
        }
        View view = this.getView();
        if (view != null) {
            if ((view = view.findViewById(16908298)) instanceof ListView) {
                this.mList = (ListView)view;
                view = this.mList;
                if (view != null) {
                    view.setOnKeyListener(this.mListOnKeyListener);
                    this.mHandler.post(this.mRequestFocus);
                    return;
                }
                throw new RuntimeException("Your content must have a ListView whose id attribute is 'android.R.id.list'");
            }
            throw new RuntimeException("Content has view with id attribute 'android.R.id.list' that is not a ListView class");
        }
        throw new IllegalStateException("Content view not yet created");
    }

    private void postBindPreferences() {
        if (this.mHandler.hasMessages(1)) {
            return;
        }
        this.mHandler.obtainMessage(1).sendToTarget();
    }

    private void requirePreferenceManager() {
        if (this.mPreferenceManager != null) {
            return;
        }
        throw new RuntimeException("This should be called after super.onCreate.");
    }

    public void addPreferencesFromIntent(Intent intent) {
        this.requirePreferenceManager();
        this.setPreferenceScreen(this.mPreferenceManager.inflateFromIntent(intent, this.getPreferenceScreen()));
    }

    public void addPreferencesFromResource(int n) {
        this.requirePreferenceManager();
        this.setPreferenceScreen(this.mPreferenceManager.inflateFromResource(this.getActivity(), n, this.getPreferenceScreen()));
    }

    public Preference findPreference(CharSequence charSequence) {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return null;
        }
        return preferenceManager.findPreference(charSequence);
    }

    @UnsupportedAppUsage
    public ListView getListView() {
        this.ensureList();
        return this.mList;
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public PreferenceScreen getPreferenceScreen() {
        return this.mPreferenceManager.getPreferenceScreen();
    }

    public boolean hasListView() {
        if (this.mList != null) {
            return true;
        }
        View view = this.getView();
        if (view == null) {
            return false;
        }
        if (!((view = view.findViewById(16908298)) instanceof ListView)) {
            return false;
        }
        this.mList = (ListView)view;
        return this.mList != null;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        PreferenceScreen preferenceScreen;
        super.onActivityCreated(bundle);
        if (this.mHavePrefs) {
            this.bindPreferences();
        }
        this.mInitDone = true;
        if (bundle != null && (bundle = bundle.getBundle(PREFERENCES_TAG)) != null && (preferenceScreen = this.getPreferenceScreen()) != null) {
            preferenceScreen.restoreHierarchyState(bundle);
        }
    }

    @Override
    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        this.mPreferenceManager.dispatchActivityResult(n, n2, intent);
    }

    protected void onBindPreferences() {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mPreferenceManager = new PreferenceManager(this.getActivity(), 100);
        this.mPreferenceManager.setFragment(this);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle object) {
        object = this.getActivity().obtainStyledAttributes(null, R.styleable.PreferenceFragment, 16844038, 0);
        this.mLayoutResId = ((TypedArray)object).getResourceId(0, this.mLayoutResId);
        ((TypedArray)object).recycle();
        return layoutInflater.inflate(this.mLayoutResId, viewGroup, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mPreferenceManager.dispatchActivityDestroy();
    }

    @Override
    public void onDestroyView() {
        ListView listView = this.mList;
        if (listView != null) {
            listView.setOnKeyListener(null);
        }
        this.mList = null;
        this.mHandler.removeCallbacks(this.mRequestFocus);
        this.mHandler.removeMessages(1);
        super.onDestroyView();
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        if (preference.getFragment() != null && this.getActivity() instanceof OnPreferenceStartFragmentCallback) {
            return ((OnPreferenceStartFragmentCallback)((Object)this.getActivity())).onPreferenceStartFragment(this, preference);
        }
        return false;
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        PreferenceScreen preferenceScreen = this.getPreferenceScreen();
        if (preferenceScreen != null) {
            Bundle bundle2 = new Bundle();
            preferenceScreen.saveHierarchyState(bundle2);
            bundle.putBundle(PREFERENCES_TAG, bundle2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mPreferenceManager.dispatchActivityStop();
        this.mPreferenceManager.setOnPreferenceTreeClickListener(null);
    }

    protected void onUnbindPreferences() {
    }

    @Override
    public void onViewCreated(View view, Bundle object) {
        super.onViewCreated(view, (Bundle)object);
        object = this.getActivity().obtainStyledAttributes(null, R.styleable.PreferenceFragment, 16844038, 0);
        view = (ListView)view.findViewById(16908298);
        if (view != null && ((TypedArray)object).hasValueOrEmpty(1)) {
            ((ListView)view).setDivider(((TypedArray)object).getDrawable(1));
        }
        ((TypedArray)object).recycle();
    }

    public void setPreferenceScreen(PreferenceScreen preferenceScreen) {
        if (this.mPreferenceManager.setPreferences(preferenceScreen) && preferenceScreen != null) {
            this.onUnbindPreferences();
            this.mHavePrefs = true;
            if (this.mInitDone) {
                this.postBindPreferences();
            }
        }
    }

    @Deprecated
    public static interface OnPreferenceStartFragmentCallback {
        public boolean onPreferenceStartFragment(PreferenceFragment var1, Preference var2);
    }

}

