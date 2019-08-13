/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceDataStore;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.internal.R;
import com.android.internal.util.CharSequences;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Deprecated
public class Preference
implements Comparable<Preference> {
    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;
    private boolean mBaseMethodCalled;
    private Context mContext;
    private Object mDefaultValue;
    private String mDependencyKey;
    private boolean mDependencyMet = true;
    private List<Preference> mDependents;
    private boolean mEnabled = true;
    private Bundle mExtras;
    private String mFragment;
    private boolean mHasSingleLineTitleAttr;
    private Drawable mIcon;
    private int mIconResId;
    private boolean mIconSpaceReserved;
    private long mId;
    private Intent mIntent;
    private String mKey;
    @UnsupportedAppUsage
    private int mLayoutResId = 17367225;
    private OnPreferenceChangeInternalListener mListener;
    private OnPreferenceChangeListener mOnChangeListener;
    private OnPreferenceClickListener mOnClickListener;
    private int mOrder = Integer.MAX_VALUE;
    private boolean mParentDependencyMet = true;
    private PreferenceGroup mParentGroup;
    private boolean mPersistent = true;
    private PreferenceDataStore mPreferenceDataStore;
    private PreferenceManager mPreferenceManager;
    private boolean mRecycleEnabled = true;
    private boolean mRequiresKey;
    private boolean mSelectable = true;
    private boolean mShouldDisableView = true;
    private boolean mSingleLineTitle = true;
    @UnsupportedAppUsage
    private CharSequence mSummary;
    private CharSequence mTitle;
    private int mTitleRes;
    @UnsupportedAppUsage
    private int mWidgetLayoutResId;

    public Preference(Context context) {
        this(context, null);
    }

    public Preference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842894);
    }

    public Preference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Preference(Context object, AttributeSet attributeSet, int n, int n2) {
        this.mContext = object;
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.Preference, n, n2);
        block19 : for (n = object.getIndexCount() - 1; n >= 0; --n) {
            n2 = ((TypedArray)object).getIndex(n);
            switch (n2) {
                default: {
                    continue block19;
                }
                case 16: {
                    this.mIconSpaceReserved = ((TypedArray)object).getBoolean(n2, this.mIconSpaceReserved);
                    continue block19;
                }
                case 15: {
                    this.mSingleLineTitle = ((TypedArray)object).getBoolean(n2, this.mSingleLineTitle);
                    this.mHasSingleLineTitleAttr = true;
                    continue block19;
                }
                case 14: {
                    this.mRecycleEnabled = ((TypedArray)object).getBoolean(n2, this.mRecycleEnabled);
                    continue block19;
                }
                case 13: {
                    this.mFragment = ((TypedArray)object).getString(n2);
                    continue block19;
                }
                case 12: {
                    this.mShouldDisableView = ((TypedArray)object).getBoolean(n2, this.mShouldDisableView);
                    continue block19;
                }
                case 11: {
                    this.mDefaultValue = this.onGetDefaultValue((TypedArray)object, n2);
                    continue block19;
                }
                case 10: {
                    this.mDependencyKey = ((TypedArray)object).getString(n2);
                    continue block19;
                }
                case 9: {
                    this.mWidgetLayoutResId = ((TypedArray)object).getResourceId(n2, this.mWidgetLayoutResId);
                    continue block19;
                }
                case 8: {
                    this.mOrder = ((TypedArray)object).getInt(n2, this.mOrder);
                    continue block19;
                }
                case 7: {
                    this.mSummary = ((TypedArray)object).getText(n2);
                    continue block19;
                }
                case 6: {
                    this.mKey = ((TypedArray)object).getString(n2);
                    continue block19;
                }
                case 5: {
                    this.mSelectable = ((TypedArray)object).getBoolean(n2, true);
                    continue block19;
                }
                case 4: {
                    this.mTitleRes = ((TypedArray)object).getResourceId(n2, 0);
                    this.mTitle = ((TypedArray)object).getText(n2);
                    continue block19;
                }
                case 3: {
                    this.mLayoutResId = ((TypedArray)object).getResourceId(n2, this.mLayoutResId);
                    continue block19;
                }
                case 2: {
                    this.mEnabled = ((TypedArray)object).getBoolean(n2, true);
                    continue block19;
                }
                case 1: {
                    this.mPersistent = ((TypedArray)object).getBoolean(n2, this.mPersistent);
                    continue block19;
                }
                case 0: {
                    this.mIconResId = ((TypedArray)object).getResourceId(n2, 0);
                }
            }
        }
        ((TypedArray)object).recycle();
    }

    private void dispatchSetInitialValue() {
        if (this.getPreferenceDataStore() != null) {
            this.onSetInitialValue(true, this.mDefaultValue);
            return;
        }
        if (this.shouldPersist() && this.getSharedPreferences().contains(this.mKey)) {
            this.onSetInitialValue(true, null);
        } else {
            Object object = this.mDefaultValue;
            if (object != null) {
                this.onSetInitialValue(false, object);
            }
        }
    }

    private void registerDependency() {
        if (TextUtils.isEmpty(this.mDependencyKey)) {
            return;
        }
        Object object = this.findPreferenceInHierarchy(this.mDependencyKey);
        if (object != null) {
            ((Preference)object).registerDependent(this);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Dependency \"");
        ((StringBuilder)object).append(this.mDependencyKey);
        ((StringBuilder)object).append("\" not found for preference \"");
        ((StringBuilder)object).append(this.mKey);
        ((StringBuilder)object).append("\" (title: \"");
        ((StringBuilder)object).append((Object)this.mTitle);
        ((StringBuilder)object).append("\"");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    @UnsupportedAppUsage
    private void registerDependent(Preference preference) {
        if (this.mDependents == null) {
            this.mDependents = new ArrayList<Preference>();
        }
        this.mDependents.add(preference);
        preference.onDependencyChanged(this, this.shouldDisableDependents());
    }

    private void setEnabledStateOnViews(View view, boolean bl) {
        view.setEnabled(bl);
        if (view instanceof ViewGroup) {
            view = (ViewGroup)view;
            for (int i = view.getChildCount() - 1; i >= 0; --i) {
                this.setEnabledStateOnViews(((ViewGroup)view).getChildAt(i), bl);
            }
        }
    }

    private void tryCommit(SharedPreferences.Editor editor) {
        if (this.mPreferenceManager.shouldCommit()) {
            try {
                editor.apply();
            }
            catch (AbstractMethodError abstractMethodError) {
                editor.commit();
            }
        }
    }

    private void unregisterDependency() {
        Object object = this.mDependencyKey;
        if (object != null && (object = this.findPreferenceInHierarchy((String)object)) != null) {
            Preference.super.unregisterDependent(this);
        }
    }

    private void unregisterDependent(Preference preference) {
        List<Preference> list = this.mDependents;
        if (list != null) {
            list.remove(preference);
        }
    }

    void assignParent(PreferenceGroup preferenceGroup) {
        this.mParentGroup = preferenceGroup;
    }

    protected boolean callChangeListener(Object object) {
        OnPreferenceChangeListener onPreferenceChangeListener = this.mOnChangeListener;
        boolean bl = onPreferenceChangeListener == null || onPreferenceChangeListener.onPreferenceChange(this, object);
        return bl;
    }

    @Override
    public int compareTo(Preference object) {
        int n = this.mOrder;
        int n2 = ((Preference)object).mOrder;
        if (n != n2) {
            return n - n2;
        }
        CharSequence charSequence = this.mTitle;
        object = ((Preference)object).mTitle;
        if (charSequence == object) {
            return 0;
        }
        if (charSequence == null) {
            return 1;
        }
        if (object == null) {
            return -1;
        }
        return CharSequences.compareToIgnoreCase(charSequence, (CharSequence)object);
    }

    void dispatchRestoreInstanceState(Bundle bundle) {
        if (this.hasKey() && (bundle = bundle.getParcelable(this.mKey)) != null) {
            this.mBaseMethodCalled = false;
            this.onRestoreInstanceState(bundle);
            if (!this.mBaseMethodCalled) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    void dispatchSaveInstanceState(Bundle bundle) {
        if (this.hasKey()) {
            this.mBaseMethodCalled = false;
            Parcelable parcelable = this.onSaveInstanceState();
            if (this.mBaseMethodCalled) {
                if (parcelable != null) {
                    bundle.putParcelable(this.mKey, parcelable);
                }
            } else {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
        }
    }

    protected Preference findPreferenceInHierarchy(String string2) {
        PreferenceManager preferenceManager;
        if (!TextUtils.isEmpty(string2) && (preferenceManager = this.mPreferenceManager) != null) {
            return preferenceManager.findPreference(string2);
        }
        return null;
    }

    public Context getContext() {
        return this.mContext;
    }

    public String getDependency() {
        return this.mDependencyKey;
    }

    public SharedPreferences.Editor getEditor() {
        if (this.mPreferenceManager != null && this.getPreferenceDataStore() == null) {
            return this.mPreferenceManager.getEditor();
        }
        return null;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    StringBuilder getFilterableStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        CharSequence charSequence = this.getTitle();
        if (!TextUtils.isEmpty(charSequence)) {
            stringBuilder.append(charSequence);
            stringBuilder.append(' ');
        }
        if (!TextUtils.isEmpty(charSequence = this.getSummary())) {
            stringBuilder.append(charSequence);
            stringBuilder.append(' ');
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        return stringBuilder;
    }

    public String getFragment() {
        return this.mFragment;
    }

    public Drawable getIcon() {
        if (this.mIcon == null && this.mIconResId != 0) {
            this.mIcon = this.getContext().getDrawable(this.mIconResId);
        }
        return this.mIcon;
    }

    @UnsupportedAppUsage
    long getId() {
        return this.mId;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    public String getKey() {
        return this.mKey;
    }

    public int getLayoutResource() {
        return this.mLayoutResId;
    }

    public OnPreferenceChangeListener getOnPreferenceChangeListener() {
        return this.mOnChangeListener;
    }

    public OnPreferenceClickListener getOnPreferenceClickListener() {
        return this.mOnClickListener;
    }

    public int getOrder() {
        return this.mOrder;
    }

    public PreferenceGroup getParent() {
        return this.mParentGroup;
    }

    protected boolean getPersistedBoolean(boolean bl) {
        if (!this.shouldPersist()) {
            return bl;
        }
        PreferenceDataStore preferenceDataStore = this.getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getBoolean(this.mKey, bl);
        }
        return this.mPreferenceManager.getSharedPreferences().getBoolean(this.mKey, bl);
    }

    protected float getPersistedFloat(float f) {
        if (!this.shouldPersist()) {
            return f;
        }
        PreferenceDataStore preferenceDataStore = this.getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getFloat(this.mKey, f);
        }
        return this.mPreferenceManager.getSharedPreferences().getFloat(this.mKey, f);
    }

    protected int getPersistedInt(int n) {
        if (!this.shouldPersist()) {
            return n;
        }
        PreferenceDataStore preferenceDataStore = this.getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getInt(this.mKey, n);
        }
        return this.mPreferenceManager.getSharedPreferences().getInt(this.mKey, n);
    }

    protected long getPersistedLong(long l) {
        if (!this.shouldPersist()) {
            return l;
        }
        PreferenceDataStore preferenceDataStore = this.getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getLong(this.mKey, l);
        }
        return this.mPreferenceManager.getSharedPreferences().getLong(this.mKey, l);
    }

    protected String getPersistedString(String string2) {
        if (!this.shouldPersist()) {
            return string2;
        }
        PreferenceDataStore preferenceDataStore = this.getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getString(this.mKey, string2);
        }
        return this.mPreferenceManager.getSharedPreferences().getString(this.mKey, string2);
    }

    public Set<String> getPersistedStringSet(Set<String> set) {
        if (!this.shouldPersist()) {
            return set;
        }
        PreferenceDataStore preferenceDataStore = this.getPreferenceDataStore();
        if (preferenceDataStore != null) {
            return preferenceDataStore.getStringSet(this.mKey, set);
        }
        return this.mPreferenceManager.getSharedPreferences().getStringSet(this.mKey, set);
    }

    public PreferenceDataStore getPreferenceDataStore() {
        Object object = this.mPreferenceDataStore;
        if (object != null) {
            return object;
        }
        object = this.mPreferenceManager;
        if (object != null) {
            return ((PreferenceManager)object).getPreferenceDataStore();
        }
        return null;
    }

    public PreferenceManager getPreferenceManager() {
        return this.mPreferenceManager;
    }

    public SharedPreferences getSharedPreferences() {
        if (this.mPreferenceManager != null && this.getPreferenceDataStore() == null) {
            return this.mPreferenceManager.getSharedPreferences();
        }
        return null;
    }

    public boolean getShouldDisableView() {
        return this.mShouldDisableView;
    }

    public CharSequence getSummary() {
        return this.mSummary;
    }

    public CharSequence getTitle() {
        return this.mTitle;
    }

    public int getTitleRes() {
        return this.mTitleRes;
    }

    public View getView(View view, ViewGroup viewGroup) {
        View view2 = view;
        if (view == null) {
            view2 = this.onCreateView(viewGroup);
        }
        this.onBindView(view2);
        return view2;
    }

    public int getWidgetLayoutResource() {
        return this.mWidgetLayoutResId;
    }

    public boolean hasKey() {
        return TextUtils.isEmpty(this.mKey) ^ true;
    }

    public boolean isEnabled() {
        boolean bl = this.mEnabled && this.mDependencyMet && this.mParentDependencyMet;
        return bl;
    }

    public boolean isIconSpaceReserved() {
        return this.mIconSpaceReserved;
    }

    public boolean isPersistent() {
        return this.mPersistent;
    }

    public boolean isRecycleEnabled() {
        return this.mRecycleEnabled;
    }

    public boolean isSelectable() {
        return this.mSelectable;
    }

    public boolean isSingleLineTitle() {
        return this.mSingleLineTitle;
    }

    protected void notifyChanged() {
        OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
        if (onPreferenceChangeInternalListener != null) {
            onPreferenceChangeInternalListener.onPreferenceChange(this);
        }
    }

    public void notifyDependencyChange(boolean bl) {
        List<Preference> list = this.mDependents;
        if (list == null) {
            return;
        }
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            list.get(i).onDependencyChanged(this, bl);
        }
    }

    protected void notifyHierarchyChanged() {
        OnPreferenceChangeInternalListener onPreferenceChangeInternalListener = this.mListener;
        if (onPreferenceChangeInternalListener != null) {
            onPreferenceChangeInternalListener.onPreferenceHierarchyChange(this);
        }
    }

    protected void onAttachedToActivity() {
        this.registerDependency();
    }

    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        this.mPreferenceManager = preferenceManager;
        this.mId = preferenceManager.getNextId();
        this.dispatchSetInitialValue();
    }

    protected void onBindView(View view) {
        Object object;
        int n;
        View view2 = (TextView)view.findViewById(16908310);
        int n2 = 8;
        if (view2 != null) {
            object = this.getTitle();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                ((TextView)view2).setText((CharSequence)object);
                view2.setVisibility(0);
                if (this.mHasSingleLineTitleAttr) {
                    ((TextView)view2).setSingleLine(this.mSingleLineTitle);
                }
            } else {
                view2.setVisibility(8);
            }
        }
        if ((view2 = (TextView)view.findViewById(16908304)) != null) {
            object = this.getSummary();
            if (!TextUtils.isEmpty((CharSequence)object)) {
                ((TextView)view2).setText((CharSequence)object);
                view2.setVisibility(0);
            } else {
                view2.setVisibility(8);
            }
        }
        if ((view2 = (ImageView)view.findViewById(16908294)) != null) {
            if (this.mIconResId != 0 || this.mIcon != null) {
                if (this.mIcon == null) {
                    this.mIcon = this.getContext().getDrawable(this.mIconResId);
                }
                if ((object = this.mIcon) != null) {
                    ((ImageView)view2).setImageDrawable((Drawable)object);
                }
            }
            if (this.mIcon != null) {
                ((ImageView)view2).setVisibility(0);
            } else {
                n = this.mIconSpaceReserved ? 4 : 8;
                ((ImageView)view2).setVisibility(n);
            }
        }
        if ((object = view.findViewById(16908350)) != null) {
            if (this.mIcon != null) {
                ((View)object).setVisibility(0);
            } else {
                n = n2;
                if (this.mIconSpaceReserved) {
                    n = 4;
                }
                ((View)object).setVisibility(n);
            }
        }
        if (this.mShouldDisableView) {
            this.setEnabledStateOnViews(view, this.isEnabled());
        }
    }

    protected void onClick() {
    }

    protected View onCreateView(ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
        View view = layoutInflater.inflate(this.mLayoutResId, viewGroup, false);
        viewGroup = (ViewGroup)view.findViewById(16908312);
        if (viewGroup != null) {
            int n = this.mWidgetLayoutResId;
            if (n != 0) {
                layoutInflater.inflate(n, viewGroup);
            } else {
                viewGroup.setVisibility(8);
            }
        }
        return view;
    }

    public void onDependencyChanged(Preference preference, boolean bl) {
        if (this.mDependencyMet == bl) {
            this.mDependencyMet = bl ^ true;
            this.notifyDependencyChange(this.shouldDisableDependents());
            this.notifyChanged();
        }
    }

    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return null;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        return false;
    }

    public void onParentChanged(Preference preference, boolean bl) {
        if (this.mParentDependencyMet == bl) {
            this.mParentDependencyMet = bl ^ true;
            this.notifyDependencyChange(this.shouldDisableDependents());
            this.notifyChanged();
        }
    }

    protected void onPrepareForRemoval() {
        this.unregisterDependency();
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        this.mBaseMethodCalled = true;
        if (parcelable != BaseSavedState.EMPTY_STATE && parcelable != null) {
            throw new IllegalArgumentException("Wrong state class -- expecting Preference State");
        }
    }

    protected Parcelable onSaveInstanceState() {
        this.mBaseMethodCalled = true;
        return BaseSavedState.EMPTY_STATE;
    }

    protected void onSetInitialValue(boolean bl, Object object) {
    }

    public Bundle peekExtras() {
        return this.mExtras;
    }

    @UnsupportedAppUsage
    public void performClick(PreferenceScreen preferenceScreen) {
        if (!this.isEnabled()) {
            return;
        }
        this.onClick();
        Object object = this.mOnClickListener;
        if (object != null && object.onPreferenceClick(this)) {
            return;
        }
        object = this.getPreferenceManager();
        if (object != null) {
            object = ((PreferenceManager)object).getOnPreferenceTreeClickListener();
            if (preferenceScreen != null && object != null && object.onPreferenceTreeClick(preferenceScreen, this)) {
                return;
            }
        }
        if (this.mIntent != null) {
            this.getContext().startActivity(this.mIntent);
        }
    }

    protected boolean persistBoolean(boolean bl) {
        if (!this.shouldPersist()) {
            return false;
        }
        if (bl == this.getPersistedBoolean(bl ^ true)) {
            return true;
        }
        Object object = this.getPreferenceDataStore();
        if (object != null) {
            object.putBoolean(this.mKey, bl);
        } else {
            object = this.mPreferenceManager.getEditor();
            object.putBoolean(this.mKey, bl);
            this.tryCommit((SharedPreferences.Editor)object);
        }
        return true;
    }

    protected boolean persistFloat(float f) {
        if (!this.shouldPersist()) {
            return false;
        }
        if (f == this.getPersistedFloat(Float.NaN)) {
            return true;
        }
        Object object = this.getPreferenceDataStore();
        if (object != null) {
            object.putFloat(this.mKey, f);
        } else {
            object = this.mPreferenceManager.getEditor();
            object.putFloat(this.mKey, f);
            this.tryCommit((SharedPreferences.Editor)object);
        }
        return true;
    }

    protected boolean persistInt(int n) {
        if (!this.shouldPersist()) {
            return false;
        }
        if (n == this.getPersistedInt(n)) {
            return true;
        }
        Object object = this.getPreferenceDataStore();
        if (object != null) {
            object.putInt(this.mKey, n);
        } else {
            object = this.mPreferenceManager.getEditor();
            object.putInt(this.mKey, n);
            this.tryCommit((SharedPreferences.Editor)object);
        }
        return true;
    }

    protected boolean persistLong(long l) {
        if (!this.shouldPersist()) {
            return false;
        }
        if (l == this.getPersistedLong(l)) {
            return true;
        }
        Object object = this.getPreferenceDataStore();
        if (object != null) {
            object.putLong(this.mKey, l);
        } else {
            object = this.mPreferenceManager.getEditor();
            object.putLong(this.mKey, l);
            this.tryCommit((SharedPreferences.Editor)object);
        }
        return true;
    }

    protected boolean persistString(String string2) {
        if (!this.shouldPersist()) {
            return false;
        }
        if (TextUtils.equals(string2, this.getPersistedString(null))) {
            return true;
        }
        Object object = this.getPreferenceDataStore();
        if (object != null) {
            object.putString(this.mKey, string2);
        } else {
            object = this.mPreferenceManager.getEditor();
            object.putString(this.mKey, string2);
            this.tryCommit((SharedPreferences.Editor)object);
        }
        return true;
    }

    public boolean persistStringSet(Set<String> set) {
        if (!this.shouldPersist()) {
            return false;
        }
        if (set.equals(this.getPersistedStringSet(null))) {
            return true;
        }
        Object object = this.getPreferenceDataStore();
        if (object != null) {
            object.putStringSet(this.mKey, set);
        } else {
            object = this.mPreferenceManager.getEditor();
            object.putStringSet(this.mKey, set);
            this.tryCommit((SharedPreferences.Editor)object);
        }
        return true;
    }

    void requireKey() {
        if (this.mKey != null) {
            this.mRequiresKey = true;
            return;
        }
        throw new IllegalStateException("Preference does not have a key assigned.");
    }

    public void restoreHierarchyState(Bundle bundle) {
        this.dispatchRestoreInstanceState(bundle);
    }

    public void saveHierarchyState(Bundle bundle) {
        this.dispatchSaveInstanceState(bundle);
    }

    public void setDefaultValue(Object object) {
        this.mDefaultValue = object;
    }

    public void setDependency(String string2) {
        this.unregisterDependency();
        this.mDependencyKey = string2;
        this.registerDependency();
    }

    public void setEnabled(boolean bl) {
        if (this.mEnabled != bl) {
            this.mEnabled = bl;
            this.notifyDependencyChange(this.shouldDisableDependents());
            this.notifyChanged();
        }
    }

    public void setFragment(String string2) {
        this.mFragment = string2;
    }

    public void setIcon(int n) {
        if (this.mIconResId != n) {
            this.mIconResId = n;
            this.setIcon(this.mContext.getDrawable(n));
        }
    }

    public void setIcon(Drawable drawable2) {
        if (drawable2 == null && this.mIcon != null || drawable2 != null && this.mIcon != drawable2) {
            this.mIcon = drawable2;
            this.notifyChanged();
        }
    }

    public void setIconSpaceReserved(boolean bl) {
        this.mIconSpaceReserved = bl;
        this.notifyChanged();
    }

    public void setIntent(Intent intent) {
        this.mIntent = intent;
    }

    public void setKey(String string2) {
        this.mKey = string2;
        if (this.mRequiresKey && !this.hasKey()) {
            this.requireKey();
        }
    }

    public void setLayoutResource(int n) {
        if (n != this.mLayoutResId) {
            this.mRecycleEnabled = false;
        }
        this.mLayoutResId = n;
    }

    @UnsupportedAppUsage
    final void setOnPreferenceChangeInternalListener(OnPreferenceChangeInternalListener onPreferenceChangeInternalListener) {
        this.mListener = onPreferenceChangeInternalListener;
    }

    public void setOnPreferenceChangeListener(OnPreferenceChangeListener onPreferenceChangeListener) {
        this.mOnChangeListener = onPreferenceChangeListener;
    }

    public void setOnPreferenceClickListener(OnPreferenceClickListener onPreferenceClickListener) {
        this.mOnClickListener = onPreferenceClickListener;
    }

    public void setOrder(int n) {
        if (n != this.mOrder) {
            this.mOrder = n;
            this.notifyHierarchyChanged();
        }
    }

    public void setPersistent(boolean bl) {
        this.mPersistent = bl;
    }

    public void setPreferenceDataStore(PreferenceDataStore preferenceDataStore) {
        this.mPreferenceDataStore = preferenceDataStore;
    }

    public void setRecycleEnabled(boolean bl) {
        this.mRecycleEnabled = bl;
        this.notifyChanged();
    }

    public void setSelectable(boolean bl) {
        if (this.mSelectable != bl) {
            this.mSelectable = bl;
            this.notifyChanged();
        }
    }

    public void setShouldDisableView(boolean bl) {
        this.mShouldDisableView = bl;
        this.notifyChanged();
    }

    public void setSingleLineTitle(boolean bl) {
        this.mHasSingleLineTitleAttr = true;
        this.mSingleLineTitle = bl;
        this.notifyChanged();
    }

    public void setSummary(int n) {
        this.setSummary(this.mContext.getString(n));
    }

    public void setSummary(CharSequence charSequence) {
        if (charSequence == null && this.mSummary != null || charSequence != null && !charSequence.equals(this.mSummary)) {
            this.mSummary = charSequence;
            this.notifyChanged();
        }
    }

    public void setTitle(int n) {
        this.setTitle(this.mContext.getString(n));
        this.mTitleRes = n;
    }

    public void setTitle(CharSequence charSequence) {
        if (charSequence == null && this.mTitle != null || charSequence != null && !charSequence.equals(this.mTitle)) {
            this.mTitleRes = 0;
            this.mTitle = charSequence;
            this.notifyChanged();
        }
    }

    public void setWidgetLayoutResource(int n) {
        if (n != this.mWidgetLayoutResId) {
            this.mRecycleEnabled = false;
        }
        this.mWidgetLayoutResId = n;
    }

    public boolean shouldCommit() {
        PreferenceManager preferenceManager = this.mPreferenceManager;
        if (preferenceManager == null) {
            return false;
        }
        return preferenceManager.shouldCommit();
    }

    public boolean shouldDisableDependents() {
        return this.isEnabled() ^ true;
    }

    protected boolean shouldPersist() {
        boolean bl = this.mPreferenceManager != null && this.isPersistent() && this.hasKey();
        return bl;
    }

    public String toString() {
        return this.getFilterableStringBuilder().toString();
    }

    @Deprecated
    public static class BaseSavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.Creator<BaseSavedState>(){

            @Override
            public BaseSavedState createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            public BaseSavedState[] newArray(int n) {
                return new BaseSavedState[n];
            }
        };

        public BaseSavedState(Parcel parcel) {
            super(parcel);
        }

        public BaseSavedState(Parcelable parcelable) {
            super(parcelable);
        }

    }

    static interface OnPreferenceChangeInternalListener {
        public void onPreferenceChange(Preference var1);

        public void onPreferenceHierarchyChange(Preference var1);
    }

    @Deprecated
    public static interface OnPreferenceChangeListener {
        public boolean onPreferenceChange(Preference var1, Object var2);
    }

    @Deprecated
    public static interface OnPreferenceClickListener {
        public boolean onPreferenceClick(Preference var1);
    }

}

