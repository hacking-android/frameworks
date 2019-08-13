/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.Conference;
import android.telecom.Conferenceable;
import android.telecom.Connection;
import android.telecom.ConnectionRequest;
import android.telecom.ConnectionServiceAdapter;
import android.telecom.DisconnectCause;
import android.telecom.Log;
import android.telecom.Logging.Runnable;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConference;
import android.telecom.ParcelableConnection;
import android.telecom.PhoneAccountHandle;
import android.telecom.RemoteConference;
import android.telecom.RemoteConnection;
import android.telecom.RemoteConnectionManager;
import android.telecom.StatusHints;
import com.android.internal.os.SomeArgs;
import com.android.internal.telecom.IConnectionService;
import com.android.internal.telecom.IConnectionServiceAdapter;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class ConnectionService
extends Service {
    public static final String EXTRA_IS_HANDOVER = "android.telecom.extra.IS_HANDOVER";
    private static final int MSG_ABORT = 3;
    private static final int MSG_ADD_CONNECTION_SERVICE_ADAPTER = 1;
    private static final int MSG_ANSWER = 4;
    private static final int MSG_ANSWER_VIDEO = 17;
    private static final int MSG_CONFERENCE = 12;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_GAINED = 31;
    private static final int MSG_CONNECTION_SERVICE_FOCUS_LOST = 30;
    private static final int MSG_CREATE_CONNECTION = 2;
    private static final int MSG_CREATE_CONNECTION_COMPLETE = 29;
    private static final int MSG_CREATE_CONNECTION_FAILED = 25;
    private static final int MSG_DEFLECT = 34;
    private static final int MSG_DISCONNECT = 6;
    private static final int MSG_HANDOVER_COMPLETE = 33;
    private static final int MSG_HANDOVER_FAILED = 32;
    private static final int MSG_HOLD = 7;
    private static final int MSG_MERGE_CONFERENCE = 18;
    private static final int MSG_ON_CALL_AUDIO_STATE_CHANGED = 9;
    private static final int MSG_ON_EXTRAS_CHANGED = 24;
    private static final int MSG_ON_POST_DIAL_CONTINUE = 14;
    private static final int MSG_ON_START_RTT = 26;
    private static final int MSG_ON_STOP_RTT = 27;
    private static final int MSG_PLAY_DTMF_TONE = 10;
    private static final int MSG_PULL_EXTERNAL_CALL = 22;
    private static final int MSG_REJECT = 5;
    private static final int MSG_REJECT_WITH_MESSAGE = 20;
    private static final int MSG_REMOVE_CONNECTION_SERVICE_ADAPTER = 16;
    private static final int MSG_RTT_UPGRADE_RESPONSE = 28;
    private static final int MSG_SEND_CALL_EVENT = 23;
    private static final int MSG_SILENCE = 21;
    private static final int MSG_SPLIT_FROM_CONFERENCE = 13;
    private static final int MSG_STOP_DTMF_TONE = 11;
    private static final int MSG_SWAP_CONFERENCE = 19;
    private static final int MSG_UNHOLD = 8;
    private static final boolean PII_DEBUG = Log.isLoggable(3);
    public static final String SERVICE_INTERFACE = "android.telecom.ConnectionService";
    private static final String SESSION_ABORT = "CS.ab";
    private static final String SESSION_ADD_CS_ADAPTER = "CS.aCSA";
    private static final String SESSION_ANSWER = "CS.an";
    private static final String SESSION_ANSWER_VIDEO = "CS.anV";
    private static final String SESSION_CALL_AUDIO_SC = "CS.cASC";
    private static final String SESSION_CONFERENCE = "CS.c";
    private static final String SESSION_CONNECTION_SERVICE_FOCUS_GAINED = "CS.cSFG";
    private static final String SESSION_CONNECTION_SERVICE_FOCUS_LOST = "CS.cSFL";
    private static final String SESSION_CREATE_CONN = "CS.crCo";
    private static final String SESSION_CREATE_CONN_COMPLETE = "CS.crCoC";
    private static final String SESSION_CREATE_CONN_FAILED = "CS.crCoF";
    private static final String SESSION_DEFLECT = "CS.def";
    private static final String SESSION_DISCONNECT = "CS.d";
    private static final String SESSION_EXTRAS_CHANGED = "CS.oEC";
    private static final String SESSION_HANDLER = "H.";
    private static final String SESSION_HANDOVER_COMPLETE = "CS.hC";
    private static final String SESSION_HANDOVER_FAILED = "CS.haF";
    private static final String SESSION_HOLD = "CS.h";
    private static final String SESSION_MERGE_CONFERENCE = "CS.mC";
    private static final String SESSION_PLAY_DTMF = "CS.pDT";
    private static final String SESSION_POST_DIAL_CONT = "CS.oPDC";
    private static final String SESSION_PULL_EXTERNAL_CALL = "CS.pEC";
    private static final String SESSION_REJECT = "CS.r";
    private static final String SESSION_REJECT_MESSAGE = "CS.rWM";
    private static final String SESSION_REMOVE_CS_ADAPTER = "CS.rCSA";
    private static final String SESSION_RTT_UPGRADE_RESPONSE = "CS.rTRUR";
    private static final String SESSION_SEND_CALL_EVENT = "CS.sCE";
    private static final String SESSION_SILENCE = "CS.s";
    private static final String SESSION_SPLIT_CONFERENCE = "CS.sFC";
    private static final String SESSION_START_RTT = "CS.+RTT";
    private static final String SESSION_STOP_DTMF = "CS.sDT";
    private static final String SESSION_STOP_RTT = "CS.-RTT";
    private static final String SESSION_SWAP_CONFERENCE = "CS.sC";
    private static final String SESSION_UNHOLD = "CS.u";
    private static final String SESSION_UPDATE_RTT_PIPES = "CS.uRTT";
    private static Connection sNullConnection;
    private final ConnectionServiceAdapter mAdapter = new ConnectionServiceAdapter();
    private boolean mAreAccountsInitialized = false;
    private final IBinder mBinder = new IConnectionService.Stub(){

        @Override
        public void abort(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_ABORT);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(3, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_ADD_CS_ADAPTER);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = iConnectionServiceAdapter;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(1, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void answer(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_ANSWER);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(4, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void answerVideo(String string2, int n, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_ANSWER_VIDEO);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ((SomeArgs)object).argi1 = n;
                ConnectionService.this.mHandler.obtainMessage(17, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void conference(String string2, String string3, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_CONFERENCE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = string3;
                ((SomeArgs)object).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(12, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void connectionServiceFocusGained(Session.Info info) throws RemoteException {
            Log.startSession(info, ConnectionService.SESSION_CONNECTION_SERVICE_FOCUS_GAINED);
            try {
                ConnectionService.this.mHandler.obtainMessage(31).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void connectionServiceFocusLost(Session.Info info) throws RemoteException {
            Log.startSession(info, ConnectionService.SESSION_CONNECTION_SERVICE_FOCUS_LOST);
            try {
                ConnectionService.this.mHandler.obtainMessage(30).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void createConnection(PhoneAccountHandle phoneAccountHandle, String string2, ConnectionRequest connectionRequest, boolean bl, boolean bl2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_CREATE_CONN);
            object = SomeArgs.obtain();
            ((SomeArgs)object).arg1 = phoneAccountHandle;
            ((SomeArgs)object).arg2 = string2;
            ((SomeArgs)object).arg3 = connectionRequest;
            ((SomeArgs)object).arg4 = Log.createSubsession();
            int n = 1;
            int n2 = bl ? 1 : 0;
            ((SomeArgs)object).argi1 = n2;
            n2 = bl2 ? n : 0;
            try {
                ((SomeArgs)object).argi2 = n2;
                ConnectionService.this.mHandler.obtainMessage(2, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void createConnectionComplete(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_CREATE_CONN_COMPLETE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(29, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String string2, ConnectionRequest connectionRequest, boolean bl, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_CREATE_CONN_FAILED);
            object = SomeArgs.obtain();
            ((SomeArgs)object).arg1 = string2;
            ((SomeArgs)object).arg2 = connectionRequest;
            ((SomeArgs)object).arg3 = Log.createSubsession();
            ((SomeArgs)object).arg4 = phoneAccountHandle;
            int n = bl ? 1 : 0;
            try {
                ((SomeArgs)object).argi1 = n;
                ConnectionService.this.mHandler.obtainMessage(25, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void deflect(String string2, Uri uri, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_DEFLECT);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = uri;
                ((SomeArgs)object).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(34, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void disconnect(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_DISCONNECT);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(6, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void handoverComplete(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_HANDOVER_COMPLETE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(33, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void handoverFailed(String string2, ConnectionRequest connectionRequest, int n, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_HANDOVER_FAILED);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = connectionRequest;
                ((SomeArgs)object).arg3 = Log.createSubsession();
                ((SomeArgs)object).arg4 = n;
                ConnectionService.this.mHandler.obtainMessage(32, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void hold(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_HOLD);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(7, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void mergeConference(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_MERGE_CONFERENCE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(18, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void onCallAudioStateChanged(String string2, CallAudioState callAudioState, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_CALL_AUDIO_SC);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = callAudioState;
                ((SomeArgs)object).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(9, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void onExtrasChanged(String string2, Bundle bundle, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_EXTRAS_CHANGED);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = bundle;
                ((SomeArgs)object).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(24, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void onPostDialContinue(String string2, boolean bl, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_POST_DIAL_CONT);
            object = SomeArgs.obtain();
            ((SomeArgs)object).arg1 = string2;
            ((SomeArgs)object).arg2 = Log.createSubsession();
            int n = bl ? 1 : 0;
            try {
                ((SomeArgs)object).argi1 = n;
                ConnectionService.this.mHandler.obtainMessage(14, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void playDtmfTone(String string2, char c, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_PLAY_DTMF);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = Character.valueOf(c);
                ((SomeArgs)object).arg2 = string2;
                ((SomeArgs)object).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(10, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void pullExternalCall(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_PULL_EXTERNAL_CALL);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(22, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void reject(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_REJECT);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(5, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void rejectWithMessage(String string2, String string3, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_REJECT_MESSAGE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = string3;
                ((SomeArgs)object).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(20, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_REMOVE_CS_ADAPTER);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = iConnectionServiceAdapter;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(16, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void respondToRttUpgradeRequest(String object, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info object2) throws RemoteException {
            Log.startSession((Session.Info)object2, ConnectionService.SESSION_RTT_UPGRADE_RESPONSE);
            try {
                object2 = SomeArgs.obtain();
                ((SomeArgs)object2).arg1 = object;
                ((SomeArgs)object2).arg2 = parcelFileDescriptor2 != null && parcelFileDescriptor != null ? (object = new Connection.RttTextStream(parcelFileDescriptor2, parcelFileDescriptor)) : null;
                ((SomeArgs)object2).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(28, object2).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void sendCallEvent(String string2, String string3, Bundle bundle, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_SEND_CALL_EVENT);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = string3;
                ((SomeArgs)object).arg3 = bundle;
                ((SomeArgs)object).arg4 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(23, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void silence(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_SILENCE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(21, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void splitFromConference(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_SPLIT_CONFERENCE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(13, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void startRtt(String object, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info object2) throws RemoteException {
            Log.startSession((Session.Info)object2, ConnectionService.SESSION_START_RTT);
            try {
                object2 = SomeArgs.obtain();
                ((SomeArgs)object2).arg1 = object;
                ((SomeArgs)object2).arg2 = object = new Connection.RttTextStream(parcelFileDescriptor2, parcelFileDescriptor);
                ((SomeArgs)object2).arg3 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(26, object2).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void stopDtmfTone(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_STOP_DTMF);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(11, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void stopRtt(String string2, Session.Info object) throws RemoteException {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_STOP_RTT);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(27, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void swapConference(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_SWAP_CONFERENCE);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(19, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }

        @Override
        public void unhold(String string2, Session.Info object) {
            Log.startSession((Session.Info)object, ConnectionService.SESSION_UNHOLD);
            try {
                object = SomeArgs.obtain();
                ((SomeArgs)object).arg1 = string2;
                ((SomeArgs)object).arg2 = Log.createSubsession();
                ConnectionService.this.mHandler.obtainMessage(8, object).sendToTarget();
                return;
            }
            finally {
                Log.endSession();
            }
        }
    };
    private final Map<String, Conference> mConferenceById = new ConcurrentHashMap<String, Conference>();
    private final Conference.Listener mConferenceListener = new Conference.Listener(){

        @Override
        public void onAddressChanged(Conference object, Uri uri, int n) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.setAddress((String)object, uri, n);
            }
        }

        @Override
        public void onCallerDisplayNameChanged(Conference object, String string2, int n) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.setCallerDisplayName((String)object, string2, n);
            }
        }

        @Override
        public void onConferenceStateChanged(Conference object, boolean bl) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.setConferenceState((String)object, bl);
            }
        }

        @Override
        public void onConferenceableConnectionsChanged(Conference conference, List<Connection> list) {
            ConnectionService.this.mAdapter.setConferenceableConnections((String)ConnectionService.this.mIdByConference.get(conference), ConnectionService.this.createConnectionIdList(list));
        }

        @Override
        public void onConnectionAdded(Conference conference, Connection connection) {
        }

        @Override
        public void onConnectionCapabilitiesChanged(Conference object, int n) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            Log.d(this, "call capabilities: conference: %s", Connection.capabilitiesToString(n));
            ConnectionService.this.mAdapter.setConnectionCapabilities((String)object, n);
        }

        @Override
        public void onConnectionEvent(Conference object, String string2, Bundle bundle) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onConnectionEvent((String)object, string2, bundle);
            }
        }

        @Override
        public void onConnectionPropertiesChanged(Conference object, int n) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            Log.d(this, "call capabilities: conference: %s", Connection.propertiesToString(n));
            ConnectionService.this.mAdapter.setConnectionProperties((String)object, n);
        }

        @Override
        public void onConnectionRemoved(Conference conference, Connection connection) {
        }

        @Override
        public void onDestroyed(Conference conference) {
            ConnectionService.this.removeConference(conference);
        }

        @Override
        public void onDisconnected(Conference object, DisconnectCause disconnectCause) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            ConnectionService.this.mAdapter.setDisconnected((String)object, disconnectCause);
        }

        @Override
        public void onExtrasChanged(Conference object, Bundle bundle) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.putExtras((String)object, bundle);
            }
        }

        @Override
        public void onExtrasRemoved(Conference object, List<String> list) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.removeExtras((String)object, list);
            }
        }

        @Override
        public void onStateChanged(Conference object, int n, int n2) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (n2 != 4) {
                if (n2 == 5) {
                    ConnectionService.this.mAdapter.setOnHold((String)object);
                }
            } else {
                ConnectionService.this.mAdapter.setActive((String)object);
            }
        }

        @Override
        public void onStatusHintsChanged(Conference object, StatusHints statusHints) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.setStatusHints((String)object, statusHints);
            }
        }

        @Override
        public void onVideoProviderChanged(Conference conference, Connection.VideoProvider videoProvider) {
            String string2 = (String)ConnectionService.this.mIdByConference.get(conference);
            Log.d(this, "onVideoProviderChanged: Connection: %s, VideoProvider: %s", conference, videoProvider);
            ConnectionService.this.mAdapter.setVideoProvider(string2, videoProvider);
        }

        @Override
        public void onVideoStateChanged(Conference object, int n) {
            object = (String)ConnectionService.this.mIdByConference.get(object);
            Log.d(this, "onVideoStateChanged set video state %d", n);
            ConnectionService.this.mAdapter.setVideoState((String)object, n);
        }
    };
    private final Map<String, Connection> mConnectionById = new ConcurrentHashMap<String, Connection>();
    private final Connection.Listener mConnectionListener = new Connection.Listener(){

        @Override
        public void onAddressChanged(Connection object, Uri uri, int n) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            ConnectionService.this.mAdapter.setAddress((String)object, uri, n);
        }

        @Override
        public void onAudioModeIsVoipChanged(Connection object, boolean bl) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            ConnectionService.this.mAdapter.setIsVoipAudioMode((String)object, bl);
        }

        @Override
        public void onAudioRouteChanged(Connection object, int n, String string2) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.setAudioRoute((String)object, n, string2);
            }
        }

        @Override
        public void onCallerDisplayNameChanged(Connection object, String string2, int n) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            ConnectionService.this.mAdapter.setCallerDisplayName((String)object, string2, n);
        }

        @Override
        public void onConferenceChanged(Connection object, Conference conference) {
            String string2 = (String)ConnectionService.this.mIdByConnection.get(object);
            if (string2 != null) {
                object = null;
                if (conference != null) {
                    object = (String)ConnectionService.this.mIdByConference.get(conference);
                }
                ConnectionService.this.mAdapter.setIsConferenced(string2, (String)object);
            }
        }

        @Override
        public void onConferenceMergeFailed(Connection object) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onConferenceMergeFailed((String)object);
            }
        }

        @Override
        public void onConferenceablesChanged(Connection connection, List<Conferenceable> list) {
            ConnectionService.this.mAdapter.setConferenceableConnections((String)ConnectionService.this.mIdByConnection.get(connection), ConnectionService.this.createIdList(list));
        }

        @Override
        public void onConnectionCapabilitiesChanged(Connection object, int n) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            Log.d(this, "capabilities: parcelableconnection: %s", Connection.capabilitiesToString(n));
            ConnectionService.this.mAdapter.setConnectionCapabilities((String)object, n);
        }

        @Override
        public void onConnectionEvent(Connection object, String string2, Bundle bundle) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onConnectionEvent((String)object, string2, bundle);
            }
        }

        @Override
        public void onConnectionPropertiesChanged(Connection object, int n) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            Log.d(this, "properties: parcelableconnection: %s", Connection.propertiesToString(n));
            ConnectionService.this.mAdapter.setConnectionProperties((String)object, n);
        }

        @Override
        public void onConnectionTimeReset(Connection object) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.resetConnectionTime((String)object);
            }
        }

        @Override
        public void onDestroyed(Connection connection) {
            ConnectionService.this.removeConnection(connection);
        }

        @Override
        public void onDisconnected(Connection object, DisconnectCause disconnectCause) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            Log.d(this, "Adapter set disconnected %s", disconnectCause);
            ConnectionService.this.mAdapter.setDisconnected((String)object, disconnectCause);
        }

        @Override
        public void onExtrasChanged(Connection object, Bundle bundle) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.putExtras((String)object, bundle);
            }
        }

        @Override
        public void onExtrasRemoved(Connection object, List<String> list) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.removeExtras((String)object, list);
            }
        }

        @Override
        public void onPhoneAccountChanged(Connection object, PhoneAccountHandle phoneAccountHandle) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onPhoneAccountChanged((String)object, phoneAccountHandle);
            }
        }

        @Override
        public void onPostDialChar(Connection connection, char c) {
            String string2 = (String)ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter onPostDialChar %s, %s", connection, Character.valueOf(c));
            ConnectionService.this.mAdapter.onPostDialChar(string2, c);
        }

        @Override
        public void onPostDialWait(Connection connection, String string2) {
            String string3 = (String)ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "Adapter onPostDialWait %s, %s", connection, string2);
            ConnectionService.this.mAdapter.onPostDialWait(string3, string2);
        }

        @Override
        public void onRemoteRttRequest(Connection object) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onRemoteRttRequest((String)object);
            }
        }

        @Override
        public void onRingbackRequested(Connection object, boolean bl) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            Log.d(this, "Adapter onRingback %b", bl);
            ConnectionService.this.mAdapter.setRingbackRequested((String)object, bl);
        }

        @Override
        public void onRttInitiationFailure(Connection object, int n) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onRttInitiationFailure((String)object, n);
            }
        }

        @Override
        public void onRttInitiationSuccess(Connection object) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onRttInitiationSuccess((String)object);
            }
        }

        @Override
        public void onRttSessionRemotelyTerminated(Connection object) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            if (object != null) {
                ConnectionService.this.mAdapter.onRttSessionRemotelyTerminated((String)object);
            }
        }

        @Override
        public void onStateChanged(Connection object, int n) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            Log.d(this, "Adapter set state %s %s", object, Connection.stateToString(n));
            switch (n) {
                default: {
                    break;
                }
                case 7: {
                    ConnectionService.this.mAdapter.setPulling((String)object);
                    break;
                }
                case 6: {
                    break;
                }
                case 5: {
                    ConnectionService.this.mAdapter.setOnHold((String)object);
                    break;
                }
                case 4: {
                    ConnectionService.this.mAdapter.setActive((String)object);
                    break;
                }
                case 3: {
                    ConnectionService.this.mAdapter.setDialing((String)object);
                    break;
                }
                case 2: {
                    ConnectionService.this.mAdapter.setRinging((String)object);
                }
                case 1: 
            }
        }

        @Override
        public void onStatusHintsChanged(Connection object, StatusHints statusHints) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            ConnectionService.this.mAdapter.setStatusHints((String)object, statusHints);
        }

        @Override
        public void onVideoProviderChanged(Connection connection, Connection.VideoProvider videoProvider) {
            String string2 = (String)ConnectionService.this.mIdByConnection.get(connection);
            Log.d(this, "onVideoProviderChanged: Connection: %s, VideoProvider: %s", connection, videoProvider);
            ConnectionService.this.mAdapter.setVideoProvider(string2, videoProvider);
        }

        @Override
        public void onVideoStateChanged(Connection object, int n) {
            object = (String)ConnectionService.this.mIdByConnection.get(object);
            Log.d(this, "Adapter set video state %d", n);
            ConnectionService.this.mAdapter.setVideoState((String)object, n);
        }
    };
    private final Handler mHandler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message object) {
            final int n = ((Message)object).what;
            final boolean bl = false;
            switch (n) {
                default: {
                    break;
                }
                case 34: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.def");
                    try {
                        ConnectionService.this.deflect((String)((SomeArgs)object).arg1, (Uri)((SomeArgs)object).arg2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 33: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.hC");
                        String string2 = (String)((SomeArgs)object).arg1;
                        ConnectionService.this.notifyHandoverComplete(string2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 32: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.haF");
                    try {
                        final String string3 = (String)((SomeArgs)object).arg1;
                        final ConnectionRequest connectionRequest = (ConnectionRequest)((SomeArgs)object).arg2;
                        n = (Integer)((SomeArgs)object).arg4;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", string3);
                            List list = ConnectionService.this.mPreInitializationConnectionRequests;
                            Runnable runnable = new Runnable("H.CS.haF.pICR", null){

                                @Override
                                public void loggedRun() {
                                    ConnectionService.this.handoverFailed(string3, connectionRequest, n);
                                }
                            };
                            list.add(runnable.prepare());
                            break;
                        }
                        Log.i(this, "createConnectionFailed %s", string3);
                        ConnectionService.this.handoverFailed(string3, connectionRequest, n);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 31: {
                    ConnectionService.this.onConnectionServiceFocusGained();
                    break;
                }
                case 30: {
                    ConnectionService.this.onConnectionServiceFocusLost();
                    break;
                }
                case 29: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.crCoC");
                    try {
                        final String string4 = (String)((SomeArgs)object).arg1;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", string4);
                            List list = ConnectionService.this.mPreInitializationConnectionRequests;
                            Runnable runnable = new Runnable("H.CS.crCoC.pICR", null){

                                @Override
                                public void loggedRun() {
                                    ConnectionService.this.notifyCreateConnectionComplete(string4);
                                }
                            };
                            list.add(runnable.prepare());
                            break;
                        }
                        ConnectionService.this.notifyCreateConnectionComplete(string4);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 28: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.rTRUR");
                        String string5 = (String)((SomeArgs)object).arg1;
                        Connection.RttTextStream rttTextStream = (Connection.RttTextStream)((SomeArgs)object).arg2;
                        ConnectionService.this.handleRttUpgradeResponse(string5, rttTextStream);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 27: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.-RTT");
                        String string6 = (String)((SomeArgs)object).arg1;
                        ConnectionService.this.stopRtt(string6);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 26: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.+RTT");
                        String string7 = (String)((SomeArgs)object).arg1;
                        Connection.RttTextStream rttTextStream = (Connection.RttTextStream)((SomeArgs)object).arg2;
                        ConnectionService.this.startRtt(string7, rttTextStream);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 25: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.crCoF");
                    final String string8 = (String)((SomeArgs)object).arg1;
                    final ConnectionRequest connectionRequest = (ConnectionRequest)((SomeArgs)object).arg2;
                    bl = ((SomeArgs)object).argi1 == 1;
                    try {
                        final PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle)((SomeArgs)object).arg4;
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", string8);
                            List list = ConnectionService.this.mPreInitializationConnectionRequests;
                            Runnable runnable = new Runnable("H.CS.crCoF.pICR", null){

                                @Override
                                public void loggedRun() {
                                    ConnectionService.this.createConnectionFailed(phoneAccountHandle, string8, connectionRequest, bl);
                                }
                            };
                            list.add(runnable.prepare());
                            break;
                        }
                        Log.i(this, "createConnectionFailed %s", string8);
                        ConnectionService.this.createConnectionFailed(phoneAccountHandle, string8, connectionRequest, bl);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 24: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.oEC");
                        String string9 = (String)((SomeArgs)object).arg1;
                        Bundle bundle = (Bundle)((SomeArgs)object).arg2;
                        ConnectionService.this.handleExtrasChanged(string9, bundle);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 23: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg4, "H.CS.sCE");
                        String string10 = (String)((SomeArgs)object).arg1;
                        String string11 = (String)((SomeArgs)object).arg2;
                        Bundle bundle = (Bundle)((SomeArgs)object).arg3;
                        ConnectionService.this.sendCallEvent(string10, string11, bundle);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 22: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)someArgs.arg2, "H.CS.pEC");
                        ConnectionService.this.pullExternalCall((String)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                        Log.endSession();
                    }
                }
                case 21: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)someArgs.arg2, "H.CS.s");
                    try {
                        ConnectionService.this.silence((String)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                        Log.endSession();
                    }
                }
                case 20: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.rWM");
                    try {
                        ConnectionService.this.reject((String)((SomeArgs)object).arg1, (String)((SomeArgs)object).arg2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 19: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)someArgs.arg2, "H.CS.sC");
                        ConnectionService.this.swapConference((String)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                        Log.endSession();
                    }
                }
                case 18: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.mC");
                        ConnectionService.this.mergeConference((String)((SomeArgs)object).arg1);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 17: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.anV");
                    try {
                        String string12 = (String)((SomeArgs)object).arg1;
                        n = ((SomeArgs)object).argi1;
                        ConnectionService.this.answerVideo(string12, n);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 16: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.rCSA");
                        ConnectionService.this.mAdapter.removeAdapter((IConnectionServiceAdapter)((SomeArgs)object).arg1);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 14: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.oPDC");
                        String string13 = (String)((SomeArgs)object).arg1;
                        if (((SomeArgs)object).argi1 == 1) {
                            bl = true;
                        }
                        ConnectionService.this.onPostDialContinue(string13, bl);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 13: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.sFC");
                        ConnectionService.this.splitFromConference((String)((SomeArgs)object).arg1);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 12: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.c");
                        String string14 = (String)((SomeArgs)object).arg1;
                        String string15 = (String)((SomeArgs)object).arg2;
                        ConnectionService.this.conference(string14, string15);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 11: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.sDT");
                        ConnectionService.this.stopDtmfTone((String)((SomeArgs)object).arg1);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 10: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.pDT");
                        ConnectionService.this.playDtmfTone((String)((SomeArgs)object).arg2, ((Character)((SomeArgs)object).arg1).charValue());
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 9: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg3, "H.CS.cASC");
                    try {
                        String string16 = (String)((SomeArgs)object).arg1;
                        CallAudioState callAudioState = (CallAudioState)((SomeArgs)object).arg2;
                        ConnectionService connectionService = ConnectionService.this;
                        CallAudioState callAudioState2 = new CallAudioState(callAudioState);
                        connectionService.onCallAudioStateChanged(string16, callAudioState2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 8: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)someArgs.arg2, "H.CS.u");
                    try {
                        ConnectionService.this.unhold((String)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                        Log.endSession();
                    }
                }
                case 7: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)someArgs.arg2, "H.CS.r");
                    try {
                        ConnectionService.this.hold((String)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                        Log.endSession();
                    }
                }
                case 6: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.d");
                    try {
                        ConnectionService.this.disconnect((String)((SomeArgs)object).arg1);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 5: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)someArgs.arg2, "H.CS.r");
                    try {
                        ConnectionService.this.reject((String)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                        Log.endSession();
                    }
                }
                case 4: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.an");
                    try {
                        ConnectionService.this.answer((String)((SomeArgs)object).arg1);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 3: {
                    SomeArgs someArgs = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)someArgs.arg2, "H.CS.ab");
                    try {
                        ConnectionService.this.abort((String)someArgs.arg1);
                        break;
                    }
                    finally {
                        someArgs.recycle();
                        Log.endSession();
                    }
                }
                case 2: {
                    object = (SomeArgs)((Message)object).obj;
                    Log.continueSession((Session)((SomeArgs)object).arg4, "H.CS.crCo");
                    final PhoneAccountHandle phoneAccountHandle = (PhoneAccountHandle)((SomeArgs)object).arg1;
                    final String string17 = (String)((SomeArgs)object).arg2;
                    final ConnectionRequest connectionRequest = (ConnectionRequest)((SomeArgs)object).arg3;
                    bl = ((SomeArgs)object).argi1 == 1;
                    final boolean bl2 = ((SomeArgs)object).argi2 == 1;
                    try {
                        if (!ConnectionService.this.mAreAccountsInitialized) {
                            Log.d(this, "Enqueueing pre-init request %s", string17);
                            List list = ConnectionService.this.mPreInitializationConnectionRequests;
                            Runnable runnable = new Runnable("H.CS.crCo.pICR", null){

                                @Override
                                public void loggedRun() {
                                    ConnectionService.this.createConnection(phoneAccountHandle, string17, connectionRequest, bl, bl2);
                                }
                            };
                            list.add(runnable.prepare());
                            break;
                        }
                        ConnectionService.this.createConnection(phoneAccountHandle, string17, connectionRequest, bl, bl2);
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
                case 1: {
                    object = (SomeArgs)((Message)object).obj;
                    try {
                        IConnectionServiceAdapter iConnectionServiceAdapter = (IConnectionServiceAdapter)((SomeArgs)object).arg1;
                        Log.continueSession((Session)((SomeArgs)object).arg2, "H.CS.aCSA");
                        ConnectionService.this.mAdapter.addAdapter(iConnectionServiceAdapter);
                        ConnectionService.this.onAdapterAttached();
                        break;
                    }
                    finally {
                        ((SomeArgs)object).recycle();
                        Log.endSession();
                    }
                }
            }
        }

    };
    private int mId = 0;
    private final Map<Conference, String> mIdByConference = new ConcurrentHashMap<Conference, String>();
    private final Map<Connection, String> mIdByConnection = new ConcurrentHashMap<Connection, String>();
    private Object mIdSyncRoot = new Object();
    private final List<java.lang.Runnable> mPreInitializationConnectionRequests = new ArrayList<java.lang.Runnable>();
    private final RemoteConnectionManager mRemoteConnectionManager = new RemoteConnectionManager(this);
    private Conference sNullConference;

    private void abort(String string2) {
        Log.d(this, "abort %s", string2);
        this.findConnectionForAction(string2, "abort").onAbort();
    }

    private String addConferenceInternal(Conference conference) {
        String string2;
        String string3 = string2 = null;
        if (conference.getExtras() != null) {
            string3 = string2;
            if (conference.getExtras().containsKey("android.telecom.extra.ORIGINAL_CONNECTION_ID")) {
                string3 = conference.getExtras().getString("android.telecom.extra.ORIGINAL_CONNECTION_ID");
                Log.d(this, "addConferenceInternal: conf %s reusing original id %s", conference.getTelecomCallId(), string3);
            }
        }
        if (this.mIdByConference.containsKey(conference)) {
            Log.w(this, "Re-adding an existing conference: %s.", conference);
            return null;
        }
        if (string3 == null) {
            string3 = UUID.randomUUID().toString();
        }
        this.mConferenceById.put(string3, conference);
        this.mIdByConference.put(conference, string3);
        conference.addListener(this.mConferenceListener);
        return string3;
    }

    private void addConnection(PhoneAccountHandle phoneAccountHandle, String string2, Connection connection) {
        connection.setTelecomCallId(string2);
        this.mConnectionById.put(string2, connection);
        this.mIdByConnection.put(connection, string2);
        connection.addConnectionListener(this.mConnectionListener);
        connection.setConnectionService(this);
        connection.setPhoneAccountHandle(phoneAccountHandle);
        this.onConnectionAdded(connection);
    }

    private String addExistingConnectionInternal(PhoneAccountHandle phoneAccountHandle, Connection connection) {
        CharSequence charSequence;
        if (connection.getExtras() != null && connection.getExtras().containsKey("android.telecom.extra.ORIGINAL_CONNECTION_ID")) {
            charSequence = connection.getExtras().getString("android.telecom.extra.ORIGINAL_CONNECTION_ID");
            Log.d(this, "addExistingConnectionInternal - conn %s reusing original id %s", connection.getTelecomCallId(), charSequence);
        } else if (phoneAccountHandle == null) {
            charSequence = UUID.randomUUID().toString();
        } else {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(phoneAccountHandle.getComponentName().getClassName());
            ((StringBuilder)charSequence).append("@");
            ((StringBuilder)charSequence).append(this.getNextCallId());
            charSequence = ((StringBuilder)charSequence).toString();
        }
        this.addConnection(phoneAccountHandle, (String)charSequence, connection);
        return charSequence;
    }

    private void answer(String string2) {
        Log.d(this, "answer %s", string2);
        this.findConnectionForAction(string2, "answer").onAnswer();
    }

    private void answerVideo(String string2, int n) {
        Log.d(this, "answerVideo %s", string2);
        this.findConnectionForAction(string2, "answer").onAnswer(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void conference(String string2, String object) {
        Log.d(this, "conference %s, %s", string2, object);
        Connection connection = this.findConnectionForAction((String)object, "conference");
        Conference conference = this.getNullConference();
        if (connection == ConnectionService.getNullConnection()) {
            Conference conference2;
            conference = conference2 = this.findConferenceForAction((String)object, "conference");
            if (conference2 == this.getNullConference()) {
                Log.w(this, "Connection2 or Conference2 missing in conference request %s.", object);
                return;
            }
        }
        if ((object = this.findConnectionForAction(string2, "conference")) == ConnectionService.getNullConnection()) {
            object = this.findConferenceForAction(string2, "addConnection");
            if (object == this.getNullConference()) {
                Log.w(this, "Connection1 or Conference1 missing in conference request %s.", string2);
                return;
            }
            if (connection != ConnectionService.getNullConnection()) {
                ((Conference)object).onMerge(connection);
                return;
            }
            Log.wtf((Object)this, "There can only be one conference and an attempt was made to merge two conferences.", new Object[0]);
            return;
        }
        if (conference != this.getNullConference()) {
            conference.onMerge((Connection)object);
            return;
        }
        this.onConference((Connection)object, connection);
    }

    private void createConnection(PhoneAccountHandle object, String string2, ConnectionRequest connectionRequest, boolean bl, boolean bl2) {
        boolean bl3;
        boolean bl4 = connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean(EXTRA_IS_HANDOVER, false);
        boolean bl5 = connectionRequest.getExtras() != null && connectionRequest.getExtras().getBoolean("android.telecom.extra.IS_HANDOVER_CONNECTION", false);
        Log.d(this, "createConnection, callManagerAccount: %s, callId: %s, request: %s, isIncoming: %b, isUnknown: %b, isLegacyHandover: %b, isHandover: %b", object, string2, connectionRequest, bl, bl2, bl4, bl5);
        Object var8_8 = null;
        if (bl5) {
            object = connectionRequest.getExtras() != null ? (PhoneAccountHandle)connectionRequest.getExtras().getParcelable("android.telecom.extra.HANDOVER_FROM_PHONE_ACCOUNT") : null;
            object = !bl ? this.onCreateOutgoingHandoverConnection((PhoneAccountHandle)object, connectionRequest) : this.onCreateIncomingHandoverConnection((PhoneAccountHandle)object, connectionRequest);
        } else {
            object = bl2 ? this.onCreateUnknownConnection((PhoneAccountHandle)object, connectionRequest) : (bl ? this.onCreateIncomingConnection((PhoneAccountHandle)object, connectionRequest) : this.onCreateOutgoingConnection((PhoneAccountHandle)object, connectionRequest));
        }
        Log.d(this, "createConnection, connection: %s", object);
        Object object2 = object;
        if (object == null) {
            Log.i(this, "createConnection, implementation returned null connection.", new Object[0]);
            object2 = Connection.createFailedConnection(new DisconnectCause(1, "IMPL_RETURNED_NULL_CONNECTION"));
        }
        if (bl3 = (((Connection)object2).getConnectionProperties() & 128) == 128) {
            ((Connection)object2).setAudioModeIsVoip(true);
        }
        ((Connection)object2).setTelecomCallId(string2);
        if (((Connection)object2).getState() != 6) {
            this.addConnection(connectionRequest.getAccountHandle(), string2, (Connection)object2);
        }
        object = (object = ((Connection)object2).getAddress()) == null ? "null" : ((Uri)object).getSchemeSpecificPart();
        Log.v(this, "createConnection, number: %s, state: %s, capabilities: %s, properties: %s", Connection.toLogSafePhoneNumber((String)object), Connection.stateToString(((Connection)object2).getState()), Connection.capabilitiesToString(((Connection)object2).getConnectionCapabilities()), Connection.propertiesToString(((Connection)object2).getConnectionProperties()));
        Log.d(this, "createConnection, calling handleCreateConnectionSuccessful %s", string2);
        ConnectionServiceAdapter connectionServiceAdapter = this.mAdapter;
        PhoneAccountHandle phoneAccountHandle = connectionRequest.getAccountHandle();
        int n = ((Connection)object2).getState();
        int n2 = ((Connection)object2).getConnectionCapabilities();
        int n3 = ((Connection)object2).getConnectionProperties();
        int n4 = ((Connection)object2).getSupportedAudioRoutes();
        Uri uri = ((Connection)object2).getAddress();
        int n5 = ((Connection)object2).getAddressPresentation();
        String string3 = ((Connection)object2).getCallerDisplayName();
        int n6 = ((Connection)object2).getCallerDisplayNamePresentation();
        object = ((Connection)object2).getVideoProvider() == null ? var8_8 : ((Connection)object2).getVideoProvider().getInterface();
        connectionServiceAdapter.handleCreateConnectionComplete(string2, connectionRequest, new ParcelableConnection(phoneAccountHandle, n, n2, n3, n4, uri, n5, string3, n6, (IVideoProvider)object, ((Connection)object2).getVideoState(), ((Connection)object2).isRingbackRequested(), ((Connection)object2).getAudioModeIsVoip(), ((Connection)object2).getConnectTimeMillis(), ((Connection)object2).getConnectElapsedTimeMillis(), ((Connection)object2).getStatusHints(), ((Connection)object2).getDisconnectCause(), this.createIdList(((Connection)object2).getConferenceables()), ((Connection)object2).getExtras()));
        if (bl && connectionRequest.shouldShowIncomingCallUi() && bl3) {
            ((Connection)object2).onShowIncomingCallUi();
        }
        if (bl2) {
            this.triggerConferenceRecalculate();
        }
    }

    private void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String string2, ConnectionRequest connectionRequest, boolean bl) {
        Log.i(this, "createConnectionFailed %s", string2);
        if (bl) {
            this.onCreateIncomingConnectionFailed(phoneAccountHandle, connectionRequest);
        } else {
            this.onCreateOutgoingConnectionFailed(phoneAccountHandle, connectionRequest);
        }
    }

    private List<String> createConnectionIdList(List<Connection> object) {
        ArrayList<String> arrayList = new ArrayList<String>();
        object = object.iterator();
        while (object.hasNext()) {
            Connection connection = (Connection)object.next();
            if (!this.mIdByConnection.containsKey(connection)) continue;
            arrayList.add(this.mIdByConnection.get(connection));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private List<String> createIdList(List<Conferenceable> object) {
        ArrayList<String> arrayList = new ArrayList<String>();
        object = object.iterator();
        while (object.hasNext()) {
            Conferenceable conferenceable = (Conferenceable)object.next();
            if (conferenceable instanceof Connection) {
                if (!this.mIdByConnection.containsKey(conferenceable = (Connection)conferenceable)) continue;
                arrayList.add(this.mIdByConnection.get(conferenceable));
                continue;
            }
            if (!(conferenceable instanceof Conference) || !this.mIdByConference.containsKey(conferenceable = (Conference)conferenceable)) continue;
            arrayList.add(this.mIdByConference.get(conferenceable));
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    private void deflect(String string2, Uri uri) {
        Log.d(this, "deflect %s", string2);
        this.findConnectionForAction(string2, "deflect").onDeflect(uri);
    }

    private void disconnect(String string2) {
        Log.d(this, "disconnect %s", string2);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "disconnect").onDisconnect();
        } else {
            this.findConferenceForAction(string2, "disconnect").onDisconnect();
        }
    }

    private void endAllConnections() {
        for (Connection object2 : this.mIdByConnection.keySet()) {
            if (object2.getConference() != null) continue;
            object2.onDisconnect();
        }
        Iterator<Conference> iterator = this.mIdByConference.keySet().iterator();
        while (iterator.hasNext()) {
            iterator.next().onDisconnect();
        }
    }

    private Conference findConferenceForAction(String string2, String string3) {
        if (this.mConferenceById.containsKey(string2)) {
            return this.mConferenceById.get(string2);
        }
        Log.w(this, "%s - Cannot find conference %s", string3, string2);
        return this.getNullConference();
    }

    private Connection findConnectionForAction(String string2, String string3) {
        if (string2 != null && this.mConnectionById.containsKey(string2)) {
            return this.mConnectionById.get(string2);
        }
        Log.w(this, "%s - Cannot find Connection %s", string3, string2);
        return ConnectionService.getNullConnection();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int getNextCallId() {
        Object object = this.mIdSyncRoot;
        synchronized (object) {
            int n;
            this.mId = n = this.mId + 1;
            return n;
        }
    }

    private Conference getNullConference() {
        if (this.sNullConference == null) {
            this.sNullConference = new Conference(null){};
        }
        return this.sNullConference;
    }

    static Connection getNullConnection() {
        synchronized (ConnectionService.class) {
            Connection connection;
            if (sNullConnection == null) {
                connection = new Connection(){};
                sNullConnection = connection;
            }
            connection = sNullConnection;
            return connection;
        }
    }

    private void handleExtrasChanged(String string2, Bundle bundle) {
        Log.d(this, "handleExtrasChanged(%s, %s)", string2, bundle);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "handleExtrasChanged").handleExtrasChanged(bundle);
        } else if (this.mConferenceById.containsKey(string2)) {
            this.findConferenceForAction(string2, "handleExtrasChanged").handleExtrasChanged(bundle);
        }
    }

    private void handleRttUpgradeResponse(String string2, Connection.RttTextStream rttTextStream) {
        boolean bl = rttTextStream == null;
        Log.d(this, "handleRttUpgradeResponse(%s, %s)", string2, bl);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "handleRttUpgradeResponse").handleRttUpgradeResponse(rttTextStream);
        } else if (this.mConferenceById.containsKey(string2)) {
            Log.w(this, "handleRttUpgradeResponse called on a conference.", new Object[0]);
        }
    }

    private void handoverFailed(String string2, ConnectionRequest connectionRequest, int n) {
        Log.i(this, "handoverFailed %s", string2);
        this.onHandoverFailed(connectionRequest, n);
    }

    private void hold(String string2) {
        Log.d(this, "hold %s", string2);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "hold").onHold();
        } else {
            this.findConferenceForAction(string2, "hold").onHold();
        }
    }

    private void mergeConference(String object) {
        Log.d(this, "mergeConference(%s)", object);
        object = this.findConferenceForAction((String)object, "mergeConference");
        if (object != null) {
            ((Conference)object).onMerge();
        }
    }

    private void notifyCreateConnectionComplete(String string2) {
        Log.i(this, "notifyCreateConnectionComplete %s", string2);
        if (string2 == null) {
            Log.w(this, "notifyCreateConnectionComplete: callId is null.", new Object[0]);
            return;
        }
        this.onCreateConnectionComplete(this.findConnectionForAction(string2, "notifyCreateConnectionComplete"));
    }

    private void notifyHandoverComplete(String object) {
        Log.d(this, "notifyHandoverComplete(%s)", object);
        object = this.findConnectionForAction((String)object, "notifyHandoverComplete");
        if (object != null) {
            ((Connection)object).onHandoverComplete();
        }
    }

    private void onAccountsInitialized() {
        this.mAreAccountsInitialized = true;
        Iterator<java.lang.Runnable> iterator = this.mPreInitializationConnectionRequests.iterator();
        while (iterator.hasNext()) {
            iterator.next().run();
        }
        this.mPreInitializationConnectionRequests.clear();
    }

    private void onAdapterAttached() {
        if (this.mAreAccountsInitialized) {
            return;
        }
        String string2 = this.getOpPackageName();
        this.mAdapter.queryRemoteConnectionServices(new RemoteServiceCallback.Stub(){

            @Override
            public void onError() {
                ConnectionService.this.mHandler.post(new Runnable("oAA.qRCS.oE", null){

                    @Override
                    public void loggedRun() {
                        ConnectionService.this.mAreAccountsInitialized = true;
                    }
                }.prepare());
            }

            @Override
            public void onResult(final List<ComponentName> list, final List<IBinder> list2) {
                ConnectionService.this.mHandler.post(new Runnable("oAA.qRCS.oR", null){

                    @Override
                    public void loggedRun() {
                        for (int i = 0; i < list.size() && i < list2.size(); ++i) {
                            ConnectionService.this.mRemoteConnectionManager.addConnectionService((ComponentName)list.get(i), IConnectionService.Stub.asInterface((IBinder)list2.get(i)));
                        }
                        ConnectionService.this.onAccountsInitialized();
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("remote connection services found: ");
                        stringBuilder.append(list2);
                        Log.d(this, stringBuilder.toString(), new Object[0]);
                    }
                }.prepare());
            }

        }, string2);
    }

    private void onCallAudioStateChanged(String string2, CallAudioState callAudioState) {
        Log.d(this, "onAudioStateChanged %s %s", string2, callAudioState);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "onCallAudioStateChanged").setCallAudioState(callAudioState);
        } else {
            this.findConferenceForAction(string2, "onCallAudioStateChanged").setCallAudioState(callAudioState);
        }
    }

    private void onPostDialContinue(String string2, boolean bl) {
        Log.d(this, "onPostDialContinue(%s)", string2);
        this.findConnectionForAction(string2, "stopDtmfTone").onPostDialContinue(bl);
    }

    private void playDtmfTone(String string2, char c) {
        Log.d(this, "playDtmfTone %s %c", string2, Character.valueOf(c));
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "playDtmfTone").onPlayDtmfTone(c);
        } else {
            this.findConferenceForAction(string2, "playDtmfTone").onPlayDtmfTone(c);
        }
    }

    private void pullExternalCall(String object) {
        Log.d(this, "pullExternalCall(%s)", object);
        object = this.findConnectionForAction((String)object, "pullExternalCall");
        if (object != null) {
            ((Connection)object).onPullExternalCall();
        }
    }

    private void reject(String string2) {
        Log.d(this, "reject %s", string2);
        this.findConnectionForAction(string2, "reject").onReject();
    }

    private void reject(String string2, String string3) {
        Log.d(this, "reject %s with message", string2);
        this.findConnectionForAction(string2, "reject").onReject(string3);
    }

    private void removeConference(Conference conference) {
        if (this.mIdByConference.containsKey(conference)) {
            conference.removeListener(this.mConferenceListener);
            String string2 = this.mIdByConference.get(conference);
            this.mConferenceById.remove(string2);
            this.mIdByConference.remove(conference);
            this.mAdapter.removeCall(string2);
            this.onConferenceRemoved(conference);
        }
    }

    private void sendCallEvent(String object, String string2, Bundle bundle) {
        Log.d(this, "sendCallEvent(%s, %s)", object, string2);
        object = this.findConnectionForAction((String)object, "sendCallEvent");
        if (object != null) {
            ((Connection)object).onCallEvent(string2, bundle);
        }
    }

    private void silence(String string2) {
        Log.d(this, "silence %s", string2);
        this.findConnectionForAction(string2, "silence").onSilence();
    }

    private void splitFromConference(String object) {
        Log.d(this, "splitFromConference(%s)", object);
        Connection connection = this.findConnectionForAction((String)object, "splitFromConference");
        if (connection == ConnectionService.getNullConnection()) {
            Log.w(this, "Connection missing in conference request %s.", object);
            return;
        }
        object = connection.getConference();
        if (object != null) {
            ((Conference)object).onSeparate(connection);
        }
    }

    private void startRtt(String string2, Connection.RttTextStream rttTextStream) {
        Log.d(this, "startRtt(%s)", string2);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "startRtt").onStartRtt(rttTextStream);
        } else if (this.mConferenceById.containsKey(string2)) {
            Log.w(this, "startRtt called on a conference.", new Object[0]);
        }
    }

    private void stopDtmfTone(String string2) {
        Log.d(this, "stopDtmfTone %s", string2);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "stopDtmfTone").onStopDtmfTone();
        } else {
            this.findConferenceForAction(string2, "stopDtmfTone").onStopDtmfTone();
        }
    }

    private void stopRtt(String string2) {
        Log.d(this, "stopRtt(%s)", string2);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "stopRtt").onStopRtt();
        } else if (this.mConferenceById.containsKey(string2)) {
            Log.w(this, "stopRtt called on a conference.", new Object[0]);
        }
    }

    private void swapConference(String object) {
        Log.d(this, "swapConference(%s)", object);
        object = this.findConferenceForAction((String)object, "swapConference");
        if (object != null) {
            ((Conference)object).onSwap();
        }
    }

    private void unhold(String string2) {
        Log.d(this, "unhold %s", string2);
        if (this.mConnectionById.containsKey(string2)) {
            this.findConnectionForAction(string2, "unhold").onUnhold();
        } else {
            this.findConferenceForAction(string2, "unhold").onUnhold();
        }
    }

    public final void addConference(Conference conference) {
        Log.d(this, "addConference: conference=%s", conference);
        String string2 = this.addConferenceInternal(conference);
        if (string2 != null) {
            ArrayList<String> object22 = new ArrayList<String>(2);
            for (Connection connection : conference.getConnections()) {
                if (!this.mIdByConnection.containsKey(connection)) continue;
                object22.add(this.mIdByConnection.get(connection));
            }
            conference.setTelecomCallId(string2);
            PhoneAccountHandle phoneAccountHandle = conference.getPhoneAccountHandle();
            int n = conference.getState();
            int n2 = conference.getConnectionCapabilities();
            int n3 = conference.getConnectionProperties();
            Object object = conference.getVideoProvider() == null ? null : conference.getVideoProvider().getInterface();
            object = new ParcelableConference(phoneAccountHandle, n, n2, n3, object22, (IVideoProvider)object, conference.getVideoState(), conference.getConnectTimeMillis(), conference.getConnectionStartElapsedRealTime(), conference.getStatusHints(), conference.getExtras(), conference.getAddress(), conference.getAddressPresentation(), conference.getCallerDisplayName(), conference.getCallerDisplayNamePresentation());
            this.mAdapter.addConferenceCall(string2, (ParcelableConference)object);
            this.mAdapter.setVideoProvider(string2, conference.getVideoProvider());
            this.mAdapter.setVideoState(string2, conference.getVideoState());
            for (Connection connection : conference.getConnections()) {
                String string3 = this.mIdByConnection.get(connection);
                if (string3 == null) continue;
                this.mAdapter.setIsConferenced(string3, string2);
            }
            this.onConferenceAdded(conference);
        }
    }

    public final void addExistingConnection(PhoneAccountHandle phoneAccountHandle, Connection connection) {
        this.addExistingConnection(phoneAccountHandle, connection, null);
    }

    public final void addExistingConnection(PhoneAccountHandle parcelable, Connection connection, Conference object) {
        String string2 = this.addExistingConnectionInternal((PhoneAccountHandle)parcelable, connection);
        if (string2 != null) {
            ArrayList<String> arrayList = new ArrayList<String>(0);
            object = object != null ? this.mIdByConference.get(object) : null;
            int n = connection.getState();
            int n2 = connection.getConnectionCapabilities();
            int n3 = connection.getConnectionProperties();
            int n4 = connection.getSupportedAudioRoutes();
            Uri uri = connection.getAddress();
            int n5 = connection.getAddressPresentation();
            String string3 = connection.getCallerDisplayName();
            int n6 = connection.getCallerDisplayNamePresentation();
            IVideoProvider iVideoProvider = connection.getVideoProvider() == null ? null : connection.getVideoProvider().getInterface();
            parcelable = new ParcelableConnection((PhoneAccountHandle)parcelable, n, n2, n3, n4, uri, n5, string3, n6, iVideoProvider, connection.getVideoState(), connection.isRingbackRequested(), connection.getAudioModeIsVoip(), connection.getConnectTimeMillis(), connection.getConnectElapsedTimeMillis(), connection.getStatusHints(), connection.getDisconnectCause(), (List<String>)arrayList, connection.getExtras(), (String)object, connection.getCallDirection());
            this.mAdapter.addExistingConnection(string2, (ParcelableConnection)parcelable);
        }
    }

    void addRemoteConference(RemoteConference remoteConference) {
        this.onRemoteConferenceAdded(remoteConference);
    }

    void addRemoteExistingConnection(RemoteConnection remoteConnection) {
        this.onRemoteExistingConnectionAdded(remoteConnection);
    }

    public final void conferenceRemoteConnections(RemoteConnection remoteConnection, RemoteConnection remoteConnection2) {
        this.mRemoteConnectionManager.conferenceRemoteConnections(remoteConnection, remoteConnection2);
    }

    public final void connectionServiceFocusReleased() {
        this.mAdapter.onConnectionServiceFocusReleased();
    }

    public boolean containsConference(Conference conference) {
        return this.mIdByConference.containsKey(conference);
    }

    public final RemoteConnection createRemoteIncomingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConnection(phoneAccountHandle, connectionRequest, true);
    }

    public final RemoteConnection createRemoteOutgoingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return this.mRemoteConnectionManager.createRemoteConnection(phoneAccountHandle, connectionRequest, false);
    }

    public final Collection<Conference> getAllConferences() {
        return this.mConferenceById.values();
    }

    public final Collection<Connection> getAllConnections() {
        return this.mConnectionById.values();
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void onConference(Connection connection, Connection connection2) {
    }

    public void onConferenceAdded(Conference conference) {
    }

    public void onConferenceRemoved(Conference conference) {
    }

    public void onConnectionAdded(Connection connection) {
    }

    public void onConnectionRemoved(Connection connection) {
    }

    public void onConnectionServiceFocusGained() {
    }

    public void onConnectionServiceFocusLost() {
    }

    public void onCreateConnectionComplete(Connection connection) {
    }

    public Connection onCreateIncomingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onCreateIncomingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
    }

    public Connection onCreateIncomingHandoverConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public Connection onCreateOutgoingConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onCreateOutgoingConnectionFailed(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
    }

    public Connection onCreateOutgoingHandoverConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public Connection onCreateUnknownConnection(PhoneAccountHandle phoneAccountHandle, ConnectionRequest connectionRequest) {
        return null;
    }

    public void onHandoverFailed(ConnectionRequest connectionRequest, int n) {
    }

    public void onRemoteConferenceAdded(RemoteConference remoteConference) {
    }

    public void onRemoteExistingConnectionAdded(RemoteConnection remoteConnection) {
    }

    @Override
    public boolean onUnbind(Intent intent) {
        this.endAllConnections();
        return super.onUnbind(intent);
    }

    protected void removeConnection(Connection connection) {
        connection.unsetConnectionService(this);
        connection.removeConnectionListener(this.mConnectionListener);
        String string2 = this.mIdByConnection.get(connection);
        if (string2 != null) {
            this.mConnectionById.remove(string2);
            this.mIdByConnection.remove(connection);
            this.mAdapter.removeCall(string2);
            this.onConnectionRemoved(connection);
        }
    }

    public void triggerConferenceRecalculate() {
    }

}

