/*
 * Decompiled with CFR 0.145.
 */
package android.printservice;

import android.content.pm.ParceledListSlice;
import android.graphics.drawable.Icon;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterId;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public interface IPrintServiceClient
extends IInterface {
    public PrintJobInfo getPrintJobInfo(PrintJobId var1) throws RemoteException;

    public List<PrintJobInfo> getPrintJobInfos() throws RemoteException;

    public void onCustomPrinterIconLoaded(PrinterId var1, Icon var2) throws RemoteException;

    public void onPrintersAdded(ParceledListSlice var1) throws RemoteException;

    public void onPrintersRemoved(ParceledListSlice var1) throws RemoteException;

    public boolean setPrintJobState(PrintJobId var1, int var2, String var3) throws RemoteException;

    public boolean setPrintJobTag(PrintJobId var1, String var2) throws RemoteException;

    public void setProgress(PrintJobId var1, float var2) throws RemoteException;

    public void setStatus(PrintJobId var1, CharSequence var2) throws RemoteException;

    public void setStatusRes(PrintJobId var1, int var2, CharSequence var3) throws RemoteException;

    public void writePrintJobData(ParcelFileDescriptor var1, PrintJobId var2) throws RemoteException;

    public static class Default
    implements IPrintServiceClient {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public PrintJobInfo getPrintJobInfo(PrintJobId printJobId) throws RemoteException {
            return null;
        }

        @Override
        public List<PrintJobInfo> getPrintJobInfos() throws RemoteException {
            return null;
        }

        @Override
        public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException {
        }

        @Override
        public void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override
        public boolean setPrintJobState(PrintJobId printJobId, int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean setPrintJobTag(PrintJobId printJobId, String string2) throws RemoteException {
            return false;
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
    implements IPrintServiceClient {
        private static final String DESCRIPTOR = "android.printservice.IPrintServiceClient";
        static final int TRANSACTION_getPrintJobInfo = 2;
        static final int TRANSACTION_getPrintJobInfos = 1;
        static final int TRANSACTION_onCustomPrinterIconLoaded = 11;
        static final int TRANSACTION_onPrintersAdded = 9;
        static final int TRANSACTION_onPrintersRemoved = 10;
        static final int TRANSACTION_setPrintJobState = 3;
        static final int TRANSACTION_setPrintJobTag = 4;
        static final int TRANSACTION_setProgress = 6;
        static final int TRANSACTION_setStatus = 7;
        static final int TRANSACTION_setStatusRes = 8;
        static final int TRANSACTION_writePrintJobData = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintServiceClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintServiceClient) {
                return (IPrintServiceClient)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintServiceClient getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "onCustomPrinterIconLoaded";
                }
                case 10: {
                    return "onPrintersRemoved";
                }
                case 9: {
                    return "onPrintersAdded";
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
                    return "writePrintJobData";
                }
                case 4: {
                    return "setPrintJobTag";
                }
                case 3: {
                    return "setPrintJobState";
                }
                case 2: {
                    return "getPrintJobInfo";
                }
                case 1: 
            }
            return "getPrintJobInfos";
        }

        public static boolean setDefaultImpl(IPrintServiceClient iPrintServiceClient) {
            if (Proxy.sDefaultImpl == null && iPrintServiceClient != null) {
                Proxy.sDefaultImpl = iPrintServiceClient;
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
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrinterId printerId = ((Parcel)object).readInt() != 0 ? PrinterId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? Icon.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCustomPrinterIconLoaded(printerId, (Icon)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPrintersRemoved((ParceledListSlice)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onPrintersAdded((ParceledListSlice)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setStatusRes(printJobId, n, (CharSequence)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setStatus(printJobId, (CharSequence)object);
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setProgress(printJobId, ((Parcel)object).readFloat());
                        ((Parcel)object2).writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.writePrintJobData((ParcelFileDescriptor)object2, (PrintJobId)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setPrintJobTag(printJobId, ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PrintJobId printJobId = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.setPrintJobState(printJobId, ((Parcel)object).readInt(), ((Parcel)object).readString()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? PrintJobId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getPrintJobInfo((PrintJobId)object);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((PrintJobInfo)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getPrintJobInfos();
                ((Parcel)object2).writeNoException();
                ((Parcel)object2).writeTypedList(object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPrintServiceClient {
            public static IPrintServiceClient sDefaultImpl;
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PrintJobInfo getPrintJobInfo(PrintJobId parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((PrintJobId)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        PrintJobInfo printJobInfo = Stub.getDefaultImpl().getPrintJobInfo((PrintJobId)parcelable);
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
            public List<PrintJobInfo> getPrintJobInfos() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<PrintJobInfo> list = Stub.getDefaultImpl().getPrintJobInfos();
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
            public void onCustomPrinterIconLoaded(PrinterId printerId, Icon icon) throws RemoteException {
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
                    if (icon != null) {
                        parcel.writeInt(1);
                        icon.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCustomPrinterIconLoaded(printerId, icon);
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
            public void onPrintersAdded(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintersAdded(parceledListSlice);
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
            public void onPrintersRemoved(ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parceledListSlice != null) {
                        parcel.writeInt(1);
                        parceledListSlice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrintersRemoved(parceledListSlice);
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
            public boolean setPrintJobState(PrintJobId printJobId, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setPrintJobState(printJobId, n, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean setPrintJobTag(PrintJobId printJobId, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (printJobId != null) {
                        parcel.writeInt(1);
                        printJobId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setPrintJobTag(printJobId, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void setProgress(PrintJobId printJobId, float f) throws RemoteException {
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
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProgress(printJobId, f);
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
            public void setStatus(PrintJobId printJobId, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStatus(printJobId, charSequence);
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
            public void setStatusRes(PrintJobId printJobId, int n, CharSequence charSequence) throws RemoteException {
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
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStatusRes(printJobId, n, charSequence);
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
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
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

