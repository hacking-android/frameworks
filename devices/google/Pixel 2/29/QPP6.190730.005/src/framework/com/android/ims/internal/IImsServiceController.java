/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.ims.internal.IImsFeatureStatusCallback;
import com.android.ims.internal.IImsMMTelFeature;
import com.android.ims.internal.IImsRcsFeature;

public interface IImsServiceController
extends IInterface {
    public IImsMMTelFeature createEmergencyMMTelFeature(int var1, IImsFeatureStatusCallback var2) throws RemoteException;

    public IImsMMTelFeature createMMTelFeature(int var1, IImsFeatureStatusCallback var2) throws RemoteException;

    public IImsRcsFeature createRcsFeature(int var1, IImsFeatureStatusCallback var2) throws RemoteException;

    public void removeImsFeature(int var1, int var2, IImsFeatureStatusCallback var3) throws RemoteException;

    public static class Default
    implements IImsServiceController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public IImsMMTelFeature createEmergencyMMTelFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
            return null;
        }

        @Override
        public IImsMMTelFeature createMMTelFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
            return null;
        }

        @Override
        public IImsRcsFeature createRcsFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
            return null;
        }

        @Override
        public void removeImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsServiceController {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsServiceController";
        static final int TRANSACTION_createEmergencyMMTelFeature = 1;
        static final int TRANSACTION_createMMTelFeature = 2;
        static final int TRANSACTION_createRcsFeature = 3;
        static final int TRANSACTION_removeImsFeature = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsServiceController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsServiceController) {
                return (IImsServiceController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsServiceController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "removeImsFeature";
                    }
                    return "createRcsFeature";
                }
                return "createMMTelFeature";
            }
            return "createEmergencyMMTelFeature";
        }

        public static boolean setDefaultImpl(IImsServiceController iImsServiceController) {
            if (Proxy.sDefaultImpl == null && iImsServiceController != null) {
                Proxy.sDefaultImpl = iImsServiceController;
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
            IImsRcsFeature iImsRcsFeature = null;
            Object var6_6 = null;
            IImsMMTelFeature iImsMMTelFeature = null;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, (Parcel)object, parcel, n2);
                            }
                            parcel.writeString(DESCRIPTOR);
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeImsFeature(((Parcel)object).readInt(), ((Parcel)object).readInt(), IImsFeatureStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    iImsRcsFeature = this.createRcsFeature(((Parcel)object).readInt(), IImsFeatureStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    parcel.writeNoException();
                    object = iImsMMTelFeature;
                    if (iImsRcsFeature != null) {
                        object = iImsRcsFeature.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                iImsMMTelFeature = this.createMMTelFeature(((Parcel)object).readInt(), IImsFeatureStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                object = iImsRcsFeature;
                if (iImsMMTelFeature != null) {
                    object = iImsMMTelFeature.asBinder();
                }
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            iImsMMTelFeature = this.createEmergencyMMTelFeature(((Parcel)object).readInt(), IImsFeatureStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
            parcel.writeNoException();
            object = var6_6;
            if (iImsMMTelFeature != null) {
                object = iImsMMTelFeature.asBinder();
            }
            parcel.writeStrongBinder((IBinder)object);
            return true;
        }

        private static class Proxy
        implements IImsServiceController {
            public static IImsServiceController sDefaultImpl;
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
            public IImsMMTelFeature createEmergencyMMTelFeature(int n, IImsFeatureStatusCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsMMTelFeature iImsMMTelFeature = Stub.getDefaultImpl().createEmergencyMMTelFeature(n, (IImsFeatureStatusCallback)iInterface);
                        return iImsMMTelFeature;
                    }
                    parcel2.readException();
                    IImsMMTelFeature iImsMMTelFeature = IImsMMTelFeature.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsMMTelFeature;
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
            public IImsMMTelFeature createMMTelFeature(int n, IImsFeatureStatusCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsMMTelFeature iImsMMTelFeature = Stub.getDefaultImpl().createMMTelFeature(n, (IImsFeatureStatusCallback)iInterface);
                        return iImsMMTelFeature;
                    }
                    parcel2.readException();
                    IImsMMTelFeature iImsMMTelFeature = IImsMMTelFeature.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsMMTelFeature;
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
            public IImsRcsFeature createRcsFeature(int n, IImsFeatureStatusCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsRcsFeature iImsRcsFeature = Stub.getDefaultImpl().createRcsFeature(n, (IImsFeatureStatusCallback)iInterface);
                        return iImsRcsFeature;
                    }
                    parcel2.readException();
                    IImsRcsFeature iImsRcsFeature = IImsRcsFeature.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsRcsFeature;
                }
                finally {
                    parcel2.recycle();
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
            public void removeImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iImsFeatureStatusCallback != null ? iImsFeatureStatusCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeImsFeature(n, n2, iImsFeatureStatusCallback);
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

