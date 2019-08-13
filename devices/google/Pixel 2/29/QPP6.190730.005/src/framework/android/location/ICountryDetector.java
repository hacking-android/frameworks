/*
 * Decompiled with CFR 0.145.
 */
package android.location;

import android.location.Country;
import android.location.ICountryListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ICountryDetector
extends IInterface {
    public void addCountryListener(ICountryListener var1) throws RemoteException;

    public Country detectCountry() throws RemoteException;

    public void removeCountryListener(ICountryListener var1) throws RemoteException;

    public static class Default
    implements ICountryDetector {
        @Override
        public void addCountryListener(ICountryListener iCountryListener) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public Country detectCountry() throws RemoteException {
            return null;
        }

        @Override
        public void removeCountryListener(ICountryListener iCountryListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICountryDetector {
        private static final String DESCRIPTOR = "android.location.ICountryDetector";
        static final int TRANSACTION_addCountryListener = 2;
        static final int TRANSACTION_detectCountry = 1;
        static final int TRANSACTION_removeCountryListener = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICountryDetector asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICountryDetector) {
                return (ICountryDetector)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICountryDetector getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "removeCountryListener";
                }
                return "addCountryListener";
            }
            return "detectCountry";
        }

        public static boolean setDefaultImpl(ICountryDetector iCountryDetector) {
            if (Proxy.sDefaultImpl == null && iCountryDetector != null) {
                Proxy.sDefaultImpl = iCountryDetector;
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
                    this.removeCountryListener(ICountryListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.addCountryListener(ICountryListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.detectCountry();
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((Country)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements ICountryDetector {
            public static ICountryDetector sDefaultImpl;
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
            public void addCountryListener(ICountryListener iCountryListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCountryListener != null ? iCountryListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addCountryListener(iCountryListener);
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
            public Country detectCountry() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Country country = Stub.getDefaultImpl().detectCountry();
                        parcel.recycle();
                        parcel2.recycle();
                        return country;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Country country = parcel.readInt() != 0 ? Country.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return country;
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
            public void removeCountryListener(ICountryListener iCountryListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCountryListener != null ? iCountryListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeCountryListener(iCountryListener);
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

