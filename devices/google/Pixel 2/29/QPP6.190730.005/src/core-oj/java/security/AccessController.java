/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AccessControlContext;
import java.security.AccessControlException;
import java.security.Permission;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

public final class AccessController {
    private AccessController() {
    }

    public static void checkPermission(Permission permission) throws AccessControlException {
    }

    public static <T> T doPrivileged(PrivilegedAction<T> privilegedAction) {
        return privilegedAction.run();
    }

    public static <T> T doPrivileged(PrivilegedAction<T> privilegedAction, AccessControlContext accessControlContext) {
        return privilegedAction.run();
    }

    public static <T> T doPrivileged(PrivilegedExceptionAction<T> privilegedExceptionAction) throws PrivilegedActionException {
        try {
            privilegedExceptionAction = privilegedExceptionAction.run();
        }
        catch (Exception exception) {
            throw new PrivilegedActionException(exception);
        }
        catch (RuntimeException runtimeException) {
            throw runtimeException;
        }
        return (T)privilegedExceptionAction;
    }

    public static <T> T doPrivileged(PrivilegedExceptionAction<T> privilegedExceptionAction, AccessControlContext accessControlContext) throws PrivilegedActionException {
        return AccessController.doPrivileged(privilegedExceptionAction);
    }

    public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> privilegedAction) {
        return privilegedAction.run();
    }

    public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> privilegedExceptionAction) throws PrivilegedActionException {
        return AccessController.doPrivileged(privilegedExceptionAction);
    }

    public static AccessControlContext getContext() {
        return new AccessControlContext(null);
    }
}

