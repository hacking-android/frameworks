/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IIncidentAuthListener;
import android.os.IInterface;
import android.os.IncidentManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IIncidentCompanion
extends IInterface {
    public void approveReport(String var1) throws RemoteException;

    public void authorizeReport(int var1, String var2, String var3, String var4, int var5, IIncidentAuthListener var6) throws RemoteException;

    public void cancelAuthorization(IIncidentAuthListener var1) throws RemoteException;

    public void deleteAllIncidentReports(String var1) throws RemoteException;

    public void deleteIncidentReports(String var1, String var2, String var3) throws RemoteException;

    public void denyReport(String var1) throws RemoteException;

    public IncidentManager.IncidentReport getIncidentReport(String var1, String var2, String var3) throws RemoteException;

    public List<String> getIncidentReportList(String var1, String var2) throws RemoteException;

    public List<String> getPendingReports() throws RemoteException;

    public void sendReportReadyBroadcast(String var1, String var2) throws RemoteException;

    public static class Default
    implements IIncidentCompanion {
        @Override
        public void approveReport(String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void authorizeReport(int n, String string2, String string3, String string4, int n2, IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
        }

        @Override
        public void cancelAuthorization(IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
        }

        @Override
        public void deleteAllIncidentReports(String string2) throws RemoteException {
        }

        @Override
        public void deleteIncidentReports(String string2, String string3, String string4) throws RemoteException {
        }

        @Override
        public void denyReport(String string2) throws RemoteException {
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
        public List<String> getPendingReports() throws RemoteException {
            return null;
        }

        @Override
        public void sendReportReadyBroadcast(String string2, String string3) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIncidentCompanion {
        private static final String DESCRIPTOR = "android.os.IIncidentCompanion";
        static final int TRANSACTION_approveReport = 5;
        static final int TRANSACTION_authorizeReport = 1;
        static final int TRANSACTION_cancelAuthorization = 2;
        static final int TRANSACTION_deleteAllIncidentReports = 10;
        static final int TRANSACTION_deleteIncidentReports = 9;
        static final int TRANSACTION_denyReport = 6;
        static final int TRANSACTION_getIncidentReport = 8;
        static final int TRANSACTION_getIncidentReportList = 7;
        static final int TRANSACTION_getPendingReports = 4;
        static final int TRANSACTION_sendReportReadyBroadcast = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIncidentCompanion asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIncidentCompanion) {
                return (IIncidentCompanion)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIncidentCompanion getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "deleteAllIncidentReports";
                }
                case 9: {
                    return "deleteIncidentReports";
                }
                case 8: {
                    return "getIncidentReport";
                }
                case 7: {
                    return "getIncidentReportList";
                }
                case 6: {
                    return "denyReport";
                }
                case 5: {
                    return "approveReport";
                }
                case 4: {
                    return "getPendingReports";
                }
                case 3: {
                    return "sendReportReadyBroadcast";
                }
                case 2: {
                    return "cancelAuthorization";
                }
                case 1: 
            }
            return "authorizeReport";
        }

        public static boolean setDefaultImpl(IIncidentCompanion iIncidentCompanion) {
            if (Proxy.sDefaultImpl == null && iIncidentCompanion != null) {
                Proxy.sDefaultImpl = iIncidentCompanion;
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
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteAllIncidentReports(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteIncidentReports(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIncidentReport(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IncidentManager.IncidentReport)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getIncidentReportList(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.denyReport(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.approveReport(((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPendingReports();
                        parcel.writeNoException();
                        parcel.writeStringList((List<String>)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendReportReadyBroadcast(((Parcel)object).readString(), ((Parcel)object).readString());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelAuthorization(IIncidentAuthListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.authorizeReport(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), IIncidentAuthListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IIncidentCompanion {
            public static IIncidentCompanion sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void approveReport(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().approveReport(string2);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void authorizeReport(int n, String string2, String string3, String string4, int n2, IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
                void var2_10;
                Parcel parcel;
                block15 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string3);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string4);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                        IBinder iBinder = iIncidentAuthListener != null ? iIncidentAuthListener.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().authorizeReport(n, string2, string3, string4, n2, iIncidentAuthListener);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_10;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void cancelAuthorization(IIncidentAuthListener iIncidentAuthListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iIncidentAuthListener != null ? iIncidentAuthListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().cancelAuthorization(iIncidentAuthListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deleteAllIncidentReports(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public void denyReport(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().denyReport(string2);
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
                        if (this.mRemote.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
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
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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
            public List<String> getPendingReports() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<String> list = Stub.getDefaultImpl().getPendingReports();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<String> arrayList = parcel2.createStringArrayList();
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void sendReportReadyBroadcast(String string2, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendReportReadyBroadcast(string2, string3);
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

