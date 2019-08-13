/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.AudioState;
import android.telecom.Call;
import android.telecom.CallAudioState;
import android.telecom.Conferenceable;
import android.telecom.Connection;
import android.telecom.DisconnectCause;
import android.telecom.Log;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import android.telephony.ServiceState;
import android.util.ArraySet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class Conference
extends Conferenceable {
    public static final long CONNECT_TIME_NOT_SPECIFIED = 0L;
    private Uri mAddress;
    private int mAddressPresentation;
    private CallAudioState mCallAudioState;
    private String mCallerDisplayName;
    private int mCallerDisplayNamePresentation;
    private final List<Connection> mChildConnections = new CopyOnWriteArrayList<Connection>();
    private final List<Connection> mConferenceableConnections = new ArrayList<Connection>();
    private long mConnectTimeMillis = 0L;
    private int mConnectionCapabilities;
    private final Connection.Listener mConnectionDeathListener = new Connection.Listener(){

        @Override
        public void onDestroyed(Connection connection) {
            if (Conference.this.mConferenceableConnections.remove(connection)) {
                Conference.this.fireOnConferenceableConnectionsChanged();
            }
        }
    };
    private int mConnectionProperties;
    private long mConnectionStartElapsedRealTime = 0L;
    private DisconnectCause mDisconnectCause;
    private String mDisconnectMessage;
    private Bundle mExtras;
    private final Object mExtrasLock = new Object();
    private final Set<Listener> mListeners = new CopyOnWriteArraySet<Listener>();
    private PhoneAccountHandle mPhoneAccount;
    private Set<String> mPreviousExtraKeys;
    private int mState = 1;
    private StatusHints mStatusHints;
    private String mTelecomCallId;
    private final List<Connection> mUnmodifiableChildConnections = Collections.unmodifiableList(this.mChildConnections);
    private final List<Connection> mUnmodifiableConferenceableConnections = Collections.unmodifiableList(this.mConferenceableConnections);

    public Conference(PhoneAccountHandle phoneAccountHandle) {
        this.mPhoneAccount = phoneAccountHandle;
    }

    public static boolean can(int n, int n2) {
        boolean bl = (n & n2) != 0;
        return bl;
    }

    private final void clearConferenceableList() {
        Iterator<Connection> iterator = this.mConferenceableConnections.iterator();
        while (iterator.hasNext()) {
            iterator.next().removeConnectionListener(this.mConnectionDeathListener);
        }
        this.mConferenceableConnections.clear();
    }

    private final void fireOnConferenceableConnectionsChanged() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceableConnectionsChanged(this, this.getConferenceableConnections());
        }
    }

    private void setState(int n) {
        if (n != 4 && n != 5 && n != 6) {
            Log.w(this, "Unsupported state transition for Conference call.", Connection.stateToString(n));
            return;
        }
        if (this.mState != n) {
            int n2 = this.mState;
            this.mState = n;
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onStateChanged(this, n2, n);
            }
        }
    }

    public void addCapability(int n) {
        this.setConnectionCapabilities(this.mConnectionCapabilities | n);
    }

    public final boolean addConnection(Connection connection) {
        Log.d(this, "Connection=%s, connection=", connection);
        if (connection != null && !this.mChildConnections.contains(connection) && connection.setConference(this)) {
            this.mChildConnections.add(connection);
            this.onConnectionAdded(connection);
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onConnectionAdded(this, connection);
            }
            return true;
        }
        return false;
    }

    public final Conference addListener(Listener listener) {
        this.mListeners.add(listener);
        return this;
    }

    public boolean can(int n) {
        return Conference.can(this.mConnectionCapabilities, n);
    }

    public final void destroy() {
        Log.d(this, "destroying conference : %s", this);
        for (Connection connection : this.mChildConnections) {
            Log.d(this, "removing connection %s", connection);
            this.removeConnection(connection);
        }
        if (this.mState != 6) {
            Log.d(this, "setting to disconnected", new Object[0]);
            this.setDisconnected(new DisconnectCause(2));
        }
        Iterator<Object> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            ((Listener)iterator.next()).onDestroyed(this);
        }
    }

    public final Uri getAddress() {
        return this.mAddress;
    }

    public final int getAddressPresentation() {
        return this.mAddressPresentation;
    }

    @SystemApi
    @Deprecated
    public final AudioState getAudioState() {
        return new AudioState(this.mCallAudioState);
    }

    public final CallAudioState getCallAudioState() {
        return this.mCallAudioState;
    }

    public final int getCallRadioTech() {
        int n = 0;
        Bundle bundle = this.getExtras();
        if (bundle != null) {
            n = bundle.getInt("android.telecom.extra.CALL_NETWORK_TYPE", 0);
        }
        return ServiceState.networkTypeToRilRadioTechnology(n);
    }

    public final String getCallerDisplayName() {
        return this.mCallerDisplayName;
    }

    public final int getCallerDisplayNamePresentation() {
        return this.mCallerDisplayNamePresentation;
    }

    public final List<Connection> getConferenceableConnections() {
        return this.mUnmodifiableConferenceableConnections;
    }

    @SystemApi
    @Deprecated
    public final long getConnectTimeMillis() {
        return this.getConnectionTime();
    }

    public final int getConnectionCapabilities() {
        return this.mConnectionCapabilities;
    }

    public final int getConnectionProperties() {
        return this.mConnectionProperties;
    }

    public final long getConnectionStartElapsedRealTime() {
        return this.mConnectionStartElapsedRealTime;
    }

    public final long getConnectionTime() {
        return this.mConnectTimeMillis;
    }

    public final List<Connection> getConnections() {
        return this.mUnmodifiableChildConnections;
    }

    public final DisconnectCause getDisconnectCause() {
        return this.mDisconnectCause;
    }

    public final Bundle getExtras() {
        return this.mExtras;
    }

    public final PhoneAccountHandle getPhoneAccountHandle() {
        return this.mPhoneAccount;
    }

    @SystemApi
    public Connection getPrimaryConnection() {
        List<Connection> list = this.mUnmodifiableChildConnections;
        if (list != null && !list.isEmpty()) {
            return this.mUnmodifiableChildConnections.get(0);
        }
        return null;
    }

    public final int getState() {
        return this.mState;
    }

    public final StatusHints getStatusHints() {
        return this.mStatusHints;
    }

    public final String getTelecomCallId() {
        return this.mTelecomCallId;
    }

    public Connection.VideoProvider getVideoProvider() {
        return null;
    }

    public int getVideoState() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    final void handleExtrasChanged(Bundle bundle) {
        Object var2_2 = null;
        Object object = this.mExtrasLock;
        synchronized (object) {
            this.mExtras = bundle;
            bundle = var2_2;
            if (this.mExtras != null) {
                bundle = new Bundle(this.mExtras);
            }
        }
        this.onExtrasChanged(bundle);
    }

    @SystemApi
    @Deprecated
    public void onAudioStateChanged(AudioState audioState) {
    }

    public void onCallAudioStateChanged(CallAudioState callAudioState) {
    }

    public void onConnectionAdded(Connection connection) {
    }

    public void onDisconnect() {
    }

    public void onExtrasChanged(Bundle bundle) {
    }

    public void onHold() {
    }

    public void onMerge() {
    }

    public void onMerge(Connection connection) {
    }

    public void onPlayDtmfTone(char c) {
    }

    public void onSeparate(Connection connection) {
    }

    public void onStopDtmfTone() {
    }

    public void onSwap() {
    }

    public void onUnhold() {
    }

    public final void putExtra(String string2, int n) {
        Bundle bundle = new Bundle();
        bundle.putInt(string2, n);
        this.putExtras(bundle);
    }

    public final void putExtra(String string2, String string3) {
        Bundle bundle = new Bundle();
        bundle.putString(string2, string3);
        this.putExtras(bundle);
    }

    public final void putExtra(String string2, boolean bl) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(string2, bl);
        this.putExtras(bundle);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void putExtras(Bundle bundle) {
        if (bundle == null) {
            return;
        }
        Iterator<Listener> iterator = this.mExtrasLock;
        synchronized (iterator) {
            if (this.mExtras == null) {
                Bundle bundle2;
                this.mExtras = bundle2 = new Bundle();
            }
            this.mExtras.putAll(bundle);
            bundle = new Bundle(this.mExtras);
        }
        iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onExtrasChanged(this, new Bundle(bundle));
        }
        return;
    }

    public void removeCapability(int n) {
        this.setConnectionCapabilities(this.mConnectionCapabilities & n);
    }

    public final void removeConnection(Connection connection) {
        Log.d(this, "removing %s from %s", connection, this.mChildConnections);
        if (connection != null && this.mChildConnections.remove(connection)) {
            connection.resetConference();
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onConnectionRemoved(this, connection);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void removeExtras(List<String> object) {
        if (object == null) return;
        if (object.isEmpty()) {
            return;
        }
        List<String> list = this.mExtrasLock;
        synchronized (list) {
            if (this.mExtras != null) {
                Iterator<String> iterator = object.iterator();
                while (iterator.hasNext()) {
                    String string2 = iterator.next();
                    this.mExtras.remove(string2);
                }
            }
        }
        list = Collections.unmodifiableList(object);
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onExtrasRemoved(this, list);
        }
    }

    public final void removeExtras(String ... arrstring) {
        this.removeExtras(Arrays.asList(arrstring));
    }

    public final Conference removeListener(Listener listener) {
        this.mListeners.remove(listener);
        return this;
    }

    public void sendConnectionEvent(String string2, Bundle bundle) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConnectionEvent(this, string2, bundle);
        }
    }

    public final void setActive() {
        this.setState(4);
    }

    public final void setAddress(Uri uri, int n) {
        Log.d(this, "setAddress %s", uri);
        this.mAddress = uri;
        this.mAddressPresentation = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onAddressChanged(this, uri, n);
        }
    }

    final void setCallAudioState(CallAudioState callAudioState) {
        Log.d(this, "setCallAudioState %s", callAudioState);
        this.mCallAudioState = callAudioState;
        this.onAudioStateChanged(this.getAudioState());
        this.onCallAudioStateChanged(callAudioState);
    }

    public final void setCallRadioTech(int n) {
        this.putExtra("android.telecom.extra.CALL_NETWORK_TYPE", ServiceState.rilRadioTechnologyToNetworkType(n));
    }

    public final void setCallerDisplayName(String string2, int n) {
        Log.d(this, "setCallerDisplayName %s", string2);
        this.mCallerDisplayName = string2;
        this.mCallerDisplayNamePresentation = n;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onCallerDisplayNameChanged(this, string2, n);
        }
    }

    public void setConferenceState(boolean bl) {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onConferenceStateChanged(this, bl);
        }
    }

    public final void setConferenceableConnections(List<Connection> object) {
        this.clearConferenceableList();
        Iterator<Connection> iterator = object.iterator();
        while (iterator.hasNext()) {
            object = iterator.next();
            if (this.mConferenceableConnections.contains(object)) continue;
            ((Connection)object).addConnectionListener(this.mConnectionDeathListener);
            this.mConferenceableConnections.add((Connection)object);
        }
        this.fireOnConferenceableConnectionsChanged();
    }

    @SystemApi
    @Deprecated
    public final void setConnectTimeMillis(long l) {
        this.setConnectionTime(l);
    }

    public final void setConnectionCapabilities(int n) {
        if (n != this.mConnectionCapabilities) {
            this.mConnectionCapabilities = n;
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onConnectionCapabilitiesChanged(this, this.mConnectionCapabilities);
            }
        }
    }

    public final void setConnectionProperties(int n) {
        if (n != this.mConnectionProperties) {
            this.mConnectionProperties = n;
            Iterator<Listener> iterator = this.mListeners.iterator();
            while (iterator.hasNext()) {
                iterator.next().onConnectionPropertiesChanged(this, this.mConnectionProperties);
            }
        }
    }

    public final void setConnectionStartElapsedRealTime(long l) {
        this.mConnectionStartElapsedRealTime = l;
    }

    public final void setConnectionTime(long l) {
        this.mConnectTimeMillis = l;
    }

    public final void setDialing() {
        this.setState(3);
    }

    public final void setDisconnected(DisconnectCause object) {
        this.mDisconnectCause = object;
        this.setState(6);
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onDisconnected(this, this.mDisconnectCause);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final void setExtras(Bundle bundle) {
        Object object = this.mExtrasLock;
        synchronized (object) {
            Collection<String> collection;
            this.putExtras(bundle);
            if (this.mPreviousExtraKeys != null) {
                collection = new Collection<String>();
                for (String string2 : this.mPreviousExtraKeys) {
                    if (bundle != null && bundle.containsKey(string2)) continue;
                    collection.add(string2);
                }
                if (!collection.isEmpty()) {
                    this.removeExtras((List<String>)collection);
                }
            }
            if (this.mPreviousExtraKeys == null) {
                collection = new Collection<String>();
                this.mPreviousExtraKeys = collection;
            }
            this.mPreviousExtraKeys.clear();
            if (bundle != null) {
                this.mPreviousExtraKeys.addAll(bundle.keySet());
            }
            return;
        }
    }

    public final void setOnHold() {
        this.setState(5);
    }

    public final void setStatusHints(StatusHints statusHints) {
        this.mStatusHints = statusHints;
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onStatusHintsChanged(this, statusHints);
        }
    }

    public final void setTelecomCallId(String string2) {
        this.mTelecomCallId = string2;
    }

    public final void setVideoProvider(Connection object, Connection.VideoProvider videoProvider) {
        Log.d(this, "setVideoProvider Conference: %s Connection: %s VideoState: %s", this, object, videoProvider);
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onVideoProviderChanged(this, videoProvider);
        }
    }

    public final void setVideoState(Connection object, int n) {
        Log.d(this, "setVideoState Conference: %s Connection: %s VideoState: %s", this, object, n);
        object = this.mListeners.iterator();
        while (object.hasNext()) {
            ((Listener)object.next()).onVideoStateChanged(this, n);
        }
    }

    public String toString() {
        return String.format(Locale.US, "[State: %s,Capabilites: %s, VideoState: %s, VideoProvider: %s, ThisObject %s]", Connection.stateToString(this.mState), Call.Details.capabilitiesToString(this.mConnectionCapabilities), this.getVideoState(), this.getVideoProvider(), Object.super.toString());
    }

    public void updateCallRadioTechAfterCreation() {
        Connection connection = this.getPrimaryConnection();
        if (connection != null) {
            this.setCallRadioTech(connection.getCallRadioTech());
        } else {
            Log.w(this, "No primary connection found while updateCallRadioTechAfterCreation", new Object[0]);
        }
    }

    public static abstract class Listener {
        public void onAddressChanged(Conference conference, Uri uri, int n) {
        }

        public void onCallerDisplayNameChanged(Conference conference, String string2, int n) {
        }

        public void onConferenceStateChanged(Conference conference, boolean bl) {
        }

        public void onConferenceableConnectionsChanged(Conference conference, List<Connection> list) {
        }

        public void onConnectionAdded(Conference conference, Connection connection) {
        }

        public void onConnectionCapabilitiesChanged(Conference conference, int n) {
        }

        public void onConnectionEvent(Conference conference, String string2, Bundle bundle) {
        }

        public void onConnectionPropertiesChanged(Conference conference, int n) {
        }

        public void onConnectionRemoved(Conference conference, Connection connection) {
        }

        public void onDestroyed(Conference conference) {
        }

        public void onDisconnected(Conference conference, DisconnectCause disconnectCause) {
        }

        public void onExtrasChanged(Conference conference, Bundle bundle) {
        }

        public void onExtrasRemoved(Conference conference, List<String> list) {
        }

        public void onStateChanged(Conference conference, int n, int n2) {
        }

        public void onStatusHintsChanged(Conference conference, StatusHints statusHints) {
        }

        public void onVideoProviderChanged(Conference conference, Connection.VideoProvider videoProvider) {
        }

        public void onVideoStateChanged(Conference conference, int n) {
        }
    }

}

