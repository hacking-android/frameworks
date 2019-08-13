/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.telecom.AudioState;
import android.telecom.CallAudioState;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.DisconnectCause;
import android.telecom.Log;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConnection;
import android.telecom.RemoteConference;
import android.telecom.StatusHints;
import android.telecom.VideoCallbackServant;
import android.telecom.VideoProfile;
import android.telecom._$$Lambda$RemoteConnection$AwagQDJDcNDplrFif6DlYZldL5E;
import android.telecom._$$Lambda$RemoteConnection$C4t0J6QK31Ef1UFsdPVwkew1VaQ;
import android.telecom._$$Lambda$RemoteConnection$mmHouQhUco_u9PRJ9qkMqlkKzAs;
import android.telecom._$$Lambda$RemoteConnection$yp1cNJ53RzQGFz3RZRlC3urzQv4;
import android.view.Surface;
import com.android.internal.telecom.IConnectionService;
import com.android.internal.telecom.IVideoCallback;
import com.android.internal.telecom.IVideoProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class RemoteConnection {
    private Uri mAddress;
    private int mAddressPresentation;
    private final Set<CallbackRecord> mCallbackRecords = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));
    private String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private RemoteConference mConference;
    private final List<RemoteConnection> mConferenceableConnections = new ArrayList<RemoteConnection>();
    private boolean mConnected;
    private int mConnectionCapabilities;
    private final String mConnectionId;
    private int mConnectionProperties;
    private IConnectionService mConnectionService;
    private DisconnectCause mDisconnectCause;
    private Bundle mExtras;
    private boolean mIsVoipAudioMode;
    private boolean mRingbackRequested;
    private int mState = 1;
    private StatusHints mStatusHints;
    private final List<RemoteConnection> mUnmodifiableconferenceableConnections = Collections.unmodifiableList(this.mConferenceableConnections);
    private VideoProvider mVideoProvider;
    private int mVideoState;

    RemoteConnection(DisconnectCause disconnectCause) {
        this.mConnectionId = "NULL";
        this.mConnected = false;
        this.mState = 6;
        this.mDisconnectCause = disconnectCause;
    }

    RemoteConnection(String string2, IConnectionService iConnectionService, ConnectionRequest connectionRequest) {
        this.mConnectionId = string2;
        this.mConnectionService = iConnectionService;
        this.mConnected = true;
        this.mState = 0;
    }

    RemoteConnection(String string2, IConnectionService object, ParcelableConnection parcelableConnection, String string3, int n) {
        this.mConnectionId = string2;
        this.mConnectionService = object;
        this.mConnected = true;
        this.mState = parcelableConnection.getState();
        this.mDisconnectCause = parcelableConnection.getDisconnectCause();
        this.mRingbackRequested = parcelableConnection.isRingbackRequested();
        this.mConnectionCapabilities = parcelableConnection.getConnectionCapabilities();
        this.mConnectionProperties = parcelableConnection.getConnectionProperties();
        this.mVideoState = parcelableConnection.getVideoState();
        object = parcelableConnection.getVideoProvider();
        this.mVideoProvider = object != null ? new VideoProvider((IVideoProvider)object, string3, n) : null;
        this.mIsVoipAudioMode = parcelableConnection.getIsVoipAudioMode();
        this.mStatusHints = parcelableConnection.getStatusHints();
        this.mAddress = parcelableConnection.getHandle();
        this.mAddressPresentation = parcelableConnection.getHandlePresentation();
        this.mCallerDisplayName = parcelableConnection.getCallerDisplayName();
        this.mCallerDisplayNamePresentation = parcelableConnection.getCallerDisplayNamePresentation();
        this.mConference = null;
        this.putExtras(parcelableConnection.getExtras());
        object = new Bundle();
        ((BaseBundle)object).putString("android.telecom.extra.ORIGINAL_CONNECTION_ID", string2);
        this.putExtras((Bundle)object);
    }

    public static RemoteConnection failure(DisconnectCause disconnectCause) {
        return new RemoteConnection(disconnectCause);
    }

    static /* synthetic */ void lambda$onRemoteRttRequest$3(Callback callback, RemoteConnection remoteConnection) {
        callback.onRemoteRttRequest(remoteConnection);
    }

    static /* synthetic */ void lambda$onRttInitiationFailure$1(Callback callback, RemoteConnection remoteConnection, int n) {
        callback.onRttInitiationFailure(remoteConnection, n);
    }

    static /* synthetic */ void lambda$onRttInitiationSuccess$0(Callback callback, RemoteConnection remoteConnection) {
        callback.onRttInitiationSuccess(remoteConnection);
    }

    static /* synthetic */ void lambda$onRttSessionRemotelyTerminated$2(Callback callback, RemoteConnection remoteConnection) {
        callback.onRttSessionRemotelyTerminated(remoteConnection);
    }

    private void notifyExtrasChanged() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onExtrasChanged(this, RemoteConnection.this.mExtras);
                }
            });
        }
    }

    public void abort() {
        try {
            if (this.mConnected) {
                this.mConnectionService.abort(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void answer() {
        try {
            if (this.mConnected) {
                this.mConnectionService.answer(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void answer(int n) {
        try {
            if (this.mConnected) {
                this.mConnectionService.answerVideo(this.mConnectionId, n, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void disconnect() {
        try {
            if (this.mConnected) {
                this.mConnectionService.disconnect(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public Uri getAddress() {
        return this.mAddress;
    }

    public int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    public CharSequence getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public RemoteConference getConference() {
        return this.mConference;
    }

    public List<RemoteConnection> getConferenceableConnections() {
        return this.mUnmodifiableconferenceableConnections;
    }

    public int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    IConnectionService getConnectionService() {
        return this.mConnectionService;
    }

    public DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    String getId() {
        return this.mConnectionId;
    }

    public int getState() {
        return this.mState;
    }

    public StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final VideoProvider getVideoProvider() {
        return this.mVideoProvider;
    }

    public int getVideoState() {
        return this.mVideoState;
    }

    public void hold() {
        try {
            if (this.mConnected) {
                this.mConnectionService.hold(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public boolean isRingbackRequested() {
        return this.mRingbackRequested;
    }

    public boolean isVoipAudioMode() {
        return this.mIsVoipAudioMode;
    }

    void onConnectionEvent(final String string2, final Bundle bundle) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onConnectionEvent(this, string2, bundle);
                }
            });
        }
    }

    void onPostDialChar(final char c) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onPostDialChar(this, c);
                }
            });
        }
    }

    void onRemoteRttRequest() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$RemoteConnection$yp1cNJ53RzQGFz3RZRlC3urzQv4(callback, this));
        }
    }

    void onRttInitiationFailure(int n) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$RemoteConnection$AwagQDJDcNDplrFif6DlYZldL5E(callback, this, n));
        }
    }

    void onRttInitiationSuccess() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$RemoteConnection$C4t0J6QK31Ef1UFsdPVwkew1VaQ(callback, this));
        }
    }

    void onRttSessionRemotelyTerminated() {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new _$$Lambda$RemoteConnection$mmHouQhUco_u9PRJ9qkMqlkKzAs(callback, this));
        }
    }

    public void playDtmfTone(char c) {
        try {
            if (this.mConnected) {
                this.mConnectionService.playDtmfTone(this.mConnectionId, c, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void postDialContinue(boolean bl) {
        try {
            if (this.mConnected) {
                this.mConnectionService.onPostDialContinue(this.mConnectionId, bl, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void pullExternalCall() {
        try {
            if (this.mConnected) {
                this.mConnectionService.pullExternalCall(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    void putExtras(Bundle object) {
        if (object == null) {
            return;
        }
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        try {
            this.mExtras.putAll((Bundle)object);
        }
        catch (BadParcelableException badParcelableException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("putExtras: could not unmarshal extras; exception = ");
            ((StringBuilder)object).append(badParcelableException);
            Log.w(this, ((StringBuilder)object).toString(), new Object[0]);
        }
        this.notifyExtrasChanged();
    }

    public void registerCallback(Callback callback) {
        this.registerCallback(callback, new Handler());
    }

    public void registerCallback(Callback callback, Handler handler) {
        this.unregisterCallback(callback);
        if (callback != null && handler != null) {
            this.mCallbackRecords.add(new CallbackRecord(callback, handler));
        }
    }

    public void reject() {
        try {
            if (this.mConnected) {
                this.mConnectionService.reject(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    void removeExtras(List<String> object) {
        if (this.mExtras != null && object != null && !object.isEmpty()) {
            Iterator<String> iterator = object.iterator();
            while (iterator.hasNext()) {
                object = iterator.next();
                this.mExtras.remove((String)object);
            }
            this.notifyExtrasChanged();
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendRttUpgradeResponse(Connection.RttTextStream rttTextStream) {
        try {
            if (!this.mConnected) return;
            if (rttTextStream == null) {
                this.mConnectionService.respondToRttUpgradeRequest(this.mConnectionId, null, null, null);
                return;
            }
            this.mConnectionService.respondToRttUpgradeRequest(this.mConnectionId, rttTextStream.getFdFromInCall(), rttTextStream.getFdToInCall(), null);
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    void setAddress(final Uri uri, final int n) {
        this.mAddress = uri;
        this.mAddressPresentation = n;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onAddressChanged(this, uri, n);
                }
            });
        }
    }

    @SystemApi
    @Deprecated
    public void setAudioState(AudioState audioState) {
        this.setCallAudioState(new CallAudioState(audioState));
    }

    public void setCallAudioState(CallAudioState callAudioState) {
        try {
            if (this.mConnected) {
                this.mConnectionService.onCallAudioStateChanged(this.mConnectionId, callAudioState, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    void setCallerDisplayName(final String string2, final int n) {
        this.mCallerDisplayName = string2;
        this.mCallerDisplayNamePresentation = n;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onCallerDisplayNameChanged(this, string2, n);
                }
            });
        }
    }

    void setConference(final RemoteConference remoteConference) {
        if (this.mConference != remoteConference) {
            this.mConference = remoteConference;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onConferenceChanged(this, remoteConference);
                    }
                });
            }
        }
    }

    void setConferenceableConnections(List<RemoteConnection> object) {
        this.mConferenceableConnections.clear();
        this.mConferenceableConnections.addAll((Collection<RemoteConnection>)object);
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            object = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable((Callback)object, this){
                final /* synthetic */ Callback val$callback;
                final /* synthetic */ RemoteConnection val$connection;
                {
                    this.val$callback = callback;
                    this.val$connection = remoteConnection2;
                }

                @Override
                public void run() {
                    this.val$callback.onConferenceableConnectionsChanged(this.val$connection, RemoteConnection.this.mUnmodifiableconferenceableConnections);
                }
            });
        }
    }

    void setConnectionCapabilities(final int n) {
        this.mConnectionCapabilities = n;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onConnectionCapabilitiesChanged(this, n);
                }
            });
        }
    }

    void setConnectionProperties(final int n) {
        this.mConnectionProperties = n;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onConnectionPropertiesChanged(this, n);
                }
            });
        }
    }

    void setDestroyed() {
        if (!this.mCallbackRecords.isEmpty()) {
            if (this.mState != 6) {
                this.setDisconnected(new DisconnectCause(1, "Connection destroyed."));
            }
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onDestroyed(this);
                    }
                });
            }
            this.mCallbackRecords.clear();
            this.mConnected = false;
        }
    }

    void setDisconnected(final DisconnectCause disconnectCause) {
        if (this.mState != 6) {
            this.mState = 6;
            this.mDisconnectCause = disconnectCause;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onDisconnected(this, disconnectCause);
                    }
                });
            }
        }
    }

    void setIsVoipAudioMode(final boolean bl) {
        this.mIsVoipAudioMode = bl;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onVoipAudioChanged(this, bl);
                }
            });
        }
    }

    void setPostDialWait(final String string2) {
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onPostDialWait(this, string2);
                }
            });
        }
    }

    void setRingbackRequested(final boolean bl) {
        if (this.mRingbackRequested != bl) {
            this.mRingbackRequested = bl;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onRingbackRequested(this, bl);
                    }
                });
            }
        }
    }

    void setState(final int n) {
        if (this.mState != n) {
            this.mState = n;
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onStateChanged(this, n);
                    }
                });
            }
        }
    }

    void setStatusHints(final StatusHints statusHints) {
        this.mStatusHints = statusHints;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onStatusHintsChanged(this, statusHints);
                }
            });
        }
    }

    void setVideoProvider(final VideoProvider videoProvider) {
        this.mVideoProvider = videoProvider;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onVideoProviderChanged(this, videoProvider);
                }
            });
        }
    }

    void setVideoState(final int n) {
        this.mVideoState = n;
        for (CallbackRecord callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onVideoStateChanged(this, n);
                }
            });
        }
    }

    public void startRtt(Connection.RttTextStream rttTextStream) {
        try {
            if (this.mConnected) {
                this.mConnectionService.startRtt(this.mConnectionId, rttTextStream.getFdFromInCall(), rttTextStream.getFdToInCall(), null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void stopDtmfTone() {
        try {
            if (this.mConnected) {
                this.mConnectionService.stopDtmfTone(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void stopRtt() {
        try {
            if (this.mConnected) {
                this.mConnectionService.stopRtt(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void unhold() {
        try {
            if (this.mConnected) {
                this.mConnectionService.unhold(this.mConnectionId, null);
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void unregisterCallback(Callback callback) {
        if (callback != null) {
            for (CallbackRecord callbackRecord : this.mCallbackRecords) {
                if (callbackRecord.getCallback() != callback) continue;
                this.mCallbackRecords.remove(callbackRecord);
                break;
            }
        }
    }

    public static abstract class Callback {
        public void onAddressChanged(RemoteConnection remoteConnection, Uri uri, int n) {
        }

        public void onCallerDisplayNameChanged(RemoteConnection remoteConnection, String string2, int n) {
        }

        public void onConferenceChanged(RemoteConnection remoteConnection, RemoteConference remoteConference) {
        }

        public void onConferenceableConnectionsChanged(RemoteConnection remoteConnection, List<RemoteConnection> list) {
        }

        public void onConnectionCapabilitiesChanged(RemoteConnection remoteConnection, int n) {
        }

        public void onConnectionEvent(RemoteConnection remoteConnection, String string2, Bundle bundle) {
        }

        public void onConnectionPropertiesChanged(RemoteConnection remoteConnection, int n) {
        }

        public void onDestroyed(RemoteConnection remoteConnection) {
        }

        public void onDisconnected(RemoteConnection remoteConnection, DisconnectCause disconnectCause) {
        }

        public void onExtrasChanged(RemoteConnection remoteConnection, Bundle bundle) {
        }

        public void onPostDialChar(RemoteConnection remoteConnection, char c) {
        }

        public void onPostDialWait(RemoteConnection remoteConnection, String string2) {
        }

        public void onRemoteRttRequest(RemoteConnection remoteConnection) {
        }

        public void onRingbackRequested(RemoteConnection remoteConnection, boolean bl) {
        }

        public void onRttInitiationFailure(RemoteConnection remoteConnection, int n) {
        }

        public void onRttInitiationSuccess(RemoteConnection remoteConnection) {
        }

        public void onRttSessionRemotelyTerminated(RemoteConnection remoteConnection) {
        }

        public void onStateChanged(RemoteConnection remoteConnection, int n) {
        }

        public void onStatusHintsChanged(RemoteConnection remoteConnection, StatusHints statusHints) {
        }

        public void onVideoProviderChanged(RemoteConnection remoteConnection, VideoProvider videoProvider) {
        }

        public void onVideoStateChanged(RemoteConnection remoteConnection, int n) {
        }

        public void onVoipAudioChanged(RemoteConnection remoteConnection, boolean bl) {
        }
    }

    private static final class CallbackRecord
    extends Callback {
        private final Callback mCallback;
        private final Handler mHandler;

        public CallbackRecord(Callback callback, Handler handler) {
            this.mCallback = callback;
            this.mHandler = handler;
        }

        public Callback getCallback() {
            return this.mCallback;
        }

        public Handler getHandler() {
            return this.mHandler;
        }
    }

    public static class VideoProvider {
        private final Set<Callback> mCallbacks = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));
        private final String mCallingPackage;
        private final int mTargetSdkVersion;
        private final IVideoCallback mVideoCallbackDelegate = new IVideoCallback(){

            @Override
            public IBinder asBinder() {
                return null;
            }

            @Override
            public void changeCallDataUsage(long l) {
                Iterator iterator = VideoProvider.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((Callback)iterator.next()).onCallDataUsageChanged(VideoProvider.this, l);
                }
            }

            @Override
            public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) {
                Iterator iterator = VideoProvider.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((Callback)iterator.next()).onCameraCapabilitiesChanged(VideoProvider.this, cameraCapabilities);
                }
            }

            @Override
            public void changePeerDimensions(int n, int n2) {
                Iterator iterator = VideoProvider.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((Callback)iterator.next()).onPeerDimensionsChanged(VideoProvider.this, n, n2);
                }
            }

            @Override
            public void changeVideoQuality(int n) {
                Iterator iterator = VideoProvider.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((Callback)iterator.next()).onVideoQualityChanged(VideoProvider.this, n);
                }
            }

            @Override
            public void handleCallSessionEvent(int n) {
                Iterator iterator = VideoProvider.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((Callback)iterator.next()).onCallSessionEvent(VideoProvider.this, n);
                }
            }

            @Override
            public void receiveSessionModifyRequest(VideoProfile videoProfile) {
                Iterator iterator = VideoProvider.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((Callback)iterator.next()).onSessionModifyRequestReceived(VideoProvider.this, videoProfile);
                }
            }

            @Override
            public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) {
                Iterator iterator = VideoProvider.this.mCallbacks.iterator();
                while (iterator.hasNext()) {
                    ((Callback)iterator.next()).onSessionModifyResponseReceived(VideoProvider.this, n, videoProfile, videoProfile2);
                }
            }
        };
        private final VideoCallbackServant mVideoCallbackServant = new VideoCallbackServant(this.mVideoCallbackDelegate);
        private final IVideoProvider mVideoProviderBinder;

        VideoProvider(IVideoProvider iVideoProvider, String string2, int n) {
            this.mVideoProviderBinder = iVideoProvider;
            this.mCallingPackage = string2;
            this.mTargetSdkVersion = n;
            try {
                this.mVideoProviderBinder.addVideoCallback(this.mVideoCallbackServant.getStub().asBinder());
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void registerCallback(Callback callback) {
            this.mCallbacks.add(callback);
        }

        public void requestCallDataUsage() {
            try {
                this.mVideoProviderBinder.requestCallDataUsage();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void requestCameraCapabilities() {
            try {
                this.mVideoProviderBinder.requestCameraCapabilities();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) {
            try {
                this.mVideoProviderBinder.sendSessionModifyRequest(videoProfile, videoProfile2);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void sendSessionModifyResponse(VideoProfile videoProfile) {
            try {
                this.mVideoProviderBinder.sendSessionModifyResponse(videoProfile);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void setCamera(String string2) {
            try {
                this.mVideoProviderBinder.setCamera(string2, this.mCallingPackage, this.mTargetSdkVersion);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void setDeviceOrientation(int n) {
            try {
                this.mVideoProviderBinder.setDeviceOrientation(n);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void setDisplaySurface(Surface surface) {
            try {
                this.mVideoProviderBinder.setDisplaySurface(surface);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void setPauseImage(Uri uri) {
            try {
                this.mVideoProviderBinder.setPauseImage(uri);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void setPreviewSurface(Surface surface) {
            try {
                this.mVideoProviderBinder.setPreviewSurface(surface);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void setZoom(float f) {
            try {
                this.mVideoProviderBinder.setZoom(f);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }

        public void unregisterCallback(Callback callback) {
            this.mCallbacks.remove(callback);
        }

        public static abstract class Callback {
            public void onCallDataUsageChanged(VideoProvider videoProvider, long l) {
            }

            public void onCallSessionEvent(VideoProvider videoProvider, int n) {
            }

            public void onCameraCapabilitiesChanged(VideoProvider videoProvider, VideoProfile.CameraCapabilities cameraCapabilities) {
            }

            public void onPeerDimensionsChanged(VideoProvider videoProvider, int n, int n2) {
            }

            public void onSessionModifyRequestReceived(VideoProvider videoProvider, VideoProfile videoProfile) {
            }

            public void onSessionModifyResponseReceived(VideoProvider videoProvider, int n, VideoProfile videoProfile, VideoProfile videoProfile2) {
            }

            public void onVideoQualityChanged(VideoProvider videoProvider, int n) {
            }
        }

    }

}

