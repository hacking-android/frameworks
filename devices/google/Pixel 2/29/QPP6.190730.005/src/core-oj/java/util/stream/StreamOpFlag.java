/*
 * Decompiled with CFR 0.145.
 */
package java.util.stream;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Spliterator;

public final class StreamOpFlag
extends Enum<StreamOpFlag> {
    private static final /* synthetic */ StreamOpFlag[] $VALUES;
    private static final int CLEAR_BITS = 2;
    public static final /* enum */ StreamOpFlag DISTINCT;
    private static final int FLAG_MASK;
    private static final int FLAG_MASK_IS;
    private static final int FLAG_MASK_NOT;
    public static final int INITIAL_OPS_VALUE;
    public static final int IS_DISTINCT;
    public static final int IS_ORDERED;
    public static final int IS_SHORT_CIRCUIT;
    public static final int IS_SIZED;
    public static final int IS_SORTED;
    public static final int NOT_DISTINCT;
    public static final int NOT_ORDERED;
    public static final int NOT_SIZED;
    public static final int NOT_SORTED;
    public static final int OP_MASK;
    public static final /* enum */ StreamOpFlag ORDERED;
    private static final int PRESERVE_BITS = 3;
    private static final int SET_BITS = 1;
    public static final /* enum */ StreamOpFlag SHORT_CIRCUIT;
    public static final /* enum */ StreamOpFlag SIZED;
    public static final /* enum */ StreamOpFlag SORTED;
    public static final int SPLITERATOR_CHARACTERISTICS_MASK;
    public static final int STREAM_MASK;
    public static final int TERMINAL_OP_MASK;
    public static final int UPSTREAM_TERMINAL_OP_MASK;
    private final int bitPosition;
    private final int clear;
    private final Map<Type, Integer> maskTable;
    private final int preserve;
    private final int set;

    static {
        int n;
        DISTINCT = new StreamOpFlag(0, StreamOpFlag.set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP));
        SORTED = new StreamOpFlag(1, StreamOpFlag.set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP));
        ORDERED = new StreamOpFlag(2, StreamOpFlag.set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP).clear(Type.TERMINAL_OP).clear(Type.UPSTREAM_TERMINAL_OP));
        SIZED = new StreamOpFlag(3, StreamOpFlag.set(Type.SPLITERATOR).set(Type.STREAM).clear(Type.OP));
        SHORT_CIRCUIT = new StreamOpFlag(12, StreamOpFlag.set(Type.OP).set(Type.TERMINAL_OP));
        $VALUES = new StreamOpFlag[]{DISTINCT, SORTED, ORDERED, SIZED, SHORT_CIRCUIT};
        SPLITERATOR_CHARACTERISTICS_MASK = StreamOpFlag.createMask(Type.SPLITERATOR);
        STREAM_MASK = StreamOpFlag.createMask(Type.STREAM);
        OP_MASK = StreamOpFlag.createMask(Type.OP);
        TERMINAL_OP_MASK = StreamOpFlag.createMask(Type.TERMINAL_OP);
        UPSTREAM_TERMINAL_OP_MASK = StreamOpFlag.createMask(Type.UPSTREAM_TERMINAL_OP);
        FLAG_MASK = StreamOpFlag.createFlagMask();
        FLAG_MASK_IS = n = STREAM_MASK;
        FLAG_MASK_NOT = n << 1;
        INITIAL_OPS_VALUE = FLAG_MASK_IS | FLAG_MASK_NOT;
        StreamOpFlag streamOpFlag = DISTINCT;
        IS_DISTINCT = streamOpFlag.set;
        NOT_DISTINCT = streamOpFlag.clear;
        streamOpFlag = SORTED;
        IS_SORTED = streamOpFlag.set;
        NOT_SORTED = streamOpFlag.clear;
        streamOpFlag = ORDERED;
        IS_ORDERED = streamOpFlag.set;
        NOT_ORDERED = streamOpFlag.clear;
        streamOpFlag = SIZED;
        IS_SIZED = streamOpFlag.set;
        NOT_SIZED = streamOpFlag.clear;
        IS_SHORT_CIRCUIT = StreamOpFlag.SHORT_CIRCUIT.set;
    }

    private StreamOpFlag(int n2, MaskBuilder maskBuilder) {
        this.maskTable = maskBuilder.build();
        this.bitPosition = n = n2 * 2;
        this.set = 1 << n;
        this.clear = 2 << n;
        this.preserve = 3 << n;
    }

    public static int combineOpFlags(int n, int n2) {
        return StreamOpFlag.getMask(n) & n2 | n;
    }

    private static int createFlagMask() {
        int n = 0;
        StreamOpFlag[] arrstreamOpFlag = StreamOpFlag.values();
        int n2 = arrstreamOpFlag.length;
        for (int i = 0; i < n2; ++i) {
            n |= arrstreamOpFlag[i].preserve;
        }
        return n;
    }

    private static int createMask(Type type) {
        int n = 0;
        for (StreamOpFlag streamOpFlag : StreamOpFlag.values()) {
            n |= streamOpFlag.maskTable.get((Object)type) << streamOpFlag.bitPosition;
        }
        return n;
    }

    public static int fromCharacteristics(int n) {
        return SPLITERATOR_CHARACTERISTICS_MASK & n;
    }

    public static int fromCharacteristics(Spliterator<?> spliterator) {
        int n = spliterator.characteristics();
        if ((n & 4) != 0 && spliterator.getComparator() != null) {
            return SPLITERATOR_CHARACTERISTICS_MASK & n & -5;
        }
        return SPLITERATOR_CHARACTERISTICS_MASK & n;
    }

    private static int getMask(int n) {
        n = n == 0 ? FLAG_MASK : (FLAG_MASK_IS & n) << 1 | n | (FLAG_MASK_NOT & n) >> 1;
        return n;
    }

    private static MaskBuilder set(Type type) {
        return new MaskBuilder(new EnumMap<Type, Integer>(Type.class)).set(type);
    }

    public static int toCharacteristics(int n) {
        return SPLITERATOR_CHARACTERISTICS_MASK & n;
    }

    public static int toStreamFlags(int n) {
        return n >> 1 & FLAG_MASK_IS & n;
    }

    public static StreamOpFlag valueOf(String string) {
        return Enum.valueOf(StreamOpFlag.class, string);
    }

    public static StreamOpFlag[] values() {
        return (StreamOpFlag[])$VALUES.clone();
    }

    public boolean canSet(Type type) {
        int n = this.maskTable.get((Object)type);
        boolean bl = true;
        if ((n & 1) <= 0) {
            bl = false;
        }
        return bl;
    }

    public int clear() {
        return this.clear;
    }

    public boolean isCleared(int n) {
        boolean bl = (this.preserve & n) == this.clear;
        return bl;
    }

    public boolean isKnown(int n) {
        boolean bl = (this.preserve & n) == this.set;
        return bl;
    }

    public boolean isPreserved(int n) {
        int n2 = this.preserve;
        boolean bl = (n & n2) == n2;
        return bl;
    }

    public boolean isStreamFlag() {
        boolean bl = this.maskTable.get((Object)Type.STREAM) > 0;
        return bl;
    }

    public int set() {
        return this.set;
    }

    private static class MaskBuilder {
        final Map<Type, Integer> map;

        MaskBuilder(Map<Type, Integer> map) {
            this.map = map;
        }

        Map<Type, Integer> build() {
            for (Type type : Type.values()) {
                this.map.putIfAbsent(type, 0);
            }
            return this.map;
        }

        MaskBuilder clear(Type type) {
            return this.mask(type, 2);
        }

        MaskBuilder mask(Type type, Integer n) {
            this.map.put(type, n);
            return this;
        }

        MaskBuilder set(Type type) {
            return this.mask(type, 1);
        }

        MaskBuilder setAndClear(Type type) {
            return this.mask(type, 3);
        }
    }

    static enum Type {
        SPLITERATOR,
        STREAM,
        OP,
        TERMINAL_OP,
        UPSTREAM_TERMINAL_OP;
        
    }

}

