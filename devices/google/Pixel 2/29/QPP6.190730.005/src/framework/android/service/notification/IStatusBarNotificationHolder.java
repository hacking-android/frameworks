/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;

public interface IStatusBarNotificationHolder
extends IInterface {
    public StatusBarNotification get() throws RemoteException;

    public static class Default
    implements IStatusBarNotificationHolder {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public StatusBarNotification get() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IStatusBarNotificationHolder {
        private static final String DESCRIPTOR = "android.service.notification.IStatusBarNotificationHolder";
        static final int TRANSACTION_get = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IStatusBarNotificationHolder asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IStatusBarNotificationHolder) {
                return (IStatusBarNotificationHolder)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IStatusBarNotificationHolder getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "get";
        }

        public static boolean setDefaultImpl(IStatusBarNotificationHolder iStatusBarNotificationHolder) {
            if (Proxy.sDefaultImpl == null && iStatusBarNotificationHolder != null) {
                Proxy.sDefaultImpl = iStatusBarNotificationHolder;
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
            object = this.get();
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((StatusBarNotification)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IStatusBarNotificationHolder {
            public static IStatusBarNotificationHolder sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public StatusBarNotification get() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        StatusBarNotification statusBarNotification = Stub.getDefaultImpl().get();
                        parcel.recycle();
                        parcel2.recycle();
                        return statusBarNotification;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                StatusBarNotification statusBarNotification = parcel.readInt() != 0 ? StatusBarNotification.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return statusBarNotification;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

