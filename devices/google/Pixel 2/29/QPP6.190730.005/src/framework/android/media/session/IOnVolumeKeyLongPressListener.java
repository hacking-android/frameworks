/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.KeyEvent;

public interface IOnVolumeKeyLongPressListener
extends IInterface {
    public void onVolumeKeyLongPress(KeyEvent var1) throws RemoteException;

    public static class Default
    implements IOnVolumeKeyLongPressListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onVolumeKeyLongPress(KeyEvent keyEvent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOnVolumeKeyLongPressListener {
        private static final String DESCRIPTOR = "android.media.session.IOnVolumeKeyLongPressListener";
        static final int TRANSACTION_onVolumeKeyLongPress = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOnVolumeKeyLongPressListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOnVolumeKeyLongPressListener) {
                return (IOnVolumeKeyLongPressListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOnVolumeKeyLongPressListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onVolumeKeyLongPress";
        }

        public static boolean setDefaultImpl(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) {
            if (Proxy.sDefaultImpl == null && iOnVolumeKeyLongPressListener != null) {
                Proxy.sDefaultImpl = iOnVolumeKeyLongPressListener;
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
            object = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
            this.onVolumeKeyLongPress((KeyEvent)object);
            return true;
        }

        private static class Proxy
        implements IOnVolumeKeyLongPressListener {
            public static IOnVolumeKeyLongPressListener sDefaultImpl;
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
            public void onVolumeKeyLongPress(KeyEvent keyEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVolumeKeyLongPress(keyEvent);
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

