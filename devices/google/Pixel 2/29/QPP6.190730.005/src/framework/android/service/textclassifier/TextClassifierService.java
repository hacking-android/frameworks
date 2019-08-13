/*
 * Decompiled with CFR 0.145.
 */
package android.service.textclassifier;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.textclassifier.ITextClassifierCallback;
import android.service.textclassifier.ITextClassifierService;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$LziW7ahHkWlZlAFekrEQR96QofM;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$Xkudza2Bh6W4NodH1DO_FiRgfuM;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$_Nsl56ysLPoVPJ4Gu0VUwYCh4wE;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$bqy_LY0V0g3pGHWd_N7ARYwQWLY;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$fhIvecFpMXNthJWnvX_RvpNrPFA;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$lcpBFMoy_hRkYQ42cWViBMbNnMk;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$mKOXH9oGuUFyRz_Oo15GnAPhABs;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$oecuM3n2XJWuEPg_O0hSZtoF0ls;
import android.service.textclassifier._$$Lambda$TextClassifierService$1$suS99xMAl9SLES4WhRmaub16wIc;
import android.service.textclassifier._$$Lambda$TextClassifierService$9kfVuo6FJ1uQiU277_n9JgliEEc;
import android.service.textclassifier._$$Lambda$TextClassifierService$OMrgO9sL3mlBJfpfxbmg7ieGoWk;
import android.text.TextUtils;
import android.util.Slog;
import android.view.textclassifier.ConversationActions;
import android.view.textclassifier.SelectionEvent;
import android.view.textclassifier.TextClassification;
import android.view.textclassifier.TextClassificationContext;
import android.view.textclassifier.TextClassificationManager;
import android.view.textclassifier.TextClassificationSessionId;
import android.view.textclassifier.TextClassifier;
import android.view.textclassifier.TextClassifierEvent;
import android.view.textclassifier.TextLanguage;
import android.view.textclassifier.TextLinks;
import android.view.textclassifier.TextSelection;
import com.android.internal.util.Preconditions;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SystemApi
public abstract class TextClassifierService
extends Service {
    private static final String KEY_RESULT = "key_result";
    private static final String LOG_TAG = "TextClassifierService";
    public static final String SERVICE_INTERFACE = "android.service.textclassifier.TextClassifierService";
    private final ITextClassifierService.Stub mBinder = new ITextClassifierService.Stub(){
        private final CancellationSignal mCancellationSignal = new CancellationSignal();

        public /* synthetic */ void lambda$onClassifyText$1$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onClassifyText(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback<TextClassification>(iTextClassifierCallback));
        }

        public /* synthetic */ void lambda$onCreateTextClassificationSession$7$TextClassifierService$1(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
            TextClassifierService.this.onCreateTextClassificationSession(textClassificationContext, textClassificationSessionId);
        }

        public /* synthetic */ void lambda$onDestroyTextClassificationSession$8$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId) {
            TextClassifierService.this.onDestroyTextClassificationSession(textClassificationSessionId);
        }

        public /* synthetic */ void lambda$onDetectLanguage$5$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onDetectLanguage(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback<TextLanguage>(iTextClassifierCallback));
        }

        public /* synthetic */ void lambda$onGenerateLinks$2$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onGenerateLinks(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback<TextLinks>(iTextClassifierCallback));
        }

        public /* synthetic */ void lambda$onSelectionEvent$3$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) {
            TextClassifierService.this.onSelectionEvent(textClassificationSessionId, selectionEvent);
        }

        public /* synthetic */ void lambda$onSuggestConversationActions$6$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onSuggestConversationActions(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback<ConversationActions>(iTextClassifierCallback));
        }

        public /* synthetic */ void lambda$onSuggestSelection$0$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) {
            TextClassifierService.this.onSuggestSelection(textClassificationSessionId, request, this.mCancellationSignal, new ProxyCallback<TextSelection>(iTextClassifierCallback));
        }

        public /* synthetic */ void lambda$onTextClassifierEvent$4$TextClassifierService$1(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) {
            TextClassifierService.this.onTextClassifierEvent(textClassificationSessionId, textClassifierEvent);
        }

        @Override
        public void onClassifyText(TextClassificationSessionId textClassificationSessionId, TextClassification.Request request, ITextClassifierCallback iTextClassifierCallback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$LziW7ahHkWlZlAFekrEQR96QofM(this, textClassificationSessionId, request, iTextClassifierCallback));
        }

        @Override
        public void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
            Preconditions.checkNotNull(textClassificationContext);
            Preconditions.checkNotNull(textClassificationSessionId);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$oecuM3n2XJWuEPg_O0hSZtoF0ls(this, textClassificationContext, textClassificationSessionId));
        }

        @Override
        public void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) {
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$fhIvecFpMXNthJWnvX_RvpNrPFA(this, textClassificationSessionId));
        }

        @Override
        public void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, ITextClassifierCallback iTextClassifierCallback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$lcpBFMoy_hRkYQ42cWViBMbNnMk(this, textClassificationSessionId, request, iTextClassifierCallback));
        }

        @Override
        public void onGenerateLinks(TextClassificationSessionId textClassificationSessionId, TextLinks.Request request, ITextClassifierCallback iTextClassifierCallback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$suS99xMAl9SLES4WhRmaub16wIc(this, textClassificationSessionId, request, iTextClassifierCallback));
        }

        @Override
        public void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) {
            Preconditions.checkNotNull(selectionEvent);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$_Nsl56ysLPoVPJ4Gu0VUwYCh4wE(this, textClassificationSessionId, selectionEvent));
        }

        @Override
        public void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, ITextClassifierCallback iTextClassifierCallback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$Xkudza2Bh6W4NodH1DO_FiRgfuM(this, textClassificationSessionId, request, iTextClassifierCallback));
        }

        @Override
        public void onSuggestSelection(TextClassificationSessionId textClassificationSessionId, TextSelection.Request request, ITextClassifierCallback iTextClassifierCallback) {
            Preconditions.checkNotNull(request);
            Preconditions.checkNotNull(iTextClassifierCallback);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$mKOXH9oGuUFyRz_Oo15GnAPhABs(this, textClassificationSessionId, request, iTextClassifierCallback));
        }

        @Override
        public void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) {
            Preconditions.checkNotNull(textClassifierEvent);
            TextClassifierService.this.mMainThreadHandler.post(new _$$Lambda$TextClassifierService$1$bqy_LY0V0g3pGHWd_N7ARYwQWLY(this, textClassificationSessionId, textClassifierEvent));
        }
    };
    private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper(), null, true);
    private final ExecutorService mSingleThreadExecutor = Executors.newSingleThreadExecutor();

    public static TextClassifier getDefaultTextClassifierImplementation(Context object) {
        if ((object = ((Context)object).getSystemService(TextClassificationManager.class)) != null) {
            return ((TextClassificationManager)object).getTextClassifier(0);
        }
        return TextClassifier.NO_OP;
    }

    public static <T extends Parcelable> T getResponse(Bundle bundle) {
        return bundle.getParcelable(KEY_RESULT);
    }

    public static ComponentName getServiceComponentName(Context object) {
        String string2 = ((Context)object).getPackageManager().getSystemTextClassifierPackageName();
        if (TextUtils.isEmpty(string2)) {
            Slog.d(LOG_TAG, "No configured system TextClassifierService");
            return null;
        }
        Parcelable parcelable = new Intent(SERVICE_INTERFACE).setPackage(string2);
        parcelable = ((Context)object).getPackageManager().resolveService((Intent)parcelable, 1048576);
        if (parcelable != null && ((ResolveInfo)parcelable).serviceInfo != null) {
            object = ((ResolveInfo)parcelable).serviceInfo;
            if ("android.permission.BIND_TEXTCLASSIFIER_SERVICE".equals(((ServiceInfo)object).permission)) {
                return ((ComponentInfo)object).getComponentName();
            }
            Slog.w(LOG_TAG, String.format("Service %s should require %s permission. Found %s permission", ((ComponentInfo)object).getComponentName(), "android.permission.BIND_TEXTCLASSIFIER_SERVICE", ((ServiceInfo)object).permission));
            return null;
        }
        Slog.w(LOG_TAG, String.format("Package or service not found in package %s for user %d", string2, ((Context)object).getUserId()));
        return null;
    }

    @Deprecated
    public final TextClassifier getLocalTextClassifier() {
        return TextClassifierService.getDefaultTextClassifierImplementation(this);
    }

    public /* synthetic */ void lambda$onDetectLanguage$0$TextClassifierService(Callback callback, TextLanguage.Request request) {
        callback.onSuccess(this.getLocalTextClassifier().detectLanguage(request));
    }

    public /* synthetic */ void lambda$onSuggestConversationActions$1$TextClassifierService(Callback callback, ConversationActions.Request request) {
        callback.onSuccess(this.getLocalTextClassifier().suggestConversationActions(request));
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mBinder;
        }
        return null;
    }

    public abstract void onClassifyText(TextClassificationSessionId var1, TextClassification.Request var2, CancellationSignal var3, Callback<TextClassification> var4);

    public void onCreateTextClassificationSession(TextClassificationContext textClassificationContext, TextClassificationSessionId textClassificationSessionId) {
    }

    public void onDestroyTextClassificationSession(TextClassificationSessionId textClassificationSessionId) {
    }

    public void onDetectLanguage(TextClassificationSessionId textClassificationSessionId, TextLanguage.Request request, CancellationSignal cancellationSignal, Callback<TextLanguage> callback) {
        this.mSingleThreadExecutor.submit(new _$$Lambda$TextClassifierService$9kfVuo6FJ1uQiU277_n9JgliEEc(this, callback, request));
    }

    public abstract void onGenerateLinks(TextClassificationSessionId var1, TextLinks.Request var2, CancellationSignal var3, Callback<TextLinks> var4);

    @Deprecated
    public void onSelectionEvent(TextClassificationSessionId textClassificationSessionId, SelectionEvent selectionEvent) {
    }

    public void onSuggestConversationActions(TextClassificationSessionId textClassificationSessionId, ConversationActions.Request request, CancellationSignal cancellationSignal, Callback<ConversationActions> callback) {
        this.mSingleThreadExecutor.submit(new _$$Lambda$TextClassifierService$OMrgO9sL3mlBJfpfxbmg7ieGoWk(this, callback, request));
    }

    public abstract void onSuggestSelection(TextClassificationSessionId var1, TextSelection.Request var2, CancellationSignal var3, Callback<TextSelection> var4);

    public void onTextClassifierEvent(TextClassificationSessionId textClassificationSessionId, TextClassifierEvent textClassifierEvent) {
    }

    public static interface Callback<T> {
        public void onFailure(CharSequence var1);

        public void onSuccess(T var1);
    }

    private static final class ProxyCallback<T extends Parcelable>
    implements Callback<T> {
        private WeakReference<ITextClassifierCallback> mTextClassifierCallback;

        private ProxyCallback(ITextClassifierCallback iTextClassifierCallback) {
            this.mTextClassifierCallback = new WeakReference<ITextClassifierCallback>(Preconditions.checkNotNull(iTextClassifierCallback));
        }

        @Override
        public void onFailure(CharSequence object) {
            object = (ITextClassifierCallback)this.mTextClassifierCallback.get();
            if (object == null) {
                return;
            }
            try {
                object.onFailure();
            }
            catch (RemoteException remoteException) {
                Slog.d(TextClassifierService.LOG_TAG, "Error calling callback");
            }
        }

        @Override
        public void onSuccess(T t) {
            ITextClassifierCallback iTextClassifierCallback = (ITextClassifierCallback)this.mTextClassifierCallback.get();
            if (iTextClassifierCallback == null) {
                return;
            }
            try {
                Bundle bundle = new Bundle(1);
                bundle.putParcelable(TextClassifierService.KEY_RESULT, (Parcelable)t);
                iTextClassifierCallback.onSuccess(bundle);
            }
            catch (RemoteException remoteException) {
                Slog.d(TextClassifierService.LOG_TAG, "Error calling callback");
            }
        }
    }

}

