/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package android.net.sip;

import android.net.sip.ISipSession;
import android.net.sip.SipProfile;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISipSessionListener
extends IInterface {
    public void onCallBusy(ISipSession var1) throws RemoteException;

    public void onCallChangeFailed(ISipSession var1, int var2, String var3) throws RemoteException;

    public void onCallEnded(ISipSession var1) throws RemoteException;

    public void onCallEstablished(ISipSession var1, String var2) throws RemoteException;

    public void onCallTransferring(ISipSession var1, String var2) throws RemoteException;

    public void onCalling(ISipSession var1) throws RemoteException;

    public void onError(ISipSession var1, int var2, String var3) throws RemoteException;

    public void onRegistering(ISipSession var1) throws RemoteException;

    public void onRegistrationDone(ISipSession var1, int var2) throws RemoteException;

    public void onRegistrationFailed(ISipSession var1, int var2, String var3) throws RemoteException;

    public void onRegistrationTimeout(ISipSession var1) throws RemoteException;

    public void onRinging(ISipSession var1, SipProfile var2, String var3) throws RemoteException;

    public void onRingingBack(ISipSession var1) throws RemoteException;

    public static class Default
    implements ISipSessionListener {
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCallBusy(ISipSession iSipSession) throws RemoteException {
        }

        @Override
        public void onCallChangeFailed(ISipSession iSipSession, int n, String string) throws RemoteException {
        }

        @Override
        public void onCallEnded(ISipSession iSipSession) throws RemoteException {
        }

        @Override
        public void onCallEstablished(ISipSession iSipSession, String string) throws RemoteException {
        }

        @Override
        public void onCallTransferring(ISipSession iSipSession, String string) throws RemoteException {
        }

        @Override
        public void onCalling(ISipSession iSipSession) throws RemoteException {
        }

        @Override
        public void onError(ISipSession iSipSession, int n, String string) throws RemoteException {
        }

        @Override
        public void onRegistering(ISipSession iSipSession) throws RemoteException {
        }

        @Override
        public void onRegistrationDone(ISipSession iSipSession, int n) throws RemoteException {
        }

        @Override
        public void onRegistrationFailed(ISipSession iSipSession, int n, String string) throws RemoteException {
        }

        @Override
        public void onRegistrationTimeout(ISipSession iSipSession) throws RemoteException {
        }

        @Override
        public void onRinging(ISipSession iSipSession, SipProfile sipProfile, String string) throws RemoteException {
        }

        @Override
        public void onRingingBack(ISipSession iSipSession) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISipSessionListener {
        private static final String DESCRIPTOR = "android.net.sip.ISipSessionListener";
        static final int TRANSACTION_onCallBusy = 6;
        static final int TRANSACTION_onCallChangeFailed = 9;
        static final int TRANSACTION_onCallEnded = 5;
        static final int TRANSACTION_onCallEstablished = 4;
        static final int TRANSACTION_onCallTransferring = 7;
        static final int TRANSACTION_onCalling = 1;
        static final int TRANSACTION_onError = 8;
        static final int TRANSACTION_onRegistering = 10;
        static final int TRANSACTION_onRegistrationDone = 11;
        static final int TRANSACTION_onRegistrationFailed = 12;
        static final int TRANSACTION_onRegistrationTimeout = 13;
        static final int TRANSACTION_onRinging = 2;
        static final int TRANSACTION_onRingingBack = 3;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static ISipSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISipSessionListener) {
                return (ISipSessionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISipSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(ISipSessionListener iSipSessionListener) {
            if (Proxy.sDefaultImpl == null && iSipSessionListener != null) {
                Proxy.sDefaultImpl = iSipSessionListener;
                return true;
            }
            return false;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    case 13: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRegistrationTimeout(ISipSession.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 12: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRegistrationFailed(ISipSession.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 11: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRegistrationDone(ISipSession.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 10: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRegistering(ISipSession.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 9: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCallChangeFailed(ISipSession.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 8: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onError(ISipSession.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCallTransferring(ISipSession.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCallBusy(ISipSession.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCallEnded(ISipSession.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onCallEstablished(ISipSession.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onRingingBack(ISipSession.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        ISipSession iSipSession = ISipSession.Stub.asInterface(parcel.readStrongBinder());
                        SipProfile sipProfile = parcel.readInt() != 0 ? (SipProfile)SipProfile.CREATOR.createFromParcel(parcel) : null;
                        this.onRinging(iSipSession, sipProfile, parcel.readString());
                        parcel2.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.onCalling(ISipSession.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISipSessionListener {
            public static ISipSessionListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCallBusy(ISipSession iSipSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallBusy(iSipSession);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCallChangeFailed(ISipSession iSipSession, int n, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallChangeFailed(iSipSession, n, string);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCallEnded(ISipSession iSipSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallEnded(iSipSession);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCallEstablished(ISipSession iSipSession, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallEstablished(iSipSession, string);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCallTransferring(ISipSession iSipSession, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCallTransferring(iSipSession, string);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCalling(ISipSession iSipSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCalling(iSipSession);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onError(ISipSession iSipSession, int n, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(iSipSession, n, string);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRegistering(ISipSession iSipSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRegistering(iSipSession);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRegistrationDone(ISipSession iSipSession, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRegistrationDone(iSipSession, n);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRegistrationFailed(ISipSession iSipSession, int n, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRegistrationFailed(iSipSession, n, string);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRegistrationTimeout(ISipSession iSipSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRegistrationTimeout(iSipSession);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRinging(ISipSession iSipSession, SipProfile sipProfile, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (sipProfile != null) {
                        parcel.writeInt(1);
                        sipProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRinging(iSipSession, sipProfile, string);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onRingingBack(ISipSession iSipSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSession != null ? iSipSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRingingBack(iSipSession);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

