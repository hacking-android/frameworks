/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import java.util.Arrays;
import java.util.UUID;

public abstract class DrmInitData {
    DrmInitData() {
    }

    public abstract SchemeInitData get(UUID var1);

    public static final class SchemeInitData {
        public final byte[] data;
        public final String mimeType;

        public SchemeInitData(String string2, byte[] arrby) {
            this.mimeType = string2;
            this.data = arrby;
        }

        public boolean equals(Object object) {
            if (!(object instanceof SchemeInitData)) {
                return false;
            }
            boolean bl = true;
            if (object == this) {
                return true;
            }
            object = (SchemeInitData)object;
            if (!this.mimeType.equals(((SchemeInitData)object).mimeType) || !Arrays.equals(this.data, ((SchemeInitData)object).data)) {
                bl = false;
            }
            return bl;
        }

        public int hashCode() {
            return this.mimeType.hashCode() + Arrays.hashCode(this.data) * 31;
        }
    }

}

