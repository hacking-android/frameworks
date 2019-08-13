/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.lowpan.LowpanProperty;

public final class LowpanProperties {
    public static final LowpanProperty<int[]> KEY_CHANNEL_MASK = new LowpanStandardProperty<int[]>("android.net.lowpan.property.CHANNEL_MASK", int[].class);
    public static final LowpanProperty<Integer> KEY_MAX_TX_POWER = new LowpanStandardProperty<Integer>("android.net.lowpan.property.MAX_TX_POWER", Integer.class);

    private LowpanProperties() {
    }

    static final class LowpanStandardProperty<T>
    extends LowpanProperty<T> {
        private final String mName;
        private final Class<T> mType;

        LowpanStandardProperty(String string2, Class<T> class_) {
            this.mName = string2;
            this.mType = class_;
        }

        @Override
        public String getName() {
            return this.mName;
        }

        @Override
        public Class<T> getType() {
            return this.mType;
        }

        public String toString() {
            return this.getName();
        }
    }

}

