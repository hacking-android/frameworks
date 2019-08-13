/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.AudioAttributes;
import android.media.VolumeShaper;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.UserHandle;

public interface IRingtonePlayer
extends IInterface {
    public String getTitle(Uri var1) throws RemoteException;

    public boolean isPlaying(IBinder var1) throws RemoteException;

    public ParcelFileDescriptor openRingtone(Uri var1) throws RemoteException;

    public void play(IBinder var1, Uri var2, AudioAttributes var3, float var4, boolean var5) throws RemoteException;

    public void playAsync(Uri var1, UserHandle var2, boolean var3, AudioAttributes var4) throws RemoteException;

    public void playWithVolumeShaping(IBinder var1, Uri var2, AudioAttributes var3, float var4, boolean var5, VolumeShaper.Configuration var6) throws RemoteException;

    public void setPlaybackProperties(IBinder var1, float var2, boolean var3) throws RemoteException;

    public void stop(IBinder var1) throws RemoteException;

    public void stopAsync() throws RemoteException;

    public static class Default
    implements IRingtonePlayer {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public String getTitle(Uri uri) throws RemoteException {
            return null;
        }

        @Override
        public boolean isPlaying(IBinder iBinder) throws RemoteException {
            return false;
        }

        @Override
        public ParcelFileDescriptor openRingtone(Uri uri) throws RemoteException {
            return null;
        }

        @Override
        public void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean bl) throws RemoteException {
        }

        @Override
        public void playAsync(Uri uri, UserHandle userHandle, boolean bl, AudioAttributes audioAttributes) throws RemoteException {
        }

        @Override
        public void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean bl, VolumeShaper.Configuration configuration) throws RemoteException {
        }

        @Override
        public void setPlaybackProperties(IBinder iBinder, float f, boolean bl) throws RemoteException {
        }

        @Override
        public void stop(IBinder iBinder) throws RemoteException {
        }

        @Override
        public void stopAsync() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRingtonePlayer {
        private static final String DESCRIPTOR = "android.media.IRingtonePlayer";
        static final int TRANSACTION_getTitle = 8;
        static final int TRANSACTION_isPlaying = 4;
        static final int TRANSACTION_openRingtone = 9;
        static final int TRANSACTION_play = 1;
        static final int TRANSACTION_playAsync = 6;
        static final int TRANSACTION_playWithVolumeShaping = 2;
        static final int TRANSACTION_setPlaybackProperties = 5;
        static final int TRANSACTION_stop = 3;
        static final int TRANSACTION_stopAsync = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRingtonePlayer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRingtonePlayer) {
                return (IRingtonePlayer)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRingtonePlayer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "openRingtone";
                }
                case 8: {
                    return "getTitle";
                }
                case 7: {
                    return "stopAsync";
                }
                case 6: {
                    return "playAsync";
                }
                case 5: {
                    return "setPlaybackProperties";
                }
                case 4: {
                    return "isPlaying";
                }
                case 3: {
                    return "stop";
                }
                case 2: {
                    return "playWithVolumeShaping";
                }
                case 1: 
            }
            return "play";
        }

        public static boolean setDefaultImpl(IRingtonePlayer iRingtonePlayer) {
            if (Proxy.sDefaultImpl == null && iRingtonePlayer != null) {
                Proxy.sDefaultImpl = iRingtonePlayer;
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
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.openRingtone((Uri)object);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getTitle((Uri)object);
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeString((String)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopAsync();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        UserHandle userHandle = ((Parcel)object).readInt() != 0 ? UserHandle.CREATOR.createFromParcel((Parcel)object) : null;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        object = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        this.playAsync((Uri)object2, userHandle, bl2, (AudioAttributes)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readStrongBinder();
                        float f = ((Parcel)object).readFloat();
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setPlaybackProperties((IBinder)object2, f, bl2);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isPlaying(((Parcel)object).readStrongBinder()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stop(((Parcel)object).readStrongBinder());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        AudioAttributes audioAttributes = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                        float f = ((Parcel)object).readFloat();
                        bl2 = ((Parcel)object).readInt() != 0;
                        object = ((Parcel)object).readInt() != 0 ? VolumeShaper.Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.playWithVolumeShaping(iBinder, (Uri)object2, audioAttributes, f, bl2, (VolumeShaper.Configuration)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                IBinder iBinder = ((Parcel)object).readStrongBinder();
                object2 = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                AudioAttributes audioAttributes = ((Parcel)object).readInt() != 0 ? AudioAttributes.CREATOR.createFromParcel((Parcel)object) : null;
                float f = ((Parcel)object).readFloat();
                bl2 = ((Parcel)object).readInt() != 0;
                this.play(iBinder, (Uri)object2, audioAttributes, f, bl2);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IRingtonePlayer {
            public static IRingtonePlayer sDefaultImpl;
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
            public String getTitle(Uri object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getTitle((Uri)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readString();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isPlaying(IBinder iBinder) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder2;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeStrongBinder(iBinder);
                        iBinder2 = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder2.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isPlaying(iBinder);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelFileDescriptor openRingtone(Uri parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        parcelable.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().openRingtone((Uri)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
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
            public void play(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    int n = 0;
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeFloat(f);
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().play(iBinder, uri, audioAttributes, f, bl);
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
            public void playAsync(Uri uri, UserHandle userHandle, boolean bl, AudioAttributes audioAttributes) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (userHandle != null) {
                        parcel.writeInt(1);
                        userHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    if (audioAttributes != null) {
                        parcel.writeInt(1);
                        audioAttributes.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().playAsync(uri, userHandle, bl, audioAttributes);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void playWithVolumeShaping(IBinder iBinder, Uri uri, AudioAttributes audioAttributes, float f, boolean bl, VolumeShaper.Configuration configuration) throws RemoteException {
                Parcel parcel;
                void var1_6;
                block15 : {
                    block14 : {
                        block13 : {
                            parcel = Parcel.obtain();
                            parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                            try {
                                parcel.writeStrongBinder(iBinder);
                                if (uri != null) {
                                    parcel.writeInt(1);
                                    uri.writeToParcel(parcel, 0);
                                } else {
                                    parcel.writeInt(0);
                                }
                                if (audioAttributes != null) {
                                    parcel.writeInt(1);
                                    audioAttributes.writeToParcel(parcel, 0);
                                    break block13;
                                }
                                parcel.writeInt(0);
                            }
                            catch (Throwable throwable) {}
                        }
                        try {
                            parcel.writeFloat(f);
                            int n = bl ? 1 : 0;
                            parcel.writeInt(n);
                            if (configuration != null) {
                                parcel.writeInt(1);
                                configuration.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().playWithVolumeShaping(iBinder, uri, audioAttributes, f, bl, configuration);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_6;
            }

            @Override
            public void setPlaybackProperties(IBinder iBinder, float f, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeStrongBinder(iBinder);
                parcel.writeFloat(f);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPlaybackProperties(iBinder, f, bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stop(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stop(iBinder);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopAsync() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopAsync();
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

