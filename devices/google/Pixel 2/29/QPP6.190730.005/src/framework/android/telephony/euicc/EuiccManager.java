/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.annotation.SystemApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.TelephonyManager;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;
import com.android.internal.telephony.euicc.IEuiccController;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class EuiccManager {
    @SystemApi
    public static final String ACTION_DELETE_SUBSCRIPTION_PRIVILEGED = "android.telephony.euicc.action.DELETE_SUBSCRIPTION_PRIVILEGED";
    public static final String ACTION_MANAGE_EMBEDDED_SUBSCRIPTIONS = "android.telephony.euicc.action.MANAGE_EMBEDDED_SUBSCRIPTIONS";
    public static final String ACTION_NOTIFY_CARRIER_SETUP_INCOMPLETE = "android.telephony.euicc.action.NOTIFY_CARRIER_SETUP_INCOMPLETE";
    @SystemApi
    public static final String ACTION_OTA_STATUS_CHANGED = "android.telephony.euicc.action.OTA_STATUS_CHANGED";
    @SystemApi
    public static final String ACTION_PROVISION_EMBEDDED_SUBSCRIPTION = "android.telephony.euicc.action.PROVISION_EMBEDDED_SUBSCRIPTION";
    @SystemApi
    public static final String ACTION_RENAME_SUBSCRIPTION_PRIVILEGED = "android.telephony.euicc.action.RENAME_SUBSCRIPTION_PRIVILEGED";
    public static final String ACTION_RESOLVE_ERROR = "android.telephony.euicc.action.RESOLVE_ERROR";
    @SystemApi
    public static final String ACTION_TOGGLE_SUBSCRIPTION_PRIVILEGED = "android.telephony.euicc.action.TOGGLE_SUBSCRIPTION_PRIVILEGED";
    public static final int EMBEDDED_SUBSCRIPTION_RESULT_ERROR = 2;
    public static final int EMBEDDED_SUBSCRIPTION_RESULT_OK = 0;
    public static final int EMBEDDED_SUBSCRIPTION_RESULT_RESOLVABLE_ERROR = 1;
    @SystemApi
    public static final int EUICC_ACTIVATION_TYPE_ACCOUNT_REQUIRED = 4;
    @SystemApi
    public static final int EUICC_ACTIVATION_TYPE_BACKUP = 2;
    @SystemApi
    public static final int EUICC_ACTIVATION_TYPE_DEFAULT = 1;
    @SystemApi
    public static final int EUICC_ACTIVATION_TYPE_TRANSFER = 3;
    @SystemApi
    public static final int EUICC_OTA_FAILED = 2;
    @SystemApi
    public static final int EUICC_OTA_IN_PROGRESS = 1;
    @SystemApi
    public static final int EUICC_OTA_NOT_NEEDED = 4;
    @SystemApi
    public static final int EUICC_OTA_STATUS_UNAVAILABLE = 5;
    @SystemApi
    public static final int EUICC_OTA_SUCCEEDED = 3;
    @SystemApi
    public static final String EXTRA_ACTIVATION_TYPE = "android.telephony.euicc.extra.ACTIVATION_TYPE";
    public static final String EXTRA_EMBEDDED_SUBSCRIPTION_DETAILED_CODE = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DETAILED_CODE";
    public static final String EXTRA_EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTION";
    @SystemApi
    public static final String EXTRA_EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTIONS = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_DOWNLOADABLE_SUBSCRIPTIONS";
    public static final String EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_ACTION = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_ACTION";
    public static final String EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT";
    public static final String EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_INTENT = "android.telephony.euicc.extra.EMBEDDED_SUBSCRIPTION_RESOLUTION_INTENT";
    @SystemApi
    public static final String EXTRA_ENABLE_SUBSCRIPTION = "android.telephony.euicc.extra.ENABLE_SUBSCRIPTION";
    @SystemApi
    public static final String EXTRA_FORCE_PROVISION = "android.telephony.euicc.extra.FORCE_PROVISION";
    @SystemApi
    public static final String EXTRA_FROM_SUBSCRIPTION_ID = "android.telephony.euicc.extra.FROM_SUBSCRIPTION_ID";
    public static final String EXTRA_PHYSICAL_SLOT_ID = "android.telephony.euicc.extra.PHYSICAL_SLOT_ID";
    @SystemApi
    public static final String EXTRA_SUBSCRIPTION_ID = "android.telephony.euicc.extra.SUBSCRIPTION_ID";
    @SystemApi
    public static final String EXTRA_SUBSCRIPTION_NICKNAME = "android.telephony.euicc.extra.SUBSCRIPTION_NICKNAME";
    public static final String META_DATA_CARRIER_ICON = "android.telephony.euicc.carriericon";
    private int mCardId;
    private final Context mContext;

    public EuiccManager(Context context) {
        this.mContext = context;
        this.mCardId = ((TelephonyManager)context.getSystemService("phone")).getCardIdForDefaultEuicc();
    }

    private EuiccManager(Context context, int n) {
        this.mContext = context;
        this.mCardId = n;
    }

    private static IEuiccController getIEuiccController() {
        return IEuiccController.Stub.asInterface(ServiceManager.getService("econtroller"));
    }

    private boolean refreshCardIdIfUninitialized() {
        if (this.mCardId == -2) {
            this.mCardId = ((TelephonyManager)this.mContext.getSystemService("phone")).getCardIdForDefaultEuicc();
        }
        return this.mCardId != -2;
    }

    private static void sendUnavailableError(PendingIntent pendingIntent) {
        try {
            pendingIntent.send(2);
        }
        catch (PendingIntent.CanceledException canceledException) {
            // empty catch block
        }
    }

    @SystemApi
    public void continueOperation(Intent parcelable, Bundle bundle) {
        if (!this.isEnabled()) {
            if ((parcelable = (PendingIntent)parcelable.getParcelableExtra(EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT)) != null) {
                EuiccManager.sendUnavailableError((PendingIntent)parcelable);
            }
            return;
        }
        try {
            EuiccManager.getIEuiccController().continueOperation(this.mCardId, (Intent)parcelable, bundle);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public EuiccManager createForCardId(int n) {
        return new EuiccManager(this.mContext, n);
    }

    public void deleteSubscription(int n, PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().deleteSubscription(this.mCardId, n, this.mContext.getOpPackageName(), pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void downloadSubscription(DownloadableSubscription downloadableSubscription, boolean bl, PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().downloadSubscription(this.mCardId, downloadableSubscription, bl, this.mContext.getOpPackageName(), null, pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void eraseSubscriptions(PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().eraseSubscriptions(this.mCardId, pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void getDefaultDownloadableSubscriptionList(PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().getDefaultDownloadableSubscriptionList(this.mCardId, this.mContext.getOpPackageName(), pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void getDownloadableSubscriptionMetadata(DownloadableSubscription downloadableSubscription, PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().getDownloadableSubscriptionMetadata(this.mCardId, downloadableSubscription, this.mContext.getOpPackageName(), pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public String getEid() {
        if (!this.isEnabled()) {
            return null;
        }
        try {
            String string2 = EuiccManager.getIEuiccController().getEid(this.mCardId, this.mContext.getOpPackageName());
            return string2;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public EuiccInfo getEuiccInfo() {
        if (!this.isEnabled()) {
            return null;
        }
        try {
            EuiccInfo euiccInfo = EuiccManager.getIEuiccController().getEuiccInfo(this.mCardId);
            return euiccInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public int getOtaStatus() {
        if (!this.isEnabled()) {
            return 5;
        }
        try {
            int n = EuiccManager.getIEuiccController().getOtaStatus(this.mCardId);
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean isEnabled() {
        boolean bl = EuiccManager.getIEuiccController() != null && this.refreshCardIdIfUninitialized();
        return bl;
    }

    public void retainSubscriptionsForFactoryReset(PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().retainSubscriptionsForFactoryReset(this.mCardId, pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void startResolutionActivity(Activity activity, int n, Intent parcelable, PendingIntent pendingIntent) throws IntentSender.SendIntentException {
        if ((parcelable = (PendingIntent)((Intent)parcelable).getParcelableExtra(EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_INTENT)) != null) {
            Intent intent = new Intent();
            intent.putExtra(EXTRA_EMBEDDED_SUBSCRIPTION_RESOLUTION_CALLBACK_INTENT, pendingIntent);
            activity.startIntentSenderForResult(((PendingIntent)parcelable).getIntentSender(), n, intent, 0, 0, 0);
            return;
        }
        throw new IllegalArgumentException("Invalid result intent");
    }

    public void switchToSubscription(int n, PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().switchToSubscription(this.mCardId, n, this.mContext.getOpPackageName(), pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void updateSubscriptionNickname(int n, String string2, PendingIntent pendingIntent) {
        if (!this.isEnabled()) {
            EuiccManager.sendUnavailableError(pendingIntent);
            return;
        }
        try {
            EuiccManager.getIEuiccController().updateSubscriptionNickname(this.mCardId, n, string2, this.mContext.getOpPackageName(), pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EuiccActivationType {
    }

    @SystemApi
    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OtaStatus {
    }

}

