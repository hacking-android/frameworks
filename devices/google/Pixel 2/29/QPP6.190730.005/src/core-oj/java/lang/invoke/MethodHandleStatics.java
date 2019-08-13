/*
 * Decompiled with CFR 0.145.
 */
package java.lang.invoke;

import sun.misc.Unsafe;

class MethodHandleStatics {
    static final Unsafe UNSAFE = Unsafe.getUnsafe();

    private MethodHandleStatics() {
    }

    static Error NYI() {
        throw new AssertionError((Object)"NYI");
    }

    private static String message(String string, Object object) {
        CharSequence charSequence = string;
        if (object != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(object);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    private static String message(String string, Object object, Object object2) {
        CharSequence charSequence;
        block3 : {
            block2 : {
                if (object != null) break block2;
                charSequence = string;
                if (object2 == null) break block3;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(object);
            ((StringBuilder)charSequence).append(", ");
            ((StringBuilder)charSequence).append(object2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    static RuntimeException newIllegalArgumentException(String string) {
        return new IllegalArgumentException(string);
    }

    static RuntimeException newIllegalArgumentException(String string, Object object) {
        return new IllegalArgumentException(MethodHandleStatics.message(string, object));
    }

    static RuntimeException newIllegalArgumentException(String string, Object object, Object object2) {
        return new IllegalArgumentException(MethodHandleStatics.message(string, object, object2));
    }

    static RuntimeException newIllegalStateException(String string) {
        return new IllegalStateException(string);
    }

    static RuntimeException newIllegalStateException(String string, Object object) {
        return new IllegalStateException(MethodHandleStatics.message(string, object));
    }

    static InternalError newInternalError(String string) {
        return new InternalError(string);
    }

    static InternalError newInternalError(String string, Throwable throwable) {
        return new InternalError(string, throwable);
    }

    static InternalError newInternalError(Throwable throwable) {
        return new InternalError(throwable);
    }

    static Error uncaughtException(Throwable throwable) {
        if (!(throwable instanceof Error)) {
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            throw MethodHandleStatics.newInternalError("uncaught exception", throwable);
        }
        throw (Error)throwable;
    }
}

