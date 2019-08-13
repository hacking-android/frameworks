/*
 * Decompiled with CFR 0.145.
 */
package java.math;

public enum RoundingMode {
    UP(0),
    DOWN(1),
    CEILING(2),
    FLOOR(3),
    HALF_UP(4),
    HALF_DOWN(5),
    HALF_EVEN(6),
    UNNECESSARY(7);
    
    private final int bigDecimalRM;

    private RoundingMode(int n2) {
        this.bigDecimalRM = n2;
    }

    public static RoundingMode valueOf(String string) {
        return Enum.valueOf(RoundingMode.class, string);
    }
}

