/*
 * Decompiled with CFR 0.145.
 */
package android.print;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.print.ILayoutResultCallback;
import android.print.IPrintDocumentAdapterObserver;
import android.print.IWriteResultCallback;
import android.print.PageRange;
import android.print.PrintAttributes;

public interface IPrintDocumentAdapter
extends IInterface {
    public void finish() throws RemoteException;

    public void kill(String var1) throws RemoteException;

    public void layout(PrintAttributes var1, PrintAttributes var2, ILayoutResultCallback var3, Bundle var4, int var5) throws RemoteException;

    public void setObserver(IPrintDocumentAdapterObserver var1) throws RemoteException;

    public void start() throws RemoteException;

    public void write(PageRange[] var1, ParcelFileDescriptor var2, IWriteResultCallback var3, int var4) throws RemoteException;

    public static class Default
    implements IPrintDocumentAdapter {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void finish() throws RemoteException {
        }

        @Override
        public void kill(String string2) throws RemoteException {
        }

        @Override
        public void layout(PrintAttributes printAttributes, PrintAttributes printAttributes2, ILayoutResultCallback iLayoutResultCallback, Bundle bundle, int n) throws RemoteException {
        }

        @Override
        public void setObserver(IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws RemoteException {
        }

        @Override
        public void start() throws RemoteException {
        }

        @Override
        public void write(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, IWriteResultCallback iWriteResultCallback, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPrintDocumentAdapter {
        private static final String DESCRIPTOR = "android.print.IPrintDocumentAdapter";
        static final int TRANSACTION_finish = 5;
        static final int TRANSACTION_kill = 6;
        static final int TRANSACTION_layout = 3;
        static final int TRANSACTION_setObserver = 1;
        static final int TRANSACTION_start = 2;
        static final int TRANSACTION_write = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPrintDocumentAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPrintDocumentAdapter) {
                return (IPrintDocumentAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPrintDocumentAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "kill";
                }
                case 5: {
                    return "finish";
                }
                case 4: {
                    return "write";
                }
                case 3: {
                    return "layout";
                }
                case 2: {
                    return "start";
                }
                case 1: 
            }
            return "setObserver";
        }

        public static boolean setDefaultImpl(IPrintDocumentAdapter iPrintDocumentAdapter) {
            if (Proxy.sDefaultImpl == null && iPrintDocumentAdapter != null) {
                Proxy.sDefaultImpl = iPrintDocumentAdapter;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.kill(parcel.readString());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.finish();
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        PageRange[] arrpageRange = parcel.createTypedArray(PageRange.CREATOR);
                        object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                        this.write(arrpageRange, (ParcelFileDescriptor)object, IWriteResultCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? PrintAttributes.CREATOR.createFromParcel(parcel) : null;
                        PrintAttributes printAttributes = parcel.readInt() != 0 ? PrintAttributes.CREATOR.createFromParcel(parcel) : null;
                        ILayoutResultCallback iLayoutResultCallback = ILayoutResultCallback.Stub.asInterface(parcel.readStrongBinder());
                        Bundle bundle = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
                        this.layout((PrintAttributes)object, printAttributes, iLayoutResultCallback, bundle, parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.start();
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.setObserver(IPrintDocumentAdapterObserver.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPrintDocumentAdapter {
            public static IPrintDocumentAdapter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void finish() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finish();
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
            public void kill(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().kill(string2);
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
            public void layout(PrintAttributes printAttributes, PrintAttributes printAttributes2, ILayoutResultCallback iLayoutResultCallback, Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (printAttributes != null) {
                        parcel.writeInt(1);
                        printAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (printAttributes2 != null) {
                        parcel.writeInt(1);
                        printAttributes2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iLayoutResultCallback != null ? iLayoutResultCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().layout(printAttributes, printAttributes2, iLayoutResultCallback, bundle, n);
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
            public void setObserver(IPrintDocumentAdapterObserver iPrintDocumentAdapterObserver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPrintDocumentAdapterObserver != null ? iPrintDocumentAdapterObserver.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setObserver(iPrintDocumentAdapterObserver);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void start() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().start();
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
            public void write(PageRange[] arrpageRange, ParcelFileDescriptor parcelFileDescriptor, IWriteResultCallback iWriteResultCallback, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrpageRange, 0);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iWriteResultCallback != null ? iWriteResultCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().write(arrpageRange, parcelFileDescriptor, iWriteResultCallback, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

