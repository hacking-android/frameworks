/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ComponentInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Looper
 *  android.os.Message
 *  android.os.RemoteException
 *  android.service.euicc.DownloadSubscriptionResult
 *  android.service.euicc.GetDefaultDownloadableSubscriptionListResult
 *  android.service.euicc.GetDownloadableSubscriptionMetadataResult
 *  android.service.euicc.GetEuiccProfileInfoListResult
 *  android.service.euicc.IDeleteSubscriptionCallback
 *  android.service.euicc.IDeleteSubscriptionCallback$Stub
 *  android.service.euicc.IDownloadSubscriptionCallback
 *  android.service.euicc.IDownloadSubscriptionCallback$Stub
 *  android.service.euicc.IEraseSubscriptionsCallback
 *  android.service.euicc.IEraseSubscriptionsCallback$Stub
 *  android.service.euicc.IEuiccService
 *  android.service.euicc.IEuiccService$Stub
 *  android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback
 *  android.service.euicc.IGetDefaultDownloadableSubscriptionListCallback$Stub
 *  android.service.euicc.IGetDownloadableSubscriptionMetadataCallback
 *  android.service.euicc.IGetDownloadableSubscriptionMetadataCallback$Stub
 *  android.service.euicc.IGetEidCallback
 *  android.service.euicc.IGetEidCallback$Stub
 *  android.service.euicc.IGetEuiccInfoCallback
 *  android.service.euicc.IGetEuiccInfoCallback$Stub
 *  android.service.euicc.IGetEuiccProfileInfoListCallback
 *  android.service.euicc.IGetEuiccProfileInfoListCallback$Stub
 *  android.service.euicc.IGetOtaStatusCallback
 *  android.service.euicc.IGetOtaStatusCallback$Stub
 *  android.service.euicc.IOtaStatusChangedCallback
 *  android.service.euicc.IOtaStatusChangedCallback$Stub
 *  android.service.euicc.IRetainSubscriptionsForFactoryResetCallback
 *  android.service.euicc.IRetainSubscriptionsForFactoryResetCallback$Stub
 *  android.service.euicc.ISwitchToSubscriptionCallback
 *  android.service.euicc.ISwitchToSubscriptionCallback$Stub
 *  android.service.euicc.IUpdateSubscriptionNicknameCallback
 *  android.service.euicc.IUpdateSubscriptionNicknameCallback$Stub
 *  android.telephony.TelephonyManager
 *  android.telephony.euicc.DownloadableSubscription
 *  android.telephony.euicc.EuiccInfo
 *  android.text.TextUtils
 *  android.util.ArraySet
 *  android.util.Log
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.annotations.VisibleForTesting$Visibility
 *  com.android.internal.content.PackageMonitor
 *  com.android.internal.util.IState
 *  com.android.internal.util.State
 *  com.android.internal.util.StateMachine
 */
package com.android.internal.telephony.euicc;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
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
import android.telephony.TelephonyManager;
import android.telephony.euicc.DownloadableSubscription;
import android.telephony.euicc.EuiccInfo;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.content.PackageMonitor;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$1$wTkmDdVlxcrtbVPcCl3t7xD490o;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$10$uMqDQsfFYIEEah_N7V76hMlEL94;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$11$yvv0ylXs7V5vymCcYvu3RpgoeDw;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$12$wYal9P4llN7g9YAk_zACL8m3nS0;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$13$5nh8TOHvAdIIa_S3V0gwsRICKC4;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$13$REfW_lBcrAssQONSKwOlO3PX83k;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$2$IvG3dLVC7AcOy5j0EwIqA8hP44Q;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$3$6FrGqACrFuV_2Sxte2SudRMjR6s;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$4$S52i3hpE3_FGho807KZ1LR5rXQM;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$5$zyynBcfeewf_ACr0Sg8S162JrG4;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$6$RMNCT6pukGHYhU_7k7HVxbm5IWE;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$7$_Ogvr7PIASwQa0kQAqAyfdEKAG4;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$8$653ymvVUxXSmc5rF5YXkbNw3yw8;
import com.android.internal.telephony.euicc._$$Lambda$EuiccConnector$ConnectedState$9$xm26YKGxl72UYoxSNyEMJslmmNk;
import com.android.internal.util.IState;
import com.android.internal.util.State;
import com.android.internal.util.StateMachine;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class EuiccConnector
extends StateMachine
implements ServiceConnection {
    private static final int BIND_TIMEOUT_MILLIS = 30000;
    private static final int CMD_COMMAND_COMPLETE = 6;
    private static final int CMD_CONNECT_TIMEOUT = 2;
    private static final int CMD_DELETE_SUBSCRIPTION = 106;
    private static final int CMD_DOWNLOAD_SUBSCRIPTION = 102;
    private static final int CMD_ERASE_SUBSCRIPTIONS = 109;
    private static final int CMD_GET_DEFAULT_DOWNLOADABLE_SUBSCRIPTION_LIST = 104;
    private static final int CMD_GET_DOWNLOADABLE_SUBSCRIPTION_METADATA = 101;
    private static final int CMD_GET_EID = 100;
    private static final int CMD_GET_EUICC_INFO = 105;
    private static final int CMD_GET_EUICC_PROFILE_INFO_LIST = 103;
    private static final int CMD_GET_OTA_STATUS = 111;
    private static final int CMD_LINGER_TIMEOUT = 3;
    private static final int CMD_PACKAGE_CHANGE = 1;
    private static final int CMD_RETAIN_SUBSCRIPTIONS = 110;
    private static final int CMD_SERVICE_CONNECTED = 4;
    private static final int CMD_SERVICE_DISCONNECTED = 5;
    private static final int CMD_START_OTA_IF_NECESSARY = 112;
    private static final int CMD_SWITCH_TO_SUBSCRIPTION = 107;
    private static final int CMD_UPDATE_SUBSCRIPTION_NICKNAME = 108;
    private static final int EUICC_QUERY_FLAGS = 269484096;
    @VisibleForTesting
    static final int LINGER_TIMEOUT_MILLIS = 60000;
    private static final String TAG = "EuiccConnector";
    private Set<BaseEuiccCommandCallback> mActiveCommandCallbacks = new ArraySet();
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public AvailableState mAvailableState;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public BindingState mBindingState;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public ConnectedState mConnectedState;
    private Context mContext;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public DisconnectedState mDisconnectedState;
    private IEuiccService mEuiccService;
    private final PackageMonitor mPackageMonitor = new EuiccPackageMonitor();
    private PackageManager mPm;
    private ServiceInfo mSelectedComponent;
    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public UnavailableState mUnavailableState;
    private final BroadcastReceiver mUserUnlockedReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_UNLOCKED".equals(intent.getAction())) {
                EuiccConnector.this.sendMessage(1);
            }
        }
    };

    EuiccConnector(Context context) {
        super(TAG);
        this.init(context);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public EuiccConnector(Context context, Looper looper) {
        super(TAG, looper);
        this.init(context);
    }

    private boolean createBinding() {
        if (this.mSelectedComponent == null) {
            Log.wtf((String)TAG, (String)"Attempting to create binding but no component is selected");
            return false;
        }
        Intent intent = new Intent("android.service.euicc.EuiccService");
        intent.setComponent(this.mSelectedComponent.getComponentName());
        return this.mContext.bindService(intent, (ServiceConnection)this, 67108865);
    }

    public static ActivityInfo findBestActivity(PackageManager packageManager, Intent intent) {
        Object object = packageManager.queryIntentActivities(intent, 269484096);
        if ((packageManager = (ActivityInfo)EuiccConnector.findBestComponent(packageManager, (List<ResolveInfo>)object)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No valid component found for intent: ");
            ((StringBuilder)object).append((Object)intent);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
        }
        return packageManager;
    }

    public static ComponentInfo findBestComponent(PackageManager packageManager) {
        Intent intent = new Intent("android.service.euicc.EuiccService");
        if ((packageManager = EuiccConnector.findBestComponent(packageManager, packageManager.queryIntentServices(intent, 269484096))) == null) {
            Log.w((String)TAG, (String)"No valid EuiccService implementation found");
        }
        return packageManager;
    }

    private static ComponentInfo findBestComponent(PackageManager packageManager, List<ResolveInfo> resolveInfo) {
        int n = Integer.MIN_VALUE;
        ResolveInfo resolveInfo2 = null;
        Object var4_4 = null;
        if (resolveInfo != null) {
            Iterator<ResolveInfo> iterator = resolveInfo.iterator();
            resolveInfo = var4_4;
            do {
                resolveInfo2 = resolveInfo;
                if (!iterator.hasNext()) break;
                resolveInfo2 = iterator.next();
                if (!EuiccConnector.isValidEuiccComponent(packageManager, resolveInfo2)) continue;
                int n2 = n;
                if (resolveInfo2.filter.getPriority() > n) {
                    n2 = resolveInfo2.filter.getPriority();
                    resolveInfo = resolveInfo2.getComponentInfo();
                }
                n = n2;
            } while (true);
        }
        return resolveInfo2;
    }

    private ServiceInfo findBestComponent() {
        return (ServiceInfo)EuiccConnector.findBestComponent(this.mPm);
    }

    private static BaseEuiccCommandCallback getCallback(Message message) {
        switch (message.what) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported message: ");
                stringBuilder.append(message.what);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            case 108: {
                return ((UpdateNicknameRequest)message.obj).mCallback;
            }
            case 107: {
                return ((SwitchRequest)message.obj).mCallback;
            }
            case 106: {
                return ((DeleteRequest)message.obj).mCallback;
            }
            case 104: {
                return ((GetDefaultListRequest)message.obj).mCallback;
            }
            case 102: {
                return ((DownloadRequest)message.obj).mCallback;
            }
            case 101: {
                return ((GetMetadataRequest)message.obj).mCallback;
            }
            case 100: 
            case 103: 
            case 105: 
            case 109: 
            case 110: 
            case 111: 
            case 112: 
        }
        return (BaseEuiccCommandCallback)message.obj;
    }

    private int getSlotIdFromCardId(int n) {
        if (n != -1 && n != -2) {
            List list2 = ((TelephonyManager)this.mContext.getSystemService("phone")).getUiccCardsInfo();
            if (list2 != null && list2.size() != 0) {
                int n2 = -1;
                for (List list2 : list2) {
                    if (list2.getCardId() != n) continue;
                    n2 = list2.getSlotIndex();
                }
                return n2;
            }
            return -1;
        }
        return -1;
    }

    private void init(Context object) {
        this.mContext = object;
        this.mPm = object.getPackageManager();
        this.mUnavailableState = new UnavailableState();
        this.addState((State)this.mUnavailableState);
        this.mAvailableState = new AvailableState();
        this.addState((State)this.mAvailableState, (State)this.mUnavailableState);
        this.mBindingState = new BindingState();
        this.addState((State)this.mBindingState);
        this.mDisconnectedState = new DisconnectedState();
        this.addState((State)this.mDisconnectedState);
        this.mConnectedState = new ConnectedState();
        this.addState((State)this.mConnectedState, (State)this.mDisconnectedState);
        this.mSelectedComponent = this.findBestComponent();
        object = this.mSelectedComponent != null ? this.mAvailableState : this.mUnavailableState;
        this.setInitialState((State)object);
        this.mPackageMonitor.register(this.mContext, null, false);
        this.mContext.registerReceiver(this.mUserUnlockedReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
        this.start();
    }

    private static boolean isEuiccCommand(int n) {
        boolean bl = n >= 100;
        return bl;
    }

    private static boolean isValidEuiccComponent(PackageManager object, ResolveInfo resolveInfo) {
        block8 : {
            String string;
            block7 : {
                ComponentInfo componentInfo;
                block6 : {
                    componentInfo = resolveInfo.getComponentInfo();
                    string = componentInfo.getComponentName().getPackageName();
                    if (object.checkPermission("android.permission.WRITE_EMBEDDED_SUBSCRIPTIONS", string) != 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Package ");
                        ((StringBuilder)object).append(string);
                        ((StringBuilder)object).append(" does not declare WRITE_EMBEDDED_SUBSCRIPTIONS");
                        Log.wtf((String)TAG, (String)((StringBuilder)object).toString());
                        return false;
                    }
                    if (!(componentInfo instanceof ServiceInfo)) break block6;
                    object = ((ServiceInfo)componentInfo).permission;
                    break block7;
                }
                if (!(componentInfo instanceof ActivityInfo)) break block8;
                object = ((ActivityInfo)componentInfo).permission;
            }
            if (!TextUtils.equals((CharSequence)object, (CharSequence)"android.permission.BIND_EUICC_SERVICE")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Package ");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" does not require the BIND_EUICC_SERVICE permission");
                Log.wtf((String)TAG, (String)((StringBuilder)object).toString());
                return false;
            }
            if (resolveInfo.filter != null && resolveInfo.filter.getPriority() != 0) {
                return true;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Package ");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" does not specify a priority");
            Log.wtf((String)TAG, (String)((StringBuilder)object).toString());
            return false;
        }
        throw new IllegalArgumentException("Can only verify services/activities");
    }

    private void onCommandEnd(BaseEuiccCommandCallback baseEuiccCommandCallback) {
        if (!this.mActiveCommandCallbacks.remove(baseEuiccCommandCallback)) {
            Log.wtf((String)TAG, (String)"Callback already removed from mActiveCommandCallbacks");
        }
        if (this.mActiveCommandCallbacks.isEmpty()) {
            this.sendMessageDelayed(3, 60000L);
        }
    }

    private void onCommandStart(BaseEuiccCommandCallback baseEuiccCommandCallback) {
        this.mActiveCommandCallbacks.add(baseEuiccCommandCallback);
        this.removeMessages(3);
    }

    private void unbind() {
        this.mEuiccService = null;
        this.mContext.unbindService((ServiceConnection)this);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void deleteSubscription(int n, String string, DeleteCommandCallback deleteCommandCallback) {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.mIccid = string;
        deleteRequest.mCallback = deleteCommandCallback;
        this.sendMessage(106, n, 0, (Object)deleteRequest);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void downloadSubscription(int n, DownloadableSubscription downloadableSubscription, boolean bl, boolean bl2, Bundle bundle, DownloadCommandCallback downloadCommandCallback) {
        DownloadRequest downloadRequest = new DownloadRequest();
        downloadRequest.mSubscription = downloadableSubscription;
        downloadRequest.mSwitchAfterDownload = bl;
        downloadRequest.mForceDeactivateSim = bl2;
        downloadRequest.mResolvedBundle = bundle;
        downloadRequest.mCallback = downloadCommandCallback;
        this.sendMessage(102, n, 0, (Object)downloadRequest);
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        super.dump((FileDescriptor)object, printWriter, arrstring);
        object = new StringBuilder();
        ((StringBuilder)object).append("mSelectedComponent=");
        ((StringBuilder)object).append((Object)this.mSelectedComponent);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mEuiccService=");
        ((StringBuilder)object).append((Object)this.mEuiccService);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mActiveCommandCount=");
        ((StringBuilder)object).append(this.mActiveCommandCallbacks.size());
        printWriter.println(((StringBuilder)object).toString());
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void eraseSubscriptions(int n, EraseCommandCallback eraseCommandCallback) {
        this.sendMessage(109, n, 0, (Object)eraseCommandCallback);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void getDefaultDownloadableSubscriptionList(int n, boolean bl, GetDefaultListCommandCallback getDefaultListCommandCallback) {
        GetDefaultListRequest getDefaultListRequest = new GetDefaultListRequest();
        getDefaultListRequest.mForceDeactivateSim = bl;
        getDefaultListRequest.mCallback = getDefaultListCommandCallback;
        this.sendMessage(104, n, 0, (Object)getDefaultListRequest);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void getDownloadableSubscriptionMetadata(int n, DownloadableSubscription downloadableSubscription, boolean bl, GetMetadataCommandCallback getMetadataCommandCallback) {
        GetMetadataRequest getMetadataRequest = new GetMetadataRequest();
        getMetadataRequest.mSubscription = downloadableSubscription;
        getMetadataRequest.mForceDeactivateSim = bl;
        getMetadataRequest.mCallback = getMetadataCommandCallback;
        this.sendMessage(101, n, 0, (Object)getMetadataRequest);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void getEid(int n, GetEidCommandCallback getEidCommandCallback) {
        this.sendMessage(100, n, 0, (Object)getEidCommandCallback);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void getEuiccInfo(int n, GetEuiccInfoCommandCallback getEuiccInfoCommandCallback) {
        this.sendMessage(105, n, 0, (Object)getEuiccInfoCommandCallback);
    }

    void getEuiccProfileInfoList(int n, GetEuiccProfileInfoListCommandCallback getEuiccProfileInfoListCommandCallback) {
        this.sendMessage(103, n, 0, (Object)getEuiccProfileInfoListCommandCallback);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void getOtaStatus(int n, GetOtaStatusCommandCallback getOtaStatusCommandCallback) {
        this.sendMessage(111, n, 0, (Object)getOtaStatusCommandCallback);
    }

    public void onHalting() {
        this.mPackageMonitor.unregister();
        this.mContext.unregisterReceiver(this.mUserUnlockedReceiver);
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        this.sendMessage(4, (Object)IEuiccService.Stub.asInterface((IBinder)iBinder));
    }

    public void onServiceDisconnected(ComponentName componentName) {
        this.sendMessage(5);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void retainSubscriptions(int n, RetainSubscriptionsCommandCallback retainSubscriptionsCommandCallback) {
        this.sendMessage(110, n, 0, (Object)retainSubscriptionsCommandCallback);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void startOtaIfNecessary(int n, OtaStatusChangedCallback otaStatusChangedCallback) {
        this.sendMessage(112, n, 0, (Object)otaStatusChangedCallback);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void switchToSubscription(int n, String string, boolean bl, SwitchCommandCallback switchCommandCallback) {
        SwitchRequest switchRequest = new SwitchRequest();
        switchRequest.mIccid = string;
        switchRequest.mForceDeactivateSim = bl;
        switchRequest.mCallback = switchCommandCallback;
        this.sendMessage(107, n, 0, (Object)switchRequest);
    }

    protected void unhandledMessage(Message object) {
        IState iState = this.getCurrentState();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unhandled message ");
        stringBuilder.append(object.what);
        stringBuilder.append(" in state ");
        object = iState == null ? "null" : iState.getName();
        stringBuilder.append((String)object);
        Log.wtf((String)TAG, (String)stringBuilder.toString());
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public void updateSubscriptionNickname(int n, String string, String string2, UpdateNicknameCommandCallback updateNicknameCommandCallback) {
        UpdateNicknameRequest updateNicknameRequest = new UpdateNicknameRequest();
        updateNicknameRequest.mIccid = string;
        updateNicknameRequest.mNickname = string2;
        updateNicknameRequest.mCallback = updateNicknameCommandCallback;
        this.sendMessage(108, n, 0, (Object)updateNicknameRequest);
    }

    private class AvailableState
    extends State {
        private AvailableState() {
        }

        public boolean processMessage(Message object) {
            if (EuiccConnector.isEuiccCommand(object.what)) {
                EuiccConnector.this.deferMessage(object);
                object = EuiccConnector.this;
                object.transitionTo((IState)object.mBindingState);
                return true;
            }
            return false;
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface BaseEuiccCommandCallback {
        public void onEuiccServiceUnavailable();
    }

    private class BindingState
    extends State {
        private BindingState() {
        }

        public void enter() {
            if (EuiccConnector.this.createBinding()) {
                EuiccConnector euiccConnector = EuiccConnector.this;
                euiccConnector.transitionTo((IState)euiccConnector.mDisconnectedState);
            } else {
                EuiccConnector euiccConnector = EuiccConnector.this;
                euiccConnector.transitionTo((IState)euiccConnector.mAvailableState);
            }
        }

        public boolean processMessage(Message message) {
            EuiccConnector.this.deferMessage(message);
            return true;
        }
    }

    private class ConnectedState
    extends State {
        private ConnectedState() {
        }

        public void enter() {
            EuiccConnector.this.removeMessages(2);
            EuiccConnector.this.sendMessageDelayed(3, 60000L);
        }

        public void exit() {
            EuiccConnector.this.removeMessages(3);
            Iterator iterator = EuiccConnector.this.mActiveCommandCallbacks.iterator();
            while (iterator.hasNext()) {
                ((BaseEuiccCommandCallback)iterator.next()).onEuiccServiceUnavailable();
            }
            EuiccConnector.this.mActiveCommandCallbacks.clear();
        }

        public boolean processMessage(Message object) {
            if (object.what == 5) {
                EuiccConnector.this.mEuiccService = null;
                object = EuiccConnector.this;
                object.transitionTo((IState)object.mDisconnectedState);
                return true;
            }
            if (object.what == 3) {
                EuiccConnector.this.unbind();
                object = EuiccConnector.this;
                object.transitionTo((IState)object.mAvailableState);
                return true;
            }
            if (object.what == 6) {
                ((Runnable)object.obj).run();
                return true;
            }
            if (EuiccConnector.isEuiccCommand(object.what)) {
                block21 : {
                    final BaseEuiccCommandCallback baseEuiccCommandCallback = EuiccConnector.getCallback(object);
                    EuiccConnector.this.onCommandStart(baseEuiccCommandCallback);
                    final int n = object.arg1;
                    int n2 = EuiccConnector.this.getSlotIdFromCardId(n);
                    try {
                        switch (object.what) {
                            default: {
                                break;
                            }
                            case 112: {
                                IEuiccService iEuiccService = EuiccConnector.this.mEuiccService;
                                object = new IOtaStatusChangedCallback.Stub(){

                                    static /* synthetic */ void lambda$onOtaStatusChanged$0(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((OtaStatusChangedCallback)baseEuiccCommandCallback2).onOtaStatusChanged(n);
                                    }

                                    public /* synthetic */ void lambda$onOtaStatusChanged$1$EuiccConnector$ConnectedState$13(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((OtaStatusChangedCallback)baseEuiccCommandCallback2).onOtaStatusChanged(n);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onOtaStatusChanged(int n) throws RemoteException {
                                        if (n == 1) {
                                            EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$13$5nh8TOHvAdIIa_S3V0gwsRICKC4(baseEuiccCommandCallback, n));
                                        } else {
                                            EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$13$REfW_lBcrAssQONSKwOlO3PX83k(this, baseEuiccCommandCallback, n));
                                        }
                                    }
                                };
                                iEuiccService.startOtaIfNecessary(n2, (IOtaStatusChangedCallback)object);
                                break block21;
                            }
                            case 111: {
                                IEuiccService iEuiccService = EuiccConnector.this.mEuiccService;
                                object = new IGetOtaStatusCallback.Stub(){

                                    public /* synthetic */ void lambda$onSuccess$0$EuiccConnector$ConnectedState$12(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((GetOtaStatusCommandCallback)baseEuiccCommandCallback2).onGetOtaStatusComplete(n);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onSuccess(int n) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$12$wYal9P4llN7g9YAk_zACL8m3nS0(this, baseEuiccCommandCallback, n));
                                    }
                                };
                                iEuiccService.getOtaStatus(n2, (IGetOtaStatusCallback)object);
                                break block21;
                            }
                            case 110: {
                                object = EuiccConnector.this.mEuiccService;
                                IRetainSubscriptionsForFactoryResetCallback.Stub stub = new IRetainSubscriptionsForFactoryResetCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$11(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((RetainSubscriptionsCommandCallback)baseEuiccCommandCallback2).onRetainSubscriptionsComplete(n);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(int n) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$11$yvv0ylXs7V5vymCcYvu3RpgoeDw(this, baseEuiccCommandCallback, n));
                                    }
                                };
                                object.retainSubscriptionsForFactoryReset(n2, (IRetainSubscriptionsForFactoryResetCallback)stub);
                                break block21;
                            }
                            case 109: {
                                object = EuiccConnector.this.mEuiccService;
                                IEraseSubscriptionsCallback.Stub stub = new IEraseSubscriptionsCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$10(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((EraseCommandCallback)baseEuiccCommandCallback2).onEraseComplete(n);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(int n) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$10$uMqDQsfFYIEEah_N7V76hMlEL94(this, baseEuiccCommandCallback, n));
                                    }
                                };
                                object.eraseSubscriptions(n2, (IEraseSubscriptionsCallback)stub);
                                break block21;
                            }
                            case 108: {
                                Object object2 = (UpdateNicknameRequest)object.obj;
                                IEuiccService iEuiccService = EuiccConnector.this.mEuiccService;
                                object = ((UpdateNicknameRequest)object2).mIccid;
                                object2 = ((UpdateNicknameRequest)object2).mNickname;
                                IUpdateSubscriptionNicknameCallback.Stub stub = new IUpdateSubscriptionNicknameCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$9(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((UpdateNicknameCommandCallback)baseEuiccCommandCallback2).onUpdateNicknameComplete(n);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(int n) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$9$xm26YKGxl72UYoxSNyEMJslmmNk(this, baseEuiccCommandCallback, n));
                                    }
                                };
                                iEuiccService.updateSubscriptionNickname(n2, (String)object, (String)object2, (IUpdateSubscriptionNicknameCallback)stub);
                                break block21;
                            }
                            case 107: {
                                Object object3 = (SwitchRequest)object.obj;
                                object = EuiccConnector.this.mEuiccService;
                                String string = object3.mIccid;
                                boolean bl = object3.mForceDeactivateSim;
                                object3 = new ISwitchToSubscriptionCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$8(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((SwitchCommandCallback)baseEuiccCommandCallback2).onSwitchComplete(n);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(int n) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$8$653ymvVUxXSmc5rF5YXkbNw3yw8(this, baseEuiccCommandCallback, n));
                                    }
                                };
                                object.switchToSubscription(n2, string, bl, (ISwitchToSubscriptionCallback)object3);
                                break block21;
                            }
                            case 106: {
                                Object object4 = (DeleteRequest)object.obj;
                                object = EuiccConnector.this.mEuiccService;
                                object4 = ((DeleteRequest)object4).mIccid;
                                IDeleteSubscriptionCallback.Stub stub = new IDeleteSubscriptionCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$7(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n) {
                                        ((DeleteCommandCallback)baseEuiccCommandCallback2).onDeleteComplete(n);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(int n) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$7$_Ogvr7PIASwQa0kQAqAyfdEKAG4(this, baseEuiccCommandCallback, n));
                                    }
                                };
                                object.deleteSubscription(n2, (String)object4, (IDeleteSubscriptionCallback)stub);
                                break block21;
                            }
                            case 105: {
                                IEuiccService iEuiccService = EuiccConnector.this.mEuiccService;
                                object = new IGetEuiccInfoCallback.Stub(){

                                    public /* synthetic */ void lambda$onSuccess$0$EuiccConnector$ConnectedState$6(BaseEuiccCommandCallback baseEuiccCommandCallback2, EuiccInfo euiccInfo) {
                                        ((GetEuiccInfoCommandCallback)baseEuiccCommandCallback2).onGetEuiccInfoComplete(euiccInfo);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onSuccess(EuiccInfo euiccInfo) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$6$RMNCT6pukGHYhU_7k7HVxbm5IWE(this, baseEuiccCommandCallback, euiccInfo));
                                    }
                                };
                                iEuiccService.getEuiccInfo(n2, (IGetEuiccInfoCallback)object);
                                break block21;
                            }
                            case 104: {
                                Object object5 = (GetDefaultListRequest)object.obj;
                                object = EuiccConnector.this.mEuiccService;
                                boolean bl = object5.mForceDeactivateSim;
                                object5 = new IGetDefaultDownloadableSubscriptionListCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$5(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n2, GetDefaultDownloadableSubscriptionListResult getDefaultDownloadableSubscriptionListResult) {
                                        ((GetDefaultListCommandCallback)baseEuiccCommandCallback2).onGetDefaultListComplete(n2, getDefaultDownloadableSubscriptionListResult);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(GetDefaultDownloadableSubscriptionListResult getDefaultDownloadableSubscriptionListResult) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$5$zyynBcfeewf_ACr0Sg8S162JrG4(this, baseEuiccCommandCallback, n, getDefaultDownloadableSubscriptionListResult));
                                    }
                                };
                                object.getDefaultDownloadableSubscriptionList(n2, bl, (IGetDefaultDownloadableSubscriptionListCallback)object5);
                                break block21;
                            }
                            case 103: {
                                object = EuiccConnector.this.mEuiccService;
                                IGetEuiccProfileInfoListCallback.Stub stub = new IGetEuiccProfileInfoListCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$4(BaseEuiccCommandCallback baseEuiccCommandCallback2, GetEuiccProfileInfoListResult getEuiccProfileInfoListResult) {
                                        ((GetEuiccProfileInfoListCommandCallback)baseEuiccCommandCallback2).onListComplete(getEuiccProfileInfoListResult);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(GetEuiccProfileInfoListResult getEuiccProfileInfoListResult) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$4$S52i3hpE3_FGho807KZ1LR5rXQM(this, baseEuiccCommandCallback, getEuiccProfileInfoListResult));
                                    }
                                };
                                object.getEuiccProfileInfoList(n2, (IGetEuiccProfileInfoListCallback)stub);
                                break block21;
                            }
                            case 102: {
                                DownloadRequest downloadRequest = (DownloadRequest)object.obj;
                                object = EuiccConnector.this.mEuiccService;
                                DownloadableSubscription downloadableSubscription = downloadRequest.mSubscription;
                                boolean bl = downloadRequest.mSwitchAfterDownload;
                                boolean bl2 = downloadRequest.mForceDeactivateSim;
                                downloadRequest = downloadRequest.mResolvedBundle;
                                IDownloadSubscriptionCallback.Stub stub = new IDownloadSubscriptionCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$3(BaseEuiccCommandCallback baseEuiccCommandCallback2, DownloadSubscriptionResult downloadSubscriptionResult) {
                                        ((DownloadCommandCallback)baseEuiccCommandCallback2).onDownloadComplete(downloadSubscriptionResult);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(DownloadSubscriptionResult downloadSubscriptionResult) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$3$6FrGqACrFuV_2Sxte2SudRMjR6s(this, baseEuiccCommandCallback, downloadSubscriptionResult));
                                    }
                                };
                                object.downloadSubscription(n2, downloadableSubscription, bl, bl2, (Bundle)downloadRequest, (IDownloadSubscriptionCallback)stub);
                                break block21;
                            }
                            case 101: {
                                Object object6 = (GetMetadataRequest)object.obj;
                                object = EuiccConnector.this.mEuiccService;
                                DownloadableSubscription downloadableSubscription = object6.mSubscription;
                                boolean bl = object6.mForceDeactivateSim;
                                object6 = new IGetDownloadableSubscriptionMetadataCallback.Stub(){

                                    public /* synthetic */ void lambda$onComplete$0$EuiccConnector$ConnectedState$2(BaseEuiccCommandCallback baseEuiccCommandCallback2, int n2, GetDownloadableSubscriptionMetadataResult getDownloadableSubscriptionMetadataResult) {
                                        ((GetMetadataCommandCallback)baseEuiccCommandCallback2).onGetMetadataComplete(n2, getDownloadableSubscriptionMetadataResult);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onComplete(GetDownloadableSubscriptionMetadataResult getDownloadableSubscriptionMetadataResult) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$2$IvG3dLVC7AcOy5j0EwIqA8hP44Q(this, baseEuiccCommandCallback, n, getDownloadableSubscriptionMetadataResult));
                                    }
                                };
                                object.getDownloadableSubscriptionMetadata(n2, downloadableSubscription, bl, (IGetDownloadableSubscriptionMetadataCallback)object6);
                                break block21;
                            }
                            case 100: {
                                object = EuiccConnector.this.mEuiccService;
                                IGetEidCallback.Stub stub = new IGetEidCallback.Stub(){

                                    public /* synthetic */ void lambda$onSuccess$0$EuiccConnector$ConnectedState$1(BaseEuiccCommandCallback baseEuiccCommandCallback2, String string) {
                                        ((GetEidCommandCallback)baseEuiccCommandCallback2).onGetEidComplete(string);
                                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback2);
                                    }

                                    public void onSuccess(String string) {
                                        EuiccConnector.this.sendMessage(6, (Object)new _$$Lambda$EuiccConnector$ConnectedState$1$wTkmDdVlxcrtbVPcCl3t7xD490o(this, baseEuiccCommandCallback, string));
                                    }
                                };
                                object.getEid(n2, (IGetEidCallback)stub);
                                break block21;
                            }
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unimplemented eUICC command: ");
                        stringBuilder.append(object.what);
                        Log.wtf((String)EuiccConnector.TAG, (String)stringBuilder.toString());
                        baseEuiccCommandCallback.onEuiccServiceUnavailable();
                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback);
                        return true;
                    }
                    catch (Exception exception) {
                        Log.w((String)EuiccConnector.TAG, (String)"Exception making binder call to EuiccService", (Throwable)exception);
                        baseEuiccCommandCallback.onEuiccServiceUnavailable();
                        EuiccConnector.this.onCommandEnd(baseEuiccCommandCallback);
                    }
                }
                return true;
            }
            return false;
        }

    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface DeleteCommandCallback
    extends BaseEuiccCommandCallback {
        public void onDeleteComplete(int var1);
    }

    static class DeleteRequest {
        DeleteCommandCallback mCallback;
        String mIccid;

        DeleteRequest() {
        }
    }

    private class DisconnectedState
    extends State {
        private DisconnectedState() {
        }

        public void enter() {
            EuiccConnector.this.sendMessageDelayed(2, 30000L);
        }

        public boolean processMessage(Message object) {
            if (object.what == 4) {
                EuiccConnector.this.mEuiccService = (IEuiccService)object.obj;
                object = EuiccConnector.this;
                object.transitionTo((IState)object.mConnectedState);
                return true;
            }
            int n = object.what;
            boolean bl = false;
            if (n == 1) {
                ServiceInfo serviceInfo = EuiccConnector.this.findBestComponent();
                object = (String)object.obj;
                n = serviceInfo == null ? (EuiccConnector.this.mSelectedComponent != null ? 1 : 0) : (EuiccConnector.this.mSelectedComponent != null && !Objects.equals((Object)serviceInfo.getComponentName(), (Object)EuiccConnector.this.mSelectedComponent.getComponentName()) ? 0 : 1);
                if (serviceInfo != null && Objects.equals(serviceInfo.packageName, object)) {
                    bl = true;
                }
                if (n == 0 || bl) {
                    EuiccConnector.this.unbind();
                    EuiccConnector.this.mSelectedComponent = serviceInfo;
                    if (EuiccConnector.this.mSelectedComponent == null) {
                        object = EuiccConnector.this;
                        object.transitionTo((IState)object.mUnavailableState);
                    } else {
                        object = EuiccConnector.this;
                        object.transitionTo((IState)object.mBindingState);
                    }
                }
                return true;
            }
            if (object.what == 2) {
                object = EuiccConnector.this;
                object.transitionTo((IState)object.mAvailableState);
                return true;
            }
            if (EuiccConnector.isEuiccCommand(object.what)) {
                EuiccConnector.this.deferMessage(object);
                return true;
            }
            return false;
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface DownloadCommandCallback
    extends BaseEuiccCommandCallback {
        public void onDownloadComplete(DownloadSubscriptionResult var1);
    }

    static class DownloadRequest {
        DownloadCommandCallback mCallback;
        boolean mForceDeactivateSim;
        Bundle mResolvedBundle;
        DownloadableSubscription mSubscription;
        boolean mSwitchAfterDownload;

        DownloadRequest() {
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface EraseCommandCallback
    extends BaseEuiccCommandCallback {
        public void onEraseComplete(int var1);
    }

    private class EuiccPackageMonitor
    extends PackageMonitor {
        private EuiccPackageMonitor() {
        }

        private void sendPackageChange(String string, boolean bl) {
            EuiccConnector euiccConnector = EuiccConnector.this;
            if (!bl) {
                string = null;
            }
            euiccConnector.sendMessage(1, (Object)string);
        }

        public boolean onHandleForceStop(Intent intent, String[] arrstring, int n, boolean bl) {
            if (bl) {
                int n2 = arrstring.length;
                for (int i = 0; i < n2; ++i) {
                    this.sendPackageChange(arrstring[i], true);
                }
            }
            return super.onHandleForceStop(intent, arrstring, n, bl);
        }

        public void onPackageAdded(String string, int n) {
            this.sendPackageChange(string, true);
        }

        public void onPackageModified(String string) {
            this.sendPackageChange(string, false);
        }

        public void onPackageRemoved(String string, int n) {
            this.sendPackageChange(string, true);
        }

        public void onPackageUpdateFinished(String string, int n) {
            this.sendPackageChange(string, true);
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface GetDefaultListCommandCallback
    extends BaseEuiccCommandCallback {
        public void onGetDefaultListComplete(int var1, GetDefaultDownloadableSubscriptionListResult var2);
    }

    static class GetDefaultListRequest {
        GetDefaultListCommandCallback mCallback;
        boolean mForceDeactivateSim;

        GetDefaultListRequest() {
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface GetEidCommandCallback
    extends BaseEuiccCommandCallback {
        public void onGetEidComplete(String var1);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface GetEuiccInfoCommandCallback
    extends BaseEuiccCommandCallback {
        public void onGetEuiccInfoComplete(EuiccInfo var1);
    }

    static interface GetEuiccProfileInfoListCommandCallback
    extends BaseEuiccCommandCallback {
        public void onListComplete(GetEuiccProfileInfoListResult var1);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface GetMetadataCommandCallback
    extends BaseEuiccCommandCallback {
        public void onGetMetadataComplete(int var1, GetDownloadableSubscriptionMetadataResult var2);
    }

    static class GetMetadataRequest {
        GetMetadataCommandCallback mCallback;
        boolean mForceDeactivateSim;
        DownloadableSubscription mSubscription;

        GetMetadataRequest() {
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface GetOtaStatusCommandCallback
    extends BaseEuiccCommandCallback {
        public void onGetOtaStatusComplete(int var1);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface OtaStatusChangedCallback
    extends BaseEuiccCommandCallback {
        public void onOtaStatusChanged(int var1);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface RetainSubscriptionsCommandCallback
    extends BaseEuiccCommandCallback {
        public void onRetainSubscriptionsComplete(int var1);
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface SwitchCommandCallback
    extends BaseEuiccCommandCallback {
        public void onSwitchComplete(int var1);
    }

    static class SwitchRequest {
        SwitchCommandCallback mCallback;
        boolean mForceDeactivateSim;
        String mIccid;

        SwitchRequest() {
        }
    }

    private class UnavailableState
    extends State {
        private UnavailableState() {
        }

        public boolean processMessage(Message object) {
            if (object.what == 1) {
                object = EuiccConnector.this;
                ((EuiccConnector)((Object)object)).mSelectedComponent = ((EuiccConnector)((Object)object)).findBestComponent();
                if (EuiccConnector.this.mSelectedComponent != null) {
                    object = EuiccConnector.this;
                    object.transitionTo((IState)object.mAvailableState);
                } else if (EuiccConnector.this.getCurrentState() != EuiccConnector.this.mUnavailableState) {
                    object = EuiccConnector.this;
                    object.transitionTo((IState)object.mUnavailableState);
                }
                return true;
            }
            if (EuiccConnector.isEuiccCommand(object.what)) {
                EuiccConnector.getCallback(object).onEuiccServiceUnavailable();
                return true;
            }
            return false;
        }
    }

    @VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
    public static interface UpdateNicknameCommandCallback
    extends BaseEuiccCommandCallback {
        public void onUpdateNicknameComplete(int var1);
    }

    static class UpdateNicknameRequest {
        UpdateNicknameCommandCallback mCallback;
        String mIccid;
        String mNickname;

        UpdateNicknameRequest() {
        }
    }

}

