/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import android.annotation.UnsupportedAppUsage;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SeekBarDialogPreference;
import android.preference.SeekBarVolumizer;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.SeekBar;
import com.android.internal.R;

@Deprecated
public class VolumePreference
extends SeekBarDialogPreference
implements PreferenceManager.OnActivityStopListener,
View.OnKeyListener,
SeekBarVolumizer.Callback {
    private SeekBarVolumizer mSeekBarVolumizer;
    @UnsupportedAppUsage
    private int mStreamType;

    public VolumePreference(Context context) {
        this(context, null);
    }

    @UnsupportedAppUsage
    public VolumePreference(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 17957065);
    }

    public VolumePreference(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public VolumePreference(Context object, AttributeSet attributeSet, int n, int n2) {
        super((Context)object, attributeSet, n, n2);
        object = ((Context)object).obtainStyledAttributes(attributeSet, R.styleable.VolumePreference, n, n2);
        this.mStreamType = ((TypedArray)object).getInt(0, 0);
        ((TypedArray)object).recycle();
    }

    private void cleanup() {
        this.getPreferenceManager().unregisterOnActivityStopListener(this);
        if (this.mSeekBarVolumizer != null) {
            Dialog dialog = this.getDialog();
            if (dialog != null && dialog.isShowing()) {
                if ((dialog = dialog.getWindow().getDecorView().findViewById(16909328)) != null) {
                    ((View)((Object)dialog)).setOnKeyListener(null);
                }
                this.mSeekBarVolumizer.revertVolume();
            }
            this.mSeekBarVolumizer.stop();
            this.mSeekBarVolumizer = null;
        }
    }

    @Override
    public void onActivityStop() {
        SeekBarVolumizer seekBarVolumizer = this.mSeekBarVolumizer;
        if (seekBarVolumizer != null) {
            seekBarVolumizer.stopSample();
        }
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        SeekBar seekBar = (SeekBar)view.findViewById(16909328);
        this.mSeekBarVolumizer = new SeekBarVolumizer(this.getContext(), this.mStreamType, null, this);
        this.mSeekBarVolumizer.start();
        this.mSeekBarVolumizer.setSeekBar(seekBar);
        this.getPreferenceManager().registerOnActivityStopListener(this);
        view.setOnKeyListener(this);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    @Override
    protected void onDialogClosed(boolean bl) {
        SeekBarVolumizer seekBarVolumizer;
        super.onDialogClosed(bl);
        if (!bl && (seekBarVolumizer = this.mSeekBarVolumizer) != null) {
            seekBarVolumizer.revertVolume();
        }
        this.cleanup();
    }

    @Override
    public boolean onKey(View view, int n, KeyEvent keyEvent) {
        if (this.mSeekBarVolumizer == null) {
            return true;
        }
        boolean bl = keyEvent.getAction() == 0;
        if (n != 24) {
            if (n != 25) {
                if (n != 164) {
                    return false;
                }
                if (bl) {
                    this.mSeekBarVolumizer.muteVolume();
                }
                return true;
            }
            if (bl) {
                this.mSeekBarVolumizer.changeVolumeBy(-1);
            }
            return true;
        }
        if (bl) {
            this.mSeekBarVolumizer.changeVolumeBy(1);
        }
        return true;
    }

    @Override
    public void onMuted(boolean bl, boolean bl2) {
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
    }

    @Override
    protected void onRestoreInstanceState(Parcelable object) {
        if (object != null && object.getClass().equals(SavedState.class)) {
            SavedState savedState = (SavedState)object;
            super.onRestoreInstanceState(savedState.getSuperState());
            object = this.mSeekBarVolumizer;
            if (object != null) {
                ((SeekBarVolumizer)object).onRestoreInstanceState(savedState.getVolumeStore());
            }
            return;
        }
        super.onRestoreInstanceState((Parcelable)object);
    }

    @Override
    public void onSampleStarting(SeekBarVolumizer seekBarVolumizer) {
        SeekBarVolumizer seekBarVolumizer2 = this.mSeekBarVolumizer;
        if (seekBarVolumizer2 != null && seekBarVolumizer != seekBarVolumizer2) {
            seekBarVolumizer2.stopSample();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Object object = super.onSaveInstanceState();
        if (this.isPersistent()) {
            return object;
        }
        SavedState savedState = new SavedState((Parcelable)object);
        object = this.mSeekBarVolumizer;
        if (object != null) {
            ((SeekBarVolumizer)object).onSaveInstanceState(savedState.getVolumeStore());
        }
        return savedState;
    }

    public void setStreamType(int n) {
        this.mStreamType = n;
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
        VolumeStore mVolumeStore = new VolumeStore();

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mVolumeStore.volume = parcel.readInt();
            this.mVolumeStore.originalVolume = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        VolumeStore getVolumeStore() {
            return this.mVolumeStore;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.mVolumeStore.volume);
            parcel.writeInt(this.mVolumeStore.originalVolume);
        }

    }

    public static class VolumeStore {
        @UnsupportedAppUsage
        public int originalVolume = -1;
        @UnsupportedAppUsage
        public int volume = -1;
    }

}

