/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.media.Session2Token
 */
package android.media.session;

import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.media.IRemoteVolumeController;
import android.media.Session2Token;
import android.media.session.IActiveSessionsListener;
import android.media.session.ICallback;
import android.media.session.IOnMediaKeyListener;
import android.media.session.IOnVolumeKeyLongPressListener;
import android.media.session.ISession;
import android.media.session.ISession2TokensListener;
import android.media.session.ISessionCallback;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public interface ISessionManager
extends IInterface {
    public void addSession2TokensListener(ISession2TokensListener var1, int var2) throws RemoteException;

    public void addSessionsListener(IActiveSessionsListener var1, ComponentName var2, int var3) throws RemoteException;

    public ISession createSession(String var1, ISessionCallback var2, String var3, Bundle var4, int var5) throws RemoteException;

    public void dispatchAdjustVolume(String var1, String var2, int var3, int var4, int var5) throws RemoteException;

    public void dispatchMediaKeyEvent(String var1, boolean var2, KeyEvent var3, boolean var4) throws RemoteException;

    public boolean dispatchMediaKeyEventToSessionAsSystemService(String var1, MediaSession.Token var2, KeyEvent var3) throws RemoteException;

    public void dispatchVolumeKeyEvent(String var1, String var2, boolean var3, KeyEvent var4, int var5, boolean var6) throws RemoteException;

    public void dispatchVolumeKeyEventToSessionAsSystemService(String var1, String var2, MediaSession.Token var3, KeyEvent var4) throws RemoteException;

    public ParceledListSlice getSession2Tokens(int var1) throws RemoteException;

    public List<MediaSession.Token> getSessions(ComponentName var1, int var2) throws RemoteException;

    public boolean isGlobalPriorityActive() throws RemoteException;

    public boolean isTrusted(String var1, int var2, int var3) throws RemoteException;

    public void notifySession2Created(Session2Token var1) throws RemoteException;

    public void registerRemoteVolumeController(IRemoteVolumeController var1) throws RemoteException;

    public void removeSession2TokensListener(ISession2TokensListener var1) throws RemoteException;

    public void removeSessionsListener(IActiveSessionsListener var1) throws RemoteException;

    public void setCallback(ICallback var1) throws RemoteException;

    public void setOnMediaKeyListener(IOnMediaKeyListener var1) throws RemoteException;

    public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener var1) throws RemoteException;

    public void unregisterRemoteVolumeController(IRemoteVolumeController var1) throws RemoteException;

    public static class Default
    implements ISessionManager {
        @Override
        public void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int n) throws RemoteException {
        }

        @Override
        public void addSessionsListener(IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ISession createSession(String string2, ISessionCallback iSessionCallback, String string3, Bundle bundle, int n) throws RemoteException {
            return null;
        }

        @Override
        public void dispatchAdjustVolume(String string2, String string3, int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void dispatchMediaKeyEvent(String string2, boolean bl, KeyEvent keyEvent, boolean bl2) throws RemoteException {
        }

        @Override
        public boolean dispatchMediaKeyEventToSessionAsSystemService(String string2, MediaSession.Token token, KeyEvent keyEvent) throws RemoteException {
            return false;
        }

        @Override
        public void dispatchVolumeKeyEvent(String string2, String string3, boolean bl, KeyEvent keyEvent, int n, boolean bl2) throws RemoteException {
        }

        @Override
        public void dispatchVolumeKeyEventToSessionAsSystemService(String string2, String string3, MediaSession.Token token, KeyEvent keyEvent) throws RemoteException {
        }

        @Override
        public ParceledListSlice getSession2Tokens(int n) throws RemoteException {
            return null;
        }

        @Override
        public List<MediaSession.Token> getSessions(ComponentName componentName, int n) throws RemoteException {
            return null;
        }

        @Override
        public boolean isGlobalPriorityActive() throws RemoteException {
            return false;
        }

        @Override
        public boolean isTrusted(String string2, int n, int n2) throws RemoteException {
            return false;
        }

        @Override
        public void notifySession2Created(Session2Token session2Token) throws RemoteException {
        }

        @Override
        public void registerRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) throws RemoteException {
        }

        @Override
        public void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) throws RemoteException {
        }

        @Override
        public void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) throws RemoteException {
        }

        @Override
        public void setCallback(ICallback iCallback) throws RemoteException {
        }

        @Override
        public void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) throws RemoteException {
        }

        @Override
        public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws RemoteException {
        }

        @Override
        public void unregisterRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISessionManager {
        private static final String DESCRIPTOR = "android.media.session.ISessionManager";
        static final int TRANSACTION_addSession2TokensListener = 12;
        static final int TRANSACTION_addSessionsListener = 10;
        static final int TRANSACTION_createSession = 1;
        static final int TRANSACTION_dispatchAdjustVolume = 9;
        static final int TRANSACTION_dispatchMediaKeyEvent = 5;
        static final int TRANSACTION_dispatchMediaKeyEventToSessionAsSystemService = 6;
        static final int TRANSACTION_dispatchVolumeKeyEvent = 7;
        static final int TRANSACTION_dispatchVolumeKeyEventToSessionAsSystemService = 8;
        static final int TRANSACTION_getSession2Tokens = 4;
        static final int TRANSACTION_getSessions = 3;
        static final int TRANSACTION_isGlobalPriorityActive = 16;
        static final int TRANSACTION_isTrusted = 20;
        static final int TRANSACTION_notifySession2Created = 2;
        static final int TRANSACTION_registerRemoteVolumeController = 14;
        static final int TRANSACTION_removeSession2TokensListener = 13;
        static final int TRANSACTION_removeSessionsListener = 11;
        static final int TRANSACTION_setCallback = 17;
        static final int TRANSACTION_setOnMediaKeyListener = 19;
        static final int TRANSACTION_setOnVolumeKeyLongPressListener = 18;
        static final int TRANSACTION_unregisterRemoteVolumeController = 15;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISessionManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISessionManager) {
                return (ISessionManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISessionManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 20: {
                    return "isTrusted";
                }
                case 19: {
                    return "setOnMediaKeyListener";
                }
                case 18: {
                    return "setOnVolumeKeyLongPressListener";
                }
                case 17: {
                    return "setCallback";
                }
                case 16: {
                    return "isGlobalPriorityActive";
                }
                case 15: {
                    return "unregisterRemoteVolumeController";
                }
                case 14: {
                    return "registerRemoteVolumeController";
                }
                case 13: {
                    return "removeSession2TokensListener";
                }
                case 12: {
                    return "addSession2TokensListener";
                }
                case 11: {
                    return "removeSessionsListener";
                }
                case 10: {
                    return "addSessionsListener";
                }
                case 9: {
                    return "dispatchAdjustVolume";
                }
                case 8: {
                    return "dispatchVolumeKeyEventToSessionAsSystemService";
                }
                case 7: {
                    return "dispatchVolumeKeyEvent";
                }
                case 6: {
                    return "dispatchMediaKeyEventToSessionAsSystemService";
                }
                case 5: {
                    return "dispatchMediaKeyEvent";
                }
                case 4: {
                    return "getSession2Tokens";
                }
                case 3: {
                    return "getSessions";
                }
                case 2: {
                    return "notifySession2Created";
                }
                case 1: 
            }
            return "createSession";
        }

        public static boolean setDefaultImpl(ISessionManager iSessionManager) {
            if (Proxy.sDefaultImpl == null && iSessionManager != null) {
                Proxy.sDefaultImpl = iSessionManager;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isTrusted(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOnMediaKeyListener(IOnMediaKeyListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCallback(ICallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isGlobalPriorityActive() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterRemoteVolumeController(IRemoteVolumeController.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerRemoteVolumeController(IRemoteVolumeController.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeSession2TokensListener(ISession2TokensListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addSession2TokensListener(ISession2TokensListener.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeSessionsListener(IActiveSessionsListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IActiveSessionsListener iActiveSessionsListener = IActiveSessionsListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addSessionsListener(iActiveSessionsListener, componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.dispatchAdjustVolume(((Parcel)object).readString(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        MediaSession.Token token = ((Parcel)object).readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.dispatchVolumeKeyEventToSessionAsSystemService(string2, string3, token, (KeyEvent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string4 = ((Parcel)object).readString();
                        String string5 = ((Parcel)object).readString();
                        boolean bl2 = ((Parcel)object).readInt() != 0;
                        KeyEvent keyEvent = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        this.dispatchVolumeKeyEvent(string4, string5, bl2, keyEvent, n, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string6 = ((Parcel)object).readString();
                        MediaSession.Token token = ((Parcel)object).readInt() != 0 ? MediaSession.Token.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.dispatchMediaKeyEventToSessionAsSystemService(string6, token, (KeyEvent)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string7 = ((Parcel)object).readString();
                        boolean bl3 = ((Parcel)object).readInt() != 0;
                        KeyEvent keyEvent = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.dispatchMediaKeyEvent(string7, bl3, keyEvent, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSession2Tokens(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParceledListSlice)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ComponentName componentName = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getSessions(componentName, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? (Session2Token)Session2Token.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifySession2Created((Session2Token)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                String string8 = ((Parcel)object).readString();
                ISessionCallback iSessionCallback = ISessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                String string9 = ((Parcel)object).readString();
                Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                object = this.createSession(string8, iSessionCallback, string9, bundle, ((Parcel)object).readInt());
                parcel.writeNoException();
                object = object != null ? object.asBinder() : null;
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ISessionManager {
            public static ISessionManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addSession2TokensListener(ISession2TokensListener iSession2TokensListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSession2TokensListener != null ? iSession2TokensListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSession2TokensListener(iSession2TokensListener, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addSessionsListener(IActiveSessionsListener iActiveSessionsListener, ComponentName componentName, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iActiveSessionsListener != null ? iActiveSessionsListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSessionsListener(iActiveSessionsListener, componentName, n);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ISession createSession(String object, ISessionCallback iSessionCallback, String string2, Bundle bundle, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    IBinder iBinder = iSessionCallback != null ? iSessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createSession((String)object, iSessionCallback, string2, bundle, n);
                        return object;
                    }
                    parcel2.readException();
                    object = ISession.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void dispatchAdjustVolume(String string2, String string3, int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchAdjustVolume(string2, string3, n, n2, n3);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void dispatchMediaKeyEvent(String string2, boolean bl, KeyEvent keyEvent, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    int n = 1;
                    int n2 = bl ? 1 : 0;
                    parcel.writeInt(n2);
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    n2 = bl2 ? n : 0;
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchMediaKeyEvent(string2, bl, keyEvent, bl2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean dispatchMediaKeyEventToSessionAsSystemService(String string2, MediaSession.Token token, KeyEvent keyEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    boolean bl = true;
                    if (token != null) {
                        parcel.writeInt(1);
                        token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().dispatchMediaKeyEventToSessionAsSystemService(string2, token, keyEvent);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void dispatchVolumeKeyEvent(String string2, String string3, boolean bl, KeyEvent keyEvent, int n, boolean bl2) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    int n3;
                    int n2;
                    block13 : {
                        parcel2 = Parcel.obtain();
                        parcel = Parcel.obtain();
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel2.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block14;
                        }
                        try {
                            parcel2.writeString(string3);
                            n2 = 1;
                            n3 = bl ? 1 : 0;
                            parcel2.writeInt(n3);
                            if (keyEvent != null) {
                                parcel2.writeInt(1);
                                keyEvent.writeToParcel(parcel2, 0);
                                break block13;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        parcel2.writeInt(n);
                        n3 = bl2 ? n2 : 0;
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(7, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().dispatchVolumeKeyEvent(string2, string3, bl, keyEvent, n, bl2);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var1_7;
            }

            @Override
            public void dispatchVolumeKeyEventToSessionAsSystemService(String string2, String string3, MediaSession.Token token, KeyEvent keyEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    if (token != null) {
                        parcel.writeInt(1);
                        token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatchVolumeKeyEventToSessionAsSystemService(string2, string3, token, keyEvent);
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
            public ParceledListSlice getSession2Tokens(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(4, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParceledListSlice parceledListSlice = Stub.getDefaultImpl().getSession2Tokens(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return parceledListSlice;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParceledListSlice parceledListSlice = parcel2.readInt() != 0 ? (ParceledListSlice)ParceledListSlice.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parceledListSlice;
            }

            @Override
            public List<MediaSession.Token> getSessions(ComponentName arrayList, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (arrayList != null) {
                        parcel.writeInt(1);
                        ((ComponentName)((Object)arrayList)).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrayList = Stub.getDefaultImpl().getSessions((ComponentName)((Object)arrayList), n);
                        return arrayList;
                    }
                    parcel2.readException();
                    arrayList = parcel2.createTypedArrayList(MediaSession.Token.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isGlobalPriorityActive() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(16, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isGlobalPriorityActive();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isTrusted(String string2, int n, int n2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        parcel.writeInt(n);
                        parcel.writeInt(n2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(20, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isTrusted(string2, n, n2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void notifySession2Created(Session2Token session2Token) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (session2Token != null) {
                        parcel.writeInt(1);
                        session2Token.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifySession2Created(session2Token);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRemoteVolumeController != null ? iRemoteVolumeController.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerRemoteVolumeController(iRemoteVolumeController);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeSession2TokensListener(ISession2TokensListener iSession2TokensListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSession2TokensListener != null ? iSession2TokensListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSession2TokensListener(iSession2TokensListener);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeSessionsListener(IActiveSessionsListener iActiveSessionsListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iActiveSessionsListener != null ? iActiveSessionsListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSessionsListener(iActiveSessionsListener);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setCallback(ICallback iCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCallback != null ? iCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCallback(iCallback);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setOnMediaKeyListener(IOnMediaKeyListener iOnMediaKeyListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnMediaKeyListener != null ? iOnMediaKeyListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOnMediaKeyListener(iOnMediaKeyListener);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void setOnVolumeKeyLongPressListener(IOnVolumeKeyLongPressListener iOnVolumeKeyLongPressListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iOnVolumeKeyLongPressListener != null ? iOnVolumeKeyLongPressListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setOnVolumeKeyLongPressListener(iOnVolumeKeyLongPressListener);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterRemoteVolumeController(IRemoteVolumeController iRemoteVolumeController) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iRemoteVolumeController != null ? iRemoteVolumeController.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterRemoteVolumeController(iRemoteVolumeController);
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

