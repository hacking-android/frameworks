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
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.os.UserHandle
 *  android.telephony.AccessNetworkConstants
 *  android.telephony.AccessNetworkConstants$AccessNetworkType
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.data.ApnSetting
 *  android.telephony.data.IQualifiedNetworksService
 *  android.telephony.data.IQualifiedNetworksService$Stub
 *  android.telephony.data.IQualifiedNetworksServiceCallback
 *  android.telephony.data.IQualifiedNetworksServiceCallback$Stub
 *  android.text.TextUtils
 *  android.util.SparseArray
 *  com.android.internal.telephony.dataconnection.-$
 *  com.android.internal.telephony.dataconnection.-$$Lambda
 *  com.android.internal.telephony.dataconnection.-$$Lambda$AccessNetworksManager
 *  com.android.internal.telephony.dataconnection.-$$Lambda$AccessNetworksManager$QualifiedNetworks
 *  com.android.internal.telephony.dataconnection.-$$Lambda$AccessNetworksManager$QualifiedNetworks$RFnLI6POkxFwKMiSsed1qg8X7t0
 *  com.android.internal.telephony.dataconnection.-$$Lambda$AccessNetworksManager$QualifiedNetworksServiceCallback
 *  com.android.internal.telephony.dataconnection.-$$Lambda$AccessNetworksManager$QualifiedNetworksServiceCallback$ZAur6rkPXYVsjcy4S2I6rXzX3DM
 *  com.android.internal.telephony.dataconnection.-$$Lambda$AccessNetworksManager$Su9aGPx8cN_dALH_BE7MctE6qX8
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony.dataconnection;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.os.UserHandle;
import android.telephony.AccessNetworkConstants;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.data.ApnSetting;
import android.telephony.data.IQualifiedNetworksService;
import android.telephony.data.IQualifiedNetworksServiceCallback;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.dataconnection.-$;
import com.android.internal.telephony.dataconnection._$$Lambda$AccessNetworksManager$QualifiedNetworks$RFnLI6POkxFwKMiSsed1qg8X7t0;
import com.android.internal.telephony.dataconnection._$$Lambda$AccessNetworksManager$QualifiedNetworksServiceCallback$ZAur6rkPXYVsjcy4S2I6rXzX3DM;
import com.android.internal.telephony.dataconnection._$$Lambda$AccessNetworksManager$Su9aGPx8cN_dALH_BE7MctE6qX8;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccessNetworksManager
extends Handler {
    private static final boolean DBG = false;
    private static final int EVENT_BIND_QUALIFIED_NETWORKS_SERVICE = 1;
    private static final int[] SUPPORTED_APN_TYPES;
    private static final String TAG;
    private final SparseArray<int[]> mAvailableNetworks = new SparseArray();
    private final CarrierConfigManager mCarrierConfigManager;
    private final BroadcastReceiver mConfigChangedReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(intent.getAction()) && AccessNetworksManager.this.mPhone.getPhoneId() == intent.getIntExtra("android.telephony.extra.SLOT_INDEX", 0)) {
                AccessNetworksManager.this.sendEmptyMessage(1);
            }
        }
    };
    private AccessNetworksManagerDeathRecipient mDeathRecipient;
    private IQualifiedNetworksService mIQualifiedNetworksService;
    private final Phone mPhone;
    private final RegistrantList mQualifiedNetworksChangedRegistrants = new RegistrantList();
    private QualifiedNetworksServiceConnection mServiceConnection;
    private String mTargetBindingPackageName;

    static {
        TAG = AccessNetworksManager.class.getSimpleName();
        SUPPORTED_APN_TYPES = new int[]{17, 2, 32, 64, 128, 4, 512};
    }

    public AccessNetworksManager(Phone phone) {
        this.mPhone = phone;
        this.mCarrierConfigManager = (CarrierConfigManager)phone.getContext().getSystemService("carrier_config");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        phone.getContext().registerReceiverAsUser(this.mConfigChangedReceiver, UserHandle.ALL, intentFilter, null, null);
        this.sendEmptyMessage(1);
    }

    private void bindQualifiedNetworksService() {
        StringBuilder stringBuilder;
        CharSequence charSequence = this.getQualifiedNetworksServicePackageName();
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            this.loge("Can't find the binding package");
            return;
        }
        if (TextUtils.equals((CharSequence)charSequence, (CharSequence)this.mTargetBindingPackageName)) {
            return;
        }
        Object object = this.mIQualifiedNetworksService;
        if (object != null && object.asBinder().isBinderAlive()) {
            try {
                this.mIQualifiedNetworksService.removeNetworkAvailabilityProvider(this.mPhone.getPhoneId());
            }
            catch (RemoteException remoteException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot remove network availability updater. ");
                stringBuilder.append((Object)remoteException);
                this.loge(stringBuilder.toString());
            }
            this.mPhone.getContext().unbindService((ServiceConnection)this.mServiceConnection);
        }
        try {
            object = new QualifiedNetworksServiceConnection();
            this.mServiceConnection = object;
            object = new StringBuilder();
            ((StringBuilder)object).append("bind to ");
            ((StringBuilder)object).append((String)charSequence);
            this.log(((StringBuilder)object).toString());
            stringBuilder = this.mPhone.getContext();
            object = new Intent("android.telephony.data.QualifiedNetworksService");
            if (!stringBuilder.bindService(object.setPackage((String)charSequence), (ServiceConnection)this.mServiceConnection, 1)) {
                this.loge("Cannot bind to the qualified networks service.");
                return;
            }
            this.mTargetBindingPackageName = charSequence;
        }
        catch (Exception exception) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Cannot bind to the qualified networks service. Exception: ");
            ((StringBuilder)charSequence).append(exception);
            this.loge(((StringBuilder)charSequence).toString());
        }
    }

    private List<QualifiedNetworks> getQualifiedNetworksList() {
        ArrayList<QualifiedNetworks> arrayList = new ArrayList<QualifiedNetworks>();
        for (int i = 0; i < this.mAvailableNetworks.size(); ++i) {
            arrayList.add(new QualifiedNetworks(this.mAvailableNetworks.keyAt(i), (int[])this.mAvailableNetworks.valueAt(i)));
        }
        return arrayList;
    }

    private String getQualifiedNetworksServicePackageName() {
        String string = this.mPhone.getContext().getResources().getString(17039765);
        Object object = this.mCarrierConfigManager.getConfigForSubId(this.mPhone.getSubId());
        String string2 = string;
        if (object != null) {
            object = object.getString("carrier_qualified_networks_service_package_override_string");
            string2 = string;
            if (!TextUtils.isEmpty((CharSequence)object)) {
                string2 = object;
            }
        }
        return string2;
    }

    static /* synthetic */ String lambda$dump$0(int n) {
        return AccessNetworkConstants.AccessNetworkType.toString((int)n);
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    public void dump(FileDescriptor object, IndentingPrintWriter indentingPrintWriter, String[] arrstring) {
        indentingPrintWriter.println("AccessNetworksManager:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println("Available networks:");
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < this.mAvailableNetworks.size(); ++i) {
            object = new StringBuilder();
            ((StringBuilder)object).append("APN type ");
            ((StringBuilder)object).append(ApnSetting.getApnTypeString((int)this.mAvailableNetworks.keyAt(i)));
            ((StringBuilder)object).append(": [");
            ((StringBuilder)object).append(Arrays.stream((int[])this.mAvailableNetworks.valueAt(i)).mapToObj(_$$Lambda$AccessNetworksManager$Su9aGPx8cN_dALH_BE7MctE6qX8.INSTANCE).collect(Collectors.joining(",")));
            ((StringBuilder)object).append("]");
            indentingPrintWriter.println(((StringBuilder)object).toString());
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public void handleMessage(Message message) {
        if (message.what != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unhandled event ");
            stringBuilder.append(message.what);
            this.loge(stringBuilder.toString());
        } else {
            this.bindQualifiedNetworksService();
        }
    }

    public void registerForQualifiedNetworksChanged(Handler handler, int n) {
        if (handler != null) {
            handler = new Registrant(handler, n, null);
            this.mQualifiedNetworksChangedRegistrants.add((Registrant)handler);
            if (this.mAvailableNetworks.size() != 0) {
                handler.notifyResult(this.getQualifiedNetworksList());
            }
        }
    }

    public void unregisterForQualifiedNetworksChanged(Handler handler) {
        if (handler != null) {
            this.mQualifiedNetworksChangedRegistrants.remove(handler);
        }
    }

    private class AccessNetworksManagerDeathRecipient
    implements IBinder.DeathRecipient {
        private AccessNetworksManagerDeathRecipient() {
        }

        public void binderDied() {
            AccessNetworksManager accessNetworksManager = AccessNetworksManager.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("QualifiedNetworksService(");
            stringBuilder.append(AccessNetworksManager.this.mTargetBindingPackageName);
            stringBuilder.append(") died.");
            accessNetworksManager.loge(stringBuilder.toString());
        }
    }

    public static class QualifiedNetworks {
        public final int apnType;
        public final int[] qualifiedNetworks;

        public QualifiedNetworks(int n, int[] arrn) {
            this.apnType = n;
            this.qualifiedNetworks = arrn;
        }

        static /* synthetic */ String lambda$toString$0(int n) {
            return AccessNetworkConstants.AccessNetworkType.toString((int)n);
        }

        public String toString() {
            ArrayList<String> arrayList = new ArrayList<String>();
            Object object = this.qualifiedNetworks;
            int n = ((int[])object).length;
            for (int i = 0; i < n; ++i) {
                arrayList.add(AccessNetworkConstants.AccessNetworkType.toString((int)object[i]));
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("[QualifiedNetworks: apnType=");
            ((StringBuilder)object).append(ApnSetting.getApnTypeString((int)this.apnType));
            ((StringBuilder)object).append(", networks=");
            ((StringBuilder)object).append(Arrays.stream(this.qualifiedNetworks).mapToObj(_$$Lambda$AccessNetworksManager$QualifiedNetworks$RFnLI6POkxFwKMiSsed1qg8X7t0.INSTANCE).collect(Collectors.joining(",")));
            ((StringBuilder)object).append("]");
            return ((StringBuilder)object).toString();
        }
    }

    private final class QualifiedNetworksServiceCallback
    extends IQualifiedNetworksServiceCallback.Stub {
        private QualifiedNetworksServiceCallback() {
        }

        static /* synthetic */ String lambda$onQualifiedNetworkTypesChanged$0(int n) {
            return AccessNetworkConstants.AccessNetworkType.toString((int)n);
        }

        public void onQualifiedNetworkTypesChanged(int n, int[] arrn) {
            int[] arrn2 = AccessNetworksManager.this;
            Serializable serializable = new StringBuilder();
            ((StringBuilder)serializable).append("onQualifiedNetworkTypesChanged. apnTypes = [");
            ((StringBuilder)serializable).append(ApnSetting.getApnTypesStringFromBitmask((int)n));
            ((StringBuilder)serializable).append("], networks = [");
            ((StringBuilder)serializable).append(Arrays.stream(arrn).mapToObj(_$$Lambda$AccessNetworksManager$QualifiedNetworksServiceCallback$ZAur6rkPXYVsjcy4S2I6rXzX3DM.INSTANCE).collect(Collectors.joining(",")));
            ((StringBuilder)serializable).append("]");
            ((AccessNetworksManager)arrn2).log(((StringBuilder)serializable).toString());
            serializable = new ArrayList();
            for (int n2 : SUPPORTED_APN_TYPES) {
                if ((n & n2) != n2) continue;
                if (AccessNetworksManager.this.mAvailableNetworks.get(n2) != null && Arrays.equals((int[])AccessNetworksManager.this.mAvailableNetworks.get(n2), arrn)) {
                    AccessNetworksManager accessNetworksManager = AccessNetworksManager.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Available networks for ");
                    stringBuilder.append(ApnSetting.getApnTypesStringFromBitmask((int)n2));
                    stringBuilder.append(" not changed.");
                    accessNetworksManager.log(stringBuilder.toString());
                    continue;
                }
                AccessNetworksManager.this.mAvailableNetworks.put(n2, (Object)arrn);
                serializable.add(new QualifiedNetworks(n2, arrn));
            }
            if (!serializable.isEmpty()) {
                AccessNetworksManager.this.mQualifiedNetworksChangedRegistrants.notifyResult((Object)serializable);
            }
        }
    }

    private final class QualifiedNetworksServiceConnection
    implements ServiceConnection {
        private QualifiedNetworksServiceConnection() {
        }

        public void onServiceConnected(ComponentName object, IBinder iBinder) {
            AccessNetworksManager.this.mIQualifiedNetworksService = IQualifiedNetworksService.Stub.asInterface((IBinder)iBinder);
            object = AccessNetworksManager.this;
            ((AccessNetworksManager)((Object)object)).mDeathRecipient = (AccessNetworksManager)((Object)object).new AccessNetworksManagerDeathRecipient();
            try {
                iBinder.linkToDeath((IBinder.DeathRecipient)AccessNetworksManager.this.mDeathRecipient, 0);
                iBinder = AccessNetworksManager.this.mIQualifiedNetworksService;
                int n = AccessNetworksManager.this.mPhone.getPhoneId();
                object = new QualifiedNetworksServiceCallback();
                iBinder.createNetworkAvailabilityProvider(n, (IQualifiedNetworksServiceCallback)object);
            }
            catch (RemoteException remoteException) {
                AccessNetworksManager.this.mDeathRecipient.binderDied();
                AccessNetworksManager accessNetworksManager = AccessNetworksManager.this;
                object = new StringBuilder();
                ((StringBuilder)object).append("Remote exception. ");
                ((StringBuilder)object).append((Object)remoteException);
                accessNetworksManager.loge(((StringBuilder)object).toString());
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            AccessNetworksManager.this.mIQualifiedNetworksService.asBinder().unlinkToDeath((IBinder.DeathRecipient)AccessNetworksManager.this.mDeathRecipient, 0);
            AccessNetworksManager.this.mTargetBindingPackageName = null;
        }
    }

}

