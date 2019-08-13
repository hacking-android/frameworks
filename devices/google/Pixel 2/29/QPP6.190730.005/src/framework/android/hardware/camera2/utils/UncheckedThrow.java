/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.utils;

public class UncheckedThrow {
    public static void throwAnyException(Exception exception) {
        UncheckedThrow.throwAnyImpl(exception);
    }

    public static void throwAnyException(Throwable throwable) {
        UncheckedThrow.throwAnyImpl(throwable);
    }

    private static <T extends Throwable> void throwAnyImpl(Throwable throwable) throws Throwable {
        throw throwable;
    }
}

