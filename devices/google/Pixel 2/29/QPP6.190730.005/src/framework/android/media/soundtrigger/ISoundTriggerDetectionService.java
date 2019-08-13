/*
 * Decompiled with CFR 0.145.
 */
package android.media.soundtrigger;

import android.hardware.soundtrigger.SoundTrigger;
import android.media.soundtrigger.ISoundTriggerDetectionServiceClient;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISoundTriggerDetectionService
extends IInterface {
    public void onError(ParcelUuid var1, int var2, int var3) throws RemoteException;

    public void onGenericRecognitionEvent(ParcelUuid var1, int var2, SoundTrigger.GenericRecognitionEvent var3) throws RemoteException;

    public void onStopOperation(ParcelUuid var1, int var2) throws RemoteException;

    public void removeClient(ParcelUuid var1) throws RemoteException;

    public void setClient(ParcelUuid var1, Bundle var2, ISoundTriggerDetectionServiceClient var3) throws RemoteException;

    public static class Default
    implements ISoundTriggerDetectionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onError(ParcelUuid parcelUuid, int n, int n2) throws RemoteException {
        }

        @Override
        public void onGenericRecognitionEvent(ParcelUuid parcelUuid, int n, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException {
        }

        @Override
        public void onStopOperation(ParcelUuid parcelUuid, int n) throws RemoteException {
        }

        @Override
        public void removeClient(ParcelUuid parcelUuid) throws RemoteException {
        }

        @Override
        public void setClient(ParcelUuid parcelUuid, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISoundTriggerDetectionService {
        private static final String DESCRIPTOR = "android.media.soundtrigger.ISoundTriggerDetectionService";
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onGenericRecognitionEvent = 3;
        static final int TRANSACTION_onStopOperation = 5;
        static final int TRANSACTION_removeClient = 2;
        static final int TRANSACTION_setClient = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerDetectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISoundTriggerDetectionService) {
                return (ISoundTriggerDetectionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISoundTriggerDetectionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "onStopOperation";
                        }
                        return "onError";
                    }
                    return "onGenericRecognitionEvent";
                }
                return "removeClient";
            }
            return "setClient";
        }

        public static boolean setDefaultImpl(ISoundTriggerDetectionService iSoundTriggerDetectionService) {
            if (Proxy.sDefaultImpl == null && iSoundTriggerDetectionService != null) {
                Proxy.sDefaultImpl = iSoundTriggerDetectionService;
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
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                                }
                                ((Parcel)object2).writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            object2 = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                            this.onStopOperation((ParcelUuid)object2, ((Parcel)object).readInt());
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onError((ParcelUuid)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object2 = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                    n = ((Parcel)object).readInt();
                    object = ((Parcel)object).readInt() != 0 ? SoundTrigger.GenericRecognitionEvent.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onGenericRecognitionEvent((ParcelUuid)object2, n, (SoundTrigger.GenericRecognitionEvent)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                this.removeClient((ParcelUuid)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
            Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.setClient((ParcelUuid)object2, bundle, ISoundTriggerDetectionServiceClient.Stub.asInterface(((Parcel)object).readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ISoundTriggerDetectionService {
            public static ISoundTriggerDetectionService sDefaultImpl;
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
            public void onError(ParcelUuid parcelUuid, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onError(parcelUuid, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onGenericRecognitionEvent(ParcelUuid parcelUuid, int n, SoundTrigger.GenericRecognitionEvent genericRecognitionEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (genericRecognitionEvent != null) {
                        parcel.writeInt(1);
                        genericRecognitionEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onGenericRecognitionEvent(parcelUuid, n, genericRecognitionEvent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStopOperation(ParcelUuid parcelUuid, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStopOperation(parcelUuid, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeClient(ParcelUuid parcelUuid) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeClient(parcelUuid);
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
            public void setClient(ParcelUuid parcelUuid, Bundle bundle, ISoundTriggerDetectionServiceClient iSoundTriggerDetectionServiceClient) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iSoundTriggerDetectionServiceClient != null ? iSoundTriggerDetectionServiceClient.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setClient(parcelUuid, bundle, iSoundTriggerDetectionServiceClient);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

