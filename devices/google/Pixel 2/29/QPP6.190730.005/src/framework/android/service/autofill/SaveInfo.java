/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.content.IntentSender;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.AutofillServiceHelper;
import android.service.autofill.CustomDescription;
import android.service.autofill.InternalSanitizer;
import android.service.autofill.InternalValidator;
import android.service.autofill.Sanitizer;
import android.service.autofill.Validator;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

public final class SaveInfo
implements Parcelable {
    public static final Parcelable.Creator<SaveInfo> CREATOR = new Parcelable.Creator<SaveInfo>(){

        @Override
        public SaveInfo createFromParcel(Parcel parcel) {
            int n = parcel.readInt();
            Object object = (AutofillId[])parcel.readParcelableArray(null, AutofillId.class);
            object = object != null ? new Builder(n, (AutofillId[])object) : new Builder(n);
            Object object2 = (AutofillId[])parcel.readParcelableArray(null, AutofillId.class);
            if (object2 != null) {
                ((Builder)object).setOptionalIds((AutofillId[])object2);
            }
            ((Builder)object).setNegativeAction(parcel.readInt(), (IntentSender)parcel.readParcelable(null));
            ((Builder)object).setDescription(parcel.readCharSequence());
            object2 = (CustomDescription)parcel.readParcelable(null);
            if (object2 != null) {
                ((Builder)object).setCustomDescription((CustomDescription)object2);
            }
            if ((object2 = (InternalValidator)parcel.readParcelable(null)) != null) {
                ((Builder)object).setValidator((Validator)object2);
            }
            if ((object2 = (InternalSanitizer[])parcel.readParcelableArray(null, InternalSanitizer.class)) != null) {
                int n2 = ((AutofillId[])object2).length;
                for (n = 0; n < n2; ++n) {
                    AutofillId[] arrautofillId = (AutofillId[])parcel.readParcelableArray(null, AutofillId.class);
                    ((Builder)object).addSanitizer((Sanitizer)((Object)object2[n]), arrautofillId);
                }
            }
            if ((object2 = (AutofillId)parcel.readParcelable(null)) != null) {
                ((Builder)object).setTriggerId((AutofillId)object2);
            }
            ((Builder)object).setFlags(parcel.readInt());
            return ((Builder)object).build();
        }

        public SaveInfo[] newArray(int n) {
            return new SaveInfo[n];
        }
    };
    public static final int FLAG_DELAY_SAVE = 4;
    public static final int FLAG_DONT_SAVE_ON_FINISH = 2;
    public static final int FLAG_SAVE_ON_ALL_VIEWS_INVISIBLE = 1;
    public static final int NEGATIVE_BUTTON_STYLE_CANCEL = 0;
    public static final int NEGATIVE_BUTTON_STYLE_REJECT = 1;
    public static final int SAVE_DATA_TYPE_ADDRESS = 2;
    public static final int SAVE_DATA_TYPE_CREDIT_CARD = 4;
    public static final int SAVE_DATA_TYPE_EMAIL_ADDRESS = 16;
    public static final int SAVE_DATA_TYPE_GENERIC = 0;
    public static final int SAVE_DATA_TYPE_PASSWORD = 1;
    public static final int SAVE_DATA_TYPE_USERNAME = 8;
    private final CustomDescription mCustomDescription;
    private final CharSequence mDescription;
    private final int mFlags;
    private final IntentSender mNegativeActionListener;
    private final int mNegativeButtonStyle;
    private final AutofillId[] mOptionalIds;
    private final AutofillId[] mRequiredIds;
    private final InternalSanitizer[] mSanitizerKeys;
    private final AutofillId[][] mSanitizerValues;
    private final AutofillId mTriggerId;
    private final int mType;
    private final InternalValidator mValidator;

    private SaveInfo(Builder builder) {
        this.mType = builder.mType;
        this.mNegativeButtonStyle = builder.mNegativeButtonStyle;
        this.mNegativeActionListener = builder.mNegativeActionListener;
        this.mRequiredIds = builder.mRequiredIds;
        this.mOptionalIds = builder.mOptionalIds;
        this.mDescription = builder.mDescription;
        this.mFlags = builder.mFlags;
        this.mCustomDescription = builder.mCustomDescription;
        this.mValidator = builder.mValidator;
        if (builder.mSanitizers == null) {
            this.mSanitizerKeys = null;
            this.mSanitizerValues = null;
        } else {
            int n = builder.mSanitizers.size();
            this.mSanitizerKeys = new InternalSanitizer[n];
            this.mSanitizerValues = new AutofillId[n][];
            for (int i = 0; i < n; ++i) {
                this.mSanitizerKeys[i] = (InternalSanitizer)builder.mSanitizers.keyAt(i);
                this.mSanitizerValues[i] = (AutofillId[])builder.mSanitizers.valueAt(i);
            }
        }
        this.mTriggerId = builder.mTriggerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public CustomDescription getCustomDescription() {
        return this.mCustomDescription;
    }

    public CharSequence getDescription() {
        return this.mDescription;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public IntentSender getNegativeActionListener() {
        return this.mNegativeActionListener;
    }

    public int getNegativeActionStyle() {
        return this.mNegativeButtonStyle;
    }

    public AutofillId[] getOptionalIds() {
        return this.mOptionalIds;
    }

    public AutofillId[] getRequiredIds() {
        return this.mRequiredIds;
    }

    public InternalSanitizer[] getSanitizerKeys() {
        return this.mSanitizerKeys;
    }

    public AutofillId[][] getSanitizerValues() {
        return this.mSanitizerValues;
    }

    public AutofillId getTriggerId() {
        return this.mTriggerId;
    }

    public int getType() {
        return this.mType;
    }

    public InternalValidator getValidator() {
        return this.mValidator;
    }

    public String toString() {
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder("SaveInfo: [type=");
        stringBuilder.append(DebugUtils.flagsToString(SaveInfo.class, "SAVE_DATA_TYPE_", this.mType));
        stringBuilder.append(", requiredIds=");
        stringBuilder.append(Arrays.toString(this.mRequiredIds));
        stringBuilder.append(", style=");
        stringBuilder = stringBuilder.append(DebugUtils.flagsToString(SaveInfo.class, "NEGATIVE_BUTTON_STYLE_", this.mNegativeButtonStyle));
        if (this.mOptionalIds != null) {
            stringBuilder.append(", optionalIds=");
            stringBuilder.append(Arrays.toString(this.mOptionalIds));
        }
        if (this.mDescription != null) {
            stringBuilder.append(", description=");
            stringBuilder.append(this.mDescription);
        }
        if (this.mFlags != 0) {
            stringBuilder.append(", flags=");
            stringBuilder.append(this.mFlags);
        }
        if (this.mCustomDescription != null) {
            stringBuilder.append(", customDescription=");
            stringBuilder.append(this.mCustomDescription);
        }
        if (this.mValidator != null) {
            stringBuilder.append(", validator=");
            stringBuilder.append(this.mValidator);
        }
        if (this.mSanitizerKeys != null) {
            stringBuilder.append(", sanitizerKeys=");
            stringBuilder.append(this.mSanitizerKeys.length);
        }
        if (this.mSanitizerValues != null) {
            stringBuilder.append(", sanitizerValues=");
            stringBuilder.append(this.mSanitizerValues.length);
        }
        if (this.mTriggerId != null) {
            stringBuilder.append(", triggerId=");
            stringBuilder.append(this.mTriggerId);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mType);
        parcel.writeParcelableArray((Parcelable[])this.mRequiredIds, n);
        parcel.writeParcelableArray((Parcelable[])this.mOptionalIds, n);
        parcel.writeInt(this.mNegativeButtonStyle);
        parcel.writeParcelable(this.mNegativeActionListener, n);
        parcel.writeCharSequence(this.mDescription);
        parcel.writeParcelable(this.mCustomDescription, n);
        parcel.writeParcelable(this.mValidator, n);
        parcel.writeParcelableArray((Parcelable[])this.mSanitizerKeys, n);
        if (this.mSanitizerKeys != null) {
            AutofillId[][] arrautofillId;
            for (int i = 0; i < (arrautofillId = this.mSanitizerValues).length; ++i) {
                parcel.writeParcelableArray(arrautofillId[i], n);
            }
        }
        parcel.writeParcelable(this.mTriggerId, n);
        parcel.writeInt(this.mFlags);
    }

    public static final class Builder {
        private CustomDescription mCustomDescription;
        private CharSequence mDescription;
        private boolean mDestroyed;
        private int mFlags;
        private IntentSender mNegativeActionListener;
        private int mNegativeButtonStyle = 0;
        private AutofillId[] mOptionalIds;
        private final AutofillId[] mRequiredIds;
        private ArraySet<AutofillId> mSanitizerIds;
        private ArrayMap<InternalSanitizer, AutofillId[]> mSanitizers;
        private AutofillId mTriggerId;
        private final int mType;
        private InternalValidator mValidator;

        public Builder(int n) {
            this.mType = n;
            this.mRequiredIds = null;
        }

        public Builder(int n, AutofillId[] arrautofillId) {
            this.mType = n;
            this.mRequiredIds = AutofillServiceHelper.assertValid(arrautofillId);
        }

        private void throwIfDestroyed() {
            if (!this.mDestroyed) {
                return;
            }
            throw new IllegalStateException("Already called #build()");
        }

        public Builder addSanitizer(Sanitizer sanitizer, AutofillId ... arrautofillId) {
            this.throwIfDestroyed();
            Preconditions.checkArgument(ArrayUtils.isEmpty(arrautofillId) ^ true, "ids cannot be empty or null");
            boolean bl = sanitizer instanceof InternalSanitizer;
            Object object = new StringBuilder();
            ((StringBuilder)object).append("not provided by Android System: ");
            ((StringBuilder)object).append(sanitizer);
            Preconditions.checkArgument(bl, ((StringBuilder)object).toString());
            if (this.mSanitizers == null) {
                this.mSanitizers = new ArrayMap();
                this.mSanitizerIds = new ArraySet(arrautofillId.length);
            }
            int n = arrautofillId.length;
            for (int i = 0; i < n; ++i) {
                object = arrautofillId[i];
                Preconditions.checkArgument(this.mSanitizerIds.contains(object) ^ true, "already added %s", object);
                this.mSanitizerIds.add((AutofillId)object);
            }
            this.mSanitizers.put((InternalSanitizer)sanitizer, arrautofillId);
            return this;
        }

        public SaveInfo build() {
            this.throwIfDestroyed();
            boolean bl = !ArrayUtils.isEmpty(this.mRequiredIds) || !ArrayUtils.isEmpty(this.mOptionalIds) || (this.mFlags & 4) != 0;
            Preconditions.checkState(bl, "must have at least one required or optional id or FLAG_DELAYED_SAVE");
            this.mDestroyed = true;
            return new SaveInfo(this);
        }

        public Builder setCustomDescription(CustomDescription customDescription) {
            this.throwIfDestroyed();
            boolean bl = this.mDescription == null;
            Preconditions.checkState(bl, "Can call setDescription() or setCustomDescription(), but not both");
            this.mCustomDescription = customDescription;
            return this;
        }

        public Builder setDescription(CharSequence charSequence) {
            this.throwIfDestroyed();
            boolean bl = this.mCustomDescription == null;
            Preconditions.checkState(bl, "Can call setDescription() or setCustomDescription(), but not both");
            this.mDescription = charSequence;
            return this;
        }

        public Builder setFlags(int n) {
            this.throwIfDestroyed();
            this.mFlags = Preconditions.checkFlagsArgument(n, 7);
            return this;
        }

        public Builder setNegativeAction(int n, IntentSender object) {
            this.throwIfDestroyed();
            if (n != 0 && n != 1) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid style: ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.mNegativeButtonStyle = n;
            this.mNegativeActionListener = object;
            return this;
        }

        public Builder setOptionalIds(AutofillId[] arrautofillId) {
            this.throwIfDestroyed();
            this.mOptionalIds = AutofillServiceHelper.assertValid(arrautofillId);
            return this;
        }

        public Builder setTriggerId(AutofillId autofillId) {
            this.throwIfDestroyed();
            this.mTriggerId = Preconditions.checkNotNull(autofillId);
            return this;
        }

        public Builder setValidator(Validator validator) {
            this.throwIfDestroyed();
            boolean bl = validator instanceof InternalValidator;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("not provided by Android System: ");
            stringBuilder.append(validator);
            Preconditions.checkArgument(bl, stringBuilder.toString());
            this.mValidator = (InternalValidator)validator;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface NegativeButtonStyle {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface SaveDataType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface SaveInfoFlags {
    }

}

