/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Message
 *  android.os.RemoteException
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsReasonInfo
 *  android.telephony.ims.feature.CapabilityChangeRequest
 *  android.telephony.ims.feature.CapabilityChangeRequest$CapabilityPair
 *  android.telephony.ims.feature.ImsFeature
 *  android.telephony.ims.feature.ImsFeature$CapabilityCallbackProxy
 *  android.telephony.ims.feature.MmTelFeature
 *  android.telephony.ims.feature.MmTelFeature$MmTelCapabilities
 *  android.util.Log
 *  com.android.ims.ImsConfigListener
 *  com.android.ims.ImsConfigListener$Stub
 *  com.android.ims.internal.IImsCallSession
 *  com.android.ims.internal.IImsConfig
 *  com.android.ims.internal.IImsEcbm
 *  com.android.ims.internal.IImsMultiEndpoint
 *  com.android.ims.internal.IImsRegistrationListener
 *  com.android.ims.internal.IImsRegistrationListener$Stub
 *  com.android.ims.internal.IImsUt
 */
package com.android.internal.telephony.ims;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.feature.CapabilityChangeRequest;
import android.telephony.ims.feature.ImsFeature;
import android.telephony.ims.feature.MmTelFeature;
import android.util.Log;
import com.android.ims.ImsConfigListener;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsUt;
import com.android.internal.telephony.ims.ImsRegistrationCompatAdapter;
import com.android.internal.telephony.ims.MmTelInterfaceAdapter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MmTelFeatureCompatAdapter
extends MmTelFeature {
    public static final String ACTION_IMS_INCOMING_CALL = "com.android.ims.IMS_INCOMING_CALL";
    public static final int FEATURE_DISABLED = 0;
    public static final int FEATURE_ENABLED = 1;
    public static final int FEATURE_TYPE_UNKNOWN = -1;
    public static final int FEATURE_TYPE_UT_OVER_LTE = 4;
    public static final int FEATURE_TYPE_UT_OVER_WIFI = 5;
    public static final int FEATURE_TYPE_VIDEO_OVER_LTE = 1;
    public static final int FEATURE_TYPE_VIDEO_OVER_WIFI = 3;
    public static final int FEATURE_TYPE_VOICE_OVER_LTE = 0;
    public static final int FEATURE_TYPE_VOICE_OVER_WIFI = 2;
    public static final int FEATURE_UNKNOWN = -1;
    private static final Map<Integer, Integer> REG_TECH_TO_NET_TYPE = new HashMap<Integer, Integer>(2);
    private static final String TAG = "MmTelFeatureCompat";
    private static final int WAIT_TIMEOUT_MS = 2000;
    private final MmTelInterfaceAdapter mCompatFeature;
    private final IImsRegistrationListener mListener = new IImsRegistrationListener.Stub(){

        public void registrationAssociatedUriChanged(Uri[] arruri) throws RemoteException {
        }

        public void registrationChangeFailed(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        public void registrationConnected() throws RemoteException {
        }

        public void registrationConnectedWithRadioTech(int n) throws RemoteException {
        }

        public void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException {
            Log.i((String)MmTelFeatureCompatAdapter.TAG, (String)"registrationDisconnected: resetting MMTEL capabilities.");
            MmTelFeatureCompatAdapter.this.notifyCapabilitiesStatusChanged(new MmTelFeature.MmTelCapabilities());
        }

        public void registrationFeatureCapabilityChanged(int n, int[] arrn, int[] object) throws RemoteException {
            object = MmTelFeatureCompatAdapter.this;
            object.notifyCapabilitiesStatusChanged(((MmTelFeatureCompatAdapter)((Object)object)).convertCapabilities(arrn));
        }

        public void registrationProgressing() throws RemoteException {
        }

        public void registrationProgressingWithRadioTech(int n) throws RemoteException {
        }

        public void registrationResumed() throws RemoteException {
        }

        public void registrationServiceCapabilityChanged(int n, int n2) throws RemoteException {
        }

        public void registrationSuspended() throws RemoteException {
        }

        public void voiceMessageCountUpdate(int n) throws RemoteException {
            MmTelFeatureCompatAdapter.this.notifyVoiceMessageCountUpdate(n);
        }
    };
    private BroadcastReceiver mReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            Log.i((String)MmTelFeatureCompatAdapter.TAG, (String)"onReceive");
            if (intent.getAction().equals(MmTelFeatureCompatAdapter.ACTION_IMS_INCOMING_CALL)) {
                Log.i((String)MmTelFeatureCompatAdapter.TAG, (String)"onReceive : incoming call intent.");
                object = intent.getStringExtra("android:imsCallID");
                try {
                    object = MmTelFeatureCompatAdapter.this.mCompatFeature.getPendingCallSession(MmTelFeatureCompatAdapter.this.mSessionId, (String)object);
                    MmTelFeatureCompatAdapter.this.notifyIncomingCallSession((IImsCallSession)object, intent.getExtras());
                }
                catch (RemoteException remoteException) {
                    Log.w((String)MmTelFeatureCompatAdapter.TAG, (String)"onReceive: Couldn't get Incoming call session.");
                }
            }
        }
    };
    private ImsRegistrationCompatAdapter mRegCompatAdapter;
    private int mSessionId = -1;

    static {
        REG_TECH_TO_NET_TYPE.put(0, 13);
        REG_TECH_TO_NET_TYPE.put(1, 18);
    }

    public MmTelFeatureCompatAdapter(Context context, int n, MmTelInterfaceAdapter mmTelInterfaceAdapter) {
        this.initialize(context, n);
        this.mCompatFeature = mmTelInterfaceAdapter;
    }

    private MmTelFeature.MmTelCapabilities convertCapabilities(int[] mmTelCapabilities) {
        Object object = new boolean[((int[])mmTelCapabilities).length];
        for (int i = 0; i <= 5 && i < ((int[])mmTelCapabilities).length; ++i) {
            if (mmTelCapabilities[i] == i) {
                object[i] = true;
                continue;
            }
            if (mmTelCapabilities[i] != -1) continue;
            object[i] = false;
        }
        mmTelCapabilities = new MmTelFeature.MmTelCapabilities();
        if (object[0] || object[2]) {
            mmTelCapabilities.addCapabilities(1);
        }
        if (object[1] || object[3]) {
            mmTelCapabilities.addCapabilities(2);
        }
        if (object[4] || object[5]) {
            mmTelCapabilities.addCapabilities(4);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("convertCapabilities - capabilities: ");
        ((StringBuilder)object).append((Object)mmTelCapabilities);
        Log.i((String)TAG, (String)((StringBuilder)object).toString());
        return mmTelCapabilities;
    }

    private int convertCapability(int n, int n2) {
        int n3;
        int n4 = -1;
        if (n2 == 0) {
            n3 = n != 1 ? (n != 2 ? (n != 4 ? n4 : 4) : 1) : 0;
        } else {
            n3 = n4;
            if (n2 == 1) {
                n3 = n != 1 ? (n != 2 ? (n != 4 ? n4 : 5) : 3) : 2;
            }
        }
        return n3;
    }

    private PendingIntent createIncomingCallPendingIntent() {
        Intent intent = new Intent(ACTION_IMS_INCOMING_CALL);
        intent.setPackage("com.android.phone");
        return PendingIntent.getBroadcast((Context)this.mContext, (int)0, (Intent)intent, (int)134217728);
    }

    public void addRegistrationAdapter(ImsRegistrationCompatAdapter imsRegistrationCompatAdapter) throws RemoteException {
        this.mRegCompatAdapter = imsRegistrationCompatAdapter;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void changeEnabledCapabilities(CapabilityChangeRequest object, final ImsFeature.CapabilityCallbackProxy capabilityCallbackProxy) {
        MmTelFeatureCompatAdapter mmTelFeatureCompatAdapter2 = this;
        if (object == null) {
            return;
        }
        try {
            Object object2;
            int n;
            int n2;
            IImsConfig iImsConfig = mmTelFeatureCompatAdapter2.mCompatFeature.getConfigInterface();
            Object object3 = object.getCapabilitiesToDisable().iterator();
            do {
                boolean bl = object3.hasNext();
                n2 = 1;
                if (!bl) break;
                object2 = (CapabilityChangeRequest.CapabilityPair)object3.next();
                CountDownLatch countDownLatch = new CountDownLatch(1);
                n = mmTelFeatureCompatAdapter2.convertCapability(object2.getCapability(), object2.getRadioTech());
                n2 = REG_TECH_TO_NET_TYPE.getOrDefault(object2.getRadioTech(), -1);
                Object object4 = new StringBuilder();
                object4.append("changeEnabledCapabilities - cap: ");
                object4.append(n);
                object4.append(" radioTech: ");
                object4.append(n2);
                object4.append(" disabled");
                Log.i((String)TAG, (String)object4.toString());
                object4 = new ConfigListener(n, n2, countDownLatch, (CapabilityChangeRequest.CapabilityPair)object2){
                    final /* synthetic */ CapabilityChangeRequest.CapabilityPair val$cap;
                    {
                        this.val$cap = capabilityPair;
                        super(n, n2, countDownLatch);
                    }

                    @Override
                    public void setFeatureValueReceived(int n) {
                        Object object;
                        if (n != 0) {
                            object = capabilityCallbackProxy;
                            if (object == null) {
                                return;
                            }
                            object.onChangeCapabilityConfigurationError(this.val$cap.getCapability(), this.val$cap.getRadioTech(), -1);
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("changeEnabledCapabilities - setFeatureValueReceived with value ");
                        ((StringBuilder)object).append(n);
                        Log.i((String)MmTelFeatureCompatAdapter.TAG, (String)((StringBuilder)object).toString());
                    }
                };
                iImsConfig.setFeatureValue(n, n2, 0, (ImsConfigListener)object4);
                countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
            } while (true);
            for (MmTelFeatureCompatAdapter mmTelFeatureCompatAdapter2 : object.getCapabilitiesToEnable()) {
                object3 = new CountDownLatch(n2);
                int n3 = this.convertCapability(mmTelFeatureCompatAdapter2.getCapability(), mmTelFeatureCompatAdapter2.getRadioTech());
                n = REG_TECH_TO_NET_TYPE.getOrDefault(mmTelFeatureCompatAdapter2.getRadioTech(), -1);
                object2 = new StringBuilder();
                object2.append("changeEnabledCapabilities - cap: ");
                object2.append(n3);
                object2.append(" radioTech: ");
                object2.append(n);
                object2.append(" enabled");
                Log.i((String)TAG, (String)object2.toString());
                object2 = new ConfigListener(n3, n, (CountDownLatch)object3, (CapabilityChangeRequest.CapabilityPair)mmTelFeatureCompatAdapter2){
                    final /* synthetic */ CapabilityChangeRequest.CapabilityPair val$cap;
                    {
                        this.val$cap = capabilityPair;
                        super(n, n2, countDownLatch);
                    }

                    @Override
                    public void setFeatureValueReceived(int n) {
                        Object object;
                        if (n != 1) {
                            object = capabilityCallbackProxy;
                            if (object == null) {
                                return;
                            }
                            object.onChangeCapabilityConfigurationError(this.val$cap.getCapability(), this.val$cap.getRadioTech(), -1);
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("changeEnabledCapabilities - setFeatureValueReceived with value ");
                        ((StringBuilder)object).append(n);
                        Log.i((String)MmTelFeatureCompatAdapter.TAG, (String)((StringBuilder)object).toString());
                    }
                };
                iImsConfig.setFeatureValue(n3, n, n2, (ImsConfigListener)object2);
                ((CountDownLatch)object3).await(2000L, TimeUnit.MILLISECONDS);
            }
            return;
        }
        catch (RemoteException | InterruptedException throwable) {
            object = new StringBuilder();
            ((StringBuilder)object).append("changeEnabledCapabilities: Error processing: ");
            ((StringBuilder)object).append(throwable.getMessage());
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
        }
    }

    public ImsCallProfile createCallProfile(int n, int n2) {
        try {
            ImsCallProfile imsCallProfile = this.mCompatFeature.createCallProfile(this.mSessionId, n, n2);
            return imsCallProfile;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.getMessage());
        }
    }

    public IImsCallSession createCallSessionInterface(ImsCallProfile imsCallProfile) throws RemoteException {
        return this.mCompatFeature.createCallSession(this.mSessionId, imsCallProfile);
    }

    public void disableIms() throws RemoteException {
        this.mCompatFeature.turnOffIms();
    }

    public void enableIms() throws RemoteException {
        this.mCompatFeature.turnOnIms();
    }

    public IImsEcbm getEcbmInterface() throws RemoteException {
        return this.mCompatFeature.getEcbmInterface();
    }

    public int getFeatureState() {
        try {
            int n = this.mCompatFeature.getFeatureState();
            return n;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.getMessage());
        }
    }

    public IImsMultiEndpoint getMultiEndpointInterface() throws RemoteException {
        return this.mCompatFeature.getMultiEndpointInterface();
    }

    public IImsConfig getOldConfigInterface() {
        try {
            IImsConfig iImsConfig = this.mCompatFeature.getConfigInterface();
            return iImsConfig;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getOldConfigInterface(): ");
            stringBuilder.append(remoteException.getMessage());
            Log.w((String)TAG, (String)stringBuilder.toString());
            return null;
        }
    }

    public IImsUt getUtInterface() throws RemoteException {
        return this.mCompatFeature.getUtInterface();
    }

    public void onFeatureReady() {
        Log.i((String)TAG, (String)"onFeatureReady called!");
        Object object = new IntentFilter(ACTION_IMS_INCOMING_CALL);
        this.mContext.registerReceiver(this.mReceiver, (IntentFilter)object);
        try {
            object = this.mCompatFeature;
            PendingIntent pendingIntent = this.createIncomingCallPendingIntent();
            ImsRegistrationListenerBase imsRegistrationListenerBase = new ImsRegistrationListenerBase();
            this.mSessionId = ((MmTelInterfaceAdapter)object).startSession(pendingIntent, (IImsRegistrationListener)imsRegistrationListenerBase);
            this.mCompatFeature.addRegistrationListener(this.mListener);
            this.mCompatFeature.addRegistrationListener(this.mRegCompatAdapter.getRegistrationListener());
        }
        catch (RemoteException remoteException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Couldn't start compat feature: ");
            ((StringBuilder)object).append(remoteException.getMessage());
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
        }
    }

    public void onFeatureRemoved() {
        this.mContext.unregisterReceiver(this.mReceiver);
        try {
            this.mCompatFeature.endSession(this.mSessionId);
            this.mCompatFeature.removeRegistrationListener(this.mListener);
            if (this.mRegCompatAdapter != null) {
                this.mCompatFeature.removeRegistrationListener(this.mRegCompatAdapter.getRegistrationListener());
            }
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onFeatureRemoved: Couldn't end session: ");
            stringBuilder.append(remoteException.getMessage());
            Log.w((String)TAG, (String)stringBuilder.toString());
        }
    }

    public boolean queryCapabilityConfiguration(int n, int n2) {
        n = this.convertCapability(n, n2);
        boolean bl = true;
        Object object = new CountDownLatch(1);
        final int[] arrn = new int[]{-1};
        n2 = REG_TECH_TO_NET_TYPE.getOrDefault(n2, -1);
        try {
            IImsConfig iImsConfig = this.mCompatFeature.getConfigInterface();
            ConfigListener configListener = new ConfigListener(n, n2, (CountDownLatch)object){

                @Override
                public void getFeatureValueReceived(int n) {
                    arrn[0] = n;
                }
            };
            iImsConfig.getFeatureValue(n, n2, (ImsConfigListener)configListener);
        }
        catch (RemoteException remoteException) {
            Log.w((String)TAG, (String)"queryCapabilityConfiguration");
        }
        try {
            ((CountDownLatch)object).await(2000L, TimeUnit.MILLISECONDS);
        }
        catch (InterruptedException interruptedException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("queryCapabilityConfiguration - error waiting: ");
            ((StringBuilder)object).append(interruptedException.getMessage());
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
        }
        if (arrn[0] != 1) {
            bl = false;
        }
        return bl;
    }

    public void setUiTtyMode(int n, Message message) {
        try {
            this.mCompatFeature.setUiTTYMode(n, message);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException.getMessage());
        }
    }

    private static class ConfigListener
    extends ImsConfigListener.Stub {
        private final int mCapability;
        private final CountDownLatch mLatch;
        private final int mTech;

        public ConfigListener(int n, int n2, CountDownLatch countDownLatch) {
            this.mCapability = n;
            this.mTech = n2;
            this.mLatch = countDownLatch;
        }

        public void getFeatureValueReceived(int n) {
        }

        public void onGetFeatureResponse(int n, int n2, int n3, int n4) throws RemoteException {
            if (n == this.mCapability && n2 == this.mTech) {
                this.mLatch.countDown();
                this.getFeatureValueReceived(n3);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onGetFeatureResponse: response different than requested: feature=");
                stringBuilder.append(n);
                stringBuilder.append(" and network=");
                stringBuilder.append(n2);
                Log.i((String)MmTelFeatureCompatAdapter.TAG, (String)stringBuilder.toString());
            }
        }

        public void onGetVideoQuality(int n, int n2) throws RemoteException {
        }

        public void onSetFeatureResponse(int n, int n2, int n3, int n4) throws RemoteException {
            if (n == this.mCapability && n2 == this.mTech) {
                this.mLatch.countDown();
                this.setFeatureValueReceived(n3);
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onSetFeatureResponse: response different than requested: feature=");
                stringBuilder.append(n);
                stringBuilder.append(" and network=");
                stringBuilder.append(n2);
                Log.i((String)MmTelFeatureCompatAdapter.TAG, (String)stringBuilder.toString());
            }
        }

        public void onSetVideoQuality(int n) throws RemoteException {
        }

        public void setFeatureValueReceived(int n) {
        }
    }

    private class ImsRegistrationListenerBase
    extends IImsRegistrationListener.Stub {
        private ImsRegistrationListenerBase() {
        }

        public void registrationAssociatedUriChanged(Uri[] arruri) throws RemoteException {
        }

        public void registrationChangeFailed(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        public void registrationConnected() throws RemoteException {
        }

        public void registrationConnectedWithRadioTech(int n) throws RemoteException {
        }

        public void registrationDisconnected(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        public void registrationFeatureCapabilityChanged(int n, int[] arrn, int[] arrn2) throws RemoteException {
        }

        public void registrationProgressing() throws RemoteException {
        }

        public void registrationProgressingWithRadioTech(int n) throws RemoteException {
        }

        public void registrationResumed() throws RemoteException {
        }

        public void registrationServiceCapabilityChanged(int n, int n2) throws RemoteException {
        }

        public void registrationSuspended() throws RemoteException {
        }

        public void voiceMessageCountUpdate(int n) throws RemoteException {
        }
    }

}

