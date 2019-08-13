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
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.os.UserHandle
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.CarrierConfigManager
 *  android.telephony.INetworkService
 *  android.telephony.INetworkService$Stub
 *  android.telephony.INetworkServiceCallback
 *  android.telephony.INetworkServiceCallback$Stub
 *  android.telephony.NetworkRegistrationInfo
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telephony.AccessNetworkConstants;
import android.telephony.CarrierConfigManager;
import android.telephony.INetworkService;
import android.telephony.INetworkServiceCallback;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.Phone;
import java.util.Hashtable;
import java.util.Map;

public class NetworkRegistrationManager
extends Handler {
    private static final int EVENT_BIND_NETWORK_SERVICE = 1;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(intent.getAction()) && NetworkRegistrationManager.this.mPhone.getPhoneId() == intent.getIntExtra("android.telephony.extra.SLOT_INDEX", 0)) {
                NetworkRegistrationManager.this.logd("Carrier config changed. Try to bind network service.");
                NetworkRegistrationManager.this.sendEmptyMessage(1);
            }
        }
    };
    private final Map<NetworkRegStateCallback, Message> mCallbackTable = new Hashtable<NetworkRegStateCallback, Message>();
    private final CarrierConfigManager mCarrierConfigManager;
    private RegManagerDeathRecipient mDeathRecipient;
    private INetworkService mINetworkService;
    private final Phone mPhone;
    private final RegistrantList mRegStateChangeRegistrants = new RegistrantList();
    private NetworkServiceConnection mServiceConnection;
    private final String mTag;
    private String mTargetBindingPackageName;
    private final int mTransportType;

    public NetworkRegistrationManager(int n, Phone phone) {
        this.mTransportType = n;
        this.mPhone = phone;
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("-");
        CharSequence charSequence2 = n == 1 ? "C" : "I";
        ((StringBuilder)charSequence).append((String)charSequence2);
        charSequence = ((StringBuilder)charSequence).toString();
        charSequence2 = charSequence;
        if (TelephonyManager.getDefault().getPhoneCount() > 1) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("-");
            ((StringBuilder)charSequence2).append(this.mPhone.getPhoneId());
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("NRM");
        ((StringBuilder)charSequence).append((String)charSequence2);
        this.mTag = ((StringBuilder)charSequence).toString();
        this.mCarrierConfigManager = (CarrierConfigManager)phone.getContext().getSystemService("carrier_config");
        charSequence2 = new IntentFilter();
        charSequence2.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        phone.getContext().registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, (IntentFilter)charSequence2, null, null);
        this.sendEmptyMessage(1);
    }

    private void bindService() {
        CharSequence charSequence = this.getPackageName();
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            this.loge("Can't find the binding package");
            return;
        }
        if (TextUtils.equals((CharSequence)charSequence, (CharSequence)this.mTargetBindingPackageName)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Service ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(" already bound or being bound.");
            this.logd(stringBuilder.toString());
            return;
        }
        Object object = this.mINetworkService;
        if (object != null && object.asBinder().isBinderAlive()) {
            try {
                this.mINetworkService.removeNetworkServiceProvider(this.mPhone.getPhoneId());
            }
            catch (RemoteException remoteException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot remove data service provider. ");
                ((StringBuilder)object).append((Object)remoteException);
                this.loge(((StringBuilder)object).toString());
            }
            this.mPhone.getContext().unbindService((ServiceConnection)this.mServiceConnection);
        }
        object = new Intent("android.telephony.NetworkService");
        object.setPackage(this.getPackageName());
        try {
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Trying to bind ");
            ((StringBuilder)object2).append(this.getPackageName());
            ((StringBuilder)object2).append(" for transport ");
            ((StringBuilder)object2).append(AccessNetworkConstants.transportTypeToString((int)this.mTransportType));
            this.logd(((StringBuilder)object2).toString());
            this.mServiceConnection = object2 = new NetworkServiceConnection();
            if (!this.mPhone.getContext().bindService((Intent)object, (ServiceConnection)this.mServiceConnection, 1)) {
                this.loge("Cannot bind to the data service.");
                return;
            }
            this.mTargetBindingPackageName = charSequence;
        }
        catch (SecurityException securityException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("bindService failed ");
            ((StringBuilder)charSequence).append(securityException);
            this.loge(((StringBuilder)charSequence).toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private String getPackageName() {
        String string;
        int n = this.mTransportType;
        if (n != 1) {
            if (n != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Transport type not WWAN or WLAN. type=");
                stringBuilder.append(this.mTransportType);
                throw new IllegalStateException(stringBuilder.toString());
            }
            n = 17039788;
            string = "carrier_network_service_wlan_package_override_string";
        } else {
            n = 17039790;
            string = "carrier_network_service_wwan_package_override_string";
        }
        String string2 = this.mPhone.getContext().getResources().getString(n);
        PersistableBundle persistableBundle = this.mCarrierConfigManager.getConfigForSubId(this.mPhone.getSubId());
        String string3 = string2;
        if (persistableBundle == null) return string3;
        string3 = string2;
        if (TextUtils.isEmpty((CharSequence)persistableBundle.getString(string))) return string3;
        return persistableBundle.getString(string, string2);
    }

    private void logd(String string) {
        Rlog.d((String)this.mTag, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)this.mTag, (String)string);
    }

    public void handleMessage(Message message) {
        if (message.what != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unhandled event ");
            stringBuilder.append(message.what);
            this.loge(stringBuilder.toString());
        } else {
            this.bindService();
        }
    }

    public boolean isServiceConnected() {
        INetworkService iNetworkService = this.mINetworkService;
        boolean bl = iNetworkService != null && iNetworkService.asBinder().isBinderAlive();
        return bl;
    }

    public void registerForNetworkRegistrationInfoChanged(Handler handler, int n, Object object) {
        this.logd("registerForNetworkRegistrationInfoChanged");
        this.mRegStateChangeRegistrants.addUnique(handler, n, object);
    }

    public void requestNetworkRegistrationInfo(int n, Message message) {
        if (message == null) {
            return;
        }
        if (!this.isServiceConnected()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service not connected. Domain = ");
            String string = n == 1 ? "CS" : "PS";
            stringBuilder.append(string);
            this.loge(stringBuilder.toString());
            message.obj = new AsyncResult(message.obj, null, (Throwable)new IllegalStateException("Service not connected."));
            message.sendToTarget();
            return;
        }
        NetworkRegStateCallback networkRegStateCallback = new NetworkRegStateCallback();
        try {
            this.mCallbackTable.put(networkRegStateCallback, message);
            this.mINetworkService.requestNetworkRegistrationInfo(this.mPhone.getPhoneId(), n, (INetworkServiceCallback)networkRegStateCallback);
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("requestNetworkRegistrationInfo RemoteException ");
            stringBuilder.append((Object)remoteException);
            this.loge(stringBuilder.toString());
            this.mCallbackTable.remove((Object)networkRegStateCallback);
            message.obj = new AsyncResult(message.obj, null, (Throwable)remoteException);
            message.sendToTarget();
        }
    }

    public void unregisterForNetworkRegistrationInfoChanged(Handler handler) {
        this.mRegStateChangeRegistrants.remove(handler);
    }

    private class NetworkRegStateCallback
    extends INetworkServiceCallback.Stub {
        private NetworkRegStateCallback() {
        }

        public void onNetworkStateChanged() {
            NetworkRegistrationManager.this.logd("onNetworkStateChanged");
            NetworkRegistrationManager.this.mRegStateChangeRegistrants.notifyRegistrants();
        }

        public void onRequestNetworkRegistrationInfoComplete(int n, NetworkRegistrationInfo networkRegistrationInfo) {
            NetworkRegistrationManager networkRegistrationManager = NetworkRegistrationManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onRequestNetworkRegistrationInfoComplete result: ");
            stringBuilder.append(n);
            stringBuilder.append(", info: ");
            stringBuilder.append((Object)networkRegistrationInfo);
            networkRegistrationManager.logd(stringBuilder.toString());
            stringBuilder = (Message)NetworkRegistrationManager.this.mCallbackTable.remove((Object)this);
            if (stringBuilder != null) {
                ((Message)stringBuilder).arg1 = n;
                ((Message)stringBuilder).obj = new AsyncResult(((Message)stringBuilder).obj, (Object)new NetworkRegistrationInfo(networkRegistrationInfo), null);
                stringBuilder.sendToTarget();
            } else {
                NetworkRegistrationManager.this.loge("onCompleteMessage is null");
            }
        }
    }

    private class NetworkServiceConnection
    implements ServiceConnection {
        private NetworkServiceConnection() {
        }

        public void onServiceConnected(ComponentName object, IBinder object2) {
            Object object3 = NetworkRegistrationManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service ");
            stringBuilder.append(object);
            stringBuilder.append(" for transport ");
            stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)NetworkRegistrationManager.this.mTransportType));
            stringBuilder.append(" is now connected.");
            ((NetworkRegistrationManager)((Object)object3)).logd(stringBuilder.toString());
            NetworkRegistrationManager.this.mINetworkService = INetworkService.Stub.asInterface((IBinder)object2);
            object3 = NetworkRegistrationManager.this;
            ((NetworkRegistrationManager)((Object)object3)).mDeathRecipient = (NetworkRegistrationManager)((Object)object3).new RegManagerDeathRecipient((ComponentName)object);
            try {
                object2.linkToDeath((IBinder.DeathRecipient)NetworkRegistrationManager.this.mDeathRecipient, 0);
                NetworkRegistrationManager.this.mINetworkService.createNetworkServiceProvider(NetworkRegistrationManager.this.mPhone.getPhoneId());
                object2 = NetworkRegistrationManager.this.mINetworkService;
                int n = NetworkRegistrationManager.this.mPhone.getPhoneId();
                object = new NetworkRegStateCallback();
                object2.registerForNetworkRegistrationInfoChanged(n, (INetworkServiceCallback)object);
            }
            catch (RemoteException remoteException) {
                NetworkRegistrationManager.this.mDeathRecipient.binderDied();
                object2 = NetworkRegistrationManager.this;
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("RemoteException ");
                ((StringBuilder)object3).append((Object)remoteException);
                ((NetworkRegistrationManager)((Object)object2)).logd(((StringBuilder)object3).toString());
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            NetworkRegistrationManager networkRegistrationManager = NetworkRegistrationManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("service ");
            stringBuilder.append((Object)componentName);
            stringBuilder.append(" for transport ");
            stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)NetworkRegistrationManager.this.mTransportType));
            stringBuilder.append(" is now disconnected.");
            networkRegistrationManager.logd(stringBuilder.toString());
            NetworkRegistrationManager.this.mTargetBindingPackageName = null;
            if (NetworkRegistrationManager.this.mINetworkService != null) {
                NetworkRegistrationManager.this.mINetworkService.asBinder().unlinkToDeath((IBinder.DeathRecipient)NetworkRegistrationManager.this.mDeathRecipient, 0);
            }
        }
    }

    private class RegManagerDeathRecipient
    implements IBinder.DeathRecipient {
        private final ComponentName mComponentName;

        RegManagerDeathRecipient(ComponentName componentName) {
            this.mComponentName = componentName;
        }

        public void binderDied() {
            NetworkRegistrationManager networkRegistrationManager = NetworkRegistrationManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NetworkService(");
            stringBuilder.append((Object)this.mComponentName);
            stringBuilder.append(" transport type ");
            stringBuilder.append(NetworkRegistrationManager.this.mTransportType);
            stringBuilder.append(") died.");
            networkRegistrationManager.logd(stringBuilder.toString());
        }
    }

}

