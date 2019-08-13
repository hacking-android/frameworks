/*
 * Decompiled with CFR 0.145.
 */
package android.app.job;

import android.app.job.JobInfo;
import android.app.job.JobWorkItem;
import android.content.pm.ParceledListSlice;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IJobScheduler
extends IInterface {
    public void cancel(int var1) throws RemoteException;

    public void cancelAll() throws RemoteException;

    public int enqueue(JobInfo var1, JobWorkItem var2) throws RemoteException;

    public ParceledListSlice getAllJobSnapshots() throws RemoteException;

    public ParceledListSlice getAllPendingJobs() throws RemoteException;

    public JobInfo getPendingJob(int var1) throws RemoteException;

    public List<JobInfo> getStartedJobs() throws RemoteException;

    public int schedule(JobInfo var1) throws RemoteException;

    public int scheduleAsPackage(JobInfo var1, String var2, int var3, String var4) throws RemoteException;

    public static class Default
    implements IJobScheduler {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancel(int n) throws RemoteException {
        }

        @Override
        public void cancelAll() throws RemoteException {
        }

        @Override
        public int enqueue(JobInfo jobInfo, JobWorkItem jobWorkItem) throws RemoteException {
            return 0;
        }

        @Override
        public ParceledListSlice getAllJobSnapshots() throws RemoteException {
            return null;
        }

        @Override
        public ParceledListSlice getAllPendingJobs() throws RemoteException {
            return null;
        }

        @Override
        public JobInfo getPendingJob(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<JobInfo> getStartedJobs() throws RemoteException {
            return null;
        }

        @Override
        public int schedule(JobInfo jobInfo) throws RemoteException {
            return 0;
        }

        @Override
        public int scheduleAsPackage(JobInfo jobInfo, String string2, int n, String string3) throws RemoteException {
            return 0;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IJobScheduler {
        private static final String DESCRIPTOR = "android.app.job.IJobScheduler";
        static final int TRANSACTION_cancel = 4;
        static final int TRANSACTION_cancelAll = 5;
        static final int TRANSACTION_enqueue = 2;
        static final int TRANSACTION_getAllJobSnapshots = 9;
        static final int TRANSACTION_getAllPendingJobs = 6;
        static final int TRANSACTION_getPendingJob = 7;
        static final int TRANSACTION_getStartedJobs = 8;
        static final int TRANSACTION_schedule = 1;
        static final int TRANSACTION_scheduleAsPackage = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IJobScheduler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IJobScheduler) {
                return (IJobScheduler)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IJobScheduler getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "getAllJobSnapshots";
                }
                case 8: {
                    return "getStartedJobs";
                }
                case 7: {
                    return "getPendingJob";
                }
                case 6: {
                    return "getAllPendingJobs";
                }
                case 5: {
                    return "cancelAll";
                }
                case 4: {
                    return "cancel";
                }
                case 3: {
                    return "scheduleAsPackage";
                }
                case 2: {
                    return "enqueue";
                }
                case 1: 
            }
            return "schedule";
        }

        public static boolean setDefaultImpl(IJobScheduler iJobScheduler) {
            if (Proxy.sDefaultImpl == null && iJobScheduler != null) {
                Proxy.sDefaultImpl = iJobScheduler;
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
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllJobSnapshots();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getStartedJobs();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPendingJob(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((JobInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getAllPendingJobs();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelAll();
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancel(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        JobInfo jobInfo = ((Parcel)object).readInt() != 0 ? JobInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.scheduleAsPackage(jobInfo, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        JobInfo jobInfo = ((Parcel)object).readInt() != 0 ? JobInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? JobWorkItem.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.enqueue(jobInfo, (JobWorkItem)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? JobInfo.CREATOR.createFromParcel((Parcel)object) : null;
                n = this.schedule((JobInfo)object);
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IJobScheduler {
            public static IJobScheduler sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancel(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancel(n);
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
            public void cancelAll() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAll();
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
            public int enqueue(JobInfo jobInfo, JobWorkItem jobWorkItem) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (jobInfo != null) {
                        parcel.writeInt(1);
                        jobInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (jobWorkItem != null) {
                        parcel.writeInt(1);
                        jobWorkItem.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().enqueue(jobInfo, jobWorkItem);
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
            public ParceledListSlice getAllJobSnapshots() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getAllJobSnapshots();
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParceledListSlice parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            @Override
            public ParceledListSlice getAllPendingJobs() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getAllPendingJobs();
                        parcel.recycle();
                        parcel2.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ParceledListSlice parceledListSlice = parcel.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return parceledListSlice;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public JobInfo getPendingJob(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        JobInfo jobInfo = Stub.getDefaultImpl().getPendingJob(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return jobInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                JobInfo jobInfo = parcel2.readInt() != 0 ? JobInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return jobInfo;
            }

            @Override
            public List<JobInfo> getStartedJobs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<JobInfo> list = Stub.getDefaultImpl().getStartedJobs();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<JobInfo> arrayList = parcel2.createTypedArrayList(JobInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int schedule(JobInfo jobInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (jobInfo != null) {
                        parcel.writeInt(1);
                        jobInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().schedule(jobInfo);
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
            public int scheduleAsPackage(JobInfo jobInfo, String string2, int n, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (jobInfo != null) {
                        parcel.writeInt(1);
                        jobInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().scheduleAsPackage(jobInfo, string2, n, string3);
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
        }

    }

}

