/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.AbsSavedState;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsSeekBar;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import com.android.internal.R;

@Deprecated
public class SeekBarPreference
extends Preference
implements SeekBar.OnSeekBarChangeListener {
    private int mMax;
    private int mProgress;
    private boolean mTrackingTouch;

    @UnsupportedAppUsage
    public SeekBarPreference(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public SeekBarPreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17957066);
    }

    @UnsupportedAppUsage
    public SeekBarPreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public SeekBarPreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        TypedArray typedArray = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.ProgressBar, n, n2);
        this.setMax(typedArray.getInt(2, this.mMax));
        typedArray.recycle();
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.SeekBarPreference, n, n2);
        n = ((TypedArray)object).getResourceId(0, 17367248);
        ((TypedArray)object).recycle();
        this.setLayoutResource(n);
    }

    private void setProgress(int n, boolean bl) {
        int n2 = n;
        if (n > this.mMax) {
            n2 = this.mMax;
        }
        n = n2;
        if (n2 < 0) {
            n = 0;
        }
        if (n != this.mProgress) {
            this.mProgress = n;
            this.persistInt(n);
            if (bl) {
                this.notifyChanged();
            }
        }
    }

    public int getProgress() {
        return this.mProgress;
    }

    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        view = (SeekBar)view.findViewById(16909328);
        ((SeekBar)view).setOnSeekBarChangeListener(this);
        ((AbsSeekBar)view).setMax(this.mMax);
        ((ProgressBar)view).setProgress(this.mProgress);
        view.setEnabled(this.isEnabled());
    }

    @Override
    protected Object onGetDefaultValue(TypedArray typedArray, int n) {
        return typedArray.getInt(n, 0);
    }

    @Override
    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0) {
            return false;
        }
        if ((view = (SeekBar)view.findViewById(16909328)) == null) {
            return false;
        }
        return ((AbsSeekBar)view).onKeyDown(n, keyEvent);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
        if (bl && !this.mTrackingTouch) {
            this.syncProgress(seekBar);
        }
    }

    @Override
    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!parcelable.getClass().equals(SavedState.class)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(((AbsSavedState)parcelable).getSuperState());
        this.mProgress = ((SavedState)parcelable).progress;
        this.mMax = ((SavedState)parcelable).max;
        this.notifyChanged();
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable parcelable = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return parcelable;
        }
        parcelable = new SavedState(parcelable);
        ((SavedState)parcelable).progress = this.mProgress;
        ((SavedState)parcelable).max = this.mMax;
        return parcelable;
    }

    @Override
    protected void onSetInitialValue(boolean bl, Object object) {
        int n = bl ? this.getPersistedInt(this.mProgress) : ((Integer)object).intValue();
        this.setProgress(n);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        this.mTrackingTouch = true;
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.mTrackingTouch = false;
        if (seekBar.getProgress() != this.mProgress) {
            this.syncProgress(seekBar);
        }
    }

    public void setMax(int n) {
        if (n != this.mMax) {
            this.mMax = n;
            this.notifyChanged();
        }
    }

    public void setProgress(int n) {
        this.setProgress(n, true);
    }

    void syncProgress(SeekBar seekBar) {
        int n = seekBar.getProgress();
        if (n != this.mProgress) {
            if (this.callChangeListener(n)) {
                this.setProgress(n, false);
            } else {
                seekBar.setProgress(this.mProgress);
            }
        }
    }

    private static class SavedState
    extends Preference.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            @Override
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        int max;
        int progress;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.progress = parcel.readInt();
            this.max = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.progress);
            parcel.writeInt(this.max);
        }

    }

}

