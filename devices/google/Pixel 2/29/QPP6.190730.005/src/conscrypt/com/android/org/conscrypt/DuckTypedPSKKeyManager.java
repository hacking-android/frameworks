/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.PSKKeyManager;
import java.lang.reflect.Method;
import java.net.Socket;
import javax.crypto.SecretKey;
import javax.net.ssl.SSLEngine;

@Deprecated
final class DuckTypedPSKKeyManager
implements PSKKeyManager {
    private final Object mDelegate;

    private DuckTypedPSKKeyManager(Object object) {
        this.mDelegate = object;
    }

    static DuckTypedPSKKeyManager getInstance(Object object) throws NoSuchMethodException {
        Class<?> class_ = object.getClass();
        for (Method method : PSKKeyManager.class.getMethods()) {
            if (method.isSynthetic()) continue;
            Method method2 = class_.getMethod(method.getName(), method.getParameterTypes());
            Class<?> class_2 = method2.getReturnType();
            Class<?> class_3 = method.getReturnType();
            if (class_3.isAssignableFrom(class_2)) {
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(method2);
            ((StringBuilder)object).append(" return value (");
            ((StringBuilder)object).append(class_2);
            ((StringBuilder)object).append(") incompatible with target return value (");
            ((StringBuilder)object).append(class_3);
            ((StringBuilder)object).append(")");
            throw new NoSuchMethodException(((StringBuilder)object).toString());
        }
        return new DuckTypedPSKKeyManager(object);
    }

    @Override
    public String chooseClientKeyIdentity(String string, Socket socket) {
        try {
            string = (String)this.mDelegate.getClass().getMethod("chooseClientKeyIdentity", String.class, Socket.class).invoke(this.mDelegate, string, socket);
            return string;
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to invoke chooseClientKeyIdentity", exception);
        }
    }

    @Override
    public String chooseClientKeyIdentity(String string, SSLEngine sSLEngine) {
        try {
            string = (String)this.mDelegate.getClass().getMethod("chooseClientKeyIdentity", String.class, SSLEngine.class).invoke(this.mDelegate, string, sSLEngine);
            return string;
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to invoke chooseClientKeyIdentity", exception);
        }
    }

    @Override
    public String chooseServerKeyIdentityHint(Socket object) {
        try {
            object = (String)this.mDelegate.getClass().getMethod("chooseServerKeyIdentityHint", Socket.class).invoke(this.mDelegate, object);
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to invoke chooseServerKeyIdentityHint", exception);
        }
    }

    @Override
    public String chooseServerKeyIdentityHint(SSLEngine object) {
        try {
            object = (String)this.mDelegate.getClass().getMethod("chooseServerKeyIdentityHint", SSLEngine.class).invoke(this.mDelegate, object);
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to invoke chooseServerKeyIdentityHint", exception);
        }
    }

    @Override
    public SecretKey getKey(String object, String string, Socket socket) {
        try {
            object = (SecretKey)this.mDelegate.getClass().getMethod("getKey", String.class, String.class, Socket.class).invoke(this.mDelegate, object, string, socket);
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to invoke getKey", exception);
        }
    }

    @Override
    public SecretKey getKey(String object, String string, SSLEngine sSLEngine) {
        try {
            object = (SecretKey)this.mDelegate.getClass().getMethod("getKey", String.class, String.class, SSLEngine.class).invoke(this.mDelegate, object, string, sSLEngine);
            return object;
        }
        catch (Exception exception) {
            throw new RuntimeException("Failed to invoke getKey", exception);
        }
    }
}

