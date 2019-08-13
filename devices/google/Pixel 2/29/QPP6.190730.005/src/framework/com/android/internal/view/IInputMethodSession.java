/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CursorAnchorInfo;
import android.view.inputmethod.ExtractedText;

public interface IInputMethodSession
extends IInterface {
    public void appPrivateCommand(String var1, Bundle var2) throws RemoteException;

    public void displayCompletions(CompletionInfo[] var1) throws RemoteException;

    public void finishSession() throws RemoteException;

    public void notifyImeHidden() throws RemoteException;

    public void toggleSoftInput(int var1, int var2) throws RemoteException;

    public void updateCursor(Rect var1) throws RemoteException;

    public void updateCursorAnchorInfo(CursorAnchorInfo var1) throws RemoteException;

    public void updateExtractedText(int var1, ExtractedText var2) throws RemoteException;

    public void updateSelection(int var1, int var2, int var3, int var4, int var5, int var6) throws RemoteException;

    public void viewClicked(boolean var1) throws RemoteException;

    public static class Default
    implements IInputMethodSession {
        @Override
        public void appPrivateCommand(String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void displayCompletions(CompletionInfo[] arrcompletionInfo) throws RemoteException {
        }

        @Override
        public void finishSession() throws RemoteException {
        }

        @Override
        public void notifyImeHidden() throws RemoteException {
        }

        @Override
        public void toggleSoftInput(int n, int n2) throws RemoteException {
        }

        @Override
        public void updateCursor(Rect rect) throws RemoteException {
        }

        @Override
        public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException {
        }

        @Override
        public void updateExtractedText(int n, ExtractedText extractedText) throws RemoteException {
        }

        @Override
        public void updateSelection(int n, int n2, int n3, int n4, int n5, int n6) throws RemoteException {
        }

        @Override
        public void viewClicked(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputMethodSession {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputMethodSession";
        static final int TRANSACTION_appPrivateCommand = 6;
        static final int TRANSACTION_displayCompletions = 5;
        static final int TRANSACTION_finishSession = 8;
        static final int TRANSACTION_notifyImeHidden = 10;
        static final int TRANSACTION_toggleSoftInput = 7;
        static final int TRANSACTION_updateCursor = 4;
        static final int TRANSACTION_updateCursorAnchorInfo = 9;
        static final int TRANSACTION_updateExtractedText = 1;
        static final int TRANSACTION_updateSelection = 2;
        static final int TRANSACTION_viewClicked = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputMethodSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputMethodSession) {
                return (IInputMethodSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputMethodSession getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 10: {
                    return "notifyImeHidden";
                }
                case 9: {
                    return "updateCursorAnchorInfo";
                }
                case 8: {
                    return "finishSession";
                }
                case 7: {
                    return "toggleSoftInput";
                }
                case 6: {
                    return "appPrivateCommand";
                }
                case 5: {
                    return "displayCompletions";
                }
                case 4: {
                    return "updateCursor";
                }
                case 3: {
                    return "viewClicked";
                }
                case 2: {
                    return "updateSelection";
                }
                case 1: 
            }
            return "updateExtractedText";
        }

        public static boolean setDefaultImpl(IInputMethodSession iInputMethodSession) {
            if (Proxy.sDefaultImpl == null && iInputMethodSession != null) {
                Proxy.sDefaultImpl = iInputMethodSession;
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
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.notifyImeHidden();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? CursorAnchorInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateCursorAnchorInfo((CursorAnchorInfo)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishSession();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.toggleSoftInput(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.appPrivateCommand((String)object2, (Bundle)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.displayCompletions(((Parcel)object).createTypedArray(CompletionInfo.CREATOR));
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Rect.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateCursor((Rect)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.viewClicked(bl);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.updateSelection(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? ExtractedText.CREATOR.createFromParcel((Parcel)object) : null;
                this.updateExtractedText(n, (ExtractedText)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInputMethodSession {
            public static IInputMethodSession sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void appPrivateCommand(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().appPrivateCommand(string2, bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void displayCompletions(CompletionInfo[] arrcompletionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arrcompletionInfo, 0);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().displayCompletions(arrcompletionInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void finishSession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishSession();
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

            @Override
            public void notifyImeHidden() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyImeHidden();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void toggleSoftInput(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().toggleSoftInput(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateCursor(Rect rect) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (rect != null) {
                        parcel.writeInt(1);
                        rect.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateCursor(rect);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateCursorAnchorInfo(CursorAnchorInfo cursorAnchorInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cursorAnchorInfo != null) {
                        parcel.writeInt(1);
                        cursorAnchorInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateCursorAnchorInfo(cursorAnchorInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void updateExtractedText(int n, ExtractedText extractedText) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (extractedText != null) {
                        parcel.writeInt(1);
                        extractedText.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateExtractedText(n, extractedText);
                        return;
                    }
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
            public void updateSelection(int n, int n2, int n3, int n4, int n5, int n6) throws RemoteException {
                void var8_16;
                Parcel parcel;
                block17 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n5);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        parcel.writeInt(n6);
                    }
                    catch (Throwable throwable) {
                        break block17;
                    }
                    try {
                        if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().updateSelection(n, n2, n3, n4, n5, n6);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block17;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var8_16;
            }

            @Override
            public void viewClicked(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().viewClicked(bl);
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

