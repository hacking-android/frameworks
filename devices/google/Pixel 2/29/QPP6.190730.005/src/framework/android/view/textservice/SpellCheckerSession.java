/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.view.textservice;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.textservice.SentenceSuggestionsInfo;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.SuggestionsInfo;
import android.view.textservice.TextInfo;
import android.view.textservice.TextServicesManager;
import com.android.internal.textservice.ISpellCheckerSession;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesSessionListener;
import dalvik.system.CloseGuard;
import java.util.LinkedList;
import java.util.Queue;

public class SpellCheckerSession {
    private static final boolean DBG = false;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE = 1;
    private static final int MSG_ON_GET_SUGGESTION_MULTIPLE_FOR_SENTENCE = 2;
    public static final String SERVICE_META_DATA = "android.view.textservice.scs";
    private static final String TAG = SpellCheckerSession.class.getSimpleName();
    private final CloseGuard mGuard = CloseGuard.get();
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n == 2) {
                    SpellCheckerSession.this.handleOnGetSentenceSuggestionsMultiple((SentenceSuggestionsInfo[])message.obj);
                }
            } else {
                SpellCheckerSession.this.handleOnGetSuggestionsMultiple((SuggestionsInfo[])message.obj);
            }
        }
    };
    private final InternalListener mInternalListener;
    private final SpellCheckerInfo mSpellCheckerInfo;
    @UnsupportedAppUsage
    private final SpellCheckerSessionListener mSpellCheckerSessionListener;
    private final SpellCheckerSessionListenerImpl mSpellCheckerSessionListenerImpl;
    private final TextServicesManager mTextServicesManager;

    public SpellCheckerSession(SpellCheckerInfo spellCheckerInfo, TextServicesManager textServicesManager, SpellCheckerSessionListener spellCheckerSessionListener) {
        if (spellCheckerInfo != null && spellCheckerSessionListener != null && textServicesManager != null) {
            this.mSpellCheckerInfo = spellCheckerInfo;
            this.mSpellCheckerSessionListenerImpl = new SpellCheckerSessionListenerImpl(this.mHandler);
            this.mInternalListener = new InternalListener(this.mSpellCheckerSessionListenerImpl);
            this.mTextServicesManager = textServicesManager;
            this.mSpellCheckerSessionListener = spellCheckerSessionListener;
            this.mGuard.open("finishSession");
            return;
        }
        throw new NullPointerException();
    }

    private void handleOnGetSentenceSuggestionsMultiple(SentenceSuggestionsInfo[] arrsentenceSuggestionsInfo) {
        this.mSpellCheckerSessionListener.onGetSentenceSuggestions(arrsentenceSuggestionsInfo);
    }

    private void handleOnGetSuggestionsMultiple(SuggestionsInfo[] arrsuggestionsInfo) {
        this.mSpellCheckerSessionListener.onGetSuggestions(arrsuggestionsInfo);
    }

    public void cancel() {
        this.mSpellCheckerSessionListenerImpl.cancel();
    }

    public void close() {
        this.mGuard.close();
        this.mSpellCheckerSessionListenerImpl.close();
        this.mTextServicesManager.finishSpellCheckerService(this.mSpellCheckerSessionListenerImpl);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mGuard != null) {
                this.mGuard.warnIfOpen();
                this.close();
            }
            return;
        }
        finally {
            super.finalize();
        }
    }

    public void getSentenceSuggestions(TextInfo[] arrtextInfo, int n) {
        this.mSpellCheckerSessionListenerImpl.getSentenceSuggestionsMultiple(arrtextInfo, n);
    }

    public SpellCheckerInfo getSpellChecker() {
        return this.mSpellCheckerInfo;
    }

    public ISpellCheckerSessionListener getSpellCheckerSessionListener() {
        return this.mSpellCheckerSessionListenerImpl;
    }

    @Deprecated
    public void getSuggestions(TextInfo textInfo, int n) {
        this.getSuggestions(new TextInfo[]{textInfo}, n, false);
    }

    @Deprecated
    public void getSuggestions(TextInfo[] arrtextInfo, int n, boolean bl) {
        this.mSpellCheckerSessionListenerImpl.getSuggestionsMultiple(arrtextInfo, n, bl);
    }

    public ITextServicesSessionListener getTextServicesSessionListener() {
        return this.mInternalListener;
    }

    public boolean isSessionDisconnected() {
        return this.mSpellCheckerSessionListenerImpl.isDisconnected();
    }

    private static final class InternalListener
    extends ITextServicesSessionListener.Stub {
        private final SpellCheckerSessionListenerImpl mParentSpellCheckerSessionListenerImpl;

        public InternalListener(SpellCheckerSessionListenerImpl spellCheckerSessionListenerImpl) {
            this.mParentSpellCheckerSessionListenerImpl = spellCheckerSessionListenerImpl;
        }

        @Override
        public void onServiceConnected(ISpellCheckerSession iSpellCheckerSession) {
            this.mParentSpellCheckerSessionListenerImpl.onServiceConnected(iSpellCheckerSession);
        }
    }

    public static interface SpellCheckerSessionListener {
        public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] var1);

        public void onGetSuggestions(SuggestionsInfo[] var1);
    }

    private static final class SpellCheckerSessionListenerImpl
    extends ISpellCheckerSessionListener.Stub {
        private static final int STATE_CLOSED_AFTER_CONNECTION = 2;
        private static final int STATE_CLOSED_BEFORE_CONNECTION = 3;
        private static final int STATE_CONNECTED = 1;
        private static final int STATE_WAIT_CONNECTION = 0;
        private static final int TASK_CANCEL = 1;
        private static final int TASK_CLOSE = 3;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE = 2;
        private static final int TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE = 4;
        private Handler mAsyncHandler;
        private Handler mHandler;
        private ISpellCheckerSession mISpellCheckerSession;
        private final Queue<SpellCheckerParams> mPendingTasks = new LinkedList<SpellCheckerParams>();
        private int mState = 0;
        private HandlerThread mThread;

        public SpellCheckerSessionListenerImpl(Handler handler) {
            this.mHandler = handler;
        }

        private void processCloseLocked() {
            this.mISpellCheckerSession = null;
            Object object = this.mThread;
            if (object != null) {
                ((HandlerThread)object).quit();
            }
            this.mHandler = null;
            this.mPendingTasks.clear();
            this.mThread = null;
            this.mAsyncHandler = null;
            int n = this.mState;
            if (n != 0) {
                if (n != 1) {
                    object = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("processCloseLocked is called unexpectedly. mState=");
                    stringBuilder.append(SpellCheckerSessionListenerImpl.stateToString(this.mState));
                    Log.e((String)object, stringBuilder.toString());
                } else {
                    this.mState = 2;
                }
            } else {
                this.mState = 3;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void processOrEnqueueTask(SpellCheckerParams spellCheckerParams) {
            synchronized (this) {
                if (spellCheckerParams.mWhat == 3 && (this.mState == 2 || this.mState == 3)) {
                    return;
                }
                if (this.mState != 0 && this.mState != 1) {
                    String string2 = TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ignoring processOrEnqueueTask due to unexpected mState=");
                    stringBuilder.append(SpellCheckerSessionListenerImpl.stateToString(this.mState));
                    stringBuilder.append(" scp.mWhat=");
                    stringBuilder.append(SpellCheckerSessionListenerImpl.taskToString(spellCheckerParams.mWhat));
                    Log.e(string2, stringBuilder.toString());
                    return;
                }
                if (this.mState != 0) {
                    ISpellCheckerSession iSpellCheckerSession = this.mISpellCheckerSession;
                    // MONITOREXIT [2, 7] lbl22 : MonitorExitStatement: MONITOREXIT : this
                    this.processTask(iSpellCheckerSession, spellCheckerParams, false);
                    return;
                }
                if (spellCheckerParams.mWhat == 3) {
                    this.processCloseLocked();
                    return;
                }
                SpellCheckerParams spellCheckerParams2 = null;
                SpellCheckerParams spellCheckerParams3 = null;
                if (spellCheckerParams.mWhat == 1) {
                    do {
                        spellCheckerParams2 = spellCheckerParams3;
                        if (this.mPendingTasks.isEmpty()) break;
                        spellCheckerParams2 = this.mPendingTasks.poll();
                        if (spellCheckerParams2.mWhat != 3) continue;
                        spellCheckerParams3 = spellCheckerParams2;
                    } while (true);
                }
                this.mPendingTasks.offer(spellCheckerParams);
                if (spellCheckerParams2 != null) {
                    this.mPendingTasks.offer(spellCheckerParams2);
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void processTask(ISpellCheckerSession object, SpellCheckerParams spellCheckerParams, boolean bl) {
            Object object2;
            if (!bl && (object2 = this.mAsyncHandler) != null) {
                spellCheckerParams.mSession = object;
                ((Handler)object2).sendMessage(Message.obtain((Handler)object2, 1, spellCheckerParams));
            } else {
                int n = spellCheckerParams.mWhat;
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            if (n == 4) {
                                try {
                                    object.onGetSentenceSuggestionsMultiple(spellCheckerParams.mTextInfos, spellCheckerParams.mSuggestionsLimit);
                                }
                                catch (RemoteException remoteException) {
                                    object = TAG;
                                    StringBuilder stringBuilder = new StringBuilder();
                                    stringBuilder.append("Failed to get suggestions ");
                                    stringBuilder.append(remoteException);
                                    Log.e((String)object, stringBuilder.toString());
                                }
                            }
                        } else {
                            try {
                                object.onClose();
                            }
                            catch (RemoteException remoteException) {
                                object = TAG;
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Failed to close ");
                                ((StringBuilder)object2).append(remoteException);
                                Log.e((String)object, ((StringBuilder)object2).toString());
                            }
                        }
                    } else {
                        try {
                            object.onGetSuggestionsMultiple(spellCheckerParams.mTextInfos, spellCheckerParams.mSuggestionsLimit, spellCheckerParams.mSequentialWords);
                        }
                        catch (RemoteException remoteException) {
                            object = TAG;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Failed to get suggestions ");
                            stringBuilder.append(remoteException);
                            Log.e((String)object, stringBuilder.toString());
                        }
                    }
                } else {
                    try {
                        object.onCancel();
                    }
                    catch (RemoteException remoteException) {
                        String string2 = TAG;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Failed to cancel ");
                        ((StringBuilder)object2).append(remoteException);
                        Log.e(string2, ((StringBuilder)object2).toString());
                    }
                }
            }
            if (spellCheckerParams.mWhat != 3) return;
            synchronized (this) {
                this.processCloseLocked();
                return;
            }
        }

        private static String stateToString(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 3) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unexpected state=");
                            stringBuilder.append(n);
                            return stringBuilder.toString();
                        }
                        return "STATE_CLOSED_BEFORE_CONNECTION";
                    }
                    return "STATE_CLOSED_AFTER_CONNECTION";
                }
                return "STATE_CONNECTED";
            }
            return "STATE_WAIT_CONNECTION";
        }

        private static String taskToString(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unexpected task=");
                            stringBuilder.append(n);
                            return stringBuilder.toString();
                        }
                        return "TASK_GET_SUGGESTIONS_MULTIPLE_FOR_SENTENCE";
                    }
                    return "TASK_CLOSE";
                }
                return "TASK_GET_SUGGESTIONS_MULTIPLE";
            }
            return "TASK_CANCEL";
        }

        public void cancel() {
            this.processOrEnqueueTask(new SpellCheckerParams(1, null, 0, false));
        }

        public void close() {
            this.processOrEnqueueTask(new SpellCheckerParams(3, null, 0, false));
        }

        public void getSentenceSuggestionsMultiple(TextInfo[] arrtextInfo, int n) {
            this.processOrEnqueueTask(new SpellCheckerParams(4, arrtextInfo, n, false));
        }

        public void getSuggestionsMultiple(TextInfo[] arrtextInfo, int n, boolean bl) {
            this.processOrEnqueueTask(new SpellCheckerParams(2, arrtextInfo, n, bl));
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public boolean isDisconnected() {
            synchronized (this) {
                int n = this.mState;
                boolean bl = true;
                if (n == 1) return false;
                return bl;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onGetSentenceSuggestions(SentenceSuggestionsInfo[] arrsentenceSuggestionsInfo) {
            synchronized (this) {
                if (this.mHandler != null) {
                    this.mHandler.sendMessage(Message.obtain(this.mHandler, 2, arrsentenceSuggestionsInfo));
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onGetSuggestions(SuggestionsInfo[] arrsuggestionsInfo) {
            synchronized (this) {
                if (this.mHandler != null) {
                    this.mHandler.sendMessage(Message.obtain(this.mHandler, 1, arrsuggestionsInfo));
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onServiceConnected(ISpellCheckerSession object) {
            synchronized (this) {
                int n = this.mState;
                if (n != 0) {
                    if (n != 3) {
                        String string2 = TAG;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("ignoring onServiceConnected due to unexpected mState=");
                        ((StringBuilder)object).append(SpellCheckerSessionListenerImpl.stateToString(this.mState));
                        Log.e(string2, ((StringBuilder)object).toString());
                        return;
                    }
                    return;
                }
                if (object == null) {
                    Log.e(TAG, "ignoring onServiceConnected due to session=null");
                    return;
                }
                this.mISpellCheckerSession = object;
                if (object.asBinder() instanceof Binder && this.mThread == null) {
                    Handler handler = new HandlerThread("SpellCheckerSession", 10);
                    this.mThread = handler;
                    this.mThread.start();
                    handler = new Handler(this.mThread.getLooper()){

                        @Override
                        public void handleMessage(Message object) {
                            object = (SpellCheckerParams)((Message)object).obj;
                            SpellCheckerSessionListenerImpl.this.processTask(((SpellCheckerParams)object).mSession, (SpellCheckerParams)object, true);
                        }
                    };
                    this.mAsyncHandler = handler;
                }
                this.mState = 1;
                while (!this.mPendingTasks.isEmpty()) {
                    this.processTask((ISpellCheckerSession)object, this.mPendingTasks.poll(), false);
                }
                return;
            }
        }

        private static class SpellCheckerParams {
            public final boolean mSequentialWords;
            public ISpellCheckerSession mSession;
            public final int mSuggestionsLimit;
            public final TextInfo[] mTextInfos;
            public final int mWhat;

            public SpellCheckerParams(int n, TextInfo[] arrtextInfo, int n2, boolean bl) {
                this.mWhat = n;
                this.mTextInfos = arrtextInfo;
                this.mSuggestionsLimit = n2;
                this.mSequentialWords = bl;
            }
        }

    }

}

