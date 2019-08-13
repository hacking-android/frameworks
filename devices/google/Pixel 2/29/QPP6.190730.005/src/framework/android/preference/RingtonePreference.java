/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.AttributeSet;
import com.android.internal.R;

@Deprecated
public class RingtonePreference
extends Preference
implements PreferenceManager.OnActivityResultListener {
    private static final String TAG = "RingtonePreference";
    @UnsupportedAppUsage
    private int mRequestCode;
    private int mRingtoneType;
    private boolean mShowDefault;
    private boolean mShowSilent;

    public RingtonePreference(Context context) {
        this(context, null);
    }

    public RingtonePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842899);
    }

    public RingtonePreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public RingtonePreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.RingtonePreference, n, n2);
        this.mRingtoneType = ((TypedArray)object).getInt(0, 1);
        this.mShowDefault = ((TypedArray)object).getBoolean(1, true);
        this.mShowSilent = ((TypedArray)object).getBoolean(2, true);
        ((TypedArray)object).recycle();
    }

    public int getRingtoneType() {
        return this.mRingtoneType;
    }

    public boolean getShowDefault() {
        return this.mShowDefault;
    }

    public boolean getShowSilent() {
        return this.mShowSilent;
    }

    @Override
    public boolean onActivityResult(int n, int n2, Intent object) {
        if (n == this.mRequestCode) {
            Uri uri;
            if (object != null && this.callChangeListener(object = (uri = (Uri)((Intent)object).getParcelableExtra("android.intent.extra.ringtone.PICKED_URI")) != null ? uri.toString() : "")) {
                this.onSaveRingtone(uri);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onAttachedToHierarchy(PreferenceManager preferenceManager) {
        super.onAttachedToHierarchy(preferenceManager);
        preferenceManager.registerOnActivityResultListener(this);
        this.mRequestCode = preferenceManager.getNextRequestCode();
    }

    @Override
    protected void onClick() {
        Intent intent = new Intent("android.intent.action.RINGTONE_PICKER");
        this.onPrepareRingtonePickerIntent(intent);
        PreferenceFragment preferenceFragment = this.getPreferenceManager().getFragment();
        if (preferenceFragment != null) {
            preferenceFragment.startActivityForResult(intent, this.mRequestCode);
        } else {
            this.getPreferenceManager().getActivity().startActivityForResult(intent, this.mRequestCode);
        }
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getString(n);
    }

    protected void onPrepareRingtonePickerIntent(Intent intent) {
        intent.putExtra("android.intent.extra.ringtone.EXISTING_URI", this.onRestoreRingtone());
        intent.putExtra("android.intent.extra.ringtone.SHOW_DEFAULT", this.mShowDefault);
        if (this.mShowDefault) {
            intent.putExtra("android.intent.extra.ringtone.DEFAULT_URI", RingtoneManager.getDefaultUri(this.getRingtoneType()));
        }
        intent.putExtra("android.intent.extra.ringtone.SHOW_SILENT", this.mShowSilent);
        intent.putExtra("android.intent.extra.ringtone.TYPE", this.mRingtoneType);
        intent.putExtra("android.intent.extra.ringtone.TITLE", this.getTitle());
        intent.putExtra("android.intent.extra.ringtone.AUDIO_ATTRIBUTES_FLAGS", 64);
    }

    protected Uri onRestoreRingtone() {
        Uri uri = null;
        String string2 = this.getPersistedString(null);
        if (!TextUtils.isEmpty(string2)) {
            uri = Uri.parse(string2);
        }
        return uri;
    }

    protected void onSaveRingtone(Uri object) {
        object = object != null ? ((Uri)object).toString() : "";
        this.persistString((String)object);
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object object) {
        object = (String)object;
        if (bl) {
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)object)) {
            this.onSaveRingtone(Uri.parse((String)object));
        }
    }

    public void setRingtoneType(int n) {
        this.mRingtoneType = n;
    }

    public void setShowDefault(boolean bl) {
        this.mShowDefault = bl;
    }

    public void setShowSilent(boolean bl) {
        this.mShowSilent = bl;
    }
}

