/*
 * Decompiled with CFR 0.145.
 */
package android.view.textclassifier;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.service.textclassifier.ITextClassifierCallback;
import android.service.textclassifier.ITextClassifierService;
import android.service.textclassifier.TextClassifierService;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.Log;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationConstants;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.Preconditions;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class SystemTextClassifier
implements TextClassifier {
    private static final String LOG_TAG = "SystemTextClassifier";
    private final TextClassifier mFallback;
    private final ITextClassifierService mManagerService = ITextClassifierService.Stub.asInterface(ServiceManager.getServiceOrThrow("textclassification"));
    private final String mPackageName;
    private TextClassificationSessionId mSessionId;
    private final TextClassificationConstants mSettings;

    public SystemTextClassifier(Context context, TextClassificationConstants textClassificationConstants) throws ServiceManager.ServiceNotFoundException {
        this.mSettings = Preconditions.checkNotNull(textClassificationConstants);
        this.mFallback = context.getSystemService(TextClassificationManager.class).getTextClassifier(0);
        this.mPackageName = Preconditions.checkNotNull(context.getOpPackageName());
    }

    @Override
    public TextClassification classifyText(TextClassification.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setCallingPackageName(this.mPackageName);
            Object object = new BlockingCallback("textclassification");
            this.mManagerService.onClassifyText(this.mSessionId, request, (ITextClassifierCallback)object);
            object = (TextClassification)((BlockingCallback)object).get();
            if (object != null) {
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error classifying text. Using fallback.", remoteException);
        }
        return this.mFallback.classifyText(request);
    }

    @Override
    public void destroy() {
        try {
            if (this.mSessionId != null) {
                this.mManagerService.onDestroyTextClassificationSession(this.mSessionId);
            }
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error destroying classification session.", remoteException);
        }
    }

    @Override
    public TextLanguage detectLanguage(TextLanguage.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setCallingPackageName(this.mPackageName);
            Object object = new BlockingCallback("textlanguage");
            this.mManagerService.onDetectLanguage(this.mSessionId, request, (ITextClassifierCallback)object);
            object = (TextLanguage)((BlockingCallback)object).get();
            if (object != null) {
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error detecting language.", remoteException);
        }
        return this.mFallback.detectLanguage(request);
    }

    @Override
    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("SystemTextClassifier:");
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.printPair("mFallback", this.mFallback);
        indentingPrintWriter.printPair("mPackageName", this.mPackageName);
        indentingPrintWriter.printPair("mSessionId", this.mSessionId);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println();
    }

    @Override
    public TextLinks generateLinks(TextLinks.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        if (!this.mSettings.isSmartLinkifyEnabled() && request.isLegacyFallback()) {
            return TextClassifier.Utils.generateLegacyLinks(request);
        }
        try {
            request.setCallingPackageName(this.mPackageName);
            Object object = new BlockingCallback("textlinks");
            this.mManagerService.onGenerateLinks(this.mSessionId, request, (ITextClassifierCallback)object);
            object = (TextLinks)((BlockingCallback)object).get();
            if (object != null) {
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error generating links. Using fallback.", remoteException);
        }
        return this.mFallback.generateLinks(request);
    }

    @Override
    public int getMaxGenerateLinksTextLength() {
        return this.mFallback.getMaxGenerateLinksTextLength();
    }

    void initializeRemoteSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
        this.mSessionId = Preconditions.checkNotNull(textClassificationSessionId);
        try {
            this.mManagerService.onCreateTextClassificationSession(textClassificationContext, this.mSessionId);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error starting a new classification session.", remoteException);
        }
    }

    @Override
    public void onSelectionEvent(SelectionEvent selectionEvent) {
        Preconditions.checkNotNull(selectionEvent);
        TextClassifier.Utils.checkMainThread();
        try {
            this.mManagerService.onSelectionEvent(this.mSessionId, selectionEvent);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error reporting selection event.", remoteException);
        }
    }

    @Override
    public void onTextClassifierEvent(TextClassifierEvent textClassifierEvent) {
        Preconditions.checkNotNull(textClassifierEvent);
        TextClassifier.Utils.checkMainThread();
        try {
            this.mManagerService.onTextClassifierEvent(this.mSessionId, textClassifierEvent);
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error reporting textclassifier event.", remoteException);
        }
    }

    @Override
    public ConversationActions suggestConversationActions(ConversationActions.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setCallingPackageName(this.mPackageName);
            Object object = new BlockingCallback("conversation-actions");
            this.mManagerService.onSuggestConversationActions(this.mSessionId, request, (ITextClassifierCallback)object);
            object = (ConversationActions)((BlockingCallback)object).get();
            if (object != null) {
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error reporting selection event.", remoteException);
        }
        return this.mFallback.suggestConversationActions(request);
    }

    @Override
    public TextSelection suggestSelection(TextSelection.Request request) {
        Preconditions.checkNotNull(request);
        TextClassifier.Utils.checkMainThread();
        try {
            request.setCallingPackageName(this.mPackageName);
            Object object = new BlockingCallback("textselection");
            this.mManagerService.onSuggestSelection(this.mSessionId, request, (ITextClassifierCallback)object);
            object = (TextSelection)((BlockingCallback)object).get();
            if (object != null) {
                return object;
            }
        }
        catch (RemoteException remoteException) {
            Log.e(LOG_TAG, "Error suggesting selection for text. Using fallback.", remoteException);
        }
        return this.mFallback.suggestSelection(request);
    }

    private static final class BlockingCallback<T extends Parcelable>
    extends ITextClassifierCallback.Stub {
        private final ResponseReceiver<T> mReceiver;

        BlockingCallback(String string2) {
            this.mReceiver = new ResponseReceiver(string2);
        }

        public T get() {
            return (T)((Parcelable)this.mReceiver.get());
        }

        @Override
        public void onFailure() {
            this.mReceiver.onFailure();
        }

        @Override
        public void onSuccess(Bundle bundle) {
            this.mReceiver.onSuccess(TextClassifierService.getResponse(bundle));
        }
    }

    private static final class ResponseReceiver<T> {
        private final CountDownLatch mLatch = new CountDownLatch(1);
        private final String mName;
        private T mResponse;

        private ResponseReceiver(String string2) {
            this.mName = string2;
        }

        public T get() {
            if (Looper.myLooper() != Looper.getMainLooper()) {
                try {
                    if (!this.mLatch.await(2L, TimeUnit.SECONDS)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Timeout in ResponseReceiver.get(): ");
                        stringBuilder.append(this.mName);
                        Log.w(SystemTextClassifier.LOG_TAG, stringBuilder.toString());
                    }
                }
                catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Interrupted during ResponseReceiver.get(): ");
                    stringBuilder.append(this.mName);
                    Log.e(SystemTextClassifier.LOG_TAG, stringBuilder.toString(), interruptedException);
                }
            }
            return this.mResponse;
        }

        public void onFailure() {
            Log.e(SystemTextClassifier.LOG_TAG, "Request failed.", null);
            this.mLatch.countDown();
        }

        public void onSuccess(T t) {
            this.mResponse = t;
            this.mLatch.countDown();
        }
    }

}

