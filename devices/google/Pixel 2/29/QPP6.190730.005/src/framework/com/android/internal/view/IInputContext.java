/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputContentInfo;
import com.android.internal.view.IInputContextCallback;

public interface IInputContext
extends IInterface {
    public void beginBatchEdit() throws RemoteException;

    public void clearMetaKeyStates(int var1) throws RemoteException;

    public void commitCompletion(CompletionInfo var1) throws RemoteException;

    public void commitContent(InputContentInfo var1, int var2, Bundle var3, int var4, IInputContextCallback var5) throws RemoteException;

    public void commitCorrection(CorrectionInfo var1) throws RemoteException;

    public void commitText(CharSequence var1, int var2) throws RemoteException;

    public void deleteSurroundingText(int var1, int var2) throws RemoteException;

    public void deleteSurroundingTextInCodePoints(int var1, int var2) throws RemoteException;

    public void endBatchEdit() throws RemoteException;

    public void finishComposingText() throws RemoteException;

    public void getCursorCapsMode(int var1, int var2, IInputContextCallback var3) throws RemoteException;

    public void getExtractedText(ExtractedTextRequest var1, int var2, int var3, IInputContextCallback var4) throws RemoteException;

    public void getSelectedText(int var1, int var2, IInputContextCallback var3) throws RemoteException;

    public void getTextAfterCursor(int var1, int var2, int var3, IInputContextCallback var4) throws RemoteException;

    public void getTextBeforeCursor(int var1, int var2, int var3, IInputContextCallback var4) throws RemoteException;

    public void performContextMenuAction(int var1) throws RemoteException;

    public void performEditorAction(int var1) throws RemoteException;

    public void performPrivateCommand(String var1, Bundle var2) throws RemoteException;

    public void requestUpdateCursorAnchorInfo(int var1, int var2, IInputContextCallback var3) throws RemoteException;

    public void sendKeyEvent(KeyEvent var1) throws RemoteException;

    public void setComposingRegion(int var1, int var2) throws RemoteException;

    public void setComposingText(CharSequence var1, int var2) throws RemoteException;

    public void setSelection(int var1, int var2) throws RemoteException;

    public static class Default
    implements IInputContext {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void beginBatchEdit() throws RemoteException {
        }

        @Override
        public void clearMetaKeyStates(int n) throws RemoteException {
        }

        @Override
        public void commitCompletion(CompletionInfo completionInfo) throws RemoteException {
        }

        @Override
        public void commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
        }

        @Override
        public void commitCorrection(CorrectionInfo correctionInfo) throws RemoteException {
        }

        @Override
        public void commitText(CharSequence charSequence, int n) throws RemoteException {
        }

        @Override
        public void deleteSurroundingText(int n, int n2) throws RemoteException {
        }

        @Override
        public void deleteSurroundingTextInCodePoints(int n, int n2) throws RemoteException {
        }

        @Override
        public void endBatchEdit() throws RemoteException {
        }

        @Override
        public void finishComposingText() throws RemoteException {
        }

        @Override
        public void getCursorCapsMode(int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
        }

        @Override
        public void getExtractedText(ExtractedTextRequest extractedTextRequest, int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
        }

        @Override
        public void getSelectedText(int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
        }

        @Override
        public void getTextAfterCursor(int n, int n2, int n3, IInputContextCallback iInputContextCallback) throws RemoteException {
        }

        @Override
        public void getTextBeforeCursor(int n, int n2, int n3, IInputContextCallback iInputContextCallback) throws RemoteException {
        }

        @Override
        public void performContextMenuAction(int n) throws RemoteException {
        }

        @Override
        public void performEditorAction(int n) throws RemoteException {
        }

        @Override
        public void performPrivateCommand(String string2, Bundle bundle) throws RemoteException {
        }

        @Override
        public void requestUpdateCursorAnchorInfo(int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
        }

        @Override
        public void sendKeyEvent(KeyEvent keyEvent) throws RemoteException {
        }

        @Override
        public void setComposingRegion(int n, int n2) throws RemoteException {
        }

        @Override
        public void setComposingText(CharSequence charSequence, int n) throws RemoteException {
        }

        @Override
        public void setSelection(int n, int n2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInputContext {
        private static final String DESCRIPTOR = "com.android.internal.view.IInputContext";
        static final int TRANSACTION_beginBatchEdit = 15;
        static final int TRANSACTION_clearMetaKeyStates = 18;
        static final int TRANSACTION_commitCompletion = 10;
        static final int TRANSACTION_commitContent = 23;
        static final int TRANSACTION_commitCorrection = 11;
        static final int TRANSACTION_commitText = 9;
        static final int TRANSACTION_deleteSurroundingText = 5;
        static final int TRANSACTION_deleteSurroundingTextInCodePoints = 6;
        static final int TRANSACTION_endBatchEdit = 16;
        static final int TRANSACTION_finishComposingText = 8;
        static final int TRANSACTION_getCursorCapsMode = 3;
        static final int TRANSACTION_getExtractedText = 4;
        static final int TRANSACTION_getSelectedText = 21;
        static final int TRANSACTION_getTextAfterCursor = 2;
        static final int TRANSACTION_getTextBeforeCursor = 1;
        static final int TRANSACTION_performContextMenuAction = 14;
        static final int TRANSACTION_performEditorAction = 13;
        static final int TRANSACTION_performPrivateCommand = 19;
        static final int TRANSACTION_requestUpdateCursorAnchorInfo = 22;
        static final int TRANSACTION_sendKeyEvent = 17;
        static final int TRANSACTION_setComposingRegion = 20;
        static final int TRANSACTION_setComposingText = 7;
        static final int TRANSACTION_setSelection = 12;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInputContext asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInputContext) {
                return (IInputContext)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInputContext getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 23: {
                    return "commitContent";
                }
                case 22: {
                    return "requestUpdateCursorAnchorInfo";
                }
                case 21: {
                    return "getSelectedText";
                }
                case 20: {
                    return "setComposingRegion";
                }
                case 19: {
                    return "performPrivateCommand";
                }
                case 18: {
                    return "clearMetaKeyStates";
                }
                case 17: {
                    return "sendKeyEvent";
                }
                case 16: {
                    return "endBatchEdit";
                }
                case 15: {
                    return "beginBatchEdit";
                }
                case 14: {
                    return "performContextMenuAction";
                }
                case 13: {
                    return "performEditorAction";
                }
                case 12: {
                    return "setSelection";
                }
                case 11: {
                    return "commitCorrection";
                }
                case 10: {
                    return "commitCompletion";
                }
                case 9: {
                    return "commitText";
                }
                case 8: {
                    return "finishComposingText";
                }
                case 7: {
                    return "setComposingText";
                }
                case 6: {
                    return "deleteSurroundingTextInCodePoints";
                }
                case 5: {
                    return "deleteSurroundingText";
                }
                case 4: {
                    return "getExtractedText";
                }
                case 3: {
                    return "getCursorCapsMode";
                }
                case 2: {
                    return "getTextAfterCursor";
                }
                case 1: 
            }
            return "getTextBeforeCursor";
        }

        public static boolean setDefaultImpl(IInputContext iInputContext) {
            if (Proxy.sDefaultImpl == null && iInputContext != null) {
                Proxy.sDefaultImpl = iInputContext;
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
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? InputContentInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        n = ((Parcel)object).readInt();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.commitContent((InputContentInfo)object2, n, bundle, ((Parcel)object).readInt(), IInputContextCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestUpdateCursorAnchorInfo(((Parcel)object).readInt(), ((Parcel)object).readInt(), IInputContextCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getSelectedText(((Parcel)object).readInt(), ((Parcel)object).readInt(), IInputContextCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setComposingRegion(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.performPrivateCommand((String)object2, (Bundle)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearMetaKeyStates(((Parcel)object).readInt());
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendKeyEvent((KeyEvent)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.endBatchEdit();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.beginBatchEdit();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.performContextMenuAction(((Parcel)object).readInt());
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.performEditorAction(((Parcel)object).readInt());
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setSelection(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? CorrectionInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.commitCorrection((CorrectionInfo)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? CompletionInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.commitCompletion((CompletionInfo)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.commitText((CharSequence)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.finishComposingText();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                        this.setComposingText((CharSequence)object2, ((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteSurroundingTextInCodePoints(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteSurroundingText(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? ExtractedTextRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        this.getExtractedText((ExtractedTextRequest)object2, ((Parcel)object).readInt(), ((Parcel)object).readInt(), IInputContextCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getCursorCapsMode(((Parcel)object).readInt(), ((Parcel)object).readInt(), IInputContextCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getTextAfterCursor(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), IInputContextCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.getTextBeforeCursor(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), IInputContextCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IInputContext {
            public static IInputContext sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void beginBatchEdit() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().beginBatchEdit();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void clearMetaKeyStates(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearMetaKeyStates(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void commitCompletion(CompletionInfo completionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (completionInfo != null) {
                        parcel.writeInt(1);
                        completionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().commitCompletion(completionInfo);
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
            public void commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputContentInfo != null) {
                        parcel.writeInt(1);
                        inputContentInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n2);
                    IBinder iBinder = iInputContextCallback != null ? iInputContextCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(23, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().commitContent(inputContentInfo, n, bundle, n2, iInputContextCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void commitCorrection(CorrectionInfo correctionInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (correctionInfo != null) {
                        parcel.writeInt(1);
                        correctionInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().commitCorrection(correctionInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void commitText(CharSequence charSequence, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().commitText(charSequence, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deleteSurroundingText(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteSurroundingText(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void deleteSurroundingTextInCodePoints(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteSurroundingTextInCodePoints(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void endBatchEdit() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endBatchEdit();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void finishComposingText() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finishComposingText();
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
            public void getCursorCapsMode(int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iInputContextCallback != null ? iInputContextCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getCursorCapsMode(n, n2, iInputContextCallback);
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
            public void getExtractedText(ExtractedTextRequest extractedTextRequest, int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (extractedTextRequest != null) {
                        parcel.writeInt(1);
                        extractedTextRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iInputContextCallback != null ? iInputContextCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getExtractedText(extractedTextRequest, n, n2, iInputContextCallback);
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
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getSelectedText(int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iInputContextCallback != null ? iInputContextCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(21, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getSelectedText(n, n2, iInputContextCallback);
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
            public void getTextAfterCursor(int n, int n2, int n3, IInputContextCallback iInputContextCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    IBinder iBinder = iInputContextCallback != null ? iInputContextCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getTextAfterCursor(n, n2, n3, iInputContextCallback);
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
            public void getTextBeforeCursor(int n, int n2, int n3, IInputContextCallback iInputContextCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    IBinder iBinder = iInputContextCallback != null ? iInputContextCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getTextBeforeCursor(n, n2, n3, iInputContextCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void performContextMenuAction(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performContextMenuAction(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void performEditorAction(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performEditorAction(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void performPrivateCommand(String string2, Bundle bundle) throws RemoteException {
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
                    if (!this.mRemote.transact(19, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().performPrivateCommand(string2, bundle);
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
            public void requestUpdateCursorAnchorInfo(int n, int n2, IInputContextCallback iInputContextCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iInputContextCallback != null ? iInputContextCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(22, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().requestUpdateCursorAnchorInfo(n, n2, iInputContextCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendKeyEvent(KeyEvent keyEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (keyEvent != null) {
                        parcel.writeInt(1);
                        keyEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendKeyEvent(keyEvent);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setComposingRegion(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setComposingRegion(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setComposingText(CharSequence charSequence, int n) throws RemoteException {
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
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setComposingText(charSequence, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setSelection(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(12, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSelection(n, n2);
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

