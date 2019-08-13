/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.inputmethod.ExtractedText;

public interface IInputContextCallback
extends IInterface {
    public void setCommitContentResult(boolean var1, int var2) throws RemoteException;

    public void setCursorCapsMode(int var1, int var2) throws RemoteException;

    public void setExtractedText(ExtractedText var1, int var2) throws RemoteException;

    public void setRequestUpdateCursorAnchorInfoResult(boolean var1, int var2) throws RemoteException;

    public void setSelectedText(CharSequence var1, int var2) throws RemoteException;

    public void setTextAfterCursor(CharSequence var1, int var2) throws RemoteException;

    public void setTextBeforeCursor(CharSequence var1, int var2) throws RemoteException;

    public static class Default
    implements IInputContextCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void setCommitContentResult(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setCursorCapsMode(int n, int n2) throws RemoteException {
        }

        @Override
        public void setExtractedText(ExtractedText extractedText, int n) throws RemoteException {
        }

        @Override
        public void setRequestUpdateCursorAnchorInfoResult(boolean bl, int n) throws RemoteException {
        }

        @Override
        public void setSelectedText(CharSequence charSequence, int n) throws RemoteException {
        }

        @Override
        public void setTextAfterCursor(CharSequence charSequence, int n) throws RemoteException {
        }

        @Override
        public void setTextBeforeCursor(CharSequence charSequence, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputContextCallback {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputContextCallback";
        static final int TRANSACTION_setCommitContentResult = 7;
        static final int TRANSACTION_setCursorCapsMode = 3;
        static final int TRANSACTION_setExtractedText = 4;
        static final int TRANSACTION_setRequestUpdateCursorAnchorInfoResult = 6;
        static final int TRANSACTION_setSelectedText = 5;
        static final int TRANSACTION_setTextAfterCursor = 2;
        static final int TRANSACTION_setTextBeforeCursor = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputContextCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputContextCallback) {
                return (IInputContextCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputContextCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "setCommitContentResult";
                }
                case 6: {
                    return "setRequestUpdateCursorAnchorInfoResult";
                }
                case 5: {
                    return "setSelectedText";
                }
                case 4: {
                    return "setExtractedText";
                }
                case 3: {
                    return "setCursorCapsMode";
                }
                case 2: {
                    return "setTextAfterCursor";
                }
                case 1: 
            }
            return "setTextBeforeCursor";
        }

        public static boolean setDefaultImpl(IInputContextCallback iInputContextCallback) {
            if (Proxy.sDefaultImpl == null && iInputContextCallback != null) {
                Proxy.sDefaultImpl = iInputContextCallback;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    case 7: {
                        parcel.enforceInterface(DESCRIPTOR);
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.setCommitContentResult(bl2, parcel.readInt());
                        return true;
                    }
                    case 6: {
                        parcel.enforceInterface(DESCRIPTOR);
                        bl2 = bl;
                        if (parcel.readInt() != 0) {
                            bl2 = true;
                        }
                        this.setRequestUpdateCursorAnchorInfoResult(bl2, parcel.readInt());
                        return true;
                    }
                    case 5: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                        this.setSelectedText((CharSequence)object, parcel.readInt());
                        return true;
                    }
                    case 4: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? ExtractedText.CREATOR.createFromParcel(parcel) : null;
                        this.setExtractedText((ExtractedText)object, parcel.readInt());
                        return true;
                    }
                    case 3: {
                        parcel.enforceInterface(DESCRIPTOR);
                        this.setCursorCapsMode(parcel.readInt(), parcel.readInt());
                        return true;
                    }
                    case 2: {
                        parcel.enforceInterface(DESCRIPTOR);
                        object = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                        this.setTextAfterCursor((CharSequence)object, parcel.readInt());
                        return true;
                    }
                    case 1: 
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = parcel.readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel) : null;
                this.setTextBeforeCursor((CharSequence)object, parcel.readInt());
                return true;
            }
            ((Parcel)object).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInputContextCallback {
            public static IInputContextCallback sDefaultImpl;
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
            public void setCommitContentResult(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCommitContentResult(bl, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setCursorCapsMode(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCursorCapsMode(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setExtractedText(ExtractedText extractedText, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (extractedText != null) {
                        parcel.writeInt(1);
                        extractedText.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setExtractedText(extractedText, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setRequestUpdateCursorAnchorInfoResult(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setRequestUpdateCursorAnchorInfoResult(bl, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setSelectedText(CharSequence charSequence, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSelectedText(charSequence, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setTextAfterCursor(CharSequence charSequence, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTextAfterCursor(charSequence, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setTextBeforeCursor(CharSequence charSequence, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTextBeforeCursor(charSequence, n);
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

