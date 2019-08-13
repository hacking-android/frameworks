/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.annotation.UnsupportedAppUsage;
import android.app.job.JobParameters;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IJobService
extends IInterface {
    @UnsupportedAppUsage
    public void startJob(JobParameters var1) throws RemoteException;

    @UnsupportedAppUsage
    public void stopJob(JobParameters var1) throws RemoteException;

    public static class Default
    implements IJobService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void startJob(JobParameters jobParameters) throws RemoteException {
        }

        @Override
        public void stopJob(JobParameters jobParameters) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IJobService {
        private static final String DESCRIPTOR = "android.app.job.IJobService";
        static final int TRANSACTION_startJob = 1;
        static final int TRANSACTION_stopJob = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IJobService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IJobService) {
                return (IJobService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IJobService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "stopJob";
            }
            return "startJob";
        }

        public static boolean setDefaultImpl(IJobService iJobService) {
            if (Proxy.sDefaultImpl == null && iJobService != null) {
                Proxy.sDefaultImpl = iJobService;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? JobParameters.CREATOR.createFromParcel((Parcel)object) : null;
                this.stopJob((JobParameters)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? JobParameters.CREATOR.createFromParcel((Parcel)object) : null;
            this.startJob((JobParameters)object);
            return true;
        }

        private static class Proxy
        implements IJobService {
            public static IJobService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void startJob(JobParameters jobParameters) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (jobParameters != null) {
                        parcel.writeInt(1);
                        jobParameters.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startJob(jobParameters);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopJob(JobParameters jobParameters) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (jobParameters != null) {
                        parcel.writeInt(1);
                        jobParameters.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopJob(jobParameters);
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

