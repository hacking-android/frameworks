/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.PrintJobInfo;

public interface IPrintSpoolerClient
extends IInterface {
    public void onAllPrintJobsForServiceHandled(ComponentName var1) throws RemoteException;

    public void onAllPrintJobsHandled() throws RemoteException;

    public void onPrintJobQueued(PrintJobInfo var1) throws RemoteException;

    public void onPrintJobStateChanged(PrintJobInfo var1) throws RemoteException;

    public static class Default
    implements IPrintSpoolerClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAllPrintJobsForServiceHandled(ComponentName componentName) throws RemoteException {
        }

        @Override
        public void onAllPrintJobsHandled() throws RemoteException {
        }

        @Override
        public void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException {
        }

        @Override
        public void onPrintJobStateChanged(PrintJobInfo printJobInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintSpoolerClient {
        private static final String DESCRIPTOR = "android.print.IPrintSpoolerClient";
        static final int TRANSACTION_onAllPrintJobsForServiceHandled = 2;
        static final int TRANSACTION_onAllPrintJobsHandled = 3;
        static final int TRANSACTION_onPrintJobQueued = 1;
        static final int TRANSACTION_onPrintJobStateChanged = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpoolerClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintSpoolerClient) {
                return (IPrintSpoolerClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintSpoolerClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onPrintJobStateChanged";
                    }
                    return "onAllPrintJobsHandled";
                }
                return "onAllPrintJobsForServiceHandled";
            }
            return "onPrintJobQueued";
        }

        public static boolean setDefaultImpl(IPrintSpoolerClient iPrintSpoolerClient) {
            if (Proxy.sDefaultImpl == null && iPrintSpoolerClient != null) {
                Proxy.sDefaultImpl = iPrintSpoolerClient;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPrintJobStateChanged((PrintJobInfo)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.onAllPrintJobsHandled();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                this.onAllPrintJobsForServiceHandled((ComponentName)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onPrintJobQueued((PrintJobInfo)object);
            return true;
        }

        private static class Proxy
        implements IPrintSpoolerClient {
            public static IPrintSpoolerClient sDefaultImpl;
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
            public void onAllPrintJobsForServiceHandled(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAllPrintJobsForServiceHandled(componentName);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onAllPrintJobsHandled() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAllPrintJobsHandled();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobInfo != null) {
                        parcel.writeInt(1);
                        printJobInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintJobQueued(printJobInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPrintJobStateChanged(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobInfo != null) {
                        parcel.writeInt(1);
                        printJobInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintJobStateChanged(printJobInfo);
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

