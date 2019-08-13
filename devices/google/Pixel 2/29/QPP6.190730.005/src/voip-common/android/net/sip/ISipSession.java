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

import android.net.sip.ISipSessionListener;
import android.net.sip.SipProfile;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISipSession
extends IInterface {
    public void answerCall(String var1, int var2) throws RemoteException;

    public void changeCall(String var1, int var2) throws RemoteException;

    public void endCall() throws RemoteException;

    public String getCallId() throws RemoteException;

    public String getLocalIp() throws RemoteException;

    public SipProfile getLocalProfile() throws RemoteException;

    public SipProfile getPeerProfile() throws RemoteException;

    public int getState() throws RemoteException;

    public boolean isInCall() throws RemoteException;

    public void makeCall(SipProfile var1, String var2, int var3) throws RemoteException;

    public void register(int var1) throws RemoteException;

    public void setListener(ISipSessionListener var1) throws RemoteException;

    public void unregister() throws RemoteException;

    public static class Default
    implements ISipSession {
        @Override
        public void answerCall(String string, int n) throws RemoteException {
        }

        public IBinder asBinder() {
            return null;
        }

        @Override
        public void changeCall(String string, int n) throws RemoteException {
        }

        @Override
        public void endCall() throws RemoteException {
        }

        @Override
        public String getCallId() throws RemoteException {
            return null;
        }

        @Override
        public String getLocalIp() throws RemoteException {
            return null;
        }

        @Override
        public SipProfile getLocalProfile() throws RemoteException {
            return null;
        }

        @Override
        public SipProfile getPeerProfile() throws RemoteException {
            return null;
        }

        @Override
        public int getState() throws RemoteException {
            return 0;
        }

        @Override
        public boolean isInCall() throws RemoteException {
            return false;
        }

        @Override
        public void makeCall(SipProfile sipProfile, String string, int n) throws RemoteException {
        }

        @Override
        public void register(int n) throws RemoteException {
        }

        @Override
        public void setListener(ISipSessionListener iSipSessionListener) throws RemoteException {
        }

        @Override
        public void unregister() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISipSession {
        private static final String DESCRIPTOR = "android.net.sip.ISipSession";
        static final int TRANSACTION_answerCall = 11;
        static final int TRANSACTION_changeCall = 13;
        static final int TRANSACTION_endCall = 12;
        static final int TRANSACTION_getCallId = 6;
        static final int TRANSACTION_getLocalIp = 1;
        static final int TRANSACTION_getLocalProfile = 2;
        static final int TRANSACTION_getPeerProfile = 3;
        static final int TRANSACTION_getState = 4;
        static final int TRANSACTION_isInCall = 5;
        static final int TRANSACTION_makeCall = 10;
        static final int TRANSACTION_register = 8;
        static final int TRANSACTION_setListener = 7;
        static final int TRANSACTION_unregister = 9;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static ISipSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISipSession) {
                return (ISipSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISipSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(ISipSession iSipSession) {
            if (Proxy.sDefaultImpl == null && iSipSession != null) {
                Proxy.sDefaultImpl = iSipSession;
                return true;
            }
            return false;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 13: {
                        object.enforceInterface(DESCRIPTOR);
                        this.changeCall(object.readString(), object.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        object.enforceInterface(DESCRIPTOR);
                        this.endCall();
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        object.enforceInterface(DESCRIPTOR);
                        this.answerCall(object.readString(), object.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        object.enforceInterface(DESCRIPTOR);
                        SipProfile sipProfile = object.readInt() != 0 ? (SipProfile)SipProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.makeCall(sipProfile, object.readString(), object.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        object.enforceInterface(DESCRIPTOR);
                        this.unregister();
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        object.enforceInterface(DESCRIPTOR);
                        this.register(object.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        object.enforceInterface(DESCRIPTOR);
                        this.setListener(ISipSessionListener.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getCallId();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.isInCall() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.getState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getPeerProfile();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SipProfile)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getLocalProfile();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SipProfile)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                object = this.getLocalIp();
                parcel.writeNoException();
                parcel.writeString((String)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISipSession {
            public static ISipSession sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void answerCall(String string, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().answerCall(string, n);
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

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void changeCall(String string, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().changeCall(string, n);
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

            @Override
            public void endCall() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endCall();
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

            @Override
            public String getCallId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string = Stub.getDefaultImpl().getCallId();
                        return string;
                    }
                    parcel2.readException();
                    String string = parcel2.readString();
                    return string;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String getLocalIp() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string = Stub.getDefaultImpl().getLocalIp();
                        return string;
                    }
                    parcel2.readException();
                    String string = parcel2.readString();
                    return string;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public SipProfile getLocalProfile() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    SipProfile sipProfile;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        sipProfile = Stub.getDefaultImpl().getLocalProfile();
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel.recycle();
                    parcel2.recycle();
                    return sipProfile;
                }
                parcel.readException();
                SipProfile sipProfile = parcel.readInt() != 0 ? (SipProfile)SipProfile.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return sipProfile;
            }

            @Override
            public SipProfile getPeerProfile() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    SipProfile sipProfile;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        sipProfile = Stub.getDefaultImpl().getPeerProfile();
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel.recycle();
                    parcel2.recycle();
                    return sipProfile;
                }
                parcel.readException();
                SipProfile sipProfile = parcel.readInt() != 0 ? (SipProfile)SipProfile.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return sipProfile;
            }

            @Override
            public int getState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isInCall() throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    block4 : {
                        IBinder iBinder;
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        try {
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            iBinder = this.mRemote;
                            bl = false;
                        }
                        catch (Throwable throwable) {
                            parcel.recycle();
                            parcel2.recycle();
                            throw throwable;
                        }
                        if (iBinder.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                        bl = Stub.getDefaultImpl().isInCall();
                        parcel.recycle();
                        parcel2.recycle();
                        return bl;
                    }
                    parcel.readException();
                    int n = parcel.readInt();
                    if (n == 0) break block5;
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void makeCall(SipProfile sipProfile, String string, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (sipProfile != null) {
                        parcel.writeInt(1);
                        sipProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().makeCall(sipProfile, string, n);
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

            @Override
            public void register(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().register(n);
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
            public void setListener(ISipSessionListener iSipSessionListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSipSessionListener != null ? iSipSessionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setListener(iSipSessionListener);
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

            @Override
            public void unregister() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregister();
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

