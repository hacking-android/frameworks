/*
 * Decompiled with CFR 0.145.
 */
package android.companion;

import android.companion.AssociationRequest;
import android.companion.ICompanionDeviceDiscoveryServiceCallback;
import android.companion.IFindDeviceCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ICompanionDeviceDiscoveryService
extends IInterface {
    public void startDiscovery(AssociationRequest var1, String var2, IFindDeviceCallback var3, ICompanionDeviceDiscoveryServiceCallback var4) throws RemoteException;

    public static class Default
    implements ICompanionDeviceDiscoveryService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void startDiscovery(AssociationRequest associationRequest, String string2, IFindDeviceCallback iFindDeviceCallback, ICompanionDeviceDiscoveryServiceCallback iCompanionDeviceDiscoveryServiceCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICompanionDeviceDiscoveryService {
        private static final String DESCRIPTOR = "android.companion.ICompanionDeviceDiscoveryService";
        static final int TRANSACTION_startDiscovery = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICompanionDeviceDiscoveryService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICompanionDeviceDiscoveryService) {
                return (ICompanionDeviceDiscoveryService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICompanionDeviceDiscoveryService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "startDiscovery";
        }

        public static boolean setDefaultImpl(ICompanionDeviceDiscoveryService iCompanionDeviceDiscoveryService) {
            if (Proxy.sDefaultImpl == null && iCompanionDeviceDiscoveryService != null) {
                Proxy.sDefaultImpl = iCompanionDeviceDiscoveryService;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            AssociationRequest associationRequest = parcel.readInt() != 0 ? AssociationRequest.CREATOR.createFromParcel(parcel) : null;
            this.startDiscovery(associationRequest, parcel.readString(), IFindDeviceCallback.Stub.asInterface(parcel.readStrongBinder()), ICompanionDeviceDiscoveryServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements ICompanionDeviceDiscoveryService {
            public static ICompanionDeviceDiscoveryService sDefaultImpl;
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void startDiscovery(AssociationRequest associationRequest, String string2, IFindDeviceCallback iFindDeviceCallback, ICompanionDeviceDiscoveryServiceCallback iCompanionDeviceDiscoveryServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (associationRequest != null) {
                        parcel.writeInt(1);
                        associationRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    Object var7_8 = null;
                    IBinder iBinder = iFindDeviceCallback != null ? iFindDeviceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = var7_8;
                    if (iCompanionDeviceDiscoveryServiceCallback != null) {
                        iBinder = iCompanionDeviceDiscoveryServiceCallback.asBinder();
                    }
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startDiscovery(associationRequest, string2, iFindDeviceCallback, iCompanionDeviceDiscoveryServiceCallback);
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

