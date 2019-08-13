/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.telecom.AudioState;
import android.telecom.CallAudioState;
import android.telecom.CallbackRecord;
import android.telecom.Connection;
import android.telecom.DisconnectCause;
import android.telecom.Log;
import android.telecom.Logging.Session;
import android.telecom.RemoteConnection;
import com.android.internal.telecom.IConnectionService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public final class RemoteConference {
    private final Set<CallbackRecord<Callback>> mCallbackRecords = new CopyOnWriteArraySet<CallbackRecord<Callback>>();
    private final List<RemoteConnection> mChildConnections = new CopyOnWriteArrayList<RemoteConnection>();
    private final List<RemoteConnection> mConferenceableConnections = new ArrayList<RemoteConnection>();
    private int mConnectionCapabilities;
    private int mConnectionProperties;
    private final IConnectionService mConnectionService;
    private DisconnectCause mDisconnectCause;
    private Bundle mExtras;
    private final String mId;
    private int mState = 1;
    private final List<RemoteConnection> mUnmodifiableChildConnections = Collections.unmodifiableList(this.mChildConnections);
    private final List<RemoteConnection> mUnmodifiableConferenceableConnections = Collections.unmodifiableList(this.mConferenceableConnections);

    RemoteConference(String string2, IConnectionService iConnectionService) {
        this.mId = string2;
        this.mConnectionService = iConnectionService;
    }

    private void notifyExtrasChanged() {
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            final Callback callback = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable(){

                @Override
                public void run() {
                    callback.onExtrasChanged(this, RemoteConference.this.mExtras);
                }
            });
        }
    }

    void addConnection(final RemoteConnection remoteConnection) {
        if (!this.mChildConnections.contains(remoteConnection)) {
            this.mChildConnections.add(remoteConnection);
            remoteConnection.setConference(this);
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onConnectionAdded(this, remoteConnection);
                    }
                });
            }
        }
    }

    public void disconnect() {
        try {
            this.mConnectionService.disconnect(this.mId, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public List<RemoteConnection> getConferenceableConnections() {
        return this.mUnmodifiableConferenceableConnections;
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final List<RemoteConnection> getConnections() {
        return this.mUnmodifiableChildConnections;
    }

    public DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    String getId() {
        return this.mId;
    }

    public final int getState() {
        return this.mState;
    }

    public void hold() {
        try {
            this.mConnectionService.hold(this.mId, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void merge() {
        try {
            this.mConnectionService.mergeConference(this.mId, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void playDtmfTone(char c) {
        try {
            this.mConnectionService.playDtmfTone(this.mId, c, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    void putExtras(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        this.mExtras.putAll(bundle);
        this.notifyExtrasChanged();
    }

    public final void registerCallback(Callback callback) {
        this.registerCallback(callback, new Handler());
    }

    public final void registerCallback(Callback callback, Handler handler) {
        this.unregisterCallback(callback);
        if (callback != null && handler != null) {
            this.mCallbackRecords.add(new CallbackRecord<Callback>(callback, handler));
        }
    }

    void removeConnection(final RemoteConnection remoteConnection) {
        if (this.mChildConnections.contains(remoteConnection)) {
            this.mChildConnections.remove(remoteConnection);
            remoteConnection.setConference(null);
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onConnectionRemoved(this, remoteConnection);
                    }
                });
            }
        }
    }

    void removeExtras(List<String> object) {
        if (this.mExtras != null && object != null && !object.isEmpty()) {
            object = object.iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                this.mExtras.remove(string2);
            }
            this.notifyExtrasChanged();
            return;
        }
    }

    public void separate(RemoteConnection remoteConnection) {
        if (this.mChildConnections.contains(remoteConnection)) {
            try {
                this.mConnectionService.splitFromConference(remoteConnection.getId(), null);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    @SystemApi
    @Deprecated
    public void setAudioState(AudioState audioState) {
        this.setCallAudioState(new CallAudioState(audioState));
    }

    public void setCallAudioState(CallAudioState callAudioState) {
        try {
            this.mConnectionService.onCallAudioStateChanged(this.mId, callAudioState, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    void setConferenceableConnections(List<RemoteConnection> object) {
        this.mConferenceableConnections.clear();
        this.mConferenceableConnections.addAll((Collection<RemoteConnection>)object);
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            object = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable((Callback)object, this){
                final /* synthetic */ Callback val$callback;
                final /* synthetic */ RemoteConference val$conference;
                {
                    this.val$callback = callback;
                    this.val$conference = remoteConference2;
                }

                @Override
                public void run() {
                    this.val$callback.onConferenceableConnectionsChanged(this.val$conference, RemoteConference.this.mUnmodifiableConferenceableConnections);
                }
            });
        }
    }

    void setConnectionCapabilities(int n) {
        if (this.mConnectionCapabilities != n) {
            this.mConnectionCapabilities = n;
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onConnectionCapabilitiesChanged(this, RemoteConference.this.mConnectionCapabilities);
                    }
                });
            }
        }
    }

    void setConnectionProperties(int n) {
        if (this.mConnectionProperties != n) {
            this.mConnectionProperties = n;
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onConnectionPropertiesChanged(this, RemoteConference.this.mConnectionProperties);
                    }
                });
            }
        }
    }

    void setDestroyed() {
        Object object = this.mChildConnections.iterator();
        while (object.hasNext()) {
            object.next().setConference(null);
        }
        for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
            object = callbackRecord.getCallback();
            callbackRecord.getHandler().post(new Runnable((Callback)object, this){
                final /* synthetic */ Callback val$callback;
                final /* synthetic */ RemoteConference val$conference;
                {
                    this.val$callback = callback;
                    this.val$conference = remoteConference2;
                }

                @Override
                public void run() {
                    this.val$callback.onDestroyed(this.val$conference);
                }
            });
        }
    }

    void setDisconnected(final DisconnectCause disconnectCause) {
        if (this.mState != 6) {
            this.mDisconnectCause = disconnectCause;
            this.setState(6);
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
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

    void setState(final int n) {
        if (n != 4 && n != 5 && n != 6) {
            Log.w(this, "Unsupported state transition for Conference call.", Connection.stateToString(n));
            return;
        }
        if (this.mState != n) {
            final int n2 = this.mState;
            this.mState = n;
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
                final Callback callback = callbackRecord.getCallback();
                callbackRecord.getHandler().post(new Runnable(){

                    @Override
                    public void run() {
                        callback.onStateChanged(this, n2, n);
                    }
                });
            }
        }
    }

    public void stopDtmfTone() {
        try {
            this.mConnectionService.stopDtmfTone(this.mId, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void swap() {
        try {
            this.mConnectionService.swapConference(this.mId, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void unhold() {
        try {
            this.mConnectionService.unhold(this.mId, null);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public final void unregisterCallback(Callback callback) {
        if (callback != null) {
            for (CallbackRecord<Callback> callbackRecord : this.mCallbackRecords) {
                if (callbackRecord.getCallback() != callback) continue;
                this.mCallbackRecords.remove(callbackRecord);
                break;
            }
        }
    }

    public static abstract class Callback {
        public void onConferenceableConnectionsChanged(RemoteConference remoteConference, List<RemoteConnection> list) {
        }

        public void onConnectionAdded(RemoteConference remoteConference, RemoteConnection remoteConnection) {
        }

        public void onConnectionCapabilitiesChanged(RemoteConference remoteConference, int n) {
        }

        public void onConnectionPropertiesChanged(RemoteConference remoteConference, int n) {
        }

        public void onConnectionRemoved(RemoteConference remoteConference, RemoteConnection remoteConnection) {
        }

        public void onDestroyed(RemoteConference remoteConference) {
        }

        public void onDisconnected(RemoteConference remoteConference, DisconnectCause disconnectCause) {
        }

        public void onExtrasChanged(RemoteConference remoteConference, Bundle bundle) {
        }

        public void onStateChanged(RemoteConference remoteConference, int n, int n2) {
        }
    }

}

