/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.Notification;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.ArraySet;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class RemoteInput
implements Parcelable {
    public static final Parcelable.Creator<RemoteInput> CREATOR = new Parcelable.Creator<RemoteInput>(){

        @Override
        public RemoteInput createFromParcel(Parcel parcel) {
            return new RemoteInput(parcel);
        }

        public RemoteInput[] newArray(int n) {
            return new RemoteInput[n];
        }
    };
    private static final int DEFAULT_FLAGS = 1;
    public static final int EDIT_CHOICES_BEFORE_SENDING_AUTO = 0;
    public static final int EDIT_CHOICES_BEFORE_SENDING_DISABLED = 1;
    public static final int EDIT_CHOICES_BEFORE_SENDING_ENABLED = 2;
    private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    private static final String EXTRA_RESULTS_SOURCE = "android.remoteinput.resultsSource";
    private static final int FLAG_ALLOW_FREE_FORM_INPUT = 1;
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    public static final int SOURCE_CHOICE = 1;
    public static final int SOURCE_FREE_FORM_INPUT = 0;
    private final ArraySet<String> mAllowedDataTypes;
    private final CharSequence[] mChoices;
    private final int mEditChoicesBeforeSending;
    private final Bundle mExtras;
    private final int mFlags;
    private final CharSequence mLabel;
    private final String mResultKey;

    private RemoteInput(Parcel parcel) {
        this.mResultKey = parcel.readString();
        this.mLabel = parcel.readCharSequence();
        this.mChoices = parcel.readCharSequenceArray();
        this.mFlags = parcel.readInt();
        this.mEditChoicesBeforeSending = parcel.readInt();
        this.mExtras = parcel.readBundle();
        this.mAllowedDataTypes = parcel.readArraySet(null);
    }

    private RemoteInput(String string2, CharSequence charSequence, CharSequence[] arrcharSequence, int n, int n2, Bundle bundle, ArraySet<String> arraySet) {
        this.mResultKey = string2;
        this.mLabel = charSequence;
        this.mChoices = arrcharSequence;
        this.mFlags = n;
        this.mEditChoicesBeforeSending = n2;
        this.mExtras = bundle;
        this.mAllowedDataTypes = arraySet;
        if (this.getEditChoicesBeforeSending() == 2 && !this.getAllowFreeFormInput()) {
            throw new IllegalArgumentException("setEditChoicesBeforeSending requires setAllowFreeFormInput");
        }
    }

    /*
     * WARNING - void declaration
     */
    public static void addDataResultToIntent(RemoteInput remoteInput, Intent intent, Map<String, Uri> object2) {
        Parcelable parcelable;
        Intent intent2 = parcelable = RemoteInput.getClipDataIntentFromIntent(intent);
        if (parcelable == null) {
            intent2 = new Intent();
        }
        for (Map.Entry entry : object2.entrySet()) {
            void var2_7;
            String string2 = (String)entry.getKey();
            Uri uri = (Uri)entry.getValue();
            if (string2 == null) continue;
            Parcelable parcelable2 = parcelable = intent2.getBundleExtra(RemoteInput.getExtraResultsKeyForData(string2));
            if (parcelable == null) {
                Bundle bundle = new Bundle();
            }
            var2_7.putString(remoteInput.getResultKey(), uri.toString());
            intent2.putExtra(RemoteInput.getExtraResultsKeyForData(string2), (Bundle)var2_7);
        }
        intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, intent2));
    }

    public static void addResultsToIntent(RemoteInput[] arrremoteInput, Intent intent, Bundle bundle) {
        Parcelable parcelable = RemoteInput.getClipDataIntentFromIntent(intent);
        Intent intent2 = parcelable;
        if (parcelable == null) {
            intent2 = new Intent();
        }
        Object object = intent2.getBundleExtra(EXTRA_RESULTS_DATA);
        parcelable = object;
        if (object == null) {
            parcelable = new Bundle();
        }
        for (RemoteInput remoteInput : arrremoteInput) {
            object = bundle.get(remoteInput.getResultKey());
            if (!(object instanceof CharSequence)) continue;
            ((Bundle)parcelable).putCharSequence(remoteInput.getResultKey(), (CharSequence)object);
        }
        intent2.putExtra(EXTRA_RESULTS_DATA, (Bundle)parcelable);
        intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, intent2));
    }

    private static Intent getClipDataIntentFromIntent(Intent parcelable) {
        if ((parcelable = ((Intent)parcelable).getClipData()) == null) {
            return null;
        }
        ClipDescription clipDescription = ((ClipData)parcelable).getDescription();
        if (!clipDescription.hasMimeType("text/vnd.android.intent")) {
            return null;
        }
        if (!clipDescription.getLabel().equals(RESULTS_CLIP_LABEL)) {
            return null;
        }
        return ((ClipData)parcelable).getItemAt(0).getIntent();
    }

    public static Map<String, Uri> getDataResultsFromIntent(Intent cloneable, String string2) {
        block2 : {
            Intent intent = RemoteInput.getClipDataIntentFromIntent((Intent)cloneable);
            Object var3_3 = null;
            if (intent == null) {
                return null;
            }
            cloneable = new HashMap();
            for (String string3 : intent.getExtras().keySet()) {
                String string4;
                if (!string3.startsWith(EXTRA_DATA_TYPE_RESULTS_DATA) || (string4 = string3.substring(EXTRA_DATA_TYPE_RESULTS_DATA.length())) == null || string4.isEmpty() || (string3 = intent.getBundleExtra(string3).getString(string2)) == null || string3.isEmpty()) continue;
                cloneable.put(string4, Uri.parse(string3));
            }
            if (!cloneable.isEmpty()) break block2;
            cloneable = var3_3;
        }
        return cloneable;
    }

    private static String getExtraResultsKeyForData(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(EXTRA_DATA_TYPE_RESULTS_DATA);
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public static Bundle getResultsFromIntent(Intent intent) {
        if ((intent = RemoteInput.getClipDataIntentFromIntent(intent)) == null) {
            return null;
        }
        return (Bundle)intent.getExtras().getParcelable(EXTRA_RESULTS_DATA);
    }

    public static int getResultsSource(Intent intent) {
        if ((intent = RemoteInput.getClipDataIntentFromIntent(intent)) == null) {
            return 0;
        }
        return intent.getExtras().getInt(EXTRA_RESULTS_SOURCE, 0);
    }

    public static void setResultsSource(Intent intent, int n) {
        Intent intent2;
        Intent intent3 = intent2 = RemoteInput.getClipDataIntentFromIntent(intent);
        if (intent2 == null) {
            intent3 = new Intent();
        }
        intent3.putExtra(EXTRA_RESULTS_SOURCE, n);
        intent.setClipData(ClipData.newIntent(RESULTS_CLIP_LABEL, intent3));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean getAllowFreeFormInput() {
        int n = this.mFlags;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public Set<String> getAllowedDataTypes() {
        return this.mAllowedDataTypes;
    }

    public CharSequence[] getChoices() {
        return this.mChoices;
    }

    public int getEditChoicesBeforeSending() {
        return this.mEditChoicesBeforeSending;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public String getResultKey() {
        return this.mResultKey;
    }

    public boolean isDataOnly() {
        boolean bl = !this.getAllowFreeFormInput() && (this.getChoices() == null || this.getChoices().length == 0) && !this.getAllowedDataTypes().isEmpty();
        return bl;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mResultKey);
        parcel.writeCharSequence(this.mLabel);
        parcel.writeCharSequenceArray(this.mChoices);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mEditChoicesBeforeSending);
        parcel.writeBundle(this.mExtras);
        parcel.writeArraySet(this.mAllowedDataTypes);
    }

    public static final class Builder {
        private final ArraySet<String> mAllowedDataTypes = new ArraySet();
        private CharSequence[] mChoices;
        private int mEditChoicesBeforeSending = 0;
        private final Bundle mExtras = new Bundle();
        private int mFlags = 1;
        private CharSequence mLabel;
        private final String mResultKey;

        public Builder(String string2) {
            if (string2 != null) {
                this.mResultKey = string2;
                return;
            }
            throw new IllegalArgumentException("Result key can't be null");
        }

        private void setFlag(int n, boolean bl) {
            this.mFlags = bl ? (this.mFlags |= n) : (this.mFlags &= n);
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle != null) {
                this.mExtras.putAll(bundle);
            }
            return this;
        }

        public RemoteInput build() {
            return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mFlags, this.mEditChoicesBeforeSending, this.mExtras, this.mAllowedDataTypes);
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public Builder setAllowDataType(String string2, boolean bl) {
            if (bl) {
                this.mAllowedDataTypes.add(string2);
            } else {
                this.mAllowedDataTypes.remove(string2);
            }
            return this;
        }

        public Builder setAllowFreeFormInput(boolean bl) {
            this.setFlag(1, bl);
            return this;
        }

        public Builder setChoices(CharSequence[] arrcharSequence) {
            if (arrcharSequence == null) {
                this.mChoices = null;
            } else {
                this.mChoices = new CharSequence[arrcharSequence.length];
                for (int i = 0; i < arrcharSequence.length; ++i) {
                    this.mChoices[i] = Notification.safeCharSequence(arrcharSequence[i]);
                }
            }
            return this;
        }

        public Builder setEditChoicesBeforeSending(int n) {
            this.mEditChoicesBeforeSending = n;
            return this;
        }

        public Builder setLabel(CharSequence charSequence) {
            this.mLabel = Notification.safeCharSequence(charSequence);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EditChoicesBeforeSending {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Source {
    }

}

