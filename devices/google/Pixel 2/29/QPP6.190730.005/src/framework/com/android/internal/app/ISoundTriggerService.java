/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.content.ComponentName;
import android.hardware.soundtrigger.IRecognitionStatusCallback;
import android.hardware.soundtrigger.SoundTrigger;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ISoundTriggerService
extends IInterface {
    public void deleteSoundModel(ParcelUuid var1) throws RemoteException;

    public int getModelState(ParcelUuid var1) throws RemoteException;

    public SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid var1) throws RemoteException;

    public boolean isRecognitionActive(ParcelUuid var1) throws RemoteException;

    public int loadGenericSoundModel(SoundTrigger.GenericSoundModel var1) throws RemoteException;

    public int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel var1) throws RemoteException;

    public int startRecognition(ParcelUuid var1, IRecognitionStatusCallback var2, SoundTrigger.RecognitionConfig var3) throws RemoteException;

    public int startRecognitionForService(ParcelUuid var1, Bundle var2, ComponentName var3, SoundTrigger.RecognitionConfig var4) throws RemoteException;

    public int stopRecognition(ParcelUuid var1, IRecognitionStatusCallback var2) throws RemoteException;

    public int stopRecognitionForService(ParcelUuid var1) throws RemoteException;

    public int unloadSoundModel(ParcelUuid var1) throws RemoteException;

    public void updateSoundModel(SoundTrigger.GenericSoundModel var1) throws RemoteException;

    public static class Default
    implements ISoundTriggerService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void deleteSoundModel(ParcelUuid parcelUuid) throws RemoteException {
        }

        @Override
        public int getModelState(ParcelUuid parcelUuid) throws RemoteException {
            return 0;
        }

        @Override
        public SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid parcelUuid) throws RemoteException {
            return null;
        }

        @Override
        public boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException {
            return false;
        }

        @Override
        public int loadGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
            return 0;
        }

        @Override
        public int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException {
            return 0;
        }

        @Override
        public int startRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
            return 0;
        }

        @Override
        public int startRecognitionForService(ParcelUuid parcelUuid, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
            return 0;
        }

        @Override
        public int stopRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback) throws RemoteException {
            return 0;
        }

        @Override
        public int stopRecognitionForService(ParcelUuid parcelUuid) throws RemoteException {
            return 0;
        }

        @Override
        public int unloadSoundModel(ParcelUuid parcelUuid) throws RemoteException {
            return 0;
        }

        @Override
        public void updateSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISoundTriggerService {
        private static final String DESCRIPTOR = "com.android.internal.app.ISoundTriggerService";
        static final int TRANSACTION_deleteSoundModel = 3;
        static final int TRANSACTION_getModelState = 12;
        static final int TRANSACTION_getSoundModel = 1;
        static final int TRANSACTION_isRecognitionActive = 11;
        static final int TRANSACTION_loadGenericSoundModel = 6;
        static final int TRANSACTION_loadKeyphraseSoundModel = 7;
        static final int TRANSACTION_startRecognition = 4;
        static final int TRANSACTION_startRecognitionForService = 8;
        static final int TRANSACTION_stopRecognition = 5;
        static final int TRANSACTION_stopRecognitionForService = 9;
        static final int TRANSACTION_unloadSoundModel = 10;
        static final int TRANSACTION_updateSoundModel = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISoundTriggerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISoundTriggerService) {
                return (ISoundTriggerService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISoundTriggerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 12: {
                    return "getModelState";
                }
                case 11: {
                    return "isRecognitionActive";
                }
                case 10: {
                    return "unloadSoundModel";
                }
                case 9: {
                    return "stopRecognitionForService";
                }
                case 8: {
                    return "startRecognitionForService";
                }
                case 7: {
                    return "loadKeyphraseSoundModel";
                }
                case 6: {
                    return "loadGenericSoundModel";
                }
                case 5: {
                    return "stopRecognition";
                }
                case 4: {
                    return "startRecognition";
                }
                case 3: {
                    return "deleteSoundModel";
                }
                case 2: {
                    return "updateSoundModel";
                }
                case 1: 
            }
            return "getSoundModel";
        }

        public static boolean setDefaultImpl(ISoundTriggerService iSoundTriggerService) {
            if (Proxy.sDefaultImpl == null && iSoundTriggerService != null) {
                Proxy.sDefaultImpl = iSoundTriggerService;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.getModelState((ParcelUuid)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isRecognitionActive((ParcelUuid)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.unloadSoundModel((ParcelUuid)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.stopRecognitionForService((ParcelUuid)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? SoundTrigger.RecognitionConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startRecognitionForService(parcelUuid, bundle, componentName, (SoundTrigger.RecognitionConfig)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SoundTrigger.KeyphraseSoundModel.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.loadKeyphraseSoundModel((SoundTrigger.KeyphraseSoundModel)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SoundTrigger.GenericSoundModel.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.loadGenericSoundModel((SoundTrigger.GenericSoundModel)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.stopRecognition(parcelUuid, IRecognitionStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        IRecognitionStatusCallback iRecognitionStatusCallback = IRecognitionStatusCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? SoundTrigger.RecognitionConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.startRecognition(parcelUuid, iRecognitionStatusCallback, (SoundTrigger.RecognitionConfig)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.deleteSoundModel((ParcelUuid)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SoundTrigger.GenericSoundModel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateSoundModel((SoundTrigger.GenericSoundModel)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.getSoundModel((ParcelUuid)object);
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((SoundTrigger.GenericSoundModel)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISoundTriggerService {
            public static ISoundTriggerService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void deleteSoundModel(ParcelUuid parcelUuid) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteSoundModel(parcelUuid);
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
            public int getModelState(ParcelUuid parcelUuid) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getModelState(parcelUuid);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public SoundTrigger.GenericSoundModel getSoundModel(ParcelUuid parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((ParcelUuid)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SoundTrigger.GenericSoundModel genericSoundModel = Stub.getDefaultImpl().getSoundModel((ParcelUuid)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return genericSoundModel;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        SoundTrigger.GenericSoundModel genericSoundModel = SoundTrigger.GenericSoundModel.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isRecognitionActive(ParcelUuid parcelUuid) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isRecognitionActive(parcelUuid);
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

            @Override
            public int loadGenericSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (genericSoundModel != null) {
                        parcel.writeInt(1);
                        genericSoundModel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().loadGenericSoundModel(genericSoundModel);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int loadKeyphraseSoundModel(SoundTrigger.KeyphraseSoundModel keyphraseSoundModel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keyphraseSoundModel != null) {
                        parcel.writeInt(1);
                        keyphraseSoundModel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().loadKeyphraseSoundModel(keyphraseSoundModel);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int startRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iRecognitionStatusCallback != null ? iRecognitionStatusCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (recognitionConfig != null) {
                        parcel.writeInt(1);
                        recognitionConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().startRecognition(parcelUuid, iRecognitionStatusCallback, recognitionConfig);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int startRecognitionForService(ParcelUuid parcelUuid, Bundle bundle, ComponentName componentName, SoundTrigger.RecognitionConfig recognitionConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
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
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (recognitionConfig != null) {
                        parcel.writeInt(1);
                        recognitionConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().startRecognitionForService(parcelUuid, bundle, componentName, recognitionConfig);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public int stopRecognition(ParcelUuid parcelUuid, IRecognitionStatusCallback iRecognitionStatusCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iRecognitionStatusCallback != null ? iRecognitionStatusCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().stopRecognition(parcelUuid, iRecognitionStatusCallback);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int stopRecognitionForService(ParcelUuid parcelUuid) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().stopRecognitionForService(parcelUuid);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int unloadSoundModel(ParcelUuid parcelUuid) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().unloadSoundModel(parcelUuid);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void updateSoundModel(SoundTrigger.GenericSoundModel genericSoundModel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (genericSoundModel != null) {
                        parcel.writeInt(1);
                        genericSoundModel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateSoundModel(genericSoundModel);
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

