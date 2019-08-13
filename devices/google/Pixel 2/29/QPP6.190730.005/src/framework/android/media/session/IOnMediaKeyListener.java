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
import android.os.ResultReceiver;
import android.view.KeyEvent;

public interface IOnMediaKeyListener
extends IInterface {
    public void onMediaKey(KeyEvent var1, ResultReceiver var2) throws RemoteException;

    public static class Default
    implements IOnMediaKeyListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onMediaKey(KeyEvent keyEvent, ResultReceiver resultReceiver) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOnMediaKeyListener {
        private static final String DESCRIPTOR = "android.media.session.IOnMediaKeyListener";
        static final int TRANSACTION_onMediaKey = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOnMediaKeyListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOnMediaKeyListener) {
                return (IOnMediaKeyListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOnMediaKeyListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onMediaKey";
        }

        public static boolean setDefaultImpl(IOnMediaKeyListener iOnMediaKeyListener) {
            if (Proxy.sDefaultImpl == null && iOnMediaKeyListener != null) {
                Proxy.sDefaultImpl = iOnMediaKeyListener;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                }
                ((Parcel)object2).writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
            object = ((Parcel)object).readInt() != 0 ? ResultReceiver.CREATOR.createFromParcel((Parcel)object) : null;
            this.onMediaKey((KeyEvent)object2, (ResultReceiver)object);
            return true;
        }

        private static class Proxy
        implements IOnMediaKeyListener {
            public static IOnMediaKeyListener sDefaultImpl;
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
            public void onMediaKey(KeyEvent keyEvent, ResultReceiver resultReceiver) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (resultReceiver != null) {
                        parcel.writeInt(1);
                        resultReceiver.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onMediaKey(keyEvent, resultReceiver);
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

