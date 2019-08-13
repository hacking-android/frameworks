/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.nfc.Tag;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INfcUnlockHandler
extends IInterface {
    public boolean onUnlockAttempted(Tag var1) throws RemoteException;

    public static class Default
    implements INfcUnlockHandler {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean onUnlockAttempted(Tag tag) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements INfcUnlockHandler {
        private static final String DESCRIPTOR = "android.nfc.INfcUnlockHandler";
        static final int TRANSACTION_onUnlockAttempted = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INfcUnlockHandler asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INfcUnlockHandler) {
                return (INfcUnlockHandler)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INfcUnlockHandler getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onUnlockAttempted";
        }

        public static boolean setDefaultImpl(INfcUnlockHandler iNfcUnlockHandler) {
            if (Proxy.sDefaultImpl == null && iNfcUnlockHandler != null) {
                Proxy.sDefaultImpl = iNfcUnlockHandler;
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
            object = ((Parcel)object).readInt() != 0 ? Tag.CREATOR.createFromParcel((Parcel)object) : null;
            n = this.onUnlockAttempted((Tag)object) ? 1 : 0;
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements INfcUnlockHandler {
            public static INfcUnlockHandler sDefaultImpl;
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
            public boolean onUnlockAttempted(Tag tag) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (tag != null) {
                        parcel.writeInt(1);
                        tag.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().onUnlockAttempted(tag);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }
        }

    }

}

