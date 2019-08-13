/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IRecentsAnimationController;
import android.view.RemoteAnimationTarget;

public interface IRecentsAnimationRunner
extends IInterface {
    @UnsupportedAppUsage
    public void onAnimationCanceled(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void onAnimationStart(IRecentsAnimationController var1, RemoteAnimationTarget[] var2, Rect var3, Rect var4) throws RemoteException;

    public static class Default
    implements IRecentsAnimationRunner {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAnimationCanceled(boolean bl) throws RemoteException {
        }

        @Override
        public void onAnimationStart(IRecentsAnimationController iRecentsAnimationController, RemoteAnimationTarget[] arrremoteAnimationTarget, Rect rect, Rect rect2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecentsAnimationRunner {
        private static final String DESCRIPTOR = "android.view.IRecentsAnimationRunner";
        static final int TRANSACTION_onAnimationCanceled = 2;
        static final int TRANSACTION_onAnimationStart = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecentsAnimationRunner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecentsAnimationRunner) {
                return (IRecentsAnimationRunner)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecentsAnimationRunner getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 2) {
                if (n != 3) {
                    return null;
                }
                return "onAnimationStart";
            }
            return "onAnimationCanceled";
        }

        public static boolean setDefaultImpl(IRecentsAnimationRunner iRecentsAnimationRunner) {
            if (Proxy.sDefaultImpl == null && iRecentsAnimationRunner != null) {
                Proxy.sDefaultImpl = iRecentsAnimationRunner;
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
            if (n != 2) {
                if (n != 3) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    ((Parcel)object2).writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IRecentsAnimationController iRecentsAnimationController = IRecentsAnimationController.Stub.asInterface(((Parcel)object).readStrongBinder());
                RemoteAnimationTarget[] arrremoteAnimationTarget = ((Parcel)object).createTypedArray(RemoteAnimationTarget.CREATOR);
                object2 = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                this.onAnimationStart(iRecentsAnimationController, arrremoteAnimationTarget, (Rect)object2, (Rect)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            boolean bl = ((Parcel)object).readInt() != 0;
            this.onAnimationCanceled(bl);
            return true;
        }

        private static class Proxy
        implements IRecentsAnimationRunner {
            public static IRecentsAnimationRunner sDefaultImpl;
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
            public void onAnimationCanceled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onAnimationCanceled(bl);
                        return;
                    }
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
            public void onAnimationStart(IRecentsAnimationController iRecentsAnimationController, RemoteAnimationTarget[] arrremoteAnimationTarget, Rect rect, Rect rect2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRecentsAnimationController != null ? iRecentsAnimationController.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeTypedArray((Parcelable[])arrremoteAnimationTarget, 0);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect2 != null) {
                        parcel.writeInt(1);
                        rect2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onAnimationStart(iRecentsAnimationController, arrremoteAnimationTarget, rect, rect2);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

