/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.DisconnectCause;
import android.telecom.Log;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConference;
import android.telecom.ParcelableConnection;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

final class ConnectionServiceAdapter
implements IBinder.DeathRecipient {
    private final Set<IConnectionServiceAdapter> mAdapters = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));

    ConnectionServiceAdapter() {
    }

    void addAdapter(IConnectionServiceAdapter iConnectionServiceAdapter) {
        Iterator<IConnectionServiceAdapter> iterator = this.mAdapters.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().asBinder() != iConnectionServiceAdapter.asBinder()) continue;
            Log.w(this, "Ignoring duplicate adapter addition.", new Object[0]);
            return;
        }
        if (this.mAdapters.add(iConnectionServiceAdapter)) {
            try {
                iConnectionServiceAdapter.asBinder().linkToDeath(this, 0);
            }
            catch (RemoteException remoteException) {
                this.mAdapters.remove(iConnectionServiceAdapter);
            }
        }
    }

    void addConferenceCall(String string2, ParcelableConference parcelableConference) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.addConferenceCall(string2, parcelableConference, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void addExistingConnection(String string2, ParcelableConnection parcelableConnection) {
        Log.v(this, "addExistingConnection: %s", string2);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.addExistingConnection(string2, parcelableConnection, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    @Override
    public void binderDied() {
        Iterator<IConnectionServiceAdapter> iterator = this.mAdapters.iterator();
        while (iterator.hasNext()) {
            IConnectionServiceAdapter iConnectionServiceAdapter = iterator.next();
            if (iConnectionServiceAdapter.asBinder().isBinderAlive()) continue;
            iterator.remove();
            iConnectionServiceAdapter.asBinder().unlinkToDeath(this, 0);
        }
    }

    void handleCreateConnectionComplete(String string2, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.handleCreateConnectionComplete(string2, connectionRequest, parcelableConnection, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onConferenceMergeFailed(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "merge failed for call %s", string2);
                iConnectionServiceAdapter.setConferenceMergeFailed(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onConnectionEvent(String string2, String string3, Bundle bundle) {
        Log.v(this, "onConnectionEvent: %s", string3);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.onConnectionEvent(string2, string3, bundle, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onConnectionServiceFocusReleased() {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "onConnectionServiceFocusReleased", new Object[0]);
                iConnectionServiceAdapter.onConnectionServiceFocusReleased(Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onPhoneAccountChanged(String string2, PhoneAccountHandle phoneAccountHandle) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "onPhoneAccountChanged %s", string2);
                iConnectionServiceAdapter.onPhoneAccountChanged(string2, phoneAccountHandle, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onPostDialChar(String string2, char c) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.onPostDialChar(string2, c, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onPostDialWait(String string2, String string3) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.onPostDialWait(string2, string3, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onRemoteRttRequest(String string2) {
        Log.v(this, "onRemoteRttRequest: %s", string2);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.onRemoteRttRequest(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onRttInitiationFailure(String string2, int n) {
        Log.v(this, "onRttInitiationFailure: %s", string2);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.onRttInitiationFailure(string2, n, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onRttInitiationSuccess(String string2) {
        Log.v(this, "onRttInitiationSuccess: %s", string2);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.onRttInitiationSuccess(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void onRttSessionRemotelyTerminated(String string2) {
        Log.v(this, "onRttSessionRemotelyTerminated: %s", string2);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.onRttSessionRemotelyTerminated(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void putExtra(String string2, String string3, int n) {
        Log.v(this, "putExtra: %s %s=%d", string2, string3, n);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putInt(string3, n);
                iConnectionServiceAdapter.putExtras(string2, bundle, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void putExtra(String string2, String string3, String string4) {
        Log.v(this, "putExtra: %s %s=%s", string2, string3, string4);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putString(string3, string4);
                iConnectionServiceAdapter.putExtras(string2, bundle, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void putExtra(String string2, String string3, boolean bl) {
        Log.v(this, "putExtra: %s %s=%b", string2, string3, bl);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Bundle bundle = new Bundle();
                bundle.putBoolean(string3, bl);
                iConnectionServiceAdapter.putExtras(string2, bundle, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void putExtras(String string2, Bundle bundle) {
        Log.v(this, "putExtras: %s", string2);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.putExtras(string2, bundle, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String string2) {
        if (this.mAdapters.size() == 1) {
            try {
                this.mAdapters.iterator().next().queryRemoteConnectionServices(remoteServiceCallback, string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {
                Log.e(this, (Throwable)remoteException, "Exception trying to query for remote CSs", new Object[0]);
            }
        } else {
            try {
                remoteServiceCallback.onResult(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
            }
            catch (RemoteException remoteException) {
                Log.e(this, (Throwable)remoteException, "Exception trying to query for remote CSs", new Object[0]);
            }
        }
    }

    void removeAdapter(IConnectionServiceAdapter iConnectionServiceAdapter) {
        if (iConnectionServiceAdapter != null) {
            for (IConnectionServiceAdapter iConnectionServiceAdapter2 : this.mAdapters) {
                if (iConnectionServiceAdapter2.asBinder() != iConnectionServiceAdapter.asBinder() || !this.mAdapters.remove(iConnectionServiceAdapter2)) continue;
                iConnectionServiceAdapter.asBinder().unlinkToDeath(this, 0);
                break;
            }
        }
    }

    void removeCall(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.removeCall(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void removeExtras(String string2, List<String> list) {
        Log.v(this, "removeExtras: %s %s", string2, list);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.removeExtras(string2, list, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void resetConnectionTime(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.resetConnectionTime(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setActive(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setActive(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setAddress(String string2, Uri uri, int n) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setAddress(string2, uri, n, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setAudioRoute(String string2, int n, String string3) {
        Log.v(this, "setAudioRoute: %s %s %s", string2, CallAudioState.audioRouteToString(n), string3);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setAudioRoute(string2, n, string3, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setCallerDisplayName(String string2, String string3, int n) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setCallerDisplayName(string2, string3, n, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setConferenceState(String string2, boolean bl) {
        Log.v(this, "setConferenceState: %s %b", string2, bl);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setConferenceState(string2, bl, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setConferenceableConnections(String string2, List<String> list) {
        Log.v(this, "setConferenceableConnections: %s, %s", string2, list);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setConferenceableConnections(string2, list, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setConnectionCapabilities(String string2, int n) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setConnectionCapabilities(string2, n, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setConnectionProperties(String string2, int n) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setConnectionProperties(string2, n, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setDialing(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setDialing(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setDisconnected(String string2, DisconnectCause disconnectCause) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setDisconnected(string2, disconnectCause, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setIsConferenced(String string2, String string3) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                Log.d(this, "sending connection %s with conference %s", string2, string3);
                iConnectionServiceAdapter.setIsConferenced(string2, string3, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setIsVoipAudioMode(String string2, boolean bl) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setIsVoipAudioMode(string2, bl, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setOnHold(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setOnHold(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setPulling(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setPulling(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setRingbackRequested(String string2, boolean bl) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setRingbackRequested(string2, bl, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setRinging(String string2) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setRinging(string2, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    void setStatusHints(String string2, StatusHints statusHints) {
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setStatusHints(string2, statusHints, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void setVideoProvider(String var1_1, Connection.VideoProvider var2_2) {
        var3_3 = this.mAdapters.iterator();
        while (var3_3.hasNext() != false) {
            block3 : {
                var4_4 = var3_3.next();
                if (var2_2 != null) break block3;
                var5_5 = null;
                ** GOTO lbl10
            }
            try {
                var5_5 = var2_2.getInterface();
lbl10: // 2 sources:
                var4_4.setVideoProvider(var1_1, var5_5, Log.getExternalSession());
            }
            catch (RemoteException var5_6) {}
        }
    }

    void setVideoState(String string2, int n) {
        Log.v(this, "setVideoState: %d", n);
        for (IConnectionServiceAdapter iConnectionServiceAdapter : this.mAdapters) {
            try {
                iConnectionServiceAdapter.setVideoState(string2, n, Log.getExternalSession());
            }
            catch (RemoteException remoteException) {}
        }
    }
}

