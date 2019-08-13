/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.aidl.IImsConfig;
import android.telephony.ims.aidl.IImsMmTelFeature;
import android.telephony.ims.aidl.IImsRcsFeature;
import android.telephony.ims.aidl.IImsRegistration;
import android.telephony.ims.aidl.IImsServiceControllerListener;
import android.telephony.ims.stub.ImsFeatureConfiguration;
import com.android.ims.internal.IImsFeatureStatusCallback;

public interface IImsServiceController
extends IInterface {
    public IImsMmTelFeature createMmTelFeature(int var1, IImsFeatureStatusCallback var2) throws RemoteException;

    public IImsRcsFeature createRcsFeature(int var1, IImsFeatureStatusCallback var2) throws RemoteException;

    public void disableIms(int var1) throws RemoteException;

    public void enableIms(int var1) throws RemoteException;

    public IImsConfig getConfig(int var1) throws RemoteException;

    public IImsRegistration getRegistration(int var1) throws RemoteException;

    public void notifyImsServiceReadyForFeatureCreation() throws RemoteException;

    public ImsFeatureConfiguration querySupportedImsFeatures() throws RemoteException;

    public void removeImsFeature(int var1, int var2, IImsFeatureStatusCallback var3) throws RemoteException;

    public void setListener(IImsServiceControllerListener var1) throws RemoteException;

    public static class Default
    implements IImsServiceController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public IImsMmTelFeature createMmTelFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
            return null;
        }

        @Override
        public IImsRcsFeature createRcsFeature(int n, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
            return null;
        }

        @Override
        public void disableIms(int n) throws RemoteException {
        }

        @Override
        public void enableIms(int n) throws RemoteException {
        }

        @Override
        public IImsConfig getConfig(int n) throws RemoteException {
            return null;
        }

        @Override
        public IImsRegistration getRegistration(int n) throws RemoteException {
            return null;
        }

        @Override
        public void notifyImsServiceReadyForFeatureCreation() throws RemoteException {
        }

        @Override
        public ImsFeatureConfiguration querySupportedImsFeatures() throws RemoteException {
            return null;
        }

        @Override
        public void removeImsFeature(int n, int n2, IImsFeatureStatusCallback iImsFeatureStatusCallback) throws RemoteException {
        }

        @Override
        public void setListener(IImsServiceControllerListener iImsServiceControllerListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsServiceController {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsServiceController";
        static final int TRANSACTION_createMmTelFeature = 2;
        static final int TRANSACTION_createRcsFeature = 3;
        static final int TRANSACTION_disableIms = 10;
        static final int TRANSACTION_enableIms = 9;
        static final int TRANSACTION_getConfig = 7;
        static final int TRANSACTION_getRegistration = 8;
        static final int TRANSACTION_notifyImsServiceReadyForFeatureCreation = 5;
        static final int TRANSACTION_querySupportedImsFeatures = 4;
        static final int TRANSACTION_removeImsFeature = 6;
        static final int TRANSACTION_setListener = 1;

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
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "disableIms";
                }
                case 9: {
                    return "enableIms";
                }
                case 8: {
                    return "getRegistration";
                }
                case 7: {
                    return "getConfig";
                }
                case 6: {
                    return "removeImsFeature";
                }
                case 5: {
                    return "notifyImsServiceReadyForFeatureCreation";
                }
                case 4: {
                    return "querySupportedImsFeatures";
                }
                case 3: {
                    return "createRcsFeature";
                }
                case 2: {
                    return "createMmTelFeature";
                }
                case 1: 
            }
            return "setListener";
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
            if (n != 1598968902) {
                IImsRegistration iImsRegistration = null;
                Object var6_6 = null;
                Object var7_7 = null;
                IInterface iInterface = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disableIms(((Parcel)object).readInt());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.enableIms(((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iImsRegistration = this.getRegistration(((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = iInterface;
                        if (iImsRegistration != null) {
                            object = iImsRegistration.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iInterface = this.getConfig(((Parcel)object).readInt());
                        parcel.writeNoException();
                        object = iImsRegistration;
                        if (iInterface != null) {
                            object = iInterface.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeImsFeature(((Parcel)object).readInt(), ((Parcel)object).readInt(), IImsFeatureStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyImsServiceReadyForFeatureCreation();
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.querySupportedImsFeatures();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ImsFeatureConfiguration)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iInterface = this.createRcsFeature(((Parcel)object).readInt(), IImsFeatureStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        object = var6_6;
                        if (iInterface != null) {
                            object = iInterface.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        iInterface = this.createMmTelFeature(((Parcel)object).readInt(), IImsFeatureStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        object = var7_7;
                        if (iInterface != null) {
                            object = iInterface.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setListener(IImsServiceControllerListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
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
            public IImsMmTelFeature createMmTelFeature(int n, IImsFeatureStatusCallback iInterface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsMmTelFeature iImsMmTelFeature = Stub.getDefaultImpl().createMmTelFeature(n, (IImsFeatureStatusCallback)iInterface);
                        return iImsMmTelFeature;
                    }
                    parcel2.readException();
                    IImsMmTelFeature iImsMmTelFeature = IImsMmTelFeature.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsMmTelFeature;
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

            @Override
            public void disableIms(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disableIms(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void enableIms(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableIms(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IImsConfig getConfig(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsConfig iImsConfig = Stub.getDefaultImpl().getConfig(n);
                        return iImsConfig;
                    }
                    parcel2.readException();
                    IImsConfig iImsConfig = IImsConfig.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsConfig;
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
            public IImsRegistration getRegistration(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IImsRegistration iImsRegistration = Stub.getDefaultImpl().getRegistration(n);
                        return iImsRegistration;
                    }
                    parcel2.readException();
                    IImsRegistration iImsRegistration = IImsRegistration.Stub.asInterface(parcel2.readStrongBinder());
                    return iImsRegistration;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void notifyImsServiceReadyForFeatureCreation() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyImsServiceReadyForFeatureCreation();
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
            public ImsFeatureConfiguration querySupportedImsFeatures() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        ImsFeatureConfiguration imsFeatureConfiguration = Stub.getDefaultImpl().querySupportedImsFeatures();
                        parcel.recycle();
                        parcel2.recycle();
                        return imsFeatureConfiguration;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                ImsFeatureConfiguration imsFeatureConfiguration = parcel.readInt() != 0 ? ImsFeatureConfiguration.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return imsFeatureConfiguration;
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
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setListener(IImsServiceControllerListener iImsServiceControllerListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsServiceControllerListener != null ? iImsServiceControllerListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setListener(iImsServiceControllerListener);
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

