/*
 * Decompiled with CFR 0.145.
 */
package android.service.euicc;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.service.euicc.DownloadSubscriptionResult;
import android.service.euicc.GetDefaultDownloadableSubscriptionListResult;
import android.service.euicc.GetDownloadableSubscriptionMetadataResult;
import android.service.euicc.GetEuiccProfileInfoListResult;
import android.service.euicc.IDeleteSubscriptionCallback;
import android.service.euicc.IDownloadSubscriptionCallback;
import android.service.euicc.IEraseSubscriptionsCallback;
import android.service.euicc.IEuiccService;
import android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback;
import android.service.euicc.IGetDownloadableSubscriptionMetadataCallback;
import android.service.euicc.IGetEidCallback;
import android.service.euicc.IGetEuiccInfoCallback;
import android.service.euicc.IGetEuiccProfileInfoListCallback;
import android.service.euicc.IGetOtaStatusCallback;
import android.service.euicc.IOtaStatusChangedCallback;
import android.service.euicc.IRetainSubscriptionsForFactoryResetCallback;
import android.service.euicc.ISwitchToSubscriptionCallback;
import android.service.euicc.IUpdateSubscriptionNicknameCallback;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;
import android.util.ArraySet;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@SystemApi
public abstract class EuiccService
extends Service {
    public static final String ACTION_BIND_CARRIER_PROVISIONING_SERVICE = "android.service.euicc.action.BIND_CARRIER_PROVISIONING_SERVICE";
    public static final String ACTION_DELETE_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.DELETE_SUBSCRIPTION_PRIVILEGED";
    public static final String ACTION_MANAGE_EMBEDDED_SUBSCRIPTIONS = "android.service.euicc.action.MANAGE_EMBEDDED_SUBSCRIPTIONS";
    public static final String ACTION_PROVISION_EMBEDDED_SUBSCRIPTION = "android.service.euicc.action.PROVISION_EMBEDDED_SUBSCRIPTION";
    public static final String ACTION_RENAME_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.RENAME_SUBSCRIPTION_PRIVILEGED";
    @Deprecated
    public static final String ACTION_RESOLVE_CONFIRMATION_CODE = "android.service.euicc.action.RESOLVE_CONFIRMATION_CODE";
    public static final String ACTION_RESOLVE_DEACTIVATE_SIM = "android.service.euicc.action.RESOLVE_DEACTIVATE_SIM";
    public static final String ACTION_RESOLVE_NO_PRIVILEGES = "android.service.euicc.action.RESOLVE_NO_PRIVILEGES";
    public static final String ACTION_RESOLVE_RESOLVABLE_ERRORS = "android.service.euicc.action.RESOLVE_RESOLVABLE_ERRORS";
    public static final String ACTION_TOGGLE_SUBSCRIPTION_PRIVILEGED = "android.service.euicc.action.TOGGLE_SUBSCRIPTION_PRIVILEGED";
    public static final String CATEGORY_EUICC_UI = "android.service.euicc.category.EUICC_UI";
    public static final String EUICC_SERVICE_INTERFACE = "android.service.euicc.EuiccService";
    public static final String EXTRA_RESOLUTION_ALLOW_POLICY_RULES = "android.service.euicc.extra.RESOLUTION_ALLOW_POLICY_RULES";
    public static final String EXTRA_RESOLUTION_CALLING_PACKAGE = "android.service.euicc.extra.RESOLUTION_CALLING_PACKAGE";
    public static final String EXTRA_RESOLUTION_CARD_ID = "android.service.euicc.extra.RESOLUTION_CARD_ID";
    public static final String EXTRA_RESOLUTION_CONFIRMATION_CODE = "android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE";
    public static final String EXTRA_RESOLUTION_CONFIRMATION_CODE_RETRIED = "android.service.euicc.extra.RESOLUTION_CONFIRMATION_CODE_RETRIED";
    public static final String EXTRA_RESOLUTION_CONSENT = "android.service.euicc.extra.RESOLUTION_CONSENT";
    public static final String EXTRA_RESOLVABLE_ERRORS = "android.service.euicc.extra.RESOLVABLE_ERRORS";
    public static final ArraySet<String> RESOLUTION_ACTIONS = new ArraySet();
    public static final int RESOLVABLE_ERROR_CONFIRMATION_CODE = 1;
    public static final int RESOLVABLE_ERROR_POLICY_RULES = 2;
    public static final int RESULT_FIRST_USER = 1;
    public static final int RESULT_MUST_DEACTIVATE_SIM = -1;
    @Deprecated
    public static final int RESULT_NEED_CONFIRMATION_CODE = -2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_RESOLVABLE_ERRORS = -2;
    private static final String TAG = "EuiccService";
    private ThreadPoolExecutor mExecutor;
    private final IEuiccService.Stub mStubWrapper = new IEuiccServiceWrapper();

    static {
        RESOLUTION_ACTIONS.add(ACTION_RESOLVE_DEACTIVATE_SIM);
        RESOLUTION_ACTIONS.add(ACTION_RESOLVE_NO_PRIVILEGES);
        RESOLUTION_ACTIONS.add(ACTION_RESOLVE_RESOLVABLE_ERRORS);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mStubWrapper;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mExecutor = new ThreadPoolExecutor(4, 4, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory(){
            private final AtomicInteger mCount = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable runnable) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("EuiccService #");
                stringBuilder.append(this.mCount.getAndIncrement());
                return new Thread(runnable, stringBuilder.toString());
            }
        });
        this.mExecutor.allowCoreThreadTimeOut(true);
    }

    public abstract int onDeleteSubscription(int var1, String var2);

    @Override
    public void onDestroy() {
        this.mExecutor.shutdownNow();
        super.onDestroy();
    }

    @Deprecated
    public int onDownloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, boolean bl2) {
        return Integer.MIN_VALUE;
    }

    public DownloadSubscriptionResult onDownloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, boolean bl2, Bundle bundle) {
        return null;
    }

    public abstract int onEraseSubscriptions(int var1);

    public abstract GetDefaultDownloadableSubscriptionListResult onGetDefaultDownloadableSubscriptionList(int var1, boolean var2);

    public abstract GetDownloadableSubscriptionMetadataResult onGetDownloadableSubscriptionMetadata(int var1, DownloadableSubscription var2, boolean var3);

    public abstract String onGetEid(int var1);

    public abstract EuiccInfo onGetEuiccInfo(int var1);

    public abstract GetEuiccProfileInfoListResult onGetEuiccProfileInfoList(int var1);

    public abstract int onGetOtaStatus(int var1);

    public abstract int onRetainSubscriptionsForFactoryReset(int var1);

    public abstract void onStartOtaIfNecessary(int var1, OtaStatusChangedCallback var2);

    public abstract int onSwitchToSubscription(int var1, String var2, boolean var3);

    public abstract int onUpdateSubscriptionNickname(int var1, String var2, String var3);

    private class IEuiccServiceWrapper
    extends IEuiccService.Stub {
        private IEuiccServiceWrapper() {
        }

        @Override
        public void deleteSubscription(final int n, final String string2, final IDeleteSubscriptionCallback iDeleteSubscriptionCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    int n2 = EuiccService.this.onDeleteSubscription(n, string2);
                    try {
                        iDeleteSubscriptionCallback.onComplete(n2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void downloadSubscription(final int n, final DownloadableSubscription downloadableSubscription, final boolean bl, final boolean bl2, final Bundle bundle, final IDownloadSubscriptionCallback iDownloadSubscriptionCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    DownloadSubscriptionResult downloadSubscriptionResult;
                    try {
                        downloadSubscriptionResult = EuiccService.this.onDownloadSubscription(n, downloadableSubscription, bl, bl2, bundle);
                    }
                    catch (AbstractMethodError abstractMethodError) {
                        Log.w(EuiccService.TAG, "The new onDownloadSubscription(int, DownloadableSubscription, boolean, boolean, Bundle) is not implemented. Fall back to the old one.", abstractMethodError);
                        downloadSubscriptionResult = new DownloadSubscriptionResult(EuiccService.this.onDownloadSubscription(n, downloadableSubscription, bl, bl2), 0, -1);
                    }
                    try {
                        iDownloadSubscriptionCallback.onComplete(downloadSubscriptionResult);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void eraseSubscriptions(final int n, final IEraseSubscriptionsCallback iEraseSubscriptionsCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    int n2 = EuiccService.this.onEraseSubscriptions(n);
                    try {
                        iEraseSubscriptionsCallback.onComplete(n2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void getDefaultDownloadableSubscriptionList(final int n, final boolean bl, final IGetDefaultDownloadableSubscriptionListCallback iGetDefaultDownloadableSubscriptionListCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    GetDefaultDownloadableSubscriptionListResult getDefaultDownloadableSubscriptionListResult = EuiccService.this.onGetDefaultDownloadableSubscriptionList(n, bl);
                    try {
                        iGetDefaultDownloadableSubscriptionListCallback.onComplete(getDefaultDownloadableSubscriptionListResult);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void getDownloadableSubscriptionMetadata(final int n, final DownloadableSubscription downloadableSubscription, final boolean bl, final IGetDownloadableSubscriptionMetadataCallback iGetDownloadableSubscriptionMetadataCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    GetDownloadableSubscriptionMetadataResult getDownloadableSubscriptionMetadataResult = EuiccService.this.onGetDownloadableSubscriptionMetadata(n, downloadableSubscription, bl);
                    try {
                        iGetDownloadableSubscriptionMetadataCallback.onComplete(getDownloadableSubscriptionMetadataResult);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void getEid(final int n, final IGetEidCallback iGetEidCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    String string2 = EuiccService.this.onGetEid(n);
                    try {
                        iGetEidCallback.onSuccess(string2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void getEuiccInfo(final int n, final IGetEuiccInfoCallback iGetEuiccInfoCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    EuiccInfo euiccInfo = EuiccService.this.onGetEuiccInfo(n);
                    try {
                        iGetEuiccInfoCallback.onSuccess(euiccInfo);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void getEuiccProfileInfoList(final int n, final IGetEuiccProfileInfoListCallback iGetEuiccProfileInfoListCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    GetEuiccProfileInfoListResult getEuiccProfileInfoListResult = EuiccService.this.onGetEuiccProfileInfoList(n);
                    try {
                        iGetEuiccProfileInfoListCallback.onComplete(getEuiccProfileInfoListResult);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void getOtaStatus(final int n, final IGetOtaStatusCallback iGetOtaStatusCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    int n2 = EuiccService.this.onGetOtaStatus(n);
                    try {
                        iGetOtaStatusCallback.onSuccess(n2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void retainSubscriptionsForFactoryReset(final int n, final IRetainSubscriptionsForFactoryResetCallback iRetainSubscriptionsForFactoryResetCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    int n2 = EuiccService.this.onRetainSubscriptionsForFactoryReset(n);
                    try {
                        iRetainSubscriptionsForFactoryResetCallback.onComplete(n2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void startOtaIfNecessary(final int n, final IOtaStatusChangedCallback iOtaStatusChangedCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    EuiccService.this.onStartOtaIfNecessary(n, new OtaStatusChangedCallback(){

                        @Override
                        public void onOtaStatusChanged(int n) {
                            try {
                                iOtaStatusChangedCallback.onOtaStatusChanged(n);
                            }
                            catch (RemoteException remoteException) {
                                // empty catch block
                            }
                        }
                    });
                }

            });
        }

        @Override
        public void switchToSubscription(final int n, final String string2, final boolean bl, final ISwitchToSubscriptionCallback iSwitchToSubscriptionCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    int n2 = EuiccService.this.onSwitchToSubscription(n, string2, bl);
                    try {
                        iSwitchToSubscriptionCallback.onComplete(n2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

        @Override
        public void updateSubscriptionNickname(final int n, final String string2, final String string3, final IUpdateSubscriptionNicknameCallback iUpdateSubscriptionNicknameCallback) {
            EuiccService.this.mExecutor.execute(new Runnable(){

                @Override
                public void run() {
                    int n2 = EuiccService.this.onUpdateSubscriptionNickname(n, string2, string3);
                    try {
                        iUpdateSubscriptionNicknameCallback.onComplete(n2);
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                }
            });
        }

    }

    public static abstract class OtaStatusChangedCallback {
        public abstract void onOtaStatusChanged(int var1);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResolvableError {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Result {
    }

}

