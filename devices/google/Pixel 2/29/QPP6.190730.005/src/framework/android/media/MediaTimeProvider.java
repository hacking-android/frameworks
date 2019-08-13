/*
 * Decompiled with CFR 0.145.
 */
package android.media;

public interface MediaTimeProvider {
    public static final long NO_TIME = -1L;

    public void cancelNotifications(OnMediaTimeListener var1);

    public long getCurrentTimeUs(boolean var1, boolean var2) throws IllegalStateException;

    public void notifyAt(long var1, OnMediaTimeListener var3);

    public void scheduleUpdate(OnMediaTimeListener var1);

    public static interface OnMediaTimeListener {
        public void onSeek(long var1);

        public void onStop();

        public void onTimedEvent(long var1);
    }

}

