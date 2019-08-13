/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.GenericInflater;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.android.internal.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public abstract class PreferenceGroup
extends Preference
implements GenericInflater.Parent<Preference> {
    private boolean mAttachedToActivity = false;
    private int mCurrentPreferenceOrder = 0;
    private boolean mOrderingAsAdded = true;
    private List<Preference> mPreferenceList = new ArrayList<Preference>();

    public PreferenceGroup(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PreferenceGroup(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public PreferenceGroup(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.PreferenceGroup, n, n2);
        this.mOrderingAsAdded = ((TypedArray)object).getBoolean(0, this.mOrderingAsAdded);
        ((TypedArray)object).recycle();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean removePreferenceInt(Preference preference) {
        synchronized (this) {
            preference.onPrepareForRemoval();
            if (preference.getParent() != this) return this.mPreferenceList.remove(preference);
            preference.assignParent(null);
            return this.mPreferenceList.remove(preference);
        }
    }

    @Override
    public void addItemFromInflater(Preference preference) {
        this.addPreference(preference);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean addPreference(Preference preference) {
        int n;
        if (this.mPreferenceList.contains(preference)) {
            return true;
        }
        if (preference.getOrder() == Integer.MAX_VALUE) {
            if (this.mOrderingAsAdded) {
                n = this.mCurrentPreferenceOrder;
                this.mCurrentPreferenceOrder = n + 1;
                preference.setOrder(n);
            }
            if (preference instanceof PreferenceGroup) {
                ((PreferenceGroup)preference).setOrderingAsAdded(this.mOrderingAsAdded);
            }
        }
        if (!this.onPrepareAddPreference(preference)) {
            return false;
        }
        synchronized (this) {
            int n2;
            n = n2 = Collections.binarySearch(this.mPreferenceList, preference);
            if (n2 < 0) {
                n = n2 * -1 - 1;
            }
            this.mPreferenceList.add(n, preference);
        }
        preference.onAttachedToHierarchy(this.getPreferenceManager());
        preference.assignParent(this);
        if (this.mAttachedToActivity) {
            preference.onAttachedToActivity();
        }
        this.notifyHierarchyChanged();
        return true;
    }

    @Override
    protected void dispatchRestoreInstanceState(Bundle bundle) {
        super.dispatchRestoreInstanceState(bundle);
        int n = this.getPreferenceCount();
        for (int i = 0; i < n; ++i) {
            this.getPreference(i).dispatchRestoreInstanceState(bundle);
        }
    }

    @Override
    protected void dispatchSaveInstanceState(Bundle bundle) {
        super.dispatchSaveInstanceState(bundle);
        int n = this.getPreferenceCount();
        for (int i = 0; i < n; ++i) {
            this.getPreference(i).dispatchSaveInstanceState(bundle);
        }
    }

    public Preference findPreference(CharSequence charSequence) {
        if (TextUtils.equals(this.getKey(), charSequence)) {
            return this;
        }
        int n = this.getPreferenceCount();
        for (int i = 0; i < n; ++i) {
            Preference preference = this.getPreference(i);
            String string2 = preference.getKey();
            if (string2 != null && string2.equals(charSequence)) {
                return preference;
            }
            if (!(preference instanceof PreferenceGroup) || (preference = ((PreferenceGroup)preference).findPreference(charSequence)) == null) continue;
            return preference;
        }
        return null;
    }

    public Preference getPreference(int n) {
        return this.mPreferenceList.get(n);
    }

    public int getPreferenceCount() {
        return this.mPreferenceList.size();
    }

    protected boolean isOnSameScreenAsChildren() {
        return true;
    }

    public boolean isOrderingAsAdded() {
        return this.mOrderingAsAdded;
    }

    @Override
    public void notifyDependencyChange(boolean bl) {
        super.notifyDependencyChange(bl);
        int n = this.getPreferenceCount();
        for (int i = 0; i < n; ++i) {
            this.getPreference(i).onParentChanged(this, bl);
        }
    }

    @Override
    protected void onAttachedToActivity() {
        super.onAttachedToActivity();
        this.mAttachedToActivity = true;
        int n = this.getPreferenceCount();
        for (int i = 0; i < n; ++i) {
            this.getPreference(i).onAttachedToActivity();
        }
    }

    protected boolean onPrepareAddPreference(Preference preference) {
        preference.onParentChanged(this, this.shouldDisableDependents());
        return true;
    }

    @Override
    protected void onPrepareForRemoval() {
        super.onPrepareForRemoval();
        this.mAttachedToActivity = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void removeAll() {
        synchronized (this) {
            List<Preference> list = this.mPreferenceList;
            int n = list.size() - 1;
            do {
                if (n < 0) {
                    // MONITOREXIT [3, 4, 5] lbl6 : MonitorExitStatement: MONITOREXIT : this
                    this.notifyHierarchyChanged();
                    return;
                }
                this.removePreferenceInt(list.get(0));
                --n;
            } while (true);
        }
    }

    public boolean removePreference(Preference preference) {
        boolean bl = this.removePreferenceInt(preference);
        this.notifyHierarchyChanged();
        return bl;
    }

    public void setOrderingAsAdded(boolean bl) {
        this.mOrderingAsAdded = bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void sortPreferences() {
        synchronized (this) {
            Collections.sort(this.mPreferenceList);
            return;
        }
    }
}

