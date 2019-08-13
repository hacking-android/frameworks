/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.content.Context;
import android.preference.Preference;
import android.preference.PreferenceGroup;
import android.util.AttributeSet;

@Deprecated
public class PreferenceCategory
extends PreferenceGroup {
    private static final String TAG = "PreferenceCategory";

    public PreferenceCategory(Context context) {
        this(context, null);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842892);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public PreferenceCategory(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    protected boolean onPrepareAddPreference(Preference preference) {
        if (!(preference instanceof PreferenceCategory)) {
            return super.onPrepareAddPreference(preference);
        }
        throw new IllegalArgumentException("Cannot add a PreferenceCategory directly to a PreferenceCategory");
    }

    @Override
    public boolean shouldDisableDependents() {
        return super.isEnabled() ^ true;
    }
}

