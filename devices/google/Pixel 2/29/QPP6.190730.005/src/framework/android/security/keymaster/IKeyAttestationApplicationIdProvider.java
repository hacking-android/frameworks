/*
 * Decompiled with CFR 0.145.
 */
package android.security.keymaster;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.security.keymaster.KeyAttestationApplicationId;

public interface IKeyAttestationApplicationIdProvider
extends IInterface {
    public KeyAttestationApplicationId getKeyAttestationApplicationId(int var1) throws RemoteException;

    public static class Default
    implements IKeyAttestationApplicationIdProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public KeyAttestationApplicationId getKeyAttestationApplicationId(int n) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IKeyAttestationApplicationIdProvider {
        private static final String DESCRIPTOR = "android.security.keymaster.IKeyAttestationApplicationIdProvider";
        static final int TRANSACTION_getKeyAttestationApplicationId = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IKeyAttestationApplicationIdProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IKeyAttestationApplicationIdProvider) {
                return (IKeyAttestationApplicationIdProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IKeyAttestationApplicationIdProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getKeyAttestationApplicationId";
        }

        public static boolean setDefaultImpl(IKeyAttestationApplicationIdProvider iKeyAttestationApplicationIdProvider) {
            if (Proxy.sDefaultImpl == null && iKeyAttestationApplicationIdProvider != null) {
                Proxy.sDefaultImpl = iKeyAttestationApplicationIdProvider;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getKeyAttestationApplicationId(((Parcel)object).readInt());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((KeyAttestationApplicationId)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IKeyAttestationApplicationIdProvider {
            public static IKeyAttestationApplicationIdProvider sDefaultImpl;
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

            @Override
            public KeyAttestationApplicationId getKeyAttestationApplicationId(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        KeyAttestationApplicationId keyAttestationApplicationId = Stub.getDefaultImpl().getKeyAttestationApplicationId(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return keyAttestationApplicationId;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                KeyAttestationApplicationId keyAttestationApplicationId = parcel2.readInt() != 0 ? KeyAttestationApplicationId.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return keyAttestationApplicationId;
            }
        }

    }

}

