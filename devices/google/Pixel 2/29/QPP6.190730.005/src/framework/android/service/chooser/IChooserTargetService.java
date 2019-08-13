/*
 * Decompiled with CFR 0.145.
 */
package android.service.chooser;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.chooser.IChooserTargetResult;

public interface IChooserTargetService
extends IInterface {
    public void getChooserTargets(ComponentName var1, IntentFilter var2, IChooserTargetResult var3) throws RemoteException;

    public static class Default
    implements IChooserTargetService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getChooserTargets(ComponentName componentName, IntentFilter intentFilter, IChooserTargetResult iChooserTargetResult) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IChooserTargetService {
        private static final String DESCRIPTOR = "android.service.chooser.IChooserTargetService";
        static final int TRANSACTION_getChooserTargets = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IChooserTargetService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IChooserTargetService) {
                return (IChooserTargetService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IChooserTargetService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getChooserTargets";
        }

        public static boolean setDefaultImpl(IChooserTargetService iChooserTargetService) {
            if (Proxy.sDefaultImpl == null && iChooserTargetService != null) {
                Proxy.sDefaultImpl = iChooserTargetService;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? ComponentName.CREATOR.createFromParcel(parcel) : null;
            IntentFilter intentFilter = parcel.readInt() != 0 ? IntentFilter.CREATOR.createFromParcel(parcel) : null;
            this.getChooserTargets((ComponentName)object, intentFilter, IChooserTargetResult.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IChooserTargetService {
            public static IChooserTargetService sDefaultImpl;
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
            public void getChooserTargets(ComponentName componentName, IntentFilter intentFilter, IChooserTargetResult iChooserTargetResult) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (intentFilter != null) {
                        parcel.writeInt(1);
                        intentFilter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iChooserTargetResult != null ? iChooserTargetResult.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getChooserTargets(componentName, intentFilter, iChooserTargetResult);
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

