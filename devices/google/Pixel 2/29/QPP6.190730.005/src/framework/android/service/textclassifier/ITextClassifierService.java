/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.textclassifier.ITextClassifierCallback;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;

public interface ITextClassifierService
extends IInterface {
    public void onClassifyText(TextClassificationSessionId var1, TextClassification.Request var2, ITextClassifierCallback var3) throws RemoteException;

    public void onCreateTextClassificationSession(TextClassificationContext var1, TextClassificationSessionId var2) throws RemoteException;

    public void onDestroyTextClassificationSession(TextClassificationSessionId var1) throws RemoteException;

    public void onDetectLanguage(TextClassificationSessionId var1, TextLanguage.Request var2, ITextClassifierCallback var3) throws RemoteException;

    public void onGenerateLinks(TextClassificationSessionId var1, TextLinks.Request var2, ITextClassifierCallback var3) throws RemoteException;

    public void onSelectionEvent(TextClassificationSessionId var1, SelectionEvent var2) throws RemoteException;

    public void onSuggestConversationActions(TextClassificationSessionId var1, ConversationActions.Request var2, ITextClassifierCallback var3) throws RemoteException;

    public void onSuggestSelection(TextClassificationSessionId var1, TextSelection.Request var2, ITextClassifierCallback var3) throws RemoteException;

    public void onTextClassifierEvent(TextClassificationSessionId var1, TextClassifierEvent var2) throws RemoteException;

    public static class Default
    implements ITextClassifierService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override
        public void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) throws RemoteException {
        }

        @Override
        public void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) throws RemoteException {
        }

        @Override
        public void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override
        public void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override
        public void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) throws RemoteException {
        }

        @Override
        public void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override
        public void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
        }

        @Override
        public void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITextClassifierService {
        private static final String DESCRIPTOR = "android.service.textclassifier.ITextClassifierService";
        static final int TRANSACTION_onClassifyText = 2;
        static final int TRANSACTION_onCreateTextClassificationSession = 6;
        static final int TRANSACTION_onDestroyTextClassificationSession = 7;
        static final int TRANSACTION_onDetectLanguage = 8;
        static final int TRANSACTION_onGenerateLinks = 3;
        static final int TRANSACTION_onSelectionEvent = 4;
        static final int TRANSACTION_onSuggestConversationActions = 9;
        static final int TRANSACTION_onSuggestSelection = 1;
        static final int TRANSACTION_onTextClassifierEvent = 5;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITextClassifierService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITextClassifierService) {
                return (ITextClassifierService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITextClassifierService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    return "onSuggestConversationActions";
                }
                case 8: {
                    return "onDetectLanguage";
                }
                case 7: {
                    return "onDestroyTextClassificationSession";
                }
                case 6: {
                    return "onCreateTextClassificationSession";
                }
                case 5: {
                    return "onTextClassifierEvent";
                }
                case 4: {
                    return "onSelectionEvent";
                }
                case 3: {
                    return "onGenerateLinks";
                }
                case 2: {
                    return "onClassifyText";
                }
                case 1: 
            }
            return "onSuggestSelection";
        }

        public static boolean setDefaultImpl(ITextClassifierService iTextClassifierService) {
            if (Proxy.sDefaultImpl == null && iTextClassifierService != null) {
                Proxy.sDefaultImpl = iTextClassifierService;
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
                        object2 = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        ConversationActions.Request request = ((Parcel)object).readInt() != 0 ? ConversationActions.Request.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSuggestConversationActions((TextClassificationSessionId)object2, request, ITextClassifierCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        TextLanguage.Request request = ((Parcel)object).readInt() != 0 ? TextLanguage.Request.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onDetectLanguage((TextClassificationSessionId)object2, request, ITextClassifierCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onDestroyTextClassificationSession((TextClassificationSessionId)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextClassificationContext.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCreateTextClassificationSession((TextClassificationContext)object2, (TextClassificationSessionId)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? TextClassifierEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTextClassifierEvent((TextClassificationSessionId)object2, (TextClassifierEvent)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? SelectionEvent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onSelectionEvent((TextClassificationSessionId)object2, (SelectionEvent)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        TextLinks.Request request = ((Parcel)object).readInt() != 0 ? TextLinks.Request.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onGenerateLinks((TextClassificationSessionId)object2, request, ITextClassifierCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                        TextClassification.Request request = ((Parcel)object).readInt() != 0 ? TextClassification.Request.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onClassifyText((TextClassificationSessionId)object2, request, ITextClassifierCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? TextClassificationSessionId.CREATOR.createFromParcel((Parcel)object) : null;
                TextSelection.Request request = ((Parcel)object).readInt() != 0 ? TextSelection.Request.CREATOR.createFromParcel((Parcel)object) : null;
                this.onSuggestSelection((TextClassificationSessionId)object2, request, ITextClassifierCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITextClassifierService {
            public static ITextClassifierService sDefaultImpl;
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (request != null) {
                        parcel.writeInt(1);
                        request.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iTextClassifierCallback != null ? iTextClassifierCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onClassifyText(textClassificationSessionId, request, iTextClassifierCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationContext != null) {
                        parcel.writeInt(1);
                        textClassificationContext.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDestroyTextClassificationSession(textClassificationSessionId);
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
            public void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (request != null) {
                        parcel.writeInt(1);
                        request.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iTextClassifierCallback != null ? iTextClassifierCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onDetectLanguage(textClassificationSessionId, request, iTextClassifierCallback);
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
            public void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (request != null) {
                        parcel.writeInt(1);
                        request.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iTextClassifierCallback != null ? iTextClassifierCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onGenerateLinks(textClassificationSessionId, request, iTextClassifierCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (selectionEvent != null) {
                        parcel.writeInt(1);
                        selectionEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSelectionEvent(textClassificationSessionId, selectionEvent);
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
            public void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (request != null) {
                        parcel.writeInt(1);
                        request.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iTextClassifierCallback != null ? iTextClassifierCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSuggestConversationActions(textClassificationSessionId, request, iTextClassifierCallback);
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
            public void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (request != null) {
                        parcel.writeInt(1);
                        request.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iTextClassifierCallback != null ? iTextClassifierCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onSuggestSelection(textClassificationSessionId, request, iTextClassifierCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (textClassificationSessionId != null) {
                        parcel.writeInt(1);
                        textClassificationSessionId.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (textClassifierEvent != null) {
                        parcel.writeInt(1);
                        textClassifierEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTextClassifierEvent(textClassificationSessionId, textClassifierEvent);
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

