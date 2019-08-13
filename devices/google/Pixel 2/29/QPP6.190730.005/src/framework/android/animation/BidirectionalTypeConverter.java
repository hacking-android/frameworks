/*
 * Decompiled with CFR 0.145.
 */
package android.animation;

import android.animation.TypeConverter;

public abstract class BidirectionalTypeConverter<T, V>
extends TypeConverter<T, V> {
    private BidirectionalTypeConverter mInvertedConverter;

    public BidirectionalTypeConverter(Class<T> class_, Class<V> class_2) {
        super(class_, class_2);
    }

    public abstract T convertBack(V var1);

    public BidirectionalTypeConverter<V, T> invert() {
        if (this.mInvertedConverter == null) {
            this.mInvertedConverter = new InvertedConverter<From, To>(this);
        }
        return this.mInvertedConverter;
    }

    private static class InvertedConverter<From, To>
    extends BidirectionalTypeConverter<From, To> {
        private BidirectionalTypeConverter<To, From> mConverter;

        public InvertedConverter(BidirectionalTypeConverter<To, From> bidirectionalTypeConverter) {
            super(bidirectionalTypeConverter.getTargetType(), bidirectionalTypeConverter.getSourceType());
            this.mConverter = bidirectionalTypeConverter;
        }

        @Override
        public To convert(From From) {
            return this.mConverter.convertBack(From);
        }

        @Override
        public From convertBack(To To) {
            return (From)this.mConverter.convert(To);
        }
    }

}

