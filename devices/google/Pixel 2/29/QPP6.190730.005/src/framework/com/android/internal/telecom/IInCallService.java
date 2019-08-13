/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.CallAudioState;
import android.telecom.ParcelableCall;
import com.android.internal.telecom.IInCallAdapter;

public interface IInCallService
extends IInterface {
    public void addCall(ParcelableCall var1) throws RemoteException;

    public void bringToForeground(boolean var1) throws RemoteException;

    public void onCallAudioStateChanged(CallAudioState var1) throws RemoteException;

    public void onCanAddCallChanged(boolean var1) throws RemoteException;

    public void onConnectionEvent(String var1, String var2, Bundle var3) throws RemoteException;

    public void onHandoverComplete(String var1) throws RemoteException;

    public void onHandoverFailed(String var1, int var2) throws RemoteException;

    public void onRttInitiationFailure(String var1, int var2) throws RemoteException;

    public void onRttUpgradeRequest(String var1, int var2) throws RemoteException;

    public void setInCallAdapter(IInCallAdapter var1) throws RemoteException;

    public void setPostDial(String var1, String var2) throws RemoteException;

    public void setPostDialWait(String var1, String var2) throws RemoteException;

    public void silenceRinger() throws RemoteException;

    public void updateCall(ParcelableCall var1) throws RemoteException;

    public static class Default
    implements IInCallService {
        @Override
        public void addCall(ParcelableCall parcelableCall) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void bringToForeground(boolean bl) throws RemoteException {
        }

        @Override
        public void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException {
        }

        @Override
        public void onCanAddCallChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onConnectionEvent(String string2, String string3, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onHandoverComplete(String string2) throws RemoteException {
        }

        @Override
        public void onHandoverFailed(String string2, int n) throws RemoteException {
        }

        @Override
        public void onRttInitiationFailure(String string2, int n) throws RemoteException {
        }

        @Override
        public void onRttUpgradeRequest(String string2, int n) throws RemoteException {
        }

        @Override
        public void setInCallAdapter(IInCallAdapter iInCallAdapter) throws RemoteException {
        }

        @Override
        public void setPostDial(String string2, String string3) throws RemoteException {
        }

        @Override
        public void setPostDialWait(String string2, String string3) throws RemoteException {
        }

        @Override
        public void silenceRinger() throws RemoteException {
        }

        @Override
        public void updateCall(ParcelableCall parcelableCall) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInCallService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IInCallService";
        static final int TRANSACTION_addCall = 2;
        static final int TRANSACTION_bringToForeground = 7;
        static final int TRANSACTION_onCallAudioStateChanged = 6;
        static final int TRANSACTION_onCanAddCallChanged = 8;
        static final int TRANSACTION_onConnectionEvent = 10;
        static final int TRANSACTION_onHandoverComplete = 14;
        static final int TRANSACTION_onHandoverFailed = 13;
        static final int TRANSACTION_onRttInitiationFailure = 12;
        static final int TRANSACTION_onRttUpgradeRequest = 11;
        static final int TRANSACTION_setInCallAdapter = 1;
        static final int TRANSACTION_setPostDial = 4;
        static final int TRANSACTION_setPostDialWait = 5;
        static final int TRANSACTION_silenceRinger = 9;
        static final int TRANSACTION_updateCall = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInCallService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInCallService) {
                return (IInCallService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInCallService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 14: {
                    return "onHandoverComplete";
                }
                case 13: {
                    return "onHandoverFailed";
                }
                case 12: {
                    return "onRttInitiationFailure";
                }
                case 11: {
                    return "onRttUpgradeRequest";
                }
                case 10: {
                    return "onConnectionEvent";
                }
                case 9: {
                    return "silenceRinger";
                }
                case 8: {
                    return "onCanAddCallChanged";
                }
                case 7: {
                    return "bringToForeground";
                }
                case 6: {
                    return "onCallAudioStateChanged";
                }
                case 5: {
                    return "setPostDialWait";
                }
                case 4: {
                    return "setPostDial";
                }
                case 3: {
                    return "updateCall";
                }
                case 2: {
                    return "addCall";
                }
                case 1: 
            }
            return "setInCallAdapter";
        }

        public static boolean setDefaultImpl(IInCallService iInCallService) {
            if (Proxy.sDefaultImpl == null && iInCallService != null) {
                Proxy.sDefaultImpl = iInCallService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onHandoverComplete(((Parcel)object).readString());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onHandoverFailed(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRttInitiationFailure(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRttUpgradeRequest(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onConnectionEvent(string2, (String)object2, (Bundle)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.silenceRinger();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.onCanAddCallChanged(bl2);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.bringToForeground(bl2);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? CallAudioState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCallAudioStateChanged((CallAudioState)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPostDialWait(((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPostDial(((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelableCall.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateCall((ParcelableCall)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelableCall.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addCall((ParcelableCall)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setInCallAdapter(IInCallAdapter.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInCallService {
            public static IInCallService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addCall(ParcelableCall parcelableCall) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableCall != null) {
                        parcel.writeInt(1);
                        parcelableCall.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addCall(parcelableCall);
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
            public void bringToForeground(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().bringToForeground(bl);
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
            public void onCallAudioStateChanged(CallAudioState callAudioState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callAudioState != null) {
                        parcel.writeInt(1);
                        callAudioState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallAudioStateChanged(callAudioState);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCanAddCallChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCanAddCallChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onConnectionEvent(String string2, String string3, Bundle bundle) throws RemoteException {
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
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectionEvent(string2, string3, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onHandoverComplete(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHandoverComplete(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onHandoverFailed(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHandoverFailed(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRttInitiationFailure(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRttInitiationFailure(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRttUpgradeRequest(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRttUpgradeRequest(string2, n);
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
            public void setInCallAdapter(IInCallAdapter iInCallAdapter) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInCallAdapter != null ? iInCallAdapter.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setInCallAdapter(iInCallAdapter);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPostDial(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPostDial(string2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPostDialWait(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPostDialWait(string2, string3);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void silenceRinger() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().silenceRinger();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateCall(ParcelableCall parcelableCall) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelableCall != null) {
                        parcel.writeInt(1);
                        parcelableCall.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateCall(parcelableCall);
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

