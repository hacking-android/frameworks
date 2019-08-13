/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.mbms.vendor;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.mbms.IGroupCallCallback;
import android.telephony.mbms.IMbmsGroupCallSessionCallback;
import java.util.ArrayList;
import java.util.List;

public interface IMbmsGroupCallService
extends IInterface {
    public void dispose(int var1) throws RemoteException;

    public int initialize(IMbmsGroupCallSessionCallback var1, int var2) throws RemoteException;

    public int startGroupCall(int var1, long var2, List var4, List var5, IGroupCallCallback var6) throws RemoteException;

    public void stopGroupCall(int var1, long var2) throws RemoteException;

    public void updateGroupCall(int var1, long var2, List var4, List var5) throws RemoteException;

    public static class Default
    implements IMbmsGroupCallService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void dispose(int n) throws RemoteException {
        }

        @Override
        public int initialize(IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int n) throws RemoteException {
            return 0;
        }

        @Override
        public int startGroupCall(int n, long l, List list, List list2, IGroupCallCallback iGroupCallCallback) throws RemoteException {
            return 0;
        }

        @Override
        public void stopGroupCall(int n, long l) throws RemoteException {
        }

        @Override
        public void updateGroupCall(int n, long l, List list, List list2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMbmsGroupCallService {
        private static final String DESCRIPTOR = "android.telephony.mbms.vendor.IMbmsGroupCallService";
        static final int TRANSACTION_dispose = 5;
        static final int TRANSACTION_initialize = 1;
        static final int TRANSACTION_startGroupCall = 4;
        static final int TRANSACTION_stopGroupCall = 2;
        static final int TRANSACTION_updateGroupCall = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMbmsGroupCallService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMbmsGroupCallService) {
                return (IMbmsGroupCallService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMbmsGroupCallService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "dispose";
                        }
                        return "startGroupCall";
                    }
                    return "updateGroupCall";
                }
                return "stopGroupCall";
            }
            return "initialize";
        }

        public static boolean setDefaultImpl(IMbmsGroupCallService iMbmsGroupCallService) {
            if (Proxy.sDefaultImpl == null && iMbmsGroupCallService != null) {
                Proxy.sDefaultImpl = iMbmsGroupCallService;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, parcel, parcel2, n2);
                                }
                                parcel2.writeString(DESCRIPTOR);
                                return true;
                            }
                            parcel.enforceInterface(DESCRIPTOR);
                            this.dispose(parcel.readInt());
                            parcel2.writeNoException();
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        n = parcel.readInt();
                        long l = parcel.readLong();
                        ClassLoader classLoader = this.getClass().getClassLoader();
                        n = this.startGroupCall(n, l, parcel.readArrayList(classLoader), parcel.readArrayList(classLoader), IGroupCallCallback.Stub.asInterface(parcel.readStrongBinder()));
                        parcel2.writeNoException();
                        parcel2.writeInt(n);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = parcel.readInt();
                    long l = parcel.readLong();
                    ClassLoader classLoader = this.getClass().getClassLoader();
                    this.updateGroupCall(n, l, parcel.readArrayList(classLoader), parcel.readArrayList(classLoader));
                    parcel2.writeNoException();
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.stopGroupCall(parcel.readInt(), parcel.readLong());
                parcel2.writeNoException();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            n = this.initialize(IMbmsGroupCallSessionCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
            parcel2.writeNoException();
            parcel2.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IMbmsGroupCallService {
            public static IMbmsGroupCallService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void dispose(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispose(n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int initialize(IMbmsGroupCallSessionCallback iMbmsGroupCallSessionCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMbmsGroupCallSessionCallback != null ? iMbmsGroupCallSessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().initialize(iMbmsGroupCallSessionCallback, n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
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
            public int startGroupCall(int n, long l, List list, List list2, IGroupCallCallback iGroupCallCallback) throws RemoteException {
                Parcel parcel;
                void var4_10;
                Parcel parcel2;
                block14 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeLong(l);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeList(list);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeList(list2);
                        IBinder iBinder = iGroupCallCallback != null ? iGroupCallCallback.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(4, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            n = Stub.getDefaultImpl().startGroupCall(n, l, list, list2, iGroupCallCallback);
                            parcel.recycle();
                            parcel2.recycle();
                            return n;
                        }
                        parcel.readException();
                        n = parcel.readInt();
                        parcel.recycle();
                        parcel2.recycle();
                        return n;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var4_10;
            }

            @Override
            public void stopGroupCall(int n, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopGroupCall(n, l);
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
            public void updateGroupCall(int n, long l, List list, List list2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeList(list);
                    parcel.writeList(list2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateGroupCall(n, l, list, list2);
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

