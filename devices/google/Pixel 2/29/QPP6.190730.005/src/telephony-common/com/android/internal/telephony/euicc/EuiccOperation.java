/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Intent
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.telephony.euicc.DownloadableSubscription
 *  android.text.TextUtils
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 */
package com.android.internal.telephony.euicc;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.euicc.DownloadableSubscription;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.euicc.EuiccController;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public class EuiccOperation
implements Parcelable {
    @Deprecated
    @VisibleForTesting
    static final int ACTION_DOWNLOAD_CONFIRMATION_CODE = 8;
    @VisibleForTesting
    static final int ACTION_DOWNLOAD_DEACTIVATE_SIM = 2;
    @VisibleForTesting
    static final int ACTION_DOWNLOAD_NO_PRIVILEGES = 3;
    @VisibleForTesting
    static final int ACTION_DOWNLOAD_NO_PRIVILEGES_OR_DEACTIVATE_SIM_CHECK_METADATA = 9;
    @VisibleForTesting
    static final int ACTION_DOWNLOAD_RESOLVABLE_ERRORS = 7;
    @VisibleForTesting
    static final int ACTION_GET_DEFAULT_LIST_DEACTIVATE_SIM = 4;
    @VisibleForTesting
    static final int ACTION_GET_METADATA_DEACTIVATE_SIM = 1;
    @VisibleForTesting
    static final int ACTION_SWITCH_DEACTIVATE_SIM = 5;
    @VisibleForTesting
    static final int ACTION_SWITCH_NO_PRIVILEGES = 6;
    public static final Parcelable.Creator<EuiccOperation> CREATOR = new Parcelable.Creator<EuiccOperation>(){

        public EuiccOperation createFromParcel(Parcel parcel) {
            return new EuiccOperation(parcel);
        }

        public EuiccOperation[] newArray(int n) {
            return new EuiccOperation[n];
        }
    };
    private static final String TAG = "EuiccOperation";
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public final int mAction;
    private final String mCallingPackage;
    private final long mCallingToken;
    private final DownloadableSubscription mDownloadableSubscription;
    private final int mResolvableErrors;
    private final int mSubscriptionId;
    private final boolean mSwitchAfterDownload;

    EuiccOperation(int n, long l, DownloadableSubscription downloadableSubscription, int n2, boolean bl, String string) {
        this.mAction = n;
        this.mCallingToken = l;
        this.mDownloadableSubscription = downloadableSubscription;
        this.mSubscriptionId = n2;
        this.mSwitchAfterDownload = bl;
        this.mCallingPackage = string;
        this.mResolvableErrors = 0;
    }

    EuiccOperation(int n, long l, DownloadableSubscription downloadableSubscription, int n2, boolean bl, String string, int n3) {
        this.mAction = n;
        this.mCallingToken = l;
        this.mDownloadableSubscription = downloadableSubscription;
        this.mSubscriptionId = n2;
        this.mSwitchAfterDownload = bl;
        this.mCallingPackage = string;
        this.mResolvableErrors = n3;
    }

    EuiccOperation(Parcel parcel) {
        this.mAction = parcel.readInt();
        this.mCallingToken = parcel.readLong();
        this.mDownloadableSubscription = (DownloadableSubscription)parcel.readTypedObject(DownloadableSubscription.CREATOR);
        this.mSubscriptionId = parcel.readInt();
        this.mSwitchAfterDownload = parcel.readBoolean();
        this.mCallingPackage = parcel.readString();
        this.mResolvableErrors = parcel.readInt();
    }

    private static void fail(PendingIntent pendingIntent) {
        EuiccController.get().sendResult(pendingIntent, 2, null);
    }

    @Deprecated
    public static EuiccOperation forDownloadConfirmationCode(long l, DownloadableSubscription downloadableSubscription, boolean bl, String string) {
        return new EuiccOperation(8, l, downloadableSubscription, 0, bl, string);
    }

    static EuiccOperation forDownloadDeactivateSim(long l, DownloadableSubscription downloadableSubscription, boolean bl, String string) {
        return new EuiccOperation(2, l, downloadableSubscription, 0, bl, string);
    }

    static EuiccOperation forDownloadNoPrivileges(long l, DownloadableSubscription downloadableSubscription, boolean bl, String string) {
        return new EuiccOperation(3, l, downloadableSubscription, 0, bl, string);
    }

    static EuiccOperation forDownloadNoPrivilegesOrDeactivateSimCheckMetadata(long l, DownloadableSubscription downloadableSubscription, boolean bl, String string) {
        return new EuiccOperation(9, l, downloadableSubscription, 0, bl, string);
    }

    static EuiccOperation forDownloadResolvableErrors(long l, DownloadableSubscription downloadableSubscription, boolean bl, String string, int n) {
        return new EuiccOperation(7, l, downloadableSubscription, 0, bl, string, n);
    }

    static EuiccOperation forGetDefaultListDeactivateSim(long l, String string) {
        return new EuiccOperation(4, l, null, 0, false, string);
    }

    static EuiccOperation forGetMetadataDeactivateSim(long l, DownloadableSubscription downloadableSubscription, String string) {
        return new EuiccOperation(1, l, downloadableSubscription, 0, false, string);
    }

    static EuiccOperation forSwitchDeactivateSim(long l, int n, String string) {
        return new EuiccOperation(5, l, null, n, false, string);
    }

    static EuiccOperation forSwitchNoPrivileges(long l, int n, String string) {
        return new EuiccOperation(6, l, null, n, false, string);
    }

    @Deprecated
    private void resolvedDownloadConfirmationCode(int n, String string, PendingIntent pendingIntent) {
        if (TextUtils.isEmpty((CharSequence)string)) {
            EuiccOperation.fail(pendingIntent);
        } else {
            this.mDownloadableSubscription.setConfirmationCode(string);
            EuiccController.get().downloadSubscription(n, this.mDownloadableSubscription, this.mSwitchAfterDownload, this.mCallingPackage, true, null, pendingIntent);
        }
    }

    private void resolvedDownloadDeactivateSim(int n, boolean bl, PendingIntent pendingIntent) {
        if (bl) {
            EuiccController.get().downloadSubscription(n, this.mDownloadableSubscription, this.mSwitchAfterDownload, this.mCallingPackage, true, null, pendingIntent);
        } else {
            EuiccOperation.fail(pendingIntent);
        }
    }

    private void resolvedDownloadNoPrivileges(int n, boolean bl, PendingIntent pendingIntent) {
        if (bl) {
            long l = Binder.clearCallingIdentity();
            try {
                EuiccController.get().downloadSubscriptionPrivileged(n, l, this.mDownloadableSubscription, this.mSwitchAfterDownload, true, this.mCallingPackage, null, pendingIntent);
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        } else {
            EuiccOperation.fail(pendingIntent);
        }
    }

    private void resolvedDownloadNoPrivilegesOrDeactivateSimCheckMetadata(int n, boolean bl, PendingIntent pendingIntent) {
        if (bl) {
            long l = Binder.clearCallingIdentity();
            try {
                EuiccController.get().downloadSubscriptionPrivilegedCheckMetadata(n, l, this.mDownloadableSubscription, this.mSwitchAfterDownload, true, this.mCallingPackage, null, pendingIntent);
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        } else {
            EuiccOperation.fail(pendingIntent);
        }
    }

    private void resolvedDownloadResolvableErrors(int n, Bundle bundle, PendingIntent pendingIntent) {
        boolean bl = true;
        String string = null;
        boolean bl2 = bl;
        if ((this.mResolvableErrors & 2) != 0) {
            bl2 = bl;
            if (!bundle.getBoolean("android.service.euicc.extra.RESOLUTION_ALLOW_POLICY_RULES")) {
                bl2 = false;
            }
        }
        bl = bl2;
        if ((this.mResolvableErrors & 1) != 0) {
            String string2 = bundle.getString("android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE");
            bl = bl2;
            string = string2;
            if (TextUtils.isEmpty((CharSequence)string2)) {
                bl = false;
                string = string2;
            }
        }
        if (!bl) {
            EuiccOperation.fail(pendingIntent);
        } else {
            this.mDownloadableSubscription.setConfirmationCode(string);
            EuiccController.get().downloadSubscription(n, this.mDownloadableSubscription, this.mSwitchAfterDownload, this.mCallingPackage, true, bundle, pendingIntent);
        }
    }

    private void resolvedGetDefaultListDeactivateSim(int n, boolean bl, PendingIntent pendingIntent) {
        if (bl) {
            EuiccController.get().getDefaultDownloadableSubscriptionList(n, true, this.mCallingPackage, pendingIntent);
        } else {
            EuiccOperation.fail(pendingIntent);
        }
    }

    private void resolvedGetMetadataDeactivateSim(int n, boolean bl, PendingIntent pendingIntent) {
        if (bl) {
            EuiccController.get().getDownloadableSubscriptionMetadata(n, this.mDownloadableSubscription, true, this.mCallingPackage, pendingIntent);
        } else {
            EuiccOperation.fail(pendingIntent);
        }
    }

    private void resolvedSwitchDeactivateSim(int n, boolean bl, PendingIntent pendingIntent) {
        if (bl) {
            EuiccController.get().switchToSubscription(n, this.mSubscriptionId, true, this.mCallingPackage, pendingIntent);
        } else {
            EuiccOperation.fail(pendingIntent);
        }
    }

    private void resolvedSwitchNoPrivileges(int n, boolean bl, PendingIntent pendingIntent) {
        if (bl) {
            long l = Binder.clearCallingIdentity();
            try {
                EuiccController.get().switchToSubscriptionPrivileged(n, l, this.mSubscriptionId, true, this.mCallingPackage, pendingIntent);
            }
            finally {
                Binder.restoreCallingIdentity((long)l);
            }
        } else {
            EuiccOperation.fail(pendingIntent);
        }
    }

    public void continueOperation(int n, Bundle object, PendingIntent pendingIntent) {
        Binder.restoreCallingIdentity((long)this.mCallingToken);
        switch (this.mAction) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown action: ");
                ((StringBuilder)object).append(this.mAction);
                Log.wtf((String)TAG, (String)((StringBuilder)object).toString());
                break;
            }
            case 9: {
                this.resolvedDownloadNoPrivilegesOrDeactivateSimCheckMetadata(n, object.getBoolean("android.service.euicc.extra.RESOLUTION_CONSENT"), pendingIntent);
                break;
            }
            case 8: {
                this.resolvedDownloadConfirmationCode(n, object.getString("android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE"), pendingIntent);
                break;
            }
            case 7: {
                this.resolvedDownloadResolvableErrors(n, (Bundle)object, pendingIntent);
                break;
            }
            case 6: {
                this.resolvedSwitchNoPrivileges(n, object.getBoolean("android.service.euicc.extra.RESOLUTION_CONSENT"), pendingIntent);
                break;
            }
            case 5: {
                this.resolvedSwitchDeactivateSim(n, object.getBoolean("android.service.euicc.extra.RESOLUTION_CONSENT"), pendingIntent);
                break;
            }
            case 4: {
                this.resolvedGetDefaultListDeactivateSim(n, object.getBoolean("android.service.euicc.extra.RESOLUTION_CONSENT"), pendingIntent);
                break;
            }
            case 3: {
                this.resolvedDownloadNoPrivileges(n, object.getBoolean("android.service.euicc.extra.RESOLUTION_CONSENT"), pendingIntent);
                break;
            }
            case 2: {
                this.resolvedDownloadDeactivateSim(n, object.getBoolean("android.service.euicc.extra.RESOLUTION_CONSENT"), pendingIntent);
                break;
            }
            case 1: {
                this.resolvedGetMetadataDeactivateSim(n, object.getBoolean("android.service.euicc.extra.RESOLUTION_CONSENT"), pendingIntent);
            }
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mAction);
        parcel.writeLong(this.mCallingToken);
        parcel.writeTypedObject((Parcelable)this.mDownloadableSubscription, n);
        parcel.writeInt(this.mSubscriptionId);
        parcel.writeBoolean(this.mSwitchAfterDownload);
        parcel.writeString(this.mCallingPackage);
        parcel.writeInt(this.mResolvableErrors);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    @VisibleForTesting
    static @interface Action {
    }

}

