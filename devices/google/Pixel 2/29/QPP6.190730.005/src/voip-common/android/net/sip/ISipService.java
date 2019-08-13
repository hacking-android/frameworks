/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Binder
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package android.net.sip;

import android.app.PendingIntent;
import android.net.sip.ISipSession;
import android.net.sip.ISipSessionListener;
import android.net.sip.SipProfile;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISipService
extends IInterface {
    public void close(String var1, String var2) throws RemoteException;

    public ISipSession createSession(SipProfile var1, ISipSessionListener var2, String var3) throws RemoteException;

    public SipProfile[] getListOfProfiles(String var1) throws RemoteException;

    public ISipSession getPendingSession(String var1, String var2) throws RemoteException;

    public boolean isOpened(String var1, String var2) throws RemoteException;

    public boolean isRegistered(String var1, String var2) throws RemoteException;

    public void open(SipProfile var1, String var2) throws RemoteException;

    public void open3(SipProfile var1, PendingIntent var2, ISipSessionListener var3, String var4) throws RemoteException;

    public void setRegistrationListener(String var1, ISipSessionListener var2, String var3) throws RemoteException;

    public static class Default
    implements ISipService {
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void close(String string, String string2) throws RemoteException {
        }

        @Override
        public ISipSession createSession(SipProfile sipProfile, ISipSessionListener iSipSessionListener, String string) throws RemoteException {
            return null;
        }

        @Override
        public SipProfile[] getListOfProfiles(String string) throws RemoteException {
            return null;
        }

        @Override
        public ISipSession getPendingSession(String string, String string2) throws RemoteException {
            return null;
        }

        @Override
        public boolean isOpened(String string, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isRegistered(String string, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void open(SipProfile sipProfile, String string) throws RemoteException {
        }

        @Override
        public void open3(SipProfile sipProfile, PendingIntent pendingIntent, ISipSessionListener iSipSessionListener, String string) throws RemoteException {
        }

        @Override
        public void setRegistrationListener(String string, ISipSessionListener iSipSessionListener, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISipService {
        private static final String DESCRIPTOR = "android.net.sip.ISipService";
        static final int TRANSACTION_close = 3;
        static final int TRANSACTION_createSession = 7;
        static final int TRANSACTION_getListOfProfiles = 9;
        static final int TRANSACTION_getPendingSession = 8;
        static final int TRANSACTION_isOpened = 4;
        static final int TRANSACTION_isRegistered = 5;
        static final int TRANSACTION_open = 1;
        static final int TRANSACTION_open3 = 2;
        static final int TRANSACTION_setRegistrationListener = 6;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static ISipService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISipService) {
                return (ISipService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISipService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(ISipService iSipService) {
            if (Proxy.sDefaultImpl == null && iSipService != null) {
                Proxy.sDefaultImpl = iSipService;
                return true;
            }
            return false;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                ISipSession iSipSession = null;
                Object object2 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 9: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getListOfProfiles(object.readString());
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 8: {
                        object.enforceInterface(DESCRIPTOR);
                        iSipSession = this.getPendingSession(object.readString(), object.readString());
                        parcel.writeNoException();
                        object = object2;
                        if (iSipSession != null) {
                            object = iSipSession.asBinder();
                        }
                        parcel.writeStrongBinder(object);
                        return true;
                    }
                    case 7: {
                        object.enforceInterface(DESCRIPTOR);
                        object2 = object.readInt() != 0 ? (SipProfile)SipProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        object2 = this.createSession((SipProfile)object2, ISipSessionListener.Stub.asInterface(object.readStrongBinder()), object.readString());
                        parcel.writeNoException();
                        object = iSipSession;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder(object);
                        return true;
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        this.setRegistrationListener(object.readString(), ISipSessionListener.Stub.asInterface(object.readStrongBinder()), object.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.isRegistered(object.readString(), object.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.isOpened(object.readString(), object.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        this.close(object.readString(), object.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        object2 = object.readInt() != 0 ? (SipProfile)SipProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        iSipSession = object.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.open3((SipProfile)object2, (PendingIntent)iSipSession, ISipSessionListener.Stub.asInterface(object.readStrongBinder()), object.readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                object2 = object.readInt() != 0 ? (SipProfile)SipProfile.CREATOR.createFromParcel((Parcel)object) : null;
                this.open((SipProfile)object2, object.readString());
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISipService {
            public static ISipService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void close(String string, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close(string, string2);
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
            public ISipSession createSession(SipProfile object, ISipSessionListener iSipSessionListener, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        ((SipProfile)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iSipSessionListener != null ? iSipSessionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createSession((SipProfile)object, iSipSessionListener, string);
                        return object;
                    }
                    parcel2.readException();
                    object = ISipSession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public SipProfile[] getListOfProfiles(String arrsipProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)arrsipProfile);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrsipProfile = Stub.getDefaultImpl().getListOfProfiles((String)arrsipProfile);
                        return arrsipProfile;
                    }
                    parcel2.readException();
                    arrsipProfile = (SipProfile[])parcel2.createTypedArray(SipProfile.CREATOR);
                    return arrsipProfile;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ISipSession getPendingSession(String object, String string) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getPendingSession((String)object, string);
                        return object;
                    }
                    parcel2.readException();
                    object = ISipSession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isOpened(String string, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    block4 : {
                        IBinder iBinder;
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        try {
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            parcel2.writeString(string);
                            parcel2.writeString(string2);
                            iBinder = this.mRemote;
                            bl = false;
                        }
                        catch (Throwable throwable) {
                            parcel.recycle();
                            parcel2.recycle();
                            throw throwable;
                        }
                        if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                        bl = Stub.getDefaultImpl().isOpened(string, string2);
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
            public boolean isRegistered(String string, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    block4 : {
                        IBinder iBinder;
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        try {
                            parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                            parcel2.writeString(string);
                            parcel2.writeString(string2);
                            iBinder = this.mRemote;
                            bl = false;
                        }
                        catch (Throwable throwable) {
                            parcel.recycle();
                            parcel2.recycle();
                            throw throwable;
                        }
                        if (iBinder.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                        bl = Stub.getDefaultImpl().isRegistered(string, string2);
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
            public void open(SipProfile sipProfile, String string) throws RemoteException {
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
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().open(sipProfile, string);
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
            public void open3(SipProfile sipProfile, PendingIntent pendingIntent, ISipSessionListener iSipSessionListener, String string) throws RemoteException {
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
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iSipSessionListener != null ? iSipSessionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().open3(sipProfile, pendingIntent, iSipSessionListener, string);
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
            public void setRegistrationListener(String string, ISipSessionListener iSipSessionListener, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string);
                    IBinder iBinder = iSipSessionListener != null ? iSipSessionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRegistrationListener(string, iSipSessionListener, string2);
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

