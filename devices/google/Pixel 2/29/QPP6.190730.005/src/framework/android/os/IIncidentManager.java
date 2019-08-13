/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IIncidentReportStatusListener;
import android.os.IInterface;
import android.os.IncidentManager;
import android.os.IncidentReportArgs;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

public interface IIncidentManager
extends IInterface {
    public void deleteAllIncidentReports(String var1) throws RemoteException;

    public void deleteIncidentReports(String var1, String var2, String var3) throws RemoteException;

    public IncidentManager.IncidentReport getIncidentReport(String var1, String var2, String var3) throws RemoteException;

    public List<String> getIncidentReportList(String var1, String var2) throws RemoteException;

    public void reportIncident(IncidentReportArgs var1) throws RemoteException;

    public void reportIncidentToStream(IncidentReportArgs var1, IIncidentReportStatusListener var2, FileDescriptor var3) throws RemoteException;

    public void systemRunning() throws RemoteException;

    public static class Default
    implements IIncidentManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void deleteAllIncidentReports(String string2) throws RemoteException {
        }

        @Override
        public void deleteIncidentReports(String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public IncidentManager.IncidentReport getIncidentReport(String string2, String string3, String string4) throws RemoteException {
            return null;
        }

        @Override
        public List<String> getIncidentReportList(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void reportIncident(IncidentReportArgs incidentReportArgs) throws RemoteException {
        }

        @Override
        public void reportIncidentToStream(IncidentReportArgs incidentReportArgs, IIncidentReportStatusListener iIncidentReportStatusListener, FileDescriptor fileDescriptor) throws RemoteException {
        }

        @Override
        public void systemRunning() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIncidentManager {
        private static final String DESCRIPTOR = "android.os.IIncidentManager";
        static final int TRANSACTION_deleteAllIncidentReports = 7;
        static final int TRANSACTION_deleteIncidentReports = 6;
        static final int TRANSACTION_getIncidentReport = 5;
        static final int TRANSACTION_getIncidentReportList = 4;
        static final int TRANSACTION_reportIncident = 1;
        static final int TRANSACTION_reportIncidentToStream = 2;
        static final int TRANSACTION_systemRunning = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIncidentManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIncidentManager) {
                return (IIncidentManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIncidentManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "deleteAllIncidentReports";
                }
                case 6: {
                    return "deleteIncidentReports";
                }
                case 5: {
                    return "getIncidentReport";
                }
                case 4: {
                    return "getIncidentReportList";
                }
                case 3: {
                    return "systemRunning";
                }
                case 2: {
                    return "reportIncidentToStream";
                }
                case 1: 
            }
            return "reportIncident";
        }

        public static boolean setDefaultImpl(IIncidentManager iIncidentManager) {
            if (Proxy.sDefaultImpl == null && iIncidentManager != null) {
                Proxy.sDefaultImpl = iIncidentManager;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteAllIncidentReports(((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteIncidentReports(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIncidentReport(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((IncidentManager.IncidentReport)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIncidentReportList(((Parcel)object).readString(), ((Parcel)object).readString());
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeStringList((List<String>)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.systemRunning();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? IncidentReportArgs.CREATOR.createFromParcel((Parcel)object) : null;
                        this.reportIncidentToStream((IncidentReportArgs)object2, IIncidentReportStatusListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readRawFileDescriptor());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? IncidentReportArgs.CREATOR.createFromParcel((Parcel)object) : null;
                this.reportIncident((IncidentReportArgs)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IIncidentManager {
            public static IIncidentManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void deleteAllIncidentReports(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteAllIncidentReports(string2);
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
            public void deleteIncidentReports(String string2, String string3, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteIncidentReports(string2, string3, string4);
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
            public IncidentManager.IncidentReport getIncidentReport(String object, String string2, String string3) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeString(string2);
                        parcel.writeString(string3);
                        if (this.mRemote.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getIncidentReport((String)object, string2, string3);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? IncidentManager.IncidentReport.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public List<String> getIncidentReportList(String list, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)((Object)list));
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        list = Stub.getDefaultImpl().getIncidentReportList((String)((Object)list), string2);
                        return list;
                    }
                    parcel2.readException();
                    list = parcel2.createStringArrayList();
                    return list;
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
            public void reportIncident(IncidentReportArgs incidentReportArgs) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (incidentReportArgs != null) {
                        parcel.writeInt(1);
                        incidentReportArgs.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportIncident(incidentReportArgs);
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
            public void reportIncidentToStream(IncidentReportArgs incidentReportArgs, IIncidentReportStatusListener iIncidentReportStatusListener, FileDescriptor fileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (incidentReportArgs != null) {
                        parcel.writeInt(1);
                        incidentReportArgs.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iIncidentReportStatusListener != null ? iIncidentReportStatusListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeRawFileDescriptor(fileDescriptor);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().reportIncidentToStream(incidentReportArgs, iIncidentReportStatusListener, fileDescriptor);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void systemRunning() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().systemRunning();
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

