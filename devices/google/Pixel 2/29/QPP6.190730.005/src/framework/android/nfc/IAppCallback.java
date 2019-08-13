/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.nfc.BeamShareData;
import android.nfc.Tag;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IAppCallback
extends IInterface {
    public BeamShareData createBeamShareData(byte var1) throws RemoteException;

    public void onNdefPushComplete(byte var1) throws RemoteException;

    public void onTagDiscovered(Tag var1) throws RemoteException;

    public static class Default
    implements IAppCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public BeamShareData createBeamShareData(byte by) throws RemoteException {
            return null;
        }

        @Override
        public void onNdefPushComplete(byte by) throws RemoteException {
        }

        @Override
        public void onTagDiscovered(Tag tag) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAppCallback {
        private static final String DESCRIPTOR = "android.nfc.IAppCallback";
        static final int TRANSACTION_createBeamShareData = 1;
        static final int TRANSACTION_onNdefPushComplete = 2;
        static final int TRANSACTION_onTagDiscovered = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAppCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAppCallback) {
                return (IAppCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAppCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onTagDiscovered";
                }
                return "onNdefPushComplete";
            }
            return "createBeamShareData";
        }

        public static boolean setDefaultImpl(IAppCallback iAppCallback) {
            if (Proxy.sDefaultImpl == null && iAppCallback != null) {
                Proxy.sDefaultImpl = iAppCallback;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? Tag.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onTagDiscovered((Tag)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onNdefPushComplete(((Parcel)object).readByte());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.createBeamShareData(((Parcel)object).readByte());
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((BeamShareData)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IAppCallback {
            public static IAppCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public BeamShareData createBeamShareData(byte by) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeByte(by);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        BeamShareData beamShareData = Stub.getDefaultImpl().createBeamShareData(by);
                        parcel2.recycle();
                        parcel.recycle();
                        return beamShareData;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                BeamShareData beamShareData = parcel2.readInt() != 0 ? BeamShareData.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return beamShareData;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void onNdefPushComplete(byte by) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByte(by);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNdefPushComplete(by);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTagDiscovered(Tag tag) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tag != null) {
                        parcel.writeInt(1);
                        tag.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTagDiscovered(tag);
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

