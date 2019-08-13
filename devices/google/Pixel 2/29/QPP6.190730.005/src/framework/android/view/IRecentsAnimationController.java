/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.app.ActivityManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IRecentsAnimationController
extends IInterface {
    public void cleanupScreenshot() throws RemoteException;

    @UnsupportedAppUsage
    public void finish(boolean var1, boolean var2) throws RemoteException;

    public void hideCurrentInputMethod() throws RemoteException;

    @UnsupportedAppUsage
    public ActivityManager.TaskSnapshot screenshotTask(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setAnimationTargetsBehindSystemBars(boolean var1) throws RemoteException;

    public void setCancelWithDeferredScreenshot(boolean var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setInputConsumerEnabled(boolean var1) throws RemoteException;

    public void setSplitScreenMinimized(boolean var1) throws RemoteException;

    public static class Default
    implements IRecentsAnimationController {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cleanupScreenshot() throws RemoteException {
        }

        @Override
        public void finish(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void hideCurrentInputMethod() throws RemoteException {
        }

        @Override
        public ActivityManager.TaskSnapshot screenshotTask(int n) throws RemoteException {
            return null;
        }

        @Override
        public void setAnimationTargetsBehindSystemBars(boolean bl) throws RemoteException {
        }

        @Override
        public void setCancelWithDeferredScreenshot(boolean bl) throws RemoteException {
        }

        @Override
        public void setInputConsumerEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void setSplitScreenMinimized(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRecentsAnimationController {
        private static final String DESCRIPTOR = "android.view.IRecentsAnimationController";
        static final int TRANSACTION_cleanupScreenshot = 8;
        static final int TRANSACTION_finish = 2;
        static final int TRANSACTION_hideCurrentInputMethod = 6;
        static final int TRANSACTION_screenshotTask = 1;
        static final int TRANSACTION_setAnimationTargetsBehindSystemBars = 4;
        static final int TRANSACTION_setCancelWithDeferredScreenshot = 7;
        static final int TRANSACTION_setInputConsumerEnabled = 3;
        static final int TRANSACTION_setSplitScreenMinimized = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRecentsAnimationController asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRecentsAnimationController) {
                return (IRecentsAnimationController)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRecentsAnimationController getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "cleanupScreenshot";
                }
                case 7: {
                    return "setCancelWithDeferredScreenshot";
                }
                case 6: {
                    return "hideCurrentInputMethod";
                }
                case 5: {
                    return "setSplitScreenMinimized";
                }
                case 4: {
                    return "setAnimationTargetsBehindSystemBars";
                }
                case 3: {
                    return "setInputConsumerEnabled";
                }
                case 2: {
                    return "finish";
                }
                case 1: 
            }
            return "screenshotTask";
        }

        public static boolean setDefaultImpl(IRecentsAnimationController iRecentsAnimationController) {
            if (Proxy.sDefaultImpl == null && iRecentsAnimationController != null) {
                Proxy.sDefaultImpl = iRecentsAnimationController;
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
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cleanupScreenshot();
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setCancelWithDeferredScreenshot(bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.hideCurrentInputMethod();
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setSplitScreenMinimized(bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setAnimationTargetsBehindSystemBars(bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setInputConsumerEnabled(bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl5 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.finish(bl5, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.screenshotTask(((Parcel)object).readInt());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((ActivityManager.TaskSnapshot)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IRecentsAnimationController {
            public static IRecentsAnimationController sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cleanupScreenshot() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cleanupScreenshot();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void finish(boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finish(bl, bl2);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void hideCurrentInputMethod() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hideCurrentInputMethod();
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public ActivityManager.TaskSnapshot screenshotTask(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ActivityManager.TaskSnapshot taskSnapshot = Stub.getDefaultImpl().screenshotTask(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return taskSnapshot;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ActivityManager.TaskSnapshot taskSnapshot = parcel2.readInt() != 0 ? ActivityManager.TaskSnapshot.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return taskSnapshot;
            }

            @Override
            public void setAnimationTargetsBehindSystemBars(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAnimationTargetsBehindSystemBars(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setCancelWithDeferredScreenshot(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCancelWithDeferredScreenshot(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setInputConsumerEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInputConsumerEnabled(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setSplitScreenMinimized(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSplitScreenMinimized(bl);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

