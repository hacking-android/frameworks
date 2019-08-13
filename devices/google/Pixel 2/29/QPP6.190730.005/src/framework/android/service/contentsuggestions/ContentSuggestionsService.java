/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.contentsuggestions.-$
 *  android.service.contentsuggestions.-$$Lambda
 *  android.service.contentsuggestions.-$$Lambda$5oRtA6f92le979Nv8-bd2We4x10
 *  android.service.contentsuggestions.-$$Lambda$Mv-op4AGm9iWERwfXEFnqOVKWt0
 *  android.service.contentsuggestions.-$$Lambda$XFxerYS8emT_xgiGwwUrQtqnPnc
 *  android.service.contentsuggestions.-$$Lambda$yZSFRdNS_6TrQJ8NQKXAv0kSKzk
 */
package android.service.contentsuggestions;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.contentsuggestions.ClassificationsRequest;
import android.app.contentsuggestions.ContentClassification;
import android.app.contentsuggestions.ContentSelection;
import android.app.contentsuggestions.ContentSuggestionsManager;
import android.app.contentsuggestions.IClassificationsCallback;
import android.app.contentsuggestions.ISelectionsCallback;
import android.app.contentsuggestions.SelectionsRequest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.service.contentsuggestions.-$;
import android.service.contentsuggestions.IContentSuggestionsService;
import android.service.contentsuggestions._$$Lambda$5oRtA6f92le979Nv8_bd2We4x10;
import android.service.contentsuggestions._$$Lambda$ContentSuggestionsService$Cq6WuwbJQLqgS0UnqLBYUMft1GM;
import android.service.contentsuggestions._$$Lambda$ContentSuggestionsService$EMLezZyRGdfK3m_N1TAvrHKUEII;
import android.service.contentsuggestions._$$Lambda$Mv_op4AGm9iWERwfXEFnqOVKWt0;
import android.service.contentsuggestions._$$Lambda$XFxerYS8emT_xgiGwwUrQtqnPnc;
import android.service.contentsuggestions._$$Lambda$yZSFRdNS_6TrQJ8NQKXAv0kSKzk;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.List;

@SystemApi
public abstract class ContentSuggestionsService
extends Service {
    public static final String SERVICE_INTERFACE = "android.service.contentsuggestions.ContentSuggestionsService";
    private static final String TAG = ContentSuggestionsService.class.getSimpleName();
    private Handler mHandler;
    private final IContentSuggestionsService mInterface = new IContentSuggestionsService.Stub(){

        @Override
        public void classifyContentSelections(ClassificationsRequest classificationsRequest, IClassificationsCallback iClassificationsCallback) {
            Handler handler = ContentSuggestionsService.this.mHandler;
            -$.Lambda.5oRtA6f92le979Nv8-bd2We4x10 oRtA6f92le979Nv8-bd2We4x10 = _$$Lambda$5oRtA6f92le979Nv8_bd2We4x10.INSTANCE;
            ContentSuggestionsService contentSuggestionsService = ContentSuggestionsService.this;
            handler.sendMessage(PooledLambda.obtainMessage(oRtA6f92le979Nv8-bd2We4x10, contentSuggestionsService, classificationsRequest, contentSuggestionsService.wrapClassificationCallback(iClassificationsCallback)));
        }

        @Override
        public void notifyInteraction(String string2, Bundle bundle) {
            ContentSuggestionsService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$XFxerYS8emT_xgiGwwUrQtqnPnc.INSTANCE, ContentSuggestionsService.this, string2, bundle));
        }

        @Override
        public void provideContextImage(int n, GraphicBuffer graphicBuffer, int n2, Bundle bundle) {
            Object object = null;
            if (graphicBuffer != null) {
                Bitmap bitmap = null;
                object = bitmap;
                if (n2 >= 0) {
                    object = bitmap;
                    if (n2 < ColorSpace.Named.values().length) {
                        object = ColorSpace.get(ColorSpace.Named.values()[n2]);
                    }
                }
                object = Bitmap.wrapHardwareBuffer(graphicBuffer, object);
            }
            ContentSuggestionsService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$Mv_op4AGm9iWERwfXEFnqOVKWt0.INSTANCE, ContentSuggestionsService.this, n, object, bundle));
        }

        @Override
        public void suggestContentSelections(SelectionsRequest selectionsRequest, ISelectionsCallback iSelectionsCallback) {
            Handler handler = ContentSuggestionsService.this.mHandler;
            -$.Lambda.yZSFRdNS_6TrQJ8NQKXAv0kSKzk yZSFRdNS_6TrQJ8NQKXAv0kSKzk2 = _$$Lambda$yZSFRdNS_6TrQJ8NQKXAv0kSKzk.INSTANCE;
            ContentSuggestionsService contentSuggestionsService = ContentSuggestionsService.this;
            handler.sendMessage(PooledLambda.obtainMessage(yZSFRdNS_6TrQJ8NQKXAv0kSKzk2, contentSuggestionsService, selectionsRequest, contentSuggestionsService.wrapSelectionsCallback(iSelectionsCallback)));
        }
    };

    static /* synthetic */ void lambda$wrapClassificationCallback$1(IClassificationsCallback object, int n, List object2) {
        try {
            object.onContentClassificationsAvailable(n, (List<ContentClassification>)object2);
        }
        catch (RemoteException remoteException) {
            object2 = TAG;
            object = new StringBuilder();
            ((StringBuilder)object).append("Error sending result: ");
            ((StringBuilder)object).append(remoteException);
            Slog.e((String)object2, ((StringBuilder)object).toString());
        }
    }

    static /* synthetic */ void lambda$wrapSelectionsCallback$0(ISelectionsCallback object, int n, List list) {
        try {
            object.onContentSelectionsAvailable(n, list);
        }
        catch (RemoteException remoteException) {
            object = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Error sending result: ");
            stringBuilder.append(remoteException);
            Slog.e((String)object, stringBuilder.toString());
        }
    }

    private ContentSuggestionsManager.ClassificationsCallback wrapClassificationCallback(IClassificationsCallback iClassificationsCallback) {
        return new _$$Lambda$ContentSuggestionsService$EMLezZyRGdfK3m_N1TAvrHKUEII(iClassificationsCallback);
    }

    private ContentSuggestionsManager.SelectionsCallback wrapSelectionsCallback(ISelectionsCallback iSelectionsCallback) {
        return new _$$Lambda$ContentSuggestionsService$Cq6WuwbJQLqgS0UnqLBYUMft1GM(iSelectionsCallback);
    }

    @Override
    public final IBinder onBind(Intent intent) {
        if (SERVICE_INTERFACE.equals(intent.getAction())) {
            return this.mInterface.asBinder();
        }
        String string2 = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tried to bind to wrong intent (should be android.service.contentsuggestions.ContentSuggestionsService: ");
        stringBuilder.append(intent);
        Log.w(string2, stringBuilder.toString());
        return null;
    }

    public abstract void onClassifyContentSelections(ClassificationsRequest var1, ContentSuggestionsManager.ClassificationsCallback var2);

    @Override
    public void onCreate() {
        super.onCreate();
        this.mHandler = new Handler(Looper.getMainLooper(), null, true);
    }

    public abstract void onNotifyInteraction(String var1, Bundle var2);

    public abstract void onProcessContextImage(int var1, Bitmap var2, Bundle var3);

    public abstract void onSuggestContentSelections(SelectionsRequest var1, ContentSuggestionsManager.SelectionsCallback var2);

}

