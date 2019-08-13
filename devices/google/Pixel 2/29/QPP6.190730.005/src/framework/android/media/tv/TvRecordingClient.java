/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.annotation.SystemApi;
import android.content.Context;
import android.media.tv.TvInputManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import java.util.ArrayDeque;
import java.util.Queue;

public class TvRecordingClient {
    private static final boolean DEBUG = false;
    private static final String TAG = "TvRecordingClient";
    private final RecordingCallback mCallback;
    private final Handler mHandler;
    private boolean mIsRecordingStarted;
    private boolean mIsTuned;
    private final Queue<Pair<String, Bundle>> mPendingAppPrivateCommands = new ArrayDeque<Pair<String, Bundle>>();
    private TvInputManager.Session mSession;
    private MySessionCallback mSessionCallback;
    private final TvInputManager mTvInputManager;

    public TvRecordingClient(Context context, String string2, RecordingCallback recordingCallback, Handler handler) {
        this.mCallback = recordingCallback;
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        this.mHandler = handler;
        this.mTvInputManager = (TvInputManager)context.getSystemService("tv_input");
    }

    private void resetInternal() {
        this.mSessionCallback = null;
        this.mPendingAppPrivateCommands.clear();
        TvInputManager.Session session = this.mSession;
        if (session != null) {
            session.release();
            this.mSession = null;
        }
    }

    public void release() {
        this.resetInternal();
    }

    public void sendAppPrivateCommand(String string2, Bundle bundle) {
        if (!TextUtils.isEmpty(string2)) {
            Object object = this.mSession;
            if (object != null) {
                ((TvInputManager.Session)object).sendAppPrivateCommand(string2, bundle);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("sendAppPrivateCommand - session not yet created (action \"");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("\" pending)");
                Log.w(TAG, ((StringBuilder)object).toString());
                this.mPendingAppPrivateCommands.add(Pair.create(string2, bundle));
            }
            return;
        }
        throw new IllegalArgumentException("action cannot be null or an empty string");
    }

    public void startRecording(Uri uri) {
        if (this.mIsTuned) {
            TvInputManager.Session session = this.mSession;
            if (session != null) {
                session.startRecording(uri);
                this.mIsRecordingStarted = true;
            }
            return;
        }
        throw new IllegalStateException("startRecording failed - not yet tuned");
    }

    public void stopRecording() {
        TvInputManager.Session session;
        if (!this.mIsRecordingStarted) {
            Log.w(TAG, "stopRecording failed - recording not yet started");
        }
        if ((session = this.mSession) != null) {
            session.stopRecording();
        }
    }

    public void tune(String string2, Uri uri) {
        this.tune(string2, uri, null);
    }

    public void tune(String object, Uri object2, Bundle bundle) {
        if (!TextUtils.isEmpty((CharSequence)object)) {
            if (!this.mIsRecordingStarted) {
                MySessionCallback mySessionCallback = this.mSessionCallback;
                if (mySessionCallback != null && TextUtils.equals(mySessionCallback.mInputId, (CharSequence)object)) {
                    object = this.mSession;
                    if (object != null) {
                        ((TvInputManager.Session)object).tune((Uri)object2, bundle);
                    } else {
                        object = this.mSessionCallback;
                        ((MySessionCallback)object).mChannelUri = object2;
                        ((MySessionCallback)object).mConnectionParams = bundle;
                    }
                } else {
                    this.resetInternal();
                    this.mSessionCallback = new MySessionCallback((String)object, (Uri)object2, bundle);
                    object2 = this.mTvInputManager;
                    if (object2 != null) {
                        ((TvInputManager)object2).createRecordingSession((String)object, this.mSessionCallback, this.mHandler);
                    }
                }
                return;
            }
            throw new IllegalStateException("tune failed - recording already started");
        }
        throw new IllegalArgumentException("inputId cannot be null or an empty string");
    }

    private class MySessionCallback
    extends TvInputManager.SessionCallback {
        Uri mChannelUri;
        Bundle mConnectionParams;
        final String mInputId;

        MySessionCallback(String string2, Uri uri, Bundle bundle) {
            this.mInputId = string2;
            this.mChannelUri = uri;
            this.mConnectionParams = bundle;
        }

        @Override
        public void onError(TvInputManager.Session session, int n) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onError - session not created");
                return;
            }
            TvRecordingClient.this.mCallback.onError(n);
        }

        @Override
        public void onRecordingStopped(TvInputManager.Session session, Uri uri) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onRecordingStopped - session not created");
                return;
            }
            TvRecordingClient.this.mIsRecordingStarted = false;
            TvRecordingClient.this.mCallback.onRecordingStopped(uri);
        }

        @Override
        public void onSessionCreated(TvInputManager.Session object2) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionCreated - session already created");
                if (object2 != null) {
                    ((TvInputManager.Session)object2).release();
                }
                return;
            }
            TvRecordingClient.this.mSession = (TvInputManager.Session)object2;
            if (object2 != null) {
                for (Object object2 : TvRecordingClient.this.mPendingAppPrivateCommands) {
                    TvRecordingClient.this.mSession.sendAppPrivateCommand((String)((Pair)object2).first, (Bundle)((Pair)object2).second);
                }
                TvRecordingClient.this.mPendingAppPrivateCommands.clear();
                TvRecordingClient.this.mSession.tune(this.mChannelUri, this.mConnectionParams);
            } else {
                TvRecordingClient.this.mSessionCallback = null;
                if (TvRecordingClient.this.mCallback != null) {
                    TvRecordingClient.this.mCallback.onConnectionFailed(this.mInputId);
                }
            }
        }

        @Override
        public void onSessionEvent(TvInputManager.Session session, String string2, Bundle bundle) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionEvent - session not created");
                return;
            }
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onEvent(this.mInputId, string2, bundle);
            }
        }

        @Override
        public void onSessionReleased(TvInputManager.Session session) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onSessionReleased - session not created");
                return;
            }
            TvRecordingClient.this.mIsTuned = false;
            TvRecordingClient.this.mIsRecordingStarted = false;
            TvRecordingClient.this.mSessionCallback = null;
            TvRecordingClient.this.mSession = null;
            if (TvRecordingClient.this.mCallback != null) {
                TvRecordingClient.this.mCallback.onDisconnected(this.mInputId);
            }
        }

        @Override
        void onTuned(TvInputManager.Session session, Uri uri) {
            if (this != TvRecordingClient.this.mSessionCallback) {
                Log.w(TvRecordingClient.TAG, "onTuned - session not created");
                return;
            }
            TvRecordingClient.this.mIsTuned = true;
            TvRecordingClient.this.mCallback.onTuned(uri);
        }
    }

    public static abstract class RecordingCallback {
        public void onConnectionFailed(String string2) {
        }

        public void onDisconnected(String string2) {
        }

        public void onError(int n) {
        }

        @SystemApi
        public void onEvent(String string2, String string3, Bundle bundle) {
        }

        public void onRecordingStopped(Uri uri) {
        }

        public void onTuned(Uri uri) {
        }
    }

}

