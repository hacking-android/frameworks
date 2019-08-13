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
import android.telephony.ims.stub.ImsFeatureConfiguration;

public interface IImsServiceControllerListener
extends IInterface {
    public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration var1) throws RemoteException;

    public static class Default
    implements IImsServiceControllerListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsServiceControllerListener {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsServiceControllerListener";
        static final int TRANSACTION_onUpdateSupportedImsFeatures = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsServiceControllerListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsServiceControllerListener) {
                return (IImsServiceControllerListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsServiceControllerListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onUpdateSupportedImsFeatures";
        }

        public static boolean setDefaultImpl(IImsServiceControllerListener iImsServiceControllerListener) {
            if (Proxy.sDefaultImpl == null && iImsServiceControllerListener != null) {
                Proxy.sDefaultImpl = iImsServiceControllerListener;
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
            object = ((Parcel)object).readInt() != 0 ? ImsFeatureConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
            this.onUpdateSupportedImsFeatures((ImsFeatureConfiguration)object);
            return true;
        }

        private static class Proxy
        implements IImsServiceControllerListener {
            public static IImsServiceControllerListener sDefaultImpl;
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
            public void onUpdateSupportedImsFeatures(ImsFeatureConfiguration imsFeatureConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsFeatureConfiguration != null) {
                        parcel.writeInt(1);
                        imsFeatureConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUpdateSupportedImsFeatures(imsFeatureConfiguration);
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

