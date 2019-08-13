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
import android.telecom.PhoneAccountHandle;
import java.util.ArrayList;
import java.util.List;

public interface IInCallAdapter
extends IInterface {
    public void answerCall(String var1, int var2) throws RemoteException;

    public void conference(String var1, String var2) throws RemoteException;

    public void deflectCall(String var1, Uri var2) throws RemoteException;

    public void disconnectCall(String var1) throws RemoteException;

    public void handoverTo(String var1, PhoneAccountHandle var2, int var3, Bundle var4) throws RemoteException;

    public void holdCall(String var1) throws RemoteException;

    public void mergeConference(String var1) throws RemoteException;

    public void mute(boolean var1) throws RemoteException;

    public void phoneAccountSelected(String var1, PhoneAccountHandle var2, boolean var3) throws RemoteException;

    public void playDtmfTone(String var1, char var2) throws RemoteException;

    public void postDialContinue(String var1, boolean var2) throws RemoteException;

    public void pullExternalCall(String var1) throws RemoteException;

    public void putExtras(String var1, Bundle var2) throws RemoteException;

    public void rejectCall(String var1, boolean var2, String var3) throws RemoteException;

    public void removeExtras(String var1, List<String> var2) throws RemoteException;

    public void respondToRttRequest(String var1, int var2, boolean var3) throws RemoteException;

    public void sendCallEvent(String var1, String var2, int var3, Bundle var4) throws RemoteException;

    public void sendRttRequest(String var1) throws RemoteException;

    public void setAudioRoute(int var1, String var2) throws RemoteException;

    public void setRttMode(String var1, int var2) throws RemoteException;

    public void splitFromConference(String var1) throws RemoteException;

    public void stopDtmfTone(String var1) throws RemoteException;

    public void stopRtt(String var1) throws RemoteException;

    public void swapConference(String var1) throws RemoteException;

    public void turnOffProximitySensor(boolean var1) throws RemoteException;

    public void turnOnProximitySensor() throws RemoteException;

    public void unholdCall(String var1) throws RemoteException;

    public static class Default
    implements IInCallAdapter {
        @Override
        public void answerCall(String string2, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void conference(String string2, String string3) throws RemoteException {
        }

        @Override
        public void deflectCall(String string2, Uri uri) throws RemoteException {
        }

        @Override
        public void disconnectCall(String string2) throws RemoteException {
        }

        @Override
        public void handoverTo(String string2, PhoneAccountHandle phoneAccountHandle, int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void holdCall(String string2) throws RemoteException {
        }

        @Override
        public void mergeConference(String string2) throws RemoteException {
        }

        @Override
        public void mute(boolean bl) throws RemoteException {
        }

        @Override
        public void phoneAccountSelected(String string2, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
        }

        @Override
        public void playDtmfTone(String string2, char c) throws RemoteException {
        }

        @Override
        public void postDialContinue(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void pullExternalCall(String string2) throws RemoteException {
        }

        @Override
        public void putExtras(String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void rejectCall(String string2, boolean bl, String string3) throws RemoteException {
        }

        @Override
        public void removeExtras(String string2, List<String> list) throws RemoteException {
        }

        @Override
        public void respondToRttRequest(String string2, int n, boolean bl) throws RemoteException {
        }

        @Override
        public void sendCallEvent(String string2, String string3, int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void sendRttRequest(String string2) throws RemoteException {
        }

        @Override
        public void setAudioRoute(int n, String string2) throws RemoteException {
        }

        @Override
        public void setRttMode(String string2, int n) throws RemoteException {
        }

        @Override
        public void splitFromConference(String string2) throws RemoteException {
        }

        @Override
        public void stopDtmfTone(String string2) throws RemoteException {
        }

        @Override
        public void stopRtt(String string2) throws RemoteException {
        }

        @Override
        public void swapConference(String string2) throws RemoteException {
        }

        @Override
        public void turnOffProximitySensor(boolean bl) throws RemoteException {
        }

        @Override
        public void turnOnProximitySensor() throws RemoteException {
        }

        @Override
        public void unholdCall(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInCallAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IInCallAdapter";
        static final int TRANSACTION_answerCall = 1;
        static final int TRANSACTION_conference = 13;
        static final int TRANSACTION_deflectCall = 2;
        static final int TRANSACTION_disconnectCall = 4;
        static final int TRANSACTION_handoverTo = 27;
        static final int TRANSACTION_holdCall = 5;
        static final int TRANSACTION_mergeConference = 15;
        static final int TRANSACTION_mute = 7;
        static final int TRANSACTION_phoneAccountSelected = 12;
        static final int TRANSACTION_playDtmfTone = 9;
        static final int TRANSACTION_postDialContinue = 11;
        static final int TRANSACTION_pullExternalCall = 19;
        static final int TRANSACTION_putExtras = 21;
        static final int TRANSACTION_rejectCall = 3;
        static final int TRANSACTION_removeExtras = 22;
        static final int TRANSACTION_respondToRttRequest = 24;
        static final int TRANSACTION_sendCallEvent = 20;
        static final int TRANSACTION_sendRttRequest = 23;
        static final int TRANSACTION_setAudioRoute = 8;
        static final int TRANSACTION_setRttMode = 26;
        static final int TRANSACTION_splitFromConference = 14;
        static final int TRANSACTION_stopDtmfTone = 10;
        static final int TRANSACTION_stopRtt = 25;
        static final int TRANSACTION_swapConference = 16;
        static final int TRANSACTION_turnOffProximitySensor = 18;
        static final int TRANSACTION_turnOnProximitySensor = 17;
        static final int TRANSACTION_unholdCall = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInCallAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInCallAdapter) {
                return (IInCallAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInCallAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 27: {
                    return "handoverTo";
                }
                case 26: {
                    return "setRttMode";
                }
                case 25: {
                    return "stopRtt";
                }
                case 24: {
                    return "respondToRttRequest";
                }
                case 23: {
                    return "sendRttRequest";
                }
                case 22: {
                    return "removeExtras";
                }
                case 21: {
                    return "putExtras";
                }
                case 20: {
                    return "sendCallEvent";
                }
                case 19: {
                    return "pullExternalCall";
                }
                case 18: {
                    return "turnOffProximitySensor";
                }
                case 17: {
                    return "turnOnProximitySensor";
                }
                case 16: {
                    return "swapConference";
                }
                case 15: {
                    return "mergeConference";
                }
                case 14: {
                    return "splitFromConference";
                }
                case 13: {
                    return "conference";
                }
                case 12: {
                    return "phoneAccountSelected";
                }
                case 11: {
                    return "postDialContinue";
                }
                case 10: {
                    return "stopDtmfTone";
                }
                case 9: {
                    return "playDtmfTone";
                }
                case 8: {
                    return "setAudioRoute";
                }
                case 7: {
                    return "mute";
                }
                case 6: {
                    return "unholdCall";
                }
                case 5: {
                    return "holdCall";
                }
                case 4: {
                    return "disconnectCall";
                }
                case 3: {
                    return "rejectCall";
                }
                case 2: {
                    return "deflectCall";
                }
                case 1: 
            }
            return "answerCall";
        }

        public static boolean setDefaultImpl(IInCallAdapter iInCallAdapter) {
            if (Proxy.sDefaultImpl == null && iInCallAdapter != null) {
                Proxy.sDefaultImpl = iInCallAdapter;
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
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handoverTo(string2, (PhoneAccountHandle)object2, n, (Bundle)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setRttMode(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopRtt(((Parcel)object).readString());
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.respondToRttRequest((String)object2, n, bl6);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendRttRequest(((Parcel)object).readString());
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeExtras(((Parcel)object).readString(), ((Parcel)object).createStringArrayList());
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.putExtras((String)object2, (Bundle)object);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendCallEvent((String)object2, string3, n, (Bundle)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pullExternalCall(((Parcel)object).readString());
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl6 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.turnOffProximitySensor(bl6);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.turnOnProximitySensor();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.swapConference(((Parcel)object).readString());
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.mergeConference(((Parcel)object).readString());
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.splitFromConference(((Parcel)object).readString());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.conference(((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        bl6 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.phoneAccountSelected(string4, (PhoneAccountHandle)object2, bl6);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        bl6 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.postDialContinue((String)object2, bl6);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopDtmfTone(((Parcel)object).readString());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.playDtmfTone(((Parcel)object).readString(), (char)((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAudioRoute(((Parcel)object).readInt(), ((Parcel)object).readString());
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl6 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.mute(bl6);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unholdCall(((Parcel)object).readString());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.holdCall(((Parcel)object).readString());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disconnectCall(((Parcel)object).readString());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        bl6 = bl5;
                        if (((Parcel)object).readInt() != 0) {
                            bl6 = true;
                        }
                        this.rejectCall((String)object2, bl6, ((Parcel)object).readString());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deflectCall((String)object2, (Uri)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.answerCall(((Parcel)object).readString(), ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInCallAdapter {
            public static IInCallAdapter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void answerCall(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().answerCall(string2, n);
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
            public void conference(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().conference(string2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deflectCall(String string2, Uri uri) throws RemoteException {
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
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deflectCall(string2, uri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void disconnectCall(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnectCall(string2);
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
            public void handoverTo(String string2, PhoneAccountHandle phoneAccountHandle, int n, Bundle bundle) throws RemoteException {
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
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handoverTo(string2, phoneAccountHandle, n, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void holdCall(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().holdCall(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void mergeConference(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().mergeConference(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void mute(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().mute(bl);
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
            public void phoneAccountSelected(String string2, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    int n = 0;
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().phoneAccountSelected(string2, phoneAccountHandle, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void playDtmfTone(String string2, char c) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(c);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().playDtmfTone(string2, c);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void postDialContinue(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().postDialContinue(string2, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void pullExternalCall(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pullExternalCall(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void putExtras(String string2, Bundle bundle) throws RemoteException {
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
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().putExtras(string2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void rejectCall(String string2, boolean bl, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().rejectCall(string2, bl, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeExtras(String string2, List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeExtras(string2, list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void respondToRttRequest(String string2, int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().respondToRttRequest(string2, n, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendCallEvent(String string2, String string3, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendCallEvent(string2, string3, n, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendRttRequest(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendRttRequest(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setAudioRoute(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAudioRoute(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRttMode(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(26, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRttMode(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void splitFromConference(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().splitFromConference(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopDtmfTone(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopDtmfTone(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopRtt(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopRtt(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void swapConference(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().swapConference(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void turnOffProximitySensor(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().turnOffProximitySensor(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void turnOnProximitySensor() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().turnOnProximitySensor();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void unholdCall(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unholdCall(string2);
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

