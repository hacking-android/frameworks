/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IInputFilterHost;
import android.view.InputEvent;

public interface IInputFilter
extends IInterface {
    public void filterInputEvent(InputEvent var1, int var2) throws RemoteException;

    public void install(IInputFilterHost var1) throws RemoteException;

    public void uninstall() throws RemoteException;

    public static class Default
    implements IInputFilter {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void filterInputEvent(InputEvent inputEvent, int n) throws RemoteException {
        }

        @Override
        public void install(IInputFilterHost iInputFilterHost) throws RemoteException {
        }

        @Override
        public void uninstall() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputFilter {
        private static final String DESCRIPTOR = "android.view.IInputFilter";
        static final int TRANSACTION_filterInputEvent = 3;
        static final int TRANSACTION_install = 1;
        static final int TRANSACTION_uninstall = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputFilter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputFilter) {
                return (IInputFilter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputFilter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "filterInputEvent";
                }
                return "uninstall";
            }
            return "install";
        }

        public static boolean setDefaultImpl(IInputFilter iInputFilter) {
            if (Proxy.sDefaultImpl == null && iInputFilter != null) {
                Proxy.sDefaultImpl = iInputFilter;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, (Parcel)object, n2);
                        }
                        ((Parcel)object).writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    object = parcel.readInt() != 0 ? InputEvent.CREATOR.createFromParcel(parcel) : null;
                    this.filterInputEvent((InputEvent)object, parcel.readInt());
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.uninstall();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.install(IInputFilterHost.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IInputFilter {
            public static IInputFilter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void filterInputEvent(InputEvent inputEvent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputEvent != null) {
                        parcel.writeInt(1);
                        inputEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().filterInputEvent(inputEvent, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void install(IInputFilterHost iInputFilterHost) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInputFilterHost != null ? iInputFilterHost.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().install(iInputFilterHost);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void uninstall() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().uninstall();
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

