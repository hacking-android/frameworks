/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.os.Binder;
import android.os.IBinder;
import android.os.IDumpstateListener;
import android.os.IDumpstateToken;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.io.FileDescriptor;

public interface IDumpstate
extends IInterface {
    public static final int BUGREPORT_MODE_DEFAULT = 6;
    public static final int BUGREPORT_MODE_FULL = 0;
    public static final int BUGREPORT_MODE_INTERACTIVE = 1;
    public static final int BUGREPORT_MODE_REMOTE = 2;
    public static final int BUGREPORT_MODE_TELEPHONY = 4;
    public static final int BUGREPORT_MODE_WEAR = 3;
    public static final int BUGREPORT_MODE_WIFI = 5;

    public void cancelBugreport() throws RemoteException;

    public IDumpstateToken setListener(String var1, IDumpstateListener var2, boolean var3) throws RemoteException;

    public void startBugreport(int var1, String var2, FileDescriptor var3, FileDescriptor var4, int var5, IDumpstateListener var6) throws RemoteException;

    public static class Default
    implements IDumpstate {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelBugreport() throws RemoteException {
        }

        @Override
        public IDumpstateToken setListener(String string2, IDumpstateListener iDumpstateListener, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public void startBugreport(int n, String string2, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int n2, IDumpstateListener iDumpstateListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IDumpstate {
        private static final String DESCRIPTOR = "android.os.IDumpstate";
        static final int TRANSACTION_cancelBugreport = 3;
        static final int TRANSACTION_setListener = 1;
        static final int TRANSACTION_startBugreport = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IDumpstate asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IDumpstate) {
                return (IDumpstate)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IDumpstate getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "cancelBugreport";
                }
                return "startBugreport";
            }
            return "setListener";
        }

        public static boolean setDefaultImpl(IDumpstate iDumpstate) {
            if (Proxy.sDefaultImpl == null && iDumpstate != null) {
                Proxy.sDefaultImpl = iDumpstate;
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
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.cancelBugreport();
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.startBugreport(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readRawFileDescriptor(), ((Parcel)object).readRawFileDescriptor(), ((Parcel)object).readInt(), IDumpstateListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            String string2 = ((Parcel)object).readString();
            IDumpstateListener iDumpstateListener = IDumpstateListener.Stub.asInterface(((Parcel)object).readStrongBinder());
            boolean bl = ((Parcel)object).readInt() != 0;
            object = this.setListener(string2, iDumpstateListener, bl);
            parcel.writeNoException();
            object = object != null ? object.asBinder() : null;
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class Proxy
        implements IDumpstate {
            public static IDumpstate sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelBugreport() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelBugreport();
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public IDumpstateToken setListener(String object, IDumpstateListener iDumpstateListener, boolean bl) throws RemoteException {
                Parcel parcel;
                IBinder iBinder;
                Parcel parcel2;
                block8 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (iDumpstateListener == null) break block8;
                    iBinder = iDumpstateListener.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().setListener((String)object, iDumpstateListener, bl);
                        return object;
                    }
                    parcel2.readException();
                    object = IDumpstateToken.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
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
            public void startBugreport(int n, String string2, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, int n2, IDumpstateListener iDumpstateListener) throws RemoteException {
                void var2_10;
                Parcel parcel;
                Parcel parcel2;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeRawFileDescriptor(fileDescriptor);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeRawFileDescriptor(fileDescriptor2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                        IBinder iBinder = iDumpstateListener != null ? iDumpstateListener.asBinder() : null;
                        parcel2.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(2, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().startBugreport(n, string2, fileDescriptor, fileDescriptor2, n2, iDumpstateListener);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_10;
            }
        }

    }

}

