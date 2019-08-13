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
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.ConnectionRequest;
import android.telecom.DisconnectCause;
import android.telecom.Logging.Session;
import android.telecom.ParcelableConference;
import android.telecom.ParcelableConnection;
import android.telecom.PhoneAccountHandle;
import android.telecom.StatusHints;
import com.android.internal.telecom.IVideoProvider;
import com.android.internal.telecom.RemoteServiceCallback;
import java.util.ArrayList;
import java.util.List;

public interface IConnectionServiceAdapter
extends IInterface {
    public void addConferenceCall(String var1, ParcelableConference var2, Session.Info var3) throws RemoteException;

    public void addExistingConnection(String var1, ParcelableConnection var2, Session.Info var3) throws RemoteException;

    public void handleCreateConnectionComplete(String var1, ConnectionRequest var2, ParcelableConnection var3, Session.Info var4) throws RemoteException;

    public void onConnectionEvent(String var1, String var2, Bundle var3, Session.Info var4) throws RemoteException;

    public void onConnectionServiceFocusReleased(Session.Info var1) throws RemoteException;

    public void onPhoneAccountChanged(String var1, PhoneAccountHandle var2, Session.Info var3) throws RemoteException;

    public void onPostDialChar(String var1, char var2, Session.Info var3) throws RemoteException;

    public void onPostDialWait(String var1, String var2, Session.Info var3) throws RemoteException;

    public void onRemoteRttRequest(String var1, Session.Info var2) throws RemoteException;

    public void onRttInitiationFailure(String var1, int var2, Session.Info var3) throws RemoteException;

    public void onRttInitiationSuccess(String var1, Session.Info var2) throws RemoteException;

    public void onRttSessionRemotelyTerminated(String var1, Session.Info var2) throws RemoteException;

    public void putExtras(String var1, Bundle var2, Session.Info var3) throws RemoteException;

    public void queryRemoteConnectionServices(RemoteServiceCallback var1, String var2, Session.Info var3) throws RemoteException;

    public void removeCall(String var1, Session.Info var2) throws RemoteException;

    public void removeExtras(String var1, List<String> var2, Session.Info var3) throws RemoteException;

    public void resetConnectionTime(String var1, Session.Info var2) throws RemoteException;

    public void setActive(String var1, Session.Info var2) throws RemoteException;

    public void setAddress(String var1, Uri var2, int var3, Session.Info var4) throws RemoteException;

    public void setAudioRoute(String var1, int var2, String var3, Session.Info var4) throws RemoteException;

    public void setCallerDisplayName(String var1, String var2, int var3, Session.Info var4) throws RemoteException;

    public void setConferenceMergeFailed(String var1, Session.Info var2) throws RemoteException;

    public void setConferenceState(String var1, boolean var2, Session.Info var3) throws RemoteException;

    public void setConferenceableConnections(String var1, List<String> var2, Session.Info var3) throws RemoteException;

    public void setConnectionCapabilities(String var1, int var2, Session.Info var3) throws RemoteException;

    public void setConnectionProperties(String var1, int var2, Session.Info var3) throws RemoteException;

    public void setDialing(String var1, Session.Info var2) throws RemoteException;

    public void setDisconnected(String var1, DisconnectCause var2, Session.Info var3) throws RemoteException;

    public void setIsConferenced(String var1, String var2, Session.Info var3) throws RemoteException;

    public void setIsVoipAudioMode(String var1, boolean var2, Session.Info var3) throws RemoteException;

    public void setOnHold(String var1, Session.Info var2) throws RemoteException;

    public void setPulling(String var1, Session.Info var2) throws RemoteException;

    public void setRingbackRequested(String var1, boolean var2, Session.Info var3) throws RemoteException;

    public void setRinging(String var1, Session.Info var2) throws RemoteException;

    public void setStatusHints(String var1, StatusHints var2, Session.Info var3) throws RemoteException;

    public void setVideoProvider(String var1, IVideoProvider var2, Session.Info var3) throws RemoteException;

    public void setVideoState(String var1, int var2, Session.Info var3) throws RemoteException;

    public static class Default
    implements IConnectionServiceAdapter {
        @Override
        public void addConferenceCall(String string2, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
        }

        @Override
        public void addExistingConnection(String string2, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void handleCreateConnectionComplete(String string2, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
        }

        @Override
        public void onConnectionEvent(String string2, String string3, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override
        public void onConnectionServiceFocusReleased(Session.Info info) throws RemoteException {
        }

        @Override
        public void onPhoneAccountChanged(String string2, PhoneAccountHandle phoneAccountHandle, Session.Info info) throws RemoteException {
        }

        @Override
        public void onPostDialChar(String string2, char c, Session.Info info) throws RemoteException {
        }

        @Override
        public void onPostDialWait(String string2, String string3, Session.Info info) throws RemoteException {
        }

        @Override
        public void onRemoteRttRequest(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void onRttInitiationFailure(String string2, int n, Session.Info info) throws RemoteException {
        }

        @Override
        public void onRttInitiationSuccess(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void onRttSessionRemotelyTerminated(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void putExtras(String string2, Bundle bundle, Session.Info info) throws RemoteException {
        }

        @Override
        public void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void removeCall(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void removeExtras(String string2, List<String> list, Session.Info info) throws RemoteException {
        }

        @Override
        public void resetConnectionTime(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void setActive(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void setAddress(String string2, Uri uri, int n, Session.Info info) throws RemoteException {
        }

        @Override
        public void setAudioRoute(String string2, int n, String string3, Session.Info info) throws RemoteException {
        }

        @Override
        public void setCallerDisplayName(String string2, String string3, int n, Session.Info info) throws RemoteException {
        }

        @Override
        public void setConferenceMergeFailed(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void setConferenceState(String string2, boolean bl, Session.Info info) throws RemoteException {
        }

        @Override
        public void setConferenceableConnections(String string2, List<String> list, Session.Info info) throws RemoteException {
        }

        @Override
        public void setConnectionCapabilities(String string2, int n, Session.Info info) throws RemoteException {
        }

        @Override
        public void setConnectionProperties(String string2, int n, Session.Info info) throws RemoteException {
        }

        @Override
        public void setDialing(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void setDisconnected(String string2, DisconnectCause disconnectCause, Session.Info info) throws RemoteException {
        }

        @Override
        public void setIsConferenced(String string2, String string3, Session.Info info) throws RemoteException {
        }

        @Override
        public void setIsVoipAudioMode(String string2, boolean bl, Session.Info info) throws RemoteException {
        }

        @Override
        public void setOnHold(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void setPulling(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void setRingbackRequested(String string2, boolean bl, Session.Info info) throws RemoteException {
        }

        @Override
        public void setRinging(String string2, Session.Info info) throws RemoteException {
        }

        @Override
        public void setStatusHints(String string2, StatusHints statusHints, Session.Info info) throws RemoteException {
        }

        @Override
        public void setVideoProvider(String string2, IVideoProvider iVideoProvider, Session.Info info) throws RemoteException {
        }

        @Override
        public void setVideoState(String string2, int n, Session.Info info) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IConnectionServiceAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IConnectionServiceAdapter";
        static final int TRANSACTION_addConferenceCall = 13;
        static final int TRANSACTION_addExistingConnection = 25;
        static final int TRANSACTION_handleCreateConnectionComplete = 1;
        static final int TRANSACTION_onConnectionEvent = 29;
        static final int TRANSACTION_onConnectionServiceFocusReleased = 35;
        static final int TRANSACTION_onPhoneAccountChanged = 34;
        static final int TRANSACTION_onPostDialChar = 16;
        static final int TRANSACTION_onPostDialWait = 15;
        static final int TRANSACTION_onRemoteRttRequest = 33;
        static final int TRANSACTION_onRttInitiationFailure = 31;
        static final int TRANSACTION_onRttInitiationSuccess = 30;
        static final int TRANSACTION_onRttSessionRemotelyTerminated = 32;
        static final int TRANSACTION_putExtras = 26;
        static final int TRANSACTION_queryRemoteConnectionServices = 17;
        static final int TRANSACTION_removeCall = 14;
        static final int TRANSACTION_removeExtras = 27;
        static final int TRANSACTION_resetConnectionTime = 36;
        static final int TRANSACTION_setActive = 2;
        static final int TRANSACTION_setAddress = 22;
        static final int TRANSACTION_setAudioRoute = 28;
        static final int TRANSACTION_setCallerDisplayName = 23;
        static final int TRANSACTION_setConferenceMergeFailed = 12;
        static final int TRANSACTION_setConferenceState = 37;
        static final int TRANSACTION_setConferenceableConnections = 24;
        static final int TRANSACTION_setConnectionCapabilities = 9;
        static final int TRANSACTION_setConnectionProperties = 10;
        static final int TRANSACTION_setDialing = 4;
        static final int TRANSACTION_setDisconnected = 6;
        static final int TRANSACTION_setIsConferenced = 11;
        static final int TRANSACTION_setIsVoipAudioMode = 20;
        static final int TRANSACTION_setOnHold = 7;
        static final int TRANSACTION_setPulling = 5;
        static final int TRANSACTION_setRingbackRequested = 8;
        static final int TRANSACTION_setRinging = 3;
        static final int TRANSACTION_setStatusHints = 21;
        static final int TRANSACTION_setVideoProvider = 18;
        static final int TRANSACTION_setVideoState = 19;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IConnectionServiceAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IConnectionServiceAdapter) {
                return (IConnectionServiceAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IConnectionServiceAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 37: {
                    return "setConferenceState";
                }
                case 36: {
                    return "resetConnectionTime";
                }
                case 35: {
                    return "onConnectionServiceFocusReleased";
                }
                case 34: {
                    return "onPhoneAccountChanged";
                }
                case 33: {
                    return "onRemoteRttRequest";
                }
                case 32: {
                    return "onRttSessionRemotelyTerminated";
                }
                case 31: {
                    return "onRttInitiationFailure";
                }
                case 30: {
                    return "onRttInitiationSuccess";
                }
                case 29: {
                    return "onConnectionEvent";
                }
                case 28: {
                    return "setAudioRoute";
                }
                case 27: {
                    return "removeExtras";
                }
                case 26: {
                    return "putExtras";
                }
                case 25: {
                    return "addExistingConnection";
                }
                case 24: {
                    return "setConferenceableConnections";
                }
                case 23: {
                    return "setCallerDisplayName";
                }
                case 22: {
                    return "setAddress";
                }
                case 21: {
                    return "setStatusHints";
                }
                case 20: {
                    return "setIsVoipAudioMode";
                }
                case 19: {
                    return "setVideoState";
                }
                case 18: {
                    return "setVideoProvider";
                }
                case 17: {
                    return "queryRemoteConnectionServices";
                }
                case 16: {
                    return "onPostDialChar";
                }
                case 15: {
                    return "onPostDialWait";
                }
                case 14: {
                    return "removeCall";
                }
                case 13: {
                    return "addConferenceCall";
                }
                case 12: {
                    return "setConferenceMergeFailed";
                }
                case 11: {
                    return "setIsConferenced";
                }
                case 10: {
                    return "setConnectionProperties";
                }
                case 9: {
                    return "setConnectionCapabilities";
                }
                case 8: {
                    return "setRingbackRequested";
                }
                case 7: {
                    return "setOnHold";
                }
                case 6: {
                    return "setDisconnected";
                }
                case 5: {
                    return "setPulling";
                }
                case 4: {
                    return "setDialing";
                }
                case 3: {
                    return "setRinging";
                }
                case 2: {
                    return "setActive";
                }
                case 1: 
            }
            return "handleCreateConnectionComplete";
        }

        public static boolean setDefaultImpl(IConnectionServiceAdapter iConnectionServiceAdapter) {
            if (Proxy.sDefaultImpl == null && iConnectionServiceAdapter != null) {
                Proxy.sDefaultImpl = iConnectionServiceAdapter;
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
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setConferenceState((String)object2, bl3, (Session.Info)object);
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.resetConnectionTime((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onConnectionServiceFocusReleased((Session.Info)object);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPhoneAccountChanged(string2, (PhoneAccountHandle)object2, (Session.Info)object);
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRemoteRttRequest((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRttSessionRemotelyTerminated((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRttInitiationFailure((String)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onRttInitiationSuccess((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        String string4 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onConnectionEvent(string3, string4, (Bundle)object2, (Session.Info)object);
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string5 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAudioRoute(string5, n, (String)object2, (Session.Info)object);
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        ArrayList<String> arrayList = ((Parcel)object).createStringArrayList();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeExtras((String)object2, arrayList, (Session.Info)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.putExtras(string6, (Bundle)object2, (Session.Info)object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string7 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? ParcelableConnection.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addExistingConnection(string7, (ParcelableConnection)object2, (Session.Info)object);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string8 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).createStringArrayList();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setConferenceableConnections(string8, (List<String>)object2, (Session.Info)object);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string9 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setCallerDisplayName(string9, (String)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string10 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAddress(string10, (Uri)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string11 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? StatusHints.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setStatusHints(string11, (StatusHints)object2, (Session.Info)object);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        bl3 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setIsVoipAudioMode((String)object2, bl3, (Session.Info)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setVideoState((String)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string12 = ((Parcel)object).readString();
                        object2 = IVideoProvider.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setVideoProvider(string12, (IVideoProvider)object2, (Session.Info)object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        RemoteServiceCallback remoteServiceCallback = RemoteServiceCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.queryRemoteConnectionServices(remoteServiceCallback, (String)object2, (Session.Info)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        char c = (char)((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPostDialChar((String)object2, c, (Session.Info)object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        String string13 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPostDialWait((String)object2, string13, (Session.Info)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeCall((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string14 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? ParcelableConference.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addConferenceCall(string14, (ParcelableConference)object2, (Session.Info)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setConferenceMergeFailed((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        String string15 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setIsConferenced((String)object2, string15, (Session.Info)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setConnectionProperties((String)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setConnectionCapabilities((String)object2, n, (Session.Info)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        bl3 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setRingbackRequested((String)object2, bl3, (Session.Info)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setOnHold((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string16 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? DisconnectCause.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDisconnected(string16, (DisconnectCause)object2, (Session.Info)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPulling((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDialing((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setRinging((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setActive((String)object2, (Session.Info)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string17 = ((Parcel)object).readString();
                object2 = ((Parcel)object).readInt() != 0 ? ConnectionRequest.CREATOR.createFromParcel((Parcel)object) : null;
                ParcelableConnection parcelableConnection = ((Parcel)object).readInt() != 0 ? ParcelableConnection.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? Session.Info.CREATOR.createFromParcel((Parcel)object) : null;
                this.handleCreateConnectionComplete(string17, (ConnectionRequest)object2, parcelableConnection, (Session.Info)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IConnectionServiceAdapter {
            public static IConnectionServiceAdapter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addConferenceCall(String string2, ParcelableConference parcelableConference, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelableConference != null) {
                        parcel.writeInt(1);
                        parcelableConference.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addConferenceCall(string2, parcelableConference, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void addExistingConnection(String string2, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (parcelableConnection != null) {
                        parcel.writeInt(1);
                        parcelableConnection.writeToParcel(parcel, 0);
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
                        Stub.getDefaultImpl().addExistingConnection(string2, parcelableConnection, info);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void handleCreateConnectionComplete(String string2, ConnectionRequest connectionRequest, ParcelableConnection parcelableConnection, Session.Info info) throws RemoteException {
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
                    if (parcelableConnection != null) {
                        parcel.writeInt(1);
                        parcelableConnection.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleCreateConnectionComplete(string2, connectionRequest, parcelableConnection, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConnectionEvent(String string2, String string3, Bundle bundle, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(29, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectionEvent(string2, string3, bundle, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConnectionServiceFocusReleased(Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectionServiceFocusReleased(info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPhoneAccountChanged(String string2, PhoneAccountHandle phoneAccountHandle, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(34, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPhoneAccountChanged(string2, phoneAccountHandle, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPostDialChar(String string2, char c, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPostDialChar(string2, c, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPostDialWait(String string2, String string3, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPostDialWait(string2, string3, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRemoteRttRequest(String string2, Session.Info info) throws RemoteException {
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
                        Stub.getDefaultImpl().onRemoteRttRequest(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRttInitiationFailure(String string2, int n, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(31, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRttInitiationFailure(string2, n, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRttInitiationSuccess(String string2, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(30, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRttInitiationSuccess(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRttSessionRemotelyTerminated(String string2, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(32, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRttSessionRemotelyTerminated(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void putExtras(String string2, Bundle bundle, Session.Info info) throws RemoteException {
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
                        Stub.getDefaultImpl().putExtras(string2, bundle, info);
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
            public void queryRemoteConnectionServices(RemoteServiceCallback remoteServiceCallback, String string2, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = remoteServiceCallback != null ? remoteServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(17, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().queryRemoteConnectionServices(remoteServiceCallback, string2, info);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeCall(String string2, Session.Info info) throws RemoteException {
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
                        Stub.getDefaultImpl().removeCall(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeExtras(String string2, List<String> list, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStringList(list);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeExtras(string2, list, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void resetConnectionTime(String string2, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(36, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resetConnectionTime(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setActive(String string2, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActive(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setAddress(String string2, Uri uri, int n, Session.Info info) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAddress(string2, uri, n, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setAudioRoute(String string2, int n, String string3, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(28, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAudioRoute(string2, n, string3, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setCallerDisplayName(String string2, String string3, int n, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCallerDisplayName(string2, string3, n, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setConferenceMergeFailed(String string2, Session.Info info) throws RemoteException {
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
                        Stub.getDefaultImpl().setConferenceMergeFailed(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setConferenceState(String string2, boolean bl, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(37, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setConferenceState(string2, bl, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setConferenceableConnections(String string2, List<String> list, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStringList(list);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setConferenceableConnections(string2, list, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setConnectionCapabilities(String string2, int n, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setConnectionCapabilities(string2, n, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setConnectionProperties(String string2, int n, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setConnectionProperties(string2, n, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setDialing(String string2, Session.Info info) throws RemoteException {
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
                        Stub.getDefaultImpl().setDialing(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setDisconnected(String string2, DisconnectCause disconnectCause, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (disconnectCause != null) {
                        parcel.writeInt(1);
                        disconnectCause.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDisconnected(string2, disconnectCause, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setIsConferenced(String string2, String string3, Session.Info info) throws RemoteException {
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
                        Stub.getDefaultImpl().setIsConferenced(string2, string3, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setIsVoipAudioMode(String string2, boolean bl, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setIsVoipAudioMode(string2, bl, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setOnHold(String string2, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOnHold(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPulling(String string2, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPulling(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRingbackRequested(String string2, boolean bl, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRingbackRequested(string2, bl, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRinging(String string2, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRinging(string2, info);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setStatusHints(String string2, StatusHints statusHints, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (statusHints != null) {
                        parcel.writeInt(1);
                        statusHints.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStatusHints(string2, statusHints, info);
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
            public void setVideoProvider(String string2, IVideoProvider iVideoProvider, Session.Info info) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iVideoProvider != null ? iVideoProvider.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (info != null) {
                        parcel.writeInt(1);
                        info.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(18, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setVideoProvider(string2, iVideoProvider, info);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setVideoState(String string2, int n, Session.Info info) throws RemoteException {
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
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVideoState(string2, n, info);
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

