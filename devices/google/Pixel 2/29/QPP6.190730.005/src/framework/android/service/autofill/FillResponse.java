/*
 * Decompiled with CFR 0.145.
 */
package android.service.autofill;

import android.content.IntentSender;
import android.content.pm.ParceledListSlice;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.autofill.AutofillServiceHelper;
import android.service.autofill.Dataset;
import android.service.autofill.SaveInfo;
import android.service.autofill.UserData;
import android.view.autofill.AutofillId;
import android.view.autofill.Helper;
import android.widget.RemoteViews;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class FillResponse
implements Parcelable {
    public static final Parcelable.Creator<FillResponse> CREATOR = new Parcelable.Creator<FillResponse>(){

        @Override
        public FillResponse createFromParcel(Parcel parcel) {
            Builder builder = new Builder();
            Object object = (ParceledListSlice)parcel.readParcelable(null);
            object = object != null ? ((ParceledListSlice)object).getList() : null;
            int n = object != null ? object.size() : 0;
            for (int i = 0; i < n; ++i) {
                builder.addDataset((Dataset)object.get(i));
            }
            builder.setSaveInfo((SaveInfo)parcel.readParcelable(null));
            builder.setClientState((Bundle)parcel.readParcelable(null));
            AutofillId[] arrautofillId = (AutofillId[])parcel.readParcelableArray(null, AutofillId.class);
            IntentSender intentSender = (IntentSender)parcel.readParcelable(null);
            object = (RemoteViews)parcel.readParcelable(null);
            if (arrautofillId != null) {
                builder.setAuthentication(arrautofillId, intentSender, (RemoteViews)object);
            }
            if ((object = (RemoteViews)parcel.readParcelable(null)) != null) {
                builder.setHeader((RemoteViews)object);
            }
            if ((object = (RemoteViews)parcel.readParcelable(null)) != null) {
                builder.setFooter((RemoteViews)object);
            }
            if ((object = (UserData)parcel.readParcelable(null)) != null) {
                builder.setUserData((UserData)object);
            }
            builder.setIgnoredIds((AutofillId[])parcel.readParcelableArray(null, AutofillId.class));
            long l = parcel.readLong();
            if (l > 0L) {
                builder.disableAutofill(l);
            }
            if ((object = (AutofillId[])parcel.readParcelableArray(null, AutofillId.class)) != null) {
                builder.setFieldClassificationIds((AutofillId[])object);
            }
            builder.setFlags(parcel.readInt());
            object = builder.build();
            ((FillResponse)object).setRequestId(parcel.readInt());
            return object;
        }

        public FillResponse[] newArray(int n) {
            return new FillResponse[n];
        }
    };
    public static final int FLAG_DISABLE_ACTIVITY_ONLY = 2;
    public static final int FLAG_TRACK_CONTEXT_COMMITED = 1;
    private final IntentSender mAuthentication;
    private final AutofillId[] mAuthenticationIds;
    private final Bundle mClientState;
    private final ParceledListSlice<Dataset> mDatasets;
    private final long mDisableDuration;
    private final AutofillId[] mFieldClassificationIds;
    private final int mFlags;
    private final RemoteViews mFooter;
    private final RemoteViews mHeader;
    private final AutofillId[] mIgnoredIds;
    private final RemoteViews mPresentation;
    private int mRequestId;
    private final SaveInfo mSaveInfo;
    private final UserData mUserData;

    private FillResponse(Builder builder) {
        ParceledListSlice parceledListSlice = builder.mDatasets != null ? new ParceledListSlice(builder.mDatasets) : null;
        this.mDatasets = parceledListSlice;
        this.mSaveInfo = builder.mSaveInfo;
        this.mClientState = builder.mClientState;
        this.mPresentation = builder.mPresentation;
        this.mHeader = builder.mHeader;
        this.mFooter = builder.mFooter;
        this.mAuthentication = builder.mAuthentication;
        this.mAuthenticationIds = builder.mAuthenticationIds;
        this.mIgnoredIds = builder.mIgnoredIds;
        this.mDisableDuration = builder.mDisableDuration;
        this.mFieldClassificationIds = builder.mFieldClassificationIds;
        this.mFlags = builder.mFlags;
        this.mRequestId = Integer.MIN_VALUE;
        this.mUserData = builder.mUserData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public IntentSender getAuthentication() {
        return this.mAuthentication;
    }

    public AutofillId[] getAuthenticationIds() {
        return this.mAuthenticationIds;
    }

    public Bundle getClientState() {
        return this.mClientState;
    }

    public List<Dataset> getDatasets() {
        Object object = this.mDatasets;
        object = object != null ? ((ParceledListSlice)object).getList() : null;
        return object;
    }

    public long getDisableDuration() {
        return this.mDisableDuration;
    }

    public AutofillId[] getFieldClassificationIds() {
        return this.mFieldClassificationIds;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public RemoteViews getFooter() {
        return this.mFooter;
    }

    public RemoteViews getHeader() {
        return this.mHeader;
    }

    public AutofillId[] getIgnoredIds() {
        return this.mIgnoredIds;
    }

    public RemoteViews getPresentation() {
        return this.mPresentation;
    }

    public int getRequestId() {
        return this.mRequestId;
    }

    public SaveInfo getSaveInfo() {
        return this.mSaveInfo;
    }

    public UserData getUserData() {
        return this.mUserData;
    }

    public void setRequestId(int n) {
        this.mRequestId = n;
    }

    public String toString() {
        Object[] arrobject;
        if (!Helper.sDebug) {
            return super.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("FillResponse : [mRequestId=");
        stringBuilder.append(this.mRequestId);
        stringBuilder = new StringBuilder(stringBuilder.toString());
        if (this.mDatasets != null) {
            stringBuilder.append(", datasets=");
            stringBuilder.append(this.mDatasets.getList());
        }
        if (this.mSaveInfo != null) {
            stringBuilder.append(", saveInfo=");
            stringBuilder.append(this.mSaveInfo);
        }
        if (this.mClientState != null) {
            stringBuilder.append(", hasClientState");
        }
        if (this.mPresentation != null) {
            stringBuilder.append(", hasPresentation");
        }
        if (this.mHeader != null) {
            stringBuilder.append(", hasHeader");
        }
        if (this.mFooter != null) {
            stringBuilder.append(", hasFooter");
        }
        if (this.mAuthentication != null) {
            stringBuilder.append(", hasAuthentication");
        }
        if (this.mAuthenticationIds != null) {
            stringBuilder.append(", authenticationIds=");
            stringBuilder.append(Arrays.toString(this.mAuthenticationIds));
        }
        stringBuilder.append(", disableDuration=");
        stringBuilder.append(this.mDisableDuration);
        if (this.mFlags != 0) {
            stringBuilder.append(", flags=");
            stringBuilder.append(this.mFlags);
        }
        if ((arrobject = this.mFieldClassificationIds) != null) {
            stringBuilder.append(Arrays.toString(arrobject));
        }
        if (this.mUserData != null) {
            stringBuilder.append(", userData=");
            stringBuilder.append(this.mUserData);
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mDatasets, n);
        parcel.writeParcelable(this.mSaveInfo, n);
        parcel.writeParcelable(this.mClientState, n);
        parcel.writeParcelableArray((Parcelable[])this.mAuthenticationIds, n);
        parcel.writeParcelable(this.mAuthentication, n);
        parcel.writeParcelable(this.mPresentation, n);
        parcel.writeParcelable(this.mHeader, n);
        parcel.writeParcelable(this.mFooter, n);
        parcel.writeParcelable(this.mUserData, n);
        parcel.writeParcelableArray((Parcelable[])this.mIgnoredIds, n);
        parcel.writeLong(this.mDisableDuration);
        parcel.writeParcelableArray((Parcelable[])this.mFieldClassificationIds, n);
        parcel.writeInt(this.mFlags);
        parcel.writeInt(this.mRequestId);
    }

    public static final class Builder {
        private IntentSender mAuthentication;
        private AutofillId[] mAuthenticationIds;
        private Bundle mClientState;
        private ArrayList<Dataset> mDatasets;
        private boolean mDestroyed;
        private long mDisableDuration;
        private AutofillId[] mFieldClassificationIds;
        private int mFlags;
        private RemoteViews mFooter;
        private RemoteViews mHeader;
        private AutofillId[] mIgnoredIds;
        private RemoteViews mPresentation;
        private SaveInfo mSaveInfo;
        private UserData mUserData;

        private void throwIfAuthenticationCalled() {
            if (this.mAuthentication == null) {
                return;
            }
            throw new IllegalStateException("Already called #setAuthentication()");
        }

        private void throwIfDestroyed() {
            if (!this.mDestroyed) {
                return;
            }
            throw new IllegalStateException("Already called #build()");
        }

        private void throwIfDisableAutofillCalled() {
            if (this.mDisableDuration <= 0L) {
                return;
            }
            throw new IllegalStateException("Already called #disableAutofill()");
        }

        public Builder addDataset(Dataset dataset) {
            this.throwIfDestroyed();
            this.throwIfDisableAutofillCalled();
            if (dataset == null) {
                return this;
            }
            if (this.mDatasets == null) {
                this.mDatasets = new ArrayList();
            }
            if (!this.mDatasets.add(dataset)) {
                return this;
            }
            return this;
        }

        public FillResponse build() {
            this.throwIfDestroyed();
            if (this.mAuthentication == null && this.mDatasets == null && this.mSaveInfo == null && this.mDisableDuration == 0L && this.mFieldClassificationIds == null && this.mClientState == null) {
                throw new IllegalStateException("need to provide: at least one DataSet, or a SaveInfo, or an authentication with a presentation, or a FieldsDetection, or a client state, or disable autofill");
            }
            if (this.mDatasets == null && (this.mHeader != null || this.mFooter != null)) {
                throw new IllegalStateException("must add at least 1 dataset when using header or footer");
            }
            this.mDestroyed = true;
            return new FillResponse(this);
        }

        public Builder disableAutofill(long l) {
            this.throwIfDestroyed();
            if (l > 0L) {
                if (this.mAuthentication == null && this.mDatasets == null && this.mSaveInfo == null && this.mFieldClassificationIds == null && this.mClientState == null) {
                    this.mDisableDuration = l;
                    return this;
                }
                throw new IllegalStateException("disableAutofill() must be the only method called");
            }
            throw new IllegalArgumentException("duration must be greater than 0");
        }

        public Builder setAuthentication(AutofillId[] arrautofillId, IntentSender intentSender, RemoteViews remoteViews) {
            this.throwIfDestroyed();
            this.throwIfDisableAutofillCalled();
            if (this.mHeader == null && this.mFooter == null) {
                boolean bl = true;
                boolean bl2 = intentSender == null;
                if (remoteViews != null) {
                    bl = false;
                }
                if (!(bl ^ bl2)) {
                    this.mAuthentication = intentSender;
                    this.mPresentation = remoteViews;
                    this.mAuthenticationIds = AutofillServiceHelper.assertValid(arrautofillId);
                    return this;
                }
                throw new IllegalArgumentException("authentication and presentation must be both non-null or null");
            }
            throw new IllegalStateException("Already called #setHeader() or #setFooter()");
        }

        public Builder setClientState(Bundle bundle) {
            this.throwIfDestroyed();
            this.throwIfDisableAutofillCalled();
            this.mClientState = bundle;
            return this;
        }

        public Builder setFieldClassificationIds(AutofillId ... arrautofillId) {
            this.throwIfDestroyed();
            this.throwIfDisableAutofillCalled();
            Preconditions.checkArrayElementsNotNull(arrautofillId, "ids");
            Preconditions.checkArgumentInRange(arrautofillId.length, 1, UserData.getMaxFieldClassificationIdsSize(), "ids length");
            this.mFieldClassificationIds = arrautofillId;
            this.mFlags |= 1;
            return this;
        }

        public Builder setFlags(int n) {
            this.throwIfDestroyed();
            this.mFlags = Preconditions.checkFlagsArgument(n, 3);
            return this;
        }

        public Builder setFooter(RemoteViews remoteViews) {
            this.throwIfDestroyed();
            this.throwIfAuthenticationCalled();
            this.mFooter = Preconditions.checkNotNull(remoteViews);
            return this;
        }

        public Builder setHeader(RemoteViews remoteViews) {
            this.throwIfDestroyed();
            this.throwIfAuthenticationCalled();
            this.mHeader = Preconditions.checkNotNull(remoteViews);
            return this;
        }

        public Builder setIgnoredIds(AutofillId ... arrautofillId) {
            this.throwIfDestroyed();
            this.mIgnoredIds = arrautofillId;
            return this;
        }

        public Builder setSaveInfo(SaveInfo saveInfo) {
            this.throwIfDestroyed();
            this.throwIfDisableAutofillCalled();
            this.mSaveInfo = saveInfo;
            return this;
        }

        public Builder setUserData(UserData userData) {
            this.throwIfDestroyed();
            this.throwIfAuthenticationCalled();
            this.mUserData = Preconditions.checkNotNull(userData);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    static @interface FillResponseFlags {
    }

}

