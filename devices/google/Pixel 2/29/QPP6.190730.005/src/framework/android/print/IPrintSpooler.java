/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.content.ComponentName;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.IPrintSpoolerCallbacks;
import android.print.IPrintSpoolerClient;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public interface IPrintSpooler
extends IInterface {
    public void clearCustomPrinterIconCache(IPrintSpoolerCallbacks var1, int var2) throws RemoteException;

    public void createPrintJob(PrintJobInfo var1) throws RemoteException;

    public void getCustomPrinterIcon(PrinterId var1, IPrintSpoolerCallbacks var2, int var3) throws RemoteException;

    public void getPrintJobInfo(PrintJobId var1, IPrintSpoolerCallbacks var2, int var3, int var4) throws RemoteException;

    public void getPrintJobInfos(IPrintSpoolerCallbacks var1, ComponentName var2, int var3, int var4, int var5) throws RemoteException;

    public void onCustomPrinterIconLoaded(PrinterId var1, Icon var2, IPrintSpoolerCallbacks var3, int var4) throws RemoteException;

    public void pruneApprovedPrintServices(List<ComponentName> var1) throws RemoteException;

    public void removeObsoletePrintJobs() throws RemoteException;

    public void setClient(IPrintSpoolerClient var1) throws RemoteException;

    public void setPrintJobCancelling(PrintJobId var1, boolean var2) throws RemoteException;

    public void setPrintJobState(PrintJobId var1, int var2, String var3, IPrintSpoolerCallbacks var4, int var5) throws RemoteException;

    public void setPrintJobTag(PrintJobId var1, String var2, IPrintSpoolerCallbacks var3, int var4) throws RemoteException;

    public void setProgress(PrintJobId var1, float var2) throws RemoteException;

    public void setStatus(PrintJobId var1, CharSequence var2) throws RemoteException;

    public void setStatusRes(PrintJobId var1, int var2, CharSequence var3) throws RemoteException;

    public void writePrintJobData(ParcelFileDescriptor var1, PrintJobId var2) throws RemoteException;

    public static class Default
    implements IPrintSpooler {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearCustomPrinterIconCache(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
        }

        @Override
        public void createPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
        }

        @Override
        public void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
        }

        @Override
        public void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n, int n2) throws RemoteException {
        }

        @Override
        public void getPrintJobInfos(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, ComponentName componentName, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
        }

        @Override
        public void pruneApprovedPrintServices(List<ComponentName> list) throws RemoteException {
        }

        @Override
        public void removeObsoletePrintJobs() throws RemoteException {
        }

        @Override
        public void setClient(IPrintSpoolerClient iPrintSpoolerClient) throws RemoteException {
        }

        @Override
        public void setPrintJobCancelling(PrintJobId printJobId, boolean bl) throws RemoteException {
        }

        @Override
        public void setPrintJobState(PrintJobId printJobId, int n, String string2, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n2) throws RemoteException {
        }

        @Override
        public void setPrintJobTag(PrintJobId printJobId, String string2, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
        }

        @Override
        public void setProgress(PrintJobId printJobId, float f) throws RemoteException {
        }

        @Override
        public void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void setStatusRes(PrintJobId printJobId, int n, CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintSpooler {
        private static final String DESCRIPTOR = "android.print.IPrintSpooler";
        static final int TRANSACTION_clearCustomPrinterIconCache = 11;
        static final int TRANSACTION_createPrintJob = 4;
        static final int TRANSACTION_getCustomPrinterIcon = 10;
        static final int TRANSACTION_getPrintJobInfo = 3;
        static final int TRANSACTION_getPrintJobInfos = 2;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 9;
        static final int TRANSACTION_pruneApprovedPrintServices = 16;
        static final int TRANSACTION_removeObsoletePrintJobs = 1;
        static final int TRANSACTION_setClient = 14;
        static final int TRANSACTION_setPrintJobCancelling = 15;
        static final int TRANSACTION_setPrintJobState = 5;
        static final int TRANSACTION_setPrintJobTag = 12;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 13;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintSpooler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintSpooler) {
                return (IPrintSpooler)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintSpooler getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 16: {
                    return "pruneApprovedPrintServices";
                }
                case 15: {
                    return "setPrintJobCancelling";
                }
                case 14: {
                    return "setClient";
                }
                case 13: {
                    return "writePrintJobData";
                }
                case 12: {
                    return "setPrintJobTag";
                }
                case 11: {
                    return "clearCustomPrinterIconCache";
                }
                case 10: {
                    return "getCustomPrinterIcon";
                }
                case 9: {
                    return "onCustomPrinterIconLoaded";
                }
                case 8: {
                    return "setStatusRes";
                }
                case 7: {
                    return "setStatus";
                }
                case 6: {
                    return "setProgress";
                }
                case 5: {
                    return "setPrintJobState";
                }
                case 4: {
                    return "createPrintJob";
                }
                case 3: {
                    return "getPrintJobInfo";
                }
                case 2: {
                    return "getPrintJobInfos";
                }
                case 1: 
            }
            return "removeObsoletePrintJobs";
        }

        public static boolean setDefaultImpl(IPrintSpooler iPrintSpooler) {
            if (Proxy.sDefaultImpl == null && iPrintSpooler != null) {
                Proxy.sDefaultImpl = iPrintSpooler;
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
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pruneApprovedPrintServices(((Parcel)object).createTypedArrayList(ComponentName.CREATOR));
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.setPrintJobCancelling((PrintJobId)object2, bl);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setClient(IPrintSpoolerClient.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.writePrintJobData((ParcelFileDescriptor)object2, (PrintJobId)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPrintJobTag((PrintJobId)object2, ((Parcel)object).readString(), IPrintSpoolerCallbacks.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearCustomPrinterIconCache(IPrintSpoolerCallbacks.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getCustomPrinterIcon((PrinterId)object2, IPrintSpoolerCallbacks.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        Icon icon = ((Parcel)object).readInt() != 0 ? Icon.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCustomPrinterIconLoaded((PrinterId)object2, icon, IPrintSpoolerCallbacks.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setStatusRes((PrintJobId)object2, n, (CharSequence)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setStatus((PrintJobId)object2, (CharSequence)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setProgress((PrintJobId)object2, ((Parcel)object).readFloat());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPrintJobState((PrintJobId)object2, ((Parcel)object).readInt(), ((Parcel)object).readString(), IPrintSpoolerCallbacks.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrintJobInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createPrintJob((PrintJobInfo)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getPrintJobInfo((PrintJobId)object2, IPrintSpoolerCallbacks.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IPrintSpoolerCallbacks iPrintSpoolerCallbacks = IPrintSpoolerCallbacks.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getPrintJobInfos(iPrintSpoolerCallbacks, (ComponentName)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.removeObsoletePrintJobs();
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPrintSpooler {
            public static IPrintSpooler sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void clearCustomPrinterIconCache(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintSpoolerCallbacks != null ? iPrintSpoolerCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().clearCustomPrinterIconCache(iPrintSpoolerCallbacks, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void createPrintJob(PrintJobInfo printJobInfo) throws RemoteException {
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
                        Stub.getDefaultImpl().createPrintJob(printJobInfo);
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
            public void getCustomPrinterIcon(PrinterId printerId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        parcel.writeInt(1);
                        printerId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iPrintSpoolerCallbacks != null ? iPrintSpoolerCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(10, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getCustomPrinterIcon(printerId, iPrintSpoolerCallbacks, n);
                    return;
                }
                finally {
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
            public void getPrintJobInfo(PrintJobId printJobId, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iPrintSpoolerCallbacks != null ? iPrintSpoolerCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getPrintJobInfo(printJobId, iPrintSpoolerCallbacks, n, n2);
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
            public void getPrintJobInfos(IPrintSpoolerCallbacks iPrintSpoolerCallbacks, ComponentName componentName, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintSpoolerCallbacks != null ? iPrintSpoolerCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getPrintJobInfos(iPrintSpoolerCallbacks, componentName, n, n2, n3);
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
            public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printerId != null) {
                        parcel.writeInt(1);
                        printerId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (icon != null) {
                        parcel.writeInt(1);
                        icon.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iPrintSpoolerCallbacks != null ? iPrintSpoolerCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onCustomPrinterIconLoaded(printerId, icon, iPrintSpoolerCallbacks, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void pruneApprovedPrintServices(List<ComponentName> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pruneApprovedPrintServices(list);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeObsoletePrintJobs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeObsoletePrintJobs();
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
            public void setClient(IPrintSpoolerClient iPrintSpoolerClient) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintSpoolerClient != null ? iPrintSpoolerClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(14, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setClient(iPrintSpoolerClient);
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
            public void setPrintJobCancelling(PrintJobId printJobId, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(15, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setPrintJobCancelling(printJobId, bl);
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
            public void setPrintJobState(PrintJobId printJobId, int n, String string2, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    IBinder iBinder = iPrintSpoolerCallbacks != null ? iPrintSpoolerCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setPrintJobState(printJobId, n, string2, iPrintSpoolerCallbacks, n2);
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
            public void setPrintJobTag(PrintJobId printJobId, String string2, IPrintSpoolerCallbacks iPrintSpoolerCallbacks, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    IBinder iBinder = iPrintSpoolerCallbacks != null ? iPrintSpoolerCallbacks.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setPrintJobTag(printJobId, string2, iPrintSpoolerCallbacks, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setProgress(PrintJobId printJobId, float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProgress(printJobId, f);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStatus(printJobId, charSequence);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setStatusRes(PrintJobId printJobId, int n, CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStatusRes(printJobId, n, charSequence);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void writePrintJobData(ParcelFileDescriptor parcelFileDescriptor, PrintJobId printJobId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().writePrintJobData(parcelFileDescriptor, printJobId);
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

