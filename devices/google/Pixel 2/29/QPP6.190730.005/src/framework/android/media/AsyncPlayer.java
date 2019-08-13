/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.PlayerBase;
import android.net.Uri;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import java.io.Serializable;
import java.util.LinkedList;

public class AsyncPlayer {
    private static final int PLAY = 1;
    private static final int STOP = 2;
    private static final boolean mDebug = false;
    private final LinkedList<Command> mCmdQueue = new LinkedList();
    private MediaPlayer mPlayer;
    private int mState = 2;
    private String mTag;
    private Thread mThread;
    private PowerManager.WakeLock mWakeLock;

    public AsyncPlayer(String string2) {
        this.mTag = string2 != null ? string2 : "AsyncPlayer";
    }

    private void acquireWakeLock() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.acquire();
        }
    }

    private void enqueueLocked(Command command) {
        this.mCmdQueue.add(command);
        if (this.mThread == null) {
            this.acquireWakeLock();
            this.mThread = new Thread();
            this.mThread.start();
        }
    }

    private void releaseWakeLock() {
        PowerManager.WakeLock wakeLock = this.mWakeLock;
        if (wakeLock != null) {
            wakeLock.release();
        }
    }

    private void startSound(Command command) {
        block4 : {
            Object object = new MediaPlayer();
            ((MediaPlayer)object).setAudioAttributes(command.attributes);
            ((MediaPlayer)object).setDataSource(command.context, command.uri);
            ((MediaPlayer)object).setLooping(command.looping);
            ((MediaPlayer)object).prepare();
            ((MediaPlayer)object).start();
            if (this.mPlayer != null) {
                this.mPlayer.release();
            }
            this.mPlayer = object;
            long l = SystemClock.uptimeMillis() - command.requestTime;
            if (l <= 1000L) break block4;
            try {
                object = this.mTag;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Notification sound delayed by ");
                stringBuilder.append(l);
                stringBuilder.append("msecs");
                Log.w((String)object, stringBuilder.toString());
            }
            catch (Exception exception) {
                String string2 = this.mTag;
                object = new StringBuilder();
                ((StringBuilder)object).append("error loading sound for ");
                ((StringBuilder)object).append(command.uri);
                Log.w(string2, ((StringBuilder)object).toString(), exception);
            }
        }
    }

    public void play(Context context, Uri uri, boolean bl, int n) {
        PlayerBase.deprecateStreamTypeForPlayback(n, "AsyncPlayer", "play()");
        if (context != null && uri != null) {
            try {
                AudioAttributes.Builder builder = new AudioAttributes.Builder();
                this.play(context, uri, bl, builder.setInternalLegacyStreamType(n).build());
            }
            catch (IllegalArgumentException illegalArgumentException) {
                Log.e(this.mTag, "Call to deprecated AsyncPlayer.play() method caused:", illegalArgumentException);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void play(Context object, Uri uri, boolean bl, AudioAttributes audioAttributes) throws IllegalArgumentException {
        if (object != null && uri != null && audioAttributes != null) {
            Command command = new Command();
            command.requestTime = SystemClock.uptimeMillis();
            command.code = 1;
            command.context = object;
            command.uri = uri;
            command.looping = bl;
            command.attributes = audioAttributes;
            object = this.mCmdQueue;
            synchronized (object) {
                this.enqueueLocked(command);
                this.mState = 1;
                return;
            }
        }
        throw new IllegalArgumentException("Illegal null AsyncPlayer.play() argument");
    }

    @UnsupportedAppUsage
    public void setUsesWakeLock(Context object) {
        if (this.mWakeLock == null && this.mThread == null) {
            this.mWakeLock = ((PowerManager)((Context)object).getSystemService("power")).newWakeLock(1, this.mTag);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("assertion failed mWakeLock=");
        ((StringBuilder)object).append(this.mWakeLock);
        ((StringBuilder)object).append(" mThread=");
        ((StringBuilder)object).append(this.mThread);
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stop() {
        LinkedList<Command> linkedList = this.mCmdQueue;
        synchronized (linkedList) {
            if (this.mState != 2) {
                Command command = new Command();
                command.requestTime = SystemClock.uptimeMillis();
                command.code = 2;
                this.enqueueLocked(command);
                this.mState = 2;
            }
            return;
        }
    }

    private static final class Command {
        AudioAttributes attributes;
        int code;
        Context context;
        boolean looping;
        long requestTime;
        Uri uri;

        private Command() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ code=");
            stringBuilder.append(this.code);
            stringBuilder.append(" looping=");
            stringBuilder.append(this.looping);
            stringBuilder.append(" attr=");
            stringBuilder.append(this.attributes);
            stringBuilder.append(" uri=");
            stringBuilder.append(this.uri);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    private final class Thread
    extends java.lang.Thread {
        Thread() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AsyncPlayer-");
            stringBuilder.append(AsyncPlayer.this.mTag);
            super(stringBuilder.toString());
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            do {
                Object object;
                Serializable serializable = AsyncPlayer.this.mCmdQueue;
                synchronized (serializable) {
                    object = (Command)AsyncPlayer.this.mCmdQueue.removeFirst();
                }
                int n = ((Command)object).code;
                if (n != 1) {
                    if (n == 2) {
                        if (AsyncPlayer.this.mPlayer != null) {
                            long l = SystemClock.uptimeMillis() - ((Command)object).requestTime;
                            if (l > 1000L) {
                                object = AsyncPlayer.this.mTag;
                                serializable = new StringBuilder();
                                ((StringBuilder)serializable).append("Notification stop delayed by ");
                                ((StringBuilder)serializable).append(l);
                                ((StringBuilder)serializable).append("msecs");
                                Log.w((String)object, ((StringBuilder)serializable).toString());
                            }
                            AsyncPlayer.this.mPlayer.stop();
                            AsyncPlayer.this.mPlayer.release();
                            AsyncPlayer.this.mPlayer = null;
                        } else {
                            Log.w(AsyncPlayer.this.mTag, "STOP command without a player");
                        }
                    }
                } else {
                    AsyncPlayer.this.startSound((Command)object);
                }
                object = AsyncPlayer.this.mCmdQueue;
                synchronized (object) {
                    if (AsyncPlayer.this.mCmdQueue.size() == 0) {
                        AsyncPlayer.this.mThread = null;
                        AsyncPlayer.this.releaseWakeLock();
                        return;
                    }
                }
            } while (true);
        }
    }

}

