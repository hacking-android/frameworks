/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;

class AudioHandle {
    @UnsupportedAppUsage
    private final int mId;

    @UnsupportedAppUsage
    AudioHandle(int n) {
        this.mId = n;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null && object instanceof AudioHandle) {
            if (this.mId == ((AudioHandle)(object = (AudioHandle)object)).id()) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public int hashCode() {
        return this.mId;
    }

    int id() {
        return this.mId;
    }

    public String toString() {
        return Integer.toString(this.mId);
    }
}

