/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.telecom.VideoProfile
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsExternalCallState
 *  android.util.ArrayMap
 *  android.util.Log
 *  com.android.ims.ImsExternalCallStateListener
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony.imsphone;

import android.net.Uri;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telecom.VideoProfile;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsExternalCallState;
import android.util.ArrayMap;
import android.util.Log;
import com.android.ims.ImsExternalCallStateListener;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.imsphone.ImsExternalConnection;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCallTracker;
import com.android.internal.telephony.imsphone.ImsPullCall;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ImsExternalCallTracker
implements ImsPhoneCallTracker.PhoneStateListener {
    private static final int EVENT_VIDEO_CAPABILITIES_CHANGED = 1;
    public static final String EXTRA_IMS_EXTERNAL_CALL_ID = "android.telephony.ImsExternalCallTracker.extra.EXTERNAL_CALL_ID";
    public static final String TAG = "ImsExternalCallTracker";
    private ImsPullCall mCallPuller;
    private final ImsCallNotify mCallStateNotifier;
    private Map<Integer, Boolean> mExternalCallPullableState = new ArrayMap();
    private final ExternalCallStateListener mExternalCallStateListener;
    private final ExternalConnectionListener mExternalConnectionListener = new ExternalConnectionListener();
    private Map<Integer, ImsExternalConnection> mExternalConnections = new ArrayMap();
    private final Handler mHandler = new Handler(){

        public void handleMessage(Message message) {
            if (message.what == 1) {
                ImsExternalCallTracker.this.handleVideoCapabilitiesChanged((AsyncResult)message.obj);
            }
        }
    };
    private boolean mHasActiveCalls;
    private boolean mIsVideoCapable;
    private final ImsPhone mPhone;

    public ImsExternalCallTracker(ImsPhone imsPhone) {
        this.mPhone = imsPhone;
        this.mCallStateNotifier = new ImsCallNotify(){

            @Override
            public void notifyPreciseCallStateChanged() {
                ImsExternalCallTracker.this.mPhone.notifyPreciseCallStateChanged();
            }

            @Override
            public void notifyUnknownConnection(Connection connection) {
                ImsExternalCallTracker.this.mPhone.notifyUnknownConnection(connection);
            }
        };
        this.mExternalCallStateListener = new ExternalCallStateListener();
        this.registerForNotifications();
    }

    @VisibleForTesting
    public ImsExternalCallTracker(ImsPhone imsPhone, ImsPullCall imsPullCall, ImsCallNotify imsCallNotify) {
        this.mPhone = imsPhone;
        this.mCallStateNotifier = imsCallNotify;
        this.mExternalCallStateListener = new ExternalCallStateListener();
        this.mCallPuller = imsPullCall;
    }

    private boolean containsCallId(List<ImsExternalCallState> object, int n) {
        if (object == null) {
            return false;
        }
        object = object.iterator();
        while (object.hasNext()) {
            if (((ImsExternalCallState)object.next()).getCallId() != n) continue;
            return true;
        }
        return false;
    }

    private void createExternalConnection(ImsExternalCallState imsExternalCallState) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("createExternalConnection : state = ");
        stringBuilder.append((Object)imsExternalCallState);
        Log.i((String)TAG, (String)stringBuilder.toString());
        int n = ImsCallProfile.getVideoStateFromCallType((int)imsExternalCallState.getCallType());
        boolean bl = this.isCallPullPermitted(imsExternalCallState.isCallPullable(), n);
        ImsExternalConnection imsExternalConnection = new ImsExternalConnection(this.mPhone, imsExternalCallState.getCallId(), imsExternalCallState.getAddress(), bl);
        imsExternalConnection.setVideoState(n);
        imsExternalConnection.addListener(this.mExternalConnectionListener);
        stringBuilder = new StringBuilder();
        stringBuilder.append("createExternalConnection - pullable state : externalCallId = ");
        stringBuilder.append(imsExternalConnection.getCallId());
        stringBuilder.append(" ; isPullable = ");
        stringBuilder.append(bl);
        stringBuilder.append(" ; networkPullable = ");
        stringBuilder.append(imsExternalCallState.isCallPullable());
        stringBuilder.append(" ; isVideo = ");
        stringBuilder.append(VideoProfile.isVideo((int)n));
        stringBuilder.append(" ; videoEnabled = ");
        stringBuilder.append(this.mIsVideoCapable);
        stringBuilder.append(" ; hasActiveCalls = ");
        stringBuilder.append(this.mHasActiveCalls);
        Log.d((String)TAG, (String)stringBuilder.toString());
        this.mExternalConnections.put(imsExternalConnection.getCallId(), imsExternalConnection);
        this.mExternalCallPullableState.put(imsExternalConnection.getCallId(), imsExternalCallState.isCallPullable());
        this.mCallStateNotifier.notifyUnknownConnection(imsExternalConnection);
    }

    private void handleVideoCapabilitiesChanged(AsyncResult object) {
        this.mIsVideoCapable = (Boolean)((AsyncResult)object).result;
        object = new StringBuilder();
        ((StringBuilder)object).append("handleVideoCapabilitiesChanged : isVideoCapable = ");
        ((StringBuilder)object).append(this.mIsVideoCapable);
        Log.i((String)TAG, (String)((StringBuilder)object).toString());
        this.refreshCallPullState();
    }

    private boolean isCallPullPermitted(boolean bl, int n) {
        if (VideoProfile.isVideo((int)n) && !this.mIsVideoCapable) {
            return false;
        }
        if (this.mHasActiveCalls) {
            return false;
        }
        return bl;
    }

    private void refreshCallPullState() {
        Log.d((String)TAG, (String)"refreshCallPullState");
        for (ImsExternalConnection imsExternalConnection : this.mExternalConnections.values()) {
            boolean bl = this.mExternalCallPullableState.get(imsExternalConnection.getCallId());
            boolean bl2 = this.isCallPullPermitted(bl, imsExternalConnection.getVideoState());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("refreshCallPullState : externalCallId = ");
            stringBuilder.append(imsExternalConnection.getCallId());
            stringBuilder.append(" ; isPullable = ");
            stringBuilder.append(bl2);
            stringBuilder.append(" ; networkPullable = ");
            stringBuilder.append(bl);
            stringBuilder.append(" ; isVideo = ");
            stringBuilder.append(VideoProfile.isVideo((int)imsExternalConnection.getVideoState()));
            stringBuilder.append(" ; videoEnabled = ");
            stringBuilder.append(this.mIsVideoCapable);
            stringBuilder.append(" ; hasActiveCalls = ");
            stringBuilder.append(this.mHasActiveCalls);
            Log.d((String)TAG, (String)stringBuilder.toString());
            imsExternalConnection.setIsPullable(bl2);
        }
    }

    private void registerForNotifications() {
        if (this.mPhone != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Registering: ");
            stringBuilder.append(this.mPhone);
            Log.d((String)TAG, (String)stringBuilder.toString());
            this.mPhone.getDefaultPhone().registerForVideoCapabilityChanged(this.mHandler, 1, null);
        }
    }

    private void unregisterForNotifications() {
        if (this.mPhone != null) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unregistering: ");
            stringBuilder.append(this.mPhone);
            Log.d((String)TAG, (String)stringBuilder.toString());
            this.mPhone.getDefaultPhone().unregisterForVideoCapabilityChanged(this.mHandler);
        }
    }

    private void updateExistingConnection(ImsExternalConnection imsExternalConnection, ImsExternalCallState imsExternalCallState) {
        int n;
        Object object = new StringBuilder();
        object.append("updateExistingConnection : state = ");
        object.append((Object)imsExternalCallState);
        Log.i((String)TAG, (String)object.toString());
        Call.State state = imsExternalConnection.getState();
        object = imsExternalCallState.getCallState() == 1 ? Call.State.ACTIVE : Call.State.DISCONNECTED;
        if (state != object) {
            if (object == Call.State.ACTIVE) {
                imsExternalConnection.setActive();
            } else {
                imsExternalConnection.setTerminated();
                imsExternalConnection.removeListener(this.mExternalConnectionListener);
                this.mExternalConnections.remove(imsExternalConnection.getCallId());
                this.mExternalCallPullableState.remove(imsExternalConnection.getCallId());
                this.mCallStateNotifier.notifyPreciseCallStateChanged();
            }
        }
        if ((n = ImsCallProfile.getVideoStateFromCallType((int)imsExternalCallState.getCallType())) != imsExternalConnection.getVideoState()) {
            imsExternalConnection.setVideoState(n);
        }
        this.mExternalCallPullableState.put(imsExternalCallState.getCallId(), imsExternalCallState.isCallPullable());
        boolean bl = this.isCallPullPermitted(imsExternalCallState.isCallPullable(), n);
        object = new StringBuilder();
        object.append("updateExistingConnection - pullable state : externalCallId = ");
        object.append(imsExternalConnection.getCallId());
        object.append(" ; isPullable = ");
        object.append(bl);
        object.append(" ; networkPullable = ");
        object.append(imsExternalCallState.isCallPullable());
        object.append(" ; isVideo = ");
        object.append(VideoProfile.isVideo((int)imsExternalConnection.getVideoState()));
        object.append(" ; videoEnabled = ");
        object.append(this.mIsVideoCapable);
        object.append(" ; hasActiveCalls = ");
        object.append(this.mHasActiveCalls);
        Log.d((String)TAG, (String)object.toString());
        imsExternalConnection.setIsPullable(bl);
    }

    public Connection getConnectionById(int n) {
        return this.mExternalConnections.get(n);
    }

    public ExternalCallStateListener getExternalCallStateListener() {
        return this.mExternalCallStateListener;
    }

    @Override
    public void onPhoneStateChanged(PhoneConstants.State object, PhoneConstants.State state) {
        boolean bl = state != PhoneConstants.State.IDLE;
        this.mHasActiveCalls = bl;
        object = new StringBuilder();
        ((StringBuilder)object).append("onPhoneStateChanged : hasActiveCalls = ");
        ((StringBuilder)object).append(this.mHasActiveCalls);
        Log.i((String)TAG, (String)((StringBuilder)object).toString());
        this.refreshCallPullState();
    }

    public void refreshExternalCallState(List<ImsExternalCallState> object) {
        Map.Entry<Integer, ImsExternalConnection> entry;
        Log.d((String)TAG, (String)"refreshExternalCallState");
        ImsExternalCallState imsExternalCallState = this.mExternalConnections.entrySet().iterator();
        boolean bl = false;
        while (imsExternalCallState.hasNext()) {
            entry = imsExternalCallState.next();
            if (this.containsCallId((List<ImsExternalCallState>)object, entry.getKey())) continue;
            entry = entry.getValue();
            ((ImsExternalConnection)((Object)entry)).setTerminated();
            ((ImsExternalConnection)((Object)entry)).removeListener(this.mExternalConnectionListener);
            imsExternalCallState.remove();
            bl = true;
        }
        if (bl) {
            this.mCallStateNotifier.notifyPreciseCallStateChanged();
        }
        if (object != null && !object.isEmpty()) {
            object = object.iterator();
            while (object.hasNext()) {
                imsExternalCallState = (ImsExternalCallState)object.next();
                if (!this.mExternalConnections.containsKey(imsExternalCallState.getCallId())) {
                    entry = new StringBuilder();
                    ((StringBuilder)((Object)entry)).append("refreshExternalCallState: got = ");
                    ((StringBuilder)((Object)entry)).append((Object)imsExternalCallState);
                    Log.d((String)TAG, (String)((StringBuilder)((Object)entry)).toString());
                    if (imsExternalCallState.getCallState() != 1) continue;
                    this.createExternalConnection(imsExternalCallState);
                    continue;
                }
                this.updateExistingConnection(this.mExternalConnections.get(imsExternalCallState.getCallId()), imsExternalCallState);
            }
        }
    }

    public void setCallPuller(ImsPullCall imsPullCall) {
        this.mCallPuller = imsPullCall;
    }

    public void tearDown() {
        this.unregisterForNotifications();
    }

    public class ExternalCallStateListener
    extends ImsExternalCallStateListener {
        public void onImsExternalCallStateUpdate(List<ImsExternalCallState> list) {
            ImsExternalCallTracker.this.refreshExternalCallState(list);
        }
    }

    public class ExternalConnectionListener
    implements ImsExternalConnection.Listener {
        @Override
        public void onPullExternalCall(ImsExternalConnection imsExternalConnection) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onPullExternalCall: connection = ");
            stringBuilder.append(imsExternalConnection);
            Log.d((String)ImsExternalCallTracker.TAG, (String)stringBuilder.toString());
            if (ImsExternalCallTracker.this.mCallPuller == null) {
                Log.e((String)ImsExternalCallTracker.TAG, (String)"onPullExternalCall : No call puller defined");
                return;
            }
            ImsExternalCallTracker.this.mCallPuller.pullExternalCall(imsExternalConnection.getAddress(), imsExternalConnection.getVideoState(), imsExternalConnection.getCallId());
        }
    }

    public static interface ImsCallNotify {
        public void notifyPreciseCallStateChanged();

        public void notifyUnknownConnection(Connection var1);
    }

}

