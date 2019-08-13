/*
 * Decompiled with CFR 0.145.
 */
package android.media.session;

import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.MediaMetadata;
import android.media.Rating;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.ArrayMap;
import android.util.Log;
import android.view.KeyEvent;

public class MediaSessionLegacyHelper {
    private static final boolean DEBUG = Log.isLoggable("MediaSessionHelper", 3);
    private static final String TAG = "MediaSessionHelper";
    private static MediaSessionLegacyHelper sInstance;
    private static final Object sLock;
    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private MediaSessionManager mSessionManager;
    private ArrayMap<PendingIntent, SessionHolder> mSessions = new ArrayMap();

    static {
        sLock = new Object();
    }

    private MediaSessionLegacyHelper(Context context) {
        this.mContext = context;
        this.mSessionManager = (MediaSessionManager)context.getSystemService("media_session");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static MediaSessionLegacyHelper getHelper(Context context) {
        Object object = sLock;
        synchronized (object) {
            if (sInstance == null) {
                MediaSessionLegacyHelper mediaSessionLegacyHelper;
                sInstance = mediaSessionLegacyHelper = new MediaSessionLegacyHelper(context.getApplicationContext());
            }
            return sInstance;
        }
    }

    private SessionHolder getHolder(PendingIntent pendingIntent, boolean bl) {
        Object object;
        Object object2 = object = this.mSessions.get(pendingIntent);
        if (object == null) {
            object2 = object;
            if (bl) {
                object2 = this.mContext;
                object = new StringBuilder();
                ((StringBuilder)object).append("MediaSessionHelper-");
                ((StringBuilder)object).append(pendingIntent.getCreatorPackage());
                object2 = new MediaSession((Context)object2, ((StringBuilder)object).toString());
                ((MediaSession)object2).setActive(true);
                object2 = new SessionHolder((MediaSession)object2, pendingIntent);
                this.mSessions.put(pendingIntent, (SessionHolder)object2);
            }
        }
        return object2;
    }

    public static Bundle getOldMetadata(MediaMetadata mediaMetadata, int n, int n2) {
        boolean bl = n != -1 && n2 != -1;
        Bundle bundle = new Bundle();
        if (mediaMetadata.containsKey("android.media.metadata.ALBUM")) {
            bundle.putString(String.valueOf(1), mediaMetadata.getString("android.media.metadata.ALBUM"));
        }
        if (bl && mediaMetadata.containsKey("android.media.metadata.ART")) {
            bundle.putParcelable(String.valueOf(100), MediaSessionLegacyHelper.scaleBitmapIfTooBig(mediaMetadata.getBitmap("android.media.metadata.ART"), n, n2));
        } else if (bl && mediaMetadata.containsKey("android.media.metadata.ALBUM_ART")) {
            bundle.putParcelable(String.valueOf(100), MediaSessionLegacyHelper.scaleBitmapIfTooBig(mediaMetadata.getBitmap("android.media.metadata.ALBUM_ART"), n, n2));
        }
        if (mediaMetadata.containsKey("android.media.metadata.ALBUM_ARTIST")) {
            bundle.putString(String.valueOf(13), mediaMetadata.getString("android.media.metadata.ALBUM_ARTIST"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.ARTIST")) {
            bundle.putString(String.valueOf(2), mediaMetadata.getString("android.media.metadata.ARTIST"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.AUTHOR")) {
            bundle.putString(String.valueOf(3), mediaMetadata.getString("android.media.metadata.AUTHOR"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.COMPILATION")) {
            bundle.putString(String.valueOf(15), mediaMetadata.getString("android.media.metadata.COMPILATION"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.COMPOSER")) {
            bundle.putString(String.valueOf(4), mediaMetadata.getString("android.media.metadata.COMPOSER"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.DATE")) {
            bundle.putString(String.valueOf(5), mediaMetadata.getString("android.media.metadata.DATE"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.DISC_NUMBER")) {
            bundle.putLong(String.valueOf(14), mediaMetadata.getLong("android.media.metadata.DISC_NUMBER"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.DURATION")) {
            bundle.putLong(String.valueOf(9), mediaMetadata.getLong("android.media.metadata.DURATION"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.GENRE")) {
            bundle.putString(String.valueOf(6), mediaMetadata.getString("android.media.metadata.GENRE"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.NUM_TRACKS")) {
            bundle.putLong(String.valueOf(10), mediaMetadata.getLong("android.media.metadata.NUM_TRACKS"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.RATING")) {
            bundle.putParcelable(String.valueOf(101), mediaMetadata.getRating("android.media.metadata.RATING"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.USER_RATING")) {
            bundle.putParcelable(String.valueOf(268435457), mediaMetadata.getRating("android.media.metadata.USER_RATING"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.TITLE")) {
            bundle.putString(String.valueOf(7), mediaMetadata.getString("android.media.metadata.TITLE"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.TRACK_NUMBER")) {
            bundle.putLong(String.valueOf(0), mediaMetadata.getLong("android.media.metadata.TRACK_NUMBER"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.WRITER")) {
            bundle.putString(String.valueOf(11), mediaMetadata.getString("android.media.metadata.WRITER"));
        }
        if (mediaMetadata.containsKey("android.media.metadata.YEAR")) {
            bundle.putLong(String.valueOf(8), mediaMetadata.getLong("android.media.metadata.YEAR"));
        }
        return bundle;
    }

    private static Bitmap scaleBitmapIfTooBig(Bitmap object, int n, int n2) {
        Object object2;
        block4 : {
            int n3;
            Bitmap bitmap;
            int n4;
            block5 : {
                bitmap = object;
                object2 = bitmap;
                if (bitmap == null) break block4;
                n3 = ((Bitmap)object).getWidth();
                n4 = ((Bitmap)object).getHeight();
                if (n3 > n) break block5;
                object2 = bitmap;
                if (n4 <= n2) break block4;
            }
            float f = Math.min((float)n / (float)n3, (float)n2 / (float)n4);
            n = Math.round((float)n3 * f);
            n2 = Math.round((float)n4 * f);
            object = object2 = ((Bitmap)object).getConfig();
            if (object2 == null) {
                object = Bitmap.Config.ARGB_8888;
            }
            object2 = Bitmap.createBitmap(n, n2, (Bitmap.Config)((Object)object));
            Canvas canvas = new Canvas((Bitmap)object2);
            object = new Paint();
            ((Paint)object).setAntiAlias(true);
            ((Paint)object).setFilterBitmap(true);
            canvas.drawBitmap(bitmap, null, new RectF(0.0f, 0.0f, ((Bitmap)object2).getWidth(), ((Bitmap)object2).getHeight()), (Paint)object);
        }
        return object2;
    }

    private static void sendKeyEvent(PendingIntent pendingIntent, Context context, Intent intent) {
        try {
            pendingIntent.send(context, 0, intent);
            return;
        }
        catch (PendingIntent.CanceledException canceledException) {
            Log.e(TAG, "Error sending media key down event:", canceledException);
            return;
        }
    }

    public void addMediaButtonListener(PendingIntent pendingIntent, ComponentName object, Context context) {
        if (pendingIntent == null) {
            Log.w(TAG, "Pending intent was null, can't addMediaButtonListener.");
            return;
        }
        SessionHolder sessionHolder = this.getHolder(pendingIntent, true);
        if (sessionHolder == null) {
            return;
        }
        if (sessionHolder.mMediaButtonListener != null && DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("addMediaButtonListener already added ");
            ((StringBuilder)object).append(pendingIntent);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
        sessionHolder.mMediaButtonListener = new MediaButtonListener(pendingIntent, context);
        sessionHolder.mFlags = 1 | sessionHolder.mFlags;
        sessionHolder.mSession.setFlags(sessionHolder.mFlags);
        sessionHolder.mSession.setMediaButtonReceiver(pendingIntent);
        sessionHolder.update();
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("addMediaButtonListener added ");
            ((StringBuilder)object).append(pendingIntent);
            Log.d(TAG, ((StringBuilder)object).toString());
        }
    }

    public void addRccListener(PendingIntent pendingIntent, MediaSession.Callback object) {
        if (pendingIntent == null) {
            Log.w(TAG, "Pending intent was null, can't add rcc listener.");
            return;
        }
        SessionHolder sessionHolder = this.getHolder(pendingIntent, true);
        if (sessionHolder == null) {
            return;
        }
        if (sessionHolder.mRccListener != null && sessionHolder.mRccListener == object) {
            if (DEBUG) {
                Log.d(TAG, "addRccListener listener already added.");
            }
            return;
        }
        sessionHolder.mRccListener = object;
        sessionHolder.mFlags |= 2;
        sessionHolder.mSession.setFlags(sessionHolder.mFlags);
        sessionHolder.update();
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Added rcc listener for ");
            ((StringBuilder)object).append(pendingIntent);
            ((StringBuilder)object).append(".");
            Log.d(TAG, ((StringBuilder)object).toString());
        }
    }

    public MediaSession getSession(PendingIntent object) {
        object = (object = this.mSessions.get(object)) == null ? null : ((SessionHolder)object).mSession;
        return object;
    }

    public boolean isGlobalPriorityActive() {
        return this.mSessionManager.isGlobalPriorityActive();
    }

    public void removeMediaButtonListener(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        Object object = this.getHolder(pendingIntent, false);
        if (object != null && ((SessionHolder)object).mMediaButtonListener != null) {
            ((SessionHolder)object).mFlags &= -2;
            ((SessionHolder)object).mSession.setFlags(((SessionHolder)object).mFlags);
            ((SessionHolder)object).mMediaButtonListener = null;
            ((SessionHolder)object).update();
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("removeMediaButtonListener removed ");
                ((StringBuilder)object).append(pendingIntent);
                Log.d(TAG, ((StringBuilder)object).toString());
            }
        }
    }

    public void removeRccListener(PendingIntent pendingIntent) {
        if (pendingIntent == null) {
            return;
        }
        Object object = this.getHolder(pendingIntent, false);
        if (object != null && ((SessionHolder)object).mRccListener != null) {
            ((SessionHolder)object).mRccListener = null;
            ((SessionHolder)object).mFlags &= -3;
            ((SessionHolder)object).mSession.setFlags(((SessionHolder)object).mFlags);
            ((SessionHolder)object).update();
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Removed rcc listener for ");
                ((StringBuilder)object).append(pendingIntent);
                ((StringBuilder)object).append(".");
                Log.d(TAG, ((StringBuilder)object).toString());
            }
        }
    }

    public void sendAdjustVolumeBy(int n, int n2, int n3) {
        this.mSessionManager.dispatchAdjustVolume(n, n2, n3);
        if (DEBUG) {
            Log.d(TAG, "dispatched volume adjustment");
        }
    }

    public void sendMediaButtonEvent(KeyEvent keyEvent, boolean bl) {
        if (keyEvent == null) {
            Log.w(TAG, "Tried to send a null key event. Ignoring.");
            return;
        }
        this.mSessionManager.dispatchMediaKeyEvent(keyEvent, bl);
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("dispatched media key ");
            stringBuilder.append(keyEvent);
            Log.d(TAG, stringBuilder.toString());
        }
    }

    public void sendVolumeKeyEvent(KeyEvent keyEvent, int n, boolean bl) {
        if (keyEvent == null) {
            Log.w(TAG, "Tried to send a null key event. Ignoring.");
            return;
        }
        this.mSessionManager.dispatchVolumeKeyEvent(keyEvent, n, bl);
    }

    private static final class MediaButtonListener
    extends MediaSession.Callback {
        private final Context mContext;
        private final PendingIntent mPendingIntent;

        public MediaButtonListener(PendingIntent pendingIntent, Context context) {
            this.mPendingIntent = pendingIntent;
            this.mContext = context;
        }

        private void sendKeyEvent(int n) {
            KeyEvent keyEvent = new KeyEvent(0, n);
            Object object = new Intent("android.intent.action.MEDIA_BUTTON");
            ((Intent)object).addFlags(268435456);
            ((Intent)object).putExtra("android.intent.extra.KEY_EVENT", keyEvent);
            MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, (Intent)object);
            ((Intent)object).putExtra("android.intent.extra.KEY_EVENT", new KeyEvent(1, n));
            MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, (Intent)object);
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Sent ");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append(" to pending intent ");
                ((StringBuilder)object).append(this.mPendingIntent);
                Log.d(MediaSessionLegacyHelper.TAG, ((StringBuilder)object).toString());
            }
        }

        @Override
        public void onFastForward() {
            this.sendKeyEvent(90);
        }

        @Override
        public boolean onMediaButtonEvent(Intent intent) {
            MediaSessionLegacyHelper.sendKeyEvent(this.mPendingIntent, this.mContext, intent);
            return true;
        }

        @Override
        public void onPause() {
            this.sendKeyEvent(127);
        }

        @Override
        public void onPlay() {
            this.sendKeyEvent(126);
        }

        @Override
        public void onRewind() {
            this.sendKeyEvent(89);
        }

        @Override
        public void onSkipToNext() {
            this.sendKeyEvent(87);
        }

        @Override
        public void onSkipToPrevious() {
            this.sendKeyEvent(88);
        }

        @Override
        public void onStop() {
            this.sendKeyEvent(86);
        }
    }

    private class SessionHolder {
        public SessionCallback mCb;
        public int mFlags;
        public MediaButtonListener mMediaButtonListener;
        public final PendingIntent mPi;
        public MediaSession.Callback mRccListener;
        public final MediaSession mSession;

        public SessionHolder(MediaSession mediaSession, PendingIntent pendingIntent) {
            this.mSession = mediaSession;
            this.mPi = pendingIntent;
        }

        public void update() {
            if (this.mMediaButtonListener == null && this.mRccListener == null) {
                this.mSession.setCallback(null);
                this.mSession.release();
                this.mCb = null;
                MediaSessionLegacyHelper.this.mSessions.remove(this.mPi);
            } else if (this.mCb == null) {
                this.mCb = new SessionCallback();
                Handler handler = new Handler(Looper.getMainLooper());
                this.mSession.setCallback(this.mCb, handler);
            }
        }

        private class SessionCallback
        extends MediaSession.Callback {
            private SessionCallback() {
            }

            @Override
            public void onFastForward() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onFastForward();
                }
            }

            @Override
            public boolean onMediaButtonEvent(Intent intent) {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onMediaButtonEvent(intent);
                }
                return true;
            }

            @Override
            public void onPause() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onPause();
                }
            }

            @Override
            public void onPlay() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onPlay();
                }
            }

            @Override
            public void onRewind() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onRewind();
                }
            }

            @Override
            public void onSeekTo(long l) {
                if (SessionHolder.this.mRccListener != null) {
                    SessionHolder.this.mRccListener.onSeekTo(l);
                }
            }

            @Override
            public void onSetRating(Rating rating) {
                if (SessionHolder.this.mRccListener != null) {
                    SessionHolder.this.mRccListener.onSetRating(rating);
                }
            }

            @Override
            public void onSkipToNext() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onSkipToNext();
                }
            }

            @Override
            public void onSkipToPrevious() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onSkipToPrevious();
                }
            }

            @Override
            public void onStop() {
                if (SessionHolder.this.mMediaButtonListener != null) {
                    SessionHolder.this.mMediaButtonListener.onStop();
                }
            }
        }

    }

}

