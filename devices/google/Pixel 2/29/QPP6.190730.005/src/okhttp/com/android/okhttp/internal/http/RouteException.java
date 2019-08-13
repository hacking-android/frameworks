/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class RouteException
extends Exception {
    private static final Method addSuppressedExceptionMethod;
    private IOException lastException;

    static {
        Method method;
        try {
            method = Throwable.class.getDeclaredMethod("addSuppressed", Throwable.class);
        }
        catch (Exception exception) {
            method = null;
        }
        addSuppressedExceptionMethod = method;
    }

    public RouteException(IOException iOException) {
        super(iOException);
        this.lastException = iOException;
    }

    private void addSuppressedIfPossible(IOException iOException, IOException iOException2) {
        Method method = addSuppressedExceptionMethod;
        if (method != null) {
            try {
                method.invoke(iOException, iOException2);
            }
            catch (IllegalAccessException | InvocationTargetException reflectiveOperationException) {
                // empty catch block
            }
        }
    }

    public void addConnectException(IOException iOException) {
        this.addSuppressedIfPossible(iOException, this.lastException);
        this.lastException = iOException;
    }

    public IOException getLastConnectException() {
        return this.lastException;
    }
}

