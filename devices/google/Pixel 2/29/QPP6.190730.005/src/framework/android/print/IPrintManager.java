/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.IPrintDocumentAdapter;
import android.print.IPrintJobStateChangeListener;
import android.print.IPrintServicesChangeListener;
import android.print.IPrinterDiscoveryObserver;
import android.print.PrintAttributes;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.printservice.PrintServiceInfo;
import android.printservice.recommendation.IRecommendationsChangeListener;
import android.printservice.recommendation.RecommendationInfo;
import java.util.ArrayList;
import java.util.List;

public interface IPrintManager
extends IInterface {
    public void addPrintJobStateChangeListener(IPrintJobStateChangeListener var1, int var2, int var3) throws RemoteException;

    public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener var1, int var2) throws RemoteException;

    public void addPrintServicesChangeListener(IPrintServicesChangeListener var1, int var2) throws RemoteException;

    public void cancelPrintJob(PrintJobId var1, int var2, int var3) throws RemoteException;

    public void createPrinterDiscoverySession(IPrinterDiscoveryObserver var1, int var2) throws RemoteException;

    public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver var1, int var2) throws RemoteException;

    public boolean getBindInstantServiceAllowed(int var1) throws RemoteException;

    public Icon getCustomPrinterIcon(PrinterId var1, int var2) throws RemoteException;

    public PrintJobInfo getPrintJobInfo(PrintJobId var1, int var2, int var3) throws RemoteException;

    public List<PrintJobInfo> getPrintJobInfos(int var1, int var2) throws RemoteException;

    public List<RecommendationInfo> getPrintServiceRecommendations(int var1) throws RemoteException;

    public List<PrintServiceInfo> getPrintServices(int var1, int var2) throws RemoteException;

    public Bundle print(String var1, IPrintDocumentAdapter var2, PrintAttributes var3, String var4, int var5, int var6) throws RemoteException;

    public void removePrintJobStateChangeListener(IPrintJobStateChangeListener var1, int var2) throws RemoteException;

    public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener var1, int var2) throws RemoteException;

    public void removePrintServicesChangeListener(IPrintServicesChangeListener var1, int var2) throws RemoteException;

    public void restartPrintJob(PrintJobId var1, int var2, int var3) throws RemoteException;

    public void setBindInstantServiceAllowed(int var1, boolean var2) throws RemoteException;

    public void setPrintServiceEnabled(ComponentName var1, boolean var2, int var3) throws RemoteException;

    public void startPrinterDiscovery(IPrinterDiscoveryObserver var1, List<PrinterId> var2, int var3) throws RemoteException;

    public void startPrinterStateTracking(PrinterId var1, int var2) throws RemoteException;

    public void stopPrinterDiscovery(IPrinterDiscoveryObserver var1, int var2) throws RemoteException;

    public void stopPrinterStateTracking(PrinterId var1, int var2) throws RemoteException;

    public void validatePrinters(List<PrinterId> var1, int var2) throws RemoteException;

    public static class Default
    implements IPrintManager {
        @Override
        public void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int n, int n2) throws RemoteException {
        }

        @Override
        public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int n) throws RemoteException {
        }

        @Override
        public void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelPrintJob(PrintJobId printJobId, int n, int n2) throws RemoteException {
        }

        @Override
        public void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int n) throws RemoteException {
        }

        @Override
        public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int n) throws RemoteException {
        }

        @Override
        public boolean getBindInstantServiceAllowed(int n) throws RemoteException {
            return false;
        }

        @Override
        public Icon getCustomPrinterIcon(PrinterId printerId, int n) throws RemoteException {
            return null;
        }

        @Override
        public PrintJobInfo getPrintJobInfo(PrintJobId printJobId, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public List<PrintJobInfo> getPrintJobInfos(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public List<RecommendationInfo> getPrintServiceRecommendations(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<PrintServiceInfo> getPrintServices(int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public Bundle print(String string2, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String string3, int n, int n2) throws RemoteException {
            return null;
        }

        @Override
        public void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int n) throws RemoteException {
        }

        @Override
        public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int n) throws RemoteException {
        }

        @Override
        public void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int n) throws RemoteException {
        }

        @Override
        public void restartPrintJob(PrintJobId printJobId, int n, int n2) throws RemoteException {
        }

        @Override
        public void setBindInstantServiceAllowed(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPrintServiceEnabled(ComponentName componentName, boolean bl, int n) throws RemoteException {
        }

        @Override
        public void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List<PrinterId> list, int n) throws RemoteException {
        }

        @Override
        public void startPrinterStateTracking(PrinterId printerId, int n) throws RemoteException {
        }

        @Override
        public void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int n) throws RemoteException {
        }

        @Override
        public void stopPrinterStateTracking(PrinterId printerId, int n) throws RemoteException {
        }

        @Override
        public void validatePrinters(List<PrinterId> list, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintManager {
        private static final String DESCRIPTOR = "android.print.IPrintManager";
        static final int TRANSACTION_addPrintJobStateChangeListener = 6;
        static final int TRANSACTION_addPrintServiceRecommendationsChangeListener = 12;
        static final int TRANSACTION_addPrintServicesChangeListener = 8;
        static final int TRANSACTION_cancelPrintJob = 4;
        static final int TRANSACTION_createPrinterDiscoverySession = 15;
        static final int TRANSACTION_destroyPrinterDiscoverySession = 22;
        static final int TRANSACTION_getBindInstantServiceAllowed = 23;
        static final int TRANSACTION_getCustomPrinterIcon = 20;
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_getPrintServiceRecommendations = 14;
        static final int TRANSACTION_getPrintServices = 10;
        static final int TRANSACTION_print = 3;
        static final int TRANSACTION_removePrintJobStateChangeListener = 7;
        static final int TRANSACTION_removePrintServiceRecommendationsChangeListener = 13;
        static final int TRANSACTION_removePrintServicesChangeListener = 9;
        static final int TRANSACTION_restartPrintJob = 5;
        static final int TRANSACTION_setBindInstantServiceAllowed = 24;
        static final int TRANSACTION_setPrintServiceEnabled = 11;
        static final int TRANSACTION_startPrinterDiscovery = 16;
        static final int TRANSACTION_startPrinterStateTracking = 19;
        static final int TRANSACTION_stopPrinterDiscovery = 17;
        static final int TRANSACTION_stopPrinterStateTracking = 21;
        static final int TRANSACTION_validatePrinters = 18;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintManager) {
                return (IPrintManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 24: {
                    return "setBindInstantServiceAllowed";
                }
                case 23: {
                    return "getBindInstantServiceAllowed";
                }
                case 22: {
                    return "destroyPrinterDiscoverySession";
                }
                case 21: {
                    return "stopPrinterStateTracking";
                }
                case 20: {
                    return "getCustomPrinterIcon";
                }
                case 19: {
                    return "startPrinterStateTracking";
                }
                case 18: {
                    return "validatePrinters";
                }
                case 17: {
                    return "stopPrinterDiscovery";
                }
                case 16: {
                    return "startPrinterDiscovery";
                }
                case 15: {
                    return "createPrinterDiscoverySession";
                }
                case 14: {
                    return "getPrintServiceRecommendations";
                }
                case 13: {
                    return "removePrintServiceRecommendationsChangeListener";
                }
                case 12: {
                    return "addPrintServiceRecommendationsChangeListener";
                }
                case 11: {
                    return "setPrintServiceEnabled";
                }
                case 10: {
                    return "getPrintServices";
                }
                case 9: {
                    return "removePrintServicesChangeListener";
                }
                case 8: {
                    return "addPrintServicesChangeListener";
                }
                case 7: {
                    return "removePrintJobStateChangeListener";
                }
                case 6: {
                    return "addPrintJobStateChangeListener";
                }
                case 5: {
                    return "restartPrintJob";
                }
                case 4: {
                    return "cancelPrintJob";
                }
                case 3: {
                    return "print";
                }
                case 2: {
                    return "getPrintJobInfo";
                }
                case 1: 
            }
            return "getPrintJobInfos";
        }

        public static boolean setDefaultImpl(IPrintManager iPrintManager) {
            if (Proxy.sDefaultImpl == null && iPrintManager != null) {
                Proxy.sDefaultImpl = iPrintManager;
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
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setBindInstantServiceAllowed(n, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getBindInstantServiceAllowed(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.destroyPrinterDiscoverySession(IPrinterDiscoveryObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrinterId printerId = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.stopPrinterStateTracking(printerId, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrinterId printerId = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getCustomPrinterIcon(printerId, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Icon)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrinterId printerId = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startPrinterStateTracking(printerId, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.validatePrinters(((Parcel)object).createTypedArrayList(PrinterId.CREATOR), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopPrinterDiscovery(IPrinterDiscoveryObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startPrinterDiscovery(IPrinterDiscoveryObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createTypedArrayList(PrinterId.CREATOR), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.createPrinterDiscoverySession(IPrinterDiscoveryObserver.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPrintServiceRecommendations(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setPrintServiceEnabled(componentName, bl2, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPrintServices(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removePrintServicesChangeListener(IPrintServicesChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addPrintServicesChangeListener(IPrintServicesChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removePrintJobStateChangeListener(IPrintJobStateChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addPrintJobStateChangeListener(IPrintJobStateChangeListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.restartPrintJob(printJobId, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.cancelPrintJob(printJobId, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        IPrintDocumentAdapter iPrintDocumentAdapter = IPrintDocumentAdapter.Stub.asInterface(((Parcel)object).readStrongBinder());
                        PrintAttributes printAttributes = ((Parcel)object).readInt() != 0 ? PrintAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.print(string2, iPrintDocumentAdapter, printAttributes, ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bundle)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPrintJobInfo(printJobId, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((PrintJobInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getPrintJobInfos(((Parcel)object).readInt(), ((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPrintManager {
            public static IPrintManager sDefaultImpl;
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
            public void addPrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintJobStateChangeListener != null ? iPrintJobStateChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPrintJobStateChangeListener(iPrintJobStateChangeListener, n, n2);
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
            public void addPrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecommendationsChangeListener != null ? iRecommendationsChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPrintServiceRecommendationsChangeListener(iRecommendationsChangeListener, n);
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
            public void addPrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintServicesChangeListener != null ? iPrintServicesChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addPrintServicesChangeListener(iPrintServicesChangeListener, n);
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
            public void cancelPrintJob(PrintJobId printJobId, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelPrintJob(printJobId, n, n2);
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
            public void createPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrinterDiscoveryObserver != null ? iPrinterDiscoveryObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().createPrinterDiscoverySession(iPrinterDiscoveryObserver, n);
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
            public void destroyPrinterDiscoverySession(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrinterDiscoveryObserver != null ? iPrinterDiscoveryObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroyPrinterDiscoverySession(iPrinterDiscoveryObserver, n);
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
            public boolean getBindInstantServiceAllowed(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(23, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getBindInstantServiceAllowed(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Icon getCustomPrinterIcon(PrinterId parcelable, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((PrinterId)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Icon icon = Stub.getDefaultImpl().getCustomPrinterIcon((PrinterId)parcelable, (int)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return icon;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        Icon icon = Icon.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PrintJobInfo getPrintJobInfo(PrintJobId parcelable, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((PrintJobId)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt((int)var2_7);
                    parcel.writeInt((int)var3_8);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        PrintJobInfo printJobInfo = Stub.getDefaultImpl().getPrintJobInfo((PrintJobId)parcelable, (int)var2_7, (int)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return printJobInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        PrintJobInfo printJobInfo = PrintJobInfo.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public List<PrintJobInfo> getPrintJobInfos(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<PrintJobInfo> list = Stub.getDefaultImpl().getPrintJobInfos(n, n2);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<PrintJobInfo> arrayList = parcel2.createTypedArrayList(PrintJobInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<RecommendationInfo> getPrintServiceRecommendations(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<RecommendationInfo> list = Stub.getDefaultImpl().getPrintServiceRecommendations(n);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<RecommendationInfo> arrayList = parcel2.createTypedArrayList(RecommendationInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<PrintServiceInfo> getPrintServices(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<PrintServiceInfo> list = Stub.getDefaultImpl().getPrintServices(n, n2);
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<PrintServiceInfo> arrayList = parcel2.createTypedArrayList(PrintServiceInfo.CREATOR);
                    return arrayList;
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
            public Bundle print(String object, IPrintDocumentAdapter iPrintDocumentAdapter, PrintAttributes printAttributes, String string2, int n, int n2) throws RemoteException {
                Parcel parcel;
                void var1_8;
                Parcel parcel2;
                block16 : {
                    block15 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString((String)object);
                            IBinder iBinder = iPrintDocumentAdapter != null ? iPrintDocumentAdapter.asBinder() : null;
                            parcel2.writeStrongBinder(iBinder);
                            if (printAttributes != null) {
                                parcel2.writeInt(1);
                                printAttributes.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        if (!this.mRemote.transact(3, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().print((String)object, iPrintDocumentAdapter, printAttributes, string2, n, n2);
                            parcel.recycle();
                            parcel2.recycle();
                            return object;
                        }
                        parcel.readException();
                        object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_8;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removePrintJobStateChangeListener(IPrintJobStateChangeListener iPrintJobStateChangeListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintJobStateChangeListener != null ? iPrintJobStateChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removePrintJobStateChangeListener(iPrintJobStateChangeListener, n);
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
            public void removePrintServiceRecommendationsChangeListener(IRecommendationsChangeListener iRecommendationsChangeListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecommendationsChangeListener != null ? iRecommendationsChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removePrintServiceRecommendationsChangeListener(iRecommendationsChangeListener, n);
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
            public void removePrintServicesChangeListener(IPrintServicesChangeListener iPrintServicesChangeListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintServicesChangeListener != null ? iPrintServicesChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removePrintServicesChangeListener(iPrintServicesChangeListener, n);
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
            public void restartPrintJob(PrintJobId printJobId, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().restartPrintJob(printJobId, n, n2);
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
            public void setBindInstantServiceAllowed(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setBindInstantServiceAllowed(n, bl);
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
            public void setPrintServiceEnabled(ComponentName componentName, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 1;
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPrintServiceEnabled(componentName, bl, n);
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
            public void startPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, List<PrinterId> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrinterDiscoveryObserver != null ? iPrinterDiscoveryObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startPrinterDiscovery(iPrinterDiscoveryObserver, list, n);
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
            public void startPrinterStateTracking(PrinterId printerId, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        parcel.writeInt(1);
                        printerId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startPrinterStateTracking(printerId, n);
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
            public void stopPrinterDiscovery(IPrinterDiscoveryObserver iPrinterDiscoveryObserver, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrinterDiscoveryObserver != null ? iPrinterDiscoveryObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopPrinterDiscovery(iPrinterDiscoveryObserver, n);
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
            public void stopPrinterStateTracking(PrinterId printerId, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        parcel.writeInt(1);
                        printerId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopPrinterStateTracking(printerId, n);
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
            public void validatePrinters(List<PrinterId> list, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().validatePrinters(list, n);
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

