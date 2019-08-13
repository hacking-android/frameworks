/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.annotation.UnsupportedAppUsage;
import android.app.job.JobWorkItem;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IJobCallback
extends IInterface {
    @UnsupportedAppUsage
    public void acknowledgeStartMessage(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void acknowledgeStopMessage(int var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public boolean completeWork(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public JobWorkItem dequeueWork(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void jobFinished(int var1, boolean var2) throws RemoteException;

    public static class Default
    implements IJobCallback {
        @Override
        public void acknowledgeStartMessage(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void acknowledgeStopMessage(int n, boolean bl) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean completeWork(int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public JobWorkItem dequeueWork(int n) throws RemoteException {
            return null;
        }

        @Override
        public void jobFinished(int n, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IJobCallback {
        private static final String DESCRIPTOR = "android.app.job.IJobCallback";
        static final int TRANSACTION_acknowledgeStartMessage = 1;
        static final int TRANSACTION_acknowledgeStopMessage = 2;
        static final int TRANSACTION_completeWork = 4;
        static final int TRANSACTION_dequeueWork = 3;
        static final int TRANSACTION_jobFinished = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IJobCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IJobCallback) {
                return (IJobCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IJobCallback getDefaultImpl() {
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
                            return "jobFinished";
                        }
                        return "completeWork";
                    }
                    return "dequeueWork";
                }
                return "acknowledgeStopMessage";
            }
            return "acknowledgeStartMessage";
        }

        public static boolean setDefaultImpl(IJobCallback iJobCallback) {
            if (Proxy.sDefaultImpl == null && iJobCallback != null) {
                Proxy.sDefaultImpl = iJobCallback;
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
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            n = ((Parcel)object).readInt();
                            if (((Parcel)object).readInt() != 0) {
                                bl3 = true;
                            }
                            this.jobFinished(n, bl3);
                            parcel.writeNoException();
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.completeWork(((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = this.dequeueWork(((Parcel)object).readInt());
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((JobWorkItem)object).writeToParcel(parcel, 1);
                    } else {
                        parcel.writeInt(0);
                    }
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                bl3 = bl;
                if (((Parcel)object).readInt() != 0) {
                    bl3 = true;
                }
                this.acknowledgeStopMessage(n, bl3);
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            bl3 = bl2;
            if (((Parcel)object).readInt() != 0) {
                bl3 = true;
            }
            this.acknowledgeStartMessage(n, bl3);
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IJobCallback {
            public static IJobCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void acknowledgeStartMessage(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acknowledgeStartMessage(n, bl);
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
            public void acknowledgeStopMessage(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().acknowledgeStopMessage(n, bl);
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
            public boolean completeWork(int n, int n2) throws RemoteException {
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
                    if (iBinder.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().completeWork(n, n2);
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
            public JobWorkItem dequeueWork(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        JobWorkItem jobWorkItem = Stub.getDefaultImpl().dequeueWork(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return jobWorkItem;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                JobWorkItem jobWorkItem = parcel2.readInt() != 0 ? JobWorkItem.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return jobWorkItem;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void jobFinished(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().jobFinished(n, bl);
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

