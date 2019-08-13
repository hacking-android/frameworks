/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.textservice;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.SpellCheckerSubtype;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesSessionListener;

public interface ITextServicesManager
extends IInterface {
    public void finishSpellCheckerService(int var1, ISpellCheckerSessionListener var2) throws RemoteException;

    public SpellCheckerInfo getCurrentSpellChecker(int var1, String var2) throws RemoteException;

    public SpellCheckerSubtype getCurrentSpellCheckerSubtype(int var1, boolean var2) throws RemoteException;

    public SpellCheckerInfo[] getEnabledSpellCheckers(int var1) throws RemoteException;

    public void getSpellCheckerService(int var1, String var2, String var3, ITextServicesSessionListener var4, ISpellCheckerSessionListener var5, Bundle var6) throws RemoteException;

    public boolean isSpellCheckerEnabled(int var1) throws RemoteException;

    public static class Default
    implements ITextServicesManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void finishSpellCheckerService(int n, ISpellCheckerSessionListener iSpellCheckerSessionListener) throws RemoteException {
        }

        @Override
        public SpellCheckerInfo getCurrentSpellChecker(int n, String string2) throws RemoteException {
            return null;
        }

        @Override
        public SpellCheckerSubtype getCurrentSpellCheckerSubtype(int n, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public SpellCheckerInfo[] getEnabledSpellCheckers(int n) throws RemoteException {
            return null;
        }

        @Override
        public void getSpellCheckerService(int n, String string2, String string3, ITextServicesSessionListener iTextServicesSessionListener, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle) throws RemoteException {
        }

        @Override
        public boolean isSpellCheckerEnabled(int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITextServicesManager {
        private static final String DESCRIPTOR = "com.android.internal.textservice.ITextServicesManager";
        static final int TRANSACTION_finishSpellCheckerService = 4;
        static final int TRANSACTION_getCurrentSpellChecker = 1;
        static final int TRANSACTION_getCurrentSpellCheckerSubtype = 2;
        static final int TRANSACTION_getEnabledSpellCheckers = 6;
        static final int TRANSACTION_getSpellCheckerService = 3;
        static final int TRANSACTION_isSpellCheckerEnabled = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITextServicesManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITextServicesManager) {
                return (ITextServicesManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITextServicesManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 6: {
                    return "getEnabledSpellCheckers";
                }
                case 5: {
                    return "isSpellCheckerEnabled";
                }
                case 4: {
                    return "finishSpellCheckerService";
                }
                case 3: {
                    return "getSpellCheckerService";
                }
                case 2: {
                    return "getCurrentSpellCheckerSubtype";
                }
                case 1: 
            }
            return "getCurrentSpellChecker";
        }

        public static boolean setDefaultImpl(ITextServicesManager iTextServicesManager) {
            if (Proxy.sDefaultImpl == null && iTextServicesManager != null) {
                Proxy.sDefaultImpl = iTextServicesManager;
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
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getEnabledSpellCheckers(((Parcel)object).readInt());
                        ((Parcel)object2).writeNoException();
                        object2.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isSpellCheckerEnabled(((Parcel)object).readInt()) ? 1 : 0;
                        ((Parcel)object2).writeNoException();
                        ((Parcel)object2).writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishSpellCheckerService(((Parcel)object).readInt(), ISpellCheckerSessionListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        ITextServicesSessionListener iTextServicesSessionListener = ITextServicesSessionListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = ISpellCheckerSessionListener.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getSpellCheckerService(n, string2, string3, iTextServicesSessionListener, (ISpellCheckerSessionListener)object2, (Bundle)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        boolean bl = ((Parcel)object).readInt() != 0;
                        object = this.getCurrentSpellCheckerSubtype(n, bl);
                        ((Parcel)object2).writeNoException();
                        if (object != null) {
                            ((Parcel)object2).writeInt(1);
                            ((SpellCheckerSubtype)object).writeToParcel((Parcel)object2, 1);
                        } else {
                            ((Parcel)object2).writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getCurrentSpellChecker(((Parcel)object).readInt(), ((Parcel)object).readString());
                ((Parcel)object2).writeNoException();
                if (object != null) {
                    ((Parcel)object2).writeInt(1);
                    ((SpellCheckerInfo)object).writeToParcel((Parcel)object2, 1);
                } else {
                    ((Parcel)object2).writeInt(0);
                }
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITextServicesManager {
            public static ITextServicesManager sDefaultImpl;
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
            public void finishSpellCheckerService(int n, ISpellCheckerSessionListener iSpellCheckerSessionListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iSpellCheckerSessionListener != null ? iSpellCheckerSessionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().finishSpellCheckerService(n, iSpellCheckerSessionListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public SpellCheckerInfo getCurrentSpellChecker(int n, String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString((String)object);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getCurrentSpellChecker(n, (String)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? SpellCheckerInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public SpellCheckerSubtype getCurrentSpellCheckerSubtype(int n, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n2;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        n2 = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n2);
                    if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    SpellCheckerSubtype spellCheckerSubtype = Stub.getDefaultImpl().getCurrentSpellCheckerSubtype(n, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return spellCheckerSubtype;
                }
                parcel.readException();
                SpellCheckerSubtype spellCheckerSubtype = parcel.readInt() != 0 ? SpellCheckerSubtype.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return spellCheckerSubtype;
            }

            @Override
            public SpellCheckerInfo[] getEnabledSpellCheckers(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SpellCheckerInfo[] arrspellCheckerInfo = Stub.getDefaultImpl().getEnabledSpellCheckers(n);
                        return arrspellCheckerInfo;
                    }
                    parcel2.readException();
                    SpellCheckerInfo[] arrspellCheckerInfo = parcel2.createTypedArray(SpellCheckerInfo.CREATOR);
                    return arrspellCheckerInfo;
                }
                finally {
                    parcel2.recycle();
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
            public void getSpellCheckerService(int n, String string2, String string3, ITextServicesSessionListener iTextServicesSessionListener, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle) throws RemoteException {
                void var2_8;
                Parcel parcel;
                block13 : {
                    block12 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        try {
                            parcel.writeInt(n);
                        }
                        catch (Throwable throwable) {
                            break block13;
                        }
                        try {
                            parcel.writeString(string2);
                        }
                        catch (Throwable throwable) {
                            break block13;
                        }
                        try {
                            parcel.writeString(string3);
                            IBinder iBinder = iTextServicesSessionListener != null ? iTextServicesSessionListener.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                            iBinder = iSpellCheckerSessionListener != null ? iSpellCheckerSessionListener.asBinder() : null;
                            parcel.writeStrongBinder(iBinder);
                            if (bundle != null) {
                                parcel.writeInt(1);
                                bundle.writeToParcel(parcel, 0);
                                break block12;
                            }
                            parcel.writeInt(0);
                        }
                        catch (Throwable throwable) {}
                    }
                    try {
                        if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().getSpellCheckerService(n, string2, string3, iTextServicesSessionListener, iSpellCheckerSessionListener, bundle);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block13;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var2_8;
            }

            @Override
            public boolean isSpellCheckerEnabled(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(5, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isSpellCheckerEnabled(n);
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
        }

    }

}

