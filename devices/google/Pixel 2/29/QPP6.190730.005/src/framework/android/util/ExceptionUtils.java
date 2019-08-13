/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.os.ParcelableException;
import com.android.internal.util.Preconditions;
import java.io.IOException;

public class ExceptionUtils {
    public static Throwable appendCause(Throwable throwable, Throwable throwable2) {
        if (throwable2 != null) {
            ExceptionUtils.getRootCause(throwable).initCause(throwable2);
        }
        return throwable;
    }

    public static String getCompleteMessage(String object, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        if (object != null) {
            stringBuilder.append((String)object);
            stringBuilder.append(": ");
        }
        stringBuilder.append(throwable.getMessage());
        object = throwable;
        do {
            throwable = ((Throwable)object).getCause();
            object = throwable;
            if (throwable == null) break;
            stringBuilder.append(": ");
            stringBuilder.append(((Throwable)object).getMessage());
        } while (true);
        return stringBuilder.toString();
    }

    public static String getCompleteMessage(Throwable throwable) {
        return ExceptionUtils.getCompleteMessage(null, throwable);
    }

    public static Throwable getRootCause(Throwable throwable) {
        while (throwable.getCause() != null) {
            throwable = throwable.getCause();
        }
        return throwable;
    }

    public static void maybeUnwrapIOException(RuntimeException runtimeException) throws IOException {
        if (runtimeException instanceof ParcelableException) {
            ((ParcelableException)runtimeException).maybeRethrow(IOException.class);
        }
    }

    public static RuntimeException propagate(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        ExceptionUtils.propagateIfInstanceOf(throwable, Error.class);
        ExceptionUtils.propagateIfInstanceOf(throwable, RuntimeException.class);
        throw new RuntimeException(throwable);
    }

    public static <E extends Exception> RuntimeException propagate(Throwable throwable, Class<E> class_) throws Exception {
        ExceptionUtils.propagateIfInstanceOf(throwable, class_);
        return ExceptionUtils.propagate(throwable);
    }

    public static <E extends Throwable> void propagateIfInstanceOf(Throwable throwable, Class<E> class_) throws Throwable {
        if (throwable != null && class_.isInstance(throwable)) {
            throw (Throwable)class_.cast(throwable);
        }
    }

    public static RuntimeException wrap(IOException iOException) {
        throw new ParcelableException(iOException);
    }
}

