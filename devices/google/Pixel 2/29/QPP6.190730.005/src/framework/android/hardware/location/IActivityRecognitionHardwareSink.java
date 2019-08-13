/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.location;

import android.hardware.location.ActivityChangedEvent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IActivityRecognitionHardwareSink
extends IInterface {
    public void onActivityChanged(ActivityChangedEvent var1) throws RemoteException;

    public static class Default
    implements IActivityRecognitionHardwareSink {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onActivityChanged(ActivityChangedEvent activityChangedEvent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IActivityRecognitionHardwareSink {
        private static final String DESCRIPTOR = "android.hardware.location.IActivityRecognitionHardwareSink";
        static final int TRANSACTION_onActivityChanged = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IActivityRecognitionHardwareSink asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IActivityRecognitionHardwareSink) {
                return (IActivityRecognitionHardwareSink)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IActivityRecognitionHardwareSink getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onActivityChanged";
        }

        public static boolean setDefaultImpl(IActivityRecognitionHardwareSink iActivityRecognitionHardwareSink) {
            if (Proxy.sDefaultImpl == null && iActivityRecognitionHardwareSink != null) {
                Proxy.sDefaultImpl = iActivityRecognitionHardwareSink;
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
            object = ((Parcel)object).readInt() != 0 ? ActivityChangedEvent.CREATOR.createFromParcel((Parcel)object) : null;
            this.onActivityChanged((ActivityChangedEvent)object);
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IActivityRecognitionHardwareSink {
            public static IActivityRecognitionHardwareSink sDefaultImpl;
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
            public void onActivityChanged(ActivityChangedEvent activityChangedEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (activityChangedEvent != null) {
                        parcel.writeInt(1);
                        activityChangedEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onActivityChanged(activityChangedEvent);
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

