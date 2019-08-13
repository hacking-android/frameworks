/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.inputmethod;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.InputChannel;
import com.android.internal.inputmethod.IMultiClientInputMethodSession;
import com.android.internal.view.IInputMethodSession;

public interface IMultiClientInputMethodPrivilegedOperations
extends IInterface {
    public void acceptClient(int var1, IInputMethodSession var2, IMultiClientInputMethodSession var3, InputChannel var4) throws RemoteException;

    public IBinder createInputMethodWindowToken(int var1) throws RemoteException;

    public void deleteInputMethodWindowToken(IBinder var1) throws RemoteException;

    public boolean isUidAllowedOnDisplay(int var1, int var2) throws RemoteException;

    public void reportImeWindowTarget(int var1, int var2, IBinder var3) throws RemoteException;

    public void setActive(int var1, boolean var2) throws RemoteException;

    public static class Default
    implements IMultiClientInputMethodPrivilegedOperations {
        @Override
        public void acceptClient(int n, IInputMethodSession iInputMethodSession, IMultiClientInputMethodSession iMultiClientInputMethodSession, InputChannel inputChannel) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public IBinder createInputMethodWindowToken(int n) throws RemoteException {
            return null;
        }

        @Override
        public void deleteInputMethodWindowToken(IBinder iBinder) throws RemoteException {
        }

        @Override
        public boolean isUidAllowedOnDisplay(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void reportImeWindowTarget(int n, int n2, IBinder iBinder) throws RemoteException {
        }

        @Override
        public void setActive(int n, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMultiClientInputMethodPrivilegedOperations {
        private static final String DESCRIPTOR = "com.android.internal.inputmethod.IMultiClientInputMethodPrivilegedOperations";
        static final int TRANSACTION_acceptClient = 3;
        static final int TRANSACTION_createInputMethodWindowToken = 1;
        static final int TRANSACTION_deleteInputMethodWindowToken = 2;
        static final int TRANSACTION_isUidAllowedOnDisplay = 5;
        static final int TRANSACTION_reportImeWindowTarget = 4;
        static final int TRANSACTION_setActive = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMultiClientInputMethodPrivilegedOperations asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMultiClientInputMethodPrivilegedOperations) {
                return (IMultiClientInputMethodPrivilegedOperations)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMultiClientInputMethodPrivilegedOperations getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "setActive";
                }
                case 5: {
                    return "isUidAllowedOnDisplay";
                }
                case 4: {
                    return "reportImeWindowTarget";
                }
                case 3: {
                    return "acceptClient";
                }
                case 2: {
                    return "deleteInputMethodWindowToken";
                }
                case 1: 
            }
            return "createInputMethodWindowToken";
        }

        public static boolean setDefaultImpl(IMultiClientInputMethodPrivilegedOperations iMultiClientInputMethodPrivilegedOperations) {
            if (Proxy.sDefaultImpl == null && iMultiClientInputMethodPrivilegedOperations != null) {
                Proxy.sDefaultImpl = iMultiClientInputMethodPrivilegedOperations;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.setActive(n, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUidAllowedOnDisplay(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reportImeWindowTarget(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        IInputMethodSession iInputMethodSession = IInputMethodSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IMultiClientInputMethodSession iMultiClientInputMethodSession = IMultiClientInputMethodSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? InputChannel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.acceptClient(n, iInputMethodSession, iMultiClientInputMethodSession, (InputChannel)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteInputMethodWindowToken(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.createInputMethodWindowToken(((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMultiClientInputMethodPrivilegedOperations {
            public static IMultiClientInputMethodPrivilegedOperations sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void acceptClient(int n, IInputMethodSession iInputMethodSession, IMultiClientInputMethodSession iMultiClientInputMethodSession, InputChannel inputChannel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    Object var7_8 = null;
                    IBinder iBinder = iInputMethodSession != null ? iInputMethodSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var7_8;
                    if (iMultiClientInputMethodSession != null) {
                        iBinder = iMultiClientInputMethodSession.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (inputChannel != null) {
                        parcel.writeInt(1);
                        inputChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acceptClient(n, iInputMethodSession, iMultiClientInputMethodSession, inputChannel);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public IBinder createInputMethodWindowToken(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IBinder iBinder = Stub.getDefaultImpl().createInputMethodWindowToken(n);
                        return iBinder;
                    }
                    parcel2.readException();
                    IBinder iBinder = parcel2.readStrongBinder();
                    return iBinder;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void deleteInputMethodWindowToken(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteInputMethodWindowToken(iBinder);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean isUidAllowedOnDisplay(int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(5, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUidAllowedOnDisplay(n, n2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void reportImeWindowTarget(int n, int n2, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportImeWindowTarget(n, n2, iBinder);
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
            public void setActive(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setActive(n, bl);
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

