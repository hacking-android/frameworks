/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ComponentInfo
 *  android.content.pm.PackageManager
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.preference.PreferenceManager
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.service.euicc.EuiccProfileInfo
 *  android.telephony.euicc.EuiccNotification
 *  android.telephony.euicc.EuiccRulesAuthTable
 *  android.text.TextUtils
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.telephony.euicc.IAuthenticateServerCallback
 *  com.android.internal.telephony.euicc.ICancelSessionCallback
 *  com.android.internal.telephony.euicc.IDeleteProfileCallback
 *  com.android.internal.telephony.euicc.IDisableProfileCallback
 *  com.android.internal.telephony.euicc.IEuiccCardController
 *  com.android.internal.telephony.euicc.IEuiccCardController$Stub
 *  com.android.internal.telephony.euicc.IGetAllProfilesCallback
 *  com.android.internal.telephony.euicc.IGetDefaultSmdpAddressCallback
 *  com.android.internal.telephony.euicc.IGetEuiccChallengeCallback
 *  com.android.internal.telephony.euicc.IGetEuiccInfo1Callback
 *  com.android.internal.telephony.euicc.IGetEuiccInfo2Callback
 *  com.android.internal.telephony.euicc.IGetProfileCallback
 *  com.android.internal.telephony.euicc.IGetRulesAuthTableCallback
 *  com.android.internal.telephony.euicc.IGetSmdsAddressCallback
 *  com.android.internal.telephony.euicc.IListNotificationsCallback
 *  com.android.internal.telephony.euicc.ILoadBoundProfilePackageCallback
 *  com.android.internal.telephony.euicc.IPrepareDownloadCallback
 *  com.android.internal.telephony.euicc.IRemoveNotificationFromListCallback
 *  com.android.internal.telephony.euicc.IResetMemoryCallback
 *  com.android.internal.telephony.euicc.IRetrieveNotificationCallback
 *  com.android.internal.telephony.euicc.IRetrieveNotificationListCallback
 *  com.android.internal.telephony.euicc.ISetDefaultSmdpAddressCallback
 *  com.android.internal.telephony.euicc.ISetNicknameCallback
 *  com.android.internal.telephony.euicc.ISwitchToProfileCallback
 */
package com.android.internal.telephony.euicc;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.service.euicc.EuiccProfileInfo;
import android.telephony.euicc.EuiccNotification;
import android.telephony.euicc.EuiccRulesAuthTable;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.euicc.EuiccConnector;
import com.android.internal.telephony.euicc.EuiccController;
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
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.telephony.uicc.UiccSlot;
import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.EuiccCardErrorException;
import com.android.internal.telephony.uicc.euicc.async.AsyncResultCallback;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class EuiccCardController
extends IEuiccCardController.Stub {
    private static final String KEY_LAST_BOOT_COUNT = "last_boot_count";
    private static final String TAG = "EuiccCardController";
    private static EuiccCardController sInstance;
    private AppOpsManager mAppOps;
    private ComponentInfo mBestComponent;
    private String mCallingPackage;
    private final Context mContext;
    private EuiccController mEuiccController;
    private Handler mEuiccMainThreadHandler;
    private SimSlotStatusChangedBroadcastReceiver mSimSlotStatusChangeReceiver;
    private UiccController mUiccController;

    private EuiccCardController(Context context) {
        this(context, new Handler(), EuiccController.get(), UiccController.getInstance());
        ServiceManager.addService((String)"euicc_card_controller", (IBinder)this);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public EuiccCardController(Context context, Handler handler, EuiccController euiccController, UiccController uiccController) {
        this.mContext = context;
        this.mAppOps = (AppOpsManager)context.getSystemService("appops");
        this.mEuiccMainThreadHandler = handler;
        this.mUiccController = uiccController;
        this.mEuiccController = euiccController;
        if (EuiccCardController.isBootUp(this.mContext)) {
            this.mSimSlotStatusChangeReceiver = new SimSlotStatusChangedBroadcastReceiver();
            this.mContext.registerReceiver((BroadcastReceiver)this.mSimSlotStatusChangeReceiver, new IntentFilter("android.telephony.action.SIM_SLOT_STATUS_CHANGED"));
        }
    }

    private void checkCallingPackage(String string) {
        this.mAppOps.checkPackage(Binder.getCallingUid(), string);
        this.mCallingPackage = string;
        this.mBestComponent = EuiccConnector.findBestComponent(this.mContext.getPackageManager());
        string = this.mBestComponent;
        if (string != null && TextUtils.equals((CharSequence)this.mCallingPackage, (CharSequence)((ComponentInfo)string).packageName)) {
            return;
        }
        throw new SecurityException("The calling package can only be LPA.");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static EuiccCardController get() {
        if (sInstance != null) return sInstance;
        synchronized (EuiccCardController.class) {
            if (sInstance != null) {
                return sInstance;
            }
            IllegalStateException illegalStateException = new IllegalStateException("get() called before init()");
            throw illegalStateException;
        }
    }

    private EuiccCard getEuiccCard(String string) {
        Object object = UiccController.getInstance();
        int n = object.getUiccSlotForCardId(string);
        if (n != -1 && object.getUiccSlot(n).isEuicc()) {
            return (EuiccCard)object.getUiccCardForSlot(n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("EuiccCard is null. CardId : ");
        ((StringBuilder)object).append(string);
        EuiccCardController.loge(((StringBuilder)object).toString());
        return null;
    }

    private int getResultCode(Throwable throwable) {
        if (throwable instanceof EuiccCardErrorException) {
            return ((EuiccCardErrorException)throwable).getErrorCode();
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static EuiccCardController init(Context object) {
        synchronized (EuiccCardController.class) {
            if (sInstance == null) {
                EuiccCardController euiccCardController;
                sInstance = euiccCardController = new EuiccCardController((Context)object);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("init() called multiple times! sInstance = ");
                ((StringBuilder)object).append((Object)sInstance);
                Log.wtf((String)TAG, (String)((StringBuilder)object).toString());
            }
            return sInstance;
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public static boolean isBootUp(Context context) {
        int n = Settings.Global.getInt((ContentResolver)context.getContentResolver(), (String)"boot_count", (int)-1);
        context = PreferenceManager.getDefaultSharedPreferences((Context)context);
        int n2 = context.getInt(KEY_LAST_BOOT_COUNT, -1);
        if (n != -1 && n2 != -1 && n == n2) {
            return false;
        }
        context.edit().putInt(KEY_LAST_BOOT_COUNT, n).apply();
        return true;
    }

    private static void loge(String string) {
        Log.e((String)TAG, (String)string);
    }

    private static void loge(String string, Throwable throwable) {
        Log.e((String)TAG, (String)string, (Throwable)throwable);
    }

    public void authenticateServer(String object, String string, String string2, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, final IAuthenticateServerCallback iAuthenticateServerCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iAuthenticateServerCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iAuthenticateServerCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("authenticateServer callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).authenticateServer(string2, arrby, arrby2, arrby3, arrby4, new AsyncResultCallback<byte[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("authenticateServer callback onException: ", throwable);
                    iAuthenticateServerCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("authenticateServer callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(byte[] arrby) {
                try {
                    iAuthenticateServerCallback.onComplete(0, arrby);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("authenticateServer callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void cancelSession(String object, String string, byte[] arrby, int n, final ICancelSessionCallback iCancelSessionCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iCancelSessionCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iCancelSessionCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("cancelSession callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).cancelSession(arrby, n, new AsyncResultCallback<byte[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("cancelSession callback onException: ", throwable);
                    iCancelSessionCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("cancelSession callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(byte[] arrby) {
                try {
                    iCancelSessionCallback.onComplete(0, arrby);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("cancelSession callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void deleteProfile(String object, final String string, String string2, final IDeleteProfileCallback iDeleteProfileCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iDeleteProfileCallback.onComplete(-3);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iDeleteProfileCallback.onComplete(-2);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("deleteProfile callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).deleteProfile(string2, new AsyncResultCallback<Void>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("deleteProfile callback onException: ", throwable);
                    iDeleteProfileCallback.onComplete(EuiccCardController.this.getResultCode(throwable));
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("deleteProfile callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(Void void_) {
                Log.i((String)EuiccCardController.TAG, (String)"Request subscription info list refresh after delete.");
                SubscriptionController.getInstance().requestEmbeddedSubscriptionInfoListRefresh(EuiccCardController.this.mUiccController.convertToPublicCardId(string));
                try {
                    iDeleteProfileCallback.onComplete(0);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("deleteProfile callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void disableProfile(String object, String string, String string2, boolean bl, final IDisableProfileCallback iDisableProfileCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iDisableProfileCallback.onComplete(-3);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iDisableProfileCallback.onComplete(-2);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("disableProfile callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).disableProfile(string2, bl, new AsyncResultCallback<Void>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("disableProfile callback onException: ", throwable);
                    iDisableProfileCallback.onComplete(EuiccCardController.this.getResultCode(throwable));
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("disableProfile callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(Void void_) {
                try {
                    iDisableProfileCallback.onComplete(0);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("disableProfile callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        this.mContext.enforceCallingOrSelfPermission("android.permission.DUMP", "Requires DUMP");
        long l = Binder.clearCallingIdentity();
        super.dump((FileDescriptor)object, printWriter, arrstring);
        object = new StringBuilder();
        ((StringBuilder)object).append("mCallingPackage=");
        ((StringBuilder)object).append(this.mCallingPackage);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mBestComponent=");
        ((StringBuilder)object).append((Object)this.mBestComponent);
        printWriter.println(((StringBuilder)object).toString());
        Binder.restoreCallingIdentity((long)l);
    }

    public void getAllProfiles(String object, String string, final IGetAllProfilesCallback iGetAllProfilesCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetAllProfilesCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetAllProfilesCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getAllProfiles callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getAllProfiles(new AsyncResultCallback<EuiccProfileInfo[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getAllProfiles callback onException: ", throwable);
                    iGetAllProfilesCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getAllProfiles callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(EuiccProfileInfo[] arreuiccProfileInfo) {
                try {
                    iGetAllProfilesCallback.onComplete(0, arreuiccProfileInfo);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getAllProfiles callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void getDefaultSmdpAddress(String object, String string, final IGetDefaultSmdpAddressCallback iGetDefaultSmdpAddressCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetDefaultSmdpAddressCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetDefaultSmdpAddressCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getDefaultSmdpAddress callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getDefaultSmdpAddress(new AsyncResultCallback<String>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getDefaultSmdpAddress callback onException: ", throwable);
                    iGetDefaultSmdpAddressCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getDefaultSmdpAddress callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(String string) {
                try {
                    iGetDefaultSmdpAddressCallback.onComplete(0, string);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getDefaultSmdpAddress callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void getEuiccChallenge(String object, String string, final IGetEuiccChallengeCallback iGetEuiccChallengeCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetEuiccChallengeCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetEuiccChallengeCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getEuiccChallenge callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getEuiccChallenge(new AsyncResultCallback<byte[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getEuiccChallenge callback onException: ", throwable);
                    iGetEuiccChallengeCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getEuiccChallenge callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(byte[] arrby) {
                try {
                    iGetEuiccChallengeCallback.onComplete(0, arrby);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getEuiccChallenge callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void getEuiccInfo1(String object, String string, final IGetEuiccInfo1Callback iGetEuiccInfo1Callback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetEuiccInfo1Callback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetEuiccInfo1Callback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getEuiccInfo1 callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getEuiccInfo1(new AsyncResultCallback<byte[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getEuiccInfo1 callback onException: ", throwable);
                    iGetEuiccInfo1Callback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getEuiccInfo1 callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(byte[] arrby) {
                try {
                    iGetEuiccInfo1Callback.onComplete(0, arrby);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getEuiccInfo1 callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void getEuiccInfo2(String object, String string, final IGetEuiccInfo2Callback iGetEuiccInfo2Callback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetEuiccInfo2Callback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetEuiccInfo2Callback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getEuiccInfo2 callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getEuiccInfo2(new AsyncResultCallback<byte[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getEuiccInfo2 callback onException: ", throwable);
                    iGetEuiccInfo2Callback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getEuiccInfo2 callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(byte[] arrby) {
                try {
                    iGetEuiccInfo2Callback.onComplete(0, arrby);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getEuiccInfo2 callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void getProfile(String object, String string, String string2, final IGetProfileCallback iGetProfileCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetProfileCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetProfileCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getProfile callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getProfile(string2, new AsyncResultCallback<EuiccProfileInfo>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getProfile callback onException: ", throwable);
                    iGetProfileCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getProfile callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(EuiccProfileInfo euiccProfileInfo) {
                try {
                    iGetProfileCallback.onComplete(0, euiccProfileInfo);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getProfile callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void getRulesAuthTable(String object, String string, final IGetRulesAuthTableCallback iGetRulesAuthTableCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetRulesAuthTableCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetRulesAuthTableCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getRulesAuthTable callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getRulesAuthTable(new AsyncResultCallback<EuiccRulesAuthTable>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getRulesAuthTable callback onException: ", throwable);
                    iGetRulesAuthTableCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getRulesAuthTable callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(EuiccRulesAuthTable euiccRulesAuthTable) {
                try {
                    iGetRulesAuthTableCallback.onComplete(0, euiccRulesAuthTable);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getRulesAuthTable callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void getSmdsAddress(String object, String string, final IGetSmdsAddressCallback iGetSmdsAddressCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iGetSmdsAddressCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iGetSmdsAddressCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("getSmdsAddress callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getSmdsAddress(new AsyncResultCallback<String>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getSmdsAddress callback onException: ", throwable);
                    iGetSmdsAddressCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getSmdsAddress callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(String string) {
                try {
                    iGetSmdsAddressCallback.onComplete(0, string);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("getSmdsAddress callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public boolean isEmbeddedCardPresent() {
        UiccSlot[] arruiccSlot = this.mUiccController.getUiccSlots();
        if (arruiccSlot == null) {
            return false;
        }
        for (UiccSlot uiccSlot : arruiccSlot) {
            if (uiccSlot == null || uiccSlot.isRemovable() || uiccSlot.getCardState() == null || !uiccSlot.getCardState().isCardPresent()) continue;
            return true;
        }
        return false;
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PRIVATE)
    public boolean isEmbeddedSlotActivated() {
        UiccSlot[] arruiccSlot = this.mUiccController.getUiccSlots();
        if (arruiccSlot == null) {
            return false;
        }
        for (int i = 0; i < arruiccSlot.length; ++i) {
            UiccSlot uiccSlot = arruiccSlot[i];
            if (uiccSlot == null || uiccSlot.isRemovable() || !uiccSlot.isActive()) continue;
            return true;
        }
        return false;
    }

    public void listNotifications(String object, String string, int n, final IListNotificationsCallback iListNotificationsCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iListNotificationsCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iListNotificationsCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("listNotifications callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).listNotifications(n, new AsyncResultCallback<EuiccNotification[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("listNotifications callback onException: ", throwable);
                    iListNotificationsCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("listNotifications callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(EuiccNotification[] arreuiccNotification) {
                try {
                    iListNotificationsCallback.onComplete(0, arreuiccNotification);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("listNotifications callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void loadBoundProfilePackage(String object, final String string, byte[] arrby, final ILoadBoundProfilePackageCallback iLoadBoundProfilePackageCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iLoadBoundProfilePackageCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iLoadBoundProfilePackageCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("loadBoundProfilePackage callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).loadBoundProfilePackage(arrby, new AsyncResultCallback<byte[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("loadBoundProfilePackage callback onException: ", throwable);
                    iLoadBoundProfilePackageCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("loadBoundProfilePackage callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(byte[] arrby) {
                Log.i((String)EuiccCardController.TAG, (String)"Request subscription info list refresh after install.");
                SubscriptionController.getInstance().requestEmbeddedSubscriptionInfoListRefresh(EuiccCardController.this.mUiccController.convertToPublicCardId(string));
                try {
                    iLoadBoundProfilePackageCallback.onComplete(0, arrby);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("loadBoundProfilePackage callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void prepareDownload(String object, String string, byte[] arrby, byte[] arrby2, byte[] arrby3, byte[] arrby4, final IPrepareDownloadCallback iPrepareDownloadCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iPrepareDownloadCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iPrepareDownloadCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("prepareDownload callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).prepareDownload(arrby, arrby2, arrby3, arrby4, new AsyncResultCallback<byte[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("prepareDownload callback onException: ", throwable);
                    iPrepareDownloadCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("prepareDownload callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(byte[] arrby) {
                try {
                    iPrepareDownloadCallback.onComplete(0, arrby);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("prepareDownload callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void removeNotificationFromList(String object, String string, int n, final IRemoveNotificationFromListCallback iRemoveNotificationFromListCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iRemoveNotificationFromListCallback.onComplete(-3);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iRemoveNotificationFromListCallback.onComplete(-2);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("removeNotificationFromList callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).removeNotificationFromList(n, new AsyncResultCallback<Void>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("removeNotificationFromList callback onException: ", throwable);
                    iRemoveNotificationFromListCallback.onComplete(EuiccCardController.this.getResultCode(throwable));
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("removeNotificationFromList callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(Void void_) {
                try {
                    iRemoveNotificationFromListCallback.onComplete(0);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("removeNotificationFromList callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void resetMemory(String object, final String string, int n, final IResetMemoryCallback iResetMemoryCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iResetMemoryCallback.onComplete(-3);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iResetMemoryCallback.onComplete(-2);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("resetMemory callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).resetMemory(n, new AsyncResultCallback<Void>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("resetMemory callback onException: ", throwable);
                    iResetMemoryCallback.onComplete(EuiccCardController.this.getResultCode(throwable));
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("resetMemory callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(Void void_) {
                Log.i((String)EuiccCardController.TAG, (String)"Request subscription info list refresh after reset memory.");
                SubscriptionController.getInstance().requestEmbeddedSubscriptionInfoListRefresh(EuiccCardController.this.mUiccController.convertToPublicCardId(string));
                try {
                    iResetMemoryCallback.onComplete(0);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("resetMemory callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void retrieveNotification(String object, String string, int n, final IRetrieveNotificationCallback iRetrieveNotificationCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iRetrieveNotificationCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iRetrieveNotificationCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("retrieveNotification callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).retrieveNotification(n, new AsyncResultCallback<EuiccNotification>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("retrieveNotification callback onException: ", throwable);
                    iRetrieveNotificationCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("retrieveNotification callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(EuiccNotification euiccNotification) {
                try {
                    iRetrieveNotificationCallback.onComplete(0, euiccNotification);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("retrieveNotification callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void retrieveNotificationList(String object, String string, int n, final IRetrieveNotificationListCallback iRetrieveNotificationListCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iRetrieveNotificationListCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iRetrieveNotificationListCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("retrieveNotificationList callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).retrieveNotificationList(n, new AsyncResultCallback<EuiccNotification[]>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("retrieveNotificationList callback onException: ", throwable);
                    iRetrieveNotificationListCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("retrieveNotificationList callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(EuiccNotification[] arreuiccNotification) {
                try {
                    iRetrieveNotificationListCallback.onComplete(0, arreuiccNotification);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("retrieveNotificationList callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void setDefaultSmdpAddress(String object, String string, String string2, final ISetDefaultSmdpAddressCallback iSetDefaultSmdpAddressCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iSetDefaultSmdpAddressCallback.onComplete(-3);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iSetDefaultSmdpAddressCallback.onComplete(-2);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("setDefaultSmdpAddress callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).setDefaultSmdpAddress(string2, new AsyncResultCallback<Void>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("setDefaultSmdpAddress callback onException: ", throwable);
                    iSetDefaultSmdpAddressCallback.onComplete(EuiccCardController.this.getResultCode(throwable));
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("setDefaultSmdpAddress callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(Void void_) {
                try {
                    iSetDefaultSmdpAddressCallback.onComplete(0);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("setDefaultSmdpAddress callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void setNickname(String object, String string, String string2, String string3, final ISetNicknameCallback iSetNicknameCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iSetNicknameCallback.onComplete(-3);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iSetNicknameCallback.onComplete(-2);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("setNickname callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).setNickname(string2, string3, new AsyncResultCallback<Void>(){

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("setNickname callback onException: ", throwable);
                    iSetNicknameCallback.onComplete(EuiccCardController.this.getResultCode(throwable));
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("setNickname callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(Void void_) {
                try {
                    iSetNicknameCallback.onComplete(0);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("setNickname callback failure.", remoteException);
                }
            }
        }, this.mEuiccMainThreadHandler);
    }

    public void switchToProfile(String object, String string, String string2, boolean bl, final ISwitchToProfileCallback iSwitchToProfileCallback) {
        try {
            this.checkCallingPackage((String)object);
        }
        catch (SecurityException securityException) {
            try {
                iSwitchToProfileCallback.onComplete(-3, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("callback onComplete failure after checkCallingPackage.", remoteException);
            }
            return;
        }
        object = this.getEuiccCard(string);
        if (object == null) {
            try {
                iSwitchToProfileCallback.onComplete(-2, null);
            }
            catch (RemoteException remoteException) {
                EuiccCardController.loge("switchToProfile callback failure.", remoteException);
            }
            return;
        }
        ((EuiccCard)object).getProfile(string2, new AsyncResultCallback<EuiccProfileInfo>((EuiccCard)object, string2, bl){
            final /* synthetic */ EuiccCard val$card;
            final /* synthetic */ String val$iccid;
            final /* synthetic */ boolean val$refresh;
            {
                this.val$card = euiccCard;
                this.val$iccid = string;
                this.val$refresh = bl;
            }

            @Override
            public void onException(Throwable throwable) {
                try {
                    EuiccCardController.loge("getProfile in switchToProfile callback onException: ", throwable);
                    iSwitchToProfileCallback.onComplete(EuiccCardController.this.getResultCode(throwable), null);
                }
                catch (RemoteException remoteException) {
                    EuiccCardController.loge("switchToProfile callback failure.", remoteException);
                }
            }

            @Override
            public void onResult(final EuiccProfileInfo object) {
                object = new AsyncResultCallback<Void>(){

                    @Override
                    public void onException(Throwable throwable) {
                        try {
                            EuiccCardController.loge("switchToProfile callback onException: ", throwable);
                            iSwitchToProfileCallback.onComplete(EuiccCardController.this.getResultCode(throwable), object);
                        }
                        catch (RemoteException remoteException) {
                            EuiccCardController.loge("switchToProfile callback failure.", remoteException);
                        }
                    }

                    @Override
                    public void onResult(Void void_) {
                        try {
                            iSwitchToProfileCallback.onComplete(0, object);
                        }
                        catch (RemoteException remoteException) {
                            EuiccCardController.loge("switchToProfile callback failure.", remoteException);
                        }
                    }
                };
                this.val$card.switchToProfile(this.val$iccid, this.val$refresh, (AsyncResultCallback<Void>)object, EuiccCardController.this.mEuiccMainThreadHandler);
            }

        }, this.mEuiccMainThreadHandler);
    }

    private class SimSlotStatusChangedBroadcastReceiver
    extends BroadcastReceiver {
        private SimSlotStatusChangedBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.telephony.action.SIM_SLOT_STATUS_CHANGED".equals(intent.getAction())) {
                if (!EuiccCardController.this.isEmbeddedCardPresent()) {
                    return;
                }
                if (EuiccCardController.this.isEmbeddedSlotActivated()) {
                    EuiccCardController.this.mEuiccController.startOtaUpdatingIfNecessary();
                }
                EuiccCardController.this.mContext.unregisterReceiver((BroadcastReceiver)EuiccCardController.this.mSimSlotStatusChangeReceiver);
            }
        }
    }

}

