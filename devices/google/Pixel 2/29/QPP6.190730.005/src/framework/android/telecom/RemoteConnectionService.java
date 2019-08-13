/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionService;
import android.telecom.ConnectionServiceAdapterServant;
import android.telecom.DisconnectCause;
import android.telecom.Log;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConference;
import android.telecom.ParcelableConnection;
import android.telecom.PhoneAccountHandle;
import android.telecom.RemoteConference;
import android.telecom.RemoteConnection;
import android.telecom.StatusHints;
import com.android.internal.telecom.IConnectionService;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

final class RemoteConnectionService {
    private static final RemoteConference NULL_CONFERENCE;
    private static final RemoteConnection NULL_CONNECTION;
    private final Map<String, RemoteConference> mConferenceById = new HashMap<String, RemoteConference>();
    private final Map<String, RemoteConnection> mConnectionById = new HashMap<String, RemoteConnection>();
    private final IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient(){

        @Override
        public void binderDied() {
            Iterator iterator = RemoteConnectionService.this.mConnectionById.values().iterator();
            while (iterator.hasNext()) {
                ((RemoteConnection)iterator.next()).setDestroyed();
            }
            iterator = RemoteConnectionService.this.mConferenceById.values().iterator();
            while (iterator.hasNext()) {
                ((RemoteConference)iterator.next()).setDestroyed();
            }
            RemoteConnectionService.this.mConnectionById.clear();
            RemoteConnectionService.this.mConferenceById.clear();
            RemoteConnectionService.this.mPendingConnections.clear();
            RemoteConnectionService.this.mOutgoingConnectionServiceRpc.asBinder().unlinkToDeath(RemoteConnectionService.this.mDeathRecipient, 0);
        }
    };
    private final ConnectionService mOurConnectionServiceImpl;
    private final IConnectionService mOutgoingConnectionServiceRpc;
    private final Set<RemoteConnection> mPendingConnections = new HashSet<RemoteConnection>();
    private final ConnectionServiceAdapterServant mServant = new ConnectionServiceAdapterServant(this.mServantDelegate);
    private final IConnectionServiceAdapter mServantDelegate = new IConnectionServiceAdapter(){

        @Override
        public void addConferenceCall(final String string2, ParcelableConference parcelable, Session.Info object) {
            object = new RemoteConference(string2, RemoteConnectionService.this.mOutgoingConnectionServiceRpc);
            for (String string3 : ((ParcelableConference)parcelable).getConnectionIds()) {
                RemoteConnection object2 = (RemoteConnection)RemoteConnectionService.this.mConnectionById.get(string3);
                if (object2 == null) continue;
                ((RemoteConference)object).addConnection(object2);
            }
            if (((RemoteConference)object).getConnections().size() == 0) {
                Log.d(this, "addConferenceCall - skipping", new Object[0]);
                return;
            }
            ((RemoteConference)object).setState(((ParcelableConference)parcelable).getState());
            ((RemoteConference)object).setConnectionCapabilities(((ParcelableConference)parcelable).getConnectionCapabilities());
            ((RemoteConference)object).setConnectionProperties(((ParcelableConference)parcelable).getConnectionProperties());
            ((RemoteConference)object).putExtras(((ParcelableConference)parcelable).getExtras());
            RemoteConnectionService.this.mConferenceById.put(string2, object);
            parcelable = new Bundle();
            ((BaseBundle)((Object)parcelable)).putString("android.telecom.extra.ORIGINAL_CONNECTION_ID", string2);
            ((RemoteConference)object).putExtras((Bundle)parcelable);
            ((RemoteConference)object).registerCallback(new RemoteConference.Callback(){

                @Override
                public void onDestroyed(RemoteConference remoteConference) {
                    RemoteConnectionService.this.mConferenceById.remove(string2);
                    RemoteConnectionService.this.maybeDisconnectAdapter();
                }
            });
            RemoteConnectionService.this.mOurConnectionServiceImpl.addRemoteConference((RemoteConference)object);
        }

        @Override
        public void addExistingConnection(final String string2, ParcelableConnection object, Session.Info object2) {
            object2 = RemoteConnectionService.this.mOurConnectionServiceImpl.getApplicationContext().getOpPackageName();
            int n = RemoteConnectionService.access$900((RemoteConnectionService)RemoteConnectionService.this).getApplicationInfo().targetSdkVersion;
            object = new RemoteConnection(string2, RemoteConnectionService.this.mOutgoingConnectionServiceRpc, (ParcelableConnection)object, (String)object2, n);
            RemoteConnectionService.this.mConnectionById.put(string2, object);
            ((RemoteConnection)object).registerCallback(new RemoteConnection.Callback(){

                @Override
                public void onDestroyed(RemoteConnection remoteConnection) {
                    RemoteConnectionService.this.mConnectionById.remove(string2);
                    RemoteConnectionService.this.maybeDisconnectAdapter();
                }
            });
            RemoteConnectionService.this.mOurConnectionServiceImpl.addRemoteExistingConnection((RemoteConnection)object);
        }

        @Override
        public IBinder asBinder() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void handleCreateConnectionComplete(String object, ConnectionRequest object2, ParcelableConnection parcelableConnection, Session.Info object3) {
            object2 = RemoteConnectionService.this.findConnectionForAction((String)object, "handleCreateConnectionSuccessful");
            if (object2 != NULL_CONNECTION && RemoteConnectionService.this.mPendingConnections.contains(object2)) {
                RemoteConnectionService.this.mPendingConnections.remove(object2);
                ((RemoteConnection)object2).setConnectionCapabilities(parcelableConnection.getConnectionCapabilities());
                ((RemoteConnection)object2).setConnectionProperties(parcelableConnection.getConnectionProperties());
                if (parcelableConnection.getHandle() != null || parcelableConnection.getState() != 6) {
                    ((RemoteConnection)object2).setAddress(parcelableConnection.getHandle(), parcelableConnection.getHandlePresentation());
                }
                if (parcelableConnection.getCallerDisplayName() != null || parcelableConnection.getState() != 6) {
                    ((RemoteConnection)object2).setCallerDisplayName(parcelableConnection.getCallerDisplayName(), parcelableConnection.getCallerDisplayNamePresentation());
                }
                if (parcelableConnection.getState() == 6) {
                    ((RemoteConnection)object2).setDisconnected(parcelableConnection.getDisconnectCause());
                } else {
                    ((RemoteConnection)object2).setState(parcelableConnection.getState());
                }
                object = new ArrayList();
                for (String string2 : parcelableConnection.getConferenceableConnectionIds()) {
                    if (!RemoteConnectionService.this.mConnectionById.containsKey(string2)) continue;
                    object.add((RemoteConnection)RemoteConnectionService.this.mConnectionById.get(string2));
                }
                ((RemoteConnection)object2).setConferenceableConnections((List<RemoteConnection>)object);
                ((RemoteConnection)object2).setVideoState(parcelableConnection.getVideoState());
                if (((RemoteConnection)object2).getState() == 6) {
                    ((RemoteConnection)object2).setDestroyed();
                }
                ((RemoteConnection)object2).setStatusHints(parcelableConnection.getStatusHints());
                ((RemoteConnection)object2).setIsVoipAudioMode(parcelableConnection.getIsVoipAudioMode());
                ((RemoteConnection)object2).setRingbackRequested(parcelableConnection.isRingbackRequested());
                ((RemoteConnection)object2).putExtras(parcelableConnection.getExtras());
            }
        }

        @Override
        public void onConnectionEvent(String string2, String string3, Bundle bundle, Session.Info info) {
            if (RemoteConnectionService.this.mConnectionById.containsKey(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "onConnectionEvent").onConnectionEvent(string3, bundle);
            }
        }

        @Override
        public void onConnectionServiceFocusReleased(Session.Info info) {
        }

        @Override
        public void onPhoneAccountChanged(String string2, PhoneAccountHandle phoneAccountHandle, Session.Info info) {
        }

        @Override
        public void onPostDialChar(String string2, char c, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "onPostDialChar").onPostDialChar(c);
        }

        @Override
        public void onPostDialWait(String string2, String string3, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "onPostDialWait").setPostDialWait(string3);
        }

        @Override
        public void onRemoteRttRequest(String string2, Session.Info info) throws RemoteException {
            if (RemoteConnectionService.this.hasConnection(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "onRemoteRttRequest").onRemoteRttRequest();
            } else {
                Log.w(this, "onRemoteRttRequest called on a remote conference", new Object[0]);
            }
        }

        @Override
        public void onRttInitiationFailure(String string2, int n, Session.Info info) throws RemoteException {
            if (RemoteConnectionService.this.hasConnection(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "onRttInitiationFailure").onRttInitiationFailure(n);
            } else {
                Log.w(this, "onRttInitiationFailure called on a remote conference", new Object[0]);
            }
        }

        @Override
        public void onRttInitiationSuccess(String string2, Session.Info info) throws RemoteException {
            if (RemoteConnectionService.this.hasConnection(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "onRttInitiationSuccess").onRttInitiationSuccess();
            } else {
                Log.w(this, "onRttInitiationSuccess called on a remote conference", new Object[0]);
            }
        }

        @Override
        public void onRttSessionRemotelyTerminated(String string2, Session.Info info) throws RemoteException {
            if (RemoteConnectionService.this.hasConnection(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "onRttSessionRemotelyTerminated").onRttSessionRemotelyTerminated();
            } else {
                Log.w(this, "onRttSessionRemotelyTerminated called on a remote conference", new Object[0]);
            }
        }

        @Override
        public void putExtras(String string2, Bundle bundle, Session.Info info) {
            if (RemoteConnectionService.this.hasConnection(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "putExtras").putExtras(bundle);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "putExtras").putExtras(bundle);
            }
        }

        @Override
        public void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String string2, Session.Info info) {
        }

        @Override
        public void removeCall(String string2, Session.Info info) {
            if (RemoteConnectionService.this.mConnectionById.containsKey(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "removeCall").setDestroyed();
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "removeCall").setDestroyed();
            }
        }

        @Override
        public void removeExtras(String string2, List<String> list, Session.Info info) {
            if (RemoteConnectionService.this.hasConnection(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "removeExtra").removeExtras(list);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "removeExtra").removeExtras(list);
            }
        }

        @Override
        public void resetConnectionTime(String string2, Session.Info info) {
        }

        @Override
        public void setActive(String string2, Session.Info info) {
            if (RemoteConnectionService.this.mConnectionById.containsKey(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "setActive").setState(4);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "setActive").setState(4);
            }
        }

        @Override
        public void setAddress(String string2, Uri uri, int n, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setAddress").setAddress(uri, n);
        }

        @Override
        public void setAudioRoute(String string2, int n, String string3, Session.Info info) {
            RemoteConnectionService.this.hasConnection(string2);
        }

        @Override
        public void setCallerDisplayName(String string2, String string3, int n, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setCallerDisplayName").setCallerDisplayName(string3, n);
        }

        @Override
        public void setConferenceMergeFailed(String string2, Session.Info info) {
        }

        @Override
        public void setConferenceState(String string2, boolean bl, Session.Info info) {
        }

        @Override
        public final void setConferenceableConnections(String string2, List<String> object, Session.Info object2) {
            object2 = new ArrayList();
            object = object.iterator();
            while (object.hasNext()) {
                String string3 = (String)object.next();
                if (!RemoteConnectionService.this.mConnectionById.containsKey(string3)) continue;
                object2.add((RemoteConnection)RemoteConnectionService.this.mConnectionById.get(string3));
            }
            if (RemoteConnectionService.this.hasConnection(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "setConferenceableConnections").setConferenceableConnections((List<RemoteConnection>)object2);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "setConferenceableConnections").setConferenceableConnections((List<RemoteConnection>)object2);
            }
        }

        @Override
        public void setConnectionCapabilities(String string2, int n, Session.Info info) {
            if (RemoteConnectionService.this.mConnectionById.containsKey(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "setConnectionCapabilities").setConnectionCapabilities(n);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "setConnectionCapabilities").setConnectionCapabilities(n);
            }
        }

        @Override
        public void setConnectionProperties(String string2, int n, Session.Info info) {
            if (RemoteConnectionService.this.mConnectionById.containsKey(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "setConnectionProperties").setConnectionProperties(n);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "setConnectionProperties").setConnectionProperties(n);
            }
        }

        @Override
        public void setDialing(String string2, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setDialing").setState(3);
        }

        @Override
        public void setDisconnected(String string2, DisconnectCause disconnectCause, Session.Info info) {
            if (RemoteConnectionService.this.mConnectionById.containsKey(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "setDisconnected").setDisconnected(disconnectCause);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "setDisconnected").setDisconnected(disconnectCause);
            }
        }

        @Override
        public void setIsConferenced(String object, String object2, Session.Info info) {
            if ((object = RemoteConnectionService.this.findConnectionForAction((String)object, "setIsConferenced")) != NULL_CONNECTION) {
                if (object2 == null) {
                    if (((RemoteConnection)object).getConference() != null) {
                        ((RemoteConnection)object).getConference().removeConnection((RemoteConnection)object);
                    }
                } else if ((object2 = RemoteConnectionService.this.findConferenceForAction((String)object2, "setIsConferenced")) != NULL_CONFERENCE) {
                    ((RemoteConference)object2).addConnection((RemoteConnection)object);
                }
            }
        }

        @Override
        public void setIsVoipAudioMode(String string2, boolean bl, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setIsVoipAudioMode").setIsVoipAudioMode(bl);
        }

        @Override
        public void setOnHold(String string2, Session.Info info) {
            if (RemoteConnectionService.this.mConnectionById.containsKey(string2)) {
                RemoteConnectionService.this.findConnectionForAction(string2, "setOnHold").setState(5);
            } else {
                RemoteConnectionService.this.findConferenceForAction(string2, "setOnHold").setState(5);
            }
        }

        @Override
        public void setPulling(String string2, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setPulling").setState(7);
        }

        @Override
        public void setRingbackRequested(String string2, boolean bl, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setRingbackRequested").setRingbackRequested(bl);
        }

        @Override
        public void setRinging(String string2, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setRinging").setState(2);
        }

        @Override
        public void setStatusHints(String string2, StatusHints statusHints, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setStatusHints").setStatusHints(statusHints);
        }

        @Override
        public void setVideoProvider(String string2, IVideoProvider iVideoProvider, Session.Info object) {
            String string3 = RemoteConnectionService.this.mOurConnectionServiceImpl.getApplicationContext().getOpPackageName();
            int n = RemoteConnectionService.access$900((RemoteConnectionService)RemoteConnectionService.this).getApplicationInfo().targetSdkVersion;
            object = null;
            if (iVideoProvider != null) {
                object = new RemoteConnection.VideoProvider(iVideoProvider, string3, n);
            }
            RemoteConnectionService.this.findConnectionForAction(string2, "setVideoProvider").setVideoProvider((RemoteConnection.VideoProvider)object);
        }

        @Override
        public void setVideoState(String string2, int n, Session.Info info) {
            RemoteConnectionService.this.findConnectionForAction(string2, "setVideoState").setVideoState(n);
        }

    };

    static {
        NULL_CONNECTION = new RemoteConnection("NULL", null, null);
        NULL_CONFERENCE = new RemoteConference("NULL", null);
    }

    RemoteConnectionService(IConnectionService iConnectionService, ConnectionService connectionService) throws RemoteException {
        this.mOutgoingConnectionServiceRpc = iConnectionService;
        this.mOutgoingConnectionServiceRpc.asBinder().linkToDeath(this.mDeathRecipient, 0);
        this.mOurConnectionServiceImpl = connectionService;
    }

    private RemoteConference findConferenceForAction(String string2, String string3) {
        if (this.mConferenceById.containsKey(string2)) {
            return this.mConferenceById.get(string2);
        }
        Log.w(this, "%s - Cannot find Conference %s", string3, string2);
        return NULL_CONFERENCE;
    }

    private RemoteConnection findConnectionForAction(String string2, String string3) {
        if (this.mConnectionById.containsKey(string2)) {
            return this.mConnectionById.get(string2);
        }
        Log.w(this, "%s - Cannot find Connection %s", string3, string2);
        return NULL_CONNECTION;
    }

    private boolean hasConnection(String string2) {
        return this.mConnectionById.containsKey(string2);
    }

    private void maybeDisconnectAdapter() {
        if (this.mConnectionById.isEmpty() && this.mConferenceById.isEmpty()) {
            try {
                this.mOutgoingConnectionServiceRpc.removeConnectionServiceAdapter(this.mServant.getStub(), null);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
    }

    final RemoteConnection createRemoteConnection(PhoneAccountHandle callback, ConnectionRequest object, boolean bl) {
        final String string2 = UUID.randomUUID().toString();
        ConnectionRequest connectionRequest = new ConnectionRequest.Builder().setAccountHandle(((ConnectionRequest)object).getAccountHandle()).setAddress(((ConnectionRequest)object).getAddress()).setExtras(((ConnectionRequest)object).getExtras()).setVideoState(((ConnectionRequest)object).getVideoState()).setRttPipeFromInCall(((ConnectionRequest)object).getRttPipeFromInCall()).setRttPipeToInCall(((ConnectionRequest)object).getRttPipeToInCall()).build();
        try {
            if (this.mConnectionById.isEmpty()) {
                this.mOutgoingConnectionServiceRpc.addConnectionServiceAdapter(this.mServant.getStub(), null);
            }
            object = new RemoteConnection(string2, this.mOutgoingConnectionServiceRpc, connectionRequest);
            this.mPendingConnections.add((RemoteConnection)object);
            this.mConnectionById.put(string2, (RemoteConnection)object);
            this.mOutgoingConnectionServiceRpc.createConnection((PhoneAccountHandle)((Object)callback), string2, connectionRequest, bl, false, null);
            callback = new RemoteConnection.Callback(){

                @Override
                public void onDestroyed(RemoteConnection remoteConnection) {
                    RemoteConnectionService.this.mConnectionById.remove(string2);
                    RemoteConnectionService.this.maybeDisconnectAdapter();
                }
            };
            ((RemoteConnection)object).registerCallback(callback);
            return object;
        }
        catch (RemoteException remoteException) {
            return RemoteConnection.failure(new DisconnectCause(1, remoteException.toString()));
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[RemoteCS - ");
        stringBuilder.append(this.mOutgoingConnectionServiceRpc.asBinder().toString());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

}

