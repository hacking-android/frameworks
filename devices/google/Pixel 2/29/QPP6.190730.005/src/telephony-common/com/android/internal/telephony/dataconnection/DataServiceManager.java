/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.content.BroadcastReceiver
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.ServiceConnection
 *  android.content.pm.IPackageManager
 *  android.content.pm.IPackageManager$Stub
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.content.res.Resources
 *  android.net.LinkProperties
 *  android.os.AsyncResult
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Message
 *  android.os.Parcelable
 *  android.os.PersistableBundle
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.os.UserHandle
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.data.DataCallResponse
 *  android.telephony.data.DataProfile
 *  android.telephony.data.IDataService
 *  android.telephony.data.IDataService$Stub
 *  android.telephony.data.IDataServiceCallback
 *  android.telephony.data.IDataServiceCallback$Stub
 *  android.text.TextUtils
 */
package com.android.internal.telephony.dataconnection;

import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.res.Resources;
import android.net.LinkProperties;
import android.os.AsyncResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.telephony.AccessNetworkConstants;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.data.DataCallResponse;
import android.telephony.data.DataProfile;
import android.telephony.data.IDataService;
import android.telephony.data.IDataServiceCallback;
import android.text.TextUtils;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.TransportManager;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DataServiceManager
extends Handler {
    static final String DATA_CALL_RESPONSE = "data_call_response";
    private static final boolean DBG = true;
    private static final int EVENT_BIND_DATA_SERVICE = 1;
    private final AppOpsManager mAppOps;
    private boolean mBound;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(intent.getAction()) && DataServiceManager.this.mPhone.getPhoneId() == intent.getIntExtra("android.telephony.extra.SLOT_INDEX", 0)) {
                DataServiceManager.this.log("Carrier config changed. Try to bind data service.");
                DataServiceManager.this.sendEmptyMessage(1);
            }
        }
    };
    private final CarrierConfigManager mCarrierConfigManager;
    private final RegistrantList mDataCallListChangedRegistrants = new RegistrantList();
    private DataServiceManagerDeathRecipient mDeathRecipient;
    private IDataService mIDataService;
    private final Map<IBinder, Message> mMessageMap = new ConcurrentHashMap<IBinder, Message>();
    private final IPackageManager mPackageManager;
    private final Phone mPhone;
    private final RegistrantList mServiceBindingChangedRegistrants = new RegistrantList();
    private CellularDataServiceConnection mServiceConnection;
    private final String mTag;
    private String mTargetBindingPackageName;
    private final int mTransportType;

    public DataServiceManager(Phone phone, int n, String string) {
        this.mPhone = phone;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DSM");
        stringBuilder.append(string);
        this.mTag = stringBuilder.toString();
        this.mTransportType = n;
        this.mBound = false;
        this.mCarrierConfigManager = (CarrierConfigManager)phone.getContext().getSystemService("carrier_config");
        this.mPackageManager = IPackageManager.Stub.asInterface((IBinder)ServiceManager.getService((String)"package"));
        this.mAppOps = (AppOpsManager)phone.getContext().getSystemService("appops");
        string = new IntentFilter();
        string.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        phone.getContext().registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, (IntentFilter)string, null, null);
        this.sendEmptyMessage(1);
    }

    private void bindDataService() {
        StringBuilder stringBuilder;
        String string = this.getDataServicePackageName();
        if (TextUtils.isEmpty((CharSequence)string)) {
            this.loge("Can't find the binding package");
            return;
        }
        if (TextUtils.equals((CharSequence)string, (CharSequence)this.mTargetBindingPackageName)) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Service ");
            stringBuilder2.append(string);
            stringBuilder2.append(" already bound or being bound.");
            this.log(stringBuilder2.toString());
            return;
        }
        this.revokePermissionsFromUnusedDataServices();
        Object object = this.mIDataService;
        if (object != null && object.asBinder().isBinderAlive()) {
            try {
                this.mIDataService.removeDataServiceProvider(this.mPhone.getPhoneId());
            }
            catch (RemoteException remoteException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot remove data service provider. ");
                stringBuilder.append((Object)remoteException);
                this.loge(stringBuilder.toString());
            }
            this.mPhone.getContext().unbindService((ServiceConnection)this.mServiceConnection);
        }
        this.grantPermissionsToService(string);
        try {
            object = new CellularDataServiceConnection();
            this.mServiceConnection = object;
            object = this.mPhone.getContext();
            stringBuilder = new Intent("android.telephony.data.DataService");
            if (!object.bindService(stringBuilder.setPackage(string), (ServiceConnection)this.mServiceConnection, 1)) {
                this.loge("Cannot bind to the data service.");
                return;
            }
            this.mTargetBindingPackageName = string;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot bind to the data service. Exception: ");
            ((StringBuilder)object).append(exception);
            this.loge(((StringBuilder)object).toString());
        }
    }

    private Set<String> getAllDataServicePackageNames() {
        Object object = this.mPhone.getContext().getPackageManager().queryIntentServices(new Intent("android.telephony.data.DataService"), 1048576);
        HashSet<String> hashSet = new HashSet<String>();
        object = object.iterator();
        while (object.hasNext()) {
            ResolveInfo resolveInfo = (ResolveInfo)object.next();
            if (resolveInfo.serviceInfo == null) continue;
            hashSet.add(resolveInfo.serviceInfo.packageName);
        }
        return hashSet;
    }

    private String getDataServicePackageName() {
        return this.getDataServicePackageName(this.mTransportType);
    }

    /*
     * Enabled aggressive block sorting
     */
    private String getDataServicePackageName(int n) {
        String string;
        if (n != 1) {
            if (n != 2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Transport type not WWAN or WLAN. type=");
                stringBuilder.append(AccessNetworkConstants.transportTypeToString((int)this.mTransportType));
                throw new IllegalStateException(stringBuilder.toString());
            }
            n = 17039787;
            string = "carrier_data_service_wlan_package_override_string";
        } else {
            n = 17039789;
            string = "carrier_data_service_wwan_package_override_string";
        }
        String string2 = this.mPhone.getContext().getResources().getString(n);
        PersistableBundle persistableBundle = this.mCarrierConfigManager.getConfigForSubId(this.mPhone.getSubId());
        String string3 = string2;
        if (persistableBundle == null) return string3;
        string3 = string2;
        if (TextUtils.isEmpty((CharSequence)persistableBundle.getString(string))) return string3;
        return persistableBundle.getString(string, string2);
    }

    private void grantPermissionsToService(String string) {
        String[] arrstring = new String[]{string};
        try {
            this.mPackageManager.grantDefaultPermissionsToEnabledTelephonyDataServices(arrstring, this.mPhone.getContext().getUserId());
            this.mAppOps.setMode(75, this.mPhone.getContext().getUserId(), arrstring[0], 0);
            return;
        }
        catch (RemoteException remoteException) {
            this.loge("Binder to package manager died, permission grant for DataService failed.");
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    private void log(String string) {
        Rlog.d((String)this.mTag, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)this.mTag, (String)string);
    }

    private void revokePermissionsFromUnusedDataServices() {
        Object object = this.getAllDataServicePackageNames();
        Object object2 = this.mPhone.getTransportManager().getAvailableTransports();
        int n = ((int[])object2).length;
        for (int i = 0; i < n; ++i) {
            object.remove(this.getDataServicePackageName(object2[i]));
        }
        try {
            object2 = new String[object.size()];
            object.toArray((T[])object2);
            this.mPackageManager.revokeDefaultPermissionsFromDisabledTelephonyDataServices((String[])object2, this.mPhone.getContext().getUserId());
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (String)object.next();
                this.mAppOps.setMode(75, this.mPhone.getContext().getUserId(), (String)object2, 2);
            }
            return;
        }
        catch (RemoteException remoteException) {
            this.loge("Binder to package manager died; failed to revoke DataService permissions.");
            throw remoteException.rethrowAsRuntimeException();
        }
    }

    private void sendCompleteMessage(Message message, int n) {
        if (message != null) {
            message.arg1 = n;
            message.sendToTarget();
        }
    }

    public void deactivateDataCall(int n, int n2, Message message) {
        this.log("deactivateDataCall");
        if (!this.mBound) {
            this.loge("Data service not bound.");
            this.sendCompleteMessage(message, 4);
            return;
        }
        CellularDataServiceCallback cellularDataServiceCallback = new CellularDataServiceCallback();
        if (message != null) {
            this.mMessageMap.put(cellularDataServiceCallback.asBinder(), message);
        }
        try {
            this.mIDataService.deactivateDataCall(this.mPhone.getPhoneId(), n, n2, (IDataServiceCallback)cellularDataServiceCallback);
        }
        catch (RemoteException remoteException) {
            this.loge("Cannot invoke deactivateDataCall on data service.");
            this.mMessageMap.remove((Object)cellularDataServiceCallback.asBinder());
            this.sendCompleteMessage(message, 4);
        }
    }

    public int getTransportType() {
        return this.mTransportType;
    }

    public void handleMessage(Message message) {
        if (message.what != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unhandled event ");
            stringBuilder.append(message.what);
            this.loge(stringBuilder.toString());
        } else {
            this.bindDataService();
        }
    }

    public void registerForDataCallListChanged(Handler handler, int n) {
        if (handler != null) {
            this.mDataCallListChangedRegistrants.addUnique(handler, n, null);
        }
    }

    public void registerForServiceBindingChanged(Handler handler, int n, Object object) {
        if (handler != null) {
            this.mServiceBindingChangedRegistrants.addUnique(handler, n, object);
        }
    }

    public void requestDataCallList(Message message) {
        this.log("requestDataCallList");
        if (!this.mBound) {
            this.loge("Data service not bound.");
            this.sendCompleteMessage(message, 4);
            return;
        }
        CellularDataServiceCallback cellularDataServiceCallback = new CellularDataServiceCallback();
        if (message != null) {
            this.mMessageMap.put(cellularDataServiceCallback.asBinder(), message);
        }
        try {
            this.mIDataService.requestDataCallList(this.mPhone.getPhoneId(), (IDataServiceCallback)cellularDataServiceCallback);
        }
        catch (RemoteException remoteException) {
            this.loge("Cannot invoke requestDataCallList on data service.");
            this.mMessageMap.remove((Object)cellularDataServiceCallback.asBinder());
            this.sendCompleteMessage(message, 4);
        }
    }

    public void setDataProfile(List<DataProfile> list, boolean bl, Message message) {
        this.log("setDataProfile");
        if (!this.mBound) {
            this.loge("Data service not bound.");
            this.sendCompleteMessage(message, 4);
            return;
        }
        CellularDataServiceCallback cellularDataServiceCallback = new CellularDataServiceCallback();
        if (message != null) {
            this.mMessageMap.put(cellularDataServiceCallback.asBinder(), message);
        }
        try {
            this.mIDataService.setDataProfile(this.mPhone.getPhoneId(), list, bl, (IDataServiceCallback)cellularDataServiceCallback);
        }
        catch (RemoteException remoteException) {
            this.loge("Cannot invoke setDataProfile on data service.");
            this.mMessageMap.remove((Object)cellularDataServiceCallback.asBinder());
            this.sendCompleteMessage(message, 4);
        }
    }

    public void setInitialAttachApn(DataProfile dataProfile, boolean bl, Message message) {
        this.log("setInitialAttachApn");
        if (!this.mBound) {
            this.loge("Data service not bound.");
            this.sendCompleteMessage(message, 4);
            return;
        }
        CellularDataServiceCallback cellularDataServiceCallback = new CellularDataServiceCallback();
        if (message != null) {
            this.mMessageMap.put(cellularDataServiceCallback.asBinder(), message);
        }
        try {
            this.mIDataService.setInitialAttachApn(this.mPhone.getPhoneId(), dataProfile, bl, (IDataServiceCallback)cellularDataServiceCallback);
        }
        catch (RemoteException remoteException) {
            this.loge("Cannot invoke setInitialAttachApn on data service.");
            this.mMessageMap.remove((Object)cellularDataServiceCallback.asBinder());
            this.sendCompleteMessage(message, 4);
        }
    }

    public void setupDataCall(int n, DataProfile dataProfile, boolean bl, boolean bl2, int n2, LinkProperties linkProperties, Message message) {
        this.log("setupDataCall");
        if (!this.mBound) {
            this.loge("Data service not bound.");
            this.sendCompleteMessage(message, 4);
            return;
        }
        CellularDataServiceCallback cellularDataServiceCallback = new CellularDataServiceCallback();
        if (message != null) {
            this.mMessageMap.put(cellularDataServiceCallback.asBinder(), message);
        }
        try {
            this.mIDataService.setupDataCall(this.mPhone.getPhoneId(), n, dataProfile, bl, bl2, n2, linkProperties, (IDataServiceCallback)cellularDataServiceCallback);
        }
        catch (RemoteException remoteException) {
            this.loge("Cannot invoke setupDataCall on data service.");
            this.mMessageMap.remove((Object)cellularDataServiceCallback.asBinder());
            this.sendCompleteMessage(message, 4);
        }
    }

    public void unregisterForDataCallListChanged(Handler handler) {
        if (handler != null) {
            this.mDataCallListChangedRegistrants.remove(handler);
        }
    }

    public void unregisterForServiceBindingChanged(Handler handler) {
        if (handler != null) {
            this.mServiceBindingChangedRegistrants.remove(handler);
        }
    }

    private final class CellularDataServiceCallback
    extends IDataServiceCallback.Stub {
        private CellularDataServiceCallback() {
        }

        public void onDataCallListChanged(List<DataCallResponse> list) {
            DataServiceManager.this.mDataCallListChangedRegistrants.notifyRegistrants(new AsyncResult(null, list, null));
        }

        public void onDeactivateDataCallComplete(int n) {
            DataServiceManager dataServiceManager = DataServiceManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onDeactivateDataCallComplete. resultCode = ");
            stringBuilder.append(n);
            dataServiceManager.log(stringBuilder.toString());
            dataServiceManager = (Message)DataServiceManager.this.mMessageMap.remove((Object)this.asBinder());
            DataServiceManager.this.sendCompleteMessage((Message)dataServiceManager, n);
        }

        public void onRequestDataCallListComplete(int n, List<DataCallResponse> message) {
            message = DataServiceManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onRequestDataCallListComplete. resultCode = ");
            stringBuilder.append(n);
            message.log(stringBuilder.toString());
            message = (Message)DataServiceManager.this.mMessageMap.remove((Object)this.asBinder());
            DataServiceManager.this.sendCompleteMessage(message, n);
        }

        public void onSetDataProfileComplete(int n) {
            DataServiceManager dataServiceManager = DataServiceManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSetDataProfileComplete. resultCode = ");
            stringBuilder.append(n);
            dataServiceManager.log(stringBuilder.toString());
            dataServiceManager = (Message)DataServiceManager.this.mMessageMap.remove((Object)this.asBinder());
            DataServiceManager.this.sendCompleteMessage((Message)dataServiceManager, n);
        }

        public void onSetInitialAttachApnComplete(int n) {
            DataServiceManager dataServiceManager = DataServiceManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSetInitialAttachApnComplete. resultCode = ");
            stringBuilder.append(n);
            dataServiceManager.log(stringBuilder.toString());
            dataServiceManager = (Message)DataServiceManager.this.mMessageMap.remove((Object)this.asBinder());
            DataServiceManager.this.sendCompleteMessage((Message)dataServiceManager, n);
        }

        public void onSetupDataCallComplete(int n, DataCallResponse dataCallResponse) {
            DataServiceManager dataServiceManager = DataServiceManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onSetupDataCallComplete. resultCode = ");
            stringBuilder.append(n);
            stringBuilder.append(", response = ");
            stringBuilder.append((Object)dataCallResponse);
            dataServiceManager.log(stringBuilder.toString());
            stringBuilder = (Message)DataServiceManager.this.mMessageMap.remove((Object)this.asBinder());
            if (stringBuilder != null) {
                stringBuilder.getData().putParcelable(DataServiceManager.DATA_CALL_RESPONSE, (Parcelable)dataCallResponse);
                DataServiceManager.this.sendCompleteMessage((Message)stringBuilder, n);
            } else {
                DataServiceManager.this.loge("Unable to find the message for setup call response.");
            }
        }
    }

    private final class CellularDataServiceConnection
    implements ServiceConnection {
        private CellularDataServiceConnection() {
        }

        public void onServiceConnected(ComponentName object, IBinder object2) {
            DataServiceManager.this.log("onServiceConnected");
            DataServiceManager.this.mIDataService = IDataService.Stub.asInterface((IBinder)object2);
            object = DataServiceManager.this;
            ((DataServiceManager)((Object)object)).mDeathRecipient = (DataServiceManager)((Object)object).new DataServiceManagerDeathRecipient();
            DataServiceManager.this.mBound = true;
            try {
                object2.linkToDeath((IBinder.DeathRecipient)DataServiceManager.this.mDeathRecipient, 0);
                DataServiceManager.this.mIDataService.createDataServiceProvider(DataServiceManager.this.mPhone.getPhoneId());
                object2 = DataServiceManager.this.mIDataService;
                int n = DataServiceManager.this.mPhone.getPhoneId();
                object = new CellularDataServiceCallback();
                object2.registerForDataCallListChanged(n, (IDataServiceCallback)object);
            }
            catch (RemoteException remoteException) {
                DataServiceManager.this.mDeathRecipient.binderDied();
                object2 = DataServiceManager.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Remote exception. ");
                stringBuilder.append((Object)remoteException);
                ((DataServiceManager)((Object)object2)).loge(stringBuilder.toString());
                return;
            }
            DataServiceManager.this.mServiceBindingChangedRegistrants.notifyResult((Object)true);
        }

        public void onServiceDisconnected(ComponentName componentName) {
            DataServiceManager.this.log("onServiceDisconnected");
            DataServiceManager.this.mIDataService.asBinder().unlinkToDeath((IBinder.DeathRecipient)DataServiceManager.this.mDeathRecipient, 0);
            DataServiceManager.this.mIDataService = null;
            DataServiceManager.this.mBound = false;
            DataServiceManager.this.mServiceBindingChangedRegistrants.notifyResult((Object)false);
            DataServiceManager.this.mTargetBindingPackageName = null;
        }
    }

    private class DataServiceManagerDeathRecipient
    implements IBinder.DeathRecipient {
        private DataServiceManagerDeathRecipient() {
        }

        public void binderDied() {
            DataServiceManager dataServiceManager = DataServiceManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("DataService ");
            stringBuilder.append(DataServiceManager.this.mTargetBindingPackageName);
            stringBuilder.append(", transport type ");
            stringBuilder.append(DataServiceManager.this.mTransportType);
            stringBuilder.append(" died.");
            dataServiceManager.loge(stringBuilder.toString());
        }
    }

}

