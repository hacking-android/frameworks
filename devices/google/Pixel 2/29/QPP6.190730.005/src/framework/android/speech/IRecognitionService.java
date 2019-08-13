/*
 * Decompiled with CFR 0.145.
 */
package android.speech;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.speech.IRecognitionListener;

public interface IRecognitionService
extends IInterface {
    public void cancel(IRecognitionListener var1) throws RemoteException;

    public void startListening(Intent var1, IRecognitionListener var2) throws RemoteException;

    public void stopListening(IRecognitionListener var1) throws RemoteException;

    public static class Default
    implements IRecognitionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancel(IRecognitionListener iRecognitionListener) throws RemoteException {
        }

        @Override
        public void startListening(Intent intent, IRecognitionListener iRecognitionListener) throws RemoteException {
        }

        @Override
        public void stopListening(IRecognitionListener iRecognitionListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecognitionService {
        private static final String DESCRIPTOR = "android.speech.IRecognitionService";
        static final int TRANSACTION_cancel = 3;
        static final int TRANSACTION_startListening = 1;
        static final int TRANSACTION_stopListening = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecognitionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecognitionService) {
                return (IRecognitionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecognitionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "cancel";
                }
                return "stopListening";
            }
            return "startListening";
        }

        public static boolean setDefaultImpl(IRecognitionService iRecognitionService) {
            if (Proxy.sDefaultImpl == null && iRecognitionService != null) {
                Proxy.sDefaultImpl = iRecognitionService;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, (Parcel)object, n2);
                        }
                        ((Parcel)object).writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    this.cancel(IRecognitionListener.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.stopListening(IRecognitionListener.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
            this.startListening((Intent)object, IRecognitionListener.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IRecognitionService {
            public static IRecognitionService sDefaultImpl;
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
            public void cancel(IRecognitionListener iRecognitionListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecognitionListener != null ? iRecognitionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().cancel(iRecognitionListener);
                    return;
                }
                finally {
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
            public void startListening(Intent intent, IRecognitionListener iRecognitionListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iRecognitionListener != null ? iRecognitionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().startListening(intent, iRecognitionListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void stopListening(IRecognitionListener iRecognitionListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecognitionListener != null ? iRecognitionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().stopListening(iRecognitionListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

