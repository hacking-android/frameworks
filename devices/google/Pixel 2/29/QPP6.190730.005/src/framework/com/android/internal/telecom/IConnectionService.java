/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.ConnectionRequest;
import android.telecom.Logging.Session;
import android.telecom.PhoneAccountHandle;
import com.android.internal.telecom.IConnectionServiceAdapter;

public interface IConnectionService
extends IInterface {
    public void abort(String var1, Session.Info var2) throws RemoteException;

    public void addConnectionServiceAdapter(IConnectionServiceAdapter var1, Session.Info var2) throws RemoteException;

    public void answer(String var1, Session.Info var2) throws RemoteException;

    public void answerVideo(String var1, int var2, Session.Info var3) throws RemoteException;

    public void conference(String var1, String var2, Session.Info var3) throws RemoteException;

    public void connectionServiceFocusGained(Session.Info var1) throws RemoteException;

    public void connectionServiceFocusLost(Session.Info var1) throws RemoteException;

    public void createConnection(PhoneAccountHandle var1, String var2, ConnectionRequest var3, boolean var4, boolean var5, Session.Info var6) throws RemoteException;

    public void createConnectionComplete(String var1, Session.Info var2) throws RemoteException;

    public void createConnectionFailed(PhoneAccountHandle var1, String var2, ConnectionRequest var3, boolean var4, Session.Info var5) throws RemoteException;

    public void deflect(String var1, Uri var2, Session.Info var3) throws RemoteException;

    public void disconnect(String var1, Session.Info var2) throws RemoteException;

    public void handoverComplete(String var1, Session.Info var2) throws RemoteException;

    public void handoverFailed(String var1, ConnectionRequest var2, int var3, Session.Info var4) throws RemoteException;

    public void hold(String var1, Session.Info var2) throws RemoteException;

    public void mergeConference(String var1, Session.Info var2) throws RemoteException;

    public void onCallAudioStateChanged(String var1, CallAudioState var2, Session.Info var3) throws RemoteException;

    public void onExtrasChanged(String var1, Bundle var2, Session.Info var3) throws RemoteException;

    public void onPostDialContinue(String var1, boolean var2, Session.Info var3) throws RemoteException;

    public void playDtmfTone(String var1, char var2, Session.Info var3) throws RemoteException;

    public void pullExternalCall(String var1, Session.Info var2) throws RemoteException;

    public void reject(String var1, Session.Info var2) throws RemoteException;

    public void rejectWithMessage(String var1, String var2, Session.Info var3) throws RemoteException;

    public void removeConnectionServiceAdapter(IConnectionServiceAdapter var1, Session.Info var2) throws RemoteException;

    public void respondToRttUpgradeRequest(String var1, ParcelFileDescriptor var2, ParcelFileDescriptor var3, Session.Info var4) throws RemoteException;

    public void sendCallEvent(String var1, String var2, Bundle var3, Session.Info var4) throws RemoteException;

    public void silence(String var1, Session.Info var2) throws RemoteException;

    public void splitFromConference(String var1, Session.Info var2) throws RemoteException;

    public void startRtt(String var1, ParcelFileDescriptor var2, ParcelFileDescriptor var3, Session.Info var4) throws RemoteException;

    public void stopDtmfTone(String var1, Session.Info var2) throws RemoteException;

    public void stopRtt(String var1, Session.Info var2) throws RemoteException;

    public void swapConference(String var1, Session.Info var2) throws RemoteException;

    public void unhold(String var1, Session.Info var2) throws RemoteException;

    public static class Default
    implements IConnectionService {
        @Override
        public void abort(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
        }

        @Override
        public void answer(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void answerVideo(String string2, int n, Session.Info info) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void conference(String string2, String string3, Session.Info info) throws RemoteException {
        }

        @Override
        public void connectionServiceFocusGained(Session.Info info) throws RemoteException {
        }

        @Override
        public void connectionServiceFocusLost(Session.Info info) throws RemoteException {
        }

        @Override
        public void createConnection(PhoneAccountHandle phoneAccountHandle, String string2, ConnectionRequest connectionRequest, boolean bl, boolean bl2, Session.Info info) throws RemoteException {
        }

        @Override
        public void createConnectionComplete(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String string2, ConnectionRequest connectionRequest, boolean bl, Session.Info info) throws RemoteException {
        }

        @Override
        public void deflect(String string2, Uri uri, Session.Info info) throws RemoteException {
        }

        @Override
        public void disconnect(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void handoverComplete(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void handoverFailed(String string2, ConnectionRequest connectionRequest, int n, Session.Info info) throws RemoteException {
        }

        @Override
        public void hold(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void mergeConference(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void onCallAudioStateChanged(String string2, CallAudioState callAudioState, Session.Info info) throws RemoteException {
        }

        @Override
        public void onExtrasChanged(String string2, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override
        public void onPostDialContinue(String string2, boolean bl, Session.Info info) throws RemoteException {
        }

        @Override
        public void playDtmfTone(String string2, char c, Session.Info info) throws RemoteException {
        }

        @Override
        public void pullExternalCall(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void reject(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void rejectWithMessage(String string2, String string3, Session.Info info) throws RemoteException {
        }

        @Override
        public void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
        }

        @Override
        public void respondToRttUpgradeRequest(String string2, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
        }

        @Override
        public void sendCallEvent(String string2, String string3, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override
        public void silence(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void splitFromConference(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void startRtt(String string2, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
        }

        @Override
        public void stopDtmfTone(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void stopRtt(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void swapConference(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void unhold(String string2, Session.Info info) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IConnectionService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IConnectionService";
        static final int TRANSACTION_abort = 6;
        static final int TRANSACTION_addConnectionServiceAdapter = 1;
        static final int TRANSACTION_answer = 8;
        static final int TRANSACTION_answerVideo = 7;
        static final int TRANSACTION_conference = 19;
        static final int TRANSACTION_connectionServiceFocusGained = 31;
        static final int TRANSACTION_connectionServiceFocusLost = 30;
        static final int TRANSACTION_createConnection = 3;
        static final int TRANSACTION_createConnectionComplete = 4;
        static final int TRANSACTION_createConnectionFailed = 5;
        static final int TRANSACTION_deflect = 9;
        static final int TRANSACTION_disconnect = 12;
        static final int TRANSACTION_handoverComplete = 33;
        static final int TRANSACTION_handoverFailed = 32;
        static final int TRANSACTION_hold = 14;
        static final int TRANSACTION_mergeConference = 21;
        static final int TRANSACTION_onCallAudioStateChanged = 16;
        static final int TRANSACTION_onExtrasChanged = 26;
        static final int TRANSACTION_onPostDialContinue = 23;
        static final int TRANSACTION_playDtmfTone = 17;
        static final int TRANSACTION_pullExternalCall = 24;
        static final int TRANSACTION_reject = 10;
        static final int TRANSACTION_rejectWithMessage = 11;
        static final int TRANSACTION_removeConnectionServiceAdapter = 2;
        static final int TRANSACTION_respondToRttUpgradeRequest = 29;
        static final int TRANSACTION_sendCallEvent = 25;
        static final int TRANSACTION_silence = 13;
        static final int TRANSACTION_splitFromConference = 20;
        static final int TRANSACTION_startRtt = 27;
        static final int TRANSACTION_stopDtmfTone = 18;
        static final int TRANSACTION_stopRtt = 28;
        static final int TRANSACTION_swapConference = 22;
        static final int TRANSACTION_unhold = 15;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IConnectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IConnectionService) {
                return (IConnectionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IConnectionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 33: {
                    return "handoverComplete";
                }
                case 32: {
                    return "handoverFailed";
                }
                case 31: {
                    return "connectionServiceFocusGained";
                }
                case 30: {
                    return "connectionServiceFocusLost";
                }
                case 29: {
                    return "respondToRttUpgradeRequest";
                }
                case 28: {
                    return "stopRtt";
                }
                case 27: {
                    return "startRtt";
                }
                case 26: {
                    return "onExtrasChanged";
                }
                case 25: {
                    return "sendCallEvent";
                }
                case 24: {
                    return "pullExternalCall";
                }
                case 23: {
                    return "onPostDialContinue";
                }
                case 22: {
                    return "swapConference";
                }
                case 21: {
                    return "mergeConference";
                }
                case 20: {
                    return "splitFromConference";
                }
                case 19: {
                    return "conference";
                }
                case 18: {
                    return "stopDtmfTone";
                }
                case 17: {
                    return "playDtmfTone";
                }
                case 16: {
                    return "onCallAudioStateChanged";
                }
                case 15: {
                    return "unhold";
                }
                case 14: {
                    return "hold";
                }
                case 13: {
                    return "silence";
                }
                case 12: {
                    return "disconnect";
                }
                case 11: {
                    return "rejectWithMessage";
                }
                case 10: {
                    return "reject";
                }
                case 9: {
                    return "deflect";
                }
                case 8: {
                    return "answer";
                }
                case 7: {
                    return "answerVideo";
                }
                case 6: {
                    return "abort";
                }
                case 5: {
                    return "createConnectionFailed";
                }
                case 4: {
                    return "createConnectionComplete";
                }
                case 3: {
                    return "createConnection";
                }
                case 2: {
                    return "removeConnectionServiceAdapter";
                }
                case 1: 
            }
            return "addConnectionServiceAdapter";
        }

        public static boolean setDefaultImpl(IConnectionService iConnectionService) {
            if (Proxy.sDefaultImpl == null && iConnectionService != null) {
                Proxy.sDefaultImpl = iConnectionService;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handoverComplete((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? ConnectionRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handoverFailed(string2, (ConnectionRequest)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.connectionServiceFocusGained((Session.Info)object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.connectionServiceFocusLost((Session.Info)object);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.respondToRttUpgradeRequest(string3, (ParcelFileDescriptor)object2, parcelFileDescriptor, (Session.Info)object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.stopRtt((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startRtt(string4, (ParcelFileDescriptor)object2, parcelFileDescriptor, (Session.Info)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onExtrasChanged(string5, (Bundle)object2, (Session.Info)object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        String string7 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendCallEvent(string6, string7, (Bundle)object2, (Session.Info)object);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.pullExternalCall((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPostDialContinue((String)object2, bl, (Session.Info)object);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.swapConference((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.mergeConference((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.splitFromConference((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        String string8 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.conference((String)object2, string8, (Session.Info)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.stopDtmfTone((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        char c = (char)((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.playDtmfTone((String)object2, c, (Session.Info)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string9 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? CallAudioState.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCallAudioStateChanged(string9, (CallAudioState)object2, (Session.Info)object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.unhold((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.hold((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.silence((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.disconnect((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string10 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.rejectWithMessage(string10, (String)object2, (Session.Info)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.reject((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string11 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deflect(string11, (Uri)object2, (Session.Info)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.answer((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.answerVideo((String)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.abort((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        String string12 = ((Parcel)object).readString();
                        ConnectionRequest connectionRequest = ((Parcel)object).readInt() != 0 ? ConnectionRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        bl = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createConnectionFailed((PhoneAccountHandle)object2, string12, connectionRequest, bl, (Session.Info)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createConnectionComplete((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        String string13 = ((Parcel)object).readString();
                        ConnectionRequest connectionRequest = ((Parcel)object).readInt() != 0 ? ConnectionRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        bl = ((Parcel)object).readInt() != 0;
                        boolean bl2 = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createConnection((PhoneAccountHandle)object2, string13, connectionRequest, bl, bl2, (Session.Info)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IConnectionServiceAdapter.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeConnectionServiceAdapter((IConnectionServiceAdapter)object2, (Session.Info)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = IConnectionServiceAdapter.Stub.asInterface(((Parcel)object).readStrongBinder());
                object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                this.addConnectionServiceAdapter((IConnectionServiceAdapter)object2, (Session.Info)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IConnectionService {
            public static IConnectionService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void abort(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().abort(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iConnectionServiceAdapter != null ? iConnectionServiceAdapter.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().addConnectionServiceAdapter(iConnectionServiceAdapter, info);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void answer(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().answer(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void answerVideo(String string2, int n, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().answerVideo(string2, n, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void conference(String string2, String string3, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().conference(string2, string3, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void connectionServiceFocusGained(Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(31, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connectionServiceFocusGained(info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void connectionServiceFocusLost(Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(30, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connectionServiceFocusLost(info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void createConnection(PhoneAccountHandle phoneAccountHandle, String string2, ConnectionRequest connectionRequest, boolean bl, boolean bl2, Session.Info info) throws RemoteException {
                Parcel parcel;
                void var1_5;
                block13 : {
                    block12 : {
                        block11 : {
                            parcel = Parcel.obtain();
                            parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                            if (phoneAccountHandle != null) {
                                parcel.writeInt(1);
                                phoneAccountHandle.writeToParcel(parcel, 0);
                                break block11;
                            }
                            parcel.writeInt(0);
                        }
                        try {
                            parcel.writeString(string2);
                            if (connectionRequest != null) {
                                parcel.writeInt(1);
                                connectionRequest.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            int n = bl ? 1 : 0;
                            parcel.writeInt(n);
                            n = bl2 ? 1 : 0;
                            parcel.writeInt(n);
                            if (info != null) {
                                parcel.writeInt(1);
                                info.writeToParcel(parcel, 0);
                                break block12;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().createConnection(phoneAccountHandle, string2, connectionRequest, bl, bl2, info);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_5;
            }

            @Override
            public void createConnectionComplete(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createConnectionComplete(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void createConnectionFailed(PhoneAccountHandle phoneAccountHandle, String string2, ConnectionRequest connectionRequest, boolean bl, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (connectionRequest != null) {
                        parcel.writeInt(1);
                        connectionRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().createConnectionFailed(phoneAccountHandle, string2, connectionRequest, bl, info);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deflect(String string2, Uri uri, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deflect(string2, uri, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disconnect(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void handoverComplete(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(33, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handoverComplete(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void handoverFailed(String string2, ConnectionRequest connectionRequest, int n, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (connectionRequest != null) {
                        parcel.writeInt(1);
                        connectionRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(32, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handoverFailed(string2, connectionRequest, n, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void hold(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hold(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void mergeConference(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().mergeConference(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCallAudioStateChanged(String string2, CallAudioState callAudioState, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (callAudioState != null) {
                        parcel.writeInt(1);
                        callAudioState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallAudioStateChanged(string2, callAudioState, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onExtrasChanged(String string2, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(26, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onExtrasChanged(string2, bundle, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPostDialContinue(String string2, boolean bl, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPostDialContinue(string2, bl, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void playDtmfTone(String string2, char c, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(c);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playDtmfTone(string2, c, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void pullExternalCall(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pullExternalCall(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void reject(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reject(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void rejectWithMessage(String string2, String string3, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rejectWithMessage(string2, string3, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeConnectionServiceAdapter(IConnectionServiceAdapter iConnectionServiceAdapter, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iConnectionServiceAdapter != null ? iConnectionServiceAdapter.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().removeConnectionServiceAdapter(iConnectionServiceAdapter, info);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void respondToRttUpgradeRequest(String string2, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor2 != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(29, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().respondToRttUpgradeRequest(string2, parcelFileDescriptor, parcelFileDescriptor2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendCallEvent(String string2, String string3, Bundle bundle, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendCallEvent(string2, string3, bundle, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void silence(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().silence(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void splitFromConference(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().splitFromConference(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void startRtt(String string2, ParcelFileDescriptor parcelFileDescriptor, ParcelFileDescriptor parcelFileDescriptor2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (parcelFileDescriptor2 != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startRtt(string2, parcelFileDescriptor, parcelFileDescriptor2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopDtmfTone(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDtmfTone(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopRtt(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(28, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopRtt(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void swapConference(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().swapConference(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void unhold(String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unhold(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

