/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.euicc.EuiccProfileInfo;
import android.telephony.euicc.EuiccNotification;
import android.telephony.euicc.EuiccRulesAuthTable;
import android.telephony.euicc._$$Lambda$EuiccCardManager$1$WCF3YSMl2TGGvaCq1GRblRP0j8M;
import android.telephony.euicc._$$Lambda$EuiccCardManager$10$tNYkM_c2PgDDurtC_iDXbvWa5_8;
import android.telephony.euicc._$$Lambda$EuiccCardManager$11$IPX2CweBQhOCbcMAQ3yyU_N8fjQ;
import android.telephony.euicc._$$Lambda$EuiccCardManager$12$0dAgJQ0nijxpb9xsSsFqNuBkchU;
import android.telephony.euicc._$$Lambda$EuiccCardManager$13$Kd_aeGG9po3MXhUohSTDAoC8kqI;
import android.telephony.euicc._$$Lambda$EuiccCardManager$14$v9_1WsmNGIOXMEjPL4FGhZERO18;
import android.telephony.euicc._$$Lambda$EuiccCardManager$15$_sAstfKdFkraAjC1faT_C0t_PgM;
import android.telephony.euicc._$$Lambda$EuiccCardManager$16$HzYHHKtPhCzsbbDlkzDxayy5kVM;
import android.telephony.euicc._$$Lambda$EuiccCardManager$17$uqb_wFIaxTmLnjUZZIY6J_DjHD0;
import android.telephony.euicc._$$Lambda$EuiccCardManager$18$2cHWlkpkAsYqhkpHbNv_QMqc0Ng;
import android.telephony.euicc._$$Lambda$EuiccCardManager$19$Gjn1FcgJf1Gqq6yBzVQLrvqlyg0;
import android.telephony.euicc._$$Lambda$EuiccCardManager$2$TyPTPQ9XsUKfhC8yZUgq_jP_Ugs;
import android.telephony.euicc._$$Lambda$EuiccCardManager$20$BvkqzlF_5oeo0InlIzG65QhyNT0;
import android.telephony.euicc._$$Lambda$EuiccCardManager$21$srrmNYPqPTZF4uUZIcVq86p1JpU;
import android.telephony.euicc._$$Lambda$EuiccCardManager$22$JpF6e8fcw8874GZTA7KUvqsNhY8;
import android.telephony.euicc._$$Lambda$EuiccCardManager$3$rixBHO_K_K3SJ1SVCAj8_82SxFE;
import android.telephony.euicc._$$Lambda$EuiccCardManager$4$yTPBL_lMjfIGHQUa_JxPKPLvVR8;
import android.telephony.euicc._$$Lambda$EuiccCardManager$5$Tw9Ac3hC3rh6YoO0o4ip_fVYWq0;
import android.telephony.euicc._$$Lambda$EuiccCardManager$6$K_EQz__QgHyjI0itfruTgIG7hos;
import android.telephony.euicc._$$Lambda$EuiccCardManager$7$W9T937HBG_sD8BsVWGQ6kDb28dk;
import android.telephony.euicc._$$Lambda$EuiccCardManager$8$A_S7upEqW6mofzD1_YkLBP5INOM;
import android.telephony.euicc._$$Lambda$EuiccCardManager$9$cPEHH7JlllMuvBHJOu0A2hY4QyU;
import android.util.Log;
import com.android.internal.telephony.euicc.IAuthenticateServerCallback;
import com.android.internal.telephony.euicc.ICancelSessionCallback;
import com.android.internal.telephony.euicc.IDeleteProfileCallback;
import com.android.internal.telephony.euicc.IDisableProfileCallback;
import com.android.internal.telephony.euicc.IEuiccCardController;
import com.android.internal.telephony.euicc.IGetAllProfilesCallback;
import com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.IGetEuiccChallengeCallback;
import com.android.internal.telephony.euicc.IGetEuiccInfo1Callback;
import com.android.internal.telephony.euicc.IGetEuiccInfo2Callback;
import com.android.internal.telephony.euicc.IGetProfileCallback;
import com.android.internal.telephony.euicc.IGetRulesAuthTableCallback;
import com.android.internal.telephony.euicc.IGetSmdsAddressCallback;
import com.android.internal.telephony.euicc.IListNotificationsCallback;
import com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback;
import com.android.internal.telephony.euicc.IPrepareDownloadCallback;
import com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback;
import com.android.internal.telephony.euicc.IResetMemoryCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationCallback;
import com.android.internal.telephony.euicc.IRetrieveNotificationListCallback;
import com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback;
import com.android.internal.telephony.euicc.ISetNicknameCallback;
import com.android.internal.telephony.euicc.ISwitchToProfileCallback;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
public class EuiccCardManager {
    public static final int CANCEL_REASON_END_USER_REJECTED = 0;
    public static final int CANCEL_REASON_POSTPONED = 1;
    public static final int CANCEL_REASON_PPR_NOT_ALLOWED = 3;
    public static final int CANCEL_REASON_TIMEOUT = 2;
    public static final int RESET_OPTION_DELETE_FIELD_LOADED_TEST_PROFILES = 2;
    public static final int RESET_OPTION_DELETE_OPERATIONAL_PROFILES = 1;
    public static final int RESET_OPTION_RESET_DEFAULT_SMDP_ADDRESS = 4;
    public static final int RESULT_CALLER_NOT_ALLOWED = -3;
    public static final int RESULT_EUICC_NOT_FOUND = -2;
    public static final int RESULT_OK = 0;
    public static final int RESULT_UNKNOWN_ERROR = -1;
    private static final String TAG = "EuiccCardManager";
    private final Context mContext;

    public EuiccCardManager(Context context) {
        this.mContext = context;
    }

    private IEuiccCardController getIEuiccCardController() {
        return IEuiccCardController.Stub.asInterface(ServiceManager.getService("euicc_card_controller"));
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void authenticateServer(String string2, String string3, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, final Executor executor, final ResultCallback<byte[]> resultCallback) {
        void var1_4;
        block4 : {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string4 = this.mContext.getOpPackageName();
            try {
                IAuthenticateServerCallback.Stub stub = new IAuthenticateServerCallback.Stub(){

                    static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, byte[] arrby) {
                        resultCallback2.onComplete(n, arrby);
                    }

                    @Override
                    public void onComplete(int n, byte[] arrby) {
                        executor.execute(new _$$Lambda$EuiccCardManager$15$_sAstfKdFkraAjC1faT_C0t_PgM(resultCallback, n, arrby));
                    }
                };
                iEuiccCardController.authenticateServer(string4, string2, string3, arrby, arrby2, arrby3, arrby4, stub);
                return;
            }
            catch (RemoteException remoteException) {}
            break block4;
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        Log.e(TAG, "Error calling authenticateServer", (Throwable)var1_4);
        throw var1_4.rethrowFromSystemServer();
    }

    public void cancelSession(String string2, byte[] arrby, int n, final Executor executor, final ResultCallback<byte[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            ICancelSessionCallback.Stub stub = new ICancelSessionCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, byte[] arrby) {
                    resultCallback2.onComplete(n, arrby);
                }

                @Override
                public void onComplete(int n, byte[] arrby) {
                    executor.execute(new _$$Lambda$EuiccCardManager$18$2cHWlkpkAsYqhkpHbNv_QMqc0Ng(resultCallback, n, arrby));
                }
            };
            iEuiccCardController.cancelSession(string3, string2, arrby, n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling cancelSession", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void deleteProfile(String string2, String string3, final Executor executor, final ResultCallback<Void> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string4 = this.mContext.getOpPackageName();
            IDeleteProfileCallback.Stub stub = new IDeleteProfileCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n) {
                    resultCallback2.onComplete(n, null);
                }

                @Override
                public void onComplete(int n) {
                    executor.execute(new _$$Lambda$EuiccCardManager$6$K_EQz__QgHyjI0itfruTgIG7hos(resultCallback, n));
                }
            };
            iEuiccCardController.deleteProfile(string4, string2, string3, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling deleteProfile", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void disableProfile(String string2, String string3, boolean bl, final Executor executor, final ResultCallback<Void> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string4 = this.mContext.getOpPackageName();
            IDisableProfileCallback.Stub stub = new IDisableProfileCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n) {
                    resultCallback2.onComplete(n, null);
                }

                @Override
                public void onComplete(int n) {
                    executor.execute(new _$$Lambda$EuiccCardManager$3$rixBHO_K_K3SJ1SVCAj8_82SxFE(resultCallback, n));
                }
            };
            iEuiccCardController.disableProfile(string4, string2, string3, bl, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling disableProfile", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void listNotifications(String string2, int n, final Executor executor, final ResultCallback<EuiccNotification[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IListNotificationsCallback.Stub stub = new IListNotificationsCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, EuiccNotification[] arreuiccNotification) {
                    resultCallback2.onComplete(n, arreuiccNotification);
                }

                @Override
                public void onComplete(int n, EuiccNotification[] arreuiccNotification) {
                    executor.execute(new _$$Lambda$EuiccCardManager$19$Gjn1FcgJf1Gqq6yBzVQLrvqlyg0(resultCallback, n, arreuiccNotification));
                }
            };
            iEuiccCardController.listNotifications(string3, string2, n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling listNotifications", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void loadBoundProfilePackage(String string2, byte[] arrby, final Executor executor, final ResultCallback<byte[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            ILoadBoundProfilePackageCallback.Stub stub = new ILoadBoundProfilePackageCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, byte[] arrby) {
                    resultCallback2.onComplete(n, arrby);
                }

                @Override
                public void onComplete(int n, byte[] arrby) {
                    executor.execute(new _$$Lambda$EuiccCardManager$17$uqb_wFIaxTmLnjUZZIY6J_DjHD0(resultCallback, n, arrby));
                }
            };
            iEuiccCardController.loadBoundProfilePackage(string3, string2, arrby, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling loadBoundProfilePackage", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void prepareDownload(String string2, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, final Executor executor, final ResultCallback<byte[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IPrepareDownloadCallback.Stub stub = new IPrepareDownloadCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, byte[] arrby) {
                    resultCallback2.onComplete(n, arrby);
                }

                @Override
                public void onComplete(int n, byte[] arrby) {
                    executor.execute(new _$$Lambda$EuiccCardManager$16$HzYHHKtPhCzsbbDlkzDxayy5kVM(resultCallback, n, arrby));
                }
            };
            iEuiccCardController.prepareDownload(string3, string2, arrby, arrby2, arrby3, arrby4, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling prepareDownload", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void removeNotificationFromList(String string2, int n, final Executor executor, final ResultCallback<Void> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IRemoveNotificationFromListCallback.Stub stub = new IRemoveNotificationFromListCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n) {
                    resultCallback2.onComplete(n, null);
                }

                @Override
                public void onComplete(int n) {
                    executor.execute(new _$$Lambda$EuiccCardManager$22$JpF6e8fcw8874GZTA7KUvqsNhY8(resultCallback, n));
                }
            };
            iEuiccCardController.removeNotificationFromList(string3, string2, n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling removeNotificationFromList", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestAllProfiles(String string2, final Executor executor, final ResultCallback<EuiccProfileInfo[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IGetAllProfilesCallback.Stub stub = new IGetAllProfilesCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, EuiccProfileInfo[] arreuiccProfileInfo) {
                    resultCallback2.onComplete(n, arreuiccProfileInfo);
                }

                @Override
                public void onComplete(int n, EuiccProfileInfo[] arreuiccProfileInfo) {
                    executor.execute(new _$$Lambda$EuiccCardManager$1$WCF3YSMl2TGGvaCq1GRblRP0j8M(resultCallback, n, arreuiccProfileInfo));
                }
            };
            iEuiccCardController.getAllProfiles(string3, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getAllProfiles", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestDefaultSmdpAddress(String string2, final Executor executor, final ResultCallback<String> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IGetDefaultSmdpAddressCallback.Stub stub = new IGetDefaultSmdpAddressCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, String string2) {
                    resultCallback2.onComplete(n, string2);
                }

                @Override
                public void onComplete(int n, String string2) {
                    executor.execute(new _$$Lambda$EuiccCardManager$8$A_S7upEqW6mofzD1_YkLBP5INOM(resultCallback, n, string2));
                }
            };
            iEuiccCardController.getDefaultSmdpAddress(string3, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getDefaultSmdpAddress", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestEuiccChallenge(String string2, final Executor executor, final ResultCallback<byte[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IGetEuiccChallengeCallback.Stub stub = new IGetEuiccChallengeCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, byte[] arrby) {
                    resultCallback2.onComplete(n, arrby);
                }

                @Override
                public void onComplete(int n, byte[] arrby) {
                    executor.execute(new _$$Lambda$EuiccCardManager$12$0dAgJQ0nijxpb9xsSsFqNuBkchU(resultCallback, n, arrby));
                }
            };
            iEuiccCardController.getEuiccChallenge(string3, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getEuiccChallenge", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestEuiccInfo1(String string2, final Executor executor, final ResultCallback<byte[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IGetEuiccInfo1Callback.Stub stub = new IGetEuiccInfo1Callback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, byte[] arrby) {
                    resultCallback2.onComplete(n, arrby);
                }

                @Override
                public void onComplete(int n, byte[] arrby) {
                    executor.execute(new _$$Lambda$EuiccCardManager$13$Kd_aeGG9po3MXhUohSTDAoC8kqI(resultCallback, n, arrby));
                }
            };
            iEuiccCardController.getEuiccInfo1(string3, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getEuiccInfo1", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestEuiccInfo2(String string2, final Executor executor, final ResultCallback<byte[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IGetEuiccInfo2Callback.Stub stub = new IGetEuiccInfo2Callback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, byte[] arrby) {
                    resultCallback2.onComplete(n, arrby);
                }

                @Override
                public void onComplete(int n, byte[] arrby) {
                    executor.execute(new _$$Lambda$EuiccCardManager$14$v9_1WsmNGIOXMEjPL4FGhZERO18(resultCallback, n, arrby));
                }
            };
            iEuiccCardController.getEuiccInfo2(string3, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getEuiccInfo2", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestProfile(String string2, String string3, final Executor executor, final ResultCallback<EuiccProfileInfo> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string4 = this.mContext.getOpPackageName();
            IGetProfileCallback.Stub stub = new IGetProfileCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, EuiccProfileInfo euiccProfileInfo) {
                    resultCallback2.onComplete(n, euiccProfileInfo);
                }

                @Override
                public void onComplete(int n, EuiccProfileInfo euiccProfileInfo) {
                    executor.execute(new _$$Lambda$EuiccCardManager$2$TyPTPQ9XsUKfhC8yZUgq_jP_Ugs(resultCallback, n, euiccProfileInfo));
                }
            };
            iEuiccCardController.getProfile(string4, string2, string3, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getProfile", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestRulesAuthTable(String string2, final Executor executor, final ResultCallback<EuiccRulesAuthTable> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IGetRulesAuthTableCallback.Stub stub = new IGetRulesAuthTableCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, EuiccRulesAuthTable euiccRulesAuthTable) {
                    resultCallback2.onComplete(n, euiccRulesAuthTable);
                }

                @Override
                public void onComplete(int n, EuiccRulesAuthTable euiccRulesAuthTable) {
                    executor.execute(new _$$Lambda$EuiccCardManager$11$IPX2CweBQhOCbcMAQ3yyU_N8fjQ(resultCallback, n, euiccRulesAuthTable));
                }
            };
            iEuiccCardController.getRulesAuthTable(string3, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getRulesAuthTable", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestSmdsAddress(String string2, final Executor executor, final ResultCallback<String> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IGetSmdsAddressCallback.Stub stub = new IGetSmdsAddressCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, String string2) {
                    resultCallback2.onComplete(n, string2);
                }

                @Override
                public void onComplete(int n, String string2) {
                    executor.execute(new _$$Lambda$EuiccCardManager$9$cPEHH7JlllMuvBHJOu0A2hY4QyU(resultCallback, n, string2));
                }
            };
            iEuiccCardController.getSmdsAddress(string3, string2, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling getSmdsAddress", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void resetMemory(String string2, int n, final Executor executor, final ResultCallback<Void> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IResetMemoryCallback.Stub stub = new IResetMemoryCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n) {
                    resultCallback2.onComplete(n, null);
                }

                @Override
                public void onComplete(int n) {
                    executor.execute(new _$$Lambda$EuiccCardManager$7$W9T937HBG_sD8BsVWGQ6kDb28dk(resultCallback, n));
                }
            };
            iEuiccCardController.resetMemory(string3, string2, n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling resetMemory", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void retrieveNotification(String string2, int n, final Executor executor, final ResultCallback<EuiccNotification> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IRetrieveNotificationCallback.Stub stub = new IRetrieveNotificationCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, EuiccNotification euiccNotification) {
                    resultCallback2.onComplete(n, euiccNotification);
                }

                @Override
                public void onComplete(int n, EuiccNotification euiccNotification) {
                    executor.execute(new _$$Lambda$EuiccCardManager$21$srrmNYPqPTZF4uUZIcVq86p1JpU(resultCallback, n, euiccNotification));
                }
            };
            iEuiccCardController.retrieveNotification(string3, string2, n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling retrieveNotification", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void retrieveNotificationList(String string2, int n, final Executor executor, final ResultCallback<EuiccNotification[]> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string3 = this.mContext.getOpPackageName();
            IRetrieveNotificationListCallback.Stub stub = new IRetrieveNotificationListCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, EuiccNotification[] arreuiccNotification) {
                    resultCallback2.onComplete(n, arreuiccNotification);
                }

                @Override
                public void onComplete(int n, EuiccNotification[] arreuiccNotification) {
                    executor.execute(new _$$Lambda$EuiccCardManager$20$BvkqzlF_5oeo0InlIzG65QhyNT0(resultCallback, n, arreuiccNotification));
                }
            };
            iEuiccCardController.retrieveNotificationList(string3, string2, n, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling retrieveNotificationList", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setDefaultSmdpAddress(String string2, String string3, final Executor executor, final ResultCallback<Void> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string4 = this.mContext.getOpPackageName();
            ISetDefaultSmdpAddressCallback.Stub stub = new ISetDefaultSmdpAddressCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n) {
                    resultCallback2.onComplete(n, null);
                }

                @Override
                public void onComplete(int n) {
                    executor.execute(new _$$Lambda$EuiccCardManager$10$tNYkM_c2PgDDurtC_iDXbvWa5_8(resultCallback, n));
                }
            };
            iEuiccCardController.setDefaultSmdpAddress(string4, string2, string3, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling setDefaultSmdpAddress", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setNickname(String string2, String string3, String string4, final Executor executor, final ResultCallback<Void> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string5 = this.mContext.getOpPackageName();
            ISetNicknameCallback.Stub stub = new ISetNicknameCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n) {
                    resultCallback2.onComplete(n, null);
                }

                @Override
                public void onComplete(int n) {
                    executor.execute(new _$$Lambda$EuiccCardManager$5$Tw9Ac3hC3rh6YoO0o4ip_fVYWq0(resultCallback, n));
                }
            };
            iEuiccCardController.setNickname(string5, string2, string3, string4, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling setNickname", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void switchToProfile(String string2, String string3, boolean bl, final Executor executor, final ResultCallback<EuiccProfileInfo> resultCallback) {
        try {
            IEuiccCardController iEuiccCardController = this.getIEuiccCardController();
            String string4 = this.mContext.getOpPackageName();
            ISwitchToProfileCallback.Stub stub = new ISwitchToProfileCallback.Stub(){

                static /* synthetic */ void lambda$onComplete$0(ResultCallback resultCallback2, int n, EuiccProfileInfo euiccProfileInfo) {
                    resultCallback2.onComplete(n, euiccProfileInfo);
                }

                @Override
                public void onComplete(int n, EuiccProfileInfo euiccProfileInfo) {
                    executor.execute(new _$$Lambda$EuiccCardManager$4$yTPBL_lMjfIGHQUa_JxPKPLvVR8(resultCallback, n, euiccProfileInfo));
                }
            };
            iEuiccCardController.switchToProfile(string4, string2, string3, bl, stub);
            return;
        }
        catch (RemoteException remoteException) {
            Log.e(TAG, "Error calling switchToProfile", remoteException);
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CancelReason {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResetOption {
    }

    public static interface ResultCallback<T> {
        public void onComplete(int var1, T var2);
    }

}

