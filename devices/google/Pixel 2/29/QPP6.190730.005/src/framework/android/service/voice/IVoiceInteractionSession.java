/*
 * Decompiled with CFR 0.145.
 */
package android.service.voice;

import android.app.assist.AssistContent;
import android.app.assist.AssistStructure;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.app.IVoiceInteractionSessionShowCallback;

public interface IVoiceInteractionSession
extends IInterface {
    public void closeSystemDialogs() throws RemoteException;

    public void destroy() throws RemoteException;

    public void handleAssist(int var1, IBinder var2, Bundle var3, AssistStructure var4, AssistContent var5, int var6, int var7) throws RemoteException;

    public void handleScreenshot(Bitmap var1) throws RemoteException;

    public void hide() throws RemoteException;

    public void onLockscreenShown() throws RemoteException;

    public void show(Bundle var1, int var2, IVoiceInteractionSessionShowCallback var3) throws RemoteException;

    public void taskFinished(Intent var1, int var2) throws RemoteException;

    public void taskStarted(Intent var1, int var2) throws RemoteException;

    public static class Default
    implements IVoiceInteractionSession {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void closeSystemDialogs() throws RemoteException {
        }

        @Override
        public void destroy() throws RemoteException {
        }

        @Override
        public void handleAssist(int n, IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int n2, int n3) throws RemoteException {
        }

        @Override
        public void handleScreenshot(Bitmap bitmap) throws RemoteException {
        }

        @Override
        public void hide() throws RemoteException {
        }

        @Override
        public void onLockscreenShown() throws RemoteException {
        }

        @Override
        public void show(Bundle bundle, int n, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws RemoteException {
        }

        @Override
        public void taskFinished(Intent intent, int n) throws RemoteException {
        }

        @Override
        public void taskStarted(Intent intent, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceInteractionSession {
        private static final String DESCRIPTOR = "android.service.voice.IVoiceInteractionSession";
        static final int TRANSACTION_closeSystemDialogs = 7;
        static final int TRANSACTION_destroy = 9;
        static final int TRANSACTION_handleAssist = 3;
        static final int TRANSACTION_handleScreenshot = 4;
        static final int TRANSACTION_hide = 2;
        static final int TRANSACTION_onLockscreenShown = 8;
        static final int TRANSACTION_show = 1;
        static final int TRANSACTION_taskFinished = 6;
        static final int TRANSACTION_taskStarted = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceInteractionSession) {
                return (IVoiceInteractionSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceInteractionSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "destroy";
                }
                case 8: {
                    return "onLockscreenShown";
                }
                case 7: {
                    return "closeSystemDialogs";
                }
                case 6: {
                    return "taskFinished";
                }
                case 5: {
                    return "taskStarted";
                }
                case 4: {
                    return "handleScreenshot";
                }
                case 3: {
                    return "handleAssist";
                }
                case 2: {
                    return "hide";
                }
                case 1: 
            }
            return "show";
        }

        public static boolean setDefaultImpl(IVoiceInteractionSession iVoiceInteractionSession) {
            if (Proxy.sDefaultImpl == null && iVoiceInteractionSession != null) {
                Proxy.sDefaultImpl = iVoiceInteractionSession;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.destroy();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onLockscreenShown();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeSystemDialogs();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.taskFinished((Intent)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? Intent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.taskStarted((Intent)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Bitmap.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handleScreenshot((Bitmap)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        AssistStructure assistStructure = ((Parcel)object).readInt() != 0 ? AssistStructure.CREATOR.createFromParcel((Parcel)object) : null;
                        AssistContent assistContent = ((Parcel)object).readInt() != 0 ? AssistContent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.handleAssist(n, iBinder, (Bundle)object2, assistStructure, assistContent, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.hide();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                this.show((Bundle)object2, ((Parcel)object).readInt(), IVoiceInteractionSessionShowCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IVoiceInteractionSession {
            public static IVoiceInteractionSession sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void closeSystemDialogs() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeSystemDialogs();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void destroy() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().destroy();
                        return;
                    }
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void handleAssist(int n, IBinder iBinder, Bundle bundle, AssistStructure assistStructure, AssistContent assistContent, int n2, int n3) throws RemoteException {
                Parcel parcel;
                void var2_7;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block15;
                        }
                        try {
                            parcel.writeStrongBinder(iBinder);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            if (assistStructure != null) {
                                parcel.writeInt(1);
                                assistStructure.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            if (assistContent != null) {
                                parcel.writeInt(1);
                                assistContent.writeToParcel(parcel, 0);
                                break block14;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel.writeInt(n2);
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().handleAssist(n, iBinder, bundle, assistStructure, assistContent, n2, n3);
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
                throw var2_7;
            }

            @Override
            public void handleScreenshot(Bitmap bitmap) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bitmap != null) {
                        parcel.writeInt(1);
                        bitmap.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleScreenshot(bitmap);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void hide() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().hide();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLockscreenShown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLockscreenShown();
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
            public void show(Bundle bundle, int n, IVoiceInteractionSessionShowCallback iVoiceInteractionSessionShowCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    IBinder iBinder = iVoiceInteractionSessionShowCallback != null ? iVoiceInteractionSessionShowCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().show(bundle, n, iVoiceInteractionSessionShowCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void taskFinished(Intent intent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().taskFinished(intent, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void taskStarted(Intent intent, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().taskStarted(intent, n);
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

