/*
 * Decompiled with CFR 0.145.
 */
package android.media;

public class MediaSyncEvent {
    public static final int SYNC_EVENT_NONE = 0;
    public static final int SYNC_EVENT_PRESENTATION_COMPLETE = 1;
    private int mAudioSession = 0;
    private final int mType;

    private MediaSyncEvent(int n) {
        this.mType = n;
    }

    public static MediaSyncEvent createEvent(int n) throws IllegalArgumentException {
        if (MediaSyncEvent.isValidType(n)) {
            return new MediaSyncEvent(n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("is not a valid MediaSyncEvent type.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static boolean isValidType(int n) {
        return n == 0 || n == 1;
    }

    public int getAudioSessionId() {
        return this.mAudioSession;
    }

    public int getType() {
        return this.mType;
    }

    public MediaSyncEvent setAudioSessionId(int n) throws IllegalArgumentException {
        if (n > 0) {
            this.mAudioSession = n;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is not a valid session ID.");
        throw new IllegalArgumentException(stringBuilder.toString());
    }
}

