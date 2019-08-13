/*
 * Decompiled with CFR 0.145.
 */
package android.media;

public final class MediaTimestamp {
    public static final MediaTimestamp TIMESTAMP_UNKNOWN = new MediaTimestamp(-1L, -1L, 0.0f);
    public final float clockRate;
    public final long mediaTimeUs;
    public final long nanoTime;

    MediaTimestamp() {
        this.mediaTimeUs = 0L;
        this.nanoTime = 0L;
        this.clockRate = 1.0f;
    }

    public MediaTimestamp(long l, long l2, float f) {
        this.mediaTimeUs = l;
        this.nanoTime = l2;
        this.clockRate = f;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (MediaTimestamp)object;
            if (this.mediaTimeUs != ((MediaTimestamp)object).mediaTimeUs || this.nanoTime != ((MediaTimestamp)object).nanoTime || this.clockRate != ((MediaTimestamp)object).clockRate) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public long getAnchorMediaTimeUs() {
        return this.mediaTimeUs;
    }

    public long getAnchorSystemNanoTime() {
        return this.nanoTime;
    }

    @Deprecated
    public long getAnchorSytemNanoTime() {
        return this.getAnchorSystemNanoTime();
    }

    public float getMediaClockRate() {
        return this.clockRate;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("{AnchorMediaTimeUs=");
        stringBuilder.append(this.mediaTimeUs);
        stringBuilder.append(" AnchorSystemNanoTime=");
        stringBuilder.append(this.nanoTime);
        stringBuilder.append(" clockRate=");
        stringBuilder.append(this.clockRate);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

