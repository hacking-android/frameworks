/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 */
package android.media;

import android.media.Controller2Link;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMediaSession2Service
extends IInterface {
    public void connect(Controller2Link var1, int var2, Bundle var3) throws RemoteException;

    public static class Default
    implements IMediaSession2Service {
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void connect(Controller2Link controller2Link, int n, Bundle bundle) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMediaSession2Service {
        private static final String DESCRIPTOR = "android.media.IMediaSession2Service";
        static final int TRANSACTION_connect = 1;

        public Stub() {
            this.attachInterface((IInterface)this, DESCRIPTOR);
        }

        public static IMediaSession2Service asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMediaSession2Service) {
                return (IMediaSession2Service)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMediaSession2Service getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static boolean setDefaultImpl(IMediaSession2Service iMediaSession2Service) {
            if (Proxy.sDefaultImpl == null && iMediaSession2Service != null) {
                Proxy.sDefaultImpl = iMediaSession2Service;
                return true;
            }
            return false;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, object, object2, n2);
                }
                object2.writeString(DESCRIPTOR);
                return true;
            }
            object.enforceInterface(DESCRIPTOR);
            object2 = object.readInt() != 0 ? (Controller2Link)Controller2Link.CREATOR.createFromParcel(object) : null;
            n = object.readInt();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(object) : null;
            this.connect((Controller2Link)object2, n, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IMediaSession2Service {
            public static IMediaSession2Service sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void connect(Controller2Link controller2Link, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (controller2Link != null) {
                        parcel.writeInt(1);
                        controller2Link.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connect(controller2Link, n, bundle);
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
        }

    }

}

