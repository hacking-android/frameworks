/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IImsRcsFeature
extends IInterface {

    public static class Default
    implements IImsRcsFeature {
        @Override
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsRcsFeature {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRcsFeature";

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsRcsFeature asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsRcsFeature) {
                return (IImsRcsFeature)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsRcsFeature getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            return null;
        }

        public static boolean setDefaultImpl(IImsRcsFeature iImsRcsFeature) {
            if (Proxy.sDefaultImpl == null && iImsRcsFeature != null) {
                Proxy.sDefaultImpl = iImsRcsFeature;
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
            if (n != 1598968902) {
                return super.onTransact(n, parcel, parcel2, n2);
            }
            parcel2.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsRcsFeature {
            public static IImsRcsFeature sDefaultImpl;
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
        }

    }

}

