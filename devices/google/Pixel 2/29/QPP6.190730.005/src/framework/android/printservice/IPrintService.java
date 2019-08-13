/*
 * Decompiled with CFR 0.145.
 */
package android.printservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.printservice.IPrintServiceClient;
import java.util.ArrayList;
import java.util.List;

public interface IPrintService
extends IInterface {
    public void createPrinterDiscoverySession() throws RemoteException;

    public void destroyPrinterDiscoverySession() throws RemoteException;

    public void onPrintJobQueued(PrintJobInfo var1) throws RemoteException;

    public void requestCancelPrintJob(PrintJobInfo var1) throws RemoteException;

    public void requestCustomPrinterIcon(PrinterId var1) throws RemoteException;

    public void setClient(IPrintServiceClient var1) throws RemoteException;

    public void startPrinterDiscovery(List<PrinterId> var1) throws RemoteException;

    public void startPrinterStateTracking(PrinterId var1) throws RemoteException;

    public void stopPrinterDiscovery() throws RemoteException;

    public void stopPrinterStateTracking(PrinterId var1) throws RemoteException;

    public void validatePrinters(List<PrinterId> var1) throws RemoteException;

    public static class Default
    implements IPrintService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void createPrinterDiscoverySession() throws RemoteException {
        }

        @Override
        public void destroyPrinterDiscoverySession() throws RemoteException {
        }

        @Override
        public void onPrintJobQueued(PrintJobInfo printJobInfo) throws RemoteException {
        }

        @Override
        public void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
        }

        @Override
        public void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException {
        }

        @Override
        public void setClient(IPrintServiceClient iPrintServiceClient) throws RemoteException {
        }

        @Override
        public void startPrinterDiscovery(List<PrinterId> list) throws RemoteException {
        }

        @Override
        public void startPrinterStateTracking(PrinterId printerId) throws RemoteException {
        }

        @Override
        public void stopPrinterDiscovery() throws RemoteException {
        }

        @Override
        public void stopPrinterStateTracking(PrinterId printerId) throws RemoteException {
        }

        @Override
        public void validatePrinters(List<PrinterId> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintService {
        private static final String DESCRIPTOR = "android.printservice.IPrintService";
        static final int TRANSACTION_createPrinterDiscoverySession = 4;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 11;
        static final int TRANSACTION_onPrintJobQueued = 3;
        static final int TRANSACTION_requestCancelPrintJob = 2;
        static final int TRANSACTION_requestCustomPrinterIcon = 9;
        static final int TRANSACTION_setClient = 1;
        static final int TRANSACTION_startPrinterDiscovery = 5;
        static final int TRANSACTION_startPrinterStateTracking = 8;
        static final int TRANSACTION_stopPrinterDiscovery = 6;
        static final int TRANSACTION_stopPrinterStateTracking = 10;
        static final int TRANSACTION_validatePrinters = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintService) {
                return (IPrintService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "destroyPrinterDiscoverySession";
                }
                case 10: {
                    return "stopPrinterStateTracking";
                }
                case 9: {
                    return "requestCustomPrinterIcon";
                }
                case 8: {
                    return "startPrinterStateTracking";
                }
                case 7: {
                    return "validatePrinters";
                }
                case 6: {
                    return "stopPrinterDiscovery";
                }
                case 5: {
                    return "startPrinterDiscovery";
                }
                case 4: {
                    return "createPrinterDiscoverySession";
                }
                case 3: {
                    return "onPrintJobQueued";
                }
                case 2: {
                    return "requestCancelPrintJob";
                }
                case 1: 
            }
            return "setClient";
        }

        public static boolean setDefaultImpl(IPrintService iPrintService) {
            if (Proxy.sDefaultImpl == null && iPrintService != null) {
                Proxy.sDefaultImpl = iPrintService;
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
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.destroyPrinterDiscoverySession();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.stopPrinterStateTracking((PrinterId)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestCustomPrinterIcon((PrinterId)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startPrinterStateTracking((PrinterId)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.validatePrinters(((Parcel)object).createTypedArrayList(PrinterId.CREATOR));
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopPrinterDiscovery();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startPrinterDiscovery(((Parcel)object).createTypedArrayList(PrinterId.CREATOR));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.createPrinterDiscoverySession();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPrintJobQueued((PrintJobInfo)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestCancelPrintJob((PrintJobInfo)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setClient(IPrintServiceClient.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPrintService {
            public static IPrintService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void createPrinterDiscoverySession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createPrinterDiscoverySession();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void destroyPrinterDiscoverySession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyPrinterDiscoverySession();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
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
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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
            public void requestCancelPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobInfo != null) {
                        parcel.writeInt(1);
                        printJobInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestCancelPrintJob(printJobInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void requestCustomPrinterIcon(PrinterId printerId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        parcel.writeInt(1);
                        printerId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestCustomPrinterIcon(printerId);
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
            public void setClient(IPrintServiceClient iPrintServiceClient) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintServiceClient != null ? iPrintServiceClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setClient(iPrintServiceClient);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void startPrinterDiscovery(List<PrinterId> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startPrinterDiscovery(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void startPrinterStateTracking(PrinterId printerId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        parcel.writeInt(1);
                        printerId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startPrinterStateTracking(printerId);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopPrinterDiscovery() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopPrinterDiscovery();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopPrinterStateTracking(PrinterId printerId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        parcel.writeInt(1);
                        printerId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopPrinterStateTracking(printerId);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void validatePrinters(List<PrinterId> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().validatePrinters(list);
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

