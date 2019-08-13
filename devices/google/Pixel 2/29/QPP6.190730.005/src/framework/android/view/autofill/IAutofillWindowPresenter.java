/*
 * Decompiled with CFR 0.145.
 */
package android.view.autofill;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.WindowManager;

public interface IAutofillWindowPresenter
extends IInterface {
    public void hide(Rect var1) throws RemoteException;

    public void show(WindowManager.LayoutParams var1, Rect var2, boolean var3, int var4) throws RemoteException;

    public static class Default
    implements IAutofillWindowPresenter {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void hide(Rect rect) throws RemoteException {
        }

        @Override
        public void show(WindowManager.LayoutParams layoutParams, Rect rect, boolean bl, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IAutofillWindowPresenter {
        private static final String DESCRIPTOR = "android.view.autofill.IAutofillWindowPresenter";
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_show = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IAutofillWindowPresenter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IAutofillWindowPresenter) {
                return (IAutofillWindowPresenter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IAutofillWindowPresenter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "hide";
            }
            return "show";
        }

        public static boolean setDefaultImpl(IAutofillWindowPresenter iAutofillWindowPresenter) {
            if (Proxy.sDefaultImpl == null && iAutofillWindowPresenter != null) {
                Proxy.sDefaultImpl = iAutofillWindowPresenter;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    ((Parcel)object2).writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                this.hide((Rect)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? WindowManager.LayoutParams.CREATOR.createFromParcel((Parcel)object) : null;
            Rect rect = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
            boolean bl = ((Parcel)object).readInt() != 0;
            this.show((WindowManager.LayoutParams)object2, rect, bl, ((Parcel)object).readInt());
            return true;
        }

        private static class Proxy
        implements IAutofillWindowPresenter {
            public static IAutofillWindowPresenter sDefaultImpl;
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
            public void hide(Rect rect) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hide(rect);
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
            public void show(WindowManager.LayoutParams layoutParams, Rect rect, boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n2 = 0;
                    if (layoutParams != null) {
                        parcel.writeInt(1);
                        layoutParams.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n2 = 1;
                    }
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().show(layoutParams, rect, bl, n);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

