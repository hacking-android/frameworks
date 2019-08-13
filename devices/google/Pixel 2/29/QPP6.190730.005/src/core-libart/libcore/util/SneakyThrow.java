/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

public class SneakyThrow {
    private SneakyThrow() {
    }

    public static void sneakyThrow(Throwable throwable) {
        SneakyThrow.sneakyThrow_(throwable);
    }

    private static <T extends Throwable> void sneakyThrow_(Throwable throwable) throws Throwable {
        throw throwable;
    }
}

